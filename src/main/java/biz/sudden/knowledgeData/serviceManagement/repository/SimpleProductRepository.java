package biz.sudden.knowledgeData.serviceManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.serviceManagement.domain.SimpleProduct;

public interface SimpleProductRepository extends
		GenericRepository<SimpleProduct, Long> {

	public List<SimpleProduct> retrieveSimpleProductBy(String name);

	public List<SimpleProduct> retrieveAll();
}
