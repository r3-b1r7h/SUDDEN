package biz.sudden.designCoordination.collaborativePlanning.web.controller.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.baseAndUtility.web.controller.RootLinkController;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;
import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoard;
import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoardRating;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.domain.Topic;
import biz.sudden.designCoordination.collaborativePlanning.domain.TopicRating;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPBulletinBoardService;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPGetMessageService;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPSendMessageService;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.domain.SuddenUserObject;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.domain.WebCommunicationWrapper;
import biz.sudden.userInterface.gui.web.controller.UserSessionController;

import com.icesoft.faces.component.ext.HtmlCommandButton;
import com.icesoft.faces.component.ext.HtmlCommandLink;
import com.icesoft.faces.component.ext.HtmlDataTable;
import com.icesoft.faces.component.ext.HtmlOutputText;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.panelpopup.PanelPopup;
import com.icesoft.faces.component.tree.Tree;
import com.icesoft.faces.component.tree.TreeNode;

public class CpController {

	HtmlOutputText commLengthOutputText;

	public HtmlOutputText getCommLengthOutputText() {
		return commLengthOutputText;
	}

	public void setCommLengthOutputText(HtmlOutputText commLengthOutputText) {
		this.commLengthOutputText = commLengthOutputText;
	}

	private HtmlPanelGroup linkHookup = new HtmlPanelGroup();

	public HtmlPanelGroup getLinkHookup() {

		return linkHookup;

		// HtmlPanelGroup group = new HtmlPanelGroup();
		//		
		// linkHookups.add(group);
		//		
		// return group;

	}

	public void setLinkHookup(HtmlPanelGroup linkHookup) {

		UIParameter parameter = (UIParameter) linkHookup.getChildren().get(0);

		System.out.println(parameter);

		System.out.println(parameter.getValue());

		this.linkHookup = linkHookup;
		//
		//		
		// linkHookups.add(linkHookup);
		//		
		// UIParameter component =
		// (UIParameter)linkHookup.findComponent("link");
		//		
		// component.getValue();
		//		
		// HtmlCommandLink link = new HtmlCommandLink();
		// link.setValue(component.getValue());

	}

	private TreeModel treemodel;

	private DefaultMutableTreeNode rootTreeNode;

	private boolean refreshTree = false;

	private Logger logger = Logger.getLogger(this.getClass());

	private long millis = 0;
	private List<WebCommunicationWrapper> receiverComm = new LinkedList();
	private List webCommList = new LinkedList();

	protected BulletinBoard selectedBoard = null;

	private Communication inReplyTo;

	private CPSendMessageService cpSendMessageService;
	private CPGetMessageService cpGetMessageService;

	public Communication getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Communication inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	protected Topic selectedTopic = null;

	private Communication selectedContribution = null;

	public Communication getSelectedContribution() {
		return selectedContribution;
	}

	public void setSelectedContribution(Communication selectedContribution) {
		this.selectedContribution = selectedContribution;
	}

	public void setSelectedContribution(String selectedContributionId)
			throws Exception {

		this.selectedContribution = cpBulletinBoardService
				.retrieveCommunication(new Long(selectedContributionId));
		setSelectedTopic(selectedContribution.getTopic().getId().toString());

	}

	public Topic getSelectedTopic() {

		return selectedTopic;
	}

	protected CPBulletinBoardService cpBulletinBoardService;

	public CPBulletinBoardService getCpBulletinBoardService() {
		return cpBulletinBoardService;
	}

	public void setCpBulletinBoardService(
			CPBulletinBoardService cpBulletinBoardService) {
		this.cpBulletinBoardService = cpBulletinBoardService;
	}

	public void setSelectedTopic(Topic selectedTopic) {
		this.selectedTopic = selectedTopic;
	}

	public void setSelectedTopic(String id) throws Exception {

		logger.debug("Set selected Topic " + id);
		Topic myTopic = cpBulletinBoardService.retrieveTopic(new Long(id));
		cpBulletinBoardService.retrieveAllBulletinBoards();
		logger.debug("size of communications "
				+ myTopic.getCommunications().size());
		setSelectedTopic(myTopic);
		logger.debug("Set selected Topic " + myTopic.getName());
		this.selectedBoard = this.selectedTopic.getBelongsToBoard();
		treemodel = null;
		// cpBulletinBoardService.retrieveAllTopics(this.selectedBoard);
		// cpBulletinBoardService.getAllCommunications(this.selectedTopic);
	}

	public int getCommLength() {
		UIParameter uiParam = (UIParameter) commLengthOutputText.getChildren()
				.get(0);
		Topic topic = (Topic) uiParam.getValue();
		cpBulletinBoardService.updateTopic(topic);
		return cpBulletinBoardService.getAllCommunications(topic).size();
		// commLengthOutputText.findComponent("topic");
		// return 0;
	}

	private UserSessionController userSessionController;
	private UserService userService;
	private UserConverter userConverter;

	private HtmlDataTable dataTable;

	private Folder folder = null;

	private int lastPage = -1;

	private DataModel model;

	int size = 0;

	private List<Message> messageList = new ArrayList();

	final Properties props = new Properties();

	private Store store;

	private Session session;

	protected MyDataModel bulletinModel;

	private MyDataModel topicModel;

	private TreeModel contributionModel;

