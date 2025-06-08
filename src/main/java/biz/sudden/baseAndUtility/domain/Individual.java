package biz.sudden.baseAndUtility.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity
public class Individual implements Connectable {

	Logger logger = Logger.getLogger(this.getClass());

	Long id;
	String name;

	public Individual() {
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
