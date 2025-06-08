package biz.sudden.designCoordination.collaborativePlanning.web.controller;

import java.util.LinkedList;

import javax.faces.event.ActionEvent;

import com.icesoft.faces.async.render.Renderable;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public interface Popup extends Renderable {

	public int getTopint();

	public void setTopint(int topint);

	public int getLeftint();

	public void setLeftint(int leftint);

	public int getWidth();

	public void setWidth(int width);

	public int getHeight();

	public void setHeight(int height);

	public Participant getParticipant();

	public void setParticipant(Participant participant);

	public Popup getOtherPopup();

	public void setOtherPopup(Popup otherPopup);

	public boolean isRequestRendered();

	public void setRequestRendered(boolean requestRendered);

	public void addParticipant(Participant participant);

	public void removeParticipant(Participant participant);

	public void addMessage(String message);

	public void dispose(ActionEvent event);

	public void dispose() throws Exception;

	public String getMessage();

	public void setMessage(String message);

	public void renderingException(RenderingException renderEx);

	public void setState(PersistentFacesState state);

	public PersistentFacesState getState();

	public LinkedList getMessages();

	public void setMessages(LinkedList messages);

	public void sendMessage(ActionEvent event);

	public int getPopupid();

	public void setPopupid(int popupid);

	public void sendMessage();

}