package biz.sudden.knowledgeData.serviceManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.serviceManagement.domain.SupportingService;

public interface SupportingServiceRepository extends
		GenericRepository<SupportingService, Long> {

	public List<SupportingService> retrieveSupportingServicesBy(String name);

	public List<SupportingService> retrieveAll();
}
