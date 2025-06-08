package biz.sudden.designCoordination.teamFormation.bottomUp.ontologies;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.TermSchema;
import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.FromRangeConstraintSchema;
import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.ValueFromConstraintSchema;

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

public class ConstrainedProductOntology extends Ontology {

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

	private static Ontology theInstance = new ConstrainedProductOntology();

	public static final String ONTOLOGY_NAME = "ConstrainedProductOntology";

	private static Ontology basicOnt;

	// Ontology concept name constants

	public static final String PRODUCT = "Product";
	public static final String PRODUCT_STRING_ATTRIBUTE = "Product_String_Attribute";
	public static final String PRODUCT_INT_ATTRIBUTE = "Product_int_Attribute";

	public static final String PRODUCTSUBTYPE = "Product_SubType";

	public ConstrainedProductOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		basicOnt = BasicOntology.getInstance();
		try {
			// prelims
			TermSchema stringSchema = (TermSchema) basicOnt
					.getSchema(BasicOntology.STRING);
			TermSchema intSchema = (TermSchema) basicOnt
					.getSchema(BasicOntology.INTEGER);
			ConceptSchema conceptSchema = (ConceptSchema) basicOnt
					.getSchema(ConceptSchema.BASE_NAME);

			ConceptSchema productSchema = new ConceptSchema(PRODUCT);
			productSchema.add(PRODUCT_STRING_ATTRIBUTE, stringSchema);
			productSchema.add(PRODUCT_INT_ATTRIBUTE, intSchema);

			ConceptSchema productSubType = new ConceptSchema(PRODUCTSUBTYPE);
			productSubType.addSuperSchema(productSchema);

			add(productSchema);
			add(productSubType);
			add(new ValueFromConstraintSchema());
			add(new FromRangeConstraintSchema());

		}
		// Pretty well everything we do here might generate an ontology
		// exception.
		catch (OntologyException e) {
			e.printStackTrace();
		}
	}
}
