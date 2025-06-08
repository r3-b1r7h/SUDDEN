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
import jade.core.Agent;
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
 *         This behaviour is responsible for answering queries about which
 *         noticeboards are present at the given noticeboard factory agent.
 * 
 *         As such it has to interpret queries written as IRE's in SL. Ideally
 *         it would be a completely free form IRE interpreter - however this is
 *         likely to prove an unrealistic goal.
 * 
 *         The predicates used & the form of the predicates are listed in the
 *         design documentation.
 */

@SuppressWarnings("serial")
public class AnswerQueryForNoticeboardsBehaviour extends OneShotBehaviour {

	private ACLMessage queryIn;
	private NoticeBoardFactoryAgent myNBFAgent;

	public AnswerQueryForNoticeboardsBehaviour(Agent myAgent, ACLMessage Query) {
		super(myAgent);
		this.queryIn = Query;
		myNBFAgent = (NoticeBoardFactoryAgent) myAgent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void action() {

		// We have our query intepreter so simply use it, catching the
		// appropiate errors :)
		ContentManager cm = myNBFAgent.getContentManager();
		AbsIRE IREfromQuery = null;
		CompiledIRE queryCompiled = null;
		Collection<AbsConcept> matchedResults;

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

		// I don't want to have my catches too badly nested.
		if ((queryCompiled != null) && (IREfromQuery != null)) {
			try {
				/*
				 * So we've compiled the query OK. Get results and unless
				 * there's an abberant number (>1 for an iota & 0 for iota/any)
				 * reply with the answer we get.
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
				myNBFAgent.getContentManager()
						.fillContent(replySuccess, equals);
				myNBFAgent.send(replySuccess);

			} catch (TooManyMatchesForIOTAException e) {
				// TODO - would ideally tell them *why* we've failed here
				ACLMessage reply = queryIn.createReply();
				reply.setPerformative(ACLMessage.FAILURE);
				reply.setContent(queryIn.getContent());
				myNBFAgent.send(reply);

			} catch (NoMatchesLocatedException e) {
				// TODO - would ideally tell them *why* we've failed here
				System.out.println(" no matches");
				ACLMessage reply = queryIn.createReply();
				reply.setPerformative(ACLMessage.FAILURE);
				reply.setContent(queryIn.getContent());
				myNBFAgent.send(reply);
			}
			// These things again :)
			catch (CodecException e) {
				e.printStackTrace();
			} catch (OntologyException e) {
				e.printStackTrace();
			}
		}

	}
}
