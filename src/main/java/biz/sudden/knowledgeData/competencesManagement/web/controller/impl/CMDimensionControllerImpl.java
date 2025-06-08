package biz.sudden.knowledgeData.competencesManagement.web.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireEntity;
import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IDimension;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.web.controller.ICMController;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.TabSetDefaultControllerImpl;

public class CMDimensionControllerImpl extends CMControllerBaseClass implements
		ICMController {

	// Attributes to control UI presentation
	private TabSetDefaultControllerImpl addDimensionTabController;

	// Beans
	private CMCVIControllerImpl cmCVIController;
	private IDimension dimension;
	private CMCategoryControllerImpl dimensionCategoryController;
	private CMCategoryControllerImpl dimensionController;
	private String weightMultiplier = "1";

	public CMDimensionControllerImpl() {
		super();

		addDimensionTabController = new TabSetDefaultControllerImpl();
		addDimensionTabController.addTab("Attributes",
				"cmAddDimensionTab1.jspx", true);
		addDimensionTabController
				.addTab("CVI", "cmAddDimensionTab2.jspx", true);
		addDimensionTabController.addTab("Categorize",
				"cmAddDimensionTab3.jspx", true);
	}

	@Override
	public void addCategory() {
		dimensionCategoryController.addCategory();
		dimensionController.buildEntityTree();
	}

	public String addDimension() {
		if (cmCVIController.getCvi().getId() != null) {
			ICategory category = dimensionCategoryController.getCategory();
			dimension.setCategoryId(category.getId());
			dimension.setCategoryName(category.getDescription());
			try {
				dimension.setWeightMultiplier(Float.valueOf(weightMultiplier));
			} catch (NumberFormatException e) {
				dimension.setWeightMultiplier(1.0f);
			}
			if (category.getId() == null) {
				dimension.setCategoryId(new Long(-1));
				dimension.setCategoryName("Root Category");
			}

			dimension.setCvi((CVI) cmCVIController.getCvi());

			long id = ((ICMCompetencesManagement_Service) service)
					.addDimension((Dimension) dimension);
			dimension.setId(id);

			dimensionController.buildEntityTree();

			return "dimensionAdded";
		} else {
			return "";
		}
	}

	@Override
	public void buildCategoryTree() {
		dimensionCategoryController.buildCategoryTree();
	}

	@Override
	public void buildEntityTree() {
		dimensionController.buildEntityTree();
	}

	public TabSetDefaultControllerImpl getAddDimensionTabController() {
		return addDimensionTabController;
	}

	public List<Dimension> getAllDimensions() {
		return ((ICMCompetencesManagement_Service) service)
				.retrieveAllDimensions();
	}

	@Override
	public ICMController getCategoryController() {
		return this.dimensionCategoryController;
	}

	@Override
	public ArrayList<CMQuestionnaireEntity> getCategoryEntities(
			Long categoryID, String categoryName) {
		List<Dimension> listOfDimensions = ((ICMCompetencesManagement_Service) service)
				.retrieveAllDimensions();
		ArrayList<CMQuestionnaireEntity> listOfEntities = new ArrayList<CMQuestionnaireEntity>();

		ListIterator<Dimension> iterator = listOfDimensions.listIterator();
		while (iterator.hasNext()) {
			Dimension dimension = iterator.next();
			if (dimension.getCategoryId().intValue() == categoryID.intValue()) {
				listOfEntities.add(dimension);
			}
		}

		return listOfEntities;
	}

	public CMCVIControllerImpl getCmCVIController() {
		return cmCVIController;
	}

	public IDimension getDimension() {
		return dimension;
	}

	public CMCategoryControllerImpl getDimensionCategoryController() {
		return dimensionCategoryController;
	}

	public CMCategoryControllerImpl getDimensionController() {
		return dimensionController;
	}

	public List<Dimension> getDimensionsByCategoryId() {
		return ((ICMCompetencesManagement_Service) service)
				.retrieveDimensionsByCategoryId(dimension.getCategoryId());
	}

	public String getWeightMultiplier() {
		return weightMultiplier;
	}

	@Override
	public void init() {
		dimensionCategoryController.setEntityController(this);
		dimensionCategoryController.setService(this.service);
		dimensionCategoryController
				.setCategoryType(ICategory.CATEGORY_DIMENSION);
		dimensionCategoryController.buildCategoryTree();

		dimensionController.setEntityController(this);
		dimensionController.setService(this.service);
		dimensionController.setCategoryType(ICategory.CATEGORY_DIMENSION);
		dimensionController.buildEntityTree();
	}

	public String navigateToAddNewDimension() {
		dimension = new Dimension();
		weightMultiplier = "1";
		cmCVIController.navigateToAddNewCVI();
		return "addDimension";
	}

	@Override
	public void removeAll() {
		((ICMCompetencesManagement_Service) service).removeAllDimensions();
	}

	public void setAddDimensionTabController(
			TabSetDefaultControllerImpl addDimensionTabController) {
		this.addDimensionTabController = addDimensionTabController;
	}

	@Override
	public void setCategoryId(Long categoryId) {
		dimensionCategoryController.setCategoryId(categoryId);
	}

	@Override
	public void setCategoryName(String categoryName) {
		dimensionCategoryController.setCategoryName(categoryName);
	}

	public void setCmCVIController(CMCVIControllerImpl cmCVIController) {
		this.cmCVIController = cmCVIController;
	}

	public void setDimension(IDimension dimension) {
		this.dimension = dimension;
	}

	public void setDimensionCategoryController(
			CMCategoryControllerImpl dimensionCategoryController) {
		this.dimensionCategoryController = dimensionCategoryController;
	}

	public void setDimensionController(
			CMCategoryControllerImpl dimensionController) {
		this.dimensionController = dimensionController;
	}

	@Override
	public void setEntity(CMQuestionnaireEntity entity) {
		dimension = (IDimension) entity;
		/*
		 * dimension.setName(entity.getName());
		 * dimension.setDescription(entity.getDescription());
		 * dimension.setId(entity.getId());
		 * dimension.setCategoryId(entity.getCategoryId());
		 */
	}

	@Override
	public void setSelectedCategory(ICategory category) {

	}

	public void setWeightMultiplier(String weightMultiplier) {
		this.weightMultiplier = weightMultiplier;
	}

}
