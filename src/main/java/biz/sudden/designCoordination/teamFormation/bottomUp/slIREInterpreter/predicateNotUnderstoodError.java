package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

import jade.content.abs.AbsPredicate;

/**
 * 
 * @author mcassmc
 * 
 *         For when the conversion process meets an unknown predicate - we need
 *         to throw this back to the agent so it can send a Not_Understood
 *         reply.
 * 
 */

@SuppressWarnings("serial")
public class predicateNotUnderstoodError extends Exception {

	public AbsPredicate predicateNotUnderstood;

	public predicateNotUnderstoodError(AbsPredicate predNotUnderstood) {
		super(" Predicate not understood " + predNotUnderstood.toString());
		this.predicateNotUnderstood = predNotUnderstood;
	}

}
