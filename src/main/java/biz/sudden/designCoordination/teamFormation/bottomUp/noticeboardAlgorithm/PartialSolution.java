package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.UnknownConstraintTypeException;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessIntrospector;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.SuddenProductOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.AbstractTask;
import biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.Arc;
import biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.Node;
import biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.ResourceDependency;
import biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.ShapeGraph;
import biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.TeamMember;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

/**
 * A class tying together the various elements within a given partial solution.
 * 
 * Also contains sundry useful related methods.
 * 
 * @author mcassmc
 * 
 */

public class PartialSolution {

	// The order between the states
	private PartialOrder partialOrder;

	// The actual states
	private HashMap<Integer, State> states;

	// The declared capabilities (*including* the projection functions).
	private LinkedList<DeclaredCapability> declaredCapabilities;

	// The potential extensions
	private LinkedHashSet<PotentialExtension> potentialExtensions;

	// Our ID - the time we were added into the partial solution holder
	private long partialSolutionID;

	public PartialSolution() {
		this.partialOrder = new PartialOrder();
		this.states = new HashMap<Integer, State>();
		this.declaredCapabilities = new LinkedList<DeclaredCapability>();
		this.potentialExtensions = new LinkedHashSet<PotentialExtension>();
	}

	public long getPartialSolutionID() {
		return this.partialSolutionID;
	}

	/**
	 * Not a safe method to call this except where it is currently called.
	 * 
	 * Update our ID & all of the pointers held by our potential extensions. Yes
	 * this is horrible in terms of encapsualtion but this information has to be
	 * externalised and there isn't in truth a pretty way to do it.
	 */
	protected void setPartialSolutionID(long timeAdded) {
		this.partialSolutionID = timeAdded;
		for (PotentialExtension pe : potentialExtensions) {
			pe.setPartialSolutionID(timeAdded);
		}
	}

	public PartialOrder getPartialOrder() {
		return this.partialOrder;
	}

	public HashMap getStates() {
		return this.states;
	}

	public Collection<DeclaredCapability> getDeclaredCapabilities() {
		return this.declaredCapabilities;
	}

	/**
	 * Set the initial and the goal state of the partial solution. This is
	 * mostly used when generating the initial solution for a new noticeboard.
	 */

	public void setIntitialStates(State initialState, State goalState) {
		initialState.setStateID(PartialOrder.INITITIAL_STATE_ID.intValue());
		goalState.setStateID(PartialOrder.FINAL_STATE_ID.intValue());
		initialState.markAllUnvailableBackwards();
		goalState.markAllUnvailableForwards();
		states.put(PartialOrder.INITITIAL_STATE_ID, initialState);
		states.put(PartialOrder.FINAL_STATE_ID, goalState);

		HashMap<ResourceSet, Integer> initialStateResourceForwards = new HashMap<ResourceSet, Integer>();
		Integer tempInteger;
		Iterator it = initialState.getResourcesAvaliableForwards().iterator();
		while (it.hasNext()) {
			tempInteger = (Integer) it.next();
			initialStateResourceForwards.put(initialState
					.getResourceSet(tempInteger.intValue()),
					PartialOrder.INITITIAL_STATE_ID);
		}

		HashMap<ResourceSet, Integer> finalStateResourceBackwards = new HashMap<ResourceSet, Integer>();
		it = goalState.getResourcesAvaliableBackwards().iterator();
		while (it.hasNext()) {
			tempInteger = (Integer) it.next();
			finalStateResourceBackwards.put(goalState
					.getResourceSet(tempInteger.intValue()),
					PartialOrder.FINAL_STATE_ID);
		}

		this.potentialExtensions.add(new PotentialExtension(
				initialStateResourceForwards, finalStateResourceBackwards,
				this.partialSolutionID));
	}

	/**
	 * Add a new state to the partial solution.
	 * 
	 * This involves: (1) Adding the new state, (2) Putting it into the partial
	 * order, (3) Updating the potential extensions.
	 * 
	 * This function call is only used when adding a state which does not relate
	 * to ANY previous state (except the initial & goal states of course), and
	 * isn't used very often.
	 * 
	 * @throws CycleDetectedException
	 */
	public void addNewState(State newState) throws CycleDetectedException {
		// This generates a unique integer at least:)
		/*
		 * DON'T change this. It would break several things which rely on it.
		 * Currently these are: (1) Ensuring that remainders are </> each other
		 * (2) When adding in 'new' states to a partial solution
		 */
		int stateID = states.size() - 2;
		newState.setStateID(stateID);
		partialOrder.addNewState(stateID);
		states.put(new Integer(stateID), newState);
		updatePotentialExtensions(newState);
	}

	/**
	 * And add a new state which is >/< a set of states This is the normal call
	 * used when incrementally updating a partial solution.
	 * 
	 * This involves: (1) Adding the new state, (2) Putting it into the partial
	 * order, (3) Udpating the partial order with the extra information (4)
	 * Updating the potential extensions.
	 * 
	 * @throws CycleDetectedException
	 */
	public void addNewState(State newState, Set<Integer> previousStates,
			Set<Integer> laterStates) throws CycleDetectedException {

		// System.out.println(" Adding new state " + newState + " Before " +
		// previousStates + " After " + laterStates);
		// System.out.println(" To Partial Solution " + this);
		// A simple way to get a set of unique integers
		int stateID = states.size() - 2;
		newState.setStateID(stateID);
		previousStates.add(PartialOrder.INITITIAL_STATE_ID);
		laterStates.add(PartialOrder.FINAL_STATE_ID);
		partialOrder.addNewState(stateID, previousStates, laterStates);
		states.put(new Integer(stateID), newState);
		updatePotentialExtensions(newState);
	}

