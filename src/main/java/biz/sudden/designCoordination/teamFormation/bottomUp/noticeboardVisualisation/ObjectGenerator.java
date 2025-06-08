package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardVisualisation;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsPrimitive;
import jade.content.lang.sl.SL0Vocabulary;
import jade.content.onto.BasicOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.FromRangeConstraintSchema;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ConstrainedProductOntology;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessOntology;

/**
 * 
 * @author mcassmc
 * 
 *         A simple class containing static methods to give me example partial
 *         solutions, states etc etc to play around with showing.
 * 
 */
public class ObjectGenerator {

	public static AbsConcept getState() {
		AbsConcept result;

		result = new AbsConcept(ProcessOntology.STATE);

		AbsAggregate goalResources = new AbsAggregate(SL0Vocabulary.SET);

		AbsConcept simplifiedResourceSet1 = new AbsConcept(
				ProcessOntology.CONSTRAINED_RESOURCE_SET);
		simplifiedResourceSet1.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_QUANTITY, AbsPrimitive
						.wrap(1));
		// TODO - get these as absconcepts from some product ontology
		AbsConcept product = new AbsConcept(ConstrainedProductOntology.PRODUCT);
		product.set(ConstrainedProductOntology.PRODUCT_STRING_ATTRIBUTE, "a");

		AbsConcept constraint1 = new AbsConcept(
				FromRangeConstraintSchema.FROM_RANGE_CONSTRAINT);
		constraint1.set(FromRangeConstraintSchema.MINIMUM, 2);
		constraint1.set(FromRangeConstraintSchema.MAXIMUM, 6);

		product.set(ConstrainedProductOntology.PRODUCT_INT_ATTRIBUTE,
				constraint1);

		product.set(ConstrainedProductOntology.PRODUCT_STRING_ATTRIBUTE, "a");
		simplifiedResourceSet1.set(
				ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE, product);
		goalResources.add(simplifiedResourceSet1);

		result.set(ProcessOntology.STATE_RESOURCES_CONSTRAINED, goalResources);

		return result;
	}

}
