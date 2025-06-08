package biz.sudden.knowledgeData.kdm.web.controller.impl;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.collection.AbstractPersistentCollection;
import org.hibernate.proxy.HibernateProxy;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.SearchedAssoc;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.knowledgeData.kdm.service.KdmService;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;

import com.hp.hpl.jena.ontology.OntModel;
import com.icesoft.faces.component.ext.HtmlCommandButton;
import com.icesoft.faces.component.ext.HtmlCommandLink;
import com.icesoft.faces.component.ext.HtmlForm;
import com.icesoft.faces.component.ext.HtmlGraphicImage;
import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.HtmlMessage;
import com.icesoft.faces.component.ext.HtmlOutputText;
import com.icesoft.faces.component.ext.HtmlPanelGrid;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.ext.HtmlSelectOneMenu;
import com.icesoft.faces.component.panelpopup.PanelPopup;
import com.icesoft.faces.component.paneltabset.PanelTab;
import com.icesoft.faces.component.paneltabset.PanelTabSet;
import com.icesoft.faces.component.selectinputdate.SelectInputDate;
import com.icesoft.faces.component.tree.IceUserObject;
import com.icesoft.faces.component.tree.TreeNode;

public class CaseFileControllerHelper {

	private static ExpressionFactory expressionFactory = FacesContext
			.getCurrentInstance().getApplication().getExpressionFactory();

	protected static ELContext getELContext() {
		return FacesContext.getCurrentInstance().getELContext();
	}

	// Helper methods BEGIN

	protected static Object getValueOfExpression(ValueExpression valueExpression) {
		return valueExpression.getValue(getELContext());
	}

	// protected static HtmlInputText createInputText(UIComponent parent,
	// ValueExpression valueExpression, String fieldName, String
	// htmlInputTextId, boolean immediate, boolean partial, boolean disabled) {
	//
	// HtmlInputText component = new HtmlInputText();
	//
	// if (fieldName != null && fieldName != "") {
	// String newValueExpressionString =
	// valueExpression.getExpressionString().substring(0,
	// valueExpression.getExpressionString().length() - 1) + "." + fieldName +
	// "}";
	// ValueExpression newValueExpression =
	// expressionFactory.createValueExpression(getELContext(),
	// newValueExpressionString, Object.class);
	// if (getValueOfExpression(valueExpression) != null) {
	// component.setValueExpression("value", newValueExpression);
	// }
	// } else {
	// component.setValueExpression("value", valueExpression);
	// }
	//
	// component.setImmediate(immediate);
	// component.setPartialSubmit(partial);
	// component.setDisabled(disabled);
	// component.setId(htmlInputTextId);
	// if (parent != null) {
	// parent.getChildren().add(component);
	// }
	// return component;
	//
	// }

	protected static HtmlInputText createInputText(UIComponent parent,
			String htmlInputTextId, ValueExpression valueExpression,
			boolean disabled) {

		HtmlInputText component = new HtmlInputText();
		component.setValueExpression("value", valueExpression);
		// for immediate update so also the others concurrently working on this
		// case will see the values
		component.setImmediate(true);
		component.setPartialSubmit(true);
		component.setDisabled(disabled);
		component.setId(htmlInputTextId);
		if (parent != null) {
			parent.getChildren().add(component);
		}
		return component;
	}

	// protected static HtmlInputText createInputText(UIComponent parent, String
	// entityString, String fieldNameString, String htmlInputTextId, boolean
	// immediate, boolean partial, boolean disabled, boolean
	// checkForNullOrEmpty) {
	//
	// ValueExpression valueExpression = createValueExpression(entityString,
	// fieldNameString, Object.class, checkForNullOrEmpty);
	// return createInputText(parent, valueExpression, htmlInputTextId,
	// immediate, partial, disabled);
	// }

	protected static ValueExpression createValueExpression(String entityString,
			String fieldNameString, Class clazz, boolean checkForNullOrEmpty) {

		if (checkForNullOrEmpty) {
			return expressionFactory
					.createValueExpression(getELContext(), "#{empty "
							+ entityString + " ? " + entityString + " : "
							+ entityString + "." + fieldNameString + "}", clazz);
		} else {
			return expressionFactory.createValueExpression(getELContext(), "#{"
					+ entityString + "." + fieldNameString + "}", clazz);
		}

	}

