package biz.sudden.baseAndUtility.service;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import biz.sudden.baseAndUtility.domain.connectable.AssocRoleType;
import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;

/**
 * 
 * @author chris
 * 
 */
@Transactional
public class CSTwoTest extends ConnectableServiceTestBase {
	private Association association;
	private AssocType at = getAssociationTypes(1)[0];
	private AssocRoleType art = getAssocRoleTypes(1)[0];
	private Scope scope;
	private HashMap<String, List<Connectable>> connectables;
	private List<Connectable> connectablelist;
	private Connectable connectable1;
	private List<Association> assoclist;

	@Test
	public void oneOneOneTest() {
		connectables = getConnectablesHashMap(2);
		assertNotNull("Connectables is NULL", connectables);
		scope = getScope();
		System.out.println("Scope = " + scope.getName());
		for (List<Connectable> li : connectables.values()) {
			System.out.println("ID of Connectable = "
					+ li.listIterator().next().getId());
			System.out.println("Connectabel Value = ");
		}
		System.out.println("AssertationType = " + at.getType());
		association = service.associate(at.getType(), connectables, scope);
		assertNotNull("association is NULL", association);
		System.out.println(association.getScope().getName()
				+ " assocsScopeName");
		// List<Association> set =service.retrieveAssociationsFrom(scope);
	}

	/**
	 * Associates connectable itself
	 */
	@Test
	public void associateOneConnectable() {
		conarray = getConnectableArray(2);
		service.associateTwoConnectables(conarray[0], conarray[0],
				at.getType(), at.getType(), art.getType(), scope);
		List<Association> association = service.retrieveAssociationBy(
				conarray[0], art.getType(), scope);
		System.out.println("association size = " + association.size());
		assertNotNull("association is NULL", association);
		assertNotNull("scope is NULL", scope);
		// List<Association> li = service.retrieveAssociationsFrom(scope);
		List<Association> li1 = service.getAssociations(conarray[0],
				conarray[0], at.getType(), art.getType(), art.getType(), scope);
		assertNotNull("association list is NULL", li1);
		System.out.println("listSize = " + li1.size());
		// List<Association> li2 =service.retrieveAssociationsFrom(scope);

	}
}
