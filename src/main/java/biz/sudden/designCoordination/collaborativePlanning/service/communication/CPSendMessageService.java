package biz.sudden.designCoordination.collaborativePlanning.service.communication;

import java.util.Date;
import java.util.List;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;

/**
 * 
 * With the aid of this ServiceInterface it is possible to retrieve the inbox
 * (of the user logged in) and to send a message to an other user. Further it is
 * possible to send a Invitations to potential partners which are contained in
 * an enterprise list
 * 
 * @author Thomas Feiner
 * 
 */

public interface CPSendMessageService {

	public void initializeCommunications();

	public void sendMessage(SuddenUser sender, List<SuddenUser> receiver,
			String subject, String message, Date deadLine);

	public Long createMessage(Communication communication);

	public void deleteInitializedCommuncations();

	// public void sendMessage(String sender, List<String> receivers, String
	// subject, String message) throws
	// ActorNotFoundException,AmbiguousActorException;
	//	
	// public void sendMessage(String sender, List<String> receivers, String
	// subject, String message, Date deadLine, BusinessOpportunity invitation,
	// Map backLink) throws ActorNotFoundException, AmbiguousActorException;

	/**
	 * @deprecated is now covered by sendMessage!
	 * 
	 * @param enterpriseList
	 *            A list of enterprises, where each user is extracted and the
	 *            invitation message is sent
	 * @param message
	 *            Invitation message
	 */
	@Deprecated
	public void invitePotentialPartner(List enterpriseList, String message);

}
