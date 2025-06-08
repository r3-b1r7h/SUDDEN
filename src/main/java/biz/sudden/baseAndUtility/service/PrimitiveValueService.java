package biz.sudden.baseAndUtility.service;

import biz.sudden.baseAndUtility.domain.PrimitiveType;

public interface PrimitiveValueService {

	public Long create(PrimitiveType type);

	public PrimitiveType get(Long id);

}
