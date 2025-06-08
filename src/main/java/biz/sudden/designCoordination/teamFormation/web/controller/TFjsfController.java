package biz.sudden.designCoordination.teamFormation.web.controller;

import jade.wrapper.AgentController;

import java.util.List;

import biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject;
import biz.sudden.designCoordination.teamFormation.service.AgentCommunications;
import biz.sudden.designCoordination.teamFormation.service.JadeService;

import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;

public interface TFjsfController extends Renderable {

	/**
	 * configure this controller Spring uses the inversion of control paradigm,
	 * so tell this controller which service to use
	 * 
	 * @param jadeService
	 *            the jadeService to set
	 */
	public void setJadeService(JadeService jadeService);

	public void setRenderManager(RenderManager rm);

	public AgentCommunications getAgentCommunications();

	public void clearAgentCommunications();

	/**
	 * we tell the agent that the user has changed the content of the
	 * AgentCommunicationObject
	 */
	public void updateAgent(AgentCommunicationObject co2);

	/**
	 * provide access to the agents on this platform
	 * 
	 * @return
	 */
	public List<AgentController> getAgents();

	/** start the agent specified by agentName, agentType, agentParam */
	public void startAgent();

	/**
	 * parameter for an agent to start with startAgent
	 * 
	 * @return the agentName
	 */
	public String getAgentName();

	/**
	 * parameter for an agent to start with startAgent
	 * 
	 * @param agentName
	 *            the agentName to set
	 */
	public void setAgentName(String agentName);

	/**
	 * parameter for an agent to start with startAgent
	 * 
	 * @return the agentType
	 */
	public String getAgentType();

	/**
	 * parameter for an agent to start with startAgent
	 * 
	 * @param agentType
	 *            the agentType to set
	 */
	public void setAgentType(String agentType);

	/**
	 * parameter for an agent to start with startAgent
	 * 
	 * @return the agentParam
	 */
	public String getAgentParam();

	/**
	 * parameter for an agent to start with startAgent
	 * 
	 * @param agentParam
	 *            the agentParam to set
	 */
	public void setAgentParam(String agentParam);

}