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
 *         Returns true iff the given potential extension produces (ie has in
 *         it's postconditions) the given set of abstract types.
 */

public class ProducesAbstractTypesTest extends ConcreteCompiledPredicate {

	private Collection<String> typesToTest;
	private Ontology o;

	public ProducesAbstractTypesTest(AbsPredicate predToConvert, Ontology ont) {
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

		PotentialExtension PEToBeMatched = (PotentialExtension) toBeMatched;

		Collection<String> typesProduced = PEToBeMatched
				.getAbstractResourcesBackwards().keySet();

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
			boolean tempMatch = false;
			tempTypeToTest = (String) overTypesToTest.next();
			try {
				tempSchemaToTest = (ConceptSchema) o.getSchema(tempTypeToTest);
			} catch (OntologyException e) {
				// I've gone and restricted myself via interfaces. Grrrrrr.
				e.printStackTrace();
				return false;
			}
			overTypesContained = typesProduced.iterator();
			while (!tempMatch && overTypesContained.hasNext()) {
				tempTypeContained = (String) overTypesContained.next();
				try {
					tempSchemaContained = (ConceptSchema) o
							.getSchema(tempTypeContained);
				} catch (OntologyException e) {
					e.printStackTrace();
					return false;
				}

				tempMatch = tempSchemaContained
						.isCompatibleWith(tempSchemaToTest);
			}
			matchedAll = matchedAll && tempMatch;
		}

		return matchedAll;
	}

}
