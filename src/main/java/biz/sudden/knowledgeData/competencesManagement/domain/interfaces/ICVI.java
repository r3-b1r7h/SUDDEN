package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.CMQuestionnaireToHtml;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;

import com.icesoft.faces.component.ext.HtmlPanelGrid;

public interface ICVI extends ICMQuestionnaireEntity {

	public void addTick(Tick tick);

	public ICVI clone();

	public float getMaxRange();

	public float getMinRange();

	public String getMultipleChoiceValues();

	public List<Tick> getTicks();

	public boolean isMultipleChoice();

	public void removeTick(int i);

	public void setMaxRange(float maxRange);

	public void setMinRange(float minRange);

	public void setMultipleChoice(boolean multipleChoice);

	public void setMultipleChoiceValues(String multipleChoiceValues);

	public void setTicks(List<Tick> ticks);

	public void toHtml(HtmlPanelGrid container,
			CMQuestionnaireToHtml questionnaireToHtml, String prefixId);

	public String toString();
}
