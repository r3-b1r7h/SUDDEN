package biz.sudden.knowledgeData.kdm.web.controller.impl;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.ConnectableUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.baseAndUtility.web.controller.tree.UserObject.UserObjectActionEvent;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.CMControllerImpl;
import biz.sudden.knowledgeData.kdm.service.KdmService;
import biz.sudden.knowledgeData.kdm.web.controller.KdmController;

import com.icesoft.faces.component.inputfile.InputFile;

import datasource.HsqldbDataSource;
import edu.emory.mathcs.backport.java.util.concurrent.ThreadPoolExecutor;

public class KdmControllerImpl implements KdmController, ActionListener {

	private Logger logger = Logger.getLogger(this.getClass());

	private KdmService kdmService;
	private CMControllerImpl competencesmanagementController;
	private ScopeController scopeController;

	private boolean popupIsVisible = false;

	private boolean refresh = false;
	private boolean showassociations = true;
	private boolean showoccurrences = false;
	private int showTreeDepth = 1;

	private int width;
	private int depth;

	private long lasttime = 0;
	private int lastcount = 0;
	private StringBuffer performanceinfo = new StringBuffer();

	private boolean inBuild = false;

	private StringBuffer nodedetailview;
	private boolean showNodeDetails;

	private File ontologyUploadFile;

	private ThreadPoolExecutor executor;

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	public KdmControllerImpl() {
		logger.debug("KdmControllerImpl -> cst");
	}

	@Override
	public void clean() {
		kdmService.clean();
		// also set the competences management
		// don't do this if the data with respect to questionnaire is cleaned
		// using the Competence management Module
		// competencesmanagementController.removeAll();

		popupIsVisible = false;
		refresh = true;
		// getConnectableTree();
	}

	@Override
	public void upload(ActionEvent e) {
		InputFile inputFile = (InputFile) e.getSource();
		ontologyUploadFile = inputFile.getFile();
	}

	@Override
	public void initOntology() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		// final WebApplicationContext webAppContext =
		// WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());

		// this below causes troubles:
		// for competencesmanagement all organisations and other stuff needs to
		// be available.
		// the executer causes problems. why??
		// executor.execute(new Runnable() {
		// @Override
		// public void run() {
		kdmService.initOntology(ontologyUploadFile);
		// also set the competences management
		// don't do this when data with respect to questionnaire is initialized
		// using the Competence management Module
		// competencesmanagementController.init();
		// competencesmanagementController.insertDBTestData();
		// }
		// });

