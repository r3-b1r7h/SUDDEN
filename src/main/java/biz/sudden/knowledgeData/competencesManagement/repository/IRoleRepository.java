package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.Role;

public interface IRoleRepository {

	public long addRole(Role role);

	public List<Role> getAllRoles();

	public List<Role> getRolesByCategoryId(long categoryId);

	public List<Role> getRolesById(long id);

	public List<Role> getRolesByQuestionnaireId(long questionnaireId);

	public void removeAllRoles();

}
