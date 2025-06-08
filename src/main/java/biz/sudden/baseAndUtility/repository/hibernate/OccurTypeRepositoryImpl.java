package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.domain.connectable.OccurType;
import biz.sudden.baseAndUtility.repository.OccurTypeRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class OccurTypeRepositoryImpl extends
		GenericRepositoryImpl<OccurType, Long> implements OccurTypeRepository {

	public OccurTypeRepositoryImpl() {
		super(OccurType.class);
	}

	public OccurTypeRepositoryImpl(Class<OccurType> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OccurType> retrieveOccurTypeBy(String type) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(OccurType.class);
		query.add(Restrictions.eq("type", type));
		return query.list();

		// DetachedCriteria query = DetachedCriteria.forClass(OccurType.class)
		// .add( Property.forName("type").eq(type) );
		// return getHibernateTemplate().findByCriteria(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OccurType> retrieveOccurTypes() {
		return getHibernateTemplate().loadAll(OccurType.class);
	}

}
