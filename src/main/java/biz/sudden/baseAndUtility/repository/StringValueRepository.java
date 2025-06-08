package biz.sudden.baseAndUtility.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.connectable.StringValue;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface StringValueRepository extends
		GenericRepository<StringValue, Long> {

	public List<StringValue> retrieveStringBy(String value);
}
