package biz.sudden.designCoordination.handleBO.service;

import java.util.ArrayList;
import java.util.HashMap;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class Test {
	private static final String SuddenOntologyURI = "http://www.Sudden-ontologies.com/SuddenOntology.owl";
	private static final String NameSpace = SuddenOntologyURI + "#";
	private static final String LocalFileWithSuddenOntology = "file:SUDDENontologyOWL_KF.owl";

	public static void main(String[] args) {

		BusinessOpportunity boIN = new BusinessOpportunity();

		// Lets get an individual :)
		OntModel ontologyInMemory = ModelFactory.createOntologyModel();

		OntDocumentManager docM = ontologyInMemory.getDocumentManager();
		docM.addAltEntry(SuddenOntologyURI, LocalFileWithSuddenOntology);
		ontologyInMemory.read(SuddenOntologyURI);

		HandleBOServiceImpl hb = new HandleBOServiceImpl();

		ExtendedIterator it = ontologyInMemory.listClasses();
		while (it.hasNext()) {
			System.out.println(" class in ontology in memory " + it.next());
		}

		hb.setOntology(ontologyInMemory);

		Individual goal = ontologyInMemory.getIndividual(NameSpace
				+ "Abgassystem");
		HashMap<Individual, ArrayList<Decomposition>> decompositions = new HashMap<Individual, ArrayList<Decomposition>>();
		BusinessOpportunity businessOpportunityIn = new BusinessOpportunity();
		businessOpportunityIn.setIndividualGoalURI(NameSpace
				+ "Abgassystem_Brennstoffzelle_Individual");

		System.out.println(hb
				.generateInitialSupplyNetworks(businessOpportunityIn));
		boIN.setIndividualGoalURI(goal.getURI());

	}
}
