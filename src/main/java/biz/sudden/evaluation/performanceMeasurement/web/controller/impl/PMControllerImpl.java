package biz.sudden.evaluation.performanceMeasurement.web.controller.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.web.controller.RootLinkController;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;
import biz.sudden.baseAndUtility.web.controller.tree.ConnectableUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.SearchedAssoc;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.evaluation.performanceMeasurement.PerformanceEvaluation;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.domain.EvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.evaluation.performanceMeasurement.service.NetworkEvaluationService;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.service.IOrganization;

import com.icesoft.faces.component.ext.HtmlCommandLink;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.panelpopup.PanelPopup;
import com.icesoft.faces.component.tree.TreeNode;

public class PMControllerImpl implements PMController {

	private Logger logger = Logger.getLogger(this.getClass());

	protected ConnectableService connectableService;
	protected ScopeController scopeController;
	protected IOrganization organizationService;
	protected EnterpriseEvaluationService enterpriseEvaluationService;
	protected NetworkEvaluationService networkEvaluationService;

	@Override
	public double evaluate(CompetenceNeeded profileNode, Organization org) {
		Scope s = scopeController.getScopeService().retrieveScopeBy(
				org.getName());
		if (s == null)
			s = scopeController.getScopeService().createOrRetrieveScope(
					org.getName());
		return PerformanceEvaluation.evaluate(profileNode, org,
				connectableService, enterpriseEvaluationService,
				scopeController.getUserScope(), s);
	}

	@Override
	public double evaluate(ConnectableUserObject profileNode, Organization org) {
		Scope s = scopeController.getScopeService().retrieveScopeBy(
				org.getName());
		if (s == null)
			s = scopeController.getScopeService().createOrRetrieveScope(
					org.getName());
		return PerformanceEvaluation.evaluate(profileNode, org,
				connectableService, enterpriseEvaluationService,
				scopeController.getUserScope(), s);
	}

	@Override
	public double evaluate(EvaluationProfile profileNode, Organization org) {
		Scope s = scopeController.getScopeService().retrieveScopeBy(
				org.getName());
		if (s == null)
			s = scopeController.getScopeService().createOrRetrieveScope(
					org.getName());
		return PerformanceEvaluation.evaluate(profileNode, org,
				connectableService, enterpriseEvaluationService,
				scopeController.getUserScope(), s);
	}

	@Override
	public double evaluate(ConcreteSupplyNetwork net) {
		return PerformanceEvaluation.evaluate(net, connectableService,
				enterpriseEvaluationService, scopeController);
	}

