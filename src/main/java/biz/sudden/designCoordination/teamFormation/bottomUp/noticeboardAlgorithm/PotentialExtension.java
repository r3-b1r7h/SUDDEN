package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class to represent potential extensions within the algorithm.
 * 
 * Essentially this shouldn't be used except within the algorithm itself.
 * 
 * Basically it is a pair of sets of resources. (+ a pair of simplified types of
 * resources.).
 * 
 * Each partial solution has a set of these and updates them incrementally as it
 * is extended.
 * 
 * @author mcassmc
 * 
 */

public class PotentialExtension {

	// The resource sets for forwards & backwards chaining
	protected HashMap<ResourceSet, Integer> resourcesForward;
	protected HashMap<ResourceSet, Integer> resourcesBackwards;

	// And the abstract types
	protected HashMap<String, Integer> abstractResourcesForward;
	protected HashMap<String, Integer> abstractResourcesBackwards;

	// For when it needs to refer back to it's partial solution.
	// Things are rather complicated by the round tripping through
	// agent messages - at the process provider agents we can't
	// get at the actual partial solution so we just remember the ID
	// as opposed to a more direct reference.
	private long myPartialSolutionID;

	private int timeCreated;

	// those agents who have contributed 1 or more declared capability to this
	// potential extension's partial solution.
	private Collection<String> agentsContributing;

	/**
	 * The sets of resources are specified in hash maps with the key the actual
	 * resource and the entry the state the key has come from.
	 * 
	 * Apologies for this.
	 * 
	 */
	public PotentialExtension(HashMap<ResourceSet, Integer> resourcesForwards,
			HashMap<ResourceSet, Integer> resourcesBackwards,
			long myPartialSolutionID) {
		this.resourcesForward = resourcesForwards;
		this.resourcesBackwards = resourcesBackwards;
		this.calculateAbstractTypes();
		this.timeCreated = new Long(System.currentTimeMillis()).intValue();
		this.myPartialSolutionID = myPartialSolutionID;
		this.agentsContributing = new LinkedList<String>();
	}

	public int getTimeCreated() {
		return this.timeCreated;
	}

	protected void setPartialSolutionID(long l) {
		this.myPartialSolutionID = l;
	}

	public long getPartialSolutionID() {
		return this.myPartialSolutionID;
	}

	public void setAgentsContributing(Collection<String> agentsContributing) {
		this.agentsContributing = agentsContributing;
	}

	public Collection<String> getAgentsContributing() {
		return this.agentsContributing;
	}

	/**
	 * 
	 * Should only call this after checking if the partial order allows the
	 * given state to be added into here. (The stateID added is < all the states
	 * in the backwards chaining part of the potential solution). This check is
	 * ommited here as this class has no knowledge of the partial order.
	 * 
	 * @param extensionToExtend
	 * @param resourcesForward
	 * @param resourcesBackwards
	 * @return an appropiately extended potential extension
	 */
	public PotentialExtension getForwardsExtendedPotentialExtension(
			PotentialExtension extensionToExtend, ResourceSet extraResource,
			Integer stateID) {
		PotentialExtension result;

		HashMap<ResourceSet, Integer> tempResForward = extensionToExtend
				.getResourceSetsForwards();
		tempResForward.put(extraResource, stateID);

		result = new PotentialExtension(tempResForward,
				extensionToExtend.resourcesBackwards, extensionToExtend
						.getPartialSolutionID());

		return result;
	}

	/**
	 * 
	 * Should only call this after checking if the partial order allows the
	 * given state to be added into here. (The stateID added is > all the states
	 * in the forwards chaining part of the potential solution). This check is
	 * ommited here as this class has no knowledge of the partial order.
	 * 
	 * @param extensionToExtend
	 * @param resourcesForward
	 * @param resourcesBackwards
	 * @return an appropiately extended potential extension
	 */
	public PotentialExtension getBackwardsExtendedPotentialExtension(
			PotentialExtension extensionToExtend, ResourceSet extraResource,
			Integer stateID) {
		PotentialExtension result;

		HashMap<ResourceSet, Integer> tempResBackward = extensionToExtend
				.getResourceSetsBackwards();
		tempResBackward.put(extraResource, stateID);

		result = new PotentialExtension(extensionToExtend.resourcesForward,
				tempResBackward, extensionToExtend.getPartialSolutionID());

		return result;
	}

	/**
	 * This is responsible for ensuring the abstract types are lined up with the
	 * concrete ones.
	 * 
	 * @param resourceSets
	 * @return
	 */

