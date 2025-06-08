package biz.sudden.designCoordination.teamFormation.bottomUp.ontologies;

import jade.content.lang.sl.SL0Vocabulary;
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
 *         The ontological structures needed for the creation of a noticeboard.
 * 
 *         These are dependent on the process ontology.
 * 
 */

public class ShapeGraphOntology extends Ontology {

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

	private static Ontology theInstance = new ShapeGraphOntology();

	public static final String ONTOLOGY_NAME = "EmptyShellCommandOntology";

	private static Ontology basicOnt;

	// Ontology concept name constants

	public static final String NODE_NODEID = "NodeId";
	public static final String NODE_MEMBERLIST = "MemberList";
	public static final String NODE_TASKLIST = "TaskList";
	public static final String NODE = "Node";

	public static final String ABSTRACTTASK_NAME = "Name";
	public static final String ABSTRACTTASK_IDENTIFIERS = "Identifiers";
	public static final String ABSTRACTTASK = "AbstractTask";

	public static final String TEAMMEMBER_NAME = "Name";
	public static final String TEAMMEMBER_ROLESPLAYED = "RolesPlayed";
	public static final String TEAMMEMBER_IDENTIFIERS = "Identifiers";
	public static final String TEAMMEMBER = "TeamMember";

	public static final String ABSTRACTROLE_NAME = "Name";
	public static final String ABSTRACTROLE_IDENTIFIER = "Identifier";
	public static final String ABSTRACTROLE = "AbstractRole";

	public static final String SHAPEGRAPH_ARCS = "Arcs";
	public static final String SHAPEGRAPH_NODES = "Nodes";
	public static final String SHAPEGRAPH = "ShapeGraph";

	public static final String ARC_DEPENDENCYTYPE = "DependencyType";
	public static final String ARC_OUTPUTNODE = "OutputNode";
	public static final String ARC_INPUTNODE = "InputNode";
	public static final String ARC = "Arc";

	public static final String DEPENDENCY = "Dependency";
	public static final String RESOURCEDEPENDENCY = "ResourceDependency";

	public ShapeGraphOntology() {
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

			// Schemas from the process ontology
			ConceptSchema stateSchema = (ConceptSchema) ProcessOntology
					.getInstance().getSchema(ProcessOntology.STATE);
			ConceptSchema declaredCapabilitySchema = (ConceptSchema) ProcessOntology
					.getInstance().getSchema(
							ProcessOntology.DECLARED_CAPABILITY);

			ConceptSchema abstractTaskSchema = new ConceptSchema(ABSTRACTTASK);
			abstractTaskSchema.add(ABSTRACTTASK_IDENTIFIERS,
					(TermSchema) getSchema(BasicOntology.STRING), 0,
					ObjectSchema.UNLIMITED);
			abstractTaskSchema.add(ABSTRACTTASK_NAME,
					(TermSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			add(
					abstractTaskSchema,
					biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.AbstractTask.class);

			ConceptSchema abstractRoleSchema = new ConceptSchema(ABSTRACTROLE);
			abstractRoleSchema.add(ABSTRACTROLE_IDENTIFIER,
					(TermSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.MANDATORY);
			abstractRoleSchema.add(ABSTRACTROLE_NAME,
					(TermSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			add(
					abstractRoleSchema,
					biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.AbstractRole.class);

			ConceptSchema teamMemberSchema = new ConceptSchema(TEAMMEMBER);
			teamMemberSchema.add(TEAMMEMBER_IDENTIFIERS,
					(TermSchema) getSchema(BasicOntology.STRING), 0,
					ObjectSchema.UNLIMITED);
			teamMemberSchema.add(TEAMMEMBER_ROLESPLAYED, abstractRoleSchema, 0,
					ObjectSchema.UNLIMITED);
			teamMemberSchema.add(TEAMMEMBER_NAME,
					(TermSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			add(
					teamMemberSchema,
					biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.TeamMember.class);

			ConceptSchema nodeSchema = new ConceptSchema(NODE);
			nodeSchema.add(NODE_TASKLIST, abstractTaskSchema, 0,
					ObjectSchema.UNLIMITED);
			nodeSchema.add(NODE_MEMBERLIST, teamMemberSchema, 0,
					ObjectSchema.UNLIMITED);
			nodeSchema.add(NODE_NODEID,
					(TermSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			add(
					nodeSchema,
					biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.Node.class);

			ConceptSchema dependencySchema = new ConceptSchema(DEPENDENCY);
			add(
					dependencySchema,
					biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.Dependency.class);

			ConceptSchema arcSchema = new ConceptSchema(ARC);
			arcSchema.add(ARC_INPUTNODE,
					(TermSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			arcSchema.add(ARC_OUTPUTNODE,
					(TermSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			arcSchema.add(ARC_DEPENDENCYTYPE, dependencySchema,
					ObjectSchema.OPTIONAL);
			add(
					arcSchema,
					biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.Arc.class);

			ConceptSchema resourceDependencySchema = new ConceptSchema(
					RESOURCEDEPENDENCY);
			add(
					resourceDependencySchema,
					biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.ResourceDependency.class);

			ConceptSchema shapeGraphSchema = new ConceptSchema(SHAPEGRAPH);
			shapeGraphSchema.add(SHAPEGRAPH_NODES, nodeSchema, 0,
					ObjectSchema.UNLIMITED);
			shapeGraphSchema.add(SHAPEGRAPH_ARCS, arcSchema, 0,
					ObjectSchema.UNLIMITED);
			add(
					shapeGraphSchema,
					biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits.ShapeGraph.class);

		}
		// Pretty well everything we do here might generate an ontology
		// exception.
		catch (OntologyException e) {
			e.printStackTrace();
		}
	}
}
