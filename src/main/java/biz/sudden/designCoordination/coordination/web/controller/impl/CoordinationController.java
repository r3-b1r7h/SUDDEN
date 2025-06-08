package biz.sudden.designCoordination.coordination.web.controller.impl;

import java.util.List;

import biz.sudden.designCoordination.coordination.service.impl.CoordinationService;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency.TransportationLogistics;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode.ProductionMethod;

public interface CoordinationController {

	/*
	 * Add what makes sense/is needed here. Hopefully not a lot :)
	 */

	public CoordinationService getCoordService();

	public void setCoordService(CoordinationService cs);

	public ConcreteSupplyNetwork getCSN();

	public void init();

	public void setCSN(ConcreteSupplyNetwork csnIN);

	public List<ASNRoleNode> getccsnrolenodes();

	public List<ASNMaterialDependency> getmaterialDependencies();

	public void productionMethodChanged(ASNRoleNode node,
			ProductionMethod newValue);

	public void transportationLogisticsChanged(ASNMaterialDependency node,
			TransportationLogistics newValue);

	public void selectNodeInTable(ASNRoleNode node);
}
