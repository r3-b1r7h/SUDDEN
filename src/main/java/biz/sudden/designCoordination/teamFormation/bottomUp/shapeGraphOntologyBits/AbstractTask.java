package biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits;

import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;

/**
 * Protege name: AbstractTask
 * 
 * @author ontology bean generator
 * @version 2006/11/29, 17:09:01
 */
public class AbstractTask extends Thing {

	/**
	 * Asignment of multiple IDs Protege name: Identifiers
	 */
	private List identifiers = new ArrayList();

	public void addIdentifiers(String elem) {
		List oldList = this.identifiers;
		identifiers.add(elem);
	}

	public boolean removeIdentifiers(String elem) {
		List oldList = this.identifiers;
		boolean result = identifiers.remove(elem);
		return result;
	}

	public void clearAllIdentifiers() {
		List oldList = this.identifiers;
		identifiers.clear();
	}

	public Iterator getAllIdentifiers() {
		return identifiers.iterator();
	}

	public List getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List l) {
		identifiers = l;
	}

	/**
	 * Protege name: Name
	 */
	private String name;

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

}
