package biz.sudden.knowledgeData.competencesManagement.domain;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICMBaseClass;

public abstract class CMBaseClass implements ICMBaseClass, Cloneable {

	protected String description;
	protected Long Id;
	protected String name;

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Long getId() {
		return Id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setId(Long id) {
		Id = id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		String aux = "";

		aux = aux + "ID: " + this.Id;
		aux = aux + " || Name: " + this.name;
		aux = aux + " || Description: " + this.description;

		return aux;
	}

	public ICMBaseClass clone(ICMBaseClass object) {
		object.setId(null);
		object.setDescription(this.description);
		object.setName(this.name);

		return object;
	}

}
