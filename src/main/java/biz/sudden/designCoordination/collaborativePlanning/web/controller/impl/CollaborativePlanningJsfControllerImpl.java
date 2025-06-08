//package biz.sudden.designCoordination.collaborativePlanning.web.controller.impl;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//
//import javax.faces.context.FacesContext;
//import javax.faces.event.ActionEvent;
//import javax.faces.event.ValueChangeEvent;
//import javax.faces.model.SelectItem;
//
//import biz.sudden.baseAndUtility.domain.exception.ActorNotFoundException;
//import biz.sudden.baseAndUtility.domain.exception.AmbiguousActorException;
//import biz.sudden.designCoordination.collaborativePlanning.domain.CP_Actor;
//import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
//import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPEngageInDiscussionService;
//import biz.sudden.designCoordination.collaborativePlanning.web.controller.ChatApplication;
//import biz.sudden.designCoordination.collaborativePlanning.web.controller.CollaborativePlanningJsfController;
//import biz.sudden.designCoordination.collaborativePlanning.web.controller.Participant;
//
//import com.icesoft.faces.component.ext.HtmlDataTable;
//import com.icesoft.faces.component.selectinputtext.SelectInputText;
//
//public class CollaborativePlanningJsfControllerImpl implements CollaborativePlanningJsfController {
//
//	private ChatApplication chatApplication;
//	private Participant participant;
//	private CPEngageInDiscussionService cpService;
//	private String actorForInbox;
//	private HtmlDataTable datatable;
//	private Communication communication;
//	private Long communicationid;
//
//	private String sender;
//	private String receiver;
//	private String message;
//	private String subject;
//
//	private String newuser;
//
//	private String detailFrom;
//	private String detailTo;
//	private String detailSubject;
//	private String detailMessage;
//
//	private List<CP_Actor> allSender;
//	private List<SelectItem> selectItems;
//	
//	private List<Communication> messages;
//
//	private List<SelectItem> matchesList = new LinkedList<SelectItem>();
//	
//	private SelectInputText autoCompleteSender;
//	private SelectInputText autoCompleteReceiver;
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getAllSender()
//	 */
//	public List<SelectItem> getAllSender() {
//		// if (selectItems == null) {
//		selectItems = new LinkedList();
//		logger.debug("allUsers size " + cpService.getAllUsers().size());
//		for (CP_Actor actor : cpService.getAllUsers()) {
//			SelectItem item = new SelectItem(actor.getName(), actor.getName(),
//					actor.getName());
//			selectItems.add(item);
//		}
//		// }
//		return selectItems;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setAllSender(java.util.List)
//	 */
//	public void setAllSender(List<CP_Actor> allSender) {
//
//		SelectItem item = new SelectItem();
//		this.allSender = allSender;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getDetailFrom()
//	 */
//	public String getDetailFrom() {
//		return detailFrom;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getSubject()
//	 */
//	public String getSubject() {
//		return subject;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setSubject(java.lang.String)
//	 */
//	public void setSubject(String subject) {
//		this.subject = subject;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setDetailFrom(java.lang.String)
//	 */
//	public void setDetailFrom(String detailFrom) {
//		this.detailFrom = detailFrom;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getDetailTo()
//	 */
//	public String getDetailTo() {
//		return detailTo;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setDetailTo(java.lang.String)
//	 */
//	public void setDetailTo(String detailTo) {
//		this.detailTo = detailTo;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getDetailSubject()
//	 */
//	public String getDetailSubject() {
//		return detailSubject;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setDetailSubject(java.lang.String)
//	 */
//	public void setDetailSubject(String detailSubject) {
//		this.detailSubject = detailSubject;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getDetailMessage()
//	 */
//	public String getDetailMessage() {
//		return detailMessage;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setDetailMessage(java.lang.String)
//	 */
//	public void setDetailMessage(String detailMessage) {
//		this.detailMessage = detailMessage;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getSender()
//	 */
//	public String getSender() {
//		return sender;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setSender(java.lang.String)
//	 */
//	public void setSender(String sender) {
//		logger.debug("SET SENDER");
//		this.sender = sender;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getCommunicationid()
//	 */
//	public Long getCommunicationid() {
//		return communicationid;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setCommunicationid(java.lang.Long)
//	 */
//	public void setCommunicationid(Long communicationid) {
//		this.communicationid = communicationid;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getReceiver()
//	 */
//	public String getReceiver() {
//		return receiver;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setReceiver(java.lang.String)
//	 */
//	public void setReceiver(String receiver) {
//		this.receiver = receiver;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getMessage()
//	 */
//	public String getMessage() {
//		return message;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setMessage(java.lang.String)
//	 */
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getCpService()
//	 */
//	public CPEngageInDiscussionService getCpService() {
//		return cpService;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setCpService(biz.sudden.designCoordination.collaborativePlanning.service.communication.CP_EngageInDiscussionService)
//	 */
//	public void setCpService(CPEngageInDiscussionService cpService) {
//		this.cpService = cpService;
//
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getParticipant()
//	 */
//	public Participant getParticipant() {
//		return participant;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setParticipant(biz.sudden.designCoordination.collaborativePlanning.web.controller.Participant)
//	 */
//	public void setParticipant(Participant participant) {
//		this.participant = participant;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getChatApplication()
//	 */
//	public ChatApplication getChatApplication() {
//		return chatApplication;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setChatApplication(biz.sudden.designCoordination.collaborativePlanning.web.controller.ChatApplication)
//	 */
//	public void setChatApplication(ChatApplication chatApplication) {
//		this.chatApplication = chatApplication;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#sendMessage(javax.faces.event.ActionEvent)
//	 */
//	public void sendMessage(ActionEvent event) {
//		logger.debug("CP Service " + cpService);
//		logger.debug(sender + " " + receiver + " " + message);
//
//		cpService.sendMessage(sender, receiver, message, null);
//		setSender("a");
//		setReceiver("b");
//		setMessage("c");
//		sender = "";
//		receiver = "";
//		message = "";
//		autoCompleteSender.setLabel("");
//		autoCompleteReceiver.setLabel("");
//		autoCompleteSender.setValue("");
//		autoCompleteReceiver.setValue("");
//		
////		updateList(event);
////		updateReceiverList(event);
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#createUser(javax.faces.event.ActionEvent)
//	 */
//	public void createUser(ActionEvent event) {
//		logger.debug("Create User ");
//		cpService.createUser(newuser);
//		
//		selectItems = new LinkedList();
//		logger.debug("allUsers size "	+ cpService.getAllUsers().size());
//		for (CP_Actor actor : cpService.getAllUsers()) {
//			SelectItem item = new SelectItem(actor.getName(), actor
//					.getName(), actor.getName());
//			selectItems.add(item);
//		}
//		newuser = "";
//	}
//	
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getForum(javax.faces.event.ActionEvent)
//	 */
//	public void getForum(ActionEvent event) {
//		try {
//		CP_Actor actor = cpService.getActorRepository().retrieve("forum");
//		if (actor == null) {
//			cpService.createUser("forum");
//		}
//		actor = cpService.getActorRepository().retrieve("forum");
//		setReceiver(actor.getName());
//		//setMessages(actor.getReceivedCommunication());
//		} catch (ActorNotFoundException actorNotFoundEx) {
//			
//		} catch (AmbiguousActorException ambiguosActorEx) {
//			
//		}
//		
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getMessages()
//	 */
//	public List getMessages() {
//		logger.debug("get messages");
//		return this.messages;
//		
//		
//	}
//	
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setMessages(java.util.List)
//	 */
//	public void setMessages(List<Communication> messages) {
//		this.messages = messages;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#showMessages(javax.faces.event.ActionEvent)
//	 */
//	public void showMessages(ActionEvent event) {
//		setMessages(cpService.getMessages(this.getActorForInbox()));
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getActorForInbox()
//	 */
//	public String getActorForInbox() {
//		return actorForInbox;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setActorForInbox(java.lang.String)
//	 */
//	public void setActorForInbox(String actorForInbox) {
//		logger.debug("set actor for inbox " + actorForInbox);
//		this.actorForInbox = actorForInbox;
//		setMessages(cpService.getMessages(this.getActorForInbox()));
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#openMessage(biz.sudden.designCoordination.collaborativePlanning.domain.Communication)
//	 */
//	public void openMessage(Communication communication) {
//		logger.debug(communication.getMessage());
//	}
//	
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#sendAnswer()
//	 */
//	public void sendAnswer() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		Object value = context.getExternalContext().getRequestMap().get(
//				"communication");
//		Communication communication = (Communication) value;
//		//communication = cpService.getCommunicationRepository().retrieve(communication.getId());
//		logger.debug(sender+" "+receiver+" "+message);
//		cpService.sendMessage(sender, receiver, message, communication);
//		
//		
//		
//		
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#openMessage()
//	 */
//	public void openMessage() {
//		
////		logger.debug("get messages "
////				+ cpService.getMessages(this.getActorForInbox()));
////		setMessages(cpService.getMessages(this.getActorForInbox()));
//		
//		logger.debug("without paramter " + communication + " "
//				+ communicationid);
//		FacesContext context = FacesContext.getCurrentInstance();
//		Object value = context.getExternalContext().getRequestMap().get(
//				"communication");
//		Communication communication = (Communication) value;
//
//		detailFrom = communication.getSender().getName();
//		detailTo = communication.getReceiver().get(0).getName();
//		detailSubject = communication.getSubject();
//		detailMessage = communication.getMessage();
//
//		logger.debug(communication.getMessage());
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getDatatable()
//	 */
//	public HtmlDataTable getDatatable() {
//		return datatable;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setDatatable(com.icesoft.faces.component.ext.HtmlDataTable)
//	 */
//	public void setDatatable(HtmlDataTable datatable) {
//		this.datatable = datatable;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getCommunication()
//	 */
//	public Communication getCommunication() {
//		return communication;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setCommunication(biz.sudden.designCoordination.collaborativePlanning.domain.Communication)
//	 */
//	public void setCommunication(Communication communication) {
//		this.communication = communication;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getNewuser()
//	 */
//	public String getNewuser() {
//		return newuser;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setNewuser(java.lang.String)
//	 */
//	public void setNewuser(String newuser) {
//		this.newuser = newuser;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#updateList(javax.faces.event.ValueChangeEvent)
//	 */
//	public void updateList(ValueChangeEvent event) {
//		logger.debug("update list "+event);
//		
//		setMatches(event);
//
//		// Get the auto complete component from the event and assing
//		if (event.getComponent() instanceof SelectInputText) {
//			autoCompleteSender = (SelectInputText) event
//					.getComponent();
//			
//			logger.debug("Sender "+sender + " " + autoCompleteSender.getSelectedItem().getValue());
//			
//			// if no selected item then return the previously selected item.
//			if (autoCompleteSender.getSelectedItem() != null) {
//				sender = (String) autoCompleteSender.getSelectedItem().getValue();
//			}
//			// otherwise if there is a selected item get the value from the
//			// match list
//			else {
//				sender = getMatch((String) autoCompleteSender.getValue());
//				logger.debug("Sender "+sender + " " + autoCompleteSender.getSelectedItem().getValue());
//				// City tempCity = getMatch(autoComplete.getValue().toString());
//				// if (tempCity != null) {
//				// currentCity = tempCity;
//				// }
//			}
//		}
//
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#updateReceiverList(javax.faces.event.ValueChangeEvent)
//	 */
//	public void updateReceiverList(ValueChangeEvent event) {
//
//		logger.debug("update receiver list "+event);
//		
//		setMatches(event);
//
//		// Get the auto complete component from the event and assing
//		if (event.getComponent() instanceof SelectInputText) {
//			autoCompleteReceiver = (SelectInputText) event
//					.getComponent();
//			// if no selected item then return the previously selected item.
//			if (autoCompleteReceiver.getSelectedItem() != null) {
//				receiver = (String) autoCompleteReceiver.getSelectedItem().getValue();
//			}
//			// otherwise if there is a selected item get the value from the
//			// match list
//			else {
//				receiver = getMatch(autoCompleteReceiver.getLabel());
//				// City tempCity = getMatch(autoComplete.getValue().toString());
//				// if (tempCity != null) {
//				// currentCity = tempCity;
//				// }
//			}
//		}
//
//	}
//
//	private String getMatch(String value) {
//
//		if (matchesList != null) {
//			SelectItem si;
//			Iterator iter = matchesList.iterator();
//			while (iter.hasNext()) {
//				si = (SelectItem) iter.next();
//				logger.debug(si.getValue() + " " + si.getLabel());
//				if (value.equals(si.getValue().toString())) {
//					logger.debug("equals");
//					return si.getLabel();
//				}
//			}
//		}
//		return "";
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setMatches(javax.faces.event.ValueChangeEvent)
//	 */
//	public void setMatches(ValueChangeEvent event) {
//
//		if (selectItems == null) {
//			selectItems = new LinkedList();
//			logger.debug("allUsers size "	+ cpService.getAllUsers().size());
//			for (CP_Actor actor : cpService.getAllUsers()) {
//				SelectItem item = new SelectItem(actor.getName(), actor
//						.getName(), actor.getName());
//				selectItems.add(item);
//			}
//		}
//
//		Object searchWord = event.getNewValue();
//		int maxMatches = ((SelectInputText) event.getComponent()).getRows();
//		List<SelectItem> matchList = new ArrayList(maxMatches);
//
//		try {
//			List<String> myList = new LinkedList<String>();
//			for (SelectItem item : selectItems) {
//				myList.add(item.getLabel());
//			}
//			Collections.sort(myList);
//			int insert = Collections.binarySearch(myList, searchWord,
//					LABEL_COMPARATOR);
//
//			// less then zero if wer have a partial match
//			if (insert < 0) {
//				insert = Math.abs(insert) - 1;
//			}
//
//			for (int i = 0; i < maxMatches; i++) {
//				// quit the match list creation if the index is larger then
//				// maxValue entries in the dictionary if we have added maxMatches.
//				if ((insert + i) >= myList.size() || i >= maxMatches) {
//					break;
//				}
//				String entry = myList.get(insert + i);
//				matchList.add(new SelectItem(entry, entry, entry));
//			}
//		} catch (Throwable e) {
//			logger.debug("Error finding autocomplete matches " + e);
//			e.printStackTrace();
//		}
//		// assign new matchList
//		if (this.matchesList != null) {
//			this.matchesList.clear();
//			this.matchesList = null;
//		}
//		this.matchesList = matchList;
//
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#getMatchesList()
//	 */
//	public List getMatchesList() {
//		return matchesList;
//	}
//
//	/* (non-Javadoc)
//	 * @see biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CollaborativePlanningJsfController#setMatchesList(java.util.List)
//	 */
//	public void setMatchesList(List matchesList) {
//		this.matchesList = matchesList;
//	}
//
//}
