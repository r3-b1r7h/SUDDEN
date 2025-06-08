package biz.sudden.knowledgeData.serviceManagement.repository.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct;
import biz.sudden.knowledgeData.serviceManagement.repository.ComplexProductRepository;

public class ComplexProductRepositoryImpl extends
		GenericRepositoryImpl<ComplexProduct, Long> implements
		ComplexProductRepository {

	public ComplexProductRepositoryImpl() {
		super(ComplexProduct.class);
	}

	public ComplexProductRepositoryImpl(Class<ComplexProduct> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplexProduct> retrieveComplexProductBy(String name) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(ComplexProduct.class);
		query.add(Restrictions.eq("name", name));
		return query.list();
		// DetachedCriteria query =
		// DetachedCriteria.forClass(ComplexProduct.class)
		// .add( Property.forName("name").eq(name) );
		// return getHibernateTemplate().findByCriteria(query);
	}

}
