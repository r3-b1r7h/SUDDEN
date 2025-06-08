package biz.sudden.knowledgeData.competencesManagement.web.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.faces.event.ActionEvent;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireEntity;
import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICompetence;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IDimension;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.web.controller.ICMController;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.DataScrollingModel;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.TabSetDefaultControllerImpl;

public class CMCompetenceControllerImpl extends CMControllerBaseClass implements
		ICMController {

	// Attributes to control UI presentation
	private DataScrollingModel addCompetenceDataScrollingModel;
	private TabSetDefaultControllerImpl addCompetenceTabController;

	// Beans
	private CMDimensionControllerImpl cmDimensionController;
	private ICompetence competence;
	private CMCategoryControllerImpl competenceCategoryController;
	private CMCategoryControllerImpl competenceController;
	private String weightMultiplier = "1";

	public CMCompetenceControllerImpl() {
		super();

		addCompetenceTabController = new TabSetDefaultControllerImpl();
		addCompetenceTabController.addTab("Attributes",
				"cmAddCompetenceTab1.jspx", true);
		addCompetenceTabController.addTab("Dimensions",
				"cmAddCompetenceTab2.jspx", true);
		addCompetenceTabController.addTab("Categorize",
				"cmAddCompetenceTab3.jspx", true);

		addCompetenceDataScrollingModel = new DataScrollingModel(5);
	}

	@Override
	public void addCategory() {
		competenceCategoryController.addCategory();
		competenceController.buildEntityTree();
	}

	public String addCompetence() {
		if (competence.getDimensions().size() > 0) {
			ICategory category = competenceCategoryController.getCategory();
			competence.setCategoryId(category.getId());
			competence.setCategoryName(category.getDescription());
			try {
				competence.setWeightMultiplier(Float.valueOf(weightMultiplier));
			} catch (NumberFormatException e) {
				competence.setWeightMultiplier(1.0f);
			}
			if (category.getId() == null) {
				competence.setCategoryId(new Long(-1));
				competence.setCategoryName("Root Category");
			}

			long id = ((ICMCompetencesManagement_Service) service)
					.addCompetence((Competence) competence);
			competence.setId(id);

			competenceController.buildEntityTree();

			return "competenceAdded";
		} else {
			return "";
		}
	}

	public void addDimensionButtonListener(ActionEvent event) {
		IDimension iDimension = cmDimensionController.getDimension();
		addCompetenceDataScrollingModel.addItem(iDimension);
		competence.getDimensions().add((Dimension) iDimension);
	}

	@Override
	public void buildCategoryTree() {
		competenceCategoryController.buildCategoryTree();
	}

	@Override
	public void buildEntityTree() {
		competenceController.buildEntityTree();
	}

	public DataScrollingModel getAddCompetenceDataScrollingModel() {
		return addCompetenceDataScrollingModel;
	}

	public TabSetDefaultControllerImpl getAddCompetenceTabController() {
		return addCompetenceTabController;
	}

	public List<Competence> getAllCompetences() {
		return ((ICMCompetencesManagement_Service) service)
				.retrieveAllCompetences();
	}

	@Override
	public ICMController getCategoryController() {
		return this.competenceCategoryController;
	}

	@Override
	public ArrayList<CMQuestionnaireEntity> getCategoryEntities(
			Long categoryID, String categoryName) {
		List<Competence> listOfCompetences = ((ICMCompetencesManagement_Service) service)
				.retrieveAllCompetences();
		ArrayList<CMQuestionnaireEntity> listOfEntities = new ArrayList<CMQuestionnaireEntity>();

		ListIterator<Competence> iterator = listOfCompetences.listIterator();
		while (iterator.hasNext()) {
			Competence competence = iterator.next();
			if (competence.getCategoryId().intValue() == categoryID.intValue()) {
				listOfEntities.add(competence);
			}
		}

		return listOfEntities;
	}

	public CMDimensionControllerImpl getCmDimensionController() {
		return cmDimensionController;
	}

	public ICompetence getCompetence() {
		return competence;
	}

	public Competence getCompetenceById(long competenceId) {
		return ((ICMCompetencesManagement_Service) service)
				.retrieveCompetenceById(competenceId);
	}

	public CMCategoryControllerImpl getCompetenceCategoryController() {
		return competenceCategoryController;
	}

	public CMCategoryControllerImpl getCompetenceController() {
		return competenceController;
	}

	public List<Competence> getCompetencesByCategoryId() {
		return ((ICMCompetencesManagement_Service) service)
				.retrieveCompetencesByCategoryId(competence.getCategoryId());
	}

	public CVI getCVIById(long cviId) {
		return ((ICMCompetencesManagement_Service) service)
				.retrieveCVIById(cviId);
	}

	public Dimension getDimensionById(long dimensionId) {
		return ((ICMCompetencesManagement_Service) service)
				.retrieveDimensionById(dimensionId);
	}

	public Tick getTickById(long tickId) {
		return ((ICMCompetencesManagement_Service) service).getTickById(tickId);
	}

	public String getWeightMultiplier() {
		return weightMultiplier;
	}

	@Override
	public void init() {
		competenceCategoryController.setEntityController(this);
		competenceCategoryController.setService(this.service);
		competenceCategoryController
				.setCategoryType(ICategory.CATEGORY_COMPETENCE);
		competenceCategoryController.buildCategoryTree();

		competenceController.setEntityController(this);
		competenceController.setService(this.service);
		competenceController.setCategoryType(ICategory.CATEGORY_COMPETENCE);
		competenceController.buildEntityTree();
	}

	public String navigateToAddNewCompetence() {
		competence = new Competence();
		addCompetenceDataScrollingModel.getItems().clear();
		weightMultiplier = "1";
		cmDimensionController.navigateToAddNewDimension();
		return "addCompetence";
	}

	@Override
	public void removeAll() {
		((ICMCompetencesManagement_Service) service).removeAllCompetences();
	}

	@SuppressWarnings("unchecked")
	public void removeSelectedDimensionsButtonListener(ActionEvent event) {
		ArrayList<Dimension> items = addCompetenceDataScrollingModel.getItems();

		int c0 = 0;
		while (c0 < items.size()) {
			Dimension item = items.get(c0);
			if (item.isSelected()) {
				items.remove(c0);
				competence.removeDimension(c0);
			} else {
				c0++;
			}
		}
	}

	public void setAddCompetenceDataScrollingModel(
			DataScrollingModel addCompetenceDataScrollingModel) {
		this.addCompetenceDataScrollingModel = addCompetenceDataScrollingModel;
	}

	public void setAddCompetenceTabController(
			TabSetDefaultControllerImpl addCompetenceTabController) {
		this.addCompetenceTabController = addCompetenceTabController;
	}

	@Override
	public void setCategoryId(Long categoryId) {
		competenceCategoryController.setCategoryId(categoryId);
	}

	@Override
	public void setCategoryName(String categoryName) {
		competenceCategoryController.setCategoryName(categoryName);
	}

	public void setCmDimensionController(
			CMDimensionControllerImpl cmDimensionController) {
		this.cmDimensionController = cmDimensionController;
	}

	public void setCompetence(ICompetence competence) {
		this.competence = competence;
	}

	public void setCompetenceCategoryController(
			CMCategoryControllerImpl competenceCategoryController) {
		this.competenceCategoryController = competenceCategoryController;
	}

	public void setCompetenceController(
			CMCategoryControllerImpl competenceController) {
		this.competenceController = competenceController;
	}

	@Override
	public void setEntity(CMQuestionnaireEntity entity) {
		Competence competenceAux = (Competence) entity;
		competence = competenceAux;
	}

	@Override
	public void setSelectedCategory(ICategory category) {

	}

	public void setWeightMultiplier(String weightMultiplier) {
		this.weightMultiplier = weightMultiplier;
	}

}
