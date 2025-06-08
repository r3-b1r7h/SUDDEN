package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleCompetence;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleQuestionnaire;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleQuestionnaireRepository;

public class RoleQuestionnaireRepositoryImpl extends
		GenericRepositoryImpl<RoleQuestionnaire, Long> implements
		IRoleQuestionnaireRepository {

	public RoleQuestionnaireRepositoryImpl() {
		super(RoleQuestionnaire.class);
	}

	public RoleQuestionnaireRepositoryImpl(Class<RoleCompetence> type) {
		super(RoleQuestionnaire.class);
	}

	@Override
	public long addRoleQuestionnaire(RoleQuestionnaire roleQuestionnaire) {
		long id = create(roleQuestionnaire);
		roleQuestionnaire.setId(id);
		System.out.println("Adding Role Questionnaire to repository...");
		System.out.println(roleQuestionnaire.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleQuestionnaire> getAllRoleQuestionnaires() {
		return getHibernateTemplate().loadAll(RoleQuestionnaire.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleQuestionnaire> getRoleQuestionnairesByQuestionnaireId(
			long questionnaireId) {
		return getHibernateTemplate().find(
				"from RoleQuestionnaire as role where questionnaire.id = ?",
				questionnaireId);
	}

	@Override
	public void removeAllRoleQuestionnaires() {
		List<RoleQuestionnaire> roleQuestionnaire = getAllRoleQuestionnaires();
		getHibernateTemplate().deleteAll(roleQuestionnaire);
	}

}
