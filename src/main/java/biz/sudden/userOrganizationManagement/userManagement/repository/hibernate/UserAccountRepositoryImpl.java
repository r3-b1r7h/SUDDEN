package biz.sudden.userOrganizationManagement.userManagement.repository.hibernate;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.userOrganizationManagement.userManagement.domain.UserAccount;
import biz.sudden.userOrganizationManagement.userManagement.repository.IUserAccountRepository;

public class UserAccountRepositoryImpl extends
		GenericRepositoryImpl<UserAccount, Long> implements
		IUserAccountRepository {

	Logger logger = Logger.getLogger(this.getClass());

	public UserAccountRepositoryImpl(Class<UserAccount> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public UserAccountRepositoryImpl() {
		super(UserAccount.class);
	}

	@Override
	public long addUserAccount(String userName, String ePassword) {
		// TODO Auto-generated method stub
		UserAccount newUserAccount = new UserAccount();
		newUserAccount.setId(newUserAccount.getId());
		newUserAccount.setUserName(userName);
		newUserAccount.setEPassword(ePassword);

		long id = create(newUserAccount);
		logger.info("USER ACCOUNT");
		logger.info("Id: " + id);
		logger.info("User Name: " + newUserAccount.getUserName());
		logger.info("Password: " + newUserAccount.getEPassword());
		logger.info("***************");
		return id;

	}

}
