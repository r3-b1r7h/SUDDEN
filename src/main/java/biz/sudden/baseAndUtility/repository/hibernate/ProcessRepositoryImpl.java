package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.repository.ProcessRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class ProcessRepositoryImpl extends GenericRepositoryImpl<Process, Long>
		implements ProcessRepository {

	public ProcessRepositoryImpl() {
		super(Process.class);
	}

	public ProcessRepositoryImpl(Class<Process> type) {
		super(type);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Process> retrieveAll() {
		return getHibernateTemplate().loadAll(Process.class);
	}

	@SuppressWarnings("unchecked")
	public List<Process> retrieveProcessBy(String name) {
		// DetachedCriteria query = DetachedCriteria.forClass(Process.class)
		// .add( Property.forName("name").eq(name) );
		// return getHibernateTemplate().findByCriteria(query);
		return getHibernateTemplate().findByNamedParam(
				"from Process p " + "where p.name = :name", "name", name);

	}

}
