package biz.sudden.userOrganizationManagement.userManagement.service;

public interface IUserProfile {

	public String getAddress();

	public String getAux_countryEmployee();

	public String getContactEmail();

	public char getContactWay();

	public String getCountryId();

	public String getFax();

	public String getPhone();

	public void setAddress(String address);

	public void setAux_countryEmployee(String aux_countryEmployee);

	public void setContactEmail(String contactEmail);

	public void setContactWay(char contactWay);

	public void setCountryId(String countryId);

	public void setFax(String fax);

	public void setPhone(String phone);

	// ------

	public long addUserProfile(String address, String aux_countryEmployee,
			String contactEmail, char contactWay, String countryId, String fax,
			String phone);

}
