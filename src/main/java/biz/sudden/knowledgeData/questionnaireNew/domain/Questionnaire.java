package biz.sudden.knowledgeData.questionnaireNew.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity(name = "questionnaireNew.Questionnaire")
public class Questionnaire implements Connectable {

	private Long id;
	private List<Question> questionList;
	private String name;

	public Questionnaire() {
		// empty constructor for hibernate
	}

	public Questionnaire(String name) {
		this.name = name;
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

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
