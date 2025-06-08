package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

import jade.content.abs.AbsIRE;

import java.util.Collection;

/**
 * 
 * @author mcassmc
 * 
 *         A superclass for the compiled IRE's (One type for each of
 *         All/Any/IOTA)
 */

public abstract class CompiledIRE {

	protected CompiledPredicateNode ourCompiledPredicateNode;

	protected AbsIRE originalIRE;

	public abstract Collection findMatches(Collection objectsToMatchOver)
			throws TooManyMatchesForIOTAException, NoMatchesLocatedException;

	public void setCompiledPredicateNode(CompiledPredicateNode cpn) {
		this.ourCompiledPredicateNode = cpn;
	}

	public CompiledPredicateNode getCompiledPredNode() {
		return this.ourCompiledPredicateNode;
	}

	public void setOriginalIRE(AbsIRE ire) {
		this.originalIRE = ire;
	}

	public AbsIRE getOriginalIRE() {
		return this.originalIRE;
	}

	@Override
	public String toString() {
		return this.originalIRE.toString();
	}
}
