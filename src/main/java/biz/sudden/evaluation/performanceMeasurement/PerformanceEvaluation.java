package biz.sudden.evaluation.performanceMeasurement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.domain.connectable.Value;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.ConnectableUserObject;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.evaluation.performanceMeasurement.domain.AggregationFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeededByNetworkEvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.domain.EvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.domain.ParameterizedCompetence;
import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public class PerformanceEvaluation {

	static Logger logger = Logger.getLogger(PerformanceEvaluation.class);

	// public static double PENALTY_FOR_NOT_MEETING_COMPETENCE = -10.0d;

	/**
	 * First a Hash with the Organisation.name as key then a Hash with the
	 * profile name as key
	 */
	private static HashMap<String, HashMap<String, Double>> lastValuesCacheOrganisations = new HashMap<String, HashMap<String, Double>>();

	public static void clearCacheForOrganisation(String org_name) {
		HashMap<String, Double> orgvalues = lastValuesCacheOrganisations
				.remove(org_name);
		if (orgvalues != null)
			orgvalues.clear();
	}

	/** this updates the EvaluationProfile object but not in the repository! */
	public static Double evaluate(CompetenceNeeded profileOrDimensionNeeded,
			Organization org, ConnectableService connectableService,
			EnterpriseEvaluationService enterpriseService, Scope scope,
			Scope orgscope) {
		double result = -1.0d; // Double.MIN_VALUE;
		if (profileOrDimensionNeeded != null
				&& profileOrDimensionNeeded.getTypeOfCompetenceProfile() != null) {
			if (profileOrDimensionNeeded.getTypeOfCompetenceProfile() == CompetenceNeeded.COMPETENCE_VALUE) {
				// FIXME: VG: I need the CompetenceInstance identified by its ID
				// (Long)
				// not by its dimension!
				result = evaluate(enterpriseService.getCompetenceService()
						.retrieveDimensionById(
								profileOrDimensionNeeded
										.getIdOfCompetenceProfile()), org,
						connectableService, enterpriseService, orgscope);
			} else if (profileOrDimensionNeeded.getTypeOfCompetenceProfile() == CompetenceNeeded.ENTERPRISE_EVALUATION_PROFILE) {
				result = evaluate(enterpriseService
						.retrieveEnterpriseProfile(profileOrDimensionNeeded
								.getIdOfCompetenceProfile()), org,
						connectableService, enterpriseService, scope, orgscope);
			}
			if ((profileOrDimensionNeeded.getMinValue() != null && result < profileOrDimensionNeeded
					.getMinValue())
					|| (profileOrDimensionNeeded.getMaxValue() != null && result > profileOrDimensionNeeded
							.getMaxValue()))
				// result -= PENALTY_FOR_NOT_MEETING_COMPETENCE;
				result = -1.0d;
		}
		return result;
	}

	// FIXME: VG: check this!
	/** this updates the EvaluationProfile object but not in the repository! */
	public static Double evaluate(ConnectableUserObject profileOrDimension,
			Organization org, ConnectableService connectableService,
			EnterpriseEvaluationService enterpriseService, Scope scope,
			Scope orgscope) {

		if (profileOrDimension.getReference() instanceof EvaluationProfile) {
			return evaluate((EvaluationProfile) profileOrDimension
					.getReference(), org, connectableService,
					enterpriseService, scope, orgscope);
		} else if (profileOrDimension.getReference() instanceof Dimension) {
			return evaluate((Dimension) profileOrDimension.getReference(), org,
					connectableService, enterpriseService, orgscope);
		}
		logger.error("Can not evaluate Connectable: "
				+ profileOrDimension.getReference() + "  of type: "
				+ profileOrDimension.getReference().getClass().getName());
		return -1.0d;

	}

	public static Double evaluate(ConcreteSupplyNetwork net,
			ConnectableService connectableService,
			EnterpriseEvaluationService enterpriseService,
			ScopeController scopeController) {
		double result = -1.0d;
		if (net.getASN() != null
				&& net.getASN().getASNEvaluationProfile() != null
				&& net.getCandidateSuppliers() != null) {
			List<CompetenceNeededByNetworkEvaluationProfile> neps = net
					.getASN().getASNEvaluationProfile().getAllCompetences();
			Map<CompetenceNeededByNetworkEvaluationProfile, Integer> nepCount = new HashMap<CompetenceNeededByNetworkEvaluationProfile, Integer>();
			// EVALUATE individual suppliers and how good they fit the role
			// also take into account competences on network level
			double nodes = net.getCandidateSuppliers().size();
			result = 0.0d;
			for (ASNRoleNode node : net.getCandidateSuppliers().keySet()) {
				Supplier xx = net.getCandidateSuppliers().get(node);
				if (xx != null) {
					Organization x = xx.getOrganisation();
					double value = 0d;
					if (x != null) {
						// evaluate fit for role
						// MartinC - emergency change to avoid NPE crashes.
						// Hibernate issues may be causing the competence needed
						// fields to be lost.
						if (node.getCompetenceNeeded() != null) {
							if (node.getCompetenceNeeded().getValue(x) == null)
								node.getCompetenceNeeded().setValue(
										x,
										evaluate(node.getCompetenceNeeded(), x,
												connectableService,
												enterpriseService,
												scopeController.getUserScope(),
												scopeController.retrieveScope(x
														.getName())));
							value = node.getCompetenceNeeded().getValue(x);
						}
					}
					if (nodes > 0)
						result += (value / nodes);
					else
						result = -1.0d;

					// check competences all need to have
					for (CompetenceNeededByNetworkEvaluationProfile nep : neps) {
						if (nep != null
								&& (nep.getMaxValue() == null || nep
										.getMaxValue() >= value)
								&& (nep.getMinValue() == null || nep
										.getMinValue() <= value)) {
							Integer count = nepCount.get(nep);
							if (count == null) {
								nepCount.put(nep, new Integer(1));
							} else {
								nepCount.put(nep, count + 1);
							}
						}
					}
				}
			}
			// LETS check if we satisfy all constraints:

			for (CompetenceNeededByNetworkEvaluationProfile nep : nepCount
					.keySet()) {
				Integer count = nepCount.get(nep);
				if (count != null) {
					if (nep.getNumberOfSuppliersNeedCompetence() > count
							|| (nep.getNumberOfSuppliersNeedCompetence() == -1 && count < nepCount
									.size())) {
						// result -= PENALTY_FOR_NOT_MEETING_COMPETENCE;
						result = -1.0d;
					}
				}
			}
		} else {
			if (net.getASN() == null) {
				logger
						.error("ASN == null!!! no evaluation possible!!!: for ConcreteSupplyNetwork:"
								+ net.getId());
			}
			if (net.getASN().getASNEvaluationProfile() == null) {
				logger
						.error("EvaluationProfile == null!!! no evaluation possible!!!: for ConcreteSupplyNetwork:"
								+ net.getId()
								+ "  for ASN: "
								+ net.getASN().getId()
								+ " global product: "
								+ net.getASN().getGoalProductString());
			}
		}

		return check(result);
	}

	/**
	 * this updates the EvaluationProfile object and adds occurences for the
	 * values scope is used for retrieving the structure of the evaluation
	 * profile (i.e. the sub-elements) if this produces no subelements the
	 * unspecified scope is used as fall back.
	 * */
	public static Double evaluate(EvaluationProfile profile, Organization org,
			ConnectableService connectableService,
			EnterpriseEvaluationService enterpriseService, Scope scope,
			Scope orgscope) {

		// we now assume that if there is an occurence it is current! so we are
		// not
		// recalculating
		Double result = getLastValue(profile, connectableService, orgscope);
		if (result == null || result < 0.1d) {
			// no result; so re-calculate the value
			List<ParameterizedCompetence> subelements = retrieveSubElements(
					profile, connectableService, scope, connectableService
							.getUnspecifiedScope());

			List<Object> parameters = new ArrayList<Object>();
			List<Double> values = new ArrayList<Double>();
			if (subelements.size() > 0
					&& subelements.get(0).getFunction() != null) {
				for (ParameterizedCompetence subelement : subelements) {
					if (subelement.getEvaluationProfile() != null) {
						// check if there are occurences of this evaluation
						// profile! and
						// take the latest value
						result = null;//
						if (result != null && result > 0.1d) {
							values.add(result);
						} else {
							// update the value of the subprofile
							result = evaluate(
									subelement.getEvaluationProfile(), org,
									connectableService, enterpriseService,
									scope, orgscope);
							values.add(result);
						}

						if (subelement.getParameter() != null) {
							parameters.add(subelement.getParameter());
						} else {
							parameters.add(new Double(1.01d));
						}
					} else if (subelement.getCompetenceDimension() != null) {
						values
								.add(evaluate(subelement
										.getCompetenceDimension(), org,
										connectableService, enterpriseService,
										orgscope));
						if (subelement.getParameter() != null) {
							parameters.add(subelement.getParameter());
						} else {
							parameters.add(new Double(1.01d));
						}
					}
				}
				result = subelements.get(0).getFunction().calculate(parameters,
						values);
				// check if we have the same value already
				if (!result.equals(getLastValue(profile, connectableService,
						orgscope)))
					// update the Occurence of the Profile with the new created
					// value
					enterpriseService.associateOccurence(Double
							.toString(result), profile, "Double", orgscope,
							true);
			} else {
				// no subelements
				result = getLastValue(profile, connectableService, orgscope);
			}
		}
		// profile.setResult(result);
		if (result == null || result.equals(0.0d)) {
			result = new Double(-1.0d);
		}
		return check(result);

	}

	// /** this updates the EvaluationProfile object and adds occurrences! */
	// public static Double updateEvalProfile(EvaluationProfile profile,
	// Organization org,
	// ConnectableService connectableService, ICMInstancesManagement_Service
	// competenceService, EnterpriseEvaluationService enterpriseService, Scope
	// scope,
	// Scope orgscope) {
	// Double result = null;
	// List<ParameterizedCompetence> subelements = retrieveSubElements(profile,
	// connectableService, scope, connectableService.getUnspecifiedScope());
	// List<Object> parameters = new ArrayList<Object>();
	// List<Double> values = new ArrayList<Double>();
	// if (subelements.size() > 0 && subelements.get(0).getFunction() != null) {
	// for (ParameterizedCompetence subelement : subelements) {
	// if (subelement.getEvaluationProfile() != null) {
	// // update the value of the subprofile
	// result = updateEvalProfile(subelement.getEvaluationProfile(), org,
	// connectableService, competenceService, enterpriseService, scope,
	// orgscope);
	// values.add(result);
	//
	// if (subelement.getParameter() != null) {
	// parameters.add(subelement.getParameter());
	// } else {
	// parameters.add(new Double(1.01d));
	// }
	// } else if (subelement.getCompetenceDimension() != null) {
	// result = updateCompetenceDimension(subelement.getCompetenceDimension(),
	// org, connectableService, enterpriseService, competenceService, orgscope);
	// values.add(result);
	// if (subelement.getParameter() != null) {
	// parameters.add(subelement.getParameter());
	// } else {
	// parameters.add(new Double(1.01d));
	// }
	// }
	// }
	// }
	// result = subelements.get(0).getFunction().calculate(parameters, values);
	// // check if we have the same value already
	// if(! result.equals(getLastValue(profile, connectableService, orgscope)))
	// // update the Occurence of the Profile with the new created value
	// enterpriseService.associateOccurence(Double.toString(result), profile,
	// "Double", orgscope);
	//
	// return result;
	// }

	// public static Double updateCompetenceDimension(Dimension competence,
	// Organization org, ConnectableService connectableService,
	// EnterpriseEvaluationService enterpriseService,
	// ICMInstancesManagement_Service instanceService, Scope orgscope) {
	//
	// // FIXME: VG: getWeight is the actual value?? of the DimensionInstance??
	// // or getValue?
	// DimensionInstance di =
	// instanceService.retrieveDimensionInstanceByOrganization(org.getId(),
	// competence.getId());
	// Double resultD = -1.0d;
	// if(di!=null) {
	// String result = di.getValue();
	// resultD = Double.parseDouble(result);
	// if(!resultD.equals(getLastValue(competence,connectableService,
	// orgscope)))
	// enterpriseService.associateOccurence(result, competence, "Double",
	// orgscope);
	// }
	// return resultD;
	// }

	// private static Double check(String key, Double value) {
	// boolean mod = false;
	// if(value < -1 || (value < 0 && value > -1)) {
	// logger.error("Value is not within range 0-10 & -1 : " + value);
	// value = -1.0d;
	// mod = true;
	// }
	// if (value>10){
	// logger.error("Value is not within range 0-10 & -1 : " + value);
	// value = value % 10d;
	// mod = true;
	// }
	// // if(mod && key!=null)
	// // lastValuesCache.put(key, value);
	// return value;
	// }

	private static Double check(Double value) {
		if (value < -1 || (value < 0 && value > -1)) {
			logger.error("Value is not within range 0-10 & -1 : " + value);
			value = -1.0d;
		}
		if (value > 10) {
			logger.error("Value is not within range 0-10 & -1 : " + value);
			value = value % 10d;
		}
		return value;
	}

	private static Double check(Double value, String profilename,
			HashMap<String, Double> orgvalues, String orgname) {
		boolean mod = false;
		if (value < -1 || (value < 0 && value > -1)) {
			logger.error("Value is not within range 0-10 & -1 : " + value);
			value = -1.0d;
			mod = true;
		}
		if (value > 10) {
			logger.error("Value is not within range 0-10 & -1 : " + value);
			value = value % 10d;
			mod = true;
		}
		if (value != null && profilename != null && orgvalues != null) {
			orgvalues.put(profilename, value);
		} else if (value != null && profilename != null) {
			orgvalues = new HashMap<String, Double>();
			orgvalues.put(profilename, value);
			lastValuesCacheOrganisations.put(orgname, orgvalues);
		} else {
			logger.error("something went wrong during evaluation!!");
		}
		return value;

	}

	protected static Double getLastValue(EvaluationProfile profile,
			ConnectableService connectableService, Scope orgscope) {
		return getLastValue(profile, profile.getName() + profile.getId(),
				connectableService, orgscope);
	}

	protected static Double getLastValue(Dimension dimension,
			ConnectableService connectableService, Scope orgscope) {
		return getLastValue(dimension, dimension.getName() + dimension.getId(),
				connectableService, orgscope);
	}

	protected static Double getLastValue(Connectable profile,
			String profilenameAndID, ConnectableService connectableService,
			Scope orgscope) {
		Value<?> result = null;
		Date lastOcc = null;
		if (profile != null && orgscope != null && connectableService != null) {
			HashMap<String, Double> orgvalues = lastValuesCacheOrganisations
					.get(orgscope.getName());
			Double cached = null;
			if (orgvalues != null)
				cached = orgvalues.get(profilenameAndID);
			if (cached != null)
				return check(cached, profilenameAndID, orgvalues, orgscope
						.getName());

			for (Occurrence occ : connectableService.retrieveOccurrences(
					profile, orgscope)) {
				if (lastOcc == null || lastOcc.before(occ.getCreationDate())) {
					lastOcc = occ.getCreationDate();
					result = occ.getValue();
				}
			}
			if (result != null) {
				cached = Double.parseDouble(result.getValue().toString());
				return check(cached, profilenameAndID, orgvalues, orgscope
						.getName());
			} else
				return null;
		} else
			return null;
	}

	// protected static Double getLastValue(Connectable profile,
	// ConnectableService connectableService, Scope orgscope) {
	// Value<?> result = null;
	// Date lastOcc = null;
	// if(profile!=null && orgscope !=null && connectableService!=null){
	// String key = new
	// StringBuffer().append(profile.getId()).append('-').append(orgscope.getId()).toString();
	//  		
	// // Double cached = lastValuesCache.get(key);
	// // if (cached != null) {
	// // return check(key,cached);
	// // }
	// for (Occurrence occ : connectableService.retrieveOccurrences(profile,
	// orgscope)) {
	// if (lastOcc == null || lastOcc.before(occ.getCreationDate())) {
	// lastOcc = occ.getCreationDate();
	// result = occ.getValue();
	// }
	// }
	// if (result != null) {
	// Double cached = Double.parseDouble(result.getValue().toString());
	// //lastValuesCache.put(key, cached);
	// return check(key,cached);
	// } else
	// return null;
	// } else
	// return null;
	// }

	public static Double evaluate(Dimension dimension, Organization org,
			ConnectableService connectableService,
			EnterpriseEvaluationService enterpriseService, Scope orgscope) {
		// check if there are occurences of this competence dimension! and take
		// the
		// latest value
		Double currentOccurrence = getLastValue(dimension, connectableService,
				orgscope);
		// DimensionInstance di
		// =enterpriseService.getCompetenceInstanceService().retrieveDimensionInstanceByOrganization(org.getId(),
		// dimension.getId());
		// if(di!=null) {
		// String result = di.getValue();
		// if (currentOccurrence == null) {
		// // FIXME: VG: getWeight is the actual value?? of the
		// DimensionInstance??
		// // or getValue?
		// enterpriseService.associateOccurence(result, dimension, "Double",
		// orgscope);
		// currentOccurrence=Double.parseDouble(result);
		// } else {
		// // compare if the last value is the current one (only required with
		// competences;
		// // FIXME: VG: getWeight is the actual value?? of the
		// DimensionInstance??
		// if (!currentOccurrence.equals(Double.parseDouble(result)))
		// enterpriseService.associateOccurence(result, dimension, "Double",
		// orgscope);
		// currentOccurrence=Double.parseDouble(result);
		// }
		// }
		return currentOccurrence;
	}

	/**
	 * Retrieve the subelements of a evaluation profile in the given scope ; if
	 * with a given scope no sibelements are found the unspecified scope is used
	 * as falback
	 * 
	 * @param eprofile
	 * @param connectableService
	 * @param scope
	 * @return
	 */
	public static List<ParameterizedCompetence> retrieveSubElements(
			EvaluationProfile eprofile, ConnectableService connectableService,
			Scope scope, Scope unspecifiedscope) {
		List<AssociationRole> roles = connectableService
				.retrieveCounterpartAssociationRoles(eprofile,
						EvaluationProfile.ResultRoleType, scope);
		if (roles.size() == 0)
			roles = connectableService.retrieveCounterpartAssociationRoles(
					eprofile, EvaluationProfile.ResultRoleType,
					unspecifiedscope);
		List<ParameterizedCompetence> result = new ArrayList<ParameterizedCompetence>();
		for (int i = 0; i < roles.size(); ++i) {
			ParameterizedCompetence wc = new ParameterizedCompetence();
			wc.setParameter(Double
					.parseDouble(roles.get(i).getType().getType()));
			wc.setFunction((AggregationFunction) roles.get(i).getParent()
					.getType());
			Connectable sub = roles.get(i).getPlayer();
			if (sub instanceof EnterpriseEvaluationProfile) {
				wc.setEvaluationProfile((EnterpriseEvaluationProfile) sub);
			} else if (sub instanceof Dimension) {
				wc.setCompetenceDimension((Dimension) sub);
			} else {
				logger
						.error("EnterpriseEvaluationServiceImpl.retrieveSubElements");
			}
			result.add(wc);
		}
		return result;
	}
}
