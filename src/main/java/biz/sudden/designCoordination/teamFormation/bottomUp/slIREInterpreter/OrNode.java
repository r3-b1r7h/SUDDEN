package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

/**
 * 
 * @author mcassmc
 * 
 *         Again this class is much as expected!
 */

public class OrNode implements CompiledPredicateNode {

	private CompiledPredicateNode leftNode;
	private CompiledPredicateNode rightNode;

	public OrNode(CompiledPredicateNode leftNode,
			CompiledPredicateNode rightNode) {
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}

	public boolean evaluateMatch(Object toBeMatched) {

		return (this.leftNode.evaluateMatch(toBeMatched) || this.rightNode
				.evaluateMatch(toBeMatched));
	}

}
