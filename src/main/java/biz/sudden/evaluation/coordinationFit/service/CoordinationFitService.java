package biz.sudden.evaluation.coordinationFit.service;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;

public interface CoordinationFitService {
	CoordinationFitResult evaluate(ConcreteSupplyNetwork csn);

	void computeCoordinationScore(ConcreteSupplyNetwork csn, int phaze);

	void computeEfficiencyRating(CaseFile caseFile);

	void computeEfficiencyRating(ConcreteSupplyNetwork csn);
}