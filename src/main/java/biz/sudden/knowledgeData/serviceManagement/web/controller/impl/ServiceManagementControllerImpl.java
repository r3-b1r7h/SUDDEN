package biz.sudden.knowledgeData.serviceManagement.web.controller.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.ConnectableUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.SearchedAssoc;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.baseAndUtility.web.controller.tree.TreeDragDrop;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.domain.Machine;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.knowledgeData.serviceManagement.service.MachineryType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ManufacturingProcessType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.knowledgeData.serviceManagement.service.TechnologyService;
import biz.sudden.knowledgeData.serviceManagement.web.controller.ServiceManagementController;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public class ServiceManagementControllerImpl implements
		ServiceManagementController {

	private Logger logger = Logger.getLogger(this.getClass());

	// attributes for UI
	// -----------------clean
	private SelectItem[] existingOrganisations = new SelectItem[] {};// 4
																		// selectBox
	private boolean orgPopupVisible = false;

	private SelectItem orgSelectedItem;
	private Organization selectedOrganisation;
	private Long selectedOrganisationId;
	private String selectedOrganisationName = " ";// initialize with space
													// character, otherwise it
													// won't be rendered

	// drag drop trees
	private TreeDragDrop prodValueTree;
	private TreeDragDrop materialTree;
	private TreeDragDrop technologyTree;
	private TreeDragDrop machineTree; // ServiceManagementController of
										// subsystems
	// ------------------------
	private ServiceManagementService baseService;
	private ConnectableService connectableService;
	private ProductMaterialSupportingServices_Service pmsService;
	private ManufacturingProcessType_Service mptService;
	private TechnologyService technologyService;
	private MachineryType_Service machineService;
	private ScopeController scopeController;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #initProdValueTree()
	 */
	@Override
	public void initProdValueTree() {
		logger.debug("initProdValueTree: Organisation name: "
				+ this.selectedOrganisationName);

		// TODO Refactoring -> maybe during creation of selectedOrganisation
		// scope generation!
		if (selectedOrganisationName != null && connectableService != null
				&& pmsService != null) {
			this.setScope(selectedOrganisationName);

			prodValueTree = new TreeDragDrop(connectableService,
					scopeController);
			prodValueTree.setSimpleCopy(true);

			// SOURCE

			Tree sourceTree = new Tree(connectableService, scopeController,
					"Product(-parts)");
			sourceTree.setAssociationScope(scopeController
					.getUnspecifiedScope());
			sourceTree.setAlsoCreateAssociationNodes(false);
			sourceTree.setAlsoShowOccurrence(false);
			sourceTree.setExpandAll(true);

			// Systems
			List<Connectable> tmp = new ArrayList<Connectable>();
			tmp.addAll(pmsService.retrieveAllSystems());
			// Complex Products
			tmp.addAll(pmsService.retrieveComplexProduct("ComplexPart"));
			// simple parts
			tmp.addAll(pmsService.retrieveSimpleProduct("Simple_Part"));
			// services
			tmp.addAll(pmsService.retrieveSupportingService("Service"));
			logger.debug("Tree size: " + tmp.size());
			sourceTree.removeWhenInRole(tmp, Item.isASubType, scopeController
					.getRetrieveAllScope());
			logger.debug("removed Item.isASubType Tree size: " + tmp.size());

			SearchedAssoc sourceAss = new SearchedAssoc(Item.isAAssocType,
					Item.isASuperType, Item.isASubType);
			sourceTree.retrieveNodes(5, tmp, sourceAss);

			logger.debug("retrieved Nodes");
			prodValueTree.setSourceTree(sourceTree);
			logger.debug("Source ProductValueTree loaded");

			// TARGET
			// SearchedAssoc targetAssoc = new
			// SearchedAssoc(Product.ASSOCIATIONTYPE, Product.SUPER,
			// Product.SUB);

			Tree targetTree = new Tree(connectableService, scopeController,
					"Products/Services provided by " + selectedOrganisationName);
			targetTree.setAssociationScope(scopeController.getUserScope());
			targetTree.setAlsoCreateAssociationNodes(false);
			targetTree.setOccurrenceScope(scopeController.getUserScope());
			targetTree.setAlsoShowOccurrence(true);
			targetTree.setExpandAll(true);
			// targetTree.retrieveNodes(-1, null, targetAssoc);

			tmp.clear();
			tmp
					.addAll(mptService
							.retrieveProductsServicesManufCanDo(this.selectedOrganisation));
			targetTree.retrieveNodes(5, tmp, sourceAss);
			prodValueTree.setTargetTree(targetTree);
			logger.debug("Target Product/ServiceTree loaded");

		}
	}

	public Scope setScope(String orgname) {
		Scope s = connectableService.retrieveScopeBy(orgname);
		if (s == null) {
			Set<Connectable> c = new HashSet<Connectable>();
			// c.addAll(baseService.retrieveOrganisationsBy(this.selectedOrganisationName));
			c.add(selectedOrganisation);
			logger.debug("Scope created for org: " + selectedOrganisationName);
			s = setScope(orgname, c);
		}
		return s;
	}

	/**
	 * set a new scope
	 * 
	 * @param name
	 * @param c
	 *            ignored for the moment...
	 * @return the new scope
	 */
	public Scope setScope(String name, Set<Connectable> c) {
		Scope s = connectableService
				.createOrRetrieveScope(selectedOrganisationName);
		setScope(s); // don't use the scopereturned,,
		return s;
	}

	/**
	 * sets the new socpe
	 * 
	 * @param s
	 * @return the old scope
	 */
	public Scope setScope(Scope s) {
		return scopeController.setUserScope(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #initMaterialTree()
	 */
	@Override
	public void initMaterialTree() {

		materialTree = new TreeDragDrop(connectableService, scopeController);
		materialTree.setSimpleCopy(true);
		// SOURCE

		Tree sourceTree = new Tree(connectableService, scopeController,
				"Materials");
		sourceTree.setAssociationScope(scopeController.getUnspecifiedScope());
		sourceTree.setAlsoCreateAssociationNodes(false);
		sourceTree.setAlsoShowOccurrence(false);
		sourceTree.setExpandAll(true);

		List<Connectable> tmp = new ArrayList<Connectable>();
		tmp.addAll(pmsService.retrieveAllMaterials());
		sourceTree.removeWhenInRole(tmp, Material.SUB, scopeController
				.getRetrieveAllScope());

		SearchedAssoc sA = new SearchedAssoc(Material.ASSOCIATIONTYPE,
				Material.SUPER, Material.SUB);
		sourceTree.retrieveNodes(5, tmp, sA);
		materialTree.setSourceTree(sourceTree);

		// TARGET

		tmp.clear();
		tmp.addAll(pmsService.retrieveMaterials(
				(Item) ((ConnectableUserObject) prodValueTree
						.getSelectedNodeObject()).getReference(),
				connectableService.retrieveScopeBy(selectedOrganisationName)));

		SearchedAssoc targetAssoc = new SearchedAssoc(Material.ASSOCIATIONTYPE,
				Material.SUPER, Material.SUB);
		Tree targetTree = new Tree(connectableService, scopeController,
				"Materials for " + prodValueTree.getSelectedText());
		// targetTree.retrieveNodes(-1, null, targetAssoc);
		targetTree.setAssociationScope(scopeController.getUserScope());
		targetTree.setAlsoCreateAssociationNodes(false);
		targetTree.setOccurrenceScope(scopeController.getUserScope());
		targetTree.setAlsoShowOccurrence(true);
		targetTree.retrieveNodes(5, tmp, targetAssoc);
		materialTree.setTargetTree(targetTree);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #initTechnologyTree()
	 */
	@Override
	public void initTechnologyTree() {
		List<Connectable> tmp = new ArrayList<Connectable>();
		technologyTree = new TreeDragDrop(connectableService, scopeController);
		technologyTree.setSimpleCopy(true);

		// SOURCE
		Tree sourceTree = new Tree(connectableService, scopeController,
				"Technology");
		sourceTree.setAssociationScope(scopeController.getUnspecifiedScope());
		sourceTree.setAlsoCreateAssociationNodes(false);
		sourceTree.setAlsoShowOccurrence(false);
		sourceTree.setExpandAll(true);

		tmp.addAll(technologyService.retrieveAllTechnologies());
		sourceTree.removeWhenInRole(tmp, Technology.SUB, scopeController
				.getRetrieveAllScope());

		SearchedAssoc sA = new SearchedAssoc(Technology.ASSOCIATIONTYPE,
				Technology.SUPER, Technology.SUB);
		sourceTree.retrieveNodes(3, tmp, sA);
		technologyTree.setSourceTree(sourceTree);

		// TARGET
		SearchedAssoc targetAssoc = new SearchedAssoc(
				Technology.ASSOCIATIONTYPE, Technology.SUPER, Technology.SUB);
		Tree targetTree = new Tree(connectableService, scopeController,
				"Technology for " + materialTree.getSelectedText());
		targetTree.setAssociationScope(scopeController.getUserScope());
		targetTree.setOccurrenceScope(scopeController.getUserScope());
		targetTree.setAlsoCreateAssociationNodes(false);
		targetTree.setAlsoShowOccurrence(true);

		tmp.clear();
		tmp.addAll(technologyService.retrieveTechnologies(
				(Material) ((ConnectableUserObject) materialTree
						.getSelectedNodeObject()).getReference(),
				connectableService
						.retrieveScopeBy(this.selectedOrganisationName)));
		targetTree.retrieveNodes(-1, tmp, targetAssoc);
		technologyTree.setTargetTree(targetTree);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #initMachineTree()
	 */
	@Override
	public void initMachineTree() {
		machineTree = new TreeDragDrop(connectableService, scopeController);
		machineTree.setSimpleCopy(true);

		// SOURCE tree
		Tree sourceTree = new Tree(connectableService, scopeController,
				"Machines");
		sourceTree.setAssociationScope(scopeController.getUnspecifiedScope());
		sourceTree.setAlsoCreateAssociationNodes(false);
		sourceTree.setAlsoShowOccurrence(false);

		List<Connectable> mL = new ArrayList<Connectable>();
		mL.addAll(machineService.retrieveAllMachines());
		sourceTree.retrieveNodes(-1, mL, null);
		machineTree.setSourceTree(sourceTree);

		// TARGET
		// init machineTree according to selected Technology
		Set<Machine> machines = new HashSet<Machine>();
		Technology selectedTech = (Technology) ((ConnectableUserObject) technologyTree
				.getSelectedNodeObject()).getReference();
		machines = mptService.retrieveMachines(selectedTech, connectableService
				.retrieveScopeBy(this.selectedOrganisationName));
		mL = new ArrayList<Connectable>();
		mL.addAll(machines);

		Tree targetTree = new Tree(connectableService, scopeController,
				"Machines Used for " + technologyTree.getSelectedText());
		targetTree.setAssociationScope(scopeController.getUserScope());
		targetTree.setOccurrenceScope(scopeController.getUserScope());
		targetTree.setAlsoCreateAssociationNodes(false);
		targetTree.setAlsoShowOccurrence(true);
		targetTree.retrieveNodes(-1, mL, null);
		machineTree.setTargetTree(targetTree);
	}

	// Listener
	// --------
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #createOrganisation(javax.faces.event .ActionEvent)
	 */
	@Override
	public void createOrganisation(ActionEvent event) {
		selectedOrganisation = new Organization();
		selectedOrganisation.setName(selectedOrganisationName);
		baseService.createOrganisation(selectedOrganisation);
		this.orgPopupVisible = false;
		initExistingOrganisations();
		setSelectedOrganisation(selectedOrganisation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #initExistingOrganisations(javax.faces .event.ActionEvent)
	 */
	public void initExistingOrganisations() {
		logger.debug("initExistingOrganisations");
		List<Organization> orgs = baseService.retrieveAllOrganisations();
		logger.debug("after retrieveOrganisations: " + orgs);
		if (orgs != null && orgs.size() > 0) {
			existingOrganisations = new SelectItem[orgs.size()];
			for (int i = 0; i < orgs.size(); i++) {
				// SelectItem sI = new
				// SelectItem(orgs.get(i),orgs.get(i).getNiame());
				// logger.debug("Value of SelectItem: "+sI.getValue().getClass().getName());
				existingOrganisations[i] = new SelectItem(orgs.get(i).getId(),
						orgs.get(i).getName());
			}
			if (orgs.size() > 0 && getSelectedOrganisation() == null)
				setSelectedOrganisation(orgs.get(0));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #selectedOrganisationChanged(javax.faces .event.ValueChangeEvent)
	 */
	@Override
	public void selectedOrganisationChanged(ValueChangeEvent event) {
		// logger.debug("selectedOrganisation value changed: "+event.getNewValue().getClass().getName());
		// logger.debug("selectedOrganisationID - without getLong: "+(String)event.getNewValue());
		// logger.debug("selectedOrganisationID - with getLong: "+Long.valueOf((String)event.getNewValue()).longValue());
		setSelectedOrganisationId((Long) event.getNewValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #selectedOrganisationChanged(javax.faces .event.ValueChangeEvent)
	 */
	@Override
	public void setSelectedOrganisation(Organization org) {
		logger.debug("setSelectedOrganisation: " + org);
		logger.debug("setSelectedOrganisation: " + org.getName());
		logger.debug("setSelectedOrganisation: " + org.getId());

		selectedOrganisation = org;
		selectedOrganisationName = org.getName();
		selectedOrganisationId = org.getId();
		// logger.debug("selected selectedOrganisation id: "+selectedOrganisationId);
		// logger.debug("selectedOrgName: "+this.selectedOrganisationName);
		if (connectableService != null && baseService != null
				&& this.pmsService != null && scopeController != null) {
			scopeController.setUserScope(connectableService
					.createOrRetrieveScope(selectedOrganisationName));
			initProdValueTree();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #mapProductTreeToTM(javax.faces.event .ActionEvent)
	 */
	@Override
	public void mapProductServiceTreeToTM(ActionEvent event) {
		if (this.prodValueTree == null) {
			return;
		}
		DefaultMutableTreeNode root = ((DefaultMutableTreeNode) prodValueTree
				.getTargetTree().getRoot());

		for (int i = 0; i < (root).getChildCount(); ++i) {
			Item p = (Item) ((ConnectableUserObject) ((DefaultMutableTreeNode) (root)
					.getChildAt(i)).getUserObject()).getReference();
			logger.debug("mapProductServiceTreeToTM: product/Service: "
					+ p.getName() + " org: " + selectedOrganisation.getName());
			mptService.addCanDoProductService(this.selectedOrganisation, p);
			// recursive mapping
			mapProductsServiceRecursive((DefaultMutableTreeNode) (root)
					.getChildAt(i), this.selectedOrganisation);
		}
	}

	@Override
	public void mapMaterialTreeToTM(ActionEvent event) {
		if (this.materialTree == null) {
			return;
		}
		Item cp = (Item) ((ConnectableUserObject) prodValueTree
				.getSelectedNodeObject()).getReference();
		DefaultMutableTreeNode root = ((DefaultMutableTreeNode) materialTree
				.getTargetTree().getRoot());

		for (int i = 0; i < (root).getChildCount(); ++i) {
			Material m = (Material) ((ConnectableUserObject) ((DefaultMutableTreeNode) (root)
					.getChildAt(i)).getUserObject()).getReference();
			logger.debug("mapMaterialTreeToTM: Material: " + m.getName()
					+ " product: " + cp.getName());
			pmsService.addMaterial(cp, m, connectableService
					.retrieveScopeBy(this.selectedOrganisationName));
			// recursive mapping
			mapMaterialsRecursive(
					(DefaultMutableTreeNode) (root).getChildAt(i),
					this.selectedOrganisation, cp);
		}
	}

	@Override
	public void mapTechnologyTreeToTM(ActionEvent event) {
		if (this.technologyTree == null) {
			return;
		}
		DefaultMutableTreeNode root = ((DefaultMutableTreeNode) technologyTree
				.getTargetTree().getRoot());
		Material material = (Material) ((ConnectableUserObject) materialTree
				.getSelectedNodeObject()).getReference();
		// logger.debug("Material: " + material.getName());
		for (int i = 0; i < (root).getChildCount(); ++i) {
			Technology t = (Technology) ((ConnectableUserObject) ((DefaultMutableTreeNode) (root)
					.getChildAt(i)).getUserObject()).getReference();
			logger.debug("mapTechnologyTreeToTM: technology: " + t.getName()
					+ " material: " + material.getName());
			technologyService.addTechnology(material, t, connectableService
					.retrieveScopeBy(this.selectedOrganisationName));
			mapTechnologiesRecursive((DefaultMutableTreeNode) (root)
					.getChildAt(i), this.selectedOrganisation, material);
		}

	}

	@Override
	public void mapMachineTreeToTM(ActionEvent event) {
		if (this.machineTree == null) {
			return;
		}
		DefaultMutableTreeNode root = ((DefaultMutableTreeNode) machineTree
				.getTargetTree().getRoot());
		Technology technology = (Technology) ((ConnectableUserObject) technologyTree
				.getSelectedNodeObject()).getReference();

		for (int i = 0; i < (root).getChildCount(); ++i) {
			Machine m = (Machine) ((ConnectableUserObject) ((DefaultMutableTreeNode) (root)
					.getChildAt(i)).getUserObject()).getReference();
			logger.debug("mapMachineTreeToTM: Machine: " + m.getName()
					+ " technology: " + technology.getName());
			mptService.addMachine(technology, m, connectableService
					.retrieveScopeBy(this.selectedOrganisationName));
			mapMachinesRecursive((DefaultMutableTreeNode) (root).getChildAt(i),
					this.selectedOrganisation, technology);
		}
	}

	// just to test
	@Override
	public void printProductsServicesCanDo(ActionEvent event) {
		logger.debug("---Products/Services " + selectedOrganisation.getName()
				+ " can do---");
		for (Item p : mptService
				.retrieveProductsServicesManufCanDo(selectedOrganisation)) {
			logger.debug("ProductService: " + p.getName());
		}
	}

	private void mapProductsServiceRecursive(DefaultMutableTreeNode treeNode,
			Organization org) {
		Item superC = (Item) ((ConnectableUserObject) (treeNode)
				.getUserObject()).getReference();
		// logger.debug("superC: "+superC.getName());
		int nextChildren = treeNode.getChildCount();
		// logger.debug("#nextChildren: "+nextChildren);
		if (nextChildren > 0) {
			for (int i = 0; i < nextChildren; i++) {
				// temporally just item TODO generic method which defines class
				// Type

				Item subC = (Item) ((ConnectableUserObject) ((DefaultMutableTreeNode) treeNode
						.getChildAt(i)).getUserObject()).getReference();
				// logger.debug("--subC Node Text: "+
				// ((ConnectableUserObject)((DefaultMutableTreeNode)
				// treeNode).getUserObject()).getText());
				// logger.debug("--subC in MapRecursive: "+subC.getName());
				pmsService.addPart(superC, subC, connectableService
						.retrieveScopeBy(org.getName()));
				// logger.debug("--org in rec "+org.getName());
				mptService.addCanDoProductService(org, subC);

				if (treeNode.getChildAt(i).getChildCount() > 0) {
					mapProductsServiceRecursive(
							((DefaultMutableTreeNode) treeNode.getChildAt(i)),
							org);

				}
			}
		}

	}

	private void mapMaterialsRecursive(DefaultMutableTreeNode treeNode,
			Organization org, Item product) {
		Material superC = (Material) ((ConnectableUserObject) (treeNode)
				.getUserObject()).getReference();
		int nextChildren = treeNode.getChildCount();
		if (nextChildren > 0) {
			for (int i = 0; i < nextChildren; i++) {
				Material subC = (Material) ((ConnectableUserObject) ((DefaultMutableTreeNode) treeNode
						.getChildAt(i)).getUserObject()).getReference();
				pmsService.addChildMaterial(superC, subC, connectableService
						.retrieveScopeBy(org.getName()));
				pmsService.addMaterial(product, subC, connectableService
						.retrieveScopeBy(org.getName()));

				if (treeNode.getChildAt(i).getChildCount() > 0) {
					mapMaterialsRecursive(((DefaultMutableTreeNode) treeNode
							.getChildAt(i)), org, product);

				}
			}
		}

	}

	private void mapTechnologiesRecursive(DefaultMutableTreeNode treeNode,
			Organization org, Material material) {
		Technology superC = (Technology) ((ConnectableUserObject) (treeNode)
				.getUserObject()).getReference();
		// logger.debug("Technology: "+superC.getName());
		int nextChildren = treeNode.getChildCount();
		if (nextChildren > 0) {
			for (int i = 0; i < nextChildren; i++) {
				Technology subC = (Technology) ((ConnectableUserObject) ((DefaultMutableTreeNode) treeNode
						.getChildAt(i)).getUserObject()).getReference();
				technologyService.addSub(superC, subC, connectableService
						.retrieveScopeBy(org.getName()));
				technologyService.addTechnology(material, subC,
						connectableService.retrieveScopeBy(org.getName()));
				// logger.debug("SubTechnology: "+subC.getName());

				if (treeNode.getChildAt(i).getChildCount() > 0) {
					mapTechnologiesRecursive(((DefaultMutableTreeNode) treeNode
							.getChildAt(i)), org, material);
				}
			}
		}

	}

	private void mapMachinesRecursive(DefaultMutableTreeNode treeNode,
			Organization org, Technology technology) {
		int nextChildren = treeNode.getChildCount();
		if (nextChildren > 0) {
			for (int i = 0; i < nextChildren; i++) {
				Machine subC = (Machine) ((ConnectableUserObject) ((DefaultMutableTreeNode) treeNode
						.getChildAt(i)).getUserObject()).getReference();
				this.mptService.addMachine(technology, subC, connectableService
						.retrieveScopeBy(org.getName()));

				if (treeNode.getChildAt(i).getChildCount() > 0) {
					mapMachinesRecursive(((DefaultMutableTreeNode) treeNode
							.getChildAt(i)), org, technology);
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #navigateToProductValueCreatingView()
	 */
	@Override
	public String navigateToProductValueCreatingView() {
		this.initProdValueTree();
		// logger.debug("navigateTo after init");
		return "productValueCreatingView";
	}

	@Override
	public boolean isButtonNavigateToProductValueCreatingViewEnabled() {
		return !isButtonNavigateToProductValueCreatingViewDisabled();
	}

	@Override
	public boolean isButtonNavigateToProductValueCreatingViewDisabled() {
		return this.selectedOrganisationName == null;
	}

	@Override
	public boolean getButtonNavigateToProductValueCreatingViewDisabled() {
		return isButtonNavigateToProductValueCreatingViewDisabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #navigateToMaterialView()
	 */
	@Override
	public String navigateToMaterialView() {
		this.initMaterialTree();
		mapProductServiceTreeToTM(null);
		// logger.debug("Selected Component of prodValueTree"+this.prodValueTree.getSelectedComponent());
		// logger.debug("navigateTo after init");
		if (this.prodValueTree.getSelectedNodeObject() != null)
			return "materialView";
		else
			return "";
	}

	@Override
	public boolean isButtonNavigateToMaterialViewEnabled() {
		return !isButtonNavigateToMaterialViewDisabled();
	}

	@Override
	public boolean isButtonNavigateToMaterialViewDisabled() {
		return this.prodValueTree.getSelectedNodeObject() == null
				|| !(this.prodValueTree.getSelectedNodeObject() instanceof ConnectableUserObject);
	}

	@Override
	public boolean getButtonNavigateToMaterialViewDisabled() {
		return isButtonNavigateToMaterialViewDisabled();
	}

	/*
	 * ge (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #navigateToTechnologyView()
	 */
	@Override
	public String navigateToTechnologyView() {
		this.initTechnologyTree();
		mapMaterialTreeToTM(null);
		if (this.materialTree.getSelectedNodeObject() != null)
			return "technologyView";
		else
			return "";
	}

	@Override
	public boolean isButtonNavigateToTechnologyViewEnabled() {
		return !isButtonNavigateToTechnologyViewDisabled();
	}

	@Override
	public boolean isButtonNavigateToTechnologyViewDisabled() {
		return this.materialTree.getSelectedNodeObject() == null
				|| !(this.materialTree.getSelectedNodeObject() instanceof ConnectableUserObject);
	}

	@Override
	public boolean getButtonNavigateToTechnologyViewDisabled() {
		return isButtonNavigateToTechnologyViewDisabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #navigateToMachineryView()
	 */
	@Override
	public String navigateToMachineryView() {
		this.initMachineTree();
		mapTechnologyTreeToTM(null);
		if (this.technologyTree.getSelectedNodeObject() != null)
			return "machineryView";
		else
			return "";
	}

	@Override
	public boolean isButtonNavigateToMachineryViewEnabled() {
		return !isButtonNavigateToMachineryViewDisabled();
	}

	@Override
	public boolean isButtonNavigateToMachineryViewDisabled() {
		return this.technologyTree.getSelectedNodeObject() == null
				|| !(this.technologyTree.getSelectedNodeObject() instanceof ConnectableUserObject);
	}

	@Override
	public boolean getButtonNavigateToMachineryViewDisabled() {
		return isButtonNavigateToMachineryViewDisabled();
	}

	@Override
	public boolean isButtonSaveMachinesDisabled() {
		return false;
	}

	@Override
	public boolean getButtonSaveMachinesDisabled() {
		return isButtonSaveMachinesDisabled();
	}

	// Getter and Setter
	// -----------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getExistingOrganisations()
	 */
	@Override
	public SelectItem[] getExistingOrganisations() {
		initExistingOrganisations();
		logger.debug("Existing orgs: " + existingOrganisations);
		return existingOrganisations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setExistingOrganisations(javax.faces .model.SelectItem[])
	 */

	@Override
	public void setExistingOrganisations(SelectItem[] existingOrganisations) {
		this.existingOrganisations = existingOrganisations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getBaseService()
	 */

	@Override
	public ServiceManagementService getBaseService() {
		return baseService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setBaseService(biz.sudden.baseAndUtility .service.BaseService)
	 */

	@Override
	public void setBaseService(ServiceManagementService baseService) {
		this.baseService = baseService;
		this.initExistingOrganisations();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getConnectableService()
	 */

	@Override
	public ConnectableService getConnectableService() {
		return connectableService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * @seebiz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setConnectableService(biz.sudden.
	 * baseAndUtility.service.ConnectableService)
	 */

	@Override
	public void setConnectableService(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

	@Override
	public void setScopeController(ScopeController scope) {
		scopeController = scope;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #isOrgPopupVisible()
	 */
	@Override
	public boolean isOrgPopupVisible() {
		return orgPopupVisible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setOrgPopupVisible(boolean)
	 */
	@Override
	public void setOrgPopupVisible(boolean orgPopupVisible) {
		this.orgPopupVisible = orgPopupVisible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #showOrgPopup(javax.faces.event.ActionEvent )
	 */
	@Override
	public void showOrgPopup(ActionEvent event) {
		this.orgPopupVisible = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #hideOrgPopup(javax.faces.event.ActionEvent )
	 */
	@Override
	public void hideOrgPopup(ActionEvent event) {
		this.orgPopupVisible = false;
	}

	// public Organisation getSelectedOrganisation() {
	// return selectedOrganisation;
	// }
	//
	// public void setSelectedOrganisation(Organisation selectedOrganisation) {
	// this.selectedOrganisation = selectedOrganisation;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getSelectedOrganisationId()
	 */
	@Override
	public Long getSelectedOrganisationId() {
		return selectedOrganisationId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setSelectedOrganisationId(java.lang. Long)
	 */
	@Override
	public void setSelectedOrganisationId(Long selectedOrganisationId) {
		setSelectedOrganisation(baseService
				.retrieveOrganisation(selectedOrganisationId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getSelectedOrganisationName()
	 */
	@Override
	public String getSelectedOrganisationName() {
		// logger.debug("selectedOrgName: "+this.selectedOrganisationName);
		return selectedOrganisationName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setSelectedOrganisationName(java.lang .String)
	 */
	@Override
	public void setSelectedOrganisationName(String selectedOrganisationName) {
		List<Organization> orgs = baseService
				.retrieveOrganisationsByName(selectedOrganisationName);
		if (orgs.size() > 0)
			setSelectedOrganisation(orgs.get(0));
	}

	@Override
	public Organization getSelectedOrganisation() {
		return selectedOrganisation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getProdValueTree()
	 */
	@Override
	public TreeDragDrop getProdValueTree() {
		logger.debug("ProductTree.source: "
				+ prodValueTree.getSourceTree().getChildCount(
						prodValueTree.getSourceTree().getRoot()));
		return prodValueTree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setProdValueTree(biz.sudden.baseAndUtility
	 * .web.controller.impl.tree.TreeDragDropImpl)
	 */
	@Override
	public void setProdValueTree(TreeDragDrop prodValueTree) {
		this.prodValueTree = prodValueTree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getOrgSelectedItem()
	 */
	@Override
	public SelectItem getOrgSelectedItem() {
		return orgSelectedItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setOrgSelectedItem(javax.faces.model .SelectItem)
	 */
	@Override
	public void setOrgSelectedItem(SelectItem orgSelectedItem) {
		this.orgSelectedItem = orgSelectedItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getPmsService()
	 */
	@Override
	public ProductMaterialSupportingServices_Service getPmsService() {
		return pmsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setPmsService(biz.sudden.knowledgeData
	 * .serviceManagement.service.ProductMaterialSupportingServices_Service)
	 */
	@Override
	public void setPmsService(
			ProductMaterialSupportingServices_Service pmsService) {
		this.pmsService = pmsService;
	}

	// public String getSelectedProductName() {
	// return selectedProductName;
	// }
	//
	// public void setSelectedProductName(String selectedProductName) {
	// this.selectedProductName = selectedProductName;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getMaterialTree()
	 */
	@Override
	public TreeDragDrop getMaterialTree() {
		return materialTree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setMaterialTree(biz.sudden.baseAndUtility
	 * .web.controller.impl.tree.TreeDragDropImpl)
	 */
	@Override
	public void setMaterialTree(TreeDragDrop materialTree) {
		this.materialTree = materialTree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getTechnologyTree()
	 */
	@Override
	public TreeDragDrop getTechnologyTree() {
		return technologyTree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setTechnologyTree(biz.sudden.baseAndUtility
	 * .web.controller.impl.tree.TreeDragDropImpl)
	 */
	@Override
	public void setTechnologyTree(TreeDragDrop technologyTree) {
		this.technologyTree = technologyTree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getTechnologyService()
	 */
	@Override
	public TechnologyService getTechnologyService() {
		return technologyService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * @seebiz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setTechnologyService(biz.sudden.
	 * knowledgeData.serviceManagement.service.TechnologyService)
	 */
	@Override
	public void setTechnologyService(TechnologyService technologyService) {
		this.technologyService = technologyService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getMachineTree()
	 */
	@Override
	public TreeDragDrop getMachineTree() {
		return machineTree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setMachineTree(biz.sudden.baseAndUtility
	 * .web.controller.impl.tree.TreeDragDropImpl)
	 */
	@Override
	public void setMachineTree(TreeDragDrop machineTree) {
		this.machineTree = machineTree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getMachineService()
	 */
	@Override
	public MachineryType_Service getMachineService() {
		return machineService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setMachineService(biz.sudden.knowledgeData
	 * .serviceManagement.service.MachineryType_Service)
	 */
	@Override
	public void setMachineService(MachineryType_Service machineService) {
		this.machineService = machineService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #getMptService()
	 */
	@Override
	public ManufacturingProcessType_Service getMptService() {
		return mptService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.Controller
	 * #setMptService(biz.sudden.knowledgeData
	 * .serviceManagement.service.ManufacturingProcessType_Service)
	 */
	@Override
	public void setMptService(ManufacturingProcessType_Service mptService) {
		this.mptService = mptService;
	}
}