	/**
	 * Used exclusively when adding in new declared capabilities at the creation
	 * of a new noticeboard
	 * 
	 * @throws UnknownConstraintTypeException
	 * 
	 */

	public MatchedCapability getBestFittingPotentialExtension(
			DeclaredCapability dc) throws UnknownConstraintTypeException {
		MatchedCapability result = null;

		/*
		 * Get the potential extension with the best fit from our list
		 * 
		 * ie minimise the forwards and backwards remainders.
		 * 
		 * This is important to ensure that complete solutions are actually
		 * spotted once formed :)
		 */

		Iterator overPotentialExtensions = this.potentialExtensions.iterator();
		MatchedCapability tempMatchedCapability;

		int backwardsRemainderSize;
		int forwardsRemainderSize;

		/*
		 * This no longer filters out all things where there is no match at all.
		 * This is partly because of the check for a nice fit, and partly
		 * because the setting up of the initial partial solution requires such
		 * non matching extensions.
		 */

		// Now minimise over the backwards remainder first (most important
		// perhaps)
		int minimalBackwardsRemainderSize = 99999999;
		int minimalForwardsRemainderSize = 99999999; // Quite big enough I feel
														// :)

		while (overPotentialExtensions.hasNext()) {
			tempMatchedCapability = dc
					.matchToPotentialExtension((PotentialExtension) overPotentialExtensions
							.next());
			backwardsRemainderSize = tempMatchedCapability
					.getRemainderBackwards().size();
			forwardsRemainderSize = tempMatchedCapability.getRemainderForward()
					.size();

			if (backwardsRemainderSize < minimalBackwardsRemainderSize) {
				minimalBackwardsRemainderSize = backwardsRemainderSize;
				minimalForwardsRemainderSize = forwardsRemainderSize;
				result = tempMatchedCapability;
			} else if ((backwardsRemainderSize == minimalBackwardsRemainderSize)
					&& (forwardsRemainderSize < minimalForwardsRemainderSize)) {
				minimalForwardsRemainderSize = forwardsRemainderSize;
				result = tempMatchedCapability;
			}
		}
		// //System.out.println(" ***** Best fitting potential extension for declared capability "
		// + dc + " is " + result);

		return result;
	}

	/**
	 * A method for udpating the potential extensions within a partial solution
	 * when a new state has been added to the solution.
	 * 
	 * Everything here works on a purely incremental basis.
	 * 
	 * Before calling this function the set of free resources within *every*
	 * state (including the new one) should be updated (to remove those that
	 * aren't free/required any longer.).
	 * 
	 * The partial order must also have been updated.
	 * 
	 * Basically two main differences here: the 'matched' resources are removed
	 * from all of the potential extensions and any empty potential extensions
	 * are removed; the new states added to the partial solution provide new
	 * resources which generate new potential extensions (incrementally
	 * extending old ones in line with the partial order.).
	 */
	@SuppressWarnings("unchecked")
	private void updatePotentialExtensions(State newState) {

		// System.out.println(" Updating potential extensions due to addition of "
		// + newState);

		// Ok so we double the number of potential extensions (modulo what the
		// partial order allows)
		Iterator overCurrentPotentialExtensions = potentialExtensions
				.iterator();

		/*
		 * for (PotentialExtension pe : potentialExtensions) {
		 * //System.out.println(" ^^ " + pe); }
		 */

		// to avoid concurrency issues
		LinkedList toRemove = new LinkedList();
		LinkedList toAdd = new LinkedList();

		PotentialExtension tempPotentialExtension;

		Integer stateID = new Integer(newState.getStateID());
		LinkedList resourcesForward = new LinkedList(newState
				.getResourcesAvaliableForwards());
		LinkedList resourcesBackwards = new LinkedList(newState
				.getResourcesAvaliableBackwards());

		// System.out.println(" new res forwards " + resourcesForward);
		// System.out.println(" new res backwards " + resourcesBackwards);

		HashMap tempResourcesForward;
		HashMap tempResourcesBackwards;

		ResourceSet tempResourceSet;

		Iterator stateIDsForward;
		Iterator stateIDsBackwards;

		while (overCurrentPotentialExtensions.hasNext()) {
			tempPotentialExtension = (PotentialExtension) overCurrentPotentialExtensions
					.next();
			// System.out.println(" Extending " + tempPotentialExtension);
			toRemove.add(tempPotentialExtension);

			/*
			 * Start test block stateIDsForward =
			 * tempPotentialExtension.getStateIDsForwards(); stateIDsBackwards =
			 * tempPotentialExtension.getStateIDsBackwards();
			 * 
			 * System.out.println(" New State " + stateID);
			 * System.out.println(" States forward "); while
			 * (stateIDsForward.hasNext()) {
			 * System.out.print(stateIDsForward.next() + ": "); }
			 * System.out.println(" States backwards "); while
			 * (stateIDsBackwards.hasNext()) {
			 * System.out.print(stateIDsBackwards.next() + ": "); }
			 * 
			 * //End test block NB Either have all the block going or none -
			 * this can kill the system!
			 */

			stateIDsForward = tempPotentialExtension.getStateIDsForwards();
			stateIDsBackwards = tempPotentialExtension.getStateIDsBackwards();

			tempResourcesForward = new HashMap();
			tempResourcesForward.putAll(tempPotentialExtension
					.getResourceSetsForwards());

			// System.out.println(" Resources Forward " + tempResourcesForward);

			tempResourcesBackwards = new HashMap();
			tempResourcesBackwards.putAll(tempPotentialExtension
					.getResourceSetsBackwards());

			// System.out.println(" Resources backwards " +
			// tempResourcesBackwards);

			// Forwards Extension
			if (okForForwardsExtension(stateID, stateIDsBackwards)) {
				// System.out.println(" Forwards Extension!");
				Iterator overResourcesForward = resourcesForward.iterator();
				while (overResourcesForward.hasNext()) {
					tempResourceSet = newState
							.getResourceSet(((Integer) overResourcesForward
									.next()).intValue());
					tempResourcesForward.put(tempResourceSet, stateID);
				}
				toAdd.add(new PotentialExtension(tempResourcesForward,
						tempPotentialExtension.getResourceSetsBackwards(),
						this.partialSolutionID));
			}

			// backwards extension - we might well do both here
			if (okForBackwardsExtension(stateID, stateIDsForward)) {
				// System.out.println(" Backwards Extension!");
				Iterator overResourcesBackward = resourcesBackwards.iterator();
				while (overResourcesBackward.hasNext()) {
					tempResourceSet = newState
							.getResourceSet(((Integer) overResourcesBackward
									.next()).intValue());
					// System.out.println(" adding resource" + tempResourceSet);
					tempResourcesBackwards.put(tempResourceSet, stateID);
				}
				toAdd.add(new PotentialExtension(tempPotentialExtension
						.getResourceSetsForwards(), tempResourcesBackwards,
						this.partialSolutionID));
			}
		}

		// System.out.println(" PEs To Add " + toAdd);

		/*
		 * There's an implicit assumption here that every potential extension
		 * will be extended in one direction or the other.
		 */
		this.potentialExtensions.removeAll(toRemove);
		this.potentialExtensions.addAll(toAdd);

	}

