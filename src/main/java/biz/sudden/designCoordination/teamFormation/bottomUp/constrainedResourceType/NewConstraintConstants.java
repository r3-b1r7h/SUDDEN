package biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType;

public class NewConstraintConstants {

	public static final String BASIC_BASE_NAME = "Constraint";
	public static final String VALUE_FROM_BASE_NAME = "Value_From_Constraint";
	public static final String FROM_RANGE_BASE_NAME = "From_Range_Constraint";

	public static boolean isConstraintType(String type) {
		boolean result = false;

		result = (type.equals(BASIC_BASE_NAME)
				|| type.equals(VALUE_FROM_BASE_NAME) || type
				.equals(FROM_RANGE_BASE_NAME));

		return result;
	}
}