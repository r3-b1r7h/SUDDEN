package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardVisualisation;

import jade.content.abs.AbsConcept;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.DeclaredCapability;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PartialOrder;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PartialSolution;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.ResourceSet;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.State;

/**
 * 
 * @author mcassmc
 * 
 *         Takes a partial solution and pops out a kind of graphical viewer.
 *         Uses the JGraph library since TUE have already used this within XW.
 */

public class FancyPartialSolutionVisualiser {

	public static Font standardFont;

	public static void showPartialSolution(PartialSolution psInput) {
		standardFont = new Font(Font.SANS_SERIF, Font.PLAIN, 10);

		GraphModel graphModel = new DefaultGraphModel();
		GraphLayoutCache layoutCache = new GraphLayoutCache(graphModel,
				new DefaultCellViewFactory());
		JGraph graph = new JGraph(graphModel, layoutCache);
		graph.setFont(standardFont);

		// For getting at the cell corresponding to a given stateID (for drawing
		// the connections.).
		Hashtable<Integer, DefaultGraphCell> stateCellMap = new Hashtable<Integer, DefaultGraphCell>();

		ArrayList<DefaultGraphCell> cells = new ArrayList<DefaultGraphCell>();

		// Layout parameters
		int initialXPosition = 0;
		int initialYPosition = 80;
		int initialXSize = 90;
		int initialYSize = 40;
		int yExtraLineSize = 20;
		int XOffSet = 580;
		int partialXOffSet = 300;
		int partialYOffSet = 75;
		int excessYOffSet = 150;

		State initialState = (State) psInput.getStates().get(new Integer(-1));
		int resourcesAmount = initialState.getAllResources().size();

		DefaultGraphCell initialStateCell = new DefaultGraphCell(new String(
				"<html><b>Initial State</b><br>"
						+ getStateResources(initialState)));

		GraphConstants.setBounds(initialStateCell.getAttributes(),
				new Rectangle2D.Double(initialXPosition, initialYPosition,
						initialXSize, initialYSize
								+ (yExtraLineSize * resourcesAmount)));
		GraphConstants.setOpaque(initialStateCell.getAttributes(), true);
		GraphConstants.setBackground(initialStateCell.getAttributes(),
				Color.LIGHT_GRAY);
		GraphConstants.setBorderColor(initialStateCell.getAttributes(),
				Color.BLACK);
		GraphConstants.setFont(initialStateCell.getAttributes(), standardFont);

		DefaultPort portInitial = new DefaultPort();
		initialStateCell.add(portInitial);

		stateCellMap.put(-1, initialStateCell);
		cells.add(initialStateCell);

		System.out.println(" Sorting intermediate states");
		ArrayList<ArrayList<Integer>> sortedInterStatesIDs = sortIntermediateStates(
				psInput, cells);
		int currentXPosition;
		State tempState;
		DefaultGraphCell tempStateCell;
		int statesDoneSoFar;
		DefaultPort tempInputPort;
		DefaultPort tempOutputPort;

		ArrayList<Integer> currentSetofSortedStateIDs;
		System.out.println(" Sorted State IDs " + sortedInterStatesIDs);
		for (int i = 0; i < sortedInterStatesIDs.size(); i++) {
			currentSetofSortedStateIDs = sortedInterStatesIDs.get(i);
			System.out.println(" i is " + i);
			System.out.println(" now drawing " + currentSetofSortedStateIDs);
			currentXPosition = initialXPosition + ((i + 1) * XOffSet);

			statesDoneSoFar = 0;

			for (Integer stateID : currentSetofSortedStateIDs) {
				tempState = (State) psInput.getStates().get(stateID);

				resourcesAmount = tempState.getAllResources().size();
				tempStateCell = new DefaultGraphCell(new String(
						"<html><b>State_" + tempState.getStateID()
								+ " </b><br>" + getStateResources(tempState)));

				tempInputPort = new DefaultPort();
				tempOutputPort = new DefaultPort();

				// Input port = getChild(0), output port = getChild(1).
				tempStateCell.add(tempInputPort);
				tempStateCell.add(tempOutputPort);

				GraphConstants.setBounds(tempStateCell.getAttributes(),
						new Rectangle2D.Double(currentXPosition,
								initialYPosition
										+ (statesDoneSoFar * excessYOffSet),
								initialXSize, initialYSize
										+ (yExtraLineSize * resourcesAmount)));
				GraphConstants.setOpaque(tempStateCell.getAttributes(), true);
				GraphConstants.setBackground(tempStateCell.getAttributes(),
						Color.LIGHT_GRAY);
				GraphConstants.setBorderColor(tempStateCell.getAttributes(),
						Color.BLACK);
				GraphConstants.setFont(tempStateCell.getAttributes(),
						standardFont);

				cells.add(tempStateCell);
				stateCellMap.put(stateID, tempStateCell);

				statesDoneSoFar++;
			}
		}

		State finalState = (State) psInput.getStates().get(new Integer(-2));
		resourcesAmount = finalState.getAllResources().size();
		DefaultGraphCell finalStateCell = new DefaultGraphCell(new String(
				"<html><b>Final State</b><br>" + getStateResources(finalState)));

		int totalXOffset = (sortedInterStatesIDs.size() + 1) * XOffSet;
		GraphConstants.setBounds(finalStateCell.getAttributes(),
				new Rectangle2D.Double(initialXPosition + totalXOffset,
						initialYPosition, initialXSize, initialYSize
								+ (yExtraLineSize * resourcesAmount)));
		GraphConstants.setOpaque(finalStateCell.getAttributes(), true);
		GraphConstants.setBackground(finalStateCell.getAttributes(),
				Color.LIGHT_GRAY);
		GraphConstants.setBorderColor(finalStateCell.getAttributes(),
				Color.BLACK);
		GraphConstants.setFont(finalStateCell.getAttributes(), standardFont);

		DefaultPort portFinal = new DefaultPort();
		finalStateCell.add(portFinal);

		stateCellMap.put(-2, finalStateCell);
		cells.add(finalStateCell);

		// Crashes (out of memory with 3+ layers of states) due to code added
		// before this point
		// Not due to size either

		/*
		 * Now draw in the declared capabilities - these naturally become edges
		 * between the sundry states. However they can both come from and go to
		 * several states at once... Can't see how to do this natively with
		 * JGraph - needs a fake state inserting.
		 */

		Hashtable<Integer, ArrayList<AbsConcept>> statesConsumedFrom;
		Hashtable<Integer, ArrayList<AbsConcept>> statesProducedFor;
		DefaultGraphCell intermediary;

		// For helping avoiding drawing DC's on top of each other
		Hashtable<Integer, Integer> DCsLinkedFromState = new Hashtable<Integer, Integer>();

		for (DeclaredCapability dc : psInput.getDeclaredCapabilities()) {
			statesConsumedFrom = new Hashtable<Integer, ArrayList<AbsConcept>>();
			statesProducedFor = new Hashtable<Integer, ArrayList<AbsConcept>>();
			int tempStateID;
			ArrayList<AbsConcept> tempResourceArrayList;

			for (ResourceSet rs : dc.getResourcesRequired()) {
				tempStateID = psInput.getStateID(rs);
				if (statesConsumedFrom.get(tempStateID) == null) {
					tempResourceArrayList = new ArrayList<AbsConcept>();
					tempResourceArrayList.add(rs.getResourceType());
					statesConsumedFrom.put(tempStateID, tempResourceArrayList);
				} else {
					statesConsumedFrom.get(tempStateID).add(
							rs.getResourceType());
				}
			}

			for (ResourceSet rs : dc.getResourcesProduced()) {
				tempStateID = psInput.getStateID(rs);
				if (statesProducedFor.get(tempStateID) == null) {
					tempResourceArrayList = new ArrayList<AbsConcept>();
					tempResourceArrayList.add(rs.getResourceType());
					statesProducedFor.put(tempStateID, tempResourceArrayList);
				} else {
					statesProducedFor.get(tempStateID)
							.add(rs.getResourceType());
				}
			}

			intermediary = new DefaultGraphCell(dc.getOwningAgent());
			// Pick one state that we produce resources for, go backwards into
			// the resultant gap
			// Remember that this state has been used once (twice, three times
			// etc.).
			int statesFromPositionX;
			int statesFromPositionY;
			ArrayList<Integer> states = new ArrayList<Integer>(
					statesProducedFor.keySet());

			int stateIDToGoBackFrom = states.get(0);
			DefaultGraphCell stateToBuildOffBackwards = stateCellMap
					.get(stateIDToGoBackFrom);
			statesFromPositionX = (int) GraphConstants.getBounds(
					stateToBuildOffBackwards.getAttributes()).getMinX();
			statesFromPositionY = (int) GraphConstants.getBounds(
					stateToBuildOffBackwards.getAttributes()).getCenterY();

			Integer dcsAlreadyFromState = DCsLinkedFromState
					.get(stateIDToGoBackFrom);

			if (dcsAlreadyFromState == null) {
				DCsLinkedFromState.put(stateIDToGoBackFrom, 0);
				dcsAlreadyFromState = 0;
			} else {
				DCsLinkedFromState.put(stateIDToGoBackFrom,
						dcsAlreadyFromState + 1);
				dcsAlreadyFromState++;
			}

			GraphConstants.setBounds(intermediary.getAttributes(),
					new Rectangle2D.Double(
							statesFromPositionX - partialXOffSet,
							statesFromPositionY
									+ (partialYOffSet * dcsAlreadyFromState),
							150, 50));
			GraphConstants.setOpaque(intermediary.getAttributes(), false);
			GraphConstants.setFont(intermediary.getAttributes(), standardFont);

			DefaultPort portIn = new DefaultPort();
			DefaultPort portOut = new DefaultPort();
			intermediary.add(portIn);
			intermediary.add(portOut);

			cells.add(intermediary);

			DefaultEdge edge;
			String tempResources;
			ArrayList<AbsConcept> allResources;

			/*
			 * If null put in a token link to the first state - there's no a
			 * priori reason to do this, it just looks slightly prettier?
			 */
			if (statesConsumedFrom.keySet().size() == 0) {
				edge = new DefaultEdge("NoResourcesRequired");
				edge.setSource(initialStateCell.getChildAt(0));
				edge.setTarget(intermediary.getChildAt(0));
				GraphConstants.setLineEnd(edge.getAttributes(),
						GraphConstants.ARROW_CLASSIC);
				GraphConstants.setFont(edge.getAttributes(), standardFont);
				cells.add(edge);
			}

			for (Integer stateID : statesConsumedFrom.keySet()) {
				tempResources = new String("");

				allResources = statesConsumedFrom.get(stateID);

				for (AbsConcept absCon : allResources) {
					tempResources += absCon.toString() + ";";
				}

				edge = new DefaultEdge(tempResources);
				if (stateID != -1) {
					edge.setSource(stateCellMap.get(stateID).getChildAt(1));
				} else {
					// Initial state has only one port
					edge.setSource(stateCellMap.get(stateID).getChildAt(0));
				}
				edge.setTarget(intermediary.getChildAt(0));
				GraphConstants.setLineEnd(edge.getAttributes(),
						GraphConstants.ARROW_CLASSIC);
				GraphConstants.setFont(edge.getAttributes(), standardFont);
				cells.add(edge);
			}

			for (Integer stateID : statesProducedFor.keySet()) {
				tempResources = new String("");

				allResources = statesProducedFor.get(stateID);

				for (AbsConcept absCon : allResources) {
					tempResources += absCon.toString() + ";";
				}

				edge = new DefaultEdge(tempResources);
				edge.setTarget(stateCellMap.get(stateID).getChildAt(0));
				edge.setSource(intermediary.getChildAt(1));
				GraphConstants.setLineEnd(edge.getAttributes(),
						GraphConstants.ARROW_CLASSIC);
				GraphConstants.setFont(edge.getAttributes(), standardFont);
				cells.add(edge);
			}
		}

		// Stick all this information in and...
		graph.getGraphLayoutCache().insert(cells.toArray());

		// Draw the thing
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JScrollPane(graph));
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * 
	 * @param psInput
	 * @param cells
	 * 
	 *            Responsible for organising the intermediate states between the
	 *            initial & final ones. This is non trivial since it involves
	 *            sorting the states according to the partial order with some
	 *            potential parralelism.
	 */
	private static ArrayList<ArrayList<Integer>> sortIntermediateStates(
			PartialSolution psInput, ArrayList<DefaultGraphCell> cells) {
		PartialOrder partialOrderToUse = psInput.getPartialOrder();
		// Get all states just bigger than the initial state
		ArrayList<ArrayList<Integer>> sortedStatesIDs = new ArrayList<ArrayList<Integer>>();

		/*
		 * Being blunt my previous algorithm didn't (and wasn't ever going to!)
		 * workLet's try again :)
		 */

		ArrayList<Integer> unsortedStates = new ArrayList<Integer>(psInput
				.getStates().keySet());
		// Remove the initial/final states
		unsortedStates.remove(new Integer(-2));
		unsortedStates.remove(new Integer(-1));
		ArrayList<Integer> statesToRemove = new ArrayList<Integer>();

		Iterator overStatesIDs;
		int numberOfStatesLessThan;
		ArrayList<Integer> thisSetOfSortedStates;
		int minimalLessThanSize = Integer.MAX_VALUE;

		while (unsortedStates.size() > 0) {
			/*
			 * Find the smallest size
			 */
			for (Integer stateID : unsortedStates) {
				numberOfStatesLessThan = partialOrderToUse
						.getAllStatesLessThan(stateID).size();
				if (numberOfStatesLessThan < minimalLessThanSize) {
					minimalLessThanSize = numberOfStatesLessThan;
				}
			}

			// Now use this value to extract all states with this number of
			// states <
			// Store this as next group, remove and run again
			thisSetOfSortedStates = new ArrayList<Integer>();

			for (Integer stateID : unsortedStates) {
				numberOfStatesLessThan = partialOrderToUse
						.getAllStatesLessThan(stateID).size();

				if (numberOfStatesLessThan == minimalLessThanSize) {
					thisSetOfSortedStates.add(stateID);
					statesToRemove.add(stateID);
				}
			}

			sortedStatesIDs.add(thisSetOfSortedStates);
			unsortedStates.removeAll(statesToRemove);
			minimalLessThanSize = Integer.MAX_VALUE;
		}

		return sortedStatesIDs;
	}

