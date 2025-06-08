package biz.sudden.knowledgeData.kdm.service;

import java.util.List;
import java.util.Map;

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

public interface CaseService {

	public void delete(BusinessOpportunity bo);

	/**
	 * this deletes also the BO and probably other stuff (dependent on hibernate
	 * config)
	 */
	public void delete(CaseFile cf);

	public void delete(AbstractSupplyNetwork asn);

	// public void delete(ASNNode node);

	public void delete(ASNDependency dep);

	public void delete(ConcreteSupplyNetwork csn);

	public BusinessOpportunity retrieveBO(Long id);

	public AbstractSupplyNetwork retrieveASN(Long id);

	public ASNNode retrieveASNNode(Long id);

	public List<BusinessOpportunity> retrieveAllBOs();

	public CaseFile retrieveCaseFile(Long id);

	public List<CaseFile> retrieveAllCaseFiles();

	public List<CaseFile> retrieveCaseFileByKeyword(String nameKW,
			String sundryinfoKW);

	public List<CaseFile> retrieveCaseFile(String partOfName);

	public List<BusinessOpportunity> retrieveBusinessOpportunity(
			String partOfName);

	public CaseFile retrieveCaseFile(BusinessOpportunity bo);

	public long create(CaseFile intothedb);

	public long create(BusinessOpportunity intothedb);

	public void create(AbstractSupplyNetwork asn);

	public void create(ConcreteSupplyNetwork csn);

	public void create(ASNNode node);

	public void create(ASNDependency dep);

	public void update(CaseFile caseFile);

	public void update(BusinessOpportunity bo);

	public void update(AbstractSupplyNetwork asn);

	public void update(ConcreteSupplyNetwork csn);

	public void update(ASNNode node);

	public void update(ASNDependency dep);

	public void setGenericRepository(SuddenGenericRepository rep);

	public void setConnectableService(ConnectableService service);

	public ConnectableService getConnectableService();

	public void setCaseFileRepository(CaseFileRepository rep);

	public void setBusinessOpportunityRepository(
			BusinessOpportunityRepository rep);

	public void setHboService(HandleBOService serv);

	public CompetenceNeeded retrieveCompetenceNeeded(Long id);

	public QualificationProfile retrieveQualificationProfile(Long id);

	public void update(NetworkEvaluationProfile nep);

	public void update(CompetenceNeeded cn);

	public Map<Long, String> retrieveCaseFileNames();

}
