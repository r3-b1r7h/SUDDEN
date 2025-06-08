package biz.sudden.knowledgeData.competencesManagement.repository.hibernate.testDBData;

import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICategoryRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleCompetenceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleDimensionRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleQuestionnaireRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickRepository;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;

public class TestDBData {

	// Repositories
	private IQuestionnaireRepository questionnaireRepository;
	private ICompetenceRepository competenceRepository;
	private IDimensionRepository dimensionRepository;
	private ICVIRepository cviRepository;
	private ITickRepository tickRepository;
	private ICategoryRepository categoryRepository;
	private IRoleDimensionRepository roleDimensionRepository;
	private IRoleCompetenceRepository roleCompetenceRepository;
	private IRoleQuestionnaireRepository roleQuestionnaireRepository;
	private IRoleRepository roleRepository;
	private ITickInstanceRepository tickInstanceRepository;
	private ICVIInstanceRepository cviInstanceRepository;
	private IDimensionInstanceRepository dimensionInstanceRepository;
	private ICompetenceInstanceRepository competenceInstanceRepository;
	private IQuestionnaireInstanceRepository questionnaireInstanceRepository;
	private IOrganizationRepository organizationRepository;

	// Access to other entities
	private TestDBDataCategories categories = new TestDBDataCategories();
	private TestDBDataCVIs cvis = new TestDBDataCVIs();
	private TestDBDataDimensions dimensions = new TestDBDataDimensions();
	private TestDBDataCompetences competences = new TestDBDataCompetences();
	private TestDBDataQuestionnaires questionnaires = new TestDBDataQuestionnaires();
	private TestDBDataRoleDimensions roleDimensions = new TestDBDataRoleDimensions();
	private TestDBDataRoleCompetences roleCompetences = new TestDBDataRoleCompetences();
	private TestDBDataRoles roles = new TestDBDataRoles();
	private TestDBDataInstances instances = new TestDBDataInstances();
	private TestDBDataRealSuppliers instancesRealSuppliers = new TestDBDataRealSuppliers();

	private ProductMaterialSupportingServices_Service smService;
	private EnterpriseEvaluationService enterpriseEvalService;

	public TestDBDataCategories getCategories() {
		return categories;
	}

	public ICategoryRepository getCategoryRepository() {
		return categoryRepository;
	}

	public ICompetenceInstanceRepository getCompetenceInstanceRepository() {
		return competenceInstanceRepository;
	}

	public ICompetenceRepository getCompetenceRepository() {
		return competenceRepository;
	}

	public TestDBDataCompetences getCompetences() {
		return competences;
	}

	public ICVIInstanceRepository getCviInstanceRepository() {
		return cviInstanceRepository;
	}

	public ICVIRepository getCviRepository() {
		return cviRepository;
	}

	public TestDBDataCVIs getCvis() {
		return cvis;
	}

	public IDimensionInstanceRepository getDimensionInstanceRepository() {
		return dimensionInstanceRepository;
	}

	public IDimensionRepository getDimensionRepository() {
		return dimensionRepository;
	}

	public TestDBDataDimensions getDimensions() {
		return dimensions;
	}

	public IOrganizationRepository getOrganizationRepository() {
		return organizationRepository;
	}

	public IQuestionnaireInstanceRepository getQuestionnaireInstanceRepository() {
		return questionnaireInstanceRepository;
	}

	public IQuestionnaireRepository getQuestionnaireRepository() {
		return questionnaireRepository;
	}

	public TestDBDataQuestionnaires getQuestionnaires() {
		return questionnaires;
	}

	public IRoleCompetenceRepository getRoleCompetenceRepository() {
		return roleCompetenceRepository;
	}

	public TestDBDataRoleCompetences getRoleCompetences() {
		return roleCompetences;
	}

	public IRoleDimensionRepository getRoleDimensionRepository() {
		return roleDimensionRepository;
	}

	public TestDBDataRoleDimensions getRoleDimensions() {
		return roleDimensions;
	}

