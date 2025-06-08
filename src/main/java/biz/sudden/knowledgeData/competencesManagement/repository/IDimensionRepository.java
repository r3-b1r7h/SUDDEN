package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;

public interface IDimensionRepository {

	public long addDimension(Dimension dimension);

	public long addDimension(long categoryId, String categoryName, String name,
			String description, String eText, String qText, CVI cvi);

	public List<Dimension> getAllDimensions();

	public Dimension getDimensionById(long dimensionId);

	public List<Dimension> getDimensionByName(String name);

	public List<Dimension> getDimensionsByCategoryId(long categoryId);

	public void removeAllDimensions();

}
