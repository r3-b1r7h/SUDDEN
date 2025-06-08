package biz.sudden.baseAndUtility.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;

public interface CreateProcessJSFController {

	public String getRole();

	public void setRole(String role);

	public String getAssocType();

	public void setAssocType(String assocType);

	public Connectable getConnectable();

	public void setConnectable(Connectable connectable);

	public List<Process> getAllProcesses();

	public String getProcessString();

	public void setProcessString(String processString);

	public Process getCurrentProcess();

	public void setCurrentProcess(Process currentProcess);

	public ServiceManagementService getBaseService();

	public void setBaseService(ServiceManagementService baseService);

	public String getSearchName();

	public void setSearchName(String searchName);

	public String store();

	public String search();

	public String assocSearchByATandCon();

	public String assocSearch();

	public String assoc4CSearch();

	public String associate();

	public void test_create();

	public void test_getAssoc();

	public void test_Occurrences();

	public void test_OccurrencesAndScope();

	// methods 4 navigation
	// --------------------
	public String navigateToCreate();

	public String navigateToFind();

	public String navigateToAssociate();

	public String navigateToFindAssoc();

	public String navigateToFindAssoc4C();

	public String navigateToShowC();

	public String navigateToFindAssocByAt_P();

	// getter and setter
	// -----------------
	public List<Process> getFound();

	public void setFound(List<Process> found);

	public List<Connectable> getConnectables();

	public void setConnectables(List<Connectable> connectables);

	public String getRole2();

	public void setRole2(String role2);

	public HashMap<String, List<Connectable>> getConInRoles();

	public void setConInRoles(HashMap<String, List<Connectable>> conInRoles);

	public String getP1();

	public void setP1(String p1);

	public String getP2();

	public void setP2(String p2);

	public ConnectableService getConnectableService();

	public void setConnectableService(ConnectableService connectableService);

	public ScopeController getScopeController();

	public void setScopeController(ScopeController sc);

	public List<Association> getAssocfound();

	public void setAssocfound(List<Association> assocfound);

	public List<AssociationRole> getArfound();

	public void setArfound(List<AssociationRole> arfound);

	public Set<Association> getAssocfound4c();

	public void setAssocfound4c(Set<Association> assocfound4c);

	public List<Connectable> getAllConnectables();

	public void setAllConnectables(List<Connectable> allConnectables);

}