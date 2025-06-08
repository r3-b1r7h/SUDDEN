package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.competencesManagement.domain.CVIInstance;

public interface ICVIInstanceRepository extends
		GenericRepository<CVIInstance, Long> {

	public long addCVIInstance(CVIInstance cviInstance);

	public List<CVIInstance> getAllCVIInstances();

	public List<CVIInstance> getCVIInstancesByOrganizationId(Long organizationId);

	public void removeAllCVIInstances();

}
