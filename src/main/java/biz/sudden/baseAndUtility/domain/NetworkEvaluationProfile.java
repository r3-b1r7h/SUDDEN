package biz.sudden.baseAndUtility.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeededByNetworkEvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.domain.EvaluationProfile;
import biz.sudden.knowledgeData.kdm.web.controller.impl.CaseFileControllerHelper;

/**
 * The NetworkEvaluationProfile is used to connect different EvaluationProfile
 * results from an enterprise to get a single result on network level.
 * 
 * @see EvaluationProfile
 * @see biz.sudden.baseAndUtility.domain.connectable.Connectable
 * @author gweich
 * 
 */
@Entity
public class NetworkEvaluationProfile extends EvaluationProfile {

	List<CompetenceNeededByNetworkEvaluationProfile> competenceAllHave;
	List<CompetenceNeededByNetworkEvaluationProfile> competenceOneOrMoreHave;
	Boolean SocialNetworkCount = Boolean.TRUE;

	public NetworkEvaluationProfile() {
		super();
		// competenceAllHave = new ArrayList<CompetenceNeeded>();
		// competenceOneOrMoreHave = new ArrayList<CompetenceNeeded>();
	}

	public NetworkEvaluationProfile(String name) {
		super(name);
	}

	// Boolean getSocialNetworkCount(){
	// return SocialNetworkCount;
	// }
	//	
	// public void setSocialNetworkCount(Boolean takeMeIntoAccount) {
	// if(takeMeIntoAccount!=null)
	// this.SocialNetworkCount = takeMeIntoAccount;
	// else
	// this.SocialNetworkCount = Boolean.TRUE;
	// }

	/**
	 * @return the competenceAllHave
	 */
	// @OneToMany(targetEntity=CompetenceNeededByNetworkEvaluationProfile.class,
	// mappedBy="networkEvaluationProfile")
	@Transient
	public List<CompetenceNeededByNetworkEvaluationProfile> getCompetenceAllHave() {
		return competenceAllHave;
	}

	@OneToMany(targetEntity = CompetenceNeededByNetworkEvaluationProfile.class, mappedBy = "networkEvaluationProfile", cascade = { CascadeType.ALL })
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<CompetenceNeededByNetworkEvaluationProfile> getAllCompetences() {
		if (competenceAllHave == null)
			competenceAllHave = new ArrayList<CompetenceNeededByNetworkEvaluationProfile>();
		if (competenceOneOrMoreHave == null)
			competenceOneOrMoreHave = new ArrayList<CompetenceNeededByNetworkEvaluationProfile>();
		List<CompetenceNeededByNetworkEvaluationProfile> all = new ArrayList<CompetenceNeededByNetworkEvaluationProfile>(
				competenceAllHave.size() + competenceOneOrMoreHave.size());
		all.addAll(competenceAllHave);
		all.addAll(competenceOneOrMoreHave);
		return all;
	}

	public void setAllCompetences(
			List<CompetenceNeededByNetworkEvaluationProfile> all) {
		if (all != null)
			if (competenceAllHave == null)
				competenceAllHave = new ArrayList<CompetenceNeededByNetworkEvaluationProfile>();
			else
				competenceAllHave.clear();
		if (competenceOneOrMoreHave == null)
			competenceOneOrMoreHave = new ArrayList<CompetenceNeededByNetworkEvaluationProfile>();
		else
			competenceOneOrMoreHave.clear();

		for (CompetenceNeededByNetworkEvaluationProfile comp : (List<CompetenceNeededByNetworkEvaluationProfile>) CaseFileControllerHelper
				.transformHibernateLazyObject(all)) {
			if (comp.getNumberOfSuppliersNeedCompetence().equals(
					CompetenceNeeded.ALL_NETWORK_MEMBERS_NEED_COMPETENCE))
				this.competenceAllHave.add(comp);
			else
				this.competenceOneOrMoreHave.add(comp);
			comp.setNetworkEvaluationProfile(this);
		}
	}

	/**
	 * @return the competenceOneOrMoreHave
	 */
	// @OneToMany(targetEntity=CompetenceNeededByNetworkEvaluationProfile.class,
	// mappedBy="networkEvaluationProfile")
	@Transient
	public List<CompetenceNeededByNetworkEvaluationProfile> getCompetenceOneOrMoreHave() {
		return competenceOneOrMoreHave;
	}

