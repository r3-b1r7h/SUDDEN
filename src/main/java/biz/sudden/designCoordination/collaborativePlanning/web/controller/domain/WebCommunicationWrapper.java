package biz.sudden.designCoordination.collaborativePlanning.web.controller.domain;

import org.apache.log4j.Logger;

import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.web.controller.impl.CpController;

public class WebCommunicationWrapper extends Communication {

	private Logger logger = Logger.getLogger(this.getClass());

	private CpController cpController;
	private Communication communication;

	public Communication getCommunication() {
		return communication;
	}

	public void setCommunication(Communication communication) {
		this.communication = communication;
	}

	public CpController getCpController() {
		return cpController;
	}

	public void setCpController(CpController cpController) {
		this.cpController = cpController;
	}

	public WebCommunicationWrapper(CpController controller) {
		// TODO Auto-generated constructor stub
		this.cpController = controller;
	}

	public void openCommItem() {
		logger.debug("Open in Wrapper");
		this.cpController.openCommunication(this);
	}

}
