package biz.sudden.baseAndUtility.repository.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import biz.sudden.baseAndUtility.domain.Individual;
import biz.sudden.baseAndUtility.repository.IndividualRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class IndividualRepositoryImpl extends
		GenericRepositoryImpl<Individual, Long> implements IndividualRepository {

	public IndividualRepositoryImpl() {
		super(Individual.class);
	}

	@Override
	public Individual retrieve(String name) {
		Criteria query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(Individual.class);
		query.add(Restrictions.like("name", name));
		return (Individual) query.uniqueResult();
	}

}
