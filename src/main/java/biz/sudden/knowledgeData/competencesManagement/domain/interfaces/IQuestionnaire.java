package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireRequiredCompetenceDimensions;
import biz.sudden.knowledgeData.competencesManagement.domain.Competence;

import com.icesoft.faces.component.ext.HtmlOutputText;
import com.icesoft.faces.component.ext.HtmlPanelGroup;

public interface IQuestionnaire extends ICMQuestionnaireEntity {

	public CMQuestionnaireRequiredCompetenceDimensions addCompetence(
			Competence competence);

	public IQuestionnaire clone();

	public List<Competence> getCompetences();

	public HtmlOutputText getQuestionnaireHtml();

	public ArrayList<CMQuestionnaireRequiredCompetenceDimensions> getRequiredCompetenceDimensions();

	public void removeCompetence(int index);

	public void setCompetences(List<Competence> competences);

	public void setQuestionnaireHtml(HtmlOutputText questionnaireHtml);

	public void setRequiredCompetenceDimensions(
			ArrayList<CMQuestionnaireRequiredCompetenceDimensions> requiredCompetenceDimensions);

	public void toHtml(HtmlPanelGroup container);

	public String toString();

}
