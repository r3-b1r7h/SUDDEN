package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.ConstrainedComparisonAnswer;
import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.ConstrainedTypeComparator;
import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.UnknownConstraintTypeException;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessIntrospector;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessOntology;

/**
 * 
 * This class contains the information within a declared capability - as
 * serialised to AbsConcepts and back again.
 * 
 * Further it provides a function to calculate the Matched capability resulting
 * from applying this declared capability to a given potential extension.
 * 
 * 
 * @author mcassmc
 * 
 */

public class DeclaredCapability {

	// two sets of resource sets
	private Collection<ResourceSet> resourcesRequired;
	private Collection<ResourceSet> resourcesProduced;

	// The contact point - to be made an AID later
	private String owningAgent;

	public DeclaredCapability(Collection<ResourceSet> resourcesRequired,
			Collection<ResourceSet> resourcesProduced, String agentName) {
		this.resourcesRequired = resourcesRequired;
		this.resourcesProduced = resourcesProduced;
		this.owningAgent = agentName;
	}

	// TODO - store the AID surely?
	public String getOwningAgent() {
		return this.owningAgent;
	}

	public int getHowManyResourcesRequired() {
		return this.resourcesRequired.size();
	}

	public int getHowManyResourcesProduced() {
		return this.resourcesProduced.size();
	}

	public Collection<ResourceSet> getResourcesProduced() {
		return this.resourcesProduced;
	}

	public Collection<ResourceSet> getResourcesRequired() {
		return this.resourcesRequired;
	}

	public ResourceSet getResourceSetProduced(int stateID) {
		ResourceSet result = null;
		for (ResourceSet r : resourcesProduced) {
			if (r.getID() == stateID) {
				result = r;

			}
		}
		return result;
	}

	public ResourceSet getResourceSetRequired(int stateID) {
		ResourceSet result = null;
		for (ResourceSet r : resourcesRequired) {
			if (r.getID() == stateID) {
				result = r;

			}
		}
		return result;
	}

