package biz.sudden.evaluation.performanceMeasurement.web.controller.impl;

import java.util.List;

import javax.faces.event.ActionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.web.controller.tree.AssocUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.ConnectableUserObject;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeededByNetworkEvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMNetworkProfileDesignController;

public class PMNetworkProfileDesignController2Impl extends
		PMNetworkProfileDesignControllerImpl implements
		PMNetworkProfileDesignController, ActionListener {

	List<EnterpriseEvaluationProfile> nils = null;

	@Override
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
				CompetenceNeededByNetworkEvaluationProfile cah = new CompetenceNeededByNetworkEvaluationProfile();
				cah.setCompetenceProfile(profile);
				cah.setNetworkEvaluationProfile(nep);
				nep.addCompetenceAllHave(cah);
				pMController.getNetworkEvaluationService().create(cah);
				Association aa = pMController.getConnectableService()
						.retrieveAssociation(targetAUO.getReference().getId());
				this.pMController.getConnectableService()
						.addConnectableToAssociation(aa, cah, "1.0");
				if (nils == null) {
					nils = this.pMController.getEnterpriseEvaluationService()
							.retrieveEnterpriseProfile(NIL_NAME);
				}
				for (int ii = 0; ii < nils.size(); ++ii)
					this.pMController.getConnectableService()
							.removeConnectableFromAssoc(nils.get(ii), aa);
			} else if (targetAUO.getAssocTypeName().equals(
					COMPETENCES_ONE_OR_MORE_HAVE_ASSOC)) {
				CompetenceNeededByNetworkEvaluationProfile cah = new CompetenceNeededByNetworkEvaluationProfile();
				cah.setCompetenceProfile(profile);
				cah.setNetworkEvaluationProfile(nep);
				nep.addCompetenceOneOrMoreHave(cah);
				pMController.getNetworkEvaluationService().create(cah);
				Association aa = pMController.getConnectableService()
						.retrieveAssociation(targetAUO.getReference().getId());
				this.pMController.getConnectableService()
						.addConnectableToAssociation(aa, cah, "1.0");
				if (nils == null) {
					nils = this.pMController.getEnterpriseEvaluationService()
							.retrieveEnterpriseProfile(NIL_NAME);
				}
				for (int ii = 0; ii < nils.size(); ++ii)
					this.pMController.getConnectableService()
							.removeConnectableFromAssoc(nils.get(ii), aa);
			} else if (targetAUO.getAssocTypeName().startsWith(
					COMPETENCES_FOR_ROLE_ASSOC)) {
				// List<Connectable> cc = new ArrayList<Connectable>();
				// cc.addAll(
				// this.pMController.getEnterpriseEvaluationService().retrieveEnterpriseProfile(NIL_NAME));
				if (nils == null) {
					nils = this.pMController.getEnterpriseEvaluationService()
							.retrieveEnterpriseProfile(NIL_NAME);
				}
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
							// cc.add(node.getCompetenceNeeded());
						}
						node.setCompetenceNeeded(cah);
					}
				}
				pMController.getNetworkEvaluationService().create(cah);
				Association aa = pMController.getConnectableService()
						.retrieveAssociation(targetAUO.getReference().getId());
				this.pMController.getConnectableService()
						.addConnectableToAssociation(aa, cah, "1.0");
				for (int ii = 0; ii < nils.size(); ++ii)
					this.pMController.getConnectableService()
							.removeConnectableFromAssoc(nils.get(ii), aa);

				// for(int ii=0;ii<cc.size();++ii) {
				// Connectable toBeRemoved = cc.get(ii);
				// this.pMController.getConnectableService().removeConnectableFromAssoc(toBeRemoved,
				// aa);
				// }
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
	}

}
