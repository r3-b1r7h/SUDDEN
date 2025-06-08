package biz.sudden.baseAndUtility.web.controller.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.baseAndUtility.web.controller.ShutdownController;

import com.icesoft.faces.async.render.OnDemandRenderer;
import com.icesoft.faces.async.render.RenderManager;

@Component
public class ShutdownControllerImpl implements ShutdownController {

	private RenderManager manager;
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private boolean shutdown = false;

	public boolean isShutdown() {
		return shutdown;
	}

	@Override
	public void setShutdown(int waitMinutes, boolean shutdown, String username,
			String password) throws InterruptedException {
		UserDetails user = null;
		user = userService.loadUserByUsername(username);
		if (user != null) {
			if (user.getPassword().equals(password)) {
				System.out.println("Shutdown was set to " + shutdown);
				this.shutdown = shutdown;
				OnDemandRenderer renderer = manager
						.getOnDemandRenderer("loginLogout");
				renderer.requestRender();
				if (shutdown) {
					Thread.sleep(waitMinutes * 60l * 1000l);
					shutDownTomcat();
				}
			}

		}

	}

	public RenderManager getManager() {
		return manager;
	}

	public void setManager(RenderManager manager) {
		this.manager = manager;
	}

	public void shutDownTomcat() {
		InetAddress serverAddress = null;
		int serverPortNum = 8005;
		Socket socket = null;
		String magicWord = "SHUTDOWN";
		byte[] buffer = null;
		OutputStream os = null;
		try {
			/*
			 * Change to InetAddress.getHostByName(somehostname) if client is
			 * running from a remote machine
			 */
			serverAddress = InetAddress.getByName("localhost");
			socket = new Socket(serverAddress, serverPortNum);
			os = socket.getOutputStream();
			buffer = magicWord.getBytes();
			os.write(buffer, 0, buffer.length);
			os.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				os = null;
			}
		}
	}
}
