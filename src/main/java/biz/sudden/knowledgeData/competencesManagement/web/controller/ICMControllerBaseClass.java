package biz.sudden.knowledgeData.competencesManagement.web.controller;

import biz.sudden.knowledgeData.competencesManagement.service.ICMServiceCategoryBaseClass;

public interface ICMControllerBaseClass {

	public ICMController getCategoryController();

	public ICMServiceCategoryBaseClass getService();

	public void setService(ICMServiceCategoryBaseClass service);

}
