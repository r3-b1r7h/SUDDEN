package biz.sudden.evaluation.coordinationFit.service;

import java.util.Set;

public interface CoordinationFitDataProvider {
	Set<String> getBlacklistFor(String supplierId);

	double getMinCostFor(String supplierId, String serviceId);

	double getAvgCostFor(String supplierId, String serviceId);

	double getMaxCostFor(String supplierId, String serviceId);

	long getMinDurationFor(String supplierId, String serviceId);

	long getAvgDurationFor(String supplierId, String serviceId);

	long getMaxDurationFor(String supplierId, String serviceId);
}