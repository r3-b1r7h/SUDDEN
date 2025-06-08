package biz.sudden.designCoordination.handleBO.dataStructures;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

/**
 * The base 'node' class for the ASN.
 * 
 * These are then linked by dependencies
 * 
 * @author mcassmc
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ASNNode implements Connectable {

	Long id;

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

	public boolean equals(ASNNode other) {
		if (other == null)
			return false;

		if (this.id != null && other.id != null)
			return this.id.equals(other.id);
		else
			return super.equals(other);
	}

	@Override
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	// @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public ASNNode clone() {
		ASNNode result = new ASNNode();

		return result;
	}
}
