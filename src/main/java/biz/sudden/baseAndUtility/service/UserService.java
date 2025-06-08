package biz.sudden.baseAndUtility.service;

import java.util.List;

import org.springframework.security.userdetails.UserDetailsService;

import biz.sudden.baseAndUtility.domain.SuddenUser;

public interface UserService extends UserDetailsService {

	public SuddenUser createUser(String username, String password,
			String nickname, Long springId);

	public SuddenUser createUser(String username, String password,
			String nickname);

	public void loginUser(String userName);

	public void logoutUser(String userName);

	public List<SuddenUser> retrieveAllUsers();

	public SuddenUser retrieveUser(String username);

	public void deleteUser(String userName);

	public void initializeUsers();

	public void deleteInitializedUsers();

	// public User createUser(String name, String password);
	//	
	// public boolean isCorrectLogin(String name, String password);
	//	
	// public boolean userExists(String name);
	//	
	// public UserRepository getRepository();
	//
	// public void setRepository(UserRepository repository);

}
