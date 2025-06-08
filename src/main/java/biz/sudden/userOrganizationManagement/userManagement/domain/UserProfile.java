package biz.sudden.userOrganizationManagement.userManagement.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import biz.sudden.baseAndUtility.domain.IDInterface;

@SuppressWarnings("serial")
@Entity
@Table(name = "USERS_PROFILE")
public class UserProfile implements IDInterface, Serializable {

	private String address;
	private String aux_countryEmployee;
	private String contactEmail;
	private char contactWay;
	private String countryId;
	private String fax;
	private String phone;

	private User2 user;
	private long id;

	public String getAddress() {
		return address;
	}

	public String getAux_countryEmployee() {
		return aux_countryEmployee;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public char getContactWay() {
		return contactWay;
	}

	public String getCountryId() {
		return countryId;
	}

	public String getFax() {
		return fax;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getPhone() {
		return phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAux_countryEmployee(String aux_countryEmployee) {
		this.aux_countryEmployee = aux_countryEmployee;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public void setContactWay(char contactWay) {
		this.contactWay = contactWay;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	// Persistence Annotations
	@Transient
	@OneToOne(cascade = CascadeType.ALL)
	public User2 getUser() {
		return user;
	}

	public void setUser(User2 user) {
		this.user = user;
	}

	public void setId(long id) {
		this.id = id;
	}

}
