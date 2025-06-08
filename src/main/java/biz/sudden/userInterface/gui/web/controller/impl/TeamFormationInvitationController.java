package biz.sudden.userInterface.gui.web.controller.impl;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;

import biz.sudden.baseAndUtility.domain.Invitation;
import biz.sudden.baseAndUtility.domain.Invitation.Status;
import biz.sudden.baseAndUtility.repository.CaseFileRepository;
import biz.sudden.baseAndUtility.service.InvitationService;
import biz.sudden.designCoordination.handleBO.dataStructures.ASNNode;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.evaluation.coordinationFit.service.CoordinationFitResult;
import biz.sudden.evaluation.coordinationFit.service.CoordinationFitService;

public class TeamFormationInvitationController {
	CaseFileRepository caseFileRepository;
	CoordinationFitService coordinationFitService;
	InvitationService invitationService;
	UIData invitationsDataTable;

	Invitation selectedInvitation;
	Double minCostProposal;
	Double avgCostProposal;
	Double maxCostProposal;

	Long minDurationProposal;
	Long avgDurationProposal;
	Long maxDurationProposal;

	public void setCaseFileRepository(CaseFileRepository caseFileRepository) {
		this.caseFileRepository = caseFileRepository;
	}

	public void setCoordinationFitService(
			CoordinationFitService coordinationFitService) {
		this.coordinationFitService = coordinationFitService;
	}

	public void setInvitationService(InvitationService invitationService) {
		this.invitationService = invitationService;
	}

	public Invitation getSelectedInvitation() {
		return selectedInvitation;
	}

	public List<Invitation> getAllInvitations() {
		return invitationService.retrieveAllInvitations();

	}

	public UIData getInvitationsDataTable() {
		return invitationsDataTable;
	}

	public void setInvitationsDataTable(UIData invitationsDataTable) {
		this.invitationsDataTable = invitationsDataTable;
	}

	public String getInvitationId() {
		return selectedInvitation.getId().toString();
	}

	public void setInvitationId(String invitationId) {
		System.out
				.println("TeamFormationInvitationController.setInvitationId()");
		selectInvitation(Long.valueOf(invitationId));
	}

	public String selectTeamToJoin() {
		System.out
				.println("TeamFormationInvitationController.selectTeamToJoin()");

		selectInvitation(extractRequestId());

		return "teamFormationInvitation";
	}

	private void selectInvitation(Long id) {
		selectedInvitation = invitationService.retrieveInvitationById(id);

		AbstractSupplyNetwork asn = selectedInvitation
				.getConcreteSupplyNetwork().getASN();
		System.out.println("initial node:"
				+ (asn.getInitialNode() != null ? asn.getInitialNode()
						.getClass().getCanonicalName()
						+ ":" + asn.getInitialNode().getId() : null));
		System.out.println("final node:"
				+ (asn.getFinalNode() != null ? asn.getFinalNode().getClass()
						.getCanonicalName()
						+ ":" + asn.getFinalNode().getId() : null));
		List<ASNNode> myNodes = asn.getMyNodes();
		System.out.print("mynodes: ");
		for (ASNNode node : myNodes) {
			System.out.print(node.getClass().getCanonicalName() + ":"
					+ node.getId());
			System.out.print(", ");
		}
		System.out.println();
		List<ASNDependency> myDependencies = asn.getMyDependencies();
		System.out.println("depedencies: ");
		for (ASNDependency dependency : myDependencies) {
			System.out.println("\t" + dependency.getClass().getCanonicalName()
					+ " from: " + dependency.getSourceNode().getId() + " to: "
					+ dependency.getTargetNode().getId());
		}
		System.out.println();

		Supplier supplier = selectedInvitation.getSupplier();
		minCostProposal = supplier.getMinCostProposal();
		avgCostProposal = supplier.getAvgCostProposal();
		maxCostProposal = supplier.getMaxCostProposal();
		minDurationProposal = supplier.getMinDurationProposal();
		avgDurationProposal = supplier.getAvgDurationProposal();
		maxDurationProposal = supplier.getMaxDurationProposal();
	}

