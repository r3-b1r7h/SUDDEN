package biz.sudden.userOrganizationManagement.organizationManagement.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import biz.sudden.baseAndUtility.domain.Actor;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;

@SuppressWarnings("serial")
@Entity
@Table(name = "ORGANIZATIONS")
public class Organization extends Actor implements Serializable {

	private String aux_companyManager;
	private String aux_countryCompany;
	private String aux_numberEmployeeByCompany;
	private String aux_userNameCompanyManager;
	private int companyId = 0;
	private int partsNumber = 1;
	private int materialProcessing = 2;
	private int materialNumber = 3;
	private int machineryTypes = 4;

	private OrganizationProfile organizationProfile;

	private OrganizationsList organizationsList;

	/**
	 * @return Returns the aux_companyManager.
	 */
	@Column(name = "COMPANY_MANAGER")
	public String getAux_companyManager() {
		return aux_companyManager;
	}

	/**
	 * @return Returns the aux_countryCompany.
	 */
	@Column(name = "COUNTRY_COMPANY")
	public String getAux_countryCompany() {
		return aux_countryCompany;
	}

	@Column(name = "NUMBER_EMPLOYYE_COMPANY")
	public String getAux_numberEmployeeByCompany() {
		return aux_numberEmployeeByCompany;
	}

	@Column(name = "COMP_MANAGER_USER_NAME")
	public String getAux_userNameCompanyManager() {
		return aux_userNameCompanyManager;
	}

	@Column(name = "COMPANY_ID")
	public int getCompanyId() {
		// CompanyID is not the hibernate id
		// companyId = (long) getId();
		return companyId;
	}

	/**
	 * @param aux_companyManager
	 *            The aux_companyManager to set.
	 */
	public void setAux_companyManager(String aux_companyManager) {
		this.aux_companyManager = aux_companyManager;
	}

	/**
	 * @param aux_countryCompany
	 *            The aux_countryCompany to set.
	 */
	public void setAux_countryCompany(String aux_countryCompany) {
		this.aux_countryCompany = aux_countryCompany;
	}

	public void setAux_numberEmployeeByCompany(
			String aux_numberEmployeeByCompany) {
		this.aux_numberEmployeeByCompany = aux_numberEmployeeByCompany;
	}

	/**
	 * @param aux_userNameCompanyManager
	 *            The aux_userNameCompanyManager to set.
	 */
	public void setAux_userNameCompanyManager(String aux_userNameCompanyManager) {
		this.aux_userNameCompanyManager = aux_userNameCompanyManager;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	// Relation Annotations

	@ManyToOne
	public OrganizationsList getOrganizationsList() {
		return organizationsList;
	}

	public void setOrganizationsList(OrganizationsList organizationsList) {
		this.organizationsList = organizationsList;
	}

	public void setOrganizationProfile(OrganizationProfile orgp) {
		this.organizationProfile = orgp;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "organization")
	public OrganizationProfile getOrganizationProfile() {
		return this.organizationProfile;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Organization other = (Organization) obj;
		if (other.getId() != null && this.getId() != null
				&& other.getId().equals(this.getId()))
			return true;

		return super.equals(obj);
	}

	@Override
	public String toString() {
		return getName();
	}

	public int getPartsNumber() {
		return partsNumber;
	}

	public void setPartsNumber(int partsNumber) {
		this.partsNumber = partsNumber;
	}

	public int getMaterialProcessing() {
		return materialProcessing;
	}

	public void setMaterialProcessing(int materialProcessing) {
		this.materialProcessing = materialProcessing;
	}

	public int getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(int materialNumber) {
		this.materialNumber = materialNumber;
	}

	public int getMachineryTypes() {
		return machineryTypes;
	}

	public void setMachineryTypes(int machineryTypes) {
		this.machineryTypes = machineryTypes;
	}

	@Transient
	public int getCompanySMInfo(int variable) {
		switch (variable) {
		case Tick.TICK_AUTOVALUE_MACHINERY_TYPES:
			return getMachineryTypes();
		case Tick.TICK_AUTOVALUE_MATERIAL_NUMBER:
			return getMaterialNumber();
		case Tick.TICK_AUTOVALUE_MATERIAL_PROCESSING:
			return getMaterialProcessing();
		case Tick.TICK_AUTOVALUE_PARTS_NUMBER:
			return getPartsNumber();
		default:
			return -1;
		}
	}

	@Transient
	public void setCompanySMInfo(int variable, int value) {
		switch (variable) {
		case Tick.TICK_AUTOVALUE_MACHINERY_TYPES:
			setMachineryTypes(value);
			break;
		case Tick.TICK_AUTOVALUE_MATERIAL_NUMBER:
			setMaterialNumber(value);
			break;
		case Tick.TICK_AUTOVALUE_MATERIAL_PROCESSING:
			setMaterialProcessing(value);
			break;
		case Tick.TICK_AUTOVALUE_PARTS_NUMBER:
			setPartsNumber(value);
			break;
		default:

		}
	}

}
