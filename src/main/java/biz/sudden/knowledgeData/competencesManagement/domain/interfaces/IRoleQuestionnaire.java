package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleCompetence;

public interface IRoleQuestionnaire extends ICMRoleBaseClass {

	public void addRoleCompetence(RoleCompetence roleCompetence);

	public Questionnaire getQuestionnaire();

	public List<RoleCompetence> getRoleCompetences();

	public void setQuestionnaire(Questionnaire questionnaire);

	public void setRoleCompetences(List<RoleCompetence> roleCompetences);

}
