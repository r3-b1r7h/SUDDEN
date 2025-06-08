package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import biz.sudden.baseAndUtility.domain.IDInterface;

public interface ICMBaseClass extends IDInterface {

	public String getDescription();

	public String getName();

	public void setDescription(String description);

	public void setName(String name);

}
