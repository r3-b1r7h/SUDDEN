package biz.sudden.knowledgeData.questionnaireNew.web.controller;

import java.util.LinkedList;
import java.util.List;

import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.util.JSFUtils;
import biz.sudden.knowledgeData.questionnaireNew.domain.Question;
import biz.sudden.knowledgeData.questionnaireNew.domain.Questionnaire;
import biz.sudden.knowledgeData.questionnaireNew.domain.Tick;
import biz.sudden.knowledgeData.questionnaireNew.domain.TickTemplate;
import biz.sudden.knowledgeData.questionnaireNew.service.QuestionnaireService;
import biz.sudden.knowledgeData.questionnaireNew.service.TickService;

public class QuestionnaireController {

	private enum QuestionNature {
		FREETEXT("Freitext"), MULTIPLECHOICE("Multiple Choice"), SINGLECHOICE(
				"Single Choice");

		private String name;

		QuestionNature(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return name;
		}
	}

	private Logger logger = Logger.getLogger(this.getClass());

	private QuestionNature questionNature = QuestionNature.FREETEXT;

	private QuestionnaireService questionnaireService;
	private TickService tickService;
	private String questionnaireName;
	private String questionName;
	private Questionnaire currentQuestionnaire;
	private Question currentQuestion;
	private String tickText;
	private boolean additionalFreeText;

	public List<SelectItem> getAvailableTickTemplates() {
		List<SelectItem> selectItems = new LinkedList<SelectItem>();
		for (TickTemplate tickTemplate : tickService.retrieveAllTickTemplates()) {
			SelectItem item = new SelectItem();
			item.setDescription(tickTemplate.getName());
			item.setLabel(tickTemplate.getName());
			item.setValue(tickTemplate.getId());
			selectItems.add(item);
		}
		return selectItems;
	}

	private Long selectedTemplateID;

	public Long getSelectedTemplateID() {
		if (selectedTemplateID == null) {
			// FIXME TF Outch !!
			this.selectedTemplateID = (Long) getAvailableTickTemplates().get(0)
					.getValue();
		}
		return selectedTemplateID;
	}

	public void setSelectedTemplateID(Long selectedTemplateID) {
		this.selectedTemplateID = selectedTemplateID;
	}

	public boolean isAdditionalFreeText() {
		return additionalFreeText;
	}

	public void setAdditionalFreeText(boolean additionalFreeText) {
		this.additionalFreeText = additionalFreeText;
	}

	public String getTickText() {
		return tickText;
	}

	public void setTickText(String tickText) {
		this.tickText = tickText;
	}

	public TickService getTickService() {
		return tickService;
	}

