package biz.sudden.baseAndUtility.repository;

import java.util.List;

import org.springframework.security.userdetails.UserDetails;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.domain.exception.ActorNotFoundException;
import biz.sudden.baseAndUtility.domain.exception.AmbiguousActorException;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface UserRepository extends GenericRepository<SuddenUser, Long> {

	public UserDetails retrieve(String userName) throws ActorNotFoundException,
			AmbiguousActorException;

	public List<SuddenUser> retrieveAll();

	public void delete(String userName);

	//	
	//	
	//
	// public boolean isCorrectLogin(String name, String md5Password);
	//	
	// public boolean userExists(String name);
	//	
	// public User create(String user, String password);

}