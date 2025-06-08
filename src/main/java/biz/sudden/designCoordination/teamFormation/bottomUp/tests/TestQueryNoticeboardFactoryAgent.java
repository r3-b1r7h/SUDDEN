package biz.sudden.designCoordination.teamFormation.bottomUp.tests;

import jade.content.ContentManager;
import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsIRE;
import jade.content.abs.AbsPredicate;
import jade.content.abs.AbsPrimitive;
import jade.content.abs.AbsVariable;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.lang.sl.SL1Vocabulary;
import jade.content.lang.sl.SL2Vocabulary;
import jade.content.lang.sl.SLCodec;
import jade.content.lang.sl.SLOntology;
import jade.content.lang.sl.SLVocabulary;
import jade.content.onto.BasicOntology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ConstrainedProductOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.NoticeboardCombinedOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryForNoticeboards;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryOntology;

@SuppressWarnings("serial")
public class TestQueryNoticeboardFactoryAgent extends Agent {

	@Override
	public void setup() {
		addBehaviour(new echo(this));
		addBehaviour(new sendInitialQuery(this));
	}

	protected class echo extends CyclicBehaviour {

		public echo(Agent agent) {
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

	protected class sendInitialQuery extends OneShotBehaviour {

		public sendInitialQuery(Agent a) {
			super(a);
		}

		@Override
		public void action() {
			ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);
			msg.setLanguage("fipa-sl");
			msg.setOntology(NoticeboardCombinedOntology.ONTOLOGY_NAME);
			AID noticeBoardFactory = new AID(
					TestCreateNoticeboard.noticeboardFactoryAgentName,
					AID.ISLOCALNAME);
			msg.addReceiver(noticeBoardFactory);

			AbsVariable x = new AbsVariable("x",
					CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION);

			ContentManager cm = myAgent.getContentManager();
			cm.registerLanguage(new SLCodec());
			cm.registerOntology(NoticeboardCombinedOntology.getInstance());

			AbsIRE ire = new AbsIRE(SL2Vocabulary.ALL);

			AbsPredicate produces = new AbsPredicate(
					QueryOntology.PRODUCES_ABSTRACT_TYPES);
			AbsAggregate types = new AbsAggregate(SL0Vocabulary.SET);
			types.add(AbsPrimitive.wrap(ConstrainedProductOntology.PRODUCT));

			produces.set(QueryOntology.LIST_OF_TYPES, types);
			produces.set(QueryOntology.OBJECT, x);

			AbsPredicate producesB = new AbsPredicate(
					QueryOntology.REQUIRES_ABSTRACT_TYPES);
			AbsAggregate typesB = new AbsAggregate(SL0Vocabulary.SET);
			typesB.add(AbsPrimitive.wrap(ConstrainedProductOntology.PRODUCT));

			producesB.set(QueryOntology.LIST_OF_TYPES, typesB);
			producesB.set(QueryOntology.OBJECT, x);

			AbsPredicate requestedByCompany = new AbsPredicate(
					QueryForNoticeboards.REQUIRED_BY_COMPANY);
			requestedByCompany.set(
					QueryForNoticeboards.REQUIRED_BY_COMPANY_NAME, AbsPrimitive
							.wrap("ExampleCompany"));
			requestedByCompany.set(QueryOntology.OBJECT, x);

			AbsPredicate or = new AbsPredicate(SL1Vocabulary.OR);
			or.set(SL1Vocabulary.OR_LEFT, produces);
			or.set(SL1Vocabulary.OR_RIGHT, producesB);

			AbsPredicate and = new AbsPredicate(SL1Vocabulary.AND);
			and.set(SL1Vocabulary.AND_LEFT, or);
			and.set(SL1Vocabulary.AND_RIGHT, requestedByCompany);

			AbsPredicate not = new AbsPredicate(SL1Vocabulary.NOT);
			AbsPredicate isNoticeboard = new AbsPredicate(
					QueryForNoticeboards.NOTICEBOARD);
			isNoticeboard.set(QueryOntology.OBJECT, x);
			not.set(SL1Vocabulary.NOT_WHAT, isNoticeboard);

			AbsPredicate andMore = new AbsPredicate(SL1Vocabulary.AND);
			andMore.set(SL1Vocabulary.AND_LEFT, isNoticeboard);
			andMore.set(SL1Vocabulary.AND_RIGHT, and);

			ire.setVariable(x);
			ire.setProposition(andMore);

			try {
				cm.fillContent(msg, ire);
			} catch (CodecException e) {
				e.printStackTrace();
			} catch (OntologyException e) {
				e.printStackTrace();
			}

			send(msg);
		}

	}
}
