package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import biz.sudden.Util;
import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.AssociationRepository;
import biz.sudden.baseAndUtility.repository.ScopeRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class AssociationRepositoryImpl extends
		GenericRepositoryImpl<Association, Long> implements
		AssociationRepository {

	public AssociationRepositoryImpl() {
		super(Association.class);
	}

	public AssociationRepositoryImpl(Class<Association> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Association> retrieveAssocsOfType(String type, Scope scope) {// TODO
																				// Test
		Criteria mainQuery = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Association.class);
		mainQuery.createCriteria("type").add(Restrictions.eq("type", type));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			mainQuery.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		return mainQuery.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Association> retrieveAssocsOfType(AssocType type, Scope scope) {// TODO
																				// cannot
																				// work!!
		Criteria mainQuery = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Association.class);
		mainQuery.createCriteria("type").add(
				Restrictions.eq("id", type.getId()));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			mainQuery.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		return mainQuery.list();
	}

	// get inklusive der geladenen Referenzen auf player vgl. initialize
	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveAssociationRoles(Association a) {// tested
																			// by
																			// mane
		List<AssociationRole> roles = new ArrayList<AssociationRole>();
		roles.addAll(getHibernateTemplate().findByNamedParam(
				"from AssociationRole ar " + "where ar.parent = :a", "a", a));
		// TODO Check if LazyLoading Problem with out the code below
		Iterator<AssociationRole> i = roles.iterator();
		while (i.hasNext()) {
			getHibernateTemplate().initialize(i.next().getPlayer());
		}
		return roles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveAssociationRolesLazy(Association a) {
		List<AssociationRole> roles = new ArrayList<AssociationRole>();
		roles.addAll(getHibernateTemplate().findByNamedParam(
				"from AssociationRole ar " + "where ar.parent = :a)", "a", a));
		return roles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Association> retrieveAssociationBy(String assocType,
			Connectable c, String roleType, Scope scope) {// TODO:Test
		String playerType = Util.getPlayerMetaDef(c);
		Criteria mainQuery = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(AssociationRole.class)
				.setProjection(Property.forName("parent")).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_TYPE = ?",
								playerType, Hibernate.STRING)).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_ID = ?", c
								.getId(), Hibernate.LONG));

		mainQuery.createCriteria("type").add(Restrictions.eq("type", roleType));

		Criteria parent = mainQuery.createCriteria("parent");
		parent.createCriteria("type").add(Restrictions.eq("type", assocType));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			parent.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		// mainQuery.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return mainQuery.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Association> retrieveAssociationBy(Connectable c,
			String roleType, Scope scope) {// TODO:test!!

		String playerType = Util.getPlayerMetaDef(c);
		if (playerType == null || playerType.length() == 0) {
			Criteria x = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createCriteria(c.getClass());
		}

		Criteria mainQuery = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(AssociationRole.class)
				.setProjection(Property.forName("parent")).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_TYPE = ?",
								playerType, Hibernate.STRING)).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_ID = ?", c
								.getId(), Hibernate.LONG));

		mainQuery.createCriteria("type").add(Restrictions.eq("type", roleType));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			mainQuery.createCriteria("parent").createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		mainQuery
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return mainQuery.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	// FIXME: TESTME!
	public List<Association> retrieveAssociationBy(String assocType,
			Connectable c, String connectableRoleType,
			String counterpartRoleType, Scope scope) {
		// query for retrieving all association of the given type where the
		// given connectable is in a role of type connectableType
		String playerType = Util.getPlayerMetaDef(c);
		Criteria query1 = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(AssociationRole.class)
				.setProjection(Property.forName("parent")).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_TYPE = ?",
								playerType, Hibernate.STRING)).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_ID = ?", c
								.getId(), Hibernate.LONG));

		query1.createCriteria("type").add(
				Restrictions.eq("type", connectableRoleType));
		Criteria parent = query1.createCriteria("parent");
		parent.createCriteria("type").add(Restrictions.eq("type", assocType));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			parent.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		// query for retrieving all associations of the given type where roles
		// of type counterpartRoleType occur
		Criteria query2 = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(AssociationRole.class)
				.setProjection(Property.forName("parent"));

		query2.createCriteria("type").add(
				Restrictions.eq("type", counterpartRoleType));
		Criteria parent2 = query2.createCriteria("parent");
		parent2.createCriteria("type").add(Restrictions.eq("type", assocType));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			parent2.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		List<Association> assocs1 = query1.list();
		List<Association> assocs2 = query2.list();

		// get the association for which both an entry with connectableRoleType
		// and counterpartRoleType exists
		List<Association> result = new ArrayList<Association>();
		for (Association as : assocs1) {
			if (assocs2.contains(as)) {
				result.add(as);
			}
		}
		logger
				.debug("Size of Associations with one roletye and Size of Associations with other roletype  "
						+ assocs1.size() + " - " + assocs2.size());
		logger.debug("result: " + result.size());
		return result;
	}

}
