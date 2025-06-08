package biz.sudden.knowledgeData.serviceManagement.repository.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.serviceManagement.domain.SimpleProduct;
import biz.sudden.knowledgeData.serviceManagement.repository.SimpleProductRepository;

public class SimpleProductRepositoryImpl extends
		GenericRepositoryImpl<SimpleProduct, Long> implements
		SimpleProductRepository {

	public SimpleProductRepositoryImpl() {
		super(SimpleProduct.class);
	}

	public SimpleProductRepositoryImpl(Class<SimpleProduct> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleProduct> retrieveAll() {
		return super.retrieveAll();
		// return getHibernateTemplate().loadAll(SimpleProduct.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleProduct> retrieveSimpleProductBy(String name) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(SimpleProduct.class);
		query.add(Restrictions.eq("name", name));
		return query.list();
		//		
		// DetachedCriteria query =
		// DetachedCriteria.forClass(SimpleProduct.class)
		// .add( Property.forName("name").eq(name) );
		// return getHibernateTemplate().findByCriteria(query);
	}

}
