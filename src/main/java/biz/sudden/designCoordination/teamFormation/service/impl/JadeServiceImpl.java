/**
 * 
 */
package biz.sudden.designCoordination.teamFormation.service.impl;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject;
import biz.sudden.designCoordination.teamFormation.service.AgentCommunications;
import biz.sudden.designCoordination.teamFormation.service.JadeService;

/**
 * An implementation of the Jade service with basic jade-agent-platform handling
 * methods.
 * 
 * @author gweich
 * 
 */
public class JadeServiceImpl implements JadeService {

	private Logger logger = Logger.getLogger(this.getClass());

	/** reference to the Jade Runtime Singelton */
	protected static Runtime runtime = null;
	/** reference to the main container created at service start */
	protected static AgentContainer main = null;
	/** all agents (resp. their controllers) created through this service */
	protected List<AgentController> agents = null;
	/** holds the communication between the service and its agents */
	protected AgentCommunications agent2ServiceCommunication = null;

	/**
	 * default constructor which also creates the platform
	 * */
	public JadeServiceImpl() {
		super();
		logger.debug("JadeServiceImpl->cst");
	}

	/**
	 * shutdown platform when going down...
	 * 
	 * @throws java.lang.Throwable
	 */
	// FIXME wrong use of finalize here. Only GC calls this method and
	// GC is not a generic resource management mechanism! You do not know
	// whether this method is called at all!
	@Override
	public void finalize() throws Throwable {
		if (main != null) {
			try {
				main.kill();
			} catch (Exception e) {
			}
		}
		if (runtime != null) {
			runtime.shutDown();
		}
		super.finalize();
	}

	/**
	 * get the known (i.e. created through this service) agents
	 * 
	 * @see biz.sudden.designCoordination.teamFormation.service.JadeService#getAgents
	 *      ()
	 */
	@Override
	public List<AgentController> getAgents() {
		if (agents == null) {
			agents = new ArrayList<AgentController>();
		}
		logger.debug("getAgents: " + agents.size());
		// check if agents are still alive and present:
		for (int i = 0; i < agents.size(); ++i) {
			try {
				agents.get(i).getName();
			} catch (StaleProxyException spe) {
				agents.remove(i);
				i--;
			}
		}
		return agents;
	}

	protected boolean addAgent(AgentController ac) {
		if (agents == null)
			getAgents();
		return agents.add(ac);
	}

	/**
	 * create a maincontainer, (if not present and return it.. used as a further
	 * reference to the platform creating a maincontainer also starts the
	 * platform.
	 * 
	 * @see biz.sudden.designCoordination.teamFormation.service.JadeService#getPlatform
	 *      ()
	 */
	@Override
	public PlatformController getPlatform() {
		if (main == null) {
			logger
					.warn("creating new jade agent platform (reading this warning once is ok))");
			for (int i = 1099; i < 1110; ++i) {
				try {
					ProfileImpl p = new ProfileImpl();
					if (i > 1099) {
						p.setParameter(Profile.LOCAL_PORT, "" + i);
						p.setParameter(Profile.MAIN_PORT, "" + i);
					}
					main = getRuntime().createMainContainer(p);
					logger.warn("created platform" + main.getPlatformName()
							+ " in state:" + main.getState());
					break;
				} catch (Exception pe) {
					logger.debug(pe.getLocalizedMessage());
					logger.debug("Already bound at this port (" + i
							+ ")-> Try next");
					// try it at a different port....
				}
			}
		}
		return main;
	}

	/**
	 * if runtime is null call setRuntime with Runtime.instance() and then
	 * return it
	 * 
	 * @see biz.sudden.designCoordination.teamFormation.service.JadeService#getRuntime
	 *      ()
	 */
	@Override
	public Runtime getRuntime() {
		logger.debug("get jadeRT");
		if (runtime == null) {
			setRuntime(Runtime.instance());
		}
		return runtime;
	}

	/**
	 * set the jade runtime singelton
	 * 
	 * @param runtime
	 */
	public void setRuntime(jade.core.Runtime runtime) {
		logger.debug("set jadeRT");
		JadeServiceImpl.runtime = runtime;
		// startup by adding a maincontainer (in order to get main container
		// reference)
		// => getPlatform
		// this.runtime.startUp(new ProfileImpl());
	}

	/**
	 * Start a Jade Agent on our platform it is assumed that the agent accepts
	 * O2AObject calls. This is enabled by calling
	 * setEnabledO2ACommunication(true, 0); in the agent class constructor
	 * 
	 * also the agent has to be able to handle objects of type JadeService which
	 * is passed to the agent here. later CommunicationObjects are passed to the
	 * agent
	 * 
	 * @see biz.sudden.designCoordination.teamFormation.service.JadeService#startAgent
	 *      (java.lang.String, java.lang.String, java.lang.Object[])
	 */
	@Override
	public AgentController startAgent(String name, String classname,
			Object[] params) {
		AgentController ag = null;
		try {
			ag = getPlatform().createNewAgent(name, classname, params);
			ag.start();
			ag.putO2AObject(this, AgentController.ASYNC);
			if (!addAgent(ag))
				ag = null;
		} catch (ControllerException e) {
			e.printStackTrace();
		}
		logger.debug("Start Agent: " + name);
		return ag;
	}

	/**
	 * talk to an agent by passing a AgentCommunicationObject to him.
	 * 
	 * @see biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject
	 *      ;.
	 * @param message
	 */
	@Override
	public void talkToAgent(AgentCommunicationObject message) {
		logger.debug("talk to agent");
		for (AgentController agent : getAgents()) {
			try {
				if (agent.getName().startsWith(message.getAgentName())) {
					agent.putO2AObject(message, AgentController.ASYNC);
				}
			} catch (StaleProxyException e) {
				e.printStackTrace();
			}
		}
		addCommunicationObject(message);
	}

	/**
	 * An Agent talks back to this service. Store it in a message queue so this
	 * method returns immediately.
	 * 
	 * @see biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject
	 * @param message
	 */
	@Override
	public void agentTalkback(AgentCommunicationObject message) {
		addCommunicationObject(message);
		System.out.print("Agent.talkback: ");
		System.out.print(message.getQuestion() != null ? message.getQuestion()
				: message.getStatement());
		if (agent2ServiceCommunication != null) {
			logger.debug("  /" + agent2ServiceCommunication.size());
			agent2ServiceCommunication.updateUI();
		} else
			logger
					.debug("JadeServiceImpl !!!!!!!! agent2Service Communication == null!!!");
	}

	/**
	 * @return the message queue of agent communication objects
	 */
	@Override
	public AgentCommunications getCommunication() {
		return agent2ServiceCommunication;
	}

	/**
	 * set the message queue of agent communication objects
	 */
	@Override
	public void setCommunication(AgentCommunications ac) {
		agent2ServiceCommunication = ac;
		agent2ServiceCommunication.setMyService(this);
		logger.debug("JadeServiceImpl.setAgentCommunications");
	}

	/**
	 * Clear the list of communication objects
	 * 
	 */
	@Override
	public void clearCommunication() {
		agent2ServiceCommunication.clear();
	}

	/**
	 * new communication object is put into our communication list; references
	 * for agent updates are set
	 * 
	 * @param message
	 */
	protected void addCommunicationObject(AgentCommunicationObject message) {
		if (agent2ServiceCommunication != null
				&& !agent2ServiceCommunication.contains(message)) {
			message.setMyCommunication(agent2ServiceCommunication);
			message.setMyService(this);
			agent2ServiceCommunication.add(message);
		}
	}
}
