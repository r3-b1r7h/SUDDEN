package biz.sudden.designCoordination.collaborativePlanning.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.designCoordination.collaborativePlanning.domain.CommunicationContainer;

public interface CommunicationContainerRepository extends
		GenericRepository<CommunicationContainer, Long> {

	public List<CommunicationContainer> getAllCommunicationContainer();

}
