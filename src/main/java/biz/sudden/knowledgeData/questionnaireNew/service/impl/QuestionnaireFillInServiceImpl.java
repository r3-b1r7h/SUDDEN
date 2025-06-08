package biz.sudden.knowledgeData.questionnaireNew.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.knowledgeData.questionnaireNew.domain.Answer;
import biz.sudden.knowledgeData.questionnaireNew.domain.AnsweredQuestionnaire;
import biz.sudden.knowledgeData.questionnaireNew.domain.Question;
import biz.sudden.knowledgeData.questionnaireNew.service.QuestionnaireFillInService;

public class QuestionnaireFillInServiceImpl implements
		QuestionnaireFillInService {

	private Logger logger = Logger.getLogger(this.getClass());

	private SuddenGenericRepository genericRepository;

	public SuddenGenericRepository getGenericRepository() {
		return genericRepository;
	}

	public void setGenericRepository(SuddenGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	@Override
	public void create(AnsweredQuestionnaire answeredQuestionnaire) {
		genericRepository.create(answeredQuestionnaire);
	}

	@Override
	public void create(Answer answer) {
		// TODO Auto-generated method stub
		genericRepository.create(answer);
	}

	@Override
	public void update(AnsweredQuestionnaire answeredQuestionnaire) {
		genericRepository.update(answeredQuestionnaire);
	}

	@Override
	public void update(Answer answer) {
		genericRepository.update(answer);
	}

	@Override
	public void delete(AnsweredQuestionnaire answeredQuestionnaire) {
		genericRepository.delete(answeredQuestionnaire);
	}

	@Override
	public void delete(Answer answer) {
		genericRepository.delete(answer);
	}

	public Answer getAnswerFromQuestion(Question question) {
		for (Answer answer : genericRepository.retrieveAllByType(Answer.class)) {
			if (answer.getDimension().getId().equals(question.getId())) {
				return answer;
			}
		}
		return null;
	}

	@Override
	public List<AnsweredQuestionnaire> getAnsweredQuestionnaires() {
		// TODO Auto-generated method stub
		return genericRepository.retrieveAllByType(AnsweredQuestionnaire.class);
	}

	@Override
	public List<Answer> getAnswersOf(AnsweredQuestionnaire answeredQuestionnaire) {
		List<Answer> answerList = new LinkedList<Answer>();
		for (Answer answer : genericRepository.retrieveAllByType(Answer.class)) {
			if (!answerList.contains(answer)
					&& answer.getBelongsToAnsweredQuestionnaire().getId()
							.equals(answeredQuestionnaire.getId())) {
				answerList.add(answer);
			}
		}
		return answerList;
	}

}
