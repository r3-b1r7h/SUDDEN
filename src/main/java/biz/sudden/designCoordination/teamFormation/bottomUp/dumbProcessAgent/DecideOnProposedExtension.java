package biz.sudden.designCoordination.teamFormation.bottomUp.dumbProcessAgent;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * 
 * @author mcassmc
 * 
 *         Responsible for engaging in negotiations about whether or not a given
 *         proposal to extend to a partial solution this agent is already part
 *         of is acceptable.
 */

public class DecideOnProposedExtension extends OneShotBehaviour {

	private ACLMessage messageIn;

	public DecideOnProposedExtension(Agent myAgent,
			ACLMessage verifyProposeExtension) {
		super(myAgent);
		messageIn = verifyProposeExtension;
	}

	/*
	 * Stick any and all intelligence in here :) We're happy and friendly for
	 * now.
	 */
	@Override
	public void action() {
		ACLMessage reply = messageIn.createReply();
		reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
		// System.out.println("Sending " + reply);
		myAgent.send(reply);
	}

}
