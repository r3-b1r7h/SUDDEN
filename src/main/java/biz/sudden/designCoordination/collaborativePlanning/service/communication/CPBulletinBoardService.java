package biz.sudden.designCoordination.collaborativePlanning.service.communication;

import java.util.List;

import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoard;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.domain.Topic;

public interface CPBulletinBoardService {

	public void createBulletin(BulletinBoard board);

	public void createTopicAndAddToBulletinBoard(BulletinBoard board,
			Topic topic);

	public void updateTopic(Topic topic);

	public BulletinBoard getBoardWithName(String name);

	public void createContributionAndAddToTopic(Topic topic,
			Communication contribution);

	public List<BulletinBoard> retrieveAllBulletinBoards();

	public List<BulletinBoard> retrieveAllBulletinBoardsOfType(Class type);

	public BulletinBoard retrieveBulletinBoard(long id);

	public Topic retrieveTopic(Long id);

	public List<Communication> retrieveChildren(Communication comm);

	public Communication retrieveCommunication(Long id);

	public List<Topic> retrieveAllTopics(BulletinBoard board);

	public List<Communication> getAllCommunications(Topic topic);

	// public List<CommunicationRating> getAllCommunications(TopicRating topic);

	public void initializeBulletinBoards();

	public void deleteBulletinBoards();

}
