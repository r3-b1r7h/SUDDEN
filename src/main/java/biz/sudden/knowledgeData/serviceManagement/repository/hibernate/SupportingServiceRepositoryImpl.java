package biz.sudden.knowledgeData.serviceManagement.repository.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.serviceManagement.domain.SupportingService;
import biz.sudden.knowledgeData.serviceManagement.repository.SupportingServiceRepository;

public class SupportingServiceRepositoryImpl extends
		GenericRepositoryImpl<SupportingService, Long> implements
		SupportingServiceRepository {

	public SupportingServiceRepositoryImpl() {
		super(SupportingService.class);
	}

	public SupportingServiceRepositoryImpl(Class<SupportingService> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SupportingService> retrieveAll() {
		return getHibernateTemplate().loadAll(SupportingService.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SupportingService> retrieveSupportingServicesBy(String name) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(SupportingService.class);
		query.add(Restrictions.eq("name", name));
		return query.list();

		// DetachedCriteria query =
		// DetachedCriteria.forClass(SupportingService.class)
		// .add( Property.forName("name").eq(name) );
		// return getHibernateTemplate().findByCriteria(query);
	}

}
