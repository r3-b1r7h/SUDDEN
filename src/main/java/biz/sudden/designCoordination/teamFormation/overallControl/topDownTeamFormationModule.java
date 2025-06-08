package biz.sudden.designCoordination.teamFormation.overallControl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNodeFixedSupplier;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.knowledgeData.serviceManagement.domain.Thing;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public class topDownTeamFormationModule {

	private Logger logger = Logger.getLogger(this.getClass());

	// The max of nodes for which alternative suppliers are cosidered. Any more
	// nodes than this and the alternatives are thrown away.
	// For now just a straight hack of 10 random ones. For real data make it
	// choose the nodes where its most likely to make an actual difference.
	private static final int MaxDoubleNodesConsidered = 10;
	// The %age of the best score that a supplier must reach to be considered
	// valid.
	// Not simply taking the 'best' supplier due to all the non linear effects
	// involved in evaluating overall team scores.
	// Indeed this current cut off may still be too 'agressive'
	private static final double CutOffPercentage = 80;

	public ArrayList<ConcreteSupplyNetwork> generateCandidateTeams(
			AbstractSupplyNetwork ASNin,
			ProductMaterialSupportingServices_Service materialSupportingServices_ServiceIn,
			PMController controllerForEvaluation) {

		logger.debug(" Generating team");

		// for (ASNNode node : ASNin.getMyNodes()) {
		//
		// if (node instanceof ASNRoleNode) {
		// ASNRoleNode roleNode = (ASNRoleNode) node;
		// if (roleNode.getQualificationProfile() != null) {
		// suddenGenericRepository.update(roleNode.getQualificationProfile());
		// }
		// }
		// }

		// Looks scary but it's a just a list of roles together with the
		// candidate suppliers for them.
		Map<ASNRoleNode, Collection<Supplier>> tempRolesWithCandidates = new HashMap<ASNRoleNode, Collection<Supplier>>();

		ArrayList<Organization> tempCandidateOrganisations;
		ArrayList<Supplier> tempCandidateSuppliers;
		List<String> tempProductTypes;
		QualificationProfile tempQP;

		// (1) Get all feasible candidate for each of the role nodes - a
		// straight query for who can produce a given product

		// logger.debug(" ASN initially sent to top down team formation is " +
		// ASNin);

		for (ASNRoleNode newASNRN : ASNin.getAllRoleNodes()) {
			tempCandidateOrganisations = new ArrayList<Organization>();
			tempCandidateSuppliers = new ArrayList<Supplier>();
			tempQP = newASNRN.getQualificationProfile();

			logger.debug("Finding suppliers for node " + newASNRN
					+ " with class " + newASNRN.getClass()
					+ " qualification Profile " + tempQP);

			if (newASNRN.getClass().equals(ASNRoleNodeFixedSupplier.class)) {
				logger
						.debug(" &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Node with fixed supplier detected "
								+ newASNRN);
				tempCandidateSuppliers.add(new Supplier(
						((ASNRoleNodeFixedSupplier) newASNRN)
								.getFixedOrganisation()));
			}

			else if (tempQP != null) {

				tempProductTypes = tempQP.getProductTypes();

				// Basically get everyone matching the qualification profile.
				// First off does anyone match directly?
				for (String productType : tempProductTypes) {
					logger.debug(" Adding suppliers for product type "
							+ productType + " short name "
							+ Thing.getShortName(productType));
					tempCandidateOrganisations
							.addAll(materialSupportingServices_ServiceIn
									.retrieveCanDo(Thing
											.getShortName(productType)));
				}
				logger.debug(" Found candidate suppliers "
						+ tempCandidateOrganisations);

				// So did we get anyone? If not then try the super types.
				if (tempCandidateOrganisations.size() == 0) {
					tempProductTypes = tempQP.getProductSuperTypes();
					for (String productType : tempProductTypes) {
						logger
								.debug(" Adding suppliers for product super type "
										+ productType
										+ " short name "
										+ Thing.getShortName(productType));
						tempCandidateOrganisations
								.addAll(materialSupportingServices_ServiceIn
										.retrieveCanDo(Thing
												.getShortName(productType)));
					}
					logger.debug(" Found candidate suppliers "
							+ tempCandidateOrganisations);
				}

				List<String> tempMaterials;
				List<Material> tempMaterialList;
				// Still no one?! Last Chance - fall back on people working with
				// the right materials.
				if (tempCandidateOrganisations.size() == 0) {
					tempMaterials = tempQP.getPotentialMaterialURIs();
					for (String s : tempMaterials) {
						tempMaterialList = materialSupportingServices_ServiceIn
								.retrieveMaterial(Thing.getShortName(s));
						logger.debug(" Adding suppliers for material type " + s
								+ " short name " + Thing.getShortName(s));
						for (Material m : tempMaterialList) {
							logger.debug("should be "
									+ materialSupportingServices_ServiceIn
											.retrieveCanDo(m));
							tempCandidateOrganisations
									.addAll(materialSupportingServices_ServiceIn
											.retrieveCanDo(m));
						}
					}
				}

				// TODO - still no one? Then sulk!
				if (tempCandidateOrganisations.size() == 0) {
					logger
							.debug(" No candidate suppliers found in top down mode.");
				}

				/*
				 * The method call below uses info form the ontology to reduce
				 * the number of suppliers considered for each role in the ASN
				 * when there were >2 of them.Right now it isn't getting called
				 * due to the evaluation based reduction routines in places.
				 * Long term it may prove nessecary to put it back. if
				 * (tempCandidateOrganisations.size() > 2) {
				 * tempCandidateOrganisations =
				 * reduceUsingOntology(tempCandidateOrganisations, tempQP,
				 * materialSupportingServices_ServiceIn); }
				 */

				/*
				 * Take organisations & encapsulate into supplier concepts.
				 */
				for (Organization o : tempCandidateOrganisations) {
					tempCandidateSuppliers.add(new Supplier(o));
				}
			}

			if (newASNRN != null) {
				tempRolesWithCandidates.put(newASNRN, tempCandidateSuppliers);
			}
		}

		Map<ASNRoleNode, Collection<Supplier>> evenMoreTempRolesWithCandidates = new HashMap<ASNRoleNode, Collection<Supplier>>();

		// For some reason a bunch of null ASN Nodes seem to creep in sometimes.
		// Something to do with Jena perhaps. Anyway doesn't hurt to remove
		// them.
		for (ASNRoleNode node : tempRolesWithCandidates.keySet()) {
			if (!(node == null)) {
				evenMoreTempRolesWithCandidates.put(node,
						tempRolesWithCandidates.get(node));
			}
		}
		tempRolesWithCandidates = evenMoreTempRolesWithCandidates;

		// Bundling - identifying when one supplier can do > 1 role in the ASN
		// and assigning them to both
		tempRolesWithCandidates = doBundling(tempRolesWithCandidates);

		logger.debug(" Candidate suppliers after bundling "
				+ tempRolesWithCandidates);

		// Now use the evaluation system to reduce the number of people
		// considered for each role
		// Basically just keep everyone within a given %age of the 'best'
		// supplier
		tempRolesWithCandidates = reduceByScore(tempRolesWithCandidates,
				controllerForEvaluation);

		logger.debug(" Candidate suppliers after reductionByScore "
				+ tempRolesWithCandidates);

		/*
		 * 
		 */

		// Last ditch protection vs getting too many ASNs overall:
		// For the first N nodes keep every supplier, after that just the first
		// one.
		// Since we're not getting 'proper' node weights from the evaluation
		// score then....
		tempRolesWithCandidates = doFinalReduction(tempRolesWithCandidates);

		logger.debug(" Candidate suppliers after final reduction "
				+ tempRolesWithCandidates);

		// (2) Generate all the possible combinations of these candidates.
		// Note that this requires the reductions applied above or it can
		// produce
		// a rather silly combinatorial explosion!
		// (on out of memory style crash scales....)
		// Not sure whether to love generics for keeping this mess straight or
		// dislike the way they make it hugely verbose :)
		logger.debug(" Generating teams from " + tempRolesWithCandidates);

		// Get the set of roles for which we've got candidates
		ArrayList<HashMap<ASNRoleNode, Supplier>> possibleSolutions = new ArrayList<HashMap<ASNRoleNode, Supplier>>();
		for (ASNRoleNode node : tempRolesWithCandidates.keySet()) {
			Collection<Supplier> candidates = tempRolesWithCandidates.get(node);
			if (possibleSolutions.isEmpty()) {
				if (candidates.size() > 0) {
					// we need at least so many csns as there are candidates.
					for (Supplier s : candidates) {
						HashMap<ASNRoleNode, Supplier> partialSolution = new HashMap<ASNRoleNode, Supplier>();
						partialSolution.put(node, s);
						possibleSolutions.add(partialSolution);
					}
				} else {
					HashMap<ASNRoleNode, Supplier> newPartialSolution = new HashMap<ASNRoleNode, Supplier>();
					newPartialSolution.put(node, null);
					possibleSolutions.add(newPartialSolution);
				}
			} else {
				ArrayList<HashMap<ASNRoleNode, Supplier>> newPartialSolutions = new ArrayList<HashMap<ASNRoleNode, Supplier>>();
				for (HashMap<ASNRoleNode, Supplier> partialSolution : possibleSolutions) {
					// if we got candidates:
					if (candidates.size() > 0) {
						for (Supplier s : candidates) {
							// the first supplier is added to all available
							// partial solutions
							if (!partialSolution.containsKey(node)) {
								// this partial Solution does not have this node
								partialSolution.put(node, s);
							} else {
								// so we got already a partial solution with
								// this node
								// make a swallow copy of it and replace the
								// supplier for that node
								HashMap<ASNRoleNode, Supplier> newPartialSolution = new HashMap<ASNRoleNode, Supplier>(
										partialSolution);
								newPartialSolution.put(node, s);
								newPartialSolutions.add(newPartialSolution);
							}
						}
					} else {
						HashMap<ASNRoleNode, Supplier> newPartialSolution = new HashMap<ASNRoleNode, Supplier>(
								partialSolution);
						newPartialSolution.put(node, null);
						newPartialSolutions.add(newPartialSolution);
					}
				}
				// we now have added for the first partial solution a supplier
				// for the current node
				// for all other suppliers we cloned the partialSolution and
				// replaced the supplier for that node
				possibleSolutions.addAll(newPartialSolutions);
			}
		}

		ArrayList<ConcreteSupplyNetwork> concreteSupplyNetworks = new ArrayList<ConcreteSupplyNetwork>(
				possibleSolutions.size());
		for (HashMap<ASNRoleNode, Supplier> csn : possibleSolutions) {
			Map<ASNRoleNode, Supplier> candidates = new HashMap<ASNRoleNode, Supplier>();
			for (ASNRoleNode rn : csn.keySet()) {
				// we don't clone the key on purpose; lets see if it works.
				// but we definitely need to clone the supplier object
				Supplier c = csn.get(rn);
				// might be null if we don't find one
				if (c != null)
					candidates.put(rn, c.limitedClone());
				else
					candidates.put(rn, null);
			}

			// logger.debug(" List of candidates for nodes is " + candidates);
			concreteSupplyNetworks.add((new ConcreteSupplyNetwork(ASNin,
					candidates)).clone());
		}

		logger.debug(" Top down TF has produced "
				+ concreteSupplyNetworks.size() + " teams ");
		return concreteSupplyNetworks;
	}

	/*
	 * Get the scores of suppliers vs nodes and throw out those not reaching at
	 * least 80% of the best one
	 */
	private Map<ASNRoleNode, Collection<Supplier>> reduceByScore(
			Map<ASNRoleNode, Collection<Supplier>> tempRolesWithCandidates,
			PMController controllerForEvaluation) {
		Map<ASNRoleNode, Collection<Supplier>> evenMoreTempRolesWithCandidates = new HashMap<ASNRoleNode, Collection<Supplier>>();
		Collection<Supplier> tempSuppliersScoringEnough = new ArrayList<Supplier>();
		double bestScore;
		double tempScore;
		// Apologies for the algorithm!
		for (ASNRoleNode node : tempRolesWithCandidates.keySet()) {
			// -2 as -1 is the 'error' condition in the current evaluation
			// method.
			bestScore = -2;
			tempSuppliersScoringEnough = new ArrayList<Supplier>();
			for (Supplier s : tempRolesWithCandidates.get(node)) {
				tempScore = controllerForEvaluation.evaluate(node
						.getCompetenceNeeded(), s.getOrganisation());
				logger.debug(" Looking at supplier " + s + "with score "
						+ tempScore);
				if (tempScore > bestScore && (tempScore != -1)) {
					bestScore = tempScore;
				}
			}

			logger.debug(" Best Score " + bestScore);

			for (Supplier s : tempRolesWithCandidates.get(node)) {
				tempScore = controllerForEvaluation.evaluate(node
						.getCompetenceNeeded(), s.getOrganisation());
				logger.debug(" Looking at supplier " + s + "with score "
						+ tempScore + " compared to "
						+ (bestScore * (CutOffPercentage / 100)));
				if (tempScore >= (bestScore * (CutOffPercentage / 100))) {
					tempSuppliersScoringEnough.add(s);
					logger
							.debug(" passed as close enoguh to bestScore cut off "
									+ (bestScore * (CutOffPercentage / 100)));
				}
			}

			logger.debug("Candidates within best score "
					+ tempSuppliersScoringEnough);

			evenMoreTempRolesWithCandidates.put(node,
					tempSuppliersScoringEnough);
		}

		return evenMoreTempRolesWithCandidates;
	}

	/*
	 * Take a list of >2 organisations and reduce it to two using extra
	 * qualifications from the ontology. *Currently* not used as the evaluation
	 * style reduction along these lines should probably be effective enough.
	 */
	private ArrayList<Organization> reduceUsingOntology(
			ArrayList<Organization> tempCandidateOrganisations,
			QualificationProfile tempQP,
			ProductMaterialSupportingServices_Service materialSupportingServices_ServiceIn) {
		// Get the list of all suppliers who can do one of the materials used
		// for the node required
		// A horribly inneficient piece of code this but the code to do
		// otherwise would look a bit odd.
		List<String> tempMaterials;
		Material tempMaterial;
		List<Organization> orgsWhoCanDoMaterial = new ArrayList<Organization>();
		tempMaterials = tempQP.getPotentialMaterialURIs();
		for (String s : tempMaterials) {
			tempMaterial = new Material();
			tempMaterial.setName(Thing.getShortName(s));
			orgsWhoCanDoMaterial.addAll(materialSupportingServices_ServiceIn
					.retrieveCanDo(tempMaterial));
		}

		ArrayList<Organization> evenMoreTempCandidateOrganisations = new ArrayList<Organization>();
		for (Organization o : tempCandidateOrganisations) {
			// Restrict by expert in materials/processing techniques linked to
			// the individual which started it off
			// PMS restriction we can't do!
			if (orgsWhoCanDoMaterial.contains(o)) {
				evenMoreTempCandidateOrganisations.add(o);
			}
		}

		// Interesting dillema here - yes we've trimmed down the organisations
		// but what if we lost *all* of them?
		// Not straightforward but sanest to put every supplier back I think :)
		if (evenMoreTempCandidateOrganisations.size() == 0) {
			evenMoreTempCandidateOrganisations = tempCandidateOrganisations;
		}

		// Now check if still >2 suppliers and if so try trimming by production
		// techniques instead
		// Apparently technology (code) == processing technique (Ontology)
		// The same inneficient method as above.
		if (tempCandidateOrganisations.size() > 2) {

			evenMoreTempCandidateOrganisations = new ArrayList<Organization>();
			List<Organization> organisationsWithTech = new ArrayList<Organization>();
			List<String> tempProductionTechniques = tempQP
					.getPotentialProcessingMethodURIs();
			Technology tempTech;
			for (String p : tempProductionTechniques) {
				tempTech = new Technology();
				tempTech.setName(Thing.getShortName(p));
				organisationsWithTech
						.addAll(materialSupportingServices_ServiceIn
								.retrieveCanDo(tempTech));
			}

			evenMoreTempCandidateOrganisations = new ArrayList<Organization>();
			for (Organization o : tempCandidateOrganisations) {
				// Restrict by expert in materials/processing techniques linked
				// to the individual which started it off
				// PMS restriction we can't do!
				if (organisationsWithTech.contains(o)) {
					evenMoreTempCandidateOrganisations.add(o);
				}
			}

			// Interesting dillema here - yes we've trimmed down the
			// organisations but what if we lost *all* of them?
			// Not straightforward but sanest to put every supplier back I think
			// :)
			if (evenMoreTempCandidateOrganisations.size() == 0) {
				evenMoreTempCandidateOrganisations = tempCandidateOrganisations;
			}
		}
		return evenMoreTempCandidateOrganisations;
	}

	/*
	 * A list ditch reduction - only consider multiple suppliers for n nodes.
	 * Basically the only way to be sure don't get too many results.
	 */
	private Map<ASNRoleNode, Collection<Supplier>> doFinalReduction(
			Map<ASNRoleNode, Collection<Supplier>> tempRolesWithCandidates) {
		Map<ASNRoleNode, Collection<Supplier>> evenMoreTempRolesWithCandidates = new HashMap<ASNRoleNode, Collection<Supplier>>();
		ArrayList<Supplier> tempCandidates;
		int i = 0;
		for (ASNRoleNode r : tempRolesWithCandidates.keySet()) {
			if ((i < MaxDoubleNodesConsidered)) {
				evenMoreTempRolesWithCandidates.put(r, tempRolesWithCandidates
						.get(r));
				if (tempRolesWithCandidates.get(r).size() > 1) {
					i++;
				}
			} else {
				tempCandidates = new ArrayList<Supplier>(
						tempRolesWithCandidates.get(r));
				if (tempCandidates.size() != 0) {
					evenMoreTempRolesWithCandidates.put(r, tempCandidates
							.subList(0, 1));
				} else {
					evenMoreTempRolesWithCandidates.put(r,
							new ArrayList<Supplier>());
				}
			}
		}

		return evenMoreTempRolesWithCandidates;
	}

	/*
	 * Looks through the node list, sees chances to put suppliers in > 1 place
	 * and does so when possible. Doesn't try to ensure the 'best' match overall
	 * but doesn't do badly.
	 */
	private Map<ASNRoleNode, Collection<Supplier>> doBundling(
			Map<ASNRoleNode, Collection<Supplier>> tempRolesWithCandidates) {
		// 1b) Bundling - just to keep Nikolay happy :) Very hard to test given
		// the current data though.
		// It won't activate for the moment I suppose.
		Map<ASNRoleNode, Collection<Supplier>> evenMoreTempRolesWithCandidates = new HashMap<ASNRoleNode, Collection<Supplier>>();
		boolean matched;
		boolean anyMatch;
		for (ASNRoleNode tempRoleNode : tempRolesWithCandidates.keySet()) {
			anyMatch = false;
			// A while loop so that we only ever resolve one shared
			// supplier/node.
			// Its theoretically possible to have 2 different ones of course.
			// Not worth hunting for the 'larger' match though so just take
			// first one we see.
			Iterator<Supplier> overSuppliers = tempRolesWithCandidates.get(
					tempRoleNode).iterator();
			Supplier s = null;
			while (overSuppliers.hasNext() && !anyMatch) {
				s = overSuppliers.next();

				Iterator<ASNRoleNode> overRoleNodes = tempRolesWithCandidates
						.keySet().iterator();
				while (overRoleNodes.hasNext() && !anyMatch) {
					ASNRoleNode tempRoleNodeB = overRoleNodes.next();
					matched = false;
					if (!tempRoleNodeB.getproductType().equals(
							tempRoleNode.getproductType())) {
						Iterator<Supplier> overSuppliersB = tempRolesWithCandidates
								.get(tempRoleNodeB).iterator();
						while (overSuppliersB.hasNext() && !matched) {
							Supplier ss = overSuppliersB.next();
							matched = (s.getOrganisation().getName().equals(ss
									.getOrganisation().getName()));
						}

						// 2 nodes with same candidate suppliers found - assign
						// the 'shared' supplier as sole choice for both of them
						if (matched) {
							anyMatch = true;
							ArrayList<Supplier> supplier = new ArrayList<Supplier>();
							supplier.add(s);
							evenMoreTempRolesWithCandidates.put(tempRoleNodeB,
									supplier);
						}
					}
				}
			}
			if (!evenMoreTempRolesWithCandidates.containsKey(tempRoleNode)) {
				if (anyMatch) {
					ArrayList<Supplier> supplier = new ArrayList<Supplier>();
					supplier.add(s);
					evenMoreTempRolesWithCandidates.put(tempRoleNode, supplier);
				} else {
					evenMoreTempRolesWithCandidates.put(tempRoleNode,
							tempRolesWithCandidates.get(tempRoleNode));
				}
			}

		}
		return evenMoreTempRolesWithCandidates;
	}

}
