package biz.sudden.designCoordination.collaborativePlanning.service.communication.impl;

import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.repository.CPActorRepository;
import biz.sudden.designCoordination.collaborativePlanning.repository.CommunicationRepository;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPGetMessageService;

class CPGetMessageServiceImpl implements CPGetMessageService {

	private Logger logger = Logger.getLogger(this.getClass());

	private CPActorRepository cpActorRepository;
	private CommunicationRepository communicationRepository;

	public CPActorRepository getCpActorRepository() {
		return cpActorRepository;
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
	public List<Communication> retrieveMessagesFor(SuddenUser user) {
		return communicationRepository.getCommunicationFor(user);
	}

	@Override
	public List<Communication> retrieveAllCommunications() {
		// communicationRepository.create(new Communication( ));
		if (1 == 0)
			throw new RuntimeException("exception in " + this.getClass());
		return communicationRepository.getAllCommunications();
	}

	@Override
	public List<SuddenUser> retrieveReceiver(Long id) {
		return communicationRepository.retrieve(id).getReceiver();
		// TODO Auto-generated method stub
		// return communication.getReceiver();
	}

}
