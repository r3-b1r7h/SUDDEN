package biz.sudden.knowledgeData.competencesManagement.repository.hibernate.testDBData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.repository.ScopeRepository;
import biz.sudden.evaluation.performanceMeasurement.domain.WeightedSumFunction;
import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceRepository;

public class TestDBDataCompetences {

	Logger logger = Logger.getLogger(this.getClass());

	// Access to other entities
	private TestDBDataCategories categories;

	// Repositories
	private ICompetenceRepository competenceRepository;

	// Memory Objects
	private Hashtable<String, Competence> competences = new Hashtable<String, Competence>();
	private TestDBDataDimensions dimensions;

	private EnterpriseEvaluationService enterpriseEvalService;

	public TestDBDataCategories getCategories() {
		return categories;
	}

	public Competence getCompetence(String name) {
		if (!competences.containsKey(name))
			logger.error("KEY not found!!!: " + name);
		return competences.get(name);
	}

	public ICompetenceRepository getCompetenceRepository() {
		return competenceRepository;
	}

	public TestDBDataDimensions getDimensions() {
		return dimensions;
	}

	public void insertDBTestDataCompetences() {
		/* Adding Competences Test Data */
		insertDBTestDataCompetences_L0();
		insertDBTestDataCompetences_L1();
	}

