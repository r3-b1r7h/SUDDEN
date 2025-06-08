package biz.sudden.designCoordination.handleBO.dataStructures;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;

/**
 * 
 * This class represent the node of the ASN which remembers the parent business
 * opportunity initially used to generated the ASN itself.
 * 
 * For the moment it just stores the BO itself. In the future you could
 * potential chain dependencies off this to store more information about the BO
 * etc.
 * 
 * @author mcassmc
 * 
 */
@Entity
public class ASNBONode extends ASNNode {

	private BusinessOpportunity myBO;

	public ASNBONode() {
	}

	public ASNBONode(BusinessOpportunity BOin) {
		this.myBO = BOin;
	}

	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	public BusinessOpportunity getBusinessOpportunity() {
		return this.myBO;
	}

	public void setBusinessOpportunity(BusinessOpportunity bo) {
		this.myBO = bo;
	}

	@Override
	public ASNBONode clone() {
		ASNBONode result = new ASNBONode();
		if (getBusinessOpportunity() != null) {
			result.setBusinessOpportunity(this.getBusinessOpportunity());
		}

		return result;
	}
}
