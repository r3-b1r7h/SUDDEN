package biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType;

import jade.content.abs.AbsConcept;

/**
 * 
 * @author mcassmc
 * 
 *         For when the constraint handler might have a constraint type the code
 *         hasn't been fully updated to deal with.
 */

public class UnknownConstraintTypeException extends Exception {

	public UnknownConstraintTypeException(AbsConcept constraint) {
		super(" Unknown constraint type located " + constraint);
	}

}
