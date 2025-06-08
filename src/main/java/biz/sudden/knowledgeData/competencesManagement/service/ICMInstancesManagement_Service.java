package biz.sudden.knowledgeData.competencesManagement.service;

import java.util.List;

import biz.sudden.evaluation.performanceMeasurement.service.EnterpriseEvaluationService;
import biz.sudden.knowledgeData.competencesManagement.domain.CVIInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.CompetenceInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.DimensionInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.QuestionnaireInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.TickInstance;
import biz.sudden.knowledgeData.competencesManagement.repository.ICVIInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ICompetenceInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.IQuestionnaireInstanceRepository;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickInstanceRepository;
import biz.sudden.knowledgeData.serviceManagement.service.ProductMaterialSupportingServices_Service;

/**
 * 
 * CMCompetencesManagement_Service - This interface defines Methods for
 * Competences and its entities
 * 
 * @author Victor Blazquez
 * 
 */
public interface ICMInstancesManagement_Service extends
		ICMServiceCategoryBaseClass {

	/*
	 * Tick Instance Entities - Access to the entities should be done via the
	 * service, not the repository
	 */

	public long addTickInstance(TickInstance tickInstance);

	public List<TickInstance> retrieveAllTickInstances();

	public List<TickInstance> retrieveAllTickInstancesByOrganization(
			long organizationId);

	public ITickInstanceRepository getTickInstanceRepository();

	/*
	 * CVI Instance Entities - Access to the entities should be done via the
	 * service, not the repository
	 */

	public long addCVIInstance(CVIInstance cviInstance);

	public List<CVIInstance> retrieveAllCVIInstances();

	public List<CVIInstance> retrieveAllCVIInstancesByOrganization(
			long organizationId);

	public ICVIInstanceRepository getCviInstanceRepository();

	/*
	 * Dimension Instance Entities - Access to the entities should be done via
	 * the service, not the repository
	 */

	public long addDimensionInstance(DimensionInstance dimensionInstance);

	public List<DimensionInstance> retrieveAllDimensionInstances();

	public List<DimensionInstance> retrieveAllDimensionInstancesByOrganization(
			long organizationId);

	public DimensionInstance retrieveDimensionInstanceByOrganization(
			long organizationId, long dimensionId);

	public DimensionInstance retrieveDimensionInstanceByName(
			long organizationId, String name);

	public IDimensionInstanceRepository getDimensionInstanceRepository();

	/*
	 * Competence Instance Entities - Access to the entities should be done via
	 * the service, not the repository
	 */

	public long addCompetenceInstance(CompetenceInstance competenceInstance);

	public List<CompetenceInstance> retrieveAllCompetenceInstances();

	public List<CompetenceInstance> retrieveAllCompetenceInstancesByOrganization(
			long organizationId);

	public CompetenceInstance retrieveCompetenceInstanceByOrganization(
			long organizationId, long competenceId);

	public ICompetenceInstanceRepository getCompetenceInstanceRepository();

	/*
	 * Questionnaire Instance Entities - Access to the entities should be done
	 * via the service, not the repository
	 */

	public long addQuestionnaireInstance(
			QuestionnaireInstance questionnaireInstance);

	public List<QuestionnaireInstance> retrieveAllQuestionnaireInstances();

	public List<QuestionnaireInstance> retrieveAllQuestionnaireInstancesByOrganization(
			long organizationId);

	public IQuestionnaireInstanceRepository getQuestionnaireInstanceRepository();

	/* Service level methods */

	public void removeAllInstances();

	public ICMCompetencesManagement_Service getCmCompetencesManagement_Service();

	public ICMQuestionnairesManagement_Service getCmQuestionnaireManagement_Service();

	public void setCmCompetencesManagement_Service(
			ICMCompetencesManagement_Service cmCompetencesManagement_Service);

	public void setCmQuestionnaireManagement_Service(
			ICMQuestionnairesManagement_Service cmQuestionnaireManagement_Service);

	public void setEnterpriseEvalService(
			EnterpriseEvaluationService enterpriseEvalService);

	public EnterpriseEvaluationService getEnterpriseEvalService();

	public void setSmService(ProductMaterialSupportingServices_Service smService);

	public ProductMaterialSupportingServices_Service getSmService();

}
