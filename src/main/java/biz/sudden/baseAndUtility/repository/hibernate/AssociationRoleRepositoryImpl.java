package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import biz.sudden.Util;
import biz.sudden.baseAndUtility.domain.connectable.AssocRoleType;
import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.AssociationRoleRepository;
import biz.sudden.baseAndUtility.repository.ScopeRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class AssociationRoleRepositoryImpl extends
		GenericRepositoryImpl<AssociationRole, Long> implements
		AssociationRoleRepository {

	public AssociationRoleRepositoryImpl() {
		super(AssociationRole.class);
	}

	public AssociationRoleRepositoryImpl(Class<AssociationRole> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveRolesPlayedOf(Connectable c,
			Scope scope) {
		// performance of the code below is pretty bad!
		if (c != null) {
			String playerType = Util.getPlayerMetaDef(c);
			Criteria query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createCriteria(AssociationRole.class)
					.add(
							Restrictions.sqlRestriction(
									"{alias}.PLAYER_TYPE = ?", playerType,
									Hibernate.STRING)).add(
							Restrictions.sqlRestriction(
									"{alias}.PLAYER_ID = ?", c.getId(),
									Hibernate.LONG));

			if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
				query.createCriteria("parent").createCriteria("scope").add(
						Restrictions.eq("id", scope.getId()));

			return query.list();// role init necessary??
		} else
			return null;
		/*
		 * / // performance of the code below is worse! //String playerType =
		 * Util.getPlayerMetaDef(c); String[] names; Object[] values; if(scope
		 * != null && scope != ScopeRepository.RETRIEVEALL_SCOPE) { names = new
		 * String[]{"p", "sc"}; values = new Object[]{ c, scope}; return
		 * getHibernateTemplate().findByNamedParam("from AssociationRole ar " +
		 * "where ar.player = :p and ar.parent.scope = :sc", names, values); }
		 * else { names = new String[]{"p"}; values = new Object[]{c}; return
		 * getHibernateTemplate().findByNamedParam("from AssociationRole ar " +
		 * "where ar.player = :p", names, values); }
		 */
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveAssociationRolesBy(AssocType assocType) {
		return getHibernateTemplate().findByNamedParam(
				"from AssociationRole ar "
						+ "where ar.parent.type = :assocType", "assocType",
				assocType);
	}

	// TODO TEST
	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Connectable c, Scope scope) {

		String playerType = Util.getPlayerMetaDef(c);
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(AssociationRole.class).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_TYPE = ?",
								playerType, Hibernate.STRING)).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_ID = ?", c
								.getId(), Hibernate.LONG));

		Criteria parent = query.createCriteria("parent");
		parent.createCriteria("type").add(
				Restrictions.eq("id", assocType.getId()));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			parent.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		return query.list();// role init necessary??
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Connectable> retrieveConnectablesInAssocRole(Association a,
			AssocRoleType roleType) {
		String[] params = { "parent", "roleType" };
		Object[] values = { a, roleType };
		return getConnectablesFrom(getHibernateTemplate().findByNamedParam(
				"from AssociationRole ar "
						+ "where ar.parent = :parent and ar.type = :roleType",
				params, values));
	}

	@Override
	public List<Connectable> retrieveCounterpartConnectables(Association a,
			Connectable givenCon, String givenAssocRoleType,
			String searchedAssocRoleType) {
		return getConnectablesFrom(retrieveCounterpartAssociationRoles(a,
				givenCon, givenAssocRoleType, searchedAssocRoleType));
	}

	@Override
	public List<Connectable> retrieveCounterpartConnectables(Association a,
			Connectable givenCon, String givenAssocRoleType) {
		return getConnectablesFrom(this.retrieveCounterpartAssociationRoles(a,
				givenCon, givenAssocRoleType));
	}

	@Override
	public List<Connectable> retrieveCounterpartConnectables(Association a,
			Connectable givenCon) {
		return getConnectablesFrom(retrieveCounterpartAssociationRoles(a,
				givenCon));
	}

	@Override
	public List<Connectable> retrieveCounterpartConnectables(
			Connectable givenCon, String givenAssocRoleType, Scope scope) {
		return getConnectablesFrom(retrieveCounterpartAssociationRoles(
				givenCon, givenAssocRoleType, scope));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Association a, Connectable givenCon, String givenAssocRoleType,
			String searchedAssocRoleType) {
		List<AssocRoleType> givenART = getHibernateTemplate().findByNamedParam(
				"from AssocRoleType where type = :type", "type",
				givenAssocRoleType);
		if (givenART.isEmpty())
			return new ArrayList<AssociationRole>();

		String playerType = Util.getPlayerMetaDef(givenCon);
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(
						"select * from ASSOCIATIONROLE where PLAYER_TYPE = '"
								+ playerType + "' and parent_id =" + a.getId()
								+ " and PLAYER_ID = " + givenCon.getId()
								+ " and type_id = " + givenART.get(0).getId())
				.addEntity(AssociationRole.class);
		List<AssociationRole> sqlResult = query.list();

		if (sqlResult.isEmpty()) {
			// logger.debug("AssociationRoleRepositoryImpl _ retrieveCounterpartConnectables(..): ERROR given Connectable in given AssociationRoleType "
			// +
			// "does not exist within given association!!");
			return new ArrayList<AssociationRole>();
		}
		String[] params = { "parent", "searchedRT", "sqlResult" };
		Object[] values = { a, searchedAssocRoleType, sqlResult };
		return initializeRoles(getHibernateTemplate().findByNamedParam(
				"from AssociationRole ar" + " where ar.parent = :parent"
						+ " and ar.type.type = :searchedRT"
						+ " and ar not in (:sqlResult)", params, values));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Association a, Connectable givenCon, String givenAssocRoleType) {
		List<AssocRoleType> givenART = getHibernateTemplate().findByNamedParam(
				"from AssocRoleType where type = :type", "type",
				givenAssocRoleType);
		if (givenART.isEmpty())
			return new ArrayList<AssociationRole>();

		String playerType = Util.getPlayerMetaDef(givenCon);
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(
						"select * from ASSOCIATIONROLE where PLAYER_TYPE = '"
								+ playerType + "' and parent_id =" + a.getId()
								+ " and PLAYER_ID = " + givenCon.getId()
								+ " and type_id = " + givenART.get(0).getId())
				.addEntity(AssociationRole.class);
		List<AssociationRole> sqlResult = query.list();

		if (sqlResult.isEmpty()) {
			// logger.debug("AssociationRoleRepositoryImpl _ retrieveCounterpartConnectables(..): ERROR given Connectable in given AssociationRoleType"
			// +
			// "does not exist within given association!!");
			return new ArrayList<AssociationRole>();
		}
		String[] params = { "parent", "sqlResult" };
		Object[] values = { a, sqlResult };
		return initializeRoles(getHibernateTemplate().findByNamedParam(
				"from AssociationRole ar" + " where ar.parent = :parent"
						+ " and ar not in (:sqlResult)", params, values));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Association a, Connectable givenCon) {
		String playerType = Util.getPlayerMetaDef(givenCon);
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(
						"select * from ASSOCIATIONROLE where parent_id ="
								+ a.getId() + " and PLAYER_ID = "
								+ givenCon.getId() + " and PLAYER_TYPE = '"
								+ playerType + "'").addEntity(
						AssociationRole.class);
		List<AssociationRole> sqlResult = query.list();

		if (sqlResult != null && sqlResult.size() > 0) {
			String[] params = { "parent", "sqlResult" };
			Object[] values = { a, sqlResult };
			return initializeRoles(getHibernateTemplate().findByNamedParam(
					"from AssociationRole ar" + " where ar.parent = :parent"
							+ " and ar not in (:sqlResult)", params, values));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveCounterpartAssociationRoles(
			Connectable givenCon, String givenAssocRoleType, Scope scope) {

		String playerType = Util.getPlayerMetaDef(givenCon);
		Criteria mainQuery = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(AssociationRole.class)
				.setProjection(Property.forName("parent")).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_TYPE = ?",
								playerType, Hibernate.STRING)).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_ID = ?",
								givenCon.getId(), Hibernate.LONG));

		mainQuery.createCriteria("type").add(
				Restrictions.eq("type", givenAssocRoleType));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			mainQuery.createCriteria("parent").createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		// FIXME don't know if needed -> CHECK
		mainQuery.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Association> givenConAssociations = mainQuery.list();
		List<AssociationRole> resultSet = new ArrayList<AssociationRole>();
		Iterator<Association> i = givenConAssociations.iterator();
		while (i.hasNext())
			resultSet.addAll(retrieveCounterpartAssociationRoles(i.next(),
					givenCon, givenAssocRoleType));
		return resultSet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Association> retrieveAssociationsOfType(String assocType,
			Connectable c, Scope scope) {

		String playerType = Util.getPlayerMetaDef(c);
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(AssociationRole.class)
				.setProjection(Property.forName("parent")).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_TYPE = ?",
								playerType, Hibernate.STRING)).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_ID = ?", c
								.getId(), Hibernate.LONG));

		Criteria parent = query.createCriteria("parent");
		parent.createCriteria("type").add(Restrictions.eq("type", assocType));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			parent.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Connectable c, String assocRoleTypePlayed,
			Scope scope) {

		String playerType = Util.getPlayerMetaDef(c);
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(AssociationRole.class).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_TYPE = ?",
								playerType, Hibernate.STRING)).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_ID = ?", c
								.getId(), Hibernate.LONG));

		query.createCriteria("type").add(
				Restrictions.eq("type", assocRoleTypePlayed));
		Criteria parent = query.createCriteria("parent");
		parent.createCriteria("type").add(
				Restrictions.eq("id", assocType.getId()));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			parent.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveAssociationRolesBy(
			AssocType assocType, Scope scope) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(AssociationRole.class);
		Criteria parent = query.createCriteria("parent");
		parent.createCriteria("type").add(
				Restrictions.eq("id", assocType.getId()));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			parent.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveAssociationRolesBy(
			String assocTypeName, Scope scope) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(AssociationRole.class);
		Criteria parent = query.createCriteria("parent");
		parent.createCriteria("type").add(
				Restrictions.eq("type", assocTypeName));

		if (scope != null && scope != ScopeRepository.RETRIEVEALL_SCOPE)
			parent.createCriteria("scope").add(
					Restrictions.eq("id", scope.getId()));

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveAssociationRolesBy(Connectable c,
			Association a) {
		String playerType = Util.getPlayerMetaDef(c);

		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(
						"select * from ASSOCIATIONROLE where parent_id ="
								+ a.getId() + " and PLAYER_ID = " + c.getId()
								+ " and PLAYER_TYPE = '" + playerType + "'")
				.addEntity(AssociationRole.class);
		List<AssociationRole> sqlResult = query.list();
		return sqlResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveAssociationRolesBy(Connectable c,
			String arType, Association a) {
		String playerType = Util.getPlayerMetaDef(c);
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(AssociationRole.class).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_TYPE = ?",
								playerType, Hibernate.STRING)).add(
						Restrictions.sqlRestriction("{alias}.PLAYER_ID = ?", c
								.getId(), Hibernate.LONG));

		query.createCriteria("parent").add(Restrictions.eq("id", a.getId()));
		query.createCriteria("type").add(Restrictions.eq("type", arType));

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssociationRole> retrieveAssociationRolesBy(Association a,
			AssociationRole ar) {
		String[] params = { "assoc", "role" };
		Object[] values = { a, ar };
		return getHibernateTemplate().findByNamedParam(
				"from AssociationRole ar " + "where ar = :role "
						+ "and ar.parent = :parent", params, values);

	}

	/**
	 * method to initialize player references of given association roles
	 * 
	 * @param aRL
	 * @return
	 */
	private List<AssociationRole> initializeRoles(List<AssociationRole> aRL) {
		// TODO Check if LazyLoading Problem with out the code below
		Iterator<AssociationRole> i = aRL.iterator();
		List<AssociationRole> remove = new ArrayList<AssociationRole>();
		while (i.hasNext()) {
			AssociationRole ar = i.next();
			try {
				getHibernateTemplate().initialize(ar.getPlayer());// init player
			} catch (Exception e) {
				System.err.println(e.getMessage());
				remove.add(ar);
				delete(ar);
			}
		}
		if (remove.size() > 0) {
			for (AssociationRole a : remove) {
				aRL.remove(a);
			}
		}
		return aRL;
	}

	private List<Connectable> getConnectablesFrom(List<AssociationRole> aRL) {
		List<Connectable> returnCon = new ArrayList<Connectable>();
		Iterator<AssociationRole> i = aRL.iterator();
		while (i.hasNext()) {
			returnCon.add(i.next().getPlayer());
		}
		return returnCon;
	}

}
