package biz.sudden.designCoordination.collaborativePlanning.service.communication;

import java.util.List;

import biz.sudden.designCoordination.collaborativePlanning.domain.CP_Actor;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.repository.CPActorRepository;
import biz.sudden.designCoordination.collaborativePlanning.repository.CommunicationContainerRepository;
import biz.sudden.designCoordination.collaborativePlanning.repository.CommunicationRepository;

/**
 * 
 * With the aid of this ServiceInterface it is possible to communicate
 * synchronous with a couple of other users (like in a chat).
 * 
 * 
 * @author Thomas Feiner
 * 
 */

public interface CPEngageInDiscussionService {

	public CommunicationRepository getCommunicationRepository();

	public void setCommunicationRepository(
			CommunicationRepository communicationRepository);

	public CPActorRepository getActorRepository();

	public void setActorRepository(CPActorRepository actorRepository);

	public CommunicationContainerRepository getCommunicationContainerRepository();

	public void setCommunicationContainerRepository(
			CommunicationContainerRepository communicationContainerRepository);

	// /**
	// *
	// * @return All messages which already are contained in discussion
	// */
	// public List<String> getMessages(Actor actor);
	//	
	// public List<String> getMessages(String actor);
	//	
	// /**
	// * Send a message to current discussion
	// */
	// public void sendMessage(Actor sender, List<Actor>receiver, String
	// message);
	//	
	//	
	// //public void createActor(Actor actor);
	//	

	public void sendMessage(String sender, String receiver, String message,
			Communication inReplyTo);

	public List<Communication> getMessages(String actor);

	public List<CP_Actor> getAllUsers();

	public void createUser(String name);

}
