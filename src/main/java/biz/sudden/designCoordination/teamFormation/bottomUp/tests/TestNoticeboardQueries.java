package biz.sudden.designCoordination.teamFormation.bottomUp.tests;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.io.IOException;

import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ConstrainedProductOntology;

public class TestNoticeboardQueries {

	// The current set up is testing the triggering of subscriptions.
	// To check eg direct queries create the new noticeboard first.

	public static final String noticeboardFactoryAgentName = "noticeboardFactory";

	public static void main(String[] args) throws StaleProxyException {

		Runtime rt = Runtime.instance();
		Profile p = new ProfileImpl(null, 8888, null);
		AgentContainer ac = rt.createMainContainer(p);

		// AgentController sniffer =
		// ac.createNewAgent("sniffer","jade.tools.sniffer.Sniffer",null);
		// sniffer.start();

		Object[] arguments = new Object[1];
		arguments[0] = ConstrainedProductOntology.getInstance();
		AgentController noticeboardFactory = ac.createNewAgent(
				noticeboardFactoryAgentName,
				"noticeboardFactoryAgent.NoticeBoardFactoryAgent", arguments);
		noticeboardFactory.start();

		AgentController queryAgent = ac.createNewAgent("queryAgent",
				"tests.TestQueryNoticeboardFactoryAgent", null);
		queryAgent.start();

		try {
			while (System.in.read() == -1) {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		AgentController testCreation = ac.createNewAgent("testCreate",
				"tests.TestCreateNoticeboardAgent", null);
		testCreation.start();

	}
}