	/**
	 * Used when generating the sets of potential extensions - which must obey
	 * the partial order.
	 * 
	 * A bit wierd at first sight - testing that the state we're extending
	 * forwards comes *after* every state which is used to generate things for
	 * extending forwards.
	 * 
	 * @param stateID
	 * @param stateIDsForward
	 * @return
	 */

	private boolean okForBackwardsExtension(Integer stateID,
			Iterator stateIDsForward) {
		boolean result = true;

		// System.out.println(" Testing for backwards extension");

		Integer tempStateID;
		Set stateIDsToTest;

		while (result && stateIDsForward.hasNext()) {
			tempStateID = (Integer) stateIDsForward.next();

			stateIDsToTest = this.partialOrder
					.getAllStatesLessThan(tempStateID);

			result = !(stateIDsToTest.contains(stateID));
			// System.out.println(" State ID to test " + stateIDsToTest);
			result = result && !(stateID == tempStateID);
		}

		return result;
	}

	/**
	 * Used when generating the sets of potential extensions - which must obey
	 * the partial order.
	 * 
	 * A bit wierd at first sight - testing that the state we're extending
	 * forwards comes *before* every state which is used to generate things for
	 * extending backwards.
	 * 
	 * @param stateID
	 * @param stateIDsBackwards
	 * @return
	 */

	private boolean okForForwardsExtension(Integer stateID,
			Iterator stateIDsBackwards) {

		boolean result = true;

		// System.out.println(" Testing for forwards extension");

		Integer tempStateID;
		Set stateIDsToTest;

		while (result && stateIDsBackwards.hasNext()) {
			tempStateID = (Integer) stateIDsBackwards.next();

			stateIDsToTest = this.partialOrder
					.getAllStatesBiggerThan(tempStateID);
			// System.out.println(" State ID to test " + stateIDsToTest);

			result = !(stateIDsToTest.contains(stateID));
			result = result && !(tempStateID == stateID);
		}

		return result;
	}

	/**
	 * This is the advantage of incrementally generating the potential
	 * extensions :) (They could be, and indeed were at one stage, be generated
	 * on the fly when they are required.).
	 */
	public Collection<PotentialExtension> getAllPotentialExtensions() {
		return this.potentialExtensions;
	}

	/**
	 * This is a major function within the algorithm.
	 * 
	 * It takes a Declared Capability which has been matched to a potential
	 * extension.
	 * 
	 * It then uses this information to extend the partial solution: (1) Add in
	 * the declared capability to the set of declared capabilities, (2) Add in
	 * the forwards/backwards remainder(s) as new states, (3) Mark the matched
	 * resources as no longer free, (4) Regenerate the potential extensions -
	 * removing any now empty ones, (5) Keep the partial order in line.
	 * 
	 * @param matchedCapability
	 * @throws CycleDetectedException
	 */

