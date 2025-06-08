package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.CompetenceInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Questionnaire;

public interface IQuestionnaireInstance extends ICMInstanceBaseClass {

	public List<CompetenceInstance> getCompetenceInstances();

	public Questionnaire getQuestionnaire();

	public void setCompetenceInstances(
			List<CompetenceInstance> competenceInstances);

	public void setQuestionnaire(Questionnaire questionnaire);

}
