package biz.sudden.knowledgeData.questionnaireNew.web.controller;

import java.util.LinkedList;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.util.JSFUtils;
import biz.sudden.knowledgeData.questionnaireNew.domain.Answer;
import biz.sudden.knowledgeData.questionnaireNew.domain.AnsweredQuestionnaire;
import biz.sudden.knowledgeData.questionnaireNew.domain.Question;
import biz.sudden.knowledgeData.questionnaireNew.domain.Questionnaire;
import biz.sudden.knowledgeData.questionnaireNew.domain.Tick;
import biz.sudden.knowledgeData.questionnaireNew.service.QuestionnaireFillInService;
import biz.sudden.knowledgeData.questionnaireNew.service.QuestionnaireService;
import biz.sudden.knowledgeData.questionnaireNew.service.TickService;

import com.icesoft.faces.component.ext.HtmlOutputText;
import com.icesoft.faces.component.ext.HtmlPanelGrid;

public class QuestionnaireFillInController {

	private Logger logger = Logger.getLogger(this.getClass());

	private List<Answer> answerList = new LinkedList<Answer>();
	private Question currentQuestion;

	// private Answer currentAnswer;
	private AnsweredQuestionnaire currentAnsweredQuestionnaire;
	private Questionnaire currentFillinQuestionnaire;

	private boolean firstQuestionReached = true;
	private boolean lastQuestionReached = false;

	private List<SelectItem> multipleChoiceItems;

	private String organisation;
	private String selectedMultipleChoiceItem;
	private String additionalMultiTextRadiobox;

	private List<String> selectedMultipleChoiceItems = new LinkedList<String>();

	private TickService tickService;
	private QuestionnaireService questionnaireService;
	private QuestionnaireFillInService fillinService;

	private String freeTextAnswer;

	private HtmlPanelGrid choiceInputTextPanelGrid = new HtmlPanelGrid();

	private ValueExpression choiceInputTextExpression;

	private Tick currentSelectedTick;

	private boolean additionalTextRadioboxSelected = false;
	private boolean additionalMultiTextRadioboxSelected = false;

	// TODO TF l�schen!
	private String additionalRadioboxText;

	// *************** Getter and Setter ******************* /

	public boolean isAdditionalMultiTextRadioboxSelected() {
		return additionalMultiTextRadioboxSelected;
	}

	public String getAdditionalMultiTextRadiobox() {
		return additionalMultiTextRadiobox;
	}

	public void setAdditionalMultiTextRadiobox(
			String additionalMultiTextRadiobox) {
		this.additionalMultiTextRadiobox = additionalMultiTextRadiobox;
	}