	public void extendByMatchedDeclaredCapability(
			MatchedCapability matchedCapability) throws CycleDetectedException {
		/*
		 * Ok now I'm round tripping resourceSetID's I can stop passing which
		 * state resources come from in implicit nasty hash maps. Just
		 * regenerate them here mind you :)
		 */
		HashMap<ResourceSet, Integer> matchedForwardFromState = new HashMap<ResourceSet, Integer>();
		HashMap<ResourceSet, Integer> matchedBackwardsFromState = new HashMap<ResourceSet, Integer>();

		int resourceSetID;
		Integer stateID = null;
		State tempState;
		boolean found = false;
		for (ResourceSet r : matchedCapability.getMatchedResourcesForward()) {
			resourceSetID = r.getID();
			/*
			 * Only find it once!
			 */
			found = false;
			Iterator<Integer> overStatesIds = this.states.keySet().iterator();
			while (overStatesIds.hasNext() && !found) {
				stateID = overStatesIds.next();
				tempState = this.states.get(stateID);
				found = tempState.getResourcesAvaliableForwards().contains(
						resourceSetID);
			}

			matchedForwardFromState.put(r, stateID);
		}

		for (ResourceSet r : matchedCapability.getMatchedResourcesBackwards()) {
			resourceSetID = r.getID();
			/*
			 * Only find it once!
			 */
			found = false;
			Iterator<Integer> overStatesIds = this.states.keySet().iterator();
			while (overStatesIds.hasNext() && !found) {
				stateID = overStatesIds.next();
				tempState = this.states.get(stateID);
				found = tempState.getResourcesAvaliableBackwards().contains(
						resourceSetID);
			}

			matchedBackwardsFromState.put(r, stateID);
		}

		// //System.out.println(" PO " + this.partialOrder);
		// System.out.println(" matched declared capability found " +
		// matchedCapability);

		// Remember the capability!
		this.declaredCapabilities.add(matchedCapability
				.getDeclaredCapabilityMatched());

		// System.out.println(" **** Potential Extension " +
		// matchedCapability.getPotentialExtension());

		/*
		 * Remove the used up resources from the partial solution
		 */

		Iterator resourcesToMarkDoneForwards = matchedCapability
				.getMatchedResourcesForward().iterator();

		ResourceSet tempResourceSet;
		Integer tempInteger;

		// From the states

		while (resourcesToMarkDoneForwards.hasNext()) {
			tempResourceSet = (ResourceSet) resourcesToMarkDoneForwards.next();
			System.out.println(" Marking Resource " + tempResourceSet
					+ " as unavailable forwards");
			tempInteger = matchedForwardFromState.get(tempResourceSet);
			tempState = this.states.get(tempInteger);
			System.out.println(" Marking resources at state " + tempInteger
					+ " " + tempState + " as unavailable forwards");

			tempState.markAsUnavaliableForwards(new Integer(tempResourceSet
					.getID()));
			this.states.put(new Integer(tempState.getStateID()), tempState);
		}

		// //System.out.println(" PO " + this.partialOrder);

		// System.out.println(" resources found to match backwards " +
		// matchedCapability.getMatchedResourcesBackwards());
		Iterator resourcesToMarkDoneBackwards = matchedCapability
				.getMatchedResourcesBackwards().iterator();

		while (resourcesToMarkDoneBackwards.hasNext()) {
			tempResourceSet = (ResourceSet) resourcesToMarkDoneBackwards.next();
			System.out.println(" resource set to mark done backwards "
					+ tempResourceSet);
			System.out.println(" temp resource set " + tempResourceSet.getID());

			tempInteger = matchedBackwardsFromState.get(tempResourceSet);
			System.out.println(tempInteger);

			tempState = this.states.get(tempInteger);
			System.out.println(tempState);

			tempState.markAsUnavaliableBackwards(new Integer(tempResourceSet
					.getID()));
			// System.out.println(tempState);

			this.states.put(new Integer(tempState.getStateID()), tempState);
			// System.out.println(this.states.get(tempState.getStateID()));
		}

		// For helping when adding in the new states as below

		HashSet<Integer> stateIDsLessThan = new HashSet<Integer>();
		Iterator it = matchedBackwardsFromState.values().iterator();
		while (it.hasNext()) {
			Integer i = (Integer) it.next();
			// //System.out.println(" Marking states as less than " + i);
			stateIDsLessThan.add(i);
		}

		HashSet<Integer> stateIDsBiggerThan = new HashSet<Integer>();
		it = matchedForwardFromState.values().iterator();
		while (it.hasNext()) {
			Integer i = (Integer) it.next();
			// //System.out.println(" Marking states as greater than " + i);
			stateIDsBiggerThan.add(i);
		}

		/*
		 * Now to ensure that the remainders are less than each other. Relying
		 * on StateID = numberOfStates - 2. So We add in the forwards remainder
		 * first & any backwards remainder second. Only do this when have two
		 * way remainders though.
		 */
		if ((matchedCapability.getRemainderForward().size() != 0)
				&& (matchedCapability.getRemainderBackwards().size() != 0)) {
			/*
			 * Due to the transitive closure being applied to the partial order
			 * this is the only way to do it & marks the pre remainder as less
			 * than the post remainder as well as the explicit post > pre here.
			 */
			// System.out.println(" Marking states as less than due to double remainder"
			// + new Integer(states.size() - 2));
			stateIDsBiggerThan.add(new Integer(states.size() - 2));
		}

		State stateFromForwardsRemainder = new State();
		if (matchedCapability.getRemainderForward().size() != 0) {
			Iterator overRemainderFoward = matchedCapability
					.getRemainderForward().iterator();

			while (overRemainderFoward.hasNext()) {
				tempResourceSet = (ResourceSet) overRemainderFoward.next();
				stateFromForwardsRemainder.addResourceSet(tempResourceSet);
				stateFromForwardsRemainder
						.markAsUnavaliableForwards(new Integer(tempResourceSet
								.getID()));
			}

			// System.out.println(" adding new state from forwards remainder " +
			// stateFromForwardsRemainder);
			addNewState(stateFromForwardsRemainder, new HashSet<Integer>(),
					stateIDsLessThan);
		}

		State stateFromBackwardsRemainder = new State();
		if (matchedCapability.getRemainderBackwards().size() != 0) {
			Iterator overRemainderBackward = matchedCapability
					.getRemainderBackwards().iterator();

			while (overRemainderBackward.hasNext()) {
				tempResourceSet = (ResourceSet) overRemainderBackward.next();
				stateFromBackwardsRemainder.addResourceSet(tempResourceSet);
				stateFromBackwardsRemainder
						.markAsUnavaliableBackwards(new Integer(tempResourceSet
								.getID()));
			}

			// System.out.println(" adding new state from backwards remainder "
			// + stateFromBackwardsRemainder);
			addNewState(stateFromBackwardsRemainder, stateIDsBiggerThan,
					new HashSet<Integer>());
		}

		// TODO - substitute the 'replaced' resource types.

		// Done last - this way the newly free/required resources can be matched
		// into potential extensions before the potential extensions are
		// trimmed.
		trimPotentialExtensions(matchedForwardFromState.keySet(),
				matchedBackwardsFromState.keySet());

		// Finally update the set of agent names within each potential extension
		Collection<String> contributingAgents = new LinkedList<String>();
		Collection<DeclaredCapability> DCs = this.getDeclaredCapabilities();

		for (DeclaredCapability dc : DCs) {
			contributingAgents.add(dc.getOwningAgent());
		}

		Collection<PotentialExtension> potentialExtensions = this
				.getAllPotentialExtensions();

		for (PotentialExtension pe : potentialExtensions) {
			pe.setAgentsContributing(contributingAgents);
		}

		if (matchedCapability.hasReplacementResources()) {
			this.replaceResourceSets(matchedCapability);
		}
	}

