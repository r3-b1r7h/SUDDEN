package biz.sudden.baseAndUtility.service;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import biz.sudden.Util;
import biz.sudden.baseAndUtility.domain.Actor;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.repository.ActorRepository;
import biz.sudden.baseAndUtility.repository.AssocRoleTypeRepository;
import biz.sudden.baseAndUtility.repository.AssociationRepository;
import biz.sudden.baseAndUtility.repository.AssociationRoleRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/rootContext.xml" })
// @TransactionConfiguration(defaultRollback=false)
// @Transactional(propagation=Propagation.REQUIRED)
public class TestConnectablesWithCRITERIAS {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AssociationRepository assocRep;

	@Autowired
	private AssociationRoleRepository assocRoleRep;

	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private AssocRoleTypeRepository assocRoleTypeRep;

	@Autowired
	ConnectableService connectableService;

	@Test
	public void maneTEST() {
		Actor actor = new Actor();
		actor.setName("Actor1");

		Actor actor2 = new Actor();
		actor2.setName("Actor2");

		actorRepository.create(actor);
		actorRepository.create(actor2);

		java.util.HashMap<String, List<Connectable>> map = new java.util.HashMap<String, List<Connectable>>();

		List<Connectable> conn1List = new LinkedList();
		conn1List.add(actor);

		List<Connectable> conn2List = new LinkedList();
		conn2List.add(actor2);

		map.put("von", conn1List);
		map.put("zu", conn2List);

		Association assoc = connectableService.associate("assocType", map,
				connectableService.getUnspecifiedScope());

		// List<Connectable> list =
		// connectableService.retrieveCounterpartConnectables(actor, "von");

		// List list =
		// connectableService.retrieveCounterpartAssociationRoles(assoc, actor);
		// connectableService.retrieveR
		// retrieveRolesPlayedOf(Connectable c, Scope scope)
		// System.out.println("Size of list "+list.size());

		Session hibernateSession = sessionFactory.openSession();
		Transaction transaction = hibernateSession.beginTransaction();

		System.out.println("Actor class " + actor.getClass());
		System.out.println("Scope ID " + assoc.getScope());

		String playerType = Util.getPlayerMetaDef(actor);

		Criteria mainQuery = hibernateSession.createCriteria(
				AssociationRole.class).add(
				Restrictions.sqlRestriction("{alias}.PLAYER_TYPE = ?",
						playerType, Hibernate.STRING)).add(
				Restrictions.sqlRestriction("{alias}.PLAYER_ID = ?", actor
						.getId(), Hibernate.LONG));

		mainQuery.createCriteria("type").add(Restrictions.eq("type", "von"));

		mainQuery.createCriteria("parent").add(Restrictions.isNull("scope"))
				.createCriteria("type").add(
						Restrictions.eq("type", "assocType"));

		// Criteria criteria =
		// hibernateSession.createCriteria(AssociationRole.class).
		// add(Restrictions.sqlRestriction("{alias}.PLAYER_TYPE = ?",
		// playerType, Hibernate.STRING)).
		// add(Restrictions.sqlRestriction("{alias}.PLAYER_ID = ?",actor.getId(),Hibernate.LONG)).
		// createCriteria("type").add(Restrictions.eq("type", "bl�d")).
		// createCriteria("parent").
		// add(Restrictions.isNull("scope")).
		// createCriteria("type").
		// add(Restrictions.eq("type", "assocType"));

		mainQuery
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		// .add(
		// Restrictions.sqlRestriction("lower({alias}.name) like lower(?)",
		// "Fritz%", Hibernate.STRING) )
		// .list();
		List<AssociationRole> list = mainQuery.list();

		for (AssociationRole associationRole : list) {
			System.out.println("Association Role " + associationRole.getId());
		}
		// SQLQuery query =
		// hibernateSession.createSQLQuery("select * from ASSOCIATIONROLE ar inner join ASSOCIATION a "
		// +
		// "on (ar.PLAYER_TYPE = '"+playerType+"' and ar.PLAYER_ID = "+actor.getId()+
		// " and ar.PARENT_ID = a.ID and a.SCOPE_ID is "
		// +assoc.getScope()+")");
		// //.addEntity("ar", AssociationRole.class);
		// //.addJoin("a", "ar.parent");
		//
		//		
		// List<Object> zeile = query.list();
		// for(Object object : zeile) {
		// System.out.println((AssociationRole)object);
		// }
		//		
		// List l = s.createSQLQuery("select {org.*}, {emp.*}, emp.regionCode
		//
		// from
		//
		// organization org left outer join employment emp on org.id =
		// emp.employer")
		// .addEntity("org", Organization.class)
		// .addJoin("emp", "org.employments")
		// .addScalar("regionCode", Hibernate.STRING)
		// .list();
		// SQLQuery query =
		// hibernateSession.createSQLQuery("select ar.ID, ar.PLAYER_TYPE, ar.PLAYER_ID, ar.PARENT_ID, ar.TYPE_ID from ASSOCIATIONROLE ar, ASSOCIATION a where ar.PLAYER_TYPE = '"+playerType+"' and ar.PLAYER_ID = "+actor.getId()+" and ar.PARENT_ID = a.ID and a.SCOPE_ID is "+assoc.getScope()).addEntity("assocRole",
		// AssociationRole.class).addJoin("assocRole", "assocRole.parent");
		// SQLQuery query =
		// hibernateSession.createSQLQuery("select * from ASSOCIATIONROLE ar where ar.PLAYER_TYPE = '"+playerType+"' and ar.PLAYER_ID = "+actor.getId()).addEntity("assocRole",
		// AssociationRole.class);
		// List<AssociationRole> sqlResult = query.list();

		// Query query =
		// hibernateSession.createQuery("select ar.player from AssociationRole ar where ar.player=:player");
		// query.setEntity("player", actor);
		// System.out.println("Nr of AssocRoles " + query.list().size());
		// for (Object object : query.list()) {
		// System.out.println("Object "+object);
		// AssociationRole curAr = (AssociationRole) object;
		// System.out.println("assocRole "+curAr.getType().getType());
		// System.out.println("player "+((Actor)curAr.getPlayer()).getName());
		// // Connectable role = (Connectable)object;
		// // System.out.println("Role "+role);
		// }

		transaction.commit();

		hibernateSession.close();

	}

