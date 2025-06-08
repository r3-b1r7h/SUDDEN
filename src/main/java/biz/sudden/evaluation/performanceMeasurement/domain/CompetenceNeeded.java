package biz.sudden.evaluation.performanceMeasurement.domain;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CompetenceNeeded implements Connectable {

	public static int ALL_NETWORK_MEMBERS_NEED_COMPETENCE = -1;

	/**
	 * if value == ALL_NETWORK_MEMBERS_NEED_COMPETENCE -> all need it; 0 ->>
	 * no-one needs it; 1..n -> at least 1..n need it
	 */
	protected Integer NumberOfSuppliersNeedCompetence;

	public static int COMPETENCE_VALUE = 0;
	public static int ENTERPRISE_EVALUATION_PROFILE = 1;
	protected Integer TypeOfCompetenceProfile;

	/** Hibernate ID of Competence or EnterpriseEvaluationProfile */
	protected Long IdOfCompetenceProfile;

	/** Human Readable String of Competence or EnterpriseEvaluationProfile */
	protected String NameOfCompetenceProfile = "--";

	private Long Id;

	protected Double maxValue = new Double(10.0d);

	protected Double minValue = new Double(0.0d);

	// use this as cache!!
	protected HashMap<Long, Double> Value = new HashMap<Long, Double>();

	public CompetenceNeeded() {
	}

	/**
	 * @return the numberOfSuppliersNeedCompetence
	 */
	public Integer getNumberOfSuppliersNeedCompetence() {
		return NumberOfSuppliersNeedCompetence;
	}

	/**
	 * if value == ALL_NETWORK_MEMBERS_NEED_COMPETENCE -> all need it; 0 ->>
	 * no-one needs it; 1..n -> at least 1..n need it
	 * 
	 * @param numberOfSuppliersNeedCompetence
	 *            the numberOfSuppliersNeedCompetence to set
	 */
	public void setNumberOfSuppliersNeedCompetence(
			Integer numberOfSuppliersNeedCompetence) {
		NumberOfSuppliersNeedCompetence = numberOfSuppliersNeedCompetence;
	}

	/**
	 * takes either a EnterpriseEvaluationProfile or a CompetenceDimension and
	 * calls set[ID,Name, Type]
	 */
	public void setCompetenceProfile(Object profile) {
		if (profile != null) {
			if (profile instanceof EnterpriseEvaluationProfile) {
				setIdOfCompetenceProfile(((EnterpriseEvaluationProfile) profile)
						.getId());
				setNameOfCompetenceProfile(((EnterpriseEvaluationProfile) profile)
						.getName());
				setTypeOfCompetenceProfile(ENTERPRISE_EVALUATION_PROFILE);
			} else if (profile instanceof Dimension) {
				// FIXME VG: I need the reference to a single question which can
				// then be used to get the actual value for a company.
				setIdOfCompetenceProfile(((Dimension) profile).getId());
				setNameOfCompetenceProfile(((Dimension) profile).getName());
				setTypeOfCompetenceProfile(COMPETENCE_VALUE);
			} else {
				throw new RuntimeException(
						"Wrong Parameter... Expected EnterpriseEvaluationProfile Or Dimension  got: "
								+ profile.toString());
			}
		}
	}

	/**
	 * @return the maxValue
	 */
	public Double getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue
	 *            the maxValue to set
	 */
	public void setMaxValue(Double max) {
		this.maxValue = max;
	}

	/**
	 * @return the minValue
	 */
	public Double getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue
	 *            the minValue to set
	 */
	public void setMinValue(Double min) {
		this.minValue = min;
	}

	/** cache value */
	@Transient
	public Double getValue(Organization org) {
		return this.Value.get(org.getId());
	}

	public void setValue(Organization org, Double v) {
		this.Value.put(org.getId(), v);
	}

	/**
	 * @return the typeOfCompetenceProfile
	 */
	public Integer getTypeOfCompetenceProfile() {
		return TypeOfCompetenceProfile;
	}

	/**
	 * @param typeOfCompetenceProfile
	 *            the typeOfCompetenceProfile to set
	 */
	public void setTypeOfCompetenceProfile(Integer typeOfCompetenceProfile) {
		TypeOfCompetenceProfile = typeOfCompetenceProfile;
	}

	/**
	 * Hibernate ID of Competence or EnterpriseEvaluationProfile
	 * 
	 * @return the idOfCompetenceProfile
	 */
	public Long getIdOfCompetenceProfile() {
		return IdOfCompetenceProfile;
	}

	/**
	 * Hibernate ID of Competence or EnterpriseEvaluationProfile
	 * 
	 * @param idOfCompetenceProfile
	 *            the idOfCompetenceProfile to set
	 */
	public void setIdOfCompetenceProfile(Long idOfCompetenceProfile) {
		IdOfCompetenceProfile = idOfCompetenceProfile;
	}

	/**
	 * Human Readable Name of Competence or EnterpriseEvaluationProfile
	 * 
	 * @return the idOfCompetenceProfile
	 */
	public String getNameOfCompetenceProfile() {
		return NameOfCompetenceProfile;
	}

	/**
	 * Human Readable Name of Competence or EnterpriseEvaluationProfile
	 * 
	 * @param idOfCompetenceProfile
	 *            the idOfCompetenceProfile to set
	 */
	public void setNameOfCompetenceProfile(String nameOfCompetenceProfile) {
		NameOfCompetenceProfile = nameOfCompetenceProfile;
	}

	/**
	 * @return the iD
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return Id;
	}

	/**
	 * @param id
	 *            the iD to set
	 */
	@Override
	public void setId(Long id) {
		Id = id;
	}

	@Override
	public CompetenceNeeded clone() {
		CompetenceNeeded copy = new CompetenceNeeded();
		copy.setIdOfCompetenceProfile(getIdOfCompetenceProfile());
		copy.setMaxValue(this.getMaxValue());
		copy.setMinValue(this.getMinValue());
		copy.setNameOfCompetenceProfile(this.getNameOfCompetenceProfile());
		copy.setNumberOfSuppliersNeedCompetence(this
				.getNumberOfSuppliersNeedCompetence());
		copy.setTypeOfCompetenceProfile(this.getTypeOfCompetenceProfile());
		copy.Value = this.Value;
		return copy;
	}

	@Override
	public String toString() {
		return NameOfCompetenceProfile;

	}
}
