package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

/**
 * 
 * @author mcassmc
 * 
 *         An IOTA query is meant to match one and ONLY one result. If it
 *         matches two we must throw an error. The suituation with subscriptions
 *         and IOTA's is quite interesting....
 */

@SuppressWarnings("serial")
public class TooManyMatchesForIOTAException extends CompiledIREException {

	public TooManyMatchesForIOTAException() {
		super(
				"More than one result has matched an IOTA IRE (should return a *unique* value))");
	}

}