	/**
	 * Matches us to a given potential extension and outputs a corresponding
	 * matched potential extension.
	 * 
	 * NB This call is now only used when creating a new noticeboard. Otherwise
	 * the process provider agent has provided us with the required mappings
	 * etc.
	 * 
	 * @throws UnknownConstraintTypeException
	 */
	public MatchedCapability matchToPotentialExtension(PotentialExtension potExt)
			throws UnknownConstraintTypeException {
		MatchedCapability result = new MatchedCapability();

		// Match this pair
		Iterator overOurResourcesRequired = resourcesRequired.iterator();
		HashMap<ResourceSet, Integer> resourcesFreeInPotentialExtension = potExt
				.getResourceSetsForwards();

		// And this pair
		Iterator overOurResourcesProduced = resourcesProduced.iterator();
		HashMap<ResourceSet, Integer> resourcesRequiredInPotentialExtension = potExt
				.getResourceSetsBackwards();

		/*
		 * remainder = things we need/produce but which aren't
		 * provided/required. The others are defined naturally. Essentailly we
		 * partition the set of resources into: (a) Those we need/produced and
		 * are free/required (they're matched) (b) Those that are in the
		 * potential extension but aren't needed/produced by the declared
		 * capability (these are OK) (c) Those in the declared capability but
		 * not the potential extension. (these are what we need to find a
		 * potential extension minimising).
		 */
		LinkedList<ResourceSet> matchedResourcesForward = new LinkedList<ResourceSet>();
		LinkedList<ResourceSet> unmatchedResourcesForward = new LinkedList<ResourceSet>();
		LinkedList<ResourceSet> remainderForward = new LinkedList<ResourceSet>();

		LinkedList<ResourceSet> matchedResourcesBackwards = new LinkedList<ResourceSet>();
		LinkedList<ResourceSet> unmatchedResourcesBackwards = new LinkedList<ResourceSet>();
		LinkedList<ResourceSet> remainderBackwards = new LinkedList<ResourceSet>();

		ResourceSet tempOurResource;
		ResourceSet tempResourceToMatch = null;

		Iterator overResourcesToMatch;

		while (overOurResourcesRequired.hasNext()) {
			tempOurResource = (ResourceSet) overOurResourcesRequired.next();

			overResourcesToMatch = resourcesFreeInPotentialExtension.keySet()
					.iterator();

			boolean matched = false;
			ConstrainedComparisonAnswer howWellMatched;

			while (overResourcesToMatch.hasNext() && !matched) {
				tempResourceToMatch = (ResourceSet) overResourcesToMatch.next();
				// Ok we find out if it's more/less general/merely comparable
				// here but don't use the information currently.
				howWellMatched = ConstrainedTypeComparator
						.compareConstrainedTypes(tempOurResource
								.getResourceType(), tempResourceToMatch
								.getResourceType());
				matched = !(howWellMatched == ConstrainedComparisonAnswer.INCOMPARIBLE_WITH);
			}
			if (!matched) {
				remainderForward.add(tempOurResource);
			} else if (matched) {
				System.out.println(" Adding in matched resource forwards "
						+ tempResourceToMatch
						+ " from state "
						+ resourcesFreeInPotentialExtension
								.get(tempResourceToMatch));
				matchedResourcesForward.add(tempResourceToMatch);
				// To align the declared capabilitie ID's with those of the
				// resource they've matched to
				tempOurResource.setResourceSetID(tempResourceToMatch.getID());
			}
		}
		/*
		 * And now to pick up the other remainder - I could use set based
		 * commands here but I won't be able to once the constraints/types etc
		 * are added in. No doubt the algorithm can be optimised a bit here.
		 */
		overResourcesToMatch = resourcesFreeInPotentialExtension.keySet()
				.iterator();

		while (overResourcesToMatch.hasNext()) {
			tempResourceToMatch = (ResourceSet) overResourcesToMatch.next();
			boolean matched = false;
			overOurResourcesRequired = resourcesRequired.iterator();
			while (overOurResourcesRequired.hasNext() && !matched) {
				tempOurResource = (ResourceSet) overOurResourcesRequired.next();
				// Ok we find out if it's more/less general/merely comparable
				// here but don't use the information currently.
				matched = !(ConstrainedTypeComparator.compareConstrainedTypes(
						tempOurResource.getResourceType(), tempResourceToMatch
								.getResourceType()) == ConstrainedComparisonAnswer.INCOMPARIBLE_WITH);
			}
			if (!matched) {
				unmatchedResourcesForward.add(tempResourceToMatch);
			}
		}

		/*
		 * And now in the other direction!
		 */

		while (overOurResourcesProduced.hasNext()) {
			tempOurResource = (ResourceSet) overOurResourcesProduced.next();

			overResourcesToMatch = resourcesRequiredInPotentialExtension
					.keySet().iterator();

			boolean matched = false;
			while (overResourcesToMatch.hasNext() && !matched) {
				tempResourceToMatch = (ResourceSet) overResourcesToMatch.next();
				// Currently just check for compatibility (code tells us more -
				// ie is it more/less general)
				matched = !(ConstrainedTypeComparator.compareConstrainedTypes(
						tempOurResource.getResourceType(), tempResourceToMatch
								.getResourceType()) == ConstrainedComparisonAnswer.INCOMPARIBLE_WITH);
			}
			if (!matched) {
				remainderBackwards.add(tempOurResource);
			}
			if (matched) {
				System.out.println(" Adding in matched resource backwards "
						+ tempResourceToMatch
						+ " from state "
						+ resourcesRequiredInPotentialExtension
								.get(tempResourceToMatch));
				matchedResourcesBackwards.add(tempResourceToMatch);
				// To align the declared capabilitie ID's with those of the
				// resource they've matched to
				tempOurResource.setResourceSetID(tempResourceToMatch.getID());
			}
		}
		/*
		 * And now to pick up the other remainder - I could use set based
		 * commands here but I won't be able to once the constraints/types etc
		 * are added in. No doubt the algorithm can be optimised a bit here.
		 */
		overResourcesToMatch = resourcesRequiredInPotentialExtension.keySet()
				.iterator();

		while (overResourcesToMatch.hasNext()) {
			tempResourceToMatch = (ResourceSet) overResourcesToMatch.next();
			boolean matched = false;
			overOurResourcesRequired = resourcesProduced.iterator();
			while (overOurResourcesRequired.hasNext() && !matched) {
				tempOurResource = (ResourceSet) overOurResourcesRequired.next();
				// Ok we find out if it's more/less general/merely comparable
				// here but don't use the information currently.
				matched = !(ConstrainedTypeComparator.compareConstrainedTypes(
						tempOurResource.getResourceType(), tempResourceToMatch
								.getResourceType()) == ConstrainedComparisonAnswer.INCOMPARIBLE_WITH);
			}
			if (!matched) {
				unmatchedResourcesBackwards.add(tempResourceToMatch);
			}
		}
		result.setPotentialExtension(potExt);
		result.SetDeclaredCapabilityMatched(this);
		result.setMatchedResourcesBackwards(matchedResourcesBackwards);
		result.setMatchedResourcesForward(matchedResourcesForward);
		result.setRemainderBackwards(remainderBackwards);
		result.setRemainderForward(remainderForward);
		result.setUnmatchedResourcesBackwards(unmatchedResourcesBackwards);
		result.setUnmatchedResourcesForward(unmatchedResourcesForward);

		System.out.println(" Matched Capadiblity generated " + result);

		return result;
	}

