package biz.sudden.knowledgeData.questionnaireNew.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity(name = "questionnaireNew.TickTemplate")
public class TickTemplate implements Connectable {

	private Long id;
	private boolean multipleChoice = false;

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}

	public TickTemplate() {
		this.multipleChoice = false;
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

	private String name;

	// TODO TF: make unique
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
