package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

/**
 * 
 * @author mcassmc
 * 
 *         I don't consider the behaviour of this class as startling either :)
 *         (Think logical conjunctions)
 */

public class NotNode implements CompiledPredicateNode {

	private CompiledPredicateNode myCompiledPredicate;

	public NotNode(CompiledPredicateNode p) {
		this.myCompiledPredicate = p;
	}

	public boolean evaluateMatch(Object o) {
		return (!(myCompiledPredicate.evaluateMatch(o)));
	}

}
