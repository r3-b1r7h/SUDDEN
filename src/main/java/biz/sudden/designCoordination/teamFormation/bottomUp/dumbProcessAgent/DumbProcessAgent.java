package biz.sudden.designCoordination.teamFormation.bottomUp.dumbProcessAgent;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeBoardAgent.VerifyProposedExtensionBehaviour;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.DeclaredCapability;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.NoticeboardCombinedOntology;

/**
 * @author mcassmc
 * 
 *         An optimistic agent with a single fixed process. Pass the fixed
 *         process in as a argument when starting the agent.
 * 
 *         These agents are mainly designed to serve to allow the easy building
 *         of demos - the final system envisages the support of considerably
 *         more sophisticated agents.
 * 
 */

@SuppressWarnings("serial")
public class DumbProcessAgent extends Agent {

	public static final String queryForNoticeboards = "Query_For_Noticeboards";
	public static final String queryForPotentialExtensions = "Query_For_Potential_Extensions";

	// My single process
	private DeclaredCapability myProcess;
	private AID noticeboardFactoryAID;

	@Override
	protected void setup() {
		ContentManager cm = this.getContentManager();
		cm.registerLanguage(new SLCodec());
		cm.registerOntology(NoticeboardCombinedOntology.getInstance());

		myProcess = (DeclaredCapability) getArguments()[0];
		// Ok really should query DF
		this.noticeboardFactoryAID = (AID) getArguments()[1];

		addBehaviour(new receiveMessage(this));
		addBehaviour(new SubscribeToNoticeboardFactory(this));
	}

	protected AID getNBFAID() {
		return this.noticeboardFactoryAID;
	}

	protected DeclaredCapability getDeclaredCapability() {
		return this.myProcess;
	}

	/**
	 * @author mcassmc
	 * 
	 *         Echo for now
	 */
	protected class receiveMessage extends CyclicBehaviour {

		public receiveMessage(DumbProcessAgent agent) {
			super(agent);
		}

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {

			ACLMessage informOfNBMessage = myAgent
					.receive(MessageTemplate
							.and(
									MessageTemplate
											.MatchInReplyTo(DumbProcessAgent.queryForNoticeboards),
									MessageTemplate
											.MatchPerformative(ACLMessage.INFORM_REF)));

			if (informOfNBMessage == null) {
				ACLMessage informOfPEsMessage = myAgent
						.receive(MessageTemplate
								.and(
										MessageTemplate
												.MatchInReplyTo(DumbProcessAgent.queryForPotentialExtensions),
										MessageTemplate
												.MatchPerformative(ACLMessage.INFORM_REF)));

				if (informOfPEsMessage == null) {
					ACLMessage verifyProposeExtension = myAgent
							.receive(MessageTemplate
									.MatchProtocol(VerifyProposedExtensionBehaviour.VERIFY_PROPOSED_EXTENSION_PROTOCOL));
					if (verifyProposeExtension == null) {
						ACLMessage nonQueryMessage = myAgent.receive();
						if (nonQueryMessage == null) {
							block();
							return;
						} else {
							// Could also check for the performative, message
							// content etc.
							System.out.println(" Recevied unexpected Message "
									+ nonQueryMessage);
						}
					} else {
						System.out.println(verifyProposeExtension);
						addBehaviour(new DecideOnProposedExtension(myAgent,
								verifyProposeExtension));
					}
				} else {
					System.out
							.println(" Potential Extension matched - adding behaviour"
									+ informOfPEsMessage);
					addBehaviour(new DealWithMatchedPotentialExtension(myAgent,
							informOfPEsMessage));
				}

			} else {
				System.out.println(" new noticeboard detected "
						+ informOfNBMessage);
				addBehaviour(new DealWithMatchedNoticeboard(myAgent,
						informOfNBMessage));
			}
		}
	}

}
