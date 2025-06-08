package biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;
import jade.content.abs.AbsObject;
import jade.content.abs.AbsPrimitive;
import jade.content.onto.OntologyException;
import jade.content.schema.ObjectSchema;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.NoticeboardCombinedOntology;

/**
 * 
 * @author mcassmc
 * 
 *         Provides static method(s) usable to compare two absconcepts which
 *         also contain constraints.
 * 
 *         These could in principle be converted into Java objects but there
 *         seems little purpose to this - they aren't heavily manipulated within
 *         the algorithm and are communicated about a lot.
 * 
 *         NB: this method makes no attempt to verify the absobjects fed to it
 *         against ontologies - this should be done first (probably implictly
 *         when receiving the message) if desired.
 * 
 *         There is also some kind of assumption that the properties in classes
 *         are being used as positive descriptions (eg length is some given
 *         value etc.).
 * 
 *         Especially important with aggregates this. I choose to view these as
 *         a set of properties which the strcuture has -> therefore in order to
 *         match an aggregate to another aggregate they have to have the same
 *         number of values. The answer returned is then based on the best
 *         possible result generated when trying to create a bijection between
 *         the two aggregates.
 * 
 *         This is awfully inefficent especially with large numbers of elements
 *         in the aggregates so should be avoided where possible. Perhaps I
 *         should think about enforcing ordering or something.
 * 
 */

// TODO - indicate *where* the comparison fails perhaps? Probably just something
// for future reference this.
public class ConstrainedTypeComparator {

	/**
	 * 
	 * @param type1
	 * @param type2
	 * @return Returns type1 as compared to type 2 (eg MoreGeneralThan = type*1*
	 *         is more general than type2, etc.).
	 * @throws UnknownConstraintTypeException
	 */

	public static ConstrainedComparisonAnswer compareConstrainedTypes(
			AbsConcept type1, AbsConcept type2)
			throws UnknownConstraintTypeException {
		ConstrainedComparisonAnswer result = recursivelyCompareConcepts(type1,
				type2);
		return result;
	}

	private static ConstrainedComparisonAnswer recursivelyCompareConcepts(
			AbsConcept type1, AbsConcept type2)
			throws UnknownConstraintTypeException {
		ConstrainedComparisonAnswer result;
		result = compareTypeNames(type1.getTypeName(), type2.getTypeName());

		// Skip once an incomparible is found - no point looking deeper.
		// It would be nice to terminate every branch of the tree search but for
		// now
		// I'll settle for terminating the immediate branches
		if (!result.equals(ConstrainedComparisonAnswer.INCOMPARIBLE_WITH)) {

			/*
			 * Go only by the slots present in both concepts - otherwise
			 * optional slots could cause all kinds of problems (Well a lot of
			 * Null pointers anyway.).
			 */
			String[] slotNames1 = type1.getNames();
			String[] slotNames2 = type2.getNames();

			int i = 0;
			while ((i < slotNames1.length)
					&& (!result
							.equals(ConstrainedComparisonAnswer.INCOMPARIBLE_WITH))) {
				String slotName = slotNames1[i];

				// ie it contains the string slot names- rather clumsy but
				// seemingly no better way to do this
				if (Arrays.binarySearch(slotNames2, slotName) >= 0) {
					result = result.combineWith(getRecursiveComparison(type1
							.getAbsObject(slotName), type2
							.getAbsObject(slotName)));
					System.out.println("result " + result);
				}
				i++;
			}

		}
		return result;
	}

