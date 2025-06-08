package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.CVIInstance;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIInstanceRepository;

public class CVIInstanceRepositoryImpl extends
		GenericRepositoryImpl<CVIInstance, Long> implements
		ICVIInstanceRepository {

	public CVIInstanceRepositoryImpl() {
		super(CVIInstance.class);
	}

	public CVIInstanceRepositoryImpl(Class<CVIInstance> type) {
		super(CVIInstance.class);
	}

	@Override
	public long addCVIInstance(CVIInstance cviInstance) {
		long id = create(cviInstance);
		cviInstance.setId(id);
		// System.out.println("Adding CVI Instance to repository...");
		// System.out.println(cviInstance.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CVIInstance> getAllCVIInstances() {
		return getHibernateTemplate().loadAll(CVIInstance.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CVIInstance> getCVIInstancesByOrganizationId(Long organizationId) {
		return getHibernateTemplate().find(
				"from CVIInstance as cvi where cvi.organizationId = ?",
				organizationId);
	}

	@Override
	public void removeAllCVIInstances() {
		List<CVIInstance> cviInstance = getAllCVIInstances();
		getHibernateTemplate().deleteAll(cviInstance);
	}

}
