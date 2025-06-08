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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICompetenceInstance;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMPETENCE_INSTANCES")
public class CompetenceInstance extends CMInstanceBaseClass implements
		ICompetenceInstance, Cloneable {

	protected Competence competence;
	protected int competenceValuePenalty = 0;
	protected List<DimensionInstance> dimensionInstances = new ArrayList<DimensionInstance>();

	public CompetenceInstance() {
		super();
	}

	@Transient
	public void calculateValue() {
		float competenceValue = 0;
		for (int c0 = 0; c0 < dimensionInstances.size(); c0++) {
			DimensionInstance dimensionInstance = dimensionInstances.get(c0);
			competenceValue = competenceValue
					+ Float.valueOf(dimensionInstance.getValue());
		}
		setAutoCalcValue(competenceValue);
		setValue(Float
				.toString((this.autoCalcValue * this.weight * this.competence.weightMultiplier)));
	}

	@Override
	public ICompetenceInstance clone() {
		return null;
	}

	@Override
	public float getAutoCalcValue() {
		return autoCalcValue;
	}

	@Override
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@PrimaryKeyJoinColumn
	public Competence getCompetence() {
		return competence;
	}

	public int getCompetenceValuePenalty() {
		return competenceValuePenalty;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL)
	public List<DimensionInstance> getDimensionInstances() {
		return dimensionInstances;
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
	public Organization getOrganization() {
		return organization;
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
	public void setCompetence(Competence competence) {
		this.competence = competence;
	}

	public void setCompetenceValuePenalty(int competenceValuePenalty) {
		this.competenceValuePenalty = competenceValuePenalty;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public void setDimensionInstances(List<DimensionInstance> dimensionInstances) {
		this.dimensionInstances = dimensionInstances;
	}

	@Override
	public void setId(Long id) {
		this.Id = id;
	}

	@Override
	public void setOrganization(Organization organization) {
		this.organization = organization;
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
		aux = aux + "\nDate: " + this.getDate();
		aux = aux + "\nOrganization ID: " + this.getOrganization().getId();
		aux = aux + "\nValue: " + this.getValue();
		aux = aux + "\n    Competence: " + this.getCompetence().getId();
		aux = aux + "    ------------------    ";
		aux = aux + "\n Dimension Instances -->";
		for (int c0 = 0; c0 < dimensionInstances.size(); c0++) {
			aux = aux + dimensionInstances.get(c0).toString();
		}
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";

		return aux;
	}

}