	// @Test
	// public void testConnectable() {
	// Actor actor = new Actor();
	// actor.setName("Actor1");
	//		
	// Actor actor2 = new Actor();
	// actor2.setName("Actor2");
	//		
	// actorRepository.create(actor);
	// actorRepository.create(actor2);
	//		
	// AssociationRole assocRole1 = new AssociationRole();
	//		
	// AssocRoleType assocRoleType1 = new AssocRoleType();
	// assocRoleType1.setType("von");
	// assocRole1.setType(assocRoleType1);
	//		
	// AssociationRole assocRole2 = new AssociationRole();
	//		
	// AssocRoleType assocRoleType2 = new AssocRoleType();
	// assocRoleType2.setType("zu");
	// assocRole2.setType(assocRoleType2);
	//		
	// assocRoleTypeRep.create(assocRoleType1);
	// assocRoleTypeRep.create(assocRoleType2);
	//		
	//		
	//	
	//		
	//		
	// java.util.HashMap<String, List<Connectable>> map = new
	// java.util.HashMap<String, List<Connectable>>();
	//		
	// List<Connectable> conn1List = new LinkedList();
	// conn1List.add(actor);
	//		
	// List<Connectable> conn2List = new LinkedList();
	// conn2List.add(actor2);
	//		
	// map.put("von", conn1List);
	// map.put("zu", conn2List);
	//		
	// Association assoc = connectableService.associate("assocType", map);
	//	
	// //List<Connectable> list =
	// connectableService.retrieveCounterpartConnectables(actor, "von");
	//		
	// List list = connectableService.retrieveCounterpartAssociationRoles(assoc,
	// actor);
	//		
	// System.out.println("Size of list "+list.size());
	//
	//		
	// Session hibernateSession = sessionFactory.openSession();
	// Transaction transaction = hibernateSession.beginTransaction();
	//		
	// System.out.println("Actor class "+actor.getClass());
	//		
	// Query query =
	// hibernateSession.createQuery("select ar.player from AssociationRole ar where ar.player=:player");
	// query.setEntity("player", actor);
	// System.out.println("Nr of Connectables " + query.list().size());
	// for (Object object : query.list()) {
	// System.out.println("Object "+object);
	// // Connectable role = (Connectable)object;
	// // System.out.println("Role "+role);
	// }
	//		
	// transaction.commit();
	//		
	// hibernateSession.close();
	//		
	// }

}
