package biz.sudden.designCoordination.teamFormation.web.controller.impl;

import jade.wrapper.AgentController;

import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject;
import biz.sudden.designCoordination.teamFormation.service.AgentCommunications;
import biz.sudden.designCoordination.teamFormation.service.JadeService;
import biz.sudden.designCoordination.teamFormation.web.controller.TFjsfController;

import com.icesoft.faces.async.render.OnDemandRenderer;
import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public class TFjsfControllerImpl implements TFjsfController {

	private Logger logger = Logger.getLogger(this.getClass());

	/** for spring to configure the controller with the appropriate service */
	protected JadeService jadeService;
	/** parameter used to start a certain agent using the startAgent() method */
	protected String agentName = null;
	/** parameter used to start a certain agent using the startAgent() method */
	protected String agentType = null;
	/** parameter used to start a certain agent using the startAgent() method */
	protected String agentParam = null;
	protected PersistentFacesState state = null;
	protected OnDemandRenderer renderer = null;

	/** default constructor does nothing (essential) */
	public TFjsfControllerImpl() throws jade.wrapper.StaleProxyException {
		logger.debug("TFjsfController->cst");
	}

	public void updateUI() {
		if (state == null) {
			state = PersistentFacesState.getInstance();
		}
		try {
			// heavy on the server side .... therefore we now have a
			// RenderManager!!
			// state.executeAndRender();
			renderer.requestRender();
		} catch (Exception e) {
			System.err
					.print("Exception requestRender: TFjsfControllerImpl.updateUI: ");
			logger.debug(e.getMessage());
		}
		logger.debug("TFjsfControllerImpl.updated UI");
	}

	@Override
	public PersistentFacesState getState() {
		return state;
	}

	@Override
	public void renderingException(RenderingException arg0) {
		logger.debug(arg0.getLocalizedMessage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.teamFormation.service.impl.AgentCommunications
	 * #setRenderManager(com.icesoft.faces.async.render.RenderManager)
	 */
	@Override
	public void setRenderManager(RenderManager rm) {
		logger.debug("TFjsfControllerImpl.setRedermanager");
		renderer = rm.getOnDemandRenderer("AgentCommunicationsRenderer");
		renderer.add(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController
	 * #setJadeService(biz.sudden.designCoordination.teamFormation
	 * .service.JadeService)
	 */

	@Override
	public void setJadeService(JadeService jadeService) {
		logger.debug("TFjsfController.setJadeService");
		this.jadeService = jadeService;
		jadeService.getCommunication().addController(this);
	}

	// FIXME wrong use of finalize here. Only GC calls this method and
	// GC is not a generic resource management mechanism! You do not know
	// whether this method is called at all!
	@Override
	protected void finalize() {
		if (jadeService != null)
			jadeService.getCommunication().removeController(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController#getAgentCommunications()
	 */
	@Override
	public AgentCommunications getAgentCommunications() {
		return jadeService.getCommunication();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController#clearAgentCommunications()
	 */
	@Override
	public void clearAgentCommunications() {
		jadeService.clearCommunication();
		updateUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController
	 * #updateAgent(biz.sudden.designCoordination.teamFormation.service
	 * .AgentCommunicationObject)
	 */

	@Override
	public void updateAgent(AgentCommunicationObject co2) {
		jadeService.talkToAgent(co2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController#getAgents()
	 */
	@Override
	public List<AgentController> getAgents() {
		return jadeService.getAgents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController#startAgent()
	 */
	@Override
	public void startAgent() {
		if (agentName != null && agentType != null) {
			jadeService.startAgent(agentName, agentType,
					new Object[] { agentParam });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController#getAgentName()
	 */
	@Override
	public String getAgentName() {
		return agentName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController#setAgentName(java.lang.String)
	 */
	@Override
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController#getAgentType()
	 */
	@Override
	public String getAgentType() {
		return agentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController#setAgentType(java.lang.String)
	 */
	@Override
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController#getAgentParam()
	 */
	@Override
	public String getAgentParam() {
		return agentParam;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.designCoordination.teamFormation.web.controller.impl.
	 * TFjsfController#setAgentParam(java.lang.String)
	 */
	@Override
	public void setAgentParam(String agentParam) {
		this.agentParam = agentParam;
	}
}
