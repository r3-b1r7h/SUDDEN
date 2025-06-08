package biz.sudden.jade.pingpong.domain;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject;
import biz.sudden.jade.pingpong.PingPongService;

/**
 * This agent implements a simple Ping Agent.
 * 
 * It works like this:
 * <ol>
 * 
 * <li>First of all the agent registers itself with the DF (Directory
 * Facilitator) of the platform and then waits for ACLMessages.
 * 
 * <li>If a <tt>QUERY_REF</tt> or <tt>QUERY_IF</tt> message arrives that
 * contains the string "<tt>ping</tt>" within the content it replies with an
 * <tt>INFORM</tt> message whose content will be the string "<tt>(pong)</tt>".
 * 
 * <li>If it receives a <tt>NOT_UNDERSTOOD</tt> message no reply is sent. For
 * any other message received it replies with a <tt>NOT_UNDERSTOOD</tt> message.
 * 
 * </ol>
 * 
 * Created by
 * <ul>
 * <li>Tiziana Trucco - CSELT S.p.A.
 * <li>Anthony - UMIST - 2003
 * <li>Georg - JKU - 2008
 * </ul>
 * 
 */
public class PingPongAgent extends jade.core.Agent {

	/**
	 * 
	 */
	private Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 1L;
	public final static String PING_PONG_SERVICE_TYPE = "PingPongServiceType";
	public final static String AGENT_LANGUAGE = jade.domain.FIPANames.ContentLanguage.FIPA_SL;
	public final static int AGENT_SL_CODEC_TYPE = 0;
	public final static String PING_PONG_PROTOCOL = "inform";
	public final static String AGENT_TYPE = "PingPongAgent";

	/**
	 * it makes not sense to have multiple services running so lets make it
	 * static
	 */
	protected static PingPongService myService;

	private List<AgentCommunicationObject> incommingObjectQueue = null;

	/**
	 * Empty constructor.
	 */
	public PingPongAgent() {
		super();
		// enables an object queue which is used for communication with legacy
		// systems, i.e. applications outside
		// the agent world.
		setEnabledO2ACommunication(true, 0);
	}

	/**
	 * This method is automatically called before the agent when the agent is
	 * started.
	 */
	@Override
	protected void setup() {
		super.setup();
		SLCodec sl = new SLCodec(AGENT_SL_CODEC_TYPE);
		ContentManager cm = getContentManager();
		if (cm.lookupLanguage(sl.getName()) == null) {
			cm.registerLanguage(sl);
		}
		// Registration with the DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());

		// add agent type service
		ServiceDescription sd = new ServiceDescription();
		sd.setType(AGENT_TYPE);
		sd.setName(getName());
		// sd.setOwnership("anonymous");
		// sd.addOntologies(scm.ontology.SCMOntology.ONTOLOGY_NAME);
		sd.addLanguages(AGENT_LANGUAGE);
		dfd.addServices(sd);

		sd = new ServiceDescription();
		sd.setType(PING_PONG_SERVICE_TYPE);
		sd.setName(getName());
		dfd.addServices(sd);

		try {
			// System.out.println("registering Agent
			// "+DFService.createRequestMessage(this, this.getDefaultDF (),
			// jade.domain.FIPAAgentManagement.FIPAManagementVocabulary.REGISTER,
			// dfd, null));
			DFService.register(this, dfd);
			// System.out.println("registered Agent "+getName());
		} catch (FIPAException e) {
			logger.debug(getLocalName()
					+ " registration with DF unsucceeded. Reason: " + e);
			// doDelete();
		}
		addBehaviour(new WaitPingAndReplyBehaviour(this));
		addBehaviour(new SendPingBehaviour(this));
		System.out.println("PingPongAgent is setup");
		checkObjectQueue();
	}

	protected void checkObjectQueue() {
		Object received = getO2AObject();
		while (received != null) {
			System.out.println("PingPongAgent.... checkObjectQueue");

			if (received instanceof PingPongService) {
				myService = (PingPongService) received;
				System.out.println("Configured PingPongAgent with Service: "
						+ myService);
			} else if (received instanceof AgentCommunicationObject) {
				addIncommingObject((AgentCommunicationObject) received);
			}
			received = getO2AObject();
		}
	}

	/** get the message object #index from the incominObjectQueue */
	protected AgentCommunicationObject getIncommingObject(int index) {
		AgentCommunicationObject result = null;
		if (incommingObjectQueue != null) {
			synchronized (incommingObjectQueue) {
				result = incommingObjectQueue.get(index);
			}
		}
		return result;
	}

	/** get the message object #index from the incominObjectQueue */
	protected AgentCommunicationObject removeIncommingObject(int index) {
		AgentCommunicationObject result = null;
		if (incommingObjectQueue != null) {
			synchronized (incommingObjectQueue) {
				result = incommingObjectQueue.remove(index);
			}
		}
		return result;
	}

	/** add a message object to the incominObjectQueue */
	protected void addIncommingObject(AgentCommunicationObject message) {
		if (incommingObjectQueue == null)
			incommingObjectQueue = new ArrayList<AgentCommunicationObject>();
		synchronized (incommingObjectQueue) {
			incommingObjectQueue.add(message);
		}
	}

	/**
	 * get the size of the queue holding the incoming message objects
	 * 
	 * @return -1 if incommingObjectQueue == null, 0 if empty, ...
	 */
	protected int getIncommingObjectQueueSize() {
		int result = -1;
		if (incommingObjectQueue != null) {
			synchronized (incommingObjectQueue) {
				result = incommingObjectQueue.size();
			}
		}
		return result;
	}
}
