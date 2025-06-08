package biz.sudden.userOrganizationManagement.organizationManagement.service;

import java.util.List;

import biz.sudden.userOrganizationManagement.organizationManagement.domain.OrganizationProfile;

public interface IOrganizationProfile {

	public String getCActivities();

	public String getCFaxNumber();

	public String getCName();

	public int getCountryId();

	public String getCPhoneNumber();

	public String getCSocialAddress();

	public char getCStatus();

	public String getCWebPage();

	public void setCActivities(String activities);

	public void setCFaxNumber(String faxNumber);

	public void setCName(String name);

	public void setCountryId(int countryId);

	public void setCPhoneNumber(String phoneNumber);

	public void setCSocialAddress(String socialAddress);

	public void setCStatus(char status);

	public void setCWebPage(String webPage);

	// ---
	public long addNewProfileOrganization(String cActivities,
			String cFaxNumber, String cName, int countryId,
			String cPhoneNumber, String cSocialAddress, char cStatus,
			String cWebPage);

	public List<OrganizationProfile> retrieveAll();

	public OrganizationProfile retrieveOrganizationProfile(Long id);

}
