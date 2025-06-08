package biz.sudden.baseAndUtility.repository.hibernate;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import biz.sudden.baseAndUtility.domain.Actor;
import biz.sudden.baseAndUtility.repository.ActorRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class ActorRepositoryImpl extends GenericRepositoryImpl<Actor, Long>
		implements ActorRepository {

	public ActorRepositoryImpl() {
		super(Actor.class);
	}

	public ActorRepositoryImpl(Class<Actor> type) {
		super(Actor.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Actor retrieve(final String name) {
		return (Actor) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session
						.createQuery("from Actor where name = :name");
				query.setString("name", name);
				Object result = query.uniqueResult();
				return result;
			}
		});
	}

}