	protected static ValueExpression createValueExpression(String entityString,
			Class clazz) {
		return expressionFactory.createValueExpression(getELContext(), "#{"
				+ entityString + '}', clazz);
	}

	/**
	 * 
	 * @param parent
	 * @param label
	 * @param escape
	 * @return
	 */
	protected static HtmlOutputText createOutputText(UIComponent parent,
			String label, boolean escape, boolean i18n) {
		HtmlOutputText outputText = new HtmlOutputText();
		if (i18n) {
			ValueExpression fieldExpression = expressionFactory
					.createValueExpression(FacesContext.getCurrentInstance()
							.getELContext(), "#{msg." + label + '}',
							String.class);
			try {
				String txt = (String) fieldExpression.getValue(FacesContext
						.getCurrentInstance().getELContext());
				if (txt.contains("???")) {
					outputText.setValue(label);
				} else {
					outputText.setValueExpression("value", fieldExpression);
				}
			} catch (NumberFormatException ex) { // this happens when label
													// contains "-", EL treats
													// this as a minus
													// unfortunately
				outputText.setValue(label);
			}
		} else {
			outputText.setValue(label);
		}

		// Object txt =
		// fieldExpression.getValue(FacesContext.getCurrentInstance().getELContext());
		// String text = txt.toString();
		// if(text.startsWith("???"))
		// outputText.setValue(text.substring(3, text.length()-3));
		// else
		// outputText.setValue(txt.toString());

		outputText.setEscape(escape);
		if (parent != null) {
			parent.getChildren().add(outputText);
		}
		return outputText;
	}

	protected static void createEmptyOutputText(UIComponent grid) {
		HtmlOutputText txt = new HtmlOutputText();
		if (grid != null) {
			grid.getChildren().add(txt);
		}
	}

	protected static boolean hasJavaType(Field field) {
		return field.getType().getName().startsWith("java");
	}

	protected static boolean hasSuddenType(Field field) {
		return field.getType().getName().startsWith("biz.sudden");
	}

	protected static boolean hasCollectionType(Field field) {
		return Collection.class.isAssignableFrom(field.getType());
	}

	protected static boolean hasPrimitiveType(Field field) {
		return field.getType().isPrimitive();
	}

	protected static void clearPanelTabSet(PanelTabSet pnlTabSet) {
		pnlTabSet.getChildren().clear();
	}

	protected static PanelTab addTabToPanelTabSet(PanelTabSet pnlTabSet,
			String panelLabel) {
		PanelTab tab = new PanelTab();
		// tab.setLabel(panelLabel);
		// not working
		ValueExpression fieldExpression = expressionFactory
				.createValueExpression(FacesContext.getCurrentInstance()
						.getELContext(), "#{msg." + panelLabel + '}',
						String.class);

		tab.setValueExpression("label", fieldExpression);
		// tab.setLabel(label.toString());
		pnlTabSet.getChildren().add(tab);
		return tab;
	}

	public static Object transformHibernateLazyObject(Object object) {

		Hibernate.isInitialized(object);

		Object localObject = object;

		if (object instanceof HibernateProxy) {
			localObject = ((HibernateProxy) object)
					.getHibernateLazyInitializer().getImplementation();
		} else if (object instanceof AbstractPersistentCollection) {
			// /FIXME when session abgelaufen???
			// logger.debug("initializing AbstracPersistentCollection" +
			// object.getClass());
			// logger.debug("initializing AbstracPersistentCollection" +
			// ((AbstractPersistentCollection) object).getValue());
			((AbstractPersistentCollection) object).forceInitialization();
			localObject = ((AbstractPersistentCollection) object)
					.getStoredSnapshot();
		}

		return localObject;

		// this code is never reached! PersistentBag IS A
		// AbstractPersistentCollection !!
		// } else if (object instanceof PersistentBag) {
		// ((org.hibernate.collection.PersistentBag)
		// object).forceInitialization();
		// object = ((org.hibernate.collection.PersistentBag)
		// object).getStoredSnapshot();
		// clazz = object.getClass();
		// }
	}

