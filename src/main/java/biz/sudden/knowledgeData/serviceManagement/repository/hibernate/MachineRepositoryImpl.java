package biz.sudden.knowledgeData.serviceManagement.repository.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.serviceManagement.domain.Machine;
import biz.sudden.knowledgeData.serviceManagement.repository.MachineRepository;

public class MachineRepositoryImpl extends GenericRepositoryImpl<Machine, Long>
		implements MachineRepository {

	public MachineRepositoryImpl() {
		super(Machine.class);
	}

	public MachineRepositoryImpl(Class<Machine> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Machine retrieveMachineBy(String name) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Machine.class);
		query.add(Restrictions.eq("name", name));

		// DetachedCriteria query = DetachedCriteria.forClass(Machine.class)
		// .add( Property.forName("name").eq(name) );
		// List<Machine> mL = getHibernateTemplate().findByCriteria(query);
		List<Machine> mL = query.list();
		if (mL.isEmpty())
			return null;
		return mL.iterator().next();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Machine> retrieveAll() {
		return getHibernateTemplate().loadAll(Machine.class);
	}

}