	/**
	 * Used when extending a partial solution. To do this a declared capability
	 * has matched a set of resources forwards and backwards. These are removed
	 * from every potential extension which contains them (you can't use
	 * resources twice), this process generates 'empty' potential extensions
	 * which are then removed.
	 * 
	 */

	private void trimPotentialExtensions(
			Set<ResourceSet> resourceSetsMatchedForward,
			Set<ResourceSet> resourceSetsMatchedBackward) {

		// TODO - will this really work once moved to *relative* systems? Not
		// sure myself.

		/*
		 * System.out.println(" before trimming potential extensions are " +
		 * this.potentialExtensions);
		 * System.out.println(" Trimming forwards by " +
		 * resourceSetsMatchedForward);
		 * System.out.println(" Trimming backwards by " +
		 * resourceSetsMatchedBackward);
		 */

		Iterator overPotentialExtensions = this.potentialExtensions.iterator();
		PotentialExtension tempPotentialExtension;

		LinkedHashSet<PotentialExtension> newPotentialExtensions = new LinkedHashSet<PotentialExtension>();

		while (overPotentialExtensions.hasNext()) {
			tempPotentialExtension = (PotentialExtension) overPotentialExtensions
					.next();

			// modify it
			// Yes this really should be encapsulated what I'm doing here.

			// TODO - don't need to do this! Have the resource set ID's now :)

			for (ResourceSet r : resourceSetsMatchedForward) {

				Iterator overResourceSetsToTest = tempPotentialExtension.resourcesForward
						.keySet().iterator();
				boolean notDone = true;
				while (overResourceSetsToTest.hasNext() && notDone) {
					ResourceSet toTest = (ResourceSet) overResourceSetsToTest
							.next();
					if (toTest.equals(r)) {
						tempPotentialExtension.resourcesForward.remove(toTest);
						notDone = false;
					}
				}
			}

			for (ResourceSet r : resourceSetsMatchedBackward) {
				Iterator overResourceSetsToTest = tempPotentialExtension.resourcesBackwards
						.keySet().iterator();
				boolean notDone = true;
				while (overResourceSetsToTest.hasNext() && notDone) {
					ResourceSet toTest = (ResourceSet) overResourceSetsToTest
							.next();
					if (toTest.equals(r)) {
						tempPotentialExtension.resourcesBackwards
								.remove(toTest);
						notDone = false;
					}
				}
			}

			tempPotentialExtension.calculateAbstractTypes();

			// reinsert it (stripping out empty ones)
			if ((tempPotentialExtension.resourcesForward.size() != 0)
					|| (tempPotentialExtension.resourcesBackwards.size() != 0)) {
				// Check for containment - eg if we've trimmed down to be equal
				// to an already existing one.
				// In fact I don't think this stage should ever be reached....

				// System.out.println(" adding potential extension " +
				// tempPotentialExtension);

				boolean matched = false;
				Iterator overNewPotentialExtensions = newPotentialExtensions
						.iterator();
				while (overNewPotentialExtensions.hasNext() && !matched) {
					matched = ((PotentialExtension) overNewPotentialExtensions
							.next()).equals(tempPotentialExtension);
				}

				if (!matched) {
					newPotentialExtensions.add(tempPotentialExtension);
				}
			}
		}

		this.potentialExtensions = newPotentialExtensions;
	}

	/**
	 * Tells you when a partial solution is complete.
	 * 
	 * This happens iff the partial solution contains NO resources which are
	 * free for backwards extension. (ie which need creating. Resources free for
	 * forwards extension are considered to be excess and aren't a problem.).
	 */
	public boolean isCompleteSolution() {
		boolean result = true;

		Iterator overPEs = this.potentialExtensions.iterator();

		while (overPEs.hasNext() && result) {
			result = (((PotentialExtension) overPEs.next())
					.getResourceSetsBackwards().size() == 0);
		}

		return result;
	}

