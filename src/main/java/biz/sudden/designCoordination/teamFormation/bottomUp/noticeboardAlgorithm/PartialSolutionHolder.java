package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsPrimitive;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.UnknownConstraintTypeException;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessIntrospector;

/**
 * @author mcassmc
 * 
 *         A class to hold a set of partial solutions - basically this is part
 *         of every noticeboard agent.
 * 
 *         This class is also responsible for managing the setting up of the
 *         initial solution.
 */

public class PartialSolutionHolder {

	PartialSolution initialSolution;
	public LinkedList<PartialSolution> partialSolutions;

	public PartialSolutionHolder(AbsConcept initialProcessInformation)
			throws CycleDetectedException, UnknownConstraintTypeException {
		/*
		 * Generate the initial partial solution
		 */
		this.initialSolution = generateInitialSolution(initialProcessInformation);
		// System.out.println(" Initial Solution " + initialSolution);

		partialSolutions = new LinkedList<PartialSolution>();
		initialSolution.setPartialSolutionID(System.currentTimeMillis());
		partialSolutions.add(initialSolution);
		// System.out.println(" Currently have " +
		// initialSolution.getAllPotentialExtensions().size() +
		// " potential extensions ");
	}

	/**
	 * 
	 * @param ps
	 * @throws EquivalentSolutionPresentException
	 * 
	 *             Adds a partial solution - throwing an error when an
	 *             equivalent solution is already present
	 */

	public void addPartialSolution(PartialSolution ps)
			throws EquivalentSolutionPresentException {

		if (!containsEquivalentSolution(ps)) {
			ps.setPartialSolutionID(System.currentTimeMillis());
			this.partialSolutions.add(ps);

			System.out.println(" ********** adding new partial solution "
					+ this.partialSolutions.size());
			System.out.println(ps);
		} else {
			throw new EquivalentSolutionPresentException(ps);
		}

	}

	/**
	 * @param initialProcessInformation
	 * @return theInitialProcessAsAPartialSolution
	 * 
	 *         Responsible for (1) mapping the constructs into the relevant Java
	 *         classes & (2) dealing with the extra information provided in the
	 *         initial solution spec.
	 * 
	 *         This consists of: (a) Declared capabilities which must be
	 *         included in the initial solution (corresponding to a constraint
	 *         that a given company must be used) (b) States which are inserted
	 *         in the initial solution (corresponding to a partial (or total)
	 *         decomposition of the initial problem into substates.).
	 * 
	 * @throws CycleDetectedException
	 * @throws UnknownConstraintTypeException
	 */
	private PartialSolution generateInitialSolution(
			AbsConcept initialProcessInformation)
			throws CycleDetectedException, UnknownConstraintTypeException {

		/*
		 * Partly done - converting states is now lifted to ProcessIntrospector
		 * at least. TODO - (Ok maybe this should be lifted to some external
		 * functions at some point.).
		 */

		PartialSolution result = new PartialSolution();

		// First do the initial/final states
		State initialState = ProcessIntrospector
				.makeConcreteState((AbsConcept) initialProcessInformation
						.getAbsObject(CreateNoticeboardOntology.INITIAL_PROCESS_INITIAL_STATE));
		State finalState = ProcessIntrospector
				.makeConcreteState((AbsConcept) initialProcessInformation
						.getAbsObject(CreateNoticeboardOntology.INITIAL_PROCESS_GOAL_STATE));
		result.setIntitialStates(initialState, finalState);

		/*
		 * Now add in all the intermediate states
		 */
		AbsAggregate intermediateStates = (AbsAggregate) initialProcessInformation
				.getAbsObject(CreateNoticeboardOntology.INITIAL_PROCESS_INTERMEDIATE_STATES_WITH_ORDER);
		if (intermediateStates != null) {
			result = addIntermediateStates(result, intermediateStates);
		}

		/*
		 * And finally the declared capabilities.
		 */
		AbsAggregate declaredCapabilities = (AbsAggregate) initialProcessInformation
				.getAbsObject(CreateNoticeboardOntology.INITIAL_PROCESS_STARTING_DECLARED_CAPABILITIES);

		if (declaredCapabilities != null) {
			result = addDeclaredCapabilities(result, declaredCapabilities);
		}

		System.out.println(" Initial Solution  " + result);

		return result;
	}

