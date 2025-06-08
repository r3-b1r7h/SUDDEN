package biz.sudden.designCoordination.collaborativePlanning;

import java.util.Date;
import java.util.List;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.web.controller.impl.UserJSFControllerImpl;
import biz.sudden.designCoordination.collaborativePlanning.domain.ChatRoom;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.ChatManager;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.SimpleStringClass;
import biz.sudden.userInterface.gui.web.controller.UserSessionController;

import com.icesoft.faces.async.render.OnDemandRenderer;
import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;
import com.icesoft.faces.component.UIXhtmlComponent;
import com.icesoft.faces.component.ext.HtmlCommandButton;
import com.icesoft.faces.component.ext.HtmlDataTable;
import com.icesoft.faces.component.ext.HtmlForm;
import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.HtmlOutputText;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.ext.UIColumn;
import com.icesoft.faces.component.panelpopup.PanelPopup;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

/***
 * This controller is created on each request (request-scoped bean) and
 * retrieves from all injected beans (session-scoped) whether the chat list is
 * not null. If so (chatList > 0) it renders for each Chat a Icefaces Popup.
 * 
 * @author thomasfeiner
 */

public class RequestChatController implements Renderable {

	private Logger logger = Logger.getLogger(this.getClass());
	private RenderManager renderManager;
	private ChatManager chatManager;
	private OnDemandRenderer showChatPopupRenderer;

	private UserSessionController userSessionController;

	public UserSessionController getUserSessionController() {
		return userSessionController;
	}

	public void setUserSessionController(
			UserSessionController userSessionController) {
		this.userSessionController = userSessionController;
	}

	public ChatManager getChatManager() {
		return chatManager;
	}

	public void setChatManager(ChatManager chatManager) {
		logger.debug("SET CHAT manager");
		this.chatManager = chatManager;
	}

	public RenderManager getRenderManager() {
		return renderManager;
	}

	public void setRenderManager(RenderManager renderManager) {
		logger.debug("Set Request Chat Controller");
		this.renderManager = renderManager;

		showChatPopupRenderer = renderManager
				.getOnDemandRenderer("chatRenderer");
		showChatPopupRenderer.add(this);
		showChatPopupRenderer.requestRender();
		// renderManager.getOnDemandRenderer("chatRenderer");
		// state = PersistentFacesState.getInstance();
		// renderer.add(this);
		// renderer.requestRender();
		// logger.debug("PersistentFaceState " + state + " " +
		// PersistentFacesState.getInstance());
		// logger.debug("OndemandRenderer " + renderManager);
	}

	private UserJSFControllerImpl userController;

	public UserJSFControllerImpl getUserController() {
		return userController;
	}

	public void setUserController(UserJSFControllerImpl userController) {
		this.userController = userController;
		logger.debug("set user controller");
		// showPopup();
	}

	public RequestChatController() {
		// showPopup();
		// logger.debug("Constructor in " + this.getClass() + " called!");
	}

	public int getDummyNumber() {
		logger.debug("Get dummy nr in " + userController.getLoggedInUsername());
		showPopup();
		return 0;
	}

	@Override
	public PersistentFacesState getState() {
		// TODO Auto-generated method stub
		return PersistentFacesState.getInstance();
	}

	@Override
	public void renderingException(RenderingException arg0) {
		// TODO Auto-generated method stub

	}

