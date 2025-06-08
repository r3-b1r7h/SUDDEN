package biz.sudden.userInterface.gui.web.controller.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import biz.sudden.baseAndUtility.web.controller.domain.ParameterNameValuePair;
import biz.sudden.userInterface.gui.web.controller.UserSessionController;

// FIXME ThF: Duplicate code in the navigateTo methods, find ways to avoid error
// prone redundancy

public class UserSessionControllerImpl implements UserSessionController {

	private Logger logger = Logger.getLogger(this.getClass());

	private HttpSession session;

	public UserSessionControllerImpl() {

	}

	@Override
	public void navigateTo(String toViewId) {
		FacesContext.getCurrentInstance().getApplication()
				.getNavigationHandler().handleNavigation(
						FacesContext.getCurrentInstance(), null, toViewId);
	}

	@Override
	public void navigateTo(String toViewId, String beanName,
			List<ParameterNameValuePair> parameterValuePairs) {

		session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		WebApplicationContext webAppContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());

		Object controller = null;

		if (session.getAttribute(beanName) != null) {
			controller = session.getAttribute(beanName);
		} else {
			controller = webAppContext.getBean(beanName);
			session.setAttribute(beanName, controller);
		}

		int i = 0;

		for (ParameterNameValuePair parameterValuePair : parameterValuePairs) {

			Object parameterValue = parameterValuePair.getParameterValue();

			i++;

			String newParameterValueString = "set"
					+ parameterValuePair.getParameter().substring(0, 1)
							.toUpperCase()
					+ parameterValuePair.getParameter().substring(1);

			Method method;
			try {
				method = controller.getClass().getMethod(
						newParameterValueString, parameterValue.getClass());
				method.invoke(controller, parameterValue);
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		navigateTo(toViewId);
		logger.debug("Navigated to " + toViewId);
	}

	@Override
	public void navigateTo(String toViewId, String beanName,
			String parameterName, Object parameterValue) {

		session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);

		WebApplicationContext webAppContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());

		Object controller = null;

		if (session.getAttribute(beanName) != null) {
			controller = session.getAttribute(beanName);
		} else {
			controller = webAppContext.getBean(beanName);
			session.setAttribute(beanName, controller);
		}

		String newParameterValueString = "set"
				+ parameterName.substring(0, 1).toUpperCase()
				+ parameterName.substring(1);

		Method method;
		try {
			method = controller.getClass().getMethod(newParameterValueString,
					parameterValue.getClass());
			method.invoke(controller, parameterValue);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		navigateTo(toViewId);
		logger.debug("Navigated to " + toViewId);
	}

}
