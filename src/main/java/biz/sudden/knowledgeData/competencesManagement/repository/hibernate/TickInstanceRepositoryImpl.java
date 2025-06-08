package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.TickInstance;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickInstanceRepository;

public class TickInstanceRepositoryImpl extends
		GenericRepositoryImpl<TickInstance, Long> implements
		ITickInstanceRepository {

	public TickInstanceRepositoryImpl() {
		super(TickInstance.class);
	}

	public TickInstanceRepositoryImpl(Class<TickInstance> type) {
		super(TickInstance.class);
	}

	@Override
	public long addTickInstance(TickInstance tickInstance) {
		long id = create(tickInstance);
		tickInstance.setId(id);
		// System.out.println("Adding Tick Instance to repository...");
		// System.out.println(tickInstance.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TickInstance> getAllTickInstances() {
		return getHibernateTemplate().loadAll(TickInstance.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TickInstance> getTickInstancesByOrganizationId(
			Long organizationId) {
		return getHibernateTemplate().find(
				"from TickInstance as tick where tick.organizationId = ?",
				organizationId);
	}

	@Override
	public void removeAllTickInstances() {
		List<TickInstance> tickInstance = getAllTickInstances();
		getHibernateTemplate().deleteAll(tickInstance);
	}

}