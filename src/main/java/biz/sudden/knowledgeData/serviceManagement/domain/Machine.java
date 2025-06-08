package biz.sudden.knowledgeData.serviceManagement.domain;

import javax.persistence.Entity;

/**
 * 
 * @author Matthias Neubauer
 * 
 */

@Entity
public class Machine extends Thing {

	private String description;

	public Machine() {
	}

	public Machine(String name) {
		super.setName(name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
