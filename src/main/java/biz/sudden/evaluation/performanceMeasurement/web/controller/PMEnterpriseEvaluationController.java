package biz.sudden.evaluation.performanceMeasurement.web.controller;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.evaluation.performanceMeasurement.domain.ChartModel;
import biz.sudden.evaluation.performanceMeasurement.domain.OrganisationInformation;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;

public interface PMEnterpriseEvaluationController extends Renderable {

	public void setRenderManager(RenderManager rm);

	/**
	 * get all available enterprise profiles from the service
	 * 
	 * @return a list of all profiles
	 */
	public List<EnterpriseEvaluationProfile> retrieveAllEnterpriseProfiles();

	public int getTreeDepth();

	public void setTreeDepth(int treeDepth);

	public Tree refreshTree();

	public void refreshTree(ActionEvent ae);

	/**
	 * returns true if links to other modules are possible and link-buttons and
	 * already set links should be shown.
	 */
	public boolean showLinks();

	public boolean getShowLinks();

	public List<SelectItem> getListOfOrganisations();

	public Organization getSelectedOrganisation();

	public void setSelectedOrganisation(Organization org);

	public void setSelectedOrganisation(Long orgID);

	public void setSelectedOrganisation(String orgID);

	public void selectedOrganisationChanged(ValueChangeEvent event);

	public EnterpriseEvaluationProfile getSelectedEvaluationProfile();

	public Tree getProfileTree();

	public void setPMController(PMController controller);

	public String getSelectedEvaluationProfileName();

	public void setSelectedEvaluationProfile(String id);

	public void setSelectedEvaluationProfile(EnterpriseEvaluationProfile eep);

	public Product getDoProduct();

	public void setDoProduct(Product p);

	public void selectedProductChanged(ValueChangeEvent evt);

	public List<SelectItem> getListOfProducts();

	public Material getDoMaterial();

	public void setDoMaterial(Material p);

	public void selectedMaterialChanged(ValueChangeEvent evt);

	public List<SelectItem> getListOfMaterials();

	public Technology getDoTechnology();

	public void setDoTechnology(Technology p);

	public void selectedTechnologyChanged(ValueChangeEvent evt);

	public List<SelectItem> getListOfTechnologies();

	public void setSearchService(
			ProductMaterialSupportingServices_Service service);

	public List<OrganisationInformation> getSearchedEnterprises();

	public String setSelectedOrganisation(ActionEvent evt);

	public String setSelectedOrganisation();

	public boolean getCompare();

	public void showComparison(ActionEvent ae);

	public ChartModel getPerformanceComparison(List<Organization> group);

	ChartModel getPerformanceComparison();

}
