package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsContentElement;
import jade.content.abs.AbsPredicate;
import jade.content.abs.AbsPrimitive;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.onto.BasicOntology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.Iterator;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeBoardAgent.NoticeBoardAgent;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;

/**
 * @author mcassmc
 * 
 *         Triggered by some agent sending the factory agent a request to
 *         produce a new noticeboard.
 * 
 *         Responsible for (1) Creating the noticeboard agent, (2) storing it's
 *         description (3)informing the agent that it's done it; (4) triggering
 *         the subscriptions to check which match the new noticeboard.
 */

@SuppressWarnings("serial")
public class CreateNoticeBoardBehaviour extends OneShotBehaviour {

	private static int noticeBoardNumber;

	// So that each noticeboard agent gets a different name.
	private ACLMessage makeNoticeBoard;

	private NoticeBoardFactoryAgent myNBFAgent;

	public CreateNoticeBoardBehaviour(NoticeBoardFactoryAgent myAgent,
			ACLMessage createNoticeBoardRequest) {
		super(myAgent);
		this.makeNoticeBoard = createNoticeBoardRequest;
		noticeBoardNumber = 0;
		myNBFAgent = myAgent;
	}

	@Override
	public void action() {

		block(100);
		block(100);
		block(100);
		block(100);
		block(100);

		// query here is an action object containing our AID & a create
		// noticeboard object
		AbsContentElement queryContent = null;
		AbsConcept noticeboardDescription = null;
		AbsConcept noticeboardInitialProcess = null;

		try {
			queryContent = myAgent.getContentManager().extractAbsContent(
					makeNoticeBoard);
			// Amazing how long the lines in these fetches often get
			noticeboardDescription = (AbsConcept) queryContent.getAbsObject(
					SL0Vocabulary.ACTION_ACTION).getAbsObject(
					CreateNoticeboardOntology.CREATE_NOTICEBOARD_DESCRIPTION);
			noticeboardInitialProcess = (AbsConcept) queryContent
					.getAbsObject(SL0Vocabulary.ACTION_ACTION)
					.getAbsObject(
							CreateNoticeboardOntology.CREATE_NOTICEBOARD_INITIAL_PROCESS);
		}
		// *can't* throw these due to Jade's structures
		catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}

		/*
		 * TODO - check if we do accept this request (may refuse if such a
		 * noticeboard already exists)
		 */
		// If we do - tell them
		ACLMessage acceptRequest = makeNoticeBoard.createReply();
		acceptRequest.setPerformative(ACLMessage.AGREE);
		acceptRequest.setContent(makeNoticeBoard.getContent());
		myAgent.send(acceptRequest);

		/*
		 * First complete the description
		 */

		// Hum not very safe this! Mind you if it works currently it's hardly
		// likely to break in the near future
		Integer timeOfCreation = new Integer(new Long((int) System
				.currentTimeMillis()).intValue());
		String localNoticeboardName = "NoticeBoard_number_" + noticeBoardNumber;
		AID noticeboardAID = new AID(localNoticeboardName, AID.ISLOCALNAME);

		// A sad neccesity
		AbsConcept absAID = new AbsConcept(SL0Vocabulary.AID);
		absAID.set(SL0Vocabulary.AID_NAME, noticeboardAID.getName());

		String tempAddress;
		AbsAggregate addresses = new AbsAggregate(SL0Vocabulary.SEQUENCE);
		Iterator overAddresses = noticeboardAID.getAllAddresses();
		while (overAddresses.hasNext()) {
			tempAddress = (String) overAddresses.next();
			addresses.add(AbsPrimitive.wrap(tempAddress));
		}

		absAID.set(SL0Vocabulary.AID_ADDRESSES, addresses);

		noticeboardDescription.set(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_TIME_CREATED,
				AbsPrimitive.wrap(timeOfCreation.intValue()));
		noticeboardDescription.set(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_ADDRESS,
				absAID);
		noticeboardDescription
				.set(
						CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_GOAL_STATE,
						noticeboardInitialProcess
								.getAbsObject(CreateNoticeboardOntology.INITIAL_PROCESS_GOAL_STATE));
		noticeboardDescription
				.set(
						CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_INITIAL_STATE,
						noticeboardInitialProcess
								.getAbsObject(CreateNoticeboardOntology.INITIAL_PROCESS_INITIAL_STATE));

		/*
		 * Next store it
		 */
		((NoticeBoardFactoryAgent) myAgent).NoticeboardDescriptions
				.add(noticeboardDescription);

		/*
		 * Now create a corresponding agent. This will currently always put the
		 * noticeboard on the same platform as the factory.
		 */
		AgentContainer ac = myAgent.getContainerController();
		Object[] agentFile = new Object[3];
		// Feed in the noticeboard description to start things off
		agentFile[0] = noticeboardDescription;
		agentFile[1] = noticeboardInitialProcess;
		// / this last param is new....
		// / FIXME ! *** however teh NBA seems to receive more params????
		agentFile[2] = myAgent;
		// I have strong doubts this code is ever called!!!
		// @see NoticeBoardFactoryAgent.createNoticeboard
		AgentController newNoticeboardAgent = myNBFAgent.jadeService
				.startAgent(localNoticeboardName, NoticeBoardAgent.class
						.getCanonicalName(), agentFile);
		// Wait until the new agent becomes idle
		try {
			while (newNoticeboardAgent.getState().getCode() != jade.wrapper.AgentState.cAGENT_STATE_IDLE) {
			}
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

		noticeBoardNumber++;

		/*
		 * Tell them we're done TODO - does this trigger whether or not we've
		 * suceeeded? If so this is bad.
		 */
		AbsPredicate done = new AbsPredicate(SL0Vocabulary.DONE);
		done.set(SL0Vocabulary.DONE_ACTION, queryContent);
		ACLMessage informDone = makeNoticeBoard.createReply();
		try {
			myAgent.getContentManager().fillContent(informDone, done);
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
		informDone.setPerformative(ACLMessage.INFORM);
		myAgent.send(informDone);

		/*
		 * TODO Now trigger the subscriptions to see if we get a match
		 */
		myNBFAgent.checkSubscriptionsAgainst(noticeboardDescription);

	}
}
