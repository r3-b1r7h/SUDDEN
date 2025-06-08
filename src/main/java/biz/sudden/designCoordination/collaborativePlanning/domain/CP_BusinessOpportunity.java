package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * 
 * In the context of "CollaborativePlanning", BO acts as an NegotiationIssue and
 * further as CommunicationItem, so that it is possible to talk about BO. Due to
 * the fact that "BO" is a base domain objects, this BO should only act as an
 * adapter for the "BO" which resides in "baseAndUtility"
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
public class CP_BusinessOpportunity extends NegotiationIssue implements
		IDInterface {

	private Long id;
	private BusinessOpportunity businessOpportunity;

	@OneToOne(cascade = { CascadeType.ALL })
	public BusinessOpportunity getBusinessOpportunity() {
		return businessOpportunity;
	}

	public void setBusinessOpportunity(BusinessOpportunity businessOpportunity) {
		this.businessOpportunity = businessOpportunity;
	}

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