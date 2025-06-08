package biz.sudden.designCoordination.teamFormation.bottomUp.dumbProcessAgent;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsContentElement;
import jade.content.abs.AbsIRE;
import jade.content.abs.AbsPredicate;
import jade.content.abs.AbsPrimitive;
import jade.content.abs.AbsVariable;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.lang.sl.SL1Vocabulary;
import jade.content.lang.sl.SL2Vocabulary;
import jade.content.lang.sl.SLOntology;
import jade.content.onto.BasicOntology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Iterator;
import java.util.LinkedList;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.DeclaredCapability;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.ResourceSet;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.NoticeboardCombinedOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryForPotentialExtensions;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryOntology;

/**
 * 
 * @author mcassmc
 * 
 *         Responsible for dealing with the situation when a noticeboard has
 *         been located matching the decsription given (here any noticeboard.).
 * 
 *         Once this has happened we subscribe to the noticeboard with a query
 *         based on our declared capability, but also containing a predicate
 *         meaning we never contribute to the same solution more than once.
 *         (this is used in place of potentially more subtle ways to stop the
 *         agents continually proposing to eg produce something. These might be
 *         preferable but are hard and context dependent.).
 */

public class DealWithMatchedNoticeboard extends OneShotBehaviour {

	private ACLMessage queryIn;

	public DealWithMatchedNoticeboard(Agent a, ACLMessage queryMessage) {
		super(a);
		queryIn = queryMessage;
	}

	@Override
	public void action() {
		try {
			AbsContentElement content = myAgent.getContentManager()
					.extractAbsContent(queryIn);

			AbsAggregate matchedNoticeboardAgentDescriptions = (AbsAggregate) content
					.getAbsObject(SL0Vocabulary.EQUALS_RIGHT);

			Iterator overNoticeboardDescriptions = matchedNoticeboardAgentDescriptions
					.iterator();

			LinkedList<AID> noticeboardsToContact = new LinkedList<AID>();
			while (overNoticeboardDescriptions.hasNext()) {
				AbsConcept nbDesc = (AbsConcept) overNoticeboardDescriptions
						.next();
				AbsConcept AIDd = (AbsConcept) nbDesc
						.getAbsObject(CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_ADDRESS);
				/*
				 * Insert test against utility information for the noticeboard
				 * here! You'd also have to decide which process to suggest here
				 * if we had any choice etc :)
				 */

				// TODO - this AID conversion is over simplistic in a general
				// case :)
				String agentName = AIDd.getString(SL0Vocabulary.AID_NAME);
				AID nextAID = new AID(agentName, AID.ISGUID);

				noticeboardsToContact.add(nextAID);
			}

			/*
			 * Ok now we contact them. We'll assume we do a simple forwards or
			 * backwards chaining request based on our agents declared
			 * capability
			 */
			AbsIRE queryToMake = makeQueryFromOurDC();

			for (AID nextAID : noticeboardsToContact) {
				sendSubscription(nextAID, queryToMake);
			}

		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}

	}

	private void sendSubscription(AID nextAID, AbsIRE queryToMake) {

		ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);
		msg.setLanguage("fipa-sl");
		msg.setOntology(NoticeboardCombinedOntology.ONTOLOGY_NAME);
		msg.addReceiver(nextAID);
		msg.setReplyWith(DumbProcessAgent.queryForPotentialExtensions);

		try {
			myAgent.getContentManager().fillContent(msg, queryToMake);
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
		myAgent.send(msg);
	}

	/*
	 * I forgot to have in the not already containing us clause! If you leave
	 * that out when combined with processes producing based on 'nothing' you
	 * get far too many daft partial solutions to bear :) Ok so intelligent
	 * agents would also do this. Or simply using backwards chaining only.
	 */
	private AbsIRE makeQueryFromOurDC() {
		DeclaredCapability dc = ((DumbProcessAgent) myAgent)
				.getDeclaredCapability();

		/*
		 * Lets do and OR query based on producing what we produce/requiring
		 * what we require. EG forwards/backwards chaining. I'll keep it down to
		 * abstract types.
		 */
		AbsAggregate resourcesRequired = new AbsAggregate(SL0Vocabulary.SET);

		for (ResourceSet rs : dc.getResourcesRequired()) {
			resourcesRequired.add(AbsPrimitive.wrap(rs.getResourceType()
					.getTypeName()));
		}

		AbsAggregate resourcesProduced = new AbsAggregate(SL0Vocabulary.SET);

		for (ResourceSet rs : dc.getResourcesProduced()) {
			resourcesProduced.add(AbsPrimitive.wrap(rs.getResourceType()
					.getTypeName()));
		}

		AbsIRE result = new AbsIRE(SL2Vocabulary.ALL);

		AbsVariable x = new AbsVariable("x",
				ProcessOntology.POTENTIAL_EXTENSION);
		result.setVariable(x);

		AbsPredicate producesTest = new AbsPredicate(
				QueryOntology.PRODUCES_ABSTRACT_TYPES);
		producesTest.set(QueryOntology.LIST_OF_TYPES, resourcesProduced);
		producesTest.set(QueryOntology.OBJECT, x);

		AbsPredicate requiresTest = new AbsPredicate(
				QueryOntology.REQUIRES_ABSTRACT_TYPES);
		requiresTest.set(QueryOntology.LIST_OF_TYPES, resourcesRequired);
		requiresTest.set(QueryOntology.OBJECT, x);

		AbsPredicate or = new AbsPredicate(SL1Vocabulary.OR);
		// or.set(SLOntology.OR_LEFT,requiresTest);
		// TODO - remove this horrible hack which limits to backwards chaining.
		// (fix
		// algorithm first mind ;))
		or.set(SL1Vocabulary.OR_LEFT, producesTest);

		or.set(SL1Vocabulary.OR_RIGHT, producesTest);

		// Ensure we don't contribute twice to the same PSoln (ugly I admit -
		// see above).
		AbsPredicate weContribute = new AbsPredicate(
				QueryForPotentialExtensions.CONTAINS_POTENTIAL_EXTENSIONS_FROM);
		AbsAggregate companyNames = new AbsAggregate(SL0Vocabulary.SET);

		companyNames.add(AbsPrimitive.wrap(myAgent.getLocalName()));
		weContribute.set(QueryForPotentialExtensions.COMPANIES, companyNames);

		AbsPredicate not = new AbsPredicate(SL1Vocabulary.NOT);
		not.set(SL1Vocabulary.NOT_WHAT, weContribute);

		AbsPredicate and = new AbsPredicate(SL1Vocabulary.AND);
		and.set(SL1Vocabulary.AND_LEFT, or);
		and.set(SL1Vocabulary.AND_RIGHT, not);

		result.setProposition(and);

		System.out.println(" Subsciribing with " + result);

		return result;
	}

}
