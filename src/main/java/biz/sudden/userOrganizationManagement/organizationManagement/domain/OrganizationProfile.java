package biz.sudden.userOrganizationManagement.organizationManagement.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import biz.sudden.baseAndUtility.domain.IDInterface;

@SuppressWarnings("serial")
@Entity
public class OrganizationProfile implements IDInterface, Serializable {

	private String cActivities;
	private String cFaxNumber;
	private String cName;
	private int countryId;
	private String cPhoneNumber;
	private String cSocialAddress;
	private char cStatus;
	private String cWebPage;
	private Organization organization;

	private long id;

	public String getCActivities() {
		return cActivities;
	}

	public String getCFaxNumber() {
		return cFaxNumber;
	}

	public String getCName() {
		return cName;
	}

	public int getCountryId() {
		return countryId;
	}

	public String getCPhoneNumber() {
		return cPhoneNumber;
	}

	public String getCSocialAddress() {
		return cSocialAddress;
	}

	public char getCStatus() {
		return cStatus;
	}

	public String getCWebPage() {
		return cWebPage;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setCActivities(String activities) {
		cActivities = activities;
	}

	public void setCFaxNumber(String faxNumber) {
		cFaxNumber = faxNumber;
	}

	public void setCName(String name) {
		cName = name;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public void setCPhoneNumber(String phoneNumber) {
		cPhoneNumber = phoneNumber;
	}

	public void setCSocialAddress(String socialAddress) {
		cSocialAddress = socialAddress;
	}

	public void setCStatus(char status) {
		cStatus = status;
	}

	public void setCWebPage(String webPage) {
		cWebPage = webPage;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setOrganization(Organization org) {
		this.organization = org;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Organization getOrganization() {
		return this.organization;
	}

}
