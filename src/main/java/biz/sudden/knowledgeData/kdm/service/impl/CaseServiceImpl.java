package biz.sudden.knowledgeData.kdm.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.repository.BusinessOpportunityRepository;
import biz.sudden.baseAndUtility.repository.CaseFileRepository;
import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.designCoordination.handleBO.dataStructures.ASNNode;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.handleBO.service.HandleBOService;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.knowledgeData.kdm.service.CaseService;

public class CaseServiceImpl implements CaseService {

	Logger logger = Logger.getLogger(this.getClass());
	protected BusinessOpportunityRepository boRepository;
	protected CaseFileRepository caseFileRepository;
	protected ConnectableService connectableService;
	private HandleBOService hboService;
	protected SuddenGenericRepository genericRepository;

	@Override
	public List<BusinessOpportunity> retrieveAllBOs() {
		return boRepository.retrieveAll();
	}

	@Override
	public List<CaseFile> retrieveAllCaseFiles() {
		List<CaseFile> result = caseFileRepository.retrieveAll();
		logger.debug("# of Case Files: " + result.size());
		return result;
	}

	@Override
	public void setBusinessOpportunityRepository(
			BusinessOpportunityRepository rep) {
		boRepository = rep;
	}

	@Override
	public void setCaseFileRepository(CaseFileRepository rep) {
		caseFileRepository = rep;
	}

	@Override
	public void setConnectableService(ConnectableService service) {
		this.connectableService = service;
	}

	@Override
	public ConnectableService getConnectableService() {
		return this.connectableService;
	}

	@Override
	public BusinessOpportunity retrieveBO(Long id) {
		return boRepository.retrieve(id);
	}

	@Override
	public synchronized long create(BusinessOpportunity intothedb) {
		connectableService.createOrRetrieveScope(intothedb.getName());
		return boRepository.create(intothedb);
	}

	@Override
	public CaseFile retrieveCaseFile(Long id) {
		return caseFileRepository.retrieve(id);
	}

	@Override
	public List<CaseFile> retrieveCaseFile(String name) {
		return caseFileRepository.retrieveByKeyword(name, null);
	}

	@Override
	public CaseFile retrieveCaseFile(BusinessOpportunity bo) {
		return caseFileRepository.retrieve(bo);
	}

	@Override
	public List<BusinessOpportunity> retrieveBusinessOpportunity(String name) {
		return boRepository.retrieveAllByFieldNameContains("name", name);
	}

	@Override
	public List<CaseFile> retrieveCaseFileByKeyword(String name,
			String sundryInfoKW) {
		return caseFileRepository.retrieveByKeyword(name, sundryInfoKW);
	}

	@Override
	public CompetenceNeeded retrieveCompetenceNeeded(Long id) {
		return genericRepository
				.retrieveByTypeAndId(CompetenceNeeded.class, id);
	}

	@Override
	public QualificationProfile retrieveQualificationProfile(Long id) {
		return genericRepository.retrieveByTypeAndId(
				QualificationProfile.class, id);
	}

	@Override
	public synchronized void delete(BusinessOpportunity bo) {
		boRepository.delete(bo);
		// don't delete the case file... it should be kept for further
		// reference; unless done on purpose
	}

	@Override
	public synchronized void update(BusinessOpportunity bo) {
		boRepository.update(bo);
		// don't delete the case file... it should be kept for further
		// reference; unless done on purpose
	}

	@Override
	public synchronized void delete(CaseFile cf) {
		// don't think this is ncessary as we got a cascade=CascadeType.ALL
		// annotation!
		boRepository.delete(cf.getBo());
		caseFileRepository.delete(cf);
	}

	@Override
	public void setGenericRepository(SuddenGenericRepository rep) {
		this.genericRepository = rep;
	}

	@Override
	public void setHboService(HandleBOService serv) {
		this.hboService = serv;
	}

	public HandleBOService getHboService() {
		return hboService;
	}

