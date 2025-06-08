package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * 
 * This domain objects acts as an value container, which allows to add
 * constraints to NegotiationIssues
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
public class NegotiationConstraint implements IDInterface {

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
