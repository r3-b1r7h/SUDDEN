package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;

public interface IQuestionnaireRepository {

	public long addQuestionnaire(Questionnaire questionnaire);

	public List<Questionnaire> getAllQuestionnaires();

	public List<Questionnaire> getQuestionnairesByCategoryId(long categoryId);

	public List<Questionnaire> getQuestionnairesById(long id);

	public void removeAllQuestionnaires();

}
