package biz.sudden.designCoordination.collaborativePlanning.web.controller.impl;

import java.util.LinkedList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import biz.sudden.designCoordination.collaborativePlanning.web.controller.Participant;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.Popup;

import com.icesoft.faces.async.render.Renderable;
import com.icesoft.faces.context.DisposableBean;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public class PopupImpl implements Renderable, DisposableBean, Popup {

	private Logger logger = Logger.getLogger(this.getClass());

	private List<Participant> participants = new LinkedList();
	private LinkedList messages = new LinkedList();
	private boolean requestRendered = false;
	private String message = "";
	private PersistentFacesState state;
	private Popup otherPopup;
	private Participant participant;
	private int popupid;

	private int topint;

	private int leftint;

	private int width;

	private int height;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#getTopint()
	 */
	public int getTopint() {
		return topint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#setTopint(int)
	 */
	public void setTopint(int topint) {
		this.topint = topint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#getLeftint()
	 */
	public int getLeftint() {
		return leftint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#setLeftint(int)
	 */
	public void setLeftint(int leftint) {
		this.leftint = leftint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#setWidth(int)
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#setHeight(int)
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#getOtherPopup()
	 */
	public Popup getOtherPopup() {
		return otherPopup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup
	 * #setOtherPopup(biz.sudden.designCoordination.collaborativePlanning.web
	 * .controller.impl.Popup)
	 */
	public void setOtherPopup(Popup otherPopup) {
		this.otherPopup = otherPopup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#isRequestRendered()
	 */
	public boolean isRequestRendered() {
		return requestRendered;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#setRequestRendered(boolean)
	 */
	public void setRequestRendered(boolean requestRendered) {
		this.requestRendered = requestRendered;
	}

	public PopupImpl() {
		setState(PersistentFacesState.getInstance());
		this.height = 0;
		this.width = 0;
		this.leftint = 0;
		this.topint = 0;
		logger.debug("new Popup");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup
	 * #addParticipant(biz.sudden.designCoordination.collaborativePlanning.
	 * web.controller.impl.ParticipantImpl)
	 */
	public void addParticipant(Participant participant) {
		this.participants.add(participant);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup
	 * #removeParticipant(biz.sudden.designCoordination.collaborativePlanning
	 * .web.controller.Participant)
	 */
	public void removeParticipant(Participant participant) {
		this.participants.remove(participant);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#addMessage(java.lang.String)
	 */
	public void addMessage(String message) {
		messages.add(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#dispose(javax.faces.event.ActionEvent)
	 */
	public void dispose(ActionEvent event) {
		// TODO Auto-generated method stub
		// this.participants.remove(this);
		try {
			getParticipant().getPopupList().remove(this);
			logger.debug("participant " + getParticipant().getUserName());
			participant.getChatApplication().getChatroomRenderer().remove(this);
			participant.getChatApplication().getChatroomRenderer()
					.requestRender();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// this.participant.removePopup();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#dispose()
	 */
	public void dispose() throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#getMessage()
	 */
	public String getMessage() {

		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#setMessage(java.lang.String)
	 */
	public void setMessage(String message) {
		logger.debug("SET MESSAGE " + message);
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup
	 * #renderingException(com.icesoft.faces.webapp.xmlhttp.RenderingException)
	 */
	@Override
	public void renderingException(RenderingException renderEx) {
		renderEx.printStackTrace();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#setState(com.icesoft.faces.webapp.xmlhttp.PersistentFacesState)
	 */
	public void setState(PersistentFacesState state) {
		this.state = state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#getState()
	 */
	@Override
	public PersistentFacesState getState() {
		return this.state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#getMessages()
	 */
	public LinkedList getMessages() {
		return messages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#setMessages(java.util.LinkedList)
	 */
	public void setMessages(LinkedList messages) {
		this.messages = messages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#sendMessage(javax.faces.event.ActionEvent)
	 */
	public void sendMessage(ActionEvent event) {
		this.messages.addFirst(message.toString());
		// this.messages.addFirst(participant.getUserName() + ": " + message);
		this.getParticipant().getChatApplication().getChatroomRenderer()
				.requestRender();
		// if (otherPopup != null)
		// otherPopup.getParticipant().getChatApplication().getChatroomRenderer().requestRender();
		setMessage("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#getPopupid()
	 */
	public int getPopupid() {
		return popupid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#setPopupid(int)
	 */
	public void setPopupid(int popupid) {
		this.popupid = popupid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Popup#sendMessage()
	 */
	public void sendMessage() {

	}

}
