package biz.sudden.baseAndUtility.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.connectable.OccurType;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface OccurTypeRepository extends GenericRepository<OccurType, Long> {

	/**
	 * get an OccurType by name
	 * 
	 * @param name
	 *            given name
	 * @return returns a List of OccurTypes which should at most contain 1
	 *         element, an empty list if none exists
	 */
	public List<OccurType> retrieveOccurTypeBy(String type);

	/**
	 * retrieve all (in the repository) defined Occurrence types.
	 * 
	 * @return returns defined Occurrence types, an empty set if none exist
	 */
	public List<OccurType> retrieveOccurTypes();
}
