package biz.sudden.knowledgeData.competencesManagement.service.impl;

import java.util.List;

import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.knowledgeData.competencesManagement.domain.CVIInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Category;
import biz.sudden.knowledgeData.competencesManagement.domain.CompetenceInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.DimensionInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.QuestionnaireInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.TickInstance;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICategoryRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMInstancesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMQuestionnairesManagement_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;

public class CMInstancesManagement_ServiceImpl extends CMServiceBaseClassImpl
		implements ICMInstancesManagement_Service {

	// Beans
	private ICMCompetencesManagement_Service cmCompetencesManagement_Service;
	private ICMQuestionnairesManagement_Service cmQuestionnaireManagement_Service;

	private EnterpriseEvaluationService enterpriseEvalService;
	private ProductMaterialSupportingServices_Service smService;

	// Repositories
	private ICompetenceInstanceRepository competenceInstanceRepository;
	private ICVIInstanceRepository cviInstanceRepository;
	private IDimensionInstanceRepository dimensionInstanceRepository;
	private IQuestionnaireInstanceRepository questionnaireInstanceRepository;
	private ITickInstanceRepository tickInstanceRepository;

	@Override
	public long addCategory(Category category) {
		return 0;
	}

	@Override
	public long addCompetenceInstance(CompetenceInstance competenceInstance) {
		return competenceInstanceRepository
				.addCompetenceInstance(competenceInstance);
	}

	@Override
	public long addCVIInstance(CVIInstance cviInstance) {
		return cviInstanceRepository.addCVIInstance(cviInstance);
	}

	@Override
	public long addDimensionInstance(DimensionInstance dimensionInstance) {

		// Note: add an values for Georg's algorithm
		enterpriseEvalService.associateOccurence(dimensionInstance.getValue(),
				dimensionInstance.getDimension(), "Double", dimensionInstance
						.getOrganization().getName());

		return dimensionInstanceRepository
				.addDimensionInstance(dimensionInstance);
	}

	@Override
	public long addQuestionnaireInstance(
			QuestionnaireInstance questionnaireInstance) {
		return questionnaireInstanceRepository
				.addQuestionnaireInstance(questionnaireInstance);
	}

	@Override
	public long addTickInstance(TickInstance tickInstance) {
		return tickInstanceRepository.addTickInstance(tickInstance);
	}

	@Override
	public List<Category> retrieveAllCategories() {
		return null;
	}

	@Override
	public List<CompetenceInstance> retrieveAllCompetenceInstances() {
		return competenceInstanceRepository.getAllCompetenceInstances();
	}

	@Override
	public List<CompetenceInstance> retrieveAllCompetenceInstancesByOrganization(
			long organizationId) {
		return competenceInstanceRepository
				.getCompetenceInstancesByOrganizationId(organizationId);
	}

	@Override
	public List<CVIInstance> retrieveAllCVIInstances() {
		return cviInstanceRepository.getAllCVIInstances();
	}

	@Override
	public List<CVIInstance> retrieveAllCVIInstancesByOrganization(
			long organizationId) {
		return cviInstanceRepository
				.getCVIInstancesByOrganizationId(organizationId);
	}

	@Override
	public List<DimensionInstance> retrieveAllDimensionInstances() {
		return dimensionInstanceRepository.getAllDimensionInstances();
	}

	@Override
	public List<DimensionInstance> retrieveAllDimensionInstancesByOrganization(
			long organizationId) {
		return dimensionInstanceRepository
				.getDimensionInstancesByOrganizationId(organizationId);
	}

	@Override
	public List<QuestionnaireInstance> retrieveAllQuestionnaireInstances() {
		return questionnaireInstanceRepository.getAllQuestionnaireInstances();
	}

	@Override
	public List<QuestionnaireInstance> retrieveAllQuestionnaireInstancesByOrganization(
			long organizationId) {
		return questionnaireInstanceRepository
				.getQuestionnaireInstancesByOrganizationId(organizationId);
	}

	@Override
	public List<TickInstance> retrieveAllTickInstances() {
		return tickInstanceRepository.getAllTickInstances();
	}

	@Override
	public List<TickInstance> retrieveAllTickInstancesByOrganization(
			long organizationId) {
		return tickInstanceRepository
				.getTickInstancesByOrganizationId(organizationId);
	}

	@Override
	public List<Category> retrieveCategoriesByParentId(long parentCategoryId,
			int type) {
		return null;
	}

	@Override
	public List<Category> retrieveCategoriesByType(int type) {
		return null;
	}

	@Override
	public ICategoryRepository getCategoryRepository() {
		return null;
	}

	@Override
	public ICMCompetencesManagement_Service getCmCompetencesManagement_Service() {
		return cmCompetencesManagement_Service;
	}

	@Override
	public ICMQuestionnairesManagement_Service getCmQuestionnaireManagement_Service() {
		return cmQuestionnaireManagement_Service;
	}

	@Override
	public CompetenceInstance retrieveCompetenceInstanceByOrganization(
			long organizationId, long competenceId) {
		return competenceInstanceRepository
				.getCompetenceInstanceByOrganization(organizationId,
						competenceId);
	}

	@Override
	public ICompetenceInstanceRepository getCompetenceInstanceRepository() {
		return competenceInstanceRepository;
	}

	@Override
	public ICVIInstanceRepository getCviInstanceRepository() {
		return cviInstanceRepository;
	}

	@Override
	public DimensionInstance retrieveDimensionInstanceByName(
			long organizationId, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DimensionInstance retrieveDimensionInstanceByOrganization(
			long organizationId, long dimensionId) {
		return dimensionInstanceRepository.getDimensionInstanceByOrganization(
				organizationId, dimensionId);
	}

	@Override
	public IDimensionInstanceRepository getDimensionInstanceRepository() {
		return dimensionInstanceRepository;
	}

	@Override
	public IQuestionnaireInstanceRepository getQuestionnaireInstanceRepository() {
		return questionnaireInstanceRepository;
	}

	@Override
	public ITickInstanceRepository getTickInstanceRepository() {
		return tickInstanceRepository;
	}

	@Override
	public void removeAllCategories() {

	}

	@Override
	public void removeAllInstances() {
		questionnaireInstanceRepository.removeAllQuestionnaireInstances();
		competenceInstanceRepository.removeAllCompetenceInstances();
		dimensionInstanceRepository.removeAllDimensionInstances();
		cviInstanceRepository.removeAllCVIInstances();
		tickInstanceRepository.removeAllTickInstances();
	}

	@Override
	public void setCmCompetencesManagement_Service(
			ICMCompetencesManagement_Service cmCompetencesManagement_Service) {
		this.cmCompetencesManagement_Service = cmCompetencesManagement_Service;
	}

	public void setCmQuestionnaireManagement_Service(
			ICMQuestionnairesManagement_Service cmQuestionnaireManagement_Service) {
		this.cmQuestionnaireManagement_Service = cmQuestionnaireManagement_Service;
	}

	@Override
	public void setEnterpriseEvalService(
			EnterpriseEvaluationService enterpriseEvalService) {
		this.enterpriseEvalService = enterpriseEvalService;
	}

	@Override
	public EnterpriseEvaluationService getEnterpriseEvalService() {
		return this.enterpriseEvalService;
	}

	@Override
	public void setSmService(ProductMaterialSupportingServices_Service smService) {
		this.smService = smService;
	}

	@Override
	public ProductMaterialSupportingServices_Service getSmService() {
		return this.smService;
	}

	public void setCompetenceInstanceRepository(
			ICompetenceInstanceRepository competenceInstanceRepository) {
		this.competenceInstanceRepository = competenceInstanceRepository;
	}

	public void setCviInstanceRepository(
			ICVIInstanceRepository cviInstanceRepository) {
		this.cviInstanceRepository = cviInstanceRepository;
	}

	public void setDimensionInstanceRepository(
			IDimensionInstanceRepository dimensionInstanceRepository) {
		this.dimensionInstanceRepository = dimensionInstanceRepository;
	}

	public void setQuestionnaireInstanceRepository(
			IQuestionnaireInstanceRepository questionnaireInstanceRepository) {
		this.questionnaireInstanceRepository = questionnaireInstanceRepository;
	}

	public void setTickInstanceRepository(
			ITickInstanceRepository tickInstanceRepository) {
		this.tickInstanceRepository = tickInstanceRepository;
	}

}
