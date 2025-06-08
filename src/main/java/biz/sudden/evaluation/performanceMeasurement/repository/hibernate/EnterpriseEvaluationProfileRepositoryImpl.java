/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.sudden.evaluation.performanceMeasurement.repository.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.evaluation.performanceMeasurement.repository.EnterpriseEvaluationProfileRepository;

/**
 * 
 * @author gweich
 */
public class EnterpriseEvaluationProfileRepositoryImpl extends
		GenericRepositoryImpl<EnterpriseEvaluationProfile, Long> implements
		EnterpriseEvaluationProfileRepository {

	public EnterpriseEvaluationProfileRepositoryImpl() {
		super(EnterpriseEvaluationProfile.class);
		logger.debug("EnterpriseEvaluationProfileRepositoryImpl -> cst");
	}

	public EnterpriseEvaluationProfileRepositoryImpl(
			Class<EnterpriseEvaluationProfile> type) {
		super(type);
		logger.debug("EnterpriseEvaluationProfileRepositoryImpl -> cst");
	}

	@Override
	public void delete(EnterpriseEvaluationProfile o) {
		logger.debug("delete: " + o.getId());
		getHibernateTemplate().delete(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnterpriseEvaluationProfile> retrieveEEvaluationProfileBy(
			String name) {
		if (name != null && name.length() > 0) {
			DetachedCriteria query = DetachedCriteria.forClass(
					EnterpriseEvaluationProfile.class).add(
					Property.forName("name").eq(name));
			return getHibernateTemplate().findByCriteria(query);
		} else {
			return retrieveAll();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnterpriseEvaluationProfile> retrieveAll() {
		return getHibernateTemplate()
				.loadAll(EnterpriseEvaluationProfile.class);
	}
}
