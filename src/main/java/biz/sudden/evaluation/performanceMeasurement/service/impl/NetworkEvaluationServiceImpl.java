/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.sudden.evaluation.performanceMeasurement.service.impl;

import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.baseAndUtility.web.controller.tree.TreeDragDrop;
import biz.sudden.baseAndUtility.web.controller.tree.TreeShowNoRole;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeededByNetworkEvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.repository.CompetenceNeededRepository;
import biz.sudden.evaluation.performanceMeasurement.repository.NetworkEvaluationProfileRepository;
import biz.sudden.evaluation.performanceMeasurement.service.NetworkEvaluationService;
import biz.sudden.knowledgeData.kdm.service.CaseService;

/**
 * 
 * @author gweich
 */
public class NetworkEvaluationServiceImpl implements NetworkEvaluationService {

	private Logger logger = Logger.getLogger(this.getClass());

	protected NetworkEvaluationProfileRepository networkEvaluationProfileRepository;
	protected CompetenceNeededRepository competenceNeededRepository;

	protected CaseService caseService;

	protected Hashtable<String, AbstractSupplyNetwork> selectedASNs;
	protected Hashtable<String, CaseFile> selectedCaseFiles;
	protected Hashtable<String, TreeDragDrop> trees = new Hashtable<String, TreeDragDrop>();
	protected Hashtable<String, NetworkEvaluationProfile> selectedNetworkEvaluationProfile = new Hashtable<String, NetworkEvaluationProfile>();

	public NetworkEvaluationServiceImpl() {
		logger.debug("NetworkEvaluationServiceImpl: Constructor called");
		selectedASNs = new Hashtable<String, AbstractSupplyNetwork>();
		selectedCaseFiles = new Hashtable<String, CaseFile>();
	}

	/**
	 * The trees are "controlled" by the server, since all users that are in the
	 * same scope should see the same trees
	 */
	@Override
	public TreeDragDrop retrieveTrees(ScopeController scopeController) {
		if (trees.containsKey(scopeController.getUserScope().getName()))
			return trees.get(scopeController.getUserScope().getName());

		TreeDragDrop tdd = new TreeDragDrop(new Tree(caseService
				.getConnectableService(), scopeController,
				"Enterprise Evaluation Profiles"), new TreeShowNoRole(
				caseService.getConnectableService(), scopeController,
				"Network Evaluation Profile"));

		trees.put(scopeController.getUserScope().getName(), tdd);
		return tdd;

	}

	/** retrieves a new version of the CaseFile from the DB */
	@Override
	public AbstractSupplyNetwork retrieveSelectedASN(
			ScopeController scopeController) {
		return caseService.retrieveASN(selectedASNs.get(
				scopeController.getUserScope().getName()).getId());
	}

	@Override
	public void setSelectedASN(AbstractSupplyNetwork asn,
			ScopeController scopeController) {
		if (asn != null)
			selectedASNs.put(scopeController.getUserScope().getName(), asn);
	}

	/** retrieves a new version of the CaseFile from the DB */
	@Override
	public CaseFile retrieveSelectedCaseFile(ScopeController scopeController) {
		CaseFile result = selectedCaseFiles.get(scopeController.getUserScope()
				.getName());
		// logger.debug("get Selected CF: " + result
		// +" in context "+scopeController.getUserScope().getName());
		return result;
	}

	@Override
	public void setSelectedCaseFile(CaseFile cf, ScopeController scopeController) {
		if (cf != null) {
			selectedCaseFiles.put(scopeController.getUserScope().getName(), cf);
			if (cf.getAsnFinalTeam() != null)
				setSelectedASN(cf.getAsnFinalTeam(), scopeController);
		} else if (cf.getAsnPrototypeTeam() != null) {
			setSelectedASN(cf.getAsnPrototypeTeam(), scopeController);
		} else {
			setSelectedASN(null, scopeController);
		}
	}

	@Override
	public void setSelectedNetworkEvaluationProfile(
			NetworkEvaluationProfile selectedNetworkEvaluationProfile,
			ScopeController scopeController) {
		this.selectedNetworkEvaluationProfile.put(scopeController
				.getUserScope().getName(), selectedNetworkEvaluationProfile);
	}

	@Override
	public void setNetworkEvaluationProfileRepository(
			NetworkEvaluationProfileRepository rep) {
		this.networkEvaluationProfileRepository = rep;
	}

	@Override
	public void setCompetenceNeededRepository(CompetenceNeededRepository rep) {
		this.competenceNeededRepository = rep;
	}

	@Override
	public void create(NetworkEvaluationProfile profile) {
		networkEvaluationProfileRepository.create(profile);
	}

	@Override
	public void create(CompetenceNeeded cn) {
		competenceNeededRepository.create(cn);
	}

	@Override
	public NetworkEvaluationProfile retrieveNetworkProfile(Long id) {
		return networkEvaluationProfileRepository.retrieve(id);
	}

	@Override
	public List<NetworkEvaluationProfile> retrieveNetworkProfile(String name) {
		return networkEvaluationProfileRepository
				.retrieveNEvaluationProfileBy(name);
	}

	@Override
	public void update(CompetenceNeeded cn) {
		competenceNeededRepository.update(cn);
	}

	@Override
	public void update(NetworkEvaluationProfile profile) {
		if (profile != null) {
			if (profile.getCompetenceAllHave() != null) {
				for (CompetenceNeededByNetworkEvaluationProfile cn : profile
						.getCompetenceAllHave()) {
					competenceNeededRepository.update(cn);
				}
			}
			if (profile.getCompetenceOneOrMoreHave() != null) {
				for (CompetenceNeededByNetworkEvaluationProfile cn : profile
						.getCompetenceOneOrMoreHave()) {
					competenceNeededRepository.update(cn);
				}
			}
			networkEvaluationProfileRepository.update(profile);
		}
	}

	@Override
	public void delete(NetworkEvaluationProfile profile) {
		if (profile != null) {
			if (profile.getCompetenceAllHave() != null) {
				for (CompetenceNeededByNetworkEvaluationProfile cn : profile
						.getCompetenceAllHave()) {
					if (cn.getId() != null
							&& competenceNeededRepository.retrieve(cn.getId()) != null)
						competenceNeededRepository.delete(cn);
				}
				profile.getCompetenceAllHave().clear();
			}
			if (profile.getCompetenceOneOrMoreHave() != null) {
				for (CompetenceNeededByNetworkEvaluationProfile cn : profile
						.getCompetenceOneOrMoreHave()) {
					if (cn.getId() != null
							&& competenceNeededRepository.retrieve(cn.getId()) != null)
						competenceNeededRepository.delete(cn);
				}
				profile.getCompetenceOneOrMoreHave().clear();
			}
			networkEvaluationProfileRepository.delete(profile);
		}
	}

	@Override
	public void delete(CompetenceNeeded deleteme) {
		competenceNeededRepository.delete(deleteme);
	}

	@Override
	public void setCaseFileService(CaseService service) {
		this.caseService = service;
	}

	@Override
	public CaseService getCaseFileService() {
		return this.caseService;
	}
}
