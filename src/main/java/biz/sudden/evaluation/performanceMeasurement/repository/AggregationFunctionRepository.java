/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package biz.sudden.evaluation.performanceMeasurement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.evaluation.performanceMeasurement.domain.AggregationFunction;

/**
 * 
 * @author gweich
 */
public interface AggregationFunctionRepository extends
		GenericRepository<AggregationFunction, Long> {

	public List<AggregationFunction> retrieveAggregationFunctionBy(
			String typename);

	public List<AggregationFunction> retrieveAll();

	/** checks if typename does not exist and otherwise */
	public AggregationFunction createOrRetrieve(AggregationFunction af);
}
