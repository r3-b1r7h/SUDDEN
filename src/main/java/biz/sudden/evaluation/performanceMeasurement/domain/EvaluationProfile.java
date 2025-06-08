package biz.sudden.evaluation.performanceMeasurement.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;

/**
 * The EnterpriseEvaluationProfile holds the following information:
 * <ul>
 * <li>Function to use for aggregation: <i>weighted sum.</i></li>
 * <li>Selected competence <i>dimensions</i> and <i>parameters</i> for that
 * aggregation function.
 * </ul>
 * It is applied to each organisation that is measured.
 * 
 * The NetworkEvaluationProfile is used to connect different EvaluationProfile
 * results from an enterprise each to get a single result. It requires:
 * <ul>
 * <li>A directed graph of enterprises to describe the overall flow</li>
 * <li>An evaluation profile which is applied to each enterprise</li>
 * <li>A function list and parameter list that is applied for connecting
 * different parts of the evaluation profile</li>
 * </ul>
 * 
 * @see EnterpriseEvaluationProfile
 * @see NetworkEvaluationProfile
 * @author gweich
 * 
 */
@MappedSuperclass
public abstract class EvaluationProfile implements Connectable {

	public static String ResultRoleType = "Result";

	Long id = 0L;
	String name = "";

	// Double result = 0.0D;

	public EvaluationProfile() {
		super();
	}

	public EvaluationProfile(String name) {
		super();
		this.setName(name);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	// @Column(unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}

	// /**
	// * @return the result of this Profile when applied to an enterprise
	// */
	// public Double getResult() {
	// return result;
	// }
	//
	// /**
	// * @param result the result when applied to an enterprise
	// */
	// public void setResult(Double result) {
	// this.result = result;
	// }

	@Override
	public boolean equals(Object o) {
		if (!(this.getClass().isInstance(o)))
			return false;

		if (this.getId().equals(((EvaluationProfile) o).getId())
				&& this.getName().equals(((EvaluationProfile) o).getName()))
			return true;
		return false;
	}

}
