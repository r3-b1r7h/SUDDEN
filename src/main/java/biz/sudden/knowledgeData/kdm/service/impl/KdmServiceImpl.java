package biz.sudden.knowledgeData.kdm.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.Individual;
import biz.sudden.baseAndUtility.domain.Link;
import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.domain.connectable.StringValue;
import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.baseAndUtility.service.ActorService;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.IndividualService;
import biz.sudden.baseAndUtility.service.LinkService;
import biz.sudden.baseAndUtility.service.RootLinkService;
import biz.sudden.baseAndUtility.service.ScopeService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.baseAndUtility.web.controller.RootLinkController;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoard;
import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoardRating;
import biz.sudden.designCoordination.collaborativePlanning.domain.TopicRating;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPBulletinBoardService;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPSendMessageService;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.evaluation.coordinationFit.service.CoordinationFitService;
import biz.sudden.evaluation.performanceMeasurement.domain.DivisionFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.MaxFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.MinFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.MinusFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.MultiplicationFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.SumFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.WeightedSumFunction;
import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.evaluation.performanceMeasurement.service.NetworkEvaluationService;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMInstancesManagement_Service;
import biz.sudden.knowledgeData.kdm.repository.DummyCaseFileRepository;
import biz.sudden.knowledgeData.kdm.service.CaseService;
import biz.sudden.knowledgeData.kdm.service.KdmService;
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
import biz.sudden.knowledgeData.serviceManagement.repository.hibernate.ThingRepository;
import biz.sudden.knowledgeData.serviceManagement.service.MachineryType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ManufacturingProcessType_Service;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.knowledgeData.serviceManagement.service.TechnologyService;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;
import biz.sudden.userOrganizationManagement.organizationManagement.service.IOrganization;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.impl.IndividualImpl;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import datasource.HsqldbDataSource;

/**
 * Inits Database for tests via Services
 * 
 * @author chris
 */
public class KdmServiceImpl implements KdmService {

	private static String topLevelEvalProfile = "SUddEN Performance Profile";

	private StringBuffer DEBUG_MSG = new StringBuffer();

	private Logger logger = Logger.getLogger(this.getClass());
	// Services
	protected ServiceManagementService smService;
	protected ConnectableService connectableService;
	protected UserService userService;
	protected IOrganization organisationService;
	protected ActorService actorService;
	protected EnterpriseEvaluationService enterpriseEvaluationService;
	protected NetworkEvaluationService networkEvaluationService;
	protected ManufacturingProcessType_Service manufacturingProcessService;
	protected ICMInstancesManagement_Service cmInstancesService;
	protected ICMCompetencesManagement_Service cmService;

	protected CPBulletinBoardService bulletinBoardService;

	protected CPSendMessageService sendMessageService;
	protected ScopeService scopeService;

	protected ProductMaterialSupportingServices_Service pmsService;
	protected TechnologyService technologyService;
	protected MachineryType_Service machineService;
	protected CaseService caseService;
	protected CoordinationFitService coordinationFitService;
	protected IOrganizationRepository organizationRepository;

	private IndividualService individualService;

	private ThingRepository thingRepository;

	private SuddenGenericRepository suddenGenericRep;

	private CPBulletinBoardService ratingService;

	private LinkService linkService;

	private RootLinkService rootLinkService;

	private HashMap<Scope, Tree> connectableTrees = new HashMap<Scope, Tree>();

	public RootLinkService getRootLinkService() {
		return rootLinkService;
	}

	public void setRootLinkService(RootLinkService rootLinkService) {
		this.rootLinkService = rootLinkService;
	}

	public LinkService getLinkService() {
		return linkService;
	}

	public void setLinkService(LinkService linkService) {
		this.linkService = linkService;
	}

	public CPBulletinBoardService getRatingService() {
		return ratingService;
	}

	public void setRatingService(CPBulletinBoardService ratingService) {
		this.ratingService = ratingService;
	}

	public SuddenGenericRepository getSuddenGenericRep() {
		return suddenGenericRep;
	}

	public void setSuddenGenericRep(SuddenGenericRepository suddenGenericRep) {
		this.suddenGenericRep = suddenGenericRep;
	}

	private HsqldbDataSource dataSource;

