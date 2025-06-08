package biz.sudden.knowledgeData.competencesManagement.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IRoleDimension;

@SuppressWarnings("serial")
@Entity
@Table(name = "ROLE_DIMENSIONS")
public class RoleDimension extends CMRoleBaseClass implements IRoleDimension,
		Cloneable {

	protected Dimension dimension;

	@Override
	public IRoleDimension clone() {
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
	public Dimension getDimension() {
		return dimension;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@ManyToOne
	@Override
	public Long getId() {
		return Id;
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
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	@Override
	public void setId(Long id) {
		this.Id = id;
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
		aux = aux + "\n    Dimension: " + this.getDimension().toString();
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";

		return aux;
	}
}
