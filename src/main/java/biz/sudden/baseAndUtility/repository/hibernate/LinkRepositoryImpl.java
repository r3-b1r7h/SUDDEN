package biz.sudden.baseAndUtility.repository.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import biz.sudden.baseAndUtility.domain.Link;
import biz.sudden.baseAndUtility.repository.LinkRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class LinkRepositoryImpl extends GenericRepositoryImpl<Link, Long>
		implements LinkRepository {

	public LinkRepositoryImpl() {
		super(Link.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Link> getLinkFor(final Object object, final Long id) {
		return (List<Link>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(final Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(Link.class)
								.add(
										Restrictions.eq("fromClass", object
												.getClass().getName())).add(
										Restrictions.eq("fromID", id));
						List<Link> linkList = criteria.list();
						return linkList;
						// toJsfLink

					}

				});
	}

}