	public PanelPopup getLinkableTypeSelectionPopup(final JsfLink jsfLink,
			RootLinkController rootLinkController, int what) {

		logger.debug("Called getLinkableTypeSelectionPopup in " + this);
		final PanelPopup myPopup = new PanelPopup();

		myPopup.setDraggable("true");
		myPopup.setStyle("width:400px;height:300px;position:absolute;top:"
				+ (150) + "px;left:" + (500) + "px;");
		myPopup.setClientOnly(true);
		myPopup.setId("myPMPopup");

		Tree treeModel = new Tree(getConnectableService(), getScopeController());
		treeModel.setAlsoCreateAssociationNodes(false);
		treeModel.setAlsoShowOccurrence(false);
		treeModel.setExpandAll(true);
		treeModel.setAssociationScope(getScopeController()
				.getUnspecifiedScope());

		List<Connectable> conn = new ArrayList<Connectable>();

		SearchedAssoc searchSub = null;
		if (what == PMController.SELECT_ENTERPRISEEVALUATIONPROFILE) {
			conn.addAll(getEnterpriseEvaluationService()
					.retrieveAllEnterpriseProfiles());
			treeModel.removeWhenNotInRoleOnly(conn,
					EvaluationProfile.ResultRoleType, getScopeController()
							.getUnspecifiedScope());
			searchSub = new SearchedAssoc(null,
					EvaluationProfile.ResultRoleType, null);
		} else if (what == PMController.SELECT_ORGANISATION) {
			conn.addAll(getOrganizationService().retrieveAll());
		}

		logger.debug("connectable size: " + conn.size());

		treeModel.retrieveNodes(5, conn, searchSub);

		com.icesoft.faces.component.tree.Tree tree = new com.icesoft.faces.component.tree.Tree();

		tree.setHideRootNode("true");
		tree.setHideNavigation("false");
		// tree.setImmediate(true);
		tree.setVar("selectedItem");
		tree.setValue(treeModel);
		tree.setId("pmControllerTree");
		ValueExpression expression = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{treeitems}", Object.class);
		expression.setValue(FacesContext.getCurrentInstance().getELContext(),
				treeModel);
		tree.setValueExpression("value", expression);

		ValueExpression expressionText = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{selectedItem.userObject.text}", Object.class);
		logger.debug("Domain Class is " + jsfLink.getDomainClass());
		final ValueExpression reference = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{selectedItem.userObject.reference}", Object.class);

		HtmlCommandLink link = new HtmlCommandLink();
		link.setValueExpression("value", expressionText);
		final RootLinkController finalRootLinkContr = rootLinkController;

		link.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {
				logger.debug(reference.getValue(FacesContext
						.getCurrentInstance().getELContext()));
				try {
					Object object = reference.getValue(FacesContext
							.getCurrentInstance().getELContext());
					Long id = (Long) object.getClass().getMethod("getId")
							.invoke(object);
					// jsfLink.getParameterValuesPairs().get(0).setParameterValue(id.toString());
					jsfLink.setDomainId(id.toString());
					myPopup.setVisible(false);
					myPopup.getParent().getChildren().remove(myPopup);
					finalRootLinkContr.linkTogether(object, id, jsfLink);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		link.setId("link");
		link.setStyle("font-size:0.8em;color:black");
		// link.setValueExpression("rendered", expressionCompareClassNames);
		TreeNode treeNode = new TreeNode();
		tree.getChildren().add(treeNode);
		HtmlPanelGroup panelGroup = new HtmlPanelGroup();
		treeNode.getFacets().put("content", panelGroup);
		panelGroup.getChildren().add(link);
		panelGroup.setStyle("display:inline");
		HtmlPanelGroup bodyGroup = new HtmlPanelGroup();
		bodyGroup.getChildren().add(tree);
		myPopup.getFacets().put("body", bodyGroup);

		return myPopup;
	}

	/**
	 * @return the connectableService
	 */
	@Override
	public ConnectableService getConnectableService() {
		return connectableService;
	}

	/**
	 * @param connectableService
	 *            the connectableService to set
	 */
	@Override
	public void setConnectableService(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

	/**
	 * @return the scopeController
	 */
	@Override
	public ScopeController getScopeController() {
		return scopeController;
	}

	/**
	 * @param scopeController
	 *            the scopeController to set
	 */
	@Override
	public void setScopeController(ScopeController scopeController) {
		this.scopeController = scopeController;
	}

	/**
	 * @return the organizationService
	 */
	@Override
	public IOrganization getOrganizationService() {
		return organizationService;
	}

	/**
	 * @param organizationService
	 *            the organizationService to set
	 */
	@Override
	public void setOrganizationService(IOrganization organizationService) {
		this.organizationService = organizationService;
	}

	@Override
	public EnterpriseEvaluationService getEnterpriseEvaluationService() {
		return this.enterpriseEvaluationService;
	}

	@Override
	public NetworkEvaluationService getNetworkEvaluationService() {
		return this.networkEvaluationService;
	}

	@Override
	public void setEnterpriseEvaluationService(
			EnterpriseEvaluationService eservice) {
		this.enterpriseEvaluationService = eservice;
	}

	@Override
	public void setNetworkEvaluationService(NetworkEvaluationService nservice) {
		this.networkEvaluationService = nservice;
	}

	/**
	 * @return the selectedEnterpriseEvaluationProfile
	 */
	@Override
	public EnterpriseEvaluationProfile getSelectedEnterpriseEvaluationProfile() {
		EnterpriseEvaluationProfile result = this.enterpriseEvaluationService
				.retrieveSelectedEnterpriseEvaluationProfile(this.scopeController);
		return result;
	}

	@Override
	public CaseFile getSelectedCaseFile() {
		CaseFile result = this.networkEvaluationService
				.retrieveSelectedCaseFile(this.scopeController);
		if (result != null && result.getTempTeams() == null) {
			result.setTempTeams(new LinkedList<ConcreteSupplyNetwork>());
		}
		return result;
	}

	/**
	 * @param selectedEnterpriseEvaluationProfile
	 *            the selectedEnterpriseEvaluationProfile to set
	 */
	@Override
	public void setSelectedEnterpriseEvaluationProfile(
			EnterpriseEvaluationProfile selectedEnterpriseEvaluationProfile) {
		this.enterpriseEvaluationService
				.setSelectedEnterpriseEvaluationProfile(
						selectedEnterpriseEvaluationProfile, scopeController);
	}

	@Override
	public void setSelectedNetworkEvaluationProfile(
			NetworkEvaluationProfile selectedEnterpriseEvaluationProfile) {
		this.networkEvaluationService.setSelectedNetworkEvaluationProfile(
				selectedEnterpriseEvaluationProfile, scopeController);
	}

	@Override
	public void setSelectedCaseFile(CaseFile selectedCaseFile) {
		// first select the appropriate scope ; since this does influence the
		// selected CaseFile
		String boname = selectedCaseFile.getBo().getName();
		if (boname != null) {
			scopeController.setUserScope(boname);
		}
		this.networkEvaluationService.setSelectedCaseFile(selectedCaseFile,
				scopeController);
	}
}