	private void insertDBTestDataCompetences_L0() {
		/* Adding Competences Test Data */
		Competence competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence("Stammdaten")
				.getId());
		competence.setCategoryName("Stammdaten");
		competence.setName("L0 Stammdaten");
		competence
				.setDescription("Definieren Sie Ansprechpersonen fuer die verschiedenen Bereiche Ihres Unternehmens, u.a. fuer die Beantwortung der nachfolgenden Fragen");
		competence.setQText(competence.getName());
		competence.setEText(competence.getName());
		List<Dimension> competenceDimensions = dimensions
				.getDimensionsOf(competence.getName());
		List<String> params = new ArrayList<String>();
		List<EnterpriseEvaluationProfile> profiles = new ArrayList<EnterpriseEvaluationProfile>();
		String weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories
				.getCategoryCompetence("Technologie").getId());
		competence.setCategoryName("Technologie");
		competence.setName("L0 Technologie");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Kommunikationstechnologie").getId());
		competence.setCategoryName("Kommunikationstechnologie");
		competence.setName("L0 Kommunikationstechnologie");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Recht und Haftung").getId());
		competence.setCategoryName("Recht und Haftung");
		competence.setName("L0 Recht und Haftung");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Logistikmanagement").getId());
		competence.setCategoryName("Logistikmanagement");
		competence.setName("L0 Logistikmanagement");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Umweltmanagement").getId());
		competence.setCategoryName("Umweltmanagement");
		competence.setName("L0 Umweltmanagement");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Qualitaetsmanagement").getId());
		competence.setCategoryName("Qualitaetsmanagement");
		competence.setName("L0 Qualitaetsmanagement");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories
				.getCategoryCompetence("Kundenfokus").getId());
		competence.setCategoryName("Kundenfokus");
		competence.setName("L0 Kundenfokus");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Lernen und Innovation").getId());
		competence.setCategoryName("Lernen und Innovation");
		competence.setName("L0 KontinuierlicheVerbesserungLernenUndInnovation");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence("Finanzen")
				.getId());
		competence.setCategoryName("Finanzen");
		competence.setName("L0 Finanzen");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);
		weight = Double.toString(1.0d / profiles.size());
		for (int i = 0; i < profiles.size(); ++i)
			params.add(weight);
		enterpriseEvalService
				.createOrAssociateEnterpriseProfile(
						"SUddEN - L0 Profile",
						WeightedSumFunction.TYPE_NAME,
						params,
						profiles,
						enterpriseEvalService
								.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME));

	}

	private void insertDBTestDataCompetences_L1() {
		/* Adding Competences Test Data */
		Competence competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Umweltmanagement").getId());
		competence.setCategoryName("Umweltmanagement");
		competence.setName("L1 Umweltmanagement");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		List<Dimension> competenceDimensions = dimensions
				.getDimensionsOf(competence.getName());
		List<String> params = new ArrayList<String>();
		List<EnterpriseEvaluationProfile> profiles = new ArrayList<EnterpriseEvaluationProfile>();
		String weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories
				.getCategoryCompetence("Technologie").getId());
		competence.setCategoryName("Technologie");
		competence.setName("L1 Technologie");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Kommunikationstechnologie").getId());
		competence.setCategoryName("Kommunikationstechnologie");
		competence.setName("L1 Kommunikationstechnologie");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Mitarbeiterqualifikation").getId());
		competence.setCategoryName("Mitarbeiterqualifikation");
		competence.setName("L1 Mitarbeiterqualifikation");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Fuehrungskompetenz").getId());
		competence.setCategoryName("Fuehrungskompetenz");
		competence.setName("L1 Fuehrungskompetenz");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Logistikmanagement").getId());
		competence.setCategoryName("Logistikmanagement");
		competence.setName("L1 Logistikmanagement");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension(competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Qualitaetsmanagement").getId());
		competence.setCategoryName("Qualitaetsmanagement");
		competence.setName("L1 Qualitaetsmanagement");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / (double) competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension((Dimension) competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Recht und Haftung").getId());
		competence.setCategoryName("Recht und Haftung");
		competence.setName("L1 Recht und Haftung");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / (double) competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension((Dimension) competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories
				.getCategoryCompetence("Kundenfokus").getId());
		competence.setCategoryName("Kundenfokus");
		competence.setName("L1 Kundenfokus");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / (double) competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension((Dimension) competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence(
				"Lernen und Innovation").getId());
		competence.setCategoryName("Lernen und Innovation");
		competence.setName("L1 KontinuierlicheVerbesserungLernenUndInnovation");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / (double) competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension((Dimension) competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		competence = new Competence();
		competence.setCategoryId(categories.getCategoryCompetence("Finanzen")
				.getId());
		competence.setCategoryName("Finanzen");
		competence.setName("L1 Finanzen");
		competence.setDescription(competence.getName());
		competence.setEText(competence.getName());
		competence.setQText(competence.getName());
		competenceDimensions = dimensions.getDimensionsOf(competence.getName());
		weight = Double.toString(1.0d / (double) competenceDimensions.size());
		for (int c0 = 0; c0 < competenceDimensions.size(); c0++) {
			competence.addDimension((Dimension) competenceDimensions.get(c0));
			params.add(weight);
		}
		profiles
				.add(enterpriseEvalService
						.createOrAssociateEnterpriseProfile2(
								competence.getName(),
								WeightedSumFunction.TYPE_NAME,
								params,
								competence.getDimensions(),
								enterpriseEvalService
										.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME)));
		params.clear();

		competenceRepository.addCompetence(competence);
		competences.put(competence.getName(), competence);

		weight = Double.toString(1.0d / (double) profiles.size());
		for (int i = 0; i < profiles.size(); ++i)
			params.add(weight);
		enterpriseEvalService
				.createOrAssociateEnterpriseProfile(
						"SUddEN - L1 Profile",
						WeightedSumFunction.TYPE_NAME,
						params,
						profiles,
						enterpriseEvalService
								.retrieveScopeBy(ScopeRepository.UNSPECIFIED_SCOPE_NAME));

	}

	public void setCategories(TestDBDataCategories categories) {
		this.categories = categories;
	}

	public void setCompetenceRepository(
			ICompetenceRepository competenceRepository) {
		this.competenceRepository = competenceRepository;
	}

	public void setDimensions(TestDBDataDimensions dimensions) {
		this.dimensions = dimensions;
	}

	public void setEnterpriseEvalService(
			EnterpriseEvaluationService enterpriseEvalS) {
		this.enterpriseEvalService = enterpriseEvalS;
	}

}
