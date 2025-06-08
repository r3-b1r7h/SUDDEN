package biz.sudden.baseAndUtility.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

/**
 * 
 * @author Matthias Neubauer
 * 
 */

@Entity
public class Process implements Connectable {

	private String name;
	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Process))
			return false;
		Process i = (Process) o;
		if (this.getId().equals(
				i.getId().equals(i.getId())
						&& this.getName().equals(i.getName())))
			return true;
		return false;
	}

}
