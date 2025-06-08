package biz.sudden.userOrganizationManagement.userManagement.service.impl;

import java.util.List;

import biz.sudden.userOrganizationManagement.userManagement.domain.User2;
import biz.sudden.userOrganizationManagement.userManagement.domain.UserProfile;
import biz.sudden.userOrganizationManagement.userManagement.repository.IUserRepository;
import biz.sudden.userOrganizationManagement.userManagement.service.IUser;

public class UserImpl implements IUser {

	private String aux_companyName;
	private String company;
	private String companyId;
	private boolean companyManager;
	private String employeeName;

	private IUserRepository iUserRepository;

	public UserImpl() {
	}

	@Override
	public List<User2> getAllUsers() {
		return iUserRepository.retrieveAll();
	}

	@Override
	public User2 getUser(Long id) {
		return iUserRepository.retrieve(id);
	}

	@Override
	public User2 getUser(String id) {
		return getUser(Long.parseLong(id));
	}

	@Override
	public UserProfile getUserProfile(String userId) {
		return getUser(userId).getUserProfile();
	}

	@Override
	public UserProfile getUserProfile(Long userId) {
		return getUser(userId).getUserProfile();
	}

	@Override
	public User2 getUserOrganizationManager(String companyId) {
		return iUserRepository.getUserOrganizationManager(companyId);
	}

	@Override
	public boolean isUserCompanyManager(String usernameId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAux_companyName() {
		// TODO Auto-generated method stub
		return aux_companyName;
	}

	@Override
	public String getCompany() {
		// TODO Auto-generated method stub
		return company;
	}

	@Override
	public String getCompanyId() {
		// TODO Auto-generated method stub
		return companyId;
	}

	@Override
	public boolean getCompanyManager() {
		// TODO Auto-generated method stub
		return companyManager;
	}

	@Override
	public String getEmployeeName() {
		// TODO Auto-generated method stub
		return employeeName;
	}

	@Override
	public void setAux_companyName(String aux_companyName) {
		// TODO Auto-generated method stub
		this.aux_companyName = aux_companyName;
	}

	@Override
	public void setCompany(String company) {
		// TODO Auto-generated method stub
		this.company = company;
	}

	@Override
	public void setCompanyId(String companyId) {
		// TODO Auto-generated method stub
		this.companyId = companyId;
	}

	@Override
	public void setCompanyManager(boolean companyManager) {
		// TODO Auto-generated method stub
		this.companyManager = companyManager;
	}

	@Override
	public void setEmployeeName(String employeeName) {
		// TODO Auto-generated method stub
		this.employeeName = employeeName;
	}

	@Override
	public long addUser(String aux_companyName, String company,
			String companyId, boolean companyManager, String employeeName) {
		// TODO Auto-generated method stub
		return iUserRepository.addUser(aux_companyName, company, companyId,
				companyManager, employeeName);
	}

	public void setIUserRepository(IUserRepository userRepository) {
		iUserRepository = userRepository;
	}

}
