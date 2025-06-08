package biz.sudden.userOrganizationManagement.userManagement.repository.hibernate;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.userOrganizationManagement.userManagement.domain.User2;
import biz.sudden.userOrganizationManagement.userManagement.domain.UserProfile;
import biz.sudden.userOrganizationManagement.userManagement.repository.IUserRepository;

public class UserRepositoryImpl extends GenericRepositoryImpl<User2, Long>
		implements IUserRepository {

	Logger logger = Logger.getLogger(this.getClass());

	public UserRepositoryImpl(Class<User2> type) {
		super(type);
	}

	public UserRepositoryImpl() {
		super(User2.class);
	}

	@Override
	public User2 getUserOrganizationManager(String companyId) {
		// FIXME: not tested
		return retrieveByFieldName("companyId", companyId);
	}

	@Override
	public UserProfile getUserProfile(String userId) {
		return retrieve(Long.parseLong(userId)).getUserProfile();
	}

	@Override
	public boolean isUserCompanyManager(String usernameId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long addUser(String aux_companyName, String company,
			String companyId, boolean companyManager, String employeeName) {
		// TODO Auto-generated method stub
		User2 user = new User2();
		user.setAux_companyName(aux_companyName);
		user.setCompany(company);
		user.setCompanyId(companyId);
		user.setCompanyManager(companyManager);
		user.setEmployeeName(employeeName);

		logger.info("USER");
		logger.info("Aux_company Name: " + user.getAux_companyName());
		logger.info("Company: " + user.getCompany());
		logger.info("Company id: " + user.getCompanyId());
		logger.info("Company manager: " + user.getCompanyManager());
		logger.info("Employee name: " + user.getEmployeeName());

		long id = create(user);

		logger.info("id de User2: " + id);
		logger.info("**************");

		return id;
	}

}
