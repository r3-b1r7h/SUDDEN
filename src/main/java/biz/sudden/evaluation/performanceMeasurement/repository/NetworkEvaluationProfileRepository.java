package biz.sudden.evaluation.performanceMeasurement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface NetworkEvaluationProfileRepository extends
		GenericRepository<NetworkEvaluationProfile, Long> {

	public List<NetworkEvaluationProfile> retrieveNEvaluationProfileBy(
			String name);

}