	protected void calculateAbstractTypes() {
		// TODO Make this strip out the type names and return just them
		// Only truly meaningful once the type system and cosntraints are being
		// used
		this.abstractResourcesForward = new HashMap<String, Integer>();
		for (ResourceSet r : resourcesForward.keySet()) {
			this.abstractResourcesForward.put(
					r.getResourceType().getTypeName(), resourcesForward.get(r));
		}

		this.abstractResourcesBackwards = new HashMap<String, Integer>();
		for (ResourceSet r : resourcesBackwards.keySet()) {
			this.abstractResourcesBackwards.put(r.getResourceType()
					.getTypeName(), resourcesBackwards.get(r));
		}
	}

	public HashMap<ResourceSet, Integer> getResourceSetsForwards() {
		return this.resourcesForward;
	}

	public HashMap<String, Integer> getAbstractResourcesForwards() {
		return this.abstractResourcesForward;
	}

	public HashMap<ResourceSet, Integer> getResourceSetsBackwards() {
		return this.resourcesBackwards;
	}

	public HashMap<String, Integer> getAbstractResourcesBackwards() {
		return this.abstractResourcesBackwards;
	}

	/**
	 * 
	 * Get the set of stateID's going forward (on a set basis)
	 * 
	 */

	public Iterator getStateIDsForwards() {
		HashSet<Integer> tempHashSet = new HashSet<Integer>(
				this.resourcesForward.values());
		return tempHashSet.iterator();
	}

	/**
	 * 
	 * Get the set of stateID's going backward (on a set basis)
	 * 
	 */

	public Iterator getStateIDsBackwards() {
		HashSet<Integer> tempHashSet = new HashSet<Integer>(
				this.resourcesBackwards.values());
		return tempHashSet.iterator();
	}

	@Override
	public String toString() {
		String result = "";
		result += " Partial Solution ID " + myPartialSolutionID;
		result += " Resources Forward " + resourcesForward;
		result += " Resources Backward" + resourcesBackwards;

		return result;
	}

	/**
	 * Compares two potential extensions to see if they have equal resources.
	 * (could be used to group identical solution paths.).
	 * 
	 * @param pe
	 * @return
	 */
	public boolean equalResources(PotentialExtension pe) {
		Collection<ResourceSet> ourResourcesForward = this.resourcesForward
				.keySet();
		Collection<ResourceSet> toTestResourcesForward = pe.resourcesForward
				.keySet();
		Collection<ResourceSet> ourResourcesBackwards = this.resourcesBackwards
				.keySet();
		Collection<ResourceSet> toTestResourcesBackwards = pe.resourcesBackwards
				.keySet();

		boolean result;

		result = (ourResourcesForward.size() == toTestResourcesForward.size());
		result &= (ourResourcesBackwards.size() == toTestResourcesBackwards
				.size());

		if (result) {
			Iterator<ResourceSet> overOurResourcesForward = ourResourcesForward
					.iterator();

			while (result && overOurResourcesForward.hasNext()) {
				ResourceSet toTest = overOurResourcesForward.next();
				boolean matched = false;

				Iterator<ResourceSet> overToTestResourcesForward = toTestResourcesForward
						.iterator();
				while (!matched && overToTestResourcesForward.hasNext()) {
					matched = toTest.equals(overToTestResourcesForward.next());
				}
				result &= matched;
			}
		}

		if (result) {
			Iterator<ResourceSet> overOurResourcesBackwards = ourResourcesBackwards
					.iterator();

			while (result && overOurResourcesBackwards.hasNext()) {
				ResourceSet toTest = overOurResourcesBackwards.next();
				boolean matched = false;

				Iterator<ResourceSet> overToTestResourcesBackwards = toTestResourcesBackwards
						.iterator();
				while (!matched && overToTestResourcesBackwards.hasNext()) {
					matched = toTest
							.equals(overToTestResourcesBackwards.next());
				}
				result &= matched;
			}
		}

		return result;
	}

	/**
	 * As you might expect :) Two potential extensions are equal iff the set of
	 * agents contributing to them are equal, and the set of reosurces free
	 * forwards/backwards are also equal.
	 * 
	 * @param pe
	 * @return
	 */
	@Override
	public boolean equals(Object pe) {

		if (pe instanceof PotentialExtension) {
			boolean result;

			// check if same agents
			Collection<String> ourOwners = this.agentsContributing;
			Collection<String> toTestOwners = ((PotentialExtension) pe).agentsContributing;

			result = ((ourOwners.containsAll(toTestOwners)) && (toTestOwners
					.containsAll(ourOwners)));

			// And now the resources - a bit more cumbersome this.
			if (result) {
				result &= this.equalResources((PotentialExtension) pe);
			}

			return result;
		} else {
			return false;
		}
	}
}
