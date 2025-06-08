package biz.sudden.baseAndUtility.repository.hibernate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.security.userdetails.UserDetails;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.domain.exception.ActorNotFoundException;
import biz.sudden.baseAndUtility.domain.exception.AmbiguousActorException;
import biz.sudden.baseAndUtility.domain.exception.UserAlreadyExistsException;
import biz.sudden.baseAndUtility.repository.UserRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class UserRepositoryImpl extends GenericRepositoryImpl<SuddenUser, Long>
		implements UserRepository {

	public UserRepositoryImpl() {
		super(SuddenUser.class);
	}

	public UserRepositoryImpl(Class<SuddenUser> type) {
		super(SuddenUser.class);
	}

	@Override
	public Long create(SuddenUser user) throws UserAlreadyExistsException {
		// TODO Auto-generated method stub
		try {
			this.retrieve(user.getUsername());
			throw new UserAlreadyExistsException("User already exists");
		} catch (ActorNotFoundException ex) {
			return super.create(user);
		}

	}

	@Override
	public UserDetails retrieve(final String userName)
			throws ActorNotFoundException, AmbiguousActorException {
		return (UserDetails) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException,
							AmbiguousActorException, ActorNotFoundException {
						Query query = session
								.createQuery("from SuddenUser where userName = :userName");
						query.setString("userName", userName);

						logger.debug("username " + userName);
						logger.debug("Query list " + query.list());
						if (query.list().size() == 1) {
							return query.uniqueResult();
						} else if (query.list().size() == 0) {
							throw new ActorNotFoundException("User " + userName
									+ " not found");
						} else if (query.list().size() > 1) {
							throw new AmbiguousActorException("User "
									+ userName + " ist ambiguous");
						} else {
							return null;
						}
					}
				});

	}

	@Override
	public List<SuddenUser> retrieveAll() {
		return (List<SuddenUser>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery("from SuddenUser");
						return query.list();
					}
				});

	}

	@Override
	public void delete(final String userName) {
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session
						.createQuery("from SuddenUser u where u.username = :username");
				query.setString("username", userName);
				SuddenUser user = (SuddenUser) query.uniqueResult();
				logger.debug("Delete User " + user);
				delete(user);
				return null;
			}
		});

	}

	// public boolean isCorrectLogin(final String name, final String password) {
	//
	// final String md5Hash = getMD5Hash(password);
	//
	// logger.debug("The md5 hash of the password is " + md5Hash);
	//
	// return (Boolean) getHibernateTemplate().execute(new HibernateCallback() {
	//
	// public Object doInHibernate(Session session) throws HibernateException,
	// SQLException {
	// Query query =
	// session.createQuery("from User where userName = :name and password = :password");
	// query.setString("name", name);
	// query.setString("password", md5Hash);
	//
	// if (query.list().size() == 0)
	// return false;
	// else if (query.list().size() == 1)
	// return true;
	// else
	// return false;
	// }
	// });
	//
	// }

	// @Override
	// public boolean userExists(final String name) {
	// return (Boolean) getHibernateTemplate().execute(new HibernateCallback() {
	//
	// public Object doInHibernate(Session session) throws HibernateException,
	// SQLException {
	// Query query = session.createQuery("from User where userName = :name");
	// query.setString("name", name);
	//
	// if (query.list().size() == 0)
	// return false;
	// else if (query.list().size() == 1)
	// return true;
	// else
	// return false;
	// }
	// });
	// }

	// @Override
	// public User create(String userName, String password) {
	// User newUser = new User();
	// newUser.setUserName(userName);
	// newUser.setPassword(getMD5Hash(password));
	// create(newUser);
	// return newUser;
	// }

	private String getMD5Hash(String data) {

		StringBuffer strbuf = new StringBuffer();
		byte[] digestByte;

		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");

			digest.update(data.getBytes(), 0, data.length());

			digestByte = digest.digest();

			for (int i = 0; i < digestByte.length; i++) {
				strbuf.append((digestByte[i]));
			}

			return strbuf.toString();

		} catch (NoSuchAlgorithmException noSuchAlgoException) {
			noSuchAlgoException.printStackTrace();
			return "";
		}
	}

	private String toHexString(byte b) {
		int value = (b & 0x7F) + (b < 0 ? 128 : 0);

		String ret = (value < 16 ? "0" : "");
		ret += Integer.toHexString(value).toUpperCase();

		return ret;
	}

}