	public HsqldbDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(HsqldbDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public ThingRepository getThingRepository() {
		return thingRepository;
	}

	public void setThingRepository(ThingRepository thingRepository) {
		this.thingRepository = thingRepository;
	}

	public IndividualService getIndividualService() {
		return individualService;
	}

	public void setIndividualService(IndividualService individualService) {
		this.individualService = individualService;
	}

	public void setCoordinationFitService(
			CoordinationFitService coordinationFitService) {
		this.coordinationFitService = coordinationFitService;
	}

	public void setOrganizationRepository(
			IOrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	protected int testCounter = 1;

	private File ontologyUploadFile;

	private OntModel ontology;

	private IDBConnection conn = null;

	private void initOWLOntology() {

		ModelMaker maker;

		try {
			List<Material> mcons = pmsService.retrieveAllMaterials();
			for (Material m : mcons) {
				pmsService.deleteMaterial(m);
			}

			conn = new DBConnection(dataSource.getConnection(), dataSource
					.getDatabaseAsString());

			maker = ModelFactory.createModelRDBMaker(conn);

			Model model = maker.createDefaultModel();

			ontology = ModelFactory.createOntologyModel(
					OntModelSpec.OWL_DL_MEM, model);

			OntDocumentManager documentManager = ontology.getDocumentManager();

			documentManager.addModel(Thing.SuddenOntologyURI, model);

			try {
				if (ontologyUploadFile != null) {
					ontology.removeAll();

					InputStream in = new FileInputStream(ontologyUploadFile);
					ontology.read(in, "");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// conn.close();
		} catch (Exception ex) {
			logger.error(ex);
		}

	}

	public OntModel getOntology() {

		ModelMaker maker;

		if (conn == null) {

			try {

				conn = new DBConnection(dataSource.getConnection(), dataSource
						.getDatabaseAsString());

				maker = ModelFactory.createModelRDBMaker(conn);

				Model model = maker.createDefaultModel();

				ontology = ModelFactory.createOntologyModel(
						OntModelSpec.OWL_DL_MEM_TRANS_INF, model);

				OntDocumentManager documentManager = ontology
						.getDocumentManager();

				documentManager.addModel(Thing.SuddenOntologyURI, model);

			} catch (SQLException ex) {
				logger.error(ex.getLocalizedMessage());
				return null;

			}
		}

		return ontology;

	}

	public String getOntologyNameSpace() {
		return Thing.SuddenOntologyURI;
	}

	// default Constructor
	public KdmServiceImpl() {
		logger.debug("KdmServiceImpl -> cst");
	}

	/**
	 * cleans database If you want to use this functionality for your own
	 * service you have to add here the clean methods
	 */

	@Override
	public void clean() {

		sendMessageService.deleteInitializedCommuncations();

		bulletinBoardService.deleteBulletinBoards();

		logger.debug("To be cleaned HSQLDBCOUNT:" + HsqldbDataSource.COUNT);

		// do casefile and bo first as these reference a lot of other objects
		for (CaseFile cf : caseService.retrieveAllCaseFiles()) {
			caseService.delete(cf);
		}

		for (BusinessOpportunity bo : caseService.retrieveAllBOs()) {
			caseService.delete(bo);
		}

		// delete User after BO
		userService.deleteInitializedUsers();

		for (Occurrence o : scopeService
				.retrieveOccurrencesFrom(connectableService
						.getRetrieveAllScope()))
			connectableService.deleteOccurrence(o);

		for (Association a : scopeService
				.retrieveAssociationsFrom(connectableService
						.getRetrieveAllScope()))
			connectableService.delete(a);

		for (EnterpriseEvaluationProfile epep : enterpriseEvaluationService
				.retrieveAllEnterpriseProfiles()) {
			enterpriseEvaluationService.deleteEnterpriseProfile(epep);
		}

		for (System con : pmsService.retrieveAllSystems()) {
			pmsService.deleteSystem(con);
		}

		for (ComplexProduct cp : pmsService.retrieveAllComplexProducts()) {
			pmsService.deleteComplexProduct(cp);
		}

		for (SimpleProduct sp : pmsService.retrieveAllSimpleProducts()) {
			pmsService.deleteSimpleProduct(sp);
		}

		for (SupportingService ss : pmsService.retrieveAllSupportingServices()) {
			pmsService.deleteSupportingService(ss);
		}

		for (Material m : pmsService.retrieveAllMaterials()) {
			pmsService.deleteMaterial(m);
		}

		for (Technology t : technologyService.retrieveAllTechnologies()) {
			technologyService.deleteTechnology(t);
		}

		for (Machine m : machineService.retrieveAllMachines()) {
			machineService.deleteMachine(m);
		}

		for (Organization o : smService.retrieveAllOrganisations()) {
			smService.deleteOrganisation(o);
		}

		for (Scope s : scopeService.retrieveAllScopes()) {
			if (!scopeService.getUnspecifiedScope().getId().equals(s.getId()))
				scopeService.deleteScope(s);
		}
		logger.debug("cleaned HSQLDBCOUNT:" + HsqldbDataSource.COUNT);
	}

	@Override
	public void initOntology(File ontologyUploadFile) {
		long systime = java.lang.System.currentTimeMillis();

		if (ontologyUploadFile != null)
			this.ontologyUploadFile = ontologyUploadFile;

		if (this.ontologyUploadFile != null) {
			logger.debug("Ontology File: "
					+ this.ontologyUploadFile.getAbsolutePath());
			logger.debug("Ontology File - canRead: "
					+ this.ontologyUploadFile.canRead());
		}
		initOWLOntology();

		this.DEBUG_MSG.append(" owl model init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		initAssociations();

		this.DEBUG_MSG.append(" associations init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		initMaterialsTechnologiesMachinesValueCreation();

		this.DEBUG_MSG.append(" mat tech mach val init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		initMaterialsForProducts(null);

		this.DEBUG_MSG.append(" materials for products init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		initFunctions();

		this.DEBUG_MSG.append(" functions init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		initUsers();

		this.DEBUG_MSG.append(" users init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		initMessages();

		this.DEBUG_MSG.append(" msgs init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		initIndividuals();

		this.DEBUG_MSG.append(" individuals init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		initBulletinBoards();

		this.DEBUG_MSG.append(" bulletin init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		initTrainings();

		this.DEBUG_MSG.append(" trainings  init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		// initClosedCases();

		// this.DEBUG_MSG.append(" closed cases init "+
		// (java.lang.System.currentTimeMillis() - systime));
		// systime = java.lang.System.currentTimeMillis();
		// java.lang.System.err.println(DEBUG_MSG);

		initCases(5);

		this.DEBUG_MSG.append(" cases init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		/* init questionnaire & competences is done in the KDM controller!! */

		// do we really need to close the connection?
		// yes;
		try {
			// conn.getConnection().commit();
			conn.close();
		} catch (SQLException ex) {
			logger.error(ex);
		}
	}

	@Override
	public void initPMS() {
		// do casefiles && organizations first!!
		long systime = java.lang.System.currentTimeMillis();
		initEvaluationProfiles(true);
		this.DEBUG_MSG.append(" evaluation profiles init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

	}

	/**
	 * This generates organisations for all available products and services and
	 * makes BO's for all Complex Products
	 */
	@Override
	public void initCompanyPerformance() {
		long systime = java.lang.System.currentTimeMillis();

		// do simple products & users first!
		initOrganisations(pmsService.retrieveAllSimpleProducts());
		initOrganisations(pmsService.retrieveAllComplexProducts());
		initOrganisations2(pmsService.retrieveAllSupportingServices());

		// init complex Products, Users, Organizations first!
		initCases(0);

		this.DEBUG_MSG.append(" cases init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		// do casefiles first!!
		initEvaluationProfiles(true);

		this.DEBUG_MSG.append(" evaluationprofiles init "
				+ (java.lang.System.currentTimeMillis() - systime));
		systime = java.lang.System.currentTimeMillis();
		java.lang.System.err.println(DEBUG_MSG);

		try {
			// conn.getConnection().commit();
			conn.close();
		} catch (SQLException ex) {
			logger.error(ex);
		}

	}

	/**
	 * initialises the database with default stuff If you want to use this
	 * functionality for your own service you have to add here the retrieveAll
	 * methods
	 */

	@Override
	public List<Connectable> retrieveAllConnectables() {

		// return suddenGenericRep.retrieveAllByType(Connectable.class);

		// return connectableRepository.retrieveAllConnectablesWithPosition(
		// AssociationRole.Position.HEAD);

		long millis = new Date().getTime();

		logger
				.debug("GetAllContectables HSQLDBCOUNT:"
						+ HsqldbDataSource.COUNT);

		List<Connectable> result = new ArrayList<Connectable>();
		int oldsize = 0;

		// we don't do this. we only get the top
		// result.addAll(enterpriseEvaluationService.retrieveAllEnterpriseProfiles());
		result.addAll(enterpriseEvaluationService
				.retrieveEnterpriseProfile(topLevelEvalProfile));
		logger.debug((result.size() - oldsize)
				+ " retrieveAllEnterpriseProfiles");
		oldsize = result.size();

		// result.addAll(pmsService.retrieveAllSystems());
		result.addAll(pmsService.retrieveSystem("System"));
		logger.debug((result.size() - oldsize) + " retrieve Systems");
		oldsize = result.size();

		// result.addAll(pmsService.retrieveAllComplexProducts());
		result.addAll(pmsService.retrieveComplexProduct("ComplexPart"));
		logger.debug((result.size() - oldsize) + " retrieve ComplexProducts");
		oldsize = result.size();

		// result.addAll(pmsService.retrieveAllSimpleProducts());
		result.addAll(pmsService.retrieveSimpleProduct("Simple_Part"));
		logger.debug((result.size() - oldsize) + " retrieve SimpleProducts");
		oldsize = result.size();

		result.addAll(pmsService.retrieveAllSupportingServices());
		logger.debug((result.size() - oldsize)
				+ " retrieveAllSupportingServices");
		oldsize = result.size();

		// we don't do this. we only get the top
		// result.addAll(pmsService.retrieveAllMaterials());
		result.addAll(pmsService.retrieveMaterial("Material")); // or Werkstoff
																// - a
		// subclass of
		// Material
		logger.debug((result.size() - oldsize) + " retrieve Material");
		oldsize = result.size();

		// we don't do this. we only get the top
		// result.addAll(technologyService.retrieveAllTechnologies());
		result.add(technologyService.retrieveTechnology("Processing"));
		logger.debug((result.size() - oldsize) + " retrieve Technologie");
		oldsize = result.size();

		result.addAll(machineService.retrieveAllMachines());
		logger.debug((result.size() - oldsize) + " retrieveAllMachines");
		oldsize = result.size();

		result.addAll(smService.retrieveAllOrganisations());
		logger.debug((result.size() - oldsize) + " retrieveAllOrganisations");
		oldsize = result.size();

		result.addAll(smService.retrieveAllProcesses());
		logger.debug((result.size() - oldsize) + " retrieveAllProcesses");
		oldsize = result.size();

		result.addAll(caseService.retrieveAllBOs());
		logger.debug((result.size() - oldsize) + " retrieveAllBOs");
		oldsize = result.size();

		result.addAll(caseService.retrieveAllCaseFiles());
		logger.debug((result.size() - oldsize) + " retrieveAllCaseFiles");
		oldsize = result.size();

		logger
				.debug("GetAllContectables HSQLDBCOUNT:"
						+ HsqldbDataSource.COUNT);
		logger.debug("retrieveallconnectables "
				+ (new Date().getTime() - millis) + " milliseconds");

		return result;
	}

	private void initClosedCases() {
		DummyCaseFileRepository dummyCaseFileRepository = new DummyCaseFileRepository();
		dummyCaseFileRepository
				.setCoordinationFitService(coordinationFitService);
		dummyCaseFileRepository
				.setOrganizationRepository(organizationRepository);

		List<CaseFile> retrieveAll = dummyCaseFileRepository.retrieveAll();
		for (CaseFile caseFile : retrieveAll) {
			caseService.create(caseFile);
		}
	}

	/**
	 * inits test data for PMEnterpriseProfileDesignController Init CaseFiles
	 * first!
	 */
	private void initEvaluationProfiles(boolean forOrganisations) {
		// make shure we have the appropriate functions!
		enterpriseEvaluationService
				.createOrRetrieveEvaluationFunction(new DivisionFunction());
		enterpriseEvaluationService
				.createOrRetrieveEvaluationFunction(new MaxFunction());
		enterpriseEvaluationService
				.createOrRetrieveEvaluationFunction(new MinFunction());
		enterpriseEvaluationService
				.createOrRetrieveEvaluationFunction(new MinusFunction());
		enterpriseEvaluationService
				.createOrRetrieveEvaluationFunction(new MultiplicationFunction());
		enterpriseEvaluationService
				.createOrRetrieveEvaluationFunction(new SumFunction());
		enterpriseEvaluationService
				.createOrRetrieveEvaluationFunction(new WeightedSumFunction());

		logger.debug("create EvalProfiles HSQLDBCOUNT:"
				+ HsqldbDataSource.COUNT);

		if (enterpriseEvaluationService.retrieveEnterpriseProfile(
				topLevelEvalProfile).size() == 0) {
			List<EnterpriseEvaluationProfile> sub = new ArrayList<EnterpriseEvaluationProfile>();
			if (forOrganisations) {
				sub.add(makeProfileForOrganisations("L1 Technologie"));
				sub.add(makeProfileForOrganisations("L0 Finanzen"));
				sub.add(makeProfileForOrganisations("L0 Qualitaetsmanagement"));
				sub
						.add(makeProfileForOrganisations("L1 Mitarbeiterqualifikation"));
				sub.add(makeProfileForOrganisations("L1 Kundenfokus"));
				sub.add(makeProfileForOrganisations("L0 Recht und Haftung"));
				sub
						.add(makeProfileForOrganisations("L0 KontinuierlicheVerbesserungLernenUndInnovation"));
				sub
						.add(makeProfileForOrganisations("L1 Kommunikationstechnologie"));
			} else {
				sub.add(makeProfile("L1 Technologie"));
				sub.add(makeProfile("L0 Finanzen"));
				sub.add(makeProfile("L0 Qualitaetsmanagement"));
				sub.add(makeProfile("L1 Mitarbeiterqualifikation"));
				sub.add(makeProfile("L1 Kundenfokus"));
				sub.add(makeProfile("L0 Recht und Haftung"));
				sub
						.add(makeProfile("L0 KontinuierlicheVerbesserungLernenUndInnovation"));
				sub.add(makeProfile("L1 Kommunikationstechnologie"));
			}
			makeProfileInScope(scopeService.getUnspecifiedScope(), sub);
			for (BusinessOpportunity bo : caseService.retrieveAllBOs()) {
				// Profiles for all BOs!!!!!!!!!!!!!!
				Scope s = scopeService.retrieveScopeBy(bo.getName());
				if (s != null) {
					makeProfileInScope(s, sub);
				}
			}
		}

		logger.debug("EvalProfiles created  HSQLDBCOUNT:"
				+ HsqldbDataSource.COUNT);
	}

	public void initTrainings() {

		List<String> issueItems = new ArrayList<String>();
		Map<Integer, List<String>> issueSubItems = new HashMap<Integer, List<String>>();
		List<String> subItemList1 = new ArrayList<String>();
		List<String> subItemList2 = new ArrayList<String>();
		List<String> subItemList3 = new ArrayList<String>();
		List<String> subItemList4 = new ArrayList<String>();

		issueItems.add("TPM / OEE");

		createTopicsAndAddProfileLink("Instandhaltung / Wartung",
				"L1 Technologie", issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Zeitwirtschaft");
		issueItems.add("Wertanalyse");
		issueItems.add("Six Sigma");

		subItemList1.add("Durchlaufzeiten");
		subItemList1.add("Ruesten");

		issueSubItems.put(1, subItemList1);

		createTopicsAndAddProfileLink("Prozessabsicherung und -optimierung",
				"L1 Technologie", issueItems, issueSubItems);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Variantenmanagement");
		issueItems.add("TRIZ");
		issueItems.add("QFD");
		issueItems.add("Produkt FMEA");
		issueItems.add("Poka-Yoke");

		subItemList1.add("Variantenreduktionskennzahlen");

		issueSubItems.put(1, subItemList1);

		createTopicsAndAddProfileLink("Konstruktion", "L1 Technologie",
				issueItems, issueSubItems);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Kostenrechnung / Kalkulation");
		issueItems.add("Controlling");
		issueItems.add("Kennzahlsysteme");

		createTopicsAndAddProfileLink("Finanzmanagement", "L0 Finanzen",
				issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Finanzierung");
		issueItems.add("Foerderungen");

		createTopicsAndAddProfileLink("Nutzung oeffentlicher Quellen",
				"L0 Finanzen", issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		createTopicsAndAddProfileLink(
				"Wirtschaftlich nachhaltiger Umgang mit Betriebsanlagen",
				"L0 Finanzen", issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Lieferanten");
		issueItems.add("Lagerung");
		issueItems.add("Rueckverfolgbarkeit");
		issueItems.add("Logistiksysteme");

		subItemList1.add("Lieferantenmanagement");
		subItemList1.add("Einkaufsverhandlung");
		subItemList1.add("Reduzierung der Einkaufskosten");
		subItemList1.add("Preisstrukturanalyse");
		subItemList1.add("Strategischer Einkauf");

		subItemList2.add("Bestandsmanagement");
		subItemList2.add("Wertstromdesign");

		subItemList3.add("Kanban");
		subItemList3.add("Bedarfsorientierte Versorgung");

		issueSubItems.put(1, subItemList1);
		issueSubItems.put(2, subItemList2);
		issueSubItems.put(4, subItemList3);

		createTopicsAndAddProfileLink("Logistikmanagement",
				"L0 Qualitaetsmanagent", issueItems, issueSubItems);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Entsorgung und Umweltvertraeglichkeit");
		issueItems
				.add("Zustand, Organisation, Ordnung, UEbersicht und Sauberkeit im Produktionsbereich");
		issueItems.add("Energieverbrauch");

		subItemList1.add("Arbeitssicherheitsmanagement");
		subItemList1.add("SOS / 5-S Methode");

		issueSubItems.put(2, subItemList1);

		createTopicsAndAddProfileLink("Umweltmanagement",
				"L0 Qualitaetsmanagent", issueItems, issueSubItems);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Planung, Pruefmittel und Prozessbeschreibungen");
		issueItems.add("Prozessfaehigkeitsuntersuchungen");

		subItemList1
				.add("Managementsystem- Einfuehrung DIN EN ISO 9001, ISO/TS 16949, u.a.");
		subItemList1.add("Systemaudits");

		subItemList2.add("Produktionssysteme");
		subItemList2.add("Fehlervermeidung (z.B. 4D-Report)");
		subItemList2.add("System-, Produkt- und Prozessaudits");
		subItemList2.add("Prozess - FMEA");

		issueSubItems.put(1, subItemList1);
		issueSubItems.put(2, subItemList2);

		createTopicsAndAddProfileLink("Qualitaetsmanagement",
				"L0 Qualitaetsmanagent", issueItems, issueSubItems);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Unterweisungen und Schulung");
		issueItems.add("Dokumentation von Kompetenzen und Faehigkeiten");

		createTopicsAndAddProfileLink("Mitarbeiterqualifikation",
				"L1 Mitarbeiterqualifikation", issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Mitarbeiterfuehrung");
		issueItems.add("Umgang mit Veraenderungen");
		issueItems.add("Mitarbeiterfoerderung und Kommunikation");
		issueItems.add("Strukturierung von Planung von Besprechungen");

		subItemList1.add("Strategieentwicklung");
		subItemList1.add("Risikomanagement");

		subItemList2.add("Personalentwicklung");
		subItemList2.add("Entlohnungskonzepte");
		subItemList2.add("Zielvereinbarung");

		subItemList3.add("Meetings");
		subItemList3.add("Dokumentierte Mitarbeitergespraeche");

		issueSubItems.put(2, subItemList1);
		issueSubItems.put(3, subItemList2);
		issueSubItems.put(4, subItemList3);

		createTopicsAndAddProfileLink("Fuehrungskompetenz",
				"L1 Mitarbeiterqualifikation", issueItems, issueSubItems);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		createTopicsAndAddProfileLink("Versorgungssicherheit",
				"L1 Kundenfokus", issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Produktinnovationen");
		issueItems.add("Wahrgenommene Produktqualitaet");
		issueItems.add("Auszendienst");
		issueItems.add("Innendienst");
		issueItems.add("Kundenbindung");
		issueItems.add("Neukundengewinnung");
		issueItems.add("Produktmanagement");
		issueItems.add("Export/Internationales");

		subItemList1.add("Internationale Markterschlieszung");
		subItemList1.add("Exportabwicklung");

		issueSubItems.put(8, subItemList1);

		createTopicsAndAddProfileLink("Kundenerwartungen und -zufriedenheit",
				"L1 Kundenfokus", issueItems, issueSubItems);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Reklamationsmanagement (z.B. 8D-Report)");

		createTopicsAndAddProfileLink("Reklamationsmanagement",
				"L1 Kundenfokus", issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Vertragsgestaltung");
		issueItems.add("Ersatzteilversorgung");
		issueItems.add("Ausfallhaftung");

		createTopicsAndAddProfileLink("Kundenbezogene Vereinbarungen",
				"L0 Recht und Haftung", issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Rechtsberatung");

		createTopicsAndAddProfileLink("Produktbezogene Vorschriften",
				"L0 Recht und Haftung", issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Garantie und Produkthaftung");

		createTopicsAndAddProfileLink("Gewerbliche Rahmenbedingungen",
				"L0 Recht und Haftung", issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Projektmanagement");

		createTopicsAndAddProfileLink("Eigenentwicklung",
				"L0 KontinuierlicheVerbesserungLernenUndInnovation",
				issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Wissens- und Innovationsmanagement");
		issueItems.add("KVP");
		issueItems.add("Kaizen, Poka-Yoke");

		createTopicsAndAddProfileLink(
				"Strukturierter Rahmen fuer innovative und kreative Ideen",
				"L0 KontinuierlicheVerbesserungLernenUndInnovation",
				issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Changemanagement");
		issueItems.add("Produktoptimierung");

		createTopicsAndAddProfileLink(
				"Implementierung von Verbesserungsmasznahmen",
				"L0 KontinuierlicheVerbesserungLernenUndInnovation",
				issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		issueItems.add("Produktionsplanung und -steuerung");
		issueItems.add("CAD/CAM Systeme");
		issueItems.add("Versionsmanagement");

		createTopicsAndAddProfileLink("Informationstechnik",
				"L1 Kommunikationstechnologie", issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		createTopicsAndAddProfileLink("Kommunikationstechnik",
				"L1 Kommunikationstechnologie", issueItems, null);

		clearListsAndMaps(issueItems, subItemList1, subItemList2, subItemList3,
				subItemList4, issueSubItems);

		createTopicsAndAddProfileLink("Sicherheit und Sicherung",
				"L1 Kommunikationstechnologie", issueItems, null);

	}

	private void clearListsAndMaps(List first, List second, List third,
			List fourth, List fifth, Map map) {
		first.clear();
		second.clear();
		third.clear();
		fourth.clear();
		fifth.clear();
		map.clear();
	}

	private void createTopicsAndAddProfileLink(String topicName,
			String enterpriseProfileName, List<String> issueItems,
			Map<Integer, List<String>> issueSubItems) {

		JsfLink ratingBoardLink = new JsfLink();

		for (JsfLink jsfLink : rootLinkService.getAllLinkableTypes()) {

			logger.debug(jsfLink);

			if ("showtopicsrating".equals(jsfLink.getViewID())) {
				ratingBoardLink.setControllerBeanName(jsfLink
						.getControllerBeanName());
				ratingBoardLink.setDescription(jsfLink.getDescription());
				ratingBoardLink.setDomainClass(jsfLink.getDomainClass());
				ratingBoardLink.setDomainId(jsfLink.getDomainId());
				ratingBoardLink.setText(jsfLink.getText());
				ratingBoardLink.setViewID(jsfLink.getViewID());
				ratingBoardLink.setParameterValuesPairs(jsfLink
						.getParameterValuesPairs());
				break;
			}

		}

		BulletinBoard ratingBoard = ratingService
				.getBoardWithName(enterpriseProfileName);

		if (ratingBoard == null) {

			ratingBoard = new BulletinBoardRating();
			ratingBoard.setName(enterpriseProfileName);
			ratingService.createBulletin(ratingBoard);

			EnterpriseEvaluationProfile profile = null;
			List<EnterpriseEvaluationProfile> eep = enterpriseEvaluationService
					.retrieveEnterpriseProfile(enterpriseProfileName);
			if (eep.size() == 0) {
				profile = new EnterpriseEvaluationProfile();
				profile.setName(enterpriseProfileName);
				// eep1.setResult(new Double(value));
				enterpriseEvaluationService.createEnterpriseProfile(profile);
			} else {
				profile = eep.get(0);
			}

			Link link = new Link();
			link.setFromClass(profile.getClass().getName());
			link.setFromID(new Long(profile.getId().toString()));

			// TODO TF: getHumanReadable BAD !
			ratingBoardLink.setText(ratingBoardLink.getDescription() + "->"
					+ RootLinkController.getHumanReadable(ratingBoard));
			ratingBoardLink.setDomainId(ratingBoard.getId().toString());

			link.setToJsfLink(ratingBoardLink);

			linkService.createLink(link);

		}

		TopicRating topicRating = new TopicRating();
		topicRating.setName(topicName);

		topicRating.setDescription(createFormattedText(topicName + " Training",
				issueItems, issueSubItems));

		ratingService
				.createTopicAndAddToBulletinBoard(ratingBoard, topicRating);

	}

	private String createFormattedText(String headLine,
			List<String> issueItems, Map<Integer, List<String>> issueSubItems) {
		StringBuffer buffer = new StringBuffer();
		buffer
				.append("<p><span style='font-size: x-large;'><span style='color: rgb(255, 0, 0);'>");
		buffer.append(headLine);
		buffer.append("</span></span></p>");
		buffer.append("<ul>");
		int i = 0;
		for (String string : issueItems) {
			i++;
			buffer.append("<li><span style='font-size: medium;'>" + string
					+ "</span>");
			if (issueSubItems != null && issueSubItems.get(i) != null) {
				buffer.append("<ul>");
				for (String subString : issueSubItems.get(i)) {
					buffer.append("<li><span style='font-size: medium;'>"
							+ subString + "</span></li>");
				}
				buffer.append("</ul>");
			}
			buffer.append("</li>");
		}
		buffer.append("</ul>");
		buffer.append("<p>&nbsp;</p>");
		return buffer.toString();
	}

	// FIXME make this public and create a default profile for each newly
	// created
	// BO/CaseFile
	// do for each casefile
	private void makeProfileInScope(Scope s,
			List<EnterpriseEvaluationProfile> sub) {
		List<Double> weights = new ArrayList<Double>();
		Double sum = new Double(0.0d);
		for (int i = 0; i < sub.size(); i++) {
			Double weight = new Double(
					(double) ((int) (Math.random() * 100)) / 100);
			sum += weight;
			weights.add(weight);
		}
		for (int i = 0; i < weights.size(); ++i) {
			weights.set(i, weights.get(i) / sum);
		}
		enterpriseEvaluationService.createOrAssociateEnterpriseProfile(
				"SUDDEN Performance Profile", WeightedSumFunction.TYPE_NAME,
				weights, sub, s);
	}

	@Override
	public EnterpriseEvaluationProfile makeProfile(String name) {
		List<EnterpriseEvaluationProfile> eep = enterpriseEvaluationService
				.retrieveEnterpriseProfile(name);
		if (eep.size() == 0) {
			EnterpriseEvaluationProfile eep1 = new EnterpriseEvaluationProfile();
			eep1.setName(name);
			// eep1.setResult(new Double(value));
			enterpriseEvaluationService.createEnterpriseProfile(eep1);
			return eep1;
		}
		return eep.get(0);
	}

	// FIXME make this public and create a default Evaluation Profile for newly
	// created Organisations //
	/** Occurrences for Organisations!!! */
	private EnterpriseEvaluationProfile makeProfileForOrganisations(String name) {
		List<EnterpriseEvaluationProfile> eep = enterpriseEvaluationService
				.retrieveEnterpriseProfile(name);
		EnterpriseEvaluationProfile result;
		if (eep.size() == 0) {
			result = new EnterpriseEvaluationProfile();
			result.setName(name);
			// eep1.setResult(new Double(value));
			enterpriseEvaluationService.createEnterpriseProfile(result);
		} else {
			result = eep.get(0);
		}
		for (Organization org : this.organisationService.retrieveAll()) {
			// the organisation name has a int at the last position
			// orgX1 ... orgX2
			// offset would be if we have multiple org for the same
			// thing
			double offset = 1.0d;// Integer.parseInt(org.getName().substring(org
			// .getName().length()-1));
			enterpriseEvaluationService.associateOccurence(""
					+ (Math.random() * (10.0d / offset) + offset - 1.0d),
					result, "Double", org.getName());

		}
		return result;

	}

	public void initUsers() {
		logger.debug("Init Users in UserService " + userService);
		try {
			userService.initializeUsers();
		} catch (Exception ex) {
			logger.error("Error in KdmServiceImpl.initUsers " + ex);
		}
	}

	public void initMessages() {
		logger.debug("Init asynchronous messages (inbox)");
		sendMessageService.initializeCommunications();
	}

	public void initBulletinBoards() {
		logger.debug("Init Bulletin Boards");
		bulletinBoardService.deleteBulletinBoards();
		bulletinBoardService.initializeBulletinBoards();
	}

	// Do products first!!!
	private void initOrganisations(List<? extends Product> products) {
		String OrgExtension = " Manufaktur ";
		List<SuddenUser> usrs = userService.retrieveAllUsers();

		for (Product p : products) {
			String prodName = p.getName();
			// no organisatinos that do cardoorwindows. Now for the sake of
			// argument
			// no one can do actuators :)
			// Actually disabled for now to allow triple checking that all is A1
			// OK
			// if (prodName.toLowerCase().indexOf("aktuatoren") < 0) {
			for (int i = 1; i <= 2; ++i) {
				// this is for multiple org
				if (smService.retrieveOrganisationsByName(
						prodName + OrgExtension + i).size() == 0) {
					// create only once
					Organization o = new Organization();
					o.setName(prodName + OrgExtension + i);

					String user = usrs.get(
							(int) (Math.random() * (usrs.size() - 1)))
							.getNickname();
					o.setAux_companyManager(user);
					o.setAux_userNameCompanyManager(user);
					smService.createOrganisation(o);
					connectableService.createOrRetrieveScope(o.getName());
					manufacturingProcessService.addCanDoProductService(o, p);

					if (p instanceof SimpleProduct)
						pmsService.updateSimpleProduct((SimpleProduct) p);
					else if (p instanceof ComplexProduct)
						pmsService.updateComplexProduct((ComplexProduct) p);
				}
			}
			// }
		}
	}

	// Do services first!!!
	private void initOrganisations2(List<? extends SupportingService> service) {
		String OrgExtension = " Manufaktur ";
		List<SuddenUser> usrs = userService.retrieveAllUsers();

		for (SupportingService p : service) {
			String prodName = p.getName();
			// no organisatinos that do cardoorwindows - will need updating when
			// setting up demo
			// if (prodName.toLowerCase().indexOf("cardoorwindow") < 0) {
			for (int i = 1; i <= 2; ++i) {
				// this is for multiple org
				if (smService.retrieveOrganisationsByName(
						prodName + OrgExtension + i).size() == 0) {
					// create only once
					Organization o = new Organization();
					o.setName(prodName + OrgExtension + i);

					String user = usrs.get(
							(int) (Math.random() * (usrs.size() - 1)))
							.getNickname();
					o.setAux_companyManager(user);
					o.setAux_userNameCompanyManager(user);
					smService.createOrganisation(o);
					connectableService.createOrRetrieveScope(o.getName());

					pmsService.updateSupportingService(p);
				}
			}
			// }
		}
	}

	// init complex Products, Users, Organizations first!
	private void initCases(int max) {
		// List<SuddenUser> usrs = userService.retrieveAllUsers();
		// List<Organization> orgss = this.smService.retrieveAllOrganisations();
		if (caseService.retrieveAllCaseFiles().size() == 0) {
			List<ComplexProduct> cps = pmsService.retrieveAllComplexProducts();
			if (max <= 0 || max > cps.size())
				max = cps.size();
			for (int i = 0; i < max; ++i) {

				// do only 5 BOs & CaseFiles (at most) & some special ones
				ComplexProduct p = cps.get(i);
				// if(i<5 ||
				// /// add a line to get BO's and CaseFiles for other parts
				// p.getName().toLowerCase().indexOf("mechanik")>-1 ||
				// p.getName().toLowerCase().indexOf("system")>-1 ) {
				// /// add a line to get BO's and CaseFiles for other parts

				// TODO - sort this so that it *does* catch the complex product
				// thing
				if (!p.getName().contains("ComplexPart")) {
					CaseFile cf = new CaseFile();
					BusinessOpportunity bo = new BusinessOpportunity();
					bo
							.setDescription("The innovative Business Opportunity for product:"
									+ p.getName() + "! Let's make it");
					bo.setName("Case:" + p.getName());
					// if (usrs.size() > 0)
					// bo.setBoo(usrs.get((int) (Math.random() * (usrs.size() -
					// 1))));
					// if (orgss.size() > 0)
					// bo.setEndCustomer(orgss.get((int) (Math.random() *
					// (orgss.size() - 1))));
					bo.setGoal(p);
					bo
							.setQuantityPerYearFinalProduct(((int) (Math
									.random() * 100)) * 1000);
					Calendar cal = Calendar.getInstance();
					// SOP is somewhen in the next 5 years;
					long maxYears = 5;
					long maxYearsInMillis = maxYears * 12 * 30 * 24 * 60 * 60
							* 1000l;
					long randomYearsInMillis = Math.round(Math.random()
							* maxYearsInMillis);
					long sop = java.lang.System.currentTimeMillis()
							+ randomYearsInMillis;
					cal.setTimeInMillis(sop);
					bo.setStartOfProduction(cal.getTime());
					caseService.create(bo);
					cf.setBo(bo);
					caseService.create(cf);
					connectableService.createOrRetrieveScope(bo.getName());
				}
			}
		}
	}

	/**
	 * inits test data for PMEnterpriseProfileDesignController
	 */
	@Override
	public void initTest(int width, int depth) {

		List<Material> subs = pmsService.retrieveMaterial("Perform"
				+ testCounter);
		Material m1 = null;
		if (subs.size() == 0) {
			m1 = new Material();
			m1.setName("Perform" + testCounter);
			testCounter++;
			pmsService.createMaterial(m1);
			StringValue sv = connectableService.createOrRetrieveStringValue(m1
					.getName()
					+ "Occurrence");
			connectableService.addOccurrence(m1, "Implementation", sv,
					connectableService.getUnspecifiedScope());

		} else {
			m1 = subs.get(0);
		}
		add(width, depth, m1, connectableService.getUnspecifiedScope());
		pmsService.updateMaterial(m1);
	}

	private void add(int width, int remainingdepth, Material parent, Scope scope) {
		for (int w = 0; w < width; ++w) {
			List<Material> subs = pmsService.retrieveMaterial(parent.getName()
					+ "-" + w);
			Material m2 = null;
			if (subs.size() == 0) {
				m2 = new Material();
				m2.setName(parent.getName() + "-" + w);
				pmsService.createMaterial(m2);
				StringValue sv = connectableService
						.createOrRetrieveStringValue(m2.getName()
								+ "Occurrence");
				connectableService.addOccurrence(m2, "Implementation", sv,
						connectableService.getUnspecifiedScope());
			} else {
				m2 = subs.get(0);
			}
			if (remainingdepth >= 1)
				add(width, remainingdepth - 1, m2, scope);
			pmsService.addChildMaterial(parent, m2, scope);
		}
	}

	/**
	 * inits test data for trees: material, technologies, machines products and
	 * services values
	 */
	private void initMaterialsTechnologiesMachinesValueCreation() {
		OntClass materialOntClass = ontology.getOntClass(Thing.NameSpace
				+ "Material");
		if (materialOntClass != null)
			initMaterials(null, materialOntClass);

		OntClass technologyOntClass = ontology.getOntClass(Thing.NameSpace
				+ "Processing");
		if (technologyOntClass != null)
			initTechnologies(null, technologyOntClass);

		OntClass productOntClass = ontology.getOntClass(Thing.NameSpace
				+ "ValueCreation"); // Part
		// initMachines();
		if (productOntClass != null)
			initProductsServices(null, productOntClass);
	}

	/**
	 * inits materials for material tree
	 */
	private void initMaterials(Material parentMaterial, OntClass ontologyClass) {
		Material m = new Material();
		m.setName(ontologyClass.getLocalName());
		m.setURI(ontologyClass.getURI());
		m = createOrRetrieveM(m);

		if (parentMaterial != null)
			pmsService.addChildMaterial(parentMaterial, m, connectableService
					.getUnspecifiedScope());
		for (Iterator i = ontologyClass.listSubClasses(true); i.hasNext();) {
			OntClass ontClass = (OntClass) i.next();
			if (ontClass != null)
				initMaterials(m, ontClass);
		}
	}

	/**
	 * inits technologies for the technology tree
	 */
	private void initTechnologies(Technology parentTechnology,
			OntClass ontologyClass) {
		Technology t = new Technology();
		t.setName(ontologyClass.getLocalName());
		t.setURI(ontologyClass.getURI());

		t = createOrRetrieveT(t);

		if (parentTechnology != null)
			technologyService.addSub(parentTechnology, t, connectableService
					.getUnspecifiedScope());
		for (Iterator i = ontologyClass.listSubClasses(true); i.hasNext();) {
			OntClass ontClass = (OntClass) i.next();
			if (ontClass != null)
				initTechnologies(t, ontClass);
		}
	}

	private void initAssociations() {

		ExtendedIterator it = ontology.listObjectProperties();

		// AssocType assocTypeSuperSub = new AssocType();
		// assocTypeSuperSub.setType("superSub");
		// connectableService.createAssociationType(assocTypeSuperSub);
		//
		// AssocType assocTypeInstanceOf = new AssocType();
		// assocTypeInstanceOf.setType("instanceOf");
		// connectableService.createAssociationType(assocTypeInstanceOf);

		while (it.hasNext()) {
			ObjectProperty property = (ObjectProperty) it.next();
			AssocType doesitexist = connectableService
					.retrieveAssociationType(property.getLocalName());
			if (doesitexist == null) {
				AssocType assocType = new AssocType();
				assocType.setType(property.getLocalName());
				connectableService.createAssociationType(assocType);
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional(noRollbackFor = UnsupportedOperationException.class)
	private void initIndividuals() {

		ExtendedIterator it = ontology.listIndividuals();

		// Iterate over all Individuals
		while (it.hasNext()) {
			com.hp.hpl.jena.ontology.Individual individual = (com.hp.hpl.jena.ontology.Individual) it
					.next();
			if (individualService.retrieveIndividual(individual.getLocalName()) == null) {
				Individual suddenIndividual = new Individual();
				suddenIndividual.setName(individual.getLocalName());
				individualService.createIndividual(suddenIndividual);
			} else {
				logger.error("Ontology holds this individual twice: "
						+ individual.getLocalName());
			}
		}

		it = ontology.listIndividuals();

		// Iterate over all Individuals
		while (it.hasNext()) {
			com.hp.hpl.jena.ontology.Individual individual = (com.hp.hpl.jena.ontology.Individual) it
					.next();

			// Retrieve this individual from database
			Individual suddenIndividual = individualService
					.retrieveIndividual(individual.getLocalName());

			StmtIterator iterator = individual.listProperties();

			// Iterate over the properties of a individual
			while (iterator.hasNext()) {
				try {
					Statement stmt = (Statement) (iterator.next());

					// get the tail of the association ("range" in OWL)
					String rangeObjectValue = stmt.asTriple().getMatchObject()
							.getLocalName();

					HashMap<String, List<Connectable>> map = new HashMap<String, List<Connectable>>();

					LinkedList list = new LinkedList();

					// add individual to the "head" of the association and store
					// it into list
					list.add(suddenIndividual);
					LinkedList list2 = new LinkedList();

					if (individualService.retrieveIndividual(rangeObjectValue) == null) {

						Thing thing = thingRepository.retrieveByFieldName(
								"name", rangeObjectValue);

						if (thing != null) {

							list2.add(thingRepository.retrieveByFieldName(
									"name", rangeObjectValue));
						} else {
							logger
									.error("initIndividuals: whats up here - didn't find: "
											+ rangeObjectValue);
						}

					} else {
						list2.add(individualService
								.retrieveIndividual(rangeObjectValue));
					}

					if (stmt.asTriple().getMatchPredicate().getLocalName()
							.equals("type")) {
						String accocType = "typeInstance";
						map.put("instance", list);
						map.put("type", list2);

						connectableService.associate(accocType, map,
								connectableService.getUnspecifiedScope());

					} else {

						map.put("domain", list);
						map.put("range", list2);

						if (list.size() == 0 || list2.size() == 0) {
							logger.debug("something wrong");
						}

						logger.debug("associate "
								+ stmt.asTriple().getMatchPredicate()
										.getLocalName() + " " + map);

						connectableService.associate(stmt.asTriple()
								.getMatchPredicate().getLocalName(), map,
								connectableService.getUnspecifiedScope());
					}
				} catch (UnsupportedOperationException ex) {
					logger.error(ex);
				}
			}

		}

	}

	public void initFunctions() {
		OntClass functionOntClass = ontology.getOntClass(Thing.NameSpace
				+ "Function");

		if (functionOntClass != null) {
			ExtendedIterator functionIterator = functionOntClass
					.listSubClasses();
			while (functionIterator.hasNext()) {
				OntClass functionSubClass = (OntClass) functionIterator.next();
				if (thingRepository.retrieveByFieldName("name",
						functionSubClass.getLocalName()) == null) {
					Function function = new Function(functionSubClass
							.getLocalName());
					thingRepository.create(function);
				}
			}
		}

	}

	/**
	 * inits products from the onology ... init Materials first
	 * 
	 * * edited 11/05/2009 by MartinC so that it picks up the subclasses of the
	 * simple products :) - switched to detecting stuff as subclasses of
	 * simple/cim
	 */
	private void initProductsServices(Item parentProduct, OntClass ontologyClass) {

		OntClass complexProductClass = this.ontology
				.getOntClass(Thing.NameSpace + "ComplexPart");
		OntClass simpleProductClass = this.ontology.getOntClass(Thing.NameSpace
				+ "Simple_Part");
		OntClass serviceClass = this.ontology.getOntClass(Thing.NameSpace
				+ "Service");

		Iterator subClassIterator = ontologyClass.listSubClasses();
		while (subClassIterator.hasNext()) {
			OntClass subClass = (OntClass) subClassIterator.next();
			logger.debug(" Considering OWL Class: " + subClass.getLocalName());
			if (subClass.equals(complexProductClass)
					|| (subClass.hasSuperClass(complexProductClass))
					|| (subClass.getSuperClass() != null && subClass
							.getSuperClass().hasSuperClass(complexProductClass))
					|| (subClass.getSuperClass() != null
							&& subClass.getSuperClass().getSuperClass() != null && subClass
							.getSuperClass().getSuperClass().hasSuperClass(
									complexProductClass))
					|| (subClass.getSuperClass() != null
							&& subClass.getSuperClass().getSuperClass() != null
							&& subClass.getSuperClass().getSuperClass()
									.getSuperClass() != null && subClass
							.getSuperClass().getSuperClass().getSuperClass()
							.hasSuperClass(complexProductClass))) {
				logger.debug(" Considering complex product from ontology "
						+ subClass.getLocalName());

				ComplexProduct cp = new ComplexProduct();
				cp.setName(subClass.getLocalName());
				cp.setURI(subClass.getURI());

				cp = createOrRetrieveCP(cp);
				if (parentProduct != null && parentProduct instanceof Product) {
					// pmsService.addPart(whole, part, scope)
					pmsService.addChildProduct((Product) parentProduct, cp,
							connectableService.getUnspecifiedScope());
				}

				initProductsServices(cp, subClass);

			} else if (subClass.equals(simpleProductClass)
					|| (subClass.hasSuperClass(simpleProductClass))
					|| (subClass.getSuperClass() != null && subClass
							.getSuperClass().hasSuperClass(simpleProductClass))
					|| (subClass.getSuperClass() != null
							&& subClass.getSuperClass().getSuperClass() != null && subClass
							.getSuperClass().getSuperClass().hasSuperClass(
									simpleProductClass))
					|| (subClass.getSuperClass() != null
							&& subClass.getSuperClass().getSuperClass() != null
							&& subClass.getSuperClass().getSuperClass()
									.getSuperClass() != null && subClass
							.getSuperClass().getSuperClass().getSuperClass()
							.hasSuperClass(simpleProductClass))) {
				logger.debug(" Considering simple product from ontology "
						+ subClass.getLocalName());
				SimpleProduct sp = new SimpleProduct();
				sp.setName(subClass.getLocalName());
				sp.setURI(subClass.getURI());
				sp = createOrRetrieveSP(sp);
				// pmsService.updateSimpleProduct(sp);
				if (parentProduct != null && parentProduct instanceof Product) {
					pmsService.addChildProduct((Product) parentProduct, sp,
							connectableService.getUnspecifiedScope());
				}

				initProductsServices(sp, subClass);

			} else if (subClass.equals(serviceClass)
					|| (subClass.hasSuperClass(serviceClass))
					|| (subClass.getSuperClass() != null && subClass
							.getSuperClass().hasSuperClass(serviceClass))
					|| (subClass.getSuperClass() != null
							&& subClass.getSuperClass().getSuperClass() != null && subClass
							.getSuperClass().getSuperClass().hasSuperClass(
									serviceClass))
					|| (subClass.getSuperClass() != null
							&& subClass.getSuperClass().getSuperClass() != null
							&& subClass.getSuperClass().getSuperClass()
									.getSuperClass() != null && subClass
							.getSuperClass().getSuperClass().getSuperClass()
							.hasSuperClass(serviceClass))) {
				logger.debug(" Considering service from ontology "
						+ subClass.getLocalName());
				SupportingService sp = new SupportingService();
				sp.setName(subClass.getLocalName());
				sp.setURI(subClass.getURI());
				sp = createOrRetrieveServ(sp);
				if (parentProduct != null
						&& parentProduct instanceof SupportingService) {
					pmsService.addChildService(
							(SupportingService) parentProduct, sp,
							connectableService.getUnspecifiedScope());
				}
				initProductsServices(sp, subClass);

			} else
				initProductsServices(null, subClass);
			// TODO - does it work?
		}

		// Product p = new Product();
		// p.setName(ontologyClass.getLocalName());
		// p = createOrRetrieveCP();
		//
		// if (parentTechnology != null)
		// technologyService.addSub(parentTechnology, t,
		// connectableService.getUnspecifiedScope());
		// for (Iterator i = ontologyClass.listSubClasses(true); i.hasNext();) {
		// OntClass ontClass = (OntClass) i.next();
		// initTechnologies(t, ontClass);
		// }
	}

	private void initMaterialsForProducts(OntClass productOntClass) {
		if (productOntClass == null)
			productOntClass = ontology.getOntClass(Thing.NameSpace + "Part");
		if (productOntClass != null) {
			Iterator subClassIterator = productOntClass.listSubClasses();
			while (subClassIterator.hasNext()) {
				OntClass subClass = (OntClass) subClassIterator.next();
				if ((subClass.getLocalName()).equals("Simple_Part")) {
					Iterator subSubClassIterator = subClass.listSubClasses();
					while (subSubClassIterator.hasNext()) {
						OntClass subSubClass = (OntClass) subSubClassIterator
								.next();

						List<SimpleProduct> sps = pmsService
								.retrieveSimpleProduct(subSubClass
										.getLocalName());
						for (SimpleProduct sp : sps) {
							Property madeOf = ontology
									.getProperty(Thing.NameSpace
											+ "MadeOfMaterial");
							ExtendedIterator it = ontology
									.listIndividuals(subSubClass);
							while (it.hasNext()) {
								IndividualImpl i = (IndividualImpl) it.next();
								ExtendedIterator it2 = i.listProperties(madeOf);
								while (it2.hasNext()) {
									StatementImpl x = (StatementImpl) it2
											.next();
									Resource node = (Resource) x.getObject();
									String classname = ontology.getOntResource(
											node).getRDFType().getLocalName();
									List<Material> mats = pmsService
											.retrieveMaterial(classname);
									if (mats != null && mats.size() > 0) {
										logger.debug("Associate SimpleProd "
												+ sp.getName()
												+ "  with Material "
												+ classname);
										pmsService.addMaterial(sp, mats.get(0),
												connectableService
														.getUnspecifiedScope());
									}
								}
							}
							pmsService.updateSimpleProduct(sp);
						}
						for (Iterator i = subSubClass.listSubClasses(true); i
								.hasNext();) {
							OntClass ontClass = (OntClass) i.next();
							initMaterialsForProducts(ontClass);
						}
					}
				}
			}
		}
	}

	/**
	 * creates Material in DB if not exists and returns existing or created
	 * material
	 * 
	 * @param Material
	 *            m
	 * @return Material m
	 */
	private Material createOrRetrieveM(Material m) {
		if (pmsService.retrieveMaterial(m.getName()).isEmpty()) {
			pmsService.createMaterial(m);
		} else {
			m = pmsService.retrieveMaterial(m.getName()).get(0);
		}
		return m;
	}

	//
	// /**
	// * inits technologies for technology tree
	// */
	// private void initTechnologies() {
	// Technology t1 = new Technology("cutting");
	// Technology t1_1 = new Technology("hotcutting");
	// Technology t1_2 = new Technology("coldcutting");
	// Technology t2 = new Technology("pressing");
	// Technology t3 = new Technology("stamping");
	//
	// // create in DB
	// t1 = createOrRetrieveT(t1);
	// t1_1 = createOrRetrieveT(t1_1);
	// t1_2 = createOrRetrieveT(t1_2);
	// t2 = createOrRetrieveT(t2);
	// t3 = createOrRetrieveT(t3);
	//
	// // associate
	// HashSet<Technology> subTechnologies = new HashSet<Technology>();
	// subTechnologies.add(t1_1);
	// subTechnologies.add(t1_2);
	// technologyService.addSub(t1, subTechnologies,
	// connectableService.getUnspecifiedScope());
	// logger.debug("Technology created  HSQLDBCOUNT:" +
	// HsqldbDataSource.COUNT);
	//
	// }

	/**
	 * creates Technology in DB if not exists and returns existing or created
	 * technology
	 * 
	 * @param Technology
	 *            t
	 * @return Technology t
	 */
	private Technology createOrRetrieveT(Technology t) {
		if (technologyService.retrieveTechnology(t.getName()) == null) {
			technologyService.createTechnology(t);
		} else {
			t = technologyService.retrieveTechnology(t.getName());
		}
		return t;
	}

	/**
	 * inits Machines for machine tree
	 */
	private void initMachines() {
		Machine m = new Machine("CUTTER2000");
		Machine m1 = new Machine("PRESSER4000");
		Machine m2 = new Machine("STAMPER6000");

		m = createOrRetrieveMachine(m);
		m1 = createOrRetrieveMachine(m1);
		m2 = createOrRetrieveMachine(m2);
		logger.debug("Machies created  HSQLDBCOUNT:" + HsqldbDataSource.COUNT);

	}

	/**
	 * creates machine in DB if not exists and returns existing or created
	 * machine
	 * 
	 * @param Machine
	 *            t
	 * @return Machine t
	 */
	private Machine createOrRetrieveMachine(Machine t) {
		if (machineService.retrieveMachine(t.getName()) == null) {
			machineService.createMachine(t);
		} else {
			t = machineService.retrieveMachine(t.getName());
		}
		return t;
	}

	/**
	 * inits prodValues for tree
	 */
	private void initComplexProducts() {
		// COMPLEXPRODUCTS
		ComplexProduct cp1 = new ComplexProduct();
		cp1.setName("Complete vehicles");

		ComplexProduct cp1_l1 = new ComplexProduct();
		cp1_l1.setName("Bodies and essential parts");

		ComplexProduct cp1_l1_1 = new ComplexProduct();
		cp1_l1_1.setName("Structural elements; primary parts; body in white");

		ComplexProduct cp1_l1_2 = new ComplexProduct();
		cp1_l1_2.setName("Outer skin; ferrous metal deep drawn parts");

		ComplexProduct cp1_l1_3 = new ComplexProduct();
		cp1_l1_3.setName("Outer skin; non-ferrous metal deep drawn parts");

		ComplexProduct cp1_l1_4 = new ComplexProduct();
		cp1_l1_4.setName("Hydro-formed supports and elements");

		// createOr retrieve in DB
		cp1 = createOrRetrieveCP(cp1);
		cp1_l1 = createOrRetrieveCP(cp1_l1);
		cp1_l1_1 = createOrRetrieveCP(cp1_l1_1);
		cp1_l1_2 = createOrRetrieveCP(cp1_l1_2);
		cp1_l1_3 = createOrRetrieveCP(cp1_l1_3);
		cp1_l1_4 = createOrRetrieveCP(cp1_l1_4);

		// associate these values
		pmsService.addPart(cp1, cp1_l1, connectableService
				.getUnspecifiedScope());
		pmsService.addPart(cp1_l1, cp1_l1_1, connectableService
				.getUnspecifiedScope());
		pmsService.addPart(cp1_l1, cp1_l1_2, connectableService
				.getUnspecifiedScope());
		pmsService.addPart(cp1_l1, cp1_l1_3, connectableService
				.getUnspecifiedScope());
		pmsService.addPart(cp1_l1, cp1_l1_4, connectableService
				.getUnspecifiedScope());
	}

	/**
	 * creates complex product in DB if not exists and returns existing or
	 * created cp
	 * 
	 * @param ComplexProduct
	 *            cp
	 * @return ComplexProduct cp
	 */
	private ComplexProduct createOrRetrieveCP(ComplexProduct cp) {
		if (pmsService.retrieveComplexProduct(cp.getName()).isEmpty()) {
			pmsService.createComplexProduct(cp);
		} else {
			cp = pmsService.retrieveComplexProduct(cp.getName()).get(0);
		}
		return cp;
	}

	private SimpleProduct createOrRetrieveSP(SimpleProduct sp) {
		if (pmsService.retrieveSimpleProduct(sp.getName()).isEmpty()) {
			pmsService.createSimpleProduct(sp);
		} else {
			sp = pmsService.retrieveSimpleProduct(sp.getName()).get(0);
		}
		return sp;
	}

	private SupportingService createOrRetrieveServ(SupportingService sp) {
		if (pmsService.retrieveSupportingService(sp.getName()).isEmpty()) {
			pmsService.createSupportingService(sp);
		} else {
			sp = pmsService.retrieveSupportingService(sp.getName()).get(0);
		}
		return sp;
	}

	private Process createOrRetrieveProcess(Process process) {
		if (smService.retrieveProcessBy(process.getName()).isEmpty()) {
			// logger.debug("createProcessIdentifier:  "+
			// smService.createProcess(process));
			smService.createProcess(process);
			logger.debug("Process created!!");
		} else {
			process = smService.retrieveProcessBy(process.getName()).get(0);

			logger.debug("Process retrieved");
		}
		return process;
	}

	// // 4 testing reasons just valid for one call; trows duplicate entry
	// errors
	// // id Process are created more than once
	public void initProcesses() {
		logger.debug("KDMServiceImpl!!");
		// create hierarchy in Topic Map
		// move to KdmService
		Process pRoot = new Process();
		pRoot.setName("Root Process");

		Process child1 = new Process();
		child1.setName("child1");

		Process child1_1 = new Process();
		child1_1.setName("child1_1");

		Process child2 = new Process();
		child2.setName("child2");

		pRoot = createOrRetrieveProcess(pRoot);
		child1 = createOrRetrieveProcess(child1);
		child1_1 = createOrRetrieveProcess(child1_1);
		child2 = createOrRetrieveProcess(child2);

		connectableService.associateTwoConnectables(pRoot, child1, "superSub",
				"super", "sub", connectableService.getUnspecifiedScope());
		connectableService.associateTwoConnectables(child1, child1_1,
				"superSub", "super", "sub", connectableService
						.getUnspecifiedScope());
		connectableService.associateTwoConnectables(pRoot, child2, "superSub",
				"super", "sub", connectableService.getUnspecifiedScope());
		logger
				.debug("Processes created  HSQLDBCOUNT:"
						+ HsqldbDataSource.COUNT);
	}

	@Override
	public Tree retrieveTree(ScopeController scopeC) {
		Tree result = this.connectableTrees.get(scopeC.getUserScope());
		if (result == null) {
			result = new Tree(getConnectableService(), scopeC, "Connectables");
			this.connectableTrees.put(scopeC.getUserScope(), result);
			logger.warn("new retrieveTree");
		}
		// logger.warn("retrieveTree: " + result);
		return result;
	}

	@Override
	// FIXME: heavy performance impact
	public Tree retrieveTree(ScopeController scopeController,
			List<Connectable> conns, int showTreeDepth,
			boolean showassociations, boolean showoccurrences) {

		Tree connectableTree = retrieveTree(scopeController);

		connectableTree.setAlsoCreateAssociationNodes(showassociations);
		connectableTree.setAlsoShowOccurrence(showoccurrences);
		connectableTree.setAssociationScope(scopeController.getUserScope());
		connectableTree.setOccurrenceScope(scopeController.getUserScope());

		// connectableTree.removeWhenInRole(conns, new String[]{Material.SUB,
		// Product.SUB, ComplexProduct.SUB,
		// Technology.SUB},scopeController.getUserScope());
		// be more selective what is added in conns!!!
		// connectableTree.removeWhenInRole(conns, new String[] { Product.SUB,
		// ComplexProduct.SUB }, scopeController
		// .getUserScope());
		// connectableTree.removeWhenInDifferentRoles(conns,
		// scopeController.getUserScope());

		connectableTree.retrieveNodes(showTreeDepth, conns, null);

		logger.warn("retrieveTree  list: " + conns.size());
		return connectableTree;
	}

	@Override
	public ActorService getActorService() {
		return this.actorService;
	}

	@Override
	public ServiceManagementService getServiceManagementService() {
		return this.smService;
	}

	@Override
	public ConnectableService getConnectableService() {
		return this.connectableService;
	}

	@Override
	public EnterpriseEvaluationService getEnterpriseEvaluationService() {
		return this.enterpriseEvaluationService;
	}

	@Override
	public NetworkEvaluationService getNetworkEvaluationService() {
		return this.networkEvaluationService;
	}

	@Override
	public ProductMaterialSupportingServices_Service getProductMaterialSupportingServices_Service() {
		return this.pmsService;
	}

	@Override
	public void setMachineService(MachineryType_Service machineService) {
		this.machineService = machineService;
	}

	@Override
	public UserService getUserService() {
		return this.userService;
	}

	public CPSendMessageService getSendMessageService() {
		return sendMessageService;
	}

	public void setSendMessageService(CPSendMessageService sendMessageService) {
		this.sendMessageService = sendMessageService;
	}

	@Override
	public CaseService getCaseService() {
		return this.caseService;
	}

	@Override
	public void setTechnologyService(TechnologyService technologyService) {
		this.technologyService = technologyService;
	}

	@Override
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;

	}

	@Override
	public void setServiceManagementService(ServiceManagementService baseService) {
		this.smService = baseService;

	}

	@Override
	public void setConnectableService(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

	@Override
	public void setEnterpriseEvaluationService(
			EnterpriseEvaluationService enterpriseEvaluationService) {
		this.enterpriseEvaluationService = enterpriseEvaluationService;
	}

	@Override
	public void setNetworkEvaluationService(
			NetworkEvaluationService networkEvaluationService) {
		this.networkEvaluationService = networkEvaluationService;
	}

	@Override
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void setCaseService(CaseService caseService) {
		this.caseService = caseService;
	}

	@Override
	public void setProductMaterialSupportingServices_Service(
			ProductMaterialSupportingServices_Service pmsService) {
		this.pmsService = pmsService;
	}

	@Override
	public void setManufacturingProcessService(
			ManufacturingProcessType_Service manufacturingProcessService) {
		this.manufacturingProcessService = manufacturingProcessService;
	}

	@Override
	public TechnologyService getTechnologyService() {
		return technologyService;
	}

	@Override
	public MachineryType_Service getMachineService() {
		return machineService;
	}

	public ServiceManagementService getSmService() {
		return smService;
	}

	public ScopeService getScopeService() {
		return scopeService;
	}

	public void setScopeService(ScopeService scopeService) {
		this.scopeService = scopeService;
	}

	@Override
	public void setOrganisationService(IOrganization s) {
		this.organisationService = s;
	}

	@Override
	public IOrganization setOrganisationService() {
		return this.organisationService;
	}

	@Override
	public void setCmInstancesService(
			ICMInstancesManagement_Service cmInstancesService) {
		this.cmInstancesService = cmInstancesService;
	}

	@Override
	public ICMInstancesManagement_Service getCmInstancesService() {
		return this.cmInstancesService;
	}

	@Override
	public void setCmManagementService(
			ICMCompetencesManagement_Service cmService) {
		this.cmService = cmService;
	}

	@Override
	public ICMCompetencesManagement_Service getCmManagementService() {
		return this.cmService;
	}

	public ProductMaterialSupportingServices_Service getPmsService() {
		return pmsService;
	}

	public CPBulletinBoardService getBulletinBoardService() {
		return bulletinBoardService;
	}

	public void setBulletinBoardService(
			CPBulletinBoardService bulletinBoardService) {
		this.bulletinBoardService = bulletinBoardService;
	}

	@Override
	public Organization retrieveOrganization(Long id) {
		return organisationService.retrieveOrganization(id);
	}

	@Override
	public Supplier retrieveSupplier(Long id) {
		return organisationService.retrieveSupplier(id);
	}

	@Override
	public void deleteOrganization(Organization s) {
		organisationService.deleteOrganization(s);
	}

	@Override
	public void deleteSupplier(Supplier s) {
		organisationService.deleteSupplier(s);
	}

	@Override
	public void updateCreateOrganization(Organization s) {
		organisationService.updateCreateOrganization(s);
	}

	@Override
	public void updateCreateSupplier(Supplier s) {
		organisationService.updateCreateSupplier(s);
	}

	public StringBuffer getDebugMsg() {
		return this.DEBUG_MSG;
	}

	public void setDebugMsg(StringBuffer msg) {
		this.DEBUG_MSG = msg;
	}

}
/**
 * creates processes for TreeDragDrop private void
 * createProcessesForTreeDragDrop(){ Process pRoot = new Process();
 * pRoot.setName("Root Process"); Process child1 = new Process();
 * child1.setName("child1"); Process child1_1 = new Process();
 * child1_1.setName("child1_1"); Process child2 = new Process();
 * child2.setName("child2"); pRoot = createOrRetrieveProcess(pRoot); child1 =
 * createOrRetrieveProcess(child1); child1_1 =
 * createOrRetrieveProcess(child1_1); child2 = createOrRetrieveProcess(child2);
 * }
 */
/**
 * creates processes in DB if not existing
 * 
 * @param Process
 *            process
 * @return Process process private Process createOrRetrieveProcess(Process
 *         process) { if
 *         (smService.retrieveProcessBy(process.getName()).isEmpty()) { //
 *         logger.debug("createProcessIdentifier:  "+
 *         smService.createProcess(process)); smService.createProcess(process);
 *         logger.debug("Process created!!"); } else { process =
 *         smService.retrieveProcessBy(process.getName()).get(0);
 *         logger.debug("Process retrieved"); } return process; }
 */

/**
 * creates processes for CreateProcessJSFController private void
 * createProcesses(){ Process p = new Process(); p.setName("cutting"); Process
 * p1 = new Process(); p1.setName("hotcutting"); //create in db
 * smService.createProcess(p); smService.createProcess(p1); }
 */

