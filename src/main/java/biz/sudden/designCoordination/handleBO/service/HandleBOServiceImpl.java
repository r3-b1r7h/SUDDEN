package biz.sudden.designCoordination.handleBO.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.designCoordination.handleBO.dataStructures.ASNComplexNode;
import biz.sudden.designCoordination.handleBO.dataStructures.ASNNode;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNInitialNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNodeFixedSupplier;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeededByNetworkEvaluationProfile;
import biz.sudden.knowledgeData.kdm.service.KdmService;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;
import biz.sudden.knowledgeData.serviceManagement.domain.Thing;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;

public class HandleBOServiceImpl implements HandleBOService {

	Logger logger = Logger.getLogger(this.getClass());

	private OntClass simplePartClass;
	private OntClass complexPartClass;
	private OntClass partClass;
	private OntClass serviceClass;
	private KdmService kdmService;
	private OntModel ontologyInMemory;
	private SuddenGenericRepository suddenGenericRepository;

	public SuddenGenericRepository getSuddenGenericRepository() {
		return suddenGenericRepository;
	}

	public void setSuddenGenericRepository(
			SuddenGenericRepository suddenGenericRepository) {
		suddenGenericRepository.setType(ASNNode.class);
		this.suddenGenericRepository = suddenGenericRepository;
	}

	public KdmService getKdmServiceImpl() {
		return kdmService;
	}

	public void setKdmService(KdmService kdmServiceImpl) {
		this.kdmService = kdmServiceImpl;
	}

	// TODO - just so I can test things w'out starting up the whole Spring bits.
	// Remove :)
	public void setOntology(OntModel ontModel) {
		this.ontologyInMemory = ontModel;
		initPartsAndServices();
	}

	public void init() {

		if (this.kdmService != null) {
			this.ontologyInMemory = kdmService.getOntology();
		}

		initPartsAndServices();

		// the ontologyInMemory.read call is broken and to my understanding not
		// needed within the spring environment. its done in
		// kdSerivce.getOntology();
		// parts below are not needed since kdmService already loaded the
		// onlogy.
		// to my (Georg's) understanding.

		// Get a model factory, make a model. Try to find the product type as a
		// class within this model.
		// ontologyInMemory = ModelFactory.createOntologyModel();

		// Setting up an alias for a local file for our ontologies
		// OntDocumentManager docM = ontologyInMemory.getDocumentManager();
		// docM.addAltEntry(Thing.SuddenOntologyURI, fileURI);

		// ontologyInMemory.read(Thing.SuddenOntologyURI);
	}

	private void initPartsAndServices() {
		// if one of the blow is null somehting has gone wrong; so re-do this.
		if (simplePartClass == null || complexPartClass == null
				|| partClass == null || serviceClass == null) {
			if (this.ontologyInMemory != null && !ontologyInMemory.isClosed()) {
				try {
					simplePartClass = this.ontologyInMemory
							.getOntClass(Thing.NameSpace + "Simple_Part");
					complexPartClass = this.ontologyInMemory
							.getOntClass(Thing.NameSpace + "ComplexPart");
					partClass = this.ontologyInMemory
							.getOntClass(Thing.NameSpace + "Part");
					serviceClass = this.ontologyInMemory
							.getOntClass(Thing.NameSpace + "Service");
				} catch (Exception e) {
					logger
							.error("Problems with JENA DB!!; initPartsAndServices not succeeded");
					e.printStackTrace();
				}
			} else {
				logger
						.error("Ontology In Memory == null; initPartsAndServices not succeeded");
			}
		}
	}

	@Override
	public ArrayList<AbstractSupplyNetwork> generateInitialSupplyNetworks(
			BusinessOpportunity businessOpportunityIn) {

		logger.debug(" Generating initial Supply Network");

		ArrayList<AbstractSupplyNetwork> result = new ArrayList<AbstractSupplyNetwork>();
		AbstractSupplyNetwork firstSupplyNetwork = new AbstractSupplyNetwork();

		// FIXME GW: is this really correct? While generating Initial Supply
		// Networks for given Case Files I do not want
		// to save these ASN's here. First when I click at "Add ASN to CaseFile"
		// I want that the system saves the ASN
		// create(firstSupplyNetwork);

		ASNInitialNode initialNode = new ASNInitialNode();
		// create(initialNode);

		// Ok so we'd *prefer* some values here!
		firstSupplyNetwork.addInitialNode(new ArrayList<Product>());

		// Get the individual that we're going to work with.
		Individual goalIndividual = null;

		// Set up the collection of decompositions up

		init();
		initPartsAndServices();

		// Individual first
		if (businessOpportunityIn.getIndividualGoalURI() != null) {
			result.addAll(getASNsForIndividual(
					ontologyInMemory.getIndividual(businessOpportunityIn
							.getIndividualGoalURI()), firstSupplyNetwork
							.clone()));
		}

		// Or a class? (allowing for the case where an incorrect individual URI
		// was specified previously.).
		if (goalIndividual == null
				&& businessOpportunityIn.getClassGoalURI() != null) {
			OntClass goalClass = ontologyInMemory.getOntClass(Thing.NameSpace
					+ businessOpportunityIn.getClassGoalURI());

			// NPE test removed - the way its generated something has to be
			// badly wrong to get an NPE here!
			Iterator<Individual> overIndividuals = goalClass.listInstances();

			// Get all of them.
			while (overIndividuals.hasNext()) {
				result.addAll(getASNsForIndividual(overIndividuals.next(),
						firstSupplyNetwork.clone()));
			}

			// For that matter get all of the ones from subclasses too!
			Iterator<OntResource> subclasses = ontologyInMemory.getOntClass(
					Thing.NameSpace + businessOpportunityIn.getClassGoalURI())
					.listSubClasses();

			while (subclasses.hasNext()) {
				overIndividuals = ((OntClass) subclasses.next())
						.listInstances();

				// Get all of them.
				while (overIndividuals.hasNext()) {
					result.addAll(getASNsForIndividual(overIndividuals.next(),
							firstSupplyNetwork.clone()));
				}
			}
		}

		// Store the initial product so we can get back to the initial node
		// later
		// on
		// The get OntClass isn't elegant but it does mean it matches the way I
		// store it below

		return result;
	}

