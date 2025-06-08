package biz.sudden.designCoordination.teamFormation.bottomUp.ontologies;

import jade.content.lang.sl.SL0Vocabulary;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.TermSchema;

/**
 * 
 * @author mcassmc
 * 
 *         The ontological structures needed for the creation of a noticeboard.
 * 
 *         These are dependent on the process ontology.
 * 
 */

public class CreateNoticeboardOntology extends Ontology {

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

	private static Ontology theInstance = new CreateNoticeboardOntology();

	public static final String ONTOLOGY_NAME = "EmptyShellCommandOntology";

	private static Ontology basicOnt;

	// Ontology concept name constants
	public static final String NOTICEBOARD_UTILITY = "NoticeBoard_Utility";
	public static final String NOTICEBOARD_UTILITY_COMPANY_REQUESTING_SERVICE = "Company_Requesting_Service";
	public static final String NOTICEBOARD_UTILITY_AMOUNT_REQUIRED = "Amount_Required";
	public static final String NOTICEBOARD_UTILITY_EXPECTED_COST = "Expected_Cost";
	public static final String NOTICEBOARD_UTILITY_START_OF_PRODUCTION = "Start_Of_Production";

	public static final String RANGE = "range";
	public static final String RANGE_MIN = "range_min";
	public static final String RANGE_MAX = "range_max";

	public static final String CONSTRAINT_ON_PROJECT = "Constraint_on_project";

	public static final String NOTICEBOARD_DESCRIPTION = "Noticeboard_Description";
	public static final String NOTICEBOARD_DESCRIPTION_TIME_CREATED = "Time_Created";
	public static final String NOTICEBOARD_DESCRIPTION_ADDRESS = "Noticeboard_Address";
	public static final String NOTICEBOARD_DESCRIPTION_UTILITY = "Noticeboard_Utility";
	public static final String NOTICEBOARD_DESCRIPTION_INITIAL_STATE = "Initial_State";
	public static final String NOTICEBOARD_DESCRIPTION_GOAL_STATE = "Goal_State";

	public static final String CREATE_NOTICEBOARD = " Create_Noticeboard";
	public static final String CREATE_NOTICEBOARD_DESCRIPTION = "Description";
	public static final String CREATE_NOTICEBOARD_INITIAL_PROCESS = "Initial_Process";

	public static final String INITIAL_PROCESS = "Initial_Process";
	public static final String INITIAL_PROCESS_INITIAL_STATE = "Initial_State";
	public static final String INITIAL_PROCESS_GOAL_STATE = "Goal_State";
	public static final String INITIAL_PROCESS_STARTING_DECLARED_CAPABILITIES = "Starting_Declared_Capabilities";
	public static final String INITIAL_PROCESS_INTERMEDIATE_STATES_WITH_ORDER = "Intermediate_States_With_Order";

	public static final String STATE_WITH_ORDER = "State_With_Order";
	public static final String STATE_WITH_ORDER_MAIN_STATE = "Reffered_State";
	public static final String STATE_WITH_ORDER_STATES_LESS_THAN = "States_Less_Than";
	public static final String STATE_WITH_ORDER_STATES_GREATER_THAN = "States_Greater_Than";

