package biz.sudden.designCoordination.teamFormation.bottomUp.ontologies;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.TermSchema;

/**
 * 
 * @author mcassmc
 * 
 *         The ontological structures needed for talking about processes within
 *         the context of this algorithm.
 * 
 *         Some other data structures are used internally - this just covers the
 *         external bits.
 * 
 */

public class ProcessOntology extends Ontology {

	/**
	 * @return the static command ontology
	 * 
	 *         As traditional with Jade - a static instance of the ontology is
	 *         returned via a getInstance method and the constructor is private.
	 */
	public static Ontology getInstance() {
		return theInstance;
	}

	private static final long serialVersionUID = 1L;

	private static Ontology theInstance = new ProcessOntology();

	public static final String ONTOLOGY_NAME = "ProcessOntology";

	private static Ontology basicOnt;

	// Ontology concept name constants

	public static final String STATE = "state";
	public static final String STATE_RESOURCES_CONSTRAINED = "Resources_Constrained";
	public static final String STATE_ID = "ID";
	public static final String CONSTRAINED_RESOURCE_SET = "Constrained_Resource_Set";
	public static final String CONSTRAINED_RESOURCE_SET_QUANTITY = "Quantity";
	public static final String CONSTRAINED_RESOURCE_SET_TYPE = "Constrained_Resource_Set_Type";
	public static final String CONSTRAINED_RESOURCE_SET_ID = "ID";

	public static final String DECLARED_CAPABILITY = "Declared_Capability";
	public static final String DECLARED_CAPABILITY_INPUT = "Input_State";
	public static final String DECLARED_CAPABILITY_OUTPUT = "Output_State";
	public static final String DECLARED_CAPABILITY_PROVIDER = "Provider";

	public static final String POTENTIAL_EXTENSION = "Potential_Extension";
	public static final String POTENTIAL_EXTENSION_RESOURCES_FREE = "Resources_Free";
	public static final String POTENTIAL_EXTENSION_RESOURCES_PRODUCED = "Resources_Produced";
	// NB this includes every agent within the relevant partial solution not
	// merely those directly
	// contributing to the potential extension itself
	public static final String POTENTIAL_EXTENSION_CONTRIBUTING_AGENTS = "Contributing_Agents";
	// Remembering where this PO comes from
	public static final String POTENTIAL_EXTENSION_PARTIAL_SOLUTION_ID = "Partial_Solution_ID";

	public static final String EXTEND_PARTIAL_SOLUTION = "Extend_Partial_Solution";
	public static final String EXTEND_PARTIAL_SOLUTION_DECLARED_CAPABILITY = "Declared_Capability_To_Extend_With";
	public static final String EXTEND_PARTIAL_SOLUTION_POTENTIAL_EXTENSION = "Potential_Extension_To_Extend";
	public static final String EXTEND_PARTIAL_SOLUTION_RESOURCES_CONSUMED = "Resources_Consumed";
	public static final String EXTEND_PARTIAL_SOLUTION_RESOURCES_PRODUCED = "Resources_Produced";

	public static final String MATCHED_RESOURCE = "Matched_Resource";
	public static final String MATCHED_RESOURCE_RESOURCEID = "Resource_Set_ID";
	public static final String MATCHED_RESOURCE_REPLACEMENT_RESOURCE = "Replacement_Resource";

