package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import biz.sudden.knowledgeData.competencesManagement.domain.CVIInstance;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;

public interface IDimensionInstance extends ICMInstanceBaseClass {

	public CVIInstance getCviInstance();

	public Dimension getDimension();

	public void setCviInstance(CVIInstance cviInstance);

	public void setDimension(Dimension dimension);

}
