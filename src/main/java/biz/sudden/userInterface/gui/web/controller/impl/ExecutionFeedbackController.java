package biz.sudden.userInterface.gui.web.controller.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.domain.exception.ActorNotFoundException;
import biz.sudden.baseAndUtility.repository.CaseFileRepository;
import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoard;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.domain.Topic;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPBulletinBoardService;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPSendMessageService;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.CompetenceInstance;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMInstancesManagement_Service;

public class ExecutionFeedbackController {
	private CaseFileRepository caseFileRepository;
	private ICMInstancesManagement_Service competencesManagementInstancesService;
	private ICMCompetencesManagement_Service competencesManagementService;
	private CPSendMessageService messageService;
	private UserService userService;
	private CPBulletinBoardService bulletinBoardService;

	private CaseFile selectedCaseFile;
	private UIData roleDataTable;
	private Double costActual;
	private Long durationActual;
	private Date startDate;
	private Date actualReportDate;
	private Supplier selectedSupplier;

	public void setCaseFileRepository(CaseFileRepository caseFileRepository) {
		this.caseFileRepository = caseFileRepository;
	}

	public void setCompetencesManagementService(
			ICMCompetencesManagement_Service competencesManagementService) {
		this.competencesManagementService = competencesManagementService;
	}

	public void setCompetencesManagementInstancesService(
			ICMInstancesManagement_Service competencesManagementInstancesService) {
		this.competencesManagementInstancesService = competencesManagementInstancesService;
	}

	public void setMessageService(CPSendMessageService messageService) {
		this.messageService = messageService;
	}

