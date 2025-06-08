package biz.sudden.userOrganizationManagement.organizationManagement.service.impl;

import java.util.List;

import biz.sudden.userOrganizationManagement.organizationManagement.domain.OrganizationProfile;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationProfileRepository;
import biz.sudden.userOrganizationManagement.organizationManagement.service.IOrganizationProfile;

public class OrganizationProfileImpl implements IOrganizationProfile {

	private String cActivities;
	private String cFaxNumber;
	private String cName;
	private int countryId;
	private String cPhoneNumber;
	private String cSocialAddress;
	private char cStatus;
	private String cWebPage;

	private IOrganizationProfileRepository IOrganizationProfileRepository;

	public OrganizationProfileImpl() {
	}

	@Override
	public String getCActivities() {
		// TODO Auto-generated method stub
		return cActivities;
	}

	@Override
	public String getCFaxNumber() {
		// TODO Auto-generated method stub
		return cFaxNumber;
	}

	@Override
	public String getCName() {
		// TODO Auto-generated method stub
		return cName;
	}

	@Override
	public String getCPhoneNumber() {
		// TODO Auto-generated method stub
		return cPhoneNumber;
	}

	@Override
	public String getCSocialAddress() {
		// TODO Auto-generated method stub
		return cSocialAddress;
	}

	@Override
	public char getCStatus() {
		// TODO Auto-generated method stub
		return cStatus;
	}

	@Override
	public String getCWebPage() {
		// TODO Auto-generated method stub
		return cWebPage;
	}

	@Override
	public int getCountryId() {
		// TODO Auto-generated method stub
		return countryId;
	}

	@Override
	public void setCActivities(String activities) {
		// TODO Auto-generated method stub
		this.cActivities = activities;
	}

	@Override
	public void setCFaxNumber(String faxNumber) {
		// TODO Auto-generated method stub
		this.cFaxNumber = faxNumber;
	}

	@Override
	public void setCName(String name) {
		// TODO Auto-generated method stub
		this.cName = name;
	}

	@Override
	public void setCPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		this.cPhoneNumber = phoneNumber;
	}

	@Override
	public void setCSocialAddress(String socialAddress) {
		// TODO Auto-generated method stub
		this.cSocialAddress = socialAddress;
	}

	@Override
	public void setCStatus(char status) {
		// TODO Auto-generated method stub
		this.cStatus = status;
	}

	@Override
	public void setCWebPage(String webPage) {
		// TODO Auto-generated method stub
		this.cWebPage = webPage;
	}

	@Override
	public void setCountryId(int countryId) {
		// TODO Auto-generated method stub
		this.countryId = countryId;
	}

	@Override
	public long addNewProfileOrganization(String activities, String faxNumber,
			String name, int countryId, String phoneNumber,
			String socialAddress, char status, String webPage) {
		// TODO Auto-generated method stub

		return IOrganizationProfileRepository.addNewProfileOrganization(
				cActivities, cFaxNumber, cName, countryId, cPhoneNumber,
				cSocialAddress, cStatus, cWebPage);
	}

	public void setIOrganizationProfileRepository(
			IOrganizationProfileRepository organizationsProfileRepository) {
		IOrganizationProfileRepository = organizationsProfileRepository;
	}

	public List<OrganizationProfile> retrieveAll() {
		return IOrganizationProfileRepository.retrieveAll();
	}

	public OrganizationProfile retrieveOrganizationProfile(Long id) {
		return IOrganizationProfileRepository.retrieve(id);
	}

}
