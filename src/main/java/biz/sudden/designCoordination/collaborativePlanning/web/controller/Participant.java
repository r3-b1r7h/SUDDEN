package biz.sudden.designCoordination.collaborativePlanning.web.controller;

import java.util.List;

import javax.faces.event.ActionEvent;

import com.icesoft.faces.async.render.Disposable;
import com.icesoft.faces.async.render.Renderable;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public interface Participant extends Renderable, Disposable, Cloneable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#isRegistered()
	 */
	public boolean isRegistered();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setRegistered(boolean)
	 */
	public void setRegistered(boolean registered);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#isSelected()
	 */
	public boolean isSelected();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setSelected(boolean)
	 */
	public void setSelected(boolean selected);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getChatApplication()
	 */
	public ChatApplication getChatApplication();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #setChatApplication(biz.sudden.designCoordination.collaborativePlanning
	 * .web.controller.impl.ChatApplicationImpl)
	 */
	public void setChatApplication(ChatApplication chatApplication);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getUserName()
	 */
	public String getUserName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setUserName(java.lang.String)
	 */
	public void setUserName(String userName);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#register(javax.faces.event.ActionEvent)
	 */
	public void register(ActionEvent event);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#logout(javax.faces.event.ActionEvent)
	 */
	public void logout(ActionEvent event);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getState()
	 */
	public PersistentFacesState getState();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #setState(com.icesoft.faces.webapp.xmlhttp.PersistentFacesState)
	 */
	public void setState(PersistentFacesState state);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #renderingException(com.icesoft.faces.webapp.xmlhttp.RenderingException)
	 */
	public void renderingException(RenderingException renderEx);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #addPopup(biz.sudden.designCoordination.collaborativePlanning
	 * .web.controller.impl.PopupImpl)
	 */
	public void addPopup(Popup popup);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#closeMessage(javax.faces.event.ActionEvent)
	 */
	public void closeMessage(ActionEvent event);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#startChat(javax.faces.event.ActionEvent)
	 */
	public void startChat(ActionEvent event);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#dispose()
	 */
	public void dispose();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getPopupList()
	 */
	public List<Popup> getPopupList();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setPopupList(java.util.List)
	 */
	public void setPopupList(List popupList);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getUsers()
	 */
	public List<Participant> getUsers();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setUsers(java.util.List)
	 */
	public void setUsers(List<Participant> users);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #addUser(biz.sudden.designCoordination.collaborativePlanning.
	 * web.controller.impl.ParticipantImpl)
	 */
	public void addUser(Participant user);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2
	 * #removeUser(biz.sudden.designCoordination.collaborativePlanning
	 * .web.controller.impl.Participant)
	 */
	public void removeUser(Participant user);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#getPopupMessage()
	 */
	public String getPopupMessage();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .Participant2#setPopupMessage(java.lang.String)
	 */
	public void setPopupMessage(String popupMessage);

}