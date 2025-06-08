package biz.sudden.designCoordination.teamFormation.bottomUp.dumbProcessAgent;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.NoticeboardCombinedOntology;

/**
 * 
 * @author mcassmc
 * 
 *         A mildly silly name I admit.
 * 
 *         This class is used to represent process agents who have already had
 *         their process inserted into the initial partial solution. Hence they
 *         don't subscribe to noticeboards etc.
 * 
 *         They do however take part in the extension negotiations.
 */

public class ProcesslessProcessAgent extends Agent {

	@Override
	protected void setup() {
		ContentManager cm = this.getContentManager();
		cm.registerLanguage(new SLCodec());
		cm.registerOntology(NoticeboardCombinedOntology.getInstance());

		// TODO - still needs a verification behaviour
		addBehaviour(new receiveMessage(this));

	}

	protected class receiveMessage extends CyclicBehaviour {

		public receiveMessage(Agent a) {
			super(a);
		}

		@Override
		public void action() {
			ACLMessage received = myAgent.receive();

			if (received == null) {
				block();
				return;
			} else {
				// Snooze ;)
				// System.out.println(received);
			}

		}

	}
}
