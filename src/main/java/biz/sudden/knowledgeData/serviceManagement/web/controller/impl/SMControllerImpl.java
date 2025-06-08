package biz.sudden.knowledgeData.serviceManagement.web.controller.impl;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.service.ManufacturingProcessType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.knowledgeData.serviceManagement.web.controller.SMController;

public class SMControllerImpl implements SMController {

	private Logger logger = Logger.getLogger(this.getClass());

	private ProductMaterialSupportingServices_Service pmsService;
	private ManufacturingProcessType_Service manufacturingService;
	private ServiceManagementService baseService;
	private ConnectableService connectableService;
	// private
	private ComplexProduct cp;
	private ComplexProduct cp0;

	// public String associate(){//test method
	// ComplexProduct cp1 = new ComplexProduct();
	// cp1.setName("rootProduct");
	//
	// ComplexProduct cp2 = new ComplexProduct();
	// cp2.setName("inputProduct");
	//
	// pmsService.createComplexProduct(cp1);
	// pmsService.createComplexProduct(cp2);
	//		
	// pmsService.addInput(cp1, cp2);
	//		
	// // List<Item> i = pmsService.retrieveInput(cp1);
	// List<Item> i = pmsService.retrieveInput(cp1);
	// logger.debug("SMController - retrieveInput size of ItemList in associate: "+i.size());
	//		
	// cp = cp1;
	// cp0 = cp2;
	// return "stay";
	// }

	// Test setInput
	// public String associate(){//test method
	// ComplexProduct cp1 = new ComplexProduct();
	// cp1.setName("rootProduct");
	//
	// ComplexProduct cp2 = new ComplexProduct();
	// cp2.setName("inputProduct");
	//		
	// ComplexProduct cp3 = new ComplexProduct();
	// cp3.setName("inputProduct2");
	//
	//
	// pmsService.createComplexProduct(cp1);
	// pmsService.createComplexProduct(cp2);
	// pmsService.createComplexProduct(cp3);
	//		
	// Set<Item> iL = new HashSet<Item>();
	// iL.add(cp2);
	// iL.add(cp3);
	// pmsService.setInput(cp1, iL);
	//		
	// // List<Item> i = pmsService.retrieveInput(cp1);
	// Set<Item> i = pmsService.retrieveInput(cp1);
	// logger.debug("SMController - retrieveInput size of ItemList in associate: "+i.size());
	//		
	// // logger.debug("SMController - remove inputProduct2");
	// //pmsService.removeInput(cp1, cp2);
	// //test delete
	// // pmsService.deleteComplexProduct(cp2);
	// cp = cp1;
	// cp0 = cp2;
	// return "stay";
	// }

	// Test directedSearch
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #test_directedSearch()
	 */
	public String test_directedSearch() {// test method
		ComplexProduct cp1 = new ComplexProduct();
		cp1.setName("rootProduct");

		ComplexProduct cp2 = new ComplexProduct();
		cp2.setName("partProduct1");

		ComplexProduct cp3 = new ComplexProduct();
		cp3.setName("partProduct1_2");

		pmsService.createComplexProduct(cp1);
		pmsService.createComplexProduct(cp2);
		pmsService.createComplexProduct(cp3);

		pmsService.addPart(cp1, cp2, null);
		pmsService.addPart(cp2, cp3, null);
		// directedSearch size of Connectable List in associate: "+i.size());

		Set<Item> i = pmsService.retrieveAllParts(cp1, connectableService
				.getRetrieveAllScope());

		Iterator<Item> it = i.iterator();
		while (it.hasNext()) {
			logger.debug("part found: "
					+ ((ComplexProduct) it.next()).getName());
		}
		cp0 = cp2;
		return "stay";
	}

