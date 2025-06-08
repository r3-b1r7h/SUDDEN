package biz.sudden.baseAndUtility.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity
public class SuddenUser implements UserDetails, Connectable {

	private String username;
	private String password;
	private String nickname;
	private String authority;
	private Long id;

	private Long springId;

	public Long getSpringId() {
		return springId;
	}

	public void setSpringId(Long springId) {
		this.springId = springId;
	}

	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	private boolean loggedIn;

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public SuddenUser() {

	}

	public SuddenUser(String userName, String password, String nickName) {
		this.username = userName;
		this.password = password;
		this.nickname = nickName;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	@Transient
	public GrantedAuthority[] getAuthorities() {

		GrantedAuthority grantedAuthority = new GrantedAuthority() {

			public String getAuthority() {
				return authority;
			}

			public int compareTo(Object o) {
				if (((GrantedAuthority) o).getAuthority().equals(
						this.getAuthority()))
					return 0;
				else
					return -1;
			}
		};
		return new GrantedAuthority[] { grantedAuthority };
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() { // MD5 !!
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof SuddenUser) {
			SuddenUser otherUser = (SuddenUser) obj;
			return otherUser.getUsername().equals(this.getUsername());
		} else {
			return false;
		}

	}

	@Override
	public String toString() {
		return nickname + ": " + username;
	}
}