	/**
	 * 
	 * Much like the method above but only to be called in the recursive case
	 * 
	 * @param absObject
	 * @return
	 * @throws UnknownConstraintTypeException
	 */
	private static ConstrainedComparisonAnswer getRecursiveComparison(
			AbsObject object1, AbsObject object2)
			throws UnknownConstraintTypeException {

		ConstrainedComparisonAnswer result;

		/*
		 * Assumption here is that a constraint can never match a concept - this
		 * seems entirely reasonable. However a primitive *can* match a
		 * constraint of course!
		 */
		// System.out.println(" object1 " + object1.getClass());
		// System.out.println(" object2 " + object2.getClass());
		Class class1 = object1.getClass();
		Class class2 = object2.getClass();
		String type1 = object1.getTypeName();
		String type2 = object2.getTypeName();

		// AbsConcepts *AND* constraints
		if (class1.equals(AbsConcept.class) && class2.equals(AbsConcept.class)) {

			if ((!ConstraintSchema.isConstraintType(type1))
					&& (!ConstraintSchema.isConstraintType(type2))) {
				System.out.print(" Concept " + object1
						+ " comapring to concept " + object2);
				result = recursivelyCompareConcepts((AbsConcept) object1,
						(AbsConcept) object2);
				System.out.println(result);
			} else if ((ConstraintSchema.isConstraintType(type1))
					&& (ConstraintSchema.isConstraintType(type2))) {
				System.out.print(" Constraint " + object1
						+ " comparing to constraint " + object2);
				// Compare constraints
				result = compareConstraints((AbsConcept) object1,
						(AbsConcept) object2);
				System.out.println(result);
			} else {
				// Modify this if it is possible for a constraint to match a
				// concept
				result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
			}
		}
		// A rather harsh comparison here!
		else if (class1.equals(AbsPrimitive.class)
				&& class2.equals(AbsPrimitive.class)) {
			System.out.print(" AbsPrimitive " + object1
					+ " matched to absPrimitive" + object2);
			if (object1.equals(object2)) {
				result = ConstrainedComparisonAnswer.EQUALS;
			} else {
				result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
			}
			System.out.println(result);
		}

		else if (object1.getClass().equals(AbsPrimitive.class)
				&& ConstraintSchema.isConstraintType(type2)) {
			System.out.print(" AbsPrimitive " + object1
					+ " matched to constraint " + object2);

			if (type2.equals(ValueFromConstraintSchema.VALUE_FROM_CONSTRAINT)) {
				AbsAggregate typesFrom = (AbsAggregate) ((AbsConcept) object2)
						.getAbsTerm(ValueFromConstraintSchema.TEST_SLOT);
				if (typesFrom.contains((AbsPrimitive) object1)) {
					if (typesFrom.size() == 1) {
						result = ConstrainedComparisonAnswer.EQUALS;
					} else {
						result = ConstrainedComparisonAnswer.LESS_GENERAL_THAN;
					}
				} else {
					result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
				}
				System.out.println(result);
			} else if (type2
					.equals(FromRangeConstraintSchema.FROM_RANGE_CONSTRAINT)) {

				/*
				 * This *should* work I believe - maybe an issue with longs
				 */
				int value1 = ((AbsPrimitive) object1).getInteger();
				int min2 = ((AbsConcept) object2)
						.getInteger(FromRangeConstraintSchema.MINIMUM);
				int max2 = ((AbsConcept) object2)
						.getInteger(FromRangeConstraintSchema.MAXIMUM);

				// A silly case!
				if ((min2 == max2) && (min2 == value1)) {
					result = ConstrainedComparisonAnswer.EQUALS;
				} else if ((value1 >= min2) && (value1 <= max2)) {
					result = ConstrainedComparisonAnswer.LESS_GENERAL_THAN;
				} else {
					result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
				}
			}
			// Can't reach here at the moment- guarded by the isConstraintType()
			// method
			else {
				throw new UnknownConstraintTypeException((AbsConcept) object2);
			}
		}

		// Comparing constraints & concrete values
		else if (ConstraintSchema.isConstraintType(type1)
				&& object2.getClass().equals(AbsPrimitive.class)) {
			System.out.println(" Constraint " + object1
					+ " matched to absPrimitive" + object2);
			if (type1.equals(ValueFromConstraintSchema.VALUE_FROM_CONSTRAINT)) {
				AbsAggregate typesFrom = (AbsAggregate) ((AbsConcept) object1)
						.getAbsTerm(ValueFromConstraintSchema.TEST_SLOT);
				if (typesFrom.contains((AbsPrimitive) object2)) {
					if (typesFrom.size() == 1) {
						result = ConstrainedComparisonAnswer.EQUALS;
					} else {
						result = ConstrainedComparisonAnswer.MORE_GENERAL_THAN;
					}
				} else {
					result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
				}
			} else if (type1
					.equals(FromRangeConstraintSchema.FROM_RANGE_CONSTRAINT)) {

				/*
				 * This *should* work I believe - maybe an issue with longs
				 */
				int value2 = ((AbsPrimitive) object2).getInteger();
				int min1 = ((AbsConcept) object1)
						.getInteger(FromRangeConstraintSchema.MINIMUM);
				int max1 = ((AbsConcept) object1)
						.getInteger(FromRangeConstraintSchema.MAXIMUM);

				// A silly case!
				if ((min1 == max1) && (min1 == value2)) {
					result = ConstrainedComparisonAnswer.EQUALS;
				} else if ((value2 >= min1) && (value2 <= max1)) {
					result = ConstrainedComparisonAnswer.MORE_GENERAL_THAN;
				} else {
					result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
				}
			}
			// Can't reach here at the moment - guarded by the
			// isConstraintType() method
			else {
				throw new UnknownConstraintTypeException((AbsConcept) object2);
			}

		}

		else if (object1.getClass().equals(AbsAggregate.class)
				&& object2.getClass().equals(AbsAggregate.class)) {
			/*
			 * Aggregates. The issue here is that in general these are NOT
			 * ordered. Also they might contain **** concepts. Wonderful :)
			 */
			AbsAggregate agg1 = (AbsAggregate) object1;
			AbsAggregate agg2 = (AbsAggregate) object2;

			result = CompareAggregates(agg1, agg2);

		} else {
			result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
		}

		return result;
	}

