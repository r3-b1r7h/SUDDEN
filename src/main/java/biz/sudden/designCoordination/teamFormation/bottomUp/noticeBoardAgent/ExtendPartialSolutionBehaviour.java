package biz.sudden.designCoordination.teamFormation.bottomUp.noticeBoardAgent;

import jade.content.abs.AbsConcept;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Done;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Collection;

import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.UnknownConstraintTypeException;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.DeclaredCapability;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.EquivalentSolutionPresentException;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PartialSolution;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PotentialExtension;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent.NoticeBoardFactoryAgent;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessIntrospector;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessOntology;

/**
 * 
 * @author mcassmc
 * 
 *         Responsible for actually enacting a request to extend a given partial
 *         solution from a process agent - the protocol checking that the
 *         extension is acceptable is assumed to have already run.
 * 
 * 
 *         TODO - this behaviour seems to currently not be used
 */

@SuppressWarnings("serial")
public class ExtendPartialSolutionBehaviour extends OneShotBehaviour {

	public NoticeBoardAgent myNBAgent;
	private ACLMessage messageIn;

	public ExtendPartialSolutionBehaviour(NoticeBoardAgent agent,
			ACLMessage extendPartialSolutionRequest) {
		super(agent);
		this.myNBAgent = agent;
		this.messageIn = extendPartialSolutionRequest;
	}

	@Override
	public void action() {

		try {
			// Amazingly wordy this really :)
			AbsConcept extendPartialSolutionRequestContent = (AbsConcept) ((AbsConcept) myAgent
					.getContentManager().extractAbsContent(messageIn))
					.getAbsTerm(SL0Vocabulary.ACTION_ACTION);

			// TODO - do this properly! For now this simple minded thing is ok
			// (since the type system is so simple.).

			DeclaredCapability dc = ProcessIntrospector
					.makeConcreteDeclaredCapability((AbsConcept) extendPartialSolutionRequestContent
							.getAbsObject(ProcessOntology.EXTEND_PARTIAL_SOLUTION_DECLARED_CAPABILITY));
			PotentialExtension p = ProcessIntrospector
					.makeConcretePotentialExtension((AbsConcept) extendPartialSolutionRequestContent
							.getAbsObject(ProcessOntology.EXTEND_PARTIAL_SOLUTION_POTENTIAL_EXTENSION));

			/*
			 * Important Note - this potential extension is NOT directly usable
			 * since the state/resource set mapping has been lost (they're all
			 * state 0 for now). However the resource set ID's *are* round
			 * tripped ergo can regenerate the state information. Let this be a
			 * lesson in avoiding indirect pointers.
			 */
			myNBAgent.ourPartialSolutionHolder.regenerateStateInformation(p);
			// System.out.println(" Potential Extension is now " + p);

			// get newly added solution and add it.
			PartialSolution newSolution;
			try {

				newSolution = myNBAgent.ourPartialSolutionHolder
						.getNewPartialSolution(dc, p,
								extendPartialSolutionRequestContent);

				Collection<String> agentsToContact = p.getAgentsContributing();

				if (agentsToContact.size() != 0) {
					myNBAgent
							.addBehaviour(new VerifyProposedExtensionBehaviour(
									myNBAgent, messageIn, dc, p, newSolution));
				} else {
					// Note we're checking for equivalence *both* here and
					// earlier before starting negotiations
					// this is due to timing effects (new solutions added during
					// our negotiations.).
					try {
						myNBAgent.ourPartialSolutionHolder
								.addPartialSolution(newSolution);
						// no JPanel in SUddEN
						// myNBAgent.ourGraphics.addPartialSolution(newSolution);
						// DO NOT modify the casefile manually here!!! just keep
						// it as reference
						// DUE to the asynchronous call it needs to be retrieved
						// again from the DB befor updateing it
						myNBAgent.myFactoryAgent.addPartialSolution(
								myNBAgent.ourCaseFile, myNBAgent.ourCSN,
								myNBAgent.ourRoleNode, newSolution);

						sendAcceptance();

						// Check the subscriptions
						myNBAgent.checkSubscriptionsAgainst(newSolution);

						// TODO - do stuff here!!
						if (newSolution.isCompleteSolution()) {
							System.out
									.println(" *****************************  new complete solution located "
											+ newSolution);
							System.out
									.println(" *****************************  And to a concrete supply network "
											+ newSolution.convertToCSN());
							// System.out.println(" Current partial solutions "
							// + myNBAgent.ourPartialSolutionHolder);
							// System.out.println(" Converted to a shape graph "
							// +
							// ShapeGraphOntology.getInstance().fromObject(newSolution.convertToShapeGraph()));

							// no JPanels in SUdden
							// myNBAgent.ourGraphics.addCompleteSolution(newSolution);
							// DO NOT modify the casefile manually here!!! just
							// keep it as reference
							// DUE to the asynchronous call it needs to be
							// retrieved again from the DB befor updateing it
							// myNBAgent.myFactoryAgent.addCompleteSolution(myNBAgent.ourCaseFile,
							// myNBAgent.ourCSN,myNBAgent.ourRoleNode,newSolution.convertToCSN());
							NoticeBoardFactoryAgent.addCompleteSolution(
									myNBAgent.ourCaseFile, myNBAgent.ourCSN,
									myNBAgent.ourRoleNode, newSolution
											.convertToCSN(),
									myNBAgent.myFactoryAgent.getTfController(),
									myNBAgent.myFactoryAgent.getLogger());
						}
					} catch (EquivalentSolutionPresentException e) {

						sendRefusal();
					} /*
					 * catch (OntologyException e) { // Ignore
					 * e.printStackTrace(); }
					 */
				}
			}

			catch (EquivalentSolutionPresentException e) {
				// TODO - say *WHY* we're rejecting the proposal

				// System.out.println(" rejecting suggested extension " +
				// dc.getOwningAgent());
				// System.out.println(myNBAgent.ourPartialSolutionHolder.partialSolutions.size());

				ACLMessage reply = messageIn.createReply();
				reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
				reply.setContent(messageIn.getContent());
				myAgent.send(reply);
			} catch (UnknownConstraintTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ACLMessage reply = messageIn.createReply();
			reply.setPerformative(ACLMessage.INFORM);
			Done d = new Done();
			d.setAction((AbsConcept) myAgent.getContentManager()
					.extractAbsContent(messageIn));
			myAgent.getContentManager().fillContent(reply, d);
			myAgent.send(reply);

		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}

	private void sendRefusal() {
		// TODO - say *WHY* we're rejecting the proposal
		// System.out.println(" rejecting suggested extension " +
		// dc.getOwningAgent());
		// System.out.println(myNBAgent.ourPartialSolutionHolder.partialSolutions.size());
		ACLMessage reply = messageIn.createReply();
		reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
		reply.setContent(messageIn.getContent());
		myAgent.send(reply);
	}

	private void sendAcceptance() {
		ACLMessage reply = messageIn.createReply();
		reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
		reply.setContent(messageIn.getContent());
		myAgent.send(reply);
	}

}
