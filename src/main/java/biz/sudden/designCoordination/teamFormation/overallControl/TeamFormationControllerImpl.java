package biz.sudden.designCoordination.teamFormation.overallControl;

import jade.content.abs.AbsConcept;
import jade.core.AID;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.designCoordination.coordination.service.impl.CoordinationService;
import biz.sudden.designCoordination.coordination.web.controller.impl.CoordinationController;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.DeclaredCapability;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.ResourceSet;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent.NoticeBoardFactoryAgent;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.SuddenProductOntology;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.designCoordination.teamFormation.service.CreateNoticeboardCommunicationObject;
import biz.sudden.designCoordination.teamFormation.service.JadeService;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;
import biz.sudden.knowledgeData.kdm.service.KdmService;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public class TeamFormationControllerImpl implements
		TeamFormationControllerService {

	/**
	 * This is the class to call to kick team formation off. It's responsible
	 * for controlling the interaction between top down and bottom up team
	 * formation.
	 * 
	 * As such it's currently quite simple but once BUp is integrated in it'll
	 * get more complex.
	 */
	private EnterpriseEvaluationService myEnterEvalService;
	private ProductMaterialSupportingServices_Service myProductMatSuppService;
	private PMController controllerForEvaluation;
	private ServiceManagementService SMS;
	private KdmService kdm;
	private JadeService jadeService;

	// For testing purposes really
	private CoordinationController coordControl;
	private CoordinationService coordService;

	// this should definitely go to the service!
	private static AgentController theSystemsNoticeboardFactoryAgentsController;
	private topDownTeamFormationModule myTDTF;
	private Logger logger = Logger.getLogger(this.getClass());
	public static final String noticeboardFactoryAgentName = "noticeboardFactory";

	private final static int maxTeamsReturned = 40;

	public TeamFormationControllerImpl(topDownTeamFormationModule TDTF) {
		this();
		myTDTF = TDTF;
	}

	public TeamFormationControllerImpl() {
		logger.debug("TeamFormationControllerImpl -> cst");
	}

	/**
	 * Used to set up the bottom up TF stuff, ie start a JADE platform + the
	 * NBoard Factory agent For *demo* purposes only this also starts up a set
	 * of process provider agents. A real world integration would be a little
	 * more involved, likely both systems being set up independtly with the
	 * NBFactory agent as a bridge. Or something.
	 */

	private void initBottomUpTFAgents() {
		if (jadeService != null && jadeService.getPlatform() != null) {

			// Start the sniffer I suppose
			// AgentController sniffer;
			// sniffer = pc.createNewAgent("sniffer",
			// "jade.tools.sniffer.Sniffer",
			// null);
			// sniffer.start();

			// start agents only ONCE!
			if (theSystemsNoticeboardFactoryAgentsController == null) {

				// Certainly start the NBoard Factory agent!
				Object[] agentFilez = new Object[3];
				agentFilez[0] = SuddenProductOntology.getInstance();
				agentFilez[1] = this;
				agentFilez[2] = jadeService;

				TeamFormationControllerImpl.theSystemsNoticeboardFactoryAgentsController = jadeService
						.startAgent(noticeboardFactoryAgentName,
								NoticeBoardFactoryAgent.class
										.getCanonicalName(), agentFilez);

				// Wait until agent becomes idle
				try {
					while (theSystemsNoticeboardFactoryAgentsController
							.getState().getCode() != jade.wrapper.AgentState.cAGENT_STATE_ACTIVE) {
						Thread.sleep(1000);
					}
				} catch (StaleProxyException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Now for the slightly annoying bit :) Start up some process
				// provider
				// agents.
				// carDoorWindowSystem is apparently the thing we need to
				// provide
				// Two agents for now. One wouldn't test everything properly.
				// One does an electric motor, the other finishes it off. Silly
				// I
				// know.

				Object[] agentFile = new Object[2];
				LinkedList<ResourceSet> resRequired1 = new LinkedList<ResourceSet>();
				ResourceSet r1 = new ResourceSet(new AbsConcept(
						SuddenProductOntology.Elektronischs_Motormanagement));
				r1.setRelativeAmount(1);

				ResourceSet r11 = new ResourceSet(new AbsConcept(
						SuddenProductOntology.Elektrische_Leistungsregler));
				r11.setRelativeAmount(1);

				resRequired1.add(r1);
				resRequired1.add(r11);

				LinkedList<ResourceSet> resProduced1 = new LinkedList<ResourceSet>();
				ResourceSet r111 = new ResourceSet(new AbsConcept(
						SuddenProductOntology.Radnabenmotor));
				// r111.setRelativeAmount(1);
				resProduced1.add(r111);

				agentFile[0] = new DeclaredCapability(resRequired1,
						resProduced1, "GraCo");
				agentFile[1] = new AID(noticeboardFactoryAgentName,
						AID.ISLOCALNAME);

				AgentController dumbProcessAgent1 = jadeService
						.startAgent(
								"GraCo",
								"biz.sudden.designCoordination.teamFormation.bottomUp.dumbProcessAgent.DumbProcessAgent",
								agentFile);

				Object[] agentFile2 = new Object[2];
				LinkedList<ResourceSet> resRequired2 = new LinkedList<ResourceSet>();

				LinkedList<ResourceSet> resProduced2 = new LinkedList<ResourceSet>();
				ResourceSet r2 = new ResourceSet(new AbsConcept(
						SuddenProductOntology.Elektronischs_Motormanagement));
				r2.setRelativeAmount(1);
				resProduced2.add(r2);

				agentFile2[0] = new DeclaredCapability(resRequired2,
						resProduced2, "C*Limited");
				agentFile2[1] = new AID(noticeboardFactoryAgentName,
						AID.ISLOCALNAME);

				AgentController dumbProcessAgent2 = jadeService
						.startAgent(
								"C*Limited",
								"biz.sudden.designCoordination.teamFormation.bottomUp.dumbProcessAgent.DumbProcessAgent",
								agentFile2);

				Object[] agentFile3 = new Object[2];
				LinkedList<ResourceSet> resRequired3 = new LinkedList<ResourceSet>();

				LinkedList<ResourceSet> resProduced3 = new LinkedList<ResourceSet>();
				ResourceSet r3 = new ResourceSet(new AbsConcept(
						SuddenProductOntology.Elektrische_Leistungsregler));
				r3.setRelativeAmount(1);
				resProduced3.add(r3);

				agentFile3[0] = new DeclaredCapability(resRequired3,
						resProduced3, "S*Ltd");
				agentFile3[1] = new AID(noticeboardFactoryAgentName,
						AID.ISLOCALNAME);

				AgentController dumbProcessAgent3 = jadeService
						.startAgent(
								"S*Ltd",
								"biz.sudden.designCoordination.teamFormation.bottomUp.dumbProcessAgent.DumbProcessAgent",
								agentFile3);
			}
		} else {
			logger.error("!!!!!!! not able to start Agent Platform !!!!!!!!!!");
		}
	}

	public void setTDTF(topDownTeamFormationModule TDTF) {
		myTDTF = TDTF;
	}

	public void setSMS(ServiceManagementService SMS) {
		this.SMS = SMS;
	}

	public ServiceManagementService getSMS() {
		return this.SMS;
	}

	public void setCoordService(CoordinationService cs) {
		this.coordService = cs;
	}

	public CoordinationService getCoordService() {
		return this.coordService;
	}

	public void setCoordControl(CoordinationController cc) {
		this.coordControl = cc;
	}

	public CoordinationController getCoordControl() {
		return this.coordControl;
	}

	/**
	 * @param controllerForEvaluation
	 *            the controllerForEvaluation to set
	 */
	@Override
	public void setControllerForEvaluation(PMController controllerForEvaluation) {
		this.controllerForEvaluation = controllerForEvaluation;
	}

	// @Override
	// public ArrayList<ConcreteSupplyNetwork>
	// generateCandidateTeams(AbstractSupplyNetwork ASNin) {
	// // TODO Do this once bottom up is around
	//		
	// return null;
	// }
	/**
	 * This is the 'fast and light' method of TF. Just contact the right modules
	 * and do a top down TF.
	 * 
	 * The teams are now returned unranked.
	 * 
	 * @param controllerForEvaluation2
	 */
	private List<ConcreteSupplyNetwork> generateCandidateTeamsTopDown(
			AbstractSupplyNetwork ASNin,
			EnterpriseEvaluationService myEnterpriseEvaluationService,
			ProductMaterialSupportingServices_Service myMaterialSupportingServices_Service,
			PMController controllerForEvaluation2) {

		return myTDTF.generateCandidateTeams(ASNin,
				myMaterialSupportingServices_Service, controllerForEvaluation);
	}

	/**
	 * For generating candidate team members in the prototype phase. Two methods
	 * else I don't know where to store the result :) (due to how the case file
	 * is set up)
	 * 
	 * Currently this is otherwise functionally identical to the one used in the
	 * later phases. I suppose that in principle they might differ.
	 * 
	 * @param caseFileIn
	 */
	@Override
	public void generatePrototypeTeamCandidatesTopDown(CaseFile caseFileIn) {

		logger.debug(" Team formation working off ASN "
				+ caseFileIn.getAsnPrototypeTeam());

		logger.debug("generating Candidate CSN for Prototype"
				+ caseFileIn.getAsnPrototypeTeam());
		List<ConcreteSupplyNetwork> list = rankTeams(generateCandidateTeamsTopDown(
				caseFileIn.getAsnPrototypeTeam(), myEnterEvalService,
				myProductMatSuppService, controllerForEvaluation));

		// TODO - note this won't work with CSNs needing two nodes done with
		// bottom up. The current one probably won't then either though.

		initBottomUpTFAgents();

		// Ok run through, work out which CSNs are clean - add them and store
		// them in the database. Send the others on to bottom up TF.
		boolean doneTopDown;
		List<ConcreteSupplyNetwork> tempSNetworkList = new ArrayList<ConcreteSupplyNetwork>();
		for (ConcreteSupplyNetwork nextCSN : list) {
			doneTopDown = true;
			for (ASNRoleNode roleNode : nextCSN.getCandidateSuppliers()
					.keySet()) {

				if (nextCSN.getCandidateSuppliers().get(roleNode) == null) {
					doneTopDown = false;
					logger.debug(" Node " + roleNode
							+ " has been found to have no suppliers "
							+ nextCSN.getCandidateSuppliers().get(roleNode));
					generateTeamsBottomUp(caseFileIn, nextCSN, roleNode);
				}
			}
			if (doneTopDown) {
				tempSNetworkList.add(nextCSN);
			}
		}

		caseFileIn.setTempTeams(tempSNetworkList);
		kdm.getCaseService().update(caseFileIn);

		logger.debug(" Found n CSN: " + list.size());

		logger.debug(" With values " + list);

		logger.debug(" Now filling in any empty role nodes with bottom up TF");

		/*
		 * A little something I used to test the coordination module
		 * ConcreteSupplyNetwork coordinatedCSN =
		 * coordService.coordinateCSN(list.get(0));
		 * coordControl.setCSN(coordinatedCSN);
		 */

	}

	/**
	 * For generating candidate team members in the later phase. Two methods
	 * else I don't know where to store the result :) (due to how the case file
	 * is set up)
	 * 
	 * @param caseFileIn
	 */
	@Override
	public void generateFinalTeamCandidatesTopDown(CaseFile caseFileIn) {

		logger.debug("generating Candidate CSN for Final Product"
				+ caseFileIn.getAsnFinalTeam());

		List<ConcreteSupplyNetwork> list = rankTeams(generateCandidateTeamsTopDown(
				caseFileIn.getAsnFinalTeam(), myEnterEvalService,
				myProductMatSuppService, controllerForEvaluation));

		caseFileIn.setTempTeams(list);
		kdm.getCaseService().update(caseFileIn);
		logger.debug(" Found n CSN: " + list.size());

		logger.debug(" Now filling in any empty role nodes with bottom up TF");

		/*
		 * This bit should hopefully go through, find all nodes with no
		 * suppliers & kick off bottom up TF for them?
		 */

		AbstractSupplyNetwork tempASN;

		for (ConcreteSupplyNetwork nextCSN : list) {
			for (ASNRoleNode roleNode : nextCSN.getCandidateSuppliers()
					.keySet()) {
				logger.debug(" looking at node "
						+ nextCSN.getCandidateSuppliers().get(roleNode));
				if (nextCSN.getCandidateSuppliers().get(roleNode) == null) {
					ConcreteSupplyNetwork net = nextCSN.clone();
					generateTeamsBottomUp(caseFileIn, net, roleNode);
					update(net); // Don't know if this helps
				}
			}
		}
	}

	/*
	 * To keep Spring happy
	 */
	@Override
	public void setEnterpriseEvaluationService(EnterpriseEvaluationService EES) {
		this.myEnterEvalService = EES;

	}

	/*
	 * To keep Spring happy
	 */
	@Override
	public void setProductMaterialSupportingService(
			ProductMaterialSupportingServices_Service PMSS) {
		this.myProductMatSuppService = PMSS;
	}

	@Override
	public void generateTeamsBottomUp(CaseFile caseFileToChange,
			ConcreteSupplyNetwork CSNtoUpdate, ASNRoleNode materialToConstruct) {
		// Just create the noticeboard agent required and everything should
		// work?!
		// Oh dear I just cheated ;) Didn't even use that nice thing with the
		// agent
		// communication objects
		// that Georg set up.
		// Never mind. The agent would understand that too.

		// I keep this in here in addition to starting it at starttime it
		// if (theSystemsNoticeboardFactoryAgentsController == null)

		logger.debug(" Moving to create teams in bottom up style for "
				+ caseFileToChange + ", " + CSNtoUpdate + " "
				+ materialToConstruct);

		CreateNoticeboardCommunicationObject cnco = new CreateNoticeboardCommunicationObject();
		cnco.setCaseFile(caseFileToChange);
		cnco.setCSN(CSNtoUpdate);
		cnco.setRoleNode(materialToConstruct);

		try {
			theSystemsNoticeboardFactoryAgentsController.putO2AObject(cnco,
					AgentController.ASYNC);
		} catch (StaleProxyException e) {
			// Well yes may happen but then your agent system isn't running or
			// something.
			// Nothing I can really fix anyhow :)
			e.printStackTrace();
		}
	}

	@Override
	public void setKDMService(KdmService kdm) {
		this.kdm = kdm;
	}

	@Override
	public void setJadeService(JadeService jadeS) {
		this.jadeService = jadeS;
		logger.debug("Set JadeService : " + jadeS);
		// initBottomUpTFAgents();
	}

	public void update(CaseFile cf) {
		if (kdm != null) {

			kdm.getCaseService().update(cf);
		}
	}

	public void update(ConcreteSupplyNetwork csn) {
		if (kdm != null) {
			kdm.getCaseService().update(csn);
		}
	}

	public void update(CompetenceNeeded cn) {
		if (kdm != null) {
			kdm.getCaseService().update(cn);
		}
	}

	@Override
	public CaseFile retrieveCaseFile(Long id) {
		CaseFile result = null;
		if (kdm != null) {
			result = kdm.getCaseService().retrieveCaseFile(id);
		}
		return result;
	}

	@Override
	public Supplier retrieveSupplier(Long id) {
		Supplier result = null;
		if (kdm != null) {
			result = kdm.retrieveSupplier(id);
		}
		return result;
	}

	@Override
	public Organization retrieveOrganisation(Long id) {
		Organization result = null;
		if (kdm != null) {
			result = kdm.retrieveOrganization(id);
		}
		return result;
	}

	@Override
	public CompetenceNeeded retrieveCompetenceNeeded(Long id) {
		return kdm.getCaseService().retrieveCompetenceNeeded(id);
	}

	@Override
	public QualificationProfile retrieveQualificationProfile(Long id) {
		return kdm.getCaseService().retrieveQualificationProfile(id);
	}

	/*
	 * A slightly aimless direct mirroring of the call from the service
	 * management controller interface. Easiest way to get it going in agents.
	 * 
	 * thats not aimless because: TeamFormationController is in the session
	 * scope i.e. a single instance is created for every user the SM Service is
	 * in application scope; so a single instance system wide.
	 */
	@Override
	public List<Organization> retrieveOrganisationsByName(String name) {
		logger.debug(" SMS Service is " + SMS + " Name is " + name);
		return SMS.retrieveOrganisationsByName(name);
	}

	/*
	 * NB - this had been modified/originally designed to combine coordination
	 * fit + team ranking scores.
	 * 
	 * With the way that coordination fit is no longer triggered directly &
	 * we're not even formally combining the scores its changed to just
	 * attaching the 'static' rankings for the teams.
	 */
	private ArrayList<ConcreteSupplyNetwork> rankTeams(
			List<ConcreteSupplyNetwork> teamsToRank) {
		ConcreteSupplyNetwork rankedTeams[], auxTeam;
		ArrayList<ConcreteSupplyNetwork> teamList = new ArrayList<ConcreteSupplyNetwork>();

		/*
		 * Fetch + rank the teams
		 * 
		 * I'm reliably informed that this one call from Georg actually deals
		 * with all of the evaluation stuff required?
		 * 
		 * So we don't need to do individual rankings for team members etc. That
		 * would however be a useful thing to keep for future extensions.
		 */
		for (ConcreteSupplyNetwork nextTeam : teamsToRank) {
			// System.out.println(" Ranking team " + nextTeam);
			// System.out.println(" Eval controller " +
			// this.controllerForEvaluation);
			nextTeam
					.setRanking(this.controllerForEvaluation.evaluate(nextTeam));
			// System.out.println(" Ranking " + nextTeam.getRanking());
		}
		logger.debug(" Number of teams sent to ranking " + teamsToRank.size());

		// Sort the teams into rank order. Because I can ;)
		// TODO - am I crashing this due to generating only one team????
		if (teamsToRank.size() > 1) {
			rankedTeams = teamsToRank.toArray(new ConcreteSupplyNetwork[2]);
			for (int i = 0; i < rankedTeams.length - 1; i++) {
				for (int j = i + 1; j < rankedTeams.length; j++) {
					if (rankedTeams[j].getRanking() > rankedTeams[i]
							.getRanking()) {
						auxTeam = rankedTeams[i];
						rankedTeams[i] = rankedTeams[j];
						rankedTeams[j] = auxTeam;
					}
				}
			}

			// Um no. Please don't set the ranking to the position in the list.
			// The ranking wants to store the actual score :)
			for (int i = 0; i < rankedTeams.length; i++) {
				teamList.add(rankedTeams[i]);
			}
		} else if (teamsToRank.size() == 1) {
			teamList.add(teamsToRank.get(0));
		} else {
			// Can we please not get to this stage?
		}

		ArrayList<ConcreteSupplyNetwork> result = null;

		if (teamList.size() < maxTeamsReturned) {
			result = new ArrayList<ConcreteSupplyNetwork>(teamList);
		} else {
			result = new ArrayList<ConcreteSupplyNetwork>(teamList.subList(0,
					maxTeamsReturned));
		}

		logger.debug(" number of teams ranked " + result.size());

		return result;

	}
}
