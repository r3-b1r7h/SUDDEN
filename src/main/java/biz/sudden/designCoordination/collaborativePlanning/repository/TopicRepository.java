package biz.sudden.designCoordination.collaborativePlanning.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.designCoordination.collaborativePlanning.domain.Topic;

public interface TopicRepository extends GenericRepository<Topic, Long> {

	public List<Topic> retrieveAll();
}