	private List<AbstractSupplyNetwork> getASNsForIndividual(
			Individual goalIndividual, AbstractSupplyNetwork firstSupplyNetwork) {
		HashMap<String, ArrayList<Decomposition>> decompositions;
		ArrayList<AbstractSupplyNetwork> result = new ArrayList<AbstractSupplyNetwork>();

		logger.debug(" Adding in final node based on individual "
				+ goalIndividual);
		firstSupplyNetwork.addFinalNode(goalIndividual.getOntClass().getURI(),
				getSimpleProductQualificationProfile(goalIndividual));

		decompositions = new HashMap<String, ArrayList<Decomposition>>();
		decompositions = addDecompositions(goalIndividual, goalIndividual,
				decompositions);

		result.add(firstSupplyNetwork);

		result = generateCandidateASNs(result, decompositions);
		logger.debug(result);

		for (AbstractSupplyNetwork n : result) {
			logger.debug(" Initial Candidate Abstract Supply Network " + n);
		}

		return result;

	}

	public HashMap<String, ArrayList<Decomposition>> addDecompositions(
			Individual originalGoalIndividual, Individual goalIndividual,
			HashMap<String, ArrayList<Decomposition>> decompositions) {

		/*
		 * Well set it up
		 */
		Iterator<OntResource> ontologyResourceIterator;
		Individual nextPotentialDecomposition;
		Decomposition tempDecomposition;
		Individual nextResource;
		ArrayList<Decomposition> tempDecompositionList;

		// Only do anything if the thing is complex
		if (goalIndividual.getOntClass().hasSuperClass(complexPartClass)) {
			logger.debug(" Adding decomposition for part " + goalIndividual);

			ontologyResourceIterator = goalIndividual
					.listPropertyValues(ontologyInMemory
							.getProperty(Thing.NameSpace + "CanBeMadeFrom"));

			tempDecompositionList = new ArrayList<Decomposition>();

			// This covers any *extra* ways to decompose the same product - we
			// need to create a new abstract supply network for each one :)
			while (ontologyResourceIterator.hasNext()) {
				nextPotentialDecomposition = ontologyResourceIterator.next()
						.asIndividual();
				logger.debug(" Adding decomposition "
						+ nextPotentialDecomposition);

				// To make sure that its initalised for parts with no actual
				// decomps
				// attached.
				tempDecomposition = new Decomposition();

				Iterator<OntResource> ontResourceIterator = nextPotentialDecomposition
						.listPropertyValues(ontologyInMemory
								.getProperty(Thing.NameSpace
										+ "PotentialDecompositionContains"));
				while (ontResourceIterator.hasNext()) {
					nextResource = ontResourceIterator.next().asIndividual();

					if (nextResource.getOntClass().hasSuperClass(
							complexPartClass)) {
						tempDecomposition.addComplexProduct(nextResource);
						// Ok now recursively store these decompositions
						addDecompositions(originalGoalIndividual, nextResource,
								decompositions);
					} else if (nextResource.getOntClass().hasSuperClass(
							simplePartClass)) {
						tempDecomposition.addSimpleProduct(nextResource);
					}

				}
				logger.debug(" Decomposition generated for " + goalIndividual
						+ " is " + tempDecomposition);
				tempDecompositionList.add(tempDecomposition);
			}

			// A quite horrible hack. Basically as things stand the original
			// individual is passed to us a class not an individual so should
			// be stored as such here. Otherwise store the individuals name.

			if (goalIndividual.equals(originalGoalIndividual)) {
				decompositions.put(goalIndividual.getOntClass().getURI(),
						tempDecompositionList);
			} else {
				// A check to catch when you get same individual in two places
				// in the decomposition list
				// Seeing if this helps
				if (!decompositions.containsKey(goalIndividual.getURI()))
					decompositions.put(goalIndividual.getURI(),
							tempDecompositionList);
			}
		}

		return decompositions;
	}

