package biz.sudden.designCoordination.collaborativePlanning.service.communication.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.Link;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.baseAndUtility.service.LinkService;
import biz.sudden.baseAndUtility.service.RootLinkService;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;
import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoard;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.domain.CommunicationRating;
import biz.sudden.designCoordination.collaborativePlanning.domain.Topic;
import biz.sudden.designCoordination.collaborativePlanning.domain.TopicRating;
import biz.sudden.designCoordination.collaborativePlanning.repository.BulletinBoardRepository;
import biz.sudden.designCoordination.collaborativePlanning.repository.CommunicationRepository;
import biz.sudden.designCoordination.collaborativePlanning.repository.TopicRepository;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPBulletinBoardService;

class CPBulletinBoardServiceImpl implements CPBulletinBoardService {

	private Logger logger = Logger.getLogger(this.getClass());

	private BulletinBoardRepository bulletinBoardRepository;
	private TopicRepository topicRepository;
	private CommunicationRepository communicationRepository;
	private RootLinkService rootLinkService;
	private LinkService linkService;

	public RootLinkService getRootLinkService() {
		return rootLinkService;
	}

	public void setRootLinkService(RootLinkService rootLinkService) {
		this.rootLinkService = rootLinkService;
	}

	public LinkService getLinkService() {
		return linkService;
	}

	public void setLinkService(LinkService linkService) {
		this.linkService = linkService;
	}

	private SuddenGenericRepository genericRepository;

	public SuddenGenericRepository getGenericRepository() {
		return genericRepository;
	}

