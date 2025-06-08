package biz.sudden.baseAndUtility.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.Manufacturer;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface ManufacturerRepository extends
		GenericRepository<Manufacturer, Long> {

	/**
	 * retrieve all existing Manufacturers from the repository
	 */
	public List<Manufacturer> retrieveAll();

	/**
	 * retrieve a Manufacturer from Repository by name
	 * 
	 * @param name
	 *            name of the Manufacturer
	 * @return a List of Manufacturers which have the given name, an empty List
	 *         if none exist
	 */
	public List<Manufacturer> retrieveManufacturerBy(String name);

}
