package biz.sudden.knowledgeData.questionnaireNew.service;

import java.util.List;

import biz.sudden.knowledgeData.questionnaireNew.domain.Tick;
import biz.sudden.knowledgeData.questionnaireNew.domain.TickTemplate;

public interface TickService {

	public void createTick(Tick tick);

	public Tick createTick(String tickName);

	public TickTemplate createTickTemplate(String templateName);

	public List<Tick> getTicksOfTickTemplate(TickTemplate tickTemplate);

	public void addTickToTickTemplate(Tick tick, TickTemplate tickTemplate);

	public void addTickToTickTemplate(Tick tick, String tickTemplateName);

	public List<TickTemplate> retrieveAllTickTemplates();

	public void updateTickTemplate(TickTemplate tickTemplate);

	public TickTemplate getTickTemplateWithID(Long id);

	public void updateTick(Tick tick);

	public Tick getTickWithId(Long id);
}
