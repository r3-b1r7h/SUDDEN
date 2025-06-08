package biz.sudden.evaluation.performanceMeasurement.web.controller.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.AssocRoleType;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.AssociationRole;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.web.controller.tree.AssocUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.ConnectableUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.SearchedAssoc;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.baseAndUtility.web.controller.tree.TreeDragDrop;
import biz.sudden.baseAndUtility.web.controller.tree.TreeShowNoRole;
import biz.sudden.baseAndUtility.web.controller.tree.UserObject;
import biz.sudden.baseAndUtility.web.controller.tree.UserObject.UserObjectActionEvent;
import biz.sudden.evaluation.performanceMeasurement.domain.EvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.domain.WeightedSumFunction;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;

import com.icesoft.faces.async.render.OnDemandRenderer;
import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;
import com.icesoft.faces.component.dragdrop.DndEvent;
import com.icesoft.faces.component.dragdrop.DragEvent;
import com.icesoft.faces.component.dragdrop.DropEvent;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public abstract class PMProfileDesignControllerImpl implements Renderable,
		ActionListener {

	protected Logger logger = Logger.getLogger(this.getClass());
	protected PMController pMController;

	protected String newEProfileFunction = WeightedSumFunction.TYPE_NAME;
	protected String selectedProfileName = "[new Profile]";
	protected String newEProfileParameter = "0.1";

	protected PersistentFacesState state = null;
	protected OnDemandRenderer renderer;
	protected int visibleTreeDepth = 1;

	protected long targetTreeRefresh = 0l;
	protected long sourceTreeRefresh = 0l;

	protected long refreshInterval = 5000;

	public abstract TreeDragDrop getIndicatorTree();

	public abstract void dropListener(DropEvent dropEvent);

	public Tree getTargetTreeForGUI() {
		// refreshTargetTree();
		return getIndicatorTree().getTargetTree();
	}

	public Tree getSourceTreeForGUI() {
		// refreshSourceTree();
		return getIndicatorTree().getSourceTree();
	}

	public void dragListener(DragEvent dragEvent) {
		if (dragEvent.getTargetDragValue() instanceof UserObject) {
			UserObject dragNode = (UserObject) dragEvent.getTargetDragValue();
			logger
					.debug("PMProfileDesignControllerImpl: dragListener: dragged: "
							+ dragNode
							+ " "
							+ DndEvent.getEventName(dragEvent.getEventType()));
			if (dragNode != null) {
				if (dragNode instanceof ConnectableUserObject
						&& dragNode.getWrapper().getParent() != null
						&& dragNode.getWrapper().getRoot().equals(
								getIndicatorTree().getTargetTree().getRoot())) {
					// drag away from target Tree!!
					UserObject parentO = (UserObject) ((DefaultMutableTreeNode) dragNode
							.getWrapper().getParent()).getUserObject();
					if (parentO instanceof AssocUserObject) {
						Association a = ((AssocUserObject) parentO)
								.getReference();
						pMController.getConnectableService()
								.removeConnectableFromAssoc(
										((ConnectableUserObject) dragNode)
												.getReference(), a);
						((ConnectableUserObject) dragNode)
								.deleteNodeAndSubNodes();
					} else
						logger.error("Drag went wrong !!");
				}
			}
			updateUI();
		}
	}

	public List<EnterpriseEvaluationProfile> retrieveAllEnterpriseProfiles() {
		List<EnterpriseEvaluationProfile> r = pMController
				.getEnterpriseEvaluationService().retrieveEnterpriseProfile(
						(String) null);
		logger.debug(" -> retrieveAllEnterpriseProfiles: " + r.size());
		return r;
	}

	public List<NetworkEvaluationProfile> retrieveAllNetworkProfiles() {
		List<NetworkEvaluationProfile> r = pMController
				.getNetworkEvaluationService().retrieveNetworkProfile(
						(String) null);
		logger.debug(" -> retrieveAllNetworkProfiles: " + r.size());
		return r;
	}

	public String getSelectedName() {
		if (getIndicatorTree().getSelectedText() != null) {
			UserObject uo = getIndicatorTree().getSelectedNodeObject();
			if (uo instanceof ConnectableUserObject)
				selectedProfileName = ((ConnectableUserObject) uo)
						.getReference().toString();
			else if (uo instanceof AssocUserObject)
				selectedProfileName = ((AssocUserObject) uo).getReference()
						.getType().getType();
			else
				selectedProfileName = uo.getText();
		}
		if (selectedProfileName == null)
			selectedProfileName = "";
		return selectedProfileName;
	}

	public void setSelectedName(String newName) {
		this.selectedProfileName = newName;
		UserObject uo = getIndicatorTree().getSelectedNodeObject();
		if (uo != null) {
			if (uo instanceof ConnectableUserObject) {
				Method m = null;
				try {
					m = ((ConnectableUserObject) uo).getReference().getClass()
							.getMethod("setName", String.class);
				} catch (Exception e) {
					try {
						m = ((ConnectableUserObject) uo).getReference()
								.getClass().getMethod("setText", String.class);
					} catch (Exception ee) {
					}
				}
				if (m != null) {
					try {
						m.invoke(((ConnectableUserObject) uo).getReference(),
								newName);
					} catch (Exception e) {
						logger.debug("Failed to call "
								+ m.getName()
								+ " on "
								+ ((ConnectableUserObject) uo).getReference()
										.toString());
					}
				} else {
					logger.debug("Can not setSelectedName on "
							+ ((ConnectableUserObject) uo).getReference()
									.toString());
				}
			}
		}
	}

	public String getNewEProfileParameter() {
		return newEProfileParameter;
	}

	public void setNewEProfileParameter(String newWeight) {
		if (!newWeight.equals(newEProfileParameter)) {
			this.newEProfileParameter = newWeight;
			UserObject uo = getIndicatorTree().getSelectedNodeObject();
			if (uo instanceof AssocUserObject) {
				Association a = ((AssocUserObject) uo).getReference();
				Connectable c = ((AssocUserObject) uo).getParent()
						.getReference();
				setAssocRoleType(a, c, ((AssocUserObject) uo)
						.getParentRoleName(), newWeight);
			} else if (uo instanceof ConnectableUserObject) {
				Connectable c = ((ConnectableUserObject) uo).getReference();
				String roleType = ((ConnectableUserObject) uo)
						.getCurrentRoleType();
				if (((DefaultMutableTreeNode) uo.getWrapper().getParent()) != null) {
					Object o = ((DefaultMutableTreeNode) uo.getWrapper()
							.getParent()).getUserObject();
					if (o != null && o instanceof AssocUserObject) {
						Association a = ((AssocUserObject) o).getReference();
						setAssocRoleType(a, c, roleType, newWeight);
					}
				}
			}
		}
	}

	protected void setAssocRoleType(Association a, Connectable c,
			String oldType, String newType) {
		List<AssociationRole> ars = pMController.getConnectableService()
				.retrieveAssociationRole(a, c, oldType);

		for (AssociationRole ar : ars) {
			if (ar.getParent().equals(a)) {
				setAssocRoleType(ar, newType);
				break;
			}
		}
	}

	protected void setAssocRoleType(AssociationRole ar, String newType) {
		AssocRoleType art = pMController.getConnectableService()
				.retrieveAssocRoleType(newType);
		if (art == null) {
			art = pMController.getConnectableService().createAssocRoleType(
					newType);
		}
		if (!ar.getType().getId().equals(art.getId())) {
			ar.setType(art);
			pMController.getConnectableService().update(ar);
			targetTreeRefresh = 0l;
			refreshTargetTree();
		}
	}

	protected void refreshSourceTree() {
		long now = System.currentTimeMillis();
		if ((now - sourceTreeRefresh) > refreshInterval) {
			logger.debug("refreshSourceTree");
			sourceTreeRefresh = now;
			List<EnterpriseEvaluationProfile> profs = pMController
					.getEnterpriseEvaluationService()
					.retrieveAllEnterpriseProfiles();
			List<Connectable> conn = new ArrayList<Connectable>(profs.size());

			conn.addAll(profs);
			Tree source = getIndicatorTree().getSourceTree();
			source.clearTree();
			source.setAssociationScope(pMController.getScopeController()
					.getUnspecifiedScope());
			// FIXME just for testing set this to true
			source.setAlsoCreateAssociationNodes(false);
			source.setAlsoShowOccurrence(false);
			source.setExpandAll(true);
			// Performance Problems
			source.removeWhenNotInRoleOnly(conn,
					EvaluationProfile.ResultRoleType, pMController
							.getScopeController().getUnspecifiedScope());

			SearchedAssoc sa = new SearchedAssoc(null,
					EvaluationProfile.ResultRoleType, null);
			source.retrieveNodes(visibleTreeDepth, conn, sa);

			// Performance Problems
			// List<Dimension> cds =
			// pMController.getEnterpriseEvaluationService().retrieveAllCompetenceDimensions();
			// conn.clear();
			// conn.addAll(cds);
			//			
			// source.retrieveNodes(visibleTreeDepth,conn,
			// source.attachNodeObj("Competences"), sa);

			getIndicatorTree().addActionListener(this);

			// updateUI();
			// } else {
			// logger.debug("dont refreshSourceTree");
			logger.debug("refresh Source Tree: "
					+ (System.currentTimeMillis() - now));
		}
	}

	protected void refreshTargetTree() {
		long now = System.currentTimeMillis();
		if ((now - targetTreeRefresh) > refreshInterval) {
			logger.debug("refreshTargetTree");
			targetTreeRefresh = now;
			Tree targetTree = getIndicatorTree().getTargetTree();
			if (targetTree != null) {
				SearchedAssoc sa = new SearchedAssoc(null,
						EvaluationProfile.ResultRoleType, null);
				targetTree.setAssociationScope(pMController
						.getScopeController().getUserScope());
				targetTree.setExpandAll(true);
				targetTree.setAlsoCreateAssociationNodes(true);
				targetTree.recreateTree(visibleTreeDepth, sa);
			} else {
				if (((DefaultMutableTreeNode) getIndicatorTree()
						.getSourceTree().getRoot()).getChildCount() > 0) {
					DefaultMutableTreeNode sourceWrapper = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) getIndicatorTree()
							.getSourceTree().getRoot()).getFirstChild();
					if (sourceWrapper.getUserObject() instanceof ConnectableUserObject) {
						if (sourceWrapper.getUserObject() instanceof NetworkEvaluationProfile)
							targetTree = new TreeShowNoRole(pMController
									.getConnectableService(), pMController
									.getScopeController(),
									((ConnectableUserObject) sourceWrapper
											.getUserObject()).getReference());
						else
							targetTree = new Tree(pMController
									.getConnectableService(), pMController
									.getScopeController(),
									((ConnectableUserObject) sourceWrapper
											.getUserObject()).getReference());
						getIndicatorTree().setTargetTree(targetTree);
						SearchedAssoc sa = new SearchedAssoc(null,
								EvaluationProfile.ResultRoleType, null);
						targetTree.setAlsoCreateAssociationNodes(true);
						targetTree.setExpandAll(true);
						targetTree.setAssociationScope(pMController
								.getScopeController().getUserScope());
						targetTree.recreateTree(visibleTreeDepth, sa);
					} else {
						newEmptyTargetTree();
					}
				} else {
					newEmptyTargetTree();
				}
			}
			getIndicatorTree().setSelectedNodeObject(
					(UserObject) ((DefaultMutableTreeNode) getIndicatorTree()
							.getTargetTree().getRoot()).getUserObject());
			getIndicatorTree().addActionListener(this);
			// } else {
			// logger.debug("don't refreshTargetTree");
		}
		// updateUI();
	}

	/**
	 * called by TreeDragDrop when some action (like clicks) happen!
	 * 
	 * */
	public void processAction(ActionEvent arg0) throws AbortProcessingException {
		// check types && if a node in the targetTree is clicked.... First if a
		// Profile has been clicked
		if (arg0 instanceof UserObjectActionEvent
				&& ((UserObjectActionEvent) arg0).getUserObject() instanceof ConnectableUserObject
				&& ((ConnectableUserObject) ((UserObjectActionEvent) arg0)
						.getUserObject()).getReference() instanceof EnterpriseEvaluationProfile) {
			selectProfile((((ConnectableUserObject) ((UserObjectActionEvent) arg0)
					.getUserObject())).getWrapper());
		} else if (arg0 instanceof UserObjectActionEvent // check if its an
				// associations that has
				// been clicked
				&& ((UserObjectActionEvent) arg0).getUserObject() instanceof AssocUserObject) {
			selectAggregationFunction((AssocUserObject) ((UserObjectActionEvent) arg0)
					.getUserObject());
		} else {
			logger
					.debug("PMProfileDesignControllerImpl: processAction with unknown type!!"
							+ arg0.toString());
		}
	}

	protected void selectProfile(DefaultMutableTreeNode prof) {
		if (prof.getUserObject() != null
				&& prof.getUserObject() instanceof ConnectableUserObject) {
			// logger.debug("PMProfileDesignController -> selectProfile: "
			// + ((ConnectableUserObject)
			// prof.getUserObject()).getReference().getClass().getName());
			// logger.debug("PMProfileDesignController -> selectProfile: "
			// + ((ConnectableUserObject)
			// prof.getUserObject()).getReference().getId());

			if (prof != null
					&& prof.getRoot() != null
					&& prof.getRoot().equals(
							getIndicatorTree().getTargetTree().getRoot())) {
				getIndicatorTree().setSelectedNodeObject(
						((ConnectableUserObject) prof.getUserObject()));

				if (((ConnectableUserObject) prof.getUserObject())
						.getReference() instanceof EnterpriseEvaluationProfile)
					pMController
							.setSelectedEnterpriseEvaluationProfile((EnterpriseEvaluationProfile) ((ConnectableUserObject) prof
									.getUserObject()).getReference());
				else if (((ConnectableUserObject) prof.getUserObject())
						.getReference() instanceof NetworkEvaluationProfile)
					pMController
							.setSelectedNetworkEvaluationProfile((NetworkEvaluationProfile) ((ConnectableUserObject) prof
									.getUserObject()).getReference());

				newEProfileParameter = ((ConnectableUserObject) prof
						.getUserObject()).getCurrentRoleType();
			} else if (((ConnectableUserObject) prof.getUserObject())
					.getReference() instanceof EnterpriseEvaluationProfile) {
				setSelectedEProfile((EnterpriseEvaluationProfile) ((ConnectableUserObject) prof
						.getUserObject()).getReference());
			} else if (((ConnectableUserObject) prof.getUserObject())
					.getReference() instanceof NetworkEvaluationProfile) {
				setSelectedNProfile((NetworkEvaluationProfile) ((ConnectableUserObject) prof
						.getUserObject()).getReference());
			}
		}
	}

	/**
	 * this method is needed by the linking mechanism. the parameter has to be a
	 * String in order to make it persistent
	 */
	public void setSelectedEProfile(String id) {
		logger.debug("setSelectedEProfile: " + id);
		setSelectedEProfile(pMController.getEnterpriseEvaluationService()
				.retrieveEnterpriseProfile(new Long(id)));
	}

	public void setSelectedEProfile(EnterpriseEvaluationProfile eep) {
		logger.debug("PMEnterpriseProfileDesignController -> selectProfile: "
				+ eep.getName());

		this.selectedProfileName = eep.getName();

		if (getIndicatorTree().getTargetTree() == null) {
			Tree target = new Tree(pMController.getConnectableService(),
					pMController.getScopeController(), eep);
			// target.recreateTree(5, (List<SearchedAssoc>) null);
			getIndicatorTree().setSelectedNodeObject(
					(UserObject) target.getRoot());
			target.setExpandAll(true);
			target.recreateTree(5, new SearchedAssoc(null,
					EvaluationProfile.ResultRoleType, null));
			getIndicatorTree().setTargetTree(target);
			pMController.setSelectedEnterpriseEvaluationProfile(eep);

		} else {
			getIndicatorTree().getTargetTree().clearTreeAndRootNode();
			// getIndicatorTree().getTargetTree().setRoot(
			// new DefaultMutableTreeNode(new
			// ConnectableUserObject(((ConnectableUserObject) prof
			// .getUserObject()).getReference())));
			getIndicatorTree().getTargetTree().retrieveTree(
					5,
					eep,
					new SearchedAssoc(null, EvaluationProfile.ResultRoleType,
							null));
			getIndicatorTree().setSelectedNodeObject(
					(UserObject) ((DefaultMutableTreeNode) getIndicatorTree()
							.getTargetTree().getRoot()).getUserObject());
			pMController.setSelectedEnterpriseEvaluationProfile(eep);
		}
		this.targetTreeRefresh = 0l;
		refreshTargetTree();
	}

	/**
	 * this method is needed by the linking mechanism. the parameter has to be a
	 * String in order to make it persistent
	 */
	public void setSelectedNProfile(String id) {
		logger.debug("setSelectedNProfile: " + id);
		setSelectedNProfile(pMController.getNetworkEvaluationService()
				.retrieveNetworkProfile(new Long(id)));
	}

	public void setSelectedNProfile(NetworkEvaluationProfile nep) {
		if (nep != null) {
			logger.debug("setSelectedNProfile: " + nep.getName());

			this.selectedProfileName = nep.getName();
			Tree targetTree = getIndicatorTree().getTargetTree();
			if (targetTree == null) {
				Tree target = new TreeShowNoRole(pMController
						.getConnectableService(), pMController
						.getScopeController(), nep);
				// target.recreateTree(5, (List<SearchedAssoc>) null);
				getIndicatorTree().setSelectedNodeObject(
						(UserObject) target.getRoot());
				target.setAlsoCreateAssociationNodes(true);
				target.setExpandAll(true);
				target.recreateTree(5, new SearchedAssoc(null,
						EvaluationProfile.ResultRoleType, null));

				getIndicatorTree().setTargetTree(target);
				pMController.setSelectedNetworkEvaluationProfile(nep);

			} else {
				targetTree.clearTreeAndRootNode();
				// targetTree.setRoot(
				// new DefaultMutableTreeNode(new
				// ConnectableUserObject(((ConnectableUserObject) prof
				// .getUserObject()).getReference())));
				targetTree.setAlsoCreateAssociationNodes(true);
				targetTree.setExpandAll(true);
				targetTree.retrieveTree(5, nep, new SearchedAssoc(null,
						EvaluationProfile.ResultRoleType, null));
				getIndicatorTree().setSelectedNodeObject(
						(UserObject) ((DefaultMutableTreeNode) targetTree
								.getRoot()).getUserObject());
				pMController.setSelectedNetworkEvaluationProfile(nep);
			}
		}
		// don't do this... it will loop!
		// this.targetTreeRefresh = 0l;
		refreshTargetTree();
	}

	protected void selectAggregationFunction(AssocUserObject xx) {
		newEProfileParameter = xx.getParentRoleName();
		newEProfileFunction = xx.getAssocTypeName();
		getIndicatorTree().setSelectedNodeObject(xx);
	}

	protected void newEmptyTargetTree() {
		Tree target = new Tree(pMController.getConnectableService(),
				pMController.getScopeController(), selectedProfileName);
		getIndicatorTree().setTargetTree(target);
		target.setExpandAll(true);
		target.setAlsoCreateAssociationNodes(true);
		target.setAssociationScope(pMController.getScopeController()
				.getUserScope());
		target.setAlsoCreateAssociationNodes(true);
	}

	public int getTreeDepth() {
		return visibleTreeDepth;
	}

	public void setTreeDepth(int treeDepth) {
		visibleTreeDepth = treeDepth;
	}

	public void refreshTree(ActionEvent ae) {
		logger.warn("refreshTree " + ae);
		refreshTree();
	}

	public void refreshTree() {
		logger.debug("refreshTree");
		targetTreeRefresh = 0l;
		sourceTreeRefresh = 0l;
		refreshSourceTree();
		refreshTargetTree();
	}

	/**
	 * 
	 * @return true if the links of the currently selected profile should be
	 *         shown. Used to not show anything if no profile is selected.
	 */
	public boolean showLinks() {
		TreeDragDrop tdd = getIndicatorTree();
		if (tdd != null && tdd.getSelectedNodeObject() != null
				&& tdd.getSelectedNodeObject() instanceof ConnectableUserObject)
			return true;
		return false;
	}

	public boolean getShowLinks() {
		return showLinks();
	}

	public void renderingException(RenderingException arg0) {
		logger.debug(arg0.getLocalizedMessage());
	}

	public void setRenderManager(RenderManager rm) {
		logger.debug("PMEnterpriseProfileDesignControllerImpl.setRedermanager");
		renderer = rm.getOnDemandRenderer("PMEnterpriseProfileDesignRenderer");
		renderer.setRenderManager(rm);
		if (!renderer.contains(this))
			renderer.add(this);
	}

	public PersistentFacesState getState() {
		if (state == null) {
			state = PersistentFacesState.getInstance();
		}
		return state;
	}

	protected void updateUI() {
		try {
			// code below is heavy on the server side .... therefore we now have
			// a RenderManager!! ->
			// renderer.requestRender();
			// state.executeAndRender();
			// state.renderLater();
			// state.render();
			// long now = System.currentTimeMillis();
			renderer.setBroadcasted(true);
			renderer.requestRender();
			// logger.debug("updateUI: " + (System.currentTimeMillis()-now));
		} catch (Exception e) {
			logger
					.debug("PMEnterpriseProfileDesignControllerImpl.updateUI: Render Exception");
			logger.debug(e.getMessage());
		}
	}

	public void setPMController(PMController controller) {
		pMController = controller;
	}

}
