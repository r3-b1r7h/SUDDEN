package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.repository.AssocTypeRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class AssocTypeRepositoryImpl extends
		GenericRepositoryImpl<AssocType, Long> implements AssocTypeRepository {

	public AssocTypeRepositoryImpl() {
		super(AssocType.class);
		// TODO Auto-generated constructor stub
	}

	public AssocTypeRepositoryImpl(Class<AssocType> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssocType> retrieveAssocTypeBy(String type) {
		// DetachedCriteria query = DetachedCriteria.forClass(AssocType.class)
		// .add( Property.forName("type").eq(type) );
		// return getHibernateTemplate().findByCriteria(query);

		return getHibernateTemplate().findByNamedParam(
				"from AssocType at " + "where at.type = :type", "type", type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssocType> retrieveAssocTypes() {
		return getHibernateTemplate().loadAll(AssocType.class);
	}

}
