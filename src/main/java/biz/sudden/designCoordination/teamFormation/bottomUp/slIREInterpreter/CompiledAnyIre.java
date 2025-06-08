package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author mcassmc
 * 
 *         A class corresponding to a compiled Any IRE. (gets the first match
 *         only) No constructor - the factory class deals with this.
 */

public class CompiledAnyIre extends CompiledIRE {

	// Typed collections are great but not so useful for a type agnostic
	// framework!
	@SuppressWarnings("unchecked")
	@Override
	public Collection findMatches(Collection objectsToMatchOver)
			throws NoMatchesLocatedException {
		LinkedList result = new LinkedList();
		int matched = 0;

		Iterator overObjectsToCheck = objectsToMatchOver.iterator();
		while (overObjectsToCheck.hasNext() && (matched == 0)) {
			Object objectToMatch = overObjectsToCheck.next();
			if (ourCompiledPredicateNode.evaluateMatch(objectToMatch)) {
				result.add(objectToMatch);
				matched++;
			}
		}

		if (result.size() == 0) {
			throw new NoMatchesLocatedException();
		}

		return result;
	}

}
