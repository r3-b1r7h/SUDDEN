package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent;

import jade.content.ContentManager;
import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsContentElement;
import jade.content.abs.AbsPrimitive;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Equals;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeBoardAgent.NoticeBoardAgent;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.DeclaredCapability;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PartialSolution;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.ResourceSet;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.NoticeboardCombinedOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.SuddenProductOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.CompiledIRE;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.CompiledIREFactory;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.NoMatchesLocatedException;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.TooManyMatchesForIOTAException;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.designCoordination.teamFormation.overallControl.TeamFormationControllerImpl;
import biz.sudden.designCoordination.teamFormation.service.AgentCommunicationObject;
import biz.sudden.designCoordination.teamFormation.service.CreateNoticeboardCommunicationObject;
import biz.sudden.designCoordination.teamFormation.service.JadeService;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

/**
 * 
 * @author mcassmc
 * 
 *         Two main behaviours involved here: (1) Creating new noticeboards (2)
 *         Acting as a specalised DF for the noticeboards (a) via direct queries
 *         (b) via subscriptions
 * 
 *         It needs to be passed the ontology to be used for describing products
 *         in the system when it is created.
 * 
 *         See the documentation for a fuller description.
 * 
 */

// TODO - removing subscriptions?
public class NoticeBoardFactoryAgent extends Agent {

	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(this.getClass());

	protected CompiledIREFactory noticeBoardQueryFactory;

	// Holds the noticeboard descriptions, with the reference being the time
	// they
	// were created
	protected LinkedList<AbsConcept> NoticeboardDescriptions;

	// Subcscriptions - refferenced by the compiled IRE's with the subsequent
	// message afterwards.
	private Hashtable<CompiledIRE, ACLMessage> subscriptions;

	private Queue<CreateNoticeboardCommunicationObject> noticeBoardCreationRequests = new ConcurrentLinkedQueue<CreateNoticeboardCommunicationObject>();
	private Set<AID> managedAgents = new HashSet<AID>();

	// The ontology describing the products you're talking about. Needed for
	// answerign queries
	// in a vaguely intelligent manner.
	private Ontology productOntology;

	private TeamFormationControllerImpl tfController;

	// For use when sending stuff from Spring to the agent
	private List<AgentCommunicationObject> incommingObjectQueue = null;

	// JadeService for Spring integration
	protected JadeService jadeService;

	@Override
	protected void setup() {
		// TODO - register with the DF I suppose
		// Note that this is the ONLY agent within the system which should do
		// so.

		ContentManager cm = this.getContentManager();
		cm.registerLanguage(new SLCodec());
		cm.registerOntology(NoticeboardCombinedOntology.getInstance());

		productOntology = (Ontology) getArguments()[0];
		tfController = (TeamFormationControllerImpl) getArguments()[1];
		jadeService = (JadeService) getArguments()[2];

		noticeBoardQueryFactory = new CompiledIREFactory(
				new NoticeboardFactoryQueryFactory(productOntology));

		// In general not a lot to do here except for adding the behaviours in &
		// initialising the noticeboard description store.
		NoticeboardDescriptions = new LinkedList<AbsConcept>();

		this.subscriptions = new Hashtable<CompiledIRE, ACLMessage>();

		addBehaviour(new ReceiveMessage(this));
		addBehaviour(new BatchNoticeBoardCreationBehaviour(this));

		// enables an object queue which is used for communication with legacy
		// systems, i.e. applications outside
		// the agent world.
		setEnabledO2ACommunication(true, 0);

	}

	/**
	 * A rather temporary method this one
	 */

