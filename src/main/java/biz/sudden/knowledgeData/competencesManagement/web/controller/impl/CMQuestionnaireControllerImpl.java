package biz.sudden.knowledgeData.competencesManagement.web.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.faces.event.ActionEvent;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireEntity;
import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireRequiredCompetenceDimensions;
import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICompetence;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IQuestionnaire;
import biz.sudden.knowledgeData.competencesManagement.service.ICMQuestionnairesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.web.controller.ICMController;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.DataScrollingModel;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.TabSetDefaultControllerImpl;

import com.icesoft.faces.component.ext.HtmlPanelGroup;

public class CMQuestionnaireControllerImpl extends CMControllerBaseClass
		implements ICMController {

	// Attributes to control UI presentation
	private DataScrollingModel addQuestionnaireDataScrollingModel;
	private TabSetDefaultControllerImpl addQuestionnaireTabController;

	// Beans
	private CMCompetenceControllerImpl cmCompetenceController;
	private IQuestionnaire questionnaire;
	private CMCategoryControllerImpl questionnaireCategoryController;
	private CMCategoryControllerImpl questionnaireController;
	private HtmlPanelGroup questionnaireHtmlPanel;

	public CMQuestionnaireControllerImpl() {
		super();

		addQuestionnaireTabController = new TabSetDefaultControllerImpl();
		addQuestionnaireTabController.addTab("Attributes",
				"cmAddQuestionnaireTab1.jspx", true);
		addQuestionnaireTabController.addTab("Competences",
				"cmAddQuestionnaireTab2.jspx", true);
		addQuestionnaireTabController.addTab("Categorize",
				"cmAddQuestionnaireTab3.jspx", true);
		addQuestionnaireTabController.addTab("Questionnaire",
				"cmAddQuestionnaireTab4.jspx", true);

		addQuestionnaireDataScrollingModel = new DataScrollingModel(5);

		questionnaireHtmlPanel = new HtmlPanelGroup();
	}

	@Override
	public void addCategory() {
		questionnaireCategoryController.addCategory();
		questionnaireController.buildEntityTree();
	}

	public void addCompetenceButtonListener(ActionEvent event) {
		ICompetence iCompetence = cmCompetenceController.getCompetence();
		addQuestionnaireDataScrollingModel.addItem(questionnaire
				.addCompetence((Competence) iCompetence));
		questionnaire.toHtml(questionnaireHtmlPanel);
	}

	public String addQuestionnaire() {
		if (questionnaire.getCompetences().size() > 0) {
			ICategory category = questionnaireCategoryController.getCategory();
			questionnaire.setCategoryId(category.getId());
			questionnaire.setCategoryName(category.getDescription());
			if (category.getId() == null) {
				questionnaire.setCategoryId(new Long(-1));
				questionnaire.setCategoryName("Root Category");
			}
			long id = ((ICMQuestionnairesManagement_Service) service)
					.addQuestionnaire((Questionnaire) questionnaire);
			questionnaire.setId(id);
			questionnaireController.buildEntityTree();

			return "questionnaireAdded";
		} else {
			return "";
		}
	}

	@Override
	public void buildCategoryTree() {
		questionnaireCategoryController.buildCategoryTree();
	}

	@Override
	public void buildEntityTree() {
		questionnaireCategoryController.buildEntityTree();
	}

	public DataScrollingModel getAddQuestionnaireDataScrollingModel() {
		return addQuestionnaireDataScrollingModel;
	}

	public TabSetDefaultControllerImpl getAddQuestionnaireTabController() {
		return addQuestionnaireTabController;
	}

	public List<Questionnaire> getAllQuestionnaires() {
		return ((ICMQuestionnairesManagement_Service) service)
				.retrieveAllQuestionnaires();
	}

	@Override
	public ICMController getCategoryController() {
		return this.questionnaireCategoryController;
	}

	@Override
	public ArrayList<CMQuestionnaireEntity> getCategoryEntities(
			Long categoryID, String categoryName) {
		List<Questionnaire> listOfQuestionnaires = ((ICMQuestionnairesManagement_Service) service)
				.retrieveAllQuestionnaires();
		ArrayList<CMQuestionnaireEntity> listOfEntities = new ArrayList<CMQuestionnaireEntity>();

		ListIterator<Questionnaire> iterator = listOfQuestionnaires
				.listIterator();
		while (iterator.hasNext()) {
			Questionnaire questionnaire = iterator.next();
			if (questionnaire.getCategoryId().intValue() == categoryID
					.intValue()) {
				listOfEntities.add(questionnaire);
			}
		}

		return listOfEntities;
	}

	public CMCompetenceControllerImpl getCmCompetenceController() {
		return cmCompetenceController;
	}

	public IQuestionnaire getQuestionnaire() {
		return questionnaire;
	}

	public CMCategoryControllerImpl getQuestionnaireCategoryController() {
		return questionnaireCategoryController;
	}

	public CMCategoryControllerImpl getQuestionnaireController() {
		return questionnaireController;
	}

	public HtmlPanelGroup getQuestionnaireHtmlPanel() {
		questionnaire.toHtml(questionnaireHtmlPanel);
		return questionnaireHtmlPanel;
	}

	public List<Questionnaire> getQuestionnairesByCategoryId() {
		return ((ICMQuestionnairesManagement_Service) service)
				.retrieveQuestionnairesByCategoryId(questionnaire
						.getCategoryId());
	}

	public Questionnaire getQuestionnairesById(long questionnaireId) {
		return ((ICMQuestionnairesManagement_Service) service)
				.retrieveQuestionnaireById(questionnaireId);
	}

	@Override
	public void init() {
		questionnaireCategoryController.setEntityController(this);
		questionnaireCategoryController.setService(this.service);
		questionnaireCategoryController
				.setCategoryType(ICategory.CATEGORY_QUESTIONNAIRE);
		questionnaireCategoryController.buildCategoryTree();

		questionnaireController.setEntityController(this);
		questionnaireController.setService(this.service);
		questionnaireController
				.setCategoryType(ICategory.CATEGORY_QUESTIONNAIRE);
		questionnaireController.buildEntityTree();
	}

	public String navigateToAddNewQuestionnaire() {
		questionnaire = new Questionnaire();
		addQuestionnaireDataScrollingModel.getItems().clear();
		return "addQuestionnaire";
	}

	public void refreshQuestionnaireHtmlButtonListener(ActionEvent event) {
		questionnaire.toHtml(questionnaireHtmlPanel);
	}

	@Override
	public void removeAll() {
		((ICMQuestionnairesManagement_Service) service)
				.removeAllQuestionnaires();
	}

	@SuppressWarnings("unchecked")
	public void removeSelectedCompetencesButtonListener(ActionEvent event) {
		ArrayList<CMQuestionnaireRequiredCompetenceDimensions> items = addQuestionnaireDataScrollingModel
				.getItems();

		int c0 = 0;
		while (c0 < items.size()) {
			CMQuestionnaireRequiredCompetenceDimensions item = items.get(c0);
			if (item.isSelected()) {
				items.remove(c0);
				questionnaire.removeCompetence(c0);
			} else {
				c0++;
			}
		}

		questionnaire.toHtml(questionnaireHtmlPanel);
	}

	public void setAddQuestionnaireDataScrollingModel(
			DataScrollingModel addQuestionnaireDataScrollingModel) {
		this.addQuestionnaireDataScrollingModel = addQuestionnaireDataScrollingModel;
	}

	public void setAddQuestionnaireTabController(
			TabSetDefaultControllerImpl addQuestionnaireTabController) {
		this.addQuestionnaireTabController = addQuestionnaireTabController;
	}

	@Override
	public void setCategoryId(Long categoryId) {
		questionnaireCategoryController.setCategoryId(categoryId);
	}

	@Override
	public void setCategoryName(String categoryName) {
		questionnaireCategoryController.setCategoryName(categoryName);
	}

	public void setCmCompetenceController(
			CMCompetenceControllerImpl cmCompetenceController) {
		this.cmCompetenceController = cmCompetenceController;
	}

	@Override
	public void setEntity(CMQuestionnaireEntity entity) {
		Questionnaire questionnaireAux = (Questionnaire) entity;
		questionnaire = questionnaireAux;
	}

	public void setQuestionnaire(IQuestionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public void setQuestionnaireCategoryController(
			CMCategoryControllerImpl questionnaireCategoryController) {
		this.questionnaireCategoryController = questionnaireCategoryController;
	}

	public void setQuestionnaireController(
			CMCategoryControllerImpl questionnaireController) {
		this.questionnaireController = questionnaireController;
	}

	public void setQuestionnaireHtmlPanel(HtmlPanelGroup questionnaireHtmlPanel) {
		this.questionnaireHtmlPanel = questionnaireHtmlPanel;
	}

	@Override
	public void setSelectedCategory(ICategory category) {

	}

}