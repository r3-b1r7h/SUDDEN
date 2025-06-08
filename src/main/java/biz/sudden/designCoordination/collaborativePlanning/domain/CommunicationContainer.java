package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * 
 * This domain object acts as an container to allow the retrieval of
 * Communication History (each history entry connects a Communication and a
 * CommunicationItem)
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
public class CommunicationContainer implements IDInterface {

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// private Long id;
	// private List<Communication> communications = new
	// LinkedList<Communication>();
	// private String name;
	// //private CommunicationItem communicationItem;
	// //private CP_Actor owner;
	//	
	// // @OneToOne(cascade = {CascadeType.ALL})
	// // public CP_Actor getOwner() {
	// // return owner;
	// // }
	// //
	// // public void setOwner(CP_Actor owner) {
	// // this.owner = owner;
	// // }
	//
	// public String getName() {
	// return name;
	// }
	//
	// public void setName(String name) {
	// this.name = name;
	// }
	// //
	// // @OneToMany
	// // public CommunicationItem getCommunicationItem() {
	// // return communicationItem;
	// // }
	// //
	// // public void setCommunicationItem(CommunicationItem communicationItem)
	// {
	// // this.communicationItem = communicationItem;
	// // }
	//
	// @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// public Long getId() {
	// return this.id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }
	//
	// @ManyToMany(cascade = {CascadeType.ALL}, mappedBy="receiver")
	// public List<Communication> getCommunications() {
	// return communications;
	// }
	//
	// public void setCommunications(List<Communication> communications) {
	// this.communications = communications;
	// }
	//	
	// public void addCommunication(Communication communication) {
	// this.communications.add(communication);
	// }

}
