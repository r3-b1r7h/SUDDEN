package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.domain.Manufacturer;
import biz.sudden.baseAndUtility.repository.ManufacturerRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class ManufacturerRepositoryImpl extends
		GenericRepositoryImpl<Manufacturer, Long> implements
		ManufacturerRepository {

	public ManufacturerRepositoryImpl() {
		super(Manufacturer.class);
	}

	public ManufacturerRepositoryImpl(Class<Manufacturer> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Manufacturer> retrieveAll() {
		return getHibernateTemplate().loadAll(Manufacturer.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Manufacturer> retrieveManufacturerBy(String name) {
		Criteria q = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Manufacturer.class);
		q.add(Restrictions.eq("name", name));
		return q.list();
		// DetachedCriteria query =
		// DetachedCriteria.forClass(Manufacturer.class)
		// .add( Property.forName("name").eq(name) );
		// return getHibernateTemplate().findByCriteria(query);
	}

}