	protected static HtmlPanelGrid createPanelGrid(UIPanel parentPanel,
			int nrOfColumns) {
		final HtmlPanelGrid grid = new HtmlPanelGrid();
		grid.setColumns(nrOfColumns);
		parentPanel.getChildren().add(grid);
		return grid;
	}

	// private Method getMethod(Class clazz, final Field field) {
	// String fieldName = field.getName();
	//
	// final String fieldNameFirstUpper = fieldName.substring(0,
	// 1).toUpperCase() + fieldName.substring(1);
	// Method currentMethod = null;
	// try {
	// currentMethod = clazz.getMethod("get" + fieldNameFirstUpper);
	// logger.debug(field.getType().getName());
	// } catch (NoSuchMethodException ex) {
	// logger.error("Ignore Field because no such method: " +
	// ex.getLocalizedMessage());
	// }
	// return currentMethod;
	// }

	/***
	 * retrieve the value of a given object's field
	 */

	protected static Object getFieldValue(Object object, final Field field) {

		try {
			// To make private fields readable, you have to
			// setAccesible=true
			field.setAccessible(true);
			Object fieldValue = field.get(object);
			// Set accessible back to false for security reasons
			field.setAccessible(false);
			return fieldValue;
		} catch (IllegalAccessException ex) {
			// FIXME TF: bad bad bad!
			ex.printStackTrace();
			return null;
		}

	}

	protected static HtmlSelectOneMenu addToSelectOneMenu(
			HtmlSelectOneMenu selectOneMenu, List elementsAsList,
			String entryLabel) {

		selectOneMenu.getChildren().clear();

		UISelectItems items = new UISelectItems();
		selectOneMenu.getChildren().add(items);

		List<SelectItem> selectItems = new LinkedList<SelectItem>();

		selectOneMenu.setConverter(new UserConverter(selectItems));

		int i = 0;
		for (Object object : elementsAsList) {
			i++;
			SelectItem item = new SelectItem();
			item.setValue(object);
			item.setLabel(entryLabel + " " + i);
			selectItems.add(item);
		}
		if (elementsAsList.size() > 0)
			selectOneMenu.setValue(elementsAsList.get(0));

		items.setValue(selectItems);
		return selectOneMenu;

	}

	protected static void showRequiredProducts(HtmlPanelGrid simplePartsGrid,
			List<AbstractSupplyNetwork> asnList, AbstractSupplyNetwork asn,
			OntModel ontology) {

		simplePartsGrid.getChildren().clear();

		List<ASNRoleNode> asnRoleNodes = new LinkedList();
		AbstractSupplyNetwork network = asn;

		if (asn == null && asnList != null && asnList.size() > 0) {
			asnRoleNodes = asnList.get(0).getAllRoleNodes();
			network = asnList.get(0);
		} else if (asn != null) {
			asnRoleNodes = asn.getAllRoleNodes();
		} else {
			System.err
					.println(" CaseFileControllerHelper: showRequiredProducts: unable to find required products");
		}
		getRequiredProductsGrid(simplePartsGrid, network, asnRoleNodes,
				ontology);
	}

	private static void getRequiredProductsGrid(HtmlPanelGrid simplePartsGrid,
			AbstractSupplyNetwork network, List<ASNRoleNode> asnRoleNodes,
			OntModel ontology) {
		simplePartsGrid.setColumns(1);
		simplePartsGrid.setStyle("border:1px dotted grey");
		simplePartsGrid.setId("ASNRoles" + asnRoleNodes.hashCode());
		/*
		 * for (ASNRoleNode roleNode : asnRoleNodes) { //
		 * logger.debug(" Node requires product " + //
		 * roleNode.getQualificationProfile().getProductIndividualURI()); String
		 * localName =
		 * ontology.getIndividual(roleNode.getQualificationProfile().
		 * getProductIndividualURI()).getOntClass().getLocalName(); String
		 * individual =
		 * ontology.getIndividual(roleNode.getQualificationProfile()
		 * .getProductIndividualURI()).getLocalName();
		 * createOutputText(simplePartsGrid, individual + " (" + localName +
		 * ")", false, false); }
		 */
		if (network != null) {
			createTree(simplePartsGrid, network, ontology);
		}
	}

