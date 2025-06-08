package biz.sudden.baseAndUtility.web.controller;

public interface ShutdownController {

	public void setShutdown(int waitMinutes, boolean shutdown, String userName,
			String password) throws InterruptedException;

}