	// public String associate(){//test retrieveCanDoProduct
	// ComplexProduct cp1 = new ComplexProduct();
	// cp1.setName("rootProduct");
	//
	// ComplexProduct cp2 = new ComplexProduct();
	// cp2.setName("partProduct1");
	//		
	// ComplexProduct cp3 = new ComplexProduct();
	// cp3.setName("partProduct1_2");
	//
	// pmsService.createComplexProduct(cp1);
	// pmsService.createComplexProduct(cp2);
	// pmsService.createComplexProduct(cp3);
	//
	// //associate cp1 with requiresProcesses
	// Process hotcutting = new Process();
	// hotcutting.setName("hotcutting");
	//		
	// Process cutting = new Process();
	// cutting.setName("cutting");
	//		
	// smService.createProcess(hotcutting);
	// smService.createProcess(cutting);
	//		
	// Set<Process> processes = new HashSet<Process>();
	// processes.add(hotcutting);
	// pmsService.addProcesses(cp1, processes);
	//		
	// //process hierarchy
	// Set<Process> subProcesses = new HashSet<Process>();
	// subProcesses.add(hotcutting);
	// manufacturingService.addSubProcesses(cutting, subProcesses);
	//		
	//		
	// Manufacturer m = new Manufacturer();
	// m.setName("manu_AUDI");
	// smService.createManufacturer(m);
	//		
	// manufacturingService.addProcessOwner(cutting, m);
	//		
	// pmsService.addPart(cp1, cp2);
	// pmsService.addPart(cp2, cp3);
	//		
	// Manufacturer audi =
	// smService.retrieveManufacturerBy("manu_AUDI").iterator().next();
	// ComplexProduct product =
	// pmsService.retrieveComplexProduct("rootProduct").iterator().next();
	//
	// logger.debug("Audi can do Product? "+pmsService.retrieveCanDoProduct(audi,
	// product));
	// logger.debug("Audi can do Product? "+pmsService.retrieveCanDoProduct(m,
	// product));
	// logger.debug("Audi can do Product? "+pmsService.retrieveCanDoProduct(audi,
	// cp1));
	// logger.debug("Audi can do Product? "+pmsService.retrieveCanDoProduct(m,
	// cp1));
	//
	// Set<Item> i = pmsService.retrieveAllParts(cp1);
	//
	// Iterator<Item> it = i.iterator();
	// while(it.hasNext()){
	// logger.debug("part found: "+((ComplexProduct)it.next()).getName());
	// }
	//
	// cp = cp1;
	// cp0 = cp2;
	// return "stay";
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #associate()
	 */
	public String associate() {// test retrieveCanDoProduct
		ComplexProduct cp1 = new ComplexProduct();
		cp1.setName("rootProduct");

		ComplexProduct cp2 = new ComplexProduct();
		cp2.setName("partProduct1");

		ComplexProduct cp3 = new ComplexProduct();
		cp3.setName("partProduct1_2");

		pmsService.createComplexProduct(cp1);
		pmsService.createComplexProduct(cp2);
		pmsService.createComplexProduct(cp3);

		// associate cp1 with requiresProcesses
		// Process hotcutting = new Process();
		// hotcutting.setName("hotcutting");
		//		
		// Process cutting = new Process();
		// cutting.setName("cutting");
		//		
		// smService.createProcess(hotcutting);
		// smService.createProcess(cutting);
		//		
		// Set<Process> processes = new HashSet<Process>();
		// processes.add(hotcutting);
		// pmsService.addProcesses(cp1, processes);
		//		
		// //process hierarchy
		// Set<Process> subProcesses = new HashSet<Process>();
		// subProcesses.add(hotcutting);
		// manufacturingService.addSubProcesses(cutting, subProcesses);
		//		
		//		
		// Manufacturer m = new Manufacturer();
		// m.setName("manu_AUDI");
		// smService.createManufacturer(m);
		//		
		// manufacturingService.addProcessOwner(cutting, m);
		//		
		pmsService.addPart(cp1, cp2, null);
		pmsService.addPart(cp1, cp3, null);
		//		
		// Manufacturer audi =
		// smService.retrieveManufacturerBy("manu_AUDI").iterator().next();
		// ComplexProduct product =
		// pmsService.retrieveComplexProduct("rootProduct").iterator().next();
		//
		// logger.debug("Audi can do Product? "+pmsService.retrieveCanDoProduct(audi,
		// product));
		// logger.debug("Audi can do Product? "+pmsService.retrieveCanDoProduct(m,
		// product));
		// logger.debug("Audi can do Product? "+pmsService.retrieveCanDoProduct(audi,
		// cp1));
		// logger.debug("Audi can do Product? "+pmsService.retrieveCanDoProduct(m,
		// cp1));
		//
		// Set<Item> i = pmsService.retrieveAllParts(cp1);
		//
		// Iterator<Item> it = i.iterator();
		// while(it.hasNext()){
		// logger.debug("part found: "+((ComplexProduct)it.next()).getName());
		// }

		// pmsService.deleteComplexProduct(cp1);
		// logger.debug("after delete cp1");

		// connectableService.deleteConnectableAssocs(cp2);
		pmsService.deleteComplexProduct(cp2);
		logger.debug("after delete cp2");

		Set<Item> cps = pmsService.retrieveAllParts(cp1, connectableService
				.getRetrieveAllScope());
		Iterator<Item> it = cps.iterator();
		while (it.hasNext()) {
			logger.debug("part: " + it.next().getName());
		}
		logger.debug("after getAllParts");

		// cp = cp1;
		// cp0 = cp2;
		return "stay";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #retrieveInput()
	 */
	public String getInput() {
		// List<Item> i = pmsService.retrieveInput(this.cp);
		Set<Item> i = pmsService.retrieveInput(this.cp, connectableService
				.getRetrieveAllScope());
		Iterator<Item> it = i.iterator();
		logger.debug("getInput size of ItemList: " + i.size());
		while (it.hasNext()) {
			logger.debug("--Item : " + it.next().getName());
		}

		return "stay";
	}

	// public ProductServiceType_Services getProductServiceType_S() {
	// return productServiceType_S;
	// }
	//
	// public void setProductServiceType_S(
	// ProductServiceType_Services productServiceType_S) {
	// this.productServiceType_S = productServiceType_S;
	// }
	//	
	// public String createProduct(){
	// ComplexProduct cp = new ComplexProduct();
	// cp.setName("myNewComplexProduct");
	// this.productServiceType_S.createComplexProduct(cp);
	// return "stay";
	// }
	//	
	// public String retrieveProduct(){
	// this.cp =
	// this.productServiceType_S.retrieveComplexProduct("myNewComplexProduct");
	// logger.debug("cp name: "+cp.getName());
	// logger.debug("cp id: "+cp.getId());
	// return "stay";
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #getPmsService()
	 */
	public ProductMaterialSupportingServices_Service getPmsService() {
		return pmsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #setPmsService(biz.sudden.knowledgeData.serviceManagement.service.
	 * ProductMaterialSupportingServices_Service)
	 */
	public void setPmsService(
			ProductMaterialSupportingServices_Service pmsService) {
		this.pmsService = pmsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #getCp()
	 */
	public ComplexProduct getCp() {
		return cp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #setCp(biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct)
	 */
	public void setCp(ComplexProduct cp) {
		this.cp = cp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #navigateToSM()
	 */
	public String navigateToSM() {
		return "toSM";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #navigateToTree()
	 */
	public String navigateToTree() {
		return "toTree";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #navigateToDummy()
	 */
	public String navigateToDummy() {
		return "toDummy";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #getManufacturingService()
	 */
	public ManufacturingProcessType_Service getManufacturingService() {
		return manufacturingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #
	 * setManufacturingService(biz.sudden.knowledgeData.serviceManagement.service
	 * .ManufacturingProcessType_Service)
	 */
	public void setManufacturingService(
			ManufacturingProcessType_Service manufacturingService) {
		this.manufacturingService = manufacturingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #getBaseService()
	 */
	public ServiceManagementService getBaseService() {
		return baseService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #
	 * setBaseService(biz.sudden.baseAndUtility.service.ServiceManagementService
	 * )
	 */
	public void setBaseService(ServiceManagementService baseService) {
		this.baseService = baseService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #getConnectableService()
	 */
	public ConnectableService getConnectableService() {
		return connectableService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.knowledgeData.serviceManagement.web.controller.impl.SMController
	 * #
	 * setConnectableService(biz.sudden.baseAndUtility.service.ConnectableService
	 * )
	 */
	public void setConnectableService(ConnectableService service) {
		this.connectableService = service;
	}

}
