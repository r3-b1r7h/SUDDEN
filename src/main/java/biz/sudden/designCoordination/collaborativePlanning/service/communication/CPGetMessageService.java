package biz.sudden.designCoordination.collaborativePlanning.service.communication;

import java.util.List;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;

public interface CPGetMessageService {

	public List<Communication> retrieveMessagesFor(SuddenUser user);

	public List<Communication> retrieveAllCommunications();

	public List<SuddenUser> retrieveReceiver(Long id);

}
