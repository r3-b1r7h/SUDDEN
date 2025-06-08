package biz.sudden.evaluation.performanceMeasurement.web.controller;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.handleBO.service.HandleBOService;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.overallControl.TeamFormationControllerService;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeededByNetworkEvaluationProfile;
import biz.sudden.knowledgeData.kdm.web.controller.CaseFileController;

import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;

public interface PMNetworkProfileDesignController extends Renderable {

	public void setRenderManager(RenderManager rm);

	public void setPMController(PMController controller);

	public void setCaseFileController(CaseFileController controller);

	public void setTeamController(
			TeamFormationControllerService servicecontroller);

	public List<ASNRoleNode> getASNRoleNodes();

	public List<SelectItem> getASNRoles();

	public void selectedProfilesChanged(ValueChangeEvent event);

	public void removeASNEnterpriseProfile(ActionEvent evt);

	public void assignCompetences2Roles(ActionEvent evt);

	public void updateASNEnterpriseProfile(ValueChangeEvent vce);

	public void updateASNEnterpriseProfile(ActionEvent ae);

	public void updateASNEnterpriseProfile();

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	public Long getASNSelect();

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	public void setASNSelect(Long id);

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	public void selectedASNChanged(ValueChangeEvent ae);

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	public List<SelectItem> getListOfASNs();

	public void setSelectedASN(AbstractSupplyNetwork asn);

	public AbstractSupplyNetwork getSelectedASN();

	/** make it possible on the GUI to select one available Case Files */
	public Long getCaseFileId();

	/** make it possible on the GUI to select one from the available Case Files */
	public void setCaseFileId(Long id);

	/** make it possible on the GUI to select one from the available Case Files */
	public void selectedCaseFileChanged(ValueChangeEvent ae);

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	public List<SelectItem> getListOfCaseFiles();

	public List<CompetenceNeededByNetworkEvaluationProfile> getCompetenceAllHave();

	public void removeCompetenceAllHave(ActionEvent evt);

	public void addCompetencesAllHave(ActionEvent evt);

	public void updateCompetenceAllHave(ValueChangeEvent vce);

	public void updateCompetenceAllHave(ActionEvent ae);

	public void updateCompetenceAllHave();

	public List<CompetenceNeededByNetworkEvaluationProfile> getCompetenceOneOrMoreHave();

	public void removeCompetenceOneOrMoreHave(ActionEvent evt);

	public void addCompetencesOneOrMoreHave(ActionEvent evt);

	public void updateCompetenceOneOrMoreHave(ValueChangeEvent vce);

	public void updateCompetenceOneOrMoreHave(ActionEvent ae);

	public void updateCompetenceOneOrMoreHave();

	public NetworkEvaluationProfile getNetworkEvaluationProfile();

	public List<SelectItem> getAvailableEnterpriseProfiles();

	public void selectedASNRolesChanged(ValueChangeEvent event);

	public void setSelectedASNRoles(String[] roles);

	public void setHandleBoService(HandleBOService handleBoService);

	public String[] getSelectedProfiles();

	public void setSelectedProfiles(String[] profiles);

	public String[] getSelectedASNRoles();

	public CaseFile getSelectedCaseFile();

	public void setSelectedCaseFile(CaseFile cf);

	String removeCompetenceAllHave();

	String removeCompetenceOneOrMoreHave();

	String removeASNEnterpriseProfile();

	boolean getShowFinalTeamButton();

	boolean getShowPrototypeTeamButton();

	String triggerGenerateFinalTeam();

	String triggerGeneratePrototypeTeam();
}
