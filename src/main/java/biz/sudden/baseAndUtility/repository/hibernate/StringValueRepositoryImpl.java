package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.domain.connectable.StringValue;
import biz.sudden.baseAndUtility.repository.StringValueRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class StringValueRepositoryImpl extends
		GenericRepositoryImpl<StringValue, Long> implements
		StringValueRepository {

	public StringValueRepositoryImpl() {
		super(StringValue.class);
	}

	public StringValueRepositoryImpl(Class<StringValue> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StringValue> retrieveStringBy(String value) {
		Criteria q = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(StringValue.class);
		q.add(Restrictions.eq("value", value));
		return q.list();
		// DetachedCriteria query = DetachedCriteria.forClass(StringValue.class)
		// .add( Property.forName("value").eq(value) );
		// return getHibernateTemplate().findByCriteria(query);
	}

}
