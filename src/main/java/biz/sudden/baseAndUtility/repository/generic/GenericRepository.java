package biz.sudden.baseAndUtility.repository.generic;

import java.io.Serializable;
import java.util.List;

/**
 * GenericRepository is an interface, which avoids to repeat defining CRUD
 * methods for Data Access Objects again and again. For detailed information
 * compare http://www.ibm.com/developerworks/java/library/j-genericdao.html
 * 
 * @author Thomas Feiner
 * 
 * @param <T>
 *            type (class type)
 * @param <PK>
 *            primary key
 */
public interface GenericRepository<T, PK extends Serializable> {

	/**
	 * store a new instance of T in the database
	 * 
	 * @param d
	 *            new instance which will be stored
	 * @return primary key of the created instance
	 */
	public PK create(T d);

	/**
	 * retrieve an object of type T from the database by a given key
	 * 
	 * @param id
	 *            primary key of the searched object
	 * @return object with the given key
	 */
	public T retrieve(PK id);

	/**
	 * update an object in the database
	 * 
	 * @param d
	 *            object which will be updated
	 */
	public void update(T d);

	/**
	 * delete an object from the database
	 * 
	 * @param d
	 *            object which will be deleted
	 */
	public void delete(T d);

	public List<T> retrieveAll();

	public <S> List<S> retrieveAllByType(Class<S> type);

	public <S> S retrieveByTypeAndId(Class<S> type, PK id);

	public T retrieveByFieldName(String fieldName, String value);

	public T retrieveByFieldNameLowerCase(String fieldName, String value);

	public T retrieveByFieldNameContains(String fieldName, String value);

	public T retrieveByFieldNameContainsLowerCase(String fieldName, String value);

	public boolean containsObject(T object);

	public List<T> retrieveAllByFieldNameContainsLowerCase(String fieldName,
			String value);

	public List<T> retrieveAllByFieldNameContains(String fieldName, String value);

	public List<T> retrieveAllByFieldNameLowerCase(String fieldName,
			String value);

	public List<T> retrieveAllByFieldName(String fieldName, String value);

	public <S> List<S> retrieveAllChildren(T parent, Class<S> childrenType);
}
