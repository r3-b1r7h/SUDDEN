package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * A class to support the noticeboard algorithm - this holds the partial order
 * between the states. This is never exposed to the process agents.
 * 
 * It's very important to remember that the number assigned to a state is just
 * an ID - currently it corresponds to the time at which it was added so might
 * be mistaken for a linear ordering.
 * 
 * @author mcassmc
 */

// Currently a profoundly simplistic implementation - likely to be extremly
// inefficent but hopefully it will run.*Every* state stores the set of states
// it's
// less/greater/incomparable to. The transitiveClosure method is reponsible for
// keeping
// everthing holding together.
// Everything works on manipulating numbers - instead of whole states it's
// stateID's.
public class PartialOrder {

	// Contains a set of order fragments
	private Hashtable<Integer, OrderFragment> orderFragments;

	/*
	 * IDConstants for the initial/final elements. State ID's other than these
	 * should be positive
	 * 
	 * Oh yes and LinkedLists just to make things easier when adding them?!
	 */
	public final static Integer INITITIAL_STATE_ID = new Integer(-1);

	public final static Integer FINAL_STATE_ID = new Integer(-2);

	private LinkedList<Integer> justInitialState;

	private LinkedList<Integer> justFinalState;

	public PartialOrder() {
		this.orderFragments = new Hashtable<Integer, OrderFragment>();

		this.justInitialState = new LinkedList<Integer>();
		this.justInitialState.add(INITITIAL_STATE_ID);

		this.justFinalState = new LinkedList<Integer>();
		this.justFinalState.add(FINAL_STATE_ID);

		/*
		 * Stick in the initial/final states.
		 */

		OrderFragment initialFragment = new OrderFragment(INITITIAL_STATE_ID
				.intValue(), new LinkedList<Integer>(), justFinalState,
				new LinkedList<Integer>());
		orderFragments.put(INITITIAL_STATE_ID, initialFragment);

		OrderFragment finalFragment = new OrderFragment(FINAL_STATE_ID
				.intValue(), justInitialState, new LinkedList<Integer>(),
				new LinkedList<Integer>());
		orderFragments.put(FINAL_STATE_ID, finalFragment);
	}

	/*
	 * public OrderFragment getOrderAtState(Integer stateID) { return
	 * (OrderFragment) this.orderFragments.get(stateID); }
	 */

	public Set getAllStatesLessThan(Integer stateID) {
		OrderFragment tempFragment = this.orderFragments.get(stateID);
		return tempFragment.getSmallerStates();
	}

	public Set getAllStatesBiggerThan(Integer stateID) {
		OrderFragment tempFragment = this.orderFragments.get(stateID);
		return tempFragment.getBiggerStates();
	}

	public Set getAllStatesIncomparableTo(Integer stateID) {
		OrderFragment tempFragment = this.orderFragments.get(stateID);
		return tempFragment.getIncomparableStates();
	}

	// TODO - functions to compare states without fetching the order fragment(s)
	// first

	/**
	 * Add a unspecified new state to the partial order.
	 * 
	 * @throws CycleDetectedException
	 * 
	 */
	public void addNewState(int stateID) throws CycleDetectedException {
		// * Due to the 'wonderful' design we've got to update every other entry
		// here.
		// Well at least we have to in the function we call!

		addNewState(stateID, new HashSet<Integer>(), new HashSet<Integer>());
	}

	/**
	 * Add a new state to the partial order - with a specified list of states
	 * that it's bigger and less than.
	 * 
	 * The function calculates the transitive closure of the set of
	 * previous/later states so there is no need to specify them in full.
	 * 
	 * @throws CycleDetectedException
	 */
	public void addNewState(int stateID, Set<Integer> previousStates,
			Set<Integer> laterStates) throws CycleDetectedException {
		// ////System.out.println("Partial Order currently " + this);
		Integer stateIDInteger = new Integer(stateID);

		// First calculate the transitive closure of the sets of states.
		Set<Integer> fullPreviousStates = getTransitiveClosureLessThan(previousStates);
		Set<Integer> fullLaterStates = getTransitiveClosureGreaterThan(laterStates);
		Set<Integer> incomparibleStates = getRemainingStates(
				fullPreviousStates, fullLaterStates);

		/*
		 * Update the *other* states (a not ideal side effect of the design)
		 */
		OrderFragment tempOrderFragment;
		Set<Integer> tempSet;

		for (Integer previousStateID : fullPreviousStates) {
			tempOrderFragment = orderFragments.get(previousStateID);
			tempSet = tempOrderFragment.getBiggerStates();
			tempSet.add(stateIDInteger);
			tempOrderFragment.setBiggerStates(tempSet);
			addFragment(previousStateID, tempOrderFragment);
		}
		// ////System.out.println("Partial Order currently " + this);

		for (Integer laterStateID : fullLaterStates) {
			tempOrderFragment = orderFragments.get(laterStateID);
			tempSet = tempOrderFragment.getSmallerStates();
			tempSet.add(stateIDInteger);
			tempOrderFragment.setSmallerStates(tempSet);
			addFragment(laterStateID, tempOrderFragment);
		}
		// ////System.out.println("Partial Order currently " + this);

		for (Integer incompStateID : incomparibleStates) {
			tempOrderFragment = orderFragments.get(incompStateID);
			tempSet = tempOrderFragment.getIncomparableStates();
			tempSet.add(stateIDInteger);
			tempOrderFragment.setIncomparableStates(tempSet);
			addFragment(incompStateID, tempOrderFragment);
		}
		// ////System.out.println("Partial Order currently " + this);

		OrderFragment fragment = new OrderFragment(stateID, fullPreviousStates,
				fullLaterStates, incomparibleStates);
		addFragment(stateIDInteger, fragment);
		// ////System.out.println("Partial Order currently " + this);
	}