	public void setBulletinBoardService(
			CPBulletinBoardService bulletinBoardService) {
		this.bulletinBoardService = bulletinBoardService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setCaseFileId(String id) {
		selectedCaseFile = caseFileRepository.retrieve(Long.valueOf(id));
	}

	public UIData getRoleDataTable() {
		return roleDataTable;
	}

	public void setRoleDataTable(UIData roleDataTable) {
		this.roleDataTable = roleDataTable;
	}

	public CaseFile getSelectedCaseFile() {
		return selectedCaseFile;
	}

	public Supplier getSelectedSupplier() {
		return selectedSupplier;
	}

	public Set<Map.Entry<ASNRoleNode, Supplier>> getRoleMap() {
		ConcreteSupplyNetwork finalTeam = selectedCaseFile.getFinalTeam();
		if (finalTeam == null)
			return Collections.emptySet();

		return finalTeam.getCandidateSuppliersAsMap().entrySet();
	}

	public Date getActualReportDate() {
		return actualReportDate;
	}

	public void setActualReportDate(Date actualReportDate) {
		this.actualReportDate = actualReportDate;
	}

	public Double getCostActual() {
		return costActual;
	}

	public void setCostActual(Double costActual) {
		this.costActual = costActual;
	}

	public Long getDurationActual() {
		return durationActual;
	}

	public void setDurationActual(Long durationActual) {
		this.durationActual = durationActual;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void selectSupplier(ActionEvent event) {
		System.out.println("ExecutionFeedbackController.select()");
		selectedSupplier = (Supplier) event.getComponent().getAttributes().get(
				"supplier");
		costActual = selectedSupplier.getCostActual();
		durationActual = selectedSupplier.getDurationActual();
		actualReportDate = selectedSupplier.getActualsForDate();
		startDate = selectedSupplier.getStartDate();
	}

	public String update() {
		System.out.println("ExecutionFeedbackController.update()");

		selectedSupplier.setCostActual(costActual);
		selectedSupplier.setDurationActual(durationActual);
		if (startDate != null)
			selectedSupplier.setStartDate(startDate);
		if (actualReportDate != null)
			selectedSupplier.setActualsForDate(actualReportDate);

		caseFileRepository.update(selectedCaseFile);

		if (selectedSupplier.isCompleted()
				&& (!selectedSupplier.isCostWithinLimits() || !selectedSupplier
						.isDurationWithinLimits())) {
			updateLogisticsComptetence(selectedSupplier);
		}

		if (!selectedSupplier.isCostWithinLimits()) {
			String subject = String
					.format(
							"The supplier '%s' for the case file '%s' demonstrated an execution variance in terms of cost.",
							selectedSupplier.getOrganisation().getName(),
							selectedCaseFile.getName());
			String body = String
					.format(
							"The supplier '%s' for the case file '%s' demonstrated an execution variance in terms of cost. %n The supplier should have spent %s kEuros but actually spent %s kEuros.",
							selectedSupplier.getOrganisation().getName(),
							selectedCaseFile.getName(), selectedSupplier
									.getEstimatedCost(), selectedSupplier
									.getCostActual());
			Topic topic = createTopic(subject, body);
			Collection<Supplier> suppliers = selectedCaseFile.getFinalTeam()
					.getCandidateSuppliersAsMap().values();
			body += " A new discussion forum has been opened to discuss this with the other team members.";
			informUsers(subject, body, suppliers, topic);
		}

		if (!selectedSupplier.isDurationWithinLimits()) {
			String subject = String
					.format(
							"The supplier '%s' for the case file '%s' demonstrated an execution variance in terms of effort.",
							selectedSupplier.getOrganisation().getName(),
							selectedCaseFile.getName());
			String body = String
					.format(
							"The supplier '%s' for the case file '%s' demonstrated an execution variance in terms of effort. %n The supplier should have spent %s mdays but actually spent %s mdays.",
							selectedSupplier.getOrganisation().getName(),
							selectedCaseFile.getName(), selectedSupplier
									.getEstimatedDuration(), selectedSupplier
									.getDurationActual());
			Topic topic = createTopic(subject, body);
			Collection<Supplier> suppliers = selectedCaseFile.getFinalTeam()
					.getCandidateSuppliersAsMap().values();
			body += " A new discussion forum has been opened to discuss this with the other team members.";
			informUsers(subject, body, suppliers, topic);
		}

		selectedSupplier = null;
		durationActual = null;
		costActual = null;
		startDate = null;
		actualReportDate = null;

		return null;
	}

	private Topic createTopic(String subject, String body) {
		SuddenUser adminUser = userService.retrieveUser("admin");

		BulletinBoard bulletinBoard = bulletinBoardService
				.getBoardWithName("Execution feedback bulletin board");
		if (bulletinBoard == null) {
			bulletinBoard = new BulletinBoard();
			bulletinBoard.setName("Execution feedback bulletin board");
			bulletinBoard.setOwner(adminUser);
			bulletinBoardService.createBulletin(bulletinBoard);
		}

		Topic topic = new Topic();
		topic.setName(subject);
		topic.setBelongsToBoard(bulletinBoard);
		topic.setDateCreated(new Date());
		topic.setOwner(adminUser);
		bulletinBoardService.createTopicAndAddToBulletinBoard(bulletinBoard,
				topic);

		Communication communication = new Communication();
		communication.setMessage(subject);
		communication.setMessage(body);
		communication.setTopic(topic);
		communication.setSendDate(new Date());
		communication.setSender(adminUser);
		topic.getCommunications().add(communication);
		bulletinBoardService.createContributionAndAddToTopic(topic,
				communication);
		return topic;
	}

	private List<SuddenUser> computeReceivers(Collection<Supplier> suppliers) {
		List<SuddenUser> users = new ArrayList<SuddenUser>();
		users.add(userService.retrieveUser("admin"));
		for (Supplier supplier : suppliers) {
			if (supplier.getOrganisation() != null
					&& supplier.getOrganisation()
							.getAux_userNameCompanyManager() != null) {

				SuddenUser user = null;
				try {
					user = userService.retrieveUser(supplier.getOrganisation()
							.getAux_userNameCompanyManager());
				} catch (ActorNotFoundException e) {
					// do nothing here
				}

				if (user != null)
					users.add(user);
			}
		}
		return users;
	}

	private void informUsers(String subject, String body,
			Collection<Supplier> suppliers, Topic topic) {
		SuddenUser adminUser = userService.retrieveUser("admin");

		Communication comm = new Communication();
		comm.setSender(adminUser);
		comm.setReceiver(computeReceivers(suppliers));
		comm.setSendDate(new Date());
		comm.setSubject(subject);
		comm.setMessage(body);

		messageService.createMessage(comm);
	}

	private void updateLogisticsComptetence(Supplier supplier) {
		List<Competence> competences = competencesManagementService
				.retrieveCompetenceByName("L0 Technology");
		if (competences != null && competences.size() > 0) {
			CompetenceInstance competenceInstance = competencesManagementInstancesService
					.retrieveCompetenceInstanceByOrganization(0, competences
							.get(0).getId());

			competenceInstance.setCompetenceValuePenalty(-20);
			competencesManagementInstancesService
					.getCompetenceInstanceRepository()
					.updateCompetenceInstance(competenceInstance);
		}
	}

	public String cancel() {
		System.out.println("ExecutionFeedbackController.cancel()");
		selectedSupplier = null;
		durationActual = null;
		costActual = null;
		startDate = null;
		actualReportDate = null;
		return null;
	}

}
