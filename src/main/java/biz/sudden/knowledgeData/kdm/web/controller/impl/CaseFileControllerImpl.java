package biz.sudden.knowledgeData.kdm.web.controller.impl;

import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.addTabToPanelTabSet;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.clearPanelTabSet;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.createEmptyOutputText;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.createHtmlCommandButton;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.createHtmlMessage;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.createInputField;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.createOutputText;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.createPanelGrid;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.getFieldValue;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.hasCollectionType;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.hasJavaType;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.hasPrimitiveType;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.hasSuddenType;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.showEditableDateField;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.showEditableFieldValue;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.showFieldValueAndCreateSelectDialog;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.showRequiredProducts;
import static biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.transformHibernateLazyObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIPanel;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlColumn;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.el.MethodExpressionLiteral;
import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.Individual;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.SearchedAssoc;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.handleBO.service.HandleBOService;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;
import biz.sudden.knowledgeData.kdm.service.CaseService;
import biz.sudden.knowledgeData.kdm.service.KdmService;
import biz.sudden.knowledgeData.kdm.web.controller.CaseFileController;
import biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper.UserConverter;
import biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct;
import biz.sudden.knowledgeData.serviceManagement.domain.Function;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;
import biz.sudden.knowledgeData.serviceManagement.domain.Thing;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

import com.icesoft.faces.async.render.OnDemandRenderer;
import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.component.ext.HtmlCommandButton;
import com.icesoft.faces.component.ext.HtmlCommandLink;
import com.icesoft.faces.component.ext.HtmlDataTable;
import com.icesoft.faces.component.ext.HtmlForm;
import com.icesoft.faces.component.ext.HtmlGraphicImage;
import com.icesoft.faces.component.ext.HtmlOutputText;
import com.icesoft.faces.component.ext.HtmlPanelGrid;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.ext.HtmlSelectBooleanCheckbox;
import com.icesoft.faces.component.ext.HtmlSelectOneMenu;
import com.icesoft.faces.component.panelpopup.PanelPopup;
import com.icesoft.faces.component.paneltabset.PanelTab;
import com.icesoft.faces.component.paneltabset.PanelTabSet;
import com.icesoft.faces.component.tree.IceUserObject;
import com.icesoft.faces.component.tree.TreeNode;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

import edu.emory.mathcs.backport.java.util.concurrent.ThreadPoolExecutor;

public class CaseFileControllerImpl implements CaseFileController {

	private Logger logger = Logger.getLogger(this.getClass());

	// private boolean alreadyRenderedTabSetAndTabs = false;

	private PanelTabSet panelTabSet = new PanelTabSet();
	private KdmService kdmService;
	private ScopeController scopeController;
	private List<CaseFile> availableCaseFiles;
	private List<SelectItem> selectCaseFiles;
	private long updatedCaseFileList = 0L;
	// private Long currentCaseFileId;

	private DefaultTreeModel treeModelForMaterialWindow;
	private ThreadPoolExecutor executor;
	private List<PanelTab> tabs;
	private int tabIndex = 1;
	private ExpressionFactory expressionFactory = FacesContext
			.getCurrentInstance().getApplication().getExpressionFactory();
	private Application app = FacesContext.getCurrentInstance()
			.getApplication();

	private HtmlPanelGrid asnSelectionGrid;

	private AbstractSupplyNetwork selectedASN;
	private AbstractSupplyNetwork selectedASNInMaterialWindow;

	private List<AbstractSupplyNetwork> selectedASNList = new ArrayList<AbstractSupplyNetwork>();

	private HandleBOService handleBoService;

	private String newBoName;
	private List<BusinessOpportunity> BOsToDelete;
	private boolean popupIsVisible = false;

	private List<String> desiredClasses = new ArrayList<String>();
	private List<String> desiredInstances = new ArrayList<String>();

	private List<SelectItem> desiredClassesList = new LinkedList<SelectItem>();

	private List<String> desiredFunctions = new ArrayList<String>();

	private Organization fixedSupplierToAssign;

	private boolean requiredLightweight = false;

	private List<SelectItem> desiredFunctionsList = new ArrayList<SelectItem>();

	private Map<String, Boolean> selectedIndividuals = new HashMap<String, Boolean>();
	private PMController pMController;

	private HtmlSelectOneMenu asnSelectOneMenu = new HtmlSelectOneMenu();

	private HtmlPanelGrid simplePartsGrid = new HtmlPanelGrid();

	// private List<AbstractSupplyNetwork> asnList;

	private SuddenGenericRepository genericRepository;

	protected PersistentFacesState state = null;
	protected OnDemandRenderer renderer;

	// Component to show the selected Supplier
	private HtmlOutputText selectedSupplierText;

	public CaseFileControllerImpl() {
		super();
		simplePartsGrid.setColumns(1);
	}

	public SuddenGenericRepository getGenericRepository() {
		return genericRepository;
	}

