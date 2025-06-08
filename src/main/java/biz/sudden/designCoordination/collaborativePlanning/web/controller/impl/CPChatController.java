package biz.sudden.designCoordination.collaborativePlanning.web.controller.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;

public class CPChatController {

	public List getLoggedInUser() {
		// SecurityContextHolder.getContext().
		return new LinkedList();
	}

	public String doLogin() throws IOException, ServletException {
		// ExternalContext context =
		// FacesContext.getCurrentInstance().getExternalContext();
		//        
		// System.out.println(context.getRequest());
		//        
		// ServletExternalContext context;
		//        
		// // co^
		// 
		// RequestDispatcher dispatcher = ((ServletRequest)
		// context.getRequest())
		// .getRequestDispatcher("/j_spring_security_check");
		// 
		// dispatcher.forward((ServletRequest) context.getRequest(),
		// (ServletResponse) context.getResponse());
		// 
		// FacesContext.getCurrentInstance().responseComplete();
		// // It's OK to return null here because Faces is just going to exit.
		return "login";
	}

}
