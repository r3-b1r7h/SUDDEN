package biz.sudden.designCoordination.teamFormation.dataStructures;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import biz.sudden.designCoordination.handleBO.dataStructures.ASNNode;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;

/**
 * A type of abstract supply network node which happens to be a role ie to have
 * stuff like evaluation criteria etc attached to it
 * 
 * @author mcassmc
 * 
 */
@Entity
public class ASNRoleNode extends ASNNode {

	/*
	 * For coordination
	 */
	public enum ProductionMethod {
		P2O, P2S
	};

	private ProductionMethod myPM;

	// What you'd like them to do
	private CompetenceNeeded competenceNeeded;

	// What they must do to be considered
	private QualificationProfile myRequiredQualifications;

	public ASNRoleNode() {
		competenceNeeded = null;
		myRequiredQualifications = null;
	}

	@Transient
	public ProductionMethod getproductionMethod() {
		return this.myPM;
	}

	public void setproductionMethod(ProductionMethod PM) {
		this.myPM = PM;
	}

	// The null test here is because Spring wants to get a a bean with the
	// values well in advance of it
	// strictly being required. Really this bit of code (semi 'forced') to make
	// the Icefaces interface for coordination work
	// is somewhat ugly
	public String getproductType() {
		if (myRequiredQualifications == null) {
			return null;
		} else {
			return this.myRequiredQualifications.productIndividualURI;
		}
	}

	public void setproductType(String willBeIgnored) {
		// To stop Spring trying to create a bean with this and crashing.
		// Absolutely NOT meant to
		// set the product type directly. Ever.
	}

	public ASNRoleNode(CompetenceNeeded profileToUse,
			QualificationProfile qualificationsRequired) {
		competenceNeeded = profileToUse;
		myRequiredQualifications = qualificationsRequired;
	}

	public void setCompetenceNeeded(CompetenceNeeded evalProfileToUse) {
		this.competenceNeeded = evalProfileToUse;
	}

	public void setQualificationProfile(QualificationProfile qualProfileToUse) {
		this.myRequiredQualifications = qualProfileToUse;
	}

	/**
	 * Get the evaluation profile attached to the role.
	 * 
	 * Note that when bottom up TF is used the evaluation profile return by this
	 * method can in fact be null. Live with it :)
	 * 
	 * @return
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	public CompetenceNeeded getCompetenceNeeded() {
		return this.competenceNeeded;
	}

	/**
	 * Get the qualification profile for this role - this one shouldn't ever be
	 * null I think. To be more precise - it can be but only after bottom up TF
	 * and at that stage you won't be using this method call.
	 * 
	 * Only TDTF uses this.
	 */
	@OneToOne(cascade = { CascadeType.ALL }, optional = true)
	public QualificationProfile getQualificationProfile() {
		return this.myRequiredQualifications;
	}

	@Override
	public ASNRoleNode clone() {
		ASNRoleNode result = new ASNRoleNode();
		if (getCompetenceNeeded() != null)
			result.setCompetenceNeeded(this.getCompetenceNeeded().clone());
		if (getQualificationProfile() != null)
			result.setQualificationProfile(this.getQualificationProfile()
					.clone());

		return result;
	}

	@Override
	public String toString() {
		String result = "";

		if (this.getQualificationProfile() != null) {
			result += " Role Node, product required "
					+ getQualificationProfile().productIndividualURI;
		} else {
			result += " Role Node, no product attached";
		}

		return result;
	}

}
