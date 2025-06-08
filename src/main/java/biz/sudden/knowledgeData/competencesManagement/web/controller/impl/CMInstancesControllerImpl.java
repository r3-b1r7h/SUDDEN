package biz.sudden.knowledgeData.competencesManagement.web.controller.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireEntity;
import biz.sudden.knowledgeData.competencesManagement.domain.CVIInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.CompetenceInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.DimensionInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;
import biz.sudden.knowledgeData.competencesManagement.domain.QuestionnaireInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Role;
import biz.sudden.knowledgeData.competencesManagement.domain.TickInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;
import biz.sudden.knowledgeData.competencesManagement.service.ICMInstancesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMQuestionnairesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMRolesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.web.controller.ICMController;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces.TabSetDefaultControllerImpl;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.ext.HtmlSelectBooleanCheckbox;
import com.icesoft.faces.component.ext.HtmlSelectOneRadio;
import com.icesoft.faces.component.panelcollapsible.PanelCollapsible;

public class CMInstancesControllerImpl extends CMControllerBaseClass implements
		ICMController {

	// Attributes to control UI presentation
	private SelectItem[] existingOrganizations = new SelectItem[] {};
	private SelectItem[] existingQuestionnaireInstances = new SelectItem[] {};
	private SelectItem[] existingQuestionnaires = new SelectItem[] {};
	private SelectItem[] existingRoles = new SelectItem[] {};
	private HtmlPanelGroup questionnaireHtmlPanel = new HtmlPanelGroup();
	private TabSetDefaultControllerImpl showQuestionnaireInstanceTabController = new TabSetDefaultControllerImpl();

	// Beans
	private CMCompetenceControllerImpl cmCompetenceController;
	private CMQuestionnaireControllerImpl cmQuestionnaireController;

	EnterpriseEvaluationService enterpriseEvalService;

	private CMRoleControllerImpl cmRoleController;
	private Organization selectedOrganization;
	private Long selectedOrganizationId;
	private Questionnaire selectedQuestionnaire;
	private Long selectedQuestionnaireId;
	private QuestionnaireInstance selectedQuestionnaireInstance;
	private Long selectedQuestionnaireInstanceId;
	private Role selectedRole;
	private Long selectedRoleId;

	@Override
	public void addCategory() {
	}

	@Override
	public void buildCategoryTree() {
	}

	private void buildCompetenceInstances(
			QuestionnaireInstance questionnaireInstance, UIComponent uiItem) {
		String uiItemId = uiItem.getId();
		long competenceId = Long.valueOf(uiItemId.substring(2, uiItemId
				.indexOf("_INDEX")));
		CompetenceInstance competenceInstance = new CompetenceInstance();
		competenceInstance.setCompetence(cmCompetenceController
				.getCompetenceById(competenceId));
		competenceInstance.setDate(questionnaireInstance.getDate());
		competenceInstance.setOrganization(questionnaireInstance
				.getOrganization());
		competenceInstance.setWeight(1);

		Iterator<UIComponent> iterator = uiItem.getChildren().iterator();
		while (iterator.hasNext()) {
			UIComponent item = iterator.next();
			String itemId = item.getId();
			if (itemId.indexOf(("_DIM_")) != -1) {
				buildDimensionInstances(questionnaireInstance, item,
						competenceInstance);
			}
		}

		competenceInstance.calculateValue();

		((ICMInstancesManagement_Service) service)
				.addCompetenceInstance(competenceInstance);
		questionnaireInstance.getCompetenceInstances().add(competenceInstance);
	}

	private void buildCVIInstances(QuestionnaireInstance questionnaireInstance,
			UIComponent uiItem, CVIInstance cviInstance) {

		Iterator<UIComponent> iterator = uiItem.getChildren().iterator();
		while (iterator.hasNext()) {
			UIComponent item = iterator.next();
			String itemId = item.getId();
			if (itemId.indexOf(("_T_")) != -1) {
				buildTickInstances(questionnaireInstance, item, cviInstance);
			} else if (itemId.indexOf("CVI_RADIO_") != -1) {
				Iterator<UIComponent> iteratorRadioChildren = item
						.getChildren().iterator();
				while (iteratorRadioChildren.hasNext()) {
					UIComponent radioChild = iteratorRadioChildren.next();
					buildTickInstances(questionnaireInstance, radioChild,
							cviInstance);
				}
			}
		}
	}

	private void buildDimensionInstances(
			QuestionnaireInstance questionnaireInstance, UIComponent uiItem,
			CompetenceInstance parentInstance) {
		String uiItemId = uiItem.getId();
		long dimensionId = Long.valueOf(uiItemId.substring(uiItemId
				.indexOf("DIM_") + 4));
		DimensionInstance dimensionInstance = new DimensionInstance();
		dimensionInstance.setDimension(cmCompetenceController
				.getDimensionById(dimensionId));
		dimensionInstance.setDate(questionnaireInstance.getDate());
		dimensionInstance.setOrganization(questionnaireInstance
				.getOrganization());
		dimensionInstance.setWeight(1);

		CVIInstance cviInstance = null;

		Iterator<UIComponent> iterator = uiItem.getChildren().iterator();
		if (iterator.hasNext()) {
			cviInstance = new CVIInstance();
			while (iterator.hasNext()) {
				UIComponent item = iterator.next();
				String itemId = item.getId();
				if (itemId.indexOf(("_CVI_")) != -1) {
					long cviId = Long.valueOf(itemId.substring(itemId
							.indexOf("_CVI_") + 5));
					cviInstance
							.setCvi(cmCompetenceController.getCVIById(cviId));
					cviInstance.setDate(questionnaireInstance.getDate());
					cviInstance.setOrganization(questionnaireInstance
							.getOrganization());
					cviInstance.setWeight(1);
					buildCVIInstances(questionnaireInstance, item, cviInstance);
					cviInstance.calculateValue();
				}
			}
		}

		if (cviInstance != null) {
			((ICMInstancesManagement_Service) service)
					.addCVIInstance(cviInstance);
			dimensionInstance.setCviInstance(cviInstance);
			dimensionInstance.calculateValue();

			// for georgs stuff
			enterpriseEvalService.associateOccurence(dimensionInstance
					.getValue(), dimensionInstance.getDimension(), "Double",
					enterpriseEvalService.retrieveScopeBy(dimensionInstance
							.getOrganization().getName()));
		}

		((ICMInstancesManagement_Service) service)
				.addDimensionInstance(dimensionInstance);
		parentInstance.getDimensionInstances().add(dimensionInstance);
	}

	@Override
	public void buildEntityTree() {

	}

	private void buildQuestionnaireInstance(
			QuestionnaireInstance questionnaireInstance, UIComponent uiItem) {
		Iterator<UIComponent> iterator = uiItem.getChildren().iterator();

		while (iterator.hasNext()) {
			UIComponent item = iterator.next();
			if (item.getClass().getName().indexOf("PanelCollapsible") != -1) {
				String itemId = item.getId();
				((PanelCollapsible) item).setExpanded(true);
				if (itemId.indexOf(("C_")) != -1) {
					buildCompetenceInstances(questionnaireInstance, item);
				} else {
					buildQuestionnaireInstance(questionnaireInstance, item);
				}
			}
		}

		questionnaireInstance.calculateValue();
	}

	private void buildTickInstances(
			QuestionnaireInstance questionnaireInstance, UIComponent uiItem,
			CVIInstance parentInstance) {
		String uiItemId = uiItem.getId();
		long tickId = Long.valueOf(uiItemId
				.substring(uiItemId.indexOf("_T_") + 3));
		if (uiItemId.indexOf(("CVI_CHECKBOX_")) != -1) {
			TickInstance tickInstance = new TickInstance();
			tickInstance.setTick(cmCompetenceController.getTickById(tickId));
			tickInstance.setDate(questionnaireInstance.getDate());
			tickInstance.setOrganization(questionnaireInstance
					.getOrganization());
			tickInstance.setWeight(1);

			String value = "";
			tickInstance.setValue("false");
			if (((HtmlSelectBooleanCheckbox) uiItem).getValue() != tickInstance
					.getTick().getTTextValue()) {
				value = ((HtmlSelectBooleanCheckbox) uiItem).getValue()
						.toString();
				tickInstance.setValue(value);
			} else if (((HtmlSelectBooleanCheckbox) uiItem).getSubmittedValue() != null) {
				value = ((HtmlSelectBooleanCheckbox) uiItem)
						.getSubmittedValue().toString();
				tickInstance.setValue(value);
			}

			tickInstance.calculateValue();

			((ICMInstancesManagement_Service) service)
					.addTickInstance(tickInstance);
			parentInstance.getTicks().add(tickInstance);
		}
		if (uiItemId.indexOf(("CVI_INPUTTEXT_")) != -1) {
			TickInstance tickInstance = new TickInstance();
			tickInstance.setTick(cmCompetenceController.getTickById(tickId));
			tickInstance.setDate(questionnaireInstance.getDate());
			tickInstance.setOrganization(questionnaireInstance
					.getOrganization());
			tickInstance.setWeight(1);

			String value = "";
			if (((HtmlInputText) uiItem).getValue() != null
					&& ((HtmlInputText) uiItem).getValue() != "") {
				value = ((HtmlInputText) uiItem).getValue().toString();
			} else if (((HtmlInputText) uiItem).getSubmittedValue() != null) {
				value = ((HtmlInputText) uiItem).getSubmittedValue().toString();
			}
			tickInstance.setValue(value);
			tickInstance.calculateValue();

			((ICMInstancesManagement_Service) service)
					.addTickInstance(tickInstance);
			parentInstance.getTicks().add(tickInstance);
		}
		if (uiItemId.indexOf(("CVI_RADIO_")) != -1) {
			TickInstance tickInstance = new TickInstance();
			tickInstance.setTick(cmCompetenceController.getTickById(tickId));
			tickInstance.setDate(questionnaireInstance.getDate());
			tickInstance.setOrganization(questionnaireInstance
					.getOrganization());
			tickInstance.setWeight(1);
			UISelectItem item = (UISelectItem) uiItem;
			HtmlSelectOneRadio parentItem = (HtmlSelectOneRadio) item
					.getParent();

			String value = "";
			tickInstance.setValue("NOT SELECTED");
			if ((parentItem).getValue() != null
					&& (parentItem).getValue() != "") {
				value = (parentItem).getValue().toString();
				if (value.equalsIgnoreCase(item.getId())) {
					tickInstance.setValue("SELECTED");
				}
			} else if ((parentItem).getSubmittedValue() != null) {
				value = (parentItem).getSubmittedValue().toString();
				if (value.equalsIgnoreCase(item.getId())) {
					tickInstance.setValue("SELECTED");
				}
			}

			tickInstance.calculateValue();
			((ICMInstancesManagement_Service) service)
					.addTickInstance(tickInstance);
			parentInstance.getTicks().add(tickInstance);
		}
	}

	public List<CompetenceInstance> getAllCompetenceInstances() {
		return ((ICMInstancesManagement_Service) service)
				.retrieveAllCompetenceInstances();
	}

	public List<DimensionInstance> getAllDimensionInstances() {
		return ((ICMInstancesManagement_Service) service)
				.retrieveAllDimensionInstances();
	}

	@Override
	public ICMController getCategoryController() {
		return null;
	}

	@Override
	public ArrayList<CMQuestionnaireEntity> getCategoryEntities(
			Long categoryID, String categoryName) {
		return null;
	}

	public CMCompetenceControllerImpl getCmCompetenceController() {
		return cmCompetenceController;
	}

	public CMQuestionnaireControllerImpl getCmQuestionnaireController() {
		return cmQuestionnaireController;
	}

	public CMRoleControllerImpl getCmRoleController() {
		return cmRoleController;
	}

	public SelectItem[] getExistingOrganizations() {
		return existingOrganizations;
	}

	public SelectItem[] getExistingQuestionnaireInstances() {
		return existingQuestionnaireInstances;
	}

	public SelectItem[] getExistingQuestionnaires() {
		return existingQuestionnaires;
	}

	public SelectItem[] getExistingRoles() {
		return existingRoles;
	}

	public HtmlPanelGroup getQuestionnaireHtmlPanel() {
		return questionnaireHtmlPanel;
	}

	public Organization getSelectedOrganization() {
		return selectedOrganization;
	}

	public Long getSelectedOrganizationId() {
		return selectedOrganizationId;
	}

	public Questionnaire getSelectedQuestionnaire() {
		return selectedQuestionnaire;
	}

	public Long getSelectedQuestionnaireId() {
		return selectedQuestionnaireId;
	}

	public QuestionnaireInstance getSelectedQuestionnaireInstance() {
		return selectedQuestionnaireInstance;
	}

	public Long getSelectedQuestionnaireInstanceId() {
		return selectedQuestionnaireInstanceId;
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	public Long getSelectedRoleId() {
		return selectedRoleId;
	}

	public TabSetDefaultControllerImpl getShowQuestionnaireInstanceTabController() {
		return showQuestionnaireInstanceTabController;
	}

	@Override
	public void init() {

	}

	public void initExistingOrganizations() {
		List<Organization> organizations = ((ICMQuestionnairesManagement_Service) cmQuestionnaireController
				.getService()).getOrganizationRepository().retrieveAll();
		if (organizations != null && organizations.size() > 0) {
			existingOrganizations = new SelectItem[organizations.size()];
			for (int i = 0; i < organizations.size(); i++) {
				existingOrganizations[i] = new SelectItem(organizations.get(i)
						.getId(), organizations.get(i).getName());
			}
			if (organizations.size() > 0) {
				setSelectedOrganizationId(organizations.get(0).getId());
			}
		}
	}

	public void initExistingOrganizationsWithInstances() {
		List<Organization> organizations = ((ICMQuestionnairesManagement_Service) cmQuestionnaireController
				.getService()).getOrganizationRepository().retrieveAll();
		if (organizations != null && organizations.size() > 0) {
			List<Organization> organizationsWithInstances = new ArrayList<Organization>();
			List<QuestionnaireInstance> questionnaireInstances = ((ICMInstancesManagement_Service) service)
					.retrieveAllQuestionnaireInstances();
			for (QuestionnaireInstance questionnaireInstance : questionnaireInstances) {
				Organization organization = questionnaireInstance
						.getOrganization();
				if (!organizationsWithInstances.contains(organization)) {
					organizationsWithInstances.add(organization);
				}
			}
			existingOrganizations = new SelectItem[organizationsWithInstances
					.size()];
			for (int i = 0; i < organizationsWithInstances.size(); i++) {
				existingOrganizations[i] = new SelectItem(
						organizationsWithInstances.get(i).getId(),
						organizationsWithInstances.get(i).getName());
			}
			if (organizationsWithInstances.size() > 0) {
				setSelectedOrganizationId(organizationsWithInstances.get(0)
						.getId());
			}
		}
	}

	public void initExistingQuestionnaireInstances() {
		List<QuestionnaireInstance> questionnaireInstances = ((ICMInstancesManagement_Service) service)
				.retrieveAllQuestionnaireInstancesByOrganization(selectedOrganizationId);
		if (questionnaireInstances != null && questionnaireInstances.size() > 0) {
			existingQuestionnaireInstances = new SelectItem[questionnaireInstances
					.size()];
			for (int i = 0; i < questionnaireInstances.size(); i++) {
				existingQuestionnaireInstances[i] = new SelectItem(
						questionnaireInstances.get(i).getId(),
						questionnaireInstances.get(i).getQuestionnaire()
								.getName());
			}
			if (questionnaireInstances.size() > 0) {
				setSelectedQuestionnaireInstanceId(questionnaireInstances
						.get(0).getId());
			}
		}
	}

	public void initExistingQuestionnaires() {
		List<Questionnaire> questionnaires = ((ICMQuestionnairesManagement_Service) cmQuestionnaireController
				.getService()).getQuestionnaireRepository()
				.getAllQuestionnaires();
		if (questionnaires != null && questionnaires.size() > 0) {
			existingQuestionnaires = new SelectItem[questionnaires.size()];
			for (int i = 0; i < questionnaires.size(); i++) {
				existingQuestionnaires[i] = new SelectItem(questionnaires
						.get(i).getId(), questionnaires.get(i).getName());
			}
			if (questionnaires.size() > 0) {
				setSelectedQuestionnaireId(questionnaires.get(0).getId());
				selectedQuestionnaire.toHtml(questionnaireHtmlPanel);
			}
		}
	}

	public void initExistingRoles() {
		List<Role> roles = null;
		if (selectedQuestionnaireInstance != null) {
			roles = ((ICMRolesManagement_Service) cmRoleController.getService())
					.retrieveRolesByQuestionnaireId(selectedQuestionnaireInstance
							.getQuestionnaire().getId());
			existingRoles = new SelectItem[roles.size() + 1];
			existingRoles[0] = new SelectItem(new Long(-1), "Non-Weighted Role");
			if (roles != null && roles.size() > 0) {
				for (int i = 0; i < roles.size(); i++) {
					existingRoles[i + 1] = new SelectItem(roles.get(i).getId(),
							roles.get(i).getName());
				}
			}
			setSelectedRole(null);
			setSelectedRoleId(new Long(-1));
		}
	}

	public void loadLevel0Questionnaire() {
		List<Questionnaire> questionnaires = ((ICMQuestionnairesManagement_Service) cmQuestionnaireController
				.getService()).getQuestionnaireRepository()
				.getAllQuestionnaires();
		for (Questionnaire questionnaire : questionnaires) {
			String questionnaireName = questionnaire.getName();
			if (questionnaireName.equals("EL-0 Questionnaire")) {
				setSelectedQuestionnaire(questionnaire);
				selectedQuestionnaireId = questionnaire.getId();
				selectedQuestionnaire.toHtml(questionnaireHtmlPanel);
			}
		}
	}

	public String navigateToBrowseQuestionnaireInstances() {
		initExistingOrganizationsWithInstances();
		initExistingQuestionnaires();
		return "browseQuestionnaireInstances";
	}

	public String navigateToBrowseQuestionnaireInstancesNoCVIsTicks() {
		initExistingOrganizationsWithInstances();
		initExistingQuestionnaires();
		return "browseQuestionnaireInstancesNoCVIsTicks";
	}

	public String navigateToFillInQuestionnaires() {
		initExistingOrganizations();
		initExistingQuestionnaires();
		return "fillInQuestionnaires";
	}

	public void refreshSelectedQuestionnaireHtmlButtonListener(ActionEvent event) {
		selectedQuestionnaire = ((ICMQuestionnairesManagement_Service) cmQuestionnaireController
				.getService())
				.retrieveQuestionnaireById(selectedQuestionnaireId);
		selectedQuestionnaire.toHtml(questionnaireHtmlPanel);
	}

	@Override
	public void removeAll() {
		((ICMInstancesManagement_Service) service).removeAllInstances();
	}

	@Override
	public void setCategoryId(Long categoryId) {

	}

	@Override
	public void setCategoryName(String categoryName) {

	}

	public void setCmCompetenceController(
			CMCompetenceControllerImpl cmCompetenceController) {
		this.cmCompetenceController = cmCompetenceController;
	}

	public void setCmQuestionnaireController(
			CMQuestionnaireControllerImpl cmQuestionnaireController) {
		this.cmQuestionnaireController = cmQuestionnaireController;
	}

	public void setCmRoleController(CMRoleControllerImpl cmRoleController) {
		this.cmRoleController = cmRoleController;
	}

	public void setEnterpriseEvalService(EnterpriseEvaluationService evalservice) {
		this.enterpriseEvalService = evalservice;
	}

	@Override
	public void setEntity(CMQuestionnaireEntity entity) {

	}

	public void setExistingOrganizations(SelectItem[] existingOrganizations) {
		this.existingOrganizations = existingOrganizations;
	}

	public void setExistingQuestionnaireInstances(
			SelectItem[] existingQuestionnaireInstances) {
		this.existingQuestionnaireInstances = existingQuestionnaireInstances;
	}

	public void setExistingQuestionnaires(SelectItem[] existingQuestionnaires) {
		this.existingQuestionnaires = existingQuestionnaires;
	}

	public void setExistingRoles(SelectItem[] existingRoles) {
		this.existingRoles = existingRoles;
	}

	public void setQuestionnaireHtmlPanel(HtmlPanelGroup questionnaireHtmlPanel) {
		this.questionnaireHtmlPanel = questionnaireHtmlPanel;
	}

	@Override
	public void setSelectedCategory(ICategory category) {

	}

	public void setSelectedOrganization(Organization selectedOrganization) {
		this.selectedOrganization = selectedOrganization;
		initExistingQuestionnaireInstances();
		initExistingRoles();
	}

	public void setSelectedOrganizationId(Long selectedOrganizationId) {
		this.selectedOrganizationId = selectedOrganizationId;
		List<Organization> organizations = ((ICMQuestionnairesManagement_Service) cmQuestionnaireController
				.getService()).getOrganizationRepository().retrieveAll();
		for (Organization organization : organizations) {
			if (organization.getId().longValue() == selectedOrganizationId
					.longValue()) {
				setSelectedOrganization(organization);
			}
		}
	}

	public void setSelectedQuestionnaire(Questionnaire selectedQuestionnaire) {
		this.selectedQuestionnaire = selectedQuestionnaire;
	}

	public void setSelectedQuestionnaireId(Long selectedQuestionnaireId) {
		this.selectedQuestionnaireId = selectedQuestionnaireId;
		List<Questionnaire> questionnaires = ((ICMQuestionnairesManagement_Service) cmQuestionnaireController
				.getService()).getQuestionnaireRepository()
				.getAllQuestionnaires();
		for (Questionnaire questionnaire : questionnaires) {
			if (questionnaire.getId().longValue() == selectedQuestionnaireId
					.longValue()) {
				setSelectedQuestionnaire(questionnaire);
			}
		}
	}

	public void setSelectedQuestionnaireInstance(
			QuestionnaireInstance selectedQuestionnaireInstance) {
		this.selectedQuestionnaireInstance = selectedQuestionnaireInstance;
		initExistingRoles();
	}

	public void setSelectedQuestionnaireInstanceId(
			Long selectedQuestionnaireInstanceId) {
		this.selectedQuestionnaireInstanceId = selectedQuestionnaireInstanceId;
		List<QuestionnaireInstance> questionnaireInstances = ((ICMInstancesManagement_Service) service)
				.retrieveAllQuestionnaireInstancesByOrganization(selectedOrganizationId);
		for (QuestionnaireInstance questionnaireInstance : questionnaireInstances) {
			if (questionnaireInstance.getId().longValue() == selectedQuestionnaireInstanceId
					.longValue()) {
				setSelectedQuestionnaireInstance(questionnaireInstance);
			}
		}
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
		showQuestionnaireInstanceTabController = new TabSetDefaultControllerImpl();
		showQuestionnaireInstanceTabController.addTab("Questionnaire Instance",
				"cmShowQuestionnaireInstance.jspx", true);
	}

	public void setSelectedRoleId(Long selectedRoleId) {
		List<Role> roles = ((ICMRolesManagement_Service) cmRoleController
				.getService())
				.retrieveRolesByQuestionnaireId(selectedQuestionnaireInstance
						.getQuestionnaire().getId());
		for (Role role : roles) {
			if (role.getId().longValue() == selectedRoleId.longValue()) {
				setSelectedRole(role);
			}
		}
		if (selectedRoleId != null) {
			switch (selectedRoleId.intValue()) {
			case -1:
				((ICMRolesManagement_Service) cmRoleController.getService())
						.applyNonWeightedRole(selectedQuestionnaireInstance);
				break;
			default:
				((ICMRolesManagement_Service) cmRoleController.getService())
						.applyRole(selectedRole, selectedQuestionnaireInstance);
				break;
			}
		}
		this.selectedRoleId = selectedRoleId;
	}

	public void setShowQuestionnaireInstanceTabController(
			TabSetDefaultControllerImpl showQuestionnaireInstanceTabController) {
		this.showQuestionnaireInstanceTabController = showQuestionnaireInstanceTabController;
	}

	public String submitQuestionnaire() {
		QuestionnaireInstance questionnaireInstance = new QuestionnaireInstance();
		questionnaireInstance.setQuestionnaire(selectedQuestionnaire);
		questionnaireInstance.setOrganization(selectedOrganization);
		questionnaireInstance.setWeight(1);
		questionnaireInstance.setDate(new java.util.Date());
		buildQuestionnaireInstance(questionnaireInstance,
				questionnaireHtmlPanel);
		((ICMInstancesManagement_Service) service)
				.addQuestionnaireInstance(questionnaireInstance);
		selectedQuestionnaire.toHtml(questionnaireHtmlPanel);

		return "questionnaireSubmitted";
	}

}
