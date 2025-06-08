package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

import jade.content.abs.AbsIRE;

/**
 * 
 * @author mcassmc
 * 
 *         For when an IRE totally baffles us. Currently this is only when it is
 *         of a type not one of Any/All/IOTA. Which would only happen if the SL
 *         specs have been altered.
 * 
 *         It might also get thrown for IRE's with multiple variables to bind
 *         and the like.
 */

@SuppressWarnings("serial")
public class UnknownIRETypeException extends Exception {

	public UnknownIRETypeException(AbsIRE ire) {
		super(" Cannot process IRE " + ire + " due to unknown elements");
	}

}
