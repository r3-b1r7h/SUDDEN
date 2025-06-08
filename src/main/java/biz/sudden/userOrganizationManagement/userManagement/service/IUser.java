package biz.sudden.userOrganizationManagement.userManagement.service;

import java.util.List;

import biz.sudden.userOrganizationManagement.userManagement.domain.User2;
import biz.sudden.userOrganizationManagement.userManagement.domain.UserProfile;

public interface IUser {

	User2 getUserOrganizationManager(String companyId);

	public List<User2> getAllUsers();

	public User2 getUser(String id);

	public User2 getUser(Long id);

	public UserProfile getUserProfile(String id);

	public UserProfile getUserProfile(Long id);

	boolean isUserCompanyManager(String usernameId) throws Exception;

	// -------------------
	public String getAux_companyName();

	public void setAux_companyName(String aux_companyName);

	public String getCompany();

	public void setCompany(String company);

	public String getCompanyId();

	public void setCompanyId(String companyId);

	public boolean getCompanyManager();

	public void setCompanyManager(boolean companyManager);

	public String getEmployeeName();

	public void setEmployeeName(String employeeName);

	public long addUser(String aux_companyName, String company,
			String companyId, boolean companyManager, String employeeName);

}
