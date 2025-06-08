package biz.sudden.evaluation.performanceMeasurement.domain;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class MultiplicationFunction extends AggregationFunction {

	public static String TYPE_NAME = "Multiplication";

	public MultiplicationFunction() {
		super(TYPE_NAME);
	}

	/**
	 * ignores param double result = competences.get(0); for (int i = 1; i <
	 * competences.size(); ++i) { result *= competences.get(i); }
	 * 
	 * */
	@Override
	public Double calculate(List<Object> param, List<Double> competences) {
		double result = competences.get(0);
		for (int i = 1; i < competences.size(); ++i) {
			result *= competences.get(i);
		}
		return new Double(result);
	}

}
