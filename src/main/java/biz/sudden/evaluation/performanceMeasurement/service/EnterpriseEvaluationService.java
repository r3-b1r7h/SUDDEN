/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.sudden.evaluation.performanceMeasurement.service;

import java.util.List;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.TreeDragDrop;
import biz.sudden.evaluation.performanceMeasurement.domain.AggregationFunction;
import biz.sudden.evaluation.performanceMeasurement.domain.PerformanceTree;
import biz.sudden.evaluation.performanceMeasurement.repository.AggregationFunctionRepository;
import biz.sudden.evaluation.performanceMeasurement.repository.EnterpriseEvaluationProfileRepository;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.service.ICMCompetencesManagement_Service;
import biz.sudden.knowledgeData.competencesManagement.service.ICMInstancesManagement_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.service.IOrganization;

/**
 * The EnterpriseEvaluationService interface provides methods to create,
 * retrieve, update and delete (CRUD) Enterprise Evaluation Profiles. It also
 * allows to apply these to concrete enterprises.
 * 
 * Performance Measurement (PM) provides the context for the Competence
 * Management (CM) subsystem that allows to evaluate the performance for a
 * concrete supplier or a set of suppliers/enterprises. CM deals with the
 * underlying structures (roles, competences, dimensions and competence value
 * indicators) and the calculation method (e.g. a weighted sum function). PM on
 * the other hand provides concrete values for the weights of that calculation
 * method in order to aggregate the individual competence dimension values
 * towards a single overall value for an enterprise (following D3.1)1. The
 * weights and links to individual used competences are stored in a data
 * structure called performance evaluation profile, which is described in the
 * implementation section.
 * 
 * When using PM directly to evaluate suppliers by default all competences and
 * competence dimensions defined are used and aggregated using a weighted sum
 * function which averages all values. In addition to this (e.g. when used by
 * the handle BO subsystem) it is possible to restricted the used competences
 * and competence dimensions to a sub-set of the available dimensions (through
 * the use of a performance evaluation profile). Additionally PM allows to
 * connect competences of individual companies allowing to estimate the
 * performance of supply chains and networks (stored in a network evaluation
 * profile) by aggregating enterprise competence value indicators to a single
 * overall network value. This network evaluation profile has to be created by
 * the user. It allow to specify e.g. that the overall costs are the sum of all
 * costs in a particular context (I.e. for a particular BO); or that the overall
 * value for ISO 9000 certification is the %figure of all suppliers being
 * ISO9000 certified.
 * 
 * @author gweich
 */
public interface EnterpriseEvaluationService {

	/** set the repository to hold the profiles */
	public void setEnterpriseEvaluationProfileRepository(
			EnterpriseEvaluationProfileRepository repository);

	/** set the repository to hold the aggregation/evaluation functions */
	public void setAggregationFunctionRepository(
			AggregationFunctionRepository repository);

	/**
	 * create a new profile and store it in the repository
	 * 
	 * @param scope
	 *            TODO
	 */
	public EnterpriseEvaluationProfile createOrAssociateEnterpriseProfile(
			String name, String function2useAssocTypeName,
			List functionParameters,
			List<EnterpriseEvaluationProfile> subprofiles, Scope scope);

	public EnterpriseEvaluationProfile createOrAssociateEnterpriseProfile2(
			String name, String function2useAssocTypeName,
			List functionParameters, List<Dimension> competenceIDs, Scope scope);

	/** create a new profile and store it in the repository */
	public EnterpriseEvaluationProfile createEnterpriseProfile(
			EnterpriseEvaluationProfile profiles);

	/**
	 * create a new profile and store it in the repository
	 * 
	 * @param scope
	 *            TODO
	 */
	public EnterpriseEvaluationProfile createEnterpriseProfileWithCompetences(
			String function2useAssocTypeName, List functionParameters,
			List<Dimension> competenceIDs, Scope scope);

	/**
	 * a cetain evaluation/aggregation function and the name used for
	 * identification when placed in the topicmap context and modelling the
	 * function as association and create it in the repository
	 */
	public AggregationFunction createOrRetrieveEvaluationFunction(
			String AssociationTypeName, AggregationFunction function2use);

