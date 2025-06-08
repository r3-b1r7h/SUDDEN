package biz.sudden.designCoordination.teamFormation.bottomUp.tests;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsIRE;
import jade.content.abs.AbsPredicate;
import jade.content.abs.AbsPrimitive;
import jade.content.abs.AbsVariable;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.lang.sl.SL1Vocabulary;
import jade.content.lang.sl.SL2Vocabulary;
import jade.content.lang.sl.SLOntology;
import jade.content.lang.sl.SLVocabulary;
import jade.content.onto.BasicOntology;

import java.util.Collection;
import java.util.LinkedList;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent.NoticeboardFactoryQueryFactory;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ConstrainedProductOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryForNoticeboards;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.CompiledIRE;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.CompiledIREFactory;

/**
 * 
 * @author mcassmc
 * 
 *         This test is something of a rarity - it doesn't use agents.
 * 
 *         Instead it does direct queries - to use it as a full test you have to
 *         change the query & the source data you're querying. The default at
 *         the moment is an error thrown due to detecting no matches. (as is
 *         desired.).
 */

public class TestQueriesForNoticeboardsNoAgents {

	// Much of the stuff below is slightly artifical - a lot gets filled in only
	// after the noticeboard is
	// created at the factory.

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		Collection<AbsConcept> noticeboardDescriptions = new LinkedList<AbsConcept>();
		AbsConcept nbd1 = TestNoticeboardDescriptions
				.getNoticeboardDescription();
		nbd1.set(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_TIME_CREATED,
				AbsPrimitive.wrap(18));
		// noticeboardDescriptions.add(nbd1);

		AbsConcept nbd2 = TestNoticeboardDescriptions
				.getNoticeboardDescription();
		nbd2.set(
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_TIME_CREATED,
				AbsPrimitive.wrap(8));
		noticeboardDescriptions.add(nbd2);
		AbsConcept nbu = (AbsConcept) nbd2
				.getAbsObject(CreateNoticeboardOntology.NOTICEBOARD_UTILITY);
		nbu
				.set(
						CreateNoticeboardOntology.NOTICEBOARD_UTILITY_COMPANY_REQUESTING_SERVICE,
						AbsPrimitive.wrap("foo"));

		AbsConcept state = new AbsConcept(ProcessOntology.STATE);

		AbsConcept simpleRT = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		// Need an ontology here.

		AbsConcept constrainedProduct = new AbsConcept(
				ConstrainedProductOntology.PRODUCTSUBTYPE);
		constrainedProduct.set(
				ConstrainedProductOntology.PRODUCT_INT_ATTRIBUTE, 1);
		constrainedProduct.set(
				ConstrainedProductOntology.PRODUCT_STRING_ATTRIBUTE, "b");

		simpleRT.set(ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE,
				constrainedProduct);

		AbsAggregate resources = new AbsAggregate(SL0Vocabulary.SET);
		resources.add(simpleRT);

		state.set(ProcessOntology.STATE_RESOURCES_CONSTRAINED, resources);

		nbd2.set(CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_GOAL_STATE,
				state);

		AbsIRE ire = new AbsIRE(SL2Vocabulary.ANY);

		AbsPredicate produces = new AbsPredicate(
				QueryOntology.PRODUCES_ABSTRACT_TYPES);
		AbsAggregate types = new AbsAggregate(SL0Vocabulary.SET);
		types.add(AbsPrimitive.wrap(ConstrainedProductOntology.PRODUCT));

		produces.set(QueryOntology.LIST_OF_TYPES, types);

		AbsPredicate requestedByCompany = new AbsPredicate(
				QueryForNoticeboards.REQUIRED_BY_COMPANY);
		requestedByCompany.set(QueryForNoticeboards.REQUIRED_BY_COMPANY_NAME,
				AbsPrimitive.wrap("foo"));

		AbsPredicate and = new AbsPredicate(SL1Vocabulary.AND);
		and.set(SL1Vocabulary.OR_LEFT, produces);
		and.set(SL1Vocabulary.OR_RIGHT, requestedByCompany);

		ire.setVariable(new AbsVariable("x",
				CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION));
		ire.setProposition(and);

		CompiledIREFactory fact = new CompiledIREFactory(
				new NoticeboardFactoryQueryFactory(ConstrainedProductOntology
						.getInstance()));
		CompiledIRE ireCheck = fact.generateCompiledIRE(ire);

		Collection<AbsConcept> c = ireCheck
				.findMatches(noticeboardDescriptions);
		for (AbsConcept con : c) {
			System.out.println(con);
		}

	}

}
