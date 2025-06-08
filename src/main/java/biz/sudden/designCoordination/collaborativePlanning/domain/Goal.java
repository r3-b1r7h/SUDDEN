package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * 
 * In the context of "CollaborativePlanning", Goal acts as an NegotiationIssue
 * and further as CommunicationItem, so that it is possible to talk about Goals.
 * Due to the fact that "Goal" is a base domain objects, this Goal should only
 * act as an adapter for the "Goal" which resides in "baseAndUtility"
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
public class Goal extends NegotiationIssue implements IDInterface {

	private Long id;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}