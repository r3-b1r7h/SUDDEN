package biz.sudden.designCoordination.collaborativePlanning.repository.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoard;
import biz.sudden.designCoordination.collaborativePlanning.repository.BulletinBoardRepository;

class BulletinBoardRepositoryImpl extends
		GenericRepositoryImpl<BulletinBoard, Long> implements
		BulletinBoardRepository {

	public BulletinBoardRepositoryImpl() {
		super(BulletinBoard.class);
	}

	public BulletinBoardRepositoryImpl(Class<BulletinBoard> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<BulletinBoard> retrieveAllOfType(final Class type) {
		return (List<BulletinBoard>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session)
							throws HibernateException, SQLException {

						String typeName = type.getSimpleName();

						Criteria criteria = session.createCriteria(
								BulletinBoard.class).setFetchMode("topicList",
								FetchMode.EAGER).add(
								Restrictions
										.sqlRestriction("this_.DTYPE like '"
												+ typeName + "'"));
						List list = criteria.list();

						criteria
								.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
						// criteria.setResultTransformer(Criteria.ROOT_ENTITY);

						System.out.println("BulletinBoard List " + list);

						return criteria.list();
					}

				});
	}

	@Override
	public List<BulletinBoard> retrieveAll() {
		return (List<BulletinBoard>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session.createCriteria(
								BulletinBoard.class).setFetchMode("topicList",
								FetchMode.EAGER).setFetchMode("owner",
								FetchMode.EAGER);
						List list = criteria.list();

						criteria
								.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
						// criteria.setResultTransformer(Criteria.ROOT_ENTITY);

						System.out.println("BulletinBoard List " + list);

						return criteria.list();
					}

				});
	}

}
