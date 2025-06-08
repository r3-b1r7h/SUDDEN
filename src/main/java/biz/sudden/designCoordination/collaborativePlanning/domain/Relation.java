package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.Entity;

import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * 
 * In the context of "CollaborativePlanning", Relation acts as an
 * NegotiationIssue and further as CommunicationItem, so that it is possible to
 * talk about Relations. Due to the fact that "Relation" is a base domain
 * objects, this Relation should only act as an adapter for the "Relation" which
 * resides in "baseAndUtility"
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
public class Relation extends NegotiationIssue implements IDInterface {

	// private Long id;
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

}
