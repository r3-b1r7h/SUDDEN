package biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces;

import java.util.List;
import java.util.ListIterator;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireEntity;
import biz.sudden.knowledgeData.competencesManagement.domain.Category;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;
import biz.sudden.knowledgeData.competencesManagement.service.ICMServiceCategoryBaseClass;
import biz.sudden.knowledgeData.competencesManagement.web.controller.ICMController;

public class TreeDefaultControllerImpl {

	private static final String XP_BRANCH_CONTRACTED_ICON = "./xmlhttp/css/xp/css-images/tree_folder_open.gif";
	private static final String XP_BRANCH_ENTITY_ICON = "./xmlhttp/css/xp/css-images/menu_checkbox_selected.gif";
	private static final String XP_BRANCH_EXPANDED_ICON = "./xmlhttp/css/xp/css-images/tree_folder_close.gif";

	protected ICMController controller;
	protected DefaultTreeModel model;
	protected DefaultMutableTreeNode rootTreeNode;
	protected DefaultMutableTreeNode selectedNode;

	public TreeDefaultControllerImpl() {

	}

	public void addEntityNode(DefaultMutableTreeNode parentTreeNode,
			List<CMQuestionnaireEntity> listOfEntities) {
		DefaultMutableTreeNode branchNode;
		TreeDefaultIceUserObject branchObject;

		ListIterator<CMQuestionnaireEntity> iterator = listOfEntities
				.listIterator();
		while (iterator.hasNext()) {
			CMQuestionnaireEntity entity = iterator.next();
			branchNode = new DefaultMutableTreeNode();
			branchObject = new TreeDefaultIceUserObject(branchNode, controller,
					this, branchNode);
			branchObject.setText(entity.getName());
			branchObject.setLeaf(true);
			branchNode.setUserObject(branchObject);
			branchObject.setAssociatedCategoryObject(null);
			branchObject.setAssociatedEntity(entity);
			((TreeDefaultIceUserObject) parentTreeNode.getUserObject())
					.setLeaf(false);
			parentTreeNode.add(branchNode);
			branchObject.setLeafIcon(XP_BRANCH_ENTITY_ICON);
		}
	}

	public void addNode(String text, ICategory associatedCategoryObject) {
		DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
		TreeDefaultIceUserObject branchObject = new TreeDefaultIceUserObject(
				branchNode, controller, this, branchNode);
		branchObject.setText(text);
		branchObject.setLeaf(true);
		branchObject.setBranchContractedIcon(XP_BRANCH_CONTRACTED_ICON);
		branchObject.setBranchExpandedIcon(XP_BRANCH_EXPANDED_ICON);
		branchObject.setLeafIcon(XP_BRANCH_EXPANDED_ICON);
		branchNode.setUserObject(branchObject);
		((TreeDefaultIceUserObject) selectedNode.getUserObject())
				.setLeaf(false);
		branchObject.setAssociatedCategoryObject(associatedCategoryObject);
		selectedNode.add(branchNode);
	}

	public void buildCategoryTree(int catType, ICategory iCategory,
			boolean categoryTree) {
		rootTreeNode.removeAllChildren();
		List<Category> categories = (controller.getService())
				.retrieveCategoriesByParentId(-1, catType);
		List<CMQuestionnaireEntity> listOfEntities;

		if (categories != null) {
			if (!categoryTree) {
				listOfEntities = controller.getCategoryEntities(new Long(-1),
						"Root Category");
				if ((listOfEntities != null) && (listOfEntities.size() > 0)) {
					addEntityNode(rootTreeNode, listOfEntities);
				}
			}

			buildCategoryTreeRecursive(rootTreeNode, categories, catType,
					iCategory, categoryTree);
		}
	}

	public void buildCategoryTreeRecursive(
			DefaultMutableTreeNode parentTreeNode, List<Category> nodes,
			int catType, ICategory iCategory, boolean categoryTree) {
		DefaultMutableTreeNode branchNode;
		TreeDefaultIceUserObject branchObject;
		List<Category> childNodes;
		List<CMQuestionnaireEntity> listOfEntities;

		ListIterator<Category> iterator = nodes.listIterator();
		while (iterator.hasNext()) {
			Category categoryAux = iterator.next();
			branchNode = new DefaultMutableTreeNode();
			branchObject = new TreeDefaultIceUserObject(branchNode, controller,
					this, branchNode);
			branchObject.setText((categoryAux).getDescription());
			branchObject.setLeaf(true);
			branchObject.setBranchContractedIcon(XP_BRANCH_CONTRACTED_ICON);
			branchObject.setBranchExpandedIcon(XP_BRANCH_EXPANDED_ICON);
			branchObject.setLeafIcon(XP_BRANCH_EXPANDED_ICON);

			branchNode.setUserObject(branchObject);
			branchObject.setAssociatedCategoryObject(categoryAux);
			((TreeDefaultIceUserObject) parentTreeNode.getUserObject())
					.setLeaf(false);
			parentTreeNode.add(branchNode);

			if (!categoryTree) {
				listOfEntities = controller.getCategoryEntities(categoryAux
						.getId(), categoryAux.getDescription());
				if ((listOfEntities != null) && (listOfEntities.size() > 0)) {
					addEntityNode(branchNode, listOfEntities);
				}
			}

			childNodes = (controller.getService())
					.retrieveCategoriesByParentId(categoryAux.getId()
							.intValue(), catType);
			/*
			 * childNodes =
			 * ((ICategoryRepository)controller.getRepository()).searchByParentAndId
			 * (categoryAux.getId() .intValue(), catType);
			 */
			if ((childNodes != null) && (childNodes.size() > 0)) {
				buildCategoryTreeRecursive(branchNode, childNodes, catType,
						iCategory, categoryTree);
			}
		}
	}

	/**
	 * @return the controller
	 */
	public ICMController getController() {
		return controller;
	}

	/**
	 * Gets the tree's default model.
	 * 
	 * @return tree model.
	 */
	public DefaultTreeModel getModel() {
		return model;
	}

	/**
	 * @return the selectedNode
	 */
	public DefaultMutableTreeNode getSelectedNode() {
		return selectedNode;
	}

	public void init() {

		// create root node with its children expanded
		DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
		TreeDefaultIceUserObject rootObject = new TreeDefaultIceUserObject(
				rootTreeNode, controller, this, rootTreeNode);
		rootObject.setText("Root Category");
		rootObject.setExpanded(true);
		rootObject.setBranchContractedIcon(XP_BRANCH_CONTRACTED_ICON);
		rootObject.setBranchExpandedIcon(XP_BRANCH_EXPANDED_ICON);
		rootObject.setLeafIcon(XP_BRANCH_EXPANDED_ICON);
		rootTreeNode.setUserObject(rootObject);
		Category iCategory = new Category();
		iCategory.setDescription("Root Category");
		iCategory.setParentCategoryId(new Long(-1));
		iCategory.setId(new Long(-1));
		rootObject.setAssociatedCategoryObject(iCategory);

		// model is accessed by by the ice:tree component
		model = new DefaultTreeModel(rootTreeNode);

		selectedNode = rootTreeNode;
		this.rootTreeNode = rootTreeNode;
	}

	/**
	 * @param controller
	 *            the controller to set
	 */
	public void setController(ICMController controller) {
		this.controller = controller;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(DefaultTreeModel model) {
		this.model = model;
	}

	/**
	 * @param selectedNode
	 *            the selectedNode to set
	 */
	public void setSelectedNode(DefaultMutableTreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

}
