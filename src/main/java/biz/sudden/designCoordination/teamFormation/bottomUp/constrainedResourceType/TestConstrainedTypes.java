package biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType;

import java.util.LinkedList;

public class TestConstrainedTypes {

	public static void main(String[] args)
			throws UnknownConstraintTypeException {
		/*
		 * AbsConcept constraint1 = new
		 * AbsConcept(FromRangeConstraintSchema.FROM_RANGE_CONSTRAINT); /
		 * AbsAggregate values = new AbsAggregate(BasicOntology.SET);
		 * values.add(AbsPrimitive.wrap("boo"));
		 * values.add(AbsPrimitive.wrap("foo"));
		 * constraint1.set(ValueFromConstraintSchema.TEST_SLOT,values);*
		 * constraint1.set(FromRangeConstraintSchema.MINIMUM,2);
		 * constraint1.set(FromRangeConstraintSchema.MAXIMUM,6);
		 * 
		 * AbsConcept concept1 = new AbsConcept(ProductOntology.SUBPRODUCT);
		 * //concept1
		 * .set(ProductOntology.SUBPRODUCT_SLOT1,AbsPrimitive.wrap("boo"));
		 * concept1.set(ProductOntology.SUBPRODUCT_SLOT1,"g");
		 * concept1.set(ProductOntology.SUPERPRODUCT_SLOT1,new
		 * AbsConcept(ProductOntology.CONTAINED_PRODUCT));
		 * concept1.set(ProductOntology.SUBPRODUCT_SLOT2,constraint1);
		 * 
		 * AbsConcept concept2 = new AbsConcept(ProductOntology.SUPERPRODUCT);
		 * concept2.set(ProductOntology.SUPERPRODUCT_SLOT1,new
		 * AbsConcept(ProductOntology.CONTAINED_PRODUCT));
		 * 
		 * AbsConcept constraint2 = new
		 * AbsConcept(FromRangeConstraintSchema.FROM_RANGE_CONSTRAINT); /*
		 * AbsAggregate values2 = new AbsAggregate(BasicOntology.SET);
		 * values2.add(AbsPrimitive.wrap("boo"));
		 * values2.add(AbsPrimitive.wrap("foo"));
		 * constraint2.set(ValueFromConstraintSchema.TEST_SLOT,values2);*
		 * constraint2.set(FromRangeConstraintSchema.MINIMUM,1);
		 * constraint2.set(FromRangeConstraintSchema.MAXIMUM,5);
		 * 
		 * AbsConcept concept3 = new AbsConcept(ProductOntology.SUBPRODUCT);
		 * concept3.set(ProductOntology.SUBPRODUCT_SLOT1,"g");
		 * //concept3.set(ProductOntology.SUBPRODUCT_SLOT2,constraint2);
		 * concept3.set(ProductOntology.SUBPRODUCT_SLOT2,7);
		 * concept3.set(ProductOntology.SUPERPRODUCT_SLOT1,new
		 * AbsConcept(ProductOntology.CONTAINED_PRODUCT));
		 * 
		 * 
		 * System.out.println(" Comparing " + concept1 + " to " + concept3);
		 * 
		 * 
		 * System.out.println(ConstrainedTypeComparator.compareConstrainedTypes(
		 * concept1,concept3));
		 */
		LinkedList<LinkedList<Integer>> mappings = new LinkedList<LinkedList<Integer>>();
		ConstrainedTypeComparator.generateMappings(4, mappings);
		System.out.println(mappings);
	}

}
