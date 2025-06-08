package biz.sudden.baseAndUtility.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

/**
 * AssociationRepository represents the repository interface for Associations
 * 
 * @author Matthias Neubauer
 * 
 */
public interface AssociationRepository extends
		GenericRepository<Association, Long> {

	// define further methods (e.g. finder) besides CRUD methods inherited by
	// GenericRepository

	/**
	 * retrieve Associations from the Repository by their type
	 * 
	 * @param type
	 *            String which represents the Association type
	 * @return a List of Associations which are of the given type, an empty List
	 *         if none exist
	 */
	// public List<Association> retrieveAssocsOfType(String type);
	public List<Association> retrieveAssocsOfType(String type, Scope scope);

	/**
	 * retrieve Associations from the Repository by their type
	 * 
	 * @param type
	 *            AssocType which defines the type of associations
	 * @return a List of Associations which are of the given type, an empty List
	 *         if none exist
	 */
	// public List<Association> retrieveAssocsOfType(AssocType type);
	public List<Association> retrieveAssocsOfType(AssocType type, Scope scope);

	/**
	 * retrieve AssociationRoles of a certain Association
	 * 
	 * @param a
	 *            given Association
	 * @return a List of AssociationRoles of the respective Association, an
	 *         empty List if none exist
	 */
	public List<AssociationRole> retrieveAssociationRoles(Association a);

	/**
	 * retrieve AssociationRoles of a certain Association but don't initialize
	 * the connectables
	 * 
	 * @param a
	 *            given Association
	 * @return a List of AssociationRoles of the respective Association, an
	 *         empty List if none exist
	 */
	public List<AssociationRole> retrieveAssociationRolesLazy(Association a);

	/**
	 * retrieve all associations of a certain type a connectable has, where it
	 * is in a certain role (respect also the scope)
	 * 
	 * @param assocType
	 * @param c
	 * @param roleType
	 * @param scope
	 * @return
	 */
	public List<Association> retrieveAssociationBy(String assocType,
			Connectable c, String roleType, Scope scope);

	/**
	 * retrieve all associations a connectable has where it is in a certain role
	 * (respect also the scope)
	 * 
	 * @param c
	 * @param roleType
	 * @param scope
	 * @return
	 */
	public List<Association> retrieveAssociationBy(Connectable c,
			String roleType, Scope scope);

	public List<Association> retrieveAssociationBy(String assocType,
			Connectable c, String connectableRoleType,
			String counterpartRoleType, Scope scope);

}
