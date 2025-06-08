package biz.sudden.baseAndUtility.repository;

import biz.sudden.baseAndUtility.domain.Actor;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface ActorRepository extends GenericRepository<Actor, Long> {

	public Actor retrieve(String name);

}