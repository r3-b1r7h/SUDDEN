package biz.sudden.baseAndUtility.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.Manufacturer;
import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.repository.ManufacturerRepository;
import biz.sudden.baseAndUtility.repository.ProcessRepository;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.knowledgeData.serviceManagement.service.ManufacturingProcessType_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;

public class ServiceManagementServiceImpl implements ServiceManagementService {

	private Logger logger = Logger.getLogger(this.getClass());

	private ProcessRepository repository;
	private ManufacturerRepository manufacturerRep;
	private IOrganizationRepository organisationRep;
	private ConnectableService connectableService;

	ManufacturingProcessType_Service mptService;

	@Override
	public void createManufacturer(Manufacturer m) {
		manufacturerRep.create(m);
	}

	@Override
	public void deleteManufacturer(Manufacturer m) {
		connectableService.deleteConnectableAssocs(m, connectableService
				.getRetrieveAllScope());
		manufacturerRep.delete(m);
	}

	@Override
	public List<Manufacturer> retrieveAllManufacturers() {
		return manufacturerRep.retrieveAll();
	}

	@Override
	public Manufacturer retrieveManufacturer(Long id) {
		return manufacturerRep.retrieve(id);
	}

	@Override
	public List<Manufacturer> retrieveManufacturerBy(String name) {
		return manufacturerRep.retrieveManufacturerBy(name);
	}

	@Override
	public void updateManufacturer(Manufacturer m) {
		manufacturerRep.update(m);
	}

	@Override
	public void createOrganisation(Organization o) {
		organisationRep.create(o);
		if (o.getName() != null)
			connectableService.createOrRetrieveScope(o.getName());
	}

	@Override
	public void deleteOrganisation(Organization o) {
		connectableService.deleteConnectableAssocs(o, connectableService
				.getRetrieveAllScope());
		organisationRep.delete(o);
	}

	@Override
	public List<Organization> retrieveAllOrganisations() {
		return organisationRep.retrieveAll();
	}

	@Override
	public Organization retrieveOrganisation(Long id) {
		return organisationRep.retrieve(id);
	}

	@Override
	public List<Organization> retrieveOrganisationsByName(String name) {
		return organisationRep.retrieveOrganisationByName(name);
	}

	@Override
	public void updateOrganisation(Organization o) {
		organisationRep.update(o);
	}

	/**
	 * set Manufacturer repository
	 * 
	 * @param manufacturerRep
	 */
	@Override
	public void setManufacturerRep(ManufacturerRepository manufacturerRep) {
		this.manufacturerRep = manufacturerRep;
	}

	@Override
	public void setOrganisationRep(IOrganizationRepository organisationRep) {
		this.organisationRep = organisationRep;
	}

	@Override
	public void setConnectableService(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

	@Override
	public void setManufacturingProcessTypeService(
			ManufacturingProcessType_Service service) {
		this.mptService = service;
	}

	/**
	 * @param repository
	 *            The concrete Process repository implementation to set.
	 */
	public void setProcessRepository(ProcessRepository repository) {
		this.repository = repository;
	}

	@Override
	public void createProcess(Process p) {
		logger.debug("Process with id: " + repository.create(p)
				+ " created in DB");
	}

	@Override
	public void deleteProcess(Long id) {
		connectableService.deleteConnectableAssocs(retrieveProcess(id),
				connectableService.getRetrieveAllScope());
		repository.delete(retrieveProcess(id));
	}

	@Override
	public List<Process> retrieveAllProcesses() {
		return repository.retrieveAll();
	}

	@Override
	public Process retrieveProcess(Long id) {
		Process p = repository.retrieve(id);
		return p;
	}

	@Override
	public void updateProcess(Process p) {
		repository.update(p);
	}

	@Override
	public List<Process> retrieveProcessBy(String name) {
		return repository.retrieveProcessBy(name);
	}
}
