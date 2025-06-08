package biz.sudden.baseAndUtility.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.sudden.baseAndUtility.web.controller.ShutdownController;

public class ShutdownWarning {

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"biz/sudden/spring/test.xml");
		ShutdownController shutdownController = (ShutdownController) ctx
				.getBean("shutdownControllerClient");
		shutdownController.setShutdown(1, true, "admin", "nik1kurt2");
	}

}
