package biz.sudden.designCoordination.collaborativePlanning.service.communication;

import java.util.List;

import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.designCoordination.collaborativePlanning.domain.TopicRating;

public class RatingService {

	private SuddenGenericRepository genericRepository;

	public SuddenGenericRepository getGenericRepository() {
		return genericRepository;
	}

	public void setGenericRepository(SuddenGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	public List<TopicRating> getTopicsRating() {
		return genericRepository.retrieveAllByType(TopicRating.class);
	}

}