	public String joinTeam() {
		System.out.println("TeamFormationInvitationController.joinTeam()");
		Supplier supplier = selectedInvitation.getSupplier();
		supplier.setMinCostProposal(minCostProposal);
		supplier.setAvgCostProposal(avgCostProposal);
		supplier.setMaxCostProposal(maxCostProposal);
		supplier.setMinDurationProposal(minDurationProposal);
		supplier.setAvgDurationProposal(avgDurationProposal);
		supplier.setMaxDurationProposal(maxDurationProposal);

		selectedInvitation.setStatus(Status.ACCEPTED);
		invitationService.updateInvitation(selectedInvitation);

		if (selectedInvitation.getConcreteSupplyNetwork().isReadyForCF()) {
			CoordinationFitResult result = coordinationFitService
					.evaluate(selectedInvitation.getConcreteSupplyNetwork());
			selectedInvitation.getConcreteSupplyNetwork()
					.setCoordinationFitResults(result);
		}

		caseFileRepository.update(selectedInvitation.getCaseFile());

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("The team has been joined!"));

		return "cpReceiveMessages";
	}

	public String rejectTeam() {
		System.out.println("TeamFormationInvitationController.rejectTeam()");

		selectedInvitation = invitationService
				.retrieveInvitationById(extractRequestId());
		selectedInvitation.setStatus(Status.REJECTED);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("The team invitation has been rejected."));

		return null;
	}

	private Long extractRequestId() {
		String requestId = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("id");
		if (requestId != null)
			return new Long(requestId);
		return null;
	}

	public Double getMinCostProposal() {
		return minCostProposal;
	}

	public void setMinCostProposal(Double minCostProposal) {
		this.minCostProposal = minCostProposal;
	}

	public Double getAvgCostProposal() {
		return avgCostProposal;
	}

	public void setAvgCostProposal(Double avgCostProposal) {
		this.avgCostProposal = avgCostProposal;
	}

	public Double getMaxCostProposal() {
		return maxCostProposal;
	}

	public void setMaxCostProposal(Double maxCostProposal) {
		this.maxCostProposal = maxCostProposal;
	}

	public Long getMinDurationProposal() {
		return minDurationProposal;
	}

	public void setMinDurationProposal(Long minDurationProposal) {
		this.minDurationProposal = minDurationProposal;
	}

	public Long getAvgDurationProposal() {
		return avgDurationProposal;
	}

	public void setAvgDurationProposal(Long avgDurationProposal) {
		this.avgDurationProposal = avgDurationProposal;
	}

	public Long getMaxDurationProposal() {
		return maxDurationProposal;
	}

	public void setMaxDurationProposal(Long maxDurationProposal) {
		this.maxDurationProposal = maxDurationProposal;
	}

	public void autoAcceptRemainingInvitations() {
		System.out
				.println("TeamFormationInvitationController.autoAcceptRemainingInvitations()");

		List<Invitation> invitations = invitationService
				.retrieveAllInvitations();
		for (Invitation invitation : invitations) {
			if (invitation.getStatus() == Status.OPEN) {
				Supplier supplier = invitation.getSupplier();

				supplier.setMinCostProposal(new Double(Math.random() + 1));
				supplier.setAvgCostProposal(new Double(Math.random() + 3));
				supplier.setMaxCostProposal(new Double(Math.random() + 5));
				supplier.setMinDurationProposal(new Long(
						(long) (Math.random() * 10 + 10)));
				supplier.setAvgDurationProposal(new Long(
						(long) (Math.random() * 10 + 30)));
				supplier.setMaxDurationProposal(new Long(
						(long) (Math.random() * 10 + 50)));

				invitation.setStatus(Status.ACCEPTED);
				invitationService.updateInvitation(invitation);

				if (invitation.getConcreteSupplyNetwork().isReadyForCF()) {
					CoordinationFitResult result = coordinationFitService
							.evaluate(invitation.getConcreteSupplyNetwork());
					invitation.getConcreteSupplyNetwork()
							.setCoordinationFitResults(result);
				}

				caseFileRepository.update(invitation.getCaseFile());
			}
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("All invitations have been accepted!"));
	}
}
