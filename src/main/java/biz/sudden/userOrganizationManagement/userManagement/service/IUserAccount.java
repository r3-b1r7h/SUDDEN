package biz.sudden.userOrganizationManagement.userManagement.service;

public interface IUserAccount {

	public String getUserName();

	public String getEPassword();

	public String getPasswordRepeat();

	public void setUserName(String userName);

	public void setEPassword(String ePassword);

	public void setPasswordRepeat(String passwordRepeat);

	// ------

	public long addUserAccount(String userName, String ePassword);

}
