package biz.sudden.evaluation.performanceMeasurement.domain;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class DivisionFunction extends AggregationFunction {

	public static String TYPE_NAME = "Division";

	public DivisionFunction() {
		super(TYPE_NAME);
	}

	/**
	 * all Doubles where parameter = "dividend" are multiplied and divided by
	 * those marked as divisor (which are multiplied);; if no param marks the
	 * divident and divisor first competence is divided by second is divided by
	 * third...
	 * 
	 * FIXME use the param list for identification of divisor, divident!!!
	 * 
	 * */
	@Override
	public Double calculate(List<Object> param, List<Double> competences) {
		double result = 0.0d;
		boolean founddividend = false;
		boolean founddivisor = false;
		double dividend = 1.0d;
		double divisor = 1.0d;

		for (int i = 0; i < param.size(); ++i) {
			if (param.get(i) != null
					&& param.get(i).toString().toLowerCase().equals("dividend")
					&& competences.get(i) != null) {
				founddividend = true;
				dividend *= competences.get(i);
			}
		}
		for (int i = 0; i < param.size(); ++i) {
			if (param.get(i) != null
					&& param.get(i).toString().toLowerCase().equals("divisor")
					&& competences.get(i) != null) {
				founddivisor = true;
				divisor *= competences.get(i);
			}
		}
		if (founddividend && founddivisor) {
			result = (dividend / divisor);
		} else {
			result = competences.get(0);
			for (int i = 1; i < competences.size(); ++i) {
				result /= competences.get(i);
			}
		}
		return new Double(result);
	}

}