	public void init() {
		try {
			logger.debug("init in " + userController.getLoggedInUsername());
			showPopup();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void showPopup() {

		String username = userController.getLoggedInUsername();

		logger.debug("showPopup?? for " + username);

		try {

			if (chatManager != null) {

				List<ChatRoom> chatList = chatManager.isUserinChats(username);

				if (userController != null && chatList.size() > 0) {

					UIViewRoot viewRoot = FacesContext.getCurrentInstance()
							.getViewRoot();

					int i = 0;

					for (final ChatRoom chatRoom : chatList) {

						// FacesContext.getCurrentInstance().getApplication().
						// getELResolver
						// ().setValue(FacesContext.getCurrentInstance()
						// .getELContext(), null, "chatroom", chatRoom);

						i++;

						// does the popup with this id not exist ? then create a
						// new one
						if (viewRoot.findComponent("popup" + i) == null) {

							final HtmlForm myForm = new HtmlForm();

							logger.debug("New Popup is CREATED !");
							final PanelPopup myPopup = new PanelPopup();
							myPopup.setDraggable("true");
							myPopup
									.setStyle("width:500px;height:400px;position:absolute;top:"
											+ (200 + 5 * i)
											+ "px;left:"
											+ (700 + 5 * i) + "px;");
							myPopup.setClientOnly(true);
							myPopup.setId("myPopup" + i);

							HtmlPanelGroup headerGroup = new HtmlPanelGroup();
							headerGroup.setStyleClass("popupHeaderWrapper");
							HtmlOutputText text = new HtmlOutputText();
							text.setValue("Chat");
							text.setStyle("color:black");

							// FIXME for what ever reason this button is not
							// working any more (eventually since an update of
							// icefaces libs?)
							// HtmlCommandButton closeButton =
							// (HtmlCommandButton)
							// FacesContext.getCurrentInstance().getApplication().createComponent(HtmlCommandButton.COMPONENT_TYPE);
							// closeButton.setType("submit");
							// closeButton.setValue("Close");
							// closeButton.setId("closeButton"+i);
							//							
							// closeButton.addActionListener(new
							// ActionListener() {
							// @Override
							// public void processAction(ActionEvent arg0)
							// throws AbortProcessingException {
							// // TODO Auto-generated method stub
							// myPopup.setVisible(false);
							//									
							// chatRoom.removeUser(userController.getLoggedInUser());
							// myPopup.getParent().getChildren().remove(myPopup);
							// myForm.getParent().getChildren().remove(myForm);
							// showChatPopupRenderer.requestRender();
							// //showPopup();
							// }
							// });

							headerGroup.getChildren().add(text);
							// headerGroup.getChildren().add(closeButton);

							HtmlPanelGroup bodyGroup = new HtmlPanelGroup();
							bodyGroup.setStyle("text-align:left");
							bodyGroup.setStyleClass("popupBody");

							HtmlCommandButton commandButton = (HtmlCommandButton) FacesContext
									.getCurrentInstance().getApplication()
									.createComponent(
											HtmlCommandButton.COMPONENT_TYPE);
							commandButton.setType("submit");
							commandButton.setValue("Chat");
							commandButton.setId("sendChatCommandButton" + i);

							final HtmlInputText inputText = new HtmlInputText();
							inputText.setValue("");

							// one listener for both the button and the input
							// field!
							ActionListener listen = new ActionListener() {

								@Override
								public void processAction(ActionEvent arg0)
										throws AbortProcessingException {
									// TODO Auto-generated method stub
									logger.debug("Communication: "
											+ inputText.getValue());
									Communication communication = new Communication();

									SuddenUser user = userController
											.getLoggedInUser();

									communication.setSendDate(new Date());
									communication.setSender(user);
									communication.setSubject((String) inputText
											.getValue());
									chatRoom.addCommunication(communication);
									chatManager.addCommunicationToChat(
											communication, chatRoom);
									if (inputText.getValue().toString()
											.toLowerCase().equals("exit")) {
										// manually close the chat.
										myPopup.setVisible(false);

										chatRoom.removeUser(userController
												.getLoggedInUser());
										myPopup.getParent().getChildren()
												.remove(myPopup);
										myForm.getParent().getChildren()
												.remove(myForm);
									}

									inputText.setValue("");
									showChatPopupRenderer.requestRender();
									// showPopup();
								}
							};
							commandButton.addActionListener(listen);
							inputText.addActionListener(listen);

							// commandButton.setImmediate(true);

							inputText
									.setStyle("margin-left:0px;padding-left:4px;width:100%");

							SimpleStringClass simpleString = new SimpleStringClass();
							simpleString.setValue("");

							FacesContext.getCurrentInstance().getApplication()
									.getELResolver().setValue(
											FacesContext.getCurrentInstance()
													.getELContext(), null,
											"valueWrapper", simpleString);
							ValueExpression valueExpression = FacesContext
									.getCurrentInstance().getApplication()
									.getExpressionFactory()
									.createValueExpression(
											FacesContext.getCurrentInstance()
													.getELContext(),
											"#{valueWrapper.value}",
											Object.class);

							inputText.setValueExpression("value",
									valueExpression);

							// inputText.setValue(value)

							inputText.setRequired(true);

							HtmlDataTable dataTable = new HtmlDataTable();
							dataTable.setScrollable(true);
							dataTable.setScrollHeight("250px");
							dataTable
									.setStyle("margin-left:0px;padding-left:4px;width:100%;border:1px solid black!important");

							// ValueExpression chatroomExpression =
							// FacesContext.getCurrentInstance
							// ().getApplication().getExpressionFactory
							// ().createValueExpression
							// (FacesContext.getCurrentInstance().getELContext(),
							// "#{chatroom.communications}", Object.class);

							dataTable.setValue(chatRoom.getCommunications()); // Expression
							// (
							// "value"
							// ,
							// chatroomExpression
							// )
							// ;

							// HIER

							logger.debug("Communications in chatroom "
									+ chatRoom.getCommunications().size());
							dataTable.setVar("communication");
							dataTable.setColumnClasses("tableCol");

							UIColumn column = new UIColumn();
							column.setId("chatRoomCol");
							column.setStyle("width:100%");

							HtmlOutputText outputText = new HtmlOutputText();
							outputText.setId("outputText");
							ValueExpression expression = FacesContext
									.getCurrentInstance()
									.getApplication()
									.getExpressionFactory()
									.createValueExpression(
											FacesContext.getCurrentInstance()
													.getELContext(),
											"#{communication.sender.nickname}: #{communication.subject}",
											Object.class);

							outputText.setValueExpression("value", expression);

							column.getChildren().add(outputText);

							dataTable.getChildren().add(column);

							HtmlOutputText outputTextParticipants = new HtmlOutputText();

							String participantList = "";

							for (SuddenUser user : chatRoom.getUsers()) {
								participantList += user.getNickname() + " ";
							}
							outputTextParticipants.setValue("Participants: "
									+ participantList);
							// outputTextParticipants.setValue(participantList);

							bodyGroup.getChildren().add(outputTextParticipants);
							HtmlOutputText exit = new HtmlOutputText();

							// FIXME: close button does not always work use the
							// word exit in the chat to do the same
							ExpressionFactory expressionFactory = FacesContext
									.getCurrentInstance().getApplication()
									.getExpressionFactory();
							ValueExpression fieldExpression = expressionFactory
									.createValueExpression(FacesContext
											.getCurrentInstance()
											.getELContext(), "#{msg.typeExit}",
											String.class);
							exit.setValueExpression("value", fieldExpression);

							bodyGroup.getChildren().add(exit);

							UIXhtmlComponent comp = new UIXhtmlComponent();
							comp.setTag("br");

							bodyGroup.getChildren().add(comp);

							bodyGroup.getChildren().add(inputText);

							bodyGroup.getChildren().add(commandButton);
							bodyGroup.getChildren().add(dataTable);
							//	
							//						
							// <ice:inputText id="inputtext#{thispopup.popupid}"
							// style="margin-left:0px;padding-left:4px;width:100%"
							// value="#{thispopup.message}" required="true"
							// actionListener="#{thispopup.sendMessage}" />
							// <ice:commandButton
							// value="Send"
							// actionListener="#{thispopup.sendMessage}" />
							// <ice:dataTable
							// scrollable="true" scrollHeight="140px"
							// style=
							// "margin-left:0px;padding-left:4px;width:100%;border:1px solid black!important"
							// value="#{thispopup.messages}" var="message"
							// columnClasses="tableCol">
							//
							// <ice:column style="width:100%">
							// <ice:outputText value="#{message}" />
							// </ice:column>
							// </ice:dataTable> <br />
							// </ice:panelGroup>

							myPopup.getFacets().put("header", headerGroup);

							myPopup.getFacets().put("body", bodyGroup);

							myForm.getChildren().add(myPopup);

							myForm.setId("popup" + i);

							// add this popup into the DOM tree, it is added
							// below
							// the "body" component of DOM tree
							viewRoot.getChildren().get(0).getChildren().get(2)
									.getChildren().add(myForm);

							// showChatPopupRenderer.requestRender();
						}
					}

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
