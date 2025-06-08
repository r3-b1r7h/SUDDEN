package biz.sudden.designCoordination.teamFormation.bottomUp.noticeBoardAgent;

import jade.content.ContentManager;
import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsIRE;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.onto.BasicOntology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Equals;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Collection;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PotentialExtension;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessIntrospector;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.CompiledIRE;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.NoMatchesLocatedException;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.TooManyMatchesForIOTAException;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.UnknownIRETypeException;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.predicateNotUnderstoodError;

/**
 * 
 * @author mcassmc
 * 
 *         This behaviour is responsible for answering queries about which
 *         potential extensions are present in a given noticeboard agent. When a
 *         subscription request is received the initial matches are returned and
 *         the subscription remembered and matched against all future potential
 *         extensions.
 */

@SuppressWarnings("serial")
public class AnswerSubscriptionQueriesForPotentialExtensionsBehaviour extends
		OneShotBehaviour {

	private ACLMessage queryIn;
	private NoticeBoardAgent myNBAgent;

	public AnswerSubscriptionQueriesForPotentialExtensionsBehaviour(
			NoticeBoardAgent myAgent, ACLMessage Query) {
		super(myAgent);
		this.queryIn = Query;
		myNBAgent = myAgent;
	}

	// TODO - currently this returns *all* matched potential extensions ->
	// should perhaps only return
	// one per state I think?

	@SuppressWarnings("unchecked")
	@Override
	public void action() {

		// We have our query intepreter so simply use it, catching the
		// appropiate errors :)
		ContentManager cm = myNBAgent.getContentManager();
		AbsIRE IREfromQuery = null;
		CompiledIRE queryCompiled = null;
		Collection<PotentialExtension> matchedResults;

		try {
			IREfromQuery = (AbsIRE) cm.extractAbsContent(queryIn);
			queryCompiled = myNBAgent.ourPEQFactory
					.generateCompiledIRE(IREfromQuery);
		}
		// These two simply shouldn't happen - message content has already been
		// decoded once!
		catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
		// This one I (reasonably!) choose to consider vanishingly unlikely to
		// occur
		catch (UnknownIRETypeException e) {
			e.printStackTrace();
		}
		// This one on the other hand is plausible - send a not_understood reply
		catch (predicateNotUnderstoodError e) {
			ACLMessage reply = queryIn.createReply();
			reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
			try {
				cm.fillContent(reply, e.predicateNotUnderstood);
				myAgent.send(reply);
			}
			// These CAN'T happen - trying to decode the predicate will already
			// have triggered an error.
			catch (CodecException e1) {
				e1.printStackTrace();
			} catch (OntologyException e1) {
				e1.printStackTrace();
			}
		}

		// I don't want to have my catches too badly nested.
		if ((queryCompiled != null) && (IREfromQuery != null)) {
			/*
			 * So we've compiled the query OK. Get results and unless there's an
			 * abberant number (>1 for an iota & 0 for iota/any) reply with the
			 * answer we get.
			 */

			// Inform of any matches located
			getCurrentMatches(IREfromQuery, queryCompiled);

			// Add in as a subscription
			myNBAgent.addSubscription(queryCompiled, queryIn);
		}

	}

	private void getCurrentMatches(AbsIRE IREfromQuery,
			CompiledIRE queryCompiled) {
		Collection<PotentialExtension> matchedResults;
		try {

			matchedResults = queryCompiled
					.findMatches(myNBAgent.ourPartialSolutionHolder
							.getAllPotentialExtensions());

			if (matchedResults.size() > 0) {
				Equals equals = new Equals();
				equals.setLeft(IREfromQuery);
				AbsAggregate results = new AbsAggregate(SL0Vocabulary.SET);
				for (PotentialExtension potentialExtension : matchedResults) {
					results
							.add(ProcessIntrospector
									.makeAbstractPotentialExtension(potentialExtension));
				}
				equals.setRight(results);

				ACLMessage replySuccess = queryIn.createReply();
				replySuccess.setPerformative(ACLMessage.INFORM_REF);
				myNBAgent.getContentManager().fillContent(replySuccess, equals);
				myNBAgent.send(replySuccess);
			}
		} catch (TooManyMatchesForIOTAException e) {
			// TODO - would ideally tell them *why* we've failed here
			ACLMessage reply = queryIn.createReply();
			reply.setPerformative(ACLMessage.FAILURE);
			reply.setContent(queryIn.getContent());
			myNBAgent.send(reply);

		} catch (NoMatchesLocatedException e) {
			// TODO - would ideally tell them *why* we've failed here
			System.out.println(" no matches");
			ACLMessage reply = queryIn.createReply();
			reply.setPerformative(ACLMessage.FAILURE);
			reply.setContent(queryIn.getContent());
			myNBAgent.send(reply);
		}
		// These things again :)
		catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}
}
