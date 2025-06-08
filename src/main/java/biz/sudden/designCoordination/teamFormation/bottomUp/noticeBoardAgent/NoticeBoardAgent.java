package biz.sudden.designCoordination.teamFormation.bottomUp.noticeBoardAgent;

import jade.content.ContentManager;
import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BasicOntology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Equals;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.UnknownConstraintTypeException;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.CycleDetectedException;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PartialSolution;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PartialSolutionHolder;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PotentialExtension;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent.AnswerQueryForNoticeboardsBehaviour;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent.NoticeBoardFactoryAgent;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.NoticeboardCombinedOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessIntrospector;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.CompiledIRE;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.CompiledIREFactory;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.NoMatchesLocatedException;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.TooManyMatchesForIOTAException;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;

/**
 * @author mcassmc
 * 
 *         The Noticeboard agent class.
 * 
 *         Briefly it is responsible for the partial solutions relating to a
 *         given business opportunity, coordinating the negotiations around this
 *         etc.
 * 
 *         See the designs for a fuller description.
 */

public class NoticeBoardAgent extends Agent {

	/*
     * 
     */
	private static final long serialVersionUID = 1L;

	/*
	 * Protected since they're shared with the agents behaviours. Really they're
	 * all one chunk of code :)
	 */
	protected AbsConcept ourNoticeBoardDescription;
	protected PartialSolution ourInitialPartialSolution;
	protected PartialSolutionHolder ourPartialSolutionHolder;
	// protected ConcreteNoticeboardVisualiser ourGraphics;

	protected NoticeBoardFactoryAgent myFactoryAgent;

	// And some stuff added for Sudden usability
	protected CaseFile ourCaseFile;
	protected ConcreteSupplyNetwork ourCSN;
	protected ASNRoleNode ourRoleNode;

	// For query answering
	protected CompiledIREFactory ourPEQFactory;

	// Our subscriptions
	private Hashtable<CompiledIRE, ACLMessage> subscriptions;

