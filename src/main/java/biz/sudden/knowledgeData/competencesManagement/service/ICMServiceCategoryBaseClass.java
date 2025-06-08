package biz.sudden.knowledgeData.competencesManagement.service;

import java.util.List;

import biz.sudden.knowledgeData.competencesManagement.domain.Category;
import biz.sudden.knowledgeData.competencesManagement.repository.ICategoryRepository;

public interface ICMServiceCategoryBaseClass extends ICMServiceBaseClass {

	/*
	 * Category Entities - Access to the entities should be done via the
	 * service, not the repository
	 */

	public ICategoryRepository getCategoryRepository();

	public List<Category> retrieveAllCategories();

	public List<Category> retrieveCategoriesByType(int type);

	public List<Category> retrieveCategoriesByParentId(long parentCategoryId,
			int type);

	public long addCategory(Category category);

	public void removeAllCategories();

}