	/**
	 * A private method which does most of the actual work that the function
	 * needs to do
	 * 
	 * @param decompositions
	 */
	private ArrayList<AbstractSupplyNetwork> generateCandidateASNs(
			ArrayList<AbstractSupplyNetwork> asns,
			HashMap<String, ArrayList<Decomposition>> decompositions) {

		logger.debug(" Decompositions passed to ASN Generation "
				+ decompositions);

		StringBuffer debug = new StringBuffer();
		debug.append("number of asns ").append(asns.size());

		int debugExpandCounter = 0;

		/*
		 * So expand everything. Just everything :)
		 */
		while (!allASNsFullyExpanded(asns)) {
			debugExpandCounter++;
			/*
			 * To do - set up all the bits of the 'new' ASN generating code
			 * here.
			 */
			// 1 fetch out decomps for the indicated product
			// use the asns list ... no need to create a new List.
			// ArrayList<AbstractSupplyNetwork> tempAbstractSupplyNetworkList =
			// new ArrayList<AbstractSupplyNetwork>();
			/*
			 * Find the first, non fully expanded ASN
			 */
			Iterator<AbstractSupplyNetwork> overASNsIn = asns.iterator();
			boolean found = false;
			AbstractSupplyNetwork tempASN = null;
			while (!found && overASNsIn.hasNext()) {
				tempASN = overASNsIn.next();
				found = !(tempASN.allComplexNodesExpanded());
			}

			// NB The while loop (hopefully!) ensures that tempASN is returned
			// with a
			// sensible value....
			/*
			 * Add every *other* ASN from the list to the temp list
			 */
			// use the asns list ... no need to create a new List.
			// tempAbstractSupplyNetworkList.addAll(asns);
			// tempAbstractSupplyNetworkList.remove(tempASN);
			asns.remove(tempASN);

			/*
			 * Now add some bonus ASNs. Get an unexpanded node from our current
			 * ASN. Then get its corresponding type.
			 */
			logger.debug(tempASN.toString());

			ASNComplexNode firstUnresolvedNode = tempASN.getUnresolvedNode();
			// logger.debug(" first unresolved node is " + firstUnresolvedNode);
			Individual typeToExpand = ontologyInMemory
					.getIndividual(firstUnresolvedNode
							.getComplexPartToAssemble());

			// logger.debug(" Using decomposition " + decompositions);

			// This hopefully deals with the initial case - often (as in the
			// system as
			// implemented!) there is no
			// initial individual type so....
			ArrayList<Decomposition> decompositionsForNode = decompositions
					.get(typeToExpand.getURI());

			logger.debug(" Entry found for " + typeToExpand + " is "
					+ decompositionsForNode);

			AbstractSupplyNetwork tempAbstractSupplyNetwork;
			ASNComplexNode firstUnresolvedNodeInClone;
			ASNRoleNode tempASNRoleNode;
			ASNComplexNode tempASNComplexNode;
			ASNMaterialDependency tempMaterialDependency;
			if (decompositionsForNode != null) {

				if (decompositionsForNode.size() == 0) {
					// COmplex node with no decomposition - just tick it off as
					// done
					tempAbstractSupplyNetwork = tempASN.clone();
					// Mark the node that we will shortly expand as expanded
					firstUnresolvedNodeInClone = tempAbstractSupplyNetwork
							.getUnexpandedComplexRoleNode(typeToExpand);
					firstUnresolvedNodeInClone.setExpanded(Boolean.TRUE);
					logger.debug(" ********** "
							+ tempAbstractSupplyNetwork
									.allComplexNodesExpanded());
					// use the asns list ... no need to create a new List.
					// tempAbstractSupplyNetworkList.add(tempAbstractSupplyNetwork);
					asns.add(tempAbstractSupplyNetwork);
				} else {
					for (Decomposition decompForNode : decompositionsForNode) {
						tempAbstractSupplyNetwork = tempASN.clone();

						// Mark the node that we will shortly expand as expanded
						firstUnresolvedNodeInClone = tempAbstractSupplyNetwork
								.getUnexpandedComplexRoleNode(typeToExpand);
						firstUnresolvedNodeInClone.setExpanded(Boolean.TRUE);

						/*
						 * Now make good on that promise :)
						 */
						for (Individual i : decompForNode.getSimpleProducts()) {
							// Simple - node + dependency
							tempASNRoleNode = new ASNRoleNode();
							tempASNRoleNode
									.setQualificationProfile(getSimpleProductQualificationProfile(i));
							tempAbstractSupplyNetwork
									.addNewNode(tempASNRoleNode);

							tempMaterialDependency = new ASNMaterialDependency(
									tempASNRoleNode, firstUnresolvedNodeInClone);
							tempAbstractSupplyNetwork
									.addNewDependcy(tempMaterialDependency);
						}

						for (Individual i : decompForNode.getComplexProducts()) {
							// Simple - node + dependency
							tempASNComplexNode = new ASNComplexNode();
							// TODO - this bit may cause a few issues!
							tempASNComplexNode
									.setQualificationProfile(getSimpleProductQualificationProfile(i));
							tempASNComplexNode.setComplexPartToAssemble(i
									.getURI());
							tempASNComplexNode.setComplexPartToAssembleType(i
									.getOntClass().getURI());
							tempASNComplexNode.setExpanded(Boolean.FALSE);
							tempAbstractSupplyNetwork
									.addNewNode(tempASNComplexNode);

							tempMaterialDependency = new ASNMaterialDependency(
									tempASNComplexNode,
									firstUnresolvedNodeInClone);
							tempAbstractSupplyNetwork
									.addNewDependcy(tempMaterialDependency);
						}
						logger.debug(" ********** "
								+ tempAbstractSupplyNetwork
										.allComplexNodesExpanded());
						// use the asns list ... no need to create a new List.
						// tempAbstractSupplyNetworkList.add(tempAbstractSupplyNetwork);
						asns.add(tempAbstractSupplyNetwork);
					}
				}

			}

			// use the asns list ... no need to create a new List.
			// logger.debug(" Number of Abstract supply networks so far " +
			// tempAbstractSupplyNetworkList.size());
			// asns = tempAbstractSupplyNetworkList;
			logger.debug(" Number of Abstract supply networks so far "
					+ asns.size());
		}
		debug.append("Expanded ASNS: ").append(debugExpandCounter);
		logger.debug(debug);

		for (AbstractSupplyNetwork asn : asns) {
			logger.debug(" ******** asn " + asn + " with nodes ");
			for (ASNRoleNode node : asn.getAllRoleNodes()) {
				logger.debug(" Node " + node + " with qualification profile "
						+ node.getQualificationProfile());
			}
		}

		return asns;
	}

