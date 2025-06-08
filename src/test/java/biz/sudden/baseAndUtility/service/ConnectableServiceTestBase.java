package biz.sudden.baseAndUtility.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import biz.sudden.baseAndUtility.domain.connectable.OccurType;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.domain.connectable.Statement;
import biz.sudden.baseAndUtility.domain.connectable.StringValue;
import biz.sudden.baseAndUtility.repository.AssocRoleTypeRepository;
import biz.sudden.baseAndUtility.repository.AssocTypeRepository;
import biz.sudden.baseAndUtility.repository.AssociationRepository;
import biz.sudden.baseAndUtility.repository.AssociationRoleRepository;
import biz.sudden.baseAndUtility.repository.ScopeRepository;

/**
 * Initializes domain Objects for ConnectableService test cases. Should be
 * extended from actual tests. To run this test alone add Annotation @Test with
 * dummy test method.
 * 
 * @author chris
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/testContext.xml" })
@Transactional
public class ConnectableServiceTestBase {

	@Autowired
	ConnectableService service;
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
	protected static Connectable connectable;
	protected static Association association;
	protected static AssociationRole associationrole;
	protected static AssocRoleType assocroltype;
	protected static AssocType assoctype;
	protected static BaseConnectable baseconnectable;
	protected static Occurrence occurrence;
	protected static OccurType occuretype;
	protected static Scope scope;
	protected static Statement statement;
	protected static StringValue strval;

	// Constants
	protected final static String AT = "AT";
	protected final static String ART = "ART";
	protected final String AR = "AR";
	protected final static String SCOPE = "Scope";

	// Domain Object stores
	protected static Connectable[] conarray;
	protected static Association[] a;
	protected static AssociationRole[] ar;
	protected static AssocRoleType[] art;
	protected static AssocType[] at;
	protected static Occurrence[] occ;
	protected static OccurType[] occt;
	protected static Scope[] sc;
	protected static List<HashSet<Connectable>> contextlist;// for scope
	protected static HashSet<Connectable> context;// for scope
	protected static HashMap<String, List<Connectable>> connectables;
	protected static List<Connectable> conns;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		initScope();
	}

	private static void initScope() {
		Scope s = new Scope();
		s.setName("scope1");
		s.setId(new Long(1));
		s.setContext(context);
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
	public void testDummy() {
		fail("does not matter");
	}

	public AssocRoleType[] getAssocRoleTypes(int counts) {
		art = new AssocRoleType[counts];
		for (int i = 0; i < counts; i++) {
			assocroltype = new AssocRoleType();
			assocroltype.setId(new Long(i));
			assocroltype.setType(ART + i);
			art[i] = assocroltype;
			// Long l = assocRoleTyperep.create(assocroltype);
		}
		return art;

	}

	// inits
	public Association[] getAssociations(int count) {
		a = new Association[count];
		for (int i = 0; i < count; i++) {
			association = new Association();
			association.setId(new Long(i));
			// association.setScope(getScope());
			association.setType(at[i]);
			// assocrep.create(association);
			a[i] = association;
		}
		return a;
	}

	public AssocType[] getAssociationTypes(int count) {
		at = new AssocType[count];
		for (int i = 0; i < count; i++) {
			assoctype = new AssocType();
			assoctype.setType(AT + i);
			assoctype.setId(new Long(i));
			at[i] = assoctype;
		}
		return at;
	}

	public AssociationRole[] getAssociationRoles(int count) {
		ar = new AssociationRole[count];
		for (int i = 0; i < count; i++) {
			associationrole = new AssociationRole();
			associationrole.setId(new Long(i));
			ar[i] = associationrole;
		}
		return ar;
	}

	public ArrayList<HashSet<Connectable>> getContexts(int count) {// context
																	// for scope
		contextlist = new ArrayList<HashSet<Connectable>>();
		for (int i = 0; i < count; i++) {
			context = new HashSet<Connectable>();
			connectable = new Process();
			// connectable.setId(new Long(i));
			baseservice.createProcess((Process) connectable);
			context.add(connectable);
			service.createOrRetrieveScope("scope" + i);
			contextlist.add(context);
		}
		return (ArrayList<HashSet<Connectable>>) contextlist;
	}

	public Connectable[] getConnectableArray(int count) {
		conarray = new Connectable[count];
		Process p = new Process();
		p.setName("process1");
		baseservice.createProcess(p);
		conarray[0] = p;
		/*
		 * for(int i=0; i<conarray.length; i++){ Process c=new Process();
		 * //c.setId(new Long(i)); c.setName("process"+i);
		 * baseservice.createProcess((Process) c); conarray[i] = c; }
		 */
		return conarray;
	}

	public Scope getScope() {
		context = getContexts(1).get(0);
		Long l = scopeRep.create(service
				.createOrRetrieveScope(ScopeRepository.UNSPECIFIED_SCOPE_NAME));
		return scopeRep.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME);
	}

	public HashMap<String, List<Connectable>> getConnectablesHashMap(int count) {
		connectables = new HashMap<String, List<Connectable>>();
		for (int i = 0; i < count; i++) {
			conns = new ArrayList<Connectable>();
			Process c = new Process();
			// c.setId(new Long(i));
			c.setName("process" + i);
			baseservice.createProcess(c);
			conns.add(c);
			connectables.put("connectable" + i, conns);
		}
		return connectables;
	}

	public Occurrence[] getOccurrences(int count) {
		occ = new Occurrence[count];
		for (int i = 0; i < count; i++) {
			occurrence = new Occurrence();
			occurrence.setId(new Long(i));
			occurrence.setType(occt[i]);
			occ[i] = occurrence;
		}
		return occ;
	}

	public OccurType[] getOccTypes(int count) {
		occt = new OccurType[count];
		for (int i = 0; i < count; i++) {
			occuretype = new OccurType();
			occuretype.setId(new Long(i));
			occuretype.setType("occType" + i);
			occt[i] = occuretype;
		}
		return occt;
	}
}
