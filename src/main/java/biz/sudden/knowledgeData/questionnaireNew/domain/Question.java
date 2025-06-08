package biz.sudden.knowledgeData.questionnaireNew.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity(name = "questionnaireNew.Question")
public class Question implements Connectable {

	private Long id;
	private String question;
	private List<Tick> availableTicks = new LinkedList<Tick>();
	private Competence associatedCompetence;
	private boolean multipleChoice = false;
	private boolean onlyFreeText = false;

	private boolean selected = false;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isOnlyFreeText() {
		return onlyFreeText;
	}

	public void setOnlyFreeText(boolean onlyFreeText) {
		this.onlyFreeText = onlyFreeText;
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<Tick> getAvailableTicks() {
		return availableTicks;
	}

	public void setAvailableTicks(List<Tick> availableTicks) {
		this.availableTicks = availableTicks;
	}

	@Transient
	public Tick getFirstTick() {
		return getAvailableTicks().get(0);
	}

	@ManyToOne
	public Competence getAssociatedCompetence() {
		return associatedCompetence;
	}

	public void setAssociatedCompetence(Competence associatedCompetence) {
		this.associatedCompetence = associatedCompetence;
	}

}