	/**
	 * Used when to generate a new partial solution and to check whether an
	 * equivalent partial solution already exists.
	 * 
	 * The return value is used for triggering subscriptions in the noticeboard
	 * agents.
	 * 
	 * @param extendPartialSolutionRequestContent
	 * @throws UnknownConstraintTypeException
	 */

	public PartialSolution getNewPartialSolution(DeclaredCapability dc,
			PotentialExtension pe,
			AbsConcept extendPartialSolutionRequestContent)
			throws EquivalentSolutionPresentException,
			UnknownConstraintTypeException {
		// System.out.println(" Potential Extension is " + pe);

		PartialSolution PSToExtend = this.getPartialSolution(pe
				.getPartialSolutionID());

		// //System.out.println(" ** " + PSToExtend.getPartialOrder());

		PartialSolution PSToExtendClone = PSToExtend.clone();

		// //System.out.println(PSToExtendClone.getPartialOrder());

		MatchedCapability mc = dc.matchToPotentialExtension(pe,
				extendPartialSolutionRequestContent);

		// //System.out.println(PSToExtendClone.getPartialOrder());

		try {
			PSToExtendClone.extendByMatchedDeclaredCapability(mc);
		} catch (CycleDetectedException e) {
			e.printStackTrace();
		}

		// //System.out.println(PSToExtendClone.getPartialOrder());

		/*
		 * Now do the resource type substitutions required - extracted from the
		 * AbsConcept
		 */

		if (containsEquivalentSolution(PSToExtendClone)) {
			throw new EquivalentSolutionPresentException(PSToExtendClone);
		}
		return PSToExtendClone;
	}

	/**
	 * 
	 * @param toExtendClone
	 * 
	 *            part of working out when to accept a proposed potential
	 *            extension or not
	 */

	private boolean containsEquivalentSolution(PartialSolution toExtendClone) {
		boolean matched = false;
		Iterator overPSs = this.partialSolutions.iterator();

		while (!matched && overPSs.hasNext()) {
			matched = ((PartialSolution) overPSs.next())
					.equivalentToPartialSolution(toExtendClone);
			// //System.out.println(matched);
		}
		return matched;
	}

	/**
	 * 
	 * @param result
	 * @param declaredCapabilities
	 * @return resultWithTheDeclaredCapabilitiesAddedIn
	 * @throws CycleDetectedException
	 * 
	 *             Adds a given set of declared capabilities into a partial
	 *             solution. This is normally only used when creating the
	 *             initial solution - the process agents typically control where
	 *             their porcesses match to the potential extensions in normal
	 *             operation.
	 * @throws UnknownConstraintTypeException
	 */

	private PartialSolution addDeclaredCapabilities(PartialSolution result,
			AbsAggregate declaredCapabilities) throws CycleDetectedException,
			UnknownConstraintTypeException {
		DeclaredCapability tempDeclaredCapability;
		MatchedCapability bestMatchedCapability;

		Iterator overStartingDeclaredCapabilities = declaredCapabilities
				.iterator();
		while (overStartingDeclaredCapabilities.hasNext()) {
			tempDeclaredCapability = ProcessIntrospector
					.makeConcreteDeclaredCapability((AbsConcept) overStartingDeclaredCapabilities
							.next());
			// And the extension (two lines hiding quite a lot :))
			bestMatchedCapability = result
					.getBestFittingPotentialExtension(tempDeclaredCapability);
			result.extendByMatchedDeclaredCapability(bestMatchedCapability);
		}
		return result;
	}

