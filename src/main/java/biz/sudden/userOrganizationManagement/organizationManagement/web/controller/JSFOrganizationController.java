package biz.sudden.userOrganizationManagement.organizationManagement.web.controller;

import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.CMControllerImpl;
import biz.sudden.knowledgeData.competencesManagement.web.controller.impl.CMInstancesControllerImpl;
import biz.sudden.knowledgeData.serviceManagement.web.controller.ServiceManagementController;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.OrganizationProfile;
import biz.sudden.userOrganizationManagement.organizationManagement.service.IOrganization;
import biz.sudden.userOrganizationManagement.organizationManagement.service.IOrganizationProfile;
import biz.sudden.userOrganizationManagement.userManagement.service.IUser;
import biz.sudden.userOrganizationManagement.userManagement.service.IUserAccount;
import biz.sudden.userOrganizationManagement.userManagement.service.IUserProfile;

public class JSFOrganizationController {

	private CMControllerImpl cmController;

	private CMInstancesControllerImpl cmInstanceController;
	long id;
	private IOrganization iOrganization;
	private IOrganizationProfile iOrganizationProfile;
	private IUser iUser;
	private IUserAccount iUserAccount;
	private IUserProfile iUserProfile;
	Logger logger = Logger.getLogger(this.getClass());
	private ServiceManagementController serviceManagementController;

	// SETS OF INTERFACES. REQUIRED BY CONTEXT

	public String addCompany() {

		logger.debug("\n\n\ngetCompanyId->" + iUser.getCompanyId());
		logger.debug("getCompanyManager->"
				+ iOrganization.getAux_companyManager());
		logger.debug("\n\n\n");

		if (!iUserAccount.getEPassword().equals(
				iUserAccount.getPasswordRepeat())) {
			logger.warn("The password are not equals.");
			return null;
		}
		iUser.setCompanyManager(false);

		iUser.setCompany(iOrganizationProfile.getCName());

		id = iUserAccount.addUserAccount(iUserAccount.getUserName(),
				iUserAccount.getEPassword());
		id = iUserProfile.addUserProfile(iUserProfile.getAddress(),
				iUserProfile.getAux_countryEmployee(), iUserProfile
						.getContactEmail(), iUserProfile.getContactWay(),
				iUserProfile.getCountryId(), iUserProfile.getFax(),
				iUserProfile.getPhone());

		iOrganization.setAux_companyManager("" + id);

		Organization or = iOrganization.addOrganization(iOrganization
				.getAux_companyManager(),
				iOrganization.getAux_countryCompany(), iOrganization
						.getAux_numberEmployeeByCompany(), iOrganization
						.getAux_userNameCompanyManager(), iOrganization
						.getCompanyId(), iOrganization.getAux_name());

		id = iOrganizationProfile.addNewProfileOrganization(
				iOrganizationProfile.getCActivities(), iOrganizationProfile
						.getCFaxNumber(), iOrganizationProfile.getCName(),
				iOrganizationProfile.getCountryId(), iOrganizationProfile
						.getCPhoneNumber(), iOrganizationProfile
						.getCSocialAddress(),
				iOrganizationProfile.getCStatus(), iOrganizationProfile
						.getCWebPage());

		iUser.setCompanyId("" + id);

		id = iUser.addUser(iOrganization.getAux_name(), iUser.getCompany(),
				iUser.getCompanyId(), iUser.getCompanyManager(), iUser
						.getEmployeeName());

		/**
		 * we now move to SM
		 * 
		 */

		serviceManagementController.setSelectedOrganisation(or);
		// cmInstanceController.setSelectedOrganization(or);
		cmInstanceController.setSelectedOrganizationId(or.getId());
		cmInstanceController.loadLevel0Questionnaire();

		return "productValueCreatingView";

		// usersession.navigateTo("productValueCreatingView",
		// ServiceManagementControllerImpl.class, "selectedOrganisation", or);

	}

	public void changeCountryId(ValueChangeEvent event) {

		if (event.getNewValue() != null) {
			iOrganizationProfile.setCountryId(Integer.parseInt(event
					.getNewValue().toString()));
		} else {
			iOrganizationProfile.setCountryId(0);
		}

	}

	public List<OrganizationProfile> getAllOrganizationProfiles() {
		return iOrganizationProfile.retrieveAll();
	}

	public List<Organization> getAllOrganizations() {
		return iOrganization.retrieveAll();
	}

	public SelectItem[] getAllOrganizationsSelect() {

		List<Organization> org = iOrganization.retrieveAll();

		SelectItem[] organizations = new SelectItem[org.size()];
		for (int i = 0; i < org.size(); i++) {
			organizations[i] = new SelectItem(org.get(i).getId(), org.get(i)
					.getAux_userNameCompanyManager());
		}
		return organizations;

	}

