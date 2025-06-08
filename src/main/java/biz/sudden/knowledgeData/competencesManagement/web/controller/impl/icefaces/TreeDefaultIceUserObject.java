package biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces;

import javax.faces.event.ActionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireEntity;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;
import biz.sudden.knowledgeData.competencesManagement.web.controller.ICMController;

import com.icesoft.faces.component.tree.IceUserObject;

public class TreeDefaultIceUserObject extends IceUserObject {

	protected ICategory associatedCategoryObject;
	protected CMQuestionnaireEntity associatedEntity;
	protected ICMController controller;
	protected DefaultMutableTreeNode nodeController;
	protected TreeDefaultControllerImpl treeController;

	public TreeDefaultIceUserObject(DefaultMutableTreeNode wrapper,
			ICMController controller, TreeDefaultControllerImpl treeController,
			DefaultMutableTreeNode nodeController) {
		super(wrapper);
		this.controller = controller;
		this.treeController = treeController;
		this.nodeController = nodeController;
	}

	/**
	 * @return the associatedEntity
	 */
	public CMQuestionnaireEntity getAssociatedEntity() {
		return associatedEntity;
	}

	/**
	 * @param associatedEntity
	 *            the associatedEntity to set
	 */
	public void setAssociatedEntity(CMQuestionnaireEntity associatedEntity) {
		this.associatedEntity = associatedEntity;
	}

	/**
	 * @return the associatedCategory
	 */
	public ICategory getAssociatedCategoryObject() {
		return associatedCategoryObject;
	}

	/**
	 * @return the controller
	 */
	public ICMController getController() {
		return controller;
	}

	/**
	 * @return the nodeController
	 */
	public DefaultMutableTreeNode getNodeController() {
		return nodeController;
	}

	/**
	 * @return the treeController
	 */
	public TreeDefaultControllerImpl getTreeController() {
		return treeController;
	}

	public void nodeSelected(ActionEvent event) {
		if (associatedEntity == null) {
			controller.setCategoryName(text);
			controller.setCategoryId(new Long(0));
			controller.setSelectedCategory(associatedCategoryObject);
			treeController.selectedNode = this.nodeController;
		} else {
			controller.setEntity(associatedEntity);
			treeController.selectedNode = this.nodeController;
		}
	}

	/**
	 * @param associatedCategory
	 *            the associatedCategory to set
	 */
	public void setAssociatedCategoryObject(ICategory associatedCategoryObject) {
		this.associatedCategoryObject = associatedCategoryObject;
	}

	/**
	 * @param controller
	 *            the controller to set
	 */
	public void setController(ICMController controller) {
		this.controller = controller;
	}

	/**
	 * @param nodeController
	 *            the nodeController to set
	 */
	public void setNodeController(DefaultMutableTreeNode nodeController) {
		this.nodeController = nodeController;
	}

	/**
	 * @param treeController
	 *            the treeController to set
	 */
	public void setTreeController(TreeDefaultControllerImpl treeController) {
		this.treeController = treeController;
	}

}
