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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IRoleCompetence;

@SuppressWarnings("serial")
@Entity
@Table(name = "ROLE_COMPETENCES")
public class RoleCompetence extends CMRoleBaseClass implements IRoleCompetence,
		Cloneable {

	protected Competence competence;
	protected List<RoleDimension> roleDimensions = new ArrayList<RoleDimension>();

	public RoleCompetence() {
		super();
	}

	@Override
	@Transient
	public void addRoleDimension(RoleDimension roleDimension) {
		roleDimensions.add(roleDimension);
	}

	@Override
	public IRoleCompetence clone() {
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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@Override
	public Long getId() {
		return Id;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public List<RoleDimension> getRoleDimensions() {
		return roleDimensions;
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

	@Override
	public void setId(Long id) {
		this.Id = id;
	}

	@Override
	public void setRoleDimensions(List<RoleDimension> roleDimensions) {
		this.roleDimensions = roleDimensions;
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
		aux = aux + "\nWeight: " + this.getWeight();
		aux = aux + "\nValue: " + this.getAutoCalcValue();
		aux = aux + "\n    Competence: " + this.getCompetence().toString();
		aux = aux + "    ------------------    ";
		aux = aux + "\n Role Dimensions -->";
		for (int c0 = 0; c0 < roleDimensions.size(); c0++) {
			aux = aux + roleDimensions.get(c0).toString();
		}
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";

		return aux;
	}

}
