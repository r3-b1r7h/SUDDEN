/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.sudden.evaluation.performanceMeasurement.service;

import java.util.List;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.TreeDragDrop;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.repository.CompetenceNeededRepository;
import biz.sudden.evaluation.performanceMeasurement.repository.NetworkEvaluationProfileRepository;
import biz.sudden.knowledgeData.kdm.service.CaseService;

/**
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
public interface NetworkEvaluationService {

	public void create(NetworkEvaluationProfile profile);

	public void create(CompetenceNeeded cn);

	public void delete(CompetenceNeeded deleteme);

	public void delete(NetworkEvaluationProfile profile);

	public NetworkEvaluationProfile retrieveNetworkProfile(Long id);

	public List<NetworkEvaluationProfile> retrieveNetworkProfile(String name);

	public void update(NetworkEvaluationProfile profile);

	public void update(CompetenceNeeded cn);

	public void setNetworkEvaluationProfileRepository(
			NetworkEvaluationProfileRepository rep);

	public void setCompetenceNeededRepository(CompetenceNeededRepository rep);

	public AbstractSupplyNetwork retrieveSelectedASN(
			ScopeController scopeController);

	public void setSelectedASN(AbstractSupplyNetwork asn,
			ScopeController scopeController);

	public CaseFile retrieveSelectedCaseFile(ScopeController scopeController);

	public void setSelectedCaseFile(CaseFile cf, ScopeController scopeController);

	public void setCaseFileService(CaseService service);

	public CaseService getCaseFileService();

	public TreeDragDrop retrieveTrees(ScopeController scopeController);

	public void setSelectedNetworkEvaluationProfile(
			NetworkEvaluationProfile selectedEnterpriseEvaluationProfile,
			ScopeController scopeController);

}
