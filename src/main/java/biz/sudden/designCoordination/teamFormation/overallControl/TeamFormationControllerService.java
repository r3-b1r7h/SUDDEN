package biz.sudden.designCoordination.teamFormation.overallControl;

import java.util.List;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.designCoordination.teamFormation.service.JadeService;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;
import biz.sudden.knowledgeData.kdm.service.KdmService;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

/**
 * The main interface used by the gui/other modules to trigger TF Also (back
 * end) responsible for mediating between the use of TopDown/BottomUp TF as is
 * found suitable.
 * 
 * @author mcassmc
 * 
 */

public interface TeamFormationControllerService {

	// TODO - add a ProductMaterialSupportingServices &
	// UserandOrganisationManagament saervice via Spring stuff.

	/*
	 * Some spring stuff
	 */

	public void setProductMaterialSupportingService(
			ProductMaterialSupportingServices_Service PMSS);

	/**
	 * The 'basic' call. This will (in time!) use both bottom up and top down TF
	 * in an intelligent hybrid.
	 * 
	 * @param ASNin
	 */
	// public ArrayList<ConcreteSupplyNetwork>
	// generateCandidateTeams(AbstractSupplyNetwork ASNin);

	/**
	 * For generating candidate team members in the prototype phase. This one
	 * assumes that it is provided with an ASN with an empty node to complete
	 * and updates the case file accordingly.
	 * 
	 * Currently this is otherwise functionally identical to the one used in the
	 * later phases. I suppose that in principle they might differ.
	 * 
	 * @param caseFileIn
	 */
	public void generatePrototypeTeamCandidatesTopDown(CaseFile caseFileToChange);

	/**
	 * For generating candidate team members in the fianl phase. This one
	 * assumes that it is provided with an ASN with an empty node to complete
	 * and updates the case file accordingly.
	 * 
	 * Currently this is otherwise functionally identical to the one used in the
	 * later phases. I suppose that in principle they might differ.
	 * 
	 * @param caseFileIn
	 */
	public void generateFinalTeamCandidatesTopDown(CaseFile caseFileToChange);

	/**
	 * To set bottom up TF going. The assumption here is that bottom up TF is
	 * used to 'patch' a specific hole in a given abstract supply network.
	 * 
	 * @param caseFileToChange
	 * @param CSNtoUpdate
	 * @param materialToConstruct
	 */
	public void generateTeamsBottomUp(CaseFile caseFileToChange,
			ConcreteSupplyNetwork CSNtoUpdate, ASNRoleNode materialToConstruct);

	public void setEnterpriseEvaluationService(EnterpriseEvaluationService EES);

	public List<Organization> retrieveOrganisationsByName(String name);

	public QualificationProfile retrieveQualificationProfile(Long id);

	public CompetenceNeeded retrieveCompetenceNeeded(Long id);

	public Organization retrieveOrganisation(Long id);

	public Supplier retrieveSupplier(Long id);

	public CaseFile retrieveCaseFile(Long id);

	public void setKDMService(KdmService kdm);

	public ServiceManagementService getSMS();

	public void setSMS(ServiceManagementService SMS);

	public void setTDTF(topDownTeamFormationModule TDTF);

	public void setJadeService(JadeService jadeS);

	public void setControllerForEvaluation(PMController controllerForEvaluation);

}
