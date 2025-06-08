package biz.sudden.knowledgeData.competencesManagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICategory;

@SuppressWarnings("serial")
@Entity
@Table(name = "CATEGORIES")
public class Category extends CMBaseClass implements ICategory {

	protected Long parentCategoryId;
	protected int type;

	@Override
	public ICategory clone() {
		Category iCategory = new Category();
		iCategory.setName(this.name);
		iCategory.setDescription(this.description);
		iCategory.setId(this.Id);
		iCategory.setParentCategoryId(this.parentCategoryId);
		iCategory.setType(this.type);
		return iCategory;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@Override
	public Long getId() {
		return Id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Long getParentCategoryId() {
		return parentCategoryId;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setId(Long Id) {
		this.Id = Id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	@Override
	public void setType(int type) {
		this.type = type;
	}

}
