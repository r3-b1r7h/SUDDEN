package biz.sudden.knowledgeData.competencesManagement.web.controller.impl;

import java.util.ArrayList;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireEntity;
import biz.sudden.knowledgeData.competencesManagement.domain.Category;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;
import biz.sudden.knowledgeData.competencesManagement.service.ICMServiceCategoryBaseClass;
import biz.sudden.knowledgeData.competencesManagement.web.controller.ICMController;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.TreeDefaultControllerImpl;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.TreeDefaultIceUserObject;

public class CMCategoryControllerImpl extends CMControllerBaseClass implements
		ICMController {

	private ICategory category;
	private int categoryType;
	private ICMController entityController;

	public CMCategoryControllerImpl() {
		super();
	}

	@Override
	public void addCategory() {
		TreeDefaultIceUserObject nodeSelected = (TreeDefaultIceUserObject) categoryTreeController
				.getSelectedNode().getUserObject();

		category.setDescription(category.getName());
		category.setParentCategoryId(nodeSelected.getAssociatedCategoryObject()
				.getId());
		category.setType(this.categoryType);

		ICategory categoryAux = category.clone();

		long id = (service).addCategory((Category) category.clone());
		category.setId(id);

		categoryTreeController.addNode(category.getName(), categoryAux);
	}

	@Override
	public void buildCategoryTree() {
		categoryTreeController.buildCategoryTree(categoryType, category, true);
	}

	@Override
	public void buildEntityTree() {
		categoryTreeController.buildCategoryTree(categoryType, category, false);
	}

	public ICategory getCategory() {
		return category;
	}

	@Override
	public ICMController getCategoryController() {
		return null;
	}

	@Override
	public ArrayList<CMQuestionnaireEntity> getCategoryEntities(
			Long categoryID, String categoryName) {
		return entityController.getCategoryEntities(categoryID, categoryName);
	}

	@Override
	public TreeDefaultControllerImpl getCategoryTreeController() {
		return categoryTreeController;
	}

	public int getCategoryType() {
		return categoryType;
	}

	public ICMController getEntityController() {
		return entityController;
	}

	@Override
	public void init() {

	}

	@Override
	public void removeAll() {
		(service).removeAllCategories();
	}

	public void setCategory(ICategory category) {
		this.category = category;
	}

	@Override
	public void setCategoryId(Long categoryId) {
		this.category.setId(categoryId);
	}

	@Override
	public void setCategoryName(String categoryName) {
		this.category.setName(categoryName);
		this.category.setDescription(categoryName);
	}

	@Override
	public void setCategoryTreeController(
			TreeDefaultControllerImpl categoryTreeController) {
		this.categoryTreeController = categoryTreeController;
	}

	public void setCategoryType(int categoryType) {
		this.categoryType = categoryType;
	}

	@Override
	public void setEntity(CMQuestionnaireEntity entity) {
		entityController.setEntity(entity);
	}

	public void setEntityController(ICMController entityController) {
		this.entityController = entityController;
	}

	@Override
	public void setSelectedCategory(ICategory category) {
		this.category.setId(category.getId());
		this.category.setName(category.getName());
		this.category.setDescription(category.getDescription());
		this.category.setParentCategoryId(category.getParentCategoryId());
		this.category.setType(category.getType());
	}

}
