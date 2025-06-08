package biz.sudden.userOrganizationManagement.userManagement.web.controller;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.OrganizationProfile;
import biz.sudden.userOrganizationManagement.organizationManagement.service.IOrganization;
import biz.sudden.userOrganizationManagement.userManagement.domain.User2;
import biz.sudden.userOrganizationManagement.userManagement.service.IUser;
import biz.sudden.userOrganizationManagement.userManagement.service.IUserAccount;
import biz.sudden.userOrganizationManagement.userManagement.service.IUserProfile;

public class JSFUserController {

	private IUserAccount iUserAccount;
	private IUserProfile iUserProfile;
	private IUser iUser;
	private IOrganization iOrganization;
	private boolean buttonNextVisible = true;
	private boolean buttonRegisterVisible = true;
	private boolean comboCompaniesVisible = true;
	private String selectedCompany = "";
	long id;
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	// GETS AND SETS OF IUSERACCOUNT, IUSERPROFILE, IUSER AND IUSERLIST
	// ATTRIBUTES

	public String getUserName() {
		return iUserAccount.getUserName();
	}

	public String getEPassword() {
		return iUserAccount.getEPassword();
	}

	public String getPasswordRepeat() {
		return iUserAccount.getPasswordRepeat();
	}

	public String getAddress() {
		return iUserProfile.getAddress();
	}

	public String getAux_countryEmployee() {
		return iUserProfile.getAux_countryEmployee();
	}

	public String getContactEmail() {
		return iUserProfile.getContactEmail();
	}

	public char getContactWay() {
		return iUserProfile.getContactWay();
	}

	public String getCountryId() {
		return iUserProfile.getCountryId();
	}

	public String getFax() {
		return iUserProfile.getFax();
	}

	public String getPhone() {
		return iUserProfile.getPhone();
	}

	public String getAux_companyName() {
		return iUser.getAux_companyName();
	}

	public String getCompany() {
		return iUser.getCompany();
	}

	public String getCompanyId() {
		return iUser.getCompanyId();
	}

	public boolean getCompanyManager() {
		return iUser.getCompanyManager();
	}

	public String getEmployeeName() {
		return iUser.getEmployeeName();
	}

	// SETS AND SETS OF IUSERACCOUNT, IUSERPROFILE, IUSER AND IUSERLIST

	public void setUserName(String userName) {
		iUserAccount.setUserName(userName);
	}

	public void setEPassword(String ePassword) {
		iUserAccount.setEPassword(ePassword);
	}

	public void setPasswordRepeat(String passwordRepeat) {
		iUserAccount.setPasswordRepeat(passwordRepeat);
	}

	public void setAddress(String address) {
		iUserProfile.setAddress(address);
	}

	public void setAux_countryEmployee(String aux_countryEmployee) {
		iUserProfile.setAux_countryEmployee(aux_countryEmployee);
	}

	public void setContactEmail(String contactEmail) {
		iUserProfile.setContactEmail(contactEmail);
	}

	public void setContactWay(char contactWay) {
		iUserProfile.setContactWay(contactWay);
	}

	public void setCountryId(String countryId) {
		iUserProfile.setCountryId(countryId);
	}

	public void setFax(String fax) {
		iUserProfile.setFax(fax);
	}

	public void setPhone(String phone) {
		iUserProfile.setPhone(phone);
	}

	public void setAux_companyName(String aux_companyName) {
		iUser.setAux_companyName(aux_companyName);
	}

	public void setCompany(String company) {
		iUser.setCompany(company);
	}

	public void setCompanyId(String companyId) {
		iUser.setCompanyId(companyId);
	}

	public void setCompanyManager(boolean companyManager) {
		iUser.setCompanyManager(companyManager);
	}

	public void setEmployeeName(String employeeName) {
		iUser.setEmployeeName(employeeName);
	}

	// GETS AND SETS OF IUSERACCOUNT, IUSERPROFILE, IUSER AND IUSERLIST

	public void setIUserAccount(IUserAccount userAccount) {
		iUserAccount = userAccount;
	}

	public void setIUserProfile(IUserProfile userProfile) {
		iUserProfile = userProfile;
	}

	public void setIUser(IUser user) {
		iUser = user;
	}

	public void setIOrganization(IOrganization org) {
		iOrganization = org;
	}

	// BUSSINES FUNCTIONS

	public void addUser() {

		if (!iUserAccount.getEPassword().equals(
				iUserAccount.getPasswordRepeat())) {
			System.out.println("The password are not equals.");
			return;
		}

		userService.createUser(iUserAccount.getUserName(), iUserAccount
				.getEPassword(), iUserAccount.getUserName());

		id = iUser.addUser(iUser.getAux_companyName(), iUser.getCompany(),
				iUser.getCompanyId(), iUser.getCompanyManager(), iUser
						.getEmployeeName());

		id = iUserAccount.addUserAccount(iUserAccount.getUserName(),
				iUserAccount.getEPassword());
		id = iUserProfile.addUserProfile(iUserProfile.getAddress(),
				iUserProfile.getAux_countryEmployee(), iUserProfile
						.getContactEmail(), iUserProfile.getContactWay(),
				iUserProfile.getCountryId(), iUserProfile.getFax(),
				iUserProfile.getPhone());

	}

