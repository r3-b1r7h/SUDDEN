package biz.sudden.evaluation.performanceMeasurement.web.controller.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.service.ScopeService;
import biz.sudden.baseAndUtility.web.controller.RootLinkController;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;
import biz.sudden.baseAndUtility.web.controller.tree.ConnectableUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.SearchedAssoc;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.baseAndUtility.web.controller.tree.UserObject.UserObjectActionEvent;
import biz.sudden.evaluation.performanceMeasurement.PerformanceEvaluation;
import biz.sudden.evaluation.performanceMeasurement.domain.ChartModel;
import biz.sudden.evaluation.performanceMeasurement.domain.EvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.domain.OrganisationInformation;
import biz.sudden.evaluation.performanceMeasurement.domain.ParameterizedCompetence;
import biz.sudden.evaluation.performanceMeasurement.domain.PerformanceTree;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMEnterpriseEvaluationController;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

import com.icesoft.faces.async.render.OnDemandRenderer;
import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.component.outputchart.OutputChart;
import com.icesoft.faces.component.panelpopup.PanelPopup;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public class PMEnterpriseEvaluationControllerImpl implements
		PMEnterpriseEvaluationController, ActionListener {

	private Logger logger = Logger.getLogger(this.getClass());

	protected OnDemandRenderer renderer;

	protected PMController pMController;

	protected PersistentFacesState state;

	protected long lastRefreshOrganisations = 0;
	protected List<SelectItem> listOrganisations;
	protected static long REFRESHINTERVAL = 10000;
	protected int treedepth = 1;
	protected boolean redoChartModel = true;
	protected ChartModel myChart;

	/**
	 * 
	 * @return true if the links of the currently selected profile should be
	 *         shown. Used to not show anything if no profile is selected.
	 */
	@Override
	public boolean showLinks() {
		if (pMController.getSelectedEnterpriseEvaluationProfile() != null
				&& pMController.getSelectedEnterpriseEvaluationProfile() instanceof EnterpriseEvaluationProfile)
			return true;
		return false;
	}

	@Override
	public boolean getShowLinks() {
		return showLinks();
	}

	@Override
	public int getTreeDepth() {
		return treedepth;
	}

	@Override
	public Tree refreshTree() {
		List<EnterpriseEvaluationProfile> profs = pMController
				.getEnterpriseEvaluationService()
				.retrieveAllEnterpriseProfiles();
		List<Connectable> conn = new ArrayList<Connectable>(profs.size());
		conn.addAll(profs);
		PerformanceTree profileTree = new PerformanceTree(pMController);
		Organization org = pMController
				.getEnterpriseEvaluationService()
				.retrieveSelectedOrganisation(pMController.getScopeController());
		if (org != null) {
			profileTree.setAlsoShowOccurrence(true);
			profileTree.setEvaluatedOrganisation(org);
		} else {
			// if org == null all would be shown: too much - only for a single
			// org!
			profileTree.setAlsoShowOccurrence(false);
		}
		profileTree.setExpandAll(true);
		profileTree.setAlsoCreateAssociationNodes(false);
		profileTree.removeWhenNotInRoleOnly(conn,
				EvaluationProfile.ResultRoleType, pMController
						.getScopeController().getUnspecifiedScope());
		// too much and not wanted by end-users ---> too much detail
		// List<Dimension> cds =
		// pMController.getEnterpriseEvaluationService().getCompetenceService().getAllDimensions();
		// conn.addAll(cds);

		// competenceService.getAllCompetenceDimensions();

		profileTree.setAssociationScope(pMController.getScopeController()
				.getUnspecifiedScope());
		profileTree.retrieveNodes(treedepth, conn, new SearchedAssoc(null,
				EvaluationProfile.ResultRoleType, null));
		profileTree.setAssociationScope(pMController.getScopeController()
				.getUserScope());
		profileTree.retrieveNodes(treedepth, conn, new SearchedAssoc(null,
				EvaluationProfile.ResultRoleType, null));

		profileTree.addActionListener(this);
		pMController.getEnterpriseEvaluationService().setPerformanceTree(
				profileTree, pMController.getScopeController());
		updateUI();
		return profileTree;
	}

	@Override
	public void refreshTree(ActionEvent ae) {
		refreshTree();
	}

	private void updateUI() {
		try {
			// code below is heavy on the server side .... therefore we now have
			// a RenderManager!! ->
			// renderer.requestRender();
			// state.executeAndRender();
			// state.renderLater();
			// state.render();
			renderer.requestRender();
			redoChartModel = true;
		} catch (Exception e) {
			logger
					.debug("PMEnterpriseEvaluationControllerImpl.updateUI: Render Exception");
			logger.debug(e.getMessage());
		}
	}

	@Override
	public List<EnterpriseEvaluationProfile> retrieveAllEnterpriseProfiles() {
		List<EnterpriseEvaluationProfile> r = pMController
				.getEnterpriseEvaluationService().retrieveEnterpriseProfile(
						(String) null);
		logger
				.debug("PMEnterpriseEvaluationController -> getAllEnterpriseProfiles: "
						+ r.size());
		return r;
	}

	@Override
	public List<SelectItem> getListOfOrganisations() {
		if (listOrganisations == null
				|| listOrganisations.size() == 0
				|| System.currentTimeMillis() - lastRefreshOrganisations > REFRESHINTERVAL) {
			if (listOrganisations == null)
				listOrganisations = new ArrayList<SelectItem>();
			else
				listOrganisations.clear();
			listOrganisations.add(new SelectItem(" "));
			for (Organization s : this.pMController
					.getEnterpriseEvaluationService()
					.retrieveAllOrganisations()) {
				// / SelectItem (value, label) ... passing an object for value
				// does not work with the value change
				// listener..
				listOrganisations.add(new SelectItem(s.getId(), s.getName()));
			}
			lastRefreshOrganisations = System.currentTimeMillis();
			logger
					.debug("PMEnterpriseEvaluationController -> ListOfOrganisations: "
							+ listOrganisations.size());
		}
		return listOrganisations;
	}

	@Override
	public void selectedOrganisationChanged(ValueChangeEvent event) {
		logger.debug("Selected Organisation: (ValueChanged)");
		if (event.getNewValue() instanceof Organization) {
			setSelectedOrganisation((Organization) event.getNewValue());
		} else if (event.getNewValue() instanceof Long) {
			setSelectedOrganisation((Long) event.getNewValue());
		} else {
			setSelectedOrganisation(event.getNewValue().toString());
		}
	}

	@Override
	public Organization getSelectedOrganisation() {
		return this.pMController
				.getEnterpriseEvaluationService()
				.retrieveSelectedOrganisation(pMController.getScopeController());
	}

	@Override
	public void setSelectedOrganisation(Organization org) {

		this.pMController
				.getEnterpriseEvaluationService()
				.setSelectedOrganisation(org, pMController.getScopeController());
		refreshTree();
	}

	/** needed by linking */
	@Override
	public void setSelectedOrganisation(Long orgID) {
		Organization selectedOrganisation = getSelectedOrganisation();
		if (selectedOrganisation == null
				|| !selectedOrganisation.getId().equals(orgID)) {
			setSelectedOrganisation(pMController
					.getEnterpriseEvaluationService().retrieveOrganisation(
							orgID));
		}
	}

	@Override
	public void setSelectedOrganisation(String orgID) {
		logger.debug("setSelectedOrganisation (String)  ?is this the ID?: "
				+ orgID);
		try {
			setSelectedOrganisation(new Long(orgID));
		} catch (NumberFormatException nfe) {
			List<Organization> found = pMController
					.getEnterpriseEvaluationService().retrieveOrganisation(
							orgID);
			if (found != null && found.size() > 0)
				setSelectedOrganisation(found.get(0));
			else
				setSelectedOrganisation((Organization) null);
		}
	}

	@Override
	public String setSelectedOrganisation() {
		return setSelectedOrganisation((ActionEvent) null);
	}

	@Override
	public String setSelectedOrganisation(ActionEvent evt) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		if (map.get("selectedOrg") != null
				&& map.get("selectedOrg") instanceof String) {
			setSelectedOrganisation((String) map.get("selectedOrg"));
		}
		return "performanceMeasurementEvaluation";
	}

	@Override
	public void setPMController(PMController controller) {
		this.pMController = controller;
	}

	@Override
	public void setTreeDepth(int treeDepth) {
		treedepth = treeDepth;
		// refreshTree();
	}

	@Override
	public void renderingException(RenderingException arg0) {
		logger.debug(arg0.getLocalizedMessage());
	}

	@Override
	public void setRenderManager(RenderManager rm) {
		logger.debug("PMEnterpriseEvaluationControllerImpl.setRenderManager");
		renderer = rm.getOnDemandRenderer("PMEntepriseEvaluationRenderManager");
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

	@Override
	public void processAction(ActionEvent event)
			throws AbortProcessingException {
		if (event instanceof UserObjectActionEvent
				&& ((UserObjectActionEvent) event).getUserObject() instanceof ConnectableUserObject
				&& ((ConnectableUserObject) ((UserObjectActionEvent) event)
						.getUserObject()).getReference() instanceof EnterpriseEvaluationProfile) {
			pMController
					.setSelectedEnterpriseEvaluationProfile((EnterpriseEvaluationProfile) ((ConnectableUserObject) ((UserObjectActionEvent) event)
							.getUserObject()).getReference());
			updateUI();
		} else {
			logger
					.debug("PMEnterpriseEvaluationController: processAction with unknown type!!"
							+ event.toString());
		}
	}

	@Override
	public EnterpriseEvaluationProfile getSelectedEvaluationProfile() {
		PerformanceTree tree = pMController.getEnterpriseEvaluationService()
				.retrievePerformanceTree(pMController.getScopeController());
		if (pMController.getSelectedEnterpriseEvaluationProfile() == null
				&& tree != null
				&& tree.getRoot() != null
				&& ((DefaultMutableTreeNode) tree.getRoot()).getUserObject() instanceof ConnectableUserObject) {
			pMController
					.setSelectedEnterpriseEvaluationProfile((EnterpriseEvaluationProfile) ((ConnectableUserObject) ((DefaultMutableTreeNode) tree
							.getRoot()).getUserObject()).getReference());
			refreshTree();
		}
		return pMController.getSelectedEnterpriseEvaluationProfile();
	}

	/**
	 * this method is needed by the linking mechanism. the parameter has to be a
	 * String in order to make it persistent
	 */
	@Override
	public void setSelectedEvaluationProfile(String id) {
		logger.debug("setSelectedEProfile: " + id);
		setSelectedEvaluationProfile(pMController
				.getEnterpriseEvaluationService().retrieveEnterpriseProfile(
						new Long(id)));
	}

	@Override
	public void setSelectedEvaluationProfile(EnterpriseEvaluationProfile eep) {
		logger.debug("PMEnterpriseProfileDesignController -> selectProfile: "
				+ eep.getName());
		PerformanceTree tree = pMController.getEnterpriseEvaluationService()
				.retrievePerformanceTree(pMController.getScopeController());
		tree.clearTreeAndRootNode();
		tree
				.setRoot(new DefaultMutableTreeNode(new ConnectableUserObject(
						eep)));
		tree.recreateTree(5, new SearchedAssoc(null,
				EvaluationProfile.ResultRoleType, null));
		pMController.setSelectedEnterpriseEvaluationProfile(eep);
		refreshTree();
	}

	@Override
	public String getSelectedEvaluationProfileName() {
		EnterpriseEvaluationProfile result = this
				.getSelectedEvaluationProfile();
		if (result != null)
			return result.getName();

		return "";
	}

	public PanelPopup getLinkableTypeSelectionPopup(final JsfLink jsfLink,
			RootLinkController rootLinkController) {
		return pMController.getLinkableTypeSelectionPopup(jsfLink,
				rootLinkController, PMController.SELECT_ORGANISATION);
	}

	@Override
	public List<OrganisationInformation> getSearchedEnterprises() {
		List<OrganisationInformation> result = null;
		List<Organization> orgs = searchOrganisations();
		if (orgs != null && orgs.size() > 0) {
			logger.debug("# Of Enterprises this: Product " + selectedProduct
					+ " material: " + selectedMaterial + " technology: "
					+ selectedTechnology + " -> " + orgs.size());
			result = new ArrayList<OrganisationInformation>(orgs.size());
			for (Organization o : orgs) {
				boolean add = true;
				for (OrganisationInformation oi : result) {
					if (oi.getOrganisation().getId().equals(o.getId())) {
						add = false;
						break;
					}
				}
				if (add) {
					OrganisationInformation info = new OrganisationInformation();
					info.setOrganisation(o);
					info.setProductsServices(searchService.retrieveCanDo(o));
					Scope orgScope = this.pMController.getScopeController()
							.retrieveScope(o.getName());
					if (info.getProductsServices() != null
							&& info.getProductsServices().size() > 0) {
						info.setMaterials(new ArrayList<Material>());
						for (Item p : info.getProductsServices()) {
							info.getMaterials().addAll(
									searchService
											.retrieveMaterials(p, orgScope));
							info.getMaterials().addAll(
									searchService.retrieveMaterials(p,
											pMController.getScopeController()
													.getUnspecifiedScope()));
						}
					}
					result.add(info);
				}
			}

		} else {
			logger.debug("# Of Enterprises can do this: Product "
					+ selectedProduct + " material: " + selectedMaterial
					+ " technology: " + selectedTechnology + " 0");
		}
		return result;
	}

	protected List<Organization> searchOrganisations() {
		List<Organization> orgs = null;
		if (selectedProduct != null)
			orgs = searchService.retrieveCanDo(selectedProduct);
		if (selectedMaterial != null)
			orgs = searchService.retrieveCanDo(selectedMaterial);
		if (selectedTechnology != null)
			orgs = searchService.retrieveCanDo(selectedTechnology);
		Organization o = pMController
				.getEnterpriseEvaluationService()
				.retrieveSelectedOrganisation(pMController.getScopeController());
		if (o != null) {
			if (orgs == null)
				orgs = new ArrayList<Organization>();
			if (!orgs.contains(o))
				orgs.add(o);
		}
		return orgs;
	}

	@Override
	public Tree getProfileTree() {
		Tree tree = pMController.getEnterpriseEvaluationService()
				.retrievePerformanceTree(pMController.getScopeController());
		if (tree == null)
			tree = this.refreshTree();
		return tree;
	}

	Material selectedMaterial;

	@Override
	public Material getDoMaterial() {
		return selectedMaterial;
	}

	Product selectedProduct;

	@Override
	public Product getDoProduct() {
		return selectedProduct;
	}

	Technology selectedTechnology;

	@Override
	public Technology getDoTechnology() {
		return selectedTechnology;
	}

	@Override
	public List<SelectItem> getListOfMaterials() {
		List<SelectItem> result = new ArrayList<SelectItem>();
		result.add(new SelectItem(" "));
		for (Material m : searchService.retrieveAllMaterials()) {
			result.add(new SelectItem(m.getId(), m.getName()));
		}
		return result;
	}

	@Override
	public List<SelectItem> getListOfProducts() {
		List<Item> prod = new ArrayList<Item>();
		prod.addAll(searchService.retrieveAllComplexProducts());
		prod.addAll(searchService.retrieveAllSimpleProducts());
		prod.addAll(searchService.retrieveAllSystems());
		prod.addAll(searchService.retrieveAllSupportingServices());
		List<SelectItem> result = new ArrayList<SelectItem>();
		result.add(new SelectItem(" "));
		for (Item p : prod) {
			result.add(new SelectItem(p.getId(), p.getName()));
		}
		return result;
	}

	@Override
	public List<SelectItem> getListOfTechnologies() {
		List<SelectItem> result = new ArrayList<SelectItem>();
		result.add(new SelectItem(" "));
		for (Technology t : searchService.retrieveAllTechnologies()) {
			result.add(new SelectItem(t.getId(), t.getName()));
		}
		return result;
	}

	@Override
	public void selectedMaterialChanged(ValueChangeEvent evt) {
		logger.debug("Selected Material: (ValueChanged)");
		if (evt.getNewValue() != null) {
			if (evt.getNewValue() instanceof Long) {
				setDoMaterial(searchService.retrieveMaterial((Long) evt
						.getNewValue()));
				setDoTechnology(null);
				setDoProduct(null);
			} else if (evt.getNewValue() instanceof String) {
				try {
					setDoMaterial(searchService.retrieveMaterial(new Long(
							(String) evt.getNewValue())));
					setDoTechnology(null);
					setDoProduct(null);
				} catch (NumberFormatException nfe) {
					// ignore.. its ok
				}
			}
		} else {
			setDoMaterial(null);
		}
		updateUI();
	}

	@Override
	public void selectedProductChanged(ValueChangeEvent evt) {
		logger.debug("Selected Product: (ValueChanged)");
		if (evt.getNewValue() != null) {
			if (evt.getNewValue() instanceof Long) {
				setDoTechnology(null);
				setDoProduct(null);
				setDoMaterial(null);
				if (searchService.retrieveComplexProduct((Long) evt
						.getNewValue()) != null)
					setDoProduct(searchService
							.retrieveComplexProduct((Long) evt.getNewValue()));
				if (searchService.retrieveSimpleProduct((Long) evt
						.getNewValue()) != null)
					setDoProduct(searchService.retrieveSimpleProduct((Long) evt
							.getNewValue()));
				if (searchService.retrieveSystem((Long) evt.getNewValue()) != null)
					setDoProduct(searchService.retrieveSystem((Long) evt
							.getNewValue()));
			}
			if (evt.getNewValue() instanceof String) {
				try {
					Long id = new Long((String) evt.getNewValue());
					setDoTechnology(null);
					setDoProduct(null);
					setDoMaterial(null);
					if (searchService.retrieveComplexProduct(id) != null)
						setDoProduct(searchService.retrieveComplexProduct(id));
					if (searchService.retrieveSimpleProduct(id) != null)
						setDoProduct(searchService.retrieveSimpleProduct(id));
					if (searchService.retrieveSystem(id) != null)
						setDoProduct(searchService.retrieveSystem(id));
				} catch (NumberFormatException nfe) {
				}// ignore
			}
		} else {
			setDoProduct(null);
		}
		updateUI();
	}

	@Override
	public void selectedTechnologyChanged(ValueChangeEvent evt) {
		logger.debug("Selected Technology: (ValueChanged)");
		if (evt.getNewValue() != null) {
			if (evt.getNewValue() instanceof Long) {
				setDoTechnology(searchService.retrieveTechnology((Long) evt
						.getNewValue()));
				setDoProduct(null);
				setDoMaterial(null);
			} else if (evt.getNewValue() instanceof String) {
				try {
					setDoTechnology(searchService.retrieveTechnology(new Long(
							(String) evt.getNewValue())));
					setDoProduct(null);
					setDoMaterial(null);
				} catch (NumberFormatException nfe) {
				}// ignore
			}
		} else {
			setDoTechnology(null);
		}
		updateUI();
	}

	private boolean compare = false;

	@Override
	public boolean getCompare() {
		return compare;
	}

	@Override
	public void showComparison(ActionEvent ae) {
		compare = !compare;
		redoChartModel = true;
	}

	@Override
	public ChartModel getPerformanceComparison() {
		return getPerformanceComparison(searchOrganisations());
	}

	@Override
	public ChartModel getPerformanceComparison(List<Organization> group) {
		if (redoChartModel || myChart == null) {
			redoChartModel = false;
			List<Organization> all = this.pMController.getOrganizationService()
					.retrieveAll();
			if (group == null || group.size() == 0) {
				group = all;
			}
			if (myChart == null)
				myChart = new ChartModel();

			List<double[]> data = new ArrayList<double[]>(group.size());
			List<Color> colors = new ArrayList<Color>(group.size());
			List<String> xAxis = new ArrayList<String>();
			List<String> labels = new ArrayList<String>(group.size());

			data = getPerformanceChartDataAndAverage(group, all, labels,
					colors, xAxis);

			myChart.setData(data);
			logger.debug("data " + data.size());
			myChart.setLabels(labels);
			logger.debug("legendLabels " + labels.size());
			myChart.setColors(colors);
			logger.debug("colors " + colors.size());

			myChart.setXaxisTitle("Values");
			myChart.setXaxisLabels(xAxis);
			logger.debug("xAxis " + xAxis.size());
			logger.debug("data[0] " + data.get(0).length);

			myChart.setType(OutputChart.BAR_CLUSTERED_CHART_TYPE);
			myChart.setTitle("Performance");
			myChart.setYaxisTitle("Indicators");
			myChart.setHeight(""
					+ (30 + (data.get(0).length * data.size() * 8)));
			myChart.setWidth("800");
			myChart.setLegendPlacement("top");
			myChart.setRenderOnSubmit(true);
			myChart.setHorizontal(true);
		}
		return myChart;
	}

	protected List<double[]> getPerformanceChartDataAndAverage(
			List<Organization> orgs, List<Organization> orgsForAverageValues,
			List<String> labels, List<Color> colors, List<String> xAxis) {

		final boolean showAVG = false;

		Scope userscope = pMController.getScopeController().getUserScope();
		EnterpriseEvaluationProfile eep = pMController
				.getSelectedEnterpriseEvaluationProfile();

		List<ParameterizedCompetence> data = PerformanceEvaluation
				.retrieveSubElements(eep, pMController.getConnectableService(),
						userscope, pMController.getScopeController()
								.getUnspecifiedScope());
		ParameterizedCompetence rootComp = new ParameterizedCompetence();
		rootComp.setEvaluationProfile(eep);
		data.add(0, rootComp);
		List<double[]> result = new ArrayList<double[]>(data.size());

		for (int i = 0; i < data.size(); ++i) {
			if (showAVG)
				result.add(new double[orgs.size() + 1]); // include one for the
															// average values
			else
				result.add(new double[orgs.size()]);
		}
		ScopeService sc = pMController.getScopeController().getScopeService();
		for (int i = 0; i < orgs.size(); ++i) {
			Organization org = orgs.get(i);

			Scope orgscope = sc.retrieveScopeBy(org.getName());
			if (orgscope == null)
				orgscope = sc.createOrRetrieveScope(org.getName());
			labels.add(org.getName());
			colors.add(new Color((int) (Math.random() * 255), (int) (Math
					.random() * 255), (int) (Math.random() * 255)));
			for (int ii = 0; ii < data.size(); ++ii) {
				eep = data.get(ii).getEvaluationProfile();
				if (eep == null) {
					Dimension cd = data.get(ii).getCompetenceDimension();
					if (cd != null) {
						if (i == 0) // first round (update xAxis Names
							xAxis.add(cd.getName());
						Double r = PerformanceEvaluation.evaluate(cd, org,
								pMController.getConnectableService(),
								pMController.getEnterpriseEvaluationService(),
								orgscope);
						if (r == null || r.equals(0.0d))
							result.get(ii)[i] = 0.0000001d;
						else
							result.get(ii)[i] = r.doubleValue();
					} else {
						// fill xAxis name
						if (i == 0) // first round (update xAxis Names
							xAxis.add(" - - ");
						result.get(ii)[i] = 0.0000001d;
					}
				} else {
					if (i == 0) // first round (update xAxis Names
						xAxis.add(eep.getName());
					Double r = PerformanceEvaluation.evaluate(eep, org,
							pMController.getConnectableService(), pMController
									.getEnterpriseEvaluationService(),
							userscope, orgscope);
					if (r == null || r.equals(0.0d))
						result.get(ii)[i] = 0.0000001d;
					else
						result.get(ii)[i] = r.doubleValue();
				}
			}
		}

		if (showAVG && orgsForAverageValues != null) {
			// initialize with Average values
			labels.add("AVG");
			colors.add(new Color(255, 10, 10));
			for (int ii = 0; ii < data.size(); ++ii) {
				eep = data.get(ii).getEvaluationProfile();
				Dimension cd = null;
				if (eep == null)
					cd = data.get(ii).getCompetenceDimension();
				double sum = 0.0d;
				for (int i = 0; i < orgsForAverageValues.size(); ++i) {
					Organization org = orgsForAverageValues.get(i);
					// This scope is not used only the bo scope and the
					// unspecified scope
					// Scope orgscope =
					// pMController.getScopeController().getScopeService().createOrRetrieveScope(org.getName());
					Scope orgscope = userscope;
					if (eep == null && cd != null) {
						Double r = PerformanceEvaluation.evaluate(cd, org,
								pMController.getConnectableService(),
								pMController.getEnterpriseEvaluationService(),
								orgscope);
						if (r != null)
							sum += r.doubleValue();
					} else {
						Double r = PerformanceEvaluation.evaluate(eep, org,
								pMController.getConnectableService(),
								pMController.getEnterpriseEvaluationService(),
								userscope, orgscope);
						if (r != null)
							sum += r.doubleValue();
					}
				}
				if (sum == 0.0d) // Lib for charts can not handle 0 values
					sum = 0.0000001d;
				result.get(ii)[orgs.size()] = (sum / orgsForAverageValues
						.size());
			}
		}
		return result;
	}

	@Override
	public void setDoMaterial(Material p) {
		selectedMaterial = p;
	}

	@Override
	public void setDoProduct(Product p) {
		selectedProduct = p;
	}

	@Override
	public void setDoTechnology(Technology p) {
		selectedTechnology = p;
	}

	ProductMaterialSupportingServices_Service searchService;

	@Override
	public void setSearchService(
			ProductMaterialSupportingServices_Service service) {
		this.searchService = service;
	}
}
