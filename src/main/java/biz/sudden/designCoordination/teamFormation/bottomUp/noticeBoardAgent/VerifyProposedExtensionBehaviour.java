package biz.sudden.designCoordination.teamFormation.bottomUp.noticeBoardAgent;

import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.behaviours.DataStore;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.proto.ProposeInitiator;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.DeclaredCapability;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.EquivalentSolutionPresentException;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PartialSolution;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PotentialExtension;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent.NoticeBoardFactoryAgent;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ShapeGraphOntology;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;

/**
 * 
 * @author mcassmc
 * 
 *         This class is responsible for coordinating the negotiation as to
 *         whether a proposed extension is acceptable or not.
 * 
 *         The protocol is highly simple - each agent already in the potential
 *         extension is contacted and if they do not send a refusal within a
 *         given time period the extension is accepted.
 * 
 */
@SuppressWarnings("serial")
public class VerifyProposedExtensionBehaviour extends ProposeInitiator {

	public static final String VERIFY_PROPOSED_EXTENSION_PROTOCOL = "Verify_Proposed_Extension";
	// not sure how long this was 100 was maybe = to 1 second (or 100?)
	public static final int TIME_OUT_PERIOD = 100;

	private long ourID;

	private NoticeBoardAgent myNBAgent;

	// What would happen if the thing we're verifying was added.
	private PartialSolution newSolution;

	private ACLMessage messageIn;

	private DeclaredCapability dc;

	public VerifyProposedExtensionBehaviour(NoticeBoardAgent myNBAgent,
			ACLMessage messageIn, DeclaredCapability dc, PotentialExtension p,
			PartialSolution newSolution) {
		super(myNBAgent, getInitialMessage(messageIn, p), new DataStore());
		this.newSolution = newSolution;
		this.ourID = System.currentTimeMillis();
		this.myNBAgent = myNBAgent;
		this.messageIn = messageIn;
		this.dc = dc;
		registerHandleAllResponses(new CheckResponses());
		// originally CheckResponses had these parameters: myNBAgent,newSolution
		// however it is an inner class and can access the member parameters
		// directly.
		// so we do this - to avoid confusion.
	}

	private static ACLMessage getInitialMessage(ACLMessage messageIn,
			PotentialExtension p) {
		ACLMessage result = new ACLMessage(ACLMessage.PROPOSE);

		// Reuse content from the original request for us to do an extension
		// Now we're proposing that we'll do it to lots of people.
		result.setContent(messageIn.getContent());

		// Protocol
		result.setProtocol(VERIFY_PROPOSED_EXTENSION_PROTOCOL);

		// Now add in all the responders
		Collection<String> agentsToContact = p.getAgentsContributing();

		// time out
		long time = System.currentTimeMillis()
				+ VerifyProposedExtensionBehaviour.TIME_OUT_PERIOD;

		result.setReplyByDate(new Date(time));

		for (String agentName : agentsToContact) {
			// TODO - obviously only works for local names :) I should I think
			// really be passing full AID's around.
			result.addReceiver(new AID(agentName, AID.ISLOCALNAME));
		}

		// System.out.println(" sending initial message " + result);

		// This seems to break when no agents need to be contacted

		return result;
	}

	protected class CheckResponses extends OneShotBehaviour {

		public CheckResponses() {
			super(myNBAgent);
		}

		@Override
		public void action() {

			// System.out.println(" checking responses to extension request");

			Vector responses = (Vector) getDataStore()
					.get(HANDLE_ALL_RESPONSES);

			boolean allAccept = true;
			if (responses != null) {
				Iterator overResponses = responses.iterator();

				while (overResponses.hasNext() && allAccept) {
					ACLMessage nextMessage = (ACLMessage) overResponses.next();
					allAccept = (nextMessage.getPerformative() == ACLMessage.ACCEPT_PROPOSAL);
				}
			}

			if (allAccept || (responses == null)) {

				// Note we're checking for equivalence *both* here and earlier
				// before starting negotiations
				// this is due to timing effects (new solutions added during our
				// negotiations.).
				try {
					myNBAgent.ourPartialSolutionHolder
							.addPartialSolution(newSolution);
					// no graphic panels in SUddEN
					// myNBAgent.ourGraphics.addPartialSolution(newSolution);
					// DO NOT modify the casefile manually here!!! just keep it
					// as reference
					// DUE to the asynchronous call it needs to be retrieved
					// again from the DB befor updateing it
					myNBAgent.myFactoryAgent.addPartialSolution(
							myNBAgent.ourCaseFile, myNBAgent.ourCSN,
							myNBAgent.ourRoleNode, newSolution);

					sendAcceptance();

					// Check the subscriptions
					myNBAgent.checkSubscriptionsAgainst(newSolution);

					if (newSolution.isCompleteSolution()) {
						ConcreteSupplyNetwork newlyFoundCSN = myNBAgent.myFactoryAgent
								.convertToCSN(newSolution);
						System.out
								.println(" *****************************  new complete solution located "
										+ newSolution);
						System.out.println(" **** Converted to ASN "
								+ newlyFoundCSN);
						System.out.println(" Current partial solutions "
								+ myNBAgent.ourPartialSolutionHolder);

						System.out.println(" Converted to a shape graph "
								+ ShapeGraphOntology.getInstance().fromObject(
										newSolution.convertToShapeGraph()));

						// DO NOT modify the casefile manually here!!! just keep
						// it as reference
						// DUE to the asynchronous call it needs to be retrieved
						// again from the DB befor updateing it
						NoticeBoardFactoryAgent.addCompleteSolution(
								myNBAgent.ourCaseFile, myNBAgent.ourCSN,
								myNBAgent.ourRoleNode, newlyFoundCSN,
								myNBAgent.myFactoryAgent.getTfController(),
								myNBAgent.myFactoryAgent.getLogger());
						// TODO - methods for extending a single node from a CSN
						// by a smaller one. Must respect existing dependencies
						// etc
						// myNBAgent.ourCaseFile.addNewBottomUpSolution(myNBAgent.ourCSN,myNBAgent.ourRoleNode,newlyFoundCSN);

						// no graphical solution in sudden
						// myNBAgent.ourGraphics.addCompleteSolution(newSolution);
						// TODO - temporary solution for SUDDEN demos. Stopping
						// this forcefully after creating just one
						// complete solution not an ideal solution.
						this.myAgent.doDelete();
					}
				} catch (EquivalentSolutionPresentException e) {

					sendRefusal();
				} catch (OntologyException e) {
					// Ignore
					e.printStackTrace();
				}
			} else {
				// TODO - send refusal to original proposal etc.
				sendRefusal();
			}

		}

		private void sendRefusal() {
			// TODO - say *WHY* we're rejecting the proposal
			// System.out.println(" rejecting suggested extension " +
			// dc.getOwningAgent());
			// System.out.println(myNBAgent.ourPartialSolutionHolder.partialSolutions.size());
			ACLMessage reply = messageIn.createReply();
			reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
			reply.setContent(messageIn.getContent());
			myAgent.send(reply);
		}

		private void sendAcceptance() {
			ACLMessage reply = messageIn.createReply();
			reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
			reply.setContent(messageIn.getContent());
			myAgent.send(reply);
		}
	}

}
