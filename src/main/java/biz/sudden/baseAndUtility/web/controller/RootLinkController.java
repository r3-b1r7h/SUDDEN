package biz.sudden.baseAndUtility.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import biz.sudden.baseAndUtility.domain.Link;
import biz.sudden.baseAndUtility.domain.PrimitiveType;
import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.baseAndUtility.service.LinkService;
import biz.sudden.baseAndUtility.service.RootLinkService;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;
import biz.sudden.baseAndUtility.web.controller.domain.ParameterNameValuePair;
import biz.sudden.baseAndUtility.web.controller.impl.PrimitiveValueController;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.domain.SuddenUserObject;
import biz.sudden.userInterface.gui.web.controller.impl.UserSessionControllerImpl;

import com.icesoft.faces.component.ext.HtmlCommandButton;
import com.icesoft.faces.component.ext.HtmlCommandLink;
import com.icesoft.faces.component.ext.HtmlDataTable;
import com.icesoft.faces.component.ext.HtmlForm;
import com.icesoft.faces.component.ext.HtmlOutputText;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.ext.HtmlSelectOneMenu;
import com.icesoft.faces.component.ext.UIColumn;
import com.icesoft.faces.component.panelpopup.PanelPopup;
import com.icesoft.faces.component.panelseries.PanelSeries;
import com.icesoft.faces.component.tree.Tree;
import com.icesoft.faces.component.tree.TreeNode;

public class RootLinkController {

	private LinkService linkService;
	private String linkentry;
	private HtmlCommandLink commandLink;
	private HtmlDataTable dataTable;
	private PanelSeries panelSeries;
	private HtmlSelectOneMenu selectOneMenu;
	private String selectedItem;
	private List<SelectItem> selectItemList = new ArrayList<SelectItem>();

	SuddenGenericRepository genericRepository;

	public SuddenGenericRepository getGenericRepository() {
		return genericRepository;
	}

	public void setGenericRepository(SuddenGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	public void changeItem(ValueChangeEvent event) throws Exception {
		logger.debug(event.getNewValue());
		logger.debug(selectedItem);
		setSelectedItem((String) event.getNewValue());
		Long newValue = null;
		if (event.getNewValue() != null) {
			newValue = new Long(((String) event.getNewValue()));
			UIParameter uiParam;
			uiParam = (UIParameter) selectOneMenu.findComponent("sourceObject");
			Object object = uiParam.getValue();
			Long id = (Long) object.getClass().getMethod("getId")
					.invoke(object);

			final List<JsfLink> jsfLinks = linkService.getLinksFor(object, id);

			for (JsfLink jsfLink2 : jsfLinks) {
				logger.debug(jsfLink2.getId() + "  " + newValue);
				if (jsfLink2.getId().equals(newValue)) {
					userSessionController.navigateTo(jsfLink2.getViewID(),
							jsfLink2.getControllerBeanName(), jsfLink2
									.getParameterValuesPairs().get(0)
									.getParameter(), jsfLink2.getDomainId());
					setSelectedItem(null);
				}
			}
		}
	}

	public String getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}

	public HtmlSelectOneMenu getSelectOneMenu() {
		return selectOneMenu;
	}

	public void setSelectOneMenu(HtmlSelectOneMenu selectOneMenu) {
		this.selectOneMenu = selectOneMenu;
	}

	public PanelSeries getPanelSeries() {
		return panelSeries;
	}

	public void setPanelSeries(PanelSeries panelSeries) {
		this.panelSeries = panelSeries;
	}

