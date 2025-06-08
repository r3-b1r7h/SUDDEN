package biz.sudden.baseAndUtility.service;

import biz.sudden.baseAndUtility.domain.Actor;
import biz.sudden.baseAndUtility.repository.ActorRepository;

public interface ActorService {

	public Actor createActor(String name);

	public Actor retrieveActor(String name);

	public ActorRepository getRepository();

	public void setRepository(ActorRepository repository);

}
