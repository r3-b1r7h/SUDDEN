package biz.sudden.designCoordination.teamFormation.bottomUp.ontologies;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsPrimitive;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.onto.BasicOntology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.DeclaredCapability;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PotentialExtension;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.ResourceSet;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.State;

/**
 * 
 * @author mcassmc
 * 
 *         For converting things from/to concrete Java classes/Abs stuff for the
 *         process things.
 * 
 *         This is NOT an introspector in the classical Jade mold (ie not
 *         applied when decoding the messages, just afterwards.). The Abs stuff
 *         is still used for communication and indeed there is a deliberate
 *         split between the data structures used internally and those used
 *         externally.
 */

public class ProcessIntrospector {

	/**
	 * Make a concrete state
	 */
	public static State makeConcreteState(AbsConcept absState) {
		Collection<ResourceSet> stateResources = new ArrayList<ResourceSet>();
		AbsAggregate absResources = (AbsAggregate) absState
				.getAbsObject(ProcessOntology.STATE_RESOURCES_CONSTRAINED);
		Iterator it = absResources.iterator();
		AbsConcept tempAbsResourceSet;
		ResourceSet tempResourceSet;
		while (it.hasNext()) {
			tempAbsResourceSet = (AbsConcept) it.next();
			tempResourceSet = makeConcreteResourceSet(tempAbsResourceSet);
			stateResources.add(tempResourceSet);
		}
		State result = new State();
		result.addSeveralResourceSets(stateResources);
		return result;
	}

	/**
	 * Make a concrete declared capability
	 */
	public static DeclaredCapability makeConcreteDeclaredCapability(
			AbsConcept absDeclaredCapability) {
		Collection<ResourceSet> tempInputStateResources = new ArrayList<ResourceSet>();
		Collection<ResourceSet> tempOutputStateResources = new ArrayList<ResourceSet>();
		AbsAggregate resources;
		Iterator overResources;

		resources = (AbsAggregate) absDeclaredCapability
				.getAbsObject(ProcessOntology.DECLARED_CAPABILITY_INPUT);

		overResources = resources.iterator();
		while (overResources.hasNext()) {
			tempInputStateResources
					.add(makeConcreteResourceSet((AbsConcept) overResources
							.next()));
		}

		resources = (AbsAggregate) absDeclaredCapability
				.getAbsObject(ProcessOntology.DECLARED_CAPABILITY_OUTPUT);
		overResources = resources.iterator();
		while (overResources.hasNext()) {
			tempOutputStateResources
					.add(makeConcreteResourceSet((AbsConcept) overResources
							.next()));
		}

		String agentName = absDeclaredCapability
				.getString(ProcessOntology.DECLARED_CAPABILITY_PROVIDER);

		DeclaredCapability result = new DeclaredCapability(
				tempInputStateResources, tempOutputStateResources, agentName);

		return result;
	}

	/**
	 * Make a concrete resource set
	 */
	public static ResourceSet makeConcreteResourceSet(AbsConcept absResourceSet) {
		ResourceSet result = new ResourceSet((AbsConcept) absResourceSet
				.getAbsObject(ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE));
		result.setRelativeAmount(absResourceSet
				.getInteger(ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY));
		AbsPrimitive id = (AbsPrimitive) absResourceSet
				.getAbsTerm(ProcessOntology.CONSTRAINED_RESOURCE_SET_ID);

		if (id != null) {
			result.setResourceSetID(id.getInteger());
		}
		return result;
	}

	/**
	 * Make a concrete potential extension
	 */
	public static PotentialExtension makeConcretePotentialExtension(
			AbsConcept absPotentialExtension) {
		AbsAggregate resourceSetsForward = (AbsAggregate) absPotentialExtension
				.getAbsTerm(ProcessOntology.POTENTIAL_EXTENSION_RESOURCES_FREE);
		AbsAggregate resourceSetsBackwards = (AbsAggregate) absPotentialExtension
				.getAbsTerm(ProcessOntology.POTENTIAL_EXTENSION_RESOURCES_PRODUCED);

		Iterator overResourcesForward = resourceSetsForward.iterator();
		Iterator overResourcesBackwards = resourceSetsBackwards.iterator();

		// The hashmap part of this is trivial in this case - it's used for PE's
		// *WITHIN* the noticeboard which simply hold more information
		// than those externalised to the process agents.
		HashMap<ResourceSet, Integer> concreteResourceSetsForward = new HashMap<ResourceSet, Integer>();
		HashMap<ResourceSet, Integer> concreteResourceSetsBackwards = new HashMap<ResourceSet, Integer>();

		while (overResourcesForward.hasNext()) {
			concreteResourceSetsForward.put(ProcessIntrospector
					.makeConcreteResourceSet((AbsConcept) overResourcesForward
							.next()), new Integer(0));
		}

		while (overResourcesBackwards.hasNext()) {
			concreteResourceSetsBackwards
					.put(
							ProcessIntrospector
									.makeConcreteResourceSet((AbsConcept) overResourcesBackwards
											.next()), new Integer(0));
		}

		long partialSolutionID = absPotentialExtension
				.getLong(ProcessOntology.POTENTIAL_EXTENSION_PARTIAL_SOLUTION_ID);

		PotentialExtension result = new PotentialExtension(
				concreteResourceSetsForward, concreteResourceSetsBackwards,
				partialSolutionID);

		AbsAggregate agentsContributing = (AbsAggregate) absPotentialExtension
				.getAbsTerm(ProcessOntology.POTENTIAL_EXTENSION_CONTRIBUTING_AGENTS);

		Iterator overAgentsContributing = agentsContributing.iterator();

		LinkedList<String> concreteAgentNames = new LinkedList<String>();

		while (overAgentsContributing.hasNext()) {
			concreteAgentNames.add(((AbsPrimitive) overAgentsContributing
					.next()).getString());
		}

		result.setAgentsContributing(concreteAgentNames);

		return result;
	}

