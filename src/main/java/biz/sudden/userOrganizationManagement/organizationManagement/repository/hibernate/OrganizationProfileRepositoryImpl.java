package biz.sudden.userOrganizationManagement.organizationManagement.repository.hibernate;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.OrganizationProfile;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationProfileRepository;

public class OrganizationProfileRepositoryImpl extends
		GenericRepositoryImpl<OrganizationProfile, Long> implements
		IOrganizationProfileRepository {

	Logger logger = Logger.getLogger(this.getClass());

	public OrganizationProfileRepositoryImpl(Class<OrganizationProfile> type) {
		super(type);
	}

	public OrganizationProfileRepositoryImpl() {
		super(OrganizationProfile.class);
	}

	@Override
	public long addNewProfileOrganization(String activities, String faxNumber,
			String name, int countryId, String phoneNumber,
			String socialAddress, char status, String webPage) {
		// TODO Auto-generated method stub

		OrganizationProfile organizationProfile = new OrganizationProfile();
		organizationProfile.setCActivities(activities);
		organizationProfile.setCFaxNumber(faxNumber);
		organizationProfile.setCName(name);
		organizationProfile.setCountryId(countryId);
		organizationProfile.setCPhoneNumber(phoneNumber);
		organizationProfile.setCSocialAddress(socialAddress);
		organizationProfile.setCStatus(status);
		organizationProfile.setCWebPage(webPage);

		long id = create(organizationProfile);

		logger.info("ORGANIZATIONS PROFILE");
		logger.info("Id: " + id);
		logger.info("Activities: " + organizationProfile.getCActivities());
		logger.info("Fax: " + organizationProfile.getCFaxNumber());
		logger.info("Name: " + organizationProfile.getCName());
		logger.info("Country id: " + organizationProfile.getCountryId());
		logger.info("Phone: " + organizationProfile.getCPhoneNumber());
		logger.info("Social Address: "
				+ organizationProfile.getCSocialAddress());
		logger.info("Status: " + organizationProfile.getCStatus());
		logger.info("Social Address: "
				+ organizationProfile.getCSocialAddress());
		logger.info("**************");

		return id;
	}

}
