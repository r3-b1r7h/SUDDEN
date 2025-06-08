package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * 
 * In the context of "CollaborativePlanning", Responsibility acts as an
 * NegotiationIssue and further as CommunicationItem, so that it is possible to
 * talk about Responsibility. Due to the fact that "Responsibility" is a base
 * domain objects, this Responsibility should only act as an adapter for the
 * "Responsibility" which resides in "baseAndUtility"
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
public class Responsibility extends NegotiationIssue implements IDInterface {

	// private Long id;
	private CP_Actor actor;

	// @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// public Long getId() {
	// return this.id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }

	@ManyToOne
	public CP_Actor getActor() {
		return actor;
	}

	public void setActor(CP_Actor actor) {
		this.actor = actor;
	}

}