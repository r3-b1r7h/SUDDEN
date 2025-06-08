package biz.sudden.knowledgeData.questionnaireNew.web.controller;

import java.util.LinkedList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

import biz.sudden.knowledgeData.questionnaireNew.domain.Tick;
import biz.sudden.knowledgeData.questionnaireNew.domain.TickTemplate;
import biz.sudden.knowledgeData.questionnaireNew.service.TickService;

public class TickController {

	private Logger logger = Logger.getLogger(this.getClass());

	private String tickTemplateName;
	private List<SingleTickController> singleTickControllerList = new LinkedList<SingleTickController>();

	public List<SingleTickController> getSingleTickController() {
		return singleTickControllerList;
	}

	public void setSingleTickController(
			List<SingleTickController> singleTickController) {
		this.singleTickControllerList = singleTickController;
	}

	private TickService tickService;

	public TickService getTickService() {
		return tickService;
	}

	public void setTickService(TickService tickService) {
		this.tickService = tickService;
	}

	public void init() {
		for (TickTemplate tickTemplate : getTickService()
				.retrieveAllTickTemplates()) {
			SingleTickController controller = new SingleTickController();
			controller.setTickService(tickService);
			controller.setTickTemplate(tickTemplate);
			controller.setCurrentTickText("");
			singleTickControllerList.add(controller);
		}
	}

	public String getTickTemplateName() {
		return tickTemplateName;
	}

	public void setTickTemplateName(String tickTemplateName) {
		this.tickTemplateName = tickTemplateName;
	}

	public void createTickTemplate() {
		TickTemplate tickTemplate = tickService
				.createTickTemplate(tickTemplateName);
		tickTemplateName = "";
		SingleTickController singleTickController = new SingleTickController();
		singleTickController.setTickService(tickService);
		singleTickController.setCurrentTickText("");
		singleTickController.setTickTemplate(tickTemplate);
		this.singleTickControllerList.add(singleTickController);
	}

	public static class SingleTickController {

		private Logger logger = Logger.getLogger(this.getClass());

		private String currentTickText;
		private TickTemplate tickTemplate;
		private TickService tickService;

		public TickService getTickService() {
			return tickService;
		}

		public void setTickService(TickService tickService) {
			this.tickService = tickService;
		}

		public TickTemplate getTickTemplate() {
			return tickTemplate;
		}

		public void setTickTemplate(TickTemplate tickTemplate) {
			this.tickTemplate = tickTemplate;
		}

		public String getCurrentTickText() {
			return currentTickText;
		}

		public void setCurrentTickText(String currentTickText) {
			this.currentTickText = currentTickText;
		}

		public void addTickToTemplate(ActionEvent event) {

			Tick tick = tickService.createTick(currentTickText);

			tick.setBelongsToTickTemplate(this.tickTemplate);
			currentTickText = "";

			// currentTickText = "";
			// JSFUtils.navigateTo("showSingleQuestionnaire");
		}

		public List<Tick> getAllTicksOfTemplate() {

			return tickService.getTicksOfTickTemplate(this.tickTemplate);

		}

		public void multipleChoiceChanged(ValueChangeEvent event) {
			tickTemplate.setMultipleChoice((Boolean) event.getNewValue());
			logger.debug(tickTemplate.isMultipleChoice());
			tickService.updateTickTemplate(tickTemplate);
		}

	}

}
