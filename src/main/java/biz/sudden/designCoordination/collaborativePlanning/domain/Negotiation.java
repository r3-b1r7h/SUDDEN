package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * 
 * Negotiation acts as a container due to the negotiation of negotiation items
 * between Actors. A Negotiation can contain two or more Actors and an arbitrary
 * number of NegotiationItems
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
public class Negotiation implements IDInterface {

	private Long id;
	private Contract contract;

	// private List<CP_Actor> actorList;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	// @ManyToMany
	// @JoinColumn(nullable=false)
	// public List<CP_Actor> getActorList() {
	// return actorList;
	// }
	//
	// public void setActorList(List<CP_Actor> actorList) {
	// this.actorList = actorList;
	// }

}