	public List<CompetenceNeededByNetworkEvaluationProfile> addCompetenceAllHave(
			CompetenceNeededByNetworkEvaluationProfile cah) {
		if (competenceAllHave == null)
			competenceAllHave = new ArrayList<CompetenceNeededByNetworkEvaluationProfile>();
		if (!alreadyIncluded(cah, getCompetenceAllHave())) {
			competenceAllHave.add(cah);
		}
		cah
				.setNumberOfSuppliersNeedCompetence(CompetenceNeeded.ALL_NETWORK_MEMBERS_NEED_COMPETENCE);
		cah.setNetworkEvaluationProfile(this);
		return this.getCompetenceAllHave();
	}

	/** returns element removed from list or null */
	public CompetenceNeededByNetworkEvaluationProfile deleteFromCompetenceAllHave(
			CompetenceNeededByNetworkEvaluationProfile cah) {
		if (cah.getId() != null) {
			return deleteFromCompetenceAllHave(cah.getId());
		}
		return null;
	}

	/** returns element removed from list or null */
	public CompetenceNeededByNetworkEvaluationProfile deleteFromCompetenceAllHave(
			Long competenceNeededID) {
		CompetenceNeededByNetworkEvaluationProfile result = null;
		if (competenceAllHave == null)
			competenceAllHave = new ArrayList<CompetenceNeededByNetworkEvaluationProfile>();
		for (int i = 0; i < competenceAllHave.size(); ++i) {
			CompetenceNeeded cn = competenceAllHave.get(i);
			if (cn.getId() != null && cn.getId().equals(competenceNeededID)) {
				result = competenceAllHave.remove(i);
				break;
			}
		}
		return result;
	}

	public List<CompetenceNeededByNetworkEvaluationProfile> addCompetenceOneOrMoreHave(
			CompetenceNeededByNetworkEvaluationProfile cah) {
		if (competenceOneOrMoreHave == null)
			competenceOneOrMoreHave = new ArrayList<CompetenceNeededByNetworkEvaluationProfile>();
		if (!alreadyIncluded(cah, this.getCompetenceOneOrMoreHave())) {
			this.getCompetenceOneOrMoreHave().add(cah);
		}
		cah.setNumberOfSuppliersNeedCompetence(1);
		cah.setNetworkEvaluationProfile(this);
		return this.getCompetenceOneOrMoreHave();
	}

	/** returns element removed from list or null */
	public CompetenceNeededByNetworkEvaluationProfile deleteFromCompetenceOneOrMoreHave(
			CompetenceNeededByNetworkEvaluationProfile cah) {
		if (cah.getId() != null) {
			return deleteFromCompetenceOneOrMoreHave(cah.getId());
		} else {
			return null;
		}
	}

	public CompetenceNeededByNetworkEvaluationProfile deleteFromCompetenceOneOrMoreHave(
			Long competenceNeededID) {
		CompetenceNeededByNetworkEvaluationProfile result = null;
		if (competenceOneOrMoreHave == null)
			competenceOneOrMoreHave = new ArrayList<CompetenceNeededByNetworkEvaluationProfile>();
		for (int i = 0; i < this.getCompetenceOneOrMoreHave().size(); ++i) {
			CompetenceNeeded cn = this.getCompetenceOneOrMoreHave().get(i);
			if (cn.getId() != null && cn.getId().equals(competenceNeededID)) {
				result = this.getCompetenceOneOrMoreHave().remove(i);
				break;
			}
		}
		return result;
	}

	private boolean alreadyIncluded(CompetenceNeeded cah,
			List<? extends CompetenceNeeded> competences) {
		boolean alreadyThere = false;
		for (CompetenceNeeded cn : competences) {
			if (cah.getIdOfCompetenceProfile() != null
					&& cn.getIdOfCompetenceProfile() != null
					&& cah.getIdOfCompetenceProfile().equals(
							cn.getIdOfCompetenceProfile())) {
				alreadyThere = true;
				break;
			}
		}
		return alreadyThere;
	}

	@Override
	public NetworkEvaluationProfile clone() {
		NetworkEvaluationProfile copy = new NetworkEvaluationProfile();
		copy.setName(getName());
		if (getAllCompetences() != null) {
			List<CompetenceNeededByNetworkEvaluationProfile> comps = new ArrayList<CompetenceNeededByNetworkEvaluationProfile>();
			for (CompetenceNeededByNetworkEvaluationProfile c : this
					.getAllCompetences()) {
				comps.add(c.clone());
			}
			copy.setAllCompetences(comps);
		}
		return copy;
	}

}
