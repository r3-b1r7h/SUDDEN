package biz.sudden.knowledgeData.competencesManagement.repository.hibernate.testDBData;

import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.knowledgeData.competencesManagement.domain.RoleCompetence;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleDimension;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleCompetenceRepository;

public class TestDBDataRoleCompetences {

	Logger logger = Logger.getLogger(this.getClass());

	// Access to other entities
	private TestDBDataCompetences competences;

	// Repositories
	private IRoleCompetenceRepository roleCompetenceRepository;

	// Memory Objects
	private Hashtable<String, RoleCompetence> roleCompetences = new Hashtable<String, RoleCompetence>();
	private TestDBDataRoleDimensions roleDimensions;

	public TestDBDataCompetences getCompetences() {
		return competences;
	}

	public IRoleCompetenceRepository getRoleCompetenceRepository() {
		return roleCompetenceRepository;
	}

	public Hashtable<String, RoleCompetence> getRoleCompetences() {
		return roleCompetences;
	}

	public TestDBDataRoleDimensions getRoleDimensions() {
		return roleDimensions;
	}

	public void insertDBTestDataRoleCompetences() {
		/* Adding Competences Test Data */
		insertDBTestDataRoleCompetences_L0();
		insertDBTestDataRoleCompetences_L1();
	}

	private void insertDBTestDataRoleCompetences_L0() {
		/* Adding Role Competences Test Data */
		RoleCompetence roleCompetence = new RoleCompetence();
		roleCompetence
				.setCompetence(competences.getCompetence("L0 Stammdaten"));
		roleCompetence.setWeight(0);
		List<RoleDimension> roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L0 Technologie"));
		roleCompetence.setWeight(5.74f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L0 Kommunikationstechnologie"));
		roleCompetence.setWeight(5.8f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L0 Recht und Haftung"));
		roleCompetence.setWeight(8);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L0 Logistikmanagement"));
		roleCompetence.setWeight(4);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L0 Umweltmanagement"));
		roleCompetence.setWeight(7.75f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L0 Qualitaetsmanagement"));
		roleCompetence.setWeight(0.5f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L0 Kundenfokus"));
		roleCompetence.setWeight(4.3f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence
				.setCompetence(competences
						.getCompetence("L0 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleCompetence.setWeight(3.6f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences.getCompetence("L0 Finanzen"));
		roleCompetence.setWeight(4.375f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);
	}

	private void insertDBTestDataRoleCompetences_L1() {
		/* Adding Role Competences Test Data */
		RoleCompetence roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L1 Umweltmanagement"));
		roleCompetence.setWeight(2.25f);
		List<RoleDimension> roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L1 Technologie"));
		roleCompetence.setWeight(4.26f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L1 Kommunikationstechnologie"));
		roleCompetence.setWeight(4.2f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L1 Mitarbeiterqualifikation"));
		roleCompetence.setWeight(10);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L1 Fuehrungskompetenz"));
		roleCompetence.setWeight(10);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L1 Logistikmanagement"));
		roleCompetence.setWeight(6);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L1 Qualitaetsmanagement"));
		roleCompetence.setWeight(9.5f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L1 Recht und Haftung"));
		roleCompetence.setWeight(2);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences
				.getCompetence("L1 Kundenfokus"));
		roleCompetence.setWeight(5.7f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence
				.setCompetence(competences
						.getCompetence("L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleCompetence.setWeight(6.4f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);

		roleCompetence = new RoleCompetence();
		roleCompetence.setCompetence(competences.getCompetence("L1 Finanzen"));
		roleCompetence.setWeight(5.625f);
		roleCompetenceRoleDimensions = roleDimensions
				.getRoleDimensionsOf(roleCompetence.getCompetence().getName());
		for (int c0 = 0; c0 < roleCompetenceRoleDimensions.size(); c0++) {
			roleCompetence.addRoleDimension(roleCompetenceRoleDimensions
					.get(c0));
		}
		roleCompetenceRepository.addRoleCompetence(roleCompetence);
		roleCompetences.put(roleCompetence.getCompetence().getName(),
				roleCompetence);
	}

	public void setCompetences(TestDBDataCompetences competences) {
		this.competences = competences;
	}

	public void setRoleCompetenceRepository(
			IRoleCompetenceRepository roleCompetenceRepository) {
		this.roleCompetenceRepository = roleCompetenceRepository;
	}

	public void setRoleCompetences(
			Hashtable<String, RoleCompetence> roleCompetences) {
		this.roleCompetences = roleCompetences;
	}

	public void setRoleDimensions(TestDBDataRoleDimensions roleDimensions) {
		this.roleDimensions = roleDimensions;
	}

}
