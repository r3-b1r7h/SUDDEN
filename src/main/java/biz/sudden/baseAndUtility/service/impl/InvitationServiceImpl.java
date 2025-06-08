package biz.sudden.baseAndUtility.service.impl;

import java.util.List;

import biz.sudden.baseAndUtility.domain.Invitation;
import biz.sudden.baseAndUtility.repository.InvitationRepository;
import biz.sudden.baseAndUtility.service.InvitationService;

public class InvitationServiceImpl implements InvitationService {
	InvitationRepository repository;

	public void setRepository(InvitationRepository repository) {
		this.repository = repository;
	}

	@Override
	public void cancelInvitationsForCaseFile(Long caseId) {
		repository.cancelInvitationsForCaseFile(caseId);
	}

	@Override
	public Long createInvitation(Invitation invitation) {
		return repository.create(invitation);
	}

	@Override
	public List<Invitation> retrieveAllInvitations() {
		return repository.retrieveAll();
	}

	@Override
	public Invitation retrieveInvitationById(Long id) {
		return repository.retrieve(id);
	}

	@Override
	public void updateInvitation(Invitation invitation) {
		repository.update(invitation);
	}

}
