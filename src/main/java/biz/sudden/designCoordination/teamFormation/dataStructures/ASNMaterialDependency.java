package biz.sudden.designCoordination.teamFormation.dataStructures;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("meterial")
public class ASNMaterialDependency extends ASNDependency {

	/*
	 * For use by the coordination module
	 */
	public enum TransportationLogistics {
		JIS_JIT, JIS_JIT_WithBuffer, Regular_Delivery
	};

	private TransportationLogistics myTransportationLogistics;

	public ASNMaterialDependency() {
		this.ASNDependencyType = TeamFormationConstants.MaterialDependencyType;
	}

	/**
	 * make a new dependency linking two nodes
	 * 
	 * @param sourceNode
	 *            the source node
	 * @param targetNode
	 *            the target node
	 */
	public ASNMaterialDependency(ASNRoleNode sourceNode, ASNRoleNode targetNode) {
		this.sourceNode = sourceNode;
		this.targetNode = targetNode;
		this.ASNDependencyType = TeamFormationConstants.MaterialDependencyType;
	}

	public String getsourceproduct() {
		if (((ASNRoleNode) this.sourceNode) != null
				&& ((ASNRoleNode) this.sourceNode).getQualificationProfile() != null)
			return ((ASNRoleNode) this.sourceNode).getQualificationProfile().productIndividualURI;
		else
			return "";
	}

	public void setsourceproduct(String willBeIgnored) {
		// To keep Spring/Beans etc happy. The dependency type is set on
		// creation of the dependency and never changes.
		// But it won't let me display it to the console unless there's a bean
		// ergo needs a set method ;)
	}

	public void settargetproduct(String willBeIgnored) {
		// To keep Spring/Beans etc happy. The dependency type is set on
		// creation of the dependency and never changes.
		// But it won't let me display it to the console unless there's a bean
		// ergo needs a set method ;)
	}

	public String gettargetproduct() {
		if (((ASNRoleNode) this.targetNode) != null
				&& ((ASNRoleNode) this.targetNode).getQualificationProfile() != null)
			return ((ASNRoleNode) this.targetNode).getQualificationProfile().productIndividualURI;
		else
			return "";
	}

	public TransportationLogistics gettransportationlogistics() {
		return myTransportationLogistics;
	}

	public void settransportationlogistics(TransportationLogistics trlIn) {
		this.myTransportationLogistics = trlIn;
	}

	@Override
	public String toString() {
		String result = "";
		result += " Material dep from Source Node " + this.sourceNode + " to "
				+ this.targetNode;
		return result;
	}

	@Override
	public ASNMaterialDependency clone() {
		ASNMaterialDependency result = new ASNMaterialDependency();
		result.setSourceNode(this.sourceNode);
		result.setTargetNode(this.targetNode);
		return result;
	}

	@Override
	public ASNMaterialDependency fullClone() {
		ASNMaterialDependency result = new ASNMaterialDependency();
		result.setSourceNode(this.sourceNode.clone());
		result.setTargetNode(this.targetNode.clone());
		return result;
	}

}
