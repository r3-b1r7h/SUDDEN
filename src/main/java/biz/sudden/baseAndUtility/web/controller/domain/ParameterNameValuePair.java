package biz.sudden.baseAndUtility.web.controller.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ParameterNameValuePair {

	private String parameter;
	private String parameterValue;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

}
