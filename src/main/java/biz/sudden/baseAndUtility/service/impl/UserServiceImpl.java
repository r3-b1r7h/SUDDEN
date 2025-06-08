package biz.sudden.baseAndUtility.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.domain.exception.ActorNotFoundException;
import biz.sudden.baseAndUtility.domain.exception.UserAlreadyExistsException;
import biz.sudden.baseAndUtility.repository.UserRepository;
import biz.sudden.baseAndUtility.service.UserService;

public class UserServiceImpl implements UserService {

	private Logger logger = Logger.getLogger(this.getClass());
	private UserRepository repository;
	private List<SuddenUser> initUsers;

	// public User createUser(String name, String password) {
	// return repository.create(name,password);
	// }
	//
	//	
	// public boolean isCorrectLogin(final String name, String password) {
	//		
	// return repository.isCorrectLogin(name, password);
	// }
	//	

	public void initAdmin() {

		logger.debug("CREATING ADMIN");

		SuddenUser adminUser = new SuddenUser();
		adminUser.setAuthority("ROLE_ADMIN");
		adminUser.setAccountNonExpired(true);
		adminUser.setAccountNonLocked(true);
		adminUser.setCredentialsNonExpired(true);
		adminUser.setEnabled(true);
		adminUser.setNickname("admin");
		adminUser.setUsername("admin");
		adminUser.setPassword("nik1kurt2");
		adminUser.setLoggedIn(true);

		try {
			repository.create(adminUser);
		} catch (UserAlreadyExistsException ex) {
			logger.debug("Admin exists already ");
		}
	}

	public List<SuddenUser> getInitUsers() {
		return this.initUsers;
	}

	public void setInitUsers(List<SuddenUser> users) {
		this.initUsers = users;
	}

	@Override
	public void initializeUsers() {

		for (SuddenUser initUser : this.initUsers) {
			for (SuddenUser userDatabase : repository.retrieveAll()) {
				boolean alreadyInDatabase = false;
				Long id = 0l;
				// Fixme tf if you make userdatabase.getspringid.equals... then
				// no NPE can occur !!
				if (userDatabase.getSpringId() != null
						&& initUser.getSpringId().equals(
								userDatabase.getSpringId())) {
					id = userDatabase.getId();
					alreadyInDatabase = true;
				}

				try {
					SuddenUser thisUser;
					if (!alreadyInDatabase) {
						id = createUser(initUser.getUsername(),
								initUser.getPassword(), initUser.getNickname(),
								initUser.getSpringId()).getId();
					}

					initUser.setId(id);

				} catch (Exception ex) {
					logger.debug("Exception in " + this.getClass().getName()
							+ " occurred " + ex);
				}
			}
		}
		// this.initUsers = initUsersInDatabase;
		// setInitUsers(initUsersInDatabase);
	}

	@Override
	public void deleteInitializedUsers() {
		for (SuddenUser initUser : this.initUsers) {
			for (SuddenUser userDatabase : repository.retrieveAll()) {
				boolean alreadyInDatabase = false;
				Long id = 0l;
				if (userDatabase.getSpringId() != null
						&& initUser.getSpringId().equals(
								userDatabase.getSpringId())) {
					repository.delete(userDatabase.getUsername());
				}
			}

			initUser.setId(null);

			logger.debug("Delete User " + initUser.getUsername());

		}

	}

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		UserDetails userDetails;

		// if (userName.equals("admin")) {
		// userDetails = new UserDetails() {
		// @Override
		// public GrantedAuthority[] getAuthorities() {
		//
		// GrantedAuthority grantedAuthority = new GrantedAuthority() {
		//
		// public String getAuthority() {
		// return "ROLE_ADMIN";
		// }
		//
		// public int compareTo(Object o) {
		// if (((GrantedAuthority)
		// o).getAuthority().equals(this.getAuthority()))
		// return 0;
		// else
		// return -1;
		// }
		// };
		// return new GrantedAuthority[] { grantedAuthority };
		// }
		//
		// @Override
		// public String getPassword() {
		// // TODO Auto-generated method stub
		// return "admin";
		// }
		//
		// @Override
		// public String getUsername() {
		// // TODO Auto-generated method stub
		// return "admin";
		// }
		//
		// @Override
		// public boolean isAccountNonExpired() {
		// // TODO Auto-generated method stub
		// return true;
		// }
		//
		// public boolean isAccountNonLocked() {
		// return true;
		// };
		//
		// @Override
		// public boolean isCredentialsNonExpired() {
		// // TODO Auto-generated method stub
		// return true;
		// }
		//
		// @Override
		// public boolean isEnabled() {
		// // TODO Auto-generated method stub
		// return true;
		// }
		//
		// };
		// }
		//
		// else {

		try {
			userDetails = repository.retrieve(userName);
			logger.debug(userDetails.getUsername());
			// don't log this... logger.debug(userDetails.getPassword());
			logger.debug(userDetails.isAccountNonExpired());
			logger.debug(userDetails.getAuthorities()[0]);
		} catch (ActorNotFoundException ex) {
			throw new UsernameNotFoundException("User " + userName
					+ " not found in Database");
		}

		// }

		// FIXME ThF move this to a login method
		if (userDetails instanceof SuddenUser) {
			SuddenUser user = (SuddenUser) userDetails;
			user.setLoggedIn(true);
			// FacesContext.getCurrentInstance().getExternalContext().
		}

		return userDetails;
	}

	@Override
	public SuddenUser retrieveUser(String username) {
		UserDetails userDetails = repository.retrieve(username);
		if (userDetails instanceof SuddenUser) {
			return (SuddenUser) userDetails;
		} else {
			return null;
		}
	}

	@Override
	public SuddenUser createUser(String username, String password,
			String nickname, Long springId) {
		// TODO Auto-generated method stub
		SuddenUser user = new SuddenUser(username, password, nickname);
		user.setSpringId(springId);
		// user.setUsername(username);
		// user.setPassword(password);
		// user.setNickname(nickname);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setAuthority("ROLE_USER");
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);

		repository.create(user);
		return user;
	}

	@Override
	public SuddenUser createUser(String username, String password,
			String nickname) {
		// TODO Auto-generated method stub
		return createUser(username, password, nickname, null);
	}

	@Override
	public List<SuddenUser> retrieveAllUsers() {
		return repository.retrieveAll();
	}

	@Override
	public void deleteUser(String userName) {
		// TODO Auto-generated method stub
		repository.delete(userName);
	}

	@Override
	public void loginUser(String userName) {
		SuddenUser user = retrieveUser(userName);
		if (user != null) {
			user.setLoggedIn(true);
		}
	}

	@Override
	public void logoutUser(String userName) {
		SuddenUser user = retrieveUser(userName);
		if (user != null) {
			user.setLoggedIn(false);
		}
	}

	/** Getter and Setter **/

	public UserRepository getRepository() {
		return repository;
	}

	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

}