	public IRoleQuestionnaireRepository getRoleQuestionnaireRepository() {
		return roleQuestionnaireRepository;
	}

	public IRoleRepository getRoleRepository() {
		return roleRepository;
	}

	public TestDBDataRoles getRoles() {
		return roles;
	}

	public ITickInstanceRepository getTickInstanceRepository() {
		return tickInstanceRepository;
	}

	public ITickRepository getTickRepository() {
		return tickRepository;
	}

	public void insertDBTestData() {
		categories.setCategoryRepository(categoryRepository);
		categories.insertDBTestDataDBCategories();

		cvis.setTickRepository(tickRepository);
		cvis.setCviRepository(cviRepository);
		cvis.setCategories(categories);
		cvis.insertDBTestDataCVIs();

		dimensions.setDimensionRepository(dimensionRepository);
		dimensions.setCategories(categories);
		dimensions.setCvis(cvis);
		dimensions.insertDBTestDataDimensions();

		competences.setEnterpriseEvalService(this.enterpriseEvalService);
		competences.setCompetenceRepository(competenceRepository);
		competences.setCategories(categories);
		competences.setDimensions(dimensions);
		competences.insertDBTestDataCompetences();

		questionnaires.setQuestionnaireRepository(questionnaireRepository);
		questionnaires.setCategories(categories);
		questionnaires.setCompetences(competences);
		questionnaires.insertDBQuestionnairesTestData();

		roleDimensions.setRoleDimensionRepository(roleDimensionRepository);
		roleDimensions.setDimensions(dimensions);
		roleDimensions.insertDBTestDataRoleDimensions();

		roleCompetences.setRoleCompetenceRepository(roleCompetenceRepository);
		roleCompetences.setCompetences(competences);
		roleCompetences.setRoleDimensions(roleDimensions);
		roleCompetences.insertDBTestDataRoleCompetences();

		roles.setRoleQuestionnaireRepository(roleQuestionnaireRepository);
		roles.setRoleRepository(roleRepository);
		roles.setCategories(categories);
		roles.setQuestionnaires(questionnaires);
		roles.setRoleCompetences(roleCompetences);
		roles.insertDBRoleTestData();
	}

	public void insertDBDataRandomSuppliers() {
		instances.setTickInstanceRepository(tickInstanceRepository);
		instances.setTickRepository(tickRepository);
		instances.setCviInstanceRepository(cviInstanceRepository);
		instances.setCviRepository(cviRepository);
		instances.setDimensionInstanceRepository(dimensionInstanceRepository);
		instances.setDimensionRepository(dimensionRepository);
		instances.setCompetenceInstanceRepository(competenceInstanceRepository);
		instances.setCompetenceRepository(competenceRepository);
		instances
				.setQuestionnaireInstanceRepository(questionnaireInstanceRepository);
		instances.setQuestionnaireRepository(questionnaireRepository);
		instances.setOrganizationRepository(organizationRepository);
		instances.setEnterpriseEvalService(enterpriseEvalService);
		instances.insertDBTestDataInstances();
	}

	public void insertDBDataRealSuppliers() {
		instancesRealSuppliers
				.setTickInstanceRepository(tickInstanceRepository);
		instancesRealSuppliers.setTickRepository(tickRepository);
		instancesRealSuppliers.setCviInstanceRepository(cviInstanceRepository);
		instancesRealSuppliers.setCviRepository(cviRepository);
		instancesRealSuppliers
				.setDimensionInstanceRepository(dimensionInstanceRepository);
		instancesRealSuppliers.setDimensionRepository(dimensionRepository);
		instancesRealSuppliers
				.setCompetenceInstanceRepository(competenceInstanceRepository);
		instancesRealSuppliers.setCompetenceRepository(competenceRepository);
		instancesRealSuppliers
				.setQuestionnaireInstanceRepository(questionnaireInstanceRepository);
		instancesRealSuppliers
				.setQuestionnaireRepository(questionnaireRepository);
		instancesRealSuppliers
				.setOrganizationRepository(organizationRepository);
		instancesRealSuppliers.setSmService(this.smService);
		instancesRealSuppliers.setEnterpriseEvalService(enterpriseEvalService);
		instancesRealSuppliers.insertDBTestDataInstances();
	}

