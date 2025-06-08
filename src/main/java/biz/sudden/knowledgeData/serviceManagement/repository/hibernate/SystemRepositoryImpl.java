package biz.sudden.knowledgeData.serviceManagement.repository.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.serviceManagement.domain.System;
import biz.sudden.knowledgeData.serviceManagement.repository.SystemRepository;

public class SystemRepositoryImpl extends GenericRepositoryImpl<System, Long>
		implements SystemRepository {

	public SystemRepositoryImpl() {
		super(System.class);
	}

	public SystemRepositoryImpl(Class<System> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<System> retrieveAll() {
		return getHibernateTemplate().loadAll(System.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<System> retrieveSystemBy(String name) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(System.class);
		query.add(Restrictions.eq("name", name));
		return query.list();

		// DetachedCriteria query = DetachedCriteria.forClass(System.class)
		// .add( Property.forName("name").eq(name) );
		// return getHibernateTemplate().findByCriteria(query);
	}

}
