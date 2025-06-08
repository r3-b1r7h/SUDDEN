package biz.sudden.userOrganizationManagement.userManagement.repository;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.userOrganizationManagement.userManagement.domain.UserAccount;

public interface IUserAccountRepository extends
		GenericRepository<UserAccount, Long> {

	// ------

	public long addUserAccount(String userName, String ePassword);

}