	public void setGenericRepository(SuddenGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	private List<BulletinBoard> initializeBulletinBoards = new ArrayList();

	public List<BulletinBoard> getInitializeBulletinBoards() {
		return initializeBulletinBoards;
	}

	public void setInitializeBulletinBoards(
			List<BulletinBoard> initializeBulletinBoards) {
		this.initializeBulletinBoards = initializeBulletinBoards;
	}

	@Override
	public List<Communication> retrieveChildren(Communication comm) {
		return communicationRepository.retrieve(comm.getId())
				.getChildrenCommunications();
		// return comm.getChildrenCommunications();
	}

	@Override
	public BulletinBoard retrieveBulletinBoard(long id) {
		return bulletinBoardRepository.retrieve(id);
	}

	// public void initializeCommunications() {
	//
	// for (Communication communication : initializeCommunications) {
	// try {
	// boolean alreadyInDatabase = false;
	// Long id = 0l;
	// for (Communication commDatabase :
	// communicationRepository.getAllCommunications()) {
	// if (commDatabase.getSpringId().equals(communication.getSpringId())) {
	// id =commDatabase.getId();
	// alreadyInDatabase = true;
	// }
	// }
	// if (!alreadyInDatabase)
	// id = createMessage(communication);
	// communication.setId(id);
	// } catch (Exception ex) {
	// logger.debug("Exception in "+this.getClass().getName()+ "occurred "+ex);
	// }
	// }
	// }
	//	
	// @Override
	// public void deleteInitializedCommuncations() {
	// for (Communication communication : initializeCommunications) {
	// for (Communication commDatabase :
	// communicationRepository.getAllCommunications()) {
	// if (commDatabase.getSpringId().equals(communication.getSpringId())) {
	// communication = commDatabase;
	// }
	// }
	// try {
	// deleteMessage(communication);
	// } catch (Exception ex) {
	// logger.debug("Exception in "+this.getClass().getName()+ "occurred "+ex);
	// }
	// }
	//		
	// }

	private void createLink(Connectable from, Connectable to) {
		JsfLink bulletinBoardLink = new JsfLink();

		for (JsfLink jsfLink : rootLinkService.getAllLinkableTypes()) {

			logger.debug(jsfLink);

			if ("showtopics".equals(jsfLink.getViewID())) {
				bulletinBoardLink.setControllerBeanName(jsfLink
						.getControllerBeanName());
				bulletinBoardLink.setDescription(jsfLink.getDescription());
				bulletinBoardLink.setDomainClass(jsfLink.getDomainClass());
				bulletinBoardLink.setDomainId(jsfLink.getDomainId());
				bulletinBoardLink.setText(jsfLink.getText());
				bulletinBoardLink.setViewID(jsfLink.getViewID());
				bulletinBoardLink.setParameterValuesPairs(jsfLink
						.getParameterValuesPairs());
				break;
			}

		}

		Link link = new Link();
		link.setFromClass(from.getClass().getName());
		link.setFromID(new Long(from.getId().toString()));

		bulletinBoardLink.setText("BulletinBoard " + to.toString());
		bulletinBoardLink.setDomainId(to.getId().toString());

		link.setToJsfLink(bulletinBoardLink);

		linkService.createLink(link);

	}

	private void deleteLinksTo(Connectable connectable) {

		List<JsfLink> jsfLinkToDeleteList = new LinkedList<JsfLink>();

		for (JsfLink jsflink : genericRepository
				.retrieveAllByType(JsfLink.class)) {
			if (jsflink.getDomainClass().equals(
					connectable.getClass().getName())) {
				jsfLinkToDeleteList.add(jsflink);
			}
		}

		for (Link link : genericRepository.retrieveAllByType(Link.class)) {
			if (jsfLinkToDeleteList.contains(link.getToJsfLink())) {
				genericRepository.delete(link);
			}
		}

		for (JsfLink jsfLink : jsfLinkToDeleteList) {
			genericRepository.delete(jsfLink);
		}

	}

	public void initializeBulletinBoards() {
		try {

			// deleteBulletinBoards();

			for (CaseFile caseFile : genericRepository
					.retrieveAllByType(CaseFile.class)) {

				logger.debug(caseFile.getName());
				BulletinBoard bulletinBoard = new BulletinBoard();
				bulletinBoard.setName("Forum for " + caseFile.getName());
				bulletinBoardRepository.create(bulletinBoard);

				createLink(caseFile, bulletinBoard);
			}

			for (BulletinBoard bulletinBoard : initializeBulletinBoards) {
				logger.debug(bulletinBoard);
				boolean alreadyInDatabase = false;
				Long id = 0l;
				for (BulletinBoard bullBoardDatabase : bulletinBoardRepository
						.retrieveAll()) {
					if (bullBoardDatabase.getSpringId() != null
							&& bullBoardDatabase.getSpringId() != null
							&& bullBoardDatabase.getSpringId().equals(
									bulletinBoard.getSpringId())) {
						id = bullBoardDatabase.getId();
						alreadyInDatabase = true;
					}
				}
				if (!alreadyInDatabase) {
					id = bulletinBoardRepository.create(bulletinBoard);
				}
				bulletinBoard.setId(id);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug(ex);
		}
	}

	public void deleteBulletinBoards() {

		for (BulletinBoard bulletinBoard : genericRepository
				.retrieveAllByType(BulletinBoard.class)) {
			genericRepository.delete(bulletinBoard);
			deleteLinksTo(bulletinBoard);
		}

		for (BulletinBoard bulletinBoard : initializeBulletinBoards) {
			for (BulletinBoard bullBoardDatabase : bulletinBoardRepository
					.retrieveAll()) {
				if (bullBoardDatabase.getSpringId() != null
						&& bullBoardDatabase.getSpringId().equals(
								bulletinBoard.getSpringId())) {

					for (Topic topic : bullBoardDatabase.getTopicList()) {

						for (Communication comm : topic.getCommunications()) {
							// communicationRepository.delete(d)
							comm.getChildrenCommunications().clear();
							comm.setInReplyTo(null);
							communicationRepository.update(comm);
						}
					}
					// bulletinBoardRepository.delete(bullBoardDatabase);
				}
			}

			for (BulletinBoard bullBoardDatabase : bulletinBoardRepository
					.retrieveAll()) {
				if (bullBoardDatabase.getSpringId() != null
						&& bullBoardDatabase.getSpringId().equals(
								bulletinBoard.getSpringId())) {

					bulletinBoardRepository.delete(bullBoardDatabase);
				}
			}
			try {

				bulletinBoard.setId(null);
				for (Topic topic : bulletinBoard.getTopicList()) {
					topic.setId(null);
					// topic.setBelongsToBoard(null);
					for (Communication comm : topic.getCommunications()) {
						comm.setId(null);
						for (Communication comm2 : comm
								.getChildrenCommunications()) {
							comm2.setInReplyTo(null);
						}
						comm.getChildrenCommunications().clear();

						// comm.setTopic(null);
					}
				}
				// bulletinBoard.getTopicList().clear();
			} catch (Exception ex) {
				logger.debug("Exception in " + this.getClass().getName()
						+ "occurred " + ex);
			}
		}
	}

	public CommunicationRepository getCommunicationRepository() {
		return communicationRepository;
	}

	public void setCommunicationRepository(
			CommunicationRepository communicationRepository) {
		this.communicationRepository = communicationRepository;
	}

	public BulletinBoardRepository getBulletinBoardRepository() {
		return bulletinBoardRepository;
	}

	public void setBulletinBoardRepository(
			BulletinBoardRepository bulletinBoardRepository) {
		this.bulletinBoardRepository = bulletinBoardRepository;
	}

	public TopicRepository getTopicRepository() {
		return topicRepository;
	}

	public void setTopicRepository(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}

	@Override
	public void updateTopic(Topic topic) {
		// TODO Auto-generated method stub
		topicRepository.update(topic);
	}

	@Override
	public void createTopicAndAddToBulletinBoard(BulletinBoard board,
			Topic topic) {

		topic.setBelongsToBoard(board);

		topicRepository.create(topic);

		board.getTopicList().add(topic);

	}

	@Override
	public BulletinBoard getBoardWithName(String name) {
		return bulletinBoardRepository.retrieveByFieldName("name", name);
	}

	@Override
	public void createContributionAndAddToTopic(Topic topic,
			Communication contribution) {

		// logger.debug("Contribution TOPIC " +
		// contribution.getTopic().getName() + " " +
		// contribution.getTopic().getId());

		communicationRepository.create(contribution);

		topic = topicRepository.retrieve(topic.getId());
		contribution = communicationRepository.retrieve(contribution.getId());

		contribution.setTopic(topic);

		communicationRepository.create(contribution);

		logger.debug("Create Contribution and add to topic");

		if (topic instanceof TopicRating) {
			TopicRating rating = (TopicRating) topic;
			rating.getCommunications().add(contribution);
			contribution.setTopic(rating);
		} else {

			topic.getCommunications().add(contribution);
		}
		topicRepository.update(topic);
		logger.debug("fertig");
	}

	@Override
	public void createBulletin(BulletinBoard board) {
		// TODO Auto-generated method stub
		bulletinBoardRepository.create(board);

	}

	@Override
	public List<BulletinBoard> retrieveAllBulletinBoards() {
		// TODO Auto-generated method stub
		return bulletinBoardRepository.retrieveAll();
	}

	@Override
	public List<BulletinBoard> retrieveAllBulletinBoardsOfType(Class type) {
		// TODO Auto-generated method stub
		return bulletinBoardRepository.retrieveAllOfType(type);
	}

	@Override
	public List<Topic> retrieveAllTopics(BulletinBoard board) {
		// TODO Auto-generated method stub

		return board.getTopicList();

	}

	public List<Communication> getAllCommunications(Topic topic) {
		// topicRepository.update(topic);
		return topic.getCommunications();
	}

	public List<CommunicationRating> retrieveAllCommunications(TopicRating topic) {

		TopicRating myTopic = (TopicRating) topicRepository.retrieve(topic
				.getId());
		logger.debug(Hibernate.isInitialized(myTopic));
		List commRating = myTopic.getCommunications();
		return commRating;

	}

	@Override
	public Topic retrieveTopic(Long id) {
		// TODO Auto-generated method stub
		return topicRepository.retrieve(id);
	}

	@Override
	public Communication retrieveCommunication(Long id) {
		// TODO Auto-generated method stub
		return communicationRepository.retrieve(id);
	}

}
