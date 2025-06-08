package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm;

/**
 * 
 * @author mcassmc
 * 
 *         For when an equivalent potential extension is already present within
 *         a given partial solution holder. Note that this isn't simply equality
 *         but also covers partial solutions which have identical potential
 *         extensions and the same set of contributing agents.
 * 
 *         This exception is caught internally within the algorthim.
 */

public class EquivalentSolutionPresentException extends Exception {

	public PartialSolution duplicatePartialSolution;

	public EquivalentSolutionPresentException(PartialSolution p) {
		super(" Attempt to add partial solution " + p.getPartialSolutionID()
				+ " failed due to the presence of an equivalent solution");
		this.duplicatePartialSolution = p;
	}
}
