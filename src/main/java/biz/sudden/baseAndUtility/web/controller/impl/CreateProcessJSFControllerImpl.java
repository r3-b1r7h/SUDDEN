package biz.sudden.baseAndUtility.web.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.baseAndUtility.web.controller.CreateProcessJSFController;
import biz.sudden.baseAndUtility.web.controller.ScopeController;

public class CreateProcessJSFControllerImpl implements
		CreateProcessJSFController {

	private Logger logger = Logger.getLogger(this.getClass());

	private ServiceManagementService baseService;
	private ConnectableService connectableService;
	private ScopeController scopeController;
	private Process currentProcess = new Process();
	private String processString = "MyProcess";
	private String searchName = "";
	private List<Process> found;
	private List<Association> assocfound;
	private Set<Association> assocfound4c;
	private List<AssociationRole> arfound;
	private List<Connectable> connectables;

	private List<Connectable> allConnectables;

	// var 4 associating
	private String role = "";
	private String role2 = "";
	private String p1 = "";
	private String p2 = "";
	private String assocType = "";
	private Connectable connectable;
	private HashMap<String, List<Connectable>> conInRoles;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getRole()
	 */
	public String getRole() {
		return role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setRole(java.lang.String)
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getAssocType()
	 */
	public String getAssocType() {
		return assocType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setAssocType(java.lang.String)
	 */
	public void setAssocType(String assocType) {
		this.assocType = assocType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getConnectable()
	 */
	public Connectable getConnectable() {
		return connectable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setConnectable(biz.sudden.baseAndUtility.domain.connectable.Connectable)
	 */
	public void setConnectable(Connectable connectable) {
		this.connectable = connectable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getAllProcesses()
	 */
	public List<Process> getAllProcesses() {
		return baseService.retrieveAllProcesses();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getProcessString()
	 */
	public String getProcessString() {
		return processString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setProcessString(java.lang.String)
	 */
	public void setProcessString(String processString) {
		this.processString = processString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getCurrentProcess()
	 */
	public Process getCurrentProcess() {
		return currentProcess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setCurrentProcess(biz.sudden.baseAndUtility.domain.Process)
	 */
	public void setCurrentProcess(Process currentProcess) {
		this.currentProcess = currentProcess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getBaseService()
	 */
	public ServiceManagementService getBaseService() {
		return baseService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setBaseService
	 * (biz.sudden.baseAndUtility.service.ServiceManagementService)
	 */
	public void setBaseService(ServiceManagementService baseService) {
		this.baseService = baseService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getSearchName()
	 */
	public String getSearchName() {
		return searchName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setSearchName(java.lang.String)
	 */
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #store()
	 */
	public String store() {
		// logger.debug("Process name: "+currentProcess.getName());
		// logger.debug("Process id: "+currentProcess.getId());
		baseService.createProcess(currentProcess);
		// connectables.add(currentProcess);
		// logger.debug("connectables size: "+connectables.size());
		currentProcess = new Process();
		return "success";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #search()
	 */
	public String search() {
		found = baseService.retrieveProcessBy(searchName);
		return "stay";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #assocSearchByATandCon()
	 */
	public String assocSearchByATandCon() {
		List<Process> pL = baseService.retrieveProcessBy(searchName);
		if (pL.isEmpty()) {
			arfound = new ArrayList<AssociationRole>();
			searchName = "";
			assocType = "";
			return "stay";
		}

		currentProcess = baseService.retrieveProcessBy(searchName).iterator()
				.next();
		AssocType aT = connectableService
				.retrieveAssociationType(this.assocType);
		this.arfound = connectableService.retrieveAssociationRolesBy(aT,
				currentProcess, scopeController.getUserScope());
		return "stay";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #assocSearch()
	 */
	public String assocSearch() {
		// assocfound =
		// connectableService.retrieveAssociationsOfType(connectableService.retrieveAssociationType(searchName));
		assocfound = connectableService.retrieveAssociationsOfType(searchName,
				null, scopeController.getUserScope());
		return "stay";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #assoc4CSearch()
	 */
	public String assoc4CSearch() {
		// Logik hin zum Controller auslagern seperation of concerns!!

		arfound = new ArrayList<AssociationRole>();
		Connectable process = baseService.retrieveProcessBy(searchName)
				.iterator().next();
		arfound.addAll(connectableService.retrieveAssociationRolesOf(process,
				scopeController.getUserScope()));
		return "stay";
	}

	// private List<AssociationRole> getRolesOf(List<Association> aL){
	// List<AssociationRole> arL = new ArrayList<AssociationRole>();
	// Iterator<Association> i = aL.iterator();
	// while(i.hasNext()){
	// arL.addAll(connectableService.retrieveAssocRoles(i.next()));
	// }
	// return arL;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #associate()
	 */
	public String associate() {
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
		connectableService.associate(assocType, conInRoles, scopeController
				.getUserScope());
		conInRoles = new HashMap<String, List<Connectable>>();

		return "associate";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #test_create()
	 */
	public void test_create() {
		Process p = new Process();
		p.setName("cutting");

		Process p1 = new Process();
		p1.setName("hotcutting");

		// create in db
		baseService.createProcess(p);
		baseService.createProcess(p1);

		// associate Processes
		connectableService.associateTwoConnectables(p, p1, "supersub", "super",
				"sub", scopeController.getUserScope());
		// TODO gweich test when not within hte universal scope

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #test_getAssoc()
	 */
	public void test_getAssoc() {
		// logger.debug("smService retrieveProcess: "+smService.retrieveProcessBy("cutting").size());
		Process pFromDB = baseService.retrieveProcessBy("cutting").get(0);
		// logger.debug("Process classname: "+pFromDB.getClass().getCanonicalName());
		List<Association> aL = connectableService.retrieveAssociationBy(
				"supersub", pFromDB, "super", scopeController.getUserScope());
		for (Association a : aL) {
			Connectable c = connectableService
					.retrieveCounterpartAssociationRoles(a, pFromDB, "super")
					.get(0).getPlayer();
			logger
					.debug("test method in createProcessJSFController - connectable: "
							+ ((Process) c).getName());
		}
		// logger.debug("test method in createProcessJSFController - connectable: "+
		// c);
		// logger.debug("test method in createProcessJSFController - connectable: "+
		// c.getClass().getCanonicalName());
		// connectableService.getAssociation(p, p1, "supersub", "super", "sub");

		connectableService.createOrRetrieveStringValue("occString");
		connectableService.addOccurrence(pFromDB, "stringValue",
				connectableService.retrieveStringValueBy("occString"),
				scopeController.getUserScope());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #test_Occurrences()
	 */
	public void test_Occurrences() {
		Process pFromDB = baseService.retrieveProcessBy("cutting").get(0);
		connectableService.createOrRetrieveStringValue("occStringValue");
		connectableService.addOccurrence(pFromDB, "stringValue",
				connectableService.retrieveStringValueBy("occStringValue"),
				scopeController.getUserScope());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #test_OccurrencesAndScope()
	 */
	public void test_OccurrencesAndScope() {
		Process pFromDB = baseService.retrieveProcessBy("cutting").get(0);
		logger.debug("Process: " + pFromDB.getName());

		Set<Connectable> context = new HashSet<Connectable>();
		context.add(pFromDB);
		// Scope scope = connectableService.createScope("cuttingScope",
		// context);
		// logger.debug("Scope: name="+scope.getName()+" id="+scope.getId());

		connectableService.createOrRetrieveStringValue("occScopeStringValue");
		logger.debug("retrieveStringValue: "
				+ connectableService
						.retrieveStringValueBy("occScopeStringValue"));
		connectableService
				.addOccurrence(pFromDB, "stringValue", connectableService
						.retrieveStringValueBy("occScopeStringValue"),
						scopeController.getUserScope());

		Occurrence o = null;
		logger.debug("test_OccurAnScope - occurrences: "
				+ connectableService.retrieveOccurrences(pFromDB,
						scopeController.getUserScope()).size());
		Iterator<Occurrence> oI = connectableService.retrieveOccurrences(
				pFromDB, scopeController.getUserScope()).iterator();

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
		o.setScope(scopeController.getUserScope());
		connectableService.updateOccurrence(o);
		logger.debug("---after set scope and update Occurrence");

		logger.debug("test_OccurAnScope - occurrences in Scope: "
				+ connectableService.retrieveOccurrences(pFromDB,
						scopeController.getUserScope()).size());
		logger.debug("test_OccurAnScope - occurrences in general Scope: "
				+ connectableService.retrieveOccurrences(pFromDB,
						scopeController.getUserScope()).size());
		// logger.debug("test_OccurAnScope - statements of scope: "+
		// connectableService.retrieveStatementsFrom(scope).size());
	}

	// public String showConnectables(){
	// allConnectables = new ArrayList<Connectable>();
	// allConnectables = connectableService.retrieveAllConnectables();
	// logger.debug("allConnectables size: "+ allConnectables.size());
	// return "stay";
	// }

	// public List<Process> getSearchResults(){
	//		
	// }

	// methods 4 navigation
	// --------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #navigateToCreate()
	 */
	public String navigateToCreate() {
		return "success";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #navigateToFind()
	 */
	public String navigateToFind() {
		return "find";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #navigateToAssociate()
	 */
	public String navigateToAssociate() {
		return "associate";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #navigateToFindAssoc()
	 */
	public String navigateToFindAssoc() {
		return "assocFound";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #navigateToFindAssoc4C()
	 */
	public String navigateToFindAssoc4C() {
		return "assocFound4C";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #navigateToShowC()
	 */
	public String navigateToShowC() {
		return "showC";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #navigateToFindAssocByAt_P()
	 */
	public String navigateToFindAssocByAt_P() {
		return "find";
	}

	// getter and setter
	// -----------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getFound()
	 */
	public List<Process> getFound() {
		return found;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setFound(java.util.List)
	 */
	public void setFound(List<Process> found) {
		this.found = found;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getConnectables()
	 */
	public List<Connectable> getConnectables() {
		return connectables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setConnectables(java.util.List)
	 */
	public void setConnectables(List<Connectable> connectables) {
		this.connectables = connectables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getRole2()
	 */
	public String getRole2() {
		return role2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setRole2(java.lang.String)
	 */
	public void setRole2(String role2) {
		this.role2 = role2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getConInRoles()
	 */
	public HashMap<String, List<Connectable>> getConInRoles() {
		return conInRoles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setConInRoles(java.util.HashMap)
	 */
	public void setConInRoles(HashMap<String, List<Connectable>> conInRoles) {
		this.conInRoles = conInRoles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getP1()
	 */
	public String getP1() {
		return p1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setP1(java.lang.String)
	 */
	public void setP1(String p1) {
		this.p1 = p1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getP2()
	 */
	public String getP2() {
		return p2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setP2(java.lang.String)
	 */
	public void setP2(String p2) {
		this.p2 = p2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getAssocfound()
	 */
	public List<Association> getAssocfound() {
		return assocfound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setAssocfound(java.util.List)
	 */
	public void setAssocfound(List<Association> assocfound) {
		this.assocfound = assocfound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getArfound()
	 */
	public List<AssociationRole> getArfound() {
		return arfound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setArfound(java.util.List)
	 */
	public void setArfound(List<AssociationRole> arfound) {
		this.arfound = arfound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getAssocfound4c()
	 */
	public Set<Association> getAssocfound4c() {
		return assocfound4c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setAssocfound4c(java.util.Set)
	 */
	public void setAssocfound4c(Set<Association> assocfound4c) {
		this.assocfound4c = assocfound4c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getAllConnectables()
	 */
	public List<Connectable> getAllConnectables() {
		return allConnectables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setAllConnectables(java.util.List)
	 */
	public void setAllConnectables(List<Connectable> allConnectables) {
		this.allConnectables = allConnectables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #getConnectableService()
	 */
	@Override
	public ConnectableService getConnectableService() {
		return connectableService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.CreateProcessJSFController
	 * #setConnectableService
	 * (biz.sudden.baseAndUtility.service.ConnectableService)
	 */
	@Override
	public void setConnectableService(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

	@Override
	public ScopeController getScopeController() {
		return this.scopeController;
	}

	@Override
	public void setScopeController(ScopeController sc) {
		scopeController = sc;
	}
}
