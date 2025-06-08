package biz.sudden.evaluation.coordinationFit.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.repository.CaseFileRepository;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNInitialNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.evaluation.coordinationFit.service.CoordinationFitResult;
import biz.sudden.evaluation.coordinationFit.service.CoordinationFitService;
import biz.sudden.evaluation.coordinationFit.service.CoordinationFitResult.State;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.domain.DimensionInstance;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMInstancesManagement_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public class CoordinationFitServiceImpl implements CoordinationFitService {
	private static final Map<String, Double> coordinationScoreMatrix = new HashMap<String, Double>();
	static {
		coordinationScoreMatrix.put("10_10", 10.0);
		coordinationScoreMatrix.put("7_10", 9.0);
		coordinationScoreMatrix.put("5_10", 8.0); // Small or low
		// coordinationScoreMatrix.put("5_10", 6.0); //medium
		// coordinationScoreMatrix.put("5_10", 2.0); //High value
		coordinationScoreMatrix.put("10_7", 7.0);
		coordinationScoreMatrix.put("7_7", 7.0);
		coordinationScoreMatrix.put("5_7", 6.0); // Small and low value
		// coordinationScoreMatrix.put("1_2", 4.0); //Medium value
		coordinationScoreMatrix.put("10_5", 1.0);
		coordinationScoreMatrix.put("7_1", 3.0);
		coordinationScoreMatrix.put("5_1", 4.0);
	}

	CaseFileRepository caseFileRepository;
	ICMCompetencesManagement_Service cms;
	ICMInstancesManagement_Service cims;

	public void setCaseFileRepository(CaseFileRepository caseFileRepository) {
		this.caseFileRepository = caseFileRepository;
	}

	public void setCompetencesManagementService(
			ICMCompetencesManagement_Service cms) {
		this.cms = cms;
	}

	public void setInstancesManagementService(
			ICMInstancesManagement_Service cims) {
		this.cims = cims;
	}

	private static final SupplierMetricProvider minCostProvider = new SupplierMetricProvider() {
		@Override
		public double getMetricForSupplier(Supplier supplier) {
			return supplier.getMinCostProposal();
		}
	};

	private static final SupplierMetricProvider avgCostProvider = new SupplierMetricProvider() {
		@Override
		public double getMetricForSupplier(Supplier supplier) {
			return supplier.getAvgCostProposal();
		}
	};

	private static final SupplierMetricProvider maxCostProvider = new SupplierMetricProvider() {
		@Override
		public double getMetricForSupplier(Supplier supplier) {
			return supplier.getMaxCostProposal();
		}
	};

	private static final SupplierMetricProvider minDurationProvider = new SupplierMetricProvider() {
		@Override
		public double getMetricForSupplier(Supplier supplier) {
			return supplier.getMinDurationProposal();
		}
	};

	private static final SupplierMetricProvider avgDurationProvider = new SupplierMetricProvider() {
		@Override
		public double getMetricForSupplier(Supplier supplier) {
			return supplier.getAvgDurationProposal();
		}
	};

	private static final SupplierMetricProvider maxDurationProvider = new SupplierMetricProvider() {
		@Override
		public double getMetricForSupplier(Supplier supplier) {
			return supplier.getMaxDurationProposal();
		}
	};

	private static final SupplierMetricProvider inTimeEfficiencyRatingProvider = new SupplierMetricProvider() {
		@Override
		public double getMetricForSupplier(Supplier supplier) {
			return Math.abs(supplier.getInTimeRating());
		}
	};

	private static final SupplierMetricProvider inBudgetEfficiencyRatingProvider = new SupplierMetricProvider() {
		@Override
		public double getMetricForSupplier(Supplier supplier) {
			return Math.abs(supplier.getInBudgetRating());
		}
	};

	@Override
	public CoordinationFitResult evaluate(final ConcreteSupplyNetwork csn) {
		CSNNodeProvider nodeInfoProvider = new CSNNodeProvider(csn,
				minCostProvider);
		double minTotalCost = Algorithms.computeTotalWeigth(nodeInfoProvider);

		nodeInfoProvider = new CSNNodeProvider(csn, avgCostProvider);
		double avgTotalCost = Algorithms.computeTotalWeigth(nodeInfoProvider);

		CSNNodeProvider nodeCostInfoProvider = new CSNNodeProvider(csn,
				maxCostProvider);
		double maxTotalCost = Algorithms
				.computeTotalWeigth(nodeCostInfoProvider);

		nodeInfoProvider = new CSNNodeProvider(csn, minDurationProvider);
		double minTotalDuration = Algorithms
				.computeWeigthOnCriticalPath(nodeInfoProvider);

		nodeInfoProvider = new CSNNodeProvider(csn, avgDurationProvider);
		double avgTotalDuration = Algorithms
				.computeWeigthOnCriticalPath(nodeInfoProvider);
		Path<ASNRoleNode> criticalPath = Algorithms
				.findCriticalPath(nodeInfoProvider);

		nodeInfoProvider = new CSNNodeProvider(csn, maxDurationProvider);
		double maxTotalDuration = Algorithms
				.computeWeigthOnCriticalPath(nodeInfoProvider);

		CoordinationFitResult result = csn.getCoordinationFitResults();
		if (result == null) {
			result = new CoordinationFitResult(csn);
			csn.setCoordinationFitResults(result);
		}
		result.setState(State.COMPLETED);

		result.setMinTotalCost(minTotalCost);
		result.setMaxTotalCost(maxTotalCost);
		result.setAvgTotalCost(avgTotalCost);

		result.setMinTotalDuration((long) minTotalDuration);
		result.setMaxTotalDuration((long) maxTotalDuration);
		result.setAvgTotalDuration((long) avgTotalDuration);

		if (criticalPath != null) {
			List<ASNRoleNode> list = new LinkedList<ASNRoleNode>();
			for (ASNRoleNode role : criticalPath.getPath()) {
				list.add(role);
			}
			result.setCriticalPath(list);
		}

		return result;
	}

	@Override
	public void computeEfficiencyRating(CaseFile caseFile) {
		for (ConcreteSupplyNetwork csn : caseFile.getTempTeams()) {
			computeEfficiencyRating(csn);
		}

		caseFileRepository.update(caseFile);
	}

	@Override
	public void computeEfficiencyRating(ConcreteSupplyNetwork csn) {
		CoordinationFitResult result = csn.getCoordinationFitResults();
		if (result == null) {
			result = new CoordinationFitResult(csn);
			csn.setCoordinationFitResults(result);
		}
		// if (result.getInTimeEfficiencyRating() == Double.NEGATIVE_INFINITY
		// && result.getInBudgetEfficiencyRating() == Double.NEGATIVE_INFINITY)
		// {
		result.setInBudgetEfficiencyRating(computeEfficiency(csn,
				inBudgetEfficiencyRatingProvider));
		result.setInTimeEfficiencyRating(computeEfficiency(csn,
				inTimeEfficiencyRatingProvider));

		System.out.println("Computed efficiency ratings for " + csn.getId()
				+ " inbudget:" + result.getInBudgetEfficiencyRating()
				+ " intime:" + result.getInTimeEfficiencyRating());
		// }
	}

	private double computeEfficiency(ConcreteSupplyNetwork csn,
			SupplierMetricProvider metricProvider) {
		double sum = 0;
		int counter = 0;
		for (Map.Entry<ASNRoleNode, Supplier> entry : csn
				.getCandidateSuppliersAsMap().entrySet()) {

			ASNRoleNode sourceNode = entry.getKey();
			Set<ASNRoleNode> targetNodes = csn
					.getNodesConnectedWithNode(sourceNode);

			for (ASNRoleNode targetNode : targetNodes) {
				if (targetNode instanceof ASNInitialNode)
					continue;

				Supplier sourceSupplier = csn.getSupplierForNode(sourceNode);
				Supplier targetSupplier = csn.getSupplierForNode(targetNode);
				if (sourceSupplier == null || targetSupplier == null)
					continue;

				Double supplierRating = computeRatingForSupplier(sourceSupplier
						.getOrganisation(), targetSupplier.getOrganisation(),
						metricProvider);
				if (supplierRating == null)
					continue;

				counter++;
				sum += supplierRating.doubleValue();
			}
		}
		return sum / counter;
	}

	private Double computeRatingForSupplier(Organization supplier1,
			Organization supplier2, SupplierMetricProvider metricProvider) {
		double sum = 0;
		int counter = 0;
		List<CaseFile> allCompleted = caseFileRepository.retrieveAllCompleted();
		for (CaseFile caseFile : allCompleted) {
			ConcreteSupplyNetwork finalTeam = caseFile.getFinalTeam();

			if (finalTeam == null)
				continue;

			Map<Supplier, Supplier> dependentSuppliers = finalTeam
					.findDependentSuppliers(supplier1, supplier2);
			if (dependentSuppliers.isEmpty())
				continue;

			for (Map.Entry<Supplier, Supplier> entry : dependentSuppliers
					.entrySet()) {
				counter++;
				sum += computeRatingForPair(entry.getKey(), entry.getValue(),
						metricProvider);
			}
		}
		if (counter == 0)
			return null;

		return new Double(sum / counter);
	}

	private double computeRatingForPair(Supplier supplier1, Supplier supplier2,
			SupplierMetricProvider metricProvider) {
		double a = metricProvider.getMetricForSupplier(supplier1);
		double b = metricProvider.getMetricForSupplier(supplier2);

		// System.out.println("s1 = "+a+" s2 = "+b+" min: "+Math.min(a, b));
		return Math.min(a, b);
	}

	@Override
	public void computeCoordinationScore(ConcreteSupplyNetwork csn, int phaze) {
		CoordinationFitResult result = csn.getCoordinationFitResults();
		if (result == null) {
			result = new CoordinationFitResult(csn);
			csn.setCoordinationFitResults(result);
		}
		// if (result.getCoordinationScore() == Double.NEGATIVE_INFINITY) {
		result.setCoordinationScore(doComputeCoordinationScore(csn, phaze));

		System.out.println("Computed coordination score for " + csn.getId()
				+ " score: " + result.getCoordinationScore());
		// }
	}

	private double doComputeCoordinationScore(ConcreteSupplyNetwork csn,
			int phaze) {
		int count = 0;
		double score = 0.0;

		for (ASNRoleNode node : csn.getASN().getAllRoleNodes()) {
			System.out.println("node: " + node.getId());
		}

		Map<ASNRoleNode, Supplier> suppliers = csn.getCandidateSuppliersAsMap();
		for (Map.Entry<ASNRoleNode, Supplier> entry : suppliers.entrySet()) {
			System.out.println("node: " + entry.getKey().getId()
					+ " provided by supplier:" + entry.getValue().getId());

			Supplier supplier = entry.getValue();

			if (supplier == null)
				continue;

			Collection<ASNRoleNode> clients = csn
					.getNodesConnectedbyMaterialDependency(entry.getKey());
			for (ASNRoleNode clientrole : clients) {
				System.out.println("node: " + entry.getKey().getId()
						+ " connected to node: " + clientrole.getId());
				Supplier client = csn.getSupplierForNode(clientrole);
				if (client == null)
					continue;

				score += computeCoordinationScore(supplier, client, phaze);
				count++;
			}
		}

		return score / count;
	}

	private double computeCoordinationScore(Supplier supplier, Supplier client,
			int phaze) {
		// List<Dimension> retrieveAllDimensions = cms.retrieveAllDimensions();
		List<Dimension> dimensions = cms
				.retrieveDimensionByName(chooseQuestion(phaze));
		if (dimensions.size() == 0)
			return 0.0;

		Dimension dimension = dimensions.get(0);

		float supplierValue = getDimensionValue(dimension, supplier);
		float clientValue = getDimensionValue(dimension, client);
		System.out.println("supplier: " + supplierValue + " client: "
				+ clientValue);

		Double value = coordinationScoreMatrix.get(Integer
				.toString((int) supplierValue)
				+ "_" + Integer.toString((int) clientValue));
		if (value == null)
			return 0;

		return value;
	}

	private float getDimensionValue(Dimension dimension, Supplier supplier) {
		if (supplier == null)
			return 0;

		DimensionInstance dimensionInstance = cims
				.retrieveDimensionInstanceByOrganization(supplier
						.getOrganisation().getId(), dimension.getId());
		if (dimensionInstance != null) {
			System.out.println("value: " + dimensionInstance.getValue()
					+ " autovalue: " + dimensionInstance.getAutoCalcValue());
			return dimensionInstance.getAutoCalcValue();
		}

		return 0;
	}

	private String chooseQuestion(int phaze) {
		switch (phaze) {
		case 0:
		case 1:
		case 2:
			return "7.) Welche CAE/CAD/ CAM - Tools werden verwendet?";
		default:
			return "23.) Welche Logistiksysteme werden von Ihnen bereits verwendet?";
		}
	}

}
