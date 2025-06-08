package biz.sudden.baseAndUtility.web.controller.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CollectionOfElements;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity
public class JsfLink implements Connectable {

	private String domainClass;
	private String domainId;

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getDomainClass() {
		return domainClass;
	}

	public void setDomainClass(String domainClass) {
		this.domainClass = domainClass;
	}

	private String viewID;
	private String controllerBeanName;

	private List<ParameterNameValuePair> parameterValuesPairs;

	@CollectionOfElements(fetch = FetchType.EAGER)
	public List<ParameterNameValuePair> getParameterValuesPairs() {
		return parameterValuesPairs;
	}

	public void setParameterValuesPairs(
			List<ParameterNameValuePair> parameterValuesPairs) {
		this.parameterValuesPairs = parameterValuesPairs;
	}

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String txt;

	public String getText() {
		return txt;
	}

	public void setText(String txt) {
		this.txt = txt;
	}

	public String getViewID() {
		return viewID;
	}

	public void setViewID(String viewID) {
		this.viewID = viewID;
	}

	public String getControllerBeanName() {
		return controllerBeanName;
	}

	public void setControllerBeanName(String controllerBeanName) {
		this.controllerBeanName = controllerBeanName;
	}

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