	/**
	 * A dinky little helper method
	 */
	private boolean allASNsFullyExpanded(Collection<AbstractSupplyNetwork> asns) {
		boolean result = true;
		Iterator<AbstractSupplyNetwork> asnsIt = asns.iterator();
		AbstractSupplyNetwork tempASN;
		while (asnsIt.hasNext() && result) {
			tempASN = asnsIt.next();
			result = result && tempASN.allComplexNodesExpanded();
		}

		return result;
	}

	/**
	 * Fetches the individual out of the central ontology model and generates an
	 * appropriate qualification profile for an ASN node containing it.
	 */
	public QualificationProfile getProductQualificationProfile(
			String URIofIndividual) {
		Individual i = this.ontologyInMemory.getIndividual(URIofIndividual);

		return getSimpleProductQualificationProfile(i);
	}

	/**
	 * Fetches a (random) individual of the given class out of the central
	 * ontology model and generates an appropriate qualification profile for an
	 * ASN node containing that individual.
	 */
	public QualificationProfile getProductQualificationProfileFromClass(
			String URIofClass) {
		logger.debug(URIofClass);
		OntClass c = this.ontologyInMemory.getOntClass(URIofClass);
		Iterator iterator = c.listInstances();
		Individual i = null;

		if (iterator.hasNext()) {
			i = (Individual) c.listInstances().next();
		}

		return getSimpleProductQualificationProfile(i);
	}

	/**
	 * This method extracts all the information relating to the simple part in
	 * question and builds a qualification profile for a role involving
	 * producing it.
	 * 
	 * @param individualToAdd
	 * @return
	 */
	private QualificationProfile getSimpleProductQualificationProfile(
			Individual individualToAdd) {
		QualificationProfile tempQualificationProfile = new QualificationProfile();
		Iterator<OntResource> ontologyResourceIterator;

		// (1) Store a pointer so can go back to Ontology if we want
		// logger.debug("Adding new basic product Node, URI" +
		// individualToAdd.getURI());
		tempQualificationProfile.setProductIndividualURI(individualToAdd
				.getURI());

		// (2) Store the type(s) of the individual

		// a) It's direct types

		ontologyResourceIterator = individualToAdd.listOntClasses(true);

		OntClass tempClass;
		while (ontologyResourceIterator.hasNext()) {
			tempClass = ((OntClass) ontologyResourceIterator.next());
			logger.debug(" Class type added " + tempClass.getURI());
			tempQualificationProfile.addProductType(tempClass.getURI());
		}

		//

		// b) Get its supertypes while we're here. Just not all of them!
		ontologyResourceIterator = individualToAdd.listOntClasses(false);

		String tempClassURI;
		while (ontologyResourceIterator.hasNext()) {
			tempClass = (OntClass) ontologyResourceIterator.next();
			initPartsAndServices();
			if (tempClass.hasSuperClass(simplePartClass)
					&& !(tempClass.equals(simplePartClass))) {
				tempClassURI = tempClass.getURI();
				tempQualificationProfile.addProductSuperType(tempClassURI);
				// logger.debug("Product super type " + tempClassURI);
			}
		}

		// (3) Functions

		ontologyResourceIterator = individualToAdd
				.listPropertyValues(ontologyInMemory
						.getProperty(Thing.NameSpace + "ProvidesFunction"));

		Individual tempFunction;
		while (ontologyResourceIterator.hasNext()) {
			tempFunction = ontologyResourceIterator.next().asIndividual();
			tempQualificationProfile.addNewFunction(tempFunction.getURI());
		}
		logger
				.debug(" **************************** functions added during ASN generation "
						+ tempQualificationProfile.getFunctionsProvidedURIs());

		// (4) Potential Materials and - from these - methods of processing

		ontologyResourceIterator = individualToAdd
				.listPropertyValues(ontologyInMemory
						.getProperty(Thing.NameSpace + "MadeOfMaterial"));
		logger.debug("Individual to add " + individualToAdd
				+ " can be made from materials " + ontologyResourceIterator);

		Individual tempMaterial;
		OntClass tempMaterialClass;
		OntClass tempProcessingClass;
		while (ontologyResourceIterator.hasNext()) {
			tempMaterial = ontologyResourceIterator.next().asIndividual();

			tempMaterialClass = tempMaterial.getOntClass();
			logger.debug("Made of material " + tempMaterial + " with class "
					+ tempMaterialClass);
			tempQualificationProfile.addNewPotentialMaterial(Thing
					.getShortName(tempMaterialClass.getURI()));
			// Get the processing methods for said material while we're here.
			// Why not?
			Iterator<OntResource> processTypes = tempMaterial
					.listPropertyValues(ontologyInMemory
							.getProperty(Thing.NameSpace + "ProcessedThrough"));
			while (processTypes.hasNext()) {
				tempProcessingClass = processTypes.next().asIndividual()
						.getOntClass();
				tempQualificationProfile.addNewPotentialProcessingMethod(Thing
						.getShortName(tempProcessingClass.getURI()));
			}
		}

		// (5) Mode of operation

		ontologyResourceIterator = individualToAdd
				.listPropertyValues(ontologyInMemory
						.getProperty(Thing.NameSpace + "ModusOperandi"));
		Individual tempModeOfOperation;

		while (ontologyResourceIterator.hasNext()) {
			tempQualificationProfile
					.addNewPotentialProcessingMethod(((Individual) ontologyResourceIterator
							.next()).getURI());
		}

		// (6) The location of the part
		ontologyResourceIterator = individualToAdd
				.listPropertyValues(ontologyInMemory
						.getProperty(Thing.NameSpace + "IsLocated"));
		Individual location = null;

		// Implicit assumption that only ONE location is ever allowed. Seems
		// fair?
		if (ontologyResourceIterator.hasNext()) {
			location = ontologyResourceIterator.next().asIndividual();
		}
		// logger.debug(" Location " + location);

		// Not sure why this specific call needs a null test but it does seem
		// to!
		if (location != null) {
			tempQualificationProfile.setLocationOfPart(location.getLocalName());
		}

		// logger.debug(" Qualification Profile Generated " +
		// tempQualificationProfile);
		return tempQualificationProfile;
	}

