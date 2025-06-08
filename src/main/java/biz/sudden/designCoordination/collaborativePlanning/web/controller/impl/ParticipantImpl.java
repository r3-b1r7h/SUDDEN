package biz.sudden.designCoordination.collaborativePlanning.web.controller.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import biz.sudden.designCoordination.collaborativePlanning.web.controller.ChatApplication;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.Participant;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.Popup;

import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.webapp.xmlhttp.FatalRenderingException;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public class ParticipantImpl implements Participant {

	private Logger logger = Logger.getLogger(this.getClass());

	private ChatApplication chatApplication;

	private String userName;

	private PersistentFacesState state;

	private List<Popup> popupList = Collections
			.synchronizedList(new LinkedList<Popup>());
	private List<Participant> usersInChat = new LinkedList<Participant>();

	private int nrOfPopups = 0;

	private boolean registered = false;

	private boolean selected = false;

	private List<Participant> users = new LinkedList<Participant>();

	private String popupMessage = "";

	// private List<Participant> users = null;

	// private PanelSeries messageList;

	//	
	// private String message;
	// private boolean registered = false;
	// private PersistentFacesState state;
	// private boolean selected = false;
	// //private Popup popup;
	// private List users = null;
	// private List itemlist = new LinkedList();
	//	
	// private List popupList = new LinkedList();
	// private Long idCounter = new Long(0);

	// public List getItemlist() {
	// return itemlist;
	// }
	//
	// public void setItemlist(List itemlist) {
	// this.itemlist = itemlist;
	// }

	public ParticipantImpl() {
		setState(PersistentFacesState.getInstance());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#isRegistered()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#isRegistered()
	 */
	public boolean isRegistered() {
		return registered;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setRegistered(boolean)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#setRegistered(boolean)
	 */
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#isSelected()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#isSelected()
	 */
	public boolean isSelected() {
		return selected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setSelected(boolean)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#setSelected(boolean)
	 */
	public void setSelected(boolean selected) {
		logger.debug("set participant " + this.getUserName() + " selected "
				+ selected);
		this.selected = selected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getChatApplication()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#getChatApplication()
	 */
	public ChatApplication getChatApplication() {
		return chatApplication;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #setChatApplication(biz.sudden.designCoordination.collaborativePlanning
	 * .web.controller.impl.ChatApplicationImpl)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant
	 * #setChatApplication(biz.sudden.designCoordination.collaborativePlanning
	 * .web.controller.impl.ChatApplicationImpl)
	 */
	public void setChatApplication(ChatApplication chatApplication) {
		this.chatApplication = chatApplication;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getUserName()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#getUserName()
	 */
	public String getUserName() {
		return userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setUserName(java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#setUserName(java.lang.String)
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#register(javax.faces.event.ActionEvent)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#register(javax.faces.event.ActionEvent)
	 */
	public void register(ActionEvent event) {
		chatApplication.addUser(this);
		setRegistered(true);
		// for (Participant participant : getChatApplication().getAllUsers()) {
		// if (participant != this)
		// users.add(participant);
		// }

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#logout(javax.faces.event.ActionEvent)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#logout(javax.faces.event.ActionEvent)
	 */
	public void logout(ActionEvent event) {
		chatApplication.removeUser(this);
		setRegistered(false);
		users.remove(this);
	}

	// public void sendMessage(ActionEvent event) {
	// // chatRoom.sendMessage(this, getMessage());
	// // logger.debug("Message sent " + this.getUserName());
	// // setMessage("");
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getState()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#getState()
	 */
	public PersistentFacesState getState() {
		return this.state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #setState(com.icesoft.faces.webapp.xmlhttp.PersistentFacesState)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant
	 * #setState(com.icesoft.faces.webapp.xmlhttp.PersistentFacesState)
	 */
	public void setState(PersistentFacesState state) {
		this.state = state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #renderingException(com.icesoft.faces.webapp.xmlhttp.RenderingException)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant
	 * #renderingException(com.icesoft.faces.webapp.xmlhttp.RenderingException)
	 */
	public void renderingException(RenderingException renderEx) {
		renderEx.printStackTrace();
		if (renderEx instanceof FatalRenderingException) {
			dispose();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #addPopup(biz.sudden.designCoordination.collaborativePlanning
	 * .web.controller.impl.PopupImpl)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant
	 * #addPopup(biz.sudden.designCoordination.collaborativePlanning.
	 * web.controller.impl.PopupImpl)
	 */
	public void addPopup(Popup popup) {

		logger.debug("add popup");
		popup.setHeight(300);
		popup.setWidth(400);
		popup.setLeftint(200 + popupList.size() * 15);
		popup.setTopint(200 + popupList.size() * 15);
		nrOfPopups++;
		popup.setPopupid(nrOfPopups);
		popupList.add(popup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#closeMessage(javax.faces.event.ActionEvent)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#closeMessage(javax.faces.event.ActionEvent)
	 */
	public void closeMessage(ActionEvent event) {
		setPopupMessage("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#startChat(javax.faces.event.ActionEvent)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#startChat(javax.faces.event.ActionEvent)
	 */
	public void startChat(ActionEvent event) {

		Participant myUser = null;
		//			
		for (Participant participant : getUsers()) {
			if (participant.isSelected()) {
				myUser = participant;
				break;
			}
		}

		if (usersInChat.contains(myUser)) {
			setPopupMessage("Bereits im Chat mit diesem User!");
			return;
		}
		//			
		// for (Popup popup1 : popupList) {
		// if (popup1.getOtherPopup()!= null &&
		// popup1.getOtherPopup().getParticipant() == myUser)
		// return;
		// }

		Popup popup = new PopupImpl();
		addPopup(popup);

		// popup.addParticipant(this);
		popup.setParticipant(this);
		//			
		getChatApplication().getChatroomRenderer().add(popup);
		getChatApplication().getChatroomRenderer().requestRender();
		// getChatApplication().getRenderManager().requestRender(popup);
		// this.requestRenderAllPopups();

		if (myUser != null) {
			for (Participant participant : getChatApplication().getAllUsers()) {

				if (participant.getUserName().equals(myUser.getUserName())) {
					Popup otherPopup = new PopupImpl();
					participant.addPopup(otherPopup);
					otherPopup.setParticipant(participant);
					// otherPopup.setParticipant(participant);
					otherPopup.addParticipant(participant);
					//						
					otherPopup.setMessages(popup.getMessages());
					// otherPopup.setMessage("test");

					usersInChat.add(participant);

					getChatApplication().getChatroomRenderer().add(otherPopup);
					getChatApplication().getChatroomRenderer().requestRender();
				}
			}
		}

	}

	// public void startChat(ActionEvent event) {
	// idCounter++;
	// Popup newpopup = new Popup();
	// newpopup.setId(idCounter);
	// newpopup.setDraggableRendered(true);
	// popupList.add(newpopup);
	// newpopup.setParticipant(this);
	//		
	// newpopup.setDraggableRendered(true);
	// User myUser = null;
	// getChatRoom().getRenderManager().requestRender(this);
	// }

	// FacesContext.getCurrentInstance().responseComplete();
	//
	// for (Object object : getUsers()) {
	// User user = (User) object;
	// if (user.isSelected()) {
	// myUser = user;
	// break;
	// }
	// }
	//
	// if (myUser != null) {
	//
	// for (Object object : getChatRoom().getParticipants()) {
	// Participant participant = (Participant) object;
	// if (participant.getUserName().equals(myUser.getName())) {
	// //TODO TF comment in again
	// //participant.getPopup().setDraggableRendered(true);
	// getChatRoom().getRenderManager().requestRender(participant);
	// }
	// }
	// }

	// getChatRoom().addParticipant(myParticipant);

	// public Popup getPopup() {
	// return popup;
	// }
	//
	// public void setPopup(Popup popup) {
	// this.popup = popup;
	// }

	// public List getUsers() {
	//
	// if (users == null) {
	//
	// users = new LinkedList();
	//
	// for (Object object : getChatRoom().getParticipants()) {
	// Participant participant = (Participant) object;
	// if (participant != this) {
	// User user = new User();
	// user.setName(participant.getUserName());
	// users.add(user);
	// }
	// }
	// }
	// return users;
	// }
	//
	// public void updateUsers() {
	// users = new LinkedList();
	//
	// for (Object object : getChatRoom().getParticipants()) {
	// Participant participant = (Participant) object;
	// if (participant != this) {
	// User user = new User();
	// user.setName(participant.getUserName());
	// users.add(user);
	// }
	// }
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#dispose()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub
		RenderManager renderManager = getChatApplication().getRenderManager();
		try {
			if (renderManager != null) {
				renderManager.getOnDemandRenderer("chatroom").remove(this);
				getChatApplication().removeUser(this);
				if (renderManager.getOnDemandRenderer("chatroom").isEmpty()) {
					renderManager.getOnDemandRenderer("chatroom").dispose();
				}
			}
		} catch (Exception disposeFailed) {
			logger.debug("dispose failed");
			disposeFailed.printStackTrace();
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	// public void setUsers(List users) {
	// this.users = users;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getPopupList()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#getPopupList()
	 */
	public List<Popup> getPopupList() {
		return popupList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setPopupList(java.util.List)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#setPopupList(java.util.List)
	 */
	public void setPopupList(List popupList) {
		this.popupList = popupList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getUsers()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#getUsers()
	 */
	public List<Participant> getUsers() {
		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setUsers(java.util.List)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#setUsers(java.util.List)
	 */
	public void setUsers(List<Participant> users) {
		this.users = users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #addUser(biz.sudden.designCoordination.collaborativePlanning.
	 * web.controller.impl.ParticipantImpl)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant
	 * #addUser(biz.sudden.designCoordination.collaborativePlanning.web
	 * .controller.impl.ParticipantImpl)
	 */
	public void addUser(Participant user) {
		getUsers().add(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #removeUser(biz.sudden.designCoordination.collaborativePlanning
	 * .web.controller.impl.Participant)
	 */
	public void removeUser(Participant user) {
		getUsers().remove(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getPopupMessage()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#getPopupMessage()
	 */
	public String getPopupMessage() {
		return popupMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setPopupMessage(java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant#setPopupMessage(java.lang.String)
	 */
	public void setPopupMessage(String popupMessage) {
		this.popupMessage = popupMessage;
	}

	// public void requestRenderAllPopups() {
	// synchronized (this) {
	//
	// getChatApplication().getChatroomRenderer().requestRender();
	// for (Popup mypopup : getPopupList()) {
	// getChatApplication().getRenderManager().requestRender(mypopup);
	// }
	// }
	// // getChatApplication().getChatroomRenderer().requestRender();
	// }

	// public void updateUsers() {
	// users.clear();
	// for (Participant participant : getChatApplication().getAllUsers()) {
	// users.add(participant);
	// }
	// getChatApplication().getChatroomRenderer().requestRender();
	//
	// }

	// public Long getIdCounter() {
	// return idCounter;
	// }
	//
	// public void setIdCounter(Long idCounter) {
	// this.idCounter = idCounter;
	// }
	//	
	// public void removePopup() {
	// idCounter--;
	// }

}
