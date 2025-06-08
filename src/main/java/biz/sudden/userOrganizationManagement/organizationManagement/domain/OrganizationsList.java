package biz.sudden.userOrganizationManagement.organizationManagement.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import biz.sudden.baseAndUtility.domain.IDInterface;

@SuppressWarnings("serial")
@Entity
@Table(name = "ORGANIZATIONS_LIST")
public class OrganizationsList implements IDInterface, Serializable {

	private List<Organization> organization;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub

	}

	// Persistence Annotations
	@OneToMany
	public List<Organization> getOrganization() {
		return organization;
	}

	public void setOrganization(List<Organization> organization) {
		this.organization = organization;
	}

}
