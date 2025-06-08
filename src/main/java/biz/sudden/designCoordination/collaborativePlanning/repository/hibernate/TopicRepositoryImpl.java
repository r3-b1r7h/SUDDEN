package biz.sudden.designCoordination.collaborativePlanning.repository.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.orm.hibernate3.HibernateCallback;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.designCoordination.collaborativePlanning.domain.Topic;
import biz.sudden.designCoordination.collaborativePlanning.repository.TopicRepository;

class TopicRepositoryImpl extends GenericRepositoryImpl<Topic, Long> implements
		TopicRepository {

	public TopicRepositoryImpl() {
		super(Topic.class);
	}

	@Override
	public void delete(Topic o) {
		// TODO Auto-generated method stub
		super.delete(o);
	}

	public TopicRepositoryImpl(Class<Topic> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Topic> retrieveAll() {
		return (List<Topic>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session.createCriteria(Topic.class);
						List list = criteria.list();

						criteria
								.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

						return criteria.list();

					}

				});
	}

}
