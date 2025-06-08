package biz.sudden.knowledgeData.questionnaireNew.service;

import java.util.List;

import biz.sudden.knowledgeData.questionnaireNew.domain.Question;
import biz.sudden.knowledgeData.questionnaireNew.domain.Questionnaire;
import biz.sudden.knowledgeData.questionnaireNew.domain.Tick;

public interface QuestionnaireService {

	public void createQuestionnaire(String name);

	public void addQuestionToQuestionnaire(Questionnaire questionnaire,
			String questionName);

	public List<Questionnaire> retrieveAllQuestionnaires();

	public void updateQuestion(Question question);

	public void addTickToQuestion(Question question, Tick tick);

}