	public void setGenericRepository(SuddenGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	private void setSelectedASN(AbstractSupplyNetwork asnNew) {
		selectedASN = asnNew;
		if (selectedASN != null)
			// showRequiredProducts(simplePartsGrid, asnList, selectedASN);
			showRequiredProducts(simplePartsGrid, selectedASNList, selectedASN,
					kdmService.getOntology());
		else {
			simplePartsGrid.getChildren().clear();
			asnSelectOneMenu.getChildren().clear();
			if (selectedASNList != null)
				selectedASNList.clear();

			// if(asnList!=null)
			// asnList.clear();
		}
		// updateUI();
	}

	private AbstractSupplyNetwork getSelectedASN() {
		return selectedASN;
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
		this.scopeController = sc;
	}

	@Override
	public void setPMController(PMController controller) {
		this.pMController = controller;
	}

	public HandleBOService getHandleBoService() {
		return handleBoService;
	}

	public void setHandleBoService(HandleBOService handleBoService) {
		this.handleBoService = handleBoService;
	}

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	@Override
	public Long getCurrentCaseFileId() {
		if (getCurrentCaseFile() != null)
			return getCurrentCaseFile().getId();
		else
			return new Long(0l);
	}

	@Override
	public void setCurrentCaseFileId(Long caseFileId) {
		if (caseFileId != null && !getCurrentCaseFileId().equals(caseFileId))
			setCurrentCaseFile(kdmService.getCaseService().retrieveCaseFile(
					caseFileId));
	}

	@Override
	public void setCurrentCaseFile(CaseFile currentCaseFile) {
		if (getCurrentCaseFile() == null || currentCaseFile != null) {
			// if caseFile is changed remove the selectedASN
			if (getCurrentCaseFile() != null
					&& currentCaseFile != null
					&& !currentCaseFile.getId().equals(
							getCurrentCaseFile().getId()))
				setSelectedASN(null);

			pMController.setSelectedCaseFile(currentCaseFile);
			// logger.debug(currentCaseFile.getName() +
			// ":  TempTeams Generated: " + currentCaseFile.getTempTeams());
		}
		// updateUI();
	}

	@Override
	public synchronized void updateCurrentCaseFile(ActionEvent evt) {
		setSelectedASN(null);
		// when this action is triggered we are in a new session!!!
		setCurrentCaseFile(kdmService.getCaseService().retrieveCaseFile(
				getCurrentCaseFile().getId()));
		// refresh when ever someone "touches" the CaseFile drop down list
		addOverviewPanelAndGenericGUI(CaseFile.class, getCurrentCaseFile());
		updateUI();
	}

	@Override
	public synchronized void updateCurrentCaseFile() {
		kdmService.getCaseService().update(getCurrentCaseFile());
		addOverviewPanelAndGenericGUI(CaseFile.class, getCurrentCaseFile());
		updateUI();
	}

	@Override
	public CaseFile getCurrentCaseFile() {
		return pMController.getSelectedCaseFile();
	}

	@Override
	public void setUpdateCurrentCaseFileId(String caseFileId) {
		setCurrentCaseFile(caseFileId);
		addOverviewPanelAndGenericGUI(CaseFile.class, getCurrentCaseFile());
		updateUI();
	}

	@Override
	public String getUpdateCurrentCaseFileId() {
		return pMController.getSelectedCaseFile().getId().toString();
	}

	@Override
	public void setCurrentCaseFile(String caseFileId) {
		setCurrentCaseFile(kdmService.getCaseService().retrieveCaseFile(
				new Long(caseFileId)));
	}

	@Override
	// FIXME: Performance
	public List<SelectItem> getCaseFiles(String keyword) {
		long time = System.currentTimeMillis();
		if (availableCaseFiles == null
				|| (System.currentTimeMillis() - updatedCaseFileList) > 50000) {
			if (availableCaseFiles != null)
				availableCaseFiles.clear();
			else
				availableCaseFiles = new ArrayList<CaseFile>();
			CaseFile current = getCurrentCaseFile();
			if (keyword == null) {
				if (selectCaseFiles == null)
					selectCaseFiles = new ArrayList<SelectItem>();
				else
					selectCaseFiles.clear();
			}
			for (CaseFile cf : kdmService.getCaseService()
					.retrieveAllCaseFiles()) {
				availableCaseFiles.add(cf);
				if (current == null)
					current = cf;
				if (keyword == null)
					selectCaseFiles
							.add(new SelectItem(cf.getId(), cf.getName()));
			}
			if (getCurrentCaseFile() == null && current != null)
				setCurrentCaseFile(current);
			updatedCaseFileList = System.currentTimeMillis();
			logger.debug("created list of CaseFiles: # , duration (ms) "
					+ availableCaseFiles.size() + ", "
					+ (updatedCaseFileList - time));
		}

		if (keyword != null) {
			Collection<CaseFile> cfs = kdmService.getCaseService()
					.retrieveCaseFileByKeyword(keyword, keyword);
			if (selectCaseFiles == null)
				selectCaseFiles = new ArrayList<SelectItem>(cfs.size());
			else
				selectCaseFiles.clear();
			for (CaseFile c : cfs) {
				selectCaseFiles.add(new SelectItem(c.getId(), c.getName()));
			}
		}
		updatedCaseFileList = System.currentTimeMillis();
		logger.debug("created list of CaseFiles: # , duration (ms) "
				+ availableCaseFiles.size() + ", "
				+ (updatedCaseFileList - time));
		return selectCaseFiles;
	}

	@Override
	public List<SelectItem> getCaseFiles() {
		return getCaseFiles(null);
	}

	@Override
	public List<SelectItem> getCaseFilesByPartner(String keyword) {
		if (availableCaseFiles == null)
			getCaseFiles(null);
		ArrayList<SelectItem> result = new ArrayList<SelectItem>();
		if (availableCaseFiles != null) {
			for (int i = 0; i < availableCaseFiles.size(); ++i) {
				CaseFile c = availableCaseFiles.get(i);
				java.util.Iterator<Entry<ASNRoleNode, Supplier>> it = c
						.getFinalTeam().getCandidateSuppliers().entrySet()
						.iterator();
				while (it.hasNext()) {
					// / FIXME!! check if this Pair, Tuple, ... Supplier stuff
					// works
					// with toString
					String s = it.next().toString(); // .toString("", "", "");
					logger.debug("?????????getCaseFilesByPartner - " + keyword
							+ " ->? " + s);
					if (s.indexOf(keyword) > -1)
						result.add(new SelectItem(c.getId(), c.getName(),
								"BO: " + c.getBo().getName()));
				}
			}
		}
		return result;
	}

	@Override
	public List<SelectItem> getCaseFilesByBO(String keyword) {
		if (availableCaseFiles == null)
			getCaseFiles(null);
		ArrayList<SelectItem> result = new ArrayList<SelectItem>();
		for (int i = 0; i < availableCaseFiles.size(); ++i) {
			CaseFile c = availableCaseFiles.get(i);
			BusinessOpportunity bo = c.getBo();
			if (bo.getName().indexOf(keyword) > -1)
				result.add(new SelectItem(c.getId(), c.getName(), "BO: "
						+ bo.getName()));
		}
		return result;
	}

	@Override
	public CaseFile retrieveCaseFile(Long id) {
		return kdmService.getCaseService().retrieveCaseFile(id);
	}

	@Override
	public List<CaseFile> retrieveAllCaseFiles() {
		if (availableCaseFiles == null)
			getCaseFiles(null);
		return availableCaseFiles;
	}

	/**
	 * @return the newBoName
	 */
	@Override
	public String getNewBoName() {
		return newBoName;
	}

	/**
	 * @param newBoName
	 *            the newBoName to set
	 */
	@Override
	public void setNewBoName(String newBoName) {
		this.newBoName = newBoName;
	}

	@Override
	public void newBO(ActionEvent newBOEvent) {
		newBO();
	}

	@Override
	public void newBO() {
		if (newBoName != null && newBoName.length() > 1) {
			updatedCaseFileList = 0l;
			BusinessOpportunity bo = new BusinessOpportunity();
			bo.setName(newBoName);
			kdmService.getCaseService().create(bo);
			CaseFile intothedb = new CaseFile();
			intothedb.setBo(bo);
			kdmService.getCaseService().create(intothedb);
			updateCurrentCaseFile();
		}
	}

	@Override
	public void deleteBO(ActionEvent newBOEvent) {
		deleteBO();
	}

	@Override
	public void deleteBO() {
		if (newBoName != null && newBoName.length() > 1) {
			BOsToDelete = kdmService.getCaseService()
					.retrieveBusinessOpportunity(newBoName);
			if (BOsToDelete != null && BOsToDelete.size() > 0) {
				showPopup();
			}
		}
	}

	@Override
	public String getBOsToDelete() {
		StringBuffer result = new StringBuffer();
		if (BOsToDelete != null && BOsToDelete.size() > 0) {
			for (BusinessOpportunity bo : BOsToDelete) {
				result.append("* <br />");
				result.append(bo.getName());
			}
		}
		return result.toString();
	}

	@Override
	public void reallyDeleteBOs() {
		if (BOsToDelete != null && BOsToDelete.size() > 0) {
			for (BusinessOpportunity bo : BOsToDelete) {
				CaseFile cf = kdmService.getCaseService().retrieveCaseFile(bo);
				if (cf != null) {
					// this deletes also the BO and probably other stuff
					kdmService.getCaseService().delete(cf);
				} else {
					kdmService.getCaseService().delete(bo);
				}
			}
		}
		hidePopup();
		updateCurrentCaseFile();
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

	public PanelTabSet getPanelTabSet() {
		// getRefresh();
		return panelTabSet;
	}

	public void getRefresh() {
		CaseFile current = getCurrentCaseFile();
		if (current != null) {
			addOverviewPanelAndGenericGUI(CaseFile.class, current);
			updateUI();
		}
	}

	public void setPanelTabSet(PanelTabSet panelTabSet) {
		this.panelTabSet = panelTabSet;
	}

	// public void refreshTabs(ValueChangeEvent event) {
	// initView();
	// }

	public List<PanelTab> getTabs() {
		return tabs;
	}

	public void setTabs(List<PanelTab> tabs) {
		this.tabs = tabs;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
		// click on Panel Tab;
		// addOverviewPanelAndGenericGUI( CaseFile.class, getCurrentCaseFile());
		// updateUI();

	}

	public void initView() {
		HtmlPanelGroup view = new HtmlPanelGroup();
		view.setVisible(true);
		view.setRendered(true);
		if (getCurrentCaseFile() != null) {
			addOverviewPanelAndGenericGUI(CaseFile.class, getCurrentCaseFile());
		}
		updateUI();
	}

	public void initTabs() {
		if (tabs == null) {
			tabs = new ArrayList<PanelTab>();
			PanelTab tab1 = new PanelTab();
			tab1.setLabel("Overview");
			panelTabSet.getChildren().add(tab1);
			tabs.add(tab1);
			initView();
		}
	}

	private boolean handleObject(String objectELexpression, UIPanel tab3,
			Class myObjectType, ValueExpression objectElExpression) {
		try {
			Object myObject = objectElExpression
					.getValue(CaseFileControllerHelper.getELContext());

			if (BusinessOpportunity.class.isAssignableFrom(myObjectType)) {
				return handleBO(objectELexpression, tab3, objectElExpression);
			}

			if (AbstractSupplyNetwork.class.isAssignableFrom(myObjectType)) {
				return handleASN(objectELexpression, tab3,
						(AbstractSupplyNetwork) myObject);
			}

			if (ConcreteSupplyNetwork.class.isAssignableFrom(myObjectType)) {
				return handleCSN(objectELexpression, tab3,
						(ConcreteSupplyNetwork) myObject);
			}

			if (Collection.class.isAssignableFrom(myObjectType)) {
				return handleCollection(objectELexpression, tab3,
						(Collection) myObject);
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			logger.error(e.getStackTrace());
		}
		return false;

	}

	/***
	 * 
	 * Adds an "Overview" panel to given PanelTabSet and builds subsequently
	 * appropriate widgets according the given object (form elemens for java
	 * data types, further Tabs for SUddEN data types and Collections)
	 * 
	 * @param counter
	 * @param pnlTabSet
	 * @param clazz
	 * @param object
	 */

	private void addOverviewPanelAndGenericGUI(final Class clazz,
			final Object object) {
		clearPanelTabSet(panelTabSet);
		PanelTab tab = addTabToPanelTabSet(panelTabSet, clazz.getSimpleName());
		recursiveGenerateOutput(tab, clazz, object);
	}

	/***
	 * 
	 * This method adds widgets according to given object to the given panel.
	 * Each Java data type is shown as a form element, SUddEN datatypes and
	 * collections are shown in further Panel Tabs. This is a generic approach,
	 * thus changes at domain class level are reflected automatically to the UI
	 * 
	 * @param panel
	 * @param clazz
	 * @param object
	 */

	private void recursiveGenerateOutput(final UIPanel panel, Class clazz,
			Object object) {

		/**
		 * Because of using Java Reflection the objects referenced by fields are
		 * Proxies (with the aid of these proxies Hibernate can realize lazy
		 * loading. This method transforms any proxy object to a real POJO.
		 */
		object = transformHibernateLazyObject(object);
		clazz = object.getClass();

		Long longId;

		String objectExpressionString;

		if (object instanceof Connectable) {
			longId = ((Connectable) object).getId();
		} else {
			longId = Long.valueOf(object.hashCode());
		}

		// assign an unique expression for use with Expression Language (EL)
		// like in
		// JSPX ( "${backingBean.property}" )
		String longIdString;

		if (!(longId == null)) {
			if (longId < 0) {
				// We need to remove the "-" symbol from the expression, as (EL)
				// language will interpret it as integer substraction.
				longIdString = "Neg" + String.valueOf(longId * (-1));
			} else {
				longIdString = String.valueOf(longId);
			}
			objectExpressionString = "currentObject"
					+ object.getClass().getSimpleName() + longIdString;

			boolean handleObjectSucceeded = false;

			// do not use this with lists
			// if (!(Collection.class.isAssignableFrom(clazz))) {
			ValueExpression objectExpression = expressionFactory
					.createValueExpression(FacesContext.getCurrentInstance()
							.getELContext(), "${" + objectExpressionString
							+ "}", object.getClass());
			logger.debug("setting Value for this objectExpression: "
					+ objectExpression);
			// binding current object to the Value Expression (EL)
			if (!objectExpression.isReadOnly(FacesContext.getCurrentInstance()
					.getELContext()))
				objectExpression.setValue(FacesContext.getCurrentInstance()
						.getELContext(), object);
			// Handle the given object (BO, ASN, CSN or Collection) and
			// transform it
			// into suitable UI widgets.
			handleObjectSucceeded = handleObject(objectExpressionString, panel,
					clazz, objectExpression);
			// }

			// If object was not an BO, ASN, CSN or a collection, further steps
			// have
			// to be done, this means: object is a CASEFILE !!
			if (!handleObjectSucceeded) {

				// longId might be null with a new Object!!
				HtmlPanelGrid grid = createPanelGrid(panel, 3);

				// Iterate over all fields of class of given object
				for (final Field field : clazz.getDeclaredFields()) {

					Object fieldValue = getFieldValue(object, field);

					// Field has primitive type (int, long, char) or Java type
					// (String,
					// Integer), but no Collection type (List, Set, etc.)
					if (hasPrimitiveType(field)
							|| (hasJavaType(field) && !hasCollectionType(field) && fieldValue != null)) {

						boolean hideField = field.getName().equals("id");

						// TODO TF: do we really need handling of Date here?
						// Maybe we can
						// omit this ?
						// if (field.getType().equals(Date.class)) {
						// DateTimeConverter converter = new
						// DateTimeConverter();
						// converter.setPattern("dd.MM.yyyy");
						//
						// ((HtmlInputText) component).setConverter(converter);
						// }
						//

						objectExpression = expressionFactory
								.createValueExpression(FacesContext
										.getCurrentInstance().getELContext(),
										"${" + objectExpressionString + "}",
										object.getClass());

						createOutputText(grid, field.getName(), true, true);
						createInputField(this, grid, objectExpression,
								objectExpressionString, field.getName(), null,
								hideField);
						createHtmlMessage(grid, field.getName());

					} else if (hasJavaType(field) || hasSuddenType(field)) {
						// "field" is not a Java type and not a collection, but
						// of SUddEN
						// type (like BO, ASN, etc..)
						// with the aid of the recursivegenerateoutput2 method
						// handleObject
						// (at line 464) returns true subsequently...

						if (fieldValue != null) {
							PanelTab tab = addTabToPanelTabSet(
									(PanelTabSet) panel.getParent(), field
											.getName());
							recursiveGenerateOutput(tab, fieldValue.getClass(),
									fieldValue);
						}
					}

				}
			}
		}

	}

	/***
	 * 
	 * In this method a "Business Opportunity" is handled at the UI level
	 * (second tab in "Case Files"). There exists a Panel Grid with 3 columns
	 * for Label, Field and Buttons. The first 5 rows show values of the
	 * Business Opportunity with possiblity to change these (by Buttons or by
	 * direct textfield input). The further Buttons calculate required products
	 * based on the given Part/Service. Additional functionality is added by
	 * enabling filter ASN and required Functions and a Button which allows to
	 * remove all selected filters.
	 * 
	 * @param objectELexpressionString
	 * @param parent
	 *            the parent UI element
	 * @param businessOpportunity
	 *            current Business Opportunity
	 * @return
	 */

	protected boolean handleBO(String objectELexpressionString,
			final UIPanel parent, final ValueExpression boElExpression) {

		final BusinessOpportunity businessOpportunity = (BusinessOpportunity) boElExpression
				.getValue(CaseFileControllerHelper.getELContext());

		HtmlOutputText text;

		final UIPanel hookUp = parent;

		HtmlOutputText breakText = createOutputText(null, "<br/>", false, false);

		final HtmlPanelGrid grid = createPanelGrid(parent, 3);

		showFieldValueAndCreateSelectDialog("Owner", "Select", grid,
				boElExpression, kdmService.getUserService().retrieveAllUsers(),
				kdmService, scopeController, objectELexpressionString, "boo",
				"username");
		showFieldValueAndCreateSelectDialog("CorePartner1", "Select", grid,
				boElExpression, kdmService.getUserService().retrieveAllUsers(),
				kdmService, scopeController, objectELexpressionString,
				"corePartner1", "username");
		showFieldValueAndCreateSelectDialog("CorePartner2", "Select", grid,
				boElExpression, kdmService.getUserService().retrieveAllUsers(),
				kdmService, scopeController, objectELexpressionString,
				"corePartner2", "username");
		showFieldValueAndCreateSelectDialog("CorePartner3", "Select", grid,
				boElExpression, kdmService.getUserService().retrieveAllUsers(),
				kdmService, scopeController, objectELexpressionString,
				"corePartner3", "username");
		showFieldValueAndCreateSelectDialog("CorePartner4", "Select", grid,
				boElExpression, kdmService.getUserService().retrieveAllUsers(),
				kdmService, scopeController, objectELexpressionString,
				"corePartner4", "username");

		showFieldValueAndCreateSelectDialog("createpartService", "Select",
				grid, boElExpression, kdmService
						.getProductMaterialSupportingServices_Service()
						.retrieveAllComplexProducts(), kdmService,
				scopeController, objectELexpressionString, "goal", "name");

		showFieldValueAndCreateSelectDialog("Customer", "Select", grid,
				boElExpression, kdmService.getServiceManagementService()
						.retrieveAllOrganisations(), kdmService,
				scopeController, objectELexpressionString, "endCustomer",
				"name");

		showEditableFieldValue("NumberPartsYear", grid,
				objectELexpressionString, "quantityPerYearFinalProduct",
				businessOpportunity, kdmService);

		showEditableDateField("sop", grid, objectELexpressionString,
				"startOfProduction", kdmService, businessOpportunity);

		createEmptyOutputText(grid);
		createEmptyOutputText(grid);
		createEmptyOutputText(grid);

		createHtmlCommandButton(grid, "handlebobutton",
				"Calculate required Products", new ActionListener() {
					@Override
					public void processAction(ActionEvent arg0)
							throws AbortProcessingException {
						calculateRequiredProducts(hookUp, businessOpportunity);
						// asnList =
						// handleBoService.generateInitialSupplyNetworks(businessOpportunity);
						//				
						//
						// addToSelectOneMenu(asnSelectOneMenu, asnList, "ASN");
						// //do this in setSelectedASN
						// //showRequiredProducts(simplePartsGrid, asnList,
						// null);
						// if(asnList!=null && asnList.size()>0) {
						// setSelectedASN(asnList.get(0));
						// } else setSelectedASN(null);
					}
				});
		createEmptyOutputText(grid);
		createEmptyOutputText(grid);

		asnSelectOneMenu.setImmediate(false);
		asnSelectOneMenu.setPartialSubmit(false);

		grid.getChildren().add(asnSelectOneMenu);
		asnSelectOneMenu.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void processValueChange(ValueChangeEvent valueChangeEvent)
					throws AbortProcessingException {
				Object newValue = valueChangeEvent.getNewValue();
				setSelectedASN((AbstractSupplyNetwork) newValue);
				// do this in setSelectedASN
				// showRequiredProducts(simplePartsGrid, null,
				// getSelectedASN());
			}
		});

		createHtmlCommandButton(grid, "addAsnToCaseFileButton",
				"Add ASN to CaseFile", new ActionListener() {
					// FIXME: Performance
					@Override
					public void processAction(ActionEvent actionEvent)
							throws AbortProcessingException {

						// TODO GW: just a dummy value for testing, comment in
						// this code section
						// again !
						// if (getCurrentCaseFile().getPhase() != -32) {
						// if (getCurrentCaseFile().getAsnPrototypeTeam() !=
						// null) {
						// AbstractSupplyNetwork currentPrototypeASN =
						// kdmService.getCaseService().retrieveASN(getCurrentCaseFile().getAsnPrototypeTeam().getId());
						// if
						// (!currentPrototypeASN.getId().equals(getSelectedASN().getId()))
						// {
						// kdmService.getCaseService().delete(getCurrentCaseFile().getPrototypeTeam());
						// getCurrentCaseFile().setAsnPrototypeTeam(null);
						// kdmService.getCaseService().update(getCurrentCaseFile());
						// delete only ASNs that are not referenced else
						// boolean reallydelete = true;
						// if (getCurrentCaseFile().getFinalTeam() != null &&
						// getCurrentCaseFile().getFinalTeam().getASN().getId().equals(currentPrototypeASN.getId()))
						// reallydelete = false;
						// if (getCurrentCaseFile().getPrototypeTeam() != null
						// &&
						// getCurrentCaseFile().getPrototypeTeam().getASN().getId().equals(currentPrototypeASN.getId()))
						// reallydelete = false;
						// for (ConcreteSupplyNetwork c :
						// getCurrentCaseFile().getTempTeams())
						// if
						// (c.getASN().getId().equals(currentPrototypeASN.getId()))
						// reallydelete = false;
						// if (reallydelete)
						// kdmService.getCaseService().delete(currentPrototypeASN);
						// }
						// }
						// if (getSelectedASN() != null)
						// setSelectedASN(kdmService.getCaseService().retrieveASN(getSelectedASN().getId()));
						//					
						// getCurrentCaseFile().setAsnPrototypeTeam(getSelectedASN());
						// } else {
						CaseService caseService = kdmService.getCaseService();

						CaseFile currentCaseFile = caseService
								.retrieveCaseFile(getCurrentCaseFileId());

						AbstractSupplyNetwork newASN = getSelectedASN();
						if (getCurrentCaseFile().getPhase() < 2) {
							currentCaseFile.setAsnPrototypeTeam(newASN);
						} else {
							currentCaseFile.setAsnFinalTeam(newASN);
						}
						setSelectedASN(newASN);

						caseService.update(currentCaseFile);
						setCurrentCaseFile(currentCaseFile);
						showASN = true;
						// }
					}
				});

		parent.getChildren().add(simplePartsGrid);

		final HtmlSelectOneMenu desiredClassesMenu = new HtmlSelectOneMenu();

		HtmlCommandButton filterButton = new HtmlCommandButton();
		filterButton.setValue("Apply Filters to ASN list");
		filterButton.setId("filterButton");
		filterButton.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {
				calculateRequiredProducts(hookUp, businessOpportunity);
				// How on earth did this end up here??
				// openFilterDialog(hookUp, businessOpportunity,
				// kdmService.getProductMaterialSupportingServices_Service().retrieveAllComplexProducts());
			}
		});