	private static String getStateResources(State stateIn) {

		String result = "";

		ArrayList<ResourceSet> resources = new ArrayList<ResourceSet>(stateIn
				.getAllResources());

		for (ResourceSet r : resources) {
			boolean freeForwards = false;
			boolean freeBackwards = false;

			Iterator overStateIDsFreeBackwards = stateIn
					.getResourcesAvaliableBackwards().iterator();

			while (overStateIDsFreeBackwards.hasNext() && !freeBackwards) {
				freeBackwards = r.equals(stateIn
						.getResourceSet((Integer) overStateIDsFreeBackwards
								.next()));
			}

			Iterator overStateIDsFreeForwards = stateIn
					.getResourcesAvaliableForwards().iterator();

			while (overStateIDsFreeForwards.hasNext() && !freeForwards) {
				freeForwards = r.equals(stateIn
						.getResourceSet((Integer) overStateIDsFreeForwards
								.next()));
			}

			if (freeBackwards) {
				if (freeForwards) {
					result += "<font color=#FF0000>";
				} else {
					result += "<font color=#FF0080>";
				}
			} else {
				if (freeForwards) {
					result += "<font color=#FF8040>";
				} else {
					result += "<font color=#000000>";
				}
			}

			result += r.getResourceType() + "</font><br>";
		}

		return result;
	}
}
