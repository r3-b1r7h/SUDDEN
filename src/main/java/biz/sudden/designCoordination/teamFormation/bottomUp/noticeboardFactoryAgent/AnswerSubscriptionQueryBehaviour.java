package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent;

import jade.content.ContentManager;
import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsIRE;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.onto.BasicOntology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Equals;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Collection;

import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.CompiledIRE;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.NoMatchesLocatedException;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.TooManyMatchesForIOTAException;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.UnknownIRETypeException;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.predicateNotUnderstoodError;

/**
 * 
 * @author mcassmc
 * 
 *         Responsible for answering subscription queries about the noticeobards
 *         registered within this agent.
 * 
 *         As such it uses the IRE interpreter and the classes within this
 *         package.
 * 
 *         When doing a subscription, any initial matches are returned directly
 *         and any future matches are returned later. No error is thrown for
 *         finding no initial matches.
 */

@SuppressWarnings("serial")
public class AnswerSubscriptionQueryBehaviour extends OneShotBehaviour {

	private ACLMessage queryIn;
	private NoticeBoardFactoryAgent myNBFAgent;

	public AnswerSubscriptionQueryBehaviour(NoticeBoardFactoryAgent myAgent,
			ACLMessage Query) {
		super(myAgent);
		myNBFAgent = myAgent;
		this.queryIn = Query;
	}

	@Override
	public void action() {
		/*
		 * Quite straightforward: (1) Compile the query
		 * 
		 * (2) Store it in a data structure together with the original message
		 * 
		 * (3) Whenever a new noticeboard is added each of the subscriptions is
		 * then checked and any answers returned. This bit doesn't even truly
		 * need to use behaviours - java calls attached to the noticeboard
		 * factory agent will do fine.
		 * 
		 * (4) Send an inform back that the subscription has been created.
		 * 
		 * In the end I haven't checked for IOTA subscriptions. I haven't done
		 * anything really smart like catching when they've hit > 1 *distinct*
		 * result over time either though :) (An iota subscription is: give me
		 * the value of this GIVEN object as it changes over time.).
		 * 
		 * TODO - Iota subscriptions properly? I guess on finding one result I'd
		 * promptly have to check to see if I can find > 1 result. (As long as
		 * always only one result we're ok.).
		 */

		// We have our query intepreter so simply use it, catching the
		// appropiate errors :)
		ContentManager cm = myNBFAgent.getContentManager();
		AbsIRE IREfromQuery = null;
		CompiledIRE queryCompiled = null;

		try {
			IREfromQuery = (AbsIRE) cm.extractAbsContent(queryIn);
			queryCompiled = myNBFAgent.noticeBoardQueryFactory
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

		if ((queryCompiled != null) && (IREfromQuery != null)) {

			/*
			 * Having checked with FIPA when adding Subscriptionsyou also return
			 * an answer containing every current match.
			 */
			getCurrentMatches(IREfromQuery, queryCompiled);

			/*
			 * Add to the list of 'current' subscriptions
			 */
			myNBFAgent.addSubscription(queryCompiled, queryIn);
		}
	}

	/*
	 * Responsible for getting any current matches & sending appropiate answers.
	 */
	@SuppressWarnings("unchecked")
	private void getCurrentMatches(AbsIRE IREfromQuery,
			CompiledIRE queryCompiled) {
		Collection<AbsConcept> matchedResults;
		try {
			/*
			 * So we've compiled the query OK. Get results and unless there's an
			 * abberant number (>1 for an iota & 0 for iota/any) reply with the
			 * answer we get.
			 */

			matchedResults = queryCompiled
					.findMatches(myNBFAgent.NoticeboardDescriptions);
			Equals equals = new Equals();
			equals.setLeft(IREfromQuery);
			AbsAggregate results = new AbsAggregate(SL0Vocabulary.SET);
			for (AbsConcept nextNBDesc : matchedResults) {
				results.add(nextNBDesc);
			}
			equals.setRight(results);

			ACLMessage replySuccess = queryIn.createReply();
			replySuccess.setPerformative(ACLMessage.INFORM_REF);
			myNBFAgent.getContentManager().fillContent(replySuccess, equals);
			myNBFAgent.send(replySuccess);

		} catch (TooManyMatchesForIOTAException e) {
			// TODO - would ideally tell them *why* we've failed here
			ACLMessage reply = queryIn.createReply();
			reply.setPerformative(ACLMessage.FAILURE);
			reply.setContent(queryIn.getContent());
			myNBFAgent.send(reply);

		} catch (NoMatchesLocatedException e) {
			// Silently fail - with a subscription might find a match later :)
		}
		// These things again :)
		catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}

}
