package biz.sudden.evaluation.performanceMeasurement.domain;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class MaxFunction extends AggregationFunction {

	public static String TYPE_NAME = "Max";

	public MaxFunction() {
		super(TYPE_NAME);
	}

	/**
	 * result is maxValue value of competences; ignores param
	 * 
	 * double result = competences.get(0); for (int i = competences.size() - 1;
	 * i >= 0; --i) { result = Math.max(result, competences.get(i)); } return
	 * new Double(result);
	 * 
	 * @param param
	 *            is ignored
	 * 
	 */
	@Override
	public Double calculate(List<Object> param, List<Double> competences) {
		double result = competences.get(0);
		for (int i = competences.size() - 1; i >= 0; --i) {
			result = Math.max(result, competences.get(i));
		}
		return new Double(result);
	}
}
