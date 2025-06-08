package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

import jade.content.abs.AbsPredicate;

/**
 * 
 * @author mcassmc
 * 
 *         This is the interface which contains the context specific information
 *         - ie how to generate the small Java functions which interpret the
 *         predicates. Those actual functions then need to be supplied in their
 *         own classes.
 */

public interface CompiledPredicateFactory {

	public ConcreteCompiledPredicate convertToConcreteNode(
			AbsPredicate predicateToConvert) throws predicateNotUnderstoodError;

}
