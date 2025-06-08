package biz.sudden.designCoordination.collaborativePlanning.repository.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.repository.CommunicationRepository;

class CommunicationRepositoryImpl extends
		GenericRepositoryImpl<Communication, Long> implements
		CommunicationRepository {

	public CommunicationRepositoryImpl() {
		super(Communication.class);
	}

	@Override
	public Long create(Communication o) {
		// getHibernateTemplate().initialize(o.getSender());
		return super.create(o);
	}

	@Override
	public Communication retrieve(Long id) {
		// TODO Auto-generated method stub
		Communication comm = super.retrieve(id);
		// throw new RuntimeException("fehler in retrieve");
		// getHibernateTemplate().initialize(comm.getReceiver());

		return comm;
	}

	// @Override
	// public void delete(final Communication o) {
	//		
	//		
	//		
	// for (Communication comm : o.getChildrenCommunications()) {
	// logger.debug("Delete Children Communications "+comm.getMessage());
	// delete(comm);
	// }
	// // if (o.getTopic() != null &&
	// o.getTopic().getCommunications().contains(o))
	// // o.getTopic().getCommunications().remove(o);
	// // if (o.getChildrenCommunications().size() != 0 &&
	// o.getChildrenCommunications().contains(o)) {
	// // o.getChildrenCommunications().remove(o);
	// // o.setInReplyTo(null);
	// // }
	// super.delete(o);
	//
	// // getHibernateTemplate().execute(new HibernateCallback() {
	// //
	// // @Override
	// // public Object doInHibernate(final Session session) throws
	// // HibernateException, SQLException {
	// //
	// // o.getChildrenCommunications().remove(o);
	// // final Query query =
	// // session.createQuery("delete from Communication c where c = :id");
	// // query.setEntity("id", o);
	// //
	// // query.executeUpdate();
	// // return null;
	// // }
	// //
	// // });
	// }

	//	
	// @Override
	// public Long create(Communication o) {
	//
	// getHibernateTemplate().save(o);
	// getHibernateTemplate().flush();
	// return o.getId();
	// }

	@Override
	public List<Communication> getAllCommunications() {
		// TODO Auto-generated method stub
		// return getHibernateTemplate().loadAll(Communication.class);
		return super.retrieveAll();
		// FIXME: the code below throws exception
		// org.hibernate.WrongClassException: Object with id: 1 was not of the
		// specified subclass:
		// biz.sudden.designCoordination.collaborativePlanning.domain.Communication
		// (Discriminator: null)
		// return (List<Communication>) getHibernateTemplate().execute(new
		// HibernateCallback() {
		//
		// @Override
		// public Object doInHibernate(final Session session) throws
		// HibernateException, SQLException {
		// logger.debug("Select query in CommunicationRepositoryImpl");
		// final Query query = session.createQuery("from Communication");
		// return (List<Communication>) query.list();
		// }
		//
		// });
	}

	@Override
	public List<Communication> getCommunicationFor(final SuddenUser user) {

		return (List<Communication>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(final Session session)
							throws HibernateException, SQLException {
						logger
								.debug("Select query in CommunicationRepositoryImpl");
						final Query query = session
								.createQuery("select comm from Communication comm join comm.receiver as receiver where :receiver = receiver order by comm.sendDate desc");
						query.setParameter("receiver", user);
						return query.list();
					}

				});
	}

}
