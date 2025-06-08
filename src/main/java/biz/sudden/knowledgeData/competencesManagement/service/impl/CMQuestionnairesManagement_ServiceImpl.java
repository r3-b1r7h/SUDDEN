package biz.sudden.knowledgeData.competencesManagement.service.impl;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.Category;
import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;
import biz.sudden.knowledgeData.competencesManagement.repository.ICategoryRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireRepository;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMQuestionnairesManagement_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;

public class CMQuestionnairesManagement_ServiceImpl extends
		CMServiceCategoryBaseClassImpl implements
		ICMQuestionnairesManagement_Service {

	private ICMCompetencesManagement_Service cmCompetencesManagement_Service;
	private IOrganizationRepository organizationRepository;
	private IQuestionnaireRepository questionnaireRepository;

	@Override
	public long addCategory(Category category) {
		return cmCompetencesManagement_Service.getCategoryRepository()
				.addCategory(category);
	}

	@Override
	public long addQuestionnaire(Questionnaire questionnaire) {
		return questionnaireRepository.addQuestionnaire(questionnaire);
	}

	@Override
	public List<Category> retrieveAllCategories() {
		return cmCompetencesManagement_Service.getCategoryRepository()
				.getAllCategories();
	}

	@Override
	public List<Questionnaire> retrieveAllQuestionnaires() {
		return questionnaireRepository.getAllQuestionnaires();
	}

	@Override
	public List<Category> retrieveCategoriesByParentId(long parentCategoryId,
			int type) {
		return cmCompetencesManagement_Service.getCategoryRepository()
				.getCategoriesByParentId(parentCategoryId, type);
	}

	@Override
	public List<Category> retrieveCategoriesByType(int type) {
		return cmCompetencesManagement_Service.getCategoryRepository()
				.getCategoriesByType(type);
	}

	@Override
	public ICategoryRepository getCategoryRepository() {
		return cmCompetencesManagement_Service.getCategoryRepository();
	}

	public ICMCompetencesManagement_Service getCmCompetencesManagement_Service() {
		return cmCompetencesManagement_Service;
	}

	@Override
	public IOrganizationRepository getOrganizationRepository() {
		return organizationRepository;
	}

	@Override
	public Questionnaire retrieveQuestionnaireById(long id) {
		return questionnaireRepository.getQuestionnairesById(id).get(0);
	}

	@Override
	public IQuestionnaireRepository getQuestionnaireRepository() {
		return questionnaireRepository;
	}

	@Override
	public List<Questionnaire> retrieveQuestionnairesByCategoryId(
			long categoryId) {
		return questionnaireRepository
				.getQuestionnairesByCategoryId(categoryId);
	}

	@Override
	public void removeAllCategories() {
		cmCompetencesManagement_Service.getCategoryRepository()
				.removeAllCategories();
	}

	@Override
	public void removeAllQuestionnaires() {
		questionnaireRepository.removeAllQuestionnaires();
	}

	public void setCmCompetencesManagement_Service(
			ICMCompetencesManagement_Service cmCompetencesManagement_Service) {
		this.cmCompetencesManagement_Service = cmCompetencesManagement_Service;
	}

	public void setOrganizationRepository(
			IOrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	public void setQuestionnaireRepository(
			IQuestionnaireRepository questionnaireRepository) {
		this.questionnaireRepository = questionnaireRepository;
	}

}
