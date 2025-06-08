package biz.sudden.knowledgeData.serviceManagement.domain;

import javax.persistence.Entity;

/**
 * 
 * @author Matthias Neubauer
 * 
 */

@Entity
public class Function extends Thing {

	private String description;

	public Function() {
	}

	public Function(String name) {
		super.setName(name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
