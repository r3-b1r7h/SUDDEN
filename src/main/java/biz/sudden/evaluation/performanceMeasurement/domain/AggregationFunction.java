package biz.sudden.evaluation.performanceMeasurement.domain;

import java.util.List;

import biz.sudden.baseAndUtility.domain.connectable.AssocType;

/**
 * The calculate method is used for aggregating several evaluation profiles into
 * a single one
 * 
 * Subclass and provide a concrete implementaion. A good example is the weighted
 * sum impl.
 * 
 * @see WeightedSumFunction
 * @author gweich
 */
public abstract class AggregationFunction extends AssocType {

	/**
	 * include the type of AssocType and watchout - it has to be unique
	 */
	public AggregationFunction(String name) {
		super(name);
	}

	@Override
	public boolean equals(Object o) {
		if (!(this.getClass().isInstance(o)))
			return false;

		if (this.getId().equals(((AggregationFunction) o).getId())
				&& this.getType().equals(((AggregationFunction) o).getType()))
			return true;
		return false;
	}

	/**
	 * Functions implementing this interface are used to calculate a single
	 * value for a list of competence values;
	 * 
	 * 
	 * @param param
	 *            each competence value might have an associated parameter (e.g.
	 *            weights for a weighted sum function, identification of what is
	 *            the divisor and what the divident...
	 * @param competences
	 *            list of competence values (performance indicating raw values)
	 *            or results of EvaluationProfiles
	 * @return
	 */
	public abstract Double calculate(List<Object> param,
			List<Double> competences);

}
