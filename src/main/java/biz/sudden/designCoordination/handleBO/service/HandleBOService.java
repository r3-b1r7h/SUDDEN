package biz.sudden.designCoordination.handleBO.service;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.designCoordination.handleBO.dataStructures.ASNNode;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNInitialNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

/**
 * A very, very simple HBO service interface
 * 
 * But then the interface *is* simple
 */
public interface HandleBOService {

	/**
	 * This method assumes that it is given a business opportunity with a goal
	 * and generates ASN for it.
	 * 
	 * In sequence it looks for a goal specified as : 1) An individual within
	 * the ontology - if it finds this then it uses it. 2) A *class* within the
	 * ontology. In this case it finds an individual (if one exists of course!)
	 * corresponding to this class and uses that. Not guaranteed which - just
	 * the first one it finds.
	 * 
	 * In either case it then builds up a set of possible ASNs and returns them.
	 * Either that or it just returns a null result.
	 * 
	 * @param BusinessOpportunityIn
	 * @return
	 */
	public List<AbstractSupplyNetwork> generateInitialSupplyNetworks(
			BusinessOpportunity BusinessOpportunityIn);

	/**
	 * Ok this function takes an abstract supply network and a List of desired
	 * functions - each of which should correspond to a class URI within the
	 * ontology. It then finds which - if any - of the desired functions aren't
	 * covered by the candidate ASN and returns them. If there are none then an
	 * empty array List is returned.
	 * 
	 * @param supplyNetwork
	 * @param desiredFunctions
	 * @return
	 */
	public List<String> compareFunctionsProvided(
			AbstractSupplyNetwork supplyNetwork, List<String> desiredFunctions);

	/**
	 * Tests if the desired ASN provides everyone of the indicated functions
	 * 
	 * @param networkToTest
	 * @param desiredFunctions
	 * @return
	 */
	public boolean providesFunctions(AbstractSupplyNetwork networkToTest,
			List<String> desiredFunctions);

	/**
	 * Takes a set of classes from the ontology and returns those classes for
	 * which no corresponding individual is found in the abstract supply
	 * network.
	 * 
	 * @param networkToTest
	 * @param desiredProducts
	 * @return
	 */
	public List<String> compareDesiredClasses(
			AbstractSupplyNetwork networkToTest, List<String> desiredProducts);

	/**
	 * Tests if the given network contains the set of products. The desired
	 * products are assumed to be provided as classes and the test is whether or
	 * not any of the products suppliers by the abstract supply network are
	 * *subclasses* (ie more specific) of the desired product class.
	 * 
	 * @param networkToTest
	 * @param desiredProducts
	 * @return
	 */
	public boolean containsDesiredClasses(AbstractSupplyNetwork networkToTest,
			List<String> desiredProducts);

	/**
	 * Tests if the given network contains the *exact* individuals from the set
	 * desired. No sub/superclass relationships are used. This method is
	 * designed for use when you know exactly what you want your ASN to contain.
	 * 
	 * @param networkToTest
	 * @param desiredProducts
	 * @return
	 */
	public boolean containsDesiredIndividuals(
			AbstractSupplyNetwork networkToTest,
			List<String> desiredProductIndividuals);

	/**
	 * Return a list of all of the simple parts - as URIs of individuals - which
	 * provide either a function from the indicated class or one of its
	 * subclasses.
	 * 
	 * @param function
	 * @return
	 */
	public ArrayList<String> getPartsForFunction(String functionClassURI);

	public void addInitialNode(AbstractSupplyNetwork asn,
			ASNInitialNode initialNode);

	public void addRoleNode(AbstractSupplyNetwork asn, ASNRoleNode roleNode);

	public void addDependency(AbstractSupplyNetwork asn,
			ASNDependency dependency);

	public List<ASNRoleNode> getAllRoleNodes(AbstractSupplyNetwork asn);

	public List<String> getAllProducts(AbstractSupplyNetwork asn);

	public List<String> getAllIndividuals();

	public List<String> getAllIndividualProducts();

	public ASNInitialNode getInitialNode(AbstractSupplyNetwork asn);

	public void update(ASNNode node);

	public void update(AbstractSupplyNetwork asn);

	public void update(ConcreteSupplyNetwork csn);

	public void update(ASNDependency dep);

	public void create(ASNNode node);

	public void create(AbstractSupplyNetwork asn);

	public void create(ConcreteSupplyNetwork csn);

	public void create(ASNDependency dep);

	public void delete(AbstractSupplyNetwork asn);

	public void delete(ConcreteSupplyNetwork csn);

	public void delete(ASNDependency dep);

	public ASNNode retrieveASNNode(Long id);

	public AbstractSupplyNetwork retrieveASN(Long id);

	public void removeNodeByName(AbstractSupplyNetwork asn, String localName);

	public void update(ASNRoleNode node);

	public void create(ASNRoleNode node);

	public QualificationProfile getProductQualificationProfile(
			String URIofIndividual);

	// The call used in the current interface. Not predictable WHICH individual
	// of the class
	// it'll latch on to and return the profile for.
	public QualificationProfile getProductQualificationProfileFromClass(
			String URIofClass);

	public boolean isLightWeightConstruction(AbstractSupplyNetwork asn);

	public void assignSupplierByName(AbstractSupplyNetwork asn,
			String ASNNodeName, Organization orgToAssign);

	public void update(CompetenceNeeded cn);

	public void create(CompetenceNeeded cn);

	public void update(NetworkEvaluationProfile nep);

	public void create(NetworkEvaluationProfile nep);

}
