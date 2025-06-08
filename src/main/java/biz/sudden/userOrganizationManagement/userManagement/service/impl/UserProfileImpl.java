package biz.sudden.userOrganizationManagement.userManagement.service.impl;

import biz.sudden.userOrganizationManagement.userManagement.repository.IUserProfileRepository;
import biz.sudden.userOrganizationManagement.userManagement.service.IUserProfile;

public class UserProfileImpl implements IUserProfile {

	// ---
	private String address;
	private String aux_countryEmployee;
	private String contactEmail;
	private char contactWay;
	private String countryId;
	private String fax;
	private String phone;

	private IUserProfileRepository iAddUserProfileRepository;

	public UserProfileImpl() {
	}

	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return address;
	}

	@Override
	public String getAux_countryEmployee() {
		// TODO Auto-generated method stub
		return aux_countryEmployee;
	}

	@Override
	public String getContactEmail() {
		// TODO Auto-generated method stub
		return contactEmail;
	}

	@Override
	public char getContactWay() {
		// TODO Auto-generated method stub
		return contactWay;
	}

	@Override
	public String getCountryId() {
		// TODO Auto-generated method stub
		return countryId;
	}

	@Override
	public String getFax() {
		// TODO Auto-generated method stub
		return fax;
	}

	@Override
	public String getPhone() {
		// TODO Auto-generated method stub
		return phone;
	}

	@Override
	public void setAddress(String address) {
		// TODO Auto-generated method stub
		this.address = address;
	}

	@Override
	public void setAux_countryEmployee(String aux_countryEmployee) {
		// TODO Auto-generated method stub
		this.aux_countryEmployee = aux_countryEmployee;
	}

	@Override
	public void setContactEmail(String contactEmail) {
		// TODO Auto-generated method stub
		this.contactEmail = contactEmail;
	}

	@Override
	public void setContactWay(char contactWay) {
		// TODO Auto-generated method stub
		this.contactWay = contactWay;
	}

	@Override
	public void setCountryId(String countryId) {
		// TODO Auto-generated method stub
		this.countryId = countryId;
	}

	@Override
	public void setFax(String fax) {
		// TODO Auto-generated method stub
		this.fax = fax;
	}

	@Override
	public void setPhone(String phone) {
		// TODO Auto-generated method stub
		this.phone = phone;
	}

	@Override
	public long addUserProfile(String address, String aux_countryEmployee,
			String contactEmail, char contactWay, String countryId, String fax,
			String phone) {
		// TODO Auto-generated method stub
		return iAddUserProfileRepository.addUserProfile(address,
				aux_countryEmployee, contactEmail, contactWay, countryId, fax,
				phone);
	}

	public void setIUserProfileRepository(
			IUserProfileRepository addUserProfileRepository) {
		iAddUserProfileRepository = addUserProfileRepository;
	}

}
