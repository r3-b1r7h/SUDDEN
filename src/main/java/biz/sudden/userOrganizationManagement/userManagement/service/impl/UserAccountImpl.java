package biz.sudden.userOrganizationManagement.userManagement.service.impl;

import biz.sudden.userOrganizationManagement.userManagement.repository.IUserAccountRepository;
import biz.sudden.userOrganizationManagement.userManagement.service.IUserAccount;

public class UserAccountImpl implements IUserAccount {

	// properties
	private String userName;
	private String ePassword;
	private String passwordRepeat;

	private IUserAccountRepository iUserAccountRepository;

	public UserAccountImpl() {
	}

	public void setIUserAccountRepository(
			IUserAccountRepository UserAccountRepository) {
		iUserAccountRepository = UserAccountRepository;
	}

	@Override
	public String getEPassword() {
		// TODO Auto-generated method stub
		return ePassword;
	}

	@Override
	public String getPasswordRepeat() {
		// TODO Auto-generated method stub
		return passwordRepeat;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public void setEPassword(String password) {
		// TODO Auto-generated method stub
		this.ePassword = password;
	}

	@Override
	public void setPasswordRepeat(String passwordRepeat) {
		// TODO Auto-generated method stub
		this.passwordRepeat = passwordRepeat;
	}

	@Override
	public void setUserName(String userName) {
		// TODO Auto-generated method stub
		this.userName = userName;

	}

	@Override
	public long addUserAccount(String userName, String ePassword) {
		// TODO Auto-generated method stub
		return iUserAccountRepository.addUserAccount(userName, ePassword);
	}

}
