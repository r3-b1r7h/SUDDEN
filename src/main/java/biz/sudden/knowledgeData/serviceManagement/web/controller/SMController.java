package biz.sudden.knowledgeData.serviceManagement.web.controller;

import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct;
import biz.sudden.knowledgeData.serviceManagement.service.ManufacturingProcessType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;

public interface SMController {

	// Test directedSearch
	public String test_directedSearch();

	public String associate();

	public String getInput();

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

	public ProductMaterialSupportingServices_Service getPmsService();

	public void setPmsService(
			ProductMaterialSupportingServices_Service pmsService);

	public ComplexProduct getCp();

	public void setCp(ComplexProduct cp);

	public String navigateToSM();

	public String navigateToTree();

	public String navigateToDummy();

	public ManufacturingProcessType_Service getManufacturingService();

	public void setManufacturingService(
			ManufacturingProcessType_Service manufacturingService);

	public ServiceManagementService getBaseService();

	public void setBaseService(ServiceManagementService baseService);

	public ConnectableService getConnectableService();

	public void setConnectableService(ConnectableService service);

}