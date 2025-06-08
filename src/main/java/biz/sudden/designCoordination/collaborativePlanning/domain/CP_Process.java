package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.Entity;

import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * 
 * In the context of "CollaborativePlanning", Process acts as an
 * NegotiationIssue and further as CommunicationItem, so that it is possible to
 * talk about Processes. Due to the fact that "Process" is a base domain object,
 * this Process should only act as an adapter for the "Process" which resides in
 * "baseAndUtility"
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
public class CP_Process extends NegotiationIssue implements IDInterface {

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
