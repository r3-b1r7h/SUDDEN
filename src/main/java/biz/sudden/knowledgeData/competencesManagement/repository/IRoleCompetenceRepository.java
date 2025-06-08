package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.RoleCompetence;

public interface IRoleCompetenceRepository {

	public long addRoleCompetence(RoleCompetence roleCompetence);

	public List<RoleCompetence> getAllRoleCompetences();

	public List<RoleCompetence> getRoleCompetencesByCompetenceId(
			long competenceId);

	public void removeAllRoleCompetences();

}