	public void setCategories(TestDBDataCategories categories) {
		this.categories = categories;
	}

	public void setCategoryRepository(ICategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public void setCompetenceInstanceRepository(
			ICompetenceInstanceRepository competenceInstanceRepository) {
		this.competenceInstanceRepository = competenceInstanceRepository;
	}

	public void setCompetenceRepository(
			ICompetenceRepository competenceRepository) {
		this.competenceRepository = competenceRepository;
	}

	public void setCompetences(TestDBDataCompetences competences) {
		this.competences = competences;
	}

	public void setCviInstanceRepository(
			ICVIInstanceRepository cviInstanceRepository) {
		this.cviInstanceRepository = cviInstanceRepository;
	}

	public void setCviRepository(ICVIRepository cviRepository) {
		this.cviRepository = cviRepository;
	}

	public void setCvis(TestDBDataCVIs cvis) {
		this.cvis = cvis;
	}

	public void setDimensionInstanceRepository(
			IDimensionInstanceRepository dimensionInstanceRepository) {
		this.dimensionInstanceRepository = dimensionInstanceRepository;
	}

	public void setDimensionRepository(IDimensionRepository dimensionRepository) {
		this.dimensionRepository = dimensionRepository;
	}

	public void setDimensions(TestDBDataDimensions dimensions) {
		this.dimensions = dimensions;
	}

	public void setOrganizationRepository(
			IOrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	public void setQuestionnaireInstanceRepository(
			IQuestionnaireInstanceRepository questionnaireInstanceRepository) {
		this.questionnaireInstanceRepository = questionnaireInstanceRepository;
	}

	public void setQuestionnaireRepository(
			IQuestionnaireRepository questionnaireRepository) {
		this.questionnaireRepository = questionnaireRepository;
	}

	public void setQuestionnaires(TestDBDataQuestionnaires questionnaires) {
		this.questionnaires = questionnaires;
	}

	public void setRoleCompetenceRepository(
			IRoleCompetenceRepository roleCompetenceRepository) {
		this.roleCompetenceRepository = roleCompetenceRepository;
	}

	public void setRoleCompetences(TestDBDataRoleCompetences roleCompetences) {
		this.roleCompetences = roleCompetences;
	}

	public void setRoleDimensionRepository(
			IRoleDimensionRepository roleDimensionRepository) {
		this.roleDimensionRepository = roleDimensionRepository;
	}

	public void setRoleDimensions(TestDBDataRoleDimensions roleDimensions) {
		this.roleDimensions = roleDimensions;
	}

	public void setRoleQuestionnaireRepository(
			IRoleQuestionnaireRepository roleQuestionnaireRepository) {
		this.roleQuestionnaireRepository = roleQuestionnaireRepository;
	}

	public void setRoleRepository(IRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public void setRoles(TestDBDataRoles roles) {
		this.roles = roles;
	}

	public void setTickInstanceRepository(
			ITickInstanceRepository tickInstanceRepository) {
		this.tickInstanceRepository = tickInstanceRepository;
	}

	public void setTickRepository(ITickRepository tickRepository) {
		this.tickRepository = tickRepository;
	}

	public void setSmService(ProductMaterialSupportingServices_Service smService) {
		this.smService = smService;
	}

	public ProductMaterialSupportingServices_Service getSmService() {
		return this.smService;
	}

	public void setEnterpriseEvalService(EnterpriseEvaluationService service) {
		this.enterpriseEvalService = service;
	}

	public EnterpriseEvaluationService getEnterpriseEvalService() {
		return this.enterpriseEvalService;
	}

}