	private static void createTree(HtmlPanelGrid simplePartsGrid,
			AbstractSupplyNetwork network, OntModel ontology) {
		com.icesoft.faces.component.tree.Tree tree = new com.icesoft.faces.component.tree.Tree();
		tree.setId("asnTree");
		ValueExpression expression = expressionFactory.createValueExpression(
				FacesContext.getCurrentInstance().getELContext(),
				"#{ASNtreeModel}", TreeModel.class);
		expression.setValue(FacesContext.getCurrentInstance().getELContext(),
				getTreeModel(network, ontology));
		tree.setValueExpression("value", expression);
		tree.setVar("item");

		TreeNode treeNode = new TreeNode();
		// Build the icon facet
		HtmlPanelGroup iconGroup = new HtmlPanelGroup();
		iconGroup.setStyle("display: inline");
		HtmlGraphicImage iconGraphic = new HtmlGraphicImage();
		expression = expressionFactory.createValueExpression(FacesContext
				.getCurrentInstance().getELContext(),
				"#{item.userObject.icon}", String.class);
		iconGraphic.setValueExpression("value", expression);
		iconGroup.getChildren().add(iconGraphic);
		treeNode.getFacets().put(TreeNode.FACET_ICON, iconGroup);

		// Create the content facet
		HtmlPanelGroup contentGroup = new HtmlPanelGroup();
		contentGroup.setStyle("display: inline");
		HtmlOutputText nodeText = new HtmlOutputText();
		expression = expressionFactory.createValueExpression(FacesContext
				.getCurrentInstance().getELContext(),
				"#{item.userObject.text}", String.class);
		nodeText.setValueExpression("value", expression);
		contentGroup.getChildren().add(nodeText);
		treeNode.getFacets().put(TreeNode.FACET_CONTENT, contentGroup);

		// Add the tree node to the tree
		// treeNode.setTree(tree);
		tree.getChildren().add(treeNode);

		// Add the tree to the parent component
		simplePartsGrid.getChildren().add(tree);
	}

	/**
	 * Builds the tree model. It first checks that the tree model has changed
	 * (new nodes are in the network or the nodes have been replaced) If the ASN
	 * changed completely, then the tree model is rebuild, if not, the model
	 * updates its transient properties. Transient properties:
	 * dropDownSelectedElement
	 * 
	 * @return
	 */
	private static DefaultTreeModel getTreeModel(AbstractSupplyNetwork network,
			OntModel ontology) {
		ASNRoleNode node = network.getFinalNode();
		DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
		IceUserObject rootObject = new IceUserObject(rootTreeNode);
		String localName = ontology.getIndividual(
				node.getQualificationProfile().getProductIndividualURI())
				.getOntClass().getLocalName();
		String individual = ontology.getIndividual(
				node.getQualificationProfile().getProductIndividualURI())
				.getLocalName();
		rootObject.setText(individual + " (" + localName + ")");
		rootObject.setExpanded(true);
		rootObject.setBranchContractedIcon("/images/shape29.gif");
		rootObject.setBranchExpandedIcon("/images/shape29.gif");
		rootObject.setLeafIcon("/images/shape29.gif");
		rootTreeNode.setUserObject(rootObject);
		buildSubTree(rootTreeNode, node, network, ontology);

		return new DefaultTreeModel(rootTreeNode);
	}

