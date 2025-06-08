package biz.sudden.userOrganizationManagement.userManagement.domain;

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
@Table(name = "USERS_LISTS")
public class UsersList implements IDInterface, Serializable {

	private List<User2> user;

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
	public List<User2> getUser2() {
		return user;
	}

	public void setUser2(List<User2> user) {
		this.user = user;
	}

}
