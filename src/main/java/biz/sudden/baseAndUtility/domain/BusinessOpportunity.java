package biz.sudden.baseAndUtility.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

@Entity
public class BusinessOpportunity implements Connectable {

	Long id;

	String name;

	String description;

	// For specifying when you know *which* decomposition you want
	String individualGoalURI;
	// For specfying when you want info out of a class
	String classGoalURI;

	// I will update this when the goal gets set. We have some problems with
	// setting the goal as Jena Individual;
	// this hopefully helps us in linking between Jena & TopicMap

	/** the user that started this BO for contacting */
	SuddenUser boo;
	SuddenUser corePartner1;
	SuddenUser corePartner2;
	SuddenUser corePartner3;
	SuddenUser corePartner4;

	Integer quantityPerYearFinalProduct;

	Date startOfProduction;

	/**
	 * user either Org. EndCustomer or if not registered the String
	 * EndCustomerName
	 */
	Organization endCustomer;
	String endCustomerName;

	Product goal;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the boo
	 */
	@ManyToOne(optional = true)
	public SuddenUser getBoo() {
		return boo;
	}

	/**
	 * @param corePartner1
	 *            the corePartner1 to set
	 */
	public void setCorePartner1(SuddenUser boo) {
		this.corePartner1 = boo;
	}

	/**
	 * @return the corePartner1
	 */
	@ManyToOne(optional = true)
	public SuddenUser getcorePartner1() {
		return corePartner1;
	}

	/**
	 * @param corePartner2
	 *            the corePartner2 to set
	 */
	public void setCorePartner2(SuddenUser boo) {
		this.corePartner2 = boo;
	}

	/**
	 * @return the corePartner2
	 */
	@ManyToOne(optional = true)
	public SuddenUser getcorePartner2() {
		return corePartner2;
	}

	/**
	 * @param corePartner3
	 *            the corePartner3 to set
	 */
	public void setCorePartner3(SuddenUser boo) {
		this.corePartner3 = boo;
	}

	/**
	 * @return the corePartner3
	 */
	@ManyToOne(optional = true)
	public SuddenUser getcorePartner3() {
		return corePartner3;
	}

	/**
	 * @param corePartner4
	 *            the corePartner4 to set
	 */
	public void setCorePartner4(SuddenUser boo) {
		this.corePartner4 = boo;
	}

	/**
	 * @return the corePartner4
	 */
	@ManyToOne(optional = true)
	public SuddenUser getcorePartner4() {
		return corePartner4;
	}

	/**
	 * @param boo
	 *            the boo to set
	 */
	public void setBoo(SuddenUser boo) {
		this.boo = boo;
	}

	/**
	 * @return the quantityPerYearFinalProduct
	 */
	public Integer getQuantityPerYearFinalProduct() {
		return quantityPerYearFinalProduct;
	}

	/**
	 * @param quantityPerYearFinalProduct
	 *            the quantityPerYearFinalProduct to set
	 */
	public void setQuantityPerYearFinalProduct(
			Integer quantityPerYearFinalProduct) {
		this.quantityPerYearFinalProduct = quantityPerYearFinalProduct;
	}

	/**
	 * @return the startOfProduction
	 */
	public Date getStartOfProduction() {
		return startOfProduction;
	}

	/**
	 * @param startOfProduction
	 *            the startOfProduction to set
	 */
	public void setStartOfProduction(Date startOfProduction) {
		this.startOfProduction = startOfProduction;
	}

	/**
	 * @return the endCustomer
	 */
	@ManyToOne(optional = true)
	public Organization getEndCustomer() {
		return endCustomer;
	}

	/**
	 * @param endCustomer
	 *            the endCustomer to set
	 */
	public void setEndCustomer(Organization endCustomer) {
		this.endCustomer = endCustomer;
	}

	/**
	 * @return the endCustomerName
	 */
	public String getEndCustomerName() {
		return endCustomerName;
	}

	/**
	 * @param endCustomerName
	 *            the endCustomerName to set
	 */
	public void setEndCustomerName(String endCustomerName) {
		this.endCustomerName = endCustomerName;
	}

	/*
	 * @return the class of product you're after
	 */
	@Transient
	public String getClassGoalURI() {
		if (goal != null)
			return getGoal().getName();
		else
			return null;
	}

	/*
	 * @return the URI of a specific instance of the goal class that you're
	 * after
	 */
	@Transient
	public String getIndividualGoalURI() {
		return individualGoalURI;
	}

	/**
	 * @param goal
	 *            the goal to set
	 */
	public void setIndividualGoalURI(String individualURI) {
		this.individualGoalURI = individualURI;
	}

	/**
	 * This sets the individualGoalURI which is the LocalName of the Product
	 * FIXME : Martin Check this
	 */
	public void setGoal(Product goal) {
		this.goal = goal;
		// this.setIndividualGoalURI(goal.getName());
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, optional = true)
	public Product getGoal() {
		return goal;
	}

}
