package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.Competence;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;

public interface ICompetenceRepository {

	public long addCompetence(Competence competence);

	public long addCompetence(long categoryId, String categoryName,
			String name, String description, String eText, String qText,
			ArrayList<Dimension> dimensions);

	public List<Competence> getAllCompetences();

	public Competence getCompetenceById(long id);

	public List<Competence> getCompetenceByName(String name);

	public List<Competence> getCompetencesByCategoryId(long categoryId);

	public void removeAllCompetences();

}
