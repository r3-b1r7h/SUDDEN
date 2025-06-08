package biz.sudden.knowledgeData.competencesManagement.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IRole;

@SuppressWarnings("serial")
@Entity
@Table(name = "ROLES")
public class Role extends CMQuestionnaireEntity implements IRole, Cloneable {

	protected RoleQuestionnaire roleQuestionnaire;

	public Role() {
		super();
	}

	@Override
	public IRole clone() {
		return null;
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
	public String getDescription() {
		return description;
	}

	@Override
	public String getEText() {
		return eText;
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
	public String getQText() {
		return qText;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public RoleQuestionnaire getRoleQuestionnaire() {
		return roleQuestionnaire;
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
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setEText(String text) {
		eText = text;
	}

	@Override
	public void setId(Long id) {
		this.Id = id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setQText(String text) {
		qText = text;
	}

	@Override
	public void setRoleQuestionnaire(RoleQuestionnaire roleQuestionnaire) {
		this.roleQuestionnaire = roleQuestionnaire;
	}

	@Override
	public String toString() {
		String aux = "------ ROLE ------- \n" + super.toString();

		aux = aux + "    ------------------    ";
		aux = aux + "\n Questionnaire Entity -->"
				+ this.getRoleQuestionnaire().toString();
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";

		return aux;
	}

}