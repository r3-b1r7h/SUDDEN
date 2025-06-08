package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleDimension;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleDimensionRepository;

public class RoleDimensionRepositoryImpl extends
		GenericRepositoryImpl<RoleDimension, Long> implements
		IRoleDimensionRepository {

	public RoleDimensionRepositoryImpl() {
		super(RoleDimension.class);
	}

	public RoleDimensionRepositoryImpl(Class<RoleDimension> type) {
		super(RoleDimension.class);
	}

	@Override
	public long addRoleDimension(RoleDimension roleDimension) {
		long id = create(roleDimension);
		roleDimension.setId(id);
		System.out.println("Adding Role Dimension to repository...");
		System.out.println(roleDimension.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleDimension> getAllRoleDimensions() {
		return getHibernateTemplate().loadAll(RoleDimension.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleDimension> getRoleDimensionsByDimensionId(long dimensionId) {
		return getHibernateTemplate().find(
				"from RoleDimension as role where role.dimension.id = ?",
				dimensionId);
	}

	@Override
	public void removeAllRoleDimensions() {
		List<RoleDimension> roleDimensions = getAllRoleDimensions();
		getHibernateTemplate().deleteAll(roleDimensions);
	}

}
