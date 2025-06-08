package biz.sudden.baseAndUtility.service.impl;

import biz.sudden.baseAndUtility.domain.Actor;
import biz.sudden.baseAndUtility.repository.ActorRepository;
import biz.sudden.baseAndUtility.service.ActorService;

public class ActorServiceImpl implements ActorService {

	private ActorRepository repository;

	@Override
	public Actor createActor(String name) {
		// TODO Auto-generated method stub
		Actor actor = new Actor();
		actor.setName(name);
		if (repository.retrieve(name) == null)
			repository.create(actor);
		return actor;

	}

	@Override
	public Actor retrieveActor(String name) {
		// TODO Auto-generated method stub
		return repository.retrieve(name);
	}

	public ActorRepository getRepository() {
		return repository;
	}

	public void setRepository(ActorRepository repository) {
		this.repository = repository;
	}

}
