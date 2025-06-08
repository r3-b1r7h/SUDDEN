package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.RoleQuestionnaire;

public interface IRoleQuestionnaireRepository {

	public long addRoleQuestionnaire(RoleQuestionnaire roleQuestionnaire);

	public List<RoleQuestionnaire> getAllRoleQuestionnaires();

	public List<RoleQuestionnaire> getRoleQuestionnairesByQuestionnaireId(
			long questionnaireId);

	public void removeAllRoleQuestionnaires();

}
