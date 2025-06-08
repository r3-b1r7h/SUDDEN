package biz.sudden.evaluation.performanceMeasurement.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;

@Entity
public class CompetenceNeededByNetworkEvaluationProfile extends
		CompetenceNeeded {

	Logger logger = Logger.getLogger(this.getClass());

	NetworkEvaluationProfile networkEvaluationProfile;

	public CompetenceNeededByNetworkEvaluationProfile() {
		super();
	}

	/**
	 * @return the nep
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	public NetworkEvaluationProfile getNetworkEvaluationProfile() {
		return networkEvaluationProfile;
	}

	/**
	 * @param nep
	 *            the nep to set
	 */
	public void setNetworkEvaluationProfile(NetworkEvaluationProfile nep) {
		this.networkEvaluationProfile = nep;
	}

	@Override
	public CompetenceNeededByNetworkEvaluationProfile clone() {
		CompetenceNeededByNetworkEvaluationProfile copy = new CompetenceNeededByNetworkEvaluationProfile();
		copy.setIdOfCompetenceProfile(getIdOfCompetenceProfile());
		copy.setMaxValue(this.getMaxValue());
		copy.setMinValue(this.getMinValue());
		copy.setNameOfCompetenceProfile(this.getNameOfCompetenceProfile());
		copy.setNetworkEvaluationProfile(this.getNetworkEvaluationProfile());
		copy.setNumberOfSuppliersNeedCompetence(this
				.getNumberOfSuppliersNeedCompetence());
		copy.setTypeOfCompetenceProfile(this.getTypeOfCompetenceProfile());
		return copy;
	}

}
