package biz.sudden.knowledgeData.competencesManagement.web.controller.impl;

import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.knowledgeData.competencesManagement.domain.Category;
import biz.sudden.knowledgeData.competencesManagement.repository.hibernate.testDBData.TestDBData;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMInstancesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMQuestionnairesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMRolesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.TabSetDefaultControllerImpl;

public class CMControllerImpl {

	protected Logger logger = Logger.getLogger(this.getClass());

	/* Attributes to control UI presentation */
	private TabSetDefaultControllerImpl categoryRepositoriesTabController;

	/* Controllers */
	private CMCompetenceControllerImpl cmCompetenceController;
	private CMCVIControllerImpl cmCVIController;
	private CMDimensionControllerImpl cmDimensionController;
	private CMInstancesControllerImpl cmInstanceController;
	private CMQuestionnaireControllerImpl cmQuestionnaireController;
	private CMRoleControllerImpl cmRoleController;

	/* Services */
	private ICMInstancesManagement_Service cmInstancesService;
	private ICMQuestionnairesManagement_Service cmQuestionnairesService;
	private ICMCompetencesManagement_Service cmService;
	private ICMRolesManagement_Service cmRolesService;

	// I introduced this attribute since when calling the insert test db stuff
	// it seems not to be initialized...
	private boolean initialized = false;

	/* DB Test Data Class */
	private TestDBData testDBData;

	public CMControllerImpl() {
		super();

		categoryRepositoriesTabController = new TabSetDefaultControllerImpl();
		categoryRepositoriesTabController.addTab("CVIs Categories",
				"cmCVICategoryRepository.jspx", true);
		categoryRepositoriesTabController.addTab("Dimensions Categories",
				"cmDimensionCategoryRepository.jspx", true);
		categoryRepositoriesTabController.addTab("Competences Categories",
				"cmCompetenceCategoryRepository.jspx", true);
		categoryRepositoriesTabController.addTab("Questionnaires Categories",
				"cmQuestionnaireCategoryRepository.jspx", true);
		categoryRepositoriesTabController.addTab("Roles Categories",
				"cmRoleCategoryRepository.jspx", true);
		testDBData = new TestDBData();
	}

	public List<Category> getAllCategories() {
		return cmService.retrieveAllCategories();
	}

	public TabSetDefaultControllerImpl getCategoryRepositoriesTabController() {
		return categoryRepositoriesTabController;
	}

	public CMCompetenceControllerImpl getCmCompetenceController() {
		return cmCompetenceController;
	}

	public CMCVIControllerImpl getCmCVIController() {
		return cmCVIController;
	}

	public CMDimensionControllerImpl getCmDimensionController() {
		return cmDimensionController;
	}

	public CMInstancesControllerImpl getCmInstanceController() {
		return cmInstanceController;
	}

	public CMInstancesControllerImpl getCmInstancesController() {
		return cmInstanceController;
	}

	public ICMInstancesManagement_Service getCmInstancesService() {
		return cmInstancesService;
	}

	public CMQuestionnaireControllerImpl getCmQuestionnaireController() {
		return cmQuestionnaireController;
	}

	public ICMQuestionnairesManagement_Service getCmQuestionnairesService() {
		return cmQuestionnairesService;
	}

	public CMRoleControllerImpl getCmRoleController() {
		return cmRoleController;
	}

	public ICMRolesManagement_Service getCmRolesService() {
		return cmRolesService;
	}

	public ICMCompetencesManagement_Service getCmService() {
		return cmService;
	}

