package biz.sudden.baseAndUtility.repository.hibernate;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.repository.SuddenGenericRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class SuddenGenericRepositoryImpl extends
		GenericRepositoryImpl<Connectable, Long> implements
		SuddenGenericRepository {

	public SuddenGenericRepositoryImpl() {
		// TODO Auto-generated constructor stub
		super(Connectable.class);
	}

	public SuddenGenericRepositoryImpl(Class<Connectable> type) {
		super(type);
	}

	@Override
	public void setType(Class type) {
		this.type = type;
	}

}
