package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

/**
 * 
 * @author mcassmc
 * 
 *         This is as very much as expected :)
 */

public class AndNode implements CompiledPredicateNode {

	private CompiledPredicateNode leftNode;
	private CompiledPredicateNode rightNode;

	public AndNode(CompiledPredicateNode leftNode,
			CompiledPredicateNode rightNode) {
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}

	public boolean evaluateMatch(Object toBeMatched) {

		return (this.leftNode.evaluateMatch(toBeMatched) && this.rightNode
				.evaluateMatch(toBeMatched));
	}

}
