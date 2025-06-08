package biz.sudden.baseAndUtility.repository.hibernate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.HibernateTemplate;

import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.repository.CaseFileRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;

public class CaseFileRepositoryImpl extends
		GenericRepositoryImpl<CaseFile, Long> implements CaseFileRepository {

	public CaseFileRepositoryImpl() {
		super(CaseFile.class);
	}

	public CaseFileRepositoryImpl(Class<CaseFile> type) {
		super(type);
	}

	//
	// @SuppressWarnings("unchecked")
	// @Override
	// public List<CaseFile> retrieveAll() {
	// //return getHibernateTemplate().loadAll(CaseFile.class);
	// Criteria query =
	// getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(CaseFile.class);
	// query.add(Restrictions.ilike("name", '%'+name+'%'));
	// return query.list();
	// }

	@Override
	public List<CaseFile> retrieveByKeyword(String name, String sundryinfoKW) {
		if (name != null && sundryinfoKW == null) {
			Criteria query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createCriteria(CaseFile.class);
			query.add(Restrictions.ilike("name", '%' + name + '%'));
			return query.list();
		} else if (name == null && sundryinfoKW != null) {
			Criteria query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createCriteria(CaseFile.class);
			query.add(Restrictions.ilike("sundryInformation",
					'%' + sundryinfoKW + '%'));
			return query.list();
		} else if (name != null && sundryinfoKW != null) {
			Criteria query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createCriteria(CaseFile.class);
			query.add(Restrictions.ilike("sundryInformation",
					'%' + sundryinfoKW + '%'));
			List<CaseFile> result = query.list();
			query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createCriteria(CaseFile.class);
			query.add(Restrictions.ilike("name", '%' + name + '%'));
			result.addAll(query.list());
			return result;
		}
		return new ArrayList<CaseFile>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CaseFile> retrieveAllCompleted() {
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		hibernateTemplate.setFlushMode(HibernateAccessor.FLUSH_NEVER);
		List result = hibernateTemplate.find("from CaseFile where phase > 3");
		hibernateTemplate.setFlushMode(HibernateAccessor.FLUSH_AUTO);

		return result;
	}

	@Override
	public CaseFile retrieve(BusinessOpportunity bo) {
		if (bo != null) {
			Criteria query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createCriteria(CaseFile.class);
			query.add(Restrictions.eq("bo", bo));
			return (CaseFile) query.uniqueResult();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, String> retrieveAllCaseFileNames() {
		Map<Long, String> map = new LinkedHashMap<Long, String>();

		List<Object[]> results = getHibernateTemplate().find(
				"select id, name from CaseFile order by id");

		for (Object[] result : results) {
			map.put((Long) result[0], (String) result[1]);
		}
		return map;
	}
}
