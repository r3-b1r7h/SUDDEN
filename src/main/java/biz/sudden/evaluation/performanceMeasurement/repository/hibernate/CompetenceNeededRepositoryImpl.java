package biz.sudden.evaluation.performanceMeasurement.repository.hibernate;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeededByNetworkEvaluationProfile;
import biz.sudden.evaluation.performanceMeasurement.repository.CompetenceNeededRepository;

public class CompetenceNeededRepositoryImpl extends
		GenericRepositoryImpl<CompetenceNeeded, Long> implements
		CompetenceNeededRepository {

	public CompetenceNeededRepositoryImpl() {
		super(CompetenceNeeded.class);
		logger.debug("CompetenceNeededRepositoryImpl -> cst");
	}

	public CompetenceNeededRepositoryImpl(Class<CompetenceNeeded> type) {
		super(type);
		logger.debug("CompetenceNeededRepositoryImpl -> cst");
	}

	// @Override
	// public List<CompetenceNeeded> retrieveAll() {
	// return super.retrieveAll();
	// }

	@Override
	public Long create(CompetenceNeeded cn) {
		logger.debug("CompetenceNeededRepositoryImp: create: "
				+ cn.getNameOfCompetenceProfile());
		logger.debug("type: " + cn.getClass());
		if (cn instanceof CompetenceNeededByNetworkEvaluationProfile
				&& ((CompetenceNeededByNetworkEvaluationProfile) cn)
						.getNetworkEvaluationProfile() != null)
			logger.debug(((CompetenceNeededByNetworkEvaluationProfile) cn)
					.getNetworkEvaluationProfile().getName());
		Long result = super.create(cn);
		logger.debug("CompetenceNeeded ID: " + result);
		return result;
	}
}
