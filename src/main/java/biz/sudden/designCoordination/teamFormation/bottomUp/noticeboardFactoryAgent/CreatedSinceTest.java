package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent;

import jade.content.abs.AbsConcept;
import jade.content.abs.AbsPredicate;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
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
		AbsConcept noticeboardDescription = (AbsConcept) toBeMatched;

		Integer toCheck = noticeboardDescription
				.getInteger(CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_TIME_CREATED);

		return (toCheck >= timeToTest);
	}

}
