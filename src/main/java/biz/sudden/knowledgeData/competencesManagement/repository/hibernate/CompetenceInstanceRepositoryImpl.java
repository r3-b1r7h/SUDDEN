package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.CompetenceInstance;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceInstanceRepository;

public class CompetenceInstanceRepositoryImpl extends
		GenericRepositoryImpl<CompetenceInstance, Long> implements
		ICompetenceInstanceRepository {

	public CompetenceInstanceRepositoryImpl() {
		super(CompetenceInstance.class);
	}

	public CompetenceInstanceRepositoryImpl(Class<CompetenceInstance> type) {
		super(CompetenceInstance.class);
	}

	@Override
	public long addCompetenceInstance(CompetenceInstance competenceInstance) {
		long id = create(competenceInstance);
		competenceInstance.setId(id);
		System.out.println("Adding Competence Instance to repository...");
		// System.out.println(competenceInstance.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompetenceInstance> getAllCompetenceInstances() {
		return getHibernateTemplate().loadAll(CompetenceInstance.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CompetenceInstance getCompetenceInstanceByOrganization(
			long organizationId, long competenceId) {
		List<CompetenceInstance> competenceInstances = getHibernateTemplate()
				.find(
						"from CompetenceInstance as competence where competence.organizationId = "
								+ organizationId + " and competence.id = "
								+ competenceId);
		if (competenceInstances.size() > 0) {
			return competenceInstances.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompetenceInstance> getCompetenceInstancesByOrganizationId(
			long organizationId) {
		return getHibernateTemplate()
				.find(
						"from CompetenceInstance as competence where competence.organizationId = ?",
						organizationId);
	}

	@Override
	public void removeAllCompetenceInstances() {
		List<CompetenceInstance> competenceInstances = getAllCompetenceInstances();
		getHibernateTemplate().deleteAll(competenceInstances);
	}

	@Override
	public void updateCompetenceInstance(CompetenceInstance competenceInstance) {
		update(competenceInstance);
	}

}
