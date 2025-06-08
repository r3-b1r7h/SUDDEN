package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent;

import jade.content.abs.AbsConcept;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.ConcreteCompiledPredicate;

/**
 * 
 * @author mcassmc
 * 
 *         A very simple check of the type of the variable - actually checked vs
 *         the type of the object being matched against. Potentially entirely
 *         superflous - but the corresponding predicate is needed to allow for
 *         non restrictive queries.
 */

public class IsNoticeboard extends ConcreteCompiledPredicate {

	public IsNoticeboard() {
		;
	}

	@Override
	public boolean evaluateMatch(Object toBeMatched) {

		System.out.println(" Testing if "
				+ toBeMatched
				+ " is in fact a noticeboard description or not. Answer "
				+ ((AbsConcept) toBeMatched).getTypeName().equals(
						CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION));

		return ((AbsConcept) toBeMatched).getTypeName().equals(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION);
	}

}
