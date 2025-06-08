package biz.sudden.knowledgeData.questionnaireNew.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.knowledgeData.questionnaireNew.domain.Question;
import biz.sudden.knowledgeData.questionnaireNew.domain.Questionnaire;
import biz.sudden.knowledgeData.questionnaireNew.domain.Tick;
import biz.sudden.knowledgeData.questionnaireNew.service.QuestionnaireService;

public class QuestionnaireServiceImpl implements QuestionnaireService {

	private Logger logger = Logger.getLogger(this.getClass());

	private SuddenGenericRepository genericRepository;

	public SuddenGenericRepository getGenericRepository() {
		return genericRepository;
	}

	public void setGenericRepository(SuddenGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	public void createQuestionnaire(String name) {
		Questionnaire questionnaire = new Questionnaire(name);
		genericRepository.create(questionnaire);
	}

	public void addQuestionToQuestionnaire(Questionnaire questionnaire,
			String questionString) {
		Question question = new Question();
		question.setQuestion(questionString);
		genericRepository.create(question);
		questionnaire.getQuestionList().add(question);
		genericRepository.update(questionnaire);
	}

	@Override
	public List<Questionnaire> retrieveAllQuestionnaires() {
		return genericRepository.retrieveAllByType(Questionnaire.class);
	}

	public void updateQuestion(Question question) {
		genericRepository.update(question);
	}

	public void addTickToQuestion(Question question, Tick tick) {

		if (question.getAvailableTicks().size() > 0
				&& question.isOnlyFreeText()) {
			logger
					.error("Only one FREETEXT tick can be attached to a question");
			return;
		}

		genericRepository.create(tick);
		question.getAvailableTicks().add(tick);
		genericRepository.update(question);
	}

}