	/**
	 * Matches us to a given potential extension and outputs a corresponding
	 * matched potential extension.
	 * 
	 * Used when matching in an extension request from an agent.
	 * 
	 * @throws UnknownConstraintTypeException
	 */
	public MatchedCapability matchToPotentialExtension(
			PotentialExtension potExt, AbsConcept extensionDetails)
			throws UnknownConstraintTypeException {

		System.out
				.println(" Regenerating matched capability sent by process provider agent from "
						+ potExt);

		MatchedCapability result = new MatchedCapability();

		AbsAggregate matchedForward = (AbsAggregate) extensionDetails
				.getAbsTerm(ProcessOntology.EXTEND_PARTIAL_SOLUTION_RESOURCES_CONSUMED);
		AbsAggregate matchedBackwards = (AbsAggregate) extensionDetails
				.getAbsTerm(ProcessOntology.EXTEND_PARTIAL_SOLUTION_RESOURCES_PRODUCED);

		// Match this pair
		Iterator overOurResourcesRequired = resourcesRequired.iterator();
		HashMap<ResourceSet, Integer> resourcesFreeInPotentialExtension = potExt
				.getResourceSetsForwards();

		// And this pair
		Iterator overOurResourcesProduced = resourcesProduced.iterator();
		HashMap<ResourceSet, Integer> resourcesRequiredInPotentialExtension = potExt
				.getResourceSetsBackwards();

		/*
		 * remainder = things we need/produce but which aren't
		 * provided/required. The others are defined naturally. Essentailly we
		 * partition the set of resources into: (a) Those we need/produced and
		 * are free/required (they're matched) (b) Those that are in the
		 * potential extension but aren't needed/produced by the declared
		 * capability (these are OK) (c) Those in the declared capability but
		 * not the potential extension. (these are what we need to find a
		 * potential extension minimising).
		 */
		LinkedList<ResourceSet> matchedResourcesForward = new LinkedList<ResourceSet>();
		LinkedList<ResourceSet> unmatchedResourcesForward = new LinkedList<ResourceSet>();
		LinkedList<ResourceSet> remainderForward = new LinkedList<ResourceSet>();

		LinkedList<ResourceSet> matchedResourcesBackwards = new LinkedList<ResourceSet>();
		LinkedList<ResourceSet> unmatchedResourcesBackwards = new LinkedList<ResourceSet>();
		LinkedList<ResourceSet> remainderBackwards = new LinkedList<ResourceSet>();

		ResourceSet tempOurResource;

		Iterator overResourcesMatchedForward;
		int stateIDToCheck = -99;
		AbsConcept matchedResource = null;

		while (overOurResourcesRequired.hasNext()) {
			tempOurResource = (ResourceSet) overOurResourcesRequired.next();
			System.out.println(" ** " + tempOurResource);

			overResourcesMatchedForward = matchedForward.iterator();

			boolean matched = false;
			while (overResourcesMatchedForward.hasNext() && !matched) {
				matchedResource = (AbsConcept) overResourcesMatchedForward
						.next();
				stateIDToCheck = matchedResource
						.getInteger(ProcessOntology.MATCHED_RESOURCE_RESOURCEID);
				matched = (stateIDToCheck == tempOurResource.getID());
			}

			if (!matched) {
				remainderForward.add(tempOurResource);
			} else if (matched) {
				tempOurResource.setResourceSetID(stateIDToCheck);

				/*
				 * Must get the state this came from
				 */

				matchedResourcesForward.add(tempOurResource);

			}
		}

		/*
		 * And now to pick up the other remainder - I could use set based
		 * commands here but I won't be able to once the constraints/types etc
		 * are added in. No doubt the algorithm can be optimised a bit here.
		 */
		Iterator overResourcesToMatch = resourcesFreeInPotentialExtension
				.keySet().iterator();
		ResourceSet tempResourceToMatch;

		while (overResourcesToMatch.hasNext()) {
			tempResourceToMatch = (ResourceSet) overResourcesToMatch.next();
			boolean matched = false;

			overOurResourcesRequired = matchedForward.iterator();
			while (overOurResourcesRequired.hasNext() && !matched) {
				matchedResource = (AbsConcept) overOurResourcesRequired.next();
				stateIDToCheck = matchedResource
						.getInteger(ProcessOntology.MATCHED_RESOURCE_RESOURCEID);
				matched = (stateIDToCheck == tempResourceToMatch.getID());
			}
			if (!matched) {
				unmatchedResourcesForward.add(tempResourceToMatch);
			}
		}

		/*
		 * And now in the other direction!
		 */

		Iterator overResourcesMatchedBackwards;

		while (overOurResourcesProduced.hasNext()) {
			tempOurResource = (ResourceSet) overOurResourcesProduced.next();

			overResourcesMatchedBackwards = matchedBackwards.iterator();

			boolean matched = false;
			while (overResourcesMatchedBackwards.hasNext() && !matched) {
				matchedResource = (AbsConcept) overResourcesMatchedBackwards
						.next();
				stateIDToCheck = matchedResource
						.getInteger(ProcessOntology.MATCHED_RESOURCE_RESOURCEID);
				matched = (stateIDToCheck == tempOurResource.getID());
			}

			if (!matched) {
				remainderBackwards.add(tempOurResource);
			} else if (matched) {
				tempOurResource.setResourceSetID(stateIDToCheck);
				matchedResourcesBackwards.add(tempOurResource);
			}
		}
		/*
		 * And now to pick up the other remainder - I could use set based
		 * commands here but I won't be able to once the constraints/types etc
		 * are added in. No doubt the algorithm can be optimised a bit here.
		 */
		overResourcesToMatch = resourcesRequiredInPotentialExtension.keySet()
				.iterator();

		while (overResourcesToMatch.hasNext()) {
			tempResourceToMatch = (ResourceSet) overResourcesToMatch.next();
			boolean matched = false;

			overOurResourcesRequired = matchedBackwards.iterator();
			while (overOurResourcesRequired.hasNext() && !matched) {
				matchedResource = (AbsConcept) overOurResourcesRequired.next();
				stateIDToCheck = matchedResource
						.getInteger(ProcessOntology.MATCHED_RESOURCE_RESOURCEID);
				matched = (stateIDToCheck == tempResourceToMatch.getID());
			}
			if (!matched) {
				unmatchedResourcesBackwards.add(tempResourceToMatch);
			}
		}
		/*
		 * Add in the replacement resources
		 */
		Iterator overMatchedResourcesForwards = matchedForward.iterator();
		Iterator overMatchedResourcesBackwards = matchedForward.iterator();

		AbsConcept tempMatchedResource;
		AbsConcept replacementResource;
		boolean matched = false;
		while (overMatchedResourcesForwards.hasNext()) {
			tempMatchedResource = (AbsConcept) overMatchedResourcesForwards
					.next();

			replacementResource = (AbsConcept) tempMatchedResource
					.getAbsObject(ProcessOntology.MATCHED_RESOURCE_REPLACEMENT_RESOURCE);
			int i = tempMatchedResource
					.getInteger(ProcessOntology.MATCHED_RESOURCE_RESOURCEID);

			for (ResourceSet r : potExt.resourcesForward.keySet()) {
				if (r.getID() == i) {
					System.out.println(" Adding replacement mapping from " + r
							+ " to " + replacementResource);
					result.addReplacementResource(r, ProcessIntrospector
							.makeConcreteResourceSet(replacementResource));
				}
			}
		}

		while (overMatchedResourcesBackwards.hasNext()) {
			tempMatchedResource = (AbsConcept) overMatchedResourcesBackwards
					.next();

			replacementResource = (AbsConcept) tempMatchedResource
					.getAbsObject(ProcessOntology.MATCHED_RESOURCE_REPLACEMENT_RESOURCE);
			int i = tempMatchedResource
					.getInteger(ProcessOntology.MATCHED_RESOURCE_RESOURCEID);
			for (ResourceSet r : potExt.resourcesBackwards.keySet()) {
				if (r.getID() == i) {
					System.out.println(" Adding replacement mapping from " + r
							+ " to " + replacementResource);
					result.addReplacementResource(r, ProcessIntrospector
							.makeConcreteResourceSet(replacementResource));
				}
			}

		}

		result.setPotentialExtension(potExt);
		result.SetDeclaredCapabilityMatched(this);
		result.setMatchedResourcesBackwards(matchedResourcesBackwards);
		result.setMatchedResourcesForward(matchedResourcesForward);
		result.setRemainderBackwards(remainderBackwards);
		result.setRemainderForward(remainderForward);
		result.setUnmatchedResourcesBackwards(unmatchedResourcesBackwards);
		result.setUnmatchedResourcesForward(unmatchedResourcesForward);

		System.out.println(" Produced matched capability " + result);

		return result;
	}

	@Override
	public String toString() {
		String result = "";
		result += " Declared Capability ";
		result += " resources required: ";
		Iterator overResReq = resourcesRequired.iterator();
		while (overResReq.hasNext()) {
			result += overResReq.next();
		}

		result += " resources produced: ";
		Iterator overResPro = resourcesProduced.iterator();
		while (overResPro.hasNext()) {
			result += overResPro.next();
		}

		result += " owner " + owningAgent;

		return result;
	}
}
