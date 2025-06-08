package biz.sudden.knowledgeData.competencesManagement.web.controller;

import java.util.ArrayList;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireEntity;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;

public interface ICMController extends ICMControllerBaseClass {

	public void addCategory();

	public ArrayList<CMQuestionnaireEntity> getCategoryEntities(
			Long categoryID, String categoryName);

	public void init();

	public void setCategoryId(Long categoryId);

	public void setCategoryName(String categoryName);

	public void setEntity(CMQuestionnaireEntity entity);

	public void setSelectedCategory(ICategory iCategory);

}