	/**
	 * The implementation of the method from the service interface - it checks
	 * to be sure that all of the functions involved are 'covered' by at least
	 * one superclass. The functions desired are assumed to point to classes not
	 * individuals.
	 */
	@Override
	public ArrayList<String> compareFunctionsProvided(
			AbstractSupplyNetwork supplyNetwork, List<String> desiredFunctions) {

		ArrayList<Individual> providedFunctions = new ArrayList<Individual>();
		ArrayList<String> functionsNotProvided = new ArrayList<String>();

		for (ASNRoleNode node : getAllRoleNodes(supplyNetwork)) {
			logger
					.debug(" Functions Provided by node "
							+ node.getproductType()
							+ " are "
							+ node.getQualificationProfile()
									.getFunctionsProvidedURIs());
			for (String s : node.getQualificationProfile()
					.getFunctionsProvidedURIs()) {

				providedFunctions.add(ontologyInMemory.getIndividual(s));
			}
		}

		for (Individual o : providedFunctions) {
			logger.debug(" provided function " + o);
		}

		OntClass tempDesiredFunctionClass;
		Individual tempProvidedFunction;
		boolean matchFound;
		Iterator<Individual> overProvidedFunctions;

		for (String nextFunctionDesiredClassURI : desiredFunctions) {
			tempDesiredFunctionClass = ontologyInMemory
					.getOntClass(Thing.NameSpace + nextFunctionDesiredClassURI);
			matchFound = false;
			overProvidedFunctions = providedFunctions.iterator();

			while (!matchFound && overProvidedFunctions.hasNext()) {
				tempProvidedFunction = overProvidedFunctions.next();
				logger.debug(" desired function " + tempDesiredFunctionClass
						+ " with ont class " + tempDesiredFunctionClass);
				logger.debug(" matched against " + tempProvidedFunction
						+ " with ont class "
						+ tempProvidedFunction.getOntClass(true));
				matchFound = tempDesiredFunctionClass
						.hasSuperClass(tempProvidedFunction.getOntClass(true));
			}

			if (!matchFound) {
				functionsNotProvided.add(nextFunctionDesiredClassURI);
			}
		}

		return functionsNotProvided;
	}

	@Override
	public boolean containsDesiredClasses(AbstractSupplyNetwork networkToTest,
			List<String> desiredProducts) {
		return (this.compareDesiredClasses(networkToTest, desiredProducts)
				.size() == 0);
	}

	@Override
	public boolean providesFunctions(AbstractSupplyNetwork networkToTest,
			List<String> desiredFunctions) {
		ArrayList<String> functionsMatched = this.compareFunctionsProvided(
				networkToTest, desiredFunctions);
		logger.debug(functionsMatched);
		return (functionsMatched.size() == 0);
	}

