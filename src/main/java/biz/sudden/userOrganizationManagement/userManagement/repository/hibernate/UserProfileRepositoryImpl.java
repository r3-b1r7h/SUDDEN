package biz.sudden.userOrganizationManagement.userManagement.repository.hibernate;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.userOrganizationManagement.userManagement.domain.UserProfile;
import biz.sudden.userOrganizationManagement.userManagement.repository.IUserProfileRepository;

public class UserProfileRepositoryImpl extends
		GenericRepositoryImpl<UserProfile, Long> implements
		IUserProfileRepository {

	Logger logger = Logger.getLogger(this.getClass());

	public UserProfileRepositoryImpl(Class<UserProfile> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public UserProfileRepositoryImpl() {
		super(UserProfile.class);
	}

	@Override
	public long addUserProfile(String address, String aux_countryEmployee,
			String contactEmail, char contactWay, String countryId, String fax,
			String phone) {
		// TODO Auto-generated method stub
		UserProfile userProfile = new UserProfile();
		userProfile.setAddress(address);
		userProfile.setAux_countryEmployee(aux_countryEmployee);
		userProfile.setContactEmail(contactEmail);
		userProfile.setContactWay(contactWay);
		userProfile.setCountryId(countryId);
		userProfile.setFax(fax);
		userProfile.setPhone(phone);

		long id = create(userProfile);

		logger.info("USER PROFILE");
		logger.info("Id: " + id);
		logger.info("Address: " + userProfile.getAddress());
		logger
				.info("Country Employee: "
						+ userProfile.getAux_countryEmployee());
		logger.info("E-Mail: " + userProfile.getContactEmail());
		logger.info("Country Id: " + userProfile.getCountryId());
		logger.info("Fax: " + userProfile.getFax());
		logger.info("Phone: " + userProfile.getPhone());
		logger.info("***************");

		return id;
	}

}
