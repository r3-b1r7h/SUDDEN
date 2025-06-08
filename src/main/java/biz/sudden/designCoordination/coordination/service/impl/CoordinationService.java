package biz.sudden.designCoordination.coordination.service.impl;

import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;

public interface CoordinationService {

	/**
	 * 
	 * An interface for the coordination modules service
	 * 
	 * Essentially just the one function call for now. It deals with all the
	 * material dependencies in a given concrete supply network by assinging
	 * produce to order or to stock to them & then deciding whether to use just
	 * in time or just in sequence to coordinate it all.
	 * 
	 */

	/**
	 * Take a CSN and coordinate it
	 */
	public abstract ConcreteSupplyNetwork coordinateCSN(
			ConcreteSupplyNetwork csnToCoordinate);

}
