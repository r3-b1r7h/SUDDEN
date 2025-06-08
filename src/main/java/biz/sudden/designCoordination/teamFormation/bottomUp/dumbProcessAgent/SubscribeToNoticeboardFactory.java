package biz.sudden.designCoordination.teamFormation.bottomUp.dumbProcessAgent;

import jade.content.ContentManager;
import jade.content.abs.AbsIRE;
import jade.content.abs.AbsPredicate;
import jade.content.abs.AbsVariable;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SL2Vocabulary;
import jade.content.lang.sl.SLCodec;
import jade.content.lang.sl.SLVocabulary;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.NoticeboardCombinedOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryForNoticeboards;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.tests.TestCreateNoticeboard;

/**
 * 
 * @author mcassmc
 * 
 *         For subscribing to noticeboard factories to be told of any new
 *         noticeboards -> these agents wish to hear about ANY new noticeboard
 *         created.
 */

public class SubscribeToNoticeboardFactory extends OneShotBehaviour {

	DumbProcessAgent myDPAgent;

	public SubscribeToNoticeboardFactory(DumbProcessAgent agent) {
		super(agent);
		myDPAgent = agent;
	}

	/**
	 * Do the subscription based on the information from the agents declared
	 * capability
	 * 
	 * In fact I can't think of how to make a useful query up. Maybe for a demo
	 * a query as to some kind of area. (or all projects producing say a
	 * watertank?!)
	 * 
	 * I'll keep it trivial here.
	 */
	@Override
	public void action() {

		ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);
		msg.setLanguage("fipa-sl");
		msg.setOntology(NoticeboardCombinedOntology.ONTOLOGY_NAME);
		AID noticeBoardFactory = new AID(
				TestCreateNoticeboard.noticeboardFactoryAgentName,
				AID.ISLOCALNAME);

		// For indentifying the reply!
		msg.setReplyWith(DumbProcessAgent.queryForNoticeboards);

		msg.addReceiver(myDPAgent.getNBFAID());

		AbsVariable x = new AbsVariable("x",
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION);

		ContentManager cm = myAgent.getContentManager();
		cm.registerLanguage(new SLCodec());
		cm.registerOntology(NoticeboardCombinedOntology.getInstance());

		/*
		 * The actual query - very simple in this case.
		 * 
		 * TODO - should I be feeding this as a parameter to allow for fancier
		 * behaviour more easily?
		 */
		AbsIRE ire = new AbsIRE(SL2Vocabulary.ALL);

		AbsPredicate isNoticeboard = new AbsPredicate(
				QueryForNoticeboards.NOTICEBOARD);
		isNoticeboard.set(QueryOntology.OBJECT, x);

		ire.setVariable(x);
		ire.setProposition(isNoticeboard);

		try {
			cm.fillContent(msg, ire);
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}

		myAgent.send(msg);

	}

}