	/**
	 * 
	 * @param result
	 * @return result+IntermediateStates
	 * 
	 *         Adds in the intermediate states to the forminng initial solution
	 * @throws CycleDetectedException
	 */
	private PartialSolution addIntermediateStates(PartialSolution result,
			AbsAggregate intermediateStates) throws CycleDetectedException {

		AbsAggregate absStatesLessThan;
		AbsAggregate absStatesGreaterThan;
		AbsConcept intermediateStateWithOrder;
		State tempState;
		Set<Integer> statesLessThan;
		Set<Integer> statesGreaterThan;
		Integer tempInteger;
		int i;
		int numberOfStates = intermediateStates.size();
		Iterator overStatesLessThan;
		Iterator overStatesGreaterThan;

		for (i = 0; i < numberOfStates; i++) {
			intermediateStateWithOrder = (AbsConcept) intermediateStates.get(i);
			tempState = ProcessIntrospector
					.makeConcreteState((AbsConcept) intermediateStateWithOrder
							.getAbsObject(CreateNoticeboardOntology.STATE_WITH_ORDER_MAIN_STATE));

			absStatesLessThan = (AbsAggregate) intermediateStateWithOrder
					.getAbsObject(CreateNoticeboardOntology.STATE_WITH_ORDER_STATES_LESS_THAN);
			statesLessThan = new HashSet<Integer>();

			if (absStatesLessThan != null) {

				overStatesLessThan = absStatesLessThan.iterator();
				while (overStatesLessThan.hasNext()) {
					tempInteger = new Integer(
							((AbsPrimitive) overStatesLessThan.next())
									.getInteger());
					statesLessThan.add(tempInteger);
				}
			}

			absStatesGreaterThan = (AbsAggregate) intermediateStateWithOrder
					.getAbsObject(CreateNoticeboardOntology.STATE_WITH_ORDER_STATES_GREATER_THAN);
			statesGreaterThan = new HashSet<Integer>();

			if (absStatesGreaterThan != null) {

				overStatesGreaterThan = absStatesGreaterThan.iterator();
				while (overStatesGreaterThan.hasNext()) {
					tempInteger = new Integer(
							((AbsPrimitive) overStatesGreaterThan.next())
									.getInteger());
					statesGreaterThan.add(tempInteger);
				}
			}

			// This *seems* to be the right way round....
			result.addNewState(tempState, statesGreaterThan, statesLessThan);
		}

		return result;
	}

	public PartialSolution getInitialSolution() {
		return this.initialSolution;
	}

	public PartialSolution getPartialSolution(long partialSolutionID) {
		PartialSolution result = null;

		for (PartialSolution ps : partialSolutions) {
			if (ps.getPartialSolutionID() == partialSolutionID) {
				result = ps;
			}
		}

		return result;
	}

	/**
	 * @return allPotentialExtensions
	 * 
	 *         Get all of the potential extensions within this partial solution
	 *         holder (regardless of which partial solution contains them.).
	 */
	public Collection<PotentialExtension> getAllPotentialExtensions() {
		LinkedList<PotentialExtension> result = new LinkedList<PotentialExtension>();

		for (PartialSolution ps : partialSolutions) {
			for (PotentialExtension pe : ps.getAllPotentialExtensions()) {
				result.add(pe);
			}
		}

		return result;
	}

	@Override
	public String toString() {
		String result = "";
		result += "Partial Solutions \n ";
		for (PartialSolution ps : this.partialSolutions) {
			result += ps + "\n";
		}

		return result;
	}

	/**
	 * 
	 * Take a potential extension and make sure the resource sets have the right
	 * state information. Purely used in a technical sense within the algorithm
	 * - don't use without care.
	 * 
	 * This method is nessecary since the agents purposefully do NOT see the
	 * states underlying the potential extensions, and so the information is
	 * lost when it is externalised to the agents and returned to the
	 * noticeboard agent.
	 * 
	 * Whether this is a sensible design decision is less clear but it is at
	 * least a decision.
	 * 
	 * NB this method will *NOT* fail gracefully if something get maaligned and
	 * it tries to fetch out a resource which isn't in the given partial
	 * solution.
	 */
	public void regenerateStateInformation(PotentialExtension p) {
		PartialSolution partialSolutionToExtend = this.getPartialSolution(p
				.getPartialSolutionID());

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
}