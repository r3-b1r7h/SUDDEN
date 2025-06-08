package biz.sudden.knowledgeData.serviceManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;

public interface TechnologyRepository extends
		GenericRepository<Technology, Long> {

	public Technology retrieveTechnologyBy(String name);

	public List<Technology> retrieveAll();
}
