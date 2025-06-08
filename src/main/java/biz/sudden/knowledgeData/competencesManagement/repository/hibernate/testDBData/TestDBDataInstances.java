package biz.sudden.knowledgeData.competencesManagement.repository.hibernate.testDBData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.CVIInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.CompetenceInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.domain.DimensionInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;
import biz.sudden.knowledgeData.competencesManagement.domain.QuestionnaireInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;
import biz.sudden.knowledgeData.competencesManagement.domain.TickInstance;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickRepository;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;
import biz.sudden.userOrganizationManagement.organizationManagement.repository.IOrganizationRepository;

public class TestDBDataInstances {

	// Repositories
	private ICompetenceInstanceRepository competenceInstanceRepository;
	private ICompetenceRepository competenceRepository;
	private ICVIInstanceRepository cviInstanceRepository;
	private ICVIRepository cviRepository;
	private IDimensionInstanceRepository dimensionInstanceRepository;
	private IDimensionRepository dimensionRepository;
	private IQuestionnaireInstanceRepository questionnaireInstanceRepository;
	private IQuestionnaireRepository questionnaireRepository;
	private ITickInstanceRepository tickInstanceRepository;
	private ITickRepository tickRepository;
	private IOrganizationRepository organizationRepository;
	private EnterpriseEvaluationService enterpriseEvalService;

	private void buildCompetenceInstances(
			QuestionnaireInstance questionnaireInstance, Competence competence,
			Scope scope) {
		CompetenceInstance competenceInstance = new CompetenceInstance();
		competenceInstance.setCompetence(competence);
		competenceInstance.setDate(questionnaireInstance.getDate());
		competenceInstance.setOrganization(questionnaireInstance
				.getOrganization());
		competenceInstance.setWeight(1);

		Iterator<Dimension> iterator = competence.getDimensions().iterator();
		while (iterator.hasNext()) {
			Dimension dimension = iterator.next();
			buildDimensionInstances(questionnaireInstance, dimension,
					competenceInstance, scope);
		}

		competenceInstance.calculateValue();

		competenceInstanceRepository.addCompetenceInstance(competenceInstance);
		questionnaireInstance.getCompetenceInstances().add(competenceInstance);
	}

	private void buildCVIInstances(QuestionnaireInstance questionnaireInstance,
			CVI cvi, DimensionInstance dimensionInstance) {

		CVIInstance cviInstance = new CVIInstance();
		cviInstance.setCvi(cvi);
		cviInstance.setDate(questionnaireInstance.getDate());
		cviInstance.setOrganization(questionnaireInstance.getOrganization());
		cviInstance.setWeight(1);

		Iterator<Tick> iterator = cvi.getTicks().iterator();
		while (iterator.hasNext()) {
			Tick tick = iterator.next();
			cviInstance.getTicks()
					.add(
							buildTickInstances(questionnaireInstance, tick,
									cviInstance));
		}

		List<TickInstance> tickInstancesRadio = new ArrayList<TickInstance>();
		for (TickInstance tickInstance : cviInstance.getTicks()) {
			if (tickInstance.getTick().getType() == Tick.TICK_TYPE_RADIO) {
				tickInstancesRadio.add(tickInstance);
			}
		}
		if (tickInstancesRadio.size() > 0) {
			Random rnd = new Random();
			TickInstance tickInstanceRadio = tickInstancesRadio.get(rnd
					.nextInt(tickInstancesRadio.size()));
			tickInstanceRadio.setValue("SELECTED");
			tickInstanceRadio.setAutoCalcValue(tickInstanceRadio.getTick()
					.getTNumValue());
		}

		for (TickInstance tickInstance : cviInstance.getTicks()) {
			tickInstance.calculateValue();
			tickInstanceRepository.addTickInstance(tickInstance);

		}

		cviInstance.calculateValue();

		cviInstanceRepository.addCVIInstance(cviInstance);
		dimensionInstance.setCviInstance(cviInstance);
	}

	private void buildDimensionInstances(
			QuestionnaireInstance questionnaireInstance, Dimension dimension,
			CompetenceInstance parentInstance, Scope scope) {
		DimensionInstance dimensionInstance = new DimensionInstance();
		dimensionInstance.setDimension(dimension);
		dimensionInstance.setDate(questionnaireInstance.getDate());
		dimensionInstance.setOrganization(questionnaireInstance
				.getOrganization());
		dimensionInstance.setWeight(1);

		buildCVIInstances(questionnaireInstance, dimension.getCvi(),
				dimensionInstance);

		dimensionInstance.calculateValue();

		// for georgs stuff
		enterpriseEvalService.associateOccurence(dimensionInstance.getValue(),
				dimension, "Double", scope);

		dimensionInstanceRepository.addDimensionInstance(dimensionInstance);
		parentInstance.getDimensionInstances().add(dimensionInstance);
	}

	private void buildQuestionnaireInstance(
			QuestionnaireInstance questionnaireInstance,
			Questionnaire questionnaire) {
		Iterator<Competence> iterator = questionnaire.getCompetences()
				.iterator();
		Scope orgscope = enterpriseEvalService
				.retrieveScopeBy(questionnaireInstance.getOrganization()
						.getName());
		while (iterator.hasNext()) {
			Competence competence = iterator.next();
			buildCompetenceInstances(questionnaireInstance, competence,
					orgscope);
		}

		questionnaireInstance.calculateValue();
	}