	public void setAdditionalMultiTextRadioboxSelected(
			boolean additionalMultiTextRadioboxSelected) {
		this.additionalMultiTextRadioboxSelected = additionalMultiTextRadioboxSelected;
	}

	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}

	public List<String> getSelectedMultipleChoiceItems() {
		return selectedMultipleChoiceItems;
	}

	public void setSelectedMultipleChoiceItems(
			List<String> selectedMultipleChoiceItems) {
		this.selectedMultipleChoiceItems = selectedMultipleChoiceItems;
	}

	public String getAdditionalRadioboxText() {
		return additionalRadioboxText;
	}

	public void setAdditionalRadioboxText(String additionalRadioboxText) {
		this.additionalRadioboxText = additionalRadioboxText;
	}

	public boolean isAdditionalTextRadioboxSelected() {
		return additionalTextRadioboxSelected;
	}

	public void setAdditionalTextRadioboxSelected(
			boolean additionalTextRadioboxSelected) {
		this.additionalTextRadioboxSelected = additionalTextRadioboxSelected;
	}

	public Tick getCurrentSelectedTick() {
		return currentSelectedTick;
	}

	public void setCurrentSelectedTick(Tick currentSelectedTick) {
		this.currentSelectedTick = currentSelectedTick;
	}

	public HtmlPanelGrid getChoiceInputTextPanelGrid() {
		return choiceInputTextPanelGrid;
	}

	public void setChoiceInputTextPanelGrid(
			HtmlPanelGrid choiceInputTextPanelGrid) {
		this.choiceInputTextPanelGrid = choiceInputTextPanelGrid;
	}

	public String getFreeTextAnswer() {
		return freeTextAnswer;
	}

	public void setFreeTextAnswer(String freeTextAnswer) {
		this.freeTextAnswer = freeTextAnswer;
	}

	public AnsweredQuestionnaire getCurrentAnsweredQuestionnaire() {
		return currentAnsweredQuestionnaire;
	}

	public void setCurrentAnsweredQuestionnaire(
			AnsweredQuestionnaire currentAnsweredQuestionnaire) {
		this.currentAnsweredQuestionnaire = currentAnsweredQuestionnaire;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public void setCurrentFillinQuestionnaire(
			Questionnaire currentFillinQuestionnaire) {
		this.currentFillinQuestionnaire = currentFillinQuestionnaire;

	}

	public void setFillinService(QuestionnaireFillInService fillinService) {
		this.fillinService = fillinService;
	}

	public void setFirstQuestionReached(boolean firstQuestionReached) {
		this.firstQuestionReached = firstQuestionReached;
	}

	public void setLastQuestionReached(boolean lastQuestionReached) {
		this.lastQuestionReached = lastQuestionReached;
	}

	public void setMultipleChoiceItems(List<SelectItem> multipleChoiceItems) {
		this.multipleChoiceItems = multipleChoiceItems;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public void setQuestionnaireService(
			QuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}

	public void setSelectedMultipleChoiceItem(String selectedMultipleChoiceItem) {
		this.selectedMultipleChoiceItem = selectedMultipleChoiceItem;
	}

	public void setTickService(TickService tickService) {
		this.tickService = tickService;
	}

	public List<AnsweredQuestionnaire> getAllAnsweredQuestionnaires() {
		return fillinService.getAnsweredQuestionnaires();
	}

	public List<Answer> getAnswerList() {
		return answerList;
	}

	public Questionnaire getCurrentFillinQuestionnaire() {
		return currentFillinQuestionnaire;
	}

	public QuestionnaireFillInService getFillinService() {
		return fillinService;
	}

	public List<SelectItem> getMultipleChoiceItems() {
		return multipleChoiceItems;
	}

	public String getOrganisation() {
		return organisation;
	}

	public QuestionnaireService getQuestionnaireService() {
		return questionnaireService;
	}

	public String getSelectedMultipleChoiceItem() {
		// return currentAnswer.getTick().getId().toString();
		return selectedMultipleChoiceItem;
	}

	public TickService getTickService() {
		return tickService;
	}

	// *************** END of Getter and Setter ******************* /

	public void fillInQuestionnaire(ActionEvent event) {
		UIParameter uiParam = (UIParameter) event.getComponent().findComponent(
				"fillinquestionnaire");
		currentFillinQuestionnaire = (Questionnaire) uiParam.getValue();
		JSFUtils.navigateTo("showSingleFillInQuestionnaire");
	}

	// public Answer getCurrentAnswer() {
	// if (currentAnswer == null) {
	// for (Question question : currentFillinQuestionnaire.getQuestionList()) {
	// Answer answer = fillinService.getAnswerFromQuestion(question);
	// if (answer == null || answer.getBelongsToAnsweredQuestionnaire().getId()
	// != currentAnsweredQuestionnaire.getId()) {
	// answer = new Answer();
	// answer.setDimension(question);
	// answer.setOptionalAnswerString("---");
	// answer.setBelongsToAnsweredQuestionnaire(currentAnsweredQuestionnaire);
	//
	// fillinService.create(answer);
	// }
	// answerList.add(answer);
	// }
	// currentAnswer = answerList.get(0);
	// try {
	// initializeMCItems();
	// setSelectedMultipleChoiceItem(currentAnswer.getTick().getId().toString());
	// } catch (NullPointerException ex) {
	// logger.debug(ex);
	// }
	// }
	// return currentAnswer;
	// }

	public void gotoNextQuestion() {
		gotoQuestion(currentFillinQuestionnaire.getQuestionList().indexOf(
				currentQuestion) + 1);
	}

	public void gotoPreviousQuestion() {
		gotoQuestion(currentFillinQuestionnaire.getQuestionList().indexOf(
				currentQuestion) - 1);
	}

	public void gotoFirstQuestion() {
		gotoQuestion(0);
	}

	public void gotoLastQuestion() {
		gotoQuestion(currentFillinQuestionnaire.getQuestionList().size() - 1);
	}

	public int getQuestionnaireSize() {
		return currentFillinQuestionnaire.getQuestionList().size();
	}

	public int getQuestionnairePosition() {
		return currentFillinQuestionnaire.getQuestionList().indexOf(
				currentQuestion) + 1;
	}

	private void gotoQuestion(int questionToGoTo) {

		int questionListSize = currentFillinQuestionnaire.getQuestionList()
				.size();
		List<Question> questionList = currentFillinQuestionnaire
				.getQuestionList();
		if (questionToGoTo >= questionListSize - 1)
			lastQuestionReached = true;
		else
			lastQuestionReached = false;
		if (questionToGoTo < 1)
			firstQuestionReached = true;
		else
			firstQuestionReached = false;
		if (questionToGoTo < questionList.size() && questionToGoTo >= 0) {

			// save current answer in any case
			saveAnswer();

			freeTextAnswer = "";
			// TODO GW: really?
			// setAdditionalRadioboxText("");
			currentQuestion = questionList.get(questionToGoTo);
			initializeMCItems();

			// initAdditionalTextRadioboxSelected();

			if (getCurrentQuestion().isOnlyFreeText()) {
				List<Answer> answerList = getCurrentAnsweredQuestionnaire()
						.getAnswerList();
				int indexOfCurrentQuestion = -1;
				int i = 0;
				for (Answer answer : answerList) {

					if (answer.getDimension().equals(currentQuestion)) {
						indexOfCurrentQuestion = i;
					}
					i++;
				}
				if (answerList.size() > 0) {

					if (indexOfCurrentQuestion >= 0) {
						freeTextAnswer = answerList.get(indexOfCurrentQuestion)
								.getOptionalAnswerString();
					}

				}
			}
		}
	}

	public void gotoQuestion(ActionEvent ae) {
		UIParameter uiParam = (UIParameter) ae.getComponent().findComponent(
				"thisquestion");
		// get instance of a concrete questionnaire filled out by a user or
		int x = currentFillinQuestionnaire.getQuestionList().indexOf(
				uiParam.getValue());
		gotoQuestion(x);
		// goto to this jspx page
		JSFUtils.navigateTo("startQuestionnaire");
	}

	public String viewAll() {
		gotoFirstQuestion();
		return "viewfilledinquestionnaire";
	}

	public String getAnswer() {

		Question currentQ = (Question) JSFUtils
				.getFacesContextCurrentInstance().getExternalContext()
				.getRequestMap().get("question");
		logger.debug("getAnswer for question: " + currentQ.getQuestion());

		for (Answer a : currentAnsweredQuestionnaire.getAnswerList()) {
			if (a.getDimension().equals(currentQ)) {
				StringBuffer result = new StringBuffer();
				for (Tick t : a.getAnswerTicks()) {
					if (t != null && !t.getTickText().equals("")) {
						result.append(t.getTickText());
						result.append("; ");
					}
				}
				result.append('"');
				result.append(a.getOptionalAnswerString());
				result.append('"');
				return result.toString();
			}
		}

		return "";
	}

	public void initializeMCItems() {

		getSelectedMultipleChoiceItems().clear();

		List<SelectItem> selectItems = new LinkedList<SelectItem>();
		choiceInputTextPanelGrid.getChildren().clear();
		Answer currentAnswer = null;

		setAdditionalMultiTextRadioboxSelected(false);
		setAdditionalTextRadioboxSelected(false);

		setAdditionalMultiTextRadiobox("");
		setAdditionalRadioboxText("");

		setFreeTextAnswer("");

		setCurrentSelectedTick(null);

		for (Tick tick : currentQuestion.getAvailableTicks()) {
			SelectItem item = new SelectItem();

			item.setLabel(tick.getTickText());
			item.setValue(tick.getId().toString());
			selectItems.add(item);
			if (tick.isFreeText()) {
				// HtmlInputText inputText = new HtmlInputText();
				// inputText.setId("choiceInputText" + tick.getId());
				// inputText.setStyle("margin:1px;padding:1px");

				// choiceInputTextPanelGrid.getChildren().add(inputText);

				for (Answer answer : getCurrentAnsweredQuestionnaire()
						.getAnswerList()) {
					if (answer.getDimension().getId().equals(
							currentQuestion.getId())) {
						for (Tick mytick : answer.getAnswerTicks()) {
							if (tick != null && mytick != null) {
								if (tick.getId().equals(mytick.getId())) {
									currentAnswer = answer;
									if (answer.getDimension()
											.isMultipleChoice()) {
										setAdditionalMultiTextRadiobox(currentAnswer
												.getOptionalAnswerString());
										setAdditionalMultiTextRadioboxSelected(true);
									} else {
										setAdditionalRadioboxText(currentAnswer
												.getOptionalAnswerString());
										setAdditionalTextRadioboxSelected(true);
									}
									setFreeTextAnswer(answer
											.getOptionalAnswerString());
									break;
								}
							}
						}
					}
				}
			} else {
				HtmlOutputText text = new HtmlOutputText();
				text.setValue("&nbsp;");
				text.setStyle("margin:0px");
				text.setEscape(false);
				// choiceInputTextPanelGrid.getChildren().add(text);
			}

		}

		for (Answer answer : getCurrentAnsweredQuestionnaire().getAnswerList()) {
			if (answer.getDimension().getId().equals(currentQuestion.getId())) {
				for (Tick tick : answer.getAnswerTicks()) {
					if (!getCurrentQuestion().isMultipleChoice()) {
						setCurrentSelectedTick(tick);
						if (getCurrentSelectedTick() == null) {
							setCurrentSelectedTick(currentQuestion
									.getFirstTick());
						}
						setSelectedMultipleChoiceItem(getCurrentSelectedTick()
								.getId().toString());
					} else {
						getSelectedMultipleChoiceItems().add(
								tick.getId().toString());
						setAdditionalMultiTextRadioboxSelected(tick
								.isFreeText());

					}
					// choiceInputTextExpression.setValue(JSFUtils.
					// getFacesContextCurrentInstance().getELContext(),
					// answer.getOptionalAnswerString());
				}
			}
		}

		setMultipleChoiceItems(selectItems);
		//		
		System.out.println(isAdditionalMultiTextRadioboxSelected());
		System.out.println(isAdditionalTextRadioboxSelected());
		System.out.println(getCurrentSelectedTick());

	}

	public boolean isFirstQuestionReached() {
		return firstQuestionReached;
	}

	public boolean isLastQuestionReached() {
		return lastQuestionReached;
	}

	public void openAnsweredQuestionnaire(ActionEvent event) {
		UIParameter uiParam = (UIParameter) event.getComponent().findComponent(
				"answeredquestionnaire");
		// get instance of a concrete questionnaire filled out by a user or
		// organisation
		currentAnsweredQuestionnaire = (AnsweredQuestionnaire) uiParam
				.getValue();

		// get the questionnaire on which the concrete questionnaire is based
		currentFillinQuestionnaire = currentAnsweredQuestionnaire
				.getQuestionnaire();

		// get all already answered Answers :-)
		answerList = fillinService.getAnswersOf(currentAnsweredQuestionnaire);

		// set current answer to the first element of list
		currentQuestion = currentFillinQuestionnaire.getQuestionList().get(0);

		freeTextAnswer = "";

		setAdditionalMultiTextRadiobox("");
		setAdditionalRadioboxText("");
		setSelectedMultipleChoiceItem("");

		if (getCurrentQuestion().isOnlyFreeText()) {
			// List<Answer> answerList = currentQuestion.getFirstTick().
			// getGivenAnswersFromQuestionnaireInstance
			// (getCurrentAnsweredQuestionnaire());

			List<Answer> answerList = getCurrentAnsweredQuestionnaire()
					.getAnswerList();
			if (answerList.size() > 0) {
				freeTextAnswer = answerList.get(0).getOptionalAnswerString();
			}
		}

		firstQuestionReached = true;

		// ***
		// try to initialize radiobuttons and the SelectItems
		initializeMCItems();
		initAdditionalTextRadioboxSelected();

		// ***

		// goto to this jspx page
		JSFUtils.navigateTo("startQuestionnaire");
	}

	public void deleteAnsweredQuestionnaire() {
		deleteAnsweredQuestionnaire(deleteAnsweredQ);
	}

	public void deleteAnsweredQuestionnaire(ActionEvent event) {
		if (deleteAnsweredQ != null) {
			deleteAnsweredQuestionnaire(deleteAnsweredQ);
		} else {
			UIParameter uiParam = (UIParameter) event.getComponent()
					.findComponent("deleteAquestionnaire");
			// get instance of a concrete questionnaire filled out by a user or
			// organisation
			deleteAnsweredQuestionnaire((AnsweredQuestionnaire) uiParam
					.getValue());
		}
	}

	public void deleteAnsweredQuestionnaire(AnsweredQuestionnaire answeredQ) {
		if (answeredQ != null) {
			fillinService.update(answeredQ);
			// get all already answered Answers :-)
			for (Answer a : fillinService.getAnswersOf(answeredQ)) {
				fillinService.delete(a);
			}
			fillinService.delete(answeredQ);
		}
		hideReallyDeletePopup();
	}

	private boolean showDelPopup = false;
	private AnsweredQuestionnaire deleteAnsweredQ = null;

	public void showReallyDeletePopup(ActionEvent event) {
		deleteAnsweredQ = (AnsweredQuestionnaire) ((UIParameter) event
				.getComponent().findComponent("deleteAquestionnaire"))
				.getValue();
		showReallyDeletePopup();
	}

	public void showReallyDeletePopup() {
		showDelPopup = true;
	}

	public void hideReallyDeletePopup() {
		showDelPopup = false;
		deleteAnsweredQ = null;
	}

	public boolean getReallyDeletePopup() {
		return showDelPopup;
	}

	/**
	 * save the Answer
	 */
	public void sendTextAnswer() {
		// if (currentAnswer.getTick() == null)
		// currentAnswer.setTick(currentAnswer.getDimension().getAvailableTicks()
		// .get(0));
		// currentAnswer.setOptionalAnswerString(currentAnswer.
		// getOptionalAnswerString());
		// currentAnswer.setBelongsToAnsweredQuestionnaire(
		// currentAnsweredQuestionnaire);
		//
		// fillinService.update(currentAnswer);
		// if (currentAnsweredQuestionnaire == null ||
		// currentAnsweredQuestionnaire.getId() == null) {
		// fillinService.create(currentAnsweredQuestionnaire);
		// } else {
		// fillinService.update(currentAnsweredQuestionnaire);
		// }
	}

	/**
	 * Create a new Instance of a questionnaire
	 * 
	 * @return
	 */

	public String startQuestionnaire() {
		currentAnsweredQuestionnaire = new AnsweredQuestionnaire();
		currentAnsweredQuestionnaire.setOrganisation(organisation);
		currentAnsweredQuestionnaire
				.setQuestionnaire(currentFillinQuestionnaire);
		fillinService.create(currentAnsweredQuestionnaire);
		answerList = new LinkedList<Answer>();
		// getCurrentAnswer();
		freeTextAnswer = "";
		return "showQuestionnaire";
	}

	public void initAdditionalTextRadioboxSelected() {

		if (!getCurrentQuestion().isMultipleChoice()) {

			if (getCurrentSelectedTick() != null) {
				setAdditionalTextRadioboxSelected(getCurrentSelectedTick()
						.isFreeText());
			} else {
				setAdditionalTextRadioboxSelected(getCurrentQuestion()
						.getFirstTick().isFreeText());
			}
		}
	}

	public void initAdditionalMultiTextRadioboxSelected(
			List<String> selectedItems) {

		setAdditionalMultiTextRadioboxSelected(false);

		setAdditionalMultiTextRadiobox("");
		setAdditionalRadioboxText("");
		setFreeTextAnswer("");

		if (getCurrentQuestion().isMultipleChoice()) {

			for (String selectedItem : selectedItems) {

				long tickId = Long.parseLong(selectedItem);
				Tick tick = tickService.getTickWithId(tickId);
				if (tick.isFreeText()) {
					setAdditionalMultiTextRadioboxSelected(true);
					break;
				}
			}

		}
	}

	public void valueChangeMultipleChoice(ValueChangeEvent event) {

		List<String> selectedItems = (List<String>) event.getNewValue();

		initAdditionalMultiTextRadioboxSelected(selectedItems);

	}

	public void valueChangeSingleChoice(ValueChangeEvent event) {
		// try {
		Long myLong = Long.valueOf((String) event.getNewValue());
		Tick tick = tickService.getTickWithId(myLong);
		setCurrentSelectedTick(tick);

		initAdditionalTextRadioboxSelected();

		//
		// // TODO TF: outch
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
	}

	public void saveAnswer() {

		// choiceInputTextExpression.setValue(JSFUtils.
		// getFacesContextCurrentInstance().getELContext(), "");

		String answerString = getFreeTextAnswer();

		boolean questionAlreadyAnswered = false;
		Answer givenAnswer = null;

		// TODO TF: als answerString eine Optionsfrage beantwortet verwenden ...
		if (answerString == null || answerString.equals("")) {
			if (currentQuestion.isMultipleChoice()) {
				answerString = getAdditionalMultiTextRadiobox();
			} else {
				answerString = getAdditionalRadioboxText();
			}

		}

		// List<Answer> givenAnswers =
		// currentQuestion.getFirstTick().getGivenAnswersFromQuestionnaireInstance
		// (getCurrentAnsweredQuestionnaire());

		// Tick foundTick = null;

		for (Answer answer : getCurrentAnsweredQuestionnaire().getAnswerList()) {
			if (answer.getDimension().getId().equals(
					getCurrentQuestion().getId())) {
				questionAlreadyAnswered = true;
				givenAnswer = answer;
				break;
			}
		}
		//
		// for (Tick tick : currentQuestion.getAvailableTicks()) {
		// System.out.println(tick.getTickText());
		// if (tick.getGivenAnswersFromQuestionnaireInstance(
		// getCurrentAnsweredQuestionnaire()).size() > 0) {
		//				
		// questionAlreadyAnswered = true;
		// givenAnswer = tick.getGivenAnswersFromQuestionnaireInstance(
		// getCurrentAnsweredQuestionnaire()).get(0);
		// tick.getGivenAnswers().remove(givenAnswer);
		// tickService.updateTick(tick);
		// // tick.getGivenAnswersFromQuestionnaireInstance(
		// // getCurrentAnsweredQuestionnaire()).remove(0);
		//				
		// }
		// }
		//
		// System.out.println(questionAlreadyAnswered);

		if (questionAlreadyAnswered) {

			givenAnswer.getAnswerTicks().clear();

			// givenAnswer.setOptionalAnswerString(additionalRadioboxText);
			givenAnswer.setOptionalAnswerString(answerString);

			if (currentQuestion.isOnlyFreeText()) {
				givenAnswer.setOptionalAnswerString(getFreeTextAnswer());
				givenAnswer.getAnswerTicks().add(getCurrentSelectedTick());
			} else {

				if (!currentQuestion.isMultipleChoice()) {
					if (getCurrentSelectedTick() != null) {
						givenAnswer.getAnswerTicks().add(
								getCurrentSelectedTick());
						if (!getCurrentSelectedTick().isFreeText()) {
							givenAnswer.setOptionalAnswerString("");
						} else {
							givenAnswer
									.setOptionalAnswerString(getAdditionalRadioboxText());
						}
					}

				} else {
					for (String currentSelectedItem : getSelectedMultipleChoiceItems()) {
						Long tickId = Long.parseLong(currentSelectedItem);
						Tick tick = tickService.getTickWithId(tickId);
						givenAnswer.getAnswerTicks().add(tick);
					}
					givenAnswer
							.setOptionalAnswerString(getAdditionalMultiTextRadiobox());
				}

			}

			fillinService.update(givenAnswer);

			// setAdditionalRadioboxText("");
			// givenAnswer.setOptionalAnswerString(answerString);
			// fillinService.update(givenAnswer);
			// System.out.println(getCurrentSelectedTick().getGivenAnswers().
			// contains(givenAnswer));
			// getCurrentSelectedTick().getGivenAnswers().add(givenAnswer);
			// tickService.updateTick(getCurrentSelectedTick());
			// //tickService.updateTick(foundTick);

		} else {

			Answer answer = new Answer();

			answer
					.setBelongsToAnsweredQuestionnaire(getCurrentAnsweredQuestionnaire());
			getCurrentAnsweredQuestionnaire().getAnswerList().add(answer);

			answer.setDimension(currentQuestion);
			answer.setOptionalAnswerString(answerString);

			if (currentQuestion.isOnlyFreeText()) {
				answer.getAnswerTicks().add(currentQuestion.getFirstTick());
				// currentQuestion.getFirstTick().getGivenAnswers().add(answer);
			} else if (!currentQuestion.isMultipleChoice()) {
				if (getCurrentSelectedTick() != null) {
					answer.getAnswerTicks().add(getCurrentSelectedTick());
				}
			} else {
				for (String currentSelectedItem : getSelectedMultipleChoiceItems()) {
					long tickId = Long.parseLong(currentSelectedItem);
					Tick tick = tickService.getTickWithId(tickId);
					answer.getAnswerTicks().add(tick);
				}
				// getCurrentSelectedTick().getGivenAnswers().add(answer);
				// for (Tick tick : currentQuestion.getAvailableTicks()) {
				// if (tick.getId().equals(getCurrentSelectedTick().getId())) {
				// // tick.getGivenAnswers().add(answer);
				// tickService.updateTick(getCurrentSelectedTick());
				// }
				// }

			}

			fillinService.create(answer);

			// currentQuestion.getFirstTick().getGivenAnswers().add(answer);
			// questionnaireService.updateQuestion(currentQuestion);7

		}

		freeTextAnswer = "";

	}

	// public Answer getAnswerOfSingleText() {
	//		
	// List<Answer> givenAnswers =
	// currentQuestion.getFirstTick().getGivenAnswersFromQuestionnaireInstance
	// (getCurrentAnsweredQuestionnaire());
	//		
	// if (givenAnswers.size() == 0) {
	// Answer answer = new Answer();
	// answer.setBelongsToAnsweredQuestionnaire(getCurrentAnsweredQuestionnaire()
	// );
	// answer.setDimension(currentQuestion);
	// answer.setOptionalAnswerString("");
	// givenAnswers.add(answer);
	// currentQuestion.getFirstTick().getGivenAnswers().add(answer);
	// fillinService.create(answer);
	// questionnaireService.updateQuestion(currentQuestion);
	// fillinService.update(getCurrentAnsweredQuestionnaire());
	// }
	//		
	// return givenAnswers.get(0);
	// }
	//	
	//	
	// //nicht gut jedesmal werden neue objekte angelegt ...
	// public void setAnswerOfSingleText(Answer answer) {
	//		
	// List<Answer> givenAnswers =
	// currentQuestion.getFirstTick().getGivenAnswersFromQuestionnaireInstance
	// (getCurrentAnsweredQuestionnaire());
	// if (givenAnswers.size() == 0) {
	// currentQuestion.getFirstTick().getGivenAnswers().add(answer);
	// answer.setBelongsToAnsweredQuestionnaire(getCurrentAnsweredQuestionnaire()
	// );
	// // fillinService.create(answer);
	// // questionnaireService.updateQuestion(currentQuestion);
	// } else {
	// currentQuestion.getFirstTick().getGivenAnswers().set(0, answer);
	// questionnaireService.updateQuestion(currentQuestion);
	// }
	//		
	//		
	//		
	//		
	//
	// }

}
