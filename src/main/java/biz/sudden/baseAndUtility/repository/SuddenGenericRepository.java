package biz.sudden.baseAndUtility.repository;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface SuddenGenericRepository extends
		GenericRepository<Connectable, Long> {

	/** type is needed by retrieve methods */
	public void setType(Class<? extends Connectable> type);

}