	public HtmlDataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}

	public HtmlCommandLink getCommandLink() {
		return commandLink;
	}

	public void setCommandLink(HtmlCommandLink commandLink) {
		this.commandLink = commandLink;
	}

	// public void navigateTo(ActionEvent event) throws Exception {
	//
	// // UIParameter uiParam;
	// // uiParam = (UIParameter) selectOneMenu.findComponent("test");
	// // Object object = uiParam.getValue();
	// //
	// // Long id = (Long) object.getClass().getMethod("getId").invoke(object);
	// // final JsfLink jsfLink = linkService.getLinkFor(object, id);
	// // logger.debug("LinkService getLinkFor "+object+" "+id);
	// // logger.debug(jsfLink.getParameterValuesPairs().size());
	// // userSessionController.navigateTo(jsfLink.getViewID(),
	// // Class.forName(jsfLink.getController()),
	// // jsfLink.getParameterValuesPairs().get(0).getParameter(),
	// // jsfLink.getDomainId());
	// }

	public List<PrimitiveType> getPrimitiveTypeValues() throws Exception {
		try {
			List<PrimitiveType> primitiveList = new ArrayList<PrimitiveType>();

			UIParameter uiParam;
			uiParam = (UIParameter) selectOneMenu.findComponent("sourceObject");
			Object object = uiParam.getValue();
			Long id = (Long) object.getClass().getMethod("getId")
					.invoke(object);
			final List<JsfLink> jsfLinks = linkService.getLinksFor(object, id);
			for (JsfLink jsfLink : jsfLinks) {

				// Class controllerClass =
				// Class.forName(jsfLink.getController());
				HttpSession session = (HttpSession) FacesContext
						.getCurrentInstance().getExternalContext().getSession(
								true);
				//	
				WebApplicationContext webAppContext = WebApplicationContextUtils
						.getRequiredWebApplicationContext(session
								.getServletContext());
				//			
				Object controller = webAppContext.getBean(jsfLink
						.getControllerBeanName());
				//			
				// if (beanNames.length == 0 || beanNames.length > 1)
				// return null;
				//
				// String beanName = beanNames[0];
				//
				//
				// Object controller = null;
				//			
				// if (session.getAttribute(beanName) != null) {
				// controller = session.getAttribute(beanName);
				// } else {
				// controller = webAppContext.getBean(beanName);
				// session.setAttribute(beanName, controller);
				// }

				if (controller instanceof PrimitiveValueController) {
					PrimitiveValueController primitiveController = (PrimitiveValueController) controller;
					PrimitiveType type = primitiveController
							.getPrimitiveValueService().get(
									new Long(jsfLink.getDomainId()));
					primitiveList.add(type);
				}

			}
			return primitiveList;
		} catch (Exception ex) {
			return new ArrayList<PrimitiveType>();
		}
	}

	public List<SelectItem> getLinkentries() throws InvocationTargetException,
			IllegalAccessException, NoSuchMethodException {
		Object object = null;
		UIParameter uiParam = null;
		try {

			selectItemList = new ArrayList<SelectItem>();

			uiParam = (UIParameter) selectOneMenu.findComponent("sourceObject");

			object = uiParam.getValue();

			Long id = (Long) object.getClass().getMethod("getId")
					.invoke(object);

			final List<JsfLink> jsfLinks = linkService.getLinksFor(object, id);

			SelectItem item2 = new SelectItem();
			item2.setLabel("");
			item2.setValue("");
			item2.setDescription("");
			selectItemList.add(item2);

			for (JsfLink jsfLink : jsfLinks) {
				if (jsfLink != null
						&& !jsfLink.getDomainClass().contains("PrimitiveType")) {
					SelectItem item = new SelectItem();
					if (jsfLink.getText() != null)
						item.setLabel(jsfLink.getText());
					else
						item.setLabel(jsfLink.getDescription());
					item.setValue(jsfLink.getId());
					item.setDescription(jsfLink.getDescription());
					selectItemList.add(item);
				}
			}
			if (selectItemList.size() > 0 && selectedItem == null)
				setSelectedItem(selectItemList.get(0).getLabel());

			return selectItemList;
		} catch (NullPointerException ex) {
			logger.debug("NPE raised " + ex);
			// ex.printStackTrace();
			logger.debug(object);
			logger.debug(uiParam);
		} catch (NoSuchMethodException nsme) {
			logger.debug("NoSuchMethodException raised:" + nsme);
			logger.debug(nsme.getLocalizedMessage());
			logger.debug(object);
			logger.debug(uiParam);
		}
		return null;
	}

	public String getLinkentry() {
		return linkentry;
	}

	public void setLinkentry(String linkentry) {
		this.linkentry = linkentry;
	}

	public LinkService getLinkService() {
		return linkService;
	}

	public void setLinkService(LinkService linkService) {
		this.linkService = linkService;
	}

	private Object fromObject;
	private Object fromId;

	public RootLinkController() {
		// TODO Auto-generated constructor stub
		// System.gc();
	}

	private Logger logger = Logger.getLogger(this.getClass());

	private UserSessionControllerImpl userSessionController;

	public UserSessionControllerImpl getUserSessionController() {
		return userSessionController;
	}

	public void setUserSessionController(
			UserSessionControllerImpl userSessionController) {
		this.userSessionController = userSessionController;
	}

	private RootLinkService rootLinkService;

	public RootLinkService getRootLinkService() {
		return rootLinkService;
	}

	public void setRootLinkService(RootLinkService rootLinkService) {
		this.rootLinkService = rootLinkService;
	}

	public void showLinkableTypesPopup(ActionEvent event) {

		List<JsfLink> linkableTypesAsString = rootLinkService
				.getAllLinkableTypes();

		HtmlForm myForm = createPopupinForm(linkableTypesAsString);
		myForm.setId("rootLinkableForm");
		// add this popup into the DOM tree, it is added
		// below
		// the "body" component of DOM tree
		FacesContext.getCurrentInstance().getViewRoot().getChildren().get(0)
				.getChildren().get(2).getChildren().add(myForm);

	}

	public void link(ActionEvent event) throws Exception {

		UIParameter uiParam = (UIParameter) event.getComponent().findComponent(
				"link");
		uiParam.getValue();
		logger.debug("Link source ist " + uiParam.getValue());
		Object id = uiParam.getValue().getClass().getMethod("getId").invoke(
				uiParam.getValue());
		logger.debug("The ID is " + id);

		fromObject = uiParam.getValue();
		fromId = id;
		showLinkableTypesPopup(event);

	}

	public void linkTogether(Object object, Long id, JsfLink jsfToLink)
			throws Exception {

		jsfToLink.setId(null);
		List<ParameterNameValuePair> pairs = new ArrayList<ParameterNameValuePair>();

		for (ParameterNameValuePair pair : jsfToLink.getParameterValuesPairs()) {
			String parameter = pair.getParameter();
			String parameterValue = pair.getParameterValue();
			ParameterNameValuePair newPair = new ParameterNameValuePair();
			newPair.setParameter(parameter);
			newPair.setParameterValue(parameterValue);
			pairs.add(newPair);
		}

		jsfToLink.setParameterValuesPairs(pairs);

		logger.debug("now everything will be connected");
		logger.debug("From " + fromObject + " " + fromId);
		logger.debug("To " + object + " " + id);

		jsfToLink.setText(jsfToLink.getDescription() + "->"
				+ getHumanReadable(object));

		Link link = new Link();
		link.setFromClass(fromObject.getClass().getName());
		link.setFromID(new Long(fromId.toString()));
		link.setToJsfLink(jsfToLink);

		linkService.createLink(link);

	}

	// try several methods to call for some human readable string..
	public static String getHumanReadable(Object object) {
		Method m = null;
		try {
			m = object.getClass().getMethod("getName", (Class[]) null);
		} catch (Exception e) {
			try {
				m = object.getClass().getMethod("getSubject", (Class[]) null);
			} catch (Exception ee) {
				try {
					m = object.getClass().getMethod("getInformation",
							(Class[]) null);
				} catch (Exception eee) {
					try {
						m = object.getClass().getMethod("getData",
								(Class[]) null);
					} catch (Exception eeee) {
						try {
							m = object.getClass().getMethod("getText",
									(Class[]) null);
						} catch (Exception eeeee) {
							try {
								m = object.getClass().getMethod(
										"getDescription", (Class[]) null);
							} catch (Exception eeeeee) {
							}
						}
					}
				}
			}
		}
		String result = null;
		if (m != null) {
			try {
				result = m.invoke(object, (Object[]) null).toString();
			} catch (Exception e) {
				result = object.toString();
			}
		}
		if (result != null) {
			return result.substring(0, result.length() > 25 ? 25 : result
					.length());
		}
		return result;
	}

	public HtmlForm createPopupinForm(List<JsfLink> availableLinkTypes) {

		final HttpSession httpSession = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext().getSession(true);

		// Form
		final HtmlForm myForm = new HtmlForm();

		// Popup
		final PanelPopup myPopup = new PanelPopup();
		myPopup.setDraggable("true");
		myPopup.setStyle("width:500px;height:400px;position:absolute;top:"
				+ (200) + "px;left:" + (700) + "px;");
		myPopup.setClientOnly(true);
		myPopup.setId("myPopup");

		// Header declaration
		HtmlPanelGroup headerGroup = new HtmlPanelGroup();
		headerGroup.setStyleClass("popupHeaderWrapper");
		HtmlOutputText text = new HtmlOutputText();
		text.setValue("Available Link-Types");
		text.setStyle("color:black");

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

		// Add components to header
		headerGroup.getChildren().add(text);
		headerGroup.getChildren().add(closeButton);

		// Body declaration
		HtmlPanelGroup bodyGroup = new HtmlPanelGroup();
		bodyGroup.setStyle("text-align:left");
		bodyGroup.setStyleClass("popupBody");

		// Data table
		HtmlDataTable dataTable = new HtmlDataTable();
		dataTable.setScrollable(true);
		dataTable.setScrollHeight("140px");
		dataTable
				.setStyle("margin-left:0px;padding-left:4px;width:100%;border:1px solid black!important");
		dataTable.setValue(availableLinkTypes);
		dataTable.setVar("linktype");
		dataTable.setColumnClasses("tableCol");

		// Data table column definitions
		UIColumn column = new UIColumn();
		column.setId("linkTypesCol");
		column.setStyle("width:100%");

		// Text in column

		HtmlCommandLink link = new HtmlCommandLink();

		UIParameter parameter = new UIParameter();
		parameter.setId("linkTypeParameter");
		parameter.setName("linkTypeParameter");

		ValueExpression expression1 = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{linktype}", Object.class);

		parameter.setValueExpression("value", expression1);

		link.getChildren().add(parameter);
		link.setId("link");

		ValueExpression expression = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{linktype.description}", Object.class);

		link.setValueExpression("value", expression);

		final RootLinkController self = this;

		link.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent event)
					throws AbortProcessingException {
				UIParameter component = (UIParameter) event.getComponent()
						.findComponent("linkTypeParameter");
				JsfLink jsfLink = (JsfLink) component.getValue();

				try {

					String controllerBeanName = jsfLink.getControllerBeanName();

					WebApplicationContext webAppContext = WebApplicationContextUtils
							.getRequiredWebApplicationContext(httpSession
									.getServletContext());

					Object controllerBean = webAppContext
							.getBean(controllerBeanName);

					// String beanName = beanNames[0];
					//
					// Object controller = null;
					//
					// if (httpSession.getAttribute(controllerBeanName) != null)
					// {
					// controller =
					// httpSession.getAttribute(controllerBeanName);
					// } else {
					// controller = webAppContext.getBean(beanName);
					// httpSession.setAttribute(controllerBeanName, controller);
					// }

					Object returnValue = null;

					try {
						returnValue = controllerBean.getClass().getMethod(
								"getLinkableTypeSelectionPopup", JsfLink.class,
								RootLinkController.class).invoke(
								controllerBean, jsfLink, self);
					} catch (NoSuchMethodException ex) {
						returnValue = self.getLinkableTypeSelectionPopup(
								jsfLink, self);
					}
					// myForm.getChildren().add((PanelPopup)returnValue);
					//					
					HtmlForm newForm = new HtmlForm();
					newForm.setId("getLinkableObjectsForm");
					myForm.getParent().getChildren().add(newForm);
					newForm.getChildren().add((PanelPopup) returnValue);
					myPopup.setVisible(false);
					myPopup.getParent().getChildren().remove(myPopup);
				} catch (InvocationTargetException invTargEx) {
					logger.error("Invocation Target Exception " + invTargEx);
					invTargEx.printStackTrace();
				} catch (IllegalAccessException illAcc) {
					logger.error("Illegal Access Exception " + illAcc);
				}
			}
		});

		// add Text to Column
		column.getChildren().add(link);

		// add datatable to column
		dataTable.getChildren().add(column);

		// add datatable to body
		bodyGroup.getChildren().add(dataTable);

		// add header and body components to popup
		myPopup.getFacets().put("header", headerGroup);
		myPopup.getFacets().put("body", bodyGroup);

		// add popup to surrounding form
		myForm.getChildren().add(myPopup);

		return myForm;

	}

	public PanelPopup getLinkableTypeSelectionPopup(final JsfLink jsfLink,
			RootLinkController rootLinkController) {

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

		DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
		SuddenUserObject<Communication> rootObject = new SuddenUserObject<Communication>(
				rootTreeNode);
		rootObject.setExpanded(true);
		rootTreeNode.setUserObject(rootObject);
		rootObject.setText("Root Node");

		try {
			addNode(rootTreeNode, genericRepository.retrieveAllByType(Class
					.forName(jsfLink.getDomainClass())), true);
		} catch (ClassNotFoundException ex) {

		}
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

	protected void addNode(DefaultMutableTreeNode parent,
			List domainObjectsList, boolean isBulletinBoardList) {

		for (Object domainObject : domainObjectsList) {
			DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
			SuddenUserObject branchObject = new SuddenUserObject(branchNode);
			String name = domainObject.toString();
			branchObject.setText(name);
			branchObject.setAttachedObject(domainObject);
			branchNode.setUserObject(branchObject);
			branchObject.setLeaf(true);
			parent.add(branchNode);
		}
	}

}
