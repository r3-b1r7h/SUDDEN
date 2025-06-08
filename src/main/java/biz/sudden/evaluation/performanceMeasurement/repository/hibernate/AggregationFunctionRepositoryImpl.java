/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package biz.sudden.evaluation.performanceMeasurement.repository.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.evaluation.performanceMeasurement.domain.AggregationFunction;
import biz.sudden.evaluation.performanceMeasurement.repository.AggregationFunctionRepository;

/**
 * 
 * @author gweich
 */
public class AggregationFunctionRepositoryImpl extends
		GenericRepositoryImpl<AggregationFunction, Long> implements
		AggregationFunctionRepository {

	public AggregationFunctionRepositoryImpl() {
		super(AggregationFunction.class);
		logger.debug("AggregationFunctionRepositoryImpl -> cst");
	}

	public AggregationFunctionRepositoryImpl(Class<AggregationFunction> type) {
		super(type);
		logger.debug("AggregationFunctionRepositoryImpl -> cst");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AggregationFunction> retrieveAggregationFunctionBy(
			String typename) {
		if (typename != null && typename.length() > 0) {
			DetachedCriteria query = DetachedCriteria.forClass(
					AggregationFunction.class).add(
					Property.forName("type").eq(typename));
			return getHibernateTemplate().findByCriteria(query);
		} else {
			return retrieveAll();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AggregationFunction> retrieveAll() {
		return getHibernateTemplate().loadAll(AggregationFunction.class);
	}

	/**
	 * checks first if typename does not exist but if so returns that
	 * AggregationFunction..!
	 */
	public AggregationFunction createOrRetrieve(AggregationFunction af) {
		List<AggregationFunction> alreadythere = retrieveAggregationFunctionBy(af
				.getType());
		if (alreadythere != null && alreadythere.size() > 0) {
			// logger.debug("AggregationFunctionRepositoryImpl: AggregationFunction already exists: "
			// +af.getType()
			// +" ...x types found; returning first; x="+alreadythere.size());
			return alreadythere.get(0);
		} else {
			logger
					.debug("AggregationFunctionRepositoryImpl: AggregationFunction to be created");
			create(af);
			return af;
		}
	}
}
