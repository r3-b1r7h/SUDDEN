package biz.sudden.knowledgeData.serviceManagement.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.Manufacturer;
import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.ScopeRepository;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.knowledgeData.serviceManagement.Util.Util;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.domain.Machine;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;
import biz.sudden.knowledgeData.serviceManagement.domain.SupportingService;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.knowledgeData.serviceManagement.service.ManufacturingProcessType_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public class ManufacturingProcessType_ServiceImpl implements
		ManufacturingProcessType_Service {

	private Logger logger = Logger.getLogger(this.getClass());

	private ConnectableService connectableService;

	// Variables for associationTypes and associationRole types
	// --------------------------------------------------------

	// machine relation
	private static final String machineAssocType = "requiresMachine";
	private static final String m_techRoleType = "technology";
	private static final String machineRoleType = "machine";
	// TODO-> instead Technology-Machine relation -> done

	// technology relation
	private static final String techAssocType = "requiresTechnology";
	private static final String tech_processRoleType = "process";
	private static final String techRoleType = "technology";

	// process owner relation (between process and Manufacturer)
	private static final String poAssocType = "processOwner";
	private static final String po_processRoleType = "process";
	private static final String ownerRoleType = "owner";

	// canDoProduct relation
	private static final String canDoAssocType = "canDoProduct";
	private static final String canDo_productRoleType = "product";
	private static final String canDo_manufRoleType = "manufacturer";

	// rootProduct relation
	private static final String rootPAssocType = "rootProduct";
	private static final String rootP_OrgRoleType = "organisation";
	private static final String rootP_ProdRoleType = "product";

	// super sub relation between processes
	private static final String superSubAssocType = "superSub";
	private static final String superRoleType = "super";
	private static final String subRoleType = "sub";

	// input relation
	private static final String inputAssocType = "input";
	private static final String getInputRoleType = "getInput";
	private static final String isInputRoleType = "isInput";

	// output relation 4 process
	private static final String outputAssocType = "output";
	private static final String createsRoleType = "createsOutput";
	private static final String isOutputRoleType = "isOutput";

	@Override
	public void addCanDoProductService(Organization manufacturer,
			Item productService) {
		connectableService.addToDirected1toNRelation(manufacturer,
				productService, canDoAssocType, canDo_manufRoleType,
				canDo_productRoleType, connectableService
						.retrieveScopeBy(manufacturer.getName()));
	}

	@Override
	public void addInput(Process process, Item inputItem, Scope scope) {
		connectableService.addToDirected1toNRelation(process, inputItem,
				inputAssocType, getInputRoleType, isInputRoleType, scope);
	}

	// outdated
	// public void addMachine(Process process, Machine machine) {
	// connectableService.addToDirected1toNRelation(process, machine,
	// machineAssocType, m_techRoleType, machineRoleType);
	// }
	@Override
	public void addMachine(Technology technology, Machine machine, Scope scope) {
		connectableService.addToDirected1toNRelation(technology, machine,
				machineAssocType, m_techRoleType, machineRoleType, scope);
	}

	@Override
	public void addOutput(Process process, Item outputItem, Scope scope) {
		connectableService.addToDirected1toNRelation(process, outputItem,
				outputAssocType, createsRoleType, isOutputRoleType, scope);
	}

	@Override
	public void addProcessOwner(Process process, Manufacturer owner, Scope scope) {
		connectableService.addToDirected1toNRelation(process, owner,
				poAssocType, po_processRoleType, ownerRoleType, scope);
	}

	@Override
	public void addSubProcesses(Process process, Set<Process> subProcesses,
			Scope scope) {
		Iterator<Process> i = subProcesses.iterator();
		while (i.hasNext()) {
			// TODO: check 4 better solution because of performance reasons
			connectableService.addToDirected1toNRelation(process, i.next(),
					superSubAssocType, superRoleType, subRoleType, scope);
		}
	}

	@Override
	public void addTechnology(Process process, Technology technology,
			Scope scope) {
		connectableService.addToDirected1toNRelation(process, technology,
				techAssocType, tech_processRoleType, techRoleType, scope);
	}

	@Override
	public Set<Item> retrieveInput(Process process, Scope scope) {
		return Util.getItems(connectableService.directedSearchNeighbor(
				inputAssocType, process, getInputRoleType, isInputRoleType,
				scope));
	}

	@Override
	public Set<Material> retrieveInputMaterial(Process process, Scope scope) {
		return Util.getMaterials(connectableService.directedSearchNeighbor(
				inputAssocType, process, getInputRoleType, isInputRoleType,
				scope));
	}

	@Override
	public List<Product> retrieveInputProducts(Process process, Scope scope) {
		return Util.getProducts(connectableService.directedSearchNeighbor(
				inputAssocType, process, getInputRoleType, isInputRoleType,
				scope));
	}

	@Override
	public Set<SupportingService> retrieveInputSupportingServices(
			Process process, Scope scope) {
		return Util.getSupportingServices(connectableService
				.directedSearchNeighbor(inputAssocType, process,
						getInputRoleType, isInputRoleType, scope));
	}

	@Override
	public Set<Machine> retrieveMachines(Technology technology, Scope scope) {
		return Util.getMachines(connectableService.directedSearchNeighbor(
				machineAssocType, technology, m_techRoleType, machineRoleType,
				scope));
	}

	@Override
	public Set<Item> retrieveOutput(Process process, Scope scope) {
		return Util.getItems(connectableService.directedSearchNeighbor(
				outputAssocType, process, createsRoleType, isOutputRoleType,
				scope));
	}

	@Override
	public Set<Material> retrieveOutputMaterial(Process process, Scope scope) {
		return Util.getMaterials(connectableService.directedSearchNeighbor(
				outputAssocType, process, createsRoleType, isOutputRoleType,
				scope));
	}

	@Override
	public List<Product> retrieveOutputProducts(Process process, Scope scope) {
		return Util.getProducts(connectableService.directedSearchNeighbor(
				outputAssocType, process, createsRoleType, isOutputRoleType,
				scope));
	}

	@Override
	public Set<SupportingService> retrieveOutputSupportingServices(
			Process process, Scope scope) {
		return Util.getSupportingServices(connectableService
				.directedSearchNeighbor(outputAssocType, process,
						createsRoleType, isOutputRoleType, scope));
	}

	@Override
	public List<Organization> retrieveProcessOwner(Process process, Scope scope) {
		return Util
				.getOrganizations(connectableService.directedSearchNeighbor(
						poAssocType, process, po_processRoleType,
						ownerRoleType, scope));
	}

	@Override
	public Set<Process> retrieveProcessesOwned(Manufacturer manufacturer) {
		// TODO: implementation -> auslagern zu ConnectableService -generische
		// Methode!!
		Set<Process> returnSet = new HashSet<Process>();
		Scope sc = connectableService.retrieveScopeBy(manufacturer.getName());
		if (sc != null) {
			for (Association a : connectableService.retrieveAssociationBy(
					poAssocType, manufacturer, ownerRoleType, sc))
				returnSet.addAll(Util.getProcesses(connectableService
						.retrieveCounterpartConnectables(a, manufacturer,
								ownerRoleType, po_processRoleType)));
		}
		return returnSet;
	}

	@Override
	public List<Item> retrieveProductsServicesManufCanDo(
			Organization manufacturer) {
		return Util.getProductsServices(connectableService
				.directedSearchNeighbor(canDoAssocType, manufacturer,
						canDo_manufRoleType, canDo_productRoleType,
						connectableService.retrieveScopeBy(manufacturer
								.getName())));
	}

	@Override
	public List<Organization> retrieveOrganizationsCanDo(Item productService) {
		return Util.getOrganizations(connectableService.directedSearchNeighbor(
				canDoAssocType, productService, canDo_productRoleType,
				canDo_manufRoleType, ScopeRepository.RETRIEVEALL_SCOPE));
	}

	@Override
	public List<Product> retrieveRootProducts(Organization organisation) {
		return Util.getProducts(connectableService.directedSearchNeighbor(
				rootPAssocType, organisation, rootP_OrgRoleType,
				rootP_ProdRoleType, connectableService
						.retrieveScopeBy(organisation.getName())));
	}

	@Override
	public Set<Process> retrieveSubProcesses(Process process, Scope scope) {
		return Util.getProcesses(connectableService.directedSearchNeighbor(
				superSubAssocType, process, superRoleType, subRoleType, scope));
	}

	@Override
	public Set<Process> retrieveAllSubProcesses(Process process, Scope scope) {
		return Util.getProcesses(connectableService.directedSearchAll(
				superSubAssocType, process, superRoleType, subRoleType, scope));
	}

	@Override
	public Process retrieveSuperProcess(Process process, Scope scope) {
		Set<Process> ps = Util.getProcesses(connectableService
				.directedSearchNeighbor(superSubAssocType, process,
						subRoleType, superRoleType, scope));
		if (ps.size() > 1 || ps.isEmpty())
			return null;
		return ps.iterator().next();
	}

	@Override
	public Set<Technology> retrieveTechnologies(Process process, Scope scope) {
		return Util.getTechnologies(connectableService.directedSearchNeighbor(
				techAssocType, process, tech_processRoleType, techRoleType,
				scope));
	}

	@Override
	public boolean removeInput(Process process, Item inputItem, Scope scope) {
		return connectableService.removeFromAssociations(inputAssocType,
				process, getInputRoleType, inputItem, isInputRoleType, scope);
	}

	@Override
	public boolean removeMachine(Technology technology, Machine machine,
			Scope scope) {
		return connectableService.removeFromAssociations(machineAssocType,
				technology, m_techRoleType, machine, machineRoleType, scope);
	}

	@Override
	public boolean removeOutput(Process process, Item outputItem, Scope scope) {
		return connectableService.removeFromAssociations(outputAssocType,
				process, createsRoleType, outputItem, isOutputRoleType, scope);
	}

	@Override
	public boolean removeProcessOwner(Process process, Manufacturer owner) {
		return connectableService.removeFromAssociations(poAssocType, process,
				po_processRoleType, owner, ownerRoleType, connectableService
						.retrieveScopeBy(owner.getName()));
	}

	@Override
	public boolean removeProductCanDo(Organization manufacturer, Product product) {
		return connectableService.removeFromAssociations(canDoAssocType,
				manufacturer, canDo_manufRoleType, product,
				canDo_productRoleType, connectableService
						.retrieveScopeBy(manufacturer.getName()));
	}

	@Override
	public boolean removeSubProcess(Process process, Process subProcess,
			Scope scope) {
		return connectableService.removeFromAssociations(superSubAssocType,
				process, superRoleType, subProcess, subRoleType, scope);
	}

	@Override
	public boolean removeTechology(Process process, Technology technology,
			Scope scope) {
		return connectableService.removeFromAssociations(techAssocType,
				process, tech_processRoleType, technology, techRoleType, scope);
	}

	@Override
	public void setInput(Process process, Set<Item> inputItems, Scope scope) {
		Set<Connectable> cInputs = new HashSet<Connectable>();
		cInputs.addAll(inputItems);
		connectableService.setNElementsOf1toNRelation(inputAssocType, process,
				getInputRoleType, cInputs, isInputRoleType, scope);
	}

	@Override
	public void setMachines(Technology technology, Set<Machine> machines,
			Scope scope) {
		Set<Connectable> cMachines = new HashSet<Connectable>();
		cMachines.addAll(machines);
		connectableService.setNElementsOf1toNRelation(machineAssocType,
				technology, m_techRoleType, cMachines, machineRoleType, scope);
	}

	@Override
	public void setOutput(Process process, Set<Item> outputItems, Scope scope) {
		Set<Connectable> cOutput = new HashSet<Connectable>();
		cOutput.addAll(outputItems);
		connectableService.setNElementsOf1toNRelation(outputAssocType, process,
				createsRoleType, cOutput, isOutputRoleType, scope);
	}

	/*
	 * @Override public void setProcessOwner(Process process, Set<Manufacturer>
	 * owners, Scope scope) { Set<Connectable> cOwners = new
	 * HashSet<Connectable>(); cOwners.addAll(owners); //FIXME: we have a set of
	 * Manufacturers!! but one scope!
	 * connectableService.setNElementsOf1toNRelation(poAssocType, process,
	 * po_processRoleType, cOwners, ownerRoleType, scope); }
	 */

	@Override
	public void setProductCanDo(Organization manufacturer, Set<Product> products) {
		Set<Connectable> cProducts = new HashSet<Connectable>();
		cProducts.addAll(products);
		connectableService.setNElementsOf1toNRelation(canDoAssocType,
				manufacturer, canDo_manufRoleType, cProducts,
				canDo_productRoleType, connectableService
						.retrieveScopeBy(manufacturer.getName()));
	}

	@Override
	public void setRootProduct(Organization organisation, Product product) {
		Set<Connectable> c2 = new HashSet<Connectable>();
		c2.add(product);

		connectableService.setNElementsOf1toNRelation(rootPAssocType,
				organisation, rootP_OrgRoleType, c2, rootP_ProdRoleType,
				connectableService.retrieveScopeBy(organisation.getName()));

	}

	@Override
	public void setSubProcesses(Process process, Set<Process> subProcesses,
			Scope scope) {
		Set<Connectable> cSubs = new HashSet<Connectable>();
		cSubs.addAll(subProcesses);
		connectableService.setNElementsOf1toNRelation(superSubAssocType,
				process, superRoleType, cSubs, subRoleType, scope);
	}

	@Override
	public void setTechnologies(Process process, Set<Technology> technologies,
			Scope scope) {
		Set<Connectable> cTechs = new HashSet<Connectable>();
		cTechs.addAll(technologies);
		connectableService.setNElementsOf1toNRelation(techAssocType, process,
				tech_processRoleType, cTechs, techRoleType, scope);
	}

	// setter 4 repositories and services
	// -----------------
	@Override
	public void setConnectableService(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

	@Override
	public ConnectableService getConnectableService() {
		return this.connectableService;
	}

}