	public ProcessOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		basicOnt = BasicOntology.getInstance();
		try {
			// prelims
			TermSchema stringSchema = (TermSchema) basicOnt
					.getSchema(BasicOntology.STRING);
			TermSchema intSchema = (TermSchema) basicOnt
					.getSchema(BasicOntology.INTEGER);
			ConceptSchema conceptSchema = (ConceptSchema) ConceptSchema
					.getBaseSchema();

			ConceptSchema resourceSetSchema = new ConceptSchema(
					CONSTRAINED_RESOURCE_SET);
			resourceSetSchema.add(CONSTRAINED_RESOURCE_SET_QUANTITY, intSchema);
			resourceSetSchema.add(CONSTRAINED_RESOURCE_SET_TYPE, conceptSchema);
			resourceSetSchema.add(CONSTRAINED_RESOURCE_SET_ID, intSchema,
					ObjectSchema.OPTIONAL);
			add(resourceSetSchema);

			// States
			ConceptSchema StateSchema = new ConceptSchema(STATE);
			// Optional to provide it - compulsory once it's been internalised
			// but not before then. Indeed anything you assign
			// before feeding it into the algorithm will be overwritten anyhow.
			StateSchema.add(STATE_ID, intSchema, ObjectSchema.OPTIONAL);
			StateSchema.add(STATE_RESOURCES_CONSTRAINED, resourceSetSchema, 0,
					ObjectSchema.UNLIMITED);
			add(StateSchema);

			// Declared Capabilities
			ConceptSchema declaredCapabilitySchema = new ConceptSchema(
					DECLARED_CAPABILITY);
			declaredCapabilitySchema.add(DECLARED_CAPABILITY_INPUT,
					resourceSetSchema, 0, ObjectSchema.UNLIMITED);
			declaredCapabilitySchema.add(DECLARED_CAPABILITY_OUTPUT,
					resourceSetSchema, 0, ObjectSchema.UNLIMITED);
			declaredCapabilitySchema.add(DECLARED_CAPABILITY_PROVIDER,
					stringSchema);
			add(declaredCapabilitySchema);

			// Potential Extensions

			ConceptSchema potentialExtensionSchema = new ConceptSchema(
					POTENTIAL_EXTENSION);
			potentialExtensionSchema.add(
					POTENTIAL_EXTENSION_CONTRIBUTING_AGENTS, stringSchema, 0,
					ObjectSchema.UNLIMITED);
			potentialExtensionSchema.add(POTENTIAL_EXTENSION_RESOURCES_FREE,
					resourceSetSchema, 0, ObjectSchema.UNLIMITED);
			potentialExtensionSchema.add(
					POTENTIAL_EXTENSION_RESOURCES_PRODUCED, resourceSetSchema,
					0, ObjectSchema.UNLIMITED);
			potentialExtensionSchema.add(
					POTENTIAL_EXTENSION_PARTIAL_SOLUTION_ID, intSchema);
			add(potentialExtensionSchema);

			// For proposing a partial solution extension
			ConceptSchema matchedResourceWithReplacementSchema = new ConceptSchema(
					MATCHED_RESOURCE);
			matchedResourceWithReplacementSchema.add(
					MATCHED_RESOURCE_RESOURCEID, intSchema);
			matchedResourceWithReplacementSchema.add(
					MATCHED_RESOURCE_REPLACEMENT_RESOURCE, resourceSetSchema,
					ObjectSchema.OPTIONAL);
			add(matchedResourceWithReplacementSchema);

			ConceptSchema proposeExtensionSchema = new ConceptSchema(
					EXTEND_PARTIAL_SOLUTION);
			proposeExtensionSchema.add(
					EXTEND_PARTIAL_SOLUTION_DECLARED_CAPABILITY,
					declaredCapabilitySchema);
			proposeExtensionSchema.add(
					EXTEND_PARTIAL_SOLUTION_RESOURCES_CONSUMED,
					matchedResourceWithReplacementSchema, 0,
					ObjectSchema.UNLIMITED);
			proposeExtensionSchema.add(
					EXTEND_PARTIAL_SOLUTION_RESOURCES_PRODUCED,
					matchedResourceWithReplacementSchema, 0,
					ObjectSchema.UNLIMITED);
			proposeExtensionSchema.add(
					EXTEND_PARTIAL_SOLUTION_POTENTIAL_EXTENSION,
					potentialExtensionSchema);
			add(proposeExtensionSchema);

		}
		// Pretty well everything we do here might generate an ontology
		// exception.
		catch (OntologyException e) {
			e.printStackTrace();
		}
	}
}
