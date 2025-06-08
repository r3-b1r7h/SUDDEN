package biz.sudden.designCoordination.collaborativePlanning.service.collaboration;

import java.util.List;

import biz.sudden.designCoordination.collaborativePlanning.domain.CP_Actor;
import biz.sudden.designCoordination.collaborativePlanning.domain.CP_BusinessOpportunity;
import biz.sudden.designCoordination.collaborativePlanning.domain.Negotiation;
import biz.sudden.designCoordination.collaborativePlanning.domain.NegotiationIssue;
import biz.sudden.designCoordination.collaborativePlanning.domain.NegotiationItem;
import biz.sudden.designCoordination.collaborativePlanning.domain.Valuation;

/**
 * 
 * This ServiceInterface allows to collaborate regarding NegotiationIssues,
 * NegotiationItems, Actors, Valuations, Agreements, etc... Due to Figure 39 in
 * D4.2 it allow the highly interactive planning of Actors, Processes and BO
 * (ASN resp.). The method "storeAgreement" saves the Agreement at last.
 * 
 * 
 * @author Thomas Feiner
 * 
 */

public interface CP_CollaborativeService {

	/**
	 * 
	 * Create a new Negotiation
	 * 
	 * @param negotiation
	 */
	public void createNegotiation(Negotiation negotiation);

	/**
	 * 
	 * Add an Actor to a negotiation
	 * 
	 * @param negotiation
	 * @param actor
	 */

	public void addActor(Negotiation negotiation, CP_Actor actor);

	/**
	 * 
	 * Get all Valuations of an Actor
	 * 
	 * @param actor
	 * @return
	 */

	public List<Valuation> getValuations(CP_Actor actor);

	/**
	 * 
	 * Get all Processes (delegated to UOM)
	 * 
	 * @return
	 */

	public List<Process> getProcesses();

	/**
	 * 
	 * Get Business Opportunity (BO), delegated to HBO
	 * 
	 * @return
	 */

	public CP_BusinessOpportunity getBusinessOpportunity();

	/**
	 * 
	 * Store agreement, delegated to HBO
	 * 
	 * @param negotiation
	 */

	public void storeAgreement(Negotiation negotiation);

	/**
	 * 
	 * Add a NegotiationItem to a Negotiation
	 * 
	 * @param negotiation
	 * @param negotiationItem
	 */

	public void addNegotationItem(Negotiation negotiation,
			NegotiationItem negotiationItem);

	/**
	 * 
	 * Add a NegotiationIssue to a NegotiationItem (like Actor, Relation,
	 * Process, Responsibility, Goal, BO)
	 * 
	 * @param negotiationItem
	 * @param negotiationIssue
	 */

	public void addNegotiationIssue(NegotiationItem negotiationItem,
			NegotiationIssue negotiationIssue);

}
