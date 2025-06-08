package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

public interface ICategory extends ICMBaseClass {

	public final static int CATEGORY_COMPETENCE = 2;
	public final static int CATEGORY_CVI = 0;
	public final static int CATEGORY_DIMENSION = 1;
	public final static int CATEGORY_QUESTIONNAIRE = 3;
	public final static int CATEGORY_ROLE = 4;

	public ICategory clone();

	public Long getParentCategoryId();

	public int getType();

	public void setParentCategoryId(Long parentCategoryId);

	public void setType(int type);

}
