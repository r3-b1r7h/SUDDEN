package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.TickInstance;

public interface ICVIInstance extends ICMInstanceBaseClass {

	public CVI getCvi();

	public List<TickInstance> getTicks();

	public void setCvi(CVI cvi);

	public void setTicks(List<TickInstance> ticks);

}
