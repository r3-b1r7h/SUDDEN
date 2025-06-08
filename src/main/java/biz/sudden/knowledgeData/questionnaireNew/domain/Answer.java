package biz.sudden.knowledgeData.questionnaireNew.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity(name = "questionnaireNew.Answer")
public class Answer implements Connectable, Cloneable {

	private Long id;
	private AnsweredQuestionnaire belongsToAnsweredQuestionnaire;
	private Question dimension;
	private List<Tick> answerTicks = new LinkedList<Tick>();
	private String optionalAnswerString = "";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	public AnsweredQuestionnaire getBelongsToAnsweredQuestionnaire() {
		return belongsToAnsweredQuestionnaire;
	}

	public void setBelongsToAnsweredQuestionnaire(
			AnsweredQuestionnaire belongsToAnsweredQuestionnaire) {
		this.belongsToAnsweredQuestionnaire = belongsToAnsweredQuestionnaire;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	public Question getDimension() {
		return dimension;
	}

	public void setDimension(Question dimension) {
		this.dimension = dimension;
	}

	// @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
	// CascadeType.PERSIST, CascadeType.REFRESH })
	// public Tick getTick() {
	// return tick;
	// }
	//
	// public void setTick(Tick tick) {
	// this.tick = tick;
	// }

	public String getOptionalAnswerString() {
		return optionalAnswerString;
	}

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<Tick> getAnswerTicks() {
		return answerTicks;
	}

	public void setAnswerTicks(List<Tick> answerTicks) {
		this.answerTicks = answerTicks;
	}

	public void setOptionalAnswerString(String optionalAnswerString) {
		this.optionalAnswerString = optionalAnswerString;
	}

	@Override
	public Answer clone() {
		try {
			Answer newAnswer = (Answer) super.clone();
			newAnswer.setId(null);
			newAnswer.setOptionalAnswerString("");
			return newAnswer;
		} catch (CloneNotSupportedException ex) {
			throw new InternalError(
					"This should not happen, ANSWER is cloneable!");
		}
	}
}
