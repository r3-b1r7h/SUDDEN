/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.sudden.evaluation.performanceMeasurement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

/**
 * 
 * @author gweich
 */
public interface EnterpriseEvaluationProfileRepository extends
		GenericRepository<EnterpriseEvaluationProfile, Long> {

	public List<EnterpriseEvaluationProfile> retrieveEEvaluationProfileBy(
			String name);

	public List<EnterpriseEvaluationProfile> retrieveAll();
}
