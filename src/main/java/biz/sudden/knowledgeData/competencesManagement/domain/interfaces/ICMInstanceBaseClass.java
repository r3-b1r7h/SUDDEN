package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import java.util.Date;

import biz.sudden.baseAndUtility.domain.IDInterface;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public interface ICMInstanceBaseClass extends IDInterface {

	public float getAutoCalcValue();

	public Date getDate();

	public Organization getOrganization();

	public String getValue();

	public float getWeight();

	public void setAutoCalcValue(float autoCalcValue);

	public void setDate(Date date);

	public void setOrganization(Organization organization);

	public void setValue(String value);

	public void setWeight(float weight);

	public abstract String toString();

}