	/**
	 * As it says on the tin - compare aggregates. The algorithm for this is
	 * noted above. SImply it tries to create a bijection between the two sets
	 * of objects/concepts & returns the best answer generated this way.
	 * 
	 * @param agg1
	 * @param agg2
	 * @return
	 * @throws UnknownConstraintTypeException
	 */

	private static ConstrainedComparisonAnswer CompareAggregates(
			AbsAggregate agg1, AbsAggregate agg2)
			throws UnknownConstraintTypeException {

		ConstrainedComparisonAnswer result;

		if (!(agg1.size() == agg2.size())) {
			result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
		} else {
			// Same size - but must now match them up!
			// Ok generate every possible bijective mapping between the two
			// aggregates and pick the one giving the best overall result.

			HashMap<LinkedList<Integer>, ConstrainedComparisonAnswer> ratedMappings = new HashMap<LinkedList<Integer>, ConstrainedComparisonAnswer>();

			// generate mappings
			LinkedList<LinkedList<Integer>> mappings = new LinkedList<LinkedList<Integer>>();
			generateMappings(agg1.size(), mappings);

			for (LinkedList<Integer> list : mappings) {
				ConstrainedComparisonAnswer thisResult = ConstrainedComparisonAnswer.EQUALS;
				for (int i = 0; i < list.size(); i++) {
					thisResult.combineWith(getRecursiveComparison(agg1.get(i),
							agg2.get(list.get(i).intValue())));
				}
				ratedMappings.put(list, thisResult);
			}

			result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;

			for (ConstrainedComparisonAnswer cca : ratedMappings.values()) {
				if (cca.isBetterMatch(result)) {
					result = cca;
				}
			}
		}
		return result;
	}

	/*
	 * A little recursive toy to generate Pn Surely this is somewhat crazy as
	 * algorithms to do this go?
	 * 
	 * It in fact is - much easier to create a set of lists & just inserst the
	 * numbers in the right place. But ugly. (not that the below is clever in
	 * any sense.).
	 */
	public static void generateMappings(int size,
			LinkedList<LinkedList<Integer>> mappings) {

		for (int i = 1; i <= size; i++) {
			LinkedList<Integer> tempLL = new LinkedList<Integer>();

			tempLL.add(new Integer(i));
			mappings.add(tempLL);

		}
		generateRecursiveMappings(size, mappings, 1);
	}

	/*
	 * A recursive call. Constraining by a pure depth integer is rather
	 * inellegant but undoubtedly effective.
	 */

	private static void generateRecursiveMappings(int size,
			LinkedList<LinkedList<Integer>> mappings, int depth) {

		System.out.println(mappings);

		for (int i = 1; i <= size; i++) {
			Integer tempInt = new Integer(i);
			System.out.println(i);

			LinkedList<LinkedList<Integer>> toRemove = new LinkedList<LinkedList<Integer>>();
			LinkedList<LinkedList<Integer>> toAdd = new LinkedList<LinkedList<Integer>>();
			for (LinkedList<Integer> permutations : mappings) {
				if (!permutations.contains(tempInt)
						&& (permutations.size() == (depth - 1))) {
					LinkedList<Integer> tempList = (LinkedList<Integer>) permutations
							.clone();
					tempList.add(tempInt);
					toAdd.add(tempList);
				}
			}
			mappings.addAll(toAdd);
		}

		// Incrememnt depth & trim
		depth++;

		Iterator<LinkedList<Integer>> overPermutations = mappings.iterator();
		while (overPermutations.hasNext()) {
			LinkedList<Integer> tempList = overPermutations.next();
			if (tempList.size() != (depth - 1)) {
				overPermutations.remove();
			}
		}

		if (depth <= size) {
			generateRecursiveMappings(size, mappings, depth);
		}
	}

	/**
	 * 
	 * This does an ontology based comparison - JADE based for now
	 * 
	 * Public to allow for comparisons between abstract types.
	 * 
	 * @param typeName1
	 * @param typeName2
	 * @return
	 */
	public static ConstrainedComparisonAnswer compareTypeNames(
			String typeName1, String typeName2) {
		// Ontology products = ProductOntology.getInstance();

		ConstrainedComparisonAnswer result;

		ObjectSchema schema1 = null;
		ObjectSchema schema2 = null;

		try {
			schema1 = NoticeboardCombinedOntology.getInstance().getSchema(
					typeName1);
			schema2 = NoticeboardCombinedOntology.getInstance().getSchema(
					typeName2);
		} catch (OntologyException e) {
			e.printStackTrace();
		}

		if (typeName1.equals(typeName2)) {
			result = ConstrainedComparisonAnswer.EQUALS;
		} else if (schema1.isCompatibleWith(schema2)) {
			result = ConstrainedComparisonAnswer.LESS_GENERAL_THAN;
		} else if (schema2.isCompatibleWith(schema1)) {
			result = ConstrainedComparisonAnswer.MORE_GENERAL_THAN;
		} else {
			result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
		}

		return result;
	}

