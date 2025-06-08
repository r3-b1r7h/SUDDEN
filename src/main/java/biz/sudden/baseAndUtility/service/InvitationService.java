package biz.sudden.baseAndUtility.service;

import java.util.List;

import biz.sudden.baseAndUtility.domain.Invitation;

public interface InvitationService {
	public Long createInvitation(Invitation invitation);

	public void updateInvitation(Invitation invitation);

	public Invitation retrieveInvitationById(Long id);

	public List<Invitation> retrieveAllInvitations();

	public void cancelInvitationsForCaseFile(Long caseId);
}
