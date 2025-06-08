package biz.sudden.knowledgeData.competencesManagement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.competencesManagement.domain.Tick;
import biz.sudden.knowledgeData.competencesManagement.repository.ITickRepository;

public class TickRepositoryImpl extends GenericRepositoryImpl<Tick, Long>
		implements ITickRepository {

	public TickRepositoryImpl() {
		super(Tick.class);
	}

	public TickRepositoryImpl(Class<Tick> type) {
		super(Tick.class);
	}

	@Override
	public long addTick(Tick tick) {
		long id = create(tick);
		tick.setId(id);
		System.out.println("Adding Tick to repository...");
		System.out.println(tick.toString());

		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tick> getAllTicks() {
		return getHibernateTemplate().loadAll(Tick.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Tick getTickById(long id) {
		List<Tick> listOfTicks = getHibernateTemplate().find(
				"from Tick as t where t.id = ?", id);
		if (listOfTicks.size() > 0) {
			return listOfTicks.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void removeAllTicks() {
		List<Tick> tick = getAllTicks();
		getHibernateTemplate().deleteAll(tick);
	}

}