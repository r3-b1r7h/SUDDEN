package biz.sudden.knowledgeData.serviceManagement.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.ScopeRepository;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.knowledgeData.serviceManagement.Util.Util;
import biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct;
import biz.sudden.knowledgeData.serviceManagement.domain.Function;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.domain.Machine;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;
import biz.sudden.knowledgeData.serviceManagement.domain.SimpleProduct;
import biz.sudden.knowledgeData.serviceManagement.domain.SupportingService;
import biz.sudden.knowledgeData.serviceManagement.domain.System;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.knowledgeData.serviceManagement.domain.Thing;
import biz.sudden.knowledgeData.serviceManagement.repository.ComplexProductRepository;
import biz.sudden.knowledgeData.serviceManagement.repository.MaterialRepository;
import biz.sudden.knowledgeData.serviceManagement.repository.SimpleProductRepository;
import biz.sudden.knowledgeData.serviceManagement.repository.SupportingServiceRepository;
import biz.sudden.knowledgeData.serviceManagement.repository.SystemRepository;
import biz.sudden.knowledgeData.serviceManagement.repository.hibernate.ThingRepository;
import biz.sudden.knowledgeData.serviceManagement.service.MachineryType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ManufacturingProcessType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.knowledgeData.serviceManagement.service.TechnologyService;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public class ProductMaterialSupportingServices_ServiceImpl implements
		ProductMaterialSupportingServices_Service {

	private ConnectableService connectableService;
	private ManufacturingProcessType_Service manufacturingService;
	private ComplexProductRepository complexProductRep;
	private SystemRepository systemRep;
	private SimpleProductRepository simpleProductRep;
	private SupportingServiceRepository supportingServicesRep;
	private MaterialRepository materialRep;
	private TechnologyService technologyService;
	private ThingRepository thingRepository;
	private MachineryType_Service machineService;

	// Variables for associationTypes and associationRole types
	// --------------------------------------------------------
	// super - sub - is-A relationship
	public static final String isAAssocType = Item.isAAssocType;
	public static final String isASuperType = Item.isASuperType;// 1
	public static final String isASubType = Item.isASubType;// n

	// input relation -> input relation 4 items compare D4.2 SM - Data View
	// Product / Service Description
	public static final String inputAssocType = "input";
	public static final String getInputRoleType = "getInput";// 1
	public static final String isInputRoleType = "isInput";// n

	// part relation
	public static final String partAssocType = Product.ASSOCIATIONTYPE;
	public static final String wholeRoleType = Product.SUPER;// 1
	public static final String partRoleType = Product.SUB;// n

	// material product relation
	public static final String materialAssocType = Material.materialAssocType;
	public static final String productRoleType = Material.productRoleType;// 1
	public static final String materialRoleType = Material.materialRoleType;// n
	// wrong -> Process requiresMaterial!! -> depicted with addInput(Process,
	// Item)

	// process product relation
	public static final String ppAssocType = "requiresProcesses";
	public static final String pp_productRoleType = "product";
	public static final String pp_processRoleType = "process";

	// SupportingServices relation
	public static final String ssAssocType = "requiresSupportingServices";
	public static final String ss_productRoleType = "product";
	public static final String ssRoleType = "supportingService";

	// variant relation
	public static final String variantAssocType = "hasVariant";
	public static final String itemRoleType = "item";
	public static final String variantRoleType = "variant";

	// alternative relation
	public static final String alternativeAssocType = "alternative";
	public static final String alternativeRoleType = "isAlternative";

	public static final String materialHierachryAssocType = Material.ASSOCIATIONTYPE;
	public static final String materialParentRoleType = Material.SUPER;
	public static final String materialChildRoleType = Material.SUB;

	// Overall TODO mn: delete Methods do not consider existing associations!!!!

	@Override
	public void addAlternative(Item item, Item alternative, Scope scope) {
		connectableService.addToNtoNAssociation(item, alternative,
				alternativeAssocType, alternativeRoleType, scope);
	}

	@Override
	public void addInput(Item item, Item input, Scope scope) {
		connectableService.addToDirected1toNRelation(item, input,
				inputAssocType, getInputRoleType, isInputRoleType, scope);
	}

	@Override
	public void addMaterial(Item productService, Material material, Scope scope) {
		connectableService.addToDirected1toNRelation(productService, material,
				materialAssocType, productRoleType, materialRoleType, scope);
	}

	@Override
	public void addChildMaterial(Material parent, Material child, Scope scope) {
		connectableService.addToDirected1toNRelation(parent, child,
				materialHierachryAssocType, materialParentRoleType,
				materialChildRoleType, scope);
	}// TODO: Further Methods 4 material hierarchy (get set remove)

	@Override
	public void addChildProduct(Product parent, Product child, Scope scope) {
		// TODO Auto-generated method stub
		connectableService.addToDirected1toNRelation(parent, child,
				isAAssocType, isASuperType, isASubType, scope);
	}

	@Override
	public void addChildService(SupportingService parent,
			SupportingService child, Scope scope) {
		// TODO Auto-generated method stub
		connectableService.addToDirected1toNRelation(parent, child,
				isAAssocType, isASuperType, isASubType, scope);
	}

	@Override
	public void addPart(Item whole, Item part, Scope scope) {
		connectableService.addToDirected1toNRelation(whole, part,
				partAssocType, wholeRoleType, partRoleType, scope);
	}

	@Override
	public void addProcesses(Product product, Set<Process> processes,
			Scope scope) {
		Iterator<Process> i = processes.iterator();
		while (i.hasNext()) {
			connectableService.addToDirected1toNRelation(product, i.next(),
					ppAssocType, pp_productRoleType, pp_processRoleType, scope);// TODO:
																				// check
																				// 4
																				// better
																				// solution
																				// because
																				// of
																				// performance
																				// reasons
		}
	}

	@Override
	public void addSupportingService(Product product,
			SupportingService supportingService, Scope scope) {
		connectableService.addToDirected1toNRelation(product,
				supportingService, ssAssocType, ss_productRoleType, ssRoleType,
				scope);
	}

	@Override
	public void addVariant(Item item, Item variant, Scope scope) {
		connectableService.addToDirected1toNRelation(item, variant,
				variantAssocType, itemRoleType, variantRoleType, scope);
	}

	@Override
	public boolean retrieveCanDo(Organization manufacturer,
			String productClassLocalName) {
		// strict retrieveCanDoProduct search!
		List<Item> allProd = new ArrayList<Item>();
		allProd.addAll(simpleProductRep
				.retrieveSimpleProductBy(productClassLocalName));
		allProd.addAll(complexProductRep
				.retrieveComplexProductBy(productClassLocalName));
		allProd.addAll(supportingServicesRep
				.retrieveSupportingServicesBy(productClassLocalName));
		List<Item> canDo = manufacturingService
				.retrieveProductsServicesManufCanDo(manufacturer);
		for (Item ap : allProd) {
			for (Item cd : canDo) {
				if (cd.equals(ap))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean retrieveCanDo(Organization manufacturer, Item product) {
		// strict retrieveCanDoProduct search!
		return manufacturingService.retrieveProductsServicesManufCanDo(
				manufacturer).contains(product);
	}

	@Override
	public List<Organization> retrieveCanDo(String productServiceClassLocalName) {
		List<Organization> canDo = new ArrayList<Organization>();
		List<Product> allProd = new ArrayList<Product>();
		allProd.addAll(simpleProductRep
				.retrieveSimpleProductBy(productServiceClassLocalName));
		allProd.addAll(complexProductRep
				.retrieveComplexProductBy(productServiceClassLocalName));
		for (Product p : allProd)
			canDo.addAll(retrieveCanDo(p));
		List<SupportingService> services = supportingServicesRep
				.retrieveSupportingServicesBy(productServiceClassLocalName);
		for (SupportingService s : services)
			canDo.addAll(retrieveCanDo(s));
		return canDo;
	}

	@Override
	public List<Item> retrieveCanDo(Organization o) {
		return manufacturingService.retrieveProductsServicesManufCanDo(o);
	}

	@Override
	public List<Organization> retrieveCanDo(Item productService) {
		return manufacturingService.retrieveOrganizationsCanDo(productService);
	}

	@Override
	public List<Organization> retrieveCanDo(QualificationProfile p) {
		List<Organization> result = new ArrayList<Organization>();
		for (String name : p.getProductTypes()) {
			result.addAll(retrieveCanDo(Thing.getShortName(name)));
		}
		return result;
	}

	@Override
	public boolean retrieveCanDo(Organization manufacturer,
			QualificationProfile prof) {
		return retrieveCanDo(prof).contains(manufacturer);
	}

	@Override
	public List<Organization> retrieveCanDo(Material m) {

		List<Connectable> products = connectableService
				.retrieveCounterpartConnectables(m, materialRoleType,
						ScopeRepository.RETRIEVEALL_SCOPE);
		List<Organization> result = new ArrayList<Organization>();
		for (Connectable product : products) {
			if (product instanceof Product) {
				result.addAll(retrieveCanDo((Product) product));
			}
		}
		return result;
	}

	@Override
	public List<Organization> retrieveCanDo(Technology t) {
		List<Organization> result = new ArrayList<Organization>();
		for (Material m : technologyService.retrieveMaterials(t,
				ScopeRepository.RETRIEVEALL_SCOPE)) {
			result.addAll(retrieveCanDo(m));
		}
		return result;
	}

	@Override
	public void addMachine(Technology technology, String machine, Scope orgScope) {
		Machine m = machineService.retrieveMachine(machine);
		if (m == null) {
			m = new Machine(machine);
			machineService.createMachine(m);
		}
		this.manufacturingService.addMachine(technology, m, orgScope);
	}

	@Override
	public void addProductsServices(Organization o, Item item) {
		this.manufacturingService.addCanDoProductService(o, item);
	}

	@Override
	public void addTechnology(Material material, Technology technology,
			Scope orgScope) {
		this.technologyService.addTechnology(material, technology, orgScope);
	}

	@Override
	public Scope retrieveScope(String name) {
		return this.connectableService.retrieveScopeBy(name);
	}

	@Override
	public Scope createScope(String name) {
		return this.connectableService.createOrRetrieveScope(name);
	}

	@Override
	public void createComplexProduct(ComplexProduct complexProduct) {
		complexProductRep.create(complexProduct);
	}

	@Override
	public void createMaterial(Material m) {
		materialRep.create(m);
	}

	@Override
	public void createSimpleProduct(SimpleProduct simpleProduct) {
		simpleProductRep.create(simpleProduct);
	}

	@Override
	public void createFunction(Function function) {
		thingRepository.create(function);
	}

	@Override
	public List<Function> retrieveAllFunctions() {
		return thingRepository.retrieveAllByType(Function.class);
	}

	@Override
	public void createSupportingService(SupportingService supportingService) {
		supportingServicesRep.create(supportingService);
	}

	@Override
	public void createSystem(System system) {
		systemRep.create(system);
	}

	@Override
	public void deleteComplexProduct(ComplexProduct complexProduct) {
		connectableService.deleteConnectableAssocs(complexProduct,
				connectableService.getRetrieveAllScope());

		// java.lang.logger.debug("after delete assocs in deleteComplexProduct!");
		complexProductRep.delete(complexProduct);
	}

	@Override
	public void deleteMaterial(Material material) {
		connectableService.deleteConnectableAssocs(material, connectableService
				.getRetrieveAllScope());
		materialRep.delete(material);
	}

	@Override
	public void deleteSimpleProduct(SimpleProduct simpleProduct) {
		connectableService.deleteConnectableAssocs(simpleProduct,
				connectableService.getRetrieveAllScope());
		simpleProductRep.delete(simpleProduct);
	}

	@Override
	public void deleteSupportingService(SupportingService supportingService) {
		connectableService.deleteConnectableAssocs(supportingService,
				connectableService.getRetrieveAllScope());
		supportingServicesRep.delete(supportingService);
	}

	@Override
	public void deleteSystem(System system) {
		connectableService.deleteConnectableAssocs(system, connectableService
				.getRetrieveAllScope());
		systemRep.delete(system);
	}

	@Override
	public Set<Item> retrieveAlternatives(Item item, Scope scope) {
		return Util.getItems(connectableService
				.directedSearchNeighbor(variantAssocType, item,
						variantRoleType, variantRoleType, scope));
	}

	@Override
	public Set<Item> retrieveInput(Item item, Scope scope) {
		return Util
				.getItems(connectableService.directedSearchNeighbor(
						inputAssocType, item, getInputRoleType,
						isInputRoleType, scope));
	}

	@Override
	public Set<Material> retrieveMaterials(Item productService, Scope scope) {
		return Util.getMaterials(connectableService.directedSearchNeighbor(
				materialAssocType, productService, productRoleType,
				materialRoleType, scope));
	}

	@Override
	public Set<Technology> retrieveTechnologies(Item productService, Scope scope) {
		Set<Technology> result = new HashSet<Technology>();
		for (Material m : retrieveMaterials(productService, scope)) {
			result.addAll(technologyService.retrieveTechnologies(m, scope));
		}
		return result;
	}

	@Override
	public Set<Item> retrieveParts(Item item, Scope scope) {
		return Util.getItems(connectableService.directedSearchNeighbor(
				partAssocType, item, wholeRoleType, partRoleType, scope));
	}

	@Override
	public Set<Item> retrieveAllParts(Item item, Scope scope) {
		return Util.getItems(connectableService.directedSearchAll(
				partAssocType, item, wholeRoleType, partRoleType, scope));
	}

	@Override
	public Set<Item> retrieveParts(int distance, Item item, Scope scope) {
		return Util.getItems(connectableService.directedSearch(distance,
				partAssocType, item, wholeRoleType, partRoleType, scope));
	}

	@Override
	public Set<Process> retrieveProcesses(Product product, Scope scope) {
		return Util.getProcesses(connectableService.directedSearchNeighbor(
				ppAssocType, product, pp_productRoleType, pp_processRoleType,
				scope));
	}

	@Override
	public Set<Process> retrieveAllProcesses(Product product, Scope scope) {
		return Util.getProcesses(connectableService.directedSearchAll(
				ppAssocType, product, pp_productRoleType, pp_processRoleType,
				scope));
	}

	@Override
	public Set<Process> retrieveProcesses(int distance, Product product,
			Scope scope) {
		return Util.getProcesses(connectableService.directedSearch(distance,
				ppAssocType, product, pp_productRoleType, pp_processRoleType,
				scope));
	}

	@Override
	public Set<SupportingService> retrieveSupportingServices(Product product,
			Scope scope) {
		return Util.getSupportingServices(connectableService
				.directedSearchNeighbor(ssAssocType, product,
						ss_productRoleType, ssRoleType, scope));
	}

	@Override
	public Set<Item> retrieveVariants(Item item, Scope scope) {
		return Util.getItems(connectableService.directedSearchNeighbor(
				variantAssocType, item, itemRoleType, variantRoleType, scope));
	}

	@Override
	public boolean removeAlternative(Item item, Item alternative, Scope scope) {
		return connectableService.removeFromAssociations(alternativeAssocType,
				item, alternativeRoleType, alternative, alternativeRoleType,
				scope);
	}

	@Override
	public boolean removeInput(Item item, Item input, Scope scope) {
		return connectableService.removeFromAssociations(inputAssocType, item,
				getInputRoleType, input, isInputRoleType, scope);
	}

	@Override
	public boolean removeMaterial(Product product, Material material,
			Scope scope) {
		return connectableService.removeFromAssociations(materialAssocType,
				product, productRoleType, material, materialRoleType, scope);
	}

	@Override
	public boolean removePart(Item whole, Item part, Scope scope) {
		return connectableService.removeFromAssociations(partAssocType, whole,
				wholeRoleType, part, partRoleType, scope);
	}

	@Override
	public boolean removeProcess(Product product, Process process, Scope scope) {
		return connectableService.removeFromAssociations(ppAssocType, product,
				pp_productRoleType, process, pp_processRoleType, scope);
	}

	@Override
	public boolean removeSupportingService(Product product,
			SupportingService supportingService, Scope scope) {
		return connectableService.removeFromAssociations(ssAssocType, product,
				ss_productRoleType, supportingService, ssRoleType, scope);
	}

	@Override
	public boolean removeVariant(Item item, Item variant, Scope scope) {
		return connectableService.removeFromAssociations(variantAssocType,
				item, itemRoleType, variant, variantRoleType, scope);
	}

	@Override
	public ComplexProduct retrieveComplexProduct(Long id) {
		return complexProductRep.retrieve(id);
	}

	@Override
	public List<ComplexProduct> retrieveComplexProduct(String name) {
		return complexProductRep.retrieveComplexProductBy(name);
	}

	@Override
	public List<ComplexProduct> retrieveAllComplexProducts() {
		return complexProductRep.retrieveAll();
	}

	@Override
	public Material retrieveMaterial(Long id) {
		return materialRep.retrieve(id);
	}

	@Override
	public List<Material> retrieveAllMaterials() {
		return materialRep.retrieveAll();
	}

	@Override
	public List<Material> retrieveMaterial(String name) {
		return materialRep.retrieveMaterialBy(name);
	}

	@Override
	public SimpleProduct retrieveSimpleProduct(Long id) {
		return simpleProductRep.retrieve(id);
	}

	@Override
	public List<SimpleProduct> retrieveSimpleProduct(String name) {
		return simpleProductRep.retrieveSimpleProductBy(name);
	}

	@Override
	public List<SimpleProduct> retrieveAllSimpleProducts() {
		return simpleProductRep.retrieveAll();
	}

	@Override
	public SupportingService retrieveSupportingService(Long id) {
		return supportingServicesRep.retrieve(id);
	}

	@Override
	public List<SupportingService> retrieveSupportingService(String name) {
		return supportingServicesRep.retrieveSupportingServicesBy(name);
	}

	@Override
	public List<SupportingService> retrieveAllSupportingServices() {
		return supportingServicesRep.retrieveAll();
	}

	@Override
	public System retrieveSystem(Long id) {
		return systemRep.retrieve(id);
	}

	@Override
	public List<System> retrieveSystem(String name) {
		return systemRep.retrieveSystemBy(name);
	}

	@Override
	public List<System> retrieveAllSystems() {
		return systemRep.retrieveAll();
	}

	@Override
	public Technology retrieveTechnology(Long id) {
		return technologyService.retrieveTechnology(id);
	}

	@Override
	public Technology retrieveTechnoloy(String name) {
		return technologyService.retrieveTechnology(name);
	}

	@Override
	public List<Technology> retrieveAllTechnologies() {
		return technologyService.retrieveAllTechnologies();
	}

	@Override
	public void setAlternatives(Item item, Set<Item> alternatives, Scope scope) {
		Set<Connectable> cAlternative = new HashSet<Connectable>();
		cAlternative.addAll(alternatives);
		connectableService.setNElementsOf1toNRelation(alternativeAssocType,
				item, alternativeRoleType, cAlternative, alternativeRoleType,
				scope);
	}

	@Override
	public void setInput(Item item, Set<Item> input, Scope scope) {
		Set<Connectable> cInput = new HashSet<Connectable>();
		cInput.addAll(input);
		connectableService.setNElementsOf1toNRelation(inputAssocType, item,
				getInputRoleType, cInput, isInputRoleType, scope);
	}

	@Override
	public void setMaterials(Item productService, Set<Material> materials,
			Scope scope) {
		Set<Connectable> cMaterials = new HashSet<Connectable>();
		cMaterials.addAll(materials);
		connectableService.setNElementsOf1toNRelation(materialAssocType,
				productService, productRoleType, cMaterials, materialRoleType,
				scope);
	}

	@Override
	public void setParts(Item whole, Set<Item> parts, Scope scope) {
		Set<Connectable> cParts = new HashSet<Connectable>();
		cParts.addAll(parts);
		connectableService.setNElementsOf1toNRelation(partAssocType, whole,
				wholeRoleType, cParts, partRoleType, scope);
	}

	@Override
	public void setProcesses(Product product, Set<Process> processes,
			Scope scope) {
		Set<Connectable> cProcesses = new HashSet<Connectable>();
		cProcesses.addAll(processes);
		connectableService.setNElementsOf1toNRelation(ppAssocType, product,
				pp_productRoleType, cProcesses, pp_processRoleType, scope);
	}

	@Override
	public void setSupportingServices(Product product,
			Set<SupportingService> supportingService, Scope scope) {
		Set<Connectable> cSServices = new HashSet<Connectable>();
		cSServices.addAll(supportingService);
		connectableService.setNElementsOf1toNRelation(ssAssocType, product,
				ss_productRoleType, cSServices, ssRoleType, scope);
	}

	@Override
	public void setVariants(Item item, Set<Item> variants, Scope scope) {
		Set<Connectable> cVariants = new HashSet<Connectable>();
		cVariants.addAll(variants);
		connectableService.setNElementsOf1toNRelation(variantAssocType, item,
				itemRoleType, cVariants, variantRoleType, scope);

	}

	@Override
	public void updateComplexProduct(ComplexProduct complexProduct) {
		complexProductRep.update(complexProduct);
	}

	@Override
	public void updateMaterial(Material material) {
		materialRep.update(material);
	}

	@Override
	public void updateSimpleProduct(SimpleProduct simpleProduct) {
		simpleProductRep.update(simpleProduct);
	}

	@Override
	public void updateSupportingService(SupportingService supportingService) {
		supportingServicesRep.update(supportingService);
	}

	@Override
	public void updateSystem(System system) {
		systemRep.update(system);
	}

	@Override
	public void updateTechnology(Technology tech) {
		technologyService.updateTechnology(tech);
	}

	// @Override
	// public Set<Connectable> directedSearch(int distance, String
	// associationType, Connectable c1, String roleTypeC1,
	// String roleTypeC2){
	// return connectableService.directedSearch(distance, associationType, c1,
	// roleTypeC1, roleTypeC2);
	// }

	// setter 4 repositories & used services
	// ---------------------
	public void setComplexProductRep(ComplexProductRepository complexProductRep) {
		this.complexProductRep = complexProductRep;
	}

	public void setSystemRep(SystemRepository systemRep) {
		this.systemRep = systemRep;
	}

	public void setSimpleProductRep(SimpleProductRepository simpleProductRep) {
		this.simpleProductRep = simpleProductRep;
	}

	public void setSupportingServicesRep(
			SupportingServiceRepository supportingServicesRep) {
		this.supportingServicesRep = supportingServicesRep;
	}

	public void setMaterialRep(MaterialRepository materialRep) {
		this.materialRep = materialRep;
	}

	public void setConnectableService(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

	public void setManufacturingService(
			ManufacturingProcessType_Service manufacturingService) {
		this.manufacturingService = manufacturingService;
	}

	public void setTechnologyService(TechnologyService techService) {
		this.technologyService = techService;
	}

	public void setMachineService(MachineryType_Service machineService) {
		this.machineService = machineService;
	}

	public ThingRepository getThingRepository() {
		return thingRepository;
	}

	public void setThingRepository(ThingRepository thingRepository) {
		this.thingRepository = thingRepository;
	}

}