	/**
	 * And the functions comparing constraints. Note that the constraints are
	 * fed in as AbsConcepts - this should be fairly straightforward to deal
	 * with.
	 * 
	 * They are implictly assumed to be related to the same variable since they
	 * are being extracted from a tree structure. (NB there's also an assumption
	 * that only two constraints of the same type can ever be comparable hence
	 * the lack of 'cross' comparison methods here.).
	 */
	private static ConstrainedComparisonAnswer compareConstraints(
			AbsConcept constraint1, AbsConcept constraint2) {
		ConstrainedComparisonAnswer result;

		String type1 = constraint1.getTypeName();
		String type2 = constraint2.getTypeName();

		// Extend if more constraint types get added in

		if (!type1.equals(type2)) {
			// Again this might have to change
			result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
		} else if (type1
				.equals(FromRangeConstraintSchema.FROM_RANGE_CONSTRAINT)) {
			result = compareFromRange(constraint1, constraint2);
		} else if (type1
				.equals(ValueFromConstraintSchema.VALUE_FROM_CONSTRAINT)) {
			result = compareFromSet(constraint1, constraint2);
		} else {
			result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
		}

		return result;
	}

	private static ConstrainedComparisonAnswer compareFromRange(
			AbsConcept constraint1, AbsConcept constraint2) {
		ConstrainedComparisonAnswer result;

		int min1 = constraint1.getInteger(FromRangeConstraintSchema.MINIMUM);
		int max1 = constraint1.getInteger(FromRangeConstraintSchema.MAXIMUM);
		int min2 = constraint2.getInteger(FromRangeConstraintSchema.MINIMUM);
		int max2 = constraint2.getInteger(FromRangeConstraintSchema.MAXIMUM);

		if ((min1 == min2) && (max1 == max2)) {
			result = ConstrainedComparisonAnswer.EQUALS;
		} else if (((min1 == min2) && (max1 < max2))
				|| ((min1 > min2) && (max1 == max2))
				|| ((min1 > min2) && (max1 < max2))) {
			result = ConstrainedComparisonAnswer.LESS_GENERAL_THAN;
		} else if (((min2 == min1) && (max2 < max1))
				|| ((min2 > min1) && (max2 == max1))
				|| ((min2 > min1) && (max2 < max1))) {
			result = ConstrainedComparisonAnswer.MORE_GENERAL_THAN;
		} else if (((min1 <= max2) && (min1 >= min2))
				|| ((min2 <= max1) && (min2 >= min1))) {
			result = ConstrainedComparisonAnswer.COMPARABLE_TO;
		} else {
			result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
		}

		return result;
	}

	private static ConstrainedComparisonAnswer compareFromSet(
			AbsConcept constraint1, AbsConcept constraint2) {
		ConstrainedComparisonAnswer result;

		AbsAggregate fromSet1 = (AbsAggregate) constraint1
				.getAbsObject(ValueFromConstraintSchema.TEST_SLOT);
		AbsAggregate fromSet2 = (AbsAggregate) constraint2
				.getAbsObject(ValueFromConstraintSchema.TEST_SLOT);

		LinkedList<String> fromSet1Conc = new LinkedList<String>();
		LinkedList<String> fromSet2Conc = new LinkedList<String>();

		Iterator overSet1 = fromSet1.iterator();
		Iterator overSet2 = fromSet2.iterator();

		while (overSet1.hasNext()) {
			fromSet1Conc.add(((AbsPrimitive) overSet1.next()).getString());
		}

		while (overSet2.hasNext()) {
			fromSet2Conc.add(((AbsPrimitive) overSet2.next()).getString());
		}

		if (fromSet1Conc.equals(fromSet2Conc)) {
			result = ConstrainedComparisonAnswer.EQUALS;
		} else if (fromSet1Conc.containsAll(fromSet2Conc)) {
			result = ConstrainedComparisonAnswer.MORE_GENERAL_THAN;
		} else if (fromSet2Conc.containsAll(fromSet1Conc)) {
			result = ConstrainedComparisonAnswer.LESS_GENERAL_THAN;
		}

		else {
			fromSet1Conc.retainAll(fromSet2Conc);

			if (fromSet1Conc.size() != 0) {
				result = ConstrainedComparisonAnswer.COMPARABLE_TO;
			} else {
				result = ConstrainedComparisonAnswer.INCOMPARIBLE_WITH;
			}
		}

		return result;
	}
}
