package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.ScopeRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class ScopeRepositoryImpl extends GenericRepositoryImpl<Scope, Long>
		implements ScopeRepository {

	Logger logger = Logger.getLogger(this.getClass());

	public static Scope UNSPECIFIED_SCOPE = null;

	public ScopeRepositoryImpl() {
		super(Scope.class);
	}

	public ScopeRepositoryImpl(Class<Scope> type) {
		super(type);
	}

	private void createUnspSc() {
		if (UNSPECIFIED_SCOPE == null) {
			// List<Scope> sL =
			// getHibernateTemplate().findByNamedParam("from Scope s " +
			// "where s.name = :name", "name", UNSPECIFIED_SCOPE_NAME);
			Criteria query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createCriteria(Scope.class);
			query.add(Restrictions.eq("name", UNSPECIFIED_SCOPE_NAME));
			List<Scope> sL = query.list();

			if (sL == null || sL.size() == 0) {
				UNSPECIFIED_SCOPE = new Scope(UNSPECIFIED_SCOPE_NAME, null);
				super.create(UNSPECIFIED_SCOPE);
			} else {
				UNSPECIFIED_SCOPE = sL.get(0);
			}
		}
	}

	@Override
	public Scope getUnspecifiedScope() {
		createUnspSc();
		return UNSPECIFIED_SCOPE;
	}

	@Override
	public Scope getRetrieveAllScope() {
		return RETRIEVEALL_SCOPE;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Scope retrieveScopeBy(String name) {
		// DetachedCriteria query =
		// DetachedCriteria.forClass(Scope.class).add(Property.forName("name").eq(name));
		// List<Scope> sL = getHibernateTemplate().findByCriteria(query);

		if (name == null)
			return UNSPECIFIED_SCOPE;
		// FIXME: Performance this is slow!
		// List<Scope> sL =
		// getHibernateTemplate().findByNamedParam("from Scope s " +
		// "where s.name = :name", "name", name);
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Scope.class);
		query.add(Restrictions.eq("name", name));
		List<Scope> sL = query.list();
		if (sL.isEmpty()) {
			if (name.equals(RETRIEVEALL_SCOPE_NAME))
				return RETRIEVEALL_SCOPE;
			else if (name.equals(UNSPECIFIED_SCOPE_NAME))
				return UNSPECIFIED_SCOPE;
			else
				return null;
		}
		return sL.get(0);
	}

	@Override
	public Scope retrieve(Long id) {
		if (id.equals(RETRIEVEALL_SCOPE.getId()))
			return RETRIEVEALL_SCOPE;
		return super.retrieve(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Scope> retrieveAllScopes() {
		createUnspSc();
		List<Scope> result = getHibernateTemplate().loadAll(Scope.class);
		result.add(RETRIEVEALL_SCOPE);
		logger.debug("ScopeRepository: " + result.size());
		return result;
	}

	/** TODO retrieveSCopeBy context not implemended !! */
	@Override
	public Scope retrieveScopeBy(Set<Connectable> context) {
		// Object s = getHibernateTemplate().findByNamedParam("from Scope s "
		// +
		// "where s.context member of :context and :context member of s.context",
		// "context", context).get(0);

		// List<Connectable> cL = new ArrayList<Connectable>();
		// cL.addAll(context);
		// Set<String> sL = new HashSet<String>();
		// sL.add("myString");
		//
		// Object s = getHibernateTemplate().findByNamedParam("from Scope s "
		// + "where :context.size = s.context.size", "context", cL).get(0);

		// Object s = getHibernateTemplate().find("from Scope s "
		// + "where size(s.context)>0").get(0);
		// if(s==null)
		// return null;
		// return (Scope)s;
		throw new java.lang.UnsupportedOperationException(
				"Method not implemented sofar");
		// return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Association> retrieveAssociationsFrom(Scope scope) {
		if (scope == null)
			throw new java.lang.NullPointerException("Scope is null!");
		if (scope == RETRIEVEALL_SCOPE)
			return getHibernateTemplate().loadAll(Association.class);
		return getHibernateTemplate().findByNamedParam(
				"from Association a " + "where :scope = a.scope", "scope",
				scope);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Occurrence> retrieveOccurrencesFrom(Scope scope) {
		if (scope == null)
			throw new java.lang.NullPointerException("Scope is null!");
		if (scope == RETRIEVEALL_SCOPE)
			return getHibernateTemplate().loadAll(Occurrence.class);
		return getHibernateTemplate()
				.findByNamedParam(
						"from Occurrence o " + "where :scope = o.scope",
						"scope", scope);
	}

	// @Override
	// @SuppressWarnings("unchecked")
	// public List<Statement> retrieveStatementsFrom(Scope scope){//TODO: does
	// currently not work because of mapping
	// return getHibernateTemplate().findByNamedParam("from Statement s "
	// + "where s.scope = :scope", "scope", scope);//feherhaft
	// }

}
