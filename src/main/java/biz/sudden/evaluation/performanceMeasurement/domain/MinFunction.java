package biz.sudden.evaluation.performanceMeasurement.domain;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class MinFunction extends AggregationFunction {

	public static String TYPE_NAME = "Min";

	public MinFunction() {
		super(TYPE_NAME);
	}

	/**
	 * result is minimum value of competences; ignores param
	 * 
	 * double result = competences.get(0); for (int i = competences.size() - 1;
	 * i >= 0; --i) { result = Math.min(result, competences.get(i)); } return
	 * new Double(result); *
	 * 
	 * @param param
	 *            is ignored
	 */
	@Override
	public Double calculate(List<Object> param, List<Double> competences) {
		double result = competences.get(0);
		for (int i = competences.size() - 1; i >= 0; --i) {
			result = Math.min(result, competences.get(i));
		}
		return new Double(result);
	}
}