	@Override
	public synchronized long create(CaseFile intothedb) {
		if (intothedb.getId() == null) {
			return caseFileRepository.create(intothedb);
		} else {
			caseFileRepository.update(intothedb);
			return intothedb.getId();
		}
	}

	@Override
	public synchronized void update(CaseFile caseFile) {
		logger.debug("Update CaseFile #" + caseFile.getId() + " File: "
				+ caseFile);

		// / hibernate fails with orgs and suppliers..
		for (ConcreteSupplyNetwork csn : caseFile.getTempTeams()) {
			update(csn);
		}
		update(caseFile.getFinalTeam());
		update(caseFile.getPrototypeTeam());

		// now wired through hibernate
		if (caseFile.getBo() != null)
			boRepository.update(caseFile.getBo());
		// if (caseFile.getAsnFinalTeam() != null &&
		// caseFile.getAsnFinalTeam().getASNEvaluationProfile() != null)
		// networkEvaluationService.update(caseFile.getAsnFinalTeam().getASNEvaluationProfile());
		// if (caseFile.getAsnPrototypeTeam() != null &&
		// caseFile.getAsnPrototypeTeam().getASNEvaluationProfile() != null)
		// networkEvaluationService.update(caseFile.getAsnPrototypeTeam().getASNEvaluationProfile());
		// if (caseFile.getTempTeams() != null)
		// for (int i = 0; i < caseFile.getTempTeams().size(); ++i) {
		// genericRepository.update(caseFile.getTempTeams().get(i).getASN());
		// networkEvaluationService.update(caseFile.getTempTeams().get(i).getASN().getASNEvaluationProfile());
		// }

		// done already above!
		// if (caseFile.getFinalTeam() != null)
		// genericRepository.update(caseFile.getFinalTeam());
		// if (caseFile.getPrototypeTeam() != null)
		// genericRepository.update(caseFile.getPrototypeTeam());

		create(caseFile);
	}

	@Override
	public synchronized void delete(AbstractSupplyNetwork asn) {
		hboService.delete(asn);
	}

	/*
	 * No. No deletion of nodes without going through the ASN first please!
	 * It'll leave the world in a total mess....
	 * 
	 * public void delete(ASNNode node) { hboService.delete(node); }
	 */

	@Override
	public synchronized void delete(ASNDependency dep) {
		hboService.delete(dep);
	}

	@Override
	public synchronized void delete(ConcreteSupplyNetwork csn) {
		hboService.delete(csn);
	}

	@Override
	public AbstractSupplyNetwork retrieveASN(Long id) {
		return hboService.retrieveASN(id);
	}

	@Override
	public ASNNode retrieveASNNode(Long id) {
		return hboService.retrieveASNNode(id);
	}

	@Override
	public synchronized void create(AbstractSupplyNetwork asn) {
		hboService.create(asn);
	}

	@Override
	public synchronized void create(ConcreteSupplyNetwork csn) {
		hboService.create(csn);
	}

	@Override
	public synchronized void create(ASNNode node) {
		hboService.create(node);
	}

	@Override
	public synchronized void create(ASNDependency dep) {
		hboService.create(dep);
	}

	@Override
	public synchronized void update(AbstractSupplyNetwork asn) {
		hboService.update(asn);
	}

	@Override
	public synchronized void update(ASNNode node) {
		hboService.update(node);
	}

	@Override
	public synchronized void update(ASNDependency dep) {
		hboService.update(dep);
	}

	@Override
	public synchronized void update(ConcreteSupplyNetwork csn) {
		hboService.update(csn);
	}

	@Override
	public synchronized void update(NetworkEvaluationProfile nep) {
		hboService.update(nep);
	}

	@Override
	public synchronized void update(CompetenceNeeded cn) {
		hboService.update(cn);
	}

	@Override
	public Map<Long, String> retrieveCaseFileNames() {
		return caseFileRepository.retrieveAllCaseFileNames();
	}

}
