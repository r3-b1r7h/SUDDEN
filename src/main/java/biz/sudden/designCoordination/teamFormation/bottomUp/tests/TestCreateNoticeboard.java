package biz.sudden.designCoordination.teamFormation.bottomUp.tests;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent.NoticeBoardFactoryAgent;

/**
 * A simple class which creates agents to test the Create Noticeboard protocol.
 * 
 * @author mcassmc
 * 
 */

public class TestCreateNoticeboard {

	public static final String noticeboardFactoryAgentName = "noticeboardFactory";

	public static void main(String[] args) throws StaleProxyException {

		Runtime rt = Runtime.instance();
		Profile p = new ProfileImpl(null, 8888, null);
		AgentContainer ac = rt.createMainContainer(p);

		AgentController sniffer = ac.createNewAgent("sniffer",
				"jade.tools.sniffer.Sniffer", null);
		sniffer.start();

		AgentController noticeboardFactory = ac.createNewAgent(
				noticeboardFactoryAgentName, NoticeBoardFactoryAgent.class
						.getCanonicalName(), null);
		noticeboardFactory.start();

		AgentController testCreation = ac.createNewAgent("testCreate",
				TestCreateNoticeboardAgent.class.getCanonicalName(), null);
		testCreation.start();
	}

}
