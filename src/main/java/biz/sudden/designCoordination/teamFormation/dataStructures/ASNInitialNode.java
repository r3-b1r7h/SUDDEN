package biz.sudden.designCoordination.teamFormation.dataStructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import biz.sudden.knowledgeData.serviceManagement.domain.Product;

/**
 * A distinguished class of Node within the abstract supply network.
 * 
 * This one contains the 'starting materials' given to the team building things.
 * ie those things which are provided as the initial, external thing which must
 * be added to/altered.
 * 
 * Note that while this does extend ASNRoleNode, it won't normally have a
 * qualification profile or a concrete supplier attached to it. It got promoted
 * to a role node to make the dependencies within the ASN work more easily.
 * 
 * @author mcassmc
 * 
 */
@Entity
public class ASNInitialNode extends ASNRoleNode {

	// TODO - Well why not use the product class here?

	public ArrayList<Product> productsSupplied;

	public ASNInitialNode() {
	}

	public ASNInitialNode(Collection<Product> productsSuppliedIn) {
		setProductsSupplied(productsSuppliedIn);
	}

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@Transient
	public List<Product> getProductsSupplied() {
		return this.productsSupplied;
	}

	public void setProductsSupplied(Collection<Product> productsSuppliedIn) {
		if (productsSuppliedIn != null)
			this.productsSupplied = new ArrayList<Product>(productsSuppliedIn);
		else
			this.productsSupplied = null;
	}

	@Override
	public ASNInitialNode clone() {
		ASNInitialNode result = new ASNInitialNode();
		if (this.getCompetenceNeeded() != null)
			result.setCompetenceNeeded(this.getCompetenceNeeded().clone());
		result.setProductsSupplied(this.getProductsSupplied());
		if (this.getQualificationProfile() != null)
			result.setQualificationProfile(this.getQualificationProfile()
					.clone());

		return result;
	}
}
