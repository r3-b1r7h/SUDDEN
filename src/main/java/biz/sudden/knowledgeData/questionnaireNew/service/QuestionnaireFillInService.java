package biz.sudden.knowledgeData.questionnaireNew.service;

import java.util.List;

import biz.sudden.knowledgeData.questionnaireNew.domain.Answer;
import biz.sudden.knowledgeData.questionnaireNew.domain.AnsweredQuestionnaire;
import biz.sudden.knowledgeData.questionnaireNew.domain.Question;

public interface QuestionnaireFillInService {

	public void create(AnsweredQuestionnaire answeredQuestionnaire);

	public void update(AnsweredQuestionnaire answeredQuestionnaire);

	public void delete(AnsweredQuestionnaire answeredQuestionnaire);

	public void update(Answer answer);

	public void create(Answer answer);

	public void delete(Answer answer);

	public Answer getAnswerFromQuestion(Question question);

	public List<AnsweredQuestionnaire> getAnsweredQuestionnaires();

	public List<Answer> getAnswersOf(AnsweredQuestionnaire answeredQuestionnaire);

}
