package biz.sudden.baseAndUtility.repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import biz.sudden.baseAndUtility.domain.Invitation;
import biz.sudden.baseAndUtility.domain.Invitation.Status;

public class DummyInvitationRepository implements InvitationRepository {
	List<Invitation> invitations = new LinkedList<Invitation>();
	AtomicLong idGenerator = new AtomicLong();

	@Override
	public void cancelInvitationsForCaseFile(Long caseId) {
		for (Invitation invitation : this.invitations) {
			if (invitation.getCaseFile().getId().equals(caseId)
					&& invitation.getStatus() == Status.OPEN)
				invitation.setStatus(Status.CLOSED);
		}
	}

	@Override
	public Long create(Invitation invitation) {
		invitation.setId(idGenerator.addAndGet(1));
		invitations.add(invitation);

		return invitation.getId();
	}

	@Override
	public void delete(Invitation d) {
	}

	@Override
	public Invitation retrieve(Long id) {
		for (Invitation invitation : invitations) {
			if (invitation.getId().equals(id))
				return invitation;
		}
		return null;
	}

	@Override
	public List<Invitation> retrieveAll() {
		return Collections.unmodifiableList(invitations);
	}

	@Override
	public <S> List<S> retrieveAllByType(Class<S> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invitation retrieveByFieldName(String fieldName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invitation retrieveByFieldNameContains(String fieldName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invitation retrieveByFieldNameContainsLowerCase(String fieldName,
			String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invitation retrieveByFieldNameLowerCase(String fieldName,
			String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Invitation d) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsObject(Invitation object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Invitation> retrieveAllByFieldName(String fieldName,
			String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Invitation> retrieveAllByFieldNameContains(String fieldName,
			String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Invitation> retrieveAllByFieldNameContainsLowerCase(
			String fieldName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Invitation> retrieveAllByFieldNameLowerCase(String fieldName,
			String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S> List<S> retrieveAllChildren(Invitation parent,
			Class<S> childrenType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S> S retrieveByTypeAndId(Class<S> type, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