	public SelectItem[] getAllOrganizationsSelectProfile() {

		List<OrganizationProfile> org = iOrganizationProfile.retrieveAll();

		SelectItem[] organizations = new SelectItem[org.size()];

		for (int i = 0; i < org.size(); i++) {
			organizations[i] = new SelectItem(org.get(i).getId(), org.get(i)
					.getCName());
		}

		return organizations;

	}

	public String getAux_companyManager() {
		return iOrganization.getAux_companyManager();
	}

	public String getAux_countryCompany() {
		return iOrganization.getAux_countryCompany();
	}

	public String getAux_numberEmployeeByCompany() {
		return iOrganization.getAux_numberEmployeeByCompany();
	}

	public String getAux_userNameCompanyManager() {
		return iOrganization.getAux_userNameCompanyManager();
	}

	// GET AND SET OF INTERFACES ATTRIBUTES

	public String getCActivities() {
		return iOrganizationProfile.getCActivities();
	}

	public String getCFaxNumber() {
		return iOrganizationProfile.getCFaxNumber();
	}

	public CMControllerImpl getCmController() {
		return cmController;
	}

	public CMInstancesControllerImpl getCmInstanceController() {
		return cmInstanceController;
	}

	public String getCName() {
		return iOrganizationProfile.getCName();
	}

	public int getCompanyId() {
		return iOrganization.getCompanyId();
	}

	public int getCountryId() {
		return iOrganizationProfile.getCountryId();
	}

	public String getCPhoneNumber() {
		return iOrganizationProfile.getCPhoneNumber();
	}

	public String getCSocialAddress() {
		return iOrganizationProfile.getCSocialAddress();
	}

	public char getCStatus() {
		return iOrganizationProfile.getCStatus();
	}

	public String getCWebPage() {
		return iOrganizationProfile.getCWebPage();
	}

	public void setAux_companyManager(String aux_companyManager) {
		iOrganization.setAux_companyManager(aux_companyManager);
	}

	public void setAux_countryCompany(String aux_countryCompany) {
		iOrganization.setAux_countryCompany(aux_countryCompany);
	}

	public void setAux_numberEmployeeByCompany(
			String aux_numberEmployeeByCompany) {
		iOrganization
				.setAux_numberEmployeeByCompany(aux_numberEmployeeByCompany);
	}

	public void setAux_userNameCompanyManager(String aux_userNameCompanyManager) {
		iOrganization.setAux_userNameCompanyManager(aux_userNameCompanyManager);
	}

	public void setCActivities(String activities) {
		iOrganizationProfile.setCActivities(activities);
	}

	public void setCFaxNumber(String faxNumber) {
		iOrganizationProfile.setCFaxNumber(faxNumber);
	}

	public void setCmController(CMControllerImpl cmController) {
		this.cmController = cmController;
	}

	public void setCmInstanceController(
			CMInstancesControllerImpl cmInstanceController) {
		this.cmInstanceController = cmInstanceController;
	}

	public void setCName(String name) {
		iOrganizationProfile.setCName(name);
		iOrganization.setAux_name(name);
	}

	public void setCompanyId(int companyId) {
		iOrganization.setCompanyId(companyId);
	}

	public void setCountryId(int countryId) {
		iOrganizationProfile.setCountryId(countryId);
	}

	public void setCPhoneNumber(String phoneNumber) {
		iOrganizationProfile.setCPhoneNumber(phoneNumber);
	}

	public void setCSocialAddress(String cSocialAddress) {
		iOrganizationProfile.setCSocialAddress(cSocialAddress);
	}

	// BUSSINES METHODS

	public void setCStatus(char cStatus) {
		iOrganizationProfile.setCStatus(cStatus);
	}

	public void setCWebPage(String cWebPage) {
		iOrganizationProfile.setCWebPage(cWebPage);
	}

	public void setIOrganization(IOrganization organization) {
		iOrganization = organization;
	}

	// FUNCTIONS FOR ICEFACES SELECTS, VISIBLES, EVENTS... ETC

	public void setIOrganizationProfile(IOrganizationProfile organizationProfile) {
		iOrganizationProfile = organizationProfile;
	}

	public void setIUser(IUser user) {
		iUser = user;
	}

	public void setIUserAccount(IUserAccount userAccount) {
		iUserAccount = userAccount;
	}

	public void setIUserProfile(IUserProfile userProfile) {
		iUserProfile = userProfile;
	}

	public void setServiceManagementController(
			ServiceManagementController serviceManagementController) {
		this.serviceManagementController = serviceManagementController;
	}

}
