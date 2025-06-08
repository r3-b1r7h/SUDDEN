package biz.sudden.designCoordination.teamFormation.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject;
import biz.sudden.designCoordination.teamFormation.service.AgentCommunications;
import biz.sudden.designCoordination.teamFormation.service.JadeService;
import biz.sudden.designCoordination.teamFormation.web.controller.TFjsfController;

public class AgentCommunicationsImpl extends
		ArrayList<AgentCommunicationObject> implements AgentCommunications {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass());

	protected JadeService myService = null;

	protected List<TFjsfController> controller = new ArrayList<TFjsfController>();

	public AgentCommunicationsImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.teamFormation.service.impl.AgentCommunications
	 * #updateUI()
	 */
	@Override
	public void updateUI() {
		logger.debug("AgentCommunicationsImpl.updateUI");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.teamFormation.service.impl.AgentCommunications
	 * #updateAgent()
	 */
	@Override
	public void updateAgent() {
		logger.debug("AgentCommunicationsImpl.updateAgent");
		for (Iterator<AgentCommunicationObject> e = super.iterator(); e
				.hasNext();) {
			e.next().updateAgent();
		}
	}

	@Override
	public void addController(TFjsfController contr) {
		controller.add(contr);
	}

	@Override
	public void removeController(TFjsfController contr) {
		controller.remove(contr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.teamFormation.service.impl.AgentCommunications
	 * #getMyService()
	 */
	public JadeService getMyService() {
		return myService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.teamFormation.service.impl.AgentCommunications
	 * #
	 * setMyService(biz.sudden.designCoordination.teamFormation.service.JadeService
	 * )
	 */
	public void setMyService(JadeService myService) {
		this.myService = myService;
	}

}
