package biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardVisualisation;

import jade.content.abs.AbsConcept;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import biz.sudden.designCoordination.teamFormation.bottomUp.noticeboardAlgorithm.PartialSolution;
import biz.sudden.designCoordination.teamFormation.bottomUp.ontologies.CreateNoticeboardOntology;

public class ConcreteNoticeboardVisualiser extends JPanel {

	private JTree tree;
	private JTree psTree;
	private JTree csTree;
	private DefaultTreeModel psTreeModel;
	private DefaultTreeModel csTreeModel;
	private JSplitPane topLevelSplitPane;
	private JSplitPane solutionsSplitPane;
	private JFrame frame;
	private DefaultMutableTreeNode top;
	private DefaultMutableTreeNode partialSolutionsTop;
	private DefaultMutableTreeNode completeSolutionsTop;
	private AbsConcept noticeboardDescription;
	private PartialSolution initialSolution;

	public ConcreteNoticeboardVisualiser(AbsConcept nbDescr,
			PartialSolution pSln, String agentsName) {
		// Provide minimum sizes for the two components in the split pane
		Dimension preferredSize = new Dimension(300, 600);
		Dimension psPreferredSize = new Dimension(300, 500);
		Dimension csPreferredSize = new Dimension(300, 100);

		this.noticeboardDescription = nbDescr;
		this.initialSolution = pSln;

		// Create the nodes.
		top = new DefaultMutableTreeNode(agentsName);

		// Create a tree that allows one selection at a time.
		tree = new JTree(top);

		DefaultMutableTreeNode initialSolutionNode = new DefaultMutableTreeNode(
				"Initial_Solution");
		initialSolutionNode.add(new ConcretePartialSolutionVisualiser(
				initialSolution).getAsTreeNode());
		top.add(initialSolutionNode);

		addNodes(top);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode tempNode = (DefaultMutableTreeNode) e
						.getPath().getLastPathComponent();
				Object returnedObject = tempNode.getUserObject();

				if ((returnedObject == null)
						|| !returnedObject.getClass().equals(
								PartialSolution.class)) {
					return;
				}

				else {
					PartialSolution returnedPartialSolution = (PartialSolution) returnedObject;
					FancyPartialSolutionVisualiser
							.showPartialSolution(returnedPartialSolution);
				}
			}
		});

		// Create the scroll pane and add the tree to it.
		JScrollPane treeView = new JScrollPane(tree);

		// Create the nodes.
		partialSolutionsTop = new DefaultMutableTreeNode("Partial_Solutions");
		completeSolutionsTop = new DefaultMutableTreeNode("Complete_Solutions");

		// Create a tree for partial solutions
		psTreeModel = new DefaultTreeModel(partialSolutionsTop);
		psTree = new JTree(psTreeModel);
		psTree.setEditable(true);
		psTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		psTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode tempNode = (DefaultMutableTreeNode) e
						.getPath().getLastPathComponent();
				Object returnedObject = tempNode.getUserObject();

				if ((returnedObject == null)
						|| !returnedObject.getClass().equals(
								PartialSolution.class)) {
					return;
				}

				else {
					PartialSolution returnedPartialSolution = (PartialSolution) returnedObject;
					FancyPartialSolutionVisualiser
							.showPartialSolution(returnedPartialSolution);
				}
			}
		});

		JScrollPane partialSolutionsView = new JScrollPane(psTree);

		// And complete ones
		csTreeModel = new DefaultTreeModel(completeSolutionsTop);
		csTree = new JTree(csTreeModel);
		csTree.setEditable(true);
		JScrollPane completeSolutionsView = new JScrollPane(csTree);

		csTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode tempNode = (DefaultMutableTreeNode) e
						.getPath().getLastPathComponent();
				Object returnedObject = tempNode.getUserObject();

				if ((returnedObject == null)
						|| !returnedObject.getClass().equals(
								PartialSolution.class)) {
					return;
				}

				else {
					PartialSolution returnedPartialSolution = (PartialSolution) returnedObject;
					FancyPartialSolutionVisualiser
							.showPartialSolution(returnedPartialSolution);
				}
			}
		});

		partialSolutionsView.setPreferredSize(psPreferredSize);
		completeSolutionsView.setPreferredSize(csPreferredSize);
		treeView.setPreferredSize(preferredSize);

		solutionsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
				partialSolutionsView, completeSolutionsView);

		topLevelSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
				treeView, solutionsSplitPane);
		topLevelSplitPane.setResizeWeight(0.5);

		add(topLevelSplitPane);
	}

	/**
	 * Add in the utility information etc
	 * 
	 * @param top
	 */
	private void addNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode utility = new DefaultMutableTreeNode(
				"Utility_Information");

		AbsConcept utilityConcept = (AbsConcept) this.noticeboardDescription
				.getAbsObject(CreateNoticeboardOntology.NOTICEBOARD_DESCRIPTION_UTILITY);

		DefaultMutableTreeNode averageCost = new DefaultMutableTreeNode(
				"Average_Cost");
		averageCost
				.add(new DefaultMutableTreeNode(
						utilityConcept
								.getInteger(CreateNoticeboardOntology.NOTICEBOARD_UTILITY_EXPECTED_COST)));
		utility.add(averageCost);

		DefaultMutableTreeNode requiredBy = new DefaultMutableTreeNode(
				"Required_by");
		requiredBy
				.add(new DefaultMutableTreeNode(
						utilityConcept
								.getString(CreateNoticeboardOntology.NOTICEBOARD_UTILITY_COMPANY_REQUESTING_SERVICE)));
		utility.add(requiredBy);

		AbsConcept rangeRequired = (AbsConcept) utilityConcept
				.getAbsObject(CreateNoticeboardOntology.NOTICEBOARD_UTILITY_AMOUNT_REQUIRED);

		DefaultMutableTreeNode expectedTotalAmountMin = new DefaultMutableTreeNode(
				"Expected_Amount_Minimum");
		expectedTotalAmountMin.add(new DefaultMutableTreeNode(rangeRequired
				.getInteger(CreateNoticeboardOntology.RANGE_MIN)));
		utility.add(expectedTotalAmountMin);

		DefaultMutableTreeNode expectedTotalAmountMax = new DefaultMutableTreeNode(
				"Expected_Amount_Maximum");
		expectedTotalAmountMax.add(new DefaultMutableTreeNode(rangeRequired
				.getInteger(CreateNoticeboardOntology.RANGE_MAX)));
		utility.add(expectedTotalAmountMax);

		top.add(utility);
	}

	public void addPartialSolution(PartialSolution ps) {
		DefaultMutableTreeNode partialSolutionNode = new ConcretePartialSolutionVisualiser(
				ps).getAsTreeNode();
		this.psTreeModel.insertNodeInto(partialSolutionNode,
				partialSolutionsTop, partialSolutionsTop.getChildCount());
		this.psTree.makeVisible(new TreePath(partialSolutionNode.getPath()));
	}

	public void addCompleteSolution(PartialSolution ps) {
		DefaultMutableTreeNode completeSolutionNode = new ConcretePartialSolutionVisualiser(
				ps).getAsTreeNode();
		this.csTreeModel.insertNodeInto(completeSolutionNode,
				completeSolutionsTop, completeSolutionsTop.getChildCount());
		this.csTree.makeVisible(new TreePath(completeSolutionNode.getPath()));
	}

	public void makeVisible() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		// Create and set up the window.

		// TODO - currently switched off in Sudden to avoid headless exceptions.
		/*
		 * this.frame = new JFrame("NoticeBoardInformation");
		 * frame.setContentPane(this); frame.pack();
		 */
		// frame.setVisible(true);
	}
}
