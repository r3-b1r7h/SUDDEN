package biz.sudden.designCoordination.teamFormation.bottomUp.tests;

import jade.content.ContentManager;
import jade.content.abs.AbsAgentAction;
import jade.content.abs.AbsConcept;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.NoticeboardCombinedOntology;

/**
 * @author mcassmc
 * 
 *         A class created purely for the sake of testing the creation of
 *         noticeboards
 */

public class TestCreateNoticeboardAgent extends Agent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		// Send a message to create a noticeboard
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		// TODO - which constant *IS* this?
		message.setLanguage("fipa-sl");
		message.setOntology(NoticeboardCombinedOntology.ONTOLOGY_NAME);

		message.addReceiver(new AID(
				TestCreateNoticeboard.noticeboardFactoryAgentName,
				AID.ISLOCALNAME));

		AbsAgentAction action = new AbsAgentAction(SL0Vocabulary.ACTION);

		AbsConcept absAID = new AbsConcept(SL0Vocabulary.AID);
		absAID.set(SL0Vocabulary.AID_NAME,
				TestCreateNoticeboard.noticeboardFactoryAgentName);

		action.set(SL0Vocabulary.ACTION_ACTOR, absAID);

		// Noticeboard Description
		AbsConcept noticeboardDescription = TestNoticeboardDescriptions
				.getNoticeboardDescription();

		// Initial Process

		AbsConcept initialProcess = TestNoticeboardDescriptions
				.getInitialProcessDescription();
		// All combined!

		AbsAgentAction createNewNoticeboard = new AbsAgentAction(
				CreateNoticeboardOntology.CREATE_NOTICEBOARD);
		createNewNoticeboard.set(
				CreateNoticeboardOntology.CREATE_NOTICEBOARD_DESCRIPTION,
				noticeboardDescription);
		createNewNoticeboard.set(
				CreateNoticeboardOntology.CREATE_NOTICEBOARD_INITIAL_PROCESS,
				initialProcess);

		action.set(SL0Vocabulary.ACTION_ACTION, createNewNoticeboard);

		ContentManager cm = getContentManager();
		cm.registerLanguage(new SLCodec());
		cm.registerOntology(NoticeboardCombinedOntology.getInstance());
		try {
			cm.fillContent(message, action);
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}

		send(message);
		// addBehaviour(new echo(this));
	}

	@SuppressWarnings("serial")
	protected class echo extends CyclicBehaviour {

		public echo(TestCreateNoticeboardAgent agent) {
			super(agent);
		}

		@Override
		public void action() {
			ACLMessage msgReceived = myAgent.receive();
			if (msgReceived == null) {
				block();
				return;
			} else {
				System.out.println(" Received Message " + msgReceived);
			}

		}

	}

}
