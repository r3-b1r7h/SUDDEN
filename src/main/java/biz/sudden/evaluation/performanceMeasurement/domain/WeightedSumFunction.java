package biz.sudden.evaluation.performanceMeasurement.domain;

import java.util.List;

import javax.persistence.Entity;

/**
 * 
 * @author gweich
 * 
 */
@Entity
public class WeightedSumFunction extends AggregationFunction {

	public static String TYPE_NAME = "WeightedSum";

	public WeightedSumFunction() {
		super(TYPE_NAME);
	}

	/**
	 * weighted sum of competences. param are expected to be the weights as
	 * Doulble sum of weights should be == 1 if greated then .
	 * 
	 * double result = 0.0d; double sumofweights = 0.0d; for (int i =
	 * param.size() > competences.size() ? competences.size() - 1 : param.size()
	 * - 1; i >= 0; --i) { Double p = (Double) param.get(i); if (p==null) p =
	 * new Double(0.0d); Double c = competences.get(i); if(c==null) c = new
	 * Double(0.0d); result += p*c; sumofweights += p; } return new
	 * Double(result/sumofweights);
	 * 
	 * @param param
	 *            weights as Double
	 * @param competences
	 *            results for the competences;
	 */
	@Override
	public Double calculate(List<Object> param, List<Double> competences) {
		double result = 0.0d;
		double sumofweights = 0.0d;
		for (int i = param.size() > competences.size() ? competences.size() - 1
				: param.size() - 1; i >= 0; --i) {
			Double p = (Double) param.get(i);
			if (p == null)
				p = new Double(0.0d);
			Double c = competences.get(i);
			if (c == null)
				c = new Double(0.0d);
			result += p * c;
			sumofweights += p;
		}
		// 
		return new Double(result / sumofweights);
	}
}
