package biz.sudden.designCoordination.collaborativePlanning;

import java.io.IOException;
import java.util.Date;
import java.util.EventListener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.event.authentication.AuthenticationSuccessEvent;
import org.springframework.security.event.authorization.AuthorizedEvent;
import org.springframework.security.event.authorization.PublicInvocationEvent;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.web.context.support.WebApplicationContextUtils;

import biz.sudden.baseAndUtility.domain.exception.ActorNotFoundException;
import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.baseAndUtility.web.controller.impl.UserJSFControllerImpl;

public class SuddenListener implements ApplicationListener,
		HttpSessionListener, EventListener, PhaseListener {

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(ApplicationEvent appEvent) {
		if (appEvent instanceof PublicInvocationEvent) {
			PublicInvocationEvent event = (PublicInvocationEvent) appEvent;
			if (event.getSource() instanceof FilterInvocation) {
				FilterInvocation invocation = (FilterInvocation) event
						.getSource();
				logger.debug("Request url " + invocation.getRequestUrl());
			}
		} else if (appEvent instanceof AuthorizedEvent) {
			logger.debug("Authorized !!"
					+ ((AuthorizedEvent) appEvent).getAuthentication()
							.getName());
			// special parameter to automatically redirect logged in users to
			// some page using the url: http://server:port/sudde/?goto=XXX
		} else if (appEvent instanceof AuthenticationSuccessEvent) {
			logger.debug("Authenticated !!"
					+ ((AuthenticationSuccessEvent) appEvent)
							.getAuthentication().getName());
			// SuddenListener used to be able to get the UserJSFController by
			// calling session.getAttribute("userController")
			// but now this returns null...... & code below gives a null pointer
			// exceptions -> so handle everything in UserJSFControllerImpl!
			// HttpSession session = (HttpSession)
			// FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			// loginUser(session);
		}
	}

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {

		try {

			HttpSession session = sessionEvent.getSession();

			logger.debug("Session was created " + sessionEvent.getSource());

			String userName = SecurityContextHolder.getContext()
					.getAuthentication().getName();

			if (!userName.contains("anonym")) {

				// bind username to a session attribute
				session.setAttribute("user", userName);

				logger.debug("Session created for "
						+ session.getAttribute("user"));

				// not working here?!!...> userjsfcontroller == null in
				// loginUser ,,, but just the moment before a sessionDestroyed
				// event was called and there it worked??!!
				// tried to handle this in onApplicationEvent but not working
				// there as well (can't get session);
				loginUser(session);
				// CustomSessionHandler in order to be able to show a warning
				// window
				// just before session expires
				CustomSessionHandler customSessionHandler = new CustomSessionHandler(
						sessionEvent.getSession(), 120000);
				Thread customSessionHandlerThread = new Thread(
						customSessionHandler);

				// customSessionHandlerThread.start();

				sessionEvent.getSession().setAttribute("customsession",
						customSessionHandlerThread);
				sessionEvent.getSession().setAttribute("customsessionhandler",
						customSessionHandler);

			}

		} catch (Exception ex) {
			logger.debug(ex);
		}
	}

	/**
	 * Attribute "userController" was set by the Spring application-context. in
	 * HttpSessoin After login the login method of userController is invoked in
	 * order to be able to COUNT the number of users (for chat etc.)
	 */
	protected void loginUser(HttpSession session) {

		UserJSFControllerImpl userjsfcontroller = (UserJSFControllerImpl) session
				.getAttribute("userController");

		if (userjsfcontroller != null) {
			try {
				userjsfcontroller.login();
			} catch (Exception ex) {
				logger.error("Exception during UserController login occurred "
						+ ex);
			}
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		try {

			HttpSession session = sessionEvent.getSession();

			logger.debug("Session destroyed " + session.getSessionContext());

			// FIXME ThF are there another possiblities to logout user when
			// session is destroyed (timeout...) ?
			Object object = WebApplicationContextUtils
					.getRequiredWebApplicationContext(
							session.getServletContext()).getBean(
							"baseAndUtility.userService");

			UserJSFControllerImpl userjsfcontroller = (UserJSFControllerImpl) session
					.getAttribute("userController");
			if (userjsfcontroller != null)
				userjsfcontroller.logout();
			((UserService) object).logoutUser((String) session
					.getAttribute("user"));
		} catch (ActorNotFoundException anfe) {
			// admin is not in DB so we can ignor this exception
			// else log
			if (anfe.getLocalizedMessage().indexOf("admin") == -1)
				logger.error("Exception during session destroyed ... " + anfe);
		} catch (Exception ex) {
			logger.error("Exception during session destroyed ... " + ex);
		}

	}

	@Override
	public void afterPhase(PhaseEvent arg0) {

	}

	@Override
	public void beforePhase(PhaseEvent arg0) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	// Custom Session Handler, currently not used. This custom handler should
	// check whether session expires soon and show a warning window

	class CustomSessionHandler implements Runnable {

		private long millisTimeout;
		private HttpSession session;
		private boolean warningShowed = false;
		private long lastAccessTime;
		private UserJSFControllerImpl userjsfcontroller;

		public void setLastAccessTime(long accessTime) {
			this.lastAccessTime = accessTime;
		}

		public CustomSessionHandler(HttpSession session, long millisTimeout) {
			this.millisTimeout = millisTimeout;
			this.session = session;
			this.lastAccessTime = session.getLastAccessedTime();
		}

		@Override
		public void run() {

			Thread thisThread = Thread.currentThread();

			Thread customSessionHandlerThread = (Thread) session
					.getAttribute("customsession");

			while (thisThread == customSessionHandlerThread) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					logger.debug("InterruptedException " + ex);
				}

				userjsfcontroller = (UserJSFControllerImpl) session
						.getAttribute("userController");

				logger.debug("request render ... "
						+ userjsfcontroller.getCurrentUser());

				try {
					userjsfcontroller.login();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				long now = new Date().getTime();

				long inactivity = now - lastAccessTime;

				if (inactivity > this.millisTimeout) {
					// session.invalidate();
				}

				if (inactivity > this.millisTimeout
						&& !userjsfcontroller.isShowWarning()) {
					// FIXME make this with renderManager !!
					userjsfcontroller.showWarning();
					warningShowed = true;
				}

				if (inactivity > this.millisTimeout) {
					// session.invalidate();
				}

			}

		}

		public UserJSFControllerImpl getUserjsfcontroller() {
			return userjsfcontroller;
		}

	}

}
