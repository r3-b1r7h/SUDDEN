/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package biz.sudden.jade.pingpong.domain;

import biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject;

/**
 * 
 * @author gweich
 */
public class ProposeAnotherRoundOfPingPongBehaviour extends
		jade.core.behaviours.SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean waitingForAnswer = false;
	public boolean done = false;

	public static String AnotherRound = "Play another round of PingPong??";

	@Override
	public void action() {
		((PingPongAgent) myAgent).checkObjectQueue();
		if (!waitingForAnswer) {
			AgentCommunicationObject message = new AgentCommunicationObject();
			message.setAgentName(myAgent.getLocalName());
			message.setQuestion(AnotherRound);
			PingPongAgent.myService.agentTalkback(message);
			waitingForAnswer = true;
		} else {

			for (int i = 0; i < ((PingPongAgent) myAgent)
					.getIncommingObjectQueueSize(); ++i) {
				AgentCommunicationObject co = ((PingPongAgent) myAgent)
						.getIncommingObject(i);
				if (co.getQuestion() != null
						&& co.getQuestion().equals(AnotherRound)) {
					((PingPongAgent) myAgent).removeIncommingObject(i);
					i--;
					Boolean yesno = new Boolean(co.getStatement());
					if (yesno.booleanValue()) {
						myAgent.addBehaviour(new SendPingBehaviour(myAgent));
					}
					done = true;
					myAgent.removeBehaviour(this);
					System.out.println("removed Propose another round");
					// don't forget to leave this loop
					break;
					// and be aware that after calling removeBehaviour(this)
					// myAgent == null!!!
				}
			}
		}

	}

	@Override
	public boolean done() {
		return done;
	}

}
