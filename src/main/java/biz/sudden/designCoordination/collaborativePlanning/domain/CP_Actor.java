package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import biz.sudden.baseAndUtility.domain.Actor;

/**
 * 
 * In the context of "CollaborativePlanning", Actor acts as an NegotiationIssue
 * and further as CommunicationItem, so that it is possible to talk about
 * Actors. Due to the fact that "Actor" is a base domain objects, this actor
 * should only act as an adapter for the "Actor" which resides in
 * "baseAndUtility"
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
// @DiscriminatorValue("CPActor")
// public class CP_Actor extends NegotiationIssue implements IDInterface {
public class CP_Actor extends NegotiationIssue {

	private Actor actor;
	private Long id;

	// private CommunicationContainer communicationContainer;
	// private List<Communication> sentCommunications;
	// private List<Communication> receivedCommunication;
	//	

	// public CP_Actor(Actor actor) {
	// setActor(actor);
	// }
	//	
	// public CP_Actor() {
	// // TODO Auto-generated constructor stub
	// }

	// @OneToMany(cascade = {CascadeType.ALL}, mappedBy="sender",
	// fetch=FetchType.EAGER)
	// public List<Communication> getSentCommunications() {
	// return sentCommunications;
	// }
	//
	// public void setSentCommunications(List<Communication> sentCommunications)
	// {
	// this.sentCommunications = sentCommunications;
	// }
	//
	// @ManyToMany(cascade = {CascadeType.ALL}, mappedBy="receiver")
	// @LazyCollection(value=LazyCollectionOption.FALSE)
	// public List<Communication> getReceivedCommunication() {
	// return receivedCommunication;
	// }
	//
	// public void setReceivedCommunication(List<Communication>
	// receivedCommunication) {
	// this.receivedCommunication = receivedCommunication;
	// }
	//	
	// @OneToOne(cascade = {CascadeType.ALL}, mappedBy="owner")
	// public CommunicationContainer getCommunicationContainer() {
	// return communicationContainer;
	// }
	//
	// public void setCommunicationContainer(CommunicationContainer
	// communicationContainer) {
	// this.communicationContainer = communicationContainer;
	// }

	@Transient
	public String getName() {
		return actor.getName();
	}

	public void setName(String name) {
		actor.setName(name);
	}

	// @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	// @ManyToMany(mappedBy="actorList")
	// @JoinColumn(nullable=true)
	// public List<Negotiation> getNegotiationList() {
	// return negotiationList;
	// }
	//
	// public void setNegotiationList(List<Negotiation> negotiationList) {
	// this.negotiationList = negotiationList;
	// }

	// public Long getId() {
	// return this.id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }

}
