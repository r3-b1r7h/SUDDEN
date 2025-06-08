package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm;

import jade.content.abs.AbsConcept;
import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.ConstrainedComparisonAnswer;
import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.ConstrainedTypeComparator;
import biz.sudden.designCoordination.teamFormation.bottomUp.constrainedResourceType.UnknownConstraintTypeException;

/**
 * A class wrapping the AbsConcept of a resource set
 * 
 * Eventually this will contain an AbsConcept from a JADE ontology. For the sake
 * of getting the algorithm running it currently contains only a collection of
 * Strings.
 * 
 * As such many of the function calls are empty due to not making contextual
 * sense.
 * 
 * @author mcassmc
 * 
 */

public class ResourceSet {

	// Notionally will contain constraints on it's attributes this
	private AbsConcept resourceType;

	// To be used later
	private int relativeAmount;

	private int resourceSetID;

	/*
	 * For the generation of sucessive resourceSetID's
	 */
	private static int IDNumber = 0;

	public ResourceSet(AbsConcept type) {
		this.resourceType = type;
		this.resourceSetID = ResourceSet.IDNumber;
		ResourceSet.IDNumber++;
	}

	public AbsConcept getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(AbsConcept newType) {
		this.resourceType = newType;
	}

	/**
	 * This method should in general *not* be called. It is used to ensure that
	 * resource ID's can be round tripped.
	 * 
	 * @param i
	 */
	public void setResourceSetID(int i) {
		this.resourceSetID = i;
	}

	public int getRelativeAmount() {
		return this.relativeAmount;
	}

	public void setRelativeAmount(int relativeAmount) {
		this.relativeAmount = relativeAmount;
	}

	/**
	 * Purposefully no set method for this number.
	 */
	public int getID() {
		return this.resourceSetID;
	}

	/*
	 * The methods below are the ones which are going to get more complex
	 */

	@Override
	public boolean equals(Object anotherResource) {
		if (anotherResource.getClass().equals(ResourceSet.class)) {
			try {
				return (ConstrainedTypeComparator.compareConstrainedTypes(
						this.resourceType, ((ResourceSet) anotherResource)
								.getResourceType()) == ConstrainedComparisonAnswer.EQUALS);
			} catch (UnknownConstraintTypeException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	// These don't currently make sense.
	public boolean isMoreGeneralThan(ResourceSet anotherResource) {
		return false;
	}

	// These don't currently make sense - no type heirarchy among strings!!
	public boolean isLessGeneralThan(ResourceSet anotherResource) {
		return false;
	}

	@Override
	public String toString() {
		String result = "";

		result += " ResourceSet " + this.resourceSetID + " type "
				+ resourceType;

		return result;
	}

}
