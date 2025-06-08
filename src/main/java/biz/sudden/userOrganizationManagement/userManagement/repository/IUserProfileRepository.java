package biz.sudden.userOrganizationManagement.userManagement.repository;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.userOrganizationManagement.userManagement.domain.UserProfile;

public interface IUserProfileRepository extends
		GenericRepository<UserProfile, Long> {

	public long addUserProfile(String address, String aux_countryEmployee,
			String contactEmail, char contactWay, String countryId, String fax,
			String phone);
}
