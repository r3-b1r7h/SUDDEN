package biz.sudden.evaluation.performanceMeasurement.domain;

import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public class PerformanceTree extends Tree {

	protected PMController controller;
	protected Organization evaluatedOrganisation;

	public PerformanceTree(PMController controller) {
		super(controller.getConnectableService(), controller
				.getScopeController());
		this.controller = controller;
		this.setAssociationScope(this.controller.getScopeController()
				.getUserScope());
		this.setAlsoCreateAssociationNodes(true);
		// too much!
		this.setAlsoShowOccurrence(false);
	}

	/**
	 * @return the evaluatedOrganisation
	 */
	public Organization getEvaluatedOrganisation() {
		return evaluatedOrganisation;
	}

	/**
	 * @param evaluatedOrganisation
	 *            the evaluatedOrganisation to set
	 */
	public void setEvaluatedOrganisation(Organization evaluatedOrganisation) {
		this.evaluatedOrganisation = evaluatedOrganisation;
		if (this.evaluatedOrganisation != null)
			this.setOccurrenceScope(this.controller.getScopeController()
					.getScopeService().retrieveScopeBy(
							this.evaluatedOrganisation.getName()));
		else
			this.setOccurrenceScope(null);
	}

	// public String getAssociationText(AssocUserObject assocObject) {
	// return super.getAssociationText(assocObject);
	// }

	// Too much
	// public String getNodeText(ConnectableUserObject userObject) {
	// // return super.getNodeText(userObject); //returns null => default text
	// from ConnectableUserObject is used
	// StringBuffer result = new StringBuffer(userObject.getText());
	// if(evaluatedOrganisation!=null){
	// result.append(": ");
	// result.append(evaluatedOrganisation.getName());
	// } else
	// result.append(" - ");
	// return result.toString();
	// }
}
