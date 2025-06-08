package biz.sudden.designCoordination.teamFormation.dataStructures;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

/**
 * 
 * A class designed to hold that information relating to an organisation within
 * a given network. In particular it also contains the actual organisation but
 * also projected/actual performance & suitbaility for it's assigned role.
 * 
 * @author mcassmc
 * @author gweich
 */
@Entity
public class Supplier implements Connectable {

	Long id;
	/*
	 * The actual organisation in question Stored as an object not a pointer -
	 * this may have to change in the future.
	 */
	Organization theOrganisation;

	/*
	 * For recording the values returned when organisations are asked to supply
	 * it before feeding into coordination fit This info is project specific so
	 * has to be stored here rather than within the main organisation class.
	 */
	Double minCostProposal;
	Double avgCostProposal;
	Double maxCostProposal;

	Long minDurationProposal;
	Long avgDurationProposal;
	Long maxDurationProposal;

	/*
	 * For recording how well the supplier is doing within a specific project
	 */
	Date startDate;
	Date actualsForDate;
	Double costActual;
	Long durationActual;

	/*
	 * For recording how well this organisation matches it's assigned role
	 */
	Double scoreForRole;

	// Just get/set stuff and a constructor. Joy :)
	public Supplier() {
	}

	public Supplier(Organization organisationIn) {
		this.theOrganisation = organisationIn;
	}

	public Double getMinCostProposal() {
		return this.minCostProposal;
	}

	public Double getAvgCostProposal() {
		return avgCostProposal;
	}

	public Double getMaxCostProposal() {
		return maxCostProposal;
	}

	public Long getMinDurationProposal() {
		return this.minDurationProposal;
	}

	public Long getAvgDurationProposal() {
		return avgDurationProposal;
	}

	public Long getMaxDurationProposal() {
		return maxDurationProposal;
	}

	@Transient
	public Double getTimePercentageSpent() {
		return getTimePercentageSpentTill(actualsForDate);
	}

	@Transient
	public Double getTimePercentageSpentTill(Date date) {
		if (startDate == null)
			return 0.0;

		if (avgDurationProposal == null)
			return 0.0;

		long start = startDate.getTime();
		long expectedEnd = start + getExpectedDurationInMiliis();

		if (date == null)
			return 0.0;

		long currentDate = date.getTime();
		if (currentDate < start)
			return 0.0;

		return new Double((double) (currentDate - start)
				/ (double) (expectedEnd - start));
	}

	@Transient
	private long getExpectedDurationInMiliis() {
		return (avgDurationProposal * 24 * 60 * 60 * 1000);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Transient
	public boolean isStarted() {
		return this.startDate != null;
	}

	@Transient
	public Date getDueDate() {
		if (startDate == null)
			return null;

		return new Date((startDate.getTime() + getExpectedDurationInMiliis()));
	}

	@SuppressWarnings("deprecation")
	public boolean isCompleted() {
		if (actualsForDate == null || getDueDate() == null)
			return false;

		Date dueDate = getDueDate();

		return dueDate.getYear() == actualsForDate.getYear()
				&& dueDate.getMonth() == actualsForDate.getMonth()
				&& dueDate.getDate() == actualsForDate.getDate();
	}

	protected void setCompleted(boolean completed) {
	}

	public Date getActualsForDate() {
		return actualsForDate;
	}

	public void setActualsForDate(Date actualsForDate) {
		this.actualsForDate = actualsForDate;
	}

	public Double getCostActual() {
		return this.costActual;
	}

	public Long getDurationActual() {
		return this.durationActual;
	}

	public Double getScoreForRole() {
		return this.scoreForRole;
	}

	@ManyToOne
	public Organization getOrganisation() {
		return this.theOrganisation;
	}

	public void setMinCostProposal(Double costProposalIn) {
		this.minCostProposal = costProposalIn;
	}

	public void setAvgCostProposal(Double costProposalIn) {
		this.avgCostProposal = costProposalIn;
	}

	public void setMaxCostProposal(Double costProposalIn) {
		this.maxCostProposal = costProposalIn;
	}

	public void setMinDurationProposal(Long durationProposalIn) {
		this.minDurationProposal = durationProposalIn;
	}

	public void setAvgDurationProposal(Long durationProposalIn) {
		this.avgDurationProposal = durationProposalIn;
	}

	public void setMaxDurationProposal(Long durationProposalIn) {
		this.maxDurationProposal = durationProposalIn;
	}

	public void setCostActual(Double costActualIn) {
		this.costActual = costActualIn;
	}

	public void setDurationActual(Long durationActualIn) {
		this.durationActual = durationActualIn;
	}

	@Transient
	public Double getEstimatedCost() {
		if (avgCostProposal == null)
			return 0.0;

		double expected = getTimePercentageSpent() * getAvgCostProposal();

		if (expected > getAvgCostProposal())
			return getAvgCostProposal();
		return expected;
	}

	@Transient
	public Double getEstimatedDuration() {
		if (avgDurationProposal == null)
			return 0.0;

		double expected = getTimePercentageSpent() * getAvgDurationProposal();
		if (expected > getAvgDurationProposal())
			return new Double(getAvgDurationProposal().doubleValue());
		return expected;
	}

	@Transient
	public boolean isCostWithinLimits() {
		if (costActual == null)
			return true;

		double abs = Math.abs(costActual / getEstimatedCost());

		return abs > 0.8 && abs < 1.2;
	}

	@Transient
	public boolean isDurationWithinLimits() {
		if (durationActual == null)
			return true;

		double abs = Math.abs(durationActual / getEstimatedDuration());

		return abs > 0.8 && abs < 1.2;
	}

	@Transient
	public double getInBudgetRating() {
		if (this.avgCostProposal != null && this.costActual != null)
			return (this.avgCostProposal - Math.abs(this.costActual
					- this.avgCostProposal))
					/ this.avgCostProposal;
		else
			return 0.6d;
	}

	@Transient
	public double getInTimeRating() {
		if (this.avgDurationProposal != null && this.durationActual != null)
			return ((double) this.avgDurationProposal - Math
					.abs((double) this.durationActual
							- (double) this.avgDurationProposal))
					/ (double) this.avgDurationProposal;
		else
			return 0.6d;
	}

	public void setScoreForRole(Double suppliersScore) {
		this.scoreForRole = suppliersScore;
	}

	public void setOrganisation(Organization o) {
		this.theOrganisation = o;
	}

	@Transient
	public boolean isReadyForCF() {
		return minCostProposal != null && avgCostProposal != null
				&& maxCostProposal != null && minDurationProposal != null
				&& avgDurationProposal != null && maxDurationProposal != null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getOrganisation().toString();
	}

	/**
	 * A clone method designed to ONLY be used before any of the coordination
	 * fit data has been added. So it clones the organisation added + the data
	 * regarding how well it evaluates vs the specified criteria. It
	 * purposefully doesn't do the coordination fit stuff which I'm assuming not
	 * to have been initialised when this is used.
	 * 
	 * @return
	 */
	public Supplier limitedClone() {
		Supplier result = new Supplier();

		result.setOrganisation(this.theOrganisation);
		result.setScoreForRole(this.scoreForRole);

		return result;
	}
}