	// FUNCTIONS FOR ICEFACES SELECTS, VISIBLES, EVENTS... ETC

	private static final String COUNTRY_EMPTY = "---------";
	private static final String COUNTRY_AUSTRIA = "Austria";
	private static final String COUNTRY_BULGARIA = "Bulgaria";
	private static final String COUNTRY_CANADA = "Canada";
	private static final String COUNTRY_EEUU = "United States";
	private static final String COUNTRY_BELGIUM = "Belgium";
	private static final String COUNTRY_GERMANY = "Germany";
	private static final String COUNTRY_GREECE = "Greece";
	private static final String COUNTRY_FRANCE = "France";
	private static final String COUNTRY_SPAIN = "Spain";
	private static final String COUNTRY_UK = "United Kingdom";

	private static final SelectItem[] COUNTRY_ITEMS = new SelectItem[] {
			new SelectItem(0, COUNTRY_EMPTY),
			new SelectItem(43, COUNTRY_AUSTRIA),
			new SelectItem(359, COUNTRY_BULGARIA),
			new SelectItem(32, COUNTRY_BELGIUM),
			new SelectItem(124, COUNTRY_CANADA),
			new SelectItem(33, COUNTRY_FRANCE),
			new SelectItem(49, COUNTRY_GERMANY),
			new SelectItem(30, COUNTRY_GREECE),
			new SelectItem(724, COUNTRY_SPAIN),
			new SelectItem(840, COUNTRY_EEUU), new SelectItem(44, COUNTRY_UK) };

	private static final String BY_EMAIL = "E-mail";
	private static final String BY_PHONE = "Phone";

	private static final SelectItem[] CONTACT_WAY_ITEMS = new SelectItem[] {
			new SelectItem('e', BY_EMAIL), new SelectItem('p', BY_PHONE) };

	public SelectItem[] getCountryItems() {
		return COUNTRY_ITEMS;
	}

	public SelectItem[] getContactWayItems() {
		return CONTACT_WAY_ITEMS;
	}

	public void buttonVisible(ValueChangeEvent event) {

		if (event.getNewValue() != null
				&& !(event.getNewValue().toString().equals("0"))) {

			List<OrganizationProfile> org = iOrganization
					.retrieveAllOrganizationProfiles();

			SelectItem[] COMPANY_ITEMS = new SelectItem[org.size()];

			for (int i = 0; i < org.size(); i++) {
				COMPANY_ITEMS[i] = new SelectItem(org.get(i).getId(), org
						.get(i).getCName());
			}
			int i = Integer.parseInt(event.getNewValue().toString());
			i--;
			iUser.setCompany(COMPANY_ITEMS[i].getLabel());
		}

		if (event.getNewValue() == null) {
			iUser.setCompanyId("0");
		} else {
			iUser.setCompanyId(event.getNewValue().toString());
		}

		if (Integer.parseInt(iUser.getCompanyId()) > 0) {
			this.buttonNextVisible = false;
			this.buttonRegisterVisible = true;
		} else {
			this.buttonRegisterVisible = false;
			this.buttonNextVisible = true;
		}

	}

	public void changeCountryId(ValueChangeEvent event) {

		if (event.getNewValue() != null) {
			iUserProfile.setCountryId(event.getNewValue().toString());
		} else {
			iUserProfile.setCountryId("0");
		}

	}

	public boolean isButtonNextVisible() {
		return buttonNextVisible;
	}

	public void setButtonNextVisible(boolean buttonNextVisible) {
		this.buttonNextVisible = buttonNextVisible;
	}

	public boolean isButtonRegisterVisible() {
		return buttonRegisterVisible;
	}

	public void setButtonRegisterVisible(boolean buttonRegisterVisible) {
		this.buttonRegisterVisible = buttonRegisterVisible;
	}

	public boolean isComboCompaniesVisible() {
		return comboCompaniesVisible;
	}

	public void setComboCompaniesVisible(boolean comboCompaniesVisible) {
		this.comboCompaniesVisible = comboCompaniesVisible;
	}

	public List<User2> getAllUsers2() {
		return this.iUser.getAllUsers();
	}

	public SelectItem[] getAllUserSelect() {

		List<User2> us = getAllUsers2();

		SelectItem[] users = new SelectItem[us.size() + 1];
		users[0] = new SelectItem(0, "");

		for (int i = 1; i < us.size() + 1; i++) {
			users[i] = new SelectItem(us.get(i - 1).getId(), us.get(i - 1)
					.getEmployeeName());
		}
		return users;

	}

	public void changeContactWay(ValueChangeEvent event) {

		if (event.getNewValue() != null) {
			iUserProfile
					.setContactWay(event.getNewValue().toString().charAt(0));
		} else {
			iUserProfile.setContactWay(' ');
		}

	}

	public String getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(String selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public void menuRegisterButtonVisible(ActionEvent event) {

		this.buttonRegisterVisible = true;
		this.buttonNextVisible = false;
		this.comboCompaniesVisible = true;

	}

	public void menuNextButtonVisible(ActionEvent event) {

		this.buttonNextVisible = true;
		this.buttonRegisterVisible = false;
		this.comboCompaniesVisible = false;
	}

}
