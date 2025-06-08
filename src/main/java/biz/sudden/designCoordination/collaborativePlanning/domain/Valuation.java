package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * 
 * A Valuation concerns a certain Actor and an arbitrary number of
 * "NegotiationIssues". In the Valuation this Actor is evaluated for each
 * NegotiationIssue contained.
 * 
 */

@Entity
public class Valuation implements IDInterface {

	private Long id;
	private CP_Actor actor;

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

}