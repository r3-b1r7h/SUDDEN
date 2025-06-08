package biz.sudden.designCoordination.teamFormation.dataStructures;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

/**
 * A subtype of role node which has a supplier 'preassigned' to it. Used when
 * for instance the OEM requires that a specific supplier should be used for a
 * specific product/project.
 * 
 * Also useable when feeding back partial teams into team formation (ie you only
 * want to replace one supplier etc.).
 * 
 * @author mcassmc
 * 
 */
@Entity
public class ASNRoleNodeFixedSupplier extends ASNRoleNode {

	private Organization ourFixedOrganisation;

	public ASNRoleNodeFixedSupplier() {
		super();
	}

	public ASNRoleNodeFixedSupplier(Organization organisationToUse,
			QualificationProfile qp, CompetenceNeeded compReqd) {
		this.ourFixedOrganisation = organisationToUse;
		this.setQualificationProfile(qp);
		this.setCompetenceNeeded(compReqd);
	}

	// TODO - any hibernate swiggles needed round here?

	public void setFixedOrganisation(Organization o) {
		this.ourFixedOrganisation = o;
	}

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	public Organization getFixedOrganisation() {
		return this.ourFixedOrganisation;
	}

	@Override
	public String toString() {
		String result = "";

		result += (" ASNNode for product "
				+ this.getQualificationProfile().productIndividualURI
				+ " With fixed supplier " + this.ourFixedOrganisation);

		return result;
	}

	@Override
	public ASNRoleNodeFixedSupplier clone() {
		ASNRoleNodeFixedSupplier result = new ASNRoleNodeFixedSupplier();
		if (this.getCompetenceNeeded() != null) {
			result.setCompetenceNeeded(this.getCompetenceNeeded().clone());
		} else {
			result.setCompetenceNeeded(null);
		}
		result.setFixedOrganisation(this.ourFixedOrganisation);
		result.setQualificationProfile(this.getQualificationProfile().clone());

		return result;
	}

}
