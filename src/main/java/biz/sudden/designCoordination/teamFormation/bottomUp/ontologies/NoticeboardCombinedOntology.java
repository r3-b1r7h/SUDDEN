package biz.sudden.designCoordination.teamFormation.bottomUp.ontologies;

import jade.content.onto.Ontology;

/**
 * 
 * @author mcassmc
 * 
 *         Currently this contains no concepts of it's own - it's just to let me
 *         have > 1 ontology/message. Really just part of the demo structuring.
 */

@SuppressWarnings("serial")
public class NoticeboardCombinedOntology extends Ontology {

	public static final String ONTOLOGY_NAME = "Combined_Notice_Board_BOntology";

	private static Ontology[] baseOntologies = buildArray();

	private static Ontology theInstance = new NoticeboardCombinedOntology();

	public NoticeboardCombinedOntology() {
		super(ONTOLOGY_NAME, baseOntologies, null);
	}

	// Note the order here is irrelevant - simply need to ensure we add every
	// ontology in as a base ontology
	private static Ontology[] buildArray() {
		Ontology[] result = new Ontology[7];
		result[0] = ProcessOntology.getInstance();
		result[1] = QueryOntology.getInstance();
		result[2] = CreateNoticeboardOntology.getInstance();
		result[3] = QueryForPotentialExtensions.getInstance();
		result[4] = QueryForNoticeboards.getInstance();
		result[5] = ShapeGraphOntology.getInstance();
		result[6] = SuddenProductOntology.getInstance();

		return result;
	}

	public static Ontology getInstance() {
		return theInstance;
	}

}
