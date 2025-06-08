package biz.sudden.userInterface.gui.web.controller.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.Invitation;
import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.domain.exception.ActorNotFoundException;
import biz.sudden.baseAndUtility.service.InvitationService;
import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;
import biz.sudden.baseAndUtility.web.controller.domain.ParameterNameValuePair;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPSendMessageService;
import biz.sudden.designCoordination.coordination.service.impl.CoordinationService;
import biz.sudden.designCoordination.coordination.web.controller.impl.CoordinationController;
import biz.sudden.designCoordination.handleBO.dataStructures.ASNNode;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.designCoordination.teamFormation.overallControl.TeamFormationControllerService;
import biz.sudden.evaluation.coordinationFit.service.CoordinationFitService;
import biz.sudden.evaluation.coordinationFit.service.CoordinationFitResult.State;
import biz.sudden.knowledgeData.kdm.service.KdmService;
import biz.sudden.knowledgeData.kdm.web.controller.CaseFileController;
import biz.sudden.userInterface.gui.web.controller.TeamFormationController;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public class TeamFormationControllerImpl implements TeamFormationController {

	private KdmService kdmService;
	// don't use the repository straight from the controller
	// private CaseFileRepository caseFileRepository;
	private CaseFileController caseFileController;
	private InvitationService invitationService;
	private CPSendMessageService messageService;
	private UserService userService;
	private CoordinationFitService coordinationFitService;
	private CoordinationService coordinationService;
	private CoordinationController coordinationController;
	private TeamFormationControllerService teamFormationController;

	private UIData teamsDataTable;
	private UIData teamMembersDataTable;
	private UIData criticalPathDataTable;

	private ConcreteSupplyNetwork selectedTeam;

	private Logger logger = Logger.getLogger(this.getClass());

	public KdmService getKdmService() {
		return kdmService;
	}

	public void setKdmService(KdmService kdmService) {
		this.kdmService = kdmService;
	}

	public void setCaseFileController(CaseFileController caseFileController) {
		this.caseFileController = caseFileController;
	}

	public void setCoordinationFitService(
			CoordinationFitService coordinationFitService) {
		this.coordinationFitService = coordinationFitService;
	}

	public void setCoordinationController(CoordinationController cc) {
		this.coordinationController = cc;
	}

	public CoordinationController getCoordinationController() {
		return this.coordinationController;
	}

	public UIData getTeamsDataTable() {
		return teamsDataTable;
	}

	public void setTeamsDataTable(UIData dataTable) {
		this.teamsDataTable = dataTable;
	}

	public UIData getTeamMembersDataTable() {
		return teamMembersDataTable;
	}

	public void setTeamMembersDataTable(UIData teamMembersDataTable) {
		this.teamMembersDataTable = teamMembersDataTable;
	}

	public UIData getCriticalPathDataTable() {
		return criticalPathDataTable;
	}

	public void setCriticalPathDataTable(UIData criticalPathDataTable) {
		this.criticalPathDataTable = criticalPathDataTable;
	}

	public TeamFormationControllerImpl() {
		System.out
				.println("TeamFormationControllerImpl.TeamFormationControllerImpl()");
	}

	// public void setCaseFileRepository(CaseFileRepository caseFileRepository)
	// {
	// this.caseFileRepository = caseFileRepository;
	// }

	public void setInvitationService(InvitationService invitationService) {
		this.invitationService = invitationService;
	}

	public void setMessageService(CPSendMessageService messageService) {
		this.messageService = messageService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setCoordinationService(CoordinationService coordinationService) {
		this.coordinationService = coordinationService;
	}

	public ConcreteSupplyNetwork getSelectedTeam() {
		return selectedTeam;
	}

	public void setSelectedTeam(ConcreteSupplyNetwork selectedTeam) {
		this.selectedTeam = selectedTeam;
	}

	public void setTeamFormationController(TeamFormationControllerService s) {
		this.teamFormationController = s;
	}

	public TeamFormationControllerService getTeamFormationController() {
		return this.teamFormationController;
	}

	public String startTFAllFixedButOne() {
		String supplierToReplaceName = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get(
						"supplierToReplace");

		// Getting double called - a LOT. With null values. UI libs not being
		// friendly for some reason.
		if (supplierToReplaceName != null) {
			logger.debug("woooooooooooooo " + supplierToReplaceName);

			ConcreteSupplyNetwork currentCSN = getSelectedTeam();
			AbstractSupplyNetwork currentASN = caseFileController
					.getCurrentCaseFile().getAsnPrototypeTeam();
			AbstractSupplyNetwork ASNToActuallyWorkFrom = currentCSN.getASN();
			// caseFileController.getCurrentCaseFile().setAsnPrototypeTeam(currentASN);
			// Oddly this call is outright fine w'out bottom up TF included but
			// with it in produces outright garbage

			currentASN.setMyNodes(ASNToActuallyWorkFrom.getMyNodes());
			currentASN.setMyDependencies(ASNToActuallyWorkFrom
					.getMyDependencies());

			List<ASNNode> nodes;
			ASNRoleNode replacementNode;
			Supplier s;
			for (ASNRoleNode node : currentASN.getAllRoleNodes()) {
				logger.debug(" Current csn " + currentCSN);
				logger.debug(" current ASN " + currentASN);
				logger.debug(" node " + node);
				s = currentCSN.getSupplierForNode(node).limitedClone();
				logger.debug(" Supplier " + s + " for node " + node);
				logger.debug(" Compared to supplier "
						+ supplierToReplaceName
						+ " gives "
						+ s.getOrganisation().getName().equals(
								supplierToReplaceName));
				if (!(s.getOrganisation().getName()
						.equals(supplierToReplaceName))) {
					logger.debug(" Fixing supplier for this node ");
					currentASN.assignFixedSupplier(node, s.getOrganisation());
				} else {
					/*
					 * The node could have a 'fixed' supplier attached. So build
					 * a new, clean ASNRoleNode to be sure.
					 */
					currentASN.getMyNodes().remove(node);
					replacementNode = new ASNRoleNode(node
							.getCompetenceNeeded(), node
							.getQualificationProfile());
					replacementNode.setId(node.getId());
					currentASN.getMyNodes().add(node);
				}
			}

			logger.debug(" ASN produced after fixing suppliers is "
					+ currentASN);

			// Hey lets wipe that old team info :)
			// Did doing this cause a null pointer crash?! How?!!?
			// caseFileController.getCurrentCaseFile().setTempTeams(new
			// ArrayList<ConcreteSupplyNetwork>());
			// Not previously needed this
			caseFileController.getCurrentCaseFile().setAsnPrototypeTeam(
					currentASN.clone());

			caseFileController.updateCurrentCaseFile();
			logger
					.debug(" ASN produced after fixing suppliers and assigning to the case file is.... "
							+ caseFileController.getCurrentCaseFile()
									.getAsnPrototypeTeam());

			// caseFileController.updateCurrentCaseFile();

			// Hum direct TF gets us a NPE, I guess from the evaluation system
			// somehow. Lets try jumping to evaluation
			// this.teamFormationController.generatePrototypeTeamCandidatesTopDown(getSelectedCaseFile());
		}
		return "performanceMeasurementNetwork2";
	}

	public void clearAssignedTeam() {
		logger.debug(" CLearing assigned team information");
		this.getSelectedCaseFile().setFinalTeam(null);
		// this.getSelectedTeam().getCoordinationFitResults().setState(CoordinationFitResult.State.COMPLETED);
		// caseFileController.updateCurrentCaseFile();
	}

	@Override
	public CaseFile getSelectedCaseFile() {
		CaseFile selectedCaseFile = caseFileController.getCurrentCaseFile();
		if (selectedCaseFile == null) {
			Map<Long, String> caseFileNames = kdmService.getCaseService()
					.retrieveCaseFileNames();
			if (caseFileNames.size() == 0)
				return null;

			caseFileController.setCurrentCaseFileId(caseFileNames.entrySet()
					.iterator().next().getKey());
			selectedCaseFile = caseFileController.getCurrentCaseFile();
		}

		return selectedCaseFile;
	}

	@Override
	public String selectCaseFile() {
		Long caseFileId = extractRequestId();

		System.out.println("TeamFormationControllerImpl.selectCaseFile("
				+ caseFileId + ")");

		if (caseFileId == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"The case file id was not provided.", ""));
			return "teamFormation";
		}

		// bad idea to go directly to the repository
		// CaseFile caseFile = caseFileRepository.retrieve(caseFileId);
		CaseFile caseFile = kdmService.getCaseService().retrieveCaseFile(
				caseFileId);
		if (caseFile == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"The case file with id '" + caseFileId
									+ "' was not found.", ""));
			return "teamFormation";
		}

		caseFileController.setCurrentCaseFileId(caseFile.getId());

		return "teamFormationCase";
	}

	public void computeEfficiencyRating() {
		System.out.println("Computing efficienct rating for case file:"
				+ getSelectedCaseFile().getId());

		coordinationFitService.computeEfficiencyRating(getSelectedCaseFile());
	}

	public void refreshCaseFile() {
		Long currentCaseFileId = caseFileController.getCurrentCaseFileId();
		caseFileController.setCurrentCaseFile(kdmService.getCaseService()
				.retrieveCaseFile(currentCaseFileId));
	}

	public void closeCaseFile() {
		CaseFile caseFile = caseFileController.getCurrentCaseFile();
		caseFile.setPhase((short) 4);
		updateCaseFile(caseFile);
	}

	public void computeCoordinationScore() {
		CaseFile caseFile = getSelectedCaseFile();
		System.out.println("Computing coordination score for case file:"
				+ caseFile.getId());

		for (ConcreteSupplyNetwork csn : caseFile.getTempTeams()) {
			coordinationFitService.computeCoordinationScore(csn, caseFile
					.getPhase());
		}

		updateCaseFile(caseFile);
		caseFileController.setCurrentCaseFile(caseFile);
	}

	public String selectTeam() {
		selectTeam(extractRequestId());

		return "teamFormationTeamDetails";
	}

	public void changedSelectedCaseFile(ValueChangeEvent event) {
		Long caseFileId = Long.parseLong(event.getNewValue().toString());
		// bad idea to go directly to the repository
		// CaseFile caseFile = caseFileRepository.retrieve(caseFileId);
		CaseFile caseFile = kdmService.getCaseService().retrieveCaseFile(
				caseFileId);
		caseFileController.setCurrentCaseFileId(caseFile.getId());
	}

	public void selectTeam(ActionEvent event) {
		Long id = (Long) event.getComponent().getAttributes().get("teamid");
		selectTeam(id);
	}

	public Set<Map.Entry<ASNRoleNode, Supplier>> getSelectedTeamSuppliers() {
		if (selectedTeam == null)
			return null;

		return selectedTeam.getCandidateSuppliersAsMap().entrySet();
	}

	@Transactional
	public Map<String, Long> getCaseFileNameMap() {
		Map<Long, String> names = kdmService.getCaseService()
				.retrieveCaseFileNames();
		Map<String, Long> caseFileNameMap = new LinkedHashMap<String, Long>();
		for (Map.Entry<Long, String> entry : names.entrySet()) {
			caseFileNameMap.put(entry.getKey() + ": " + entry.getValue(), entry
					.getKey());
		}

		return caseFileNameMap;
	}

	@Override
	public void updateCaseFile(CaseFile cf) {
		kdmService.getCaseService().update(cf);
	}

	private Long extractRequestId() {
		String requestId = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("id");
		if (requestId != null)
			return new Long(requestId);
		return null;
	}

	@Override
	public String inviteTeam() {
		CaseFile selectedCaseFile = kdmService.getCaseService()
				.retrieveCaseFile(caseFileController.getCurrentCaseFileId());
		// CaseFile selectedCaseFile = caseFileController.getCurrentCaseFile();

		selectTeam(selectedCaseFile, extractRequestId());

		ConcreteSupplyNetwork team = selectedCaseFile
				.findTempTeamWithId(selectedTeam.getId());

		if (team.getCoordinationFitResults().getState() == State.PENDING)
			return null;

		team = coordinationService.coordinateCSN(team);

		selectedTeam.getCoordinationFitResults().setState(State.PENDING);
		updateCaseFile(selectedCaseFile);

		for (Map.Entry<ASNRoleNode, Supplier> entry : getSelectedTeamSuppliers()) {
			Invitation invitation = new Invitation(selectedCaseFile, team,
					entry.getKey());
			Long id = invitationService.createInvitation(invitation);

			Organization supplierOrganisation = entry.getValue()
					.getOrganisation();

			SuddenUser toUser = getOrganizationUser(supplierOrganisation);

			sendInvitationMessage(toUser, id, entry.getKey(), selectedCaseFile);
		}

		// mcassmc - trying to show the results of this on my coordination
		// interface
		coordinationController.setCSN(selectedTeam);

		caseFileController.setCurrentCaseFile(selectedCaseFile);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("The participants have been invited."));

		return null;
	}

	private SuddenUser getOrganizationUser(Organization supplierOrganisation) {
		SuddenUser toUser = userService.retrieveUser("admin");
		if (supplierOrganisation != null
				&& supplierOrganisation.getAux_userNameCompanyManager() != null) {
			try {
				toUser = userService.retrieveUser(supplierOrganisation
						.getAux_userNameCompanyManager());
			} catch (ActorNotFoundException e) {
				// do nothing here
			}
		}
		return toUser;
	}

	private void sendInvitationMessage(SuddenUser toUser, Long invitationId,
			ASNRoleNode roleNode, CaseFile caseFile) {
		SuddenUser adminUser = userService.retrieveUser("admin");

		Communication comm = new Communication();
		comm.setSender(adminUser);
		comm.setReceiver(Arrays.asList(toUser));
		comm.setSendDate(new Date());
		comm.setSubject("Invitation for participation as the supplier of '"
				+ roleNode.getQualificationProfile().getFirstProductName()
				+ "'.");
		comm
				.setMessage("You have been invited to participate to the '"
						+ caseFile.getBo().getName()
						+ "' business opportunity, as the supplier of '"
						+ roleNode.getQualificationProfile()
								.getFirstProductName()
						+ "' using the "
						+ ((roleNode.getproductionMethod() != null) ? roleNode
								.getproductionMethod().toString()
								: "'not specified'")
						+ " Production Method. Please provide us with the required input in the following form.");

		JsfLink jsfLink = new JsfLink();
		jsfLink.setDescription("Invitation link");
		jsfLink.setViewID("teamFormationInvitation");
		// jsfLink.setController("teamFormationInvitationController");
		jsfLink.setControllerBeanName("teamFormationInvitationController");
		ParameterNameValuePair parameterNameValuePair = new ParameterNameValuePair();
		parameterNameValuePair.setParameter("invitationId");
		parameterNameValuePair.setParameterValue(invitationId.toString());
		jsfLink.setParameterValuesPairs(Arrays.asList(parameterNameValuePair));
		comm.setJsfLink(jsfLink);

		messageService.createMessage(comm);
	}

	private void sendExecutionFeedbackMessage(SuddenUser toUser,
			Long caseFileId, ASNRoleNode roleNode) {
		SuddenUser adminUser = userService.retrieveUser("admin");

		Communication comm = new Communication();
		comm.setSender(adminUser);
		comm.setReceiver(Arrays.asList(toUser));
		comm.setSendDate(new Date());
		comm.setSubject("Request for execution feedback as the supplier of '"
				+ roleNode.getQualificationProfile().getFirstProductName()
				+ "'.");
		comm.setMessage("Please provide execution feedback for the '"
				+ getSelectedCaseFile().getBo().getName()
				+ "' business opportunity.");

		JsfLink jsfLink = new JsfLink();
		jsfLink.setDescription("Link");
		jsfLink.setViewID("executionFeedbackForm");
		// jsfLink.setController("teamFormationInvitationController");
		jsfLink.setControllerBeanName("executionFeedbackController");
		ParameterNameValuePair parameterNameValuePair = new ParameterNameValuePair();
		parameterNameValuePair.setParameter("caseFileId");
		parameterNameValuePair.setParameterValue(caseFileId.toString());
		jsfLink.setParameterValuesPairs(Arrays.asList(parameterNameValuePair));
		comm.setJsfLink(jsfLink);

		messageService.createMessage(comm);
	}

	public String acceptTeam() {
		if (selectedTeam == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("There is no selected team to accept."));
			return null;
		}

		CaseFile selectedCaseFile = getSelectedCaseFile();
		if (selectedCaseFile.getFinalTeam() != null) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									"The selected xase has already a final team defined."));
			return null;
		}

		selectedCaseFile.setFinalTeam(selectedTeam);
		// bad idea to go directly to the repository
		updateCaseFile(selectedCaseFile);

		invitationService
				.cancelInvitationsForCaseFile(selectedCaseFile.getId());

		for (Map.Entry<ASNRoleNode, Supplier> entry : selectedTeam
				.getCandidateSuppliersAsMap().entrySet()) {
			SuddenUser toUser = getOrganizationUser(entry.getValue()
					.getOrganisation());
			sendExecutionFeedbackMessage(toUser, selectedCaseFile.getId(),
					entry.getKey());
		}

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
						"The team has been accepted as the final team."));

		return null;
	}

	private void selectTeam(Long teamId) {
		selectTeam(getSelectedCaseFile(), teamId);
	}

	private void selectTeam(CaseFile selectedCaseFile, Long teamId) {
		List<ConcreteSupplyNetwork> tempFinalTeams = selectedCaseFile
				.getTempTeams();
		for (ConcreteSupplyNetwork concreteSupplyNetwork : tempFinalTeams) {
			if (concreteSupplyNetwork.getId().equals(teamId)) {
				selectedTeam = concreteSupplyNetwork;

				System.out
						.println("initial node:"
								+ selectedTeam.getInitialNode().getId()
								+ " nodes:"
								+ keepIds(selectedTeam
										.getNodesConnectedbyMaterialDependency(selectedTeam
												.getInitialNode())));
				for (ASNRoleNode node : selectedTeam.getASN().getAllRoleNodes()) {
					System.out
							.println("node:"
									+ node.getId()
									+ " nodes:"
									+ keepIds(selectedTeam
											.getNodesConnectedbyMaterialDependency(node)));
				}
				return;
			}
		}
	}

	private List<Long> keepIds(
			Collection<ASNRoleNode> nodesConnectedbyMaterialDependency) {
		List<Long> ids = new ArrayList<Long>(nodesConnectedbyMaterialDependency
				.size());
		for (ASNRoleNode asnRoleNode : nodesConnectedbyMaterialDependency) {
			ids.add(asnRoleNode.getId());
		}

		return ids;
	}

}
