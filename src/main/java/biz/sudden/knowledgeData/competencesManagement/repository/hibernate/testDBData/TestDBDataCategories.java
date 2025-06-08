package biz.sudden.knowledgeData.competencesManagement.repository.hibernate.testDBData;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import biz.sudden.knowledgeData.competencesManagement.domain.Category;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;
import biz.sudden.knowledgeData.competencesManagement.repository.ICategoryRepository;

public class TestDBDataCategories {

	Logger logger = Logger.getLogger(this.getClass());

	// Memory Objects
	private Hashtable<String, Category> categoriesCVIs = new Hashtable<String, Category>();
	private Hashtable<String, Category> categoriesDimensions = new Hashtable<String, Category>();
	private Hashtable<String, Category> categoriesQuestionnaires = new Hashtable<String, Category>();
	private Hashtable<String, Category> categoriesCompetences = new Hashtable<String, Category>();
	private Hashtable<String, Category> categoriesRoles = new Hashtable<String, Category>();

	// Repositories
	private ICategoryRepository categoryRepository;

	public Category getCategoryCompetence(String name) {
		if (!categoriesCompetences.containsKey(name))
			logger.error("KEY not found!!" + name);
		return categoriesCompetences.get(name);
	}

	public Category getCategoryCVI(String name) {
		if (!categoriesCVIs.containsKey(name))
			logger.error("KEY not found!!" + name);
		return categoriesCVIs.get(name);
	}

	public Category getCategoryDimension(String name) {
		if (!categoriesDimensions.containsKey(name))
			logger.error("KEY not found!!" + name);
		return categoriesDimensions.get(name);
	}

	public Category getCategoryQuestionnaire(String name) {
		if (!categoriesQuestionnaires.containsKey(name))
			logger.error("KEY not found!!" + name);
		return categoriesQuestionnaires.get(name);
	}

	public ICategoryRepository getCategoryRepository() {
		return categoryRepository;
	}

	public Category getCategoryRole(String name) {
		if (!categoriesCompetences.containsKey(name))
			logger.error("KEY not found!!" + name);
		return categoriesRoles.get(name);
	}

	public void insertDBTestDataCompetenceCategories() {
		/* Adding Competences Categories Test Data */
		Category category = new Category();
		category.setName("Stammdaten");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);

		category = new Category();
		category.setName("Technologie");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);

		category = new Category();
		category.setName("Kommunikationstechnologie");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);

		category = new Category();
		category.setName("Recht und Haftung");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);

		category = new Category();
		category.setName("Logistikmanagement");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);

		category = new Category();
		category.setName("Umweltmanagement");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);

		category = new Category();
		category.setName("Finanzen");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);

		category = new Category();
		category.setName("Lernen und Innovation");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);

		category = new Category();
		category.setName("Kundenfokus");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);

		category = new Category();
		category.setName("Qualitaetsmanagement");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);

		category = new Category();
		category.setName("Mitarbeiterqualifikation");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);

		category = new Category();
		category.setName("Fuehrungskompetenz");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_COMPETENCE);
		categoryRepository.addCategory(category);
		categoriesCompetences.put(category.getName(), category);
	}

	public void insertDBTestDataCVICategories() {
		/* Adding CVIs Categories Test Data */
		Category category = new Category();
		category.setName("Quantifiable CVIs");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Non Quantifiable CVIs");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Mixed CVIs");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Specific Dimension CVIs - Stammdaten");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Specific Dimension CVIs - Technologie");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Specific Dimension CVIs - Kommunikationstechnologie");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Specific Dimension CVIs - Recht und Haftung");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Specific Dimension CVIs - Logistikmanagement");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Specific Dimension CVIs - Umweltmanagement");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Specific Dimension CVIs - Finanzen");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Specific Dimension CVIs - Lernen und Innovation");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Specific Dimension CVIs - Kundenfokus");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);

		category = new Category();
		category.setName("Specific Dimension CVIs - Qualitatsmanagement");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_CVI);
		categoryRepository.addCategory(category);
		categoriesCVIs.put(category.getName(), category);
	}

	public void insertDBTestDataDBCategories() {
		insertDBTestDataCVICategories();
		insertDBTestDataDimensionCategories();
		insertDBTestDataCompetenceCategories();
		insertDBTestDataQuestionnaireCategories();
		insertDBTestDataRoleCategories();
	}

	public void insertDBTestDataDimensionCategories() {
		/* Adding Dimensions Categories Test Data */
		Category category = new Category();
		category.setName("Stammdaten");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);

		category = new Category();
		category.setName("Technologie");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);

		category = new Category();
		category.setName("Kommunikationstechnologie");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);

		category = new Category();
		category.setName("Recht und Haftung");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);

		category = new Category();
		category.setName("Logistikmanagement");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);

		category = new Category();
		category.setName("Umweltmanagement");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);

		category = new Category();
		category.setName("Finanzen");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);

		category = new Category();
		category.setName("Lernen und Innovation");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);

		category = new Category();
		category.setName("Kundenfokus");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);

		category = new Category();
		category.setName("Qualitaetsmanagement");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);

		category = new Category();
		category.setName("Mitarbeiterqualifikation");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);

		category = new Category();
		category.setName("Fuehrungskompetenz");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_DIMENSION);
		categoryRepository.addCategory(category);
		categoriesDimensions.put(category.getName(), category);
	}

	public void insertDBTestDataQuestionnaireCategories() {
		/* Adding Questionnaires Categories Test Data */
		Category category = new Category();
		category.setName("Evaluation Level 0");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_QUESTIONNAIRE);
		categoryRepository.addCategory(category);
		categoriesQuestionnaires.put(category.getName(), category);

		category = new Category();
		category.setName("Evaluation Level 1");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_QUESTIONNAIRE);
		categoryRepository.addCategory(category);
		categoriesQuestionnaires.put(category.getName(), category);

		category = new Category();
		category.setName("Evaluation Level 2");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_QUESTIONNAIRE);
		categoryRepository.addCategory(category);
		categoriesQuestionnaires.put(category.getName(), category);
	}

	public void insertDBTestDataRoleCategories() {
		/* Adding Role Categories Test Data */
		Category category = new Category();
		category.setName("Evaluation Level 0");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_ROLE);
		categoryRepository.addCategory(category);
		categoriesRoles.put(category.getName(), category);

		category = new Category();
		category.setName("Evaluation Level 1");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_ROLE);
		categoryRepository.addCategory(category);
		categoriesRoles.put(category.getName(), category);

		category = new Category();
		category.setName("Evaluation Level 2");
		category.setDescription(category.getName());
		category.setParentCategoryId(new Long(-1));
		category.setType(ICategory.CATEGORY_ROLE);
		categoryRepository.addCategory(category);
		categoriesRoles.put(category.getName(), category);
	}

	public void setCategoryRepository(ICategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

}
