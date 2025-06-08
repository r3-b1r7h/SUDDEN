package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.QuestionnaireInstance;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireInstanceRepository;

public class QuestionnaireInstanceRepositoryImpl extends
		GenericRepositoryImpl<QuestionnaireInstance, Long> implements
		IQuestionnaireInstanceRepository {

	public QuestionnaireInstanceRepositoryImpl() {
		super(QuestionnaireInstance.class);
	}

	public QuestionnaireInstanceRepositoryImpl(Class<QuestionnaireInstance> type) {
		super(QuestionnaireInstance.class);
	}

	@Override
	public long addQuestionnaireInstance(
			QuestionnaireInstance questionnaireInstance) {
		long id = create(questionnaireInstance);
		questionnaireInstance.setId(id);
		System.out.println("Adding Questionnaire Instance to repository...");
		// System.out.println(questionnaireInstance.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionnaireInstance> getAllQuestionnaireInstances() {
		return getHibernateTemplate().loadAll(QuestionnaireInstance.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionnaireInstance> getQuestionnaireInstancesByOrganizationId(
			long organizationId) {
		return getHibernateTemplate()
				.find(
						"from QuestionnaireInstance as questionnaire where questionnaire.organization.id = ?",
						organizationId);
	}

	@Override
	public void removeAllQuestionnaireInstances() {
		List<QuestionnaireInstance> questionnaireInstances = getAllQuestionnaireInstances();
		getHibernateTemplate().deleteAll(questionnaireInstances);
	}

}