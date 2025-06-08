package biz.sudden.knowledgeData.serviceManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;

public interface MaterialRepository extends GenericRepository<Material, Long> {

	public List<Material> retrieveMaterialBy(String name);

	public List<Material> retrieveAll();

}