	/**
	 * Use this instead of adding things directly to orderFragments - it checks
	 * for cycles being introduced.
	 * 
	 * @throws CycleDetectedException
	 * 
	 */

	private void addFragment(Integer stateID, OrderFragment state)
			throws CycleDetectedException {
		// ////System.out.println(" adding " + stateID + " " + state);

		// Sadly the retains methods have nasty side effects. So the check for
		// intersections has to be brute forced.

		Set lessThanStates = state.getSmallerStates();
		Set biggerThanStates = state.getBiggerStates();
		Set incomparibleStates = state.getIncomparableStates();

		// That is success in finding a failure.
		boolean success = false;

		// We're OK if we have no non empty intersections. (because each
		// fragment contains the transitive closure.).
		Iterator overLessThan = lessThanStates.iterator();
		Iterator overMoreThan = biggerThanStates.iterator();
		Integer tempIntToCheck;

		while (!success && overLessThan.hasNext()) {
			tempIntToCheck = (Integer) overLessThan.next();
			success = biggerThanStates.contains(tempIntToCheck);
			success = success || incomparibleStates.contains(tempIntToCheck);
		}

		while (!success && overMoreThan.hasNext()) {
			tempIntToCheck = (Integer) overMoreThan.next();
			success = incomparibleStates.contains(tempIntToCheck);
		}

		// System.out.println(" Adding order fragment " + state);
		// System.out.println(" At state " + stateID);

		// TODO - good code, bad example!
		if (success) {
			throw new CycleDetectedException(" Cycle detected at state "
					+ stateID + " as a result of adding a new state");
		} else {
			orderFragments.put(stateID, state);
		}
	}

	/**
	 * Takes a set of states a new state is going to be less than and closes it
	 */
	private Set<Integer> getTransitiveClosureLessThan(
			Set<Integer> previousStates) {

		// I think one pass works when combined with the algorithimic build up
		// from an empty solution.
		// The general case needs a recursive call and a check to see if no new
		// states were added in the previous run - much more involved.

		Set<Integer> result = new HashSet<Integer>();
		result.addAll(previousStates);

		Integer tempInteger;
		OrderFragment tempFragment;
		Iterator overPreviousStates = previousStates.iterator();

		while (overPreviousStates.hasNext()) {
			tempInteger = (Integer) overPreviousStates.next();
			tempFragment = orderFragments.get(tempInteger);
			// ////System.out.println(" Int " + tempInteger + " fragment " +
			// tempFragment);
			result.addAll(tempFragment.getSmallerStates());
			// Also update the state you're adding!
		}

		return result;
	}

	/**
	 * Takes a set of states a new state is going to be bigger than and closes
	 * it
	 */
	private Set<Integer> getTransitiveClosureGreaterThan(
			Set<Integer> afterStates) {

		// I think one pass works when combined with the algorithimic build up
		// from an empty solution.
		// The general case needs a recursive call and a check to see if no new
		// states were added in the previous run - much more involved.

		Set<Integer> result = new HashSet<Integer>();
		result.addAll(afterStates);

		OrderFragment tempFragment;
		Integer tempInteger;
		Iterator overSubsequentStates = afterStates.iterator();

		while (overSubsequentStates.hasNext()) {
			tempInteger = (Integer) overSubsequentStates.next();
			tempFragment = orderFragments.get(tempInteger);
			result.addAll(tempFragment.getBiggerStates());
		}

		return result;
	}

	/**
	 * Takes two sets of states and returns the ones on the list - useful
	 * finding the set of states a state is incomparible to.
	 */
	private Set<Integer> getRemainingStates(Set<Integer> list1,
			Set<Integer> list2) {
		HashSet<Integer> result = new HashSet<Integer>();

		Integer tempInt;
		Enumeration overTableKeys = orderFragments.keys();

		while (overTableKeys.hasMoreElements()) {
			tempInt = (Integer) overTableKeys.nextElement();
			if ((!list1.contains(tempInt)) && (!list2.contains(tempInt))) {
				result.add(tempInt);
			}
		}

		return result;
	}

	@Override
	public String toString() {
		String result = "";

		Enumeration it = orderFragments.elements();

		while (it.hasMoreElements()) {
			result += " " + ((OrderFragment) it.nextElement()).toString() + " ";
		}

		return result;
	}

	@Override
	public PartialOrder clone() {
		PartialOrder result = new PartialOrder();

		Hashtable<Integer, OrderFragment> clonedOrderFragments = new Hashtable<Integer, OrderFragment>();

		for (Integer I : this.orderFragments.keySet()) {
			OrderFragment nextOrderFragment = this.orderFragments.get(I);
			OrderFragment nextClone = nextOrderFragment.clone();
			// //System.out.println(" Before cloning " + nextOrderFragment +
			// " Afterwards " + nextClone);
			clonedOrderFragments.put(new Integer(I.intValue()), nextClone);
		}

		result.orderFragments = clonedOrderFragments;
		return result;
	}
}
