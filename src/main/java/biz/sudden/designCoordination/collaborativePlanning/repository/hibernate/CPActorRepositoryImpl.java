package biz.sudden.designCoordination.collaborativePlanning.repository.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import biz.sudden.baseAndUtility.domain.Actor;
import biz.sudden.baseAndUtility.domain.exception.ActorNotFoundException;
import biz.sudden.baseAndUtility.domain.exception.AmbiguousActorException;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.designCoordination.collaborativePlanning.domain.CP_Actor;
import biz.sudden.designCoordination.collaborativePlanning.repository.CPActorRepository;

class CPActorRepositoryImpl extends GenericRepositoryImpl<CP_Actor, Long>
		implements CPActorRepository {

	public CPActorRepositoryImpl() {
		super(CP_Actor.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CP_Actor> getAllActors() {
		// TODO Auto-generated method stub
		return (List<CP_Actor>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(final Session session)
							throws HibernateException, SQLException {
						// TODO Auto-generated method stub
						final Query query = session
								.createQuery("from CP_Actor");
						return query.list();
					}

				});
		// return getHibernateTemplate().loadAll(CP_Actor.class);
	}

	// @Override
	// public Long create(final CP_Actor o) {
	// logger.debug("Create new users "+o);
	// Actor actor = new Actor();
	// o.setActor(actor);
	// return (Long)getHibernateTemplate().execute(new HibernateCallback() {
	//
	// @Override
	// public Object doInHibernate(Session session) throws HibernateException,
	// SQLException {
	// // TODO Auto-generated method stub
	// Serializable id = session.save(o);
	// session.flush();
	// return id;
	//				
	// }
	//			
	// });
	// //return (Long) getHibernateTemplate().save(o);
	// }

	@Override
	public void update(CP_Actor o) {
		super.update(o);
	}

	@Override
	public void delete(CP_Actor o) {
		super.delete(o);
	}

	@Override
	public CP_Actor retrieve(Long id) {
		// TODO Auto-generated method stub
		// Pessimistic locking
		// getHibernateTemplate().get(CP_Actor.class, id, LockMode.UPGRADE);
		return super.retrieve(id);
	}

	@Override
	public CP_Actor retrieve(final String actorName)
			throws AmbiguousActorException, ActorNotFoundException {
		Object callback = getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session
								.createQuery("from Actor actor where actor.name = :name");
						query.setString("name", actorName);
						if (query.list().size() > 1) {
							return new AmbiguousActorException("Actor "
									+ actorName + " is ambiguous!");
						} else if (query.list().size() < 1)
							return new ActorNotFoundException("Actor "
									+ actorName + " was not found!");
						Object result = query.uniqueResult();
						if (result == null) {
							return null;
						}
						Actor actor = (Actor) query.uniqueResult();

						CP_Actor cpActor = new CP_Actor();
						cpActor.setActor(actor);

						// for (Communication comm :
						// actor.getReceivedCommunication()) {
						// comm.getChildren();
						// }

						return cpActor;
					}
				});
		if (callback instanceof AmbiguousActorException) {
			throw (AmbiguousActorException) callback;
		} else if (callback instanceof ActorNotFoundException) {
			throw (ActorNotFoundException) callback;
		}

		return (CP_Actor) callback;

	}

	// public CommunicationContainer getCommunicationContainer(Actor actor) {
	// final CP_Actor cpActor = retrieve(actor);
	// return (CommunicationContainer) getHibernateTemplate().execute(new
	// HibernateCallback() {
	//
	// public Object doInHibernate(Session session) throws HibernateException,
	// SQLException {
	// Query query = session.createQuery("from CommunicationContainer where
	// owner = :owner");
	// query.setEntity("owner", cpActor);
	// Object result = query.uniqueResult();
	// if (result == null) {
	// return null;
	// }
	// return (CommunicationContainer) query.uniqueResult();
	// }
	//
	// });
	//
	// }
	//
	// @Override
	// public CommunicationContainer getCommunicationContainer(final CP_Actor
	// cpActor) {
	// return (CommunicationContainer) getHibernateTemplate().execute(new
	// HibernateCallback() {
	//
	// public Object doInHibernate(Session session) throws HibernateException,
	// SQLException {
	// Query query = session.createQuery("from CommunicationContainer where
	// owner = :owner");
	// query.setEntity("owner", cpActor);
	// Object result = query.uniqueResult();
	// if (result == null) {
	// return null;
	// }
	// return (CommunicationContainer) query.uniqueResult();
	// }
	//
	// });
	// }
	//
	// @Override
	// public CommunicationContainer createCommunicationContainer(CP_Actor
	// cpActor) {
	// CommunicationContainer communicationContainer = new
	// CommunicationContainer();
	// communicationContainer.setName(cpActor.getName());
	// communicationContainer.setOwner(cpActor);
	// getHibernateTemplate().save(communicationContainer);
	// return communicationContainer;
	// }
	//
	// @Override
	// public CommunicationContainer createCommunicationContainer(Actor actor) {
	// final CP_Actor cpActor = retrieve(actor);
	// CommunicationContainer communicationContainer = new
	// CommunicationContainer();
	// communicationContainer.setName(actor.getName());
	// communicationContainer.setOwner(cpActor);
	// getHibernateTemplate().save(communicationContainer);
	// return communicationContainer;
	//
	// }

}
