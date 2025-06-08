/*
 * Modfied May 2006 to move to normal AbsConcepts
 */
package biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType;

import jade.content.onto.BasicOntology;
import jade.content.onto.OntologyException;
import jade.content.schema.ObjectSchema;

/**
 * @author mcassmc
 * 
 *         To check if a value comes from an integer range
 */
public class FromRangeConstraintSchema extends ConstraintSchema {

	public static final String FROM_RANGE_CONSTRAINT = "From_Range_Constraint";
	public static final String MINIMUM = "minimumValue";
	public static final String MAXIMUM = "maximumValue";

	private static FromRangeConstraintSchema baseSchema = new FromRangeConstraintSchema();

	public FromRangeConstraintSchema() {
		super(NewConstraintConstants.FROM_RANGE_BASE_NAME);
		try {
			add(MINIMUM, BasicOntology.getInstance().getSchema(
					BasicOntology.INTEGER));
			add(MAXIMUM, BasicOntology.getInstance().getSchema(
					BasicOntology.INTEGER));
		} catch (OntologyException oe) {
			oe.printStackTrace();
		}
		// System.out.println(this.getTypeName());
	}

	public static ObjectSchema getBaseSchema() {
		return baseSchema;
	}
}