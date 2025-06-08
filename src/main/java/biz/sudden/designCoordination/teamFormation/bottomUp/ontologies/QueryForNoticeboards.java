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
 *         The ontology used to support the querying for suitable noticeboards.
 */

public class QueryForNoticeboards extends Ontology {

	private static Ontology theInstance = new QueryForNoticeboards();

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

	public static String ONTOLOGY_NAME = "NoticeBoard_Query_Ontology";

	// The String names
	public static final String NOTICEBOARD = "Noticeboard";

	public static final String REQUIRED_BY_COMPANY = "Required_By_Company";
	public static final String REQUIRED_BY_COMPANY_NAME = "CompanyName";

	public QueryForNoticeboards() {
		super(ONTOLOGY_NAME, SLOntology.getInstance());
		CreateNoticeboardOntology createNoticeInstance = (CreateNoticeboardOntology) CreateNoticeboardOntology
				.getInstance();

		try {

			// For the objects of these predicates
			ConceptSchema baseConcept;

			baseConcept = (ConceptSchema) createNoticeInstance
					.getSchema(CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION);
			TermSchema stringSchema = (TermSchema) BasicOntology.getInstance()
					.getSchema(BasicOntology.STRING);

			PredicateSchema isNoticeBoardSchema = new PredicateSchema(
					NOTICEBOARD);
			isNoticeBoardSchema.add(QueryOntology.OBJECT, baseConcept);
			add(isNoticeBoardSchema);

			PredicateSchema producesAbstractTypesSchema = new PredicateSchema(
					QueryOntology.PRODUCES_ABSTRACT_TYPES);
			producesAbstractTypesSchema.add(QueryOntology.OBJECT, baseConcept);
			producesAbstractTypesSchema.add(QueryOntology.LIST_OF_TYPES,
					stringSchema, 0, ObjectSchema.UNLIMITED);
			add(producesAbstractTypesSchema);

			PredicateSchema requiresAbstractTypes = new PredicateSchema(
					QueryOntology.REQUIRES_ABSTRACT_TYPES);
			requiresAbstractTypes.add(QueryOntology.OBJECT, baseConcept);
			requiresAbstractTypes.add(QueryOntology.LIST_OF_TYPES,
					stringSchema, 0, ObjectSchema.UNLIMITED);
			add(requiresAbstractTypes);

			/*
			 * TODO - do this for constrained types too once they're introduced.
			 */

			PredicateSchema requiredByCompany = new PredicateSchema(
					REQUIRED_BY_COMPANY);
			requiredByCompany.add(REQUIRED_BY_COMPANY_NAME, BasicOntology
					.getInstance().getSchema(BasicOntology.STRING));
			add(requiredByCompany);

			PredicateSchema createdSince = new PredicateSchema(
					QueryOntology.CREATED_SINCE);
			createdSince.add(QueryOntology.CREATED_SINCE_TIME, BasicOntology
					.getInstance().getSchema(BasicOntology.INTEGER));
			add(createdSince);

		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}

}
