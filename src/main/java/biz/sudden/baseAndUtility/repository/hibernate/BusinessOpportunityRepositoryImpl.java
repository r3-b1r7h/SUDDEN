package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.repository.BusinessOpportunityRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class BusinessOpportunityRepositoryImpl extends
		GenericRepositoryImpl<BusinessOpportunity, Long> implements
		BusinessOpportunityRepository {

	public BusinessOpportunityRepositoryImpl() {
		super(BusinessOpportunity.class);
	}

	public BusinessOpportunityRepositoryImpl(Class<BusinessOpportunity> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessOpportunity> retrieveAll() {
		return getHibernateTemplate().loadAll(BusinessOpportunity.class);
	}

}
