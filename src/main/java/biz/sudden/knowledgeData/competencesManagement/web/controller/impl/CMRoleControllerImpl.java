package biz.sudden.knowledgeData.competencesManagement.web.controller.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireEntity;
import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;
import biz.sudden.knowledgeData.competencesManagement.domain.Role;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleCompetence;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleDimension;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleQuestionnaire;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IRole;
import biz.sudden.knowledgeData.competencesManagement.service.ICMRolesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.web.controller.ICMController;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.DataScrollingModel;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.TabSetDefaultControllerImpl;

public class CMRoleControllerImpl extends CMControllerBaseClass implements
		ICMController {

	// Attributes to control UI presentation
	private DataScrollingModel addRoleDataScrollingModel;
	private TabSetDefaultControllerImpl addRoleTabController;

	// Beans
	private CMQuestionnaireControllerImpl cmQuestionnaireController;
	private IRole role;
	private CMCategoryControllerImpl roleCategoryController;
	private CMCategoryControllerImpl roleController;

	public CMRoleControllerImpl() {
		super();

		addRoleTabController = new TabSetDefaultControllerImpl();
		addRoleTabController.addTab("Attributes", "cmAddRoleTab1.jspx", true);
		addRoleTabController
				.addTab("Questionnaire", "cmAddRoleTab2.jspx", true);
		addRoleTabController.addTab("Categorize", "cmAddRoleTab3.jspx", true);
		addRoleTabController.addTab("Set Role Weights", "cmAddRoleTab4.jspx",
				true);

		addRoleDataScrollingModel = new DataScrollingModel(5);
	}

	@Override
	public void addCategory() {
		roleCategoryController.addCategory();
		roleController.buildEntityTree();
	}

	public void autoDistributeRoleWeightsButtonListener() {
		RoleQuestionnaire roleQuestionnaire = role.getRoleQuestionnaire();
		if (roleQuestionnaire.getQuestionnaire() != null) {
			float weightAccCom = 0;
			RoleCompetence lastRoleCompetence = new RoleCompetence();
			for (RoleCompetence roleCompetence : role.getRoleQuestionnaire()
					.getRoleCompetences()) {
				float weightAccDim = 0;
				RoleDimension lastRoleDimension = new RoleDimension();
				for (RoleDimension roleDimension : roleCompetence
						.getRoleDimensions()) {
					Float weight = (100.0f / roleCompetence.getRoleDimensions()
							.size());
					roleDimension.setWeight(Math.round(weight));
					weightAccDim += Math.round(weight);
					lastRoleDimension = roleDimension;
				}
				if (weightAccDim != 1) {
					lastRoleDimension.setWeight(100 - weightAccDim
							+ lastRoleDimension.getWeight());
				}
				Float weight = (100.0f / roleQuestionnaire.getRoleCompetences()
						.size());
				roleCompetence.setWeight(Math.round(weight));
				weightAccCom += Math.round(weight);
				lastRoleCompetence = roleCompetence;
			}
			if (weightAccCom != 1) {
				lastRoleCompetence.setWeight(100 - weightAccCom
						+ lastRoleCompetence.getWeight());
			}
			roleQuestionnaire.setWeight(1);
		}
	}

	public void setDefaultRoleWeightsButtonListener() {
		RoleQuestionnaire roleQuestionnaire = role.getRoleQuestionnaire();
		if (roleQuestionnaire.getQuestionnaire() != null) {
			for (RoleCompetence roleCompetence : role.getRoleQuestionnaire()
					.getRoleCompetences()) {
				for (RoleDimension roleDimension : roleCompetence
						.getRoleDimensions()) {
					roleDimension.setWeight(1);
				}
				roleCompetence.setWeight(1);
			}
			roleQuestionnaire.setWeight(1);
		}
	}

	public String addRole() {
		if (role.getRoleQuestionnaire().getQuestionnaire() != null) {
			ICategory category = roleCategoryController.getCategory();
			role.setCategoryId(category.getId());
			role.setCategoryName(category.getDescription());
			if (category.getId() == null) {
				role.setCategoryId(new Long(-1));
				role.setCategoryName("Root Category");
			}

			long id = ((ICMRolesManagement_Service) service)
					.addRole((Role) role);
			role.setId(id);
			roleController.buildEntityTree();

			return "roleAdded";
		} else {
			return "";
		}
	}

	@Override
	public void buildCategoryTree() {
		roleCategoryController.buildCategoryTree();
	}

	@Override
	public void buildEntityTree() {
		roleCategoryController.buildEntityTree();
	}

	private void buildRoleCompetences(RoleQuestionnaire roleQuestionnaire,
			Competence competence) {
		RoleCompetence roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competence);
		roleCompetence.setWeight(1);

		Iterator<Dimension> iterator = competence.getDimensions().iterator();
		while (iterator.hasNext()) {
			Dimension dimension = iterator.next();
			buildRoleDimensions(dimension, roleCompetence);
		}

		roleQuestionnaire.getRoleCompetences().add(roleCompetence);
	}

	private void buildRoleDimensions(Dimension dimension,
			RoleCompetence parentInstance) {
		RoleDimension roleDimension = new RoleDimension();
		roleDimension.setDimension(dimension);
		roleDimension.setWeight(1);

		parentInstance.getRoleDimensions().add(roleDimension);
	}

	private void buildRoleQuestionnaire(Questionnaire questionnaire) {
		RoleQuestionnaire roleQuestionnaire = new RoleQuestionnaire();
		roleQuestionnaire.setQuestionnaire(questionnaire);

		Iterator<Competence> iterator = questionnaire.getCompetences()
				.iterator();
		while (iterator.hasNext()) {
			Competence competence = iterator.next();
			buildRoleCompetences(roleQuestionnaire, competence);
		}

		role.setRoleQuestionnaire(roleQuestionnaire);
	}

	public DataScrollingModel getAddRoleDataScrollingModel() {
		return addRoleDataScrollingModel;
	}

	public TabSetDefaultControllerImpl getAddRoleTabController() {
		return addRoleTabController;
	}

	public List<Role> getAllRoles() {
		return ((ICMRolesManagement_Service) service).retrieveAllRoles();
	}

	@Override
	public ICMController getCategoryController() {
		return this.roleCategoryController;
	}

	@Override
	public ArrayList<CMQuestionnaireEntity> getCategoryEntities(
			Long categoryID, String categoryName) {
		List<Role> listOfRoles = ((ICMRolesManagement_Service) service)
				.retrieveAllRoles();
		ArrayList<CMQuestionnaireEntity> listOfEntities = new ArrayList<CMQuestionnaireEntity>();

		ListIterator<Role> iterator = listOfRoles.listIterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			if (role.getCategoryId().intValue() == categoryID.intValue()) {
				listOfEntities.add(role);
			}
		}

		return listOfEntities;
	}

	public CMQuestionnaireControllerImpl getCmQuestionnaireController() {
		return cmQuestionnaireController;
	}

	public CMCategoryControllerImpl getQuestionnaireCategoryController() {
		return roleCategoryController;
	}

	public CMCategoryControllerImpl getQuestionnaireController() {
		return roleController;
	}

	public IRole getRole() {
		return role;
	}

	public CMCategoryControllerImpl getRoleCategoryController() {
		return roleCategoryController;
	}

	public CMCategoryControllerImpl getRoleController() {
		return roleController;
	}

	public List<Role> getRolesByCategoryId() {
		return ((ICMRolesManagement_Service) service)
				.retrieveRolesByCategoryId(role.getCategoryId());
	}

	@Override
	public void init() {
		roleCategoryController.setEntityController(this);
		roleCategoryController.setService(this.service);
		roleCategoryController.setCategoryType(ICategory.CATEGORY_ROLE);
		roleCategoryController.buildCategoryTree();

		roleController.setEntityController(this);
		roleController.setService(this.service);
		roleController.setCategoryType(ICategory.CATEGORY_ROLE);
		roleController.buildEntityTree();
	}

	public String navigateToAddNewRole() {
		role = new Role();
		RoleQuestionnaire roleQuestionnaire = new RoleQuestionnaire();
		role.setRoleQuestionnaire(roleQuestionnaire);
		addRoleDataScrollingModel.getItems().clear();
		return "addRole";
	}

	public void refreshRoleButtonListener() {
		Questionnaire questionnaire = (Questionnaire) cmQuestionnaireController
				.getQuestionnaire();
		if (questionnaire.getId() != null) {
			// && (role.getRoleQuestionnaire().getQuestionnaire() == null ||
			// questionnaire
			// .getId() != role.getRoleQuestionnaire()
			// .getQuestionnaire().getId())) {
			questionnaire = cmQuestionnaireController
					.getQuestionnairesById(questionnaire.getId());
			role.getRoleQuestionnaire().setRoleCompetences(null);
			buildRoleQuestionnaire(questionnaire);
		} else {
			role.getRoleQuestionnaire().setQuestionnaire(null);
		}
	}

	@Override
	public void removeAll() {
		((ICMRolesManagement_Service) service).removeAllRoles();
	}

	public void setAddRoleDataScrollingModel(
			DataScrollingModel addRoleDataScrollingModel) {
		this.addRoleDataScrollingModel = addRoleDataScrollingModel;
	}

	public void setAddRoleTabController(
			TabSetDefaultControllerImpl addRoleTabController) {
		this.addRoleTabController = addRoleTabController;
	}

	@Override
	public void setCategoryId(Long categoryId) {
		roleCategoryController.setCategoryId(categoryId);
	}

	@Override
	public void setCategoryName(String categoryName) {
		roleCategoryController.setCategoryName(categoryName);
	}

	public void setCmQuestionnaireController(
			CMQuestionnaireControllerImpl cmQuestionnaireController) {
		this.cmQuestionnaireController = cmQuestionnaireController;
	}

	@Override
	public void setEntity(CMQuestionnaireEntity entity) {
		Role roleAux = (Role) entity;
		role = roleAux;
	}

	public void setRole(IRole role) {
		this.role = role;
	}

	public void setRoleCategoryController(
			CMCategoryControllerImpl roleCategoryController) {
		this.roleCategoryController = roleCategoryController;
	}

	public void setRoleController(CMCategoryControllerImpl roleController) {
		this.roleController = roleController;
	}

	@Override
	public void setSelectedCategory(ICategory category) {

	}

}