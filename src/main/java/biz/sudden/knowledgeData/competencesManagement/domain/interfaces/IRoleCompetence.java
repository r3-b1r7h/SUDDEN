package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleDimension;

public interface IRoleCompetence extends ICMRoleBaseClass {

	public void addRoleDimension(RoleDimension roleDimension);

	public Competence getCompetence();

	public List<RoleDimension> getRoleDimensions();

	public void setCompetence(Competence competence);

	public void setRoleDimensions(List<RoleDimension> roleDimensions);

}
