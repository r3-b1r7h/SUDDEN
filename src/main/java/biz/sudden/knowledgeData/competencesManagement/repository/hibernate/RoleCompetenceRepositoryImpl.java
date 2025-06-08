package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleCompetence;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleCompetenceRepository;

public class RoleCompetenceRepositoryImpl extends
		GenericRepositoryImpl<RoleCompetence, Long> implements
		IRoleCompetenceRepository {

	public RoleCompetenceRepositoryImpl() {
		super(RoleCompetence.class);
	}

	public RoleCompetenceRepositoryImpl(Class<RoleCompetence> type) {
		super(RoleCompetence.class);
	}

	@Override
	public long addRoleCompetence(RoleCompetence roleCompetence) {
		long id = create(roleCompetence);
		roleCompetence.setId(id);
		System.out.println("Adding Role Competence to repository...");
		System.out.println(roleCompetence.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleCompetence> getAllRoleCompetences() {
		return getHibernateTemplate().loadAll(RoleCompetence.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleCompetence> getRoleCompetencesByCompetenceId(
			long competenceId) {
		return getHibernateTemplate().find(
				"from RoleCompetence as role where role.competence.id = ?",
				competenceId);
	}

	@Override
	public void removeAllRoleCompetences() {
		List<RoleCompetence> roleCompetences = getAllRoleCompetences();
		getHibernateTemplate().deleteAll(roleCompetences);
	}

}
