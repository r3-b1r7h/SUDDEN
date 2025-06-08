package biz.sudden;

import javax.servlet.ServletRequestEvent;

import org.springframework.web.context.request.RequestContextListener;

public class SuddenRequestContextListener extends RequestContextListener {

	@Override
	public void requestInitialized(ServletRequestEvent requestEvent) {
		// TODO Auto-generated method stub
		System.out.println("Request initialised");
	}

	@Override
	public void requestDestroyed(ServletRequestEvent requestEvent) {
		System.out.println("request destroyed");

	}

}