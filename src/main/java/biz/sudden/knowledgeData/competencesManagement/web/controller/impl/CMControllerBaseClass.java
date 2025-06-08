package biz.sudden.knowledgeData.competencesManagement.web.controller.impl;

import java.util.ArrayList;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireEntity;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;
import biz.sudden.knowledgeData.competencesManagement.service.ICMServiceCategoryBaseClass;
import biz.sudden.knowledgeData.competencesManagement.web.controller.ICMController;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.TreeDefaultControllerImpl;

public abstract class CMControllerBaseClass implements ICMController {

	protected TreeDefaultControllerImpl categoryTreeController;
	protected ICMServiceCategoryBaseClass service;

	public CMControllerBaseClass() {
		super();

		categoryTreeController = new TreeDefaultControllerImpl();
		categoryTreeController.setController(this);
		categoryTreeController.init();
	}

	@Override
	public abstract void addCategory();

	public abstract void buildCategoryTree();

	public abstract void buildEntityTree();

	public abstract ArrayList<CMQuestionnaireEntity> getCategoryEntities(
			Long categoryID, String categoryName);

	public TreeDefaultControllerImpl getCategoryTreeController() {
		return categoryTreeController;
	}

	@Override
	public ICMServiceCategoryBaseClass getService() {
		return service;
	}

	@Override
	public abstract void init();

	public abstract void removeAll();

	@Override
	public abstract void setCategoryId(Long categoryId);

	@Override
	public abstract void setCategoryName(String categoryName);

	public void setCategoryTreeController(
			TreeDefaultControllerImpl categoryTreeController) {
		this.categoryTreeController = categoryTreeController;
	}

	@Override
	public abstract void setEntity(CMQuestionnaireEntity entity);

	@Override
	public abstract void setSelectedCategory(ICategory category);

	@Override
	public void setService(ICMServiceCategoryBaseClass service) {
		this.service = service;
	}

}