package biz.sudden.designCoordination.teamFormation.bottomUp.tests;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.io.IOException;

public class TestPotentialExtensionQueries {

	// Just queries - to test Subscriptions here needs people proposing
	// extensions.

	public static final String noticeboardFactoryAgentName = "noticeboardFactory";

	public static void main(String[] args) throws StaleProxyException {

		Runtime rt = Runtime.instance();
		Profile p = new ProfileImpl(null, 8888, null);
		AgentContainer ac = rt.createMainContainer(p);

		// AgentController sniffer =
		// ac.createNewAgent("sniffer","jade.tools.sniffer.Sniffer",null);
		// sniffer.start();

		AgentController noticeboardFactory = ac
				.createNewAgent(
						noticeboardFactoryAgentName,
						"com.crosswork.components.bottomUpTeamFormation.noticeboardFactoryAgent.NoticeBoardFactoryAgent",
						null);
		noticeboardFactory.start();

		AgentController testCreation = ac
				.createNewAgent(
						"testCreate",
						"com.crosswork.components.bottomUpTeamFormation.tests.TestCreateNoticeboardAgent",
						null);
		testCreation.start();

		try {
			while (System.in.read() == -1) {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		AgentController queryAgent = ac
				.createNewAgent(
						"queryAgent",
						"com.crosswork.components.bottomUpTeamFormation.tests.TestPotExtQueryAgent",
						null);
		queryAgent.start();

	}
}