	private void createNoticeboard(CaseFile caseFileToChange,
			ConcreteSupplyNetwork CSNtoUpdate, ASNRoleNode materialToConstruct) {

		logger.debug(" Creating noticeboard for " + caseFileToChange + ", "
				+ CSNtoUpdate + ", " + materialToConstruct);

		BusinessOpportunity boToDo = caseFileToChange.getBo();
		AbsConcept noticeboardDescription = new AbsConcept(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION);
		AbsConcept noticeboardInitialProcess = new AbsConcept(
				CreateNoticeboardOntology.CREATE_NOTICEBOARD_INITIAL_PROCESS);

		/*
		 * So what are we making?
		 * 
		 * TODO - this isn't ideal but seemingly the case file isn't sending me
		 * on the final product anyway? Or maybe my method for getting it is
		 * broken. Not a hugely relevant issue for these demos anyway.
		 */
		String productType = caseFileToChange.getName();
		String caseFileName = caseFileToChange.getName();

		/*
		 * First complete the description
		 */
		// Hum not very safe this! Mind you if it works currently it's hardly
		// likely
		// to break in the near future
		Integer timeOfCreation = new Integer(new Long((int) System
				.currentTimeMillis()).intValue());
		String localNoticeboardName = "NoticeBoard_number_" + productType + "_"
				+ caseFileName + "_" + timeOfCreation.toString();
		AID noticeboardAID = new AID(localNoticeboardName, AID.ISLOCALNAME);

		// Noticeboard Utility Info - take from the business opp not the case
		// file!
		// TODO - well yes you'd rather have actual data here! But hey :)
		AbsConcept noticeboardUtility = new AbsConcept(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_UTILITY);

		AbsConcept rangeRequired = new AbsConcept(
				CreateNoticeboardOntology.RANGE);
		rangeRequired.set(CreateNoticeboardOntology.RANGE_MIN, 1);
		Integer quant = boToDo.getQuantityPerYearFinalProduct();
		if (quant != null)
			rangeRequired.set(CreateNoticeboardOntology.RANGE_MAX, quant);
		else
			rangeRequired.set(CreateNoticeboardOntology.RANGE_MAX, new Integer(
					10000));
		noticeboardUtility.set(
				CreateNoticeboardOntology.NOTICEBOARD_UTILITY_AMOUNT_REQUIRED,
				rangeRequired);

		if (boToDo.getEndCustomerName() != null) {
			noticeboardUtility
					.set(
							CreateNoticeboardOntology.NOTICEBOARD_UTILITY_COMPANY_REQUESTING_SERVICE,
							boToDo.getEndCustomerName());
		} else {
			noticeboardUtility
					.set(
							CreateNoticeboardOntology.NOTICEBOARD_UTILITY_COMPANY_REQUESTING_SERVICE,
							"unknown supplier");
		}

		if (boToDo.getStartOfProduction() != null) {
			noticeboardUtility
					.set(
							CreateNoticeboardOntology.NOTICEBOARD_UTILITY_START_OF_PRODUCTION,
							boToDo.getStartOfProduction());
		} else {
			noticeboardUtility
					.set(
							CreateNoticeboardOntology.NOTICEBOARD_UTILITY_START_OF_PRODUCTION,
							new Date());
		}

		noticeboardUtility.set(
				CreateNoticeboardOntology.NOTICEBOARD_UTILITY_EXPECTED_COST,
				99999999);

		noticeboardDescription.set(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_UTILITY,
				noticeboardUtility);

		// A sad neccesity
		AbsConcept absAID = new AbsConcept(SL0Vocabulary.AID);
		absAID.set(SL0Vocabulary.AID_NAME, noticeboardAID.getName());

		String tempAddress;
		AbsAggregate addresses = new AbsAggregate(SL0Vocabulary.SEQUENCE);
		Iterator overAddresses = noticeboardAID.getAllAddresses();
		while (overAddresses.hasNext()) {
			tempAddress = (String) overAddresses.next();
			addresses.add(AbsPrimitive.wrap(tempAddress));
		}

		absAID.set(SL0Vocabulary.AID_ADDRESSES, addresses);

		noticeboardDescription.set(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_TIME_CREATED,
				AbsPrimitive.wrap(timeOfCreation.intValue()));
		noticeboardDescription.set(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_ADDRESS,
				absAID);

		// Initial state. Currently totally empty.
		AbsConcept initialState = new AbsConcept(ProcessOntology.STATE);
		AbsAggregate initialResources = new AbsAggregate(SL0Vocabulary.SET);
		initialState.set(ProcessOntology.STATE_RESOURCES_CONSTRAINED,
				initialResources);

		noticeboardInitialProcess.set(
				CreateNoticeboardOntology.INITIAL_PROCESS_INITIAL_STATE,
				initialState);
		noticeboardDescription
				.set(
						CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_INITIAL_STATE,
						initialState);

		// Goal state - here we need to fetch the product type out of the SUDDEN
		// ontology

		AbsConcept goalState = new AbsConcept(ProcessOntology.STATE);
		AbsAggregate goalResources = new AbsAggregate(SL0Vocabulary.SET);

		// Ah ok. Well wouldn't it be grand to actually store something here
		// then?
		// What precisely was I expecting to happen?! Never mind :)
		String productName = materialToConstruct.getQualificationProfile()
				.getFirstProductName();

		logger.debug(" ******* looking for product of type " + productName);

		AbsConcept ConstrainedResourceSet = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		ConstrainedResourceSet.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY, AbsPrimitive
						.wrap(1));
		ConstrainedResourceSet.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE, new AbsConcept(
						productName));

		goalResources.add(ConstrainedResourceSet);

		goalState.set(ProcessOntology.STATE_RESOURCES_CONSTRAINED,
				goalResources);

		noticeboardInitialProcess
				.set(CreateNoticeboardOntology.INITIAL_PROCESS_GOAL_STATE,
						goalState);
		noticeboardDescription.set(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_GOAL_STATE,
				goalState);

		noticeboardInitialProcess
				.set(
						CreateNoticeboardOntology.INITIAL_PROCESS_INTERMEDIATE_STATES_WITH_ORDER,
						new AbsAggregate(SL0Vocabulary.SET));
		noticeboardInitialProcess
				.set(
						CreateNoticeboardOntology.INITIAL_PROCESS_STARTING_DECLARED_CAPABILITIES,
						new AbsAggregate(SL0Vocabulary.SET));

		/*
		 * Next store it
		 */
		this.NoticeboardDescriptions.add(noticeboardDescription);

		logger.debug(" Creating new noticeboard with description "
				+ noticeboardDescription);
		logger.debug(" And initial process " + noticeboardInitialProcess);

		/*
		 * Now create a corresponding agent. This will currently always put the
		 * noticeboard on the same platform as the factory.
		 */
		AgentContainer ac = this.getContainerController();
		Object[] agentFile = new Object[6];
		// Feed in the noticeboard description to start things off
		// + also now all the excess Java concepts from above!
		agentFile[0] = noticeboardDescription;
		agentFile[1] = noticeboardInitialProcess;
		agentFile[2] = caseFileToChange;
		agentFile[3] = CSNtoUpdate;
		agentFile[4] = materialToConstruct;

		// new param for NBA
		agentFile[5] = this;
		AgentController newNoticeboardAgent = jadeService.startAgent(
				localNoticeboardName,
				NoticeBoardAgent.class.getCanonicalName(), agentFile);

		// Wait until the new agent becomes idle
		try {
			while (newNoticeboardAgent.getState().getCode() != jade.wrapper.AgentState.cAGENT_STATE_IDLE) {
			}
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

		/*
		 * Now trigger the subscriptions to see if we get a match
		 */
		checkSubscriptionsAgainst(noticeboardDescription);

	}

	/**
	 * @author mcassmc
	 * 
	 *         Responsible for catching incoming 'fresh' messages - ie those
	 *         which initiate various behaviours as opposed to those generated
	 *         as a result of protocols.
	 */
	protected class ReceiveMessage extends CyclicBehaviour {

		public ReceiveMessage(NoticeBoardFactoryAgent agent) {
			super(agent);
		}

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {

			// Check the object queue
			checkObjectQueue();

			// Set the templates up - one template is non trivial, the other two
			// simply catch all queries/subscription requests
			// It seems safe to assume what all queries & subscriptions will do,
			// whereas the range of requests could easily be extended
			IsCreateNoticeboardRequest ICNR = new IsCreateNoticeboardRequest(
					getContentManager(), getAID());
			MessageTemplate IsQuery = MessageTemplate
					.MatchPerformative(ACLMessage.QUERY_REF);
			MessageTemplate IsSubscriptionRequest = MessageTemplate
					.MatchPerformative(ACLMessage.SUBSCRIBE);

			ACLMessage createNoticeBoardRequest = myAgent
					.receive(new MessageTemplate(ICNR));

			/*
			 * Succesively check for each of the message templates being matched
			 */

			if (createNoticeBoardRequest != null) {
				// FIXME! is this ever called ?? check !!! it seems to be broken
				// and the
				// createNoticeboard method above is used !!
				addBehaviour(new CreateNoticeBoardBehaviour(
						(NoticeBoardFactoryAgent) myAgent,
						createNoticeBoardRequest));
			} else {
				ACLMessage queryMessage = myAgent.receive(IsQuery);
				if (queryMessage != null) {
					addBehaviour(new AnswerQueryForNoticeboardsBehaviour(
							myAgent, queryMessage));
				} else {
					ACLMessage subscriptionMessage = myAgent
							.receive(IsSubscriptionRequest);
					if (subscriptionMessage != null) {
						addBehaviour(new AnswerSubscriptionQueryBehaviour(
								(NoticeBoardFactoryAgent) myAgent,
								subscriptionMessage));
					} else {
						// Sadly can't return a not understood here - other
						// behaviours in
						// the agent
						// might need to access the message and we can't control
						// the
						// precedence
						block();
						return;
					}
				}

			}

		}
	}

	private class BatchNoticeBoardCreationBehaviour extends CyclicBehaviour {

		public BatchNoticeBoardCreationBehaviour(NoticeBoardFactoryAgent myAgent) {
			super(myAgent);
		}

		@Override
		public void action() {

			// This solution seems a bit naive, but at least it diminishes the
			// level
			// of concurrency in the system. The idea is:
			// Start a new NoticeBoardAgent only when the managed agents
			// are idle.
			/*
			 * if (managedAgents.size() == 0) { return; }
			 */

			// Verify that all managed agents are in idle state.
			/*
			 * for (AID aid : managedAgents) { try { AgentController controller
			 * = myAgent.getContainerController().getAgent(aid.getName()); if
			 * (controller.getState().getCode() != AgentState.cAGENT_STATE_IDLE)
			 * { return; } } catch (ControllerException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); return; } }
			 */

			// All managed agents are in idle state. Extract a new
			// NoticeBoardCreation request
			CreateNoticeboardCommunicationObject request = noticeBoardCreationRequests
					.poll();
			if (request != null) {
				createNoticeboard(request.getCaseFile(), request.getCSN(),
						request.getRoleNode());
			}
		}
	}

	/**
	 * A set of inner classes providing the message templates for filtering the
	 * message types
	 */
	// A very handy idea borrowed from Sven - thanks :)
	private class IsCreateNoticeboardRequest implements
			MessageTemplate.MatchExpression {
		private static final long serialVersionUID = 1L;
		private ContentManager contentManager;
		private AID myAgentsAID;

		public IsCreateNoticeboardRequest(ContentManager cm, AID myAgentsAID) {
			this.contentManager = cm;
			this.myAgentsAID = myAgentsAID;
		}

		public boolean match(ACLMessage messageIn) {
			boolean result = true;

			AbsContentElement messageContent = null;
			try {
				messageContent = contentManager.extractAbsContent(messageIn);
			} catch (CodecException e) {
				e.printStackTrace();
			} catch (OntologyException e) {
				e.printStackTrace();
			}

			AbsConcept aidRequested = (AbsConcept) messageContent
					.getAbsObject(SL0Vocabulary.ACTION_ACTOR);
			AbsConcept actionRequested = (AbsConcept) messageContent
					.getAbsObject(SL0Vocabulary.ACTION_ACTION);

			// If the protocols had names that could also be checked (but it
			// doesn't
			// seem important to do so at the moment.).
			result &= (messageIn.getPerformative() == ACLMessage.REQUEST);
			result &= (messageContent.getTypeName()
					.equals(SL0Vocabulary.ACTION));
			if (result && (aidRequested != null) && (actionRequested != null)) {
				result &= (aidRequested.getString(SL0Vocabulary.AID_NAME)
						.equals(myAgentsAID.getLocalName()));
				result &= (actionRequested.getTypeName()
						.equals(CreateNoticeboardOntology.CREATE_NOTICEBOARD));
			}

			return result;
		}

	}

	// Methods from here on down
	protected void addSubscription(CompiledIRE cIRE, ACLMessage m) {
		this.subscriptions.put(cIRE, m);
		// Also, add the sender as a managed agent
		managedAgents.add(m.getSender());
	}

	/**
	 * This method triggers a check of a given notice board description vs the
	 * set of subscriptions & informs for matches.
	 */
	@SuppressWarnings("unchecked")
	protected void checkSubscriptionsAgainst(AbsConcept NewNBDescription) {

		logger.debug(" Checking subscriptions vs new noticeboard "
				+ NewNBDescription);
		// Silly I know
		Collection<AbsConcept> NBDesc = new LinkedList<AbsConcept>();
		NBDesc.add(NewNBDescription);

		Collection<AbsConcept> tempMatches;

		// Check each subscription
		for (CompiledIRE ireToCheck : subscriptions.keySet()) {
			try {
				tempMatches = ireToCheck.findMatches(NBDesc);
				logger.debug(" Matches found to ire " + ireToCheck + " are "
						+ tempMatches);
				if (tempMatches.size() > 0) {
					notifyOfMatches(tempMatches, ireToCheck);
				}
			}
			// Ho hum - not relevant at all here! Checking for one match at a
			// time.
			// If we move to say a timed checker then this would obviously be
			// useful
			// again.
			catch (TooManyMatchesForIOTAException e) {
				e.printStackTrace();
			} catch (NoMatchesLocatedException e) {
				// Silently fail :) (no new matches is always OK.).
			}
		}
	}

	/**
	 * This method deals with the notification that a match has been located.
	 */
	private void notifyOfMatches(Collection<AbsConcept> tempMatches,
			CompiledIRE ireToCheck) {
		LinkedList<AbsConcept> tempMatchesII = new LinkedList<AbsConcept>(
				tempMatches);
		ACLMessage msgToReplyTo = subscriptions.get(ireToCheck);
		ACLMessage reply = msgToReplyTo.createReply();
		reply.setPerformative(ACLMessage.INFORM_REF);

		Equals equals = new Equals();
		equals.setLeft(ireToCheck.getOriginalIRE());
		// Since only ever one match here
		AbsAggregate matched = new AbsAggregate(SL0Vocabulary.SET);
		matched.add(tempMatchesII.getFirst());
		equals.setRight(matched);

		logger
				.debug(" Subscriptions matching to new noticeboard agent located "
						+ equals);

		try {
			getContentManager().fillContent(reply, equals);
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}

		send(reply);
	}

	protected void checkObjectQueue() {
		Object received = getO2AObject();
		while (received != null) {
			logger.debug("NBFactoryAgent.... checkObjectQueue");

			if (received instanceof CreateNoticeboardCommunicationObject) {
				logger
						.debug(" Adding NoticeBoard creation request to the requests queue ");
				// this.createNoticeboard(((CreateNoticeboardCommunicationObject)
				// received).getCaseFile(),
				// ((CreateNoticeboardCommunicationObject) received).getCSN(),
				// ((CreateNoticeboardCommunicationObject)
				// received).getRoleNode());
				noticeBoardCreationRequests
						.offer((CreateNoticeboardCommunicationObject) received);
			} else if (received instanceof AgentCommunicationObject) {
				addIncommingObject((AgentCommunicationObject) received);
			}
			received = getO2AObject();
		}
	}

	/** get the message object #index from the incominObjectQueue */
	protected AgentCommunicationObject getIncommingObject(int index) {
		AgentCommunicationObject result = null;
		if (incommingObjectQueue != null) {
			synchronized (incommingObjectQueue) {
				result = incommingObjectQueue.get(index);
			}
		}
		return result;
	}

	/** get the message object #index from the incominObjectQueue */
	protected AgentCommunicationObject removeIncommingObject(int index) {
		AgentCommunicationObject result = null;
		if (incommingObjectQueue != null) {
			synchronized (incommingObjectQueue) {
				result = incommingObjectQueue.remove(index);
			}
		}
		return result;
	}

	/** add a message object to the incominObjectQueue */
	protected void addIncommingObject(AgentCommunicationObject message) {
		if (incommingObjectQueue == null)
			incommingObjectQueue = new ArrayList<AgentCommunicationObject>();
		synchronized (incommingObjectQueue) {
			incommingObjectQueue.add(message);
		}
	}

	/**
	 * get the size of the queue holding the incoming message objects
	 * 
	 * @return -1 if incommingObjectQueue == null, 0 if empty, ...
	 */
	protected int getIncommingObjectQueueSize() {
		int result = -1;
		if (incommingObjectQueue != null) {
			synchronized (incommingObjectQueue) {
				result = incommingObjectQueue.size();
			}
		}
		return result;
	}

	/**
	 * do not direcly modify the cf! it needs to be retrieved again from the DB
	 * before modifying it and updateing the DB! * this is called
	 * asynchronously. ie. the cf comes from a different (transaction) session
	 * we have to merge it with the one from the current session..
	 */
	public void addPartialSolution(CaseFile cf,
			ConcreteSupplyNetwork concreteSupplyNetwork,
			ASNRoleNode ourRoleNode, PartialSolution ps) {
		logger.error("********* new Partial Solution: " + ps);
	}

	/**
	 * do not direcly modify the cf! it needs to be retrieved again from the DB
	 * before modifying it and updateing the DB! this is called asynchronously.
	 * ie. the cf comes from a different (transaction) session we have to merge
	 * it with the one from the current session..
	 */
	public static synchronized void addCompleteSolution(CaseFile cf,
			ConcreteSupplyNetwork concreteSupplyNetwork,
			ASNRoleNode ourRoleNode, ConcreteSupplyNetwork newSolution,
			TeamFormationControllerImpl tfController, Logger logger) {
		logger.error("************** new Complete Solution:!! " + newSolution);
		CaseFile fromthedatabase = tfController.retrieveCaseFile(cf.getId());

		ArrayList<ConcreteSupplyNetwork> tempTeams = new ArrayList<ConcreteSupplyNetwork>(
				fromthedatabase.getTempTeams());
		ConcreteSupplyNetwork csnToAdd = fromthedatabase
				.addNewBottomUpSolution(concreteSupplyNetwork, ourRoleNode,
						newSolution);

		// debug some hibernate stuff:
		logger.warn("add CompleteSolution: Competences Needed");
		for (Map.Entry<CompetenceNeeded, Supplier> ent : csnToAdd
				.getCandidateSuppliersWithProfile().entrySet()) {
			if (ent != null && ent.getKey() != null) {
				logger
						.warn("competenceneeded id:" + ent.getKey().getId()
								+ "  name "
								+ ent.getKey().getNameOfCompetenceProfile());
				tfController.update(ent.getKey());
			}
		}

		tempTeams.add(csnToAdd);
		fromthedatabase.setTempTeams(tempTeams);
		if (tfController != null) {
			tfController.update(fromthedatabase);
			logger.warn("updated casefile: " + fromthedatabase.getId());
		}

	}

	public ConcreteSupplyNetwork convertToCSN(PartialSolution newSolution) {
		// Nodes == declared capabilities,
		// Resource dependencies - derived from the state structure

		Map<ASNRoleNode, Supplier> supplierAssignment = new HashMap<ASNRoleNode, Supplier>();
		AbstractSupplyNetwork resultAbstract = new AbstractSupplyNetwork();

		ASNRoleNode tempNode;
		Supplier tempSupplier;
		Organization tempOrganisation;
		List<Organization> tempOrganisations;
		QualificationProfile tempQualProfile;
		ArrayList<String> resourcesProduced;
		HashMap<Integer, ASNRoleNode> roleNodesWithID = new HashMap<Integer, ASNRoleNode>();

		// I think this is reliant on the ordering of the declared capabilities
		// to keep things straight....
		int i = 0;
		for (DeclaredCapability dc : newSolution.getDeclaredCapabilities()) {

			// System.out.println(" COnsidering declared capability " + dc);

			tempNode = new ASNRoleNode();
			tempSupplier = new Supplier();

			// SO it won't crash when I don't get my organisation names lined up
			// quite right ;)
			tempOrganisations = tfController.retrieveOrganisationsByName(dc
					.getOwningAgent());

			if (tempOrganisations.size() != 0) {
				tempOrganisation = tempOrganisations.get(0);
			} else {
				tempOrganisation = new Organization();
			}

			tempQualProfile = new QualificationProfile();
			tempSupplier.setOrganisation(tempOrganisation);

			supplierAssignment.put(tempNode, tempSupplier);

			resourcesProduced = new ArrayList<String>();

			for (ResourceSet s : dc.getResourcesProduced()) {
				// Ok my friends this is just a little ugly :) Got to strip the
				// ()'s out + may as well reinsert the name space while here.
				String resProd = s.getResourceType().toString();
				resProd = resProd.substring(1, resProd.length() - 1);
				resProd = SuddenProductOntology.fullNameSpace + resProd;

				System.out.println(" ******* adding new product type "
						+ resProd);

				resourcesProduced.add(resProd);
			}

			/*
			 * TODO - note that, due to how the qualification profiles are
			 * currently defined, we lost a bit of info regarding which
			 * resources the company consumes in doing this.
			 * 
			 * Actually te attachment of the product individual URI isn't
			 * strictly accurate either! I've done it for clarity but may need
			 * to remove it later.
			 */
			tempQualProfile.setProductTypes(resourcesProduced);
			tempQualProfile.setProductIndividualURI(resourcesProduced.get(0));
			tempNode.setQualificationProfile(tempQualProfile);

			// So that I can fetch the nodes back out again
			roleNodesWithID.put(((LinkedList) newSolution
					.getDeclaredCapabilities()).indexOf(dc), tempNode);

			resultAbstract.addNewNode(tempNode);

			// TODO - the concrete profile needs something too :)

			i++;
		}

		// Now for arcs
		// Basically just add in the resource dependencies where they exist
		for (DeclaredCapability dc : newSolution.getDeclaredCapabilities()) {
			for (ResourceSet resProduced : dc.getResourcesProduced()) {
				int resourceSetID = resProduced.getID();
				for (DeclaredCapability dcToTest : newSolution
						.getDeclaredCapabilities()) {
					ResourceSet resToTest;
					boolean found = false;
					Iterator<ResourceSet> overResourcesToTest = dcToTest
							.getResourcesRequired().iterator();
					while (overResourcesToTest.hasNext() && !found) {
						resToTest = overResourcesToTest.next();
						if (resToTest.getID() == resourceSetID) {
							ASNMaterialDependency matDep = new ASNMaterialDependency();
							matDep.setSourceNode(roleNodesWithID
									.get(((LinkedList) newSolution
											.getDeclaredCapabilities())
											.indexOf(dc)));
							matDep.setTargetNode(roleNodesWithID
									.get(((LinkedList) newSolution
											.getDeclaredCapabilities())
											.indexOf(dcToTest)));

							resultAbstract.addNewDependcy(matDep);
							found = true;
						}
					}
				}
			}
		}

		ConcreteSupplyNetwork result = new ConcreteSupplyNetwork(
				resultAbstract, supplierAssignment);

		return result;
	}

	public TeamFormationControllerImpl getTfController() {
		return tfController;
	}

	public Logger getLogger() {
		return logger;
	}
}
