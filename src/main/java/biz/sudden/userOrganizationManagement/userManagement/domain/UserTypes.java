package biz.sudden.userOrganizationManagement.userManagement.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import biz.sudden.baseAndUtility.domain.IDInterface;

@SuppressWarnings("serial")
@Entity
@Table(name = "USERS_TYPES")
public class UserTypes implements IDInterface, Serializable {

	private User2 user;
	private long id;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub

	}

	// Persistence Annotations
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	public User2 getUser2() {
		return user;
	}

	public void setUser2(User2 user) {
		this.user = user;
	}

}
