package biz.sudden.baseAndUtility.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

/**
 * AssocTypeRepository defines the repository interface for Association
 * types(AssocType)
 * 
 * @author Matthias Neubauer
 * 
 */
public interface AssocTypeRepository extends GenericRepository<AssocType, Long> {

	/**
	 * get an AssocType by name
	 * 
	 * @param name
	 *            given name
	 * @return returns a List of AssocTypes which should at most contain 1
	 *         element, an empty list if none exists
	 */
	public List<AssocType> retrieveAssocTypeBy(String type);

	/**
	 * retrieve all (in the repository) defined Association types.
	 * 
	 * @return returns defined Association types, an empty set if none exist
	 */
	public List<AssocType> retrieveAssocTypes();
}
