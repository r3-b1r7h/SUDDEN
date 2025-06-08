package biz.sudden.evaluation.performanceMeasurement.domain;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class SumFunction extends AggregationFunction {

	public static String TYPE_NAME = "Sum";

	public SumFunction() {
		super(TYPE_NAME);
	}

	/**
	 * sum of competences; param is ignored double result = competences.get(0);
	 * for (int i = 1; i < competences.size(); ++i) { result +=
	 * competences.get(i); }
	 * 
	 * @param param
	 *            is ignored
	 * */
	@Override
	public Double calculate(List<Object> param, List<Double> competences) {
		double result = competences.get(0);
		for (int i = 1; i < competences.size(); ++i) {
			result += competences.get(i);
		}
		return new Double(result);
	}
}
