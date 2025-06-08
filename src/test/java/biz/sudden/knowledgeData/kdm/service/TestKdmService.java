package biz.sudden.knowledgeData.kdm.service;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/testContext.xml" })
@Transactional
public class TestKdmService {

	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	KdmService kdmService;

	public TestKdmService() {
		logger.debug("TestKdmService -> cst");
		logger.debug(this.hashCode());
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void kdmServiceTest() {
		logger.debug("KDM Service in Test " + kdmService + " ");
		kdmService.initOntology(null);
		kdmService.initCompanyPerformance();
	}

}