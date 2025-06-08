package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.Category;
import biz.sudden.knowledgeData.competencesManagement.repository.ICategoryRepository;

public class CategoryRepositoryImpl extends
		GenericRepositoryImpl<Category, Long> implements ICategoryRepository {

	public CategoryRepositoryImpl() {
		super(Category.class);
	}

	public CategoryRepositoryImpl(Class<Category> type) {
		super(type);
	}

	@Override
	public Long addCategory(Category category) {
		System.out.println("Id: " + category.getId());
		System.out.println("Name: " + category.getName());
		System.out.println("Description: " + category.getDescription());
		System.out.println("Parent Category Id: "
				+ category.getParentCategoryId());
		System.out.println("Type: " + category.getType());

		return create(category);
	}

	@Override
	public Long addCategory(Long Id, String name, String description,
			Long parentCategoryId, int type) {
		Category iCategory = new Category();
		iCategory.setId(Id);
		iCategory.setName(name);
		iCategory.setDescription(description);
		iCategory.setParentCategoryId(parentCategoryId);
		iCategory.setType(type);

		System.out.println("Id: " + iCategory.getId());
		System.out.println("Name: " + iCategory.getName());
		System.out.println("Description: " + iCategory.getDescription());
		System.out.println("Parent Category Id: "
				+ iCategory.getParentCategoryId());
		System.out.println("Type: " + iCategory.getType());

		return create(iCategory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAllCategories() {
		return getHibernateTemplate().loadAll(Category.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoriesByParentId(long parentCategoryId,
			int type) {
		return getHibernateTemplate().find(
				"from Category as cat where cat.type = " + type
						+ " and cat.parentCategoryId = " + parentCategoryId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoriesByType(int type) {
		return getHibernateTemplate().find(
				"from Category as cat where cat.type = ?", type);
	}

	@Override
	public void removeAllCategories() {
		List<Category> iCategories = getAllCategories();
		getHibernateTemplate().deleteAll(iCategories);
	}

}
