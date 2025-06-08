package biz.sudden.evaluation.coordinationFit.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNInitialNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/testContext.xml" })
public class CoordinationFitTest {
	@Autowired
	CoordinationFitService coordinationFitService;

	@Test
	public void testCoordinationFit() {
		assertNotNull(coordinationFitService);

		AbstractSupplyNetwork asn = new AbstractSupplyNetwork();

		ASNInitialNode initialNode = new ASNInitialNode();
		asn.addNewNode(initialNode);

		Product p = null;
		// asn.addFinalNode(p);

		ASNRoleNode aNode = new ASNRoleNode(null, null);
		asn.addNewNode(aNode);
		asn.addNewDependcy(new ASNMaterialDependency(initialNode, aNode));

		ASNRoleNode bNode = new ASNRoleNode(null, null);
		asn.addNewNode(bNode);
		asn.addNewDependcy(new ASNMaterialDependency(initialNode, bNode));

		ASNRoleNode cNode = new ASNRoleNode(null, null);
		asn.addNewNode(cNode);
		asn.addNewDependcy(new ASNMaterialDependency(aNode, cNode));

		Map<ASNRoleNode, Supplier> candidateSuppliers = new HashMap<ASNRoleNode, Supplier>();
		candidateSuppliers.put(aNode, setupSupplier(1l, 6l, 7l, 10l, 15l, 40l));
		candidateSuppliers
				.put(bNode, setupSupplier(3l, 6l, 12l, 15l, 20l, 45l));
		candidateSuppliers
				.put(cNode, setupSupplier(2l, 7l, 13l, 10l, 12l, 18l));
		ConcreteSupplyNetwork csn = new ConcreteSupplyNetwork(asn,
				candidateSuppliers);

		CoordinationFitResult result = coordinationFitService.evaluate(csn);
		assertNotNull(result);

		assertEquals(6.0, result.getMinTotalCost(), 0.1);
		assertEquals(19.0, result.getAvgTotalCost(), 0.1);
		assertEquals(32.0, result.getMaxTotalCost(), 0.1);

		assertEquals(20, result.getMinTotalDuration());
		assertEquals(27, result.getAvgTotalDuration());
		assertEquals(58, result.getMaxTotalDuration());

		assertEquals(Arrays.asList(aNode, cNode), result.getCriticalPath());
	}

	private Supplier setupSupplier(long minCost, long avgCost, long maxCost,
			long minDuration, long avgDuration, long maxDuration) {
		Supplier supplier = new Supplier(null);
		supplier.setMinCostProposal(Double.valueOf(minCost));
		supplier.setAvgCostProposal(Double.valueOf(avgCost));
		supplier.setMaxCostProposal(Double.valueOf(maxCost));

		supplier.setMinDurationProposal(Long.valueOf(minDuration));
		supplier.setAvgDurationProposal(Long.valueOf(avgDuration));
		supplier.setMaxDurationProposal(Long.valueOf(maxDuration));

		return supplier;
	}

}
