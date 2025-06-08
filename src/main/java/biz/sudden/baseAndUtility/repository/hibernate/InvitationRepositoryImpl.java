package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.domain.Invitation;
import biz.sudden.baseAndUtility.domain.Invitation.Status;
import biz.sudden.baseAndUtility.repository.InvitationRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class InvitationRepositoryImpl extends
		GenericRepositoryImpl<Invitation, Long> implements InvitationRepository {

	public InvitationRepositoryImpl() {
		super(Invitation.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void cancelInvitationsForCaseFile(Long caseId) {
		List<Invitation> list = getHibernateTemplate().find(
				"from Invitation i where i.caseFile.id = ?", caseId);
		for (Invitation invitation : list) {
			if (invitation.getStatus() == Status.OPEN) {
				invitation.setStatus(Status.CLOSED);

				update(invitation);
			}
		}
	}

}
