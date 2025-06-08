package biz.sudden.knowledgeData.competencesManagement.repository.hibernate.testDBData;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import biz.sudden.knowledgeData.competencesManagement.domain.Role;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleQuestionnaire;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleQuestionnaireRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleRepository;

public class TestDBDataRoles {

	Logger logger = Logger.getLogger(this.getClass());

	// Access to other entities
	private TestDBDataCategories categories;
	private TestDBDataQuestionnaires questionnaires;

	// Repositories
	private IRoleRepository roleRepository;
	private IRoleQuestionnaireRepository roleQuestionnaireRepository;

	// Memory Objects
	private Hashtable<String, Role> roles = new Hashtable<String, Role>();
	private TestDBDataRoleCompetences roleCompetences;

	public TestDBDataCategories getCategories() {
		return categories;
	}

	public TestDBDataQuestionnaires getQuestionnaires() {
		return questionnaires;
	}

	public TestDBDataRoleCompetences getRoleCompetences() {
		return roleCompetences;
	}

	public IRoleQuestionnaireRepository getRoleQuestionnaireRepository() {
		return roleQuestionnaireRepository;
	}

	public IRoleRepository getRoleRepository() {
		return roleRepository;
	}

	public Hashtable<String, Role> getRoles() {
		return roles;
	}

	public void insertDBRoleTestData() {
		/* Adding Roles Test Data */
		RoleQuestionnaire roleQuestionnaire = new RoleQuestionnaire();
		roleQuestionnaire.setQuestionnaire(questionnaires
				.getQuestionnaireByName("EL-0 Questionnaire"));
		roleQuestionnaire.setWeight(1);
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L0 Stammdaten"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L0 Technologie"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L0 Kommunikationstechnologie"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L0 Recht und Haftung"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L0 Logistikmanagement"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L0 Umweltmanagement"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L0 Qualitaetsmanagement"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L0 Kundenfokus"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get(
						"L0 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L0 Finanzen"));
		roleQuestionnaireRepository.addRoleQuestionnaire(roleQuestionnaire);
		Role role = new Role();
		role.setCategoryId(categories.getCategoryRole("Evaluation Level 0")
				.getId());
		role.setCategoryName("Evaluation Level 0");
		role.setName("ROLE EL-0 Questionnaire");
		role.setDescription("Evaluation Level 0 Questionnaire Role");
		role.setEText(role.getDescription());
		role.setQText(role.getDescription());
		role.setRoleQuestionnaire(roleQuestionnaire);
		roleRepository.addRole(role);
		roles.put(role.getName(), role);

		roleQuestionnaire = new RoleQuestionnaire();
		roleQuestionnaire.setQuestionnaire(questionnaires
				.getQuestionnaireByName("EL-1 Questionnaire"));
		roleQuestionnaire.setWeight(1);
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L1 Umweltmanagement"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L1 Technologie"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L1 Kommunikationstechnologie"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L1 Mitarbeiterqualifikation"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L1 Fuehrungskompetenz"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L1 Logistikmanagement"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L1 Qualitaetsmanagement"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L1 Recht und Haftung"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L1 Kundenfokus"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get(
						"L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleQuestionnaire.addRoleCompetence(roleCompetences
				.getRoleCompetences().get("L1 Finanzen"));
		roleQuestionnaireRepository.addRoleQuestionnaire(roleQuestionnaire);
		role = new Role();
		role.setCategoryId(categories.getCategoryRole("Evaluation Level 1")
				.getId());
		role.setCategoryName("Evaluation Level 1");
		role.setName("ROLE EL-1 Questionnaire");
		role.setDescription("Evaluation Level 1 Questionnaire Role");
		role.setEText(role.getDescription());
		role.setQText(role.getDescription());
		role.setRoleQuestionnaire(roleQuestionnaire);
		roleRepository.addRole(role);
		roles.put(role.getName(), role);
	}

	public void setCategories(TestDBDataCategories categories) {
		this.categories = categories;
	}

	public void setQuestionnaires(TestDBDataQuestionnaires questionnaires) {
		this.questionnaires = questionnaires;
	}

	public void setRoleCompetences(TestDBDataRoleCompetences roleCompetences) {
		this.roleCompetences = roleCompetences;
	}

	public void setRoleQuestionnaireRepository(
			IRoleQuestionnaireRepository roleQuestionnaireRepository) {
		this.roleQuestionnaireRepository = roleQuestionnaireRepository;
	}

	public void setRoleRepository(IRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public void setRoles(Hashtable<String, Role> roles) {
		this.roles = roles;
	}

}
