package biz.sudden;

import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.event.DeleteEvent;
import org.hibernate.event.DeleteEventListener;
import org.hibernate.event.EvictEvent;
import org.hibernate.event.EvictEventListener;
import org.hibernate.event.FlushEntityEvent;
import org.hibernate.event.FlushEntityEventListener;
import org.hibernate.event.FlushEvent;
import org.hibernate.event.FlushEventListener;
import org.hibernate.event.LoadEvent;
import org.hibernate.event.LoadEventListener;
import org.hibernate.event.LockEvent;
import org.hibernate.event.LockEventListener;
import org.hibernate.event.PersistEvent;
import org.hibernate.event.PersistEventListener;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.PostLoadEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.event.RefreshEvent;
import org.hibernate.event.RefreshEventListener;
import org.hibernate.event.def.DefaultDeleteEventListener;

import biz.sudden.baseAndUtility.domain.IDInterface;

public class HibernateListener implements DeleteEventListener,
		EvictEventListener, FlushEntityEventListener, FlushEventListener,
		LoadEventListener, LockEventListener, PersistEventListener,
		PostDeleteEventListener, PostInsertEventListener,
		PostLoadEventListener, PostUpdateEventListener, RefreshEventListener {

	@Override
	public void onDelete(DeleteEvent arg0) throws HibernateException {
		System.out.println("delete " + arg0);
		new DefaultDeleteEventListener().onDelete(arg0);

	}

	@Override
	public void onDelete(DeleteEvent arg0, Set arg1) throws HibernateException {
		System.out.println("delete " + arg0);

	}

	@Override
	public void onEvict(EvictEvent arg0) throws HibernateException {
		System.out.println("evict " + arg0);
	}

	@Override
	public void onFlushEntity(FlushEntityEvent arg0) throws HibernateException {
		System.out.println("flushentity " + arg0);
	}

	@Override
	public void onFlush(FlushEvent arg0) throws HibernateException {
		System.out.println("flsuh " + arg0);
	}

	@Override
	public void onLoad(LoadEvent arg0, LoadType arg1) throws HibernateException {
		// System.out.println("load " +
		// ((IDInterface)arg0.getResult()).getId()+" "+arg1.getName());
	}

	@Override
	public void onLock(LockEvent arg0) throws HibernateException {
		System.out.println("lock " + arg0);
	}

	@Override
	public void onPersist(PersistEvent arg0) throws HibernateException {
		System.out.println("persist " + arg0);
	}

	@Override
	public void onPersist(PersistEvent arg0, Map arg1)
			throws HibernateException {
		System.out.println("persist " + arg0);
	}

	@Override
	public void onPostDelete(PostDeleteEvent arg0) {
		System.out.println("post delete " + arg0);
	}

	@Override
	public void onPostInsert(PostInsertEvent arg0) {
		System.out.println("post insert " + arg0);
	}

	@Override
	public void onPostLoad(PostLoadEvent arg0) {
		System.out.println("post load  "
				+ ((IDInterface) arg0.getEntity()).getId());
	}

	@Override
	public void onPostUpdate(PostUpdateEvent arg0) {
		System.out.println("post update " + arg0);
	}

	@Override
	public void onRefresh(RefreshEvent arg0) throws HibernateException {
		System.out.println("refresh " + arg0);
	}

	@Override
	public void onRefresh(RefreshEvent arg0, Map arg1)
			throws HibernateException {
		System.out.println("refresh " + arg0);
	}

}
