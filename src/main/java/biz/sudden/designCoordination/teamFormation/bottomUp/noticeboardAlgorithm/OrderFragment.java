package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * A supporting class for the PartialOrder class. Holds a unit of data (StateID,
 * States <, States >, States incomparible.).
 * 
 * @author mcassmc
 */

public class OrderFragment {

	// I think these are self explanatory
	public int stateID;
	private HashSet<Integer> smallerStates;
	private HashSet<Integer> biggerStates;
	private HashSet<Integer> incomparableStates;

	public OrderFragment(int stateID, Collection<Integer> smallerStates,
			Collection<Integer> biggerStates,
			Collection<Integer> incomparibleStates) {
		this.stateID = stateID;
		this.smallerStates = new HashSet<Integer>(smallerStates);
		this.biggerStates = new HashSet<Integer>(biggerStates);
		this.incomparableStates = new HashSet<Integer>(incomparibleStates);
	}

	public Set<Integer> getSmallerStates() {
		return this.smallerStates;
	}

	public Set<Integer> getBiggerStates() {
		return this.biggerStates;
	}

	public Set<Integer> getIncomparableStates() {
		return this.incomparableStates;
	}

	public int getStateID() {
		return this.stateID;
	}

	public void setSmallerStates(Collection<Integer> c) {
		this.smallerStates = new HashSet<Integer>(c);
	}

	public void setBiggerStates(Collection<Integer> c) {
		this.biggerStates = new HashSet<Integer>(c);
	}

	public void setIncomparableStates(Collection<Integer> c) {
		this.incomparableStates = new HashSet<Integer>(c);
	}

	public void setStateID(int i) {
		this.stateID = i;
	}

	@Override
	public String toString() {
		String result = "[ State " + stateID + " ( <; ";
		Iterator it = biggerStates.iterator();
		while (it.hasNext()) {
			result += ((Integer) it.next()).intValue() + " : ";
		}

		result += ") (>;";

		it = smallerStates.iterator();
		while (it.hasNext()) {
			result += ((Integer) it.next()).intValue() + " : ";
		}

		result += ") (~; ";

		it = incomparableStates.iterator();
		while (it.hasNext()) {
			result += ((Integer) it.next()).intValue() + " : ";
		}
		result += ") ]";

		return result;
	}

	@Override
	public OrderFragment clone() {
		Collection<Integer> clonedBiggerStates = new LinkedList();
		for (Integer I : this.biggerStates) {
			clonedBiggerStates.add(new Integer(I.intValue()));
		}

		Collection<Integer> clonedSmallerStates = new LinkedList();
		for (Integer I : this.smallerStates) {
			clonedSmallerStates.add(new Integer(I.intValue()));
		}

		Collection<Integer> clonedIncStates = new LinkedList();
		for (Integer I : this.incomparableStates) {
			clonedIncStates.add(new Integer(I.intValue()));
		}

		OrderFragment result = new OrderFragment(stateID, clonedSmallerStates,
				clonedBiggerStates, clonedIncStates);
		return result;
	}
}