	@Override
	protected void setup() {
		// TODO - register with the DF I suppose

		ContentManager cm = this.getContentManager();
		cm.registerLanguage(new SLCodec());
		cm.registerOntology(NoticeboardCombinedOntology.getInstance());

		/*
		 * Get the description passed to the agent when it was created
		 */
		ourNoticeBoardDescription = (AbsConcept) getArguments()[0];
		AbsConcept initialPartialSolutionDescription = (AbsConcept) getArguments()[1];
		ourCaseFile = (CaseFile) getArguments()[2];
		ourCSN = (ConcreteSupplyNetwork) getArguments()[3];
		ourRoleNode = (ASNRoleNode) getArguments()[4];
		myFactoryAgent = (NoticeBoardFactoryAgent) getArguments()[5];

		try {
			ourPartialSolutionHolder = new PartialSolutionHolder(
					initialPartialSolutionDescription);
		} catch (CycleDetectedException e) {
			e.printStackTrace();
		} catch (UnknownConstraintTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ourInitialPartialSolution = ourPartialSolutionHolder
				.getInitialSolution();
		// System.out.println(ourInitialPartialSolution);
		// no graphic panels in SUddEN
		// ourGraphics = new
		// ConcreteNoticeboardVisualiser(ourNoticeBoardDescription,ourInitialPartialSolution,this.getName());
		// ourGraphics.makeVisible();

		ourPEQFactory = new CompiledIREFactory(
				new PotentialExtensionQueryFactory(NoticeboardCombinedOntology
						.getInstance()));

		this.subscriptions = new Hashtable<CompiledIRE, ACLMessage>();

		addBehaviour(new receiveMessage(this));
	}

	public void addSubscription(CompiledIRE queryCompiled, ACLMessage queryIn) {
		this.subscriptions.put(queryCompiled, queryIn);
	}

	/**
	 * This method triggers a check of a given notice board description vs the
	 * set of subscriptions & informs for matches.
	 */
	@SuppressWarnings("unchecked")
	protected void checkSubscriptionsAgainst(PartialSolution newPartialSolution) {
		// TODO - should I be trying to keep this down to only the *new*
		// potential extensions created on this
		// Partial Solution? (not sure but I doubt it's nessecary). Should I
		// restrict things to one match/Partial Solution?
		// Can't do that I think.
		// Should I somehow try to find the BEST match -> also I believe clearly
		// impossible.

		// System.out.println(" **** Checking subscriptions ");

		Collection<PotentialExtension> potExt = new LinkedList<PotentialExtension>();
		for (PotentialExtension p : newPartialSolution
				.getAllPotentialExtensions()) {
			potExt.add(p);
		}

		Collection<PotentialExtension> tempMatches;

		// Check each subscription
		for (CompiledIRE ireToCheck : subscriptions.keySet()) {
			try {
				tempMatches = ireToCheck.findMatches(potExt);
				if (tempMatches.size() > 0) {
					notifyOfMatches(tempMatches, ireToCheck);
				}
			}

			catch (TooManyMatchesForIOTAException e) {
				// TODO - would ideally tell them *why* we've failed here
				ACLMessage toReplyTo = subscriptions.get(ireToCheck);
				ACLMessage reply = toReplyTo.createReply();
				reply.setPerformative(ACLMessage.FAILURE);
				reply.setContent(toReplyTo.getContent());
				send(reply);
			} catch (NoMatchesLocatedException e) {
				// Silently fail :) (no new matches is always OK.).
			}
		}
	}

	/**
	 * This method deals with the notification that a match has been located.
	 */
	private void notifyOfMatches(Collection<PotentialExtension> tempMatches,
			CompiledIRE ireToCheck) {

		LinkedList<AbsConcept> tempMatchesII = new LinkedList<AbsConcept>();
		for (PotentialExtension p : tempMatches) {
			tempMatchesII.add(ProcessIntrospector
					.makeAbstractPotentialExtension(p));
		}
		ACLMessage msgToReplyTo = subscriptions.get(ireToCheck);
		ACLMessage reply = msgToReplyTo.createReply();
		reply.setPerformative(ACLMessage.INFORM_REF);

		Equals equals = new Equals();
		equals.setLeft(ireToCheck.getOriginalIRE());
		// Since only ever one match here
		AbsAggregate matched = new AbsAggregate(SL0Vocabulary.SET);
		matched.add(tempMatchesII.getFirst());
		equals.setRight(matched);

		try {
			getContentManager().fillContent(reply, equals);
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}

		// System.out.println(" && Sending notification of a subcription match "
		// + reply);

		send(reply);
	}

	/**
	 * @author mcassmc
	 * 
	 *         Responsible for catching incoming 'fresh' messages - ie those
	 *         which initiate various behaviours as opposed to those generated
	 *         as a result of protocols.
	 */
	protected class receiveMessage extends CyclicBehaviour {

		public receiveMessage(NoticeBoardAgent agent) {
			super(agent);
		}

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// Set the templates up - one template is non trivial, the other two
			// simply catch all queries/subscription requests
			// It seems safe to assume what all queries & subscriptions will do,
			// whereas the range of requests could easily be extended
			IsExtendPartialSolutionRequest ICNR = new IsExtendPartialSolutionRequest(
					getContentManager(), getAID());
			MessageTemplate IsQuery = MessageTemplate
					.MatchPerformative(ACLMessage.QUERY_REF);
			MessageTemplate IsSubscriptionRequest = MessageTemplate
					.MatchPerformative(ACLMessage.SUBSCRIBE);

			ACLMessage extendPartialSolutionRequest = myAgent
					.receive(new MessageTemplate(ICNR));

			/*
			 * Succesively check for each of the message templates being matched
			 */

			if (extendPartialSolutionRequest != null) {
				addBehaviour(new ExtendPartialSolutionBehaviour(
						(NoticeBoardAgent) myAgent,
						extendPartialSolutionRequest));
			} else {
				ACLMessage queryMessage = myAgent.receive(IsQuery);
				if (queryMessage != null) {
					addBehaviour(new AnswerQueryForNoticeboardsBehaviour(
							myAgent, queryMessage));
				} else {
					ACLMessage subscriptionMessage = myAgent
							.receive(IsSubscriptionRequest);
					if (subscriptionMessage != null) {
						addBehaviour(new AnswerSubscriptionQueriesForPotentialExtensionsBehaviour(
								(NoticeBoardAgent) myAgent, subscriptionMessage));
					} else {
						block();
						return;
					}
				}
			}

		}

	}

	/**
	 * A set of inner classes providing the message templates for filtering the
	 * message types
	 */
	// A very handy idea borrowed from Sven - thanks :
	protected class IsExtendPartialSolutionRequest implements
			MessageTemplate.MatchExpression {
		private static final long serialVersionUID = 1L;
		private ContentManager contentManager;
		private AID myAgentsAID;

		public IsExtendPartialSolutionRequest(ContentManager cm, AID myAgentsAID) {
			this.contentManager = cm;
			this.myAgentsAID = myAgentsAID;
		}

		@Override
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
			// doesn't seem important to do so at the moment.).
			result &= (messageIn.getPerformative() == ACLMessage.REQUEST);
			result &= (messageContent.getTypeName()
					.equals(SL0Vocabulary.ACTION));
			if (result && (aidRequested != null) && (actionRequested != null)) {
				result &= (aidRequested.getString(SL0Vocabulary.AID_NAME)
						.equals(myAgentsAID.getName()));
				result &= (actionRequested.getTypeName()
						.equals(ProcessOntology.EXTEND_PARTIAL_SOLUTION));
			}

			return result;
		}

	}

}