	@Override
	public String toString() {
		String result = "";

		result += " Partial Solution ID " + partialSolutionID;

		Iterator overStateKeys = states.keySet().iterator();
		Integer tempInt;
		State tempState;

		while (overStateKeys.hasNext()) {
			tempInt = (Integer) overStateKeys.next();
			// //System.out.println(" ** next state key is " + tempInt);
			tempState = states.get(tempInt);
			result += tempState;
		}

		result += " Partial Order :" + partialOrder;

		// TODO when declared capabilities are defined properly will need to
		// extend this.
		result += " Declared Capabilities :" + declaredCapabilities;

		Iterator overPotExt = potentialExtensions.iterator();

		while (overPotExt.hasNext()) {
			result += " Potential Extension : " + overPotExt.next();
		}

		return result;
	}

	/**
	 * Provides a measure of when two partial solutions are effectively
	 * equivalent. This holds when: (1) They have the same set of potential
	 * extensions (2) They have the same set of agents providing declared
	 * capabilities
	 * 
	 * This is used by the noticeboard agent to reduce the number of symmetries
	 * encountered within the operation of the system.
	 * 
	 * It is (scarcely) possible that this will limit the set of posible
	 * solutions considered. However this does require a rather contrived set of
	 * circumstances.
	 */
	public boolean equivalentToPartialSolution(PartialSolution toTest) {
		// In fact *internally* every potential extension also contains the set
		// of agents who have contributed to it.

		LinkedList<PotentialExtension> ourPEs = new LinkedList<PotentialExtension>(
				this.getAllPotentialExtensions());
		LinkedList<PotentialExtension> toTestPEs = new LinkedList<PotentialExtension>(
				toTest.getAllPotentialExtensions());

		Iterator<PotentialExtension> overOurPEs = ourPEs.iterator();
		Iterator<PotentialExtension> overToTestPEs;

		// Somehow seems rather a lot of effort to test for a bijective mapping
		// this!

		boolean matchedOverall = true;

		// First test if the sets of potential extensions are the same size
		boolean result = (ourPEs.size() == toTestPEs.size());

		// Test if same agents
		if (result) {
			// Slightly odd way to get these - every PE has the same set of
			// agents contributing though.
			Collection<String> ourAgents = new LinkedList<String>();
			for (DeclaredCapability dc : this.declaredCapabilities) {
				ourAgents.add(dc.getOwningAgent());
			}

			Collection<String> agentsToTest = new LinkedList<String>();
			for (DeclaredCapability dc : toTest.declaredCapabilities) {
				agentsToTest.add(dc.getOwningAgent());
			}

			result &= ((ourAgents.containsAll(agentsToTest))
					&& (agentsToTest.containsAll(ourAgents)) && (ourAgents
					.size() == agentsToTest.size()));
		}

		// Now test if all potential extensions in our lot are in the set to
		// test
		if (result) {

			while (matchedOverall && overOurPEs.hasNext()) {
				PotentialExtension ourPE = overOurPEs.next();
				boolean thisMatched = false;

				overToTestPEs = toTestPEs.iterator();

				while (!thisMatched && overToTestPEs.hasNext()) {
					PotentialExtension toTestPE = overToTestPEs.next();
					thisMatched = (toTestPE.equalResources(ourPE));
				}

				matchedOverall &= thisMatched;
			}
			result &= matchedOverall;
		}

		// Now if all potential extensions in to test are in our lot
		if (result) {
			overToTestPEs = toTestPEs.iterator();
			while (matchedOverall && overToTestPEs.hasNext()) {
				PotentialExtension toTestPE = overToTestPEs.next();
				boolean thisMatched = false;

				overOurPEs = ourPEs.iterator();

				while (!thisMatched && overOurPEs.hasNext()) {
					PotentialExtension ourPE = overOurPEs.next();
					thisMatched = (toTestPE.equalResources(ourPE));
				}

				matchedOverall &= thisMatched;
			}
			result &= matchedOverall;
		}

		return result;

	}

	/**
	 * Fairly self explanitory I think :)
	 */
	@Override
	public PartialSolution clone() {
		// Beware of some mildly wierd dodges below :)
		PartialSolution result = new PartialSolution();

		result.partialSolutionID = this.partialSolutionID;

		/*
		 * Oh yes this is *lazy*. Somewhat on the deeply inefficent side too.
		 */
		LinkedList<DeclaredCapability> clonedDCs = new LinkedList<DeclaredCapability>();
		for (DeclaredCapability dc : this.declaredCapabilities) {
			clonedDCs.add(ProcessIntrospector
					.makeConcreteDeclaredCapability(ProcessIntrospector
							.makeAbstractDeclaredCapability(dc)));
		}
		result.declaredCapabilities = clonedDCs;

		/*
		 * And this! In fact this is a few orders of magnitude inneficent....
		 */
		LinkedHashSet<PotentialExtension> clonedPEs = new LinkedHashSet<PotentialExtension>();
		for (PotentialExtension pe : this.potentialExtensions) {
			PotentialExtension clonedPe = ProcessIntrospector
					.makeConcretePotentialExtension(ProcessIntrospector
							.makeAbstractPotentialExtension(pe));
			regenerateStateInformation(clonedPe);
			clonedPEs.add(clonedPe);
		}
		result.potentialExtensions = clonedPEs;

		/*
		 * Look a proper clone method ;)
		 */
		HashMap<Integer, State> clonedStates = new HashMap<Integer, State>();
		for (Integer I : this.states.keySet()) {
			clonedStates.put(new Integer(I.intValue()), this.states.get(I)
					.clone());
		}

		result.states = clonedStates;

		// And another
		result.partialOrder = this.partialOrder.clone();

		// //System.out.println("Before cloning " + this.partialOrder +
		// " After cloning! " + result.partialOrder);

		return result;
	}

