package biz.sudden.knowledgeData.serviceManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct;

public interface ComplexProductRepository extends
		GenericRepository<ComplexProduct, Long> {

	public List<ComplexProduct> retrieveComplexProductBy(String name);

}
