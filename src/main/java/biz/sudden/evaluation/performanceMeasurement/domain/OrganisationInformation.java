package biz.sudden.evaluation.performanceMeasurement.domain;

import java.util.List;

import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public class OrganisationInformation {
	protected Organization organisation;
	protected List<Item> productsServices;
	protected List<Material> materials;
	protected List<Technology> technologies;

	/**
	 * @return the organisation
	 */
	public Organization getOrganisation() {
		return organisation;
	}

	/**
	 * @param organisation
	 *            the organisation to set
	 */
	public void setOrganisation(Organization organisation) {
		this.organisation = organisation;
	}

	/**
	 * @return the products
	 */
	public List<Item> getProductsServices() {
		return productsServices;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProductsServices(List<Item> productsServices) {
		this.productsServices = productsServices;
	}

	/**
	 * @return the materials
	 */
	public List<Material> getMaterials() {
		return materials;
	}

	/**
	 * @param materials
	 *            the materials to set
	 */
	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	/**
	 * @return the technologies
	 */
	public List<Technology> getTechnologies() {
		return technologies;
	}

	/**
	 * @param technologies
	 *            the technologies to set
	 */
	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}

}
