package biz.sudden.knowledgeData.competencesManagement.domain;

import java.util.Vector;

@SuppressWarnings("serial")
public class RatingFunctions extends Vector<Object> {

	public static final String DEFAULT_RATING_FUNCTION = "140";

	public RatingFunctions() {
		super
				.add(new RatingFunction(
						"10",
						"view.GapAnalysisResults.ratingFunction.optimalValues_low",
						"view.GapAnalysisResults.ratingFunctionDescription.optimalValues_low",
						"view.GapAnalysisResults.ratingFunctionFormat.optimalValues_low"));
		super
				.add(new RatingFunction(
						"13",
						"view.GapAnalysisResults.ratingFunction.optimalValues",
						"view.GapAnalysisResults.ratingFunctionDescription.optimalValues",
						"view.GapAnalysisResults.ratingFunctionFormat.optimalValues"));
		super
				.add(new RatingFunction(
						"16",
						"view.GapAnalysisResults.ratingFunction.optimalValues_high",
						"view.GapAnalysisResults.ratingFunctionDescription.optimalValues_high",
						"view.GapAnalysisResults.ratingFunctionFormat.optimalValues_high"));
		super
				.add(new RatingFunction(
						"20",
						"view.GapAnalysisResults.ratingFunction.actualResultValues",
						"view.GapAnalysisResults.ratingFunctionDescription.actualResultValues",
						"view.GapAnalysisResults.ratingFunctionFormat.actualResultValues"));

		super
				.add(new RatingFunction(
						"130",
						"view.GapAnalysisResults.ratingFunction.competenceDifferences",
						"view.GapAnalysisResults.ratingFunctionDescription.competenceDifferences",
						"view.GapAnalysisResults.ratingFunctionFormat.competenceDifferences"));
		super
				.add(new RatingFunction(
						"140",
						"view.GapAnalysisResults.ratingFunction.competenceWeightedDifferences",
						"view.GapAnalysisResults.ratingFunctionDescription.competenceWeightedDifferences",
						"view.GapAnalysisResults.ratingFunctionFormat.competenceWeightedDifferences"));

		// super.add(new RatingFunction("150",
		// "view.GapAnalysisResults.ratingFunction.competenceFuzzyDistribution",
		// "view.GapAnalysisResults.ratingFunctionDescription.competenceFuzzyDistribution",
		// "view.GapAnalysisResults.ratingFunctionFormat.competenceFuzzyDistribution"));
		// super.add(new RatingFunction("160",
		// "view.GapAnalysisResults.ratingFunction.competenceFuzzyDensity",
		// "view.GapAnalysisResults.ratingFunctionDescription.competenceFuzzyDensity",
		// "view.GapAnalysisResults.ratingFunctionFormat.competenceFuzzyDensity"));
		super
				.add(new RatingFunction(
						"170",
						"view.GapAnalysisResults.ratingFunction.competenceFuzzyLogicOutput",
						"view.GapAnalysisResults.ratingFunctionDescription.competenceFuzzyLogicOutput",
						"view.GapAnalysisResults.ratingFunctionFormat.competenceFuzzyLogicOutput"));
		super
				.add(new RatingFunction(
						"175",
						"view.GapAnalysisResults.ratingFunction.competenceWeightedFuzzyLogicOutput",
						"view.GapAnalysisResults.ratingFunctionDescription.competenceWeightedFuzzyLogicOutput",
						"view.GapAnalysisResults.ratingFunctionFormat.competenceWeightedFuzzyLogicOutput"));
		super
				.add(new RatingFunction(
						"176",
						"view.GapAnalysisResults.ratingFunction.competenceCentroidFuzzyLogicOutput",
						"view.GapAnalysisResults.ratingFunctionDescription.competenceCentroidFuzzyLogicOutput",
						"view.GapAnalysisResults.ratingFunctionFormat.competenceCentroidFuzzyLogicOutput"));
		super
				.add(new RatingFunction(
						"177",
						"view.GapAnalysisResults.ratingFunction.competencePositiveFuzzyLogicOutput",
						"view.GapAnalysisResults.ratingFunctionDescription.competencePositiveFuzzyLogicOutput",
						"view.GapAnalysisResults.ratingFunctionFormat.competencePositiveFuzzyLogicOutput"));
		super
				.add(new RatingFunction(
						"180",
						"view.GapAnalysisResults.ratingFunction.competenceFuzzyLogicWithinIntervalOutput",
						"view.GapAnalysisResults.ratingFunctionDescription.competenceFuzzyLogicWithinIntervalOutput",
						"view.GapAnalysisResults.ratingFunctionFormat.competenceFuzzyLogicWithinIntervalOutput"));

		super
				.add(new RatingFunction(
						"30",
						"view.GapAnalysisResults.ratingFunction.differences",
						"view.GapAnalysisResults.ratingFunctionDescription.differences",
						"view.GapAnalysisResults.ratingFunctionFormat.differences"));
		super
				.add(new RatingFunction(
						"40",
						"view.GapAnalysisResults.ratingFunction.weightedDifferences",
						"view.GapAnalysisResults.ratingFunctionDescription.weightedDifferences",
						"view.GapAnalysisResults.ratingFunctionFormat.weightedDifferences"));

		super
				.add(new RatingFunction(
						"110",
						"view.GapAnalysisResults.ratingFunction.fuzzyLogicOutput",
						"view.GapAnalysisResults.ratingFunctionDescription.fuzzyLogicOutput",
						"view.GapAnalysisResults.ratingFunctionFormat.fuzzyLogicOutput"));
		super
				.add(new RatingFunction(
						"116",
						"view.GapAnalysisResults.ratingFunction.fuzzyLogicCentroidOutput",
						"view.GapAnalysisResults.ratingFunctionDescription.fuzzyLogicCentroidOutput",
						"view.GapAnalysisResults.ratingFunctionFormat.fuzzyLogicCentroidOutput"));
		super
				.add(new RatingFunction(
						"117",
						"view.GapAnalysisResults.ratingFunction.fuzzyLogicPositiveOutput",
						"view.GapAnalysisResults.ratingFunctionDescription.fuzzyLogicPositiveOutput",
						"view.GapAnalysisResults.ratingFunctionFormat.fuzzyLogicPositiveOutput"));
		super
				.add(new RatingFunction(
						"120",
						"view.GapAnalysisResults.ratingFunction.fuzzyLogicWithinIntervalOutput",
						"view.GapAnalysisResults.ratingFunctionDescription.fuzzyLogicWithinIntervalOutput",
						"view.GapAnalysisResults.ratingFunctionFormat.fuzzyLogicWithinIntervalOutput"));
	}

}
