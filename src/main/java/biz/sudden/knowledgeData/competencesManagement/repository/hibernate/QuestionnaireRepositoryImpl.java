package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireRepository;

public class QuestionnaireRepositoryImpl extends
		GenericRepositoryImpl<Questionnaire, Long> implements
		IQuestionnaireRepository {

	public QuestionnaireRepositoryImpl() {
		super(Questionnaire.class);
	}

	public QuestionnaireRepositoryImpl(Class<Questionnaire> type) {
		super(Questionnaire.class);
	}

	@Override
	public long addQuestionnaire(Questionnaire questionnaire) {
		long id = create(questionnaire);
		questionnaire.setId(id);
		System.out.println("Adding Questionnaire to repository...");
		System.out.println(questionnaire.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Questionnaire> getAllQuestionnaires() {
		return getHibernateTemplate().loadAll(Questionnaire.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Questionnaire> getQuestionnairesByCategoryId(long categoryId) {
		return getHibernateTemplate()
				.find(
						"from Questionnaire as questionnaire where questionnaire.categoryId = ?",
						categoryId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Questionnaire> getQuestionnairesById(long id) {
		return getHibernateTemplate()
				.find(
						"from Questionnaire as questionnaire where questionnaire.id = ?",
						id);
	}

	@Override
	public void removeAllQuestionnaires() {
		List<Questionnaire> questionnaire = getAllQuestionnaires();
		getHibernateTemplate().deleteAll(questionnaire);
	}

}