	private static void buildSubTree(DefaultMutableTreeNode treeNode,
			ASNRoleNode networkNode, AbstractSupplyNetwork network,
			OntModel ontology) {
		List<ASNMaterialDependency> dependencies = network
				.getMaterialDependenciesInto(networkNode);
		if (dependencies.size() == 0) {
			((IceUserObject) treeNode.getUserObject()).setLeaf(true);
		}
		for (ASNMaterialDependency dependency : dependencies) {
			// Add the node for the dependency
			DefaultMutableTreeNode dependencyNode = new DefaultMutableTreeNode();
			IceUserObject dependencyObject = new IceUserObject(dependencyNode);
			dependencyObject.setText(dependency.getdependencytype());
			dependencyObject.setExpanded(true);
			dependencyObject.setBranchContractedIcon("/images/arrow_40.gif");
			dependencyObject.setBranchExpandedIcon("/images/arrow_40.gif");
			dependencyObject.setLeafIcon("/images/arrow_40.gif");
			dependencyNode.setUserObject(dependencyObject);
			treeNode.add(dependencyNode);

			// Add the node for the source of the dependency
			ASNRoleNode networkSourceNode = (ASNRoleNode) dependency
					.getSourceNode();
			DefaultMutableTreeNode sourceDependencyNode = new DefaultMutableTreeNode();
			IceUserObject sourceDependencyObject = new IceUserObject(
					sourceDependencyNode);
			String localName = ontology.getIndividual(
					networkSourceNode.getQualificationProfile()
							.getProductIndividualURI()).getOntClass()
					.getLocalName();
			String individual = ontology.getIndividual(
					networkSourceNode.getQualificationProfile()
							.getProductIndividualURI()).getLocalName();
			sourceDependencyObject.setText(individual + " (" + localName + ")");
			sourceDependencyObject.setExpanded(true);
			sourceDependencyObject
					.setBranchContractedIcon("/images/shape29.gif");
			sourceDependencyObject.setBranchExpandedIcon("/images/shape29.gif");
			sourceDependencyObject.setLeafIcon("/images/shape29.gif");
			sourceDependencyNode.setUserObject(sourceDependencyObject);
			dependencyNode.add(sourceDependencyNode);
			buildSubTree(sourceDependencyNode, networkSourceNode, network,
					ontology);
		}
	}

	protected static HtmlCommandButton createHtmlCommandButton(UIPanel grid,
			String id, String label, ActionListener listener) {
		final HtmlCommandButton button = new HtmlCommandButton();
		button.setId(id);
		button.setValue(label);
		if (grid != null) {
			grid.getChildren().add(button);
		}
		if (listener != null) {
			button.addActionListener(listener);
		}
		return button;
	}

	protected static void createHtmlMessage(UIPanel parent, String newFor) {
		HtmlMessage message = new HtmlMessage();
		message.setFor(newFor);
		parent.getChildren().add(message);
	}

	protected static void createInputField(final CaseFileControllerImpl caller,
			UIPanel parentPanel, String uniqueObjID, String fieldName,
			Object instance, boolean disabled) {
		UIComponent component = createInputField(caller, uniqueObjID,
				fieldName, instance, disabled, Object.class);
		parentPanel.getChildren().add(component);
	}

	protected static void createInputField(final CaseFileControllerImpl caller,
			UIPanel parentPanel, ValueExpression objectExpression,
			String entityName, String fieldName, Object instance,
			boolean disabled) {
		UIComponent component = createInputField(caller, objectExpression,
				entityName, fieldName, instance, disabled, Object.class);
		parentPanel.getChildren().add(component);
	}

	protected static UIComponent createInputField(
			final CaseFileControllerImpl caller, String uniqueObjID,
			String fieldName, Object instance, boolean disabled) {
		return createInputField(caller, uniqueObjID, fieldName, instance,
				disabled, Object.class);
	}

