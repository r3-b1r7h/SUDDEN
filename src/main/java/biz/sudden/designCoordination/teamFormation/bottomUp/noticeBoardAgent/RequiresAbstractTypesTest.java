package biz.sudden.designCoordination.teamFormation.bottomUp.noticeBoardAgent;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsPredicate;
import jade.content.abs.AbsPrimitive;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PotentialExtension;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.QueryOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter.ConcreteCompiledPredicate;

/**
 * 
 * @author mcassmc
 * 
 *         Returns true iff the given potential extension requires (ie has in
 *         it's preconditions) the given set of abstract types.
 */

public class RequiresAbstractTypesTest extends ConcreteCompiledPredicate {

	private Collection<String> typesToTest;

	private Ontology o;

	public RequiresAbstractTypesTest(AbsPredicate predToConvert, Ontology ont) {
		AbsAggregate types = (AbsAggregate) predToConvert
				.getAbsObject(QueryOntology.LIST_OF_TYPES);
		typesToTest = new LinkedList<String>();

		String tempString;
		Iterator it = types.iterator();
		while (it.hasNext()) {
			tempString = ((AbsPrimitive) it.next()).getString();
			typesToTest.add(tempString);
		}
		o = ont;
	}

	@Override
	public boolean evaluateMatch(Object toBeMatched) {

		/*
		 * System.out.println( " testing " + toBeMatched + " against"); for
		 * (String s : typesToTest) { System.out.println(" " + s); }
		 */

		PotentialExtension PEToBeMatched = (PotentialExtension) toBeMatched;

		Collection<String> typesNeeded = PEToBeMatched
				.getAbstractResourcesForwards().keySet();

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

		boolean matchedAll = true;

		while (overTypesToTest.hasNext() && matchedAll) {
			tempTypeToTest = (String) overTypesToTest.next();
			try {
				tempSchemaToTest = (ConceptSchema) o.getSchema(tempTypeToTest);
			} catch (OntologyException e) {
				System.out.println("!!!!!!");
				e.printStackTrace();
				return false;
			}

			overTypesContained = typesNeeded.iterator();

			boolean tempMatch = false;
			while (!tempMatch && overTypesContained.hasNext()) {
				tempTypeContained = (String) overTypesContained.next();
				try {
					tempSchemaContained = (ConceptSchema) o
							.getSchema(tempTypeContained);
				} catch (OntologyException e) {
					System.out.println("!!!!!!");
					e.printStackTrace();
					return false;
				}
				// System.out.println(" testing " + tempTypeContained + " vs " +
				// tempTypeToTest);
				// System.out.println(" testing schemata " + tempSchemaContained
				// + " vs " + tempSchemaToTest);

				tempMatch = tempSchemaContained
						.isCompatibleWith(tempSchemaToTest)
						|| tempSchemaToTest
								.isCompatibleWith(tempSchemaContained);
				// System.out.println(tempMatch);
			}
			matchedAll = matchedAll && tempMatch;
		}
		// System.out.println(" result " + matchedAll);

		return matchedAll;
	}

}
