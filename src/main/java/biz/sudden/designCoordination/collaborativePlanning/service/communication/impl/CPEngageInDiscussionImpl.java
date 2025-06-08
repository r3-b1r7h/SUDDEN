package biz.sudden.designCoordination.collaborativePlanning.service.communication.impl;

import java.util.LinkedList;
import java.util.List;

import biz.sudden.baseAndUtility.domain.Actor;
import biz.sudden.baseAndUtility.service.ActorService;
import biz.sudden.designCoordination.collaborativePlanning.domain.CP_Actor;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.repository.CPActorRepository;
import biz.sudden.designCoordination.collaborativePlanning.repository.CommunicationContainerRepository;
import biz.sudden.designCoordination.collaborativePlanning.repository.CommunicationRepository;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPEngageInDiscussionService;

class CPEngageInDiscussionImpl implements CPEngageInDiscussionService {

	CPActorRepository actorRepository;
	CommunicationRepository communicationRepository;
	CommunicationContainerRepository communicationContainerRepository;

	ActorService actorService;

	public ActorService getActorService() {
		return actorService;
	}

	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
	}

	public CommunicationRepository getCommunicationRepository() {
		return communicationRepository;
	}

	public void setCommunicationRepository(
			CommunicationRepository communicationRepository) {
		this.communicationRepository = communicationRepository;
	}

	public CPActorRepository getActorRepository() {
		return actorRepository;
	}

	public void setActorRepository(CPActorRepository actorRepository) {
		this.actorRepository = actorRepository;
	}

	public CommunicationContainerRepository getCommunicationContainerRepository() {
		return communicationContainerRepository;
	}

	public void setCommunicationContainerRepository(
			CommunicationContainerRepository communicationContainerRepository) {
		this.communicationContainerRepository = communicationContainerRepository;
	}

	/**
	 * 
	 */

	public List<String> getMessages(Actor actor) {

		// List<String> messages = new LinkedList<String>();
		//		
		// CP_Actor cpSender = getActorRepository().retrieve(actor);
		// CommunicationContainer communicationContainer =
		// getActorRepository().getCommunicationContainer(actor);
		// if (communicationContainer == null) {
		// communicationContainer =
		// getActorRepository().createCommunicationContainer(actor);
		// }
		// for (Communication communication :
		// communicationContainer.getCommunications() ) {
		// messages.add(communication.getSubject());
		// }
		//		
		// return messages;

		return new LinkedList();
	}

	//	
	// @Override
	// public List<String> getMessages(String stringActor) {
	// //Actor actor = actorRepository.retrieve(stringActor);
	// return new LinkedList();
	// }

	@Override
	public void sendMessage(String senderName, String receiverName,
			String message, Communication inReplyTo) {
		// CP_Actor senderActor = getActorRepository().retrieve(senderName);
		// CP_Actor receiverActor = getActorRepository().retrieve(receiverName);
		//		
		// if (senderActor == null) {
		// senderActor = new CP_Actor(senderName);
		// getActorRepository().create(senderActor);
		// senderActor.setName(senderName);
		// }
		//		
		// if (receiverActor == null) {
		// receiverActor = new CP_Actor(receiverName);
		// getActorRepository().create(receiverActor);
		// receiverActor.setName(receiverName);
		// }
		//		
		// CommunicationContainer senderContainer =
		// senderActor.getCommunicationContainer();
		// CommunicationContainer receiverContainer =
		// receiverActor.getCommunicationContainer();
		//		
		// if (senderContainer == null) {
		// senderContainer = new CommunicationContainer();
		// senderContainer.setName(senderName);
		// senderContainer.setOwner(senderActor);
		// getCommunicationContainerRepository().create(senderContainer);
		// }
		//		
		// Communication communication = new Communication();
		// communication.setSendDate(new Date());
		// communication.setMessage(message);
		// communication.setSender(senderActor);

		// inReplyTo.addChildren(communication);

		// if (inReplyTo != null) {
		// Communication otherCommunication =
		// getCommunicationRepository().retrieve(inReplyTo.getId());
		// communication.setInReplyTo(otherCommunication);
		// }
		// List receiverList = new LinkedList();
		// receiverList.add(receiverActor);
		// communication.setReceiver(receiverList);
		//		
		// if (receiverContainer == null) {
		// receiverContainer = new CommunicationContainer();
		// receiverContainer.setName(receiverName);
		// receiverContainer.setOwner(receiverActor);
		// receiverContainer.addCommunication(communication);
		// getCommunicationContainerRepository().create(receiverContainer);
		// } else {
		// receiverContainer.addCommunication(communication);
		// getCommunicationContainerRepository().update(receiverContainer);
		// }

	}

	public List<CP_Actor> getAllUsers() {
		return getActorRepository().getAllActors();
	}

	@Override
	public List<Communication> getMessages(String actorName) {
		// // TODO Auto-generated method stub
		// List messageList = new LinkedList();
		// CP_Actor actor = getActorRepository().retrieve(actorName);
		// if (actor == null)
		// return messageList;
		// return actor.getReceivedCommunication();
		return null;
	}

	@Override
	public void createUser(String name) {
		// // TODO Auto-generated method stub
		// CP_Actor senderActor = getActorRepository().retrieve(name);
		// senderActor = new CP_Actor();
		// getActorRepository().create(senderActor);
		// logger.debug("New User " + name);
		// senderActor.setName(name);
		// getActorRepository().update(senderActor);
	}

}
