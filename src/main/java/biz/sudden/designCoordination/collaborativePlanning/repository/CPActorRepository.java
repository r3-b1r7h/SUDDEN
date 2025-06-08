package biz.sudden.designCoordination.collaborativePlanning.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.exception.ActorNotFoundException;
import biz.sudden.baseAndUtility.domain.exception.AmbiguousActorException;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.designCoordination.collaborativePlanning.domain.CP_Actor;

public interface CPActorRepository extends GenericRepository<CP_Actor, Long> {

	public List<CP_Actor> getAllActors();

	public CP_Actor retrieve(String actorString)
			throws AmbiguousActorException, ActorNotFoundException;

	// public CP_Actor retrieve(Actor actor);
	//	
	// public Actor retrieve(String actor);
	//	
	// public Long create(Actor actor);

	// public CommunicationContainer getCommunicationContainer(Actor actor);
	//	
	// public CommunicationContainer getCommunicationContainer(CP_Actor
	// cpActor);
	//	
	// public CommunicationContainer createCommunicationContainer(Actor actor);
	//	
	// public CommunicationContainer createCommunicationContainer(CP_Actor
	// cpActor);

}
