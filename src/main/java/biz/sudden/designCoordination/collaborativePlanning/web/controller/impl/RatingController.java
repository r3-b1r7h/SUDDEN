package biz.sudden.designCoordination.collaborativePlanning.web.controller.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.DataModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.web.controller.RootLinkController;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;
import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoardRating;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.domain.CommunicationRating;
import biz.sudden.designCoordination.collaborativePlanning.domain.TopicRating;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.domain.SuddenUserObject;

import com.icesoft.faces.component.ext.HtmlCommandButton;
import com.icesoft.faces.component.ext.HtmlCommandLink;
import com.icesoft.faces.component.ext.HtmlInputTextarea;
import com.icesoft.faces.component.ext.HtmlOutputText;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.panelpopup.PanelPopup;
import com.icesoft.faces.component.tree.Tree;
import com.icesoft.faces.component.tree.TreeNode;

import edu.emory.mathcs.backport.java.util.Collections;

public class RatingController extends CpController {

	private HtmlInputTextarea textarea;
	private boolean inputText = false;
	private boolean editContribution = false;
	private String contributionText = "";
	private int rating = 1;
	Logger logger = Logger.getLogger(this.getClass());

	public int getRating() {

		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getContributionText() {
		return contributionText;
	}

	public void setContributionText(String contributionText) {
		this.contributionText = contributionText;
	}

	public boolean isEditContribution() {
		return editContribution;
	}

	public void setEditContribution(boolean editContribution) {
		this.editContribution = editContribution;
	}

	private List<CommunicationRating> contributions;

	public List<CommunicationRating> getContributions() {

		List ratingsReverse = new ArrayList();
		List ratings = cpBulletinBoardService
				.getAllCommunications(getSelectedTopic());
		if (ratings != null) {
			for (Object communicationRating : ratings) {
				ratingsReverse.add(communicationRating);
			}
			Collections.reverse(ratingsReverse);
		}
		return ratingsReverse;
	}

	public void setContributions(List<CommunicationRating> contributions) {
		this.contributions = contributions;
	}

	public boolean isInputText() {
		return inputText;
	}

	public void setInputText(boolean isInputText) {
		this.inputText = isInputText;
	}

	public HtmlInputTextarea getTextarea() {
		return textarea;
	}

	public void setTextarea(HtmlInputTextarea textarea) {
		this.textarea = textarea;
	}

	@Override
	public DataModel getBulletinBoardsModel() throws Exception {

		DataModel dataModel = super.getBulletinBoardsModel();
		List bulletinBoardList = cpBulletinBoardService
				.retrieveAllBulletinBoardsOfType(BulletinBoardRating.class);
		bulletinModel = new MyDataModel(bulletinBoardList, bulletinBoardList
				.size(), 10);
		// cpBulletinBoardService.createContributionAndAddToTopic(topic,
		// contribution)
		return bulletinModel;
	}

	@Override
	public void createNewBoard() {
		BulletinBoardRating board = new BulletinBoardRating();
		board.setName(this.boardName);
		board.setDateCreated(new Date());
		board.setOwner(getCurrentUser());
		cpBulletinBoardService.createBulletin(board);
		setBoardName("");
		// return null;
	}

	@Override
	public void createNewTopic() {
		TopicRating topic = new TopicRating();
		// selectedBoard
		topic.setName(this.topicName);
		topic.setDateCreated(new Date());
		topic.setOwner(getCurrentUser());
		cpBulletinBoardService.createTopicAndAddToBulletinBoard(selectedBoard,
				topic);
		setTopicName("");
		// return null;
	}

	@Override
	public String openBoard() {
		// TODO Auto-generated method stub
		super.openBoard();
		return "showtopicsrating";
	}

	@Override
	public String openTopic() {
		// TODO Auto-generated method stub
		super.openTopic();
		return "showcontributionsratings";
	}

	@Override
	public TreeModel getContributionModel() throws Exception {
		// TODO Auto-generated method stub
		return super.getContributionModel();
	}

	public String descriptionValueChanged() {

		inputText = !inputText;

		super.getCpBulletinBoardService().updateTopic(getSelectedTopic());
		return null;
	}

	public String contributionValueChanged() {

		editContribution = !editContribution;

		super.getCpBulletinBoardService().updateTopic(getSelectedTopic());
		return null;
	}

	public String saveContribution() {

		editContribution = !editContribution;

		CommunicationRating rating = new CommunicationRating();
		rating.setMessage(contributionText);
		rating.setSender(getCurrentUser());
		rating.setSendDate(new Date());
		rating.setRating(this.rating);
		this.rating = 1;
		contributionText = "";

		// selectedTopic.getCommunications().add(rating);
		super.getCpBulletinBoardService().updateTopic(getSelectedTopic());
		cpBulletinBoardService.createContributionAndAddToTopic(
				getSelectedTopic(), rating);
		super.getCpBulletinBoardService().updateTopic(getSelectedTopic());

		return null;
	}

	public void set1Star(ActionEvent event) {
		this.rating = 1;
	}

	public void set2Stars(ActionEvent event) {
		this.rating = 2;
	}

	public void set3Stars(ActionEvent event) {
		this.rating = 3;
	}

	public void set4Stars(ActionEvent event) {
		this.rating = 4;
	}

	public void set5Stars(ActionEvent event) {
		this.rating = 5;
	}

	public int getCalculateAverage() {
		return 0;
	}

	@Override
	public PanelPopup getLinkableTypeSelectionPopup(final JsfLink jsfLink,
			RootLinkController rootLinkController) {
		final PanelPopup myPopup = new PanelPopup();

		myPopup.setDraggable("true");
		myPopup.setStyle("width:400px;height:300px;position:absolute;top:"
				+ (150) + "px;left:" + (500) + "px;");
		myPopup.setClientOnly(true);
		myPopup.setId("myPopup");

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
				myPopup.setVisible(false);
				myPopup.getParent().getChildren().remove(myPopup);
			}
		});

		HtmlPanelGroup headerGroup = new HtmlPanelGroup();
		headerGroup.getChildren().add(closeButton);

		myPopup.getFacets().put("header", headerGroup);

		DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
		SuddenUserObject<Communication> rootObject = new SuddenUserObject<Communication>(
				rootTreeNode);
		rootObject.setExpanded(true);
		rootTreeNode.setUserObject(rootObject);
		rootObject.setText("Root Node");

		addNode(rootTreeNode, cpBulletinBoardService
				.retrieveAllBulletinBoardsOfType(BulletinBoardRating.class),
				true);

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

		myPopup.getFacets().put("body", bodyGroup);

		return myPopup;

	}

}
