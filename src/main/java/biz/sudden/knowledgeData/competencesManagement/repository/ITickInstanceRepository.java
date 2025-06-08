package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.competencesManagement.domain.TickInstance;

public interface ITickInstanceRepository extends
		GenericRepository<TickInstance, Long> {

	public long addTickInstance(TickInstance tickInstance);

	public List<TickInstance> getAllTickInstances();

	public List<TickInstance> getTickInstancesByOrganizationId(
			Long organizationId);

	public void removeAllTickInstances();

}
