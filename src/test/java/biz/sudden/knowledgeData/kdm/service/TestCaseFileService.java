package biz.sudden.knowledgeData.kdm.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.handleBO.service.HandleBOService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/rootContext.xml" })
// @Transactional
public class TestCaseFileService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private KdmService kdmService;
	@Autowired
	private HandleBOService handleBoService;

	public TestCaseFileService() {

	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddProductToASN() {
		logger.debug("Test");
		logger.debug("Trying to add Products to existing ASN's");
		List<CaseFile> caseFileList = kdmService.getCaseService()
				.retrieveAllCaseFiles();
		logger.debug("Found " + caseFileList.size() + " casefiles");
		for (CaseFile caseFile : caseFileList) {
			// if (caseFile.getName().equals("Case File for BO: BO_CarDoor")) {
			logger.debug("Found Casefile " + caseFile.toString());
			List<AbstractSupplyNetwork> asnList = handleBoService
					.generateInitialSupplyNetworks(caseFile.getBo());
			for (AbstractSupplyNetwork abstractSupplyNetwork : asnList) {
				logger.debug("Found ASN for caseFile " + caseFile.toString()
						+ ": " + asnList.get(0).toString());
				// caseFile.setAsnFinalTeam(asnList);
				caseFile.setAsnPrototypeTeam(asnList.get(0));
				kdmService.getCaseService().update(caseFile);
				// }
			}
		}
	}

	@Test
	public void testNix() {
		logger.debug("nix");

	}

}