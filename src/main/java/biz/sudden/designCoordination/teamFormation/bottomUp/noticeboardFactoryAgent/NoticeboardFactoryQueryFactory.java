package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent;

import jade.content.abs.AbsPredicate;
import jade.content.onto.Ontology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryForNoticeboards;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.CompiledPredicateFactory;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.ConcreteCompiledPredicate;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.predicateNotUnderstoodError;

/**
 * 
 * @author mcassmc
 * 
 *         For interpreting the predicates used to locate new noticeboards
 */

public class NoticeboardFactoryQueryFactory implements CompiledPredicateFactory {

	private Ontology ontology;

	public NoticeboardFactoryQueryFactory(Ontology o) {
		this.ontology = o;
	}

	public ConcreteCompiledPredicate convertToConcreteNode(
			AbsPredicate predicateToConvert) throws predicateNotUnderstoodError {
		ConcreteCompiledPredicate result;
		String predicateType = predicateToConvert.getTypeName();

		if (predicateType.equals(QueryOntology.CREATED_SINCE)) {
			result = new CreatedSinceTest(predicateToConvert);
		} else if (predicateType.equals(QueryOntology.PRODUCES_ABSTRACT_TYPES)) {
			result = new ProducesAbstractTypesTest(predicateToConvert, ontology);
		} else if (predicateType.equals(QueryOntology.REQUIRES_ABSTRACT_TYPES)) {
			result = new RequiresAbstractTypesTest(predicateToConvert, ontology);
		} else if (predicateType
				.equals(QueryForNoticeboards.REQUIRED_BY_COMPANY)) {
			result = new RequiredByCompanyTest(predicateToConvert);
		} else if (predicateType.equals(QueryForNoticeboards.NOTICEBOARD)) {
			result = new IsNoticeboard();
		} else {
			throw new predicateNotUnderstoodError(predicateToConvert);
		}

		/*
		 * TODO - put in when I put in the constrained types!
		 * 
		 * else if
		 * (predicateType.equals(QueryForNoticeboards.PRODUCES_CONSTRAINED_TYPES
		 * )) { result = new ProducesConstrainedTypesTest(predicateToConvert); }
		 * else if
		 * (predicateType.equals(QueryForNoticeboards.REQUIRES_CONSTRAINED_TYPES
		 * )) { result = new RequiresConstrainedTypesTest(predicateToConvert); }
		 */

		return result;
	}

}
