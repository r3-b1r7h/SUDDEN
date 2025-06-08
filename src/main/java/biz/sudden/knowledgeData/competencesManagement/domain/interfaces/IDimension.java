package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireToHtml;
import biz.sudden.knowledgeData.competencesManagement.domain.CVI;

import com.icesoft.faces.component.panelcollapsible.PanelCollapsible;

public interface IDimension extends ICMQuestionnaireEntity {

	public IDimension clone();

	public CVI getCvi();

	public float getWeightMultiplier();

	public void setCvi(CVI cvi);

	public void setWeightMultiplier(Float weightMultiplier);

	public void toHtml(PanelCollapsible container,
			CMQuestionnaireToHtml questionnaireToHtml, String prefixId,
			int dimensionIndex);

	public String toString();

}
