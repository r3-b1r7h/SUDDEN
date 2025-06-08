package biz.sudden.baseAndUtility.repository;

import biz.sudden.baseAndUtility.domain.Invitation;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface InvitationRepository extends
		GenericRepository<Invitation, Long> {
	public void cancelInvitationsForCaseFile(Long caseId);
}
