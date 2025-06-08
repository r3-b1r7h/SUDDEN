package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.DimensionInstance;

public interface ICompetenceInstance extends ICMInstanceBaseClass {

	public Competence getCompetence();

	public int getCompetenceValuePenalty();

	public List<DimensionInstance> getDimensionInstances();

	public void setCompetence(Competence competence);

	public void setCompetenceValuePenalty(int competenceValuePenalty);

	public void setDimensionInstances(List<DimensionInstance> dimensionInstances);

}
