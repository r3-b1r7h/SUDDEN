package biz.sudden.knowledgeData.competencesManagement.domain;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICMQuestionnaireEntity;

@SuppressWarnings("serial")
public class CMQuestionnaireEntity extends CMBaseClass implements
		ICMQuestionnaireEntity {

	protected Long categoryId = new Long(-1);
	protected String categoryName = "";
	protected String eText = "";
	protected String qText = "";
	protected boolean selected = false;

	public ICMQuestionnaireEntity clone(ICMQuestionnaireEntity object) {
		super.clone(object);

		object.setCategoryId(this.categoryId);
		object.setCategoryName(this.categoryName);
		object.setEText(this.eText);
		object.setQText(this.qText);
		object.setSelected(this.selected);

		return object;
	}

	@Override
	public Long getCategoryId() {
		return categoryId;
	}

	@Override
	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public String getEText() {
		return eText;
	}

	@Override
	public String getQText() {
		return qText;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public void setEText(String text) {
		eText = text;
	}

	@Override
	public void setQText(String text) {
		qText = text;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		String aux = super.toString();

		aux = aux + " || Category Id: " + this.categoryId;
		aux = aux + " || Category Name: " + this.categoryName;
		aux = aux + " || EText: " + this.eText;
		aux = aux + " || QText: " + this.qText;
		aux = aux + " || Selected: " + this.selected;

		return aux;
	}

}