	public void setTickService(TickService tickService) {
		this.tickService = tickService;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public Questionnaire getCurrentQuestionnaire() {
		return currentQuestionnaire;
	}

	public void setCurrentQuestionnaire(Questionnaire currentQuestionnaire) {
		this.currentQuestionnaire = currentQuestionnaire;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public QuestionnaireService getQuestionnaireService() {
		return questionnaireService;
	}

	public void setQuestionnaireService(
			QuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}

	public void createQuestionnaire() {
		questionnaireService.createQuestionnaire(questionnaireName);
		questionnaireName = "";
	}

	public void createQuestion() {
		questionnaireService.addQuestionToQuestionnaire(currentQuestionnaire,
				questionName);
		questionName = "";
	}

	public List<Questionnaire> getAllQuestionnaires() {
		return questionnaireService.retrieveAllQuestionnaires();
	}

	public void openQuestionnaire(ActionEvent event) {
		UIParameter uiParam = (UIParameter) event.getComponent().findComponent(
				"thisquestionnaire");
		logger.debug(uiParam.getValue());
		currentQuestionnaire = (Questionnaire) uiParam.getValue();
		JSFUtils.navigateTo("showSingleQuestionnaire");
	}

	public void openQuestion(ActionEvent event) {
		UIParameter uiParam = (UIParameter) event.getComponent().findComponent(
				"thisquestion");
		logger.debug(uiParam.getValue());
		currentQuestion = (Question) uiParam.getValue();
		JSFUtils.navigateTo("showQuestion");
	}

	public void selectOneMenuChanged(ValueChangeEvent event) {
		logger.debug(event);
		String natureOfQuestionString = (String) event.getNewValue();
		if ("freitext".equals(natureOfQuestionString)) {
			questionNature = QuestionNature.FREETEXT;
		} else if ("multiplechoice".equals(natureOfQuestionString)) {
			questionNature = QuestionNature.MULTIPLECHOICE;
		} else if ("singlechoice".equals(natureOfQuestionString)) {
			questionNature = QuestionNature.SINGLECHOICE;
		}

	}

	public QuestionNature getQuestionNature() {

		if (currentQuestion.getAvailableTicks().size() > 0) {

			if (currentQuestion.isOnlyFreeText()) {
				questionNature = QuestionNature.FREETEXT;
			} else {
				if (currentQuestion.isMultipleChoice()) {
					questionNature = QuestionNature.MULTIPLECHOICE;
				} else {
					questionNature = QuestionNature.SINGLECHOICE;
				}
			}

		}

		return questionNature;
	}

	public boolean isFreeText() {
		return questionNature.equals(QuestionNature.FREETEXT);
	}

	public boolean isMultipleChoice() {
		return questionNature.equals(QuestionNature.MULTIPLECHOICE);
	}

	public boolean isSingleChoice() {
		return questionNature.equals(QuestionNature.SINGLECHOICE);
	}

	public void addTick() {
		if (questionNature.equals(QuestionNature.FREETEXT)) {
			Tick tick = new Tick();
			tick.setFreeText(true);
			tick.setTickText("");
			currentQuestion.setOnlyFreeText(true);
			questionnaireService.addTickToQuestion(currentQuestion, tick);
		}

		if (questionNature.equals(QuestionNature.MULTIPLECHOICE)) {
			Tick tick = new Tick();
			tick.setFreeText(true);
			tick.setTickText(getTickText());
			currentQuestion.setMultipleChoice(true);
			tick.setFreeText(isAdditionalFreeText());
			questionnaireService.addTickToQuestion(currentQuestion, tick);
		}
		if (questionNature.equals(QuestionNature.SINGLECHOICE)) {
			Tick tick = new Tick();
			tick.setFreeText(true);
			tick.setTickText(getTickText());
			tick.setFreeText(isAdditionalFreeText());
			currentQuestion.setMultipleChoice(false);
			questionnaireService.addTickToQuestion(currentQuestion, tick);
		}
		setTickText("");

	}

	public void deleteTick(ActionEvent event) {
		UIParameter uiParam = (UIParameter) event.getComponent().findComponent(
				"thistick");
		Tick tickToDelete = (Tick) uiParam.getValue();
		currentQuestion.getAvailableTicks().remove(tickToDelete);
		currentQuestion.setOnlyFreeText(false);
		if (currentQuestion.getAvailableTicks().size() == 0) {
			questionNature = QuestionNature.FREETEXT;
		}
		questionnaireService.updateQuestion(currentQuestion);
	}

	public void addTicksFromTemplate() {
		logger.debug(selectedTemplateID);
		TickTemplate selectedTickTemplate = tickService
				.getTickTemplateWithID(selectedTemplateID);
		currentQuestion.setMultipleChoice(selectedTickTemplate
				.isMultipleChoice());

		for (Tick currentTick : tickService
				.getTicksOfTickTemplate(selectedTickTemplate)) {
			try {
				Tick newTick = currentTick.clone();
				tickService.createTick(newTick);
				currentQuestion.getAvailableTicks().add(newTick);
			} catch (CloneNotSupportedException ex) {
				throw new InternalError("Tick should be cloneable!");
			}
		}

		questionnaireService.updateQuestion(currentQuestion);

	}

	public void addTicksFromTemplateToSelectedQuestions() {
		TickTemplate selectedTickTemplate = tickService
				.getTickTemplateWithID(selectedTemplateID);

		for (Question myQuestion : currentQuestionnaire.getQuestionList()) {
			if (myQuestion.isSelected()) {
				logger.debug(myQuestion.getQuestion());
				myQuestion.setMultipleChoice(selectedTickTemplate
						.isMultipleChoice());

				for (Tick currentTick : tickService
						.getTicksOfTickTemplate(selectedTickTemplate)) {
					try {
						Tick newTick = currentTick.clone();
						tickService.createTick(newTick);
						myQuestion.getAvailableTicks().add(newTick);
					} catch (CloneNotSupportedException ex) {
						throw new InternalError("Tick should be cloneable!");
					}
				}

				questionnaireService.updateQuestion(myQuestion);
			}
		}

	}

	public void additionalFreeTextChanged(ValueChangeEvent event) {
		UIParameter uiParam = (UIParameter) event.getComponent().findComponent(
				"thistick");
		logger.debug(uiParam.getValue());
		Tick thisTick = (Tick) uiParam.getValue();
		thisTick.setFreeText((Boolean) event.getNewValue());
		tickService.updateTick(thisTick);
	}

	public void tickTextChanged(ValueChangeEvent event) {
		UIParameter uiParam = (UIParameter) event.getComponent().findComponent(
				"thistick");
		logger.debug(uiParam.getValue());
		Tick thisTick = (Tick) uiParam.getValue();
		thisTick.setTickText(event.getNewValue().toString());
		tickService.updateTick(thisTick);
	}

	public void questionChanged(ValueChangeEvent event) {
		logger.debug("Current Q: " + getCurrentQuestion().getQuestion());
		logger.debug("New Q: " + event.getNewValue().toString());
		getCurrentQuestion().setQuestion(event.getNewValue().toString());
		questionnaireService.updateQuestion(getCurrentQuestion());
	}

	public void selectedChangeListener(ValueChangeEvent event) {
		UIParameter uiParam = (UIParameter) event.getComponent().findComponent(
				"selectedquestion");
		Question thisQuestion = (Question) uiParam.getValue();
		thisQuestion.setSelected((Boolean) event.getNewValue());
		questionnaireService.updateQuestion(thisQuestion);
	}

}
