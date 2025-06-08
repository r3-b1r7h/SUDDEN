package biz.sudden.evaluation.performanceMeasurement.web.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.web.controller.tree.AssocUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.ConnectableUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.SearchedAssoc;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.baseAndUtility.web.controller.tree.TreeDragDrop;
import biz.sudden.baseAndUtility.web.controller.tree.TreeShowNoRole;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.handleBO.service.HandleBOService;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.overallControl.TeamFormationControllerService;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeededByNetworkEvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.domain.EvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.domain.WeightedSumFunction;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMNetworkProfileDesignController;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.kdm.web.controller.CaseFileController;

import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.component.dragdrop.DndEvent;
import com.icesoft.faces.component.dragdrop.DropEvent;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public class PMNetworkProfileDesignControllerImpl extends
		PMProfileDesignControllerImpl implements
		PMNetworkProfileDesignController {

	protected Logger logger = Logger.getLogger(this.getClass());

	protected static String COMPETENCES_ALL_HAVE_ASSOC = "Competences all have";
	protected static String COMPETENCES_ONE_OR_MORE_HAVE_ASSOC = "Competences one or more have";
	protected static String COMPETENCES_FOR_ROLE_ASSOC = "Role: ";
	// protected static String SOCIAL_NETWORK_COUNT_ASSOC =
	// "Previous Collaborations";
	protected static String NIL_NAME = "_";

	protected CaseFileController caseFileController;

	protected String[] selectedASNRoleIDs;
	protected String[] selectedProfileIDs;

	protected long SelectListUpdated = 0L;
	protected HashMap<Long, AbstractSupplyNetwork> availableASNs = new HashMap<Long, AbstractSupplyNetwork>();
	// List<SelectItem> selectASNs;

	protected List<SelectItem> selectCaseFiles;
	// long selectedCaseFile;
	protected AbstractSupplyNetwork selectedASN;

	protected long updatedEvalProf = 0L;

	protected HandleBOService handleBoService;

	protected TeamFormationControllerService teamController;

	public PMNetworkProfileDesignControllerImpl() {
	}

	@Override
	public TreeDragDrop getIndicatorTree() {
		// logger.debug("PM Controller getIndicatorTree "+indicatorTree.toString());
		TreeDragDrop result = pMController.getNetworkEvaluationService()
				.retrieveTrees(pMController.getScopeController());
		return result;
	}

	@Override
	public void processAction(ActionEvent arg0) {
		// do nothing
	}

	@Override
	public void dropListener(DropEvent dropEvent) {
		if (dropEvent.getTargetDragValue() instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode dragNode = (DefaultMutableTreeNode) dropEvent
					.getTargetDragValue();
			DefaultMutableTreeNode dropTargetNode = (DefaultMutableTreeNode) dropEvent
					.getTargetDropValue();
			logger
					.debug("PMNetworkProfileDesignControllerImpl: dropListener: dragged: "
							+ dragNode
							+ " event: "
							+ DndEvent.getEventName(dropEvent.getEventType())
							+ " target: " + dropTargetNode);
			long time = System.currentTimeMillis();
			if (dragNode != null && dropTargetNode != null) {
				if (dropTargetNode.getUserObject() instanceof ConnectableUserObject
						&& dropTargetNode.getParent() != null)
					dropTargetNode = (DefaultMutableTreeNode) dropTargetNode
							.getParent();
				if (dropTargetNode.getUserObject() instanceof AssocUserObject) {
					this.newEProfileParameter = ((AssocUserObject) dropTargetNode
							.getUserObject()).getChildRoleName();
					this.newEProfileFunction = ((AssocUserObject) dropTargetNode
							.getUserObject()).getAssocTypeName();
					if ((dragNode.getUserObject() instanceof ConnectableUserObject)
							&& (((ConnectableUserObject) dragNode
									.getUserObject()).getReference() instanceof EnterpriseEvaluationProfile)) {
						addProfile(
								getIndicatorTree().getTargetTree(),
								(AssocUserObject) dropTargetNode
										.getUserObject(),
								(EnterpriseEvaluationProfile) ((ConnectableUserObject) dragNode
										.getUserObject()).getReference(),
								pMController.getScopeController()
										.getUserScope());
						// If dragOption in jspx file is "dragGhost" this is not
						// needed!
						// dragNode.removeFromParent();
						targetTreeRefresh = 0l;
					} else if ((dragNode.getUserObject() instanceof ConnectableUserObject)
							&& ((ConnectableUserObject) dragNode
									.getUserObject()).getReference() instanceof Dimension) {
						addProfile(getIndicatorTree().getTargetTree(),
								(AssocUserObject) dropTargetNode
										.getUserObject(),
								(Dimension) ((ConnectableUserObject) dragNode
										.getUserObject()).getReference(),
								pMController.getScopeController()
										.getUserScope());
						// If dragOption in jspx file is "dragGhost" this is not
						// needed!
						// dragNode.removeFromParent();
						targetTreeRefresh = 0l;
					}
				}
			}
			refreshTargetTree();
			// refreshSourceTree();
			logger
					.debug("PMNetworkProfileDesignControllerImpl: addedProfile & refreshed tree"
							+ (System.currentTimeMillis() - time));
			updateUI();
			logger.debug("PMNetworkProfileDesignControllerImpl: updated UI"
					+ (System.currentTimeMillis() - time));
		}
	}

	protected void addProfile(Tree tree, AssocUserObject targetAUO,
			Connectable profile, Scope scope) {
		// needed ??
		if (profile instanceof EnterpriseEvaluationProfile)
			pMController.getEnterpriseEvaluationService()
					.updateEnterpriseProfile(
							(EnterpriseEvaluationProfile) profile);

		if (((DefaultMutableTreeNode) tree.getRoot()).getUserObject() instanceof ConnectableUserObject
				&& ((ConnectableUserObject) ((DefaultMutableTreeNode) tree
						.getRoot()).getUserObject()).getReference() instanceof NetworkEvaluationProfile) {
			NetworkEvaluationProfile nep = (NetworkEvaluationProfile) ((ConnectableUserObject) ((DefaultMutableTreeNode) tree
					.getRoot()).getUserObject()).getReference();
			pMController.getNetworkEvaluationService().update(nep);
			if (targetAUO.getAssocTypeName().equals(COMPETENCES_ALL_HAVE_ASSOC)) {
				List<EnterpriseEvaluationProfile> cc = this.pMController
						.getEnterpriseEvaluationService()
						.retrieveEnterpriseProfile(NIL_NAME);
				CompetenceNeededByNetworkEvaluationProfile cah = new CompetenceNeededByNetworkEvaluationProfile();
				cah.setCompetenceProfile(profile);
				cah.setNetworkEvaluationProfile(nep);
				nep.addCompetenceAllHave(cah);
				pMController.getNetworkEvaluationService().create(cah);
				Association aa = pMController.getConnectableService()
						.retrieveAssociation(targetAUO.getReference().getId());
				this.pMController.getConnectableService()
						.addConnectableToAssociation(aa, cah, "1.0");
				for (int ii = 0; ii < cc.size(); ++ii)
					this.pMController.getConnectableService()
							.removeConnectableFromAssoc(cc.get(ii), aa);
			} else if (targetAUO.getAssocTypeName().equals(
					COMPETENCES_ONE_OR_MORE_HAVE_ASSOC)) {
				List<EnterpriseEvaluationProfile> cc = this.pMController
						.getEnterpriseEvaluationService()
						.retrieveEnterpriseProfile(NIL_NAME);
				CompetenceNeededByNetworkEvaluationProfile cah = new CompetenceNeededByNetworkEvaluationProfile();
				cah.setCompetenceProfile(profile);
				cah.setNetworkEvaluationProfile(nep);
				nep.addCompetenceOneOrMoreHave(cah);
				pMController.getNetworkEvaluationService().create(cah);
				Association aa = pMController.getConnectableService()
						.retrieveAssociation(targetAUO.getReference().getId());
				this.pMController.getConnectableService()
						.addConnectableToAssociation(aa, cah, "1.0");
				for (int ii = 0; ii < cc.size(); ++ii)
					this.pMController.getConnectableService()
							.removeConnectableFromAssoc(cc.get(ii), aa);
			} else if (targetAUO.getAssocTypeName().startsWith(
					COMPETENCES_FOR_ROLE_ASSOC)) {
				List<Connectable> cc = new ArrayList<Connectable>();
				cc.addAll(this.pMController.getEnterpriseEvaluationService()
						.retrieveEnterpriseProfile(NIL_NAME));
				String asnRole = targetAUO.getAssocTypeName().substring(
						COMPETENCES_FOR_ROLE_ASSOC.length());
				CompetenceNeeded cah = new CompetenceNeeded();
				cah.setCompetenceProfile(profile);
				for (ASNRoleNode node : selectedASN.getAllRoleNodes()) {
					if (node.getQualificationProfile().getFirstProductName()
							.startsWith(asnRole)) {
						if (node.getCompetenceNeeded() != null) {
							node.getCompetenceNeeded().setCompetenceProfile(
									null);
							cc.add(node.getCompetenceNeeded());
						}
						node.setCompetenceNeeded(cah);
					}
				}
				pMController.getNetworkEvaluationService().create(cah);
				Association aa = pMController.getConnectableService()
						.retrieveAssociation(targetAUO.getReference().getId());
				this.pMController.getConnectableService()
						.addConnectableToAssociation(aa, cah, "1.0");
				for (int ii = 0; ii < cc.size(); ++ii) {
					Connectable toBeRemoved = cc.get(ii);
					this.pMController.getConnectableService()
							.removeConnectableFromAssoc(toBeRemoved, aa);
				}
				// } else if
				// (targetAUO.getAssocTypeName().equals(SOCIAL_NETWORK_COUNT_ASSOC)
				// ) {
				// nep.setSocialNetworkCount(Boolean.TRUE);
			}
			selectedASN.setASNEvaluationProfile(nep);

			this.pMController.getNetworkEvaluationService().update(nep);
			handleBoService.update(selectedASN);
			caseFileController.getKDMService().getCaseService().update(
					getSelectedCaseFile());
			this.targetTreeRefresh = 0l;
		}
		refreshTargetTree();
	}

	@Override
	public NetworkEvaluationProfile getNetworkEvaluationProfile() {
		if (selectedASN == null) {
			CaseFile cf = getSelectedCaseFile();
			if (cf != null) {
				if (cf.getAsnFinalTeam() != null) {
					setSelectedASN(cf.getAsnFinalTeam());
				} else if (cf.getAsnPrototypeTeam() != null) {
					setSelectedASN(cf.getAsnPrototypeTeam());
				}
			} else {
				List<CaseFile> cfs = caseFileController.getKDMService()
						.getCaseService().retrieveAllCaseFiles();
				if (cfs.size() > 0) {
					cf = cfs.get(0);
					setSelectedCaseFile(cf);
					if (cf.getAsnFinalTeam() != null) {
						setSelectedASN(cf.getAsnFinalTeam());
					} else if (cf.getAsnPrototypeTeam() != null) {
						setSelectedASN(cf.getAsnPrototypeTeam());
					}
				}
			}
		}
		if (selectedASN != null) {
			if (selectedASN.getASNEvaluationProfile() == null) {
				NetworkEvaluationProfile nep = new NetworkEvaluationProfile(
						"Network Evaluation Profile for: "
								+ selectedASN.getGoalProductString().substring(
										selectedASN.getGoalProductString()
												.indexOf('#')));

				this.pMController.getNetworkEvaluationService().create(nep);
				selectedASN.setASNEvaluationProfile(nep);
			}
			NetworkEvaluationProfile nep = selectedASN
					.getASNEvaluationProfile();
			nep.setName("Network Evaluation Profile for: "
					+ selectedASN.getGoalProductString());
			this.pMController.getNetworkEvaluationService().update(nep);
			List<EnterpriseEvaluationProfile> nils = this.pMController
					.getEnterpriseEvaluationService()
					.retrieveEnterpriseProfile(NIL_NAME);
			EnterpriseEvaluationProfile nil;
			if (nils == null || nils.size() == 0) {
				nil = new EnterpriseEvaluationProfile(NIL_NAME);
				this.pMController.getEnterpriseEvaluationService()
						.createEnterpriseProfile(nil);
			} else
				nil = nils.get(0);

			Scope userscope = this.pMController.getScopeController()
					.getUserScope();
			this.pMController.getEnterpriseEvaluationService()
					.createOrRetrieveEvaluationFunction(
							COMPETENCES_ALL_HAVE_ASSOC,
							new WeightedSumFunction());
			List<Association> ass = this.pMController.getConnectableService()
					.retrieveAssociationBy(COMPETENCES_ALL_HAVE_ASSOC, nep,
							EvaluationProfile.ResultRoleType, userscope);
			if (ass == null || ass.size() == 0)
				this.pMController.getConnectableService()
						.associateTwoConnectables(nep, nil,
								COMPETENCES_ALL_HAVE_ASSOC,
								EvaluationProfile.ResultRoleType, "0.0",
								userscope);

			this.pMController.getEnterpriseEvaluationService()
					.createOrRetrieveEvaluationFunction(
							COMPETENCES_ONE_OR_MORE_HAVE_ASSOC,
							new WeightedSumFunction());
			ass = this.pMController.getConnectableService()
					.retrieveAssociationBy(COMPETENCES_ONE_OR_MORE_HAVE_ASSOC,
							nep, EvaluationProfile.ResultRoleType, userscope);
			if (ass == null || ass.size() == 0)
				this.pMController.getConnectableService()
						.associateTwoConnectables(nep, nil,
								COMPETENCES_ONE_OR_MORE_HAVE_ASSOC,
								EvaluationProfile.ResultRoleType, "0.0",
								userscope);

			// this.pMController.getEnterpriseEvaluationService().createOrRetrieveEvaluationFunction(SOCIAL_NETWORK_COUNT_ASSOC,
			// new WeightedSumFunction());
			// ass =
			// this.pMController.getConnectableService().retrieveAssociationBy(SOCIAL_NETWORK_COUNT_ASSOC,
			// nep, EvaluationProfile.ResultRoleType, userscope);
			// if(ass == null || ass.size()==0)
			// this.pMController.getConnectableService().associateTwoConnectables(nep,
			// nil, SOCIAL_NETWORK_COUNT_ASSOC,
			// EvaluationProfile.ResultRoleType,"0.0", userscope);

			for (ASNRoleNode node : selectedASN.getAllRoleNodes()) {
				this.pMController.getEnterpriseEvaluationService()
						.createOrRetrieveEvaluationFunction(
								COMPETENCES_FOR_ROLE_ASSOC
										+ node.getQualificationProfile()
												.getFirstProductName(),
								new WeightedSumFunction());
				ass = this.pMController.getConnectableService()
						.retrieveAssociationBy(
								COMPETENCES_FOR_ROLE_ASSOC
										+ node.getQualificationProfile()
												.getFirstProductName(), nep,
								EvaluationProfile.ResultRoleType, userscope);
				if (ass == null || ass.size() == 0)
					this.pMController.getConnectableService()
							.associateTwoConnectables(
									nep,
									nil,
									COMPETENCES_FOR_ROLE_ASSOC
											+ node.getQualificationProfile()
													.getFirstProductName(),
									EvaluationProfile.ResultRoleType, "0.0",
									userscope);
			}
			this.pMController.getNetworkEvaluationService().update(nep);
			return nep;
		}
		return null;
	}

	@Override
	protected void refreshTargetTree() {
		long now = System.currentTimeMillis();
		if (now - targetTreeRefresh > super.refreshInterval) {
			targetTreeRefresh = now;
			setSelectedNProfile(getNetworkEvaluationProfile());
			Tree target = getIndicatorTree().getTargetTree();
			target.setExpandAll(true);
			target.recreateTree(visibleTreeDepth, new SearchedAssoc(null,
					EvaluationProfile.ResultRoleType, null));
			getIndicatorTree().addActionListener(this);
			logger.debug("refreshTargetTree - nw: "
					+ (System.currentTimeMillis() - now));
			// } else {
			// logger.debug("don't refreshTargetTree - nw");
		}
		// super.refreshTargetTree();
	}

	@Override
	protected void newEmptyTargetTree() {
		Tree target = new TreeShowNoRole(pMController.getConnectableService(),
				pMController.getScopeController(), selectedProfileName);

		getIndicatorTree().setTargetTree(target);
		target.setExpandAll(true);
		target.setAlsoCreateAssociationNodes(true);
		target.setAssociationScope(pMController.getScopeController()
				.getUserScope());
		target.setAlsoCreateAssociationNodes(true);
	}

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	@Override
	public Long getASNSelect() {
		AbstractSupplyNetwork asn = getSelectedASN();
		if (asn != null)
			return asn.getId();
		else
			return null;
	}

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	@Override
	public void setASNSelect(Long id) {
		CaseFile cf = getSelectedCaseFile();
		// availableASNs is a hashmap and wie use the Id as key
		if (cf.getAsnPrototypeTeam() != null
				&& cf.getAsnPrototypeTeam().getId().equals(id))
			setSelectedASN(availableASNs.get(id));
		else if (cf.getAsnFinalTeam() != null
				&& cf.getAsnFinalTeam().getId().equals(id))
			setSelectedASN(availableASNs.get(id));
	}

	@Override
	public AbstractSupplyNetwork getSelectedASN() {
		return selectedASN;
	}

	@Override
	public void setSelectedASN(AbstractSupplyNetwork asn) {
		pMController.getNetworkEvaluationService().setSelectedASN(asn,
				pMController.getScopeController());
		selectedASN = asn;
	}

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	@Override
	public void selectedASNChanged(ValueChangeEvent event) {
		if (event.getNewValue() instanceof Long) {
			setASNSelect((Long) event.getNewValue());
			targetTreeRefresh = 0l;
			refreshTargetTree();
		}
		updateUI();
	}

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	@Override
	public List<SelectItem> getListOfASNs() {
		List<SelectItem> selectASNs = new ArrayList<SelectItem>();
		CaseFile cf = getSelectedCaseFile();
		if (cf != null) {
			this.availableASNs = new HashMap<Long, AbstractSupplyNetwork>();
			if (cf.getAsnPrototypeTeam() != null) {
				availableASNs.put(cf.getAsnPrototypeTeam().getId(), cf
						.getAsnPrototypeTeam());
				selectASNs.add(0, new SelectItem(cf.getAsnPrototypeTeam()
						.getId(), "ASN# " + cf.getAsnPrototypeTeam().getId()));

				setSelectedASN(cf.getAsnPrototypeTeam());
			}
			if (cf.getAsnFinalTeam() != null) {
				availableASNs.put(cf.getAsnFinalTeam().getId(), cf
						.getAsnFinalTeam());
				selectASNs.add(0, new SelectItem(cf.getAsnFinalTeam().getId(),
						"ASN# " + cf.getAsnFinalTeam().getId()));
				setSelectedASN(cf.getAsnFinalTeam());
			}
			// selectCaseFiles.add(new SelectItem(selected.getId(),
			// selected.getName()));
		}

		return selectASNs;
	}

	/** make it possible on the GUI to select one from the available Case Files */
	@Override
	// FIXME: Performance
	public Long getCaseFileId() {
		CaseFile cf = getSelectedCaseFile();
		if (cf == null)
			return null;
		return cf.getId();
	}

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	@Override
	public void setCaseFileId(Long id) {
		logger.warn("setCaseFileId " + id);
		CaseFile cf = getSelectedCaseFile();
		if (cf == null || !cf.getId().equals(id)) {
			cf = caseFileController.getKDMService().getCaseService()
					.retrieveCaseFile(id);
			setSelectedCaseFile(cf);
			targetTreeRefresh = 0l;
			refreshTargetTree();
			// takes to long
			// sourceTreeRefresh=0l;
			refreshSourceTree();
		}
	}

	@Override
	public CaseFile getSelectedCaseFile() {
		return pMController.getSelectedCaseFile();
	}

	@Override
	public void setSelectedCaseFile(CaseFile cf) {
		pMController.setSelectedCaseFile(cf);
		selectedASN = null;
		getListOfASNs();
	}

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	@Override
	public void selectedCaseFileChanged(ValueChangeEvent event) {
		if (event.getNewValue() instanceof Long) {
			setCaseFileId((Long) event.getNewValue());
		}
	}

	/**
	 * make it possible on the GUI to select one ASN from the available Case
	 * Files
	 */
	@Override
	public List<SelectItem> getListOfCaseFiles() {
		// if (selectCaseFiles == null || (System.currentTimeMillis() -
		// SelectListUpdated) > 50000) {
		// long starttime = System.currentTimeMillis();
		// selectCaseFiles = new ArrayList<SelectItem>();
		// for (CaseFile cf :
		// this.caseFileController.getKDMService().getCaseService().retrieveAllCaseFiles())
		// {
		// selectCaseFiles.add(new SelectItem(cf.getId(), cf.getName()));
		// if (getSelectedCaseFile() == null ||
		// getSelectedCaseFile().getId().equals(cf.getId())) {
		// logger.debug("Selected CF " + getSelectedCaseFile());
		// logger.debug("selecting CF " + cf);
		// setSelectedCaseFile(cf);
		// logger.debug("Now Selected CF " + getSelectedCaseFile());
		// setSelectedASN((AbstractSupplyNetwork) ((cf.getAsnFinalTeam() != null
		// && cf.getAsnFinalTeam().getId().equals(
		// cf.getId())) ? cf.getAsnFinalTeam() : cf.getAsnPrototypeTeam()));
		// }
		// }
		// // the java object for a given Hibernate element is new
		// Long oldVersion = getCaseFileId();
		// if (oldVersion != null)
		// this.setCaseFileId(oldVersion);
		//
		// this.SelectListUpdated = System.currentTimeMillis();
		// logger.debug("created List of ListOfCaseFiles #, took ms " +
		// selectCaseFiles.size() + ", " +
		// (System.currentTimeMillis()-starttime));
		// }
		selectCaseFiles = this.caseFileController.getCaseFiles();
		return selectCaseFiles;
	}

	@Override
	public List<SelectItem> getASNRoles() {
		long starttime = System.currentTimeMillis();
		List<ASNRoleNode> nodes = getASNRoleNodes();
		List<SelectItem> result = null;
		if (nodes != null) {
			result = new ArrayList<SelectItem>(nodes.size() + 1);
			for (ASNRoleNode node : nodes) {
				result.add(new SelectItem(node.getId(), node
						.getQualificationProfile().getFirstProductName()));
			}
		}
		logger.debug("created List of ASNRoles #, took ms " + result.size()
				+ ", " + (System.currentTimeMillis() - starttime));
		return result;
	}

	@Override
	public void setSelectedASNRoles(String[] roles) {
		this.selectedASNRoleIDs = roles;
	}

	@Override
	public String[] getSelectedASNRoles() {
		return selectedASNRoleIDs;
	}

	@Override
	public List<ASNRoleNode> getASNRoleNodes() {
		if (selectedASN != null)
			return handleBoService.getAllRoleNodes(selectedASN);
		return null;
	}

	@Override
	public List<SelectItem> getAvailableEnterpriseProfiles() {
		long starttime = System.currentTimeMillis();
		List<EnterpriseEvaluationProfile> profs = pMController
				.getEnterpriseEvaluationService()
				.retrieveAllEnterpriseProfiles();
		// List<Dimension> cds =
		// pMController.getEnterpriseEvaluationService().getCompetenceService().retrieveAllDimensions();
		List<SelectItem> result = new ArrayList<SelectItem>(profs.size() /*
																		 * +
																		 * cds.
																		 * size
																		 * ()
																		 */);
		for (EnterpriseEvaluationProfile eep : profs) {
			result.add(new SelectItem("eep" + eep.getId(), eep.getName()));
		}
		// for (Dimension comp : cds) {
		// result.add(new SelectItem("com" + comp.getId(), comp.getName()));
		// }
		logger.debug("created List of  AvailableEnterpriseProfiles #, took ms "
				+ result.size() + ", "
				+ (System.currentTimeMillis() - starttime));
		return result;
	}

	@Override
	public void setSelectedProfiles(String[] profiles) {
		this.selectedProfileIDs = profiles;
	}

	@Override
	public String[] getSelectedProfiles() {
		return selectedProfileIDs;
	}

	@Override
	public void addCompetencesAllHave(ActionEvent event) {
		if (this.selectedProfileIDs != null) {
			for (String id : selectedProfileIDs) {
				CompetenceNeededByNetworkEvaluationProfile cn = new CompetenceNeededByNetworkEvaluationProfile();
				if (id.startsWith("eep")) {
					cn.setCompetenceProfile(pMController
							.getEnterpriseEvaluationService()
							.retrieveEnterpriseProfile(
									new Long(id.substring(3))));
				} else if (id.startsWith("com")) {
					cn.setCompetenceProfile(pMController
							.getEnterpriseEvaluationService()
							.getCompetenceService().retrieveDimensionById(
									new Long(id.substring(3))));
				}
				getNetworkEvaluationProfile().addCompetenceAllHave(cn);
				pMController.getNetworkEvaluationService().create(cn);
			}
		}
		this.update();
	}

	@Override
	public void addCompetencesOneOrMoreHave(ActionEvent event) {
		if (this.selectedProfileIDs != null) {
			for (String id : selectedProfileIDs) {
				CompetenceNeededByNetworkEvaluationProfile cn = new CompetenceNeededByNetworkEvaluationProfile();
				if (id.startsWith("eep")) {
					cn.setCompetenceProfile(pMController
							.getEnterpriseEvaluationService()
							.retrieveEnterpriseProfile(
									new Long(id.substring(3))));
				} else if (id.startsWith("com")) {
					cn.setCompetenceProfile(pMController
							.getEnterpriseEvaluationService()
							.getCompetenceService().retrieveDimensionById(
									new Long(id.substring(3))));
				}
				getNetworkEvaluationProfile().addCompetenceOneOrMoreHave(cn);
				pMController.getNetworkEvaluationService().create(cn);
			}
		}
		update();
	}

	@Override
	public void assignCompetences2Roles(ActionEvent event) {
		if (selectedASN != null && selectedProfileIDs != null
				&& selectedProfileIDs.length > 0 && selectedASNRoleIDs != null
				&& selectedASNRoleIDs.length > 0) {
			String prof = selectedProfileIDs[0];
			for (String id : selectedASNRoleIDs) {
				for (ASNRoleNode node : handleBoService
						.getAllRoleNodes(selectedASN)) {
					if (node.getId().equals(new Long(id))) {
						CompetenceNeeded cn = new CompetenceNeeded();
						if (prof.startsWith("eep")) {
							cn.setCompetenceProfile(pMController
									.getEnterpriseEvaluationService()
									.retrieveEnterpriseProfile(
											new Long(prof.substring(3))));
						} else if (prof.startsWith("com")) {
							cn.setCompetenceProfile(pMController
									.getEnterpriseEvaluationService()
									.getCompetenceService()
									.retrieveDimensionById(
											new Long(id.substring(3))));
						}
						pMController.getNetworkEvaluationService().create(cn);
						node.setCompetenceNeeded(cn);
						handleBoService.update(node);
					}
				}
			}
			update();
		}
	}

	@Override
	public void selectedASNRolesChanged(ValueChangeEvent event) {
		logger.debug(event.getNewValue().toString());
	}

	@Override
	public void selectedProfilesChanged(ValueChangeEvent event) {
		logger.debug(event.getNewValue().toString());
	}

	@Override
	public List<CompetenceNeededByNetworkEvaluationProfile> getCompetenceAllHave() {
		NetworkEvaluationProfile nep = getNetworkEvaluationProfile();
		if (nep != null)
			return nep.getCompetenceAllHave();
		else
			return null;
	}

	@Override
	public List<CompetenceNeededByNetworkEvaluationProfile> getCompetenceOneOrMoreHave() {
		NetworkEvaluationProfile nep = getNetworkEvaluationProfile();
		if (nep != null)
			return nep.getCompetenceOneOrMoreHave();
		else
			return null;
	}

	@Override
	public String removeCompetenceAllHave() {
		removeCompetenceAllHave(null);
		return "performanceMeasurementNetwork";
	}

	@Override
	public void removeCompetenceAllHave(ActionEvent evt) {
		if (evt != null) {
			try {
				Long updateElementId = (Long) ((UIComponent) evt.getSource())
						.getAttributes().get("removeElementId");
				pMController.getNetworkEvaluationService().delete(
						getNetworkEvaluationProfile()
								.deleteFromCompetenceAllHave(updateElementId));
				// getNetworkEvaluationProfile().deleteFromCompetenceAllHave(updateElementId);
				update();
			} catch (NumberFormatException nfe) {
			}// ignore for today
		}
	}

	@Override
	public String removeCompetenceOneOrMoreHave() {
		removeCompetenceOneOrMoreHave(null);
		return "performanceMeasurementNetwork";
	}

	@Override
	public void removeCompetenceOneOrMoreHave(ActionEvent evt) {
		if (evt != null) {
			try {
				Long updateElementId = (Long) ((UIComponent) evt.getSource())
						.getAttributes().get("removeElementId");
				pMController.getNetworkEvaluationService().delete(
						getNetworkEvaluationProfile()
								.deleteFromCompetenceOneOrMoreHave(
										updateElementId));
				// getNetworkEvaluationProfile().deleteFromCompetenceOneOrMoreHave(updateElementId);
				update();
			} catch (NumberFormatException nfe) {
			}// ignore for today
		}
	}

	@Override
	public String removeASNEnterpriseProfile() {
		removeASNEnterpriseProfile(null);
		return "performanceMeasurementNetwork";
	}

	@Override
	public void removeASNEnterpriseProfile(ActionEvent evt) {
		if (evt != null) {
			try {
				Long nodeId = (Long) ((UIComponent) evt.getSource())
						.getAttributes().get("updateElementRemove");
				for (int i = 0; i < getASNRoleNodes().size(); ++i) {
					if (getASNRoleNodes().get(i).getId().equals(nodeId)) {
						pMController.getNetworkEvaluationService().delete(
								getASNRoleNodes().get(i).getCompetenceNeeded());
						getASNRoleNodes().get(i).setCompetenceNeeded(null);
						update();
						break;
					}
				}
			} catch (NumberFormatException nfe) {
			}// ignore for today
		}
	}

	@Override
	public void updateCompetenceOneOrMoreHave(ValueChangeEvent vce) {
		updateCompetenceOneOrMoreHave();
	}

	@Override
	public void updateCompetenceOneOrMoreHave(ActionEvent ae) {
		updateCompetenceOneOrMoreHave();
	}

	@Override
	public void updateCompetenceOneOrMoreHave() {
		update();
	}

	@Override
	public void updateCompetenceAllHave(ValueChangeEvent vce) {
		updateCompetenceAllHave();
	}

	@Override
	public void updateCompetenceAllHave(ActionEvent ae) {
		updateCompetenceAllHave();
	}

	@Override
	public void updateCompetenceAllHave() {
		update();
	}

	@Override
	public void updateASNEnterpriseProfile(ValueChangeEvent vce) {
		updateASNEnterpriseProfile();
	}

	@Override
	public void updateASNEnterpriseProfile(ActionEvent ae) {
		updateASNEnterpriseProfile();
	}

	@Override
	public void updateASNEnterpriseProfile() {
		// Map map =
		// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		// Long nodeId = new Long((String) map.get("updateElement"));
		if (selectedASN != null) {
			for (ASNRoleNode role : this.handleBoService
					.getAllRoleNodes(selectedASN)) {
				pMController.getNetworkEvaluationService().update(
						role.getCompetenceNeeded());
				handleBoService.update(role);
			}
		}
		update();
	}

	private void update() {
		// getNetworkEvaluationProfile().setASN(availableASNs.get(selectedASN));
		if (selectedASN != null)
			handleBoService.update(selectedASN);
		pMController.getNetworkEvaluationService().update(
				getNetworkEvaluationProfile());
		caseFileController.getKDMService().getCaseService().update(
				getSelectedCaseFile());
		// updateUI();
	}

	@Override
	public String triggerGeneratePrototypeTeam() {
		CaseFile x = getSelectedCaseFile();
		teamController.generatePrototypeTeamCandidatesTopDown(x);
		caseFileController.update(x);
		logger.debug(x.getName() + ":  TempTeams Generated: "
				+ x.getTempTeams());
		return "teamFormationCase";
		// return "browseCaseFiles";
	}

	@Override
	public boolean getShowPrototypeTeamButton() {
		if (getSelectedCaseFile() == null)
			return false;
		return getSelectedCaseFile().getPhase() < 2; // CaseFile.ENGINEERING_PRODUCT_Phase2;
	}

	@Override
	public String triggerGenerateFinalTeam() {
		CaseFile x = getSelectedCaseFile();
		teamController.generateFinalTeamCandidatesTopDown(x);
		caseFileController.update(x);
		logger.debug(x.getName() + ":  TempTeams Generated: "
				+ x.getTempTeams());
		return "teamFormationCase";
		// return "browseCaseFiles";
	}

	@Override
	public boolean getShowFinalTeamButton() {
		if (getSelectedCaseFile() == null)
			return false;
		return getSelectedCaseFile().getPhase() > 1; // CaseFile.MANUFACTURING_PROTOTYPE_Phase1;
	}

	@Override
	public void setRenderManager(RenderManager rm) {
		logger.debug("PMNetworkProfileDesignControllerImpl.setRedermanager");
		renderer = rm.getOnDemandRenderer("PMNetworkControllerRenderManager");
		renderer.setRenderManager(rm);
		if (!renderer.contains(this))
			renderer.add(this);
	}

	@Override
	public PersistentFacesState getState() {
		if (state == null) {
			state = PersistentFacesState.getInstance();
		}
		return state;
	}

	@Override
	public void renderingException(RenderingException renderingException) {
		logger.debug(renderingException.getLocalizedMessage());
	}

	@Override
	public void setPMController(PMController controller) {
		this.pMController = controller;
	}

	@Override
	public void setCaseFileController(CaseFileController controller) {
		this.caseFileController = controller;
	}

	@Override
	public void setHandleBoService(HandleBOService handleBoService) {
		this.handleBoService = handleBoService;
	}

	@Override
	public void setTeamController(
			TeamFormationControllerService servicecontroller) {
		this.teamController = servicecontroller;
	}
}
