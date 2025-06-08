package biz.sudden.userInterface.gui.web.controller;

import java.util.List;

import biz.sudden.baseAndUtility.web.controller.domain.ParameterNameValuePair;

public interface UserSessionController {

	public void navigateTo(String toViewId);

	public void navigateTo(String toViewId, String beanName,
			List<ParameterNameValuePair> parameterValuePairs);

	public void navigateTo(String toViewId, String beanName,
			String parameterName, Object parameterValue);

}
