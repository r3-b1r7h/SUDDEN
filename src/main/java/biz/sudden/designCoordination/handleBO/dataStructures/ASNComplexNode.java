package biz.sudden.designCoordination.handleBO.dataStructures;

import javax.persistence.Entity;

import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;

/**
 * This node type corresponds to the 'complex' products found within the
 * decomposition. Logically it would thus indicate the need to assemble some
 * simple components into a big one.
 * 
 * Currently its a subclass of the main ASNRoleNode class so that it all gets
 * seen by coordination fit etc
 * 
 * @author mcassmc
 */
@Entity
public class ASNComplexNode extends ASNRoleNode {

	/*
	 * Used only in the internals of the generation system
	 */
	protected Boolean expanded;

	public Boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public void setExpanded(boolean expanded) {
		if (expanded)
			this.expanded = Boolean.TRUE;
		else
			this.expanded = Boolean.FALSE;
	}

	public String getComplexPartToAssemble() {
		return complexPartToAssemble;
	}

	public void setComplexPartToAssemble(String complexPartToAssemble) {
		this.complexPartToAssemble = complexPartToAssemble;
	}

	public String getComplexPartToAssembleType() {
		return complexPartToAssembleType;
	}

	public void setComplexPartToAssembleType(String complexPartToAssembleType) {
		this.complexPartToAssembleType = complexPartToAssembleType;
	}

	// The invidiuals URI
	private String complexPartToAssemble;

	// The class of the individual
	private String complexPartToAssembleType;

	public ASNComplexNode() {
		this.expanded = false;
	}

	public ASNComplexNode(String part, String partType) {
		this.complexPartToAssemble = part;
		this.complexPartToAssembleType = partType;
	}

	@Override
	public ASNComplexNode clone() {
		ASNComplexNode result = new ASNComplexNode();

		result.setComplexPartToAssemble(this.getComplexPartToAssemble());
		result
				.setComplexPartToAssembleType(this
						.getComplexPartToAssembleType());
		if (getCompetenceNeeded() != null)
			result.setCompetenceNeeded(this.getCompetenceNeeded().clone());
		result.setExpanded(this.isExpanded());
		if (getQualificationProfile() != null)
			result.setQualificationProfile(this.getQualificationProfile()
					.clone());
		return result;
	}

	@Override
	public String toString() {
		return " Part to assemble " + this.complexPartToAssemble;
	}
}
