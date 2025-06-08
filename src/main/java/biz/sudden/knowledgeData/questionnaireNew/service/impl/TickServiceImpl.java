package biz.sudden.knowledgeData.questionnaireNew.service.impl;

import java.util.List;

import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.knowledgeData.questionnaireNew.domain.Tick;
import biz.sudden.knowledgeData.questionnaireNew.domain.TickTemplate;
import biz.sudden.knowledgeData.questionnaireNew.service.TickService;

public class TickServiceImpl implements TickService {

	private SuddenGenericRepository genericRepository;

	public SuddenGenericRepository getGenericRepository() {
		return genericRepository;
	}

	public void setGenericRepository(SuddenGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	@Override
	public List<Tick> getTicksOfTickTemplate(TickTemplate tickTemplate) {
		System.out.println(genericRepository.containsObject(tickTemplate));
		return genericRepository.retrieveAllChildren(tickTemplate, Tick.class);
	}

	@Override
	public void createTick(Tick tick) {
		genericRepository.create(tick);
	}

	public Tick createTick(String tickName) {
		Tick tick = new Tick();
		tick.setTickText(tickName);
		genericRepository.create(tick);
		return tick;
	}

	public void addTickToTickTemplate(Tick tick, TickTemplate tickTemplate) {
		genericRepository.create(tick);
		// tick.setBelongsToTickTemplate(tickTemplate);
	}

	public void addTickToTickTemplate(Tick tick, String tickTemplateName) {
		TickTemplate tickTemplate = new TickTemplate();
		tickTemplate.setName(tickTemplateName);
		genericRepository.update(tickTemplate);
		tick.setBelongsToTickTemplate(tickTemplate);
	}

	@Override
	public List<TickTemplate> retrieveAllTickTemplates() {
		// TODO Auto-generated method stub
		return genericRepository.retrieveAllByType(TickTemplate.class);
	}

	@Override
	public TickTemplate createTickTemplate(String templateName) {
		TickTemplate tickTemplate = new TickTemplate();
		tickTemplate.setName(templateName);
		genericRepository.create(tickTemplate);
		return tickTemplate;
	}

	@Override
	public void updateTickTemplate(TickTemplate tickTemplate) {
		// TODO Auto-generated method stub
		genericRepository.update(tickTemplate);
	}

	@Override
	public TickTemplate getTickTemplateWithID(Long id) {
		// TODO Auto-generated method stub
		return genericRepository.retrieveByTypeAndId(TickTemplate.class, id);
	}

	@Override
	public void updateTick(Tick tick) {
		// TODO Auto-generated method stub
		genericRepository.update(tick);
	}

	@Override
	public Tick getTickWithId(Long id) {
		// TODO Auto-generated method stub
		return genericRepository.retrieveByTypeAndId(Tick.class, id);
	}

}
