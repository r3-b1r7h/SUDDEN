package biz.sudden.baseAndUtility.web.controller;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
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
import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.knowledgeData.kdm.service.KdmService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/testContext.xml" })
@Transactional
public class TestCreateProcessJSFController {

	@Autowired
	ServiceManagementService baseService;
	@Autowired
	ConnectableService connectableService;
	@Autowired
	KdmService kdmService;
	private Process currentProcess = new Process();
	private String processString = "MyProcess";
	private String searchName = "";
	private List<Process> found;
	private List<Association> assocfound;
	private Set<Association> assocfound4c;
	private List<AssociationRole> arfound;
	private List<Connectable> connectables;

	private List<Connectable> allConnectables;

	private Logger logger = Logger.getLogger(this.getClass());

	// var 4 associating
	private String role = "";
	private String role2 = "";
	private String p1 = "";
	private String p2 = "";
	private String assocType = "";
	private Connectable connectable;
	private HashMap<String, List<Connectable>> conInRoles;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

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
	public void store() {
		// logger.debug("Process name: "+currentProcess.getName());
		// logger.debug("Process id: "+currentProcess.getId());
		baseService.createProcess(currentProcess);
		// connectables.add(currentProcess);
		// logger.debug("connectables size: "+connectables.size());
		currentProcess = new Process();
		assertNotNull("currentProcess is NULL", currentProcess);
	}

	@Test
	public void search() {
		found = baseService.retrieveProcessBy(searchName);
		assertNotNull("Process List is NULL", found);
	}

	@Test
	public void assocSearchByATandCon() {
		List<Process> pL = baseService.retrieveProcessBy(searchName);
		if (pL.isEmpty()) {
			arfound = new ArrayList<AssociationRole>();
			searchName = "";
			assocType = "";
		}

		currentProcess = baseService.retrieveProcessBy(searchName).iterator()
				.next();
		AssocType aT = connectableService
				.retrieveAssociationType(this.assocType);
		// this.arfound = connectableService.retrieveAssociationRolesBy(aT,
		// currentProcess);
		assertNotNull("arfound is NULL", arfound);
	}

	@Test
	public void assocSearch() {
		assocfound = connectableService.retrieveAssociationsOfType(searchName,
				null, connectableService.getRetrieveAllScope());
		assertNotNull("assocfound is NULL", assocfound);
	}

	@Test
	public void assoc4CSearch() {
		// Logik hin zum Controller auslagern seperation of concerns!!

		arfound = new ArrayList<AssociationRole>();
		Connectable process = baseService.retrieveProcessBy(searchName)
				.iterator().next();
		arfound.addAll(connectableService.retrieveAssociationRolesOf(process,
				connectableService.getRetrieveAllScope()));
	}

	@Test
	public void associate() {
		// bad test code!!
		Connectable cP1 = baseService.retrieveProcessBy(p1).iterator().next();
		logger.debug("cP1: " + ((Process) cP1).getName());
		Connectable cP2 = baseService.retrieveProcessBy(p2).iterator().next();
		logger.debug("cP2: " + ((Process) cP2).getName());

		this.conInRoles = new HashMap<String, List<Connectable>>();
		ArrayList<Connectable> h1 = new ArrayList<Connectable>();
		h1.add(cP1);
		conInRoles.put(role, h1);

		ArrayList<Connectable> h2 = new ArrayList<Connectable>();
		h2.add(cP2);
		conInRoles.put(role2, h2);

		logger.debug("Connectable Service:" + connectableService);
		connectableService.associate(assocType, conInRoles, connectableService
				.getRetrieveAllScope());
		// TODO gweich test scope handling i.e. when not in th universal scope!
		conInRoles = new HashMap<String, List<Connectable>>();
	}

	@Test
	public void test_getAssoc() {
		Process pFromDB = baseService.retrieveProcessBy("cutting").get(0);
		for (Association a : connectableService.retrieveAssociationBy(
				"supersub", pFromDB, "super", connectableService
						.getRetrieveAllScope())) {
			Connectable c = connectableService
					.retrieveCounterpartAssociationRoles(a, pFromDB, "super")
					.get(0).getPlayer();
			logger
					.debug("test method in createProcessJSFController - connectable: "
							+ ((Process) c).getName());
		}

		connectableService.createOrRetrieveStringValue("occString");
		connectableService.addOccurrence(pFromDB, "stringValue",
				connectableService.retrieveStringValueBy("occString"),
				connectableService.getRetrieveAllScope());
	}

	@Test
	public void test_Occurrences() {
		Process pFromDB = baseService.retrieveProcessBy("cutting").get(0);
		connectableService.createOrRetrieveStringValue("occStringValue");
		connectableService.addOccurrence(pFromDB, "stringValue",
				connectableService.retrieveStringValueBy("occStringValue"),
				connectableService.getRetrieveAllScope());
	}

	@Test
	public void test_OccurrencesAndScope() {
		Process pFromDB = baseService.retrieveProcessBy("cutting").get(0);
		logger.debug("Process: " + pFromDB.getName());

		Set<Connectable> context = new HashSet<Connectable>();
		context.add(pFromDB);
		Scope scope = connectableService.createOrRetrieveScope("cuttingScope");
		logger.debug("Scope: name=" + scope.getName() + " id=" + scope.getId());

		connectableService.createOrRetrieveStringValue("occScopeStringValue");
		logger.debug("retrieveStringValue: "
				+ connectableService
						.retrieveStringValueBy("occScopeStringValue"));
		connectableService
				.addOccurrence(pFromDB, "stringValue", connectableService
						.retrieveStringValueBy("occScopeStringValue"),
						connectableService.getRetrieveAllScope());

		Occurrence o = null;
		logger.debug("test_OccurAnScope - occurrences: "
				+ connectableService.retrieveOccurrences(pFromDB, null,
						connectableService.getRetrieveAllScope()).size());
		Iterator<Occurrence> oI = connectableService.retrieveOccurrences(
				pFromDB, null, connectableService.getRetrieveAllScope())
				.iterator();

		while (oI.hasNext()) {
			o = oI.next();
			logger.debug("test_OccurAnScope - o.type=" + o.getType()
					+ " o.value=" + o.getValue() + " parent=" + o.getParent()
					+ " o.scope=" + o.getScope());
		}

		logger.debug("before retrieve scope");
		Scope rS = connectableService.retrieveScopeBy("cuttingScope");
		// Scope rS = connectableService.retrieveScopeBy(context);
		logger.debug("Retrieved Scope has name: " + rS.getName());
		logger.debug("Retrieved Scope has context: " + rS.getContext());
		logger.debug("---before set scope");
		o.setScope(connectableService.getRetrieveAllScope());
		connectableService.updateOccurrence(o);
		logger.debug("---after set scope and update Occurrence");

		logger.debug("test_OccurAnScope - occurrences in Scope: "
				+ connectableService.retrieveOccurrences(pFromDB, null,
						connectableService.getRetrieveAllScope()).size());
		logger.debug("test_OccurAnScope - occurrences in general Scope: "
				+ connectableService.retrieveOccurrences(pFromDB, null,
						connectableService.getRetrieveAllScope()).size());
		// logger.debug("test_OccurAnScope - statements of scope: "+
		// connectableService.retrieveStatementsFrom(scope).size());
	}
}
