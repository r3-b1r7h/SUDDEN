package biz.sudden.designCoordination.collaborativePlanning.web.controller;

import java.util.List;

import com.icesoft.faces.async.render.OnDemandRenderer;
import com.icesoft.faces.async.render.RenderManager;

public interface ChatApplication {

	// --- Rendermanager ---
	public RenderManager getRenderManager();

	public void setRenderManager(RenderManager renderManager);

	public void addUser(Participant participant);

	public void removeUser(Participant participant);

	public String getTime();

	public List<Participant> getAllUsers();

	public void setAllUsers(List<Participant> allUsers);

	public OnDemandRenderer getChatroomRenderer();

	public void setChatroomRenderer(OnDemandRenderer chatroomRenderer);

}