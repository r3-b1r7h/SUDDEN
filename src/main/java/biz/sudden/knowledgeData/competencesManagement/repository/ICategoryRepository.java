package biz.sudden.knowledgeData.competencesManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.competencesManagement.domain.Category;

public interface ICategoryRepository extends GenericRepository<Category, Long> {

	public Long addCategory(Category category);

	public Long addCategory(Long Id, String name, String description,
			Long parentCategoryId, int type);

	public List<Category> getAllCategories();

	public List<Category> getCategoriesByParentId(long parentCategoryId,
			int type);

	public List<Category> getCategoriesByType(int type);

	public void removeAllCategories();

}
