package biz.sudden.designCoordination.teamFormation.bottomUp.ontologies;

import jade.content.lang.sl.SLOntology;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.TermSchema;

/**
 * 
 * @author mcassmc
 * 
 *         The ontology classes shared between the two more specific query
 *         ontologies.
 */

public class QueryOntology extends Ontology {

	private static Ontology theInstance = new QueryOntology();

	private static final long serialVersionUID = 1L;

	/**
	 * @return aGenericQueryOntology
	 * 
	 *         As traditional with Jade - a static instance of the ontology is
	 *         returned via a getInstance method and the constructor is private.
	 * 
	 *         This contains the predicates which are shared between the more
	 *         specific query ontologies.
	 */
	public static Ontology getInstance() {
		return theInstance;
	}

	public static String ONTOLOGY_NAME = "Query_Ontology";

	// The String names
	public static final String PRODUCES_ABSTRACT_TYPES = "Produces_Abstract_Types";
	public static final String REQUIRES_ABSTRACT_TYPES = "Requires_Abstract_Types";

	public static final String PRODUCES_CONSTRAINED_TYPES = "Produces_Constrained_Types";
	public static final String REQUIRES_CONSTRAINED_TYPES = "Requires_Constrained_Types";

	public static final String CREATED_SINCE = "Created_Since";
	public static final String CREATED_SINCE_TIME = "Time";

	public static final String LIST_OF_TYPES = "Types";
	// The generic object for all of the predicates
	public static final String OBJECT = "Object";

	public QueryOntology() {
		// SLOntology to get the SL constructs (Not,And etc) used for IRE's
		super(ONTOLOGY_NAME, SLOntology.getInstance());
		try {

			// For the objects of these predicates
			ConceptSchema baseConcept;

			baseConcept = (ConceptSchema) ConceptSchema.getBaseSchema();
			TermSchema stringSchema = (TermSchema) BasicOntology.getInstance()
					.getSchema(BasicOntology.STRING);

			PredicateSchema producesAbstractTypesSchema = new PredicateSchema(
					PRODUCES_ABSTRACT_TYPES);
			producesAbstractTypesSchema.add(OBJECT, baseConcept);
			producesAbstractTypesSchema.add(LIST_OF_TYPES, stringSchema, 0,
					ObjectSchema.UNLIMITED);
			add(producesAbstractTypesSchema);

			PredicateSchema requiresAbstractTypes = new PredicateSchema(
					REQUIRES_ABSTRACT_TYPES);
			requiresAbstractTypes.add(OBJECT, baseConcept);
			requiresAbstractTypes.add(LIST_OF_TYPES, stringSchema, 0,
					ObjectSchema.UNLIMITED);
			add(requiresAbstractTypes);

			/*
			 * TODO - do this for constrained types too once they're introduced.
			 */

			PredicateSchema createdSince = new PredicateSchema(CREATED_SINCE);
			createdSince.add(CREATED_SINCE_TIME, BasicOntology.getInstance()
					.getSchema(BasicOntology.INTEGER));
			add(createdSince);

		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}

}
