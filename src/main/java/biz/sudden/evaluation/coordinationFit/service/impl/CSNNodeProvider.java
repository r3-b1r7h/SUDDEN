package biz.sudden.evaluation.coordinationFit.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;

final class CSNNodeProvider implements NodeInfoProvider<ASNRoleNode> {
	private final ConcreteSupplyNetwork csn;
	private final SupplierMetricProvider smp;

	protected CSNNodeProvider(ConcreteSupplyNetwork csn,
			SupplierMetricProvider smp) {
		this.csn = csn;
		this.smp = smp;
	}

	@Override
	public Set<ASNRoleNode> getChildNodes(ASNRoleNode node) {
		return new LinkedHashSet<ASNRoleNode>(csn
				.getNodesConnectedbyMaterialDependency(node));
	}

	@Override
	public double getNodeWeight(ASNRoleNode node) {
		Supplier supplierForNode = csn.getSupplierForNode(node);
		if (supplierForNode == null)
			return 0;
		return smp.getMetricForSupplier(supplierForNode);
	}

	@Override
	public Set<ASNRoleNode> getRootNodes() {
		return new LinkedHashSet<ASNRoleNode>(csn
				.getNodesConnectedbyMaterialDependency(csn.getASN()
						.getFinalNode()));
	}
}