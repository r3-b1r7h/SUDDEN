package biz.sudden.designCoordination.teamFormation.bottomUp.noticeBoardAgent;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PotentialExtension;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.ConcreteCompiledPredicate;

/**
 * True iff the object is a PotentialExtension.
 * 
 * @author mcassmc
 * 
 */

public class IsPotentialExtensionTest extends ConcreteCompiledPredicate {

	@Override
	public boolean evaluateMatch(Object toBeMatched) {

		return toBeMatched.getClass().equals(PotentialExtension.class);

	}

}
