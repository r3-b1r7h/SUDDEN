package biz.sudden.designCoordination.collaborativePlanning.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;

public interface CommunicationRepository extends
		GenericRepository<Communication, Long> {

	public List<Communication> getAllCommunications();

	public List<Communication> getCommunicationFor(SuddenUser user);

}
