package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import biz.sudden.knowledgeData.competencesManagement.domain.RoleQuestionnaire;

public interface IRole extends ICMQuestionnaireEntity {

	public IRole clone();

	public RoleQuestionnaire getRoleQuestionnaire();

	public void setRoleQuestionnaire(RoleQuestionnaire roleQuestionnaire);

	public String toString();

}
