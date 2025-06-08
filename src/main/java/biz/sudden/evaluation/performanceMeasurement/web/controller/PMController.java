package biz.sudden.evaluation.performanceMeasurement.web.controller;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.web.controller.RootLinkController;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;
import biz.sudden.baseAndUtility.web.controller.tree.ConnectableUserObject;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.domain.EvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.evaluation.performanceMeasurement.service.NetworkEvaluationService;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.service.IOrganization;

import com.icesoft.faces.component.panelpopup.PanelPopup;

public interface PMController {

	public final int SELECT_ORGANISATION = 0;
	public final int SELECT_ENTERPRISEEVALUATIONPROFILE = 1;

	public double evaluate(ConnectableUserObject profileNode, Organization org);

	public double evaluate(EvaluationProfile profileNode, Organization org);

	public double evaluate(CompetenceNeeded profileNode, Organization org);

	public double evaluate(ConcreteSupplyNetwork profileNode);

	public EnterpriseEvaluationProfile getSelectedEnterpriseEvaluationProfile();

	public void setSelectedEnterpriseEvaluationProfile(
			EnterpriseEvaluationProfile selectedEnterpriseEvaluationProfile);

	public CaseFile getSelectedCaseFile();

	public void setSelectedCaseFile(CaseFile cf);

	public PanelPopup getLinkableTypeSelectionPopup(final JsfLink jsfLink,
			RootLinkController rootLinkController, int selectWhat);

	/** the service to access organisations */
	public void setOrganizationService(IOrganization organizationService);

	/** the service to access organisations */
	public IOrganization getOrganizationService();

	/** The service to access topic maps */
	public void setConnectableService(ConnectableService service);

	/** The service to access topic maps */
	public ConnectableService getConnectableService();

	/**
	 * set the service required for constructing a performance profile for an
	 * enterprise
	 * 
	 * @param eservice
	 *            the service to be used
	 */
	public void setEnterpriseEvaluationService(
			EnterpriseEvaluationService eservice);

	/**
	 * get the service required for constructing a performance profile for an
	 * enterprise
	 * 
	 * @return the used service
	 */
	public EnterpriseEvaluationService getEnterpriseEvaluationService();

	/**
	 * set the service required for constructing a performance profile for a
	 * network
	 * 
	 * @param nservice
	 *            the service to be used
	 */
	public void setNetworkEvaluationService(NetworkEvaluationService nservice);

	/**
	 * get the service required for constructing a performance profile for a
	 * network
	 * 
	 * @return the used service
	 */
	public NetworkEvaluationService getNetworkEvaluationService();

	/** controller used to manage the user's scope */
	public void setScopeController(ScopeController scopeController);

	/** controller used to manage the user's scope */
	public ScopeController getScopeController();

	public void setSelectedNetworkEvaluationProfile(
			NetworkEvaluationProfile selectedEnterpriseEvaluationProfile);
}