	@Override
	public boolean containsDesiredIndividuals(
			AbstractSupplyNetwork networkToTest,
			List<String> desiredProductIndividuals) {
		List<String> indivsInNetwork = getAllProducts(networkToTest);

		List<String> desiredProductIndividualsWithNameSpace = new ArrayList<String>();

		for (String string : desiredProductIndividuals) {
			string = Thing.NameSpace + string;
			desiredProductIndividualsWithNameSpace.add(string);
		}

		return indivsInNetwork
				.containsAll(desiredProductIndividualsWithNameSpace);
	}

	@Override
	public ArrayList<String> compareDesiredClasses(
			AbstractSupplyNetwork networkToTest, List<String> desiredProducts) {
		ArrayList<String> indivsInNetwork = new ArrayList<String>(
				getAllProducts(networkToTest));
		OntClass tempOntClassDesired;
		OntClass tempOntClassProvided;
		Individual tempProductProvided;

		ArrayList<String> result = new ArrayList<String>();
		boolean found;
		Iterator<String> indivIterator;

		for (String nextClass : desiredProducts) {
			tempOntClassDesired = ontologyInMemory.getOntClass(Thing.NameSpace
					+ nextClass);
			found = false;
			indivIterator = indivsInNetwork.iterator();

			while (indivIterator.hasNext() && !found) {
				tempProductProvided = ontologyInMemory
						.getIndividual(indivIterator.next());
				tempOntClassProvided = tempProductProvided.getOntClass();

				// This bit of logic is interesting. I'll go for
				// accept if the thing required is a *superclass* of the thing
				// in the ASN
				found = tempOntClassDesired.hasSubClass(tempOntClassProvided);
			}

			if (!found) {
				result.add(nextClass);
			}
		}
		return result;
	}

	/**
	 * Note that this method only returns parts which directly provide a
	 * specific function. I'd have to pull decompositions apart etc to get at
	 * functions which are based on decompositions.
	 * 
	 * @param function
	 * @return
	 */
	public ArrayList<String> getPartsForFunction(String functionClassURI) {
		ArrayList<String> result = new ArrayList<String>();
		Individual tempIndividual;
		Individual tempFunction;

		OntClass functionInClass = ontologyInMemory
				.getOntClass(functionClassURI);
		OntClass tempFunctionClass;

		Iterator<OntResource> partIndividuals = ontologyInMemory
				.listIndividuals(partClass);
		Iterator<OntResource> overFunctions;
		boolean found;

		while (partIndividuals.hasNext()) {
			tempIndividual = partIndividuals.next().asIndividual();
			overFunctions = tempIndividual.listPropertyValues(ontologyInMemory
					.getProperty(Thing.NameSpace + "ProvidesFunction"));
			found = false;

			while ((!found) && overFunctions.hasNext()) {
				tempFunction = overFunctions.next().asIndividual();
				tempFunctionClass = tempFunction.getOntClass();
				found = tempFunctionClass.hasSubClass(functionInClass);
			}

			if (found) {
				result.add(tempIndividual.getURI());
			}
		}

		return result;
	}

	/**
	 * This isn't actually going to be a public method in the end if I remember
	 * to send it private.
	 * 
	 * Its for extracting/storing the complex product structure embedded in the
	 * decomposition information for a product. Annoyingly wasteful to do this
	 * in a two pass method iso of one but I can't quite see how to do it in
	 * one.
	 * 
	 * @param IndividualPartURI
	 */
	public void generateComplexProductGraph(String IndividualPartURI) {
		Individual goalIndividual = ontologyInMemory
				.getIndividual(IndividualPartURI);

		if (goalIndividual.getOntClass().hasSuperClass(complexPartClass)) {
		}
	}

	// TODO - another debug method
	public OntResource getPartClass() {
		return this.partClass;
	}

	@Override
	public synchronized void addInitialNode(AbstractSupplyNetwork asn,
			ASNInitialNode initialNode) {
		suddenGenericRepository.create(initialNode);
		asn.getMyNodes().add(initialNode);
	}

	@Override
	@Transactional
	public synchronized void addDependency(AbstractSupplyNetwork asn,
			ASNDependency dependency) {
		suddenGenericRepository.create(dependency);
		asn.getMyDependencies().add(dependency);
		suddenGenericRepository.update(dependency);
	}

	@Override
	@Transactional
	public synchronized void addRoleNode(AbstractSupplyNetwork asn,
			ASNRoleNode roleNode) {
		suddenGenericRepository.create(roleNode);
		suddenGenericRepository.update(asn);
		// we may use this deprecated method since we take care of persistency
		asn.addNewNode(roleNode);
		// update roleNode as setASN is called
		suddenGenericRepository.update(roleNode);
		suddenGenericRepository.update(asn);
	}

	@Override
	public List<ASNRoleNode> getAllRoleNodes(AbstractSupplyNetwork asn) {
		List<ASNRoleNode> result = new ArrayList<ASNRoleNode>();
		for (ASNNode asnNode : asn.getMyNodes()) {
			if (asnNode.getClass().equals(ASNRoleNode.class)
					|| asnNode.getClass().equals(ASNComplexNode.class)) {
				result.add((ASNRoleNode) asnNode);
			}
		}
		return result;
	}

