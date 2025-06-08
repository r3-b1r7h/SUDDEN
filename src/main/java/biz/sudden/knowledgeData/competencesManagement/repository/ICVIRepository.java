package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.competencesManagement.domain.CVI;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;

public interface ICVIRepository extends GenericRepository<CVI, Long> {

	public long addCVI(CVI cvi);

	public long addCVI(Long categoryId, String categoryName, String name,
			String description, float maxRange, float minRange,
			ArrayList<Tick> ticks);

	public List<CVI> getAllCVIs();

	public CVI getCVIById(long id);

	public List<CVI> getCVIsByCategoryId(Long categoryId);

	public void removeAllCVIs();

}