	/**
	 * This is used when a partial solution has been generated with some
	 * substitutions involved. Only call this AFTER the potential extensions etc
	 * have been updated.
	 */
	public void replaceResourceSets(
			MatchedCapability matchedCapabilityWithreplacements) {
		// Just a dumb brute force search? Maybe. Get every state, fetch out
		// it's replacement (if any) then replace (if needed.).
		// Somewhat innefficent of course.
		// Don't forget the potential extensions too!

		for (Integer i : states.keySet()) {
			State tempState = states.get(i);
			ArrayList<ResourceSet> replacementsThisState = new ArrayList<ResourceSet>();

			for (ResourceSet r : tempState.getAllResources()) {
				ResourceSet replacement = matchedCapabilityWithreplacements
						.getReplacementResource(r);
				if (replacement != null) {
					replacementsThisState.add(replacement);
				}
			}

			for (ResourceSet r : replacementsThisState) {
				tempState.setResourceSet(r.getID(), r);
			}
		}

		// Now the PE's
		for (PotentialExtension pe : this.potentialExtensions) {
			HashMap<ResourceSet, Integer> replacements = new HashMap<ResourceSet, Integer>();
			for (ResourceSet r : pe.resourcesForward.keySet()) {
				ResourceSet replacement = matchedCapabilityWithreplacements
						.getReplacementResource(r);
				if (replacement != null) {
					replacements.put(replacement, pe.resourcesForward.get(r));
				} else {
					replacements.put(r, pe.resourcesForward.get(r));
				}
			}
			pe.resourcesForward = replacements;

			replacements = new HashMap<ResourceSet, Integer>();
			for (ResourceSet r : pe.resourcesBackwards.keySet()) {
				ResourceSet replacement = matchedCapabilityWithreplacements
						.getReplacementResource(r);
				if (replacement != null) {
					replacements.put(replacement, pe.resourcesBackwards.get(r));
				} else {
					replacements.put(r, pe.resourcesBackwards.get(r));
				}
			}
			pe.resourcesBackwards = replacements;
		}
	}

	/**
	 * Given a resource set ID retrieve the full resource set.
	 */

	public ResourceSet getResourceSet(Integer resourceSetKey) {

		ResourceSet result = null;

		Iterator<State> it = this.states.values().iterator();

		while (it.hasNext() && (result == null)) {
			State s = it.next();
			result = s.getResourceSet(resourceSetKey);
		}

		return result;
	}

	/**
	 * 
	 * Take a potential extension and make sure the resource sets have the right
	 * state information. Here purely used in the lazy cloning mechanism.
	 * 
	 * NB this method will *NOT* fail gracefully if something get maaligned and
	 * it tries to fetch out a resource which isn't in the given partial
	 * solution.
	 */
	private void regenerateStateInformation(PotentialExtension p) {
		PartialSolution partialSolutionToExtend = this;

		HashMap<ResourceSet, Integer> resourcesForward = p
				.getResourceSetsForwards();
		HashMap<ResourceSet, Integer> newResourcesForwards = new HashMap<ResourceSet, Integer>();

		for (ResourceSet rs : resourcesForward.keySet()) {
			Integer resourceSetKey = rs.getID();
			ResourceSet rsExisting = partialSolutionToExtend
					.getResourceSet(resourceSetKey);
			// System.out.println(" ** existing resource set is " + rsExisting);
			Integer fromState = null;
			Iterator overStates = partialSolutionToExtend.getStates().values()
					.iterator();

			while (fromState == null && overStates.hasNext()) {
				State nextState = (State) overStates.next();
				if (nextState.containsResourceSet(rsExisting)) {
					fromState = nextState.getStateID();
				}
			}
			newResourcesForwards.put(rs, fromState);
		}

		HashMap<ResourceSet, Integer> resourcesBackwards = p
				.getResourceSetsBackwards();
		HashMap<ResourceSet, Integer> newResourcesBackwards = new HashMap<ResourceSet, Integer>();

		for (ResourceSet rs : resourcesBackwards.keySet()) {
			Integer resourceSetKey = rs.getID();
			ResourceSet rsExisting = partialSolutionToExtend
					.getResourceSet(resourceSetKey);
			// System.out.println(" ** existing resource set is " + rsExisting);
			Integer fromState = null;
			Iterator overStates = partialSolutionToExtend.getStates().values()
					.iterator();

			while (fromState == null && overStates.hasNext()) {
				State nextState = (State) overStates.next();
				if (nextState.containsResourceSet(rsExisting)) {
					fromState = nextState.getStateID();
				}
			}

			newResourcesBackwards.put(rs, fromState);
		}

		// Don't tell anyone about this non ecapsulation :)
		p.resourcesForward = newResourcesForwards;
		p.resourcesBackwards = newResourcesBackwards;

	}