	protected static UIComponent createInputField(
			final CaseFileControllerImpl caller, String uniqueObjID,
			String fieldName, Object instance, boolean disabled, Class type) {
		ValueExpression objectExpression = expressionFactory
				.createValueExpression(FacesContext.getCurrentInstance()
						.getELContext(), "${" + uniqueObjID + '}', Object.class);
		if (instance != null) {
			objectExpression.setValue(FacesContext.getCurrentInstance()
					.getELContext(), instance);
		}

		final ValueExpression fieldExpression = expressionFactory
				.createValueExpression(FacesContext.getCurrentInstance()
						.getELContext(), "#{" + uniqueObjID + '.' + fieldName
						+ '}', Object.class);
		HtmlInputText component = new HtmlInputText();
		component.setValueExpression("value", fieldExpression);

		// for immediate update so also the others concurrently working on this
		// case will see the values
		component.setImmediate(true);
		component.setPartialSubmit(true);
		component.setDisabled(disabled);
		// component.setReadonly(!disabled);
		component.setId(fieldName);
		component.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void processValueChange(ValueChangeEvent valueChangeEvent)
					throws AbortProcessingException {
				fieldExpression.setValue(getELContext(), valueChangeEvent
						.getNewValue());
				caller.updateCurrentCaseFile();
			}
		});
		return component;
	}

	protected static UIComponent createInputField(
			final CaseFileControllerImpl caller,
			final ValueExpression objectExpression, String entityName,
			String fieldName, Object instance, boolean disabled, Class type) {

		final ValueExpression fieldExpression = expressionFactory
				.createValueExpression(FacesContext.getCurrentInstance()
						.getELContext(), "#{" + entityName + '.' + fieldName
						+ '}', Object.class);
		final HtmlInputText component = new HtmlInputText();
		component.setValueExpression("value", fieldExpression);
		// for immediate update so also the others concurrently working on this
		// case will see the values
		component.setImmediate(true);
		component.setPartialSubmit(true);
		component.setDisabled(disabled);
		component.setId(entityName + fieldName);
		Logger.getLogger(CaseFileControllerHelper.class).debug(
				"creating Input Field with ID: " + fieldName);
		component.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void processValueChange(ValueChangeEvent valueChangeEvent)
					throws AbortProcessingException {

				fieldExpression.setValue(getELContext(), valueChangeEvent
						.getNewValue());
				component.setValue(valueChangeEvent.getNewValue());
				CaseFile currentCaseFile = (CaseFile) objectExpression
						.getValue(getELContext());
				caller.update(currentCaseFile);

			}
		});
		return component;
	}

	protected static void showFieldValueAndCreateSelectDialog(String textLabel,
			String buttonLabel, final UIPanel grid,
			final ValueExpression boElExpression, final List list,
			KdmService kdmService, ScopeController scopeController,
			String... valueExpressionString) {

		int nrOfValueExpression = valueExpressionString.length;
		String entityExpressionString = "";
		for (int i = 0; i < nrOfValueExpression - 1; i++) {
			if (entityExpressionString != "") {
				entityExpressionString = entityExpressionString + "."
						+ valueExpressionString[i];
			} else {
				entityExpressionString = valueExpressionString[i];
			}
		}

		ValueExpression entityExpression = createValueExpression(
				entityExpressionString, Object.class);
		ValueExpression fieldExpression = createValueExpression(
				entityExpressionString,
				valueExpressionString[nrOfValueExpression - 1], Object.class,
				true);

		HtmlOutputText outputText = createOutputText(grid, textLabel, false,
				true);
		HtmlInputText inputText = createInputText(grid, "inputText"
				+ grid.getChildCount(), fieldExpression, true);
		HtmlCommandButton button = createHtmlCommandButton(grid, "button"
				+ grid.getChildCount(), "Select", null);

		createSelectDialog(grid, boElExpression, entityExpression, outputText,
				button, list, kdmService, scopeController);

	}

	protected static void showEditableFieldValue(String textLabel,
			final UIPanel grid, String objectELexpressionString,
			String valueExpressionString, final BusinessOpportunity bo,
			final KdmService kdmService) {

		final ValueExpression fieldExpression = createValueExpression(
				objectELexpressionString + "." + valueExpressionString,
				Object.class);
		createOutputText(grid, textLabel, false, true);
		HtmlInputText inputText = createInputText(grid, "inputText"
				+ grid.getChildCount(), fieldExpression, false);

		inputText.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void processValueChange(ValueChangeEvent valueChangeEvent)
					throws AbortProcessingException {
				// TODO Auto-generated method stub
				fieldExpression.setValue(getELContext(), valueChangeEvent
						.getNewValue());
				System.out.println(fieldExpression.getValue(getELContext()));
				System.out.println("Quantity "
						+ bo.getQuantityPerYearFinalProduct());
				kdmService.getCaseService().update(bo);
			}
		});

		HtmlOutputText htmlOutputText = new HtmlOutputText();
		grid.getChildren().add(htmlOutputText);

		// text = new HtmlOutputText();
		// text.setValue("Expected Number of Parts per Year");
		// grid.getChildren().add(text);
		// // Integer qnt =
		// businessOpportunity.getQuantityPerYearFinalProduct();
		// grid.getChildren().add(createInputField(this, "BusinessOpportunity" +
		// businessOpportunity.getId(), "quantityPerYearFinalProduct",
		// businessOpportunity, false, Integer.class));
		// text = new HtmlOutputText();
		// text.setValue("");
		// grid.getChildren().add(text);

	}

	protected static void showEditableDateField(String textLabel,
			final UIPanel grid, String objectELexpressionString,
			String valueExpressionString, final KdmService kdmService,
			final BusinessOpportunity bo) {

		final ValueExpression fieldExpression = createValueExpression(
				objectELexpressionString + "." + valueExpressionString,
				Object.class);

		createOutputText(grid, textLabel, false, true);

		createDateField(grid, fieldExpression, kdmService, bo);

		createEmptyOutputText(grid);

	}

	protected static void createSelectDialog(UIPanel hookUp,
			final ValueExpression boElExpression,
			final ValueExpression elExpression,
			final HtmlOutputText outputText, HtmlCommandButton button,
			final List list, final KdmService kdmService,
			final ScopeController scopeController) {

		// final CaseFile finalCurrent = getCurrentCaseFile();

		button.addActionListener(new ActionListener() {

			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {
				final PanelPopup myPopup = new PanelPopup();

				myPopup.setDraggable("true");
				myPopup
						.setStyle("width:400px;height:300px;position:absolute;top:"
								+ (150) + "px;left:" + (500) + "px;");
				myPopup.setClientOnly(true);
				myPopup.setId("selectObjPopup");
				myPopup.setScrollHeight("350px");

				Tree treeModel = new Tree(kdmService.getConnectableService(),
						scopeController);
				treeModel.setAlsoCreateAssociationNodes(false);
				treeModel.setAlsoShowOccurrence(false);
				treeModel.setExpandAll(true);
				treeModel.setAssociationScope(scopeController
						.getUnspecifiedScope());

				SearchedAssoc sa = null;
				// FIXME: the lines below are commented out.... it produces a
				// simple list instead of a tree....leaving sa == null means
				// that all possible trees are expanded...
				// if(list.size()>0 && list.get(0)instanceof Product)
				// sa = new SearchedAssoc
				// (Product.ASSOCIATIONTYPE,Product.SUPER,Product.SUB);

				if (list.size() > 0 && list.get(0) instanceof Item) {
					sa = new SearchedAssoc(Item.isAAssocType,
							Item.isASuperType, Item.isASubType);
					// clean the list to only contain "root" nodes ie. complex
					// product, simple product, ....
					treeModel.retrieveNodes(5, Tree.removeWhenNotInRoleOnly(
							list, Item.isASuperType, scopeController
									.getUnspecifiedScope(), kdmService
									.getConnectableService()), sa);
				} else {
					treeModel.retrieveNodes(5, list, sa);
				}

				com.icesoft.faces.component.tree.Tree tree = new com.icesoft.faces.component.tree.Tree();
				TreeNode treeNode = new TreeNode();
				HtmlPanelGroup panelGroup = new HtmlPanelGroup();

				tree.setHideRootNode("true");
				tree.setHideNavigation("false");
				tree.setImmediate(true);
				tree.setVar("selectedItem");
				tree.setValue(treeModel);
				tree.setId("cfTree");
				ValueExpression expression = expressionFactory
						.createValueExpression(FacesContext
								.getCurrentInstance().getELContext(),
								"#{treeitems}", Object.class);
				expression.setValue(FacesContext.getCurrentInstance()
						.getELContext(), treeModel);
				tree.setValueExpression("value", expression);

				tree.getChildren().add(treeNode);

				treeNode.getFacets().put("content", panelGroup);

				ValueExpression expressionText = expressionFactory
						.createValueExpression(FacesContext
								.getCurrentInstance().getELContext(),
								"#{selectedItem.userObject.text}", Object.class);

				final ValueExpression reference = expressionFactory
						.createValueExpression(FacesContext
								.getCurrentInstance().getELContext(),
								"#{selectedItem.userObject.reference}",
								Object.class);
				HtmlCommandLink link = new HtmlCommandLink();

				link.setValueExpression("value", expressionText);

				link.addActionListener(new ActionListener() {
					@Override
					public void processAction(ActionEvent arg0)
							throws AbortProcessingException {
						Object object = reference.getValue(FacesContext
								.getCurrentInstance().getELContext());
						try {
							// if (outputText.getValueExpression("value") ==
							// null) {
							// outputText.setValueExpression("value",
							// fieldExpression);
							// outputText.setValue(fieldExpression.getValue(FacesContext.getCurrentInstance().getELContext()));
							// }

							myPopup.setVisible(false);
							myPopup.getParent().getChildren().remove(myPopup);
							// Object myObject =
							// elExpression.getValue(getELContext());
							elExpression.setValue(FacesContext
									.getCurrentInstance().getELContext(),
									object);

							System.out.println(boElExpression
									.getValue(getELContext()));

							BusinessOpportunity bo = (BusinessOpportunity) boElExpression
									.getValue(getELContext());

							System.out.println(bo);
							// calculateRequiredProducts(hookUp,
							// businessOpportunity);
							//
							// businessOpportunity.setGoal((Product)object);

							kdmService.getCaseService().update(bo);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});

				link.setId("link");
				link.setStyle("font-size:0.8em;color:black");

				panelGroup.getChildren().add(link);

				panelGroup.setStyle("display:inline");

				HtmlPanelGroup bodyGroup = new HtmlPanelGroup();

				HtmlCommandButton cancelButton = new HtmlCommandButton();
				cancelButton.setId("cancelButton");
				cancelButton.setValue("Abbrechen");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void processAction(ActionEvent arg0)
							throws AbortProcessingException {
						// TODO Auto-generated method stub
						myPopup.setVisible(false);
						myPopup.getParent().getChildren().remove(myPopup);
					}
				});

				bodyGroup.getChildren().add(cancelButton);
				bodyGroup.getChildren().add(tree);

				myPopup.getFacets().put("body", bodyGroup);

				HtmlForm newForm = new HtmlForm();
				newForm.setId("setObjectToField");
				newForm.getChildren().add(myPopup);
				FacesContext.getCurrentInstance().getViewRoot().getChildren()
						.get(0).getChildren().get(2).getChildren().add(newForm);
			}
		});

	}

	protected static void createDateField(UIPanel grid,
			final ValueExpression valueExpression, final KdmService kdmService,
			final BusinessOpportunity bo) {

		SelectInputDate component = new SelectInputDate();
		component.setValueExpression("value", valueExpression);
		component.setStyle("font-size: 60%;");
		component.setId("Date" + grid.getChildren().size());
		// for immediate update so also the others concurrently working on this
		// case will see the values
		component.setImmediate(true);
		component.setRenderMonthAsDropdown(true);
		component.setRenderYearAsDropdown(true);
		// component.setDisabled(disabled);
		component.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void processValueChange(ValueChangeEvent valueChangeEvent)
					throws AbortProcessingException {

				if (!valueChangeEvent.getNewValue().equals(
						valueChangeEvent.getOldValue())) {
					Date newDate = (Date) valueChangeEvent.getNewValue();
					valueExpression.setValue(getELContext(), newDate);
					kdmService.getCaseService().update(bo);
					// getCurrentCaseFile().getBo().setStartOfProduction(newDate);
					// kdmService.getCaseService().update(getCurrentCaseFile());

				}

			}
		});
		if (grid != null) {
			grid.getChildren().add(component);
		}
	}

	static class UserConverter implements Converter {

		private List<SelectItem> list;

		public UserConverter(List<SelectItem> list) {
			this.list = list;
		}

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1,
				String arg2) {
			for (SelectItem item : list) {

				if (item.getValue().toString().equals(arg2)) {
					return item.getValue();
				}

			}

			return null;
		}

		@Override
		public String getAsString(FacesContext arg0, UIComponent arg1,
				Object arg2) {
			return arg2.toString();
		}

	}

	// Helper methods END

}
