package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm;

/**
 * 
 * Indicates that the someone has tried to stick a cycle into the partial order.
 * Such things are not terribly helpful :)
 * 
 * When the partial order is created incrementally this error should never occur
 * - if it does then something has probably gone wrong with the calculation of
 * potential extensions wrt the partial order.
 * 
 * @author mcassmc
 * 
 */
public class CycleDetectedException extends Exception {

	private static final long serialVersionUID = -337460712698617389L;

	public CycleDetectedException(String message) {
		super(message);
	}

}
