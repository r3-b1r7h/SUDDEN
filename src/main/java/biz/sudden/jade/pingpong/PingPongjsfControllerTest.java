package biz.sudden.jade.pingpong;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import jade.wrapper.StaleProxyException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import biz.sudden.jade.pingpong.domain.PingPongAgent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/testContext.xml" })
public class PingPongjsfControllerTest {

	PingPongService service;

	@Autowired
	void setPingPongService(PingPongService serv) {
		this.service = serv;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStartPingPongAgent() {
		service.startAgent("PPAgt-" + Math.random(), PingPongAgent.class
				.getCanonicalName(), null);
		for (int i = 0; i < 3; ++i) {
			synchronized (this) {
				try {
					this.wait(5000);
				} catch (Exception e) {
				}
				;
			}
			if (service.getAgents().size() == 1)
				i = 3;
		}
		assertEquals("active agents: ", 1, service.getAgents().size());
		try {
			assertEquals("active agent: ", service.getAgents().get(0)
					.getState().getCode(),
					jade.wrapper.AgentState.cAGENT_STATE_ACTIVE);
		} catch (StaleProxyException e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void testGetAgentCommunications() {
		fail("Not yet implemented");
	}

	@Test
	public void testClearAgentCommunications() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAgent() {
		fail("Not yet implemented");
	}
}
