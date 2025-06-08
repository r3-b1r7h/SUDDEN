/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.sudden.evaluation.performanceMeasurement.web.controller.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.web.controller.RootLinkController;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;
import biz.sudden.baseAndUtility.web.controller.tree.AssocUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.ConnectableUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.SearchedAssoc;
import biz.sudden.baseAndUtility.web.controller.tree.StringUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.baseAndUtility.web.controller.tree.TreeDragDrop;
import biz.sudden.baseAndUtility.web.controller.tree.UserObject;
import biz.sudden.evaluation.performanceMeasurement.domain.EvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMEnterpriseProfileDesignController;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;

import com.icesoft.faces.component.dragdrop.DndEvent;
import com.icesoft.faces.component.dragdrop.DropEvent;
import com.icesoft.faces.component.panelpopup.PanelPopup;

/**
 * 
 * @author gweich
 */
public class PMEnterpriseProfileDesignControllerImpl extends
		PMProfileDesignControllerImpl implements
		PMEnterpriseProfileDesignController {

	protected Logger logger = Logger.getLogger(this.getClass());

	protected SelectItem[] functionnames;

	public PMEnterpriseProfileDesignControllerImpl() {
		logger.debug("PMEnterpriseProfileDesignController -> cst");
	}

	@Override
	public TreeDragDrop getIndicatorTree() {
		// logger.debug("PM Controller getIndicatorTree "+indicatorTree.toString());
		TreeDragDrop result = pMController.getEnterpriseEvaluationService()
				.retrieveTrees(pMController.getScopeController());
		// refreshTargetTree();
		// refreshSourceTree();
		return result;
	}

	@Override
	public void dropListener(DropEvent dropEvent) {
		if (dropEvent.getTargetDragValue() instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode dragNode = (DefaultMutableTreeNode) dropEvent
					.getTargetDragValue();
			DefaultMutableTreeNode dropTargetNode = (DefaultMutableTreeNode) dropEvent
					.getTargetDropValue();
			logger
					.debug("PMProfileDesignControllerImpl!!: dropListener: dragged: "
							+ dragNode
							+ " "
							+ DndEvent.getEventName(dropEvent.getEventType())
							+ " target: " + dropTargetNode);

			if (dragNode != null && dropTargetNode != null) {
				if (dragNode.getUserObject() instanceof AssocUserObject) {
					this.newEProfileParameter = ((AssocUserObject) dragNode
							.getUserObject()).getChildRoleName();
					this.newEProfileFunction = ((AssocUserObject) dragNode
							.getUserObject()).getAssocTypeName();
					dragNode = (DefaultMutableTreeNode) dragNode.getChildAt(0);
				}
				if (dropTargetNode.getUserObject() instanceof AssocUserObject) {
					this.newEProfileParameter = ((AssocUserObject) dropTargetNode
							.getUserObject()).getChildRoleName();
					this.newEProfileFunction = ((AssocUserObject) dropTargetNode
							.getUserObject()).getAssocTypeName();
					dropTargetNode = (DefaultMutableTreeNode) dropTargetNode
							.getParent();
				}
				if (dropTargetNode.getUserObject() instanceof StringUserObject) {
					// D&D into default root target:
					DefaultMutableTreeNode rootn = new DefaultMutableTreeNode();
					((UserObject) dragNode.getUserObject()).copyInto(rootn);
					getIndicatorTree().getTargetTree().setRoot(rootn);
					targetTreeRefresh = 0l;
				}

				if ((dropTargetNode.getUserObject() instanceof ConnectableUserObject)
						&& (dragNode.getUserObject() instanceof ConnectableUserObject)
						&& (((ConnectableUserObject) dragNode.getUserObject())
								.getReference() instanceof EnterpriseEvaluationProfile)) {
					addProfile(
							getIndicatorTree().getTargetTree(),
							(ConnectableUserObject) dropTargetNode
									.getUserObject(),
							(EnterpriseEvaluationProfile) ((ConnectableUserObject) dragNode
									.getUserObject()).getReference(),
							pMController.getScopeController().getUserScope());
					dragNode.removeFromParent();
					targetTreeRefresh = 0l;
				} else if ((dropTargetNode.getUserObject() instanceof ConnectableUserObject)
						&& (dragNode.getUserObject() instanceof ConnectableUserObject)
						&& (((ConnectableUserObject) dragNode.getUserObject())
								.getReference() instanceof Dimension)) {
					addProfile(getIndicatorTree().getTargetTree(),
							(ConnectableUserObject) dropTargetNode
									.getUserObject(),
							(Dimension) ((ConnectableUserObject) dragNode
									.getUserObject()).getReference(),
							pMController.getScopeController().getUserScope());
					// If dragOption in jspx file is "dragGhost" this is not
					// needed!
					// dragNode.removeFromParent();
					targetTreeRefresh = 0l;
				} /*
				 * else if ((dropTargetNode.getUserObject() instanceof
				 * ConnectableUserObject) && (dragNode.getUserObject()
				 * instanceof ConnectableUserObject) &&
				 * (((ConnectableUserObject)
				 * dragNode.getUserObject()).getReference() instanceof
				 * NetworkEvaluationProfile)) { addProfile(
				 * getIndicatorTree().getTargetTree(), (ConnectableUserObject)
				 * dropTargetNode.getUserObject(), (NetworkEvaluationProfile)
				 * ((ConnectableUserObject)
				 * dragNode.getUserObject()).getReference(),
				 * pMController.getScopeController().getUserScope());
				 * dragNode.removeFromParent(); // targetTreeRefresh = true; }
				 */
			}
			// refreshTargetTree();
			// refreshSourceTree();
			updateUI();
		}
	}

	protected void addProfile(Tree tree, ConnectableUserObject targetCUO,
			EnterpriseEvaluationProfile eep, Scope scope) {
		pMController.getEnterpriseEvaluationService().updateEnterpriseProfile(
				eep);

		Connectable targetReference = targetCUO.getReference();
		List<String> params = new ArrayList<String>();
		if (newEProfileParameter == null || newEProfileParameter.length() == 0)
			newEProfileParameter = "0.321";
		params.add(newEProfileParameter);

		List<EnterpriseEvaluationProfile> children = new ArrayList<EnterpriseEvaluationProfile>();
		children.add(eep);

		pMController.getEnterpriseEvaluationService().updateEnterpriseProfile(
				(EnterpriseEvaluationProfile) targetReference,
				newEProfileFunction, params, children, scope);

		targetCUO.deleteSubNodes();
		tree.retrieveTreeNodes(5, targetCUO, new SearchedAssoc(null,
				EvaluationProfile.ResultRoleType, null));
	}

	protected void addProfile(Tree tree, ConnectableUserObject targetCUO,
			Dimension competence, Scope scope) {
		Connectable targetReference = targetCUO.getReference();
		List<String> params = new ArrayList<String>();
		if (newEProfileParameter == null || newEProfileParameter.length() == 0)
			newEProfileParameter = "0.321";
		params.add(newEProfileParameter);
		List<Dimension> children = new ArrayList<Dimension>();
		children.add(competence);
		pMController.getEnterpriseEvaluationService()
				.updateEnterpriseProfileWithCompetences(
						(EnterpriseEvaluationProfile) targetReference,
						newEProfileFunction, params, children, scope);
		targetCUO.deleteSubNodes();
		tree.retrieveTreeNodes(5, targetCUO, new SearchedAssoc(null,
				EvaluationProfile.ResultRoleType, null));
	}

	@Override
	public void goTo() {
		FacesContext context = FacesContext.getCurrentInstance();
		selectProfile((DefaultMutableTreeNode) context.getExternalContext()
				.getRequestMap().get("position"));
	}

	@Override
	public void create(ActionEvent evt) {
		create();
	}

	@Override
	public void create() {
		EnterpriseEvaluationProfile eep = new EnterpriseEvaluationProfile();
		eep.setName(selectedProfileName);
		pMController.getEnterpriseEvaluationService().createEnterpriseProfile(
				eep);

		getIndicatorTree().getTargetTree().createTree(5, eep, null);
		targetTreeRefresh = 0l;
		refreshTargetTree();
	}

	@Override
	public void addSub(ActionEvent evt) {
		addSub();
	}

	// TODO fix to include competence dimensions!
	@Override
	public void addSub() {
		UserObject targetUserObj = null;
		ConnectableUserObject targetCUO = null;
		if (getIndicatorTree().getSelectedNodeObject() != null) {
			targetUserObj = getIndicatorTree().getSelectedNodeObject();
		} else if (getIndicatorTree().getTargetTree() != null) {
			// nothing seems selected so put this into the target Tree's root
			targetUserObj = (UserObject) ((DefaultMutableTreeNode) getIndicatorTree()
					.getTargetTree().getRoot()).getUserObject();
		}
		if (selectedProfileName == null || selectedProfileName.length() == 0)
			selectedProfileName = "EnterpriseEvalProf.X";
		if (newEProfileParameter == null || newEProfileParameter.length() == 0)
			newEProfileParameter = "0.1";

		EnterpriseEvaluationProfile eep = new EnterpriseEvaluationProfile();
		eep.setName(selectedProfileName);
		pMController.getEnterpriseEvaluationService().createEnterpriseProfile(
				eep);

		if (targetUserObj == null || targetUserObj instanceof StringUserObject) {
			getIndicatorTree().getTargetTree().retrieveTree(
					5,
					eep,
					new SearchedAssoc(null, EvaluationProfile.ResultRoleType,
							null));
		} else {
			if (targetUserObj instanceof ConnectableUserObject) {
				targetCUO = (ConnectableUserObject) targetUserObj;
			} else if (targetUserObj instanceof AssocUserObject) {
				targetCUO = ((ConnectableUserObject) ((DefaultMutableTreeNode) ((AssocUserObject) targetUserObj)
						.getWrapper().getParent()).getUserObject());
				newEProfileFunction = ((AssocUserObject) targetUserObj)
						.getAssocTypeName();
				newEProfileParameter = ((AssocUserObject) targetUserObj)
						.getChildRoleName();
			} else if (((DefaultMutableTreeNode) getIndicatorTree()
					.getTargetTree().getRoot()).getUserObject() instanceof ConnectableUserObject) {
				logger
						.debug("PMEnterpriseProfileDesignController. addSub What the f!§$ is that ?! (using TargetTree ROOT): "
								+ targetUserObj);
				targetCUO = (ConnectableUserObject) ((DefaultMutableTreeNode) getIndicatorTree()
						.getTargetTree().getRoot()).getUserObject();
			} else {
				logger
						.debug("PMEnterpriseProfileDesignController. addSub What the f!§$ is that ?!  && no Suitable ROOT :( "
								+ targetUserObj);
				targetCUO = null;
			}
			if (targetCUO != null) {
				addProfile(getIndicatorTree().getTargetTree(), targetCUO, eep,
						pMController.getScopeController().getUserScope());
			}
		}
		sourceTreeRefresh = 0l;
		targetTreeRefresh = 0l;
		refreshTargetTree();
		refreshSourceTree();
		updateUI();
	}

	@Override
	public void deleteSelected(ActionEvent arg0) {
		logger.debug("PMsjfControllerImpl.deleteSelected");
		UserObject uo = getIndicatorTree().getSelectedNodeObject();
		DefaultMutableTreeNode node = uo.getWrapper();

		if (uo instanceof ConnectableUserObject) {
			// do not delete it just de-reference it.
			// EnterpriseEvaluationProfile profile =
			// (EnterpriseEvaluationProfile) ((ConnectableUserObject) uo)
			// .getReference();
			// pMController.getEnterpriseEvaluationService().deleteEnterpriseProfile(profile);
			// getIndicatorTree().setTargetTree(null);
			if (node.getParent() != null
					&& ((DefaultMutableTreeNode) node.getParent())
							.getUserObject() instanceof AssocUserObject) {
				pMController
						.getConnectableService()
						.removeConnectableFromAssoc(
								((ConnectableUserObject) uo).getReference(),
								((AssocUserObject) ((DefaultMutableTreeNode) node
										.getParent()).getUserObject())
										.getReference());
			}
		} else if (uo instanceof AssocUserObject) {
			// EnterpriseEvaluationProfile profile =
			// (EnterpriseEvaluationProfile) ((AssocUserObject) uo).getParent()
			// .getReference();
			pMController.getConnectableService().delete(
					((AssocUserObject) uo).getReference());
		}

		if (node.getParent() != null)
			((DefaultMutableTreeNode) node.getParent()).remove(node);
		targetTreeRefresh = 0l;
		refreshTargetTree();
	}

	/**
	 * this method is needed by the linking mechanism. the parameter has to be a
	 * String in order to make it persistent
	 */
	@Override
	public void setSelectedEProfile(String id) {
		logger.debug("setSelectedEProfile: " + id);
		setSelectedEProfile(pMController.getEnterpriseEvaluationService()
				.retrieveEnterpriseProfile(new Long(id)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.evaluation.performanceMeasurement.web.controller.impl.
	 * PMEnterpriseProfileDesignController#getNewEProfileAggregationFunction ()
	 */

	@Override
	public SelectItem[] getFunctionNames() {
		if (/* targetTreeRefresh || */functionnames == null
				|| functionnames.length == 0) {
			List<SelectItem> funs = new ArrayList<SelectItem>();
			for (Iterator<String> it = pMController
					.getEnterpriseEvaluationService()
					.retrieveEvaluationFunctionNames().iterator(); it.hasNext();) {
				String name = it.next();
				if (!name
						.startsWith(PMNetworkProfileDesignControllerImpl.COMPETENCES_ALL_HAVE_ASSOC)
						&& !name
								.startsWith(PMNetworkProfileDesignControllerImpl.COMPETENCES_FOR_ROLE_ASSOC)
						&& !name
								.startsWith(PMNetworkProfileDesignControllerImpl.COMPETENCES_ONE_OR_MORE_HAVE_ASSOC))
					funs.add(new SelectItem(it.next()));
			}
			logger.debug("PMjsfContorller: Functionnames: #" + funs.size());
			functionnames = funs.toArray(new SelectItem[funs.size()]);
		}
		updateUI();

		return functionnames;
	}

	public PanelPopup getLinkableTypeSelectionPopup(final JsfLink jsfLink,
			RootLinkController rootLinkController) {
		return pMController.getLinkableTypeSelectionPopup(jsfLink,
				rootLinkController,
				PMController.SELECT_ENTERPRISEEVALUATIONPROFILE);
	}

	@Override
	public String getNewEProfileAggregationFunction() {
		return newEProfileFunction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.evaluation.performanceMeasurement.web.controller.impl.
	 * PMEnterpriseProfileDesignController#setNewEProfileAggregationFunction
	 * (java.lang.String)
	 */
	@Override
	public void setNewEProfileAggregationFunction(
			String newEProfileAggregationFunction) {
		newEProfileFunction = newEProfileAggregationFunction;
		UserObject uo = getIndicatorTree().getSelectedNodeObject();
		if (uo instanceof AssocUserObject) {
			Association a = ((AssocUserObject) uo).getReference();
			if (!a.getType().getType().equals(newEProfileAggregationFunction)) {
				AssocType at = this.pMController
						.getConnectableService()
						.retrieveAssociationType(newEProfileAggregationFunction);
				if (at == null) {
					at = new AssocType(newEProfileAggregationFunction);
					this.pMController.getConnectableService()
							.createAssociationType(at);
				}
				a.setType(at);
				this.pMController.getConnectableService().update(a);
				targetTreeRefresh = 0l;
				refreshTargetTree();
			}
		}
	}
}
