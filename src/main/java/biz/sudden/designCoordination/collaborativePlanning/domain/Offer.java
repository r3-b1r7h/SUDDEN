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
 * An offer has one ore more NegotiationItems and one Actor. Further it is a
 * CommunicationItem, so it is possible to communicate about Offers
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
public class Offer implements IDInterface {

	private Long id;
	private CP_Actor actor;
	private List<NegotiationItem> negotiationItemList;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	public CP_Actor getActor() {
		return actor;
	}

	public void setActor(CP_Actor actor) {
		this.actor = actor;
	}

	@ManyToMany(mappedBy = "offerList")
	@JoinColumn(nullable = false)
	public List<NegotiationItem> getNegotiationItemList() {
		return negotiationItemList;
	}

	public void setNegotiationItemList(List<NegotiationItem> negotiationItemList) {
		this.negotiationItemList = negotiationItemList;
	}

}
