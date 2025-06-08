package biz.sudden.jade.pingpong;

import biz.sudden.designCoordination.teamFormation.web.controller.TFjsfController;

public interface PingPongjsfController extends TFjsfController {

	/**
	 * shortcut to start the remote management agent i.e. the console for
	 * managing jade platforms
	 */
	public void startRMA();

	/** shortcut to start a single ping pong agent */
	public void startPingPongAgent();

	/** shortcut to start a number of ping pong agents */
	public void startNPingPongAgents();

	/**
	 * @return the numberOfPPAgentsToStart
	 */
	public int getNumberOfPPAgentsToStart();

	/**
	 * @param numberOfPPAgentsToStart
	 *            the numberOfPPAgentsToStart to set
	 */
	public void setNumberOfPPAgentsToStart(int numberOfPPAgentsToStart);

}