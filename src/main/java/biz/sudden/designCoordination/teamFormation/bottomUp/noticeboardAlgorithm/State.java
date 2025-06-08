package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessIntrospector;

/**
 * 
 * A class to hold all the information within a single state
 * 
 * Thus - it holds the states ID and the set of resources it contains.
 * 
 * It also contains the set of resources free at the state in both the forwards
 * and backwards chaining directions.
 * 
 * (Forwards chaining is: A ; A -> B; A -> B -> C etc. Backwards is ... C ; B ->
 * C etc).
 * 
 * @author mcassmc
 * 
 */

public class State {

	/**
	 * The states ID - assigned *only* once it has been added to a partial
	 * solution and not changed after that.
	 */
	private int stateID;

	// The resourceSets
	private HashMap<Integer, ResourceSet> resourceSets;

	// And the resource sets free & which direction they are free in
	private LinkedList<Integer> freeForForwardsChainingResourceSetIDs;
	private LinkedList<Integer> freeForBackwardsChainingResourceSetIDs;

	/**
	 * When a new state is created all of it's resources are free
	 * 
	 * Later on they are matched to declared capabilities and hence used up.
	 * 
	 * @param stateID
	 * @param resourceSets
	 */

	public State(int stateID, Collection resourceSets) {
		this.stateID = stateID;
		this.resourceSets = new HashMap<Integer, ResourceSet>();

		Iterator overResourceSets = resourceSets.iterator();

		// Add all resource ID's in & the resource sets

		this.freeForBackwardsChainingResourceSetIDs = new LinkedList<Integer>();
		this.freeForForwardsChainingResourceSetIDs = new LinkedList<Integer>();

		ResourceSet tempResources;
		Integer tempInt;

		while (overResourceSets.hasNext()) {
			tempResources = (ResourceSet) overResourceSets.next();
			tempInt = new Integer(tempResources.getID());
			this.resourceSets.put(tempInt, tempResources);
			this.freeForBackwardsChainingResourceSetIDs.add(tempInt);
			this.freeForForwardsChainingResourceSetIDs.add(tempInt);
		}
	}

	/**
	 * For when you want to add in your resource sets one at a time.
	 * 
	 * @param stateID
	 */

	public State(int stateID) {
		this.stateID = stateID;
		this.resourceSets = new HashMap<Integer, ResourceSet>();
		this.freeForBackwardsChainingResourceSetIDs = new LinkedList<Integer>();
		this.freeForForwardsChainingResourceSetIDs = new LinkedList<Integer>();
	}

	/**
	 * For when you want to add in your resource sets one at a time.
	 * 
	 */

	public State() {
		this.resourceSets = new HashMap<Integer, ResourceSet>();
		this.freeForBackwardsChainingResourceSetIDs = new LinkedList<Integer>();
		this.freeForForwardsChainingResourceSetIDs = new LinkedList<Integer>();
	}

	public int getStateID() {
		return stateID;
	}

	public void setStateID(int i) {
		this.stateID = i;
	}

	public ResourceSet getResourceSet(int resourceSetID) {
		return this.resourceSets.get(new Integer(resourceSetID));
	}

	public Collection getResourcesAvaliableForwards() {
		return this.freeForForwardsChainingResourceSetIDs;
	}

	public Collection getResourcesAvaliableBackwards() {
		return this.freeForBackwardsChainingResourceSetIDs;
	}

	public Collection<ResourceSet> getAllResources() {
		return this.resourceSets.values();
	}

	/**
	 * For making resourceSets as not available for forwards chaining
	 */
	public void markAsUnavaliableForwards(Integer resourceSetIDToRemove) {
		this.freeForForwardsChainingResourceSetIDs
				.remove(resourceSetIDToRemove);
	}

	/**
	 * For making resourceSets as not available for backwards chaining
	 */
	public void markAsUnavaliableBackwards(Integer resourceSetIDToRemove) {
		this.freeForBackwardsChainingResourceSetIDs
				.remove(resourceSetIDToRemove);
	}

