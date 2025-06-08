package biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType;

/**
 * 
 * @author mcassmc
 * 
 *         Used to return results from comparing two constrained types.
 * 
 */

public enum ConstrainedComparisonAnswer {

	LESS_GENERAL_THAN(), MORE_GENERAL_THAN(), COMPARABLE_TO(), INCOMPARIBLE_WITH(), EQUALS();

	public ConstrainedComparisonAnswer combineWith(
			ConstrainedComparisonAnswer toCombineWith) {
		System.out.println(" Current status" + this);
		System.out.println(" to combine " + toCombineWith);

		/*
		 * This is how the things combine. Ok lots of return calls but it's just
		 * a big look up table at heart so it fits quite well.
		 */
		switch (this) {
		case LESS_GENERAL_THAN:
			switch (toCombineWith) {
			case LESS_GENERAL_THAN:
				return LESS_GENERAL_THAN;
			case MORE_GENERAL_THAN:
				return COMPARABLE_TO;
			case INCOMPARIBLE_WITH:
				return INCOMPARIBLE_WITH;
			case COMPARABLE_TO:
				return COMPARABLE_TO;
			case EQUALS:
				return LESS_GENERAL_THAN;
			}

		case MORE_GENERAL_THAN:
			switch (toCombineWith) {
			case LESS_GENERAL_THAN:
				return COMPARABLE_TO;
			case MORE_GENERAL_THAN:
				return MORE_GENERAL_THAN;
			case INCOMPARIBLE_WITH:
				return INCOMPARIBLE_WITH;
			case COMPARABLE_TO:
				return COMPARABLE_TO;
			case EQUALS:
				return MORE_GENERAL_THAN;
			}

		case COMPARABLE_TO:
			switch (toCombineWith) {
			case LESS_GENERAL_THAN:
				return COMPARABLE_TO;
			case MORE_GENERAL_THAN:
				return COMPARABLE_TO;
			case INCOMPARIBLE_WITH:
				return INCOMPARIBLE_WITH;
			case COMPARABLE_TO:
				return COMPARABLE_TO;
			case EQUALS:
				return COMPARABLE_TO;
			}

		case EQUALS:
			return toCombineWith;

		case INCOMPARIBLE_WITH:
			return INCOMPARIBLE_WITH;
		}

		// This is the error type the online java example uses
		// Clearly not a normal exception - the method doesn't throw it for
		// instance.
		// Still I can't see this enumeration being expanded so should never
		// reach here & I won't worry.
		// Indeed the failure modes for the code above are slightly *odd*
		throw new AssertionError(
				"Unknown type of constrained comparison answer " + this
						+ " or " + toCombineWith);
	}

	/**
	 * 
	 * For comparing these - quite often there is no strict better/worse
	 * comparison possible.
	 * 
	 * @param result
	 * @return
	 */
	public boolean isBetterMatch(ConstrainedComparisonAnswer toCompare) {
		switch (this) {
		case LESS_GENERAL_THAN:
			switch (toCompare) {
			case EQUALS:
				return true;
			default:
				return false;
			}

		case MORE_GENERAL_THAN:
			switch (toCompare) {
			case EQUALS:
				return true;
			default:
				return false;
			}

		case COMPARABLE_TO:
			switch (toCompare) {
			case LESS_GENERAL_THAN:
				return true;
			case MORE_GENERAL_THAN:
				return true;
			case INCOMPARIBLE_WITH:
				return false;
			case COMPARABLE_TO:
				return false;
			case EQUALS:
				return true;
			}

		case EQUALS:
			return false;

		case INCOMPARIBLE_WITH:
			switch (toCompare) {
			case INCOMPARIBLE_WITH:
				return false;
			default:
				return true;
			}
		}
		throw new AssertionError(
				"Unknown type of constrained comparison answer " + this
						+ " or " + toCompare);
	}
}