	public CreateNoticeboardOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		basicOnt = BasicOntology.getInstance();
		try {
			// prelims
			TermSchema stringSchema = (TermSchema) basicOnt
					.getSchema(BasicOntology.STRING);
			ConceptSchema AIDSchema = (ConceptSchema) basicOnt
					.getSchema(SL0Vocabulary.AID);
			TermSchema intSchema = (TermSchema) basicOnt
					.getSchema(BasicOntology.INTEGER);
			TermSchema dateSchema = (TermSchema) basicOnt
					.getSchema(BasicOntology.DATE);

			// Schemas from the process ontology
			ConceptSchema stateSchema = (ConceptSchema) ProcessOntology
					.getInstance().getSchema(ProcessOntology.STATE);
			ConceptSchema declaredCapabilitySchema = (ConceptSchema) ProcessOntology
					.getInstance().getSchema(
							ProcessOntology.DECLARED_CAPABILITY);

			// Add Concepts - the utility information
			ConceptSchema range = new ConceptSchema(RANGE);
			range.add(RANGE_MIN, intSchema);
			range.add(RANGE_MAX, intSchema);
			add(range);

			// The utility information
			ConceptSchema NoticeBoardUtility = new ConceptSchema(
					NOTICEBOARD_UTILITY);
			NoticeBoardUtility.add(
					NOTICEBOARD_UTILITY_COMPANY_REQUESTING_SERVICE,
					stringSchema);
			NoticeBoardUtility.add(NOTICEBOARD_UTILITY_AMOUNT_REQUIRED, range);
			NoticeBoardUtility
					.add(NOTICEBOARD_UTILITY_EXPECTED_COST, intSchema);
			NoticeBoardUtility.add(NOTICEBOARD_UTILITY_START_OF_PRODUCTION,
					dateSchema);
			add(NoticeBoardUtility);

			// The constraints on the project (eg overall time)
			// TODO - expand this later
			ConceptSchema ConstraintSchema = new ConceptSchema(
					CONSTRAINT_ON_PROJECT);
			add(ConstraintSchema);

			// States with attached partial order information. Does this *work*
			// ?
			ConceptSchema StateWithOrderSchema = new ConceptSchema(
					STATE_WITH_ORDER);
			StateWithOrderSchema.add(STATE_WITH_ORDER_MAIN_STATE, stateSchema);
			StateWithOrderSchema.add(STATE_WITH_ORDER_STATES_LESS_THAN,
					intSchema, 0, ObjectSchema.UNLIMITED);
			StateWithOrderSchema.add(STATE_WITH_ORDER_STATES_GREATER_THAN,
					intSchema, 0, ObjectSchema.UNLIMITED);
			add(StateWithOrderSchema);

			// The initial Process itself
			ConceptSchema initialProcess = new ConceptSchema(INITIAL_PROCESS);
			initialProcess.add(INITIAL_PROCESS_GOAL_STATE, stateSchema);
			initialProcess.add(INITIAL_PROCESS_INITIAL_STATE, stateSchema);
			initialProcess.add(INITIAL_PROCESS_INTERMEDIATE_STATES_WITH_ORDER,
					StateWithOrderSchema, 0, ObjectSchema.UNLIMITED);
			initialProcess.add(INITIAL_PROCESS_STARTING_DECLARED_CAPABILITIES,
					declaredCapabilitySchema, 0, ObjectSchema.UNLIMITED);
			add(initialProcess);

			// The noticeboard description concept
			ConceptSchema NoticeBoardDescriptionSchema = new ConceptSchema(
					NOTICEBOARD_DESCRIPTION);
			NoticeBoardDescriptionSchema.add(NOTICEBOARD_DESCRIPTION_ADDRESS,
					AIDSchema, ObjectSchema.OPTIONAL);
			NoticeBoardDescriptionSchema.add(
					NOTICEBOARD_DESCRIPTION_TIME_CREATED, intSchema,
					ObjectSchema.OPTIONAL);
			NoticeBoardDescriptionSchema.add(NOTICEBOARD_DESCRIPTION_UTILITY,
					NoticeBoardUtility);
			NoticeBoardDescriptionSchema.add(
					NOTICEBOARD_DESCRIPTION_INITIAL_STATE, stateSchema,
					ObjectSchema.OPTIONAL);
			NoticeBoardDescriptionSchema.add(
					NOTICEBOARD_DESCRIPTION_GOAL_STATE, stateSchema,
					ObjectSchema.OPTIONAL);
			add(NoticeBoardDescriptionSchema);

			// The action to do with creating a new noticeboard
			AgentActionSchema createNoticeBoard = new AgentActionSchema(
					CREATE_NOTICEBOARD);
			createNoticeBoard.add(CREATE_NOTICEBOARD_DESCRIPTION,
					NoticeBoardDescriptionSchema);
			createNoticeBoard.add(CREATE_NOTICEBOARD_INITIAL_PROCESS,
					initialProcess);
			add(createNoticeBoard);

		}
		// Pretty well everything we do here might generate an ontology
		// exception.
		catch (OntologyException e) {
			e.printStackTrace();
		}
	}
}