		grid.getChildren().add(new HtmlOutputText());

		grid.getChildren().add(new HtmlOutputText());

		grid.getChildren().add(filterButton);

		final ValueExpression fieldExpression = expressionFactory
				.createValueExpression(FacesContext.getCurrentInstance()
						.getELContext(), "#{desiredClassesList}", Object.class);
		fieldExpression.setValue(FacesContext.getCurrentInstance()
				.getELContext(), desiredClassesList);
		final ValueExpression desiredClassesSize = expressionFactory
				.createValueExpression(FacesContext.getCurrentInstance()
						.getELContext(), "#{desiredClassesListLength}",
						Object.class);
		desiredClassesSize.setValue(FacesContext.getCurrentInstance()
				.getELContext(), desiredClassesList.size());

		for (String string : desiredClasses) {
			SelectItem item = new SelectItem(string);
			desiredClassesList.add(item);
		}

		UISelectItems items = new UISelectItems();

		items.setValueExpression("value", fieldExpression); // , binding);

		desiredClassesMenu.getChildren().add(items);

		grid.getChildren().add(desiredClassesMenu);

		final HtmlSelectOneMenu desiredFunctionsMenu = new HtmlSelectOneMenu();

		HtmlCommandButton removeButton = new HtmlCommandButton();
		removeButton.setValue("Remove all selected Filters");
		removeButton.setId("removeFilter");
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {

				desiredFunctions.remove(desiredFunctionsMenu.getValue());

				SelectItem functionToDelete = null;
				for (SelectItem selectItem : desiredFunctionsList) {
					if (selectItem.getValue().equals(
							desiredFunctionsMenu.getValue())) {
						functionToDelete = selectItem;
					}
				}
				if (functionToDelete != null)
					desiredFunctionsList.remove(functionToDelete);

				logger.debug(desiredClassesMenu.getValue());
				try {
					desiredClasses.remove(desiredClassesMenu.getValue());
				} catch (Exception ex) {

				}
				try {
					desiredInstances.remove(desiredClassesMenu.getValue());
				} catch (Exception ex) {
				}

				SelectItem itemToDelete = null;
				for (SelectItem selectItem : desiredClassesList) {
					if (selectItem.getValue().equals(
							desiredClassesMenu.getValue())) {
						itemToDelete = selectItem;
					}
				}
				if (itemToDelete != null)
					desiredClassesList.remove(itemToDelete);

				calculateRequiredProducts(hookUp, businessOpportunity);
			}
		});

		HtmlCommandButton functionsButton = new HtmlCommandButton();
		functionsButton.setValue("Add required Function");
		functionsButton.setId("functionsButton");
		functionsButton.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {
				openFilterDialog(hookUp, businessOpportunity, kdmService
						.getProductMaterialSupportingServices_Service()
						.retrieveAllFunctions());
			}
		});

		HtmlCommandButton weightFilterButton = new HtmlCommandButton();
		weightFilterButton.setValue("Toggle Filtering by weight");
		weightFilterButton.setId("weightFilter");
		weightFilterButton.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {
				toggleFilterByWeight();
			}
		});

		HtmlCommandButton productsButton = new HtmlCommandButton();
		productsButton.setValue("Add required Product");
		productsButton.setId("productsButton");
		productsButton.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {
				List<Product> allProducts = new ArrayList<Product>();
				allProducts.addAll(kdmService
						.getProductMaterialSupportingServices_Service()
						.retrieveAllComplexProducts());
				allProducts.addAll(kdmService
						.getProductMaterialSupportingServices_Service()
						.retrieveAllSimpleProducts());
				openFilterDialog(hookUp, businessOpportunity, allProducts);
			}
		});

		HtmlOutputText breakText2 = new HtmlOutputText();
		breakText2.setEscape(false);
		breakText2.setValue("<br />");

		grid.getChildren().add(breakText2);

		grid.getChildren().add(productsButton);
		grid.getChildren().add(functionsButton);
		grid.getChildren().add(weightFilterButton);

		for (String string : desiredFunctions) {
			SelectItem item = new SelectItem(string);
			desiredFunctionsList.add(item);
		}

		UISelectItems functionItems = new UISelectItems();

		final ValueExpression functionFieldExpression = expressionFactory
				.createValueExpression(FacesContext.getCurrentInstance()
						.getELContext(), "#{desiredFunctionsList}",
						Object.class);
		functionFieldExpression.setValue(FacesContext.getCurrentInstance()
				.getELContext(), desiredFunctionsList);

		functionItems.setValueExpression("value", functionFieldExpression);

		desiredFunctionsMenu.getChildren().add(functionItems);

		grid.getChildren().add(desiredFunctionsMenu);

		HtmlOutputText breakText3 = new HtmlOutputText();
		breakText3.setEscape(false);
		breakText3.setValue("<p  />");

		grid.getChildren().add(breakText3);

		grid.getChildren().add(removeButton);

		HtmlOutputText breakText4 = new HtmlOutputText();
		breakText4.setEscape(false);
		breakText4.setValue("<p  />");

		grid.getChildren().add(breakText4);
		// the code below is very time consuming execute only when button is
		// pressed
		// if (productExpression.getValue(FacesContext.getCurrentInstance().
		// getELContext()) != null) {
		// calculateRequiredProducts(hookUp, businessOpportunity);
		// } else {
		// try {
		// hookUp.getChildren().remove(asnSelectionGrid);
		// } catch (Exception ex) {
		// logger.debug("calculateRequiredProducts - getChildren" +
		// ex.getMessage());
		// }
		// }

		return true;
	}

	public void toggleFilterByWeight() {
		this.requiredLightweight = !this.requiredLightweight;
	}

	protected boolean handleCollection(String objectELexpression, UIPanel tab,
			final Collection coll) {
		if (coll.size() > 0) {
			HtmlPanelGrid grid = new HtmlPanelGrid();
			grid.setColumns(1);
			tab.getChildren().add(grid);
			logger.debug("handleCollectoin size: " + coll.size());

			if (!coll.isEmpty()) {
				java.util.Iterator it = coll.iterator();
				for (int i = 0; it.hasNext(); ++i) {
					Object obj = it.next();
					if (i == 0 && obj instanceof ConcreteSupplyNetwork) {
						HtmlCommandButton functionsButton = new HtmlCommandButton();
						functionsButton.setValue("Update Values");
						functionsButton.setId("functionsButton");
						functionsButton.addActionListener(new ActionListener() {
							@Override
							public void processAction(ActionEvent arg0) {
								calculate = true;
								// refresh when ever someone "touches" the
								// CaseFile drop down
								// list
								addOverviewPanelAndGenericGUI(CaseFile.class,
										getCurrentCaseFile());

								updateUI();
							}
						});
						grid.getChildren().add(functionsButton);
					}
					if (calculate && obj instanceof ConcreteSupplyNetwork)
						recursiveGenerateOutput(grid, obj.getClass(), obj);
				}
				calculate = false;
			}
		}
		return true;
	}

	boolean showASN = true;

	protected boolean handleASN(String objectELexpression,
			final UIPanel parent, final AbstractSupplyNetwork asn) {

		// HtmlCommandButton functionsButton = new HtmlCommandButton();
		// functionsButton.setValue("Show");
		// functionsButton.setId("showasnButton");
		// functionsButton.addActionListener(new ActionListener() {
		// @Override
		// public void processAction(ActionEvent arg0) {
		// showASN = true;
		// // refresh when ever someone "touches" the CaseFile drop down list
		// addOverviewPanelAndGenericGUI( CaseFile.class, getCurrentCaseFile());
		//
		// updateUI();
		// }
		// });
		// parent.getChildren().add(functionsButton);

		if (showASN) {
			// showASN = false;
			final List<SelectedItem> itemList = new ArrayList<SelectedItem>();
			// final List<SelectedItem> asnNodesList = new
			// ArrayList<SelectedItem>();
			selectedASNInMaterialWindow = asn;

			final ValueExpression asnnodeentry = expressionFactory
					.createValueExpression(FacesContext.getCurrentInstance()
							.getELContext(), "${asnnodeentry.userObject}",
							SelectedItem.class);

			ValueExpression valueExpression = expressionFactory
					.createValueExpression(FacesContext.getCurrentInstance()
							.getELContext(),
							"${asnnodeentry.userObject.entry}", String.class);

			ValueExpression valueExpressionSelected = expressionFactory
					.createValueExpression(FacesContext.getCurrentInstance()
							.getELContext(),
							"${asnnodeentry.userObject.selected}",
							Boolean.class);

			ValueExpression valueExpressionRendered = expressionFactory
					.createValueExpression(FacesContext.getCurrentInstance()
							.getELContext(),
							"${asnnodeentry.userObject.rendered}",
							Boolean.class);

			final ValueExpression individualExpression = expressionFactory
					.createValueExpression(FacesContext.getCurrentInstance()
							.getELContext(), "${individual}", Object.class);

			final ValueExpression indValueExpression = expressionFactory
					.createValueExpression(FacesContext.getCurrentInstance()
							.getELContext(), "${individual.entry}",
							Object.class);

			final ValueExpression indSelectedExpression = expressionFactory
					.createValueExpression(FacesContext.getCurrentInstance()
							.getELContext(), "${individual.selected}",
							Boolean.class);

			final HtmlSelectBooleanCheckbox checkBox = new HtmlSelectBooleanCheckbox();
			final HtmlSelectBooleanCheckbox checkBox2 = new HtmlSelectBooleanCheckbox();

			HtmlPanelGroup group = new HtmlPanelGroup();
			group.setScrollHeight("350px");
			group.setScrollWidth("100%");
			group.setId("indHeightGroup");

			HtmlPanelGroup group2 = new HtmlPanelGroup();
			group2.setId("asnHeightGroup");
			group2.setScrollHeight("350px");
			group2.setScrollWidth("100%");

			selectedIndividuals.clear();

			// List<ASNRoleNode> asnRoleNodes =
			// handleBoService.getAllRoleNodes(asn);

			// for (ASNRoleNode roleNode : asnRoleNodes) {
			// SelectedItem item = new SelectedItem();
			// item.setEntry(roleNode.getQualificationProfile().getProductWithoutPrefix());
			// item.setSelected(false);
			// asnNodesList.add(item);
			// }

			List<String> allIndividuals = handleBoService
					.getAllIndividualProducts();

			for (String individual : allIndividuals) {
				SelectedItem item = new SelectedItem(null, null);
				item.setEntry(individual);
				item.setSelected(false);
				itemList.add(item);
			}

			final HtmlPanelGrid grid = new HtmlPanelGrid();

			grid.setColumns(3);

			grid.setStyle("width:100%;height:100%");

			// final HtmlDataTable dataTable = new HtmlDataTable();
			// dataTable.setVar("asnnodeentry");
			// dataTable.setId("roleNodeTable");
			// dataTable.setValue(asnNodesList);

			// Instead of the table above, we build a tree
			// -----TREE STARTS
			com.icesoft.faces.component.tree.Tree tree = new com.icesoft.faces.component.tree.Tree();
			tree.setId("asnMaterialWindowTree");
			ValueExpression tempExpression = expressionFactory
					.createValueExpression(FacesContext.getCurrentInstance()
							.getELContext(), "#{caseFileController.treeModel}",
							TreeModel.class);
			// tempExpression.setValue(FacesContext.getCurrentInstance().getELContext(),
			// getTreeModel(asn));
			tree.setValueExpression("value", tempExpression);
			tree.setVar("asnnodeentry");

			TreeNode treeNode = new TreeNode();
			// Build the icon facet
			HtmlPanelGroup iconGroup = new HtmlPanelGroup();
			iconGroup.setStyle("display: inline");
			HtmlGraphicImage iconGraphic = new HtmlGraphicImage();
			tempExpression = expressionFactory.createValueExpression(
					FacesContext.getCurrentInstance().getELContext(),
					"#{asnnodeentry.userObject.icon}", String.class);
			iconGraphic.setValueExpression("value", tempExpression);
			iconGroup.getChildren().add(iconGraphic);
			treeNode.getFacets().put(TreeNode.FACET_ICON, iconGroup);

			// Create the content facet
			HtmlPanelGroup contentGroup = new HtmlPanelGroup();
			contentGroup.setStyle("display: inline");
			treeNode.getFacets().put(TreeNode.FACET_CONTENT, contentGroup);

			// Add the tree node to the tree
			// treeNode.setTree(tree);
			tree.getChildren().add(treeNode);
			// ---TREE FINISHES

			HtmlCommandButton addbutton = new HtmlCommandButton();
			addbutton.setImage("images/arrow-previous.gif");
			addbutton.setId("addIndividualButton");
			addbutton.setImmediate(false);
			addbutton.setPartialSubmit(false);

			addbutton.addActionListener(new ActionListener() {
				@Override
				public void processAction(ActionEvent arg0)
						throws AbortProcessingException {

					SelectedItem selectedItem = null;
					for (SelectedItem item : getAllNodesInTreeOfType(ASNRoleNode.class)) {
						if (item.isSelected()) {
							selectedItem = item;
							break;
						}
					}

					if (selectedItem == null) {
						return;
					}

					for (SelectedItem item : itemList) {
						if (item.isSelected() == true) {
							ASNRoleNode node = new ASNRoleNode();

							QualificationProfile profile = handleBoService
									.getProductQualificationProfile(Thing.NameSpace
											+ item.getEntry());
							node.setQualificationProfile(profile);
							handleBoService.addRoleNode(asn, node);
							// item.setSelected(false);

							asn.addNewDependcy(new ASNMaterialDependency(node,
									(ASNRoleNode) selectedItem
											.getNetworkObject()));
							genericRepository.update(asn);

							// SelectedItem myItem = new SelectedItem();
							// myItem.setEntry(node.getQualificationProfile().getProductWithoutPrefix());
							// myItem.setSelected(false);
							// asnNodesList.add(myItem);
						}
					}

					logger.debug(itemList);

				}
			});

			HtmlCommandButton deleteButton = new HtmlCommandButton();
			deleteButton.setId("deleteButton");
			deleteButton.setImage("images/arrow-next.gif");
			deleteButton.setValue("Delete selected Node(s) from ASN");
			deleteButton.setImmediate(false);
			deleteButton.setPartialSubmit(false);

			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void processAction(ActionEvent arg0)
						throws AbortProcessingException {
					// TODO Auto-generated method stub

					// List<SelectedItem> itemsToDelete = new
					// ArrayList<SelectedItem>();

					for (SelectedItem item : getAllNodesInTreeOfType(ASNRoleNode.class)) {
						if (item.isSelected()) {
							// String entryToDelete = item.getEntry();
							// handleBoService.removeNodeByName(asn,
							// entryToDelete);
							asn
									.recursivelyDeleteRoleNodeAndChildren((ASNRoleNode) item
											.getNetworkObject());
							genericRepository.update(asn);
							// itemsToDelete.add(item);
							// asnNodesList.remove(item);
						}
					}

					// for (SelectedItem item : itemsToDelete) {
					// asnNodesList.remove(item);
					// }
				}
			});

			// Dangerously experimental stuff
			final HtmlCommandButton fixSupplierbutton = new HtmlCommandButton();
			fixSupplierbutton.setValue("Assign fixed supplier");
			fixSupplierbutton.setId("Assign_Fixed_Supplier");

			fixSupplierbutton.addActionListener(new ActionListener() {
				@Override
				public void processAction(ActionEvent arg0)
						throws AbortProcessingException {

					for (SelectedItem item : getAllNodesInTreeOfType(ASNRoleNode.class)) {
						if (item.isSelected()) {
							String nodeToFix = item.getEntry();
							// Organization orgToFix = (Organization)
							// supplierToAssign.getValue(FacesContext.getCurrentInstance().getELContext());
							if (fixedSupplierToAssign != null) {
								handleBoService.assignSupplierByName(asn,
										nodeToFix, fixedSupplierToAssign);
							}
							item.setSelected(false);
							logger.debug(" ASN after fixing suppliers " + asn);
						}
					}

					logger.debug(itemList);

				}
			});

			final HtmlCommandButton selectFixedSupplierbutton = new HtmlCommandButton();
			selectFixedSupplierbutton
					.setValue("Choose fixed supplier to assign");
			selectFixedSupplierbutton.setId("Choose_Fixed_Supplier");

			selectFixedSupplierbutton.addActionListener(new ActionListener() {
				@Override
				public void processAction(ActionEvent arg0)
						throws AbortProcessingException {

					openChooseFixedSupplierDialog(parent);

				}
			});

			selectedSupplierText = new HtmlOutputText();
			selectedSupplierText.setId("selectedSupplierText");
			selectedSupplierText.setValue("No supplier selected");

			// End said dangerous experiment :)

			final HtmlDataTable individualsTable = new HtmlDataTable();
			individualsTable.setVar("individual");
			individualsTable.setId("individualsTable");
			individualsTable.setValue(itemList);

			HtmlColumn indColumn = new HtmlColumn();
			indColumn.setId("indColumn");

			HtmlColumn selectIndColumn = new HtmlColumn();
			selectIndColumn.setId("selectIndColumn");

			checkBox.setValueExpression("value", indSelectedExpression);

			checkBox.setId("checkBox");
			// FIXME this below seems to refresh everything too fast
			// checkBox.setImmediate(true);
			checkBox.setPartialSubmit(false);

			checkBox.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void processValueChange(ValueChangeEvent arg0)
						throws AbortProcessingException {

					SelectedItem selectedIndividual = (SelectedItem) individualExpression
							.getValue(FacesContext.getCurrentInstance()
									.getELContext());

					if (arg0.getNewValue().equals(true)) {
						selectedIndividual.setSelected(true);
					} else if (arg0.getNewValue().equals(false)) {
						selectedIndividual.setSelected(false);
					}

				}
			});

			selectIndColumn.getChildren().add(checkBox);

			HtmlPanelGroup indPanelGroup = new HtmlPanelGroup();
			indPanelGroup.setId("panelGroup");
			HtmlOutputText indText = new HtmlOutputText();
			indText.setId("indPanelGroupText");
			indText.setValueExpression("value", indValueExpression);

			indPanelGroup.getChildren().add(indText);

			indColumn.getChildren().add(indPanelGroup);

			individualsTable.getChildren().add(selectIndColumn);
			individualsTable.getChildren().add(indColumn);

			// HtmlColumn column = new HtmlColumn();

			// column.setId("roleNodeColumn");

			// HtmlPanelGroup panelGroup = new HtmlPanelGroup();
			// panelGroup.setStyle("vertical-align: top");
			// panelGroup.setId("panelGroup");
			HtmlOutputText text = new HtmlOutputText();
			text.setId("panelGroupText");
			text.setValueExpression("value", valueExpression);

			// panelGroup.getChildren().add(text);

			// column.getChildren().add(panelGroup);

			// HtmlColumn selectDataColumn = new HtmlColumn();
			// selectDataColumn.setId("selectDataColumn");

			checkBox2.setValueExpression("value", valueExpressionSelected);
			checkBox2.setValueExpression("rendered", valueExpressionRendered);

			checkBox2.setId("checkBox2");
			// FIXME the stuff below seems to refresh everything to fast
			// checkBox2.setImmediate(true);
			checkBox2.setPartialSubmit(false);

			checkBox2.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void processValueChange(ValueChangeEvent arg0)
						throws AbortProcessingException {

					SelectedItem selectedIndividual = (SelectedItem) asnnodeentry
							.getValue(FacesContext.getCurrentInstance()
									.getELContext());

					if (arg0.getNewValue().equals(true)) {
						selectedIndividual.setSelected(true);
					} else if (arg0.getNewValue().equals(false)) {
						selectedIndividual.setSelected(false);
					}

				}
			});

			// selectDataColumn.getChildren().add(checkBox2);
			contentGroup.getChildren().add(checkBox2);
			contentGroup.getChildren().add(text);
			// dataTable.getChildren().add(selectDataColumn);
			// dataTable.getChildren().add(column);

			group.getChildren().add(individualsTable);

			// group2.getChildren().add(dataTable);
			group2.getChildren().add(tree);

			grid.getChildren().add(group2);

			HtmlPanelGrid buttonGrid = new HtmlPanelGrid();
			buttonGrid.setColumns(1);
			buttonGrid.setId("buttonGroup");
			buttonGrid.getChildren().add(addbutton);
			buttonGrid.getChildren().add(deleteButton);
			// Experimental here
			// buttonGrid.getChildren().add(fixSupplierbutton);

			grid.getChildren().add(buttonGrid);
			grid.getChildren().add(group);

			HtmlCommandButton goToNetworkPMS = new HtmlCommandButton();
			goToNetworkPMS
					.setValue("Design Performance Measurement System for ASN");
			goToNetworkPMS.setId("goToNetworkPMS");
			goToNetworkPMS.setActionExpression(new MethodExpressionLiteral(
					"performanceMeasurementNetwork2", String.class, null));

			// HtmlCommandButton goToEnterprisePMS = new HtmlCommandButton();
			// goToNetworkPMS.setValue("Design Enterprise Performance Measurement Systems for ASN Roles");
			// goToNetworkPMS.setId("goToEnterprisePMS");
			// goToNetworkPMS.setActionExpression(new
			// MethodExpressionLiteral("performanceMeasurementDesign2",
			// String.class,
			// null));

			// goToBNetworkPMS.setActionExpression( new
			// MethodExpressionLiteral("#{caseFileController.goToNetwork}",
			// String.class, null));
			// parent.getChildren().add(goToEnterprisePMS);
			HtmlPanelGrid commandButtons = new HtmlPanelGrid();
			commandButtons.setColumns(3);
			commandButtons.getChildren().add(goToNetworkPMS);
			commandButtons.getChildren().add(fixSupplierbutton);
			commandButtons.getChildren().add(selectFixedSupplierbutton);
			commandButtons.getChildren().add(selectedSupplierText);

			parent.getChildren().add(commandButtons);
			parent.getChildren().add(grid);
		}
		return true;
	}

	// -------------******** START TREE FUNCTIONS
	/**
	 * Builds the tree model. It first checks that the tree model has changed
	 * (new nodes are in the network or the nodes have been replaced) If the ASN
	 * changed completely, then the tree model is rebuild, if not, the model
	 * updates its transient properties. Transient properties:
	 * dropDownSelectedElement
	 * 
	 * @return
	 */
	public DefaultTreeModel getTreeModel() {
		if (treeModelForMaterialWindow != null) {
			// We need to check if the model changed
			if (!updateAndCheckIfTreeModelNeedsRebuild(selectedASNInMaterialWindow)) {
				return treeModelForMaterialWindow;
			}
		}
		// We need to rebuild the entire model.
		ASNRoleNode node = selectedASNInMaterialWindow.getFinalNode();
		DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
		SelectedItem rootObject = new SelectedItem(rootTreeNode, node);
		rootObject.setText(node.getQualificationProfile()
				.getProductWithoutPrefix());
		rootObject.setExpanded(true);
		rootObject.setEntry(node.getQualificationProfile()
				.getProductWithoutPrefix());
		rootObject.setSelected(false);
		rootObject.setRendered(true);
		rootObject.setBranchContractedIcon("/images/shape29.gif");
		rootObject.setBranchExpandedIcon("/images/shape29.gif");
		rootObject.setLeafIcon("/images/shape29.gif");
		rootTreeNode.setUserObject(rootObject);
		treeModelForMaterialWindow = new DefaultTreeModel(rootTreeNode);
		buildSubTree(rootTreeNode, node);

		return treeModelForMaterialWindow;
	}

	private void buildSubTree(DefaultMutableTreeNode treeNode,
			ASNRoleNode networkNode) {
		List<ASNMaterialDependency> dependencies = selectedASNInMaterialWindow
				.getMaterialDependenciesInto(networkNode);
		if (dependencies.size() == 0) {
			((IceUserObject) treeNode.getUserObject()).setLeaf(true);
		}
		for (ASNMaterialDependency dependency : dependencies) {
			// Add the node for the dependency
			DefaultMutableTreeNode dependencyNode = new DefaultMutableTreeNode();
			SelectedItem dependencyObject = new SelectedItem(dependencyNode,
					dependency);
			dependencyObject.setText(dependency.getdependencytype());
			dependencyObject.setExpanded(true);
			dependencyObject.setEntry(dependency.getdependencytype());
			dependencyObject.setSelected(false);
			dependencyObject.setRendered(false);
			dependencyObject.setBranchContractedIcon("/images/arrow_40.gif");
			dependencyObject.setBranchExpandedIcon("/images/arrow_40.gif");
			dependencyObject.setLeafIcon("/images/arrow_40.gif");
			dependencyNode.setUserObject(dependencyObject);
			treeNode.add(dependencyNode);

			// Add the node for the source of the dependency
			ASNRoleNode networkSourceNode = (ASNRoleNode) dependency
					.getSourceNode();
			DefaultMutableTreeNode sourceDependencyNode = new DefaultMutableTreeNode();
			SelectedItem sourceDependencyObject = new SelectedItem(
					sourceDependencyNode, networkSourceNode);
			sourceDependencyObject.setText(networkSourceNode
					.getQualificationProfile().getProductWithoutPrefix());
			sourceDependencyObject.setExpanded(true);
			sourceDependencyObject.setEntry(networkSourceNode
					.getQualificationProfile().getProductWithoutPrefix());
			sourceDependencyObject.setSelected(false);
			sourceDependencyObject.setRendered(true);
			sourceDependencyObject
					.setBranchContractedIcon("/images/shape29.gif");
			sourceDependencyObject.setBranchExpandedIcon("/images/shape29.gif");
			sourceDependencyObject.setLeafIcon("/images/shape29.gif");
			sourceDependencyNode.setUserObject(sourceDependencyObject);
			dependencyNode.add(sourceDependencyNode);
			buildSubTree(sourceDependencyNode, networkSourceNode);
		}
	}

	/**
	 * Update the current tree model, update transient properties of the nodes.
	 * Transient properties: dropDownSelectedElement At the same time, it checks
	 * if new nodes have been added to the tree... if the tree changed
	 * completely, then it returns true.
	 * 
	 * @return
	 */
	private boolean updateAndCheckIfTreeModelNeedsRebuild(
			AbstractSupplyNetwork network) {
		ASNRoleNode node = network.getFinalNode();
		DefaultMutableTreeNode treeRoot = (DefaultMutableTreeNode) treeModelForMaterialWindow
				.getRoot();
		SelectedItem treeRootUserObject = (SelectedItem) treeRoot
				.getUserObject();
		Connectable networkObject = treeRootUserObject.getNetworkObject();
		if (!networkObject.equals(node)) {
			return true;
		}
		return updateAndCheckIfSubTreeNeedsRebuild(treeRoot, node, network);
	}

	private boolean updateAndCheckIfSubTreeNeedsRebuild(
			DefaultMutableTreeNode treeNode, ASNRoleNode networkNode,
			AbstractSupplyNetwork network) {
		List<ASNMaterialDependency> dependencies = network
				.getMaterialDependenciesInto(networkNode);
		if (treeNode.getChildCount() != dependencies.size()) {
			return true;
		}
		Enumeration<DefaultMutableTreeNode> children = treeNode.children();
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode child = children.nextElement();
			SelectedItem userObject = (SelectedItem) child.getUserObject();
			Connectable networkObject = userObject.getNetworkObject();
			if (!dependencies.contains(networkObject)) {
				return true;
			}
			// Until now, the dependency matched, now we need to check that the
			// source
			// of the dependency matches
			Enumeration<DefaultMutableTreeNode> childSources = child.children();
			DefaultMutableTreeNode childSource = childSources.nextElement();
			SelectedItem childSourceObject = (SelectedItem) childSource
					.getUserObject();
			Connectable networkChildSourceObject = childSourceObject
					.getNetworkObject();
			if (!networkChildSourceObject
					.equals(((ASNMaterialDependency) networkObject)
							.getSourceNode())) {
				return true;
			}
			if (updateAndCheckIfSubTreeNeedsRebuild(childSource,
					(ASNRoleNode) networkChildSourceObject, network)) {
				return true;
			}
		}

		return false;
	}

	private List<SelectedItem> getAllNodesInTreeOfType(
			Class<? extends Connectable> clazz) {
		List<SelectedItem> result = new ArrayList<SelectedItem>();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModelForMaterialWindow
				.getRoot();
		SelectedItem item = (SelectedItem) root.getUserObject();
		if (clazz.isAssignableFrom(item.getNetworkObject().getClass())) {
			result.add(item);
		}
		getAllNodesOfTypeInSubTree(root, clazz, result);
		return result;
	}

	private void getAllNodesOfTypeInSubTree(DefaultMutableTreeNode treeNode,
			Class<? extends Connectable> clazz, List<SelectedItem> nodes) {
		Enumeration<DefaultMutableTreeNode> children = treeNode.children();
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode child = children.nextElement();
			SelectedItem userObject = (SelectedItem) child.getUserObject();
			if (clazz
					.isAssignableFrom(userObject.getNetworkObject().getClass())) {
				nodes.add(userObject);
			}
			getAllNodesOfTypeInSubTree(child, clazz, nodes);
		}
	}

	// -------------------- ENDS TREE FUNCTIONS

	public String goToNetwork() {
		return "performanceMeasurementNetwork";
	}

	protected ConcreteSupplyNetwork getCSN(String ids) {
		Long id = new Long(ids);
		for (ConcreteSupplyNetwork csn : getCurrentCaseFile().getTempTeams()) {
			if (csn.getId().equals(id))
				return csn;
		}
		return null;
	}

	boolean calculate = false;

	protected boolean handleCSN(String objectELexpression, UIPanel parent,
			final ConcreteSupplyNetwork csn) {

		final HtmlPanelGrid grid = new HtmlPanelGrid();
		grid.setColumns(2);
		grid.setStyle("width:100%");
		parent.getChildren().add(grid);
		HtmlSelectBooleanCheckbox checkBox = new HtmlSelectBooleanCheckbox();
		checkBox.setId("CSN" + csn.getId());
		checkBox.setImmediate(false);
		checkBox.setPartialSubmit(false);
		checkBox.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void processValueChange(ValueChangeEvent arg0)
					throws AbortProcessingException {
				logger.debug(arg0.getSource());
				logger.debug(arg0.getComponent().getId());
				if (getCurrentCaseFile().getPhase() < 2) {
					getCurrentCaseFile().setPrototypeTeam(
							getCSN(arg0.getComponent().getId().substring(3)));
				} else {
					getCurrentCaseFile().setFinalTeam(
							getCSN(arg0.getComponent().getId().substring(3)));
				}
				initView();
			}
		});

		grid.getChildren().add(checkBox);
		StringBuffer buffer = new StringBuffer("Concrete Supply Network");
		// StringBuffer buffer = new StringBuffer("Product: ");
		// if (csn.getFinalNode() != null &&
		// csn.getInitialNode().getProductsSupplied() != null &&
		// csn.getFinalNode().getFinalProduct().getName() != null)
		// buffer.append(csn.getFinalNode().getFinalProduct().getName());
		// else
		// buffer.append("Final Product name unknown");
		// buffer.append("<br/> Supplier: <br/>");
		HtmlOutputText txt = new HtmlOutputText();
		txt.setValue(buffer.toString());

		HtmlPanelGrid csngrid = new HtmlPanelGrid();
		csngrid.setColumns(1);
		csngrid.setStyle("width:100%");
		grid.getChildren().add(csngrid);
		csngrid.getChildren().add(txt);

		HtmlPanelGrid suppliergrid = new HtmlPanelGrid();
		suppliergrid.setStyle("margin-top:1em;border:1px dotted grey");
		suppliergrid.setColumns(1);

		// HtmlOutputText supplierText = new HtmlOutputText();
		// supplierText.setValue("Suppliers proposed:");
		// csngrid.getChildren().add(supplierText);
		// suppliergrid.getFacets().put("header", supplierText);

		csngrid.getChildren().add(suppliergrid);

		if (csn.getCandidateSuppliers() != null) {
			java.util.Iterator<ASNRoleNode> nodeiterator = csn
					.getCandidateSuppliers().keySet().iterator();
			logger.debug("found ASN Role Nodes #: "
					+ csn.getCandidateSuppliers().size());
			while (nodeiterator.hasNext()) {
				ASNRoleNode node = nodeiterator.next();
				logger.debug(" ASN Role Node: " + node.toString());

				buffer.delete(0, buffer.length());

				buffer.append(node.getQualificationProfile()
						.getFirstProductName());
				buffer.append(": ");
				Supplier supplier = csn.getCandidateSuppliers().get(node);
				if (supplier != null && supplier.getOrganisation() != null) {
					if (supplier.getOrganisation().getName() != null) {
						buffer.append(supplier.getOrganisation().getName());
					} else {
						buffer.append("Manager: ");
						buffer.append(supplier.getOrganisation()
								.getAux_companyManager());
					}
					if (calculate) {
						if (node.getCompetenceNeeded() != null) {
							buffer.append(" Perf.: ");
							if (node.getCompetenceNeeded().getValue(
									supplier.getOrganisation()) == null)
								node.getCompetenceNeeded().setValue(
										supplier.getOrganisation(),
										pMController.evaluate(node
												.getCompetenceNeeded(),
												supplier.getOrganisation()));
							buffer.append(node.getCompetenceNeeded().getValue(
									supplier.getOrganisation()));
							buffer.append(" - ");
							buffer.append(node.getCompetenceNeeded()
									.getNameOfCompetenceProfile());
							// buffer.append("min: ");
							// buffer.append(node.getCompetenceNeeded().getMinValue()
							// );
							// buffer.append(" max: ");
							// buffer.append(node.getCompetenceNeeded().getMaxValue()
							// );
						} else
							buffer.append("  - No Competence");
					}
				} else {
					buffer.append(" - supplier unknown - ");
				}
				txt = new HtmlOutputText();
				txt.setValue(buffer.toString());
				txt
						.setStyle("font-size: small; margin-top:0px; margin-bottom: 0px");
				suppliergrid.getChildren().add(txt);
			}
		} else {
			txt = new HtmlOutputText();
			txt.setEscape(false);
			txt.setValue(" - no suppliers - ");
			suppliergrid.getChildren().add(txt);
		}
		buffer.delete(0, buffer.length());

		if (csn.getInitialNode() != null
				&& csn.getInitialNode().getProductsSupplied() != null) {
			for (biz.sudden.knowledgeData.serviceManagement.domain.Product p : csn
					.getInitialNode().getProductsSupplied()) {
				buffer.append(p.getName());
			}
		}
		if (calculate) {
			buffer.append("<br> Network Performance: ");
			buffer.append(pMController.evaluate(csn));
		}
		buffer.append("<hr width=\"100%\"/>");

		txt = new HtmlOutputText();
		txt.setValue(buffer.toString());
		txt.setEscape(false);
		csngrid.getChildren().add(txt);

		return true;
	}

	public void openFilterDialog(final UIPanel hookup,
			final BusinessOpportunity businessOpportunity, List list) {

		final CaseFile finalCurrent = this.getCurrentCaseFile();

		final PanelPopup myPopup = new PanelPopup();

		myPopup.setDraggable("true");
		myPopup.setStyle("width:400px;height:300px;position:absolute;top:"
				+ (150) + "px;left:" + (500) + "px;");
		myPopup.setClientOnly(true);
		myPopup.setId("selectObjPopup");

		Tree treeModel = new Tree(kdmService.getConnectableService(),
				scopeController);
		treeModel.setAlsoCreateAssociationNodes(false);
		treeModel.setAlsoShowOccurrence(false);
		treeModel.setExpandAll(true);
		treeModel.setAssociationScope(scopeController.getUnspecifiedScope());

		/* copy/paste from CaseFileControllerHelper, just in case it helps.... */
		SearchedAssoc sa = null;
		// FIXME: the lines below are commented out.... it produces a simple
		// list
		// instead of a tree....leaving sa == null means that all possible trees
		// are
		// expanded...
		// if(list.size()>0 && list.get(0)instanceof Product)
		// sa = new SearchedAssoc
		// (Product.ASSOCIATIONTYPE,Product.SUPER,Product.SUB);

		if (list.size() > 0 && list.get(0) instanceof Item) {
			sa = new SearchedAssoc(Item.isAAssocType, Item.isASuperType,
					Item.isASubType);
			// clean the list to only contain "root" nodes ie. complex product,
			// simple
			// product, ....
			treeModel.retrieveNodes(5, Tree.removeWhenNotInRoleOnly(list,
					Item.isASuperType, scopeController.getUnspecifiedScope(),
					kdmService.getConnectableService()), sa);
		} else {
			treeModel.retrieveNodes(5, list, sa);
		}

		// treeModel.retrieveNodes(4, list, null);

		com.icesoft.faces.component.tree.Tree tree = new com.icesoft.faces.component.tree.Tree();
		TreeNode treeNode = new TreeNode();
		HtmlPanelGroup panelGroup = new HtmlPanelGroup();

		tree.setHideRootNode("true");
		tree.setHideNavigation("false");
		tree.setImmediate(false);
		tree.setVar("selectedItem");
		tree.setValue(treeModel);
		tree.setId("cfTree");
		ValueExpression expression = expressionFactory.createValueExpression(
				FacesContext.getCurrentInstance().getELContext(),
				"#{treeitems}", Object.class);
		expression.setValue(FacesContext.getCurrentInstance().getELContext(),
				treeModel);
		tree.setValueExpression("value", expression);

		tree.getChildren().add(treeNode);

		treeNode.getFacets().put("content", panelGroup);

		ValueExpression expressionText = expressionFactory
				.createValueExpression(FacesContext.getCurrentInstance()
						.getELContext(), "#{selectedItem.userObject.text}",
						Object.class);

		final ValueExpression reference = expressionFactory
				.createValueExpression(FacesContext.getCurrentInstance()
						.getELContext(),
						"#{selectedItem.userObject.reference}", Object.class);
		HtmlCommandLink link = new HtmlCommandLink();

		link.setValueExpression("value", expressionText);

		link.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {

				Object object = reference.getValue(FacesContext
						.getCurrentInstance().getELContext());

				logger.debug("object is " + object);

				if (object instanceof Function) {
					Function function = (Function) object;

					SelectItem item = new SelectItem(function.getName());

					desiredFunctionsList.add(item);
					desiredFunctions.add(function.getName());
				} else if (object instanceof Thing) {
					Thing thing = (Thing) object;

					SelectItem item = new SelectItem(thing.getName());
					desiredClassesList.add(item);

					desiredClasses.add(thing.getName());
				}

				if (object instanceof Individual) {
					Individual individual = (Individual) object;

					SelectItem item = new SelectItem(individual.getName());
					desiredClassesList.add(item);

					desiredInstances.add(individual.getName());
				}

				myPopup.setVisible(false);
				myPopup.getParent().getChildren().remove(myPopup);

				// calculateRequiredProducts(hookup, businessOpportunity);

			}
		});

		link.setId("link");
		link.setStyle("font-size:0.8em;color:black");

		panelGroup.getChildren().add(link);

		panelGroup.setStyle("display:inline");

		HtmlPanelGroup bodyGroup = new HtmlPanelGroup();

		HtmlCommandButton cancelButton = new HtmlCommandButton();
		cancelButton.setId("cancelButton");
		cancelButton.setValue("Abbrechen");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {
				// TODO Auto-generated method stub
				myPopup.setVisible(false);
				myPopup.getParent().getChildren().remove(myPopup);
			}
		});

		bodyGroup.getChildren().add(cancelButton);
		bodyGroup.getChildren().add(tree);

		myPopup.getFacets().put("body", bodyGroup);

		HtmlForm newForm = new HtmlForm();
		newForm.setId("setObjectToField");
		newForm.getChildren().add((PanelPopup) myPopup);
		FacesContext.getCurrentInstance().getViewRoot().getChildren().get(0)
				.getChildren().get(2).getChildren().add(newForm);

	}

	public void calculateRequiredProducts(UIPanel hookup,
			BusinessOpportunity businessOpportunity) {

		if (asnSelectionGrid != null) {
			try {
				asnSelectionGrid.getChildren().clear();
			} catch (Exception ex) {
				logger.debug("not able to remove asnSelectionGrid"
						+ ex.getMessage());
			}
		} else {
			asnSelectionGrid = new HtmlPanelGrid();
			asnSelectionGrid.setColumns(1);
			hookup.getChildren().add(asnSelectionGrid);
		}

		Long longId = ((Connectable) getCurrentCaseFile()).getId();

		String objectExpressionString = "currentObject"
				+ getCurrentCaseFile().getClass().getSimpleName()
				+ longId.toString();

		logger.debug("CaseFile objectExpressionString: "
				+ objectExpressionString);

		if (getCurrentCaseFile().getBo() != null
				&& getCurrentCaseFile().getBo().getGoal() != null) {
			ValueExpression productExpression = expressionFactory
					.createValueExpression(FacesContext.getCurrentInstance()
							.getELContext(), "#{" + objectExpressionString
							+ ".bo.goal" + '}', Object.class);

			ComplexProduct complexProduct = (ComplexProduct) productExpression
					.getValue(FacesContext.getCurrentInstance().getELContext());

			if (complexProduct != null) {

				logger.debug(complexProduct.getName());

				final HtmlPanelGrid grid = new HtmlPanelGrid();
				grid.setColumns(1);
				asnSelectionGrid.getChildren().add(grid);

				asnSelectOneMenu.getChildren().clear();

				HtmlCommandButton addASNtoCaseFile = new HtmlCommandButton();
				addASNtoCaseFile.setValue("Add ASN to CaseFile");
				addASNtoCaseFile.setId("addasntocasefile");

				addASNtoCaseFile.addActionListener(new ActionListener() {
					@Override
					public void processAction(ActionEvent arg0)
							throws AbortProcessingException {
						// TODO a different object with the same identifier
						// value
						// was already associated with the session:
						// [biz.sudden.baseAndUtility.domain.CaseFile#2]

						logger.debug(asnSelectOneMenu.getValue());
						// refresh currentCaseFile but don't use the method
						// setCurrentCaseFile as this will refresh things..
						pMController.setSelectedCaseFile(kdmService
								.getCaseService().retrieveCaseFile(
										getCurrentCaseFile().getId()));
						kdmService.getCaseService()
								.update(getCurrentCaseFile());
						/*
						 * CaseFile.ENGINEERING_PRODUCT_Phase2
						 */
						if (getCurrentCaseFile().getPhase() < 2) {
							if (getCurrentCaseFile().getAsnPrototypeTeam() != null) {
								AbstractSupplyNetwork currentPrototypeASN = kdmService
										.getCaseService().retrieveASN(
												getCurrentCaseFile()
														.getAsnPrototypeTeam()
														.getId());
								// You have to first set the new
								// AsnPrototypeTeam in
								// the Casefile
								// afterwards you can safely delete the old
								// AsnPrototypeTeam
								if (!currentPrototypeASN.getId().equals(
										getSelectedASN().getId())) {
									kdmService.getCaseService().delete(
											getCurrentCaseFile()
													.getPrototypeTeam());
									getCurrentCaseFile().setAsnPrototypeTeam(
											null);
									kdmService.getCaseService().update(
											getCurrentCaseFile());
									// delete only ASNs that are not referenced
									// else
									boolean reallydelete = true;
									if (getCurrentCaseFile().getFinalTeam() != null
											&& getCurrentCaseFile()
													.getFinalTeam().getASN()
													.getId().equals(
															currentPrototypeASN
																	.getId()))
										reallydelete = false;
									if (getCurrentCaseFile().getPrototypeTeam() != null
											&& getCurrentCaseFile()
													.getPrototypeTeam()
													.getASN().getId().equals(
															currentPrototypeASN
																	.getId()))
										reallydelete = false;
									for (ConcreteSupplyNetwork c : getCurrentCaseFile()
											.getTempTeams())
										if (c.getASN().getId().equals(
												currentPrototypeASN.getId()))
											reallydelete = false;
									if (reallydelete)
										kdmService.getCaseService().delete(
												currentPrototypeASN);
								}
							}
							if (getSelectedASN() != null) {
								if (getSelectedASN().getId() == null)
									kdmService.getCaseService().create(
											getSelectedASN());
								setSelectedASN(kdmService.getCaseService()
										.retrieveASN(getSelectedASN().getId()));
							}
							getCurrentCaseFile().setAsnPrototypeTeam(
									getSelectedASN());
						} else {
							if (getCurrentCaseFile().getAsnFinalTeam() != null) {
								AbstractSupplyNetwork currentFinalASN = kdmService
										.getCaseService().retrieveASN(
												getCurrentCaseFile()
														.getAsnFinalTeam()
														.getId());
								// You have to first set the new AsnFinalTeam in
								// the Casefile
								// afterwards you can safely delete the old
								// AsnPrototypeTeam
								if (!currentFinalASN.getId().equals(
										getSelectedASN().getId())) {
									kdmService.getCaseService()
											.delete(
													getCurrentCaseFile()
															.getFinalTeam());
									getCurrentCaseFile().setAsnFinalTeam(null);
									kdmService.getCaseService().update(
											getCurrentCaseFile());
									kdmService.getCaseService().delete(
											currentFinalASN);
								}
							}
							if (getSelectedASN() != null) {
								if (getSelectedASN().getId() == null)
									kdmService.getCaseService().create(
											getSelectedASN());
								setSelectedASN(kdmService.getCaseService()
										.retrieveASN(getSelectedASN().getId()));
							}
							getCurrentCaseFile().setAsnPrototypeTeam(
									getSelectedASN());
						}
						updateCurrentCaseFile();
					}
				});

				grid.getChildren().add(addASNtoCaseFile);

				UISelectItems items = new UISelectItems();
				asnSelectOneMenu.getChildren().add(items);

				int selected = 0;
				if (getSelectedASN() != null)
					selected = selectedASNList.indexOf(getSelectedASN());
				List<SelectItem> selectItems = new LinkedList<SelectItem>();

				selectedASNList = handleBoService
						.generateInitialSupplyNetworks(businessOpportunity);
				for (int i = 0; i < selectedASNList.size(); ++i) {
					AbstractSupplyNetwork asn = selectedASNList.get(i);
					boolean containsClasses = handleBoService
							.containsDesiredClasses(asn, desiredClasses);
					boolean containsInstances = handleBoService
							.containsDesiredIndividuals(asn, desiredInstances);
					boolean containsFunctions = handleBoService
							.providesFunctions(asn, desiredFunctions);
					boolean isLightweight = true;
					if (this.requiredLightweight) {
						isLightweight = handleBoService
								.isLightWeightConstruction(asn);
					}

					if (containsClasses && containsInstances
							&& containsFunctions && isLightweight) {
						// grid.getChildren().add(new HtmlOutputText());
						SelectItem selectItem = new SelectItem(asn, "ASN "
								+ (i + 1));
						if (getSelectedASN() == null) {
							setSelectedASN(asn);
						}
						if (selected == i) {
							selectItems.add(0, selectItem);
						} else {
							selectItems.add(selectItem);
						}
					}
				}
				asnSelectOneMenu.setConverter(new UserConverter(selectItems));
				items.setValue(selectItems);

				final ValueExpression fieldExpression = expressionFactory
						.createValueExpression(FacesContext
								.getCurrentInstance().getELContext(),
								"#{selectedASN}", AbstractSupplyNetwork.class);
				fieldExpression.setValue(FacesContext.getCurrentInstance()
						.getELContext(), getSelectedASN());

				asnSelectOneMenu.setValueExpression("value", fieldExpression);
				asnSelectOneMenu.setPartialSubmit(false);
				asnSelectOneMenu.setImmediate(false);

				asnSelectOneMenu
						.addValueChangeListener(new ValueChangeListener() {
							@Override
							public void processValueChange(ValueChangeEvent arg0)
									throws AbortProcessingException {

								// Object asnold =
								// fieldExpression.getValue(FacesContext.getCurrentInstance
								// ().getELContext());
								Object asn = arg0.getNewValue();
								logger.debug("Selected ASN " + asn);
								if (asn != null
										&& asn instanceof AbstractSupplyNetwork) {
									logger.debug(asn.getClass());
									setSelectedASN((AbstractSupplyNetwork) asn);
								}

							}
						});

				// simplePartsGrid.getChildren().clear();
				// if (getSelectedASN() != null)
				// simplePartsGrid.getChildren().add(getRequiredProductsGrid(handleBoService.getAllRoleNodes(getSelectedASN()),
				// ontology));
			}
		}
		updateUI();

	}

	@Override
	public synchronized void update(CaseFile cf) {
		kdmService.getCaseService().update(cf);
		getCaseFiles(null);
	}

	@Override
	public void renderingException(RenderingException arg0) {
		logger.debug(arg0.getLocalizedMessage());
	}

	@Override
	public void setRenderManager(RenderManager rm) {
		logger.debug("setRendermanager");
		renderer = rm.getOnDemandRenderer("CaseFileRenderer");
		renderer.setRenderManager(rm);
		if (!renderer.contains(this))
			renderer.add(this);
	}

	@Override
	public PersistentFacesState getState() {
		if (state == null) {
			state = PersistentFacesState.getInstance();
		}
		return state;
	}

	// //////Experimental bits
	public void openChooseFixedSupplierDialog(final UIPanel hookup) {

		final CaseFile finalCurrent = this.getCurrentCaseFile();

		final PanelPopup myPopup = new PanelPopup();

		myPopup.setDraggable("true");
		myPopup.setStyle("width:400px;height:300px;position:absolute;top:"
				+ (150) + "px;left:" + (500) + "px;");
		myPopup.setClientOnly(true);
		myPopup.setId("selectObjPopup");

		Tree treeModel = new Tree(kdmService.getConnectableService(),
				scopeController);
		treeModel.setAlsoCreateAssociationNodes(false);
		treeModel.setAlsoShowOccurrence(false);
		treeModel.setExpandAll(true);
		treeModel.setAssociationScope(scopeController.getUnspecifiedScope());

		/* copy/paste from CaseFileControllerHelper, just in case it helps.... */
		SearchedAssoc sa = null;
		// FIXME: the lines below are commented out.... it produces a simple
		// list
		// instead of a tree....leaving sa == null means that all possible trees
		// are
		// expanded...
		// if(list.size()>0 && list.get(0)instanceof Product)
		// sa = new SearchedAssoc
		// (Product.ASSOCIATIONTYPE,Product.SUPER,Product.SUB);

		// Wow! Convoluted or what?
		List list = kdmService.getEnterpriseEvaluationService()
				.getOrganisationManagementService().retrieveAll();

		if (list.size() > 0 && list.get(0) instanceof Item) {
			sa = new SearchedAssoc(Item.isAAssocType, Item.isASuperType,
					Item.isASubType);
			// clean the list to only contain "root" nodes ie. complex product,
			// simple
			// product, ....
			treeModel.retrieveNodes(5, Tree.removeWhenNotInRoleOnly(list,
					Item.isASuperType, scopeController.getUnspecifiedScope(),
					kdmService.getConnectableService()), sa);
		} else {
			treeModel.retrieveNodes(5, list, sa);
		}

		// treeModel.retrieveNodes(4, list, null);

		com.icesoft.faces.component.tree.Tree tree = new com.icesoft.faces.component.tree.Tree();
		TreeNode treeNode = new TreeNode();
		HtmlPanelGroup panelGroup = new HtmlPanelGroup();

		tree.setHideRootNode("true");
		tree.setHideNavigation("false");
		tree.setImmediate(false);
		tree.setVar("selectedItem");
		tree.setValue(treeModel);
		tree.setId("cfTree");
		ValueExpression expression = expressionFactory.createValueExpression(
				FacesContext.getCurrentInstance().getELContext(),
				"#{treeitems}", Object.class);
		expression.setValue(FacesContext.getCurrentInstance().getELContext(),
				treeModel);
		tree.setValueExpression("value", expression);

		tree.getChildren().add(treeNode);

		treeNode.getFacets().put("content", panelGroup);

		ValueExpression expressionText = expressionFactory
				.createValueExpression(FacesContext.getCurrentInstance()
						.getELContext(), "#{selectedItem.userObject.text}",
						Object.class);

		final ValueExpression reference = expressionFactory
				.createValueExpression(FacesContext.getCurrentInstance()
						.getELContext(),
						"#{selectedItem.userObject.reference}", Object.class);
		HtmlCommandLink link = new HtmlCommandLink();

		link.setValueExpression("value", expressionText);

		link.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {

				Organization object = (Organization) reference
						.getValue(FacesContext.getCurrentInstance()
								.getELContext());

				logger.debug("object is " + object);
				fixedSupplierToAssign = object;

				selectedSupplierText.setValue("Selected Supplier: "
						+ fixedSupplierToAssign);
				/*
				 * if (object instanceof Individual) { Individual individual =
				 * (Individual) object;
				 * 
				 * SelectItem item = new SelectItem(individual.getName());
				 * desiredClassesList.add(item);
				 * 
				 * desiredInstances.add(individual.getName()); }
				 */

				myPopup.setVisible(false);
				myPopup.getParent().getChildren().remove(myPopup);

				// calculateRequiredProducts(hookup, businessOpportunity);

			}
		});

		link.setId("link");
		link.setStyle("font-size:0.8em;color:black");

		panelGroup.getChildren().add(link);

		panelGroup.setStyle("display:inline");

		HtmlPanelGroup bodyGroup = new HtmlPanelGroup();

		HtmlCommandButton cancelButton = new HtmlCommandButton();
		cancelButton.setId("cancelButton");
		cancelButton.setValue("Abbrechen");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {
				// TODO Auto-generated method stub
				myPopup.setVisible(false);
				myPopup.getParent().getChildren().remove(myPopup);
			}
		});

		bodyGroup.getChildren().add(cancelButton);
		bodyGroup.getChildren().add(tree);

		myPopup.getFacets().put("body", bodyGroup);

		HtmlForm newForm = new HtmlForm();
		newForm.setId("setObjectToField");
		newForm.getChildren().add((PanelPopup) myPopup);
		FacesContext.getCurrentInstance().getViewRoot().getChildren().get(0)
				.getChildren().get(2).getChildren().add(newForm);

	}

	// /////// Still experimental bits :)

	private void updateUI() {
		try {
			// code below is heavy on the server side .... therefore we now have
			// a
			// RenderManager!! ->
			// renderer.requestRender();
			// state.executeAndRender();
			// state.renderLater();
			// state.render();
			// renderer.setBroadcasted(true);
			renderer.requestRender();
		} catch (Exception e) {
			logger
					.debug("PMEnterpriseProfileDesignControllerImpl.updateUI: Render Exception");
			logger.debug(e.getMessage());
		}
	}

}
