package biz.sudden.baseAndUtility.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

//@MappedSuperclass
@Entity
public class Actor implements Connectable {

	private String name;
	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
