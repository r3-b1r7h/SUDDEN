package biz.sudden.knowledgeData.questionnaireNew.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity(name = "questionnaireNew.AnswerTick")
public class AnswerTick implements Connectable {

	private Tick belongsToTick;
	private String optionalAnswerString;
	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	public Tick getBelongsToTick() {
		return belongsToTick;
	}

	public void setBelongsToTick(Tick belongsToTick) {
		this.belongsToTick = belongsToTick;
	}

	public String getOptionalAnswerString() {
		return optionalAnswerString;
	}

	public void setOptionalAnswerString(String optionalAnswerString) {
		this.optionalAnswerString = optionalAnswerString;
	}

}
