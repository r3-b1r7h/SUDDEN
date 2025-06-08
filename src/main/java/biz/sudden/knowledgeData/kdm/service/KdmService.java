package biz.sudden.knowledgeData.kdm.service;

import java.io.File;
import java.util.List;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.service.ActorService;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPBulletinBoardService;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.evaluation.performanceMeasurement.service.NetworkEvaluationService;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMInstancesManagement_Service;
import biz.sudden.knowledgeData.serviceManagement.service.MachineryType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ManufacturingProcessType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.knowledgeData.serviceManagement.service.TechnologyService;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.service.IOrganization;

import com.hp.hpl.jena.ontology.OntModel;

/**
 * TODO: retrieveAll* -> *..Technolgy, Machine, Product, Material,
 * EnterpriseEvaluationProfile, ... tbc TODO: clear* || delete* -"- TODO: Georg:
 * CaseFileService einbinden
 * 
 * @author chris
 * 
 */
public interface KdmService {

	public OntModel getOntology();

	public String getOntologyNameSpace();

	/**
	 * clean up the DB!
	 * 
	 */

	public void clean();

	public void initUsers();

	public void initMessages();

	public void initPMS();

	/**
	 * inits test data for trees: material, technologies, machines and prod
	 * values
	 */

	public void initOntology(File ontologyUploadFile);

	public EnterpriseEvaluationProfile makeProfile(String name);

	public void initCompanyPerformance();

	public void initTest(int width, int depth);

	public List<Connectable> retrieveAllConnectables();

	public Organization retrieveOrganization(Long id);

	public Supplier retrieveSupplier(Long id);

	public void deleteOrganization(Organization s);

	public void deleteSupplier(Supplier s);

	public void updateCreateOrganization(Organization s);

	public void updateCreateSupplier(Supplier s);

	public ConnectableService getConnectableService();

	public void setConnectableService(ConnectableService conectableservice);

	public ServiceManagementService getServiceManagementService();

	public void setServiceManagementService(ServiceManagementService baseService);

	public UserService getUserService();

	public CaseService getCaseService();

	public CPBulletinBoardService getBulletinBoardService();

	public void setUserService(UserService userService);

	public void setCaseService(CaseService caseService);

	public ActorService getActorService();

	public void setActorService(ActorService actorService);

	public EnterpriseEvaluationService getEnterpriseEvaluationService();

	public void setEnterpriseEvaluationService(
			EnterpriseEvaluationService enterpriseEvaluationService);

	public NetworkEvaluationService getNetworkEvaluationService();

	public void setNetworkEvaluationService(
			NetworkEvaluationService networkEvaluationService);

	public void setProductMaterialSupportingServices_Service(
			ProductMaterialSupportingServices_Service pmsService);

	public ProductMaterialSupportingServices_Service getProductMaterialSupportingServices_Service();

	public void setTechnologyService(TechnologyService technologyService);

	public TechnologyService getTechnologyService();

	public void setMachineService(MachineryType_Service machineService);

	public MachineryType_Service getMachineService();

	public void setManufacturingProcessService(
			ManufacturingProcessType_Service manufacturingProcessService);

	public Tree retrieveTree(ScopeController scopeC);

	public Tree retrieveTree(ScopeController scopeController,
			List<Connectable> conns, int showTreeDepth,
			boolean showassociations, boolean showoccurrences);

	public void setOrganisationService(IOrganization s);

	public IOrganization setOrganisationService();

	public void setCmInstancesService(
			ICMInstancesManagement_Service cmInstancesService);

	public ICMInstancesManagement_Service getCmInstancesService();

	public void setCmManagementService(
			ICMCompetencesManagement_Service cmService);

	public ICMCompetencesManagement_Service getCmManagementService();

	public StringBuffer getDebugMsg();

	public void setDebugMsg(StringBuffer msg);
}
