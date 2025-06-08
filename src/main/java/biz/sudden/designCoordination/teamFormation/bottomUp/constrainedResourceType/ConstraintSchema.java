package biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType;

/**
 * @author mcassmc
 *
 * Inspired by the VariableSchema from JADE - can go anywhere.
 * Designed to allow for the insertion of constraints 'into' the
 * slots of individuals.
 * Then one individual can correspond to a set of possible indiviudals.
 * 
 * Some oddities here to do with plans to use this in conjunction with
 * the parser etc.
 */

import jade.content.abs.AbsObject;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;

public class ConstraintSchema extends ConceptSchema {
	private static ConstraintSchema baseSchema = new ConstraintSchema();

	public static final String BASIC_BASE_NAME = "Constraint";

	/**
	 * For the subclasses to let them distinguish type
	 */
	protected ConstraintSchema(String type) {
		super(type);
	}

	/**
	 * Like variable schema - no explicit typing information required.
	 */
	protected ConstraintSchema() {
		super(NewConstraintConstants.BASIC_BASE_NAME);
	}

	/**
	 * Retrieve the generic base schema for all variables.
	 * 
	 * @return the generic base schema for all variables.
	 */
	public static ObjectSchema getBaseSchema() {
		return baseSchema;
	}

	/**
	 * Check whether a given abstract descriptor complies with this schema.
	 * 
	 * @param abs
	 *            The abstract descriptor to be checked
	 * @throws OntologyException
	 *             If the abstract descriptor does not complies with this schema
	 */
	@Override
	public void validate(AbsObject abs, Ontology onto) throws OntologyException {
		// Check the type of the abstract descriptor
		String type = abs.getTypeName();
		if (!(NewConstraintConstants.isConstraintType(type))) {
			throw new OntologyException(abs + " is not an AbsConstraint");
		}

		// Check the slots
		validateSlots(abs, onto);
	}

	/**
	 * See below - basically always OK.
	 */
	@Override
	public boolean isCompatibleWith(ObjectSchema s) {
		if (s != null) {
			/*
			 * Would use line below but the call isn't visible so assuming
			 * (rationally :)) that every schema does descend from TermSchema.
			 */
			// return s.descendsFrom(TermSchema.getBaseSchema());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return true if - s is the base schema for the XXXSchema class this schema
	 * is an instance of (e.g. s is ConceptSchema.getBaseSchema() and this
	 * schema is an instance of ConceptSchema) - s is the base schema for a
	 * super-class of the XXXSchema class this schema is an instance of (e.g. s
	 * is TermSchema.getBaseSchema() and this schema is an instance of
	 * ConceptSchema)
	 */
	@Override
	protected boolean descendsFrom(ObjectSchema s) {
		if (s != null) {
			if (s.equals(getBaseSchema())) {
				return true;
			}
			return super.descendsFrom(s);
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Tells you when
	 * 
	 * @param type
	 * @return
	 */

	public static boolean isConstraintType(String type) {
		boolean result = false;

		result = (type.equals(ValueFromConstraintSchema.VALUE_FROM_CONSTRAINT) || type
				.equals(FromRangeConstraintSchema.FROM_RANGE_CONSTRAINT));

		return result;
	}
}
