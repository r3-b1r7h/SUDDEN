package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent;

import jade.content.abs.AbsConcept;
import jade.content.abs.AbsPredicate;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryForNoticeboards;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.ConcreteCompiledPredicate;

/**
 * 
 * @author mcassmc
 * 
 *         Returns true iff the given buisiness opportunity is provided by the
 *         specified company. Note that this can be used together with a not to
 *         provide a blacklist facility.
 */

public class RequiredByCompanyTest extends ConcreteCompiledPredicate {

	private String companyToTestAgainst;

	public RequiredByCompanyTest(AbsPredicate predicateToConvert) {
		this.companyToTestAgainst = predicateToConvert
				.getString(QueryForNoticeboards.REQUIRED_BY_COMPANY_NAME);
	}

	@Override
	public boolean evaluateMatch(Object toBeMatched) {
		AbsConcept noticeboardDescription = (AbsConcept) toBeMatched;
		AbsConcept utilityInformation = (AbsConcept) noticeboardDescription
				.getAbsObject(CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_UTILITY);
		String companyToMatchAgainst = utilityInformation
				.getString(CreateNoticeboardOntology.NOTICEBOARD_UTILITY_COMPANY_REQUESTING_SERVICE);

		return companyToMatchAgainst.equals(companyToTestAgainst);
	}

}
