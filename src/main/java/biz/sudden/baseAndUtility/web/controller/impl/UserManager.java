package biz.sudden.baseAndUtility.web.controller.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.icesoft.faces.async.render.GroupAsyncRenderer;

/***
 * 
 * This application scoped User Manager tracks all currently logged in users and
 * requests a render on every login/logout event. The various controllers are
 * able to become this Manager injected (with the aid of Spring) and then are
 * able to retrieve a list of currently logged in users.
 * 
 * @author thomasfeiner
 * 
 */

public class UserManager {

	private Logger logger = Logger.getLogger(this.getClass());

	private List<String> loggedInUsers = new ArrayList<String>();
	private GroupAsyncRenderer renderer;

	// private String currentUser;

	public GroupAsyncRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(GroupAsyncRenderer renderer) {
		this.renderer = renderer;
	}

	public void loginUser(String user) {
		// currentUser = user;
		if (user != null && user.length() > 0)
			loggedInUsers.add(user);
		renderer.requestRender();
	}

	public void logoutUser(String user) {
		loggedInUsers.remove(user);
	}

	public int getNrOfLoggedinUsers() {
		// logger.debug("Logged in Users : "+loggedInUsers);
		return loggedInUsers.size();
	}

	/**
	 * 
	 * 
	 * 
	 * @param withoutThisUser
	 *            The currently logged in user should not be shown in a
	 *            "logged in users"-list, so this user can be excluded through
	 *            this parameter
	 * @return a list with all users (each item has icefaces type "SelectItem")
	 */
	public List<SelectItem> getUserList(String withoutThisUser) {
		List<SelectItem> myUserList = new ArrayList<SelectItem>();
		for (String user : loggedInUsers) {
			if (!user.equals(withoutThisUser)) {
				// Fixme ThF maybe find a more loose coupled solution?
				SelectItem item = new SelectItem(user);
				myUserList.add(item);
			}
		}
		return myUserList;

	}

}
