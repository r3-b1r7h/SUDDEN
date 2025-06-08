package biz.sudden.knowledgeData.serviceManagement.service;

import java.util.List;
import java.util.Set;

import biz.sudden.baseAndUtility.domain.Manufacturer;
import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.domain.Machine;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;
import biz.sudden.knowledgeData.serviceManagement.domain.SupportingService;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

/**
 * 
 * ManufacturingProcessType_Service - This interface defines methods which are
 * used to specify (Manufacturing)Processes
 * 
 * @author Matthias Neubauer
 * 
 */
public interface ManufacturingProcessType_Service {

	public void addCanDoProductService(Organization manufacturer,
			Item productService);

	// TODO: doc
	// empty set
	public List<Item> retrieveProductsServicesManufCanDo(
			Organization manufacturer);

	public List<Organization> retrieveOrganizationsCanDo(Item productService);

	public boolean removeProductCanDo(Organization manufacturer, Product product);

	public void setProductCanDo(Organization manufacturer, Set<Product> products);

	public List<Product> retrieveRootProducts(Organization organisation);

	public void setRootProduct(Organization organisation, Product product);

	/**
	 * general method to add "Input" to a given Process. (Functionality Nr.2 in
	 * D4.2)
	 * 
	 * @param inputItem
	 *            Item (could also be one of following subclasses: Material,
	 *            Product, ComplexProduct, SimpleProduct, System,
	 *            SupportingService)
	 * @param scope
	 *            TODO
	 * @param p
	 *            Process to which input is added
	 */
	public void addInput(Process process, Item inputItem, Scope scope);

	/**
	 * general method to set "Input" of a given Process. Existing input items
	 * are discarded
	 * 
	 * @param process
	 *            Process for which input is set
	 * @param inputItem
	 *            Items which are set (could also be one of following
	 *            subclasses: Material, Product, ComplexProduct, SimpleProduct,
	 *            System, SupportingService)
	 * @param scope
	 *            TODO
	 */
	public void setInput(Process process, Set<Item> inputItem, Scope scope);

	/**
	 * get "Input" of a Process
	 * 
	 * @param process
	 *            Process which is evaluated
	 * @param scope
	 *            TODO
	 * @return a Set of Items which represent the input for a process, an empty
	 *         Set if none exists
	 */
	public Set<Item> retrieveInput(Process process, Scope scope);

	/**
	 * remove an Input Item from a given Process
	 * 
	 * @param process
	 *            Process from which the given Input Item will be removed
	 * @param inputItem
	 *            Item which represents input for the given Process
	 * @param scope
	 *            TODO
	 * @return true if input has successfully been removed, otherwise false
	 */
	public boolean removeInput(Process process, Item inputItem, Scope scope);

	/**
	 * general method to add "Output" to a given Process.
	 * 
	 * @param process
	 *            Process to which output is added
	 * @param outputItem
	 *            Item (could also be one of following subclasses: Material,
	 *            Product, ComplexProduct, SimpleProduct, System,
	 *            SupportingService)
	 * @param scope
	 *            TODO
	 */
	public void addOutput(Process process, Item outputItem, Scope scope);

	/**
	 * general method to set "Output" of a given Process.
	 * 
	 * @param process
	 *            Process for which output is set
	 * @param outputItem
	 *            Items which are set (could also be one of following
	 *            subclasses: Material, Product, ComplexProduct, SimpleProduct,
	 *            System, SupportingService)
	 * @param scope
	 *            TODO
	 */
	public void setOutput(Process process, Set<Item> outputItem, Scope scope);

	/**
	 * get "Output" of a Process
	 * 
	 * @param process
	 *            Process which is evaluated
	 * @param scope
	 *            TODO
	 * @return a Set of Items which represent the output of a process, an empty
	 *         Set if none exists
	 */
	public Set<Item> retrieveOutput(Process process, Scope scope);

	/**
	 * remove an Output Item from a given Process
	 * 
	 * @param process
	 *            Process from which the given Output Item will be removed
	 * @param outputItem
	 *            Item which represents output of the given Process
	 * @param scope
	 *            TODO
	 * @return true if output has successfully been removed, otherwise false
	 */
	public boolean removeOutput(Process process, Item outputItem, Scope scope);

	// finder Methods 4 concrete Input and Output (sub-)classes
	/**
	 * get Input Material for a given Process
	 * 
	 * @param process
	 *            Process which will be evaluated
	 * @param scope
	 *            TODO
	 * @return returns a Set of Material Objects which represent Input for the
	 *         given Process, an empty Set if none exists
	 */
	public Set<Material> retrieveInputMaterial(Process process, Scope scope);

	/**
	 * get Output Material for a given Process
	 * 
	 * @param process
	 *            Process which will be evaluated
	 * @param scope
	 *            TODO
	 * @return returns a Set of Material Objects which represent Output for the
	 *         given Process, an empty Set if none exists
	 */
	public Set<Material> retrieveOutputMaterial(Process process, Scope scope);

	/**
	 * get Input Products for a given Process
	 * 
	 * @param process
	 *            Process which will be evaluated
	 * @param scope
	 *            TODO
	 * @return returns a Set of Product Objects which represent Input for the
	 *         given Process, an empty Set if none exists
	 */
	public List<Product> retrieveInputProducts(Process process, Scope scope);

	/**
	 * get Output Products for a given Process
	 * 
	 * @param process
	 *            Process which will be evaluated
	 * @param scope
	 *            TODO
	 * @return returns a Set of Product Objects which represent Output for the
	 *         given Process, an empty Set if none exists
	 */
	public List<Product> retrieveOutputProducts(Process process, Scope scope);

	/**
	 * get Input SupportingService for a given Process
	 * 
	 * @param process
	 *            Process which will be evaluated
	 * @param scope
	 *            TODO
	 * @return returns a Set of SupportingService Objects which represent Input
	 *         for the given Process, an empty Set if none exists
	 */
	public Set<SupportingService> retrieveInputSupportingServices(
			Process process, Scope scope);

	/**
	 * get Output SupportingService for a given Process
	 * 
	 * @param process
	 *            Process which will be evaluated
	 * @param scope
	 *            TODO
	 * @return returns a Set of SupportingService Objects which represent Output
	 *         for the given Process, an empty Set if none exists
	 */
	public Set<SupportingService> retrieveOutputSupportingServices(
			Process process, Scope scope);

	// Methods 4 process owner
	/**
	 * add a Process Owner to a concrete Process
	 * 
	 * @param process
	 *            Process for which an owner will be added
	 * @param owner
	 *            Manufacturer who owns the process
	 * @param scope
	 *            TODO
	 */
	public void addProcessOwner(Process process, Manufacturer owner, Scope scope);

	/**
	 * set a Process Owner for a concrete Process
	 * 
	 * @param process
	 *            Process for which an owner will be set
	 * @param owners
	 *            Manufacturer who own the process
	 * @param scope
	 *            TODO FIXME one socpe a set of owners :( public void
	 *            setProcessOwner(Process process, Set<Manufacturer> owners,
	 *            Scope scope);
	 */

	/**
	 * get owners of a process
	 * 
	 * @param process
	 *            Process for which owners will be retrieved
	 * @param scope
	 *            TODO
	 * @return returns a Set of owners for the given Process, an empty Set if
	 *         none exist
	 */
	public List<Organization> retrieveProcessOwner(Process process, Scope scope);

	/**
	 * get all Processes which a Manufacturer owns
	 * 
	 * @param manufacturer
	 *            Manufacturer for which owned processes are retrieved
	 * @param scope
	 *            TODO
	 * @return a Set of Processes, an empty list if none exist
	 */
	public Set<Process> retrieveProcessesOwned(Manufacturer manufacturer);

	/**
	 * remove a Process owner from a given Process
	 * 
	 * @param process
	 *            given Process
	 * @param owner
	 *            given Manufacturer which represents the owner of the process
	 * @return true if the owner has successfully been removed
	 */
	public boolean removeProcessOwner(Process process, Manufacturer owner);

	// Methods 4 Technology and Machine
	/**
	 * add a Technology which realizes a Process to a given Process. (multiple
	 * Techologies can be added to a Process)
	 * 
	 * @param process
	 *            given Process
	 * @param scope
	 *            TODO
	 * @param t
	 *            Technology which realizes a Process
	 */
	public void addTechnology(Process process, Technology technology,
			Scope scope);

	/**
	 * set Technologies which realize a Process for a given Process
	 * 
	 * @param process
	 *            given Process
	 * @param scope
	 *            TODO
	 * @param t
	 *            Technology which realizes a Process
	 */
	public void setTechnologies(Process process,
			Set<Technology> setOfTechnologies, Scope scope);

	/**
	 * get Technoligies which are used to realize a process
	 * 
	 * @param process
	 *            Process for which Technoligies are determined
	 * @param scope
	 *            TODO
	 * @return a Set of Technology Objects, an empty Set if none exist
	 */
	public Set<Technology> retrieveTechnologies(Process process, Scope scope);

	/**
	 * remove a Technology from a given Process
	 * 
	 * @param process
	 *            given Process
	 * @param technology
	 *            Technology which shall be removed
	 * @param scope
	 *            TODO
	 * @return true if t has successfully been removed, otherwise false
	 */
	public boolean removeTechology(Process process, Technology technology,
			Scope scope);

	// Functionality Nr.3 in D4.2
	/**
	 * add a Machine to the defined Machines for a given Technology.
	 * 
	 * @param machine
	 *            Machine which "implements" the process
	 * @param scope
	 *            TODO
	 * @param process
	 *            given Process
	 */
	public void addMachine(Technology technology, Machine machine, Scope scope);

	/**
	 * set Machines for a given Technology in a specific context.
	 * 
	 * @param technology
	 *            given Technology
	 * @param machines
	 *            Machines which "implement" the process
	 * @param scope
	 *            TODO
	 * @param scope
	 *            provides valid context
	 */
	public void setMachines(Technology technology, Set<Machine> machines,
			Scope scope);

	/**
	 * get Machines which "implement" a given technology in a given context
	 * (Scope)
	 * 
	 * @param technology
	 *            given technology
	 * @param scope
	 *            TODO
	 * @param scope
	 *            scope which defines the valid context in which Machines for
	 *            the given technology are searched
	 * @return a Set of Machines which "implement" the given Technology, an
	 *         empty Set if none have been defined
	 */
	public Set<Machine> retrieveMachines(Technology technology, Scope scope);

	/**
	 * remove a Machine from a given Process
	 * 
	 * @param scope
	 *            TODO
	 * @param process
	 *            given Process
	 * @param m
	 *            Machine which will be removed
	 * 
	 * @return true if m has successfully been removed
	 */
	public boolean removeMachine(Technology technology, Machine machine,
			Scope scope);

	// methods to define structure of Material Processing -> compare D 3.1 Annex
	// II
	/**
	 * add sub processes to a given Process
	 * 
	 * @param process
	 *            given Process
	 * @param subProcesses
	 *            a Set of Processes which represent sub processes for a given
	 *            process
	 * @param scope
	 *            TODO
	 */
	public void addSubProcesses(Process process, Set<Process> subProcesses,
			Scope scope);

	/**
	 * set sub-processes for a given Process
	 * 
	 * @param process
	 *            given Process
	 * @param subProcesses
	 *            a Set of Processes which represent sub processes for a given
	 *            process
	 * @param scope
	 *            TODO
	 */
	public void setSubProcesses(Process process, Set<Process> subProcesses,
			Scope scope);

	/**
	 * get sub processes of a given Process (direct neighbors)
	 * 
	 * @param process
	 *            given Process
	 * @return a Set of (Sub-)Processes of a given Process, an empty Set if none
	 *         exist
	 */
	public Set<Process> retrieveSubProcesses(Process process, Scope scope);

	/**
	 * get all sub processes of a given Process (also indirect neighbors)
	 * 
	 * @param process
	 *            given Process
	 * @param scope
	 *            TODO
	 * @return a Set of (Sub-)Processes of a given Process, an empty Set if none
	 *         exist
	 */
	public Set<Process> retrieveAllSubProcesses(Process process, Scope scope);

	/**
	 * remove a sub-process from a given Process
	 * 
	 * @param process
	 *            given Process
	 * @param scope
	 *            TODO
	 * @param sub
	 *            sub-process which will be removed
	 * @return true if sub has successfully been removed
	 */
	public boolean removeSubProcess(Process process, Process subProcess,
			Scope scope);

	/**
	 * get super Process of a given Process
	 * 
	 * @param process
	 *            given Process
	 * @param scope
	 *            TODO
	 * @return a Process which represents the "Super" Process of "process", null
	 *         if none exits
	 */
	public Process retrieveSuperProcess(Process process, Scope scope);

	public void setConnectableService(ConnectableService cs);

	public ConnectableService getConnectableService();
}