		popupIsVisible = false;
		refresh = true;
		// getConnectableTree();
	}

	@Override
	public void initPMS() {
		kdmService.initPMS();
	}

	@Override
	public void initCompanyPerformance() {
		kdmService.initCompanyPerformance();
		popupIsVisible = false;
		refresh = true;
	}

	public void initForum() {
		kdmService.getBulletinBoardService().deleteBulletinBoards();
		kdmService.getBulletinBoardService().initializeBulletinBoards();
	}

	@Override
	public void initTest() {
		kdmService.initTest(this.width, this.depth);
		refresh = true;
	}

	// FIXME: Performance impact
	@Override
	public Tree getConnectableTree() {
		Tree result = kdmService.retrieveTree(scopeController);
		if (refresh) {
			logger.warn("getConnectableTree refresh");
			refresh = false;
			result.clearTree();
			buildTree();
		}
		// logger.warn("getConnectableTree: "+result);
		return result;
	}

	@Override
	public void refreshTree(ActionEvent ae) {
		refreshTree();
	}

	@Override
	public void refreshTree() {
		logger.warn("refreshTree");
		refresh = true;
		// getConnectableTree is called quite often; so don't do it here.
		// getConnectableTree();
	}

	@Override
	public void processAction(ActionEvent arg0) throws AbortProcessingException {
		logger.debug("KdmControllerImp: processAction: " + arg0);
		if (arg0 instanceof UserObjectActionEvent) {
			nodedetailview = new StringBuffer(
					"<table style=\"border:1px solid red;size:smaller; background-repeat:no-repeat; background-position:center center; background-attachment:scroll; background-image: url('themes/default/img/sudden-small.png'); \">");
			Object x = ((UserObjectActionEvent) arg0).getUserObject();
			if (x instanceof ConnectableUserObject)
				x = ((ConnectableUserObject) x).getReference();

			Method[] methods = x.getClass().getMethods();
			for (int i = 0; i < methods.length; ++i) {
				if (methods[i].getName().startsWith("get")
						&& methods[i].getParameterTypes().length == 0) {
					nodedetailview.append("<tr><td>");
					nodedetailview.append(methods[i].getName().substring(3));
					nodedetailview.append("</td><td>");
					try {
						String result = methods[i].invoke(x, new Object[] {})
								.toString();
						if (result.length() > 60)
							nodedetailview.append(result.substring(0, 60))
									.append("...");
						else
							nodedetailview.append(result);
					} catch (Exception e) {
					}
					nodedetailview.append("</td></tr>");
				}
			}
			nodedetailview.append("</table>");
			showNodeDetails();
		}
	}

	@Override
	public SelectItem[] getSelectShowAssociations() {
		return new SelectItem[] {
				new SelectItem(Boolean.TRUE, "Show Associations"),
				new SelectItem(Boolean.FALSE, "Hide Associations") };
	}

	@Override
	public SelectItem[] getSelectShowOccurrences() {
		return new SelectItem[] {
				new SelectItem(Boolean.FALSE, "Hide Occurrences"),
				new SelectItem(Boolean.TRUE, "Show Occurrences") };
	}

	@Override
	public void setShowAssociations(Boolean show) {
		showassociations = show.booleanValue();
	}

	@Override
	public Boolean getShowAssociations() {
		return new Boolean(showassociations);
	}

	@Override
	public void setShowOccurrences(Boolean show) {
		showoccurrences = show.booleanValue();
	}

	@Override
	public Boolean getShowOccurrences() {
		return new Boolean(showoccurrences);
	}

	@Override
	public void showPopup() {
		popupIsVisible = true;
	}

	@Override
	public void hidePopup() {
		popupIsVisible = false;
	}

	@Override
	public void setPopupIsVisible(boolean popupIsVisible) {
		this.popupIsVisible = popupIsVisible;
	}

	@Override
	public boolean getPopupIsVisible() {
		return popupIsVisible;
	}

	@Override
	public void showNodeDetails() {
		showNodeDetails = true;
	}

	@Override
	public void hideNodeDetails() {
		showNodeDetails = false;
	}

	@Override
	public String getNodeDetails() {
		return nodedetailview.toString();
	}

	@Override
	public void setShowNodeDetails(boolean popupIsVisible) {
		this.showNodeDetails = popupIsVisible;
	}

	@Override
	public boolean getShowNodeDetails() {
		return showNodeDetails;
	}

	@Override
	public KdmService getKDMService() {
		return kdmService;
	}

	@Override
	public void setKDMService(KdmService kdmService) {
		this.kdmService = kdmService;
	}

	@Override
	public void setScopeController(ScopeController sc) {
		scopeController = sc;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getDepth() {
		return this.depth;
	}

	@Override
	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public String getPerformanceInfo() {
		return performanceinfo.toString();
	}

	@Override
	public String getNodeCount() {
		double size = 0.0d;
		for (int i = depth + 1; i >= 0; --i) {
			size += Math.pow(width, i);
		}
		return new StringBuffer(5).append(size).toString();
	}

	@Override
	public void setPerformanceInfo(String performanceinfo) {
		this.performanceinfo = new StringBuffer(performanceinfo);
	}

	// FIXME: Performance !.... it seems this is called a number of times in
	// parallel
	private void buildTree() {
		long systime = java.lang.System.currentTimeMillis();
		if (!inBuild) {
			// lets try to call it only once.
			inBuild = true;
			resetDuration();
			resetConnections();

			this.kdmService.getDebugMsg().append(
					" connections "
							+ (java.lang.System.currentTimeMillis() - systime));
			systime = java.lang.System.currentTimeMillis();
			java.lang.System.err.println(this.kdmService.getDebugMsg());

			List<Connectable> conns = kdmService.retrieveAllConnectables();

			this.kdmService.getDebugMsg().append(
					" retrieve conns "
							+ (java.lang.System.currentTimeMillis() - systime));
			systime = java.lang.System.currentTimeMillis();
			java.lang.System.err.println(this.kdmService.getDebugMsg());

			StringBuffer sb = new StringBuffer();
			sb.append("current user scope: ");
			sb.append(scopeController.getUserScope().getName());
			sb
					.append("<br/>\n<table ><tr><th>type</th><th>#Objects</th><th>Duration (ms)</th><th>DB-Connections</th></tr>");
			sb.append("<tr><td>retrieve All Objects: </td><td>" + conns.size()
					+ "</td><td>" + compDuration() + "</td><td>"
					+ compConnections() + " </td></tr>\n ");

			Tree connectableTree = kdmService.retrieveTree(scopeController,
					conns, showTreeDepth, showassociations, showoccurrences);

			this.kdmService.getDebugMsg().append(
					" retrieve Tree  "
							+ (java.lang.System.currentTimeMillis() - systime));
			systime = java.lang.System.currentTimeMillis();
			java.lang.System.err.println(this.kdmService.getDebugMsg());

			sb.append("<tr><td>build Tree: </td><td> "
					+ connectableTree.treeNodeCounter + "</td><td>"
					+ compDuration() + "</td><td>" + compConnections()
					+ " </td></tr>\n ");
			sb.append("</table>");
			connectableTree.addActionListener(this);

			writePerformanceInfo(sb, connectableTree.treeNodeCounter);
			logger.warn("buildTree");
			inBuild = false;
		}
		this.kdmService.getDebugMsg().append(
				" build tree  "
						+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(this.kdmService.getDebugMsg());

	}

	/**
	 * @return the showTreeDepth
	 */
	@Override
	public int getShowTreeDepth() {
		return showTreeDepth;
	}

	/**
	 * @param showTreeDepth
	 *            the showTreeDepth to set
	 */
	@Override
	public void setShowTreeDepth(int showTreeDepth) {
		this.showTreeDepth = showTreeDepth;
	}

	/**
	 * @return the competencesmanagementController
	 */
	public CMControllerImpl getCompetencesmanagementController() {
		return competencesmanagementController;
	}

	/**
	 * @param competencesmanagementController
	 *            the competencesmanagementController to set
	 */
	public void setCompetencesmanagementController(
			CMControllerImpl competencesmanagementController) {
		this.competencesmanagementController = competencesmanagementController;
	}

	private void writePerformanceInfo(StringBuffer info, int counter) {
		// "PERFORMANCEINFO \n <br/>"+
		// "Java Version: "+System.getProperty("java.version")+" \n <br/>"+
		// "Java VM: "+System.getProperty("java.vm.version")+" \n <br/>"+
		// "OS: "+
		// System.getProperty("os.arch") + "\n <br/>"+ "OS Version: "+
		// System.getProperty("os.version") + "\n <br/>"+
		performanceinfo = new StringBuffer();
		performanceinfo
				.append("#Topics = SUM( Width^((Depth+1)-n) ) [n=0..Depth+1] = ");

		performanceinfo.append(getNodeCount());
		performanceinfo.append(" <br/>\n");
		performanceinfo.append("    Width=");
		performanceinfo.append(width);
		performanceinfo.append("   Depth=");
		performanceinfo.append(depth);
		performanceinfo.append(" <br/>\n");
		performanceinfo.append("Number of Topic Nodes in Tree: ");
		performanceinfo.append(counter);
		performanceinfo.append(" <br/>\n");
		performanceinfo.append(info);

		logger.debug(performanceinfo);
	}

	private long compDuration() {
		long currtime = System.currentTimeMillis();
		long duration = currtime - lasttime;
		lasttime = currtime;
		return duration;
	}

	private int compConnections() {
		int actual = HsqldbDataSource.COUNT;
		int count = actual - lastcount;
		lastcount = actual;
		return count;
	}

	private void resetDuration() {
		lasttime = System.currentTimeMillis();
	}

	private void resetConnections() {
		lastcount = HsqldbDataSource.COUNT;
	}
}
