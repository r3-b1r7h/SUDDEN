package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.competencesManagement.domain.QuestionnaireInstance;

public interface IQuestionnaireInstanceRepository extends
		GenericRepository<QuestionnaireInstance, Long> {

	public long addQuestionnaireInstance(
			QuestionnaireInstance questionnaireInstance);

	public List<QuestionnaireInstance> getAllQuestionnaireInstances();

	public List<QuestionnaireInstance> getQuestionnaireInstancesByOrganizationId(
			long organizationId);

	public void removeAllQuestionnaireInstances();

}
