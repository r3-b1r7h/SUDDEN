package biz.sudden.knowledgeData.competencesManagement.domain;

import java.util.Date;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICMInstanceBaseClass;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

@SuppressWarnings("serial")
public abstract class CMInstanceBaseClass implements ICMInstanceBaseClass,
		Cloneable {

	protected float autoCalcValue = 0;
	protected Date date;
	protected Long Id;
	protected Organization organization;
	protected String value;
	protected float weight = 0f;

	@Override
	public ICMInstanceBaseClass clone() {
		return null;
	}

	@Override
	public float getAutoCalcValue() {
		return autoCalcValue;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public Long getId() {
		return Id;
	}

	@Override
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

		return aux;
	}

}