	@Override
	public ASNInitialNode getInitialNode(AbstractSupplyNetwork asn) {
		ASNInitialNode result = null;
		for (ASNNode asnNode : asn.getMyNodes()) {
			if (asnNode instanceof ASNInitialNode) {
				result = (ASNInitialNode) asnNode;
				break;
			}
		}
		return result;
	}

	@Override
	public List<String> getAllProducts(AbstractSupplyNetwork asn) {

		ArrayList<String> result = new ArrayList<String>();
		String tempProductType;

		for (ASNNode nextNode : asn.getMyNodes()) {
			if (nextNode.getClass().equals(ASNRoleNode.class)
					|| nextNode.getClass().equals(ASNComplexNode.class)) {
				tempProductType = ((ASNRoleNode) nextNode)
						.getQualificationProfile().getProductIndividualURI();
				result.add(tempProductType);
			}
			if (nextNode.getClass().equals(ASNRoleNodeFixedSupplier.class)) {
				tempProductType = ((ASNRoleNodeFixedSupplier) nextNode)
						.getproductType();
				result.add(tempProductType);
			}
		}

		return result;

	}

	@Override
	public List<String> getAllIndividuals() {
		// TODO Auto-generated method stub
		List<String> individualList = new ArrayList<String>();
		Iterator it = ontologyInMemory.listIndividuals();
		while (it.hasNext()) {
			Individual ind = (Individual) it.next();
			individualList.add(ind.getLocalName());
		}
		return individualList;
	}

	@Override
	public List<String> getAllIndividualProducts() {
		// TODO Auto-generated method stub
		List<String> individualList = new ArrayList<String>();
		Iterator it = ontologyInMemory.listIndividuals();
		OntClass valueCreation = ontologyInMemory.getOntClass(Thing.NameSpace
				+ "ValueCreation");
		while (it.hasNext()) {
			Individual ind = (Individual) it.next();

			OntClass indivClass = ind.getOntClass(true);

			if (indivClass.hasSuperClass(valueCreation)) {
				individualList.add(ind.getLocalName());
			}
		}
		return individualList;
	}

	@Override
	public synchronized void removeNodeByName(AbstractSupplyNetwork asn,
			String localName) {
		// TODO Auto-generated method stub
		ASNRoleNode nodeToDelete = null;
		List<ASNRoleNode> nodes = this.getAllRoleNodes(asn);
		for (ASNRoleNode roleNode : nodes) {
			if (roleNode.getQualificationProfile().getProductWithoutPrefix()
					.equals(localName)) {
				nodeToDelete = roleNode;
				break;
			}
		}

		if (nodeToDelete != null) {
			asn.getMyNodes().remove(nodeToDelete);
			suddenGenericRepository.update(asn);
		}

	}

	@Override
	public synchronized void update(ASNDependency dep) {
		create(dep);
	}

	@Override
	public synchronized void create(ASNDependency dep) {
		if (dep.getSourceNode() != null)
			create(dep.getSourceNode());
		if (dep.getTargetNode() != null)
			create(dep.getTargetNode());
		if (dep.getId() == null)
			suddenGenericRepository.create(dep);
		else
			suddenGenericRepository.update(dep);
	}

	@Override
	public synchronized void update(ASNNode node) {
		create(node);
	}

	@Override
	public synchronized void create(ASNNode node) {
		if (node.getId() == null)
			suddenGenericRepository.create(node);
		else
			suddenGenericRepository.update(node);
	}

	@Override
	public synchronized void update(ASNRoleNode node) {
		create(node);
	}

	@Override
	public synchronized void create(ASNRoleNode node) {
		CompetenceNeeded cn = node.getCompetenceNeeded();
		if (cn != null) {
			suddenGenericRepository.create(cn);
		}
		if (node.getId() == null)
			suddenGenericRepository.create(node);
		else
			suddenGenericRepository.update(node);
	}

	@Override
	public synchronized void update(AbstractSupplyNetwork asn) {
		create(asn);
	}

	@Override
	public synchronized void create(AbstractSupplyNetwork asn) {
		for (ASNDependency dep : asn.getMyDependencies()) {
			create(dep);
		}
		for (ASNNode node : asn.getMyNodes()) {
			create(node);
		}
		if (asn.getId() == null) {
			suddenGenericRepository.create(asn);
		} else {
			suddenGenericRepository.update(asn);
		}
		if (asn.getASNEvaluationProfile() != null) {
			create(asn.getASNEvaluationProfile());
		}
	}

	@Override
	public synchronized void update(ConcreteSupplyNetwork csn) {
		create(csn);
	}

	@Override
	public synchronized void create(ConcreteSupplyNetwork csn) {
		if (csn != null) {
			for (ASNRoleNode node : csn.getCandidateSuppliers().keySet()) {
				Supplier s = csn.getCandidateSuppliers().get(node);
				if (s != null) {
					if (s.getId() == null)
						suddenGenericRepository.create(s);
					else
						suddenGenericRepository.update(s);
					csn.getCandidateSuppliers().put(node, s);
					Organization o = s.getOrganisation();
					if (o != null) {
						if (o.getId() == null)
							suddenGenericRepository.create(o);
						else
							suddenGenericRepository.update(o);
					}
				}
			}
			if (csn.getASN() != null)
				create(csn.getASN());
			if (csn.getInitialNode() != null)
				create(csn.getInitialNode());
			if (csn.getId() == null) {
				suddenGenericRepository.create(csn);
			} else {
				suddenGenericRepository.update(csn);
			}
		}
	}

