package biz.sudden.baseAndUtility.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;

@Entity
public class Link implements Connectable {

	private Long id;
	private String fromClass;
	private Long fromID;
	private JsfLink toJsfLink;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromClass() {
		return fromClass;
	}

	public void setFromClass(String fromClass) {
		this.fromClass = fromClass;
	}

	public Long getFromID() {
		return fromID;
	}

	public void setFromID(Long fromID) {
		this.fromID = fromID;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public JsfLink getToJsfLink() {
		return toJsfLink;
	}

	public void setToJsfLink(JsfLink toJsfLink) {
		this.toJsfLink = toJsfLink;
	}

}
