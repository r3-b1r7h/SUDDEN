package biz.sudden.knowledgeData.questionnaireNew.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity(name = "questionnaireNew.Tick")
public class Tick implements Connectable, Cloneable {

	private Long id;
	private String tickText;
	private int tickValue;
	private boolean freeText = false;
	private TickTemplate belongsToTickTemplate;

	// private List<Answer> givenAnswers;

	// @OneToMany
	// @LazyCollection(LazyCollectionOption.FALSE)
	// public List<Answer> getGivenAnswers() {
	// return givenAnswers;
	// }

	// @Transient
	// public List<Answer>
	// getGivenAnswersFromQuestionnaireInstance(AnsweredQuestionnaire
	// questionnaireInstance) {
	// List<Answer> answerList = new LinkedList<Answer>();
	// for (Answer answer : getGivenAnswers()) {
	// System.out.println("Answer ID "+answer.getId());
	// if
	// (answer.getBelongsToAnsweredQuestionnaire().equals(questionnaireInstance))
	// {
	// answerList.add(answer);
	// }
	// }
	// return answerList;
	// }
	//
	// public void setGivenAnswers(List<Answer> givenAnswers) {
	// this.givenAnswers = givenAnswers;
	// }

	@ManyToOne(fetch = FetchType.EAGER)
	public TickTemplate getBelongsToTickTemplate() {
		return belongsToTickTemplate;
	}

	public void setBelongsToTickTemplate(TickTemplate belongsToTickTemplate) {
		this.belongsToTickTemplate = belongsToTickTemplate;
	}

	public boolean isFreeText() {
		return freeText;
	}

	public void setFreeText(boolean freeText) {
		this.freeText = freeText;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTickText() {
		return tickText;
	}

	public void setTickText(String tickText) {
		this.tickText = tickText;
	}

	public int getTickValue() {
		return tickValue;
	}

	public void setTickValue(int tickValue) {
		this.tickValue = tickValue;
	}

	@Override
	public Tick clone() throws CloneNotSupportedException {
		Tick newTick = (Tick) super.clone();
		newTick.setBelongsToTickTemplate(null);
		newTick.setId(null);
		return newTick;
	}

}
