package biz.sudden.knowledgeData.serviceManagement.web.controller;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.service.ManufacturingProcessType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/testContext.xml" })
@Transactional
public class TestSMController {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	ProductMaterialSupportingServices_Service pmsService;
	@Autowired
	ManufacturingProcessType_Service manufacturingService;
	@Autowired
	ServiceManagementService baseService;
	@Autowired
	ConnectableService connectableService;
	// private
	private ComplexProduct cp;
	private ComplexProduct cp0;

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
	public void test_directedSearch() {
		ComplexProduct cp1 = new ComplexProduct();
		cp1.setName("rootProduct");

		ComplexProduct cp2 = new ComplexProduct();
		cp2.setName("partProduct1");

		ComplexProduct cp3 = new ComplexProduct();
		cp3.setName("partProduct1_2");

		pmsService.createComplexProduct(cp1);
		pmsService.createComplexProduct(cp2);
		pmsService.createComplexProduct(cp3);

		pmsService.addPart(cp1, cp2, connectableService.getUnspecifiedScope());
		pmsService.addPart(cp2, cp3, connectableService.getUnspecifiedScope());
		// directedSearch size of Connectable List in associate: "+i.size());

		Set<Item> i = pmsService.retrieveAllParts(cp1, connectableService
				.getUnspecifiedScope());

		Iterator<Item> it = i.iterator();
		while (it.hasNext()) {
			logger.debug("part found: "
					+ ((ComplexProduct) it.next()).getName());
		}
		cp0 = cp2;
		logger.debug("stay");

	}

	@Test
	public void testAccociate() {

		ComplexProduct cp1 = new ComplexProduct();
		cp1.setName("rootProduct");

		ComplexProduct cp2 = new ComplexProduct();
		cp2.setName("partProduct1");

		ComplexProduct cp3 = new ComplexProduct();
		cp3.setName("partProduct1_2");

		pmsService.createComplexProduct(cp1);
		pmsService.createComplexProduct(cp2);
		pmsService.createComplexProduct(cp3);

		pmsService.addPart(cp1, cp2, connectableService.getUnspecifiedScope());
		pmsService.addPart(cp1, cp3, connectableService.getUnspecifiedScope());

		pmsService.deleteComplexProduct(cp2);
		logger.debug("after delete cp2");

		Set<Item> cps = pmsService.retrieveAllParts(cp1, connectableService
				.getUnspecifiedScope());
		Iterator<Item> it = cps.iterator();
		while (it.hasNext()) {
			logger.debug("part: " + it.next().getName());
		}
		logger.debug("after getAllParts");
		logger.debug("stay");
	}

}
