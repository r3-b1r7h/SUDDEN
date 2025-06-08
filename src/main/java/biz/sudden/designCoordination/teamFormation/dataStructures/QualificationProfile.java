package biz.sudden.designCoordination.teamFormation.dataStructures;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.knowledgeData.serviceManagement.domain.Thing;

@Entity
public class QualificationProfile implements Connectable {

	Long id;
	Logger logger = Logger.getLogger(this.getClass());

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	// TODO again please feel more than free to replace this with the 'real'
	// class
	// (or more
	// likely classes) when they appear. This will briefly break a few things in
	// the TF module
	// but they'll be fixable soon enough.

	/*
	 * This class btw hypothetically represents a collection of a set of
	 * criteria used to check that a supplier is theoretically capable of
	 * filling a given role.
	 * 
	 * Currently it holds: 1) The product which you want making - this is the
	 * 'ideal' choice 2) The materials from which this product might be made 3)
	 * The functions that this product might provide
	 */

	// NB Product *includes* services for now
	String productIndividualURI;

	public String getProductIndividualURI() {
		return productIndividualURI;
	}

	public void setProductIndividualURI(String productIndividualURI) {
		this.productIndividualURI = productIndividualURI;
	}

	@Transient
	public String getProductWithoutPrefix() {
		return productIndividualURI
				.substring(productIndividualURI.indexOf("#") + 1);
	}

	// Not just one - I can think of scenarios where you plausibly want >1
	// Owl is happy with such things
	private List<String> productTypes;
	private List<String> productSuperTypes;

	private List<String> potentialMaterialURIs;
	private List<String> functionsProvidedURIs;
	private List<String> potentialProcessingMethodURIs;

	/**
	 * @return the requiredProductHasTypes
	 */

	@CollectionOfElements
	// @Transient
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<String> getProductTypes() {
		return productTypes;
	}

	/**
	 * @param requiredProductHasTypes
	 *            the requiredProductHasTypes to set
	 */
	public void setProductTypes(List<String> requiredProductHasTypes) {
		productTypes = requiredProductHasTypes;
	}

	/**
	 * @return the requiredProductHasSuperTypes
	 */
	@CollectionOfElements
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<String> getProductSuperTypes() {
		return productSuperTypes;
	}

	@Transient
	public String getFirstProductName() {
		String result = null;
		if (productTypes != null && productTypes.size() > 0) {
			result = Thing.getShortName(productTypes.get(0));
		}
		if (result == null)
			result = Thing.getShortName(this.productIndividualURI);
		if (result == null || result.length() == 0)
			result = "--";
		return result;
	}

	// @CollectionOfElements
	// @LazyCollection(LazyCollectionOption.FALSE)
	@Transient
	// its generated!!! so make this transient!
	public List<String> getProductNames() {
		List<String> result = null;
		if (productTypes != null && productTypes.size() > 0) {
			result = new ArrayList<String>(productTypes.size());
			for (String x : productTypes)
				result.add(Thing.getShortName(x));
		}
		return result;
	}

	/**
	 * @param requiredProductHasSuperTypes
	 *            the requiredProductHasSuperTypes to set
	 */
	public void setProductSuperTypes(List<String> requiredProductHasSuperTypes) {
		productSuperTypes = requiredProductHasSuperTypes;
	}

	/**
	 * @param potentialMaterials
	 *            the potentialMaterials to set
	 */
	public void setPotentialMaterialURIs(List<String> potentialMaterials) {
		this.potentialMaterialURIs = potentialMaterials;
	}

	/**
	 * @param potentialProcessingMethods
	 *            the potentialProcessingMethods to set
	 */
	public void setPotentialProcessingMethodURIs(
			List<String> potentialProcessingMethods) {
		this.potentialProcessingMethodURIs = potentialProcessingMethods;
	}

	// Assumption made here that the specific instance of the part is only found
	// in one place :)
	String locationOfPart;

	public QualificationProfile() {
		this.functionsProvidedURIs = new ArrayList<String>();
		this.potentialMaterialURIs = new ArrayList<String>();
		this.potentialProcessingMethodURIs = new ArrayList<String>();
		this.productTypes = new ArrayList<String>();
		this.productSuperTypes = new ArrayList<String>();
	}

	public void addProductSuperType(String superType) {
		this.productSuperTypes.add(superType);
	}

	public void addNewFunction(String functionURI) {
		logger.debug(" ************************ adding new function "
				+ functionURI);
		this.functionsProvidedURIs.add(functionURI);
	}

	public void addNewPotentialMaterial(String materialURI) {
		this.potentialMaterialURIs.add(materialURI);
	}

	public void addNewPotentialProcessingMethod(String ProcessingMethodURI) {
		this.potentialProcessingMethodURIs.add(ProcessingMethodURI);
	}

	@CollectionOfElements
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<String> getPotentialProcessingMethodURIs() {
		return this.potentialProcessingMethodURIs;
	}

	@CollectionOfElements
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<String> getFunctionsProvidedURIs() {
		return this.functionsProvidedURIs;
	}

	/**
	 * @param functionsProvided
	 *            the functionsProvided to set
	 */
	public void setFunctionsProvidedURIs(List<String> functionsProvidedURIs) {
		// logger.debug(" ************************ setting functions provided "
		// + functionsProvidedURIs);
		this.functionsProvidedURIs = functionsProvidedURIs;
	}

	@CollectionOfElements
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<String> getPotentialMaterialURIs() {
		return this.potentialMaterialURIs;
	}

	public void addProductType(String productTypeIn) {
		this.productTypes.add(productTypeIn);
	}

	public void setLocationOfPart(String location) {
		this.locationOfPart = location;
	}

	@Override
	public QualificationProfile clone() {

		QualificationProfile copy = new QualificationProfile();
		copy.setLocationOfPart(this.getLocationOfPart());
		copy.setProductIndividualURI(this.getProductIndividualURI());

		List<String> list = new ArrayList<String>();
		if (getFunctionsProvidedURIs() != null) {
			for (String x : getFunctionsProvidedURIs()) {
				list.add(x);
			}
			copy.setFunctionsProvidedURIs(list);
		}

		list = new ArrayList<String>();
		if (getPotentialMaterialURIs() != null) {
			for (String x : getPotentialMaterialURIs()) {
				list.add(x);
			}
			copy.setPotentialMaterialURIs(list);
		}
		list = new ArrayList<String>();
		if (getPotentialProcessingMethodURIs() != null) {
			for (String x : getPotentialProcessingMethodURIs()) {
				list.add(x);
			}
			copy.setPotentialProcessingMethodURIs(list);
		}
		list = new ArrayList<String>();
		if (getProductSuperTypes() != null) {
			for (String x : getProductSuperTypes()) {
				list.add(x);
			}
			copy.setProductSuperTypes(list);
		}

		list = new ArrayList<String>();
		if (getProductTypes() != null) {
			for (String x : getProductTypes()) {
				list.add(x);
			}
			copy.setProductTypes(list);
		}

		return copy;
	}

	public String getLocationOfPart() {
		return this.locationOfPart;
	}

	@Override
	public String toString() {
		String result = "";
		result += " Original individual " + this.productIndividualURI;
		result += " Product location " + this.locationOfPart;
		for (String s : this.productTypes) {
			result += " potential product type" + s;
		}

		for (String s : this.productSuperTypes) {
			result += " Product super type " + s;
		}

		for (String s : this.functionsProvidedURIs) {
			result += " Function " + s;
		}

		for (String s : this.potentialMaterialURIs) {
			result += " Material type " + s;
		}

		return result;
	}

}
