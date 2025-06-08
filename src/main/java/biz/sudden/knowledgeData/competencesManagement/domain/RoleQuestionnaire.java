package biz.sudden.knowledgeData.competencesManagement.domain;

import java.util.ArrayList;
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

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IRoleQuestionnaire;

@SuppressWarnings("serial")
@Entity
@Table(name = "ROLE_QUESTIONNAIRES")
public class RoleQuestionnaire extends CMRoleBaseClass implements
		IRoleQuestionnaire, Cloneable {

	protected Questionnaire questionnaire;
	protected List<RoleCompetence> roleCompetences = new ArrayList<RoleCompetence>();

	@Override
	@Transient
	public void addRoleCompetence(RoleCompetence roleCompetence) {
		roleCompetences.add(roleCompetence);
	}

	@Override
	public IRoleQuestionnaire clone() {
		return null;
	}

	@Override
	public float getAutoCalcValue() {
		return autoCalcValue;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@Override
	public Long getId() {
		return Id;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public List<RoleCompetence> getRoleCompetences() {
		return roleCompetences;
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
	public void setId(Long id) {
		Id = id;
	}

	@Override
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	@Override
	public void setRoleCompetences(List<RoleCompetence> roleCompetences) {
		this.roleCompetences = roleCompetences;
	}

	@Override
	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		String aux = "";

		return aux;
	}

}
