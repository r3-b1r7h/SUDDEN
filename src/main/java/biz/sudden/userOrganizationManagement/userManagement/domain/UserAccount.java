package biz.sudden.userOrganizationManagement.userManagement.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "USERS_ACCOUNTS")
public class UserAccount implements IDInterface, Serializable {

	private String userName;
	private String ePassword;
	private String passwordRepeat;

	private User2 user;
	private long id;

	public String getEPassword() {
		return ePassword;
	}

	@Override
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Transient
	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	@Column(name = "userName")
	public String getUserName() {
		return userName;
	}

	@Column(name = "ePassword")
	public void setEPassword(String epassword) {
		this.ePassword = epassword;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	// Persistence Annotations
	@OneToOne(cascade = CascadeType.ALL)
	public User2 getUser2() {
		return user;
	}

	public void setUser2(User2 user) {
		this.user = user;
	}

	public void setId(long id) {
		this.id = id;
	}

}
