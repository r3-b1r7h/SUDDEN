package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.Role;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleRepository;

public class RoleRepositoryImpl extends GenericRepositoryImpl<Role, Long>
		implements IRoleRepository {

	public RoleRepositoryImpl() {
		super(Role.class);
	}

	public RoleRepositoryImpl(Class<Role> type) {
		super(Role.class);
	}

	@Override
	public long addRole(Role role) {
		long id = create(role);
		role.setId(id);
		System.out.println("Adding Role to repository...");
		System.out.println(role.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles() {
		return getHibernateTemplate().loadAll(Role.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesByCategoryId(long categoryId) {
		return getHibernateTemplate().find(
				"from Role as role where role.categoryId = ?", categoryId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesById(long id) {
		return getHibernateTemplate().find(
				"from Role as role where role.id = ?", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesByQuestionnaireId(long questionnaireId) {
		return getHibernateTemplate()
				.find(
						"from Role as role where role.roleQuestionnaire.questionnaire.id = ?",
						questionnaireId);
	}

	@Override
	public void removeAllRoles() {
		List<Role> role = getAllRoles();
		getHibernateTemplate().deleteAll(role);
	}

}
