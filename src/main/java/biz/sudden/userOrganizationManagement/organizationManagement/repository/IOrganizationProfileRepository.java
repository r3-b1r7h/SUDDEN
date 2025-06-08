package biz.sudden.userOrganizationManagement.organizationManagement.repository;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.OrganizationProfile;

public interface IOrganizationProfileRepository extends
		GenericRepository<OrganizationProfile, Long> {

	// ---
	public long addNewProfileOrganization(String cActivities,
			String cFaxNumber, String cName, int countryId,
			String cPhoneNumber, String cSocialAddress, char cStatus,
			String cWebPage);
}
