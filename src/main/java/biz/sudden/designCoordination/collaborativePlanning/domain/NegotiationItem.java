package biz.sudden.designCoordination.collaborativePlanning.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * 
 * A NegotiationItem acts as a container, which has a Negotiation, a
 * NegotiationIssue and a arbitrary number of Offers. Further it is a
 * CommunicationItem, so Actors are able to talk about NegotiationItems
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
public class NegotiationItem implements IDInterface {

	private Long id;
	private Negotiation negotiation;
	private NegotiationIssue negotiationIssue;
	private List<Offer> offerList;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	public Negotiation getNegotiation() {
		return negotiation;
	}

	public void setNegotiation(Negotiation negotiation) {
		this.negotiation = negotiation;
	}

	@ManyToOne
	public NegotiationIssue getNegotiationIssue() {
		return negotiationIssue;
	}

	public void setNegotiationIssue(NegotiationIssue negotiationIssue) {
		this.negotiationIssue = negotiationIssue;
	}

	@ManyToMany
	@JoinColumn(nullable = true)
	public List<Offer> getOfferList() {
		return offerList;
	}

	public void setOfferList(List<Offer> offerList) {
		this.offerList = offerList;
	}

}
