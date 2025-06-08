package biz.sudden.designCoordination.teamFormation.service;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;

/**
 * 
 * A super class of the generic agent communication object which contains a bit
 * of additional info detailing the stuff needed to create a noticeboard.
 * 
 * @author mcassmc
 * 
 */
public class CreateNoticeboardCommunicationObject extends
		AgentCommunicationObject {

	private CaseFile caseFileToUpdate;
	private ConcreteSupplyNetwork csnToUpDate;
	private ASNRoleNode roleNodeToExpand;

	public CaseFile getCaseFile() {
		return this.caseFileToUpdate;
	}

	public ConcreteSupplyNetwork getCSN() {
		return this.csnToUpDate;
	}

	public ASNRoleNode getRoleNode() {
		return this.roleNodeToExpand;
	}

	public void setCaseFile(CaseFile cf) {
		this.caseFileToUpdate = cf;
	}

	public void setCSN(ConcreteSupplyNetwork csn) {
		this.csnToUpDate = csn;
	}

	public void setRoleNode(ASNRoleNode asnRoleNode) {
		this.roleNodeToExpand = asnRoleNode;
	}
}
