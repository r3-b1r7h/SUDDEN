package biz.sudden.knowledgeData.competencesManagement.domain.interfaces;

public interface ITick extends ICMBaseClass {

	public ITick clone();

	public int getAutoValue();

	public Long getId();

	public float getTNumValue();

	public String getTTextValue();

	public int getType();

	public boolean isQuantifiable();

	public boolean isSelected();

	public void setAutoValue(int autoValue);

	public void setId(Long Id);

	public void setQuantifiable(boolean quantifiable);

	public void setSelected(boolean selected);

	public void setTNumValue(float numValue);

	public void setTTextValue(String textValue);

	public void setType(int type);

	public String toString();

}
