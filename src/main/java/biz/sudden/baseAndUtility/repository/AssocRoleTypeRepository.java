package biz.sudden.baseAndUtility.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.connectable.AssocRoleType;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

/**
 * AssocRoleTypeRepository defines the repository interface for AssociationRole
 * types(AssocRoleTypes)
 * 
 * 
 * @author Matthias Neubauer
 * 
 */
public interface AssocRoleTypeRepository extends
		GenericRepository<AssocRoleType, Long> {

	/**
	 * get an AssocRoleType by name
	 * 
	 * @param name
	 *            given name
	 * @return returns a List of AssocRoleTypes which should at most contain 1
	 *         element, an empty list if none exists
	 */
	public List<AssocRoleType> retrieveAssocRoleTypeBy(String name);

	/**
	 * retrieve all (in the repository) defined AssociationRole types
	 * 
	 * @return returns defined AssociationRole types, an empty set if none exist
	 */
	public List<AssocRoleType> retrieveAssocRoleTypes();

}
