package biz.sudden.baseAndUtility.repository.hibernate;

import biz.sudden.baseAndUtility.domain.PrimitiveType;
import biz.sudden.baseAndUtility.repository.PrimitiveValueRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class PrimitiveValueRepositoryImpl extends
		GenericRepositoryImpl<PrimitiveType, Long> implements
		PrimitiveValueRepository {

	public PrimitiveValueRepositoryImpl() {
		super(PrimitiveType.class);
		// TODO Auto-generated constructor stub
	}
}