	// FIXME: Performance: This method has really *heavy* performance impact
	public void init() {
		initialized = true;
		long time = System.currentTimeMillis();
		// Initialize Services
		cmQuestionnairesService.setCmCompetencesManagement_Service(cmService);
		cmRolesService.setCmCompetencesManagement_Service(cmService);
		cmInstancesService.setCmCompetencesManagement_Service(cmService);
		cmInstancesService
				.setCmQuestionnaireManagement_Service(cmQuestionnairesService);
		logger.warn("init Service: " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		// Initialize CVI Controller
		cmCVIController.setService(cmService);
		cmCVIController.init();
		logger.warn("init CVI controller: "
				+ (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		// Initialize Dimension Controller
		cmDimensionController.setService(cmService);
		cmDimensionController.setCmCVIController(cmCVIController);
		cmDimensionController.init();
		logger.warn("init Dimension controller: "
				+ (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		// Initialize Competence Controller
		cmCompetenceController.setService(cmService);
		cmCompetenceController.setCmDimensionController(cmDimensionController);
		cmCompetenceController.init();
		logger.warn("init Competence controller: "
				+ (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		// Initialize Questionnaire Controller
		cmQuestionnaireController.setService(cmQuestionnairesService);
		cmQuestionnaireController
				.setCmCompetenceController(cmCompetenceController);
		cmQuestionnaireController.init();
		logger.warn("init Questionnaire controller: "
				+ (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		// Initialize Instances Controller
		cmInstanceController.setService(cmInstancesService);
		cmInstanceController.setCmCompetenceController(cmCompetenceController);
		cmInstanceController
				.setCmQuestionnaireController(cmQuestionnaireController);
		cmInstanceController.setCmRoleController(cmRoleController);
		cmInstanceController.setEnterpriseEvalService(cmInstancesService
				.getEnterpriseEvalService());
		cmInstanceController.init();
		logger.warn("init CVinstancesI controller: "
				+ (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		// Initialize Roles Controller
		cmRoleController.setService(cmRolesService);
		cmRoleController
				.setCmQuestionnaireController(cmQuestionnaireController);
		cmRoleController.init();
		logger.warn("init Roles controller: "
				+ (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

		// Initialize Test DB Data Class
		testDBData.setCategoryRepository(cmService.getCategoryRepository());
		testDBData.setTickRepository(cmService.getTickRepository());
		testDBData.setCviRepository(cmService.getCviRepository());
		testDBData.setDimensionRepository(cmService.getDimensionRepository());
		testDBData.setCompetenceRepository(cmService.getCompetenceRepository());
		testDBData.setQuestionnaireRepository(cmQuestionnairesService
				.getQuestionnaireRepository());
		testDBData.setRoleDimensionRepository(cmRolesService
				.getRoleDimensionRepository());
		testDBData.setRoleCompetenceRepository(cmRolesService
				.getRoleCompetenceRepository());
		testDBData.setRoleQuestionnaireRepository(cmRolesService
				.getRoleQuestionnaireRepository());
		testDBData.setRoleRepository(cmRolesService.getRoleRepository());
		testDBData.setTickInstanceRepository(cmInstancesService
				.getTickInstanceRepository());
		testDBData.setCviInstanceRepository(cmInstancesService
				.getCviInstanceRepository());
		testDBData.setDimensionInstanceRepository(cmInstancesService
				.getDimensionInstanceRepository());
		testDBData.setCompetenceInstanceRepository(cmInstancesService
				.getCompetenceInstanceRepository());
		testDBData.setQuestionnaireInstanceRepository(cmInstancesService
				.getQuestionnaireInstanceRepository());
		testDBData
				.setOrganizationRepository(((ICMQuestionnairesManagement_Service) cmQuestionnaireController
						.getService()).getOrganizationRepository());

		testDBData.setSmService(cmInstancesService.getSmService());
		testDBData.setEnterpriseEvalService(cmInstancesService
				.getEnterpriseEvalService());

		logger.warn("init Test DB controller: "
				+ (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();

	}

	public void insertDBDataRandomSuppliers() {
		testDBData.insertDBDataRandomSuppliers();
	}

	public void insertDBDataRealSuppliers() {
		testDBData.insertDBDataRealSuppliers();
	}

	public void insertDBTestData() {
		if (!initialized)
			this.init();
		testDBData.insertDBTestData();

		cmCVIController.init();
		cmDimensionController.init();
		cmCompetenceController.init();
		cmQuestionnaireController.init();
		cmInstanceController.init();
		cmRoleController.init();
	}

	public String navigateToCM() {
		return "competencesManagement";
	}

	public void removeAll() {
		cmInstancesService.removeAllInstances();
		cmService.removeAllCategories();
		cmRolesService.removeAllRoles();
		cmQuestionnairesService.removeAllQuestionnaires();
		cmService.removeAllCompetences();
		cmService.removeAllDimensions();
		cmService.removeAllCVIs();
		cmService.removeAllTicks();

		cmCVIController.init();
		cmDimensionController.init();
		cmCompetenceController.init();
		cmQuestionnaireController.init();
		cmInstanceController.init();
		cmRoleController.init();
	}

	public void removeCategories() {
		cmService.removeAllCategories();
		cmCVIController.init();
		cmDimensionController.init();
		cmCompetenceController.init();
		cmQuestionnaireController.init();
		cmRoleController.init();
	}

	public void removeCompetences() {
		cmService.removeAllCompetences();
		cmCompetenceController.init();
	}

	public void removeCVIs() {
		cmService.removeAllCVIs();
		cmCVIController.init();
	}

	public void removeDimensions() {
		cmService.removeAllDimensions();
		cmDimensionController.init();
	}

	public void removeQuestionnaires() {
		cmQuestionnairesService.removeAllQuestionnaires();
		cmQuestionnaireController.init();
	}

	public void setCategoryRepositoriesTabController(
			TabSetDefaultControllerImpl categoryRepositoriesTabController) {
		this.categoryRepositoriesTabController = categoryRepositoriesTabController;
	}

	public void setCmCompetenceController(
			CMCompetenceControllerImpl cmCompetenceController) {
		this.cmCompetenceController = cmCompetenceController;
	}

	public void setCmCVIController(CMCVIControllerImpl cmCVIController) {
		this.cmCVIController = cmCVIController;
	}

	public void setCmDimensionController(
			CMDimensionControllerImpl cmDimensionController) {
		this.cmDimensionController = cmDimensionController;
	}

	public void setCmInstanceController(
			CMInstancesControllerImpl cmInstancesController) {
		this.cmInstanceController = cmInstancesController;
	}

	public void setCmInstancesService(
			ICMInstancesManagement_Service cmInstancesService) {
		this.cmInstancesService = cmInstancesService;
	}

	public void setCmQuestionnaireController(
			CMQuestionnaireControllerImpl cmQuestionnaireController) {
		this.cmQuestionnaireController = cmQuestionnaireController;
	}

	public void setCmQuestionnairesService(
			ICMQuestionnairesManagement_Service cmQuestionnairesService) {
		this.cmQuestionnairesService = cmQuestionnairesService;
	}

	public void setCmRoleController(CMRoleControllerImpl cmRoleController) {
		this.cmRoleController = cmRoleController;
	}

	public void setCmRolesService(ICMRolesManagement_Service cmRolesService) {
		this.cmRolesService = cmRolesService;
	}

	public void setCmService(ICMCompetencesManagement_Service cmService) {
		this.cmService = cmService;
	}

}
