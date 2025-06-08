package biz.sudden.designCoordination.coordination.service.impl;

import java.util.Iterator;

import org.apache.log4j.Logger;

import biz.sudden.designCoordination.handleBO.dataStructures.ASNComplexNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency.TransportationLogistics;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode.ProductionMethod;
import biz.sudden.knowledgeData.kdm.service.KdmService;
import biz.sudden.knowledgeData.serviceManagement.domain.Thing;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;

public class CoordinationImpl implements CoordinationService {

	Logger logger = Logger.getLogger(this.getClass());

	private static final String LocalFileWithSuddenOntology = "file:SuddenOntologyOwl.owl";
	private KdmService kdmService;
	private OntModel ontologyInMemory;

	public KdmService getkdmServiceImpl() {
		return kdmService;
	}

	public void setkdmService(KdmService kdmServiceImpl) {
		this.kdmService = kdmServiceImpl;
	}

	// TODO - just so I can test things w'out starting up the whole Spring bits.
	// Remove :)
	public void setOntology(OntModel ontModel) {
		this.ontologyInMemory = ontModel;
	}

	public void init() {

		this.ontologyInMemory = kdmService.getOntology();
	}

	@Override
	public ConcreteSupplyNetwork coordinateCSN(
			ConcreteSupplyNetwork csnToCoordinate) {
		// Don't think I should need this but seems as if I might
		init();
		/*
		 * Ok this is pretty easy no? Build a recursive sort of toy thus:
		 * 
		 * 1) Start with the final node and assign it P20 2) Recurse to each
		 * child node with rule: a) If parentNode == P20 then check if canDo P2S
		 * here
		 * 
		 * canDo P2S iff: i ) SetUp Costs/Processing Cost = low, ii)
		 * Organisation concerned is qualified to do P20
		 * 
		 * Some debate about SetUpCosts/Processing Cost = high. However the only
		 * real decision to be made there is between internal P2S and external
		 * P2S. For now I'll treat these ones the same. The buffered JIT
		 * delivery logistics option covers it :)
		 * 
		 * b) If parentNode == P2S then P2S here too.
		 * 
		 * 
		 * 3) Having attached things to the whole concrete supply network, go
		 * through and attach a coord technique to each dependency. Dead easy:
		 * P2O both sides == JIT/JIS, P20 top, P2S bottom == Buffered JIT/JIS,
		 * else 'plain' delivery.
		 */

		// Because the first node is - I believe - assumed to be PTO capable
		// regardless....
		// I guess they can always buffer if it they really must
		boolean P2OCapable = true;

		csnToCoordinate.getASN().getFinalNode().setproductionMethod(
				ProductionMethod.P2O);

		recursivelySetProductionMethod(csnToCoordinate, csnToCoordinate
				.getASN().getFinalNode(), P2OCapable);

		ProductionMethod tempSourceProductionMethod;
		ProductionMethod tempGoalProductionMethod;
		// Now do the assigning to the material dependencies
		for (ASNMaterialDependency m : csnToCoordinate.getASN()
				.getAllMaterialDependencies()) {
			tempSourceProductionMethod = ((ASNRoleNode) m.getSourceNode())
					.getproductionMethod();
			tempGoalProductionMethod = ((ASNRoleNode) m.getTargetNode())
					.getproductionMethod();

			if (tempSourceProductionMethod != null
					&& tempSourceProductionMethod.equals(ProductionMethod.P2O)) {
				if (tempGoalProductionMethod.equals(ProductionMethod.P2S)) {
					m
							.settransportationlogistics(TransportationLogistics.JIS_JIT_WithBuffer);
				} else {
					m
							.settransportationlogistics(TransportationLogistics.JIS_JIT);
				}
			} else {
				m
						.settransportationlogistics(TransportationLogistics.Regular_Delivery);
			}
		}

		return csnToCoordinate;
	}

