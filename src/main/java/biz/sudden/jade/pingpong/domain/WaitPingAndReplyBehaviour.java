package biz.sudden.jade.pingpong.domain;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import org.apache.log4j.Logger;

import biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject;

/**
 * Waits for messages containing "ping" and replies with a "(pong)".
 * 
 * (c) UMIST 2003 (c) JKU 2008
 * 
 */
class WaitPingAndReplyBehaviour extends jade.core.behaviours.SimpleBehaviour {

	/**
	 * 
	 */

	private Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 1L;
	private boolean mFinished = false;

	/**
	 * This Behaviour lets the implementing Agent wait for some query-ref,
	 * query-inf message and replies with an inform (pong).
	 * 
	 * @see jade.core.behaviours.SimpleBehaviour
	 * @param a
	 *            The agent
	 */
	public WaitPingAndReplyBehaviour(final Agent a) {
		super(a);
	}

	/**
	 * Wait for a received message and reply with pong.
	 */
	@Override
	public void action() {
		// first check if there is something in the Agent's Object Queue
		((PingPongAgent) myAgent).checkObjectQueue();

		// receive any Message that can be found in the Agent's Message Queue
		// to get only selected Messages try jade's MessageTemplate Class which
		// is used for filtering Messages out of the Queue
		ACLMessage msg = this.myAgent.receive(MessageTemplate
				.MatchProtocol(PingPongAgent.PING_PONG_PROTOCOL));
		ACLMessage reply = null;
		AgentCommunicationObject communicate2UI = new AgentCommunicationObject();
		communicate2UI.setAgentName(myAgent.getLocalName());

		if (msg != null) {
			switch (msg.getPerformative()) {
			case ACLMessage.NOT_UNDERSTOOD:
				// If I receive a Message that has the Performative "Not
				// Understood" do not reply - this might otherwise create an
				// endless loop
				break;

			case ACLMessage.INFORM:
				System.out.println("Received an Inform '" + msg.getContent()
						+ "' from: " + msg.getSender().getLocalName());
				communicate2UI.setStatement("I have received an inform: "
						+ msg.getContent() + " from: "
						+ msg.getSender().getLocalName());
				break;

			case ACLMessage.QUERY_REF:
			case ACLMessage.QUERY_IF:
				reply = msg.createReply();
				reply.setSender(myAgent.getAID());
				String content = msg.getContent();
				if ((content != null) && (content.indexOf("ping") != -1)) {
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("(pong)");
					communicate2UI
							.setStatement("Replied to ping with pong to: "
									+ msg.getSender().getLocalName());
				} else {
					reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
					reply.setContent("(UnexpectedContent (expected ping))");
					logger.debug("Received: " + content);
					communicate2UI.setStatement("What is this guy ("
							+ msg.getSender().getLocalName()
							+ ") trying to tell me with: " + msg.getContent()
							+ "?");
				}
				break;

			default:
				// this msg might be needed by another behaviour
				myAgent.putBack(msg);
				// yet still we reply
				reply = msg.createReply();
				reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
				reply.setContent("( (Unexpected-act "
						+ ACLMessage.getPerformative(msg.getPerformative())
						+ ") ( expected (query-ref :content ping)))");
				communicate2UI.setStatement("What is this guy ("
						+ msg.getSender().getLocalName()
						+ ") trying to tell me with: "
						+ ACLMessage.getPerformative(msg.getPerformative())
						+ ": " + msg.getContent() + "?");
				break;
			}
			PingPongAgent.myService.agentTalkback(communicate2UI);
		}
		if (reply != null) {
			msg.setDefaultEnvelope();
			// bang! send it.
			myAgent.send(reply);
		}
		// set this behaviour into "block"
		// this behaviour is activated again when a new message is received
		// otherwise this behaviour's action() method is called immediately
		// again... (as long as it is active)
		block();
	}

	/**
	 * Is this Behaviour finished? Called after action(). If returning false
	 * action is called again
	 * 
	 * @return True if done.
	 */
	@Override
	public boolean done() {
		return mFinished;
	}
}
