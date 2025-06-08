package biz.sudden.baseAndUtility.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.domain.connectable.AssocRoleType;
import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.BaseConnectable;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.AssocRoleTypeRepository;
import biz.sudden.baseAndUtility.repository.AssocTypeRepository;
import biz.sudden.baseAndUtility.repository.AssociationRepository;
import biz.sudden.baseAndUtility.repository.AssociationRoleRepository;
import biz.sudden.baseAndUtility.repository.ScopeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/testContext.xml" })
@Transactional
public class TestThreeInCircle {

	@Autowired
	ConnectableService connectableService;
	@Autowired
	ServiceManagementService baseservice;
	@Autowired
	AssociationRepository assocrep;
	@Autowired
	AssociationRoleRepository assocRoleRep;
	@Autowired
	AssocRoleTypeRepository assocRoleTyperep;
	@Autowired
	AssocTypeRepository assocTypeRep;
	@Autowired
	ScopeRepository scopeRep;

	// Domain Objects
	protected static Process p;
	protected static Process p1;
	protected static Process p2;
	protected static Connectable connectable;
	protected static AssociationRole associationrole;
	protected static AssocRoleType assocroltype;
	protected static AssocType assoctype;
	protected static BaseConnectable baseconnectable;
	protected static Scope scope;

	private final static String ASSOCTYPE = "assoctype";
	private final static String ASSOCROLETYPE = "assocroleType";
	private final static String PROCESSNAME = "prozess";
	private final static String SCOPENAME = "scope1";

	private static HashSet<Connectable> context;
	private static Connectable[] conarray;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p = new Process();
		p.setName(PROCESSNAME);
		conarray = new Connectable[3];
		conarray[0] = p;

		p1 = new Process();
		p1.setName(PROCESSNAME + "1");
		conarray[1] = p1;

		p2 = new Process();
		p2.setName(PROCESSNAME + "2");
		conarray[2] = p2;

		scope = new Scope();
		scope.setName(SCOPENAME);

		associationrole = new AssociationRole();

		assoctype = new AssocType();
		assoctype.setType(ASSOCTYPE);

		assocroltype = new AssocRoleType();
		assocroltype.setType(ASSOCROLETYPE);

		context = new HashSet<Connectable>();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testThreeInCircle() {
		// Scope
		scope.setContext(getContexts());
		scopeRep.create(scope);
		Scope s = scopeRep.retrieveScopeBy(SCOPENAME);
		assertNotNull("retrieved scope is NULL", s);

		assocRoleRep.create(associationrole);
		assocRoleTyperep.create(assocroltype);
		assocTypeRep.create(assoctype);

		baseservice.createProcess(p);
		baseservice.createProcess(p1);
		baseservice.createProcess(p2);

		// associate in circle
		connectableService.associateTwoConnectables(p, p1, assoctype.getType(),
				assocroltype.getType(), assocroltype.getType(), scope);
		connectableService.associateTwoConnectables(p1, p2,
				assoctype.getType(), assocroltype.getType(), assocroltype
						.getType(), scope);
		connectableService.associateTwoConnectables(p2, p, assoctype.getType(),
				assocroltype.getType(), assocroltype.getType(), scope);

		// check Associations
		for (int i = 0; i < conarray.length; i++) {
			List<Association> assoclist = connectableService
					.retrieveAssociationBy(conarray[i], assoctype.getType(),
							scope);
			List<Association> assoclist1 = connectableService
					.retrieveAssociationBy(assoctype.getType(), conarray[i],
							assocroltype.getType(), scope);
			assertNotNull("associationList is NULL", assoclist);
			assertNotNull("assoclist1 is NULL", assoclist1);
			// assertNotSame("not the same size", assoclist.size(),
			// assoclist1.size());
			assertNotSame("assoclist and assoclist1 aren't teh same",
					assoclist, assoclist1);
			for (int j = 0; j < assoclist.size(); j++) {
				Association a = assoclist.get(j);
				Association b = assoclist1.get(i);
				assertNotSame(a.getScope(), b.getScope());
				assertNotSame(a.getType(), b.getType());
				assertNotSame(a.getId(), b.getId());
			}
		}

		List<Association> assoclist2 = connectableService
				.retrieveAssociationsFrom(scope);
		for (Iterator<Association> it = assoclist2.iterator(); it.hasNext();) {
			Association a = it.next();
			System.out.println(a.getScope().getName()
					+ " = Associations ScopeName in assoclist2");
			System.out.println(a.getType().getType()
					+ " = Associations Type in assoclist2");
		}
		List<Association> assoclist3 = connectableService
				.retrieveAssociationsOfType(assoctype.getType(), null, scope);
		for (Iterator<Association> it = assoclist3.iterator(); it.hasNext();) {
			Association a = it.next();
			System.out.println(a.getScope().getName()
					+ " = Associations ScopeName in assoclist3");
			System.out.println(a.getType().getType()
					+ " = Associations Type in assoclist3");
		}
	}

	public HashSet<Connectable> getContexts() {// context for scope
		context = new HashSet<Connectable>();
		connectable = p;
		context.add(connectable);
		connectable = p1;
		context.add(p1);
		connectable = p2;
		context.add(p2);
		return context;
	}
}
