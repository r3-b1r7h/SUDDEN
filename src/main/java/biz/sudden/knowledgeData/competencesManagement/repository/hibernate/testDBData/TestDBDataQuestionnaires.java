package biz.sudden.knowledgeData.competencesManagement.repository.hibernate.testDBData;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireRepository;

public class TestDBDataQuestionnaires {

	Logger logger = Logger.getLogger(this.getClass());

	// Access to other entities
	private TestDBDataCategories categories;

	// Repositories
	private IQuestionnaireRepository questionnaireRepository;

	// Memory Objects
	private Hashtable<String, Questionnaire> questionnaires = new Hashtable<String, Questionnaire>();
	private TestDBDataCompetences competences;

	public TestDBDataCategories getCategories() {
		return categories;
	}

	public TestDBDataCompetences getCompetences() {
		return competences;
	}

	public Questionnaire getQuestionnaireByName(String name) {
		return questionnaires.get(name);
	}

	public IQuestionnaireRepository getQuestionnaireRepository() {
		return questionnaireRepository;
	}

	public void insertDBQuestionnairesTestData() {
		/* Adding Questionnaires Test Data */
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setCategoryId(categories.getCategoryQuestionnaire(
				"Evaluation Level 0").getId());
		questionnaire.setCategoryName("Evaluation Level 0");
		questionnaire.setDescription("Evaluation Level 0 Questionnaire");
		questionnaire.setEText("Evaluation Level 0 Questionnaire");
		questionnaire.setQText("Evaluation Level 0 Questionnaire");
		questionnaire.setName("EL-0 Questionnaire");
		questionnaire.addCompetence(competences.getCompetence("L0 Stammdaten"));
		questionnaire
				.addCompetence(competences.getCompetence("L0 Technologie"));
		questionnaire.addCompetence(competences
				.getCompetence("L0 Kommunikationstechnologie"));
		questionnaire.addCompetence(competences
				.getCompetence("L0 Recht und Haftung"));
		questionnaire.addCompetence(competences
				.getCompetence("L0 Logistikmanagement"));
		questionnaire.addCompetence(competences
				.getCompetence("L0 Umweltmanagement"));
		questionnaire.addCompetence(competences
				.getCompetence("L0 Qualitaetsmanagement"));
		questionnaire
				.addCompetence(competences.getCompetence("L0 Kundenfokus"));
		questionnaire
				.addCompetence(competences
						.getCompetence("L0 KontinuierlicheVerbesserungLernenUndInnovation"));
		questionnaire.addCompetence(competences.getCompetence("L0 Finanzen"));
		questionnaireRepository.addQuestionnaire(questionnaire);
		questionnaires.put(questionnaire.getName(), questionnaire);

		questionnaire = new Questionnaire();
		questionnaire.setCategoryId(categories.getCategoryQuestionnaire(
				"Evaluation Level 1").getId());
		questionnaire.setCategoryName("Evaluation Level 1");
		questionnaire.setDescription("Evaluation Level 1 Questionnaire");
		questionnaire.setEText("Evaluation Level 1 Questionnaire");
		questionnaire.setQText("Evaluation Level 1 Questionnaire");
		questionnaire.setName("EL-1 Questionnaire");
		questionnaire.addCompetence(competences
				.getCompetence("L1 Umweltmanagement"));
		questionnaire
				.addCompetence(competences.getCompetence("L1 Technologie"));
		questionnaire.addCompetence(competences
				.getCompetence("L1 Kommunikationstechnologie"));
		questionnaire.addCompetence(competences
				.getCompetence("L1 Mitarbeiterqualifikation"));
		questionnaire.addCompetence(competences
				.getCompetence("L1 Fuehrungskompetenz"));
		questionnaire.addCompetence(competences
				.getCompetence("L1 Logistikmanagement"));
		questionnaire.addCompetence(competences
				.getCompetence("L1 Qualitaetsmanagement"));
		questionnaire.addCompetence(competences
				.getCompetence("L1 Recht und Haftung"));
		questionnaire
				.addCompetence(competences.getCompetence("L1 Kundenfokus"));
		questionnaire
				.addCompetence(competences
						.getCompetence("L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		questionnaire.addCompetence(competences.getCompetence("L1 Finanzen"));
		questionnaireRepository.addQuestionnaire(questionnaire);
		questionnaires.put(questionnaire.getName(), questionnaire);
	}

	public void setCategories(TestDBDataCategories categories) {
		this.categories = categories;
	}

	public void setCompetences(TestDBDataCompetences competences) {
		this.competences = competences;
	}

	public void setQuestionnaireRepository(
			IQuestionnaireRepository questionnaireRepository) {
		this.questionnaireRepository = questionnaireRepository;
	}

}