	private TickInstance buildTickInstances(
			QuestionnaireInstance questionnaireInstance, Tick tick,
			CVIInstance parentInstance) {

		TickInstance tickInstance = new TickInstance();
		tickInstance.setTick(tick);
		tickInstance.setDate(questionnaireInstance.getDate());
		tickInstance.setOrganization(questionnaireInstance.getOrganization());
		tickInstance.setWeight(1);

		Random rnd = new Random();
		switch (tick.getType()) {
		case Tick.TICK_TYPE_CHECK:
			if (rnd.nextInt(2) == 0) {
				tickInstance.setValue("false");
			} else {
				tickInstance.setValue("true");
				tickInstance.setAutoCalcValue(tick.getTNumValue());
			}
			break;
		case Tick.TICK_TYPE_RADIO:
			tickInstance.setValue("NOT SELECTED");
			break;
		case Tick.TICK_TYPE_NUMBER:
			tickInstance.setValue(String.valueOf(rnd.nextInt(100)));
			tickInstance.setAutoCalcValue(Float
					.valueOf(tickInstance.getValue()));
			break;
		case Tick.TICK_TYPE_STRING:
			tickInstance.setValue("RANDOM STRING "
					+ String.valueOf(rnd.nextInt(100)));
			break;
		case Tick.TICK_AUTOVALUE_MACHINERY_TYPES:
		case Tick.TICK_AUTOVALUE_MATERIAL_NUMBER:
		case Tick.TICK_AUTOVALUE_MATERIAL_PROCESSING:
		case Tick.TICK_AUTOVALUE_PARTS_NUMBER:
			tickInstance.setValue(String.valueOf(tickInstance.getOrganization()
					.getCompanySMInfo(tick.getType())));
			tickInstance.setAutoCalcValue(tickInstance.getOrganization()
					.getCompanySMInfo(tick.getType()));
			break;
		}

		return tickInstance;
	}

	public ICompetenceInstanceRepository getCompetenceInstanceRepository() {
		return competenceInstanceRepository;
	}

	public ICompetenceRepository getCompetenceRepository() {
		return competenceRepository;
	}

	public ICVIInstanceRepository getCviInstanceRepository() {
		return cviInstanceRepository;
	}

	public ICVIRepository getCviRepository() {
		return cviRepository;
	}

	public IDimensionInstanceRepository getDimensionInstanceRepository() {
		return dimensionInstanceRepository;
	}

	public IDimensionRepository getDimensionRepository() {
		return dimensionRepository;
	}

	public IOrganizationRepository getOrganizationRepository() {
		return organizationRepository;
	}

	public IQuestionnaireInstanceRepository getQuestionnaireInstanceRepository() {
		return questionnaireInstanceRepository;
	}

	public IQuestionnaireRepository getQuestionnaireRepository() {
		return questionnaireRepository;
	}

	public ITickInstanceRepository getTickInstanceRepository() {
		return tickInstanceRepository;
	}

	public ITickRepository getTickRepository() {
		return tickRepository;
	}

	public void insertDBTestDataInstances() {
		List<Questionnaire> questionnaires = questionnaireRepository
				.getAllQuestionnaires();
		List<Organization> organizations = organizationRepository.retrieveAll();

		if (questionnaires.size() > 0 && organizations.size() > 0) {
			// FIXME : this is very time & resource consuming.
			// TODO we demonstrate the principle by only doing this for the
			// first 5 organisations.
			for (int i = 0; i < organizations.size() && i < 5; ++i) {
				Organization organization = organizations.get(i);
				for (Questionnaire questionnaire : questionnaires) {
					insertDBTestDataInstances(questionnaire, organization);
				}
			}
		}
	}

	private void insertDBTestDataInstances(Questionnaire questionnaire,
			Organization organization) {
		QuestionnaireInstance questionnaireInstance = new QuestionnaireInstance();
		questionnaireInstance.setQuestionnaire(questionnaire);
		questionnaireInstance.setOrganization(organization);
		questionnaireInstance.setWeight(1);
		questionnaireInstance.setDate(new java.util.Date());
		buildQuestionnaireInstance(questionnaireInstance, questionnaire);
		questionnaireInstanceRepository
				.addQuestionnaireInstance(questionnaireInstance);
	}

	public void setCompetenceInstanceRepository(
			ICompetenceInstanceRepository competenceInstanceRepository) {
		this.competenceInstanceRepository = competenceInstanceRepository;
	}

	public void setCompetenceRepository(
			ICompetenceRepository competenceRepository) {
		this.competenceRepository = competenceRepository;
	}

	public void setCviInstanceRepository(
			ICVIInstanceRepository cviInstanceRepository) {
		this.cviInstanceRepository = cviInstanceRepository;
	}

	public void setCviRepository(ICVIRepository cviRepository) {
		this.cviRepository = cviRepository;
	}

	public void setDimensionInstanceRepository(
			IDimensionInstanceRepository dimensionInstanceRepository) {
		this.dimensionInstanceRepository = dimensionInstanceRepository;
	}

	public void setDimensionRepository(IDimensionRepository dimensionRepository) {
		this.dimensionRepository = dimensionRepository;
	}

	public void setOrganizationRepository(
			IOrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	public void setQuestionnaireInstanceRepository(
			IQuestionnaireInstanceRepository questionnaireInstanceRepository) {
		this.questionnaireInstanceRepository = questionnaireInstanceRepository;
	}

	public void setQuestionnaireRepository(
			IQuestionnaireRepository questionnaireRepository) {
		this.questionnaireRepository = questionnaireRepository;
	}

	public void setTickInstanceRepository(
			ITickInstanceRepository tickInstanceRepository) {
		this.tickInstanceRepository = tickInstanceRepository;
	}

	public void setTickRepository(ITickRepository tickRepository) {
		this.tickRepository = tickRepository;
	}

	public void setEnterpriseEvalService(EnterpriseEvaluationService evalservice) {
		this.enterpriseEvalService = evalservice;
	}

}