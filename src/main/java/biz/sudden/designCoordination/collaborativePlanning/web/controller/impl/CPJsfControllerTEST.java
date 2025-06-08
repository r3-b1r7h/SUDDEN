//package biz.sudden.designCoordination.collaborativePlanning.web.controller.impl;
//
//import java.util.List;
//
//import javax.faces.event.ActionEvent;
//import javax.faces.event.ValueChangeEvent;
//import javax.faces.model.SelectItem;
//
//import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
//import biz.sudden.designCoordination.collaborativePlanning.domain.CP_Actor;
//import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
//import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPEngageInDiscussionService;
//import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPGetMessageService;
//import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPSendMessageService;
//import biz.sudden.designCoordination.collaborativePlanning.web.controller.ChatApplication;
//import biz.sudden.designCoordination.collaborativePlanning.web.controller.CollaborativePlanningJsfController;
//import biz.sudden.designCoordination.collaborativePlanning.web.controller.Participant;
//import biz.sudden.userInterface.gui.web.controller.UserSessionController;
//
//import com.icesoft.faces.component.ext.HtmlDataTable;
//
//public class CPJsfControllerTEST implements CollaborativePlanningJsfController {
//
//	private CPSendMessageService cpSendMessageService;
//	private CPGetMessageService cpGetMessageService;
//	private UserSessionController userSessionController;
//	private BusinessOpportunity bo;
//	
//	public UserSessionController getUserSessionController() {
//		return userSessionController;
//	}
//
//	public void setUserSessionController(UserSessionController userSessionController) {
//		this.userSessionController = userSessionController;
//	}
//
//	public CPGetMessageService getCpGetMessageService() {
//		return cpGetMessageService;
//	}
//
//	public void setCpGetMessageService(CPGetMessageService cpGetMessageService) {
//		this.cpGetMessageService = cpGetMessageService;
//	}
//
//	public CPSendMessageService getCpSendMessageService() {
//		return cpSendMessageService;
//	}
//
//	public void setCpSendMessageService(CPSendMessageService cpSendMessageService) {
//		this.cpSendMessageService = cpSendMessageService;
//	}
//	
//	public String getBusinessOpportunityLink() {
//		
//		logger.debug("uno "+cpGetMessageService.getMessageFor("eins"));
//		logger.debug("due "+cpGetMessageService.getMessageFor("zwei"));
//		logger.debug("tre "+cpGetMessageService.getMessageFor("drei"));
//		bo = cpGetMessageService.getMessageFor("eins").getAttachment();
//		return cpGetMessageService.getMessageFor("eins").getAttachment().getDescription();
//	}
//	
//	public String showBO() {
//		userSessionController.getMap().put("biz.sudden.pana", bo);
//		logger.debug(userSessionController);
//		return "bo";
//	}
//
//
//	@Override
//	public void createUser(ActionEvent event) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public String getActorForInbox() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<SelectItem> getAllSender() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ChatApplication getChatApplication() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Communication getCommunication() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Long getCommunicationid() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public CPEngageInDiscussionService getCpService() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public HtmlDataTable getDatatable() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getDetailFrom() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getDetailMessage() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getDetailSubject() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getDetailTo() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void getForum(ActionEvent event) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public List getMatchesList() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getMessage() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List getMessages() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getNewuser() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Participant getParticipant() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getReceiver() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getSender() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getSubject() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void openMessage(Communication communication) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void openMessage() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void sendAnswer() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void sendMessage(ActionEvent event) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setActorForInbox(String actorForInbox) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setAllSender(List<CP_Actor> allSender) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setChatApplication(ChatApplication chatApplication) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setCommunication(Communication communication) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setCommunicationid(Long communicationid) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setCpService(CPEngageInDiscussionService cpService) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setDatatable(HtmlDataTable datatable) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setDetailFrom(String detailFrom) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setDetailMessage(String detailMessage) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setDetailSubject(String detailSubject) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setDetailTo(String detailTo) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setMatches(ValueChangeEvent event) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setMatchesList(List matchesList) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setMessage(String message) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setMessages(List<Communication> messages) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setNewuser(String newuser) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setParticipant(Participant participant) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setReceiver(String receiver) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setSender(String sender) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setSubject(String subject) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void showMessages(ActionEvent event) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void updateList(ValueChangeEvent event) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void updateReceiverList(ValueChangeEvent event) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
