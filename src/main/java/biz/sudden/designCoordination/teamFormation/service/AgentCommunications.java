package biz.sudden.designCoordination.teamFormation.service;

import java.util.List;

import biz.sudden.designCoordination.teamFormation.web.controller.TFjsfController;

public interface AgentCommunications extends List<AgentCommunicationObject> {

	/**
	 * that controller is in the usersession, where this AgentCommunications is
	 * in applicationsession;; to update UI.
	 */
	public void addController(TFjsfController cont);

	/**
	 * that controller is in the usersession, where this AgentCommunications is
	 * in applicationsession;; to update UI.
	 */
	public void removeController(TFjsfController cont);

	/** update the user interface that something happend */
	public void updateUI();

	/**
	 * notify agents that things (i.e. Communication Objects ) have changed yet
	 * this an "expensive" method as it iterates over all Communication Objects
	 */
	public void updateAgent();

	/**
	 * @return the Service used to communicate with the agents
	 */
	public JadeService getMyService();

	/**
	 * @param myService
	 *            the Service used to communicate with the agents
	 */
	public void setMyService(JadeService myService);
}