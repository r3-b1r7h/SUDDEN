package biz.sudden.knowledgeData.serviceManagement.service;

import java.util.List;
import java.util.Set;

import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct;
import biz.sudden.knowledgeData.serviceManagement.domain.Function;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;
import biz.sudden.knowledgeData.serviceManagement.domain.SimpleProduct;
import biz.sudden.knowledgeData.serviceManagement.domain.SupportingService;
import biz.sudden.knowledgeData.serviceManagement.domain.System;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

/**
 * ProductMaterialSupportingServices_Service - This interface provides methods
 * to create, retrieve, update and delete Materials, SupportingServices and
 * concrete Products Furthermore it provides methods to define structure of
 * Products and Services and methods to associate products with respective
 * processes, products with material and products with SupportingSservices.
 * Additionally it defines a method to check if a manufacturer "can do" a
 * product.
 * 
 * 
 * @author MN
 * 
 */
public interface ProductMaterialSupportingServices_Service {

	// CREATE, RETRIEVE, UPDATE, DELETE methods for domain objects
	// -----------------------------------------------------------

	// CRUD methods for concrete Products [System, ComplexProduct,
	// SimpleProduct]
	public void createSystem(System system);

	public System retrieveSystem(Long id);

	public List<System> retrieveSystem(String name);

	public List<System> retrieveAllSystems();

	public void updateSystem(System system);

	public void deleteSystem(System system);

	public void createComplexProduct(ComplexProduct complexProduct);

	public ComplexProduct retrieveComplexProduct(Long id);

	public List<ComplexProduct> retrieveComplexProduct(String name);

	public List<ComplexProduct> retrieveAllComplexProducts();

	public void updateComplexProduct(ComplexProduct complexProduct);

	public void deleteComplexProduct(ComplexProduct complexProduct);

	public void createSimpleProduct(SimpleProduct simpleProduct);

	public SimpleProduct retrieveSimpleProduct(Long id);

	public List<SimpleProduct> retrieveSimpleProduct(String name);

	public List<SimpleProduct> retrieveAllSimpleProducts();

	public void updateSimpleProduct(SimpleProduct simpleProduct);

	public void deleteSimpleProduct(SimpleProduct simpleProduct);

	// CRUD methods for Material and Supporting Services
	public void createMaterial(Material m);

	public Material retrieveMaterial(Long id);

	public List<Material> retrieveMaterial(String name);

	public List<Material> retrieveAllMaterials();

	public void updateMaterial(Material material);

	public void deleteMaterial(Material material);

	public void createFunction(Function function);

	public List<Function> retrieveAllFunctions();

	public void createSupportingService(SupportingService supportingService);

	public SupportingService retrieveSupportingService(Long id);

	public List<SupportingService> retrieveSupportingService(String name);

	public List<SupportingService> retrieveAllSupportingServices();

	public void updateSupportingService(SupportingService supportingService);

	public void deleteSupportingService(SupportingService supportingService);

	/**
	 * add Processes which realize a given Product to the given Product
	 * (Functionality Nr.1 in D4.2)
	 * 
	 * @param product
	 *            Product to which the given Processes are added
	 * @param processes
	 *            represents a Set of Process Objects which realize "prod"
	 * @param scope
	 *            TODO
	 */
	public void addProcesses(Product product, Set<Process> processes,
			Scope scope);

	/**
	 * set Processes which realize a given Product. If Processes already exists
	 * the will be discarded
	 * 
	 * @param product
	 *            Product for which the given Processes are set
	 * @param processes
	 *            represents a Set of Process Objects which realize "prod"
	 * @param scope
	 *            TODO
	 */
	public void setProcesses(Product product, Set<Process> processes,
			Scope scope);

	/**
	 * get (direct) processes which realize a given product
	 * 
	 * @param product
	 *            given Product
	 * @param scope
	 *            TODO
	 * @return a Set of Processes, an empty set if none exist
	 */
	public Set<Process> retrieveProcesses(Product product, Scope scope);

	/**
	 * get all processes (direct & indirect) which realize a given product
	 * 
	 * @param product
	 *            given Product
	 * @param scope
	 *            TODO
	 * @return a Set of Processes, an empty set if none exist
	 */
	public Set<Process> retrieveAllProcesses(Product product, Scope scope);

	/**
	 * get all processes (direct & indirect) which realize a given product
	 * according to the given distance. Distance 1 retrieves all "neighbors",
	 * distance -1 all processes
	 * 
	 * @param product
	 *            given Product
	 * @param scope
	 *            TODO
	 * 
	 * @return a Set of Processes, an empty set if none exist
	 */
	public Set<Process> retrieveProcesses(int distance, Product product,
			Scope scope);

	/**
	 * remove a Process from a Product
	 * 
	 * @param product
	 *            Product from which the given process will be removed
	 * @param process
	 *            Process which will be removed
	 * @param scope
	 *            TODO
	 * @return true if "process" has successfully been removed
	 */
	public boolean removeProcess(Product product, Process process, Scope scope);

	// Functionality Nr.4 in D4.2
	// --------------------------

	// eigentlich sollte dies ueber Input von Prozessen bzw. ueber Prozesse die
	// ein Produkt realisieren
	// abgebildet werden
	public void addMaterial(Item product, Material material, Scope scope);

	public void setMaterials(Item productService, Set<Material> materials,
			Scope scope);

	/**
	 * get materials which are requiered by a product
	 * 
	 * @param scope
	 *            to use
	 * @param prod
	 * 
	 * @return
	 */
	public Set<Material> retrieveMaterials(Item productService, Scope scope);

	public Set<Technology> retrieveTechnologies(Item productService, Scope scope);

	public boolean removeMaterial(Product product, Material material,
			Scope scope);

	// public Set<Product> getMaterialParents(Material material);TODO

	public void addChildMaterial(Material parent, Material child, Scope scope);

	public void addChildProduct(Product parent, Product child, Scope scope);

	public void addSupportingService(Product product,
			SupportingService supportingService, Scope scope);

	public void addChildService(SupportingService parent,
			SupportingService child, Scope scope);

	public void setSupportingServices(Product product,
			Set<SupportingService> supportingService, Scope scope);

	public Set<SupportingService> retrieveSupportingServices(Product product,
			Scope scope);

	public boolean removeSupportingService(Product product,
			SupportingService supportingService, Scope scope);

	// public Set<Product> getSupportingServiceParents(SupportingService
	// supportingService); TODO

	// --------------------------
	// --------------------------

	public Scope retrieveScope(String name);

	public Scope createScope(String name);

	public void addProductsServices(Organization o, Item item);

	public void addTechnology(Material material, Technology technology,
			Scope orgScope);

	public void addMachine(Technology technology, String string, Scope orgScope);

	/**
	 * Based on a product/service and a manufacturer check if he can do the
	 * product (i.e has all processes required, or has even specified the given
	 * product (Functionality Nr.6 in D4.2)
	 * 
	 * @param manu
	 *            Manufacturer which will be evaluated
	 * @param prod
	 *            Product which shall be manufactured
	 * @return true if the manufacturer "can do" the product, otherwise false
	 */
	public boolean retrieveCanDo(Organization manufacturer,
			String productClassLocalName);

	/**
	 * Find all organizations which can manufacture a given product
	 * 
	 * @param product
	 *            A qualification profile containing the info defining the
	 *            product for which manufacturers are searched (inc materials,
	 *            function etc etc)
	 * @return a List of organizations which can manufacture a product
	 */
	public List<Organization> retrieveCanDo(String productServiceClassLocalName);

	public List<Item> retrieveCanDo(Organization o);

	public List<Organization> retrieveCanDo(Item productService);

	public List<Organization> retrieveCanDo(Material m);

	public List<Organization> retrieveCanDo(Technology t);

	public boolean retrieveCanDo(Organization manufacturer, Item productService);

	public List<Organization> retrieveCanDo(QualificationProfile p);

	public boolean retrieveCanDo(Organization manufacturer,
			QualificationProfile prof);

	// methods to define structure of Products and Services (Items)
	// ->compare D 3.1 Annex
	// methods 4 associating Items, covers Functionality 5 in D4.2 (create and
	// link
	// ---------------------------

	// part
	public void addPart(Item whole, Item part, Scope scope);

	public void setParts(Item whole, Set<Item> parts, Scope scope);

	/**
	 * retrieves all direct parts of an item.
	 * 
	 * @param item
	 *            given Item
	 * @param scope
	 *            TODO
	 * @return a Set of items which are direct parts of the given item, an empty
	 *         Set if none exist
	 */
	public Set<Item> retrieveParts(Item item, Scope scope);

	/**
	 * retrieves all parts of an item, meaning also indirectly related parts
	 * 
	 * @param item
	 *            given Item
	 * @param scope
	 *            TODO
	 * @return a Set of items which are direct and indirect parts of the given
	 *         item, an empty Set if none exist
	 */
	public Set<Item> retrieveAllParts(Item item, Scope scope);

	/**
	 * retrieves all parts of an item according to the given distance. Distance
	 * 1 retrieves all "neighbors", distance -1 all parts
	 * 
	 * @param distance
	 *            given search distance
	 * @param item
	 *            given Item
	 * @param scope
	 *            TODO
	 * @return a Set of Items which are parts of the given item in the given
	 *         distance, an empty set if none exist
	 */
	public Set<Item> retrieveParts(int distance, Item item, Scope scope);

	public boolean removePart(Item whole, Item part, Scope scope);

	// public Set<Item> getPartParents(Item item); TODO

	// Input
	public void addInput(Item item, Item input, Scope scope);

	public void setInput(Item item, Set<Item> input, Scope scope);

	public Set<Item> retrieveInput(Item item, Scope scope);

	public boolean removeInput(Item item, Item input, Scope scope);

	// public Set<Item> getInputParents(Item item);//get Item which play role
	// retrieveInput in an input relation TODO

	// alternative
	public void addAlternative(Item item, Item alternativ, Scope scope);

	public void setAlternatives(Item item, Set<Item> alternatives, Scope scope);

	public Set<Item> retrieveAlternatives(Item item, Scope scope);

	public boolean removeAlternative(Item item, Item alternative, Scope scope);

	// variant
	public void addVariant(Item item, Item variant, Scope scope);

	public void setVariants(Item item, Set<Item> variants, Scope scope);

	public Set<Item> retrieveVariants(Item item, Scope scope);

	public boolean removeVariant(Item item, Item variant, Scope scope);

	public Technology retrieveTechnology(Long id);

	public Technology retrieveTechnoloy(String name);

	public List<Technology> retrieveAllTechnologies();

	public void updateTechnology(Technology tech);

	// public Set<Item> getVariantParents(Item item);TODO

	// just for testing reasons
	// public Set<Connectable> directedSearch(int distance, String
	// associationType, Connectable c1, String roleTypeC1, String roleTypeC2);

}
