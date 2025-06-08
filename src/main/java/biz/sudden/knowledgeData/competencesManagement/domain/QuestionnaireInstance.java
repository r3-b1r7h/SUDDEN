package biz.sudden.knowledgeData.competencesManagement.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IQuestionnaireInstance;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

@SuppressWarnings("serial")
@Entity
@Table(name = "QUESTIONNAIRE_INSTANCES")
public class QuestionnaireInstance extends CMInstanceBaseClass implements
		IQuestionnaireInstance, Cloneable {

	protected List<CompetenceInstance> competenceInstances = new ArrayList<CompetenceInstance>();
	protected Questionnaire questionnaire;

	@Transient
	public void calculateValue() {
		float questionnaireValue = 0;
		for (int c0 = 0; c0 < competenceInstances.size(); c0++) {
			CompetenceInstance competenceInstance = competenceInstances.get(c0);
			questionnaireValue = questionnaireValue
					+ Float.valueOf(competenceInstance.getValue());
		}
		setAutoCalcValue(questionnaireValue);
		setValue(Float.toString(this.autoCalcValue));
	}

	@Override
	public IQuestionnaireInstance clone() {
		return null;
	}

	@Override
	public float getAutoCalcValue() {
		return autoCalcValue;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL)
	public List<CompetenceInstance> getCompetenceInstances() {
		return competenceInstances;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@Override
	public Long getId() {
		return Id;
	}

	@Override
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public Organization getOrganization() {
		return organization;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public float getWeight() {
		return weight;
	}

	@Override
	public void setAutoCalcValue(float autoCalcValue) {
		this.autoCalcValue = autoCalcValue;
	}

	@Override
	public void setCompetenceInstances(
			List<CompetenceInstance> competenceInstances) {
		this.competenceInstances = competenceInstances;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public void setId(Long id) {
		Id = id;
	}

	@Override
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Override
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		String aux = "";

		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";
		aux = aux + "\nQuestionnaire: " + this.questionnaire.getName();
		aux = aux + "\n: " + this.getOrganization().getId();
		aux = aux + "\n Competence Instances -->";
		for (int c0 = 0; c0 < competenceInstances.size(); c0++) {
			aux = aux + competenceInstances.get(c0).toString();
		}
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";

		return aux;
	}

}
