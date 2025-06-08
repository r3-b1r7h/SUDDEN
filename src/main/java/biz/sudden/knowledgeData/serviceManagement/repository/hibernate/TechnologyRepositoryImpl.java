package biz.sudden.knowledgeData.serviceManagement.repository.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.knowledgeData.serviceManagement.repository.TechnologyRepository;

public class TechnologyRepositoryImpl extends
		GenericRepositoryImpl<Technology, Long> implements TechnologyRepository {

	public TechnologyRepositoryImpl() {
		super(Technology.class);
	}

	public TechnologyRepositoryImpl(Class<Technology> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Technology> retrieveAll() {
		return getHibernateTemplate().loadAll(Technology.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Technology retrieveTechnologyBy(String name) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Technology.class);
		query.add(Restrictions.eq("name", name));

		// DetachedCriteria query = DetachedCriteria.forClass(Technology.class)
		// .add( Property.forName("name").eq(name) );
		// List<Technology> tL = getHibernateTemplate().findByCriteria(query);
		List<Technology> tL = query.list();
		if (tL.isEmpty())
			return null;
		return tL.iterator().next();
	}

}
