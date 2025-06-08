package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.DimensionInstance;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionInstanceRepository;

public class DimensionInstanceRepositoryImpl extends
		GenericRepositoryImpl<DimensionInstance, Long> implements
		IDimensionInstanceRepository {

	public DimensionInstanceRepositoryImpl() {
		super(DimensionInstance.class);
	}

	public DimensionInstanceRepositoryImpl(Class<DimensionInstance> type) {
		super(DimensionInstance.class);
	}

	@Override
	public long addDimensionInstance(DimensionInstance dimensionInstance) {
		long id = create(dimensionInstance);
		dimensionInstance.setId(id);
		// System.out.println("Adding Dimension Instance to repository...");
		// System.out.println(dimensionInstance.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DimensionInstance> getAllDimensionInstances() {
		return getHibernateTemplate().loadAll(DimensionInstance.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DimensionInstance getDimensionInstanceByOrganization(
			Long organizationId, Long dimensionId) {
		List<DimensionInstance> dimensionInstances = getHibernateTemplate()
				.find(
						"from DimensionInstance as dimension where dimension.organization.id = "
								+ organizationId
								+ " and dimension.dimension.id = "
								+ dimensionId);
		if (dimensionInstances.size() > 0) {
			return dimensionInstances.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DimensionInstance> getDimensionInstancesByDimensionId(
			long dimensionId) {
		return getHibernateTemplate().find(
				"from DimensionInstance as dim where dim.id = ?", dimensionId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DimensionInstance> getDimensionInstancesByOrganizationId(
			long organizationId) {
		return getHibernateTemplate().find(
				"from DimensionInstance as dim where dim.organization.id = ?",
				organizationId);
	}

	@Override
	public void removeAllDimensionInstances() {
		List<DimensionInstance> dimensionInstances = getAllDimensionInstances();
		getHibernateTemplate().deleteAll(dimensionInstances);
	}

}
