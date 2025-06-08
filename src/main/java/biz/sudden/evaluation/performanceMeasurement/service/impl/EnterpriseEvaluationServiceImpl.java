/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.sudden.evaluation.performanceMeasurement.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.domain.connectable.StringValue;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.ConnectableUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.baseAndUtility.web.controller.tree.TreeDragDrop;
import biz.sudden.baseAndUtility.web.controller.tree.UserObject;
import biz.sudden.evaluation.performanceMeasurement.PerformanceEvaluation;
import biz.sudden.evaluation.performanceMeasurement.domain.AggregationFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.DivisionFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.EvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.domain.MaxFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.MinFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.MinusFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.MultiplicationFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.PerformanceTree;
import biz.sudden.evaluation.performanceMeasurement.domain.SumFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.WeightedSumFunction;
import biz.sudden.evaluation.performanceMeasurement.repository.AggregationFunctionRepository;
import biz.sudden.evaluation.performanceMeasurement.repository.EnterpriseEvaluationProfileRepository;
import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMInstancesManagement_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.service.IOrganization;

/**
 * 
 * @author gweich
 */
public class EnterpriseEvaluationServiceImpl implements
		EnterpriseEvaluationService {

	private Logger logger = Logger.getLogger(this.getClass());

	protected ConnectableService connectableService = null;
	protected EnterpriseEvaluationProfileRepository enterpriseEvaluationProfileRepository = null;
	protected AggregationFunctionRepository aggregationFunctionRepository = null;
	protected ICMCompetencesManagement_Service competenceValueService = null;
	protected ICMInstancesManagement_Service competenceInstanceService = null;
	protected IOrganization orgMgmtService = null;
	// protected Scope currentScope = null;

	protected Hashtable<String, TreeDragDrop> trees;

	protected Hashtable<String, PerformanceTree> profileTreesForEvaluation;
	protected Hashtable<String, Organization> selectedOrganisationsForEvaluation;
	protected Hashtable<String, EnterpriseEvaluationProfile> selectedEnterpriseEvaluationProfile;

	private boolean doCreateFunctions = true;

	public EnterpriseEvaluationServiceImpl() {
		logger.debug("EnterpriseEvaluationServiceImpl -> cst");
		trees = new Hashtable<String, TreeDragDrop>();
		profileTreesForEvaluation = new Hashtable<String, PerformanceTree>();
		selectedOrganisationsForEvaluation = new Hashtable<String, Organization>();
		selectedEnterpriseEvaluationProfile = new Hashtable<String, EnterpriseEvaluationProfile>();
	}

	/**
	 * sets also the currentScope to the universal scope of the connectable
	 * service
	 */
	@Override
	public void setConnectableService(ConnectableService connectableService) {
		logger
				.debug("EnterpriseEvaluationServiceImpl -> setConnectableService");
		this.connectableService = connectableService;
	}

	@Override
	public ConnectableService getConnectableService() {
		return connectableService;
	}

	@Override
	public void setEnterpriseEvaluationProfileRepository(
			EnterpriseEvaluationProfileRepository rep) {
		logger
				.debug("EnterpriseEvaluationServiceImpl -> setEnterpriseEvaluationProfileRepository");
		this.enterpriseEvaluationProfileRepository = rep;
	}

	@Override
	public void setAggregationFunctionRepository(
			AggregationFunctionRepository rep) {
		logger
				.debug("EnterpriseEvaluationServiceImpl -> setAggregationFunctionRepository");
		this.aggregationFunctionRepository = rep;
	}

	@Override
	public void setCompetenceService(
			ICMCompetencesManagement_Service CompetenceService) {
		logger.debug("EnterpriseEvaluationServiceImpl -> setCompetenceService");
		this.competenceValueService = CompetenceService;

		if (competenceInstanceService != null)
			competenceInstanceService
					.setCmCompetencesManagement_Service(competenceValueService);
	}

	@Override
	public ICMCompetencesManagement_Service getCompetenceService() {
		return this.competenceValueService;
	}

	@Override
	public void setCompetenceInstanceService(
			ICMInstancesManagement_Service CompetenceService) {
		logger
				.debug("EnterpriseEvaluationServiceImpl -> setCompetenceInstanceService");
		this.competenceInstanceService = CompetenceService;
		if (competenceValueService != null)
			competenceInstanceService
					.setCmCompetencesManagement_Service(competenceValueService);

	}

	@Override
	public ICMInstancesManagement_Service getCompetenceInstanceService() {
		return this.competenceInstanceService;
	}

	@Override
	public void setOrganisationManagementService(IOrganization orgMgmtService) {
		logger
				.debug("EnterpriseEvaluationServiceImpl -> setOrganisationManagementService");
		this.orgMgmtService = orgMgmtService;
	}

	@Override
	public IOrganization getOrganisationManagementService() {
		return orgMgmtService;
	}

	@Override
	public List<Organization> retrieveAllOrganisations() {
		return orgMgmtService.retrieveAll();
	}

	@Override
	public Organization retrieveOrganisation(Long id) {
		return orgMgmtService.retrieveOrganization(id);
	}

	@Override
	public List<Organization> retrieveOrganisation(String name) {
		List<Organization> all = orgMgmtService.retrieveAll();
		List<Organization> result = new ArrayList<Organization>();
		for (Organization o : all) {
			if (o.getName().toLowerCase().indexOf(name.toLowerCase()) > -1)
				result.add(o);
		}
		return result;
	}

	/**
	 * Creates a new Profile and associates sub-profiles using a certain
	 * function which requires a single parameter per sub-profile also create it
	 * in the repository
	 * 
	 * @param function2useAssocTypeName
	 * @param functionParameters
	 * @param subprofiles
	 * @return
	 */
	@Override
	public EnterpriseEvaluationProfile createOrAssociateEnterpriseProfile(
			String name, String function2useAssocTypeName,
			List functionParameters,
			List<EnterpriseEvaluationProfile> subprofiles, Scope scope) {
		List<EnterpriseEvaluationProfile> eeps = enterpriseEvaluationProfileRepository
				.retrieveEEvaluationProfileBy(name);
		EnterpriseEvaluationProfile prof = null;
		if (eeps == null || eeps.size() == 0) {
			prof = new EnterpriseEvaluationProfile();
			prof.setName(name);
			createEnterpriseProfile(prof);
		} else {
			prof = eeps.get(0);
		}
		prof = connect(prof, function2useAssocTypeName, functionParameters,
				subprofiles, scope);
		return prof;
	}

	@Override
	public EnterpriseEvaluationProfile createEnterpriseProfile(
			EnterpriseEvaluationProfile profile) {
		enterpriseEvaluationProfileRepository.create(profile);
		return profile;
	}

	/**
	 * FIXME: VG: check if CVI is really what we want for evaluation Creates a
	 * new Profile and associates sub-profiles using a certain function which
	 * requires a single parameter per sub-profile also create it in the
	 * repository.
	 * 
	 * @param function2useAssocTypeName
	 * @param functionParameters
	 * @param subprofiles
	 * @return
	 */
	@Override
	public EnterpriseEvaluationProfile createEnterpriseProfileWithCompetences(
			String function2useAssocTypeName, List functionParameters,
			List<Dimension> competenceDimensions, Scope scope) {
		EnterpriseEvaluationProfile result = connect2(
				new EnterpriseEvaluationProfile(), function2useAssocTypeName,
				functionParameters, competenceDimensions, scope);
		enterpriseEvaluationProfileRepository.create(result);
		return result;
	}

	@Override
	public EnterpriseEvaluationProfile createOrAssociateEnterpriseProfile2(
			String name, String function2useAssocTypeName,
			List functionParameters, List<Dimension> competenceDimensions,
			Scope scope) {
		List<EnterpriseEvaluationProfile> eeps = enterpriseEvaluationProfileRepository
				.retrieveEEvaluationProfileBy(name);
		EnterpriseEvaluationProfile prof = null;
		if (eeps == null || eeps.size() == 0) {
			prof = new EnterpriseEvaluationProfile();
			prof.setName(name);
			createEnterpriseProfile(prof);
		} else {
			prof = eeps.get(0);
		}
		prof = connect2(prof, function2useAssocTypeName, functionParameters,
				competenceDimensions, scope);
		return prof;
	}

	@Override
	public AggregationFunction createOrRetrieveEvaluationFunction(
			AggregationFunction function2use) {
		return createOrRetrieveEvaluationFunction(null, function2use);
	}

	@Override
	public AggregationFunction createOrRetrieveEvaluationFunction(
			String AssociationTypeName, AggregationFunction function2use) {
		if (AssociationTypeName != null)
			function2use.setType(AssociationTypeName);
		return aggregationFunctionRepository.createOrRetrieve(function2use);
	}

	@Override
	public EnterpriseEvaluationProfile retrieveEnterpriseProfile(Long id) {
		logger
				.debug("EnterpriseEvaluationServiceImpl: retrieveEnterpriseProfile");
		return enterpriseEvaluationProfileRepository.retrieve(id);
	}

	@Override
	public List<EnterpriseEvaluationProfile> retrieveEnterpriseProfile(
			String name) {
		logger
				.debug("EnterpriseEvaluationServiceImpl: retrieveEnterpriseProfile: "
						+ name);
		if (name == null || name.length() == 0) {
			return enterpriseEvaluationProfileRepository.retrieveAll();
		} else {
			return enterpriseEvaluationProfileRepository
					.retrieveEEvaluationProfileBy(name);
		}
	}

	@Override
	public List<EnterpriseEvaluationProfile> retrieveAllEnterpriseProfiles() {
		logger
				.debug("EnterpriseEvaluationServiceImpl: retrieveAllEnterpriseProfiles");
		return enterpriseEvaluationProfileRepository.retrieveAll();
	}

	// FIXME: VG: check if CVI is really what we want for evaluation
	@Override
	public List<Dimension> retrieveAllCompetenceDimensions() {
		logger
				.debug("EnterpriseEvaluationServiceImpl: retrieveAllCompetenceDimensions");
		return competenceValueService.retrieveAllDimensions();
	}

	@Override
	public List<String> retrieveEvaluationFunctionNames() {
		Iterator<AggregationFunction> i = aggregationFunctionRepository
				.retrieveAll().iterator();
		List<String> names = new ArrayList<String>();
		while (i.hasNext()) {
			names.add(i.next().getType());
		}
		return names;
	}

	@Override
	public EnterpriseEvaluationProfile updateEnterpriseProfile(
			EnterpriseEvaluationProfile profile) {
		logger.debug("EnterpriseEvaluationServiceImpl: updatEnterpriseProfile");
		enterpriseEvaluationProfileRepository.update(profile);
		return profile;
	}

	/**
	 * Takes a Profile and associates sub-profiles using a certain function
	 * which requires a single parameter per sub-profile. and update the
	 * repository
	 * 
	 * @param profile
	 * @param function2useAssocTypeName
	 * @param functionParameters
	 * @param subprofiles
	 * @return
	 */
	@Override
	public EnterpriseEvaluationProfile updateEnterpriseProfile(
			EnterpriseEvaluationProfile profile,
			String function2useAssocTypeName, List functionParameters,
			List<EnterpriseEvaluationProfile> subprofiles, Scope scope) {
		return connect(profile, function2useAssocTypeName, functionParameters,
				subprofiles, scope);
	}

	/**
	 * Takes a Profile and associates sub-profiles using a certain function
	 * which requires a single parameter per sub-profile.and update the
	 * repository
	 * 
	 * @param profile
	 * @param function2useAssocTypeName
	 * @param functionParameters
	 * @param subprofiles
	 * @return
	 */
	@Override
	public EnterpriseEvaluationProfile updateEnterpriseProfileWithCompetences(
			EnterpriseEvaluationProfile profile,
			String function2useAssocTypeName, List functionParameters,
			List<Dimension> competenceDimensions, Scope scope) {
		return updateEnterpriseProfile(connect2(profile,
				function2useAssocTypeName, functionParameters,
				competenceDimensions, scope));
	}

	@Override
	public void deleteEnterpriseProfile(EnterpriseEvaluationProfile profile) {
		logger
				.debug("EnterpriseEvaluationServiceImpl: deleteEnterpriseProfile");
		connectableService.deleteConnectableAssocsOccurrences(profile,
				connectableService.getRetrieveAllScope());
		enterpriseEvaluationProfileRepository.delete(profile);
	}

	@Override
	public AggregationFunction retrieveEvaluationFunction(
			EnterpriseEvaluationProfile eprofile, Scope scope) {
		List<Association> result = connectableService.retrieveAssociationBy(
				eprofile, EvaluationProfile.ResultRoleType, scope);
		if (result.size() > 0) {
			return (AggregationFunction) result.get(0).getType();
		} else {
			return null;
		}
	}

	private EnterpriseEvaluationProfile connect(
			EnterpriseEvaluationProfile profile,
			String function2useAssocTypeName, List functionParameters,
			List<EnterpriseEvaluationProfile> subprofiles, Scope scope) {
		if (doCreateFunctions) {
			createEvalFunctions();
		}
		for (int i = functionParameters.size() < subprofiles.size() ? functionParameters
				.size() - 1
				: subprofiles.size() - 1; i >= 0; --i) {
			connectableService.addToDirected1toNRelation(profile, subprofiles
					.get(i), function2useAssocTypeName,
					EvaluationProfile.ResultRoleType, functionParameters.get(i)
							.toString(), scope);
		}
		return profile;
	}

	/**
	 * The trees are "controlled" by the server, since all users that are in the
	 * same scope should see the same trees
	 */
	@Override
	public TreeDragDrop retrieveTrees(ScopeController scopeController) {
		if (trees.containsKey(scopeController.getUserScope().getName()))
			return trees.get(scopeController.getUserScope().getName());

		TreeDragDrop tdd = new TreeDragDrop(new Tree(connectableService,
				scopeController, "Indicators"), new Tree(connectableService,
				scopeController, "new Profile"));

		trees.put(scopeController.getUserScope().getName(), tdd);
		return tdd;

	}

	@Override
	public PerformanceTree retrievePerformanceTree(ScopeController scopeC) {
		String scope = scopeC.getUserScope().getName();
		PerformanceTree result = profileTreesForEvaluation.get(scope);
		if (selectedEnterpriseEvaluationProfile.get(scope) == null
				&& result != null) {
			Object x = ((DefaultMutableTreeNode) result.getRoot())
					.getUserObject();
			if (x instanceof ConnectableUserObject) {
				Object y = ((ConnectableUserObject) x).getReference();
				if (y instanceof EnterpriseEvaluationProfile) {
					selectedEnterpriseEvaluationProfile.put(scope,
							(EnterpriseEvaluationProfile) y);
				}
			} else if (x != null
					&& ((UserObject) x).getWrapper().getChildCount() > 0) {
				DefaultMutableTreeNode z = (DefaultMutableTreeNode) ((UserObject) x)
						.getWrapper().getChildAt(0);
				if (z != null
						&& z.getUserObject() instanceof ConnectableUserObject
						&& ((ConnectableUserObject) z.getUserObject())
								.getReference() instanceof EnterpriseEvaluationProfile) {
					selectedEnterpriseEvaluationProfile
							.put(
									scope,
									(EnterpriseEvaluationProfile) ((ConnectableUserObject) z
											.getUserObject()).getReference());
				}
			}
		}
		return result;
	}

	@Override
	public void setPerformanceTree(PerformanceTree tree, ScopeController scopeC) {
		profileTreesForEvaluation.put(scopeC.getUserScope().getName(), tree);
	}

	@Override
	public Organization retrieveSelectedOrganisation(ScopeController scopeC) {
		return selectedOrganisationsForEvaluation.get(scopeC.getUserScope()
				.getName());
	}

	@Override
	public void setSelectedOrganisation(Organization o, ScopeController scopeC) {
		Scope us = scopeC.getUserScope();
		selectedOrganisationsForEvaluation.put(us.getName(), o);
		if (o != null) {
			Scope s = scopeC.getScopeService().retrieveScopeBy(o.getName());
			List<Connectable> profiles = new ArrayList<Connectable>(
					retrieveAllEnterpriseProfiles());
			Tree.removeWhenNotInRoleOnly(profiles,
					EvaluationProfile.ResultRoleType, us,
					getConnectableService());
			for (Connectable profile : profiles) {
				PerformanceEvaluation.evaluate(
						(EnterpriseEvaluationProfile) profile, o,
						getConnectableService(), this, us, s);
			}
		}
	}

	@Override
	public EnterpriseEvaluationProfile retrieveSelectedEnterpriseEvaluationProfile(
			ScopeController scopeController) {
		return selectedEnterpriseEvaluationProfile.get(scopeController
				.getUserScope().getName());
	}

	@Override
	public void setSelectedEnterpriseEvaluationProfile(
			EnterpriseEvaluationProfile selectedEnterpriseEvaluationProfile,
			ScopeController scopeController) {
		this.selectedEnterpriseEvaluationProfile.put(scopeController
				.getUserScope().getName(), selectedEnterpriseEvaluationProfile);
	}

	private EnterpriseEvaluationProfile connect2(
			EnterpriseEvaluationProfile profile,
			String function2useAssocTypeName, List functionParameters,
			List<Dimension> competenceValues, Scope scope) {
		if (doCreateFunctions) {
			createEvalFunctions();
		}
		for (int i = functionParameters.size() > competenceValues.size() ? functionParameters
				.size() - 1
				: competenceValues.size() - 1; i >= 0; --i) {
			connectableService.addToDirected1toNRelation(profile,
					competenceValues.get(i), function2useAssocTypeName,
					EvaluationProfile.ResultRoleType, functionParameters.get(i)
							.toString(), scope);
		}
		return profile;
	}

	private void createEvalFunctions() {
		// make shure we have the appropriate functions!
		createOrRetrieveEvaluationFunction(new DivisionFunction());
		createOrRetrieveEvaluationFunction(new MaxFunction());
		createOrRetrieveEvaluationFunction(new MinFunction());
		createOrRetrieveEvaluationFunction(new MinusFunction());
		createOrRetrieveEvaluationFunction(new MultiplicationFunction());
		createOrRetrieveEvaluationFunction(new SumFunction());
		createOrRetrieveEvaluationFunction(new WeightedSumFunction());
		doCreateFunctions = false;
	}

	@Override
	public void associateOccurence(String value, Connectable connectable,
			String type, Scope scope) {
		associateOccurence(value, connectable, type, scope, false);
	}

	@Override
	public void associateOccurence(String value, Connectable connectable,
			String type, Scope scope, boolean dontclearcache) {
		StringValue sv = connectableService.retrieveStringValueBy(value);
		if (sv == null)
			sv = connectableService.createOrRetrieveStringValue(value);
		connectableService.addOccurrence(connectable, type, sv, scope);
		if (!dontclearcache)
			PerformanceEvaluation.clearCacheForOrganisation(scope.getName());
	}

	@Override
	public void associateOccurence(String value, Connectable connectable,
			String type, String scopename) {
		// Note: add an values for Georg's algorithm
		associateOccurence(value, connectable, type, connectableService
				.retrieveScopeBy(scopename));
	}

	@Override
	public Scope retrieveScopeBy(String name) {
		return connectableService.retrieveScopeBy(name);
	}

}
