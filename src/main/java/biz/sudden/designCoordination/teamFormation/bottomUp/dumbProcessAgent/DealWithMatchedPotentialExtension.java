package biz.sudden.designCoordination.teamFormation.bottomUp.dumbProcessAgent;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsContentElement;
import jade.content.abs.AbsPrimitive;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.lang.sl.SLOntology;
import jade.content.onto.BasicOntology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.UnknownConstraintTypeException;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.DeclaredCapability;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.MatchedCapability;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PotentialExtension;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.ResourceSet;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessIntrospector;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessOntology;

/**
 * 
 * @author mcassmc
 * 
 *         Responsible for dealing with potentially intersting potential
 *         extensions when they've been located.Basically this involves finding
 *         which of the matched potential extensions is the best fit and
 *         proposing the declared capability as an extension to it.
 */

public class DealWithMatchedPotentialExtension extends OneShotBehaviour {

	private ACLMessage matchedPEMessage;

	public DealWithMatchedPotentialExtension(Agent a, ACLMessage messageIn) {
		super(a);
		this.matchedPEMessage = messageIn;
	}

	@Override
	public void action() {
		try {
			AbsContentElement content = myAgent.getContentManager()
					.extractAbsContent(matchedPEMessage);
			AbsAggregate matchedPEs = (AbsAggregate) content
					.getAbsObject(SL0Vocabulary.EQUALS_RIGHT);

			System.out.println(matchedPEs);

			Iterator overMatchedPEs = matchedPEs.iterator();

			LinkedList<PotentialExtension> PEsToMatch = new LinkedList<PotentialExtension>();

			while (overMatchedPEs.hasNext()) {
				AbsConcept nextPEToConvert = (AbsConcept) overMatchedPEs.next();
				// System.out.println(" Abs Potential Extension " +
				// nextPEToConvert);
				PotentialExtension tempPEToAdd = ProcessIntrospector
						.makeConcretePotentialExtension(nextPEToConvert);
				// System.out.println(" Made concrete " + tempPEToAdd);
				PEsToMatch.add(tempPEToAdd);
			}

			/*
			 * Locate best fit. In principle this could be pretty complex :)
			 * Here it isn't!
			 */

			MatchedCapability bestFit = getBestFittingPotentialExtension(
					((DumbProcessAgent) myAgent).getDeclaredCapability(),
					PEsToMatch);

			// This can be null when dealing with all quantifiers.
			if (bestFit != null) {
				informNoticeboardAgentOfMatch(bestFit);
			}

		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (UnknownConstraintTypeException e) {
			// 
			e.printStackTrace();
		}

	}

	private void informNoticeboardAgentOfMatch(MatchedCapability bestFit) {
		ACLMessage msg = matchedPEMessage.createReply();

		msg.setPerformative(ACLMessage.REQUEST);

		Action a = new Action();
		a.setActor((AID) (msg.getAllIntendedReceiver().next()));

		// TODO - make this work :)

		// System.out.println(bestFit.getPotentialExtension());

		AbsConcept potentialExtension = ProcessIntrospector
				.makeAbstractPotentialExtension(bestFit.getPotentialExtension());

		// System.out.println(potentialExtension);

		AbsConcept extensionRequest = new AbsConcept(
				ProcessOntology.EXTEND_PARTIAL_SOLUTION);
		extensionRequest.set(
				ProcessOntology.EXTEND_PARTIAL_SOLUTION_DECLARED_CAPABILITY,
				ProcessIntrospector.makeAbstractDeclaredCapability(bestFit
						.getDeclaredCapabilityMatched()));
		extensionRequest.set(
				ProcessOntology.EXTEND_PARTIAL_SOLUTION_POTENTIAL_EXTENSION,
				potentialExtension);

		ArrayList<ResourceSet> resourcesConsumed = new ArrayList(bestFit
				.getMatchedResourcesForward());
		ArrayList<ResourceSet> matchedResourcesProduced = new ArrayList(bestFit
				.getMatchedResourcesBackwards());

		AbsAggregate extendedResourcesForward = new AbsAggregate(
				SL0Vocabulary.SET);
		AbsAggregate extendedResourcesBackwards = new AbsAggregate(
				SL0Vocabulary.SET);

		DeclaredCapability dcUsed = bestFit.getDeclaredCapabilityMatched();

		// TODO - informing of replacement types here.

		for (ResourceSet r : resourcesConsumed) {
			AbsConcept nextMatchedResource = new AbsConcept(
					ProcessOntology.MATCHED_RESOURCE);
			nextMatchedResource.set(
					ProcessOntology.MATCHED_RESOURCE_RESOURCEID, AbsPrimitive
							.wrap(r.getID()));
			nextMatchedResource.set(
					ProcessOntology.MATCHED_RESOURCE_REPLACEMENT_RESOURCE,
					ProcessIntrospector.makeAbstractResourceSet(dcUsed
							.getResourceSetRequired(r.getID())));
			extendedResourcesForward.add(nextMatchedResource);
		}

		for (ResourceSet r : matchedResourcesProduced) {
			// TODO - I refuse to believe that this can be null, but it seems as
			// if can somehow get that way
			if (r.getResourceType() != null) {
				AbsConcept nextMatchedResource = new AbsConcept(
						ProcessOntology.MATCHED_RESOURCE);
				nextMatchedResource.set(
						ProcessOntology.MATCHED_RESOURCE_RESOURCEID,
						AbsPrimitive.wrap(r.getID()));
				nextMatchedResource.set(
						ProcessOntology.MATCHED_RESOURCE_REPLACEMENT_RESOURCE,
						ProcessIntrospector.makeAbstractResourceSet(dcUsed
								.getResourceSetProduced(r.getID())));
				extendedResourcesBackwards.add(nextMatchedResource);
			}
		}

		extensionRequest.set(
				ProcessOntology.EXTEND_PARTIAL_SOLUTION_RESOURCES_CONSUMED,
				extendedResourcesForward);
		extensionRequest.set(
				ProcessOntology.EXTEND_PARTIAL_SOLUTION_RESOURCES_PRODUCED,
				extendedResourcesBackwards);

		a.setAction(extensionRequest);

		System.out.println(" &&&&&&&&&& Extension request " + extensionRequest);

		try {
			myAgent.getContentManager().fillContent(msg, a);
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
		myAgent.send(msg);
		// System.out.println(msg);
	}

	/**
	 * Hummmmmmmmmmm.
	 * 
	 * @throws UnknownConstraintTypeException
	 */

	public MatchedCapability getBestFittingPotentialExtension(
			DeclaredCapability dc, Collection<PotentialExtension> PExtns)
			throws UnknownConstraintTypeException {
		MatchedCapability result = null;

		/*
		 * Get the potential extension with the best fit from our list
		 * 
		 * ie minimise the forwards and backwards remainders.
		 * 
		 * This is important to ensure that complete solutions are actually
		 * spotted once formed :)
		 */

		Iterator overPotentialExtensions = PExtns.iterator();
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
		PotentialExtension tempPotentialExtension;

		// System.out.println(" --------------------------------------------------------- ");
		// System.out.println(" At agent " + myAgent.getLocalName());
		// System.out.println(" matching to DC " + dc);

		while (overPotentialExtensions.hasNext()) {
			tempPotentialExtension = (PotentialExtension) overPotentialExtensions
					.next();
			// System.out.println(" Matching to PE " + tempPotentialExtension);
			tempMatchedCapability = dc
					.matchToPotentialExtension(tempPotentialExtension);

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
			// System.out.println(" remainder sizes now backwards " +
			// backwardsRemainderSize + " forwards " + forwardsRemainderSize);
		}
		// System.out.println(" Chosen PE " + result.getPotentialExtension());
		// System.out.println(" --------------------------------------------------------- ");

		return result;
	}

}
