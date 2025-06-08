package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardVisualisation;

import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsConcept;

import java.util.Iterator;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.ProcessOntology;

/**
 * 
 * @author mcassmc
 * 
 *         Just a bunch of static methods for making trees from my fluffy
 *         ontology concepts Not all of them are in fact purely ontology
 *         concepts - several of them are stored using Java objects.
 */

public class AbsStateVisualiser extends JPanel {

	private JEditorPane htmlPane;
	private JTree tree;
	private AbsConcept stateToShow;

	public AbsStateVisualiser(AbsConcept state) {

		this.stateToShow = state;

		// Create the nodes.
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(state
				.getString(ProcessOntology.STATE_ID));
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
		DefaultMutableTreeNode resourceType = null;
		AbsConcept simplifiedResourceSet = null;

		resourceTypes = new DefaultMutableTreeNode("Resource Types");
		top.add(resourceTypes);

		AbsAggregate resourceSets = (AbsAggregate) (this.stateToShow
				.getAbsObject(ProcessOntology.STATE_RESOURCES_CONSTRAINED));
		Iterator overResourceSets = resourceSets.iterator();

		while (overResourceSets.hasNext()) {
			simplifiedResourceSet = (AbsConcept) overResourceSets.next();
			resourceType = new DefaultMutableTreeNode(
					simplifiedResourceSet.getAbsObject(
							ProcessOntology.CONSTRAINED_RESOURCE_SET_TYPE)
							.toString());
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
}
