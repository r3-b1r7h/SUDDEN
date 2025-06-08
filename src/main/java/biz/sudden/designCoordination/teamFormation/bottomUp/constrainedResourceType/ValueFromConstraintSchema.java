/*
 * Modified May 2006 to move back to AbsConcepts
 */
package biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType;

import jade.content.onto.BasicOntology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;

/**
 * @author mcassmc
 * 
 *         To check if a value comes from a set of string valued things
 * 
 *         TODO - should this do more than strings?
 */
public class ValueFromConstraintSchema extends ConstraintSchema {

	private static final long serialVersionUID = 1L;

	public static final String VALUE_FROM_CONSTRAINT = "Value_From_Constraint";
	public static final String TEST_SLOT = "test_slot";

	private static ValueFromConstraintSchema baseSchema = new ValueFromConstraintSchema();

	public ValueFromConstraintSchema() {
		super(NewConstraintConstants.VALUE_FROM_BASE_NAME);
		try {
			add(TEST_SLOT, BasicOntology.getInstance().getSchema(
					BasicOntology.STRING), 1, ObjectSchema.UNLIMITED);
		} catch (OntologyException oe) {
			oe.printStackTrace();
		}
		// System.out.println(this.getTypeName());
	}

	public static ObjectSchema getBaseSchema() {
		return baseSchema;
	}
}