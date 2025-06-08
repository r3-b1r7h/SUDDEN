package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardFactoryAgent;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsPredicate;
import jade.content.abs.AbsPrimitive;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.ConcreteCompiledPredicate;

/**
 * 
 * @author mcassmc
 * 
 *         Returns true iff the initial solution on the noticeboard contains the
 *         given abstract types within it's intial state.
 * 
 *         Note that this does not test the full contrained values - merely the
 *         top level type.
 * 
 *         In fact the name of this predicate seems to be slightly misleading.
 */

public class RequiresAbstractTypesTest extends ConcreteCompiledPredicate {

	private Collection<String> typesToTest;
	private Ontology o;

	public RequiresAbstractTypesTest(AbsPredicate predToConvert,
			Ontology ontology) {
		AbsAggregate types = (AbsAggregate) predToConvert
				.getAbsObject(QueryOntology.LIST_OF_TYPES);
		o = ontology;
		typesToTest = new LinkedList<String>();

		String tempString;
		Iterator it = types.iterator();
		while (it.hasNext()) {
			tempString = ((AbsPrimitive) it.next()).getString();
			System.out.println(" Storing type required " + tempString);
			typesToTest.add(tempString);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean evaluateMatch(Object toBeMatched) {

		System.out.println(" Testing for things requiring " + toBeMatched);

		AbsConcept noticeboardDescription = (AbsConcept) toBeMatched;

		AbsConcept stateToTest = (AbsConcept) noticeboardDescription
				.getAbsObject(CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_INITIAL_STATE);
		AbsAggregate resourceSets = (AbsAggregate) stateToTest
				.getAbsObject(ProcessOntology.STATE_RESOURCES_CONSTRAINED);

		LinkedList<String> overResourceSetsToo = new LinkedList();
		AbsConcept tempConstrainedResourceSet;
		Iterator overResourceSets = resourceSets.iterator();
		while (overResourceSets.hasNext()) {
			tempConstrainedResourceSet = (AbsConcept) overResourceSets.next();
			overResourceSetsToo.add(tempConstrainedResourceSet.getAbsObject(
					ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE)
					.getTypeName());
		}

		/*
		 * Now for a shiny ontology based match. Allowing more general matching
		 * only for now?
		 * 
		 * This test currently only covers the location of more general
		 * concepts.
		 */
		Iterator overTypesToTest = typesToTest.iterator();
		Iterator overTypesContained;
		String tempTypeContained;
		String tempTypeToTest;
		ConceptSchema tempSchemaToTest;
		ConceptSchema tempSchemaContained;
		System.out.println(" Testing for things requiring " + toBeMatched);

		boolean matchedAll = true;

		while (overTypesToTest.hasNext() && matchedAll) {
			boolean tempMatch = false;
			tempTypeToTest = (String) overTypesToTest.next();
			try {
				tempSchemaToTest = (ConceptSchema) o.getSchema(tempTypeToTest);
			} catch (OntologyException e) {
				// I've gone and restricted myself via interfaces. Grrrrrr.
				e.printStackTrace();
				return false;
			}

			System.out
					.println(" Testing for production of " + tempSchemaToTest);

			overTypesContained = overResourceSetsToo.iterator();
			while (!tempMatch && overTypesContained.hasNext()) {
				tempTypeContained = (String) overTypesContained.next();
				try {
					tempSchemaContained = (ConceptSchema) o
							.getSchema(tempTypeContained);
				} catch (OntologyException e) {
					e.printStackTrace();
					return false;
				}

				System.out.println("testing against " + tempSchemaContained);

				tempMatch = tempSchemaContained
						.isCompatibleWith(tempSchemaToTest);
			}
			matchedAll = matchedAll && tempMatch;
		}

		return matchedAll;
	}

}
