package biz.sudden.userOrganizationManagement.userManagement.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import biz.sudden.baseAndUtility.domain.IDInterface;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

@SuppressWarnings("serial")
@Entity
@Table(name = "USERS")
public class User2 implements IDInterface, Serializable {

	private String aux_companyName;
	private String company;
	private String companyId;
	private boolean companyManager;
	private String employeeName;

	private UserProfile userProfile;
	private UserAccount userAccount;
	private UsersList userList;
	private UserTypes userTypes;
	private Organization organization;

	private long id;

	@Column(name = "AUX_COMPANY_NAME")
	public String getAux_companyName() {
		return aux_companyName;
	}

	@Column(name = "COMPANY_NAME")
	public String getCompany() {
		return company;
	}

	@Column(name = "COMPANY_ID")
	public String getCompanyId() {
		return companyId;
	}

	@Column(name = "EMPLOYEE_NAME")
	public String getEmployeeName() {
		return employeeName;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Column(name = "COMPANY_MANAGER")
	public boolean getCompanyManager() {
		return companyManager;
	}

	public void setAux_companyName(String aux_companyName) {
		this.aux_companyName = aux_companyName;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCompanyManager(boolean companyManager) {
		this.companyManager = companyManager;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	// Persistence Annotations

	@OneToOne(cascade = CascadeType.ALL)
	public UserProfile getUserProfile() {
		return userProfile;
	}

	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	@ManyToOne
	public UsersList getUserList() {
		return userList;
	}

	public UserTypes getUserTypes() {
		return userTypes;
	}

	// FIXME! I receive this error since Organization is derived from the Actor
	// class:
	// ERROR SchemaUpdate:155 - Unsuccessful: alter table USERS add constraint
	// FK4D495E8F19522A7 foreign key (COMPANY_ID) references Actor
	@ManyToOne
	@JoinColumn(name = "COMPANY_ID", insertable = false, updatable = false)
	public Organization getOrganization() {
		return organization;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public void setUserList(UsersList userList) {
		this.userList = userList;
	}

	public void setUserTypes(UserTypes userTypes) {
		this.userTypes = userTypes;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

}
