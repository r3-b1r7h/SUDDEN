package biz.sudden.baseAndUtility.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

/**
 * ProcessRepository defines the repository interface for Processes
 * 
 * @author Matthias Neubauer
 * 
 */
public interface ProcessRepository extends GenericRepository<Process, Long> {

	// could also define further finder Methods besides CRUD

	/**
	 * retrieve all existing Processes from the repository
	 */
	public List<Process> retrieveAll();

	/**
	 * retrieve Process from Repository by name
	 * 
	 * @param name
	 *            name of the Process
	 * @return a List of Process which have the given name, an empty List if
	 *         none exist
	 */
	public List<Process> retrieveProcessBy(String name);
}
