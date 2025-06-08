package biz.sudden.knowledgeData.serviceManagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Thing implements Connectable {

	public static final String SuddenOntologyURI = "http://www.Sudden-ontologies.com/SuddenOntology.owl";
	public static final String NameSpace = SuddenOntologyURI + "#";

	private Long id;
	private String name;
	private String uri;

	@Transient
	public static String getShortName(String longOrShortName) {
		if (longOrShortName != null
				&& longOrShortName.length() > NameSpace.length()
				&& longOrShortName.startsWith(NameSpace))
			return longOrShortName.substring(NameSpace.length());
		return longOrShortName;
	}

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

	public String getURI() {
		return uri;
	}

	public void setURI(String name) {
		this.uri = name;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Thing && getId().equals(((Thing) o).getId())
				&& getName().equals(((Thing) o).getName()))
			return true;
		return false;
	}
}
