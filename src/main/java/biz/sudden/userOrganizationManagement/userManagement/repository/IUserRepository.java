package biz.sudden.userOrganizationManagement.userManagement.repository;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.userOrganizationManagement.userManagement.domain.User2;
import biz.sudden.userOrganizationManagement.userManagement.domain.UserProfile;

public interface IUserRepository extends GenericRepository<User2, Long> {

	User2 getUserOrganizationManager(String companyId);

	UserProfile getUserProfile(String userId);

	boolean isUserCompanyManager(String usernameId) throws Exception;

	public long addUser(String aux_companyName, String company,
			String companyId, boolean companyManager, String employeeName);

}
