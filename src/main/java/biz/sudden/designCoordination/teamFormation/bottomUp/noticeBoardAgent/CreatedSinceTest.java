package biz.sudden.designCoordination.teamFormation.bottomUp.noticeBoardAgent;

import jade.content.abs.AbsPredicate;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PotentialExtension;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.ConcreteCompiledPredicate;

/**
 * 
 * @author mcassmc
 * 
 *         Returns true iff the potential extension has been created since the
 *         given time.
 * 
 *         This is an alternative to a subscription based technique.
 */

public class CreatedSinceTest extends ConcreteCompiledPredicate {

	private int timeToTest;

	public CreatedSinceTest(AbsPredicate inputPredicate) {
		this.timeToTest = inputPredicate
				.getInteger(QueryOntology.CREATED_SINCE_TIME);
	}

	@Override
	public boolean evaluateMatch(Object toBeMatched) {
		PotentialExtension potentialExtension = (PotentialExtension) toBeMatched;

		Integer toCheck = potentialExtension.getTimeCreated();

		return (toCheck >= timeToTest);
	}

}
