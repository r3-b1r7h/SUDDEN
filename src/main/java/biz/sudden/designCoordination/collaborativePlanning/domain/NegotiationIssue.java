package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 
 * NegotiationIssue acts as an superclass for Actor, Relation, Process,
 * Responsibility, Goal and BO. A NegotiationItem can contain an arbitrary
 * number of NegotiationIssues and a NegotiationIssue can have a constraint (but
 * does not need to have one)
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// @DiscriminatorColumn(
// name="type",
// discriminatorType=javax.persistence.DiscriminatorType.STRING
// )
// @DiscriminatorValue("Negotiation")
public class NegotiationIssue {

	private Long id;
	private Valuation valuation;
	private String type;

	// public String getType() {
	// return type;
	// }
	//
	// public void setType(String type) {
	// this.type = type;
	// }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// @ManyToOne(optional=true)
	// public Valuation getValuation() {
	// return valuation;
	// }
	//
	// public void setValuation(Valuation valuation) {
	// this.valuation = valuation;
	// }

	// @ManyToOne(optional=true)
	// @JoinColumn(nullable=true)
	// public NegotiationConstraint getNegotiationConstraint() {
	// return negotiationConstraint;
	// }
	//
	// public void setNegotiationConstraint(NegotiationConstraint
	// negotiationConstraint) {
	// this.negotiationConstraint = negotiationConstraint;
	// }
}