	/**
	 * To mark *all* resource sets in a given state as unvailable for backwards
	 * chaining (used when adding in the goal state)
	 */
	public void markAllUnvailableBackwards() {
		this.freeForBackwardsChainingResourceSetIDs = new LinkedList<Integer>();
	}

	/**
	 * To mark *all* resource sets in a given state as unvailable for forwards
	 * chaining (used when adding in the goal state)
	 */
	public void markAllUnvailableForwards() {
		this.freeForForwardsChainingResourceSetIDs = new LinkedList<Integer>();
	}

	/**
	 * Add a resource set (who's resources are all free.).
	 * 
	 * @param toAdd
	 */

	public void addResourceSet(ResourceSet toAdd) {
		Integer resourceSetID = new Integer(toAdd.getID());
		this.resourceSets.put(resourceSetID, toAdd);
		this.freeForBackwardsChainingResourceSetIDs.add(resourceSetID);
		this.freeForForwardsChainingResourceSetIDs.add(resourceSetID);
	}

	public void addSeveralResourceSets(Collection c) {
		Iterator it = c.iterator();
		while (it.hasNext()) {
			addResourceSet((ResourceSet) it.next());
		}
	}

	@Override
	public String toString() {
		String result = "";

		result += " [ State " + stateID;

		Iterator overResourceSets = resourceSets.values().iterator();
		ResourceSet tempResourceSet;

		result += " ResourceSets:";
		while (overResourceSets.hasNext()) {
			tempResourceSet = (ResourceSet) overResourceSets.next();
			result += " (" + tempResourceSet + ")";
		}

		result += " ResourcesFreeForward (";

		Iterator it = freeForForwardsChainingResourceSetIDs.iterator();
		while (it.hasNext()) {
			result += ((Integer) it.next()).intValue() + ",";
		}
		result += ")";

		result += " ResourcesFreeBackward (";

		it = freeForBackwardsChainingResourceSetIDs.iterator();
		while (it.hasNext()) {
			result += ((Integer) it.next()).intValue() + ",";
		}
		result += ")";

		result += " ] ";

		return result;
	}

	@Override
	public State clone() {
		State result = new State();

		HashMap<Integer, ResourceSet> clonedResourceSets = new HashMap<Integer, ResourceSet>();

		for (Integer I : this.resourceSets.keySet()) {
			Integer clonedInt = new Integer(I.intValue());
			ResourceSet clonedResourceSet = ProcessIntrospector
					.makeConcreteResourceSet(ProcessIntrospector
							.makeAbstractResourceSet(this.resourceSets.get(I)));
			clonedResourceSets.put(clonedInt, clonedResourceSet);
		}

		LinkedList<Integer> clonedFreeBack = new LinkedList<Integer>();

		for (Integer I : this.freeForBackwardsChainingResourceSetIDs) {
			clonedFreeBack.add(new Integer(I.intValue()));
		}

		LinkedList<Integer> clonedFreeFor = new LinkedList<Integer>();

		for (Integer I : this.freeForForwardsChainingResourceSetIDs) {
			clonedFreeFor.add(new Integer(I.intValue()));
		}

		result.freeForBackwardsChainingResourceSetIDs = clonedFreeBack;
		result.freeForForwardsChainingResourceSetIDs = clonedFreeFor;
		result.resourceSets = clonedResourceSets;

		result.stateID = this.stateID;

		return result;
	}

	/**
	 * Do we have this resource set?
	 * 
	 * @param rsExisting
	 * @return
	 */
	public boolean containsResourceSet(ResourceSet rsExisting) {

		return this.resourceSets.containsKey(rsExisting.getID());
	}

	public void setResourceSet(int resourceSetID, ResourceSet replacement) {
		this.resourceSets.remove(resourceSetID);
		this.resourceSets.put(resourceSetID, replacement);
	}
}
