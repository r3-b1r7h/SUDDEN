package biz.sudden.baseAndUtility.repository.hibernate.generic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

/**
 * GenericRepositoryImpl is concrete hibernate implementation of the
 * GenericRepository interface. Hibernate Repositories for domain classes should
 * inherit from GenericRepositoryImpl to avoid duplicate "CRUD" Code.
 * 
 * @author Thomas Feiner
 * @param <T>
 *            type (class type)
 * @param <PK>
 *            primary key
 */

public abstract class GenericRepositoryImpl<T, PK extends Serializable> extends
		HibernateDaoSupport implements GenericRepository<T, PK> {

	protected Class<T> type;
	@SuppressWarnings("unchecked")
	public static HashMap hm = new HashMap();

	/**
	 * Constructor
	 * 
	 * @param type
	 *            class type
	 */
	@SuppressWarnings("unchecked")
	public GenericRepositoryImpl(Class<T> type) {
		this.type = type;
		hm.put(type, this);
	}

	public static GenericRepository<?, ?> getRepositoryForTyp(Class<?> type) {
		return (GenericRepository<?, ?>) hm.get(type);
	}

	/**
	 * store a new instance of T in the database
	 * 
	 * @param o
	 *            new instance which will be stored
	 * @return primary key of the created instance
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PK create(T o) {
		return (PK) getHibernateTemplate().save(o);
	}

	/**
	 * delete an object from the database
	 * 
	 * @param o
	 *            object which will be deleted
	 */
	@Override
	public void delete(T o) {
		// update(o);// to avaoid exceptions

		getHibernateTemplate().delete(o);
	}

	/**
	 * retrieve an object of type T from the database by a given key
	 * 
	 * @param id
	 *            primary key of the searched object
	 * @return object with the given key
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T retrieve(PK id) {
		// "get" returns null - "load" am empty proxy object
		return (T) getHibernateTemplate().get(type, id);
	}

	@Override
	public List<T> retrieveAll() {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(type);
		return query.list();
	}

	public <S> S retrieveByTypeAndId(java.lang.Class<S> type, PK id) {
		return (S) getHibernateTemplate().get(type, id);
	};

	@Override
	public <S> List<S> retrieveAllByType(Class<S> type) {

		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(type);
		return query.list();

	}

	@Override
	public T retrieveByFieldName(String fieldName, String value) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(type);
		query.add(Restrictions.like(fieldName, value));
		return (T) query.uniqueResult();
	}

	@Override
	public T retrieveByFieldNameLowerCase(String fieldName, String value) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(type);
		query.add(Restrictions.ilike(fieldName, value));
		return (T) query.uniqueResult();
	}

	@Override
	public T retrieveByFieldNameContains(String fieldName, String value) {
		return retrieveByFieldName(fieldName, "%" + value + "%");
	}

	@Override
	public T retrieveByFieldNameContainsLowerCase(String fieldName, String value) {
		return retrieveByFieldNameLowerCase(fieldName, "%" + value + "%");
	}

	@Override
	public List<T> retrieveAllByFieldName(String fieldName, String value) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(type);
		query.add(Restrictions.like(fieldName, value));
		return query.list();
	}

	@Override
	public List<T> retrieveAllByFieldNameLowerCase(String fieldName,
			String value) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(type);
		query.add(Restrictions.ilike(fieldName, value));
		return query.list();
	}

	@Override
	public List<T> retrieveAllByFieldNameContains(String fieldName, String value) {
		return retrieveAllByFieldName(fieldName, "%" + value + "%");
	}

	@Override
	public List<T> retrieveAllByFieldNameContainsLowerCase(String fieldName,
			String value) {
		return retrieveAllByFieldNameLowerCase(fieldName, "%" + value + "%");
	}

	@Override
	public <S> List<S> retrieveAllChildren(T parent, Class<S> childrenType) {

		String propertyName = "";

		boolean found = false;

		for (Field currentField : childrenType.getDeclaredFields()) {
			if (found) {
				throw new InternalError(
						"Property of parent type found more than once in children");
			}
			if (currentField.getType().equals(parent.getClass())) {
				propertyName = currentField.getName();
				found = true;
			}
		}

		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(childrenType);
		query.add(Restrictions.eq(propertyName, parent));
		return query.list();
	}

	@Override
	public boolean containsObject(T object) {

		return getHibernateTemplate().contains(object);
	};

	/**
	 * update an object in the database
	 * 
	 * @param o
	 *            object which will be updated
	 */
	@Override
	public void update(T o) {
		try {
			// logger.debug("Does object exist already in session? "+containsObject(o));
			getHibernateTemplate().update(o);
		} catch (Exception ex) {
			logger.warn("Generic Repository: update failed -> merging: " + ex);
			// ex.printStackTrace();
			getHibernateTemplate().merge(o);
		}

	}

}
