package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

public interface ICMQuestionnaireEntity extends ICMBaseClass {

	public Long getCategoryId();

	public String getCategoryName();

	public String getEText();

	public String getQText();

	public void setCategoryId(Long categoryId);

	public void setCategoryName(String categoryName);

	public void setEText(String text);

	public void setQText(String text);

	public void setSelected(boolean selected);

	public boolean isSelected();

}