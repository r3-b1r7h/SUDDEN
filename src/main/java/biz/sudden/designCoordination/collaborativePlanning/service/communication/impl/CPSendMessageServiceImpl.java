/**
 * 
 */
package biz.sudden.designCoordination.collaborativePlanning.service.communication.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.repository.UserRepository;
import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.repository.CPActorRepository;
import biz.sudden.designCoordination.collaborativePlanning.repository.CommunicationRepository;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPSendMessageService;

/**
 * @author Virtual
 * 
 */
class CPSendMessageServiceImpl implements CPSendMessageService {

	private Logger logger = Logger.getLogger(this.getClass());

	private CPActorRepository cpActorRepository;
	private CommunicationRepository communicationRepository;
	private UserRepository userRepository;
	private List<Communication> initializeCommunications;

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// private CommunicationContainerRepository
	// communicationContainerRepository;

	/** Getters and Setters **/
	public CPActorRepository getCpActorRepository() {
		return cpActorRepository;
	}

	public List<Communication> getInitializeCommunications() {
		return initializeCommunications;
	}

	public void setInitializeCommunications(
			List<Communication> initializeCommunications) {
		this.initializeCommunications = initializeCommunications;
	}

	public void initializeCommunications() {

		for (Communication communication : initializeCommunications) {
			try {
				boolean alreadyInDatabase = false;
				Long id = 0l;
				for (Communication commDatabase : communicationRepository
						.getAllCommunications()) {
					if (commDatabase.getSpringId() != null
							&& commDatabase.getSpringId().equals(
									communication.getSpringId())) {
						id = commDatabase.getId();
						alreadyInDatabase = true;
					}
				}
				if (!alreadyInDatabase)
					id = createMessage(communication);
				communication.setId(id);
			} catch (Exception ex) {
				logger.debug("Exception in " + this.getClass().getName()
						+ "occurred " + ex);
			}
		}
	}

	@Override
	public void deleteInitializedCommuncations() {
		for (Communication communication : initializeCommunications) {
			for (Communication commDatabase : communicationRepository
					.getAllCommunications()) {
				if (commDatabase.getSpringId() != null
						&& commDatabase.getSpringId().equals(
								communication.getSpringId())) {
					// communication = commDatabase;
					logger.debug("Try to delete Communication with springID "
							+ commDatabase.getSpringId());
					communicationRepository.delete(commDatabase);
				}
			}
			try {
				communication.setId(null);
			} catch (Exception ex) {
				logger.debug("Exception in " + this.getClass().getName()
						+ "occurred " + ex);
			}
		}

	}

	public void setCpActorRepository(CPActorRepository cpActorRepository) {
		this.cpActorRepository = cpActorRepository;
	}

	public CommunicationRepository getCommunicationRepository() {
		return communicationRepository;
	}

	public void setCommunicationRepository(
			CommunicationRepository communicationRepository) {
		this.communicationRepository = communicationRepository;
	}

	@Override
	public void invitePotentialPartner(List enterpriseList, String message) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sendMessage(SuddenUser sender, List<SuddenUser> receiver,
			String subject, String message, Date deadLine) {

		Communication communication = new Communication();
		communication.setSender(sender);
		communication.setReceiver(receiver);
		communication.setDeadLine(deadLine);
		communication.setMessage(message);
		communication.setSubject(subject);
		communicationRepository.create(communication);
	}

	public Long createMessage(Communication communication) {

		Long id = communicationRepository.create(communication);
		return id;
	}

	public void deleteMessage(Communication communication) {
		// TODO Auto-generated method stub
		communicationRepository.delete(communication);
	}

}
