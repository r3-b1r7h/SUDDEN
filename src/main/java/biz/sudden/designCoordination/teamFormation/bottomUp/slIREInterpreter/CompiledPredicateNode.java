package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

/**
 * 
 * @author mcassmc
 * 
 *         A very simple superinterface which represents: (1) 'Compiled'
 *         predicates, (2) Logical conjunctions of these
 */

public interface CompiledPredicateNode {

	public boolean evaluateMatch(Object toBeMatched);
}