	/**
	 * For converting into a shape graph. NB this function should only be called
	 * on complete solutions, otherwise it will break.
	 */
	public ShapeGraph convertToShapeGraph() {
		// Hum yes som odd ontology constructs for now.

		// Nodes == declared capabilities,
		// Resource dependencies - derived from the state structure

		ShapeGraph result = new ShapeGraph();

		Node tempNode;

		// I think this is reliant on the ordering of the declared capabilities
		// to keep things straight....
		int i = 0;
		for (DeclaredCapability dc : this.declaredCapabilities) {

			tempNode = new Node();
			tempNode.setNodeId(i);

			TeamMember owningAgent = new TeamMember();
			/*
			 * george changed ontology InternationalisedString st = new
			 * InternationalisedString(); st.setText(dc.getOwningAgent());
			 * owningAgent.addNames(st); tempNode.addMemberList(owningAgent);
			 */
			owningAgent.setName(dc.getOwningAgent());
			tempNode.addMemberList(owningAgent);

			// TODO - sort out how this is actually going to work
			AbstractTask task = new AbstractTask();
			task.addIdentifiers(dc.toString());
			tempNode.addTaskList(task);

			result.addNodes(tempNode);
			i++;
		}

		// Now for arcs
		// Basically just add in the resource dependencies where they exist
		for (DeclaredCapability dc : this.declaredCapabilities) {
			for (ResourceSet resProduced : dc.getResourcesProduced()) {
				int resourceSetID = resProduced.getID();
				for (DeclaredCapability dcToTest : this.declaredCapabilities) {
					ResourceSet resToTest;
					boolean found = false;
					Iterator<ResourceSet> overResourcesToTest = dcToTest
							.getResourcesRequired().iterator();
					while (overResourcesToTest.hasNext() && !found) {
						resToTest = overResourcesToTest.next();
						if (resToTest.getID() == resourceSetID) {
							Arc tempArc = new Arc();
							tempArc.setInputNode(this.declaredCapabilities
									.indexOf(dc));
							tempArc.setOutputNode(this.declaredCapabilities
									.indexOf(dcToTest));
							tempArc.setDependencyType(new ResourceDependency());
							result.addArcs(tempArc);
							found = true;
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * For converting into a shape graph. NB this function should only be called
	 * on complete solutions, otherwise it will break.
	 */
	@SuppressWarnings("deprecation")
	public ConcreteSupplyNetwork convertToCSN() {
		// Nodes == declared capabilities,
		// Resource dependencies - derived from the state structure

		Map<ASNRoleNode, Supplier> supplierAssignment = new HashMap<ASNRoleNode, Supplier>();
		AbstractSupplyNetwork resultAbstract = new AbstractSupplyNetwork();

		ASNRoleNode tempNode;
		Supplier tempSupplier;
		Organization tempOrganisation;
		QualificationProfile tempQualProfile;
		ArrayList<String> resourcesProduced;
		HashMap<Integer, ASNRoleNode> roleNodesWithID = new HashMap<Integer, ASNRoleNode>();

		// I think this is reliant on the ordering of the declared capabilities
		// to keep things straight....
		int i = 0;
		for (DeclaredCapability dc : this.declaredCapabilities) {

			tempNode = new ASNRoleNode();
			tempSupplier = new Supplier();
			tempOrganisation = new Organization();
			tempOrganisation.setName(dc.getOwningAgent());
			tempQualProfile = new QualificationProfile();
			tempSupplier.setOrganisation(tempOrganisation);

			supplierAssignment.put(tempNode, tempSupplier);

			resourcesProduced = new ArrayList<String>();

			for (ResourceSet s : dc.getResourcesProduced()) {
				// Ok my freinds this is just a little ugly :) Got to strip the
				// ()'s out + may as well reinsert the name space while here.
				String resProd = s.getResourceType().toString();
				resProd = resProd.substring(1, resProd.length() - 1);
				resProd = SuddenProductOntology.fullNameSpace + resProd;

				System.out.println(" ******* adding new product type "
						+ resProd);

				resourcesProduced.add(resProd);
			}

			/*
			 * TODO - note that, due to how the qualification profiles are
			 * currently defined, we lost a bit of info regarding which
			 * resources the company consumes in doing this.
			 * 
			 * Actually te attachment of the product individual URI isn't
			 * strictly accurate either! I've done it for clarity but may need
			 * to remove it later.
			 */
			tempQualProfile.setProductTypes(resourcesProduced);
			tempQualProfile.setProductIndividualURI(resourcesProduced.get(0));
			tempNode.setQualificationProfile(tempQualProfile);

			// So that I can fetch the nodes back out again
			roleNodesWithID
					.put(this.declaredCapabilities.indexOf(dc), tempNode);

			resultAbstract.addNewNode(tempNode);

			// TODO - the concrete profile needs something too :)

			i++;
		}

		// Now for arcs
		// Basically just add in the resource dependencies where they exist
		for (DeclaredCapability dc : this.declaredCapabilities) {
			for (ResourceSet resProduced : dc.getResourcesProduced()) {
				int resourceSetID = resProduced.getID();
				for (DeclaredCapability dcToTest : this.declaredCapabilities) {
					ResourceSet resToTest;
					boolean found = false;
					Iterator<ResourceSet> overResourcesToTest = dcToTest
							.getResourcesRequired().iterator();
					while (overResourcesToTest.hasNext() && !found) {
						resToTest = overResourcesToTest.next();
						if (resToTest.getID() == resourceSetID) {
							ASNMaterialDependency matDep = new ASNMaterialDependency();
							matDep
									.setSourceNode(roleNodesWithID
											.get(this.declaredCapabilities
													.indexOf(dc)));
							matDep.setTargetNode(roleNodesWithID
									.get(this.declaredCapabilities
											.indexOf(dcToTest)));

							resultAbstract.addNewDependcy(matDep);
							found = true;
						}
					}
				}
			}
		}

		ConcreteSupplyNetwork result = new ConcreteSupplyNetwork(
				resultAbstract, supplierAssignment);

		return result;
	}

	/**
	 * For getting the stateID from a resourceSetID
	 */
	public Integer getStateID(ResourceSet rs) {
		Integer result = null;
		Iterator<State> overStates = this.states.values().iterator();
		boolean found = false;

		while (overStates.hasNext() && !found) {
			State s = overStates.next();
			if (s.containsResourceSet(rs)) {
				result = s.getStateID();
				found = true;
			}
		}

		return result;
	}
}
