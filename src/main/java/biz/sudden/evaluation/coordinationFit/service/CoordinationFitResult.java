package biz.sudden.evaluation.coordinationFit.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import edu.emory.mathcs.backport.java.util.Arrays;

@Embeddable
public class CoordinationFitResult {
	public enum State {
		NOT_REQUESTED, PENDING, COMPLETED
	};

	private double minTotalCost = 0.0d;
	private double avgTotalCost = 0.0d;
	private double maxTotalCost = 0.0d;

	private long minTotalDuration = 0l;
	private long avgTotalDuration = 0l;
	private long maxTotalDuration = 0l;

	private double inBudgetEfficiencyRating = Double.NEGATIVE_INFINITY;
	private double inTimeEfficiencyRating = Double.NEGATIVE_INFINITY;

	private double coordinationScore = Double.NEGATIVE_INFINITY;

	private State state = State.NOT_REQUESTED;

	private List<ASNRoleNode> criticalPath;

	private ConcreteSupplyNetwork concreteSupplyNetwork;

	protected CoordinationFitResult() {
	}

	public CoordinationFitResult(ConcreteSupplyNetwork concreteSupplyNetwork) {
		this.concreteSupplyNetwork = concreteSupplyNetwork;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public double getMinTotalCost() {
		return minTotalCost;
	}

	public void setMinTotalCost(double minTotalCost) {
		this.minTotalCost = minTotalCost;
	}

	public double getAvgTotalCost() {
		return avgTotalCost;
	}

	public void setAvgTotalCost(double avgTotalCost) {
		this.avgTotalCost = avgTotalCost;
	}

	public double getMaxTotalCost() {
		return maxTotalCost;
	}

	public void setMaxTotalCost(double maxTotalCost) {
		this.maxTotalCost = maxTotalCost;
	}

	public long getMinTotalDuration() {
		return minTotalDuration;
	}

	public void setMinTotalDuration(long minTotalDuration) {
		this.minTotalDuration = minTotalDuration;
	}

	public long getAvgTotalDuration() {
		return avgTotalDuration;
	}

	public void setAvgTotalDuration(long avgTotalDuration) {
		this.avgTotalDuration = avgTotalDuration;
	}

	public long getMaxTotalDuration() {
		return maxTotalDuration;
	}

	public void setMaxTotalDuration(long maxTotalDuration) {
		this.maxTotalDuration = maxTotalDuration;
	}

	public double getInBudgetEfficiencyRating() {
		return inBudgetEfficiencyRating;
	}

	@Transient
	public String getInBudgetEfficiencyRatingAsString() {
		if (inBudgetEfficiencyRating == Double.NEGATIVE_INFINITY
				|| inBudgetEfficiencyRating == 0.0d) {
			return "";
		}
		return Integer.toString((int) (inBudgetEfficiencyRating * 100)) + "%";
	}

	public void setInBudgetEfficiencyRating(double inBudgetEfficiencyRating) {
		this.inBudgetEfficiencyRating = inBudgetEfficiencyRating;
	}

	public double getInTimeEfficiencyRating() {
		return inTimeEfficiencyRating;
	}

	@Transient
	public String getInTimeEfficiencyRatingAsString() {
		if (inTimeEfficiencyRating == Double.NEGATIVE_INFINITY
				|| inTimeEfficiencyRating == 0.0d) {
			return "";
		}
		return Integer.toString((int) (inTimeEfficiencyRating * 100)) + "%";
	}

	public void setInTimeEfficiencyRating(double inTimeEfficiencyRating) {
		this.inTimeEfficiencyRating = inTimeEfficiencyRating;
	}

	@Transient
	public String getCoordinationScoreAsString() {
		if (coordinationScore == Double.NEGATIVE_INFINITY
				|| coordinationScore == 0.0d) {
			return "";
		}
		// return Integer.toString((int) (coordinationScore*10))+"%";
		return Integer.toString((int) (coordinationScore * 10)) + "%";
	}

	public double getCoordinationScore() {
		return coordinationScore;
	}

	public void setCoordinationScore(double coordinationScore) {
		this.coordinationScore = coordinationScore;
	}

	public void setCoordinationScore(Double coordinationScore) {
		if (coordinationScore != null)
			this.coordinationScore = coordinationScore;
		else
			this.coordinationScore = Double.NEGATIVE_INFINITY;
	}

	@Transient
	public ConcreteSupplyNetwork getConcreteSupplyNetwork() {
		return concreteSupplyNetwork;
	}

	public void setConcreteSupplyNetwork(
			ConcreteSupplyNetwork concreteSupplyNetwork) {
		this.concreteSupplyNetwork = concreteSupplyNetwork;
	}

	@OneToMany(fetch = FetchType.EAGER)
	public List<ASNRoleNode> getCriticalPath() {
		return criticalPath;
	}

	public void setCriticalPath(List<ASNRoleNode> criticalPath) {
		this.criticalPath = criticalPath;
	}

	@Transient
	public Set<Entry<ASNRoleNode, Supplier>> getCriticalPathWithSuppliers() {
		if (criticalPath == null)
			return null;

		Map<ASNRoleNode, Supplier> map = new LinkedHashMap<ASNRoleNode, Supplier>();

		QualificationProfile qp = new QualificationProfile();
		qp.setProductTypes(Arrays.asList(new String[] { "Initial Node" }));
		ASNRoleNode initial = new ASNRoleNode();
		initial.setQualificationProfile(qp);
		map.put(initial, null);

		for (ASNRoleNode pathEntry : criticalPath) {
			map.put(pathEntry, concreteSupplyNetwork
					.getSupplierForNode(pathEntry));
		}
		return map.entrySet();
	}
}