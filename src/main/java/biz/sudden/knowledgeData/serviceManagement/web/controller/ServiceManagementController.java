package biz.sudden.knowledgeData.serviceManagement.web.controller;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.TreeDragDrop;
import biz.sudden.knowledgeData.serviceManagement.service.MachineryType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ManufacturingProcessType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.knowledgeData.serviceManagement.service.TechnologyService;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public interface ServiceManagementController {

	public void initProdValueTree();

	public void initMaterialTree();

	public void initTechnologyTree();

	public void initMachineTree();

	// Listener
	// --------
	public void createOrganisation(ActionEvent event);

	public void selectedOrganisationChanged(ValueChangeEvent event);

	public void mapProductServiceTreeToTM(ActionEvent event);

	public void mapMaterialTreeToTM(ActionEvent event);

	public void mapTechnologyTreeToTM(ActionEvent event);

	public void mapMachineTreeToTM(ActionEvent event);

	public void printProductsServicesCanDo(ActionEvent event);

	public void showOrgPopup(ActionEvent event);

	public void hideOrgPopup(ActionEvent event);

	public String navigateToProductValueCreatingView();

	public String navigateToMaterialView();

	public String navigateToTechnologyView();

	public String navigateToMachineryView();

	// Getter and Setter
	// -----------------
	public SelectItem[] getExistingOrganisations();

	public void setExistingOrganisations(SelectItem[] existingOrganisations);

	public ServiceManagementService getBaseService();

	public void setBaseService(ServiceManagementService baseService);

	public void setScopeController(ScopeController scope);

	public ConnectableService getConnectableService();

	public void setConnectableService(ConnectableService connectableService);

	public Organization getSelectedOrganisation();

	public void setSelectedOrganisation(Organization organisation);

	public boolean isOrgPopupVisible();

	public void setOrgPopupVisible(boolean orgPopupVisible);

	public Long getSelectedOrganisationId();

	public void setSelectedOrganisationId(Long selectedOrganisationId);

	public String getSelectedOrganisationName();

	public void setSelectedOrganisationName(String selectedOrganisationName);

	public TreeDragDrop getProdValueTree();

	public void setProdValueTree(TreeDragDrop prodValueTree);

	public SelectItem getOrgSelectedItem();

	public void setOrgSelectedItem(SelectItem orgSelectedItem);

	public ProductMaterialSupportingServices_Service getPmsService();

	public void setPmsService(
			ProductMaterialSupportingServices_Service pmsService);

	public TreeDragDrop getMaterialTree();

	public void setMaterialTree(TreeDragDrop materialTree);

	public TreeDragDrop getTechnologyTree();

	public void setTechnologyTree(TreeDragDrop technologyTree);

	public TechnologyService getTechnologyService();

	public void setTechnologyService(TechnologyService technologyService);

	public TreeDragDrop getMachineTree();

	public void setMachineTree(TreeDragDrop machineTree);

	public MachineryType_Service getMachineService();

	public void setMachineService(MachineryType_Service machineService);

	public ManufacturingProcessType_Service getMptService();

	public void setMptService(ManufacturingProcessType_Service mptService);

	public boolean isButtonNavigateToMachineryViewDisabled();

	public boolean isButtonNavigateToTechnologyViewDisabled();

	public boolean isButtonNavigateToMaterialViewDisabled();

	public boolean isButtonNavigateToProductValueCreatingViewDisabled();

	public boolean getButtonNavigateToMachineryViewDisabled();

	public boolean getButtonNavigateToTechnologyViewDisabled();

	public boolean getButtonNavigateToMaterialViewDisabled();

	public boolean getButtonNavigateToProductValueCreatingViewDisabled();

	public boolean isButtonSaveMachinesDisabled();

	public boolean getButtonSaveMachinesDisabled();

	boolean isButtonNavigateToProductValueCreatingViewEnabled();

	boolean isButtonNavigateToMaterialViewEnabled();

	boolean isButtonNavigateToMachineryViewEnabled();

	boolean isButtonNavigateToTechnologyViewEnabled();

}