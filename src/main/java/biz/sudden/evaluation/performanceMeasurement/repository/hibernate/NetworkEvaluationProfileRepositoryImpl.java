package biz.sudden.evaluation.performanceMeasurement.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.evaluation.performanceMeasurement.repository.NetworkEvaluationProfileRepository;

public class NetworkEvaluationProfileRepositoryImpl extends
		GenericRepositoryImpl<NetworkEvaluationProfile, Long> implements
		NetworkEvaluationProfileRepository {

	public NetworkEvaluationProfileRepositoryImpl() {
		super(NetworkEvaluationProfile.class);
		logger.debug("NetworkEvaluationProfileRepositoryImpl -> cst");
	}

	public NetworkEvaluationProfileRepositoryImpl(
			Class<NetworkEvaluationProfile> type) {
		super(type);
		logger.debug("NetworkEvaluationProfileRepositoryImpl -> cst");
	}

	@Override
	public void delete(NetworkEvaluationProfile o) {
		logger.debug("delete: " + o.getId());
		super.delete(o);
	}

	@Override
	public List<NetworkEvaluationProfile> retrieveNEvaluationProfileBy(
			String name) {
		if (name != null && name.length() > 0) {
			return initialize(super.retrieveAllByFieldName("name", name));
		} else {
			return initialize(retrieveAll());
		}
	}

	@Override
	public List<NetworkEvaluationProfile> retrieveAll() {
		return initialize(super.retrieveAll());
	}

	protected List<NetworkEvaluationProfile> initialize(
			List<NetworkEvaluationProfile> result) {
		for (NetworkEvaluationProfile nep : result) {
			initialize(nep);
		}
		return result;
	}

	@Override
	public NetworkEvaluationProfile retrieve(Long id) {
		return initialize(super.retrieve(id));
	}

	protected NetworkEvaluationProfile initialize(
			NetworkEvaluationProfile result) {
		getHibernateTemplate().initialize(result.getCompetenceAllHave());
		getHibernateTemplate().initialize(result.getCompetenceOneOrMoreHave());
		return result;
	}

}
