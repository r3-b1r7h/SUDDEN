package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;

import biz.sudden.Util;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.OccurType;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.OccurrenceRepository;
import biz.sudden.baseAndUtility.repository.ScopeRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class OccurrenceRepositoryImpl extends
		GenericRepositoryImpl<Occurrence, Long> implements OccurrenceRepository {

	public OccurrenceRepositoryImpl() {
		super(Occurrence.class);
	}

	public OccurrenceRepositoryImpl(Class<Occurrence> type) {
		super(type);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Occurrence> retrieveOccurrences(Connectable c, Scope scope) {
		String playerType = Util.getPlayerMetaDef(c);
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Occurrence.class).add(
						Restrictions.sqlRestriction("{alias}.PARENT_TYPE = ?",
								playerType, Hibernate.STRING)).add(
						Restrictions.sqlRestriction("{alias}.PARENT_ID = ?", c
								.getId(), Hibernate.LONG));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			query.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		return initializeOccurrences(query.list());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Occurrence> retrieveOccurrences(Connectable c, String occType,
			Scope scope) {
		String playerType = Util.getPlayerMetaDef(c);
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Occurrence.class).add(
						Restrictions.sqlRestriction("{alias}.PARENT_TYPE = ?",
								playerType, Hibernate.STRING)).add(
						Restrictions.sqlRestriction("{alias}.PARENT_ID = ?", c
								.getId(), Hibernate.LONG));
		query.createCriteria("type").add(Restrictions.eq("type", occType));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			query.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));
		return initializeOccurrences(query.list());

		/*
		 * this does not work since connectable is very generic and the
		 * reference to parent_type, perent_id is needed in Occurence.class
		 * Criteria query =
		 * getHibernateTemplate().getSessionFactory().getCurrentSession
		 * ().createCriteria(Occurrence.class);
		 * query.createCriteria("parent").add(Restrictions.eq("id", c.getId()));
		 * query.createCriteria("type").add(Restrictions.eq("type", occType));
		 * 
		 * if(scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
		 * query.createCriteria("scope").add(Restrictions.eq("id",
		 * scope.getId())); return initializeOccurrences(query.list());
		 */
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Occurrence> retrieveOccurrences(Connectable c,
			OccurType occType, Scope scope) {
		// Performance ... try to improve perf. by not using the playerType.
		// is not possible since connectable is generic and we have for each
		// connectable type a different table with id specific to htis table
		String playerType = Util.getPlayerMetaDef(c);
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Occurrence.class).add(
						Restrictions.sqlRestriction("{alias}.PARENT_TYPE = ?",
								playerType, Hibernate.STRING)).add(
						Restrictions.sqlRestriction("{alias}.PARENT_ID = ?", c
								.getId(), Hibernate.LONG));
		query.createCriteria("type")
				.add(Restrictions.eq("id", occType.getId()));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			query.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));
		return initializeOccurrences(query.list());
	}

	// needed to initialize parent connectables and Values of Occurrences to
	// avoid lazy instantiation exceptions!!
	private List<Occurrence> initializeOccurrences(List<Occurrence> occs) {
		// TODO Check if LazyLoading Problem with out the code below
		// FIXME: try to not use the stuff below; since we are regularly running
		// out of memory when we have a tree and show occurences (even if most
		// are empty)
		Iterator<Occurrence> i = occs.iterator();
		while (i.hasNext()) {
			Occurrence curO = i.next();
			getHibernateTemplate().initialize(curO.getParent());
			getHibernateTemplate().initialize(curO.getValue());
		}
		return occs;
	}

	@Override
	public List<Occurrence> retrieveOccurrencesBy(OccurType type) {
		return retrieveOccurrencesBy(type, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Occurrence> retrieveOccurrencesBy(OccurType type, Scope scope) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Occurrence.class);
		query.createCriteria("type").add(Restrictions.eq("id", type.getId()));
		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			query.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));
		return initializeOccurrences(query.list());
	}

}
