package biz.sudden.knowledgeData.serviceManagement.repository.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.repository.MaterialRepository;

public class MaterialRepositoryImpl extends
		GenericRepositoryImpl<Material, Long> implements MaterialRepository {

	public MaterialRepositoryImpl() {
		super(Material.class);
	}

	public MaterialRepositoryImpl(Class<Material> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Material> retrieveAll() {
		return getHibernateTemplate().loadAll(Material.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Material> retrieveMaterialBy(String name) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Material.class);
		query.add(Restrictions.eq("name", name));
		return query.list();

		// DetachedCriteria query = DetachedCriteria.forClass(Material.class)
		// .add( Property.forName("name").eq(name) );
		// return getHibernateTemplate().findByCriteria(query);
	}

}