	protected String boardName;

	private Date boardCreationDate;

	protected String topicName;

	private String contributionSubject;

	private String contributionMessage;

	public String getContributionMessage() {
		return contributionMessage;
	}

	public void setContributionMessage(String contributionMessage) {
		this.contributionMessage = contributionMessage;
	}

	public String getContributionSubject() {
		return contributionSubject;
	}

	public void setContributionSubject(String contributionSubject) {
		this.contributionSubject = contributionSubject;
	}

	private Date topicCreationDate;

	private SuddenUser currentUser;

	private String viewID;

	private String controller;

	private String parameter;

	private String parameterValue;

	private String linkDescription;

	public String getLinkDescription() {
		return linkDescription;
	}

	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}

	public SuddenUser getCurrentUser() {

		if (currentUser == null) {

			try {
				Object objectUser = SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				currentUser = (SuddenUser) objectUser;
				return currentUser;
			} catch (Exception ex) {
				SuddenUser user = new SuddenUser("", "", "");
				logger.debug(ex);
				return user;
			}
		} else {
			return currentUser;
		}

	}

	public CpController() {
		try {
			Object objectUser = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			currentUser = (SuddenUser) objectUser;

			// FacesContext.getCurrentInstance().getViewRoot().findComponent(
			// "linkHookup").getChildren().add(inputText);

			// HtmlOutputText text = new HtmlOutputText();
			// text.setValue("hey");
			// FacesContext.getCurrentInstance().getViewRoot().getChildren().get(
			// 0).getChildren().add(text);

		} catch (ClassCastException ex) {
			logger.debug(ex);
		}
	}

	public void navigateTo(ActionEvent event) {

		logger.debug("EVENT " + event.getComponent() + " " + event);
		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("jsfLink");
		JsfLink jsfLink = (JsfLink) component.getValue();

		userSessionController.navigateTo(jsfLink.getViewID(), jsfLink
				.getControllerBeanName(), jsfLink.getParameterValuesPairs());

	}

	public Date getBoardCreationDate() {
		return boardCreationDate;
	}

	public void setBoardCreationDate(Date boardCreationDate) {
		this.boardCreationDate = boardCreationDate;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Date getTopicCreationDate() {
		return topicCreationDate;
	}

	public void setTopicCreationDate(Date topicCreationDate) {
		this.topicCreationDate = topicCreationDate;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public HtmlDataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}

	public DataModel getTopicsModel() {

		if (selectedBoard != null) {
			List<Topic> topics = cpBulletinBoardService
					.retrieveAllTopics(selectedBoard);
			for (Topic topic : topics) {
				List list = cpBulletinBoardService.getAllCommunications(topic);
				topic.setCommunications(list);
				cpBulletinBoardService.updateTopic(topic);
				// logger.debug(list.size());
			}

			topicModel = new MyDataModel(topics, topics.size(), 10);
			return topicModel;
		} else {
			return new MyDataModel(new ArrayList(), 0, 10);
		}
	}

	// public TreeModel refreshContributionModel() throws Exception {
	//
	// if (selectedTopic != null) {
	//
	// List<Communication> contributions =
	// cpBulletinBoardService.getAllCommunications(selectedTopic);
	//
	// rootTreeNode = new DefaultMutableTreeNode();
	// SuddenUserObject<Communication> rootObject = new
	// SuddenUserObject<Communication>(rootTreeNode);
	// rootObject.setText("Root Node");
	//
	// rootObject.setExpanded(true);
	// rootTreeNode.setUserObject(rootObject);
	//
	// rootTreeNode.removeAllChildren();
	//
	// // model is accessed by by the ice:tree component via a getter
	// // method
	// treemodel = new DefaultTreeModel(rootTreeNode);
	//
	// for (Communication communication : contributions) {
	// DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
	// SuddenUserObject<Communication> branchObject = new
	// SuddenUserObject<Communication>(branchNode);
	// branchObject.setText(communication.getSubject() + " " +
	// communication.getSender().getNickname());
	// branchObject.setAttachedObject(communication);
	// branchNode.setUserObject(branchObject);
	//
	// rootTreeNode.add(branchNode);
	// }
	//
	// }
	//
	// this.contributionModel = treemodel;
	//
	// return treemodel;
	//
	// }

	private void addNode(DefaultMutableTreeNode parent,
			List<Communication> communicationList) {
		for (Communication communication : communicationList) {

			DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
			SuddenUserObject<Communication> branchObject = new SuddenUserObject<Communication>(
					branchNode);
			branchObject.setText("Message from "
					+ communication.getSender().getNickname()
					+ " with Subject: " + communication.getSubject());

			branchObject.setAttachedObject(communication);
			branchNode.setUserObject(branchObject);

			// if (parent)
			parent.add(branchNode);

			// cpBulletinBoardService.retrieveAllTopics(board)

			if (cpBulletinBoardService.retrieveChildren(communication).size() > 0) {
				branchObject.setExpanded(true);
				addNode(branchNode, cpBulletinBoardService
						.retrieveChildren(communication));
			} else {
				branchObject.setLeaf(true);
			}

		}
	}

	protected void addNode(DefaultMutableTreeNode parent,
			List<BulletinBoard> bulletinBoardList, boolean isBulletinBoardList) {

		for (BulletinBoard bulletinBoard : bulletinBoardList) {

			DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
			SuddenUserObject<BulletinBoard> branchObject = new SuddenUserObject<BulletinBoard>(
					branchNode);

			String bulletinBoardName = bulletinBoard.getName();
			String bulletinBoardOwner = (bulletinBoard.getOwner() != null) ? bulletinBoard
					.getOwner().getNickname()
					: "";

			String name = "BulletinBoard ";
			if (bulletinBoard instanceof BulletinBoardRating) {
				name = "Improvement Group ";
			}

			branchObject.setText(name + " " + bulletinBoardName + " "
					+ bulletinBoardOwner);
			branchObject.setAttachedObject(bulletinBoard);
			branchNode.setUserObject(branchObject);

			// if (parent)
			parent.add(branchNode);

			if (bulletinBoard.getTopicList().size() == 0)
				branchObject.setLeaf(true);

			for (Topic topic : bulletinBoard.getTopicList()) {
				DefaultMutableTreeNode branchNode1 = new DefaultMutableTreeNode();
				SuddenUserObject<Topic> branchObject1 = new SuddenUserObject<Topic>(
						branchNode1);

				String topicOwner = topic.getOwner() != null ? topic.getOwner()
						.getNickname() : "";

				String topicName = "";

				if (topic instanceof TopicRating)
					topicName = "Trainings/Improvements";
				else
					topicName = "Topic";

				branchObject1.setText(topicName + " " + topic.getName() + " "
						+ topicOwner);
				branchObject1.setAttachedObject(topic);
				branchNode1.setUserObject(branchObject1);

				branchNode.add(branchNode1);
				// branchObject1.setLeaf(true);
				logger.debug("Sort the communications");
				List<Communication> communicationWithChildrenList = getWithChildrenCommunications(topic
						.getCommunications());
				addNode(branchNode1, communicationWithChildrenList);

			}
			//
			// if (communication.getChildrenCommunications().size() > 0) {
			// branchObject.setExpanded(true);
			// addNode(branchNode, communication.getChildrenCommunications());
			// } else {
			// branchObject.setLeaf(true);
			// }

		}
	}

	private List<Communication> getWithChildrenCommunications(
			List<Communication> communicationListWithoutChildren) {

		List<Communication> communicationListWithChildren = new LinkedList<Communication>();

		for (Communication communication : communicationListWithoutChildren) {
			if (communication.getInReplyTo() != null) {
				if (!communication.getInReplyTo().getChildrenCommunications()
						.contains(communication))
					communication.getInReplyTo().getChildrenCommunications()
							.add(communication);
			} else {
				communicationListWithChildren.add(communication);
			}
		}

		return communicationListWithChildren;
	}

	public TreeModel getContributionModel() throws Exception {

		logger.debug("Get ContributionsModel called ");

		logger.debug(selectedTopic + " " + treemodel + " " + refreshTree);

		// FIXME ThF just a workaround
		// refreshTree = true;

		// if (selectedTopic != null && treemodel == null) {

		if ((selectedTopic != null && treemodel == null) || refreshTree == true) {

			refreshTree = false;

			List<Communication> contributions = cpBulletinBoardService
					.getAllCommunications(selectedTopic);

			List<Communication> contributionWithChildrenList = getWithChildrenCommunications(contributions);

			// create root node with its children expanded

			// if (treemodel == null) {

			rootTreeNode = new DefaultMutableTreeNode();
			SuddenUserObject<Communication> rootObject = new SuddenUserObject<Communication>(
					rootTreeNode);
			rootObject.setText("Root Node");

			rootObject.setExpanded(true);
			rootTreeNode.setUserObject(rootObject);
			// }

			rootTreeNode.removeAllChildren();

			addNode(rootTreeNode, contributionWithChildrenList);

			// model is accessed by by the ice:tree component via a getter
			// method
			treemodel = new DefaultTreeModel(rootTreeNode);

		}

		this.contributionModel = treemodel;

		return treemodel;

	}

	public BulletinBoard getSelectedBoard() {
		return selectedBoard;
	}

	public void setSelectedBoard(BulletinBoard board) {
		this.selectedBoard = board;
	}

	public void setSelectedBoard(long boardID) {
		this.selectedBoard = cpBulletinBoardService
				.retrieveBulletinBoard(boardID);
	}

	public void setSelectedBoard(String boardString) {
		// TODO Auto-generated method stub
		this.setSelectedBoard(new Long(boardString));
	}

	public DataModel getBulletinBoardsModel() throws Exception {
		List bulletinBoardList = cpBulletinBoardService
				.retrieveAllBulletinBoardsOfType(BulletinBoard.class);
		logger.debug("Bulletinboards Size " + bulletinBoardList.size());
		// FIXME TF: Error while retrieving values of bulletinBoardList
		bulletinModel = new MyDataModel(bulletinBoardList, bulletinBoardList
				.size(), 100);

		return bulletinModel;
	}

	public void createNewBoard() {
		BulletinBoard board = new BulletinBoard();
		board.setName(this.boardName);
		board.setDateCreated(new Date());
		board.setOwner(getCurrentUser());
		cpBulletinBoardService.createBulletin(board);
		setBoardName("");
		// return null;
	}

	public void createNewTopic() {
		Topic topic = new Topic();
		// selectedBoard
		topic.setName(this.topicName);
		topic.setDateCreated(new Date());
		topic.setOwner(getCurrentUser());
		cpBulletinBoardService.createTopicAndAddToBulletinBoard(selectedBoard,
				topic);
		setTopicName("");
		// return null;
	}

	public void createNewContribution(ActionEvent event) throws Exception {

		Communication comm = new Communication();

		if (inReplyTo != null) {
			comm.setInReplyTo(inReplyTo);

		}

		comm.setSubject(contributionSubject);
		comm.setSender(getCurrentUser());
		comm.setSendDate(new Date());
		comm.setTopic(selectedTopic);
		comm.setMessage(contributionMessage);
		selectedTopic.getCommunications().add(comm);
		cpBulletinBoardService.createContributionAndAddToTopic(selectedTopic,
				comm);
		// userService.createUser(username, password, nickname)
		setContributionSubject(""); // TODO TF ACHTUNG! bei referenzen auf
		contributionMessage = "";

		inReplyTo = new Communication();
		inReplyTo = null;

		refreshTree = true;

		// treemodel = null;
		//
		// getContributionModel();

		// refreshContributionModel();

		FacesContext.getCurrentInstance().getApplication()
				.getNavigationHandler().handleNavigation(
						FacesContext.getCurrentInstance(), null,
						"showcontributions");

		// DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
		// SuddenUserObject<Communication> branchObject = new
		// SuddenUserObject<Communication>(branchNode);
		// branchObject.setText(comm.getSubject());
		// branchObject.setAttachedObject(comm);
		// branchNode.setUserObject(branchObject);
		// rootTreeNode.add(branchNode);

		// rootTreeNode.add(newChild)
		// getContributionsModel();
		// commited objects!!
	}

	public String openBoard() {
		logger.debug(bulletinModel.getRowData());
		selectedBoard = (BulletinBoard) bulletinModel.getRowData();
		return "showtopics";
	}

	public String openTopic() {
		selectedTopic = (Topic) topicModel.getRowData();
		treemodel = null; // clear all shown contributions for following
		// contribution tree-list
		return "showcontributions";
	}

	public void openContribution(ActionEvent event) {

		logger.debug("OPEN CONTRIBUTION");

		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("userobject");
		SuddenUserObject<Communication> userObject = (SuddenUserObject<Communication>) component
				.getValue();

		selectedContribution = userObject.getAttachedObject();

		logger.debug(selectedContribution.getSubject());

		FacesContext.getCurrentInstance().getApplication()
				.getNavigationHandler().handleNavigation(
						FacesContext.getCurrentInstance(), null,
						"showcontribution");

	}

	public void openNewContribution(ActionEvent event) {

		UIParameter component = (UIParameter) event.getComponent()
				.findComponent("inreplyto");

		if (component != null && component.getValue() != null) {
			inReplyTo = (Communication) component.getValue();

		}

		FacesContext.getCurrentInstance().getApplication()
				.getNavigationHandler().handleNavigation(
						FacesContext.getCurrentInstance(), null,
						"opennewcontribution");

	}

	public DataModel getMyDataModel() throws Exception {

		long diff = System.currentTimeMillis() - millis;
		logger.debug("Diff " + diff);
		int first = getDataTable().getFirst() + 1;
		int last = getDataTable().getFirst() + getDataTable().getRows();

		logger.debug(first + " " + last + " " + lastPage);
		if (first != lastPage || diff > 10000) {

			millis = System.currentTimeMillis();

			webCommList = new LinkedList();

			logger.debug("Get Messages ");

			if (folder == null) {
				// initializeMail();
			}

			try {
				//
				//
				//				
				// folder = store.getFolder("INBOX");
				// folder.open(Folder.READ_WRITE);

			} catch (Exception ex) {
				logger.error(ex);
			}

			// logger.debug(folder.getMessageCount());
			// Message message[];
			// try {
			// message = folder.getMessages(folder.getMessageCount()-last,
			// folder.getMessageCount()-first);
			// } catch (IndexOutOfBoundsException ex) {
			// message = folder.getMessages(1, folder.getMessageCount()-first);
			// }
			// // folder.expunge();
			//
			// messageList = Arrays.asList(message);
			//			
			// Collections.reverse(messageList);
			//
			// List<Message> list2 = new ArrayList();
			//
			// logger.debug("SIZE " + size);
			//
			// for (Message message2 : messageList) {
			// try {
			//					
			// if (message2.isSet(Flag.DELETED)) {
			// logger.debug(message2.getSubject()+ "is deleted");
			// //message2.setSubject("deleted");
			// } else {
			// list2.add(message2);
			// }
			//					
			// } catch (Exception ex) {
			// logger.debug(ex + " " + message2);
			// }
			//
			// }
			//
			// size = list2.size();
			//
			// logger.debug("There are " + list2.size() + " messages");
			//
			// List<Message> myList = new LinkedList();
			// myList = list2;
			// // for (int j = 0; j < getDataTable().getRows(); j++) {
			// // try {
			// // myList.add(list2.get(first - 1 + j));
			// // } catch (IndexOutOfBoundsException ex) {
			// // logger.error(ex);
			// // }
			// // }
			//
			// int i = 0;
			// for (Message currMessage : myList) {
			// try {
			// i++;
			// InternetAddress address = (InternetAddress)
			// currMessage.getFrom()[0];
			// User currUser = new User();
			// Communication comm = new Communication();
			// comm.setId(new Long(i));
			// currUser.setNickname(address.getPersonal());
			// comm.setSender(currUser);
			// comm.setSubject(currMessage.getSubject());
			// String content =
			// currMessage.getContent().toString().replaceAll("(\r\n|\r|\n|\n\r)"
			// , "<br />");
			// comm.setMessage(content);
			// WebCommunicationWrapper wrapper = new
			// WebCommunicationWrapper(this);
			// wrapper.setCommunication(comm);
			// webCommList.add(wrapper);
			// } catch (Exception ex) {
			// logger.error(ex);
			// }
			// }

			try {

				// Object objectUser =
				// SecurityContextHolder.getContext().getAuthentication
				// ().getPrincipal();
				//
				// logger.debug("The user is " + objectUser);
				//
				// User user = (User) objectUser;

				SuddenUser user = currentUser;

				for (Communication communication : cpGetMessageService
						.retrieveMessagesFor(user)) {

					// logger.debug("LINK SOURCE INTERFACE "+((LinkSourceInterface
					// )communication).getLinkSource());

					WebCommunicationWrapper wrapper = new WebCommunicationWrapper(
							this);
					wrapper.setCommunication((Communication) (communication));
					webCommList.add(wrapper);
				}

			} catch (Exception ex) {
				logger.debug("Exception while retrieving messages for user "
						+ ex);
			}

			lastPage = first;

		}

		logger.debug("Size " + size);
		model = new MyDataModel(webCommList, webCommList.size(), 10);

		return model;
	}

	private int dividerPosition = 50;

	public int getDividerPosition() {
		return dividerPosition;
	}

	public void setDividerPosition(int dividerPosition) {
		this.dividerPosition = dividerPosition;
	}

	private WebCommunicationWrapper currentCommunicationWrapper = null;

	public WebCommunicationWrapper getCurrentCommunicationWrapper() {
		return currentCommunicationWrapper;
	}

	public void setCurrentCommunicationWrapper(
			WebCommunicationWrapper currentCommunicationWrapper) {
		this.currentCommunicationWrapper = currentCommunicationWrapper;
	}

	private boolean popupVisible = false;

	public boolean isPopupVisible() {
		return popupVisible;
	}

	public void setPopupVisible(boolean popupVisible) {
		this.popupVisible = popupVisible;
	}

	private SuddenUser selectedUser;

	private SelectItem[] userItems;

	private List receiver = new LinkedList();
	private Date deadLine;
	private String subject;
	private String message;
	private Date sendDate;

	private String errorMessage = "";

	private List<SuddenUser> userList = new LinkedList();

	private List list;

	// private String stringUserList;

	public String getStringUserList() {
		String concatenateString = "";
		for (SuddenUser user : userList) {
			concatenateString += " " + user.getNickname();
		}
		return concatenateString;
	}

	public List<SuddenUser> getUserList() {
		return userList;
	}

	public void setUserList(List<SuddenUser> userList) {
		this.userList = userList;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public UserConverter getUserConverter() {
		return userConverter;
	}

	public void setUserConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/** Getters and Setters **/
	public CPSendMessageService getCpSendMessageService() {
		return cpSendMessageService;
	}

	public void setCpSendMessageService(
			CPSendMessageService cpSendMessageService) {
		this.cpSendMessageService = cpSendMessageService;
	}

	public CPGetMessageService getCpGetMessageService() {
		return cpGetMessageService;
	}

	public void setCpGetMessageService(CPGetMessageService cpGetMessageService) {
		this.cpGetMessageService = cpGetMessageService;
	}

	public UserSessionController getUserSessionController() {
		return userSessionController;
	}

	public void setUserSessionController(
			UserSessionController userSessionController) {
		this.userSessionController = userSessionController;
	}

	public SuddenUser getSelectedUser() {

		Map<String, Object> map = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		logger.debug(map);
		for (String key : map.keySet()) {
			if (key.contains("biz.sudden")) {
				logger.debug("Parameter " + map.get(key));
			}
		}
		return selectedUser;
	}

	public void setSelectedUser(SuddenUser selectedUser) {
		logger.debug("SET SELECTED USER " + selectedUser);
		this.selectedUser = selectedUser;
	}

	public List getReceiver() {
		return receiver;
	}

	public void setReceiver(List receiver) {
		this.receiver = receiver;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void userItemChanged(ValueChangeEvent event) {
		logger.debug("Event " + event);
		SuddenUser user = (SuddenUser) event.getNewValue();

	}

	public List getUserItems() {
		List<SuddenUser> userList = userService.retrieveAllUsers();

		List list = new LinkedList();
		for (SuddenUser user : userList) {
			SelectItem item = new SelectItem(user, user.getNickname());
			list.add(item);
			// list.add(user);

			// SelectItem item = new SelectItem(user.getNickname(), user);
			// item.setDescription(user.getNickname());
			// item.setValue(user);
			// item.setLabel(i+" "+user.getNickname());
			// list.add(item);

		}
		userConverter = new UserConverter(list);
		// userConverter.setList(list);
		return list;
	}

	public void setUserItems(SelectItem[] userItems) {
		this.userItems = userItems;
	}

	public void addUser(ActionEvent event) {
		userList.add(selectedUser);
	}

	// public List<WebCommunicationWrapper> getMessages() {
	// getDataTable().get
	// }

	// public List<WebCommunicationWrapper> getMessages() {
	//
	// long diff = new Date().getTime() - millis;
	// logger.debug("Diff " + diff);
	// if (diff > 180000) {
	// final Properties props = new Properties();
	// props.setProperty("mail.imap.host", "imap.jku.at");
	// props.setProperty("mail.imap.user", "AK111431");
	// props.setProperty("mail.imap.password", "12492804");
	// props.setProperty("mail.imap.port", "993");
	// props.setProperty("mail.imap.auth", "true");
	// props.setProperty("mail.imap.socketFactory.class",
	// "javax.net.ssl.SSLSocketFactory");
	//
	// Session session = Session.getInstance(props, new
	// javax.mail.Authenticator() {
	// @Override
	// protected PasswordAuthentication getPasswordAuthentication() {
	// return new PasswordAuthentication(props.getProperty("mail.imap.user"),
	// props.getProperty("mail.imap.password"));
	// }
	// });
	//
	// // session.setDebug(true);
	//
	// try {
	//
	// Store store = session.getStore("imap");
	// store.connect();
	//
	// Folder folder = store.getFolder("INBOX");
	// folder.open(Folder.READ_ONLY);
	// logger.debug("Get messages from 1 to 50");
	// Message message[] = folder.getMessages(1, 4);
	//
	// millis = new Date().getTime();
	// logger.debug("getMessages - called from CpController");
	// receiverComm = new
	// java.util.ArrayList<WebCommunicationWrapper>(message.length);
	// logger.debug(receiverComm.size());
	//
	// Object objectUser =
	// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	//
	// if (objectUser instanceof User) {
	// User user = (User) objectUser;
	// List webCommList = new LinkedList();
	//
	// int i = 0;
	//
	// // Communication comm = new Communication();
	// // User currUser = new User();
	// // WebCommunicationWrapper wrapper = new
	// // WebCommunicationWrapper(this);
	//
	// for (Message currMessage : message) {
	// i++;
	// logger.debug(i);
	// InternetAddress address = (InternetAddress) currMessage.getFrom()[0];
	// logger.debug(address);
	// User currUser = new User();
	// Communication comm = new Communication();
	// currUser.setNickname(address.getPersonal());
	// comm.setSender(currUser);
	// comm.setSubject(currMessage.getSubject());
	//
	// // if (currMessage.getContent() instanceof Multipart) {
	// //
	// // Multipart part = (Multipart)
	// // currMessage.getContent();
	// //
	// // for (int j = 0; i < part.getCount(); i++) {
	// // Part currPart = part.getBodyPart(j);
	// // String disposition = currPart.getDisposition();
	// // //logger.debug("Disposition "+disposition);
	// // if (disposition == null) {
	// // MimeBodyPart mimePart =
	// // (javax.mail.internet.MimeBodyPart) currPart;
	// //
	// // //logger.debug(mimePart.getContent());
	// //
	// // //if (mimePart.isMimeType("text/*")) {
	// // BufferedReader in = new BufferedReader(new
	// // InputStreamReader(mimePart.getInputStream()));
	// // for (String line; (line=in.readLine()) != null; ) {
	// // //logger.debug("LINE "+line);
	// // }
	// // //}
	// // }
	// // }
	// //
	// // }
	//
	// String content =
	// currMessage.getContent().toString().replaceAll("(\r\n|\r|\n|\n\r)",
	// "<br />");
	// comm.setMessage(content);
	// // comm.setMessage("hallo");
	// WebCommunicationWrapper wrapper = new WebCommunicationWrapper(this);
	// wrapper.setCommunication(comm);
	// webCommList.add(wrapper);
	// }
	//
	// // for (Communication communication :
	// // cpGetMessageService.retrieveMessagesFor(user)) {
	// // wrapper = new WebCommunicationWrapper(this);
	// // wrapper.setCommunication((Communication)
	// // (communication));
	// // webCommList.add(wrapper);
	// // }
	// receiverComm = webCommList;
	// return webCommList;
	// } else {
	// return new LinkedList<WebCommunicationWrapper>();
	// }
	// } catch (Exception ex) {
	// return receiverComm;
	// }
	// } else {
	// logger.debug(
	// "Difference to last DB query was too short (<1000 millis), therefore a cached version will be returned"
	// );
	// return receiverComm;
	// }
	// }

	public PanelPopup getLinkableTypeSelectionPopup(final JsfLink jsfLink,
			RootLinkController rootLinkController) {
		logger.debug("Called getLinkableTypeSelectionPopup in " + this);
		final PanelPopup myPopup = new PanelPopup();

		myPopup.setDraggable("true");
		myPopup.setStyle("width:400px;height:300px;position:absolute;top:"
				+ (150) + "px;left:" + (500) + "px;");
		myPopup.setClientOnly(true);
		myPopup.setId("myPopup");

		// Close Button in Header
		HtmlCommandButton closeButton = (HtmlCommandButton) FacesContext
				.getCurrentInstance().getApplication().createComponent(
						HtmlCommandButton.COMPONENT_TYPE);
		closeButton.setType("submit");
		closeButton.setValue("Close");
		closeButton.setId("closeButton");

		closeButton.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {
				// TODO Auto-generated method stub
				myPopup.setVisible(false);
				myPopup.getParent().getChildren().remove(myPopup);
			}
		});

		HtmlPanelGroup headerGroup = new HtmlPanelGroup();
		headerGroup.getChildren().add(closeButton);

		myPopup.getFacets().put("header", headerGroup);

		// <ice:tree value="#{cpController.contributionModel}"
		// var="item" hideRootNode="true" hideNavigation="false"
		// immediate="true"
		// imageDir="./themes/default/style/xp/css-images/">
		// <ice:treeNode>
		// <f:facet name="content">
		// <ice:panelGroup style="display: inline">
		// <ice:commandLink value="#{item.userObject.text}"
		// actionListener="#{cpController.openContribution}">
		// <f:param value="#{item.userObject}" id="userobject"
		// name="userobject"></f:param>
		// </ice:commandLink>
		// </ice:panelGroup>
		// </f:facet>
		// </ice:treeNode>
		// </ice:tree>

		DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
		SuddenUserObject<Communication> rootObject = new SuddenUserObject<Communication>(
				rootTreeNode);
		rootObject.setExpanded(true);
		rootTreeNode.setUserObject(rootObject);
		rootObject.setText("Root Node");

		addNode(rootTreeNode, cpBulletinBoardService
				.retrieveAllBulletinBoardsOfType(BulletinBoard.class), true);

		// DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
		// SuddenUserObject<Communication> branchObject = new
		// SuddenUserObject<Communication>(branchNode);
		// branchObject.setText("Das ist der erste Eintrag");
		// branchObject.setAttachedObject(null);
		// branchNode.setUserObject(branchObject);
		// // if (parent)
		// rootTreeNode.add(branchNode);

		TreeModel treeModel = new DefaultTreeModel(rootTreeNode);

		Tree tree = new Tree();
		TreeNode treeNode = new TreeNode();
		HtmlPanelGroup panelGroup = new HtmlPanelGroup();

		tree.setHideRootNode("true");
		tree.setHideNavigation("false");
		tree.setImmediate(true);
		tree.setVar("item");
		tree.setValue(treeModel);
		tree.setId("cpControllerTree");
		ValueExpression expression = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{treeitems}", Object.class);
		expression.setValue(FacesContext.getCurrentInstance().getELContext(),
				treeModel);
		tree.setValueExpression("value", expression);
		// ValueBinding binding = new ValueBinding();
		// binding.setValue(FacesContext.getCurrentInstance(), treeModel);
		// tree.setValueBinding("value", binding);

		tree.getChildren().add(treeNode);

		treeNode.getFacets().put("content", panelGroup);

		ValueExpression expressionText = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{item.userObject.text}", Object.class);

		logger.debug("Domain Class is " + jsfLink.getDomainClass());

		ValueExpression expressionCompareClassNames = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{item.userObject.attachedObject.class.name == '"
								+ jsfLink.getDomainClass() + "'}", Object.class);

		ValueExpression notEqualsExpressionCompareClassNames = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{item.userObject.attachedObject.class.name != '"
								+ jsfLink.getDomainClass() + "'}", Object.class);

		final ValueExpression attachedObject = FacesContext
				.getCurrentInstance().getApplication().getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{item.userObject.attachedObject}", Object.class);

		HtmlOutputText text = new HtmlOutputText();

		text.setValueExpression("value", expressionText);
		text.setId("cpControllerTreeEntry");
		text.setStyle("font-size:0.8em;color:black");
		text.setValueExpression("rendered",
				notEqualsExpressionCompareClassNames);

		HtmlCommandLink link = new HtmlCommandLink();

		link.setValueExpression("value", expressionText);

		final RootLinkController finalRootLinkContr = rootLinkController;

		link.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {
				System.out.println(attachedObject.getValue(FacesContext
						.getCurrentInstance().getELContext()));
				try {
					Object object = attachedObject.getValue(FacesContext
							.getCurrentInstance().getELContext());
					Long id = (Long) object.getClass().getMethod("getId")
							.invoke(object);
					// jsfLink.getParameterValuesPairs().get(0).setParameterValue
					// (id.toString());
					jsfLink.setDomainId(id.toString());
					myPopup.setVisible(false);
					myPopup.getParent().getChildren().remove(myPopup);
					finalRootLinkContr.linkTogether(object, id, jsfLink);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		link.setId("link");
		link.setStyle("font-size:0.8em;color:black");
		link.setValueExpression("rendered", expressionCompareClassNames);

		panelGroup.getChildren().add(text);
		panelGroup.getChildren().add(link);

		panelGroup.setStyle("display:inline");

		HtmlPanelGroup bodyGroup = new HtmlPanelGroup();

		bodyGroup.getChildren().add(tree);

		// myPopup.getFacets().put("body", bodyGroup);
		myPopup.getFacets().put("body", bodyGroup);

		return myPopup;

		// TreeNode treeNode = new TreeNode();
		//		
		// HtmlPanelGroup contentPanel = new HtmlPanelGroup();
		// contentPanel.setStyle("display:inline");
		//		
		// HtmlOutputText text = new HtmlOutputText();
		// text.setValue("TEST TEST");
		//		
		// contentPanel.getChildren().add(text);
		//		
		// //treeNode.getFacets().put("content", contentPanel);
		//		
		// //tree.getChildren().add(treeNode);
		//		
		// DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
		// SuddenUserObject<Communication> rootObject = new
		// SuddenUserObject<Communication>(rootTreeNode);
		// rootObject.setText("Root Node");
		// TreeModel treeModel = new DefaultTreeModel(rootTreeNode);
		//	
		// rootObject.setExpanded(true);
		// rootTreeNode.setUserObject(rootObject);
		//		
		//
		// TreeModel treemodel = new DefaultTreeModel(rootTreeNode);
		//		
		// //tree.setValue(treeModel);
		//		
		//		

	}

	public String submit() {

		Communication communication = new Communication();

		logger.debug("User list " + userList);
		receiver = userList;

		// receiver.add(selectedUser);

		Object objectUser = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (objectUser instanceof SuddenUser) {
			SuddenUser sender = (SuddenUser) objectUser;

			// JsfLink link = new JsfLink();
			// /
			// ParameterNameValuePair parameterValuePair = new
			// ParameterNameValuePair();
			//
			// parameterValuePair.setParameter(this.parameter);
			// parameterValuePair.setParameterValue(this.parameterValue);
			//
			// List<ParameterNameValuePair> parameterValuePairs = new
			// ArrayList();
			//
			// parameterValuePairs.add(parameterValuePair);
			//
			// link.setParameterValuesPairs(parameterValuePairs);

			communication.setSender(sender);
			// communication.setJsfLink(link);

			communication.setReceiver(receiver);
			communication.setSendDate(new Date());
			// communication.setDeadLine(deadLine);
			communication.setMessage(message);
			communication.setSubject(subject);
			cpSendMessageService.createMessage(communication);

			try {
				List<SuddenUser> userList = cpGetMessageService
						.retrieveAllCommunications().get(0).getReceiver();
				System.out.println(userList.size());
				for (SuddenUser user : userList) {
					logger.debug(user.getNickname());
				}
			} catch (RuntimeException ex) {
				ex.printStackTrace();
			}

			receiver = new LinkedList();
			userList = new LinkedList();
			sender = null;
			message = "";
			subject = "";
			controller = "";
			viewID = "";
			parameter = "";
			parameterValue = "";
			return "ho11";
		} else {
			receiver = new LinkedList();
			userList = new LinkedList();
			message = "";
			subject = "";
			setErrorMessage("Not logged in as appropriate User (maybe Admin?)");
			return null;
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void openCommunication(WebCommunicationWrapper commWrapper) {
		setCurrentCommunicationWrapper(commWrapper);
		// setPopupVisible(true);
		// setDividerPosition(50);
	}

	public void closePopup() {
		setPopupVisible(false);
	}

	public void selectComm(ActionEvent event) {

		logger.debug(model.getRowData());
		WebCommunicationWrapper wrapper = (WebCommunicationWrapper) model
				.getRowData();
		logger.debug(wrapper.getCommunication().getSubject());

	}

	public String getViewID() {
		return viewID;
	}

	public void setViewID(String viewID) {
		this.viewID = viewID;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

}

class UserConverter implements Converter {

	private List list;

	public UserConverter(List list) {
		// TODO Auto-generated constructor stub
		this.list = list;
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		for (Object object : list) {
			SelectItem item = (SelectItem) object;
			if (arg2.equals(item.getLabel())) {
				SuddenUser user = (SuddenUser) item.getValue();
				return user;
			}

		}
		return null;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null && arg2 instanceof SuddenUser) {
			SuddenUser user = (SuddenUser) arg2;
			return user.getNickname();
		} else if (arg2 != null && arg2 instanceof String) {
			return (String) arg2;
		} else {
			return "";
		}
	}

}

class MyDataModel extends DataModel {

	private List data;
	private int totalNumRows;
	private int pageSize;
	private int rowIndex = -1;

	public MyDataModel() {
		// TODO Auto-generated constructor stub
		super();
	}

	public MyDataModel(List data, int totalNumRows, int pageSize) {
		// TODO Auto-generated constructor stub
		super();
		this.totalNumRows = totalNumRows;
		this.pageSize = pageSize;
		setWrappedData(data);
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.totalNumRows;
	}

	@Override
	public Object getRowData() {
		if (data == null)
			return null;
		else if (!isRowAvailable())
			throw new IllegalArgumentException();
		else {
			int dataIndex = getRowIndex();
			return data.get(dataIndex);
		}
	}

	@Override
	public int getRowIndex() {
		// if (pageSize != 0)
		// return rowIndex % pageSize;
		// else
		// return -1;
		return rowIndex;
	}

	@Override
	public Object getWrappedData() {
		// TODO Auto-generated method stub

		return this.data;
	}

	@Override
	public boolean isRowAvailable() {
		if (data == null)
			return false;

		int rowIndex = getRowIndex();
		if (rowIndex >= 0 && rowIndex < data.size())
			return true;
		else
			return false;
	}

	@Override
	public void setRowIndex(int arg0) {
		// TODO Auto-generated method stub
		this.rowIndex = arg0;
	}

	@Override
	public void setWrappedData(Object arg0) {
		// TODO Auto-generated method stub
		this.data = (List) arg0;
	}

}
