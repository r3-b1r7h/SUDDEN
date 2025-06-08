package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

/**
 * 
 * @author mcassmc
 * 
 *         Not intended to be used - merely as a superclass for nodes which
 *         correspond to a sinlge feature to match too rather than some kind of
 *         conjunction (in case this ever becomes an important distinction.).
 */

public abstract class ConcreteCompiledPredicate implements
		CompiledPredicateNode {

	public abstract boolean evaluateMatch(Object toBeMatched);
}
