package biz.sudden.knowledgeData.competencesManagement.domain;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICMRoleBaseClass;

@SuppressWarnings("serial")
public abstract class CMRoleBaseClass implements ICMRoleBaseClass, Cloneable {

	protected float autoCalcValue = 0;
	protected Long Id;
	protected float weight = 1f;

	@Override
	public ICMRoleBaseClass clone() {
		return null;
	}

	@Override
	public float getAutoCalcValue() {
		return autoCalcValue;
	}

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
	public void setId(Long id) {
		Id = id;
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
