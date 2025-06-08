package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.DimensionInstance;

public interface IDimensionInstanceRepository {

	public long addDimensionInstance(DimensionInstance dimensionInstance);

	public List<DimensionInstance> getAllDimensionInstances();

	public DimensionInstance getDimensionInstanceByOrganization(
			Long organizationId, Long dimensionId);

	public List<DimensionInstance> getDimensionInstancesByDimensionId(
			long dimensionId);

	public List<DimensionInstance> getDimensionInstancesByOrganizationId(
			long organizationId);

	public void removeAllDimensionInstances();

}
