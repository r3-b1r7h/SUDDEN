package biz.sudden.knowledgeData.competencesManagement.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IDimensionInstance;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

@SuppressWarnings("serial")
@Entity
@Table(name = "DIMENSION_INSTANCES")
public class DimensionInstance extends CMInstanceBaseClass implements
		IDimensionInstance, Cloneable {

	protected CVIInstance cviInstance;
	protected Dimension dimension;

	@Transient
	public void calculateValue() {
		setAutoCalcValue(Float.valueOf(this.cviInstance.getValue()));
		setValue(Float
				.toString((this.autoCalcValue * this.weight * this.dimension.weightMultiplier)));
	}

	@Override
	public IDimensionInstance clone() {
		return null;
	}

	@Override
	public float getAutoCalcValue() {
		return autoCalcValue;
	}

	@Override
	@OneToOne(cascade = CascadeType.ALL)
	public CVIInstance getCviInstance() {
		return cviInstance;
	}

	@Override
	public Date getDate() {
		return date;
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
	public void setCviInstance(CVIInstance cviInstance) {
		this.cviInstance = cviInstance;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
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
		aux = aux + "\n    Dimension: " + this.getDimension().getId();
		aux = aux + "\n    CVI Instance: " + this.getCviInstance().toString();
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";

		return aux;
	}
}
