package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 * @author mcassmc
 * 
 *         A class corresponding to a compiled IOTA IRE. (matches to a UNIQUE
 *         value and complains if > 1 match is located.).
 * 
 *         In the case of subscriptions it only complains if it finds > 1 match
 *         at once. After all the old match might have gone by the time the new
 *         one is located. In general subscribing with an IOTA is slightly odd
 *         on some philosophical level.
 * 
 *         No constructor - the factory class deals with this.
 */

public class CompiledIOTAIre extends CompiledIRE {

	// Typed collections are great but not so useful for a type agnostic
	// framework!
	@SuppressWarnings("unchecked")
	@Override
	public Collection findMatches(Collection objectsToMatchOver)
			throws TooManyMatchesForIOTAException, NoMatchesLocatedException {
		LinkedList result = new LinkedList();
		int matched = 0;

		// This is a J5 thing - a for,each loop. Cute I think.
		for (Object objectToMatch : objectsToMatchOver) {
			if (ourCompiledPredicateNode.evaluateMatch(objectToMatch)) {
				result.add(objectToMatch);
				matched++;
			}

			if (matched == 2) {
				throw new TooManyMatchesForIOTAException();
			}
		}

		if (result.size() == 0) {
			throw new NoMatchesLocatedException();
		}

		return result;
	}

}
