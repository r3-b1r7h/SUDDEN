package biz.sudden.designCoordination.collaborativePlanning.web.controller.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import biz.sudden.designCoordination.collaborativePlanning.web.controller.ChatApplication;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.Participant;

import com.icesoft.faces.async.render.OnDemandRenderer;
import com.icesoft.faces.async.render.RenderManager;

public class ChatApplicationImpl implements ChatApplication {

	private List<Participant> allUsers = Collections
			.synchronizedList(new LinkedList<Participant>());

	private RenderManager renderManager;
	private OnDemandRenderer chatroomRenderer;

	public ChatApplicationImpl() {
	}

	// --- Rendermanager ---
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .ChatApplication#getRenderManager()
	 */
	public RenderManager getRenderManager() {
		return renderManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .ChatApplication
	 * #setRenderManager(com.icesoft.faces.async.render.RenderManager)
	 */
	public void setRenderManager(RenderManager renderManager) {
		this.renderManager = renderManager;
		// lets use the same renderer as all the other chat classes
		chatroomRenderer = renderManager.getOnDemandRenderer("chatRenderer");
		// chatroomRenderer = renderManager.getOnDemandRenderer("chatroom");
		chatroomRenderer.setRenderManager(renderManager);
		chatroomRenderer.requestRender();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .ChatApplication
	 * #addUser(biz.sudden.designCoordination.collaborativePlanning
	 * .web.controller.impl.ParticipantImpl)
	 */
	public void addUser(Participant participant) {
		allUsers.add(participant);
		chatroomRenderer.add(participant);

		for (Participant myparticipant : getAllUsers()) {
			if (participant != myparticipant)
				myparticipant.addUser(participant);
		}
		chatroomRenderer.requestRender();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .ChatApplication
	 * #removeUser(biz.sudden.designCoordination.collaborativePlanning
	 * .web.controller.impl.ParticipantImpl)
	 */
	public void removeUser(Participant participant) {
		allUsers.remove(participant);
		chatroomRenderer.remove(participant);

		for (Participant participant1 : getAllUsers()) {
			participant1.removeUser(participant);
		}
		chatroomRenderer.requestRender();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .ChatApplication#getTime()
	 */
	public String getTime() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss ");
		return df.format(date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .ChatApplication#getAllUsers()
	 */
	public List<Participant> getAllUsers() {
		return allUsers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .ChatApplication#setAllUsers(java.util.List)
	 */
	public void setAllUsers(List<Participant> allUsers) {
		this.allUsers = allUsers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .ChatApplication#getChatroomRenderer()
	 */
	public OnDemandRenderer getChatroomRenderer() {
		return chatroomRenderer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.designCoordination.collaborativePlanning.web.controller.impl
	 * .ChatApplication
	 * #setChatroomRenderer(com.icesoft.faces.async.render.OnDemandRenderer)
	 */
	public void setChatroomRenderer(OnDemandRenderer chatroomRenderer) {
		this.chatroomRenderer = chatroomRenderer;
	}

}