	@Override
	public synchronized void update(NetworkEvaluationProfile nep) {
		create(nep);
	}

	@Override
	public synchronized void create(NetworkEvaluationProfile nep) {
		for (CompetenceNeededByNetworkEvaluationProfile cn : nep
				.getAllCompetences()) {
			if (cn.getId() == null)
				create(cn);
			else
				update(cn);
		}

		if (nep.getId() == null) {
			suddenGenericRepository.create(nep);
		} else {
			suddenGenericRepository.update(nep);
		}

	}

	@Override
	public synchronized void update(CompetenceNeeded cn) {
		create(cn);
	}

	@Override
	public synchronized void create(CompetenceNeeded cn) {
		if (cn.getId() == null)
			suddenGenericRepository.create(cn);
		else
			suddenGenericRepository.update(cn);
	}

	@Override
	public synchronized void delete(AbstractSupplyNetwork asn) {
		// for (ASNDependency d : asn.getMyDependencies()) {
		// d.setSourceNode(null);
		// d.setTargetNode(null);
		// suddenGenericRepository.delete(d);
		// }
		// asn.getMyDependencies().clear();
		// for (ASNNode n : asn.getMyNodes()) {
		// suddenGenericRepository.delete(n);
		// }
		// asn.getMyNodes().clear();

		suddenGenericRepository.delete(asn);
	}

	@Override
	public synchronized void delete(ConcreteSupplyNetwork csn) {
		if (csn != null) {
			// I *think* that removing the reference to the case file from the
			// csn means we don't need this anymore?
			/*
			 * if (csn.getTmpTeamCaseFile() != null) { if
			 * (csn.getTmpTeamCaseFile().getPrototypeTeam() != null &&
			 * csn.getTmpTeamCaseFile().getPrototypeTeam().equals(csn))
			 * csn.getTmpTeamCaseFile().setPrototypeTeam(null); if
			 * (csn.getTmpTeamCaseFile().getFinalTeam() != null &&
			 * csn.getTmpTeamCaseFile().getFinalTeam().equals(csn))
			 * csn.getTmpTeamCaseFile().setFinalTeam(null); if
			 * (csn.getTmpTeamCaseFile().getTempTeams() != null &&
			 * csn.getTmpTeamCaseFile().getTempTeams().contains(csn)) // might
			 * not work...to be sure use the ID //
			 * csn.getTmpTeamCaseFile().getTempTeams().remove(csn); for
			 * (ConcreteSupplyNetwork c :
			 * csn.getTmpTeamCaseFile().getTempTeams()) { if
			 * (c.getId().equals(csn.getId())) {
			 * csn.getTmpTeamCaseFile().getTempTeams().remove(c); break; } } }
			 */
			suddenGenericRepository.delete(csn);
		}
	}

	@Override
	public synchronized void delete(ASNDependency dep) {
		dep.setSourceNode(null);
		dep.setTargetNode(null);
		suddenGenericRepository.delete(dep);
	}

	@Override
	public ASNNode retrieveASNNode(Long id) {
		return suddenGenericRepository.retrieveByTypeAndId(ASNNode.class, id);
	}

	@Override
	public AbstractSupplyNetwork retrieveASN(Long id) {
		return suddenGenericRepository.retrieveByTypeAndId(
				AbstractSupplyNetwork.class, id);
	}

	public synchronized void assignSupplierByName(AbstractSupplyNetwork asn,
			String ASNNodeName, Organization orgToAssign) {
		ASNRoleNode nodeToFixSupplierFor = null;
		List<ASNRoleNode> nodes = this.getAllRoleNodes(asn);
		for (ASNRoleNode roleNode : nodes) {
			if (roleNode.getQualificationProfile().getProductWithoutPrefix()
					.equals(ASNNodeName)) {
				nodeToFixSupplierFor = roleNode;
				break;
			}
		}

		if (nodeToFixSupplierFor != null) {
			asn.assignFixedSupplier(nodeToFixSupplierFor, orgToAssign);
			suddenGenericRepository.update(asn);
		}
	}

	@Override
	public boolean isLightWeightConstruction(AbstractSupplyNetwork asn) {
		// The default answer when no info present
		boolean result = true;
		String indivName = asn.getFinalNode().getQualificationProfile()
				.getProductIndividualURI();

		logger.debug(" Part to assemble is " + indivName);

		Individual i = this.ontologyInMemory.getIndividual(indivName);

		OntResource res = ((OntResource) i
				.getPropertyValue(this.ontologyInMemory
						.getProperty((Thing.NameSpace + "Has_Overall_Weight"))));

		if (res != null) {
			result = res.asIndividual().getURI().equals(
					Thing.NameSpace + "LowWeightMaterial");
		}

		logger.debug(" result of checking for lightweight or not " + res);

		return result;
	}
}
