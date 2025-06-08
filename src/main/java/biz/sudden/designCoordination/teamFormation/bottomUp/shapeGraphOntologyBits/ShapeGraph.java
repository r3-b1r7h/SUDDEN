package biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits;

import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;

/**
 * Protege name: ShapeGraph
 * 
 * @author ontology bean generator
 * @version 2006/11/29, 17:09:01
 */
public class ShapeGraph extends Thing {

	/**
	 * Protege name: Nodes
	 */
	private List nodes = new ArrayList();

	public void addNodes(Node elem) {
		List oldList = this.nodes;
		nodes.add(elem);
	}

	public boolean removeNodes(Node elem) {
		List oldList = this.nodes;
		boolean result = nodes.remove(elem);
		return result;
	}

	public void clearAllNodes() {
		List oldList = this.nodes;
		nodes.clear();
	}

	public Iterator getAllNodes() {
		return nodes.iterator();
	}

	public List getNodes() {
		return nodes;
	}

	public void setNodes(List l) {
		nodes = l;
	}

	/**
	 * Protege name: Arcs
	 */
	private List arcs = new ArrayList();

	public void addArcs(Arc elem) {
		List oldList = this.arcs;
		arcs.add(elem);
	}

	public boolean removeArcs(Arc elem) {
		List oldList = this.arcs;
		boolean result = arcs.remove(elem);
		return result;
	}

	public void clearAllArcs() {
		List oldList = this.arcs;
		arcs.clear();
	}

	public Iterator getAllArcs() {
		return arcs.iterator();
	}

	public List getArcs() {
		return arcs;
	}

	public void setArcs(List l) {
		arcs = l;
	}

}
