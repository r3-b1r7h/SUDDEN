package biz.sudden.baseAndUtility.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface BusinessOpportunityRepository extends
		GenericRepository<BusinessOpportunity, Long> {

	public List<BusinessOpportunity> retrieveAll();

}
