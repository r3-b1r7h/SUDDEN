package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireRequiredCompetenceDimensions;
import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireToHtml;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;

import com.icesoft.faces.component.panelcollapsible.PanelCollapsible;

public interface ICompetence extends ICMQuestionnaireEntity {

	public void addDimension(Dimension dimension);

	public ICompetence clone();

	public List<Dimension> getDimensions();

	public float getWeightMultiplier();

	public void removeDimension(int index);

	public void setDimensions(List<Dimension> dimensions);

	public void setWeightMultiplier(float weightMultiplier);

	public void toHtml(
			PanelCollapsible container,
			CMQuestionnaireToHtml questionnaireToHtml,
			ArrayList<CMQuestionnaireRequiredCompetenceDimensions> requiredCompetenceDimensions,
			int competenceIndex);

	public String toString();

}
