package biz.sudden.baseAndUtility.service.impl;

import biz.sudden.baseAndUtility.domain.PrimitiveType;
import biz.sudden.baseAndUtility.repository.PrimitiveValueRepository;
import biz.sudden.baseAndUtility.service.PrimitiveValueService;

public class PrimitiveValueServiceImpl implements PrimitiveValueService {

	private PrimitiveValueRepository primitiveValueRepository;

	public PrimitiveValueRepository getPrimitiveValueRepository() {
		return primitiveValueRepository;
	}

	public void setPrimitiveValueRepository(
			PrimitiveValueRepository primitiveValueRepository) {
		this.primitiveValueRepository = primitiveValueRepository;
	}

	@Override
	public Long create(PrimitiveType type) {
		// TODO Auto-generated method stub
		return primitiveValueRepository.create(type);
	}

	@Override
	public PrimitiveType get(Long id) {
		// TODO Auto-generated method stub
		return primitiveValueRepository.retrieve(id);
	}

}
