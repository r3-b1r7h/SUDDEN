/**
 * 
 */
package biz.sudden.jade.pingpong;

import jade.wrapper.StaleProxyException;
import biz.sudden.designCoordination.teamFormation.web.controller.impl.TFjsfControllerImpl;
import biz.sudden.jade.pingpong.domain.PingPongAgent;

/**
 * a controller for the pingpong.jspx in order to have jade agents play ping
 * pong
 * 
 * @author gweich
 */
public class PingPongjsfControllerImpl extends TFjsfControllerImpl implements
		PingPongjsfController {

	public PingPongjsfControllerImpl() throws StaleProxyException {
		super();
		System.out.println("PingPongjsfControllerImpl->cst");
	}

	protected int numberOfPPAgentsToStart = 1;

	/**
	 * shortcut to start the remote management agent i.e. the console for
	 * managing jade platforms
	 */
	@Override
	public void startRMA() {
		jadeService.startAgent("rma", "jade.tools.rma.rma", null);
	}

	/** shortcut to start a single ping pong agent */
	@Override
	public void startPingPongAgent() {
		jadeService.startAgent("PPAgt-" + Math.random(), PingPongAgent.class
				.getCanonicalName(), null);
	}

	/** shortcut to start a number of ping pong agents */
	@Override
	public void startNPingPongAgents() {
		for (int i = 0; i < numberOfPPAgentsToStart; ++i)
			startPingPongAgent();
	}

	/**
	 * @return the numberOfPPAgentsToStart
	 */
	@Override
	public int getNumberOfPPAgentsToStart() {
		return numberOfPPAgentsToStart;
	}

	/**
	 * @param numberOfPPAgentsToStart
	 *            the numberOfPPAgentsToStart to set
	 */
	@Override
	public void setNumberOfPPAgentsToStart(int numberOfPPAgentsToStart) {
		this.numberOfPPAgentsToStart = numberOfPPAgentsToStart;
	}
}
