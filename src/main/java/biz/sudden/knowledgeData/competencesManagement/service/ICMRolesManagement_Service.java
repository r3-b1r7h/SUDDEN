package biz.sudden.knowledgeData.competencesManagement.service;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.QuestionnaireInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Role;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleCompetenceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleDimensionRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleQuestionnaireRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleRepository;

public interface ICMRolesManagement_Service extends ICMServiceCategoryBaseClass {

	/*
	 * Role Entities - Access to the entities should be done via the service,
	 * not the repository
	 */

	public long addRole(Role role);

	public void applyNonWeightedRole(QuestionnaireInstance questionnaireInstance);

	public void applyRole(Role role, QuestionnaireInstance questionnaireInstance);

	public List<Role> retrieveAllRoles();

	public Role retrieveRoleById(long id);

	public IRoleCompetenceRepository getRoleCompetenceRepository();

	public IRoleDimensionRepository getRoleDimensionRepository();

	public IRoleQuestionnaireRepository getRoleQuestionnaireRepository();

	public IRoleRepository getRoleRepository();

	public List<Role> retrieveRolesByCategoryId(long categoryId);

	public List<Role> retrieveRolesByQuestionnaireId(long questionnaireId);

	public void removeAllRoles();

	public void setCmCompetencesManagement_Service(
			ICMCompetencesManagement_Service cmCompetencesManagement_Service);

}
