package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardVisualisation;

import java.util.Iterator;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.ResourceSet;
import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.State;

/**
 * 
 * @author mcassmc
 * 
 *         Just a bunch of static methods for making trees from my fluffy
 *         ontology concepts Not all of them are in fact purely ontology
 *         concepts - several of them are stored using Java objects.
 */

public class ConcreteStateVisualiser extends JPanel {

	private JEditorPane htmlPane;
	private JTree tree;
	private State stateToShow;
	private DefaultMutableTreeNode top;

	public ConcreteStateVisualiser(State state) {

		this.stateToShow = state;

		// Create the nodes.
		this.top = new DefaultMutableTreeNode(state.getStateID());
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
		DefaultMutableTreeNode resourceTypes = null;
		DefaultMutableTreeNode resourceStatus = null;
		DefaultMutableTreeNode resourcesFreeBackwards = null;
		DefaultMutableTreeNode resourceType = null;
		ResourceSet simplifiedResourceSet = null;
		Integer tempStateID;
		int StateID;

		resourceTypes = new DefaultMutableTreeNode("Resource Present");
		top.add(resourceTypes);

		Iterator<ResourceSet> overResourceSets = this.stateToShow
				.getAllResources().iterator();

		while (overResourceSets.hasNext()) {
			simplifiedResourceSet = overResourceSets.next();
			resourceType = new DefaultMutableTreeNode(simplifiedResourceSet
					.getResourceType());

			StateID = simplifiedResourceSet.getID();

			boolean foundBackwards = false;
			Iterator overResFreeBack = this.stateToShow
					.getResourcesAvaliableBackwards().iterator();
			while (overResFreeBack.hasNext() && !foundBackwards) {
				tempStateID = (Integer) overResFreeBack.next();
				foundBackwards = (tempStateID == StateID);
			}

			boolean foundForwards = false;
			Iterator overResFreeForwards = this.stateToShow
					.getResourcesAvaliableForwards().iterator();
			while (overResFreeForwards.hasNext() && !foundForwards) {
				tempStateID = (Integer) overResFreeForwards.next();
				foundForwards = (tempStateID == StateID);
			}

			if (foundForwards) {
				if (foundBackwards) {
					resourceStatus = new DefaultMutableTreeNode(
							"Free in both directions");
				} else {
					resourceStatus = new DefaultMutableTreeNode(
							"Free Forwards only");
				}
			} else {
				if (foundBackwards) {
					resourceStatus = new DefaultMutableTreeNode(
							"Free Backwards Only");
				} else {
					resourceStatus = new DefaultMutableTreeNode(
							"Not free at all");
				}
			}

			resourceType.add(resourceStatus);
			resourceTypes.add(resourceType);
		}
	}

	public void makeVisible() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		// Create and set up the window.
		JFrame frame = new JFrame("PartialSolutionState");
		frame.setContentPane(this);
		frame.pack();
		frame.setVisible(true);
	}

	public DefaultMutableTreeNode getAsTreeNode() {
		return this.top;
	}
}
