package biz.sudden.baseAndUtility.util;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

public class JSFUtils {

	private JSFUtils() {
		throw new InternalError("JSFUtils is not allowed to be initialized!");
	}

	public static FacesContext getFacesContextCurrentInstance() {
		return FacesContext.getCurrentInstance();
	}

	public static Application getApplication() {
		return FacesContext.getCurrentInstance().getApplication();
	}

	public static NavigationHandler getNavigationHandler() {
		return FacesContext.getCurrentInstance().getApplication()
				.getNavigationHandler();
	}

	public static void navigateTo(String action) {
		getNavigationHandler().handleNavigation(
				getFacesContextCurrentInstance(), null, action);
	}

}
