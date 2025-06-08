package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardVisualisation;

import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.DeclaredCapability;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PartialSolution;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.ResourceSet;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.State;

/**
 * Builds a java based partial solution into a rather crude graphical
 * representation
 */

public class ConcretePartialSolutionVisualiser extends JPanel {

	private JTree tree;
	private PartialSolution psToShow;
	private DefaultMutableTreeNode top;

	public ConcretePartialSolutionVisualiser(PartialSolution ps) {
		this.psToShow = ps;

		// Create the nodes.
		this.top = new DefaultMutableTreeNode("Partial_Solution_"
				+ ps.getPartialSolutionID());
		this.top.setUserObject(ps);
		createNodes(top);

		// Create a tree that allows one selection at a time.
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Create the scroll pane and add the tree to it.
		JScrollPane treeView = new JScrollPane(tree);

		// Add the split pane to this panel.
		add(treeView);
	}

	private void createNodes(DefaultMutableTreeNode top) {
		/*
		 * Don't show all the information (ie constraints, amount etc) - just
		 * the pure resource types. So it's basically easy :)
		 */
		DefaultMutableTreeNode states = new DefaultMutableTreeNode(
				"States_in_Solution");
		DefaultMutableTreeNode state;
		DefaultMutableTreeNode biggerStates;
		DefaultMutableTreeNode lesserStates;
		DefaultMutableTreeNode incomparableStates;
		DefaultMutableTreeNode comparedState;
		State tempState;
		int tempStateID;

		Iterator overStates = this.psToShow.getStates().values().iterator();

		while (overStates.hasNext()) {
			tempState = (State) overStates.next();
			state = new ConcreteStateVisualiser(tempState).getAsTreeNode();

			tempStateID = tempState.getStateID();

			biggerStates = new DefaultMutableTreeNode("Bigger_States");
			Iterator overBiggerStates = this.psToShow.getPartialOrder()
					.getAllStatesBiggerThan(tempStateID).iterator();
			while (overBiggerStates.hasNext()) {
				comparedState = new DefaultMutableTreeNode(overBiggerStates
						.next());
				biggerStates.add(comparedState);
			}

			lesserStates = new DefaultMutableTreeNode("Lesser_States");
			Iterator overLesserStates = this.psToShow.getPartialOrder()
					.getAllStatesLessThan(tempStateID).iterator();
			while (overLesserStates.hasNext()) {
				comparedState = new DefaultMutableTreeNode(overLesserStates
						.next());
				lesserStates.add(comparedState);
			}

			incomparableStates = new DefaultMutableTreeNode(
					"Incomparable_States");
			Iterator overICStates = this.psToShow.getPartialOrder()
					.getAllStatesIncomparableTo(tempStateID).iterator();
			while (overICStates.hasNext()) {
				comparedState = new DefaultMutableTreeNode(overICStates.next());
				incomparableStates.add(comparedState);
			}

			state.add(lesserStates);
			state.add(biggerStates);
			state.add(incomparableStates);

			states.add(state);
		}

		top.add(states);

		DefaultMutableTreeNode declaredCapabilities = new DefaultMutableTreeNode(
				"Declared_Capabilities");

		Iterator<DeclaredCapability> DCs = this.psToShow
				.getDeclaredCapabilities().iterator();

		DeclaredCapability tempDC;
		DefaultMutableTreeNode inputState;
		DefaultMutableTreeNode outputState;
		DefaultMutableTreeNode tempResource;
		DefaultMutableTreeNode DCNode;
		while (DCs.hasNext()) {
			tempDC = DCs.next();
			DCNode = new DefaultMutableTreeNode("Declared_Capability_of_"
					+ tempDC.getOwningAgent());

			inputState = new DefaultMutableTreeNode("Input_Resources");
			Iterator<ResourceSet> resources = tempDC.getResourcesRequired()
					.iterator();
			while (resources.hasNext()) {
				inputState.add(new DefaultMutableTreeNode(resources.next()
						.getResourceType()));
			}
			DCNode.add(inputState);

			outputState = new DefaultMutableTreeNode("Output_Resources");
			resources = tempDC.getResourcesProduced().iterator();
			while (resources.hasNext()) {
				outputState.add(new DefaultMutableTreeNode(resources.next()
						.getResourceType()));
			}
			DCNode.add(outputState);

			declaredCapabilities.add(DCNode);
		}
		top.add(declaredCapabilities);

	}

	public DefaultMutableTreeNode getAsTreeNode() {
		return this.top;
	}

	public void makeVisible() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		// Create and set up the window.
		JFrame frame = new JFrame("PartialSolutionState");
		frame.setContentPane(this);
		frame.pack();
		frame.setVisible(true);
	}
}
