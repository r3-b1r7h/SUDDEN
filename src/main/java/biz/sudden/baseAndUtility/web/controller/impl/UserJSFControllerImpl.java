package biz.sudden.baseAndUtility.web.controller.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.domain.exception.UserAlreadyExistsException;
import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.baseAndUtility.web.controller.UserJSFController;
import biz.sudden.designCoordination.collaborativePlanning.domain.ChatRoom;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.ChatManager;

import com.icesoft.faces.async.render.IntervalRenderer;
import com.icesoft.faces.async.render.OnDemandRenderer;
import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

@Component
public class UserJSFControllerImpl implements UserJSFController,
		DisposableBean, Renderable {

	private PersistentFacesState state;
	private RenderManager manager;
	private IntervalRenderer renderer;
	private OnDemandRenderer ondemandRenderer;
	private OnDemandRenderer showChatPopupRenderer;
	private ShutdownControllerImpl shutdownController;
	private Locale locale = Locale.GERMAN;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public ShutdownControllerImpl getShutdownController() {
		return shutdownController;
	}

	public void setShutdownController(ShutdownControllerImpl shutdownController) {
		this.shutdownController = shutdownController;
	}

	public OnDemandRenderer getShowChatPopupRenderer() {
		return showChatPopupRenderer;
	}

	public void setShowChatPopupRenderer(OnDemandRenderer showChatPopupRenderer) {
		this.showChatPopupRenderer = showChatPopupRenderer;
	}

	private HttpSession attachedSession;
	private UserManager userManager;
	private int nrOfLoggedInUsers;
	private boolean loggedIn = false;
	private Logger logger = Logger.getLogger(this.getClass());
	private boolean showWarning = true;
	private UserService userService;
	private String username;
	private String password;
	private String nickname;
	private String currentUser = "";
	private String errorMessage = "";
	private List userList = new ArrayList();
	private String selectedUser;
	private UIViewRoot viewRoot;
	// private List<String> chatList = new ArrayList<String>();

	private ChatManager chatManager;

	public ChatManager getChatManager() {
		return chatManager;
	}

	public void setChatManager(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	// private ApplicationController applicationController;
	//
	// // private UserConverter converter;
	//
	// public ApplicationController getApplicationController() {
	// return applicationController;
	// }
	//
	// public void setApplicationController(ApplicationController
	// applicationController) {
	// this.applicationController = applicationController;
	// }

	public void chat(ActionEvent event) throws Exception {
		// logger.debug("Application Contorller "+applicationController);
		// FIXME ThF
		// chatList.add(currentUser);

		ChatRoom chatRoom = new ChatRoom();

		chatManager.addChatRoom(chatRoom);

		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		chatManager.addUserToChat((SuddenUser) principal, chatRoom);

		chatManager.addUserToChat(selectedUser, chatRoom);

		// Thread.sleep(200);

		showChatPopupRenderer.requestRender();

		// showChatPopupRenderer.
		// applicationController.showPopup();

	}

	// public List<String> getChatList() {
	// return chatList;
	// }
	//
	// public void setChatList(List chatList) {
	// this.chatList = chatList;
	// }

	public String getSelectedUser() {
		logger.debug("GET SELECTED USER in usercontroller " + selectedUser);
		return selectedUser;
	}

	public void setSelectedUser(String selectedUser) {
		logger.debug("SET SELECTED USER in usercontroller " + selectedUser);
		this.selectedUser = selectedUser;
	}

	public List<SelectItem> getUserList() {
		// List<SelectItem> myList = new ArrayList<SelectItem>();
		return userManager.getUserList(this.currentUser);
	}

	public PersistentFacesState getState() {
		return state;
	}

	public void setState(PersistentFacesState state) {
		this.state = state;
	}

	public UserJSFControllerImpl() {
		// this is not possible as user is not logged in..
		// state = PersistentFacesState.getInstance();
		logger.debug("Constructor of UserJSFControllerImpl called");
	}

	@Override
	public void init() {
		try {
			// init called when User browses to homepage; but at that time he is
			// not logged in! no security context!!
			// login();
			attachedSession = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(true);

			renderer.requestRender();
		} catch (Exception ex) {
			logger.debug(ex);
		}
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;

	}

	public RenderManager getManager() {
		return manager;
	}

	public void setManager(RenderManager manager) {

		if (this.manager == null) {
			this.manager = manager;

			ondemandRenderer = manager.getOnDemandRenderer("loginLogout");
			ondemandRenderer.add(this);

			setShowChatPopupRenderer(manager
					.getOnDemandRenderer("chatRenderer"));
			state = PersistentFacesState.getInstance();
			showChatPopupRenderer.add(this);
			showChatPopupRenderer.setRenderManager(manager);

			showChatPopupRenderer.requestRender();

			ondemandRenderer.requestRender();

			renderer = manager.getIntervalRenderer("loggedinuser");

			renderer.setInterval(30000);
			logger.debug("Broadcasted " + renderer.isBroadcasted());
			// renderer.setBroadcasted(true);
			renderer.add(this);

			renderer.requestRender();
			// renderer.
			userManager.setRenderer(renderer);
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	/** Getters and Setters **/
	public String getUsername() {
		return username;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String submit() {
		logger.debug("Submit " + username + " " + nickname);
		try {
			userService.createUser(username, password, nickname);
		} catch (UserAlreadyExistsException ex) {
			setErrorMessage("User exists already");
			return "";

		}
		setErrorMessage("");
		setUsername("");
		setPassword("");
		setNickname("");
		return "home";
	}

	public String createCommon() {
		logger.debug("Submit " + username + " " + nickname);
		try {
			userService.createUser("thomas", "thomas", "Thomas", 1l);
			userService.createUser("matthias", "matthias", "Matthias", 2l);
			userService.createUser("georg", "georg", "Georg", 3l);
		} catch (UserAlreadyExistsException ex) {
			setErrorMessage("User exists already");
			return "";

		}
		setErrorMessage("");
		setUsername("");
		setPassword("");
		setNickname("");
		return "home";
	}

	public String toggleLocale() {

		if (getLocale().equals(Locale.ENGLISH)) {
			setLocale(Locale.GERMAN);
		} else {
			setLocale(Locale.ENGLISH);
		}

		// FacesContext context = FacesContext.getCurrentInstance();
		//
		// Locale currentLocale = context.getViewRoot().getLocale();
		//
		// Locale en = new Locale("en");
		// if (currentLocale.equals(en)) {
		// context.getViewRoot().setLocale(Locale.GERMAN);
		// } else {
		// context.getViewRoot().setLocale(en);
		// }

		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String getLoggedInUsername() {
		try {
			String userName = SecurityContextHolder.getContext()
					.getAuthentication().getName();
			if (this.currentUser.length() == 0 && userName.length() > 0) {
				// SuddenListener used to be able to get the UserJSFController
				// by calling session.getAttribute("userController")
				// but now this returns null...... so when the currentUser is
				// not set && but userName is -> login.
				this.currentUser = userName;
				login();
			}
			return userName;
		} catch (Exception ex) {
			return this.currentUser;
		}

	}

	/**
	 * 
	 * @return
	 */
	public SuddenUser getLoggedInUser() {

		return userService.retrieveUser(getLoggedInUsername());
	}

	public void login() throws IOException {
		state = PersistentFacesState.getInstance();
		String userName = getLoggedInUsername();
		userManager.loginUser(userName);
		userService.loginUser(userName);

		// exceptions thrown :(
		renderer.requestRender();
		ondemandRenderer.requestRender();

		// if (!loggedIn) {
		// userManager.loginUser(this);
		// renderer.requestRender();
		// loggedIn = true;
		// }

		// ExternalContext external =
		// FacesContext.getCurrentInstance().getExternalContext();
		//		
		// String username =
		// external.getRequestParameterMap().get("j_username");
		// String password =
		// external.getRequestParameterMap().get("j_password");
		//		
		//		
		// //external.setRequest("asdfasdf");
		//		
		// external.redirect("/sudden/login.iface");

		// external.redirect("/sudden/login?j_username="+username+"&j_password="+
		// password);
		//		
		//		
		// System.out.println(FacesContext.getCurrentInstance().getExternalContext
		// ().getRequestParameterNames());
		//		
		// Iterator it = FacesContext.getCurrentInstance().getExternalContext().
		// getRequestParameterNames();
		//		
		// while (it.hasNext()) {
		// System.out.println(it.next());
		// }
		//		
		// System.exit(0);

	}

	public void logout() {

		userService.logoutUser(this.currentUser);
		userManager.logoutUser(this.currentUser);

		renderer.requestRender();
		ondemandRenderer.requestRender();

		loggedIn = false;

		FacesContext facescontext = FacesContext.getCurrentInstance();

		try {
			facescontext.getExternalContext().redirect("/sudden/logout");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException ex) {

		}

		renderer.requestRender();
	}

	public String loginAs() {

		final UserDetails details = userService.loadUserByUsername(username);

		// userManager.loginUser(this);

		// Authentication auth =
		// SecurityContextHolder.getContext().getAuthentication();
		// logger.debug("credentials "+auth.getCredentials());
		// logger.debug(auth.getDetails());
		// logger.debug(auth.getName());
		// logger.debug(auth.getPrincipal());
		// logger.debug(auth.getAuthorities());

		Authentication newAuth = new Authentication() {

			@Override
			public GrantedAuthority[] getAuthorities() {
				// TODO Auto-generated method stub
				return details.getAuthorities();
			}

			@Override
			public Object getCredentials() {
				// TODO Auto-generated method stub
				return details.getUsername();
			}

			@Override
			public Object getDetails() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getPrincipal() {
				// TODO Auto-generated method stub
				return details;
			}

			@Override
			public boolean isAuthenticated() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void setAuthenticated(boolean arg0)
					throws IllegalArgumentException {
				// TODO Auto-generated method stub
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return details.getUsername();
			}
		};

		SecurityContextHolder.getContext().setAuthentication(newAuth);

		// try {
		// userService.createUser("thomas","thomas","Thomas");
		// userService.createUser("matthias","matthias","Matthias");
		// userService.createUser("georg","georg","Georg");
		// } catch (UserAlreadyExistsException ex) {
		// setErrorMessage("User exists already");
		// return "";
		//			
		// }
		// setErrorMessage("");
		// setUsername("");
		// setPassword("");
		// setNickname("");
		return "home";
	}

	public boolean isShowWarning() {
		return shutdownController.isShutdown();
	}

	public void setShowWarning(boolean showWarning) {
		this.showWarning = showWarning;
	}

	public void showWarning() {

		setShowWarning(true);
		// renderer.requestRender();
		// getManager().requestRender(this);
	}

	public String hideWarning() {
		showWarning = false;
		UserDetails user = userService.loadUserByUsername("admin");
		try {
			shutdownController.setShutdown(0, false, "admin", user
					.getPassword());
		} catch (InterruptedException ex) {

		}
		return null;
	}

	@Override
	public void destroy() throws Exception {
		RenderManager manager = new RenderManager() {
			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				super.dispose();
			}
		};

	}

	public int getNrOfUsers() {
		return userManager.getNrOfLoggedinUsers();
	}

	public void setNrOfUsers(int nrOfLoggedInUsers) {
		this.nrOfLoggedInUsers = nrOfLoggedInUsers;
	}

	@Override
	public void renderingException(RenderingException arg0) {
		// TODO Auto-generated method stub
		logger.debug("Rendering Exception " + this.getCurrentUser() + " "
				+ arg0);

		try {
			// attachedSession.invalidate();
			// logger.debug(attachedSession + " invalidated");

		} catch (NullPointerException ex) {
			try {
				logger.debug("There is no session for user "
						+ this.currentUser
						+ " "
						+ state.getFacesContext().getExternalContext()
								.getSession(false));
			} catch (Exception ex2) {
				logger.debug("There is really no session for user "
						+ this.currentUser + " " + ex2);
			}
		}
		// renderer.remove(this);
		// ondemandRenderer.remove(this);
		// logout();

	}

	class UserConverter implements Converter {

		private List list;

		public UserConverter(List list) {
			// TODO Auto-generated constructor stub
			this.list = list;
		}

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1,
				String arg2) {

			System.out.println("String " + arg2);
			return new SelectItem(arg2);

		}

		public List getList() {
			return list;
		}

		public void setList(List list) {
			this.list = list;
		}

		@Override
		public String getAsString(FacesContext arg0, UIComponent arg1,
				Object arg2) {
			System.out.println("GetAsString " + arg2);

			return (String) arg2;
		}

	}

}