	/**
	 * PExtensions externalised
	 */
	public static AbsConcept makeAbstractPotentialExtension(
			PotentialExtension potentialExtensionIn) {
		AbsConcept result = new AbsConcept(ProcessOntology.POTENTIAL_EXTENSION);

		AbsAggregate resourcesRequiredAbs = new AbsAggregate(SL0Vocabulary.SET);
		Collection<ResourceSet> resourcesRequiredConcrete = potentialExtensionIn
				.getResourceSetsForwards().keySet();

		for (ResourceSet r : resourcesRequiredConcrete) {
			resourcesRequiredAbs.add(ProcessIntrospector
					.makeAbstractResourceSet(r));
		}

		AbsAggregate resourcesProducedAbs = new AbsAggregate(SL0Vocabulary.SET);
		Collection<ResourceSet> resourcesProducedConcrete = potentialExtensionIn
				.getResourceSetsBackwards().keySet();

		for (ResourceSet r : resourcesProducedConcrete) {
			resourcesProducedAbs.add(ProcessIntrospector
					.makeAbstractResourceSet(r));
		}

		AbsAggregate agentsInvolvedAbs = new AbsAggregate(SL0Vocabulary.SET);
		Collection<String> agentsInvolved = potentialExtensionIn
				.getAgentsContributing();

		for (String s : agentsInvolved) {
			agentsInvolvedAbs.add(AbsPrimitive.wrap(s));
		}

		result.set(ProcessOntology.POTENTIAL_EXTENSION_PARTIAL_SOLUTION_ID,
				AbsPrimitive.wrap(potentialExtensionIn.getPartialSolutionID()));
		result.set(ProcessOntology.POTENTIAL_EXTENSION_RESOURCES_FREE,
				resourcesRequiredAbs);
		result.set(ProcessOntology.POTENTIAL_EXTENSION_RESOURCES_PRODUCED,
				resourcesProducedAbs);
		result.set(ProcessOntology.POTENTIAL_EXTENSION_CONTRIBUTING_AGENTS,
				agentsInvolvedAbs);
		return result;
	}

	// And Resource Sets
	public static AbsConcept makeAbstractResourceSet(ResourceSet r) {
		AbsConcept result = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		// result.set(ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY,AbsPrimitive.wrap(r.getRelativeAmount()));
		// TODO - a theoretically temporary thing to avoid null pointer issues
		// in Sudden. It doesn't use
		// this info in meaningful sense anyway
		result.set(ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY,
				AbsPrimitive.wrap(1));
		result.set(ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE, r
				.getResourceType());
		result.set(ProcessOntology.CONSTRAINED_RESOURCE_SET_ID, AbsPrimitive
				.wrap(r.getID()));
		return result;
	}

	// And DC's
	public static AbsConcept makeAbstractDeclaredCapability(DeclaredCapability c) {
		AbsConcept result = new AbsConcept(ProcessOntology.DECLARED_CAPABILITY);
		AbsAggregate resourcesInput = new AbsAggregate(SL0Vocabulary.SET);
		AbsAggregate resourcesOutput = new AbsAggregate(SL0Vocabulary.SET);

		for (ResourceSet r : c.getResourcesRequired()) {
			resourcesInput.add(ProcessIntrospector.makeAbstractResourceSet(r));
		}

		for (ResourceSet r : c.getResourcesProduced()) {
			resourcesOutput.add(ProcessIntrospector.makeAbstractResourceSet(r));
		}

		result.set(ProcessOntology.DECLARED_CAPABILITY_INPUT, resourcesInput);
		result.set(ProcessOntology.DECLARED_CAPABILITY_OUTPUT, resourcesOutput);

		result.set(ProcessOntology.DECLARED_CAPABILITY_PROVIDER, AbsPrimitive
				.wrap(c.getOwningAgent()));

		return result;
	}
}
