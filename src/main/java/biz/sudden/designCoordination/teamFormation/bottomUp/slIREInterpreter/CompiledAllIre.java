package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 * @author mcassmc
 * 
 *         A class corresponding to a compiled All IRE. (gets all matches and
 *         never complains even if there are *no* matches.) No constructor - the
 *         factory class deals with this.
 */

public class CompiledAllIre extends CompiledIRE {

	// Typed collections are great but not so useful for a type agnostic
	// framework!
	@SuppressWarnings("unchecked")
	@Override
	public Collection findMatches(Collection objectsToMatchOver) {
		LinkedList result = new LinkedList();

		if (objectsToMatchOver != null) {
			for (Object objectToMatch : objectsToMatchOver) {
				if (ourCompiledPredicateNode.evaluateMatch(objectToMatch)) {
					result.add(objectToMatch);
				}
			}
		} else {
			System.err
					.println("CompiledAllIre.findMatches - objectsToMatchOver == null");
		}

		return result;
	}

}
