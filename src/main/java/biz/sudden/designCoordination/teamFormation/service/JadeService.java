package biz.sudden.designCoordination.teamFormation.service;

import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;

import java.util.List;

/**
 * A Basic Service to provide basic features of the Jade Agent Platform to
 * SUDDEN services
 * 
 * @author gweich
 */
public interface JadeService {

	public Runtime getRuntime();

	/**
	 * @return the known (i.e. created through this service) agents
	 * @see jade.wrapper.AgentController
	 */
	public List<AgentController> getAgents();

	/**
	 * 
	 * @return the controller for the main platform to handle e.g. it's
	 *         livecycle
	 * @see jade.wrapper.PlatformController
	 */
	public PlatformController getPlatform();

	/**
	 * start a particular agent
	 * 
	 * @param name
	 *            short name of the agent
	 * @param classname
	 *            the agent's class
	 * @param params
	 *            some parameter
	 * @return
	 */
	public AgentController startAgent(String name, String classname,
			Object[] params);

	/**
	 * Talk to an agent by passing communication objects
	 * 
	 * @param message
	 * @see biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject
	 *      ;
	 */
	public void talkToAgent(AgentCommunicationObject message);

	/**
	 * Talk to an agent by passing communication objects Have this method return
	 * immediately to not stop the agent
	 * 
	 * @param message
	 * @see biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject
	 *      ;
	 */
	public void agentTalkback(AgentCommunicationObject message);

	/**
	 * @return the message queue of agent CommunicationObjects objects
	 */
	public AgentCommunications getCommunication();

	/**
	 * @param Spring
	 *            : inversion of control; at execution time set the
	 *            AgentCommunicationsImpl object, which also manages the
	 *            rendering of changed AgentCommunicationObject<i>s</i>
	 */
	public void setCommunication(AgentCommunications comm);

	/** clear the list of communication objects */
	public void clearCommunication();
}
