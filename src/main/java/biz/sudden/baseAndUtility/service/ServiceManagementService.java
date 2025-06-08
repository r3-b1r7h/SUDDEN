package biz.sudden.baseAndUtility.service;

import java.util.List;

import biz.sudden.baseAndUtility.domain.Manufacturer;
import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.repository.ManufacturerRepository;
import biz.sudden.knowledgeData.serviceManagement.service.ManufacturingProcessType_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;

/**
 * interface 4 managing common domain objects
 * 
 * @author MN
 * 
 */
public interface ServiceManagementService {

	// DB CRUD methods

	// 4 Manufacturer
	public void createManufacturer(Manufacturer m);

	public Manufacturer retrieveManufacturer(Long id);

	public void updateManufacturer(Manufacturer m);

	public void deleteManufacturer(Manufacturer m);

	public List<Manufacturer> retrieveAllManufacturers();

	public List<Manufacturer> retrieveManufacturerBy(String name);

	// 4Organisation
	public void createOrganisation(Organization o);

	public Organization retrieveOrganisation(Long id);

	public void updateOrganisation(Organization o);

	public void deleteOrganisation(Organization o);

	public List<Organization> retrieveAllOrganisations();

	public List<Organization> retrieveOrganisationsByName(String name);

	// 4 Process
	// Factory method to create a Process Object
	// public Process generateProcess();

	public void createProcess(Process p);

	public Process retrieveProcess(Long id);

	public void updateProcess(Process p);

	public void deleteProcess(Long id);

	public List<Process> retrieveAllProcesses();

	public List<Process> retrieveProcessBy(String name);

	void setManufacturerRep(ManufacturerRepository manufacturerRep);

	void setOrganisationRep(IOrganizationRepository organisationRep);

	void setConnectableService(ConnectableService connectableService);

	void setManufacturingProcessTypeService(
			ManufacturingProcessType_Service service);

}
