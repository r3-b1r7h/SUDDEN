package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.domain.connectable.AssocRoleType;
import biz.sudden.baseAndUtility.repository.AssocRoleTypeRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class AssocRoleTypeRepositoryImpl extends
		GenericRepositoryImpl<AssocRoleType, Long> implements
		AssocRoleTypeRepository {

	public AssocRoleTypeRepositoryImpl() {
		super(AssocRoleType.class);
	}

	public AssocRoleTypeRepositoryImpl(Class<AssocRoleType> type) {
		super(type);
	}

	// empty list of none have been found
	@SuppressWarnings("unchecked")
	@Override
	public List<AssocRoleType> retrieveAssocRoleTypeBy(String type) {
		// DetachedCriteria query =
		// DetachedCriteria.forClass(AssocRoleType.class)
		// .add( Property.forName("type").eq(type) );
		// return getHibernateTemplate().findByCriteria(query);
		return getHibernateTemplate().findByNamedParam(
				"from AssocRoleType art " + "where art.type = :type", "type",
				type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssocRoleType> retrieveAssocRoleTypes() {
		return getHibernateTemplate().loadAll(AssocRoleType.class);
	}

}
