package biz.sudden.jade.pingpong.domain;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import org.apache.log4j.Logger;

import biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject;

/**
 * Just sends a "(ping)" to all another agents that offer the
 * PING_PONG_SERVICE_TYPE.
 */
class SendPingBehaviour extends jade.core.behaviours.SimpleBehaviour {

	/**
	 * 
	 */

	private Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 1L;

	private static final long CYCLE_TIME_MILLIS = 1000;

	private static final int MAX_SENT = 4;

	private int sent = 0;

	private long mLastCall = System.currentTimeMillis();

	/**
	 * This Behaviour lets the implementing Agent wait for some query-ref,
	 * query-inf message and replies with an inform (pong).
	 * 
	 * @see jade.core.behaviours.SimpleBehaviour
	 * @param a
	 */
	public SendPingBehaviour(final Agent a) {
		super(a);
	}

	/**
	 * Find agents with the PING_PONG_SERVICE_TYPE and send them a PING. Also
	 * send a message to Agents with GUIService. This is an endless loop
	 */
	@Override
	public void action() {
		// first check if there is something in the Agent's Object Queue
		((PingPongAgent) myAgent).checkObjectQueue();
		long now = System.currentTimeMillis();
		// wait until mCycleTimeMillis time has passed
		if ((now - mLastCall) > CYCLE_TIME_MILLIS) {
			// build a message
			ACLMessage msg = new ACLMessage(ACLMessage.QUERY_REF);
			msg.setSender(myAgent.getAID());
			msg.setContent("(ping)");
			msg.setProtocol(PingPongAgent.PING_PONG_PROTOCOL);
			// search for all Agents that have registered the
			// PingPongServiceType
			DFAgentDescription[] agentDescriptions = searchForAgentsWithServiceOfType(PingPongAgent.PING_PONG_SERVICE_TYPE);
			for (int i = 0; i < agentDescriptions.length; ++i) {
				msg.addReceiver(agentDescriptions[i].getName());
			}

			// bang! send it.
			msg.setDefaultEnvelope();
			myAgent.send(msg);

			AgentCommunicationObject message = new AgentCommunicationObject();
			message.setAgentName(myAgent.getLocalName());
			message.setStatement("I have sent a ping");
			PingPongAgent.myService.agentTalkback(message);

			mLastCall = now;
			block(CYCLE_TIME_MILLIS);
			sent++;
		} else {
			long blockFor = CYCLE_TIME_MILLIS - (now - mLastCall);
			System.out.println(myAgent.getName() + " wait-for (ms): "
					+ blockFor);
			if (blockFor > 0) {
				block(blockFor);
			}
		}
	}

	/**
	 * Is this Behaviour finished? Called after action(). If returning false
	 * action is called again
	 * 
	 * @return True if behaviour has finished.
	 */
	@Override
	public boolean done() {
		if (sent >= MAX_SENT) {
			Behaviour b = new ProposeAnotherRoundOfPingPongBehaviour();
			myAgent.addBehaviour(b);

			// first add Behaviour then remove this since remove sets the
			// myAgent to null
			myAgent.removeBehaviour(this);
			return true;
		} else {
			return false;
		}
	}

	protected DFAgentDescription[] searchForAgentsWithServiceOfType(
			String servicetype) {
		DFAgentDescription[] result = null;
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(servicetype);
		dfd.addServices(sd);
		try {
			result = DFService.search(myAgent, dfd);
		} catch (jade.domain.FIPAException e) {
			logger.debug("Exception while using DF" + e);
		}
		if (result == null || result.length == 0) {
			System.out.println("no Agents found when searching for service: "
					+ servicetype);
		}
		return result;
	}
}