	/*
	 * A recursive toy which assigns the correct production method (P20 or P2S)
	 * to each node
	 */
	private void recursivelySetProductionMethod(
			ConcreteSupplyNetwork coordinatedCSN, ASNRoleNode roleNode,
			boolean P2OCapable) {
		Iterator<OntResource> ontologyResourceIterator;

		for (ASNRoleNode childNode : coordinatedCSN
				.getNodesConnectedbyMaterialDependency(roleNode)) {

			if (P2OCapable && childNode.getClass().equals(ASNRoleNode.class)) {
				// Joy! Check the production technique. What if its mixed then?!
				// Umm errrr.
				// To avoid nasty situations I'll assume only one material for
				// the individual, one processing technique for that material
				// and
				// so one cost.
				// This is - in general - simply NOT true. Indeed its far from
				// the case. But hey.....
				System.out.println(" Fetching component from ontology "
						+ childNode.getQualificationProfile()
								.getProductIndividualURI());
				System.out.println(" Ont in memory is " + ontologyInMemory);
				System.out.println(" Fetched from ontology "
						+ ontologyInMemory.getIndividual(childNode
								.getQualificationProfile()
								.getProductIndividualURI()));

				Individual componentFromOntology = ontologyInMemory
						.getIndividual(childNode.getQualificationProfile()
								.getProductIndividualURI());

				// For results from bottom up team formation - which *aren't* in
				// the ontology poor dears
				if (componentFromOntology == null) {
					P2OCapable = false;
				}

				ontologyResourceIterator = componentFromOntology
						.listPropertyValues(ontologyInMemory
								.getProperty(Thing.NameSpace + "MadeOfMaterial"));

				// Ugh! A problem here perhaps? Likely with no things with no
				// material.
				// OK random decision no material assigned => high processing
				// cost

				if (ontologyResourceIterator.hasNext()) {
					Individual materialOfComponent = ontologyResourceIterator
							.next().asIndividual();

					ontologyResourceIterator = materialOfComponent
							.listPropertyValues(ontologyInMemory
									.getProperty(Thing.NameSpace
											+ "ProcessedThrough"));

					if (ontologyResourceIterator.hasNext()) {
						// Ugh!!
						Individual processingTechnique = ontologyResourceIterator
								.next().asIndividual();

						// To avoid some ugly casts, not pretty though. No
						// simple way to get an individual from extracting a
						// single value
						ontologyResourceIterator = materialOfComponent
								.listPropertyValues(ontologyInMemory
										.getProperty(Thing.NameSpace
												+ "HasProccesingCost"));

						if (ontologyResourceIterator.hasNext()) {
							Individual setUpCosts = ontologyResourceIterator
									.next().asIndividual();

							if (setUpCosts.getURI().equals(
									Thing.NameSpace + "HighProcessingCost")) {
								P2OCapable = false;
							} else {
								P2OCapable = true;
							}
						}
						// No processing cost found for our chosen process
						else {
							logger
									.debug(" No processing cost found for technique "
											+ processingTechnique
													.getNameSpace());
							// Default assumption when lacking info
							P2OCapable = false;
						}
					}
					// Material found but no processing technique associated
					else {
						logger
								.debug(" No processing technique found for material "
										+ materialOfComponent.getNameSpace());
						// Default assumption when lacking info
						P2OCapable = false;
					}

				} else {
					logger.debug(" No material type found for component "
							+ componentFromOntology.getNameSpace());
					// Default assumption when lacking info
					P2OCapable = false;
				}
			} else if (P2OCapable
					&& childNode.getClass().equals(ASNComplexNode.class)) {
				// Complex product = get the production direct directly
				Individual componentFromOntology = ontologyInMemory
						.getIndividual(childNode.getQualificationProfile()
								.getProductIndividualURI());

				ontologyResourceIterator = componentFromOntology
						.listPropertyValues(ontologyInMemory
								.getProperty(Thing.NameSpace
										+ "AssembledUsingTechnique"));

				if (ontologyResourceIterator.hasNext()) {
					Individual processingTechnique = ontologyResourceIterator
							.next().asIndividual();

					ontologyResourceIterator = componentFromOntology
							.listPropertyValues(ontologyInMemory
									.getProperty(Thing.NameSpace
											+ "HasProccesingCost"));

					if (ontologyResourceIterator.hasNext()) {
						Individual setUpCosts = ontologyResourceIterator.next()
								.asIndividual();

						if (setUpCosts.getURI().equals(
								Thing.NameSpace + "HighProcessingCost")) {
							P2OCapable = false;
						} else {
							P2OCapable = true;
						}
					}
					// No value for the processing costs. So we'll assume its
					// ummm high.
					else {
						P2OCapable = false;
					}
				} else {
					logger.debug(" No material type found for component "
							+ componentFromOntology.getNameSpace());
					// Default assumption when lacking info
					P2OCapable = false;
				}
			}

			// Still that is :)
			if (P2OCapable) {
				childNode.setproductionMethod(ProductionMethod.P2O);
			} else {
				childNode.setproductionMethod(ProductionMethod.P2S);
			}

			recursivelySetProductionMethod(coordinatedCSN, childNode,
					P2OCapable);
		}
	}

}
