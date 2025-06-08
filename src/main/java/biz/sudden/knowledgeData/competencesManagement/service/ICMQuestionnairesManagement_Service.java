package biz.sudden.knowledgeData.competencesManagement.service;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireRepository;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;

public interface ICMQuestionnairesManagement_Service extends
		ICMServiceCategoryBaseClass {

	/*
	 * Questionnaire Entities - Access to the entities should be done via the
	 * service, not the repository
	 */

	public long addQuestionnaire(Questionnaire questionnaire);

	public List<Questionnaire> retrieveAllQuestionnaires();

	public IOrganizationRepository getOrganizationRepository();

	public Questionnaire retrieveQuestionnaireById(long id);

	public IQuestionnaireRepository getQuestionnaireRepository();

	public List<Questionnaire> retrieveQuestionnairesByCategoryId(
			long categoryId);

	public void removeAllQuestionnaires();

	public void setCmCompetencesManagement_Service(
			ICMCompetencesManagement_Service cmCompetencesManagement_Service);

}
