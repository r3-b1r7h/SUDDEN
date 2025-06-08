package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import biz.sudden.baseAndUtility.domain.IDInterface;

public interface ICMRoleBaseClass extends IDInterface {

	public float getAutoCalcValue();

	public float getWeight();

	public void setAutoCalcValue(float autoCalcValue);

	public void setWeight(float weight);

	public abstract String toString();

}