	/** a cetain evaluation/aggregation function where the type property is set */
	public AggregationFunction createOrRetrieveEvaluationFunction(
			AggregationFunction function2use);

	/**
	 * associate sub-profile with a certain profile and update the repository
	 * 
	 */
	public EnterpriseEvaluationProfile updateEnterpriseProfileWithCompetences(
			EnterpriseEvaluationProfile profile,
			String function2useAssocTypeName, List functionParameters,
			List<Dimension> competenceDimensions, Scope scope);

	/**
	 * associate sub-profile with a certain profile and update the repository
	 */
	public EnterpriseEvaluationProfile updateEnterpriseProfile(
			EnterpriseEvaluationProfile profile,
			String function2useAssocTypeName, List functionParameters,
			List<EnterpriseEvaluationProfile> subprofiles, Scope scope);

	/** get a particular EnterpriseEvaluationProfile from the repository */
	public EnterpriseEvaluationProfile retrieveEnterpriseProfile(Long id);

	/**
	 * get all EnterpriseEvaluationProfiles from the repository that match a
	 * certain name
	 */
	public List<EnterpriseEvaluationProfile> retrieveEnterpriseProfile(
			String name);

	/** get all EnterpriseEvaluationProfiles from the repository */
	public List<EnterpriseEvaluationProfile> retrieveAllEnterpriseProfiles();

	/** retrieve the names of the evaluation functions that are known */
	public List<String> retrieveEvaluationFunctionNames();

	/** update a modified profile in the repostory */
	public EnterpriseEvaluationProfile updateEnterpriseProfile(
			EnterpriseEvaluationProfile profile);

	/**
	 * delete a particular profile from the repository deletes allso the
	 * associations to / from this profile
	 */
	public void deleteEnterpriseProfile(EnterpriseEvaluationProfile profile);

	/**
	 * the "background" service to manipulate topic map concepts (incl. the
	 * elements that make up a profile)
	 */
	public void setConnectableService(ConnectableService connectableService);

	public ConnectableService getConnectableService();

	public ICMCompetencesManagement_Service getCompetenceService();

	public ICMInstancesManagement_Service getCompetenceInstanceService();

	public void setCompetenceService(ICMCompetencesManagement_Service service);

	public void setCompetenceInstanceService(
			ICMInstancesManagement_Service service);

	public IOrganization getOrganisationManagementService();

	public void setOrganisationManagementService(IOrganization service);

	/**
	 * return the evaluation function used by a certain profile
	 * 
	 * @param scope
	 *            TODO
	 */
	public AggregationFunction retrieveEvaluationFunction(
			EnterpriseEvaluationProfile eprofile, Scope scope);

	/**
	 * The trees are "controlled" by the server, since all users that are in the
	 * same scope should see the same trees
	 */
	public TreeDragDrop retrieveTrees(ScopeController userscope);

	public List<Dimension> retrieveAllCompetenceDimensions();

	public List<Organization> retrieveAllOrganisations();

	public Organization retrieveOrganisation(Long id);

	public List<Organization> retrieveOrganisation(String name);

	public PerformanceTree retrievePerformanceTree(ScopeController scopeC);

	public void setPerformanceTree(PerformanceTree tree, ScopeController scopeC);

	public Organization retrieveSelectedOrganisation(ScopeController scopeC);

	public void setSelectedOrganisation(Organization o, ScopeController scopeC);

	public void setSelectedEnterpriseEvaluationProfile(
			EnterpriseEvaluationProfile selectedEnterpriseEvaluationProfile,
			ScopeController scopeController);

	public EnterpriseEvaluationProfile retrieveSelectedEnterpriseEvaluationProfile(
			ScopeController scopeController);

	public void associateOccurence(String value, Connectable connectable,
			String type, Scope scope);

	public void associateOccurence(String value, Connectable connectable,
			String type, Scope scope, boolean dontclearcache);

	public void associateOccurence(String value, Connectable connectable,
			String type, String scopeName);

	public Scope retrieveScopeBy(String name);

}
