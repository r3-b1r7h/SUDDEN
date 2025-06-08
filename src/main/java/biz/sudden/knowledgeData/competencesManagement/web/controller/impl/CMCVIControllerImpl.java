package biz.sudden.knowledgeData.competencesManagement.web.controller.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireEntity;
import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICVI;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ITick;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.web.controller.ICMController;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.DataScrollingModel;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.TabSetDefaultControllerImpl;

public class CMCVIControllerImpl extends CMControllerBaseClass implements
		ICMController {

	// Attributes to control UI presentation
	private DataScrollingModel addCVIDataScrollingModel;
	private TabSetDefaultControllerImpl addCVITabController;
	private SelectItem[] existingTickTypes = new SelectItem[] {};
	private SelectItem[] existingTickAutoValues = new SelectItem[] {};

	// Beans
	private ICVI cvi;
	private CMCategoryControllerImpl cviCategoryController;
	private CMCategoryControllerImpl cviController;
	private ITick tick;

	public CMCVIControllerImpl() {
		super();

		addCVITabController = new TabSetDefaultControllerImpl();
		addCVITabController.addTab("Attributes", "cmAddCVITab1.jspx", true);
		addCVITabController.addTab("Ticks", "cmAddCVITab2.jspx", true);
		addCVITabController.addTab("Categorize", "cmAddCVITab3.jspx", true);

		addCVIDataScrollingModel = new DataScrollingModel(5);
	}

	@Override
	public void addCategory() {
		cviCategoryController.addCategory();
		cviController.buildEntityTree();
	}

	public String addCVI() {
		ICategory category = cviCategoryController.getCategory();
		cvi.setCategoryId(category.getId());
		cvi.setCategoryName(category.getDescription());
		if (category.getId() == null) {
			cvi.setCategoryId(new Long(-1));
			cvi.setCategoryName("Root Category");
		}
		for (Tick tick : cvi.getTicks()) {
			long id = ((ICMCompetencesManagement_Service) service)
					.addTick(tick);
			tick.setId(id);
		}
		long id = ((ICMCompetencesManagement_Service) service)
				.addCVI((CVI) cvi);
		cvi.setId(id);
		cviController.buildEntityTree();

		return "cviAdded";
	}

	public void addTickButtonListener(ActionEvent event) {
		ITick iTick = tick.clone();
		iTick.setTNumValue(tick.getTNumValue());
		iTick.setTTextValue(tick.getTTextValue());
		iTick.setSelected(false);
		iTick.setQuantifiable(tick.isQuantifiable());
		iTick.setAutoValue(tick.getAutoValue());
		iTick.setType(tick.getType());
		addCVIDataScrollingModel.addItem(iTick);

		cvi.getTicks().add((Tick) iTick);

		tick.setTNumValue(0);
		tick.setTTextValue("");
		tick.setSelected(false);
		tick.setQuantifiable(true);
	}

	@Override
	public void buildCategoryTree() {
		cviCategoryController.buildCategoryTree();
	}

	@Override
	public void buildEntityTree() {
		cviController.buildEntityTree();
	}

	public void existingTickAutoValues() {
		Enumeration<Integer> keys = Tick.TICK_AUTOVALUE_STRINGS.keys();
		existingTickAutoValues = new SelectItem[Tick.TICK_AUTOVALUE_STRINGS
				.size()];
		int c0 = 0;
		while (keys.hasMoreElements()) {
			Integer key = keys.nextElement();
			String element = Tick.TICK_AUTOVALUE_STRINGS.get(key);
			existingTickAutoValues[c0] = new SelectItem(key, element);
			c0++;
		}
	}

	public DataScrollingModel getAddCVIDataScrollingModel() {
		return addCVIDataScrollingModel;
	}

	public TabSetDefaultControllerImpl getAddCVITabController() {
		return addCVITabController;
	}

	public List<CVI> getAllCVIs() {
		return ((ICMCompetencesManagement_Service) service).retrieveAllCVIs();
	}

	@Override
	public ICMController getCategoryController() {
		return this.cviCategoryController;
	}

	@Override
	public ArrayList<CMQuestionnaireEntity> getCategoryEntities(
			Long categoryID, String categoryName) {
		List<CVI> listOfCVIs = ((ICMCompetencesManagement_Service) service)
				.retrieveAllCVIs();
		ArrayList<CMQuestionnaireEntity> listOfEntities = new ArrayList<CMQuestionnaireEntity>();

		ListIterator<CVI> iterator = listOfCVIs.listIterator();
		while (iterator.hasNext()) {
			CVI cvi = iterator.next();
			if (cvi.getCategoryId().intValue() == categoryID.intValue()) {
				/*
				 * CMQuestionnaireEntity entity = new CMQuestionnaireEntity();
				 * entity.setDescription(cvi.getDescription());
				 * entity.setId(cvi.getId()); entity.setName(cvi.getName());
				 * entity.setCategoryId(cvi.getCategoryId());
				 */
				listOfEntities.add(cvi);
			}
		}

		return listOfEntities;
	}

	public ICVI getCvi() {
		return cvi;
	}

	public List<CVI> getCVIByCategoryId() {
		return ((ICMCompetencesManagement_Service) service)
				.retrieveCVIsByCategoryId(cvi.getCategoryId());
	}

	public CMCategoryControllerImpl getCviCategoryController() {
		return cviCategoryController;
	}

	public CMCategoryControllerImpl getCviController() {
		return cviController;
	}

	public SelectItem[] getExistingTickAutoValues() {
		return existingTickAutoValues;
	}

	public SelectItem[] getExistingTickTypes() {
		return existingTickTypes;
	}

	public ITick getTick() {
		return tick;
	}

	@Override
	public void init() {
		cviCategoryController.setEntityController(this);
		cviCategoryController.setService(this.service);
		cviCategoryController.setCategoryType(ICategory.CATEGORY_CVI);
		cviCategoryController.buildCategoryTree();

		cviController.setEntityController(this);
		cviController.setService(this.service);
		cviController.setCategoryType(ICategory.CATEGORY_CVI);
		cviController.buildEntityTree();
	}

	public void initExistingTickTypes() {
		Enumeration<Integer> keys = Tick.TICK_TYPE_STRINGS.keys();
		existingTickTypes = new SelectItem[Tick.TICK_TYPE_STRINGS.size()];
		int c0 = 0;
		while (keys.hasMoreElements()) {
			Integer key = keys.nextElement();
			String element = Tick.TICK_TYPE_STRINGS.get(key);
			existingTickTypes[c0] = new SelectItem(key, element);
			c0++;
		}
	}

	public String navigateToAddNewCVI() {
		addCVIDataScrollingModel.getItems().clear();
		cvi = new CVI();
		tick = new Tick();
		existingTickAutoValues();
		initExistingTickTypes();
		return "addCVI";
	}

	@Override
	public void removeAll() {
		((ICMCompetencesManagement_Service) service).removeAllCVIs();
	}

	@SuppressWarnings("unchecked")
	public void removeSelectedTicksButtonListener(ActionEvent event) {
		ArrayList<Tick> items = addCVIDataScrollingModel.getItems();

		int c0 = 0;
		while (c0 < items.size()) {
			Tick item = items.get(c0);
			if (item.isSelected()) {
				items.remove(c0);
				cvi.removeTick(c0);
			} else {
				c0++;
			}
		}
	}

	public void setAddCVIDataScrollingModel(
			DataScrollingModel addCVIDataScrollingModel) {
		this.addCVIDataScrollingModel = addCVIDataScrollingModel;
	}

	public void setAddCVITabController(
			TabSetDefaultControllerImpl addCVITabController) {
		this.addCVITabController = addCVITabController;
	}

	@Override
	public void setCategoryId(Long categoryId) {
		cviCategoryController.setCategoryId(categoryId);
	}

	@Override
	public void setCategoryName(String categoryName) {
		cviCategoryController.setCategoryName(categoryName);
	}

	public void setCvi(ICVI cvi) {
		this.cvi = cvi;
	}

	public void setCviCategoryController(
			CMCategoryControllerImpl cviCategoryController) {
		this.cviCategoryController = cviCategoryController;
	}

	public void setCviController(CMCategoryControllerImpl cviController) {
		this.cviController = cviController;
	}

	@Override
	public void setEntity(CMQuestionnaireEntity entity) {
		CVI cviAux = (CVI) entity;
		cvi = cviAux;
	}

	public void setExistingTickAutoValues(SelectItem[] existingTickAutoValues) {
		this.existingTickAutoValues = existingTickAutoValues;
	}

	public void setExistingTickTypes(SelectItem[] existingTickTypes) {
		this.existingTickTypes = existingTickTypes;
	}

	@Override
	public void setSelectedCategory(ICategory category) {

	}

	public void setTick(ITick tick) {
		this.tick = tick;
	}

}
