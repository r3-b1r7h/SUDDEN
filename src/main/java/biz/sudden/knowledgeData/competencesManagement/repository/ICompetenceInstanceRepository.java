package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.CompetenceInstance;

public interface ICompetenceInstanceRepository {

	public long addCompetenceInstance(CompetenceInstance competenceInstance);

	public void updateCompetenceInstance(CompetenceInstance competenceInstance);

	public List<CompetenceInstance> getAllCompetenceInstances();

	public CompetenceInstance getCompetenceInstanceByOrganization(
			long organizationId, long competenceId);

	public List<CompetenceInstance> getCompetenceInstancesByOrganizationId(
			long organizationId);

	public void removeAllCompetenceInstances();

}
