package biz.sudden.baseAndUtility.web.controller;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import biz.sudden.baseAndUtility.web.controller.impl.UserJSFControllerImpl;

import com.icesoft.faces.facelets.D2DFaceletViewHandler;

public class CustomViewHandler extends D2DFaceletViewHandler {

	@Override
	// FIXME: Performance -> This method is called very often!
	public Locale calculateLocale(FacesContext context) {

		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(true);

		WebApplicationContext webAppContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(session.getServletContext());

		Object controller = webAppContext.getBean("userController");

		UserJSFControllerImpl userController = (UserJSFControllerImpl) controller;

		return userController.getLocale();

	}

}
