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
 *         The ontology used to support querying for suitable potential
 *         extensions.
 */

public class QueryForPotentialExtensions extends Ontology {

	private static Ontology theInstance = new QueryForPotentialExtensions();

	private static final long serialVersionUID = 1L;

	/**
	 * @return theNoticeBoardQueryOntology
	 * 
	 *         As traditional with Jade - a static instance of the ontology is
	 *         returned via a getInstance method and the constructor is private.
	 * 
	 *         Mostly this ontology contains predicates
	 */
	public static Ontology getInstance() {
		return theInstance;
	}

	public static String ONTOLOGY_NAME = "PotentialExtension_Query_Ontology";

	// The String names
	public static final String POTENTIAL_EXTENSION = "Is_Potential_Extension";

	public static final String PRODUCES_ABSTRACT_TYPES = "Produces_Abstract_Types";
	public static final String REQUIRES_ABSTRACT_TYPES = "Requires_Abstract_Types";

	public static final String PRODUCES_CONSTRAINED_TYPES = "Produces_Constrained_Types";
	public static final String REQUIRES_CONSTRAINED_TYPES = "Requires_Constrained_Types";

	public static final String CONTAINS_POTENTIAL_EXTENSIONS_FROM = "Contains_Potential_Extensions_From";
	public static final String COMPANIES = "CompanyNames";

	public static final String CREATED_SINCE = "Created_Since";
	public static final String CREATED_SINCE_TIME = "Time";

	public static final String LIST_OF_TYPES = "Types";
	public static final String OBJECT = "Object";

	public QueryForPotentialExtensions() {
		super(ONTOLOGY_NAME, SLOntology.getInstance());
		try {

			// TODO - What does having name space clashes do to us?

			// For the objects of these predicates
			ConceptSchema baseConcept;

			baseConcept = (ConceptSchema) ProcessOntology.getInstance()
					.getSchema(ProcessOntology.DECLARED_CAPABILITY);
			TermSchema stringSchema = (TermSchema) BasicOntology.getInstance()
					.getSchema(BasicOntology.STRING);

			PredicateSchema isNoticeBoardSchema = new PredicateSchema(
					POTENTIAL_EXTENSION);
			isNoticeBoardSchema.add(OBJECT, baseConcept);
			add(isNoticeBoardSchema);

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

			PredicateSchema requiredByCompany = new PredicateSchema(
					CONTAINS_POTENTIAL_EXTENSIONS_FROM);
			requiredByCompany.add(COMPANIES, (TermSchema) BasicOntology
					.getInstance().getSchema(BasicOntology.STRING), 1,
					ObjectSchema.UNLIMITED);
			add(requiredByCompany);

			PredicateSchema createdSince = new PredicateSchema(CREATED_SINCE);
			createdSince.add(CREATED_SINCE_TIME, BasicOntology.getInstance()
					.getSchema(BasicOntology.INTEGER));
			add(createdSince);

		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}

}
