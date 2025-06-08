package biz.sudden.jade.pingpong;

import java.util.Iterator;

import biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject;
import biz.sudden.designCoordination.teamFormation.service.impl.JadeServiceImpl;

/**
 * this service provides not much functionality besides allowing to play another
 * round
 * 
 * @author gweich
 */
public class PingPongServiceImpl extends JadeServiceImpl implements
		PingPongService {

	public void playAnotherRound(String agentshortname, boolean play) {
		Iterator<AgentCommunicationObject> i = agent2ServiceCommunication
				.iterator();
		while (i.hasNext()) {
			AgentCommunicationObject co = i.next();
			if (co.getAgentName().startsWith(agentshortname)
					&& co.getQuestion() != null
					&& co.getQuestion().contains("another round")) {
				co.setStatement("" + play);
			}
		}
	}
}
