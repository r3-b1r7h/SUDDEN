package biz.sudden.baseAndUtility.web.controller.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.service.ServiceManagementService;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.UserObject.UserObjectActionEvent;

import com.icesoft.faces.component.dragdrop.DndEvent;
import com.icesoft.faces.component.dragdrop.DragEvent;
import com.icesoft.faces.component.dragdrop.DropEvent;

/**
 * Tree Bean for UI
 * 
 * @author MN
 * 
 */
public class TreeDragDrop implements ActionListener {

	private final Logger logger = Logger.getLogger(this.getClass());

	private ConnectableService connectableService;
	private ServiceManagementService baseService;
	private static final SelectItem[] COMPONENT_ITEMS = new SelectItem[] {
	// new SelectItem("Service"),
	new SelectItem("Droplet")
	/* new SelectItem("Process") */
	};
	private static final SelectItem[] copyItems = new SelectItem[] {
			new SelectItem(true, "copy single element"),
			new SelectItem(false, "copy including all subelements") };

	private static int NODES_CHANGED_EVENT = 1;
	private static int NODES_INSERTED_EVENT = 2;
	private static int NODES_REMOVED_EVENT = 3;
	private static int NODES_STRUCTURECHANGED_EVENT = 4;

	private UserObject selectedNodeObject = null;
	private String dragMessage = "";
	// object reference used to delete and copy the node
	// private ConnectableUserObject selectedNodeObject = null;
	private String name; // root node label, used to insure that it can't be
							// deleted.
	private static final String ROOT_NODE_TEXT = "Target Root Node";
	private boolean popupVisible = false;
	private boolean simpleCopy = true;
	private boolean singleClickCopyToTarget = true;
	private Tree sourceTree;
	private Tree targetTree;

	protected List<ActionListener> actionlisteners = new ArrayList<ActionListener>();

	public TreeDragDrop() {

	}

	/**
	 * default Constructor fills source tree and target tree with example nodes
	 */
	public TreeDragDrop(ConnectableService conn, ScopeController sc) {
		// default source tree
		sourceTree = new Tree(conn, sc);
		sourceTree.createTree("Default Source Tree");
		// SourceTreeListener is an inner class
		sourceTree.addTreeModelListener(new TreeListener(sourceTree));

		// default target tree
		targetTree = new Tree(conn, sc);
		targetTree.createTree("Default Target Tree");
		// TargetTreeListener is an inner class
		targetTree.addTreeModelListener(new TreeListener(targetTree));
	}

	/**
	 * Constructor
	 * 
	 * @param sourceTree
	 *            TreeModel which serves as source tree and allows to drag node
	 *            elements
	 * @param targetTree
	 *            TreeModel which serves as target tree for dropped node
	 *            elements
	 */
	public TreeDragDrop(Tree sourceTree, Tree targetTree) {
		setSourceTree(sourceTree);
		setTargetTree(targetTree);
	}

	// Methods
	// -------
	private DefaultMutableTreeNode copyInto(DefaultMutableTreeNode dragNode,
			DefaultMutableTreeNode dropTargetNode) {
		DefaultMutableTreeNode result = null;
		if (!simpleCopy) {
			result = copyRecursive(dragNode, dropTargetNode);// recursive Copy
		} else {
			result = simpleCopy(dragNode, dropTargetNode);
		}
		if (result != null)
			this.setSelectedNodeObject((UserObject) result.getUserObject());
		return result;
	}

	private DefaultMutableTreeNode copyRecursive(
			DefaultMutableTreeNode dragNode,
			DefaultMutableTreeNode targetDropNode) {
		DefaultMutableTreeNode newNode = simpleCopy(dragNode, targetDropNode);
		for (int i = 0; i < dragNode.getChildCount(); i++) {
			if ((((DefaultMutableTreeNode) dragNode.getChildAt(i))
					.getUserObject()) instanceof UserObject) {
				copyRecursive(
						((DefaultMutableTreeNode) dragNode.getChildAt(i)),
						newNode);
			}
		}
		return newNode;
	}

	private DefaultMutableTreeNode simpleCopy(DefaultMutableTreeNode treeNode,
			DefaultMutableTreeNode targetParentNode) {
		DefaultMutableTreeNode newNode = null;
		if (treeNode.getUserObject() instanceof UserObject) {
			UserObject original = (UserObject) treeNode.getUserObject();
			// MutableTreeNode
			// die getUserObject ueberschreibt und speziellen Typ
			// UserObject zurueck gibt
			newNode = new DefaultMutableTreeNode();
			(original.copyInto(newNode)).addActionListener(this);
			targetParentNode.add(newNode);
		}
		return newNode;
	}

	// use to create Object according to selected Component -> Refactor -> Util
	// class
	static Object createObject(String className) {
		Logger staticLogger = Logger.getLogger(TreeDragDrop.class);
		Object object = null;
		try {
			Class<?> classDefinition = Class.forName(className);
			object = classDefinition.newInstance();
		} catch (InstantiationException e) {
			staticLogger.debug(e);
		} catch (IllegalAccessException e) {
			staticLogger.debug(e);
		} catch (ClassNotFoundException e) {
			staticLogger.debug(e);
		}
		return object;
	}

	// Listener
	// --------
	public void dropListener(DropEvent dropEvent) {

		DefaultMutableTreeNode dragNode = (DefaultMutableTreeNode) dropEvent
				.getTargetDragValue();
		DefaultMutableTreeNode dropTargetNode = (DefaultMutableTreeNode) dropEvent
				.getTargetDropValue();
		logger.debug("TreeDragDrop: dropListener: dragged: " + dragNode + " "
				+ DndEvent.getEventName(dropEvent.getEventType()) + " target: "
				+ dropTargetNode);
		if (dragNode != null && dropTargetNode != null) {
			DefaultMutableTreeNode dropped = copyInto(dragNode, dropTargetNode);
			if (dragNode.getRoot().equals(targetTree.getRoot())) {
				// if from target to target tree ... we assume we move a node,
				// not copy it.
				dragNode.removeFromParent();
			}
			setSelectedNodeObject((UserObject) dropped.getUserObject());
		}
	}

	public void changeDragDropCopyOption(ValueChangeEvent event) {
		this.simpleCopy = (Boolean) event.getNewValue();
	}

	public boolean isSimpleCopy() {
		return simpleCopy;
	}

	public void isSingleClickCopyToTarget(boolean singleClick) {
		this.singleClickCopyToTarget = singleClick;
	}

	public boolean isSingleClickCopyToTarget() {
		return singleClickCopyToTarget;
	}

	public void setSimpleCopy(boolean simpleCopy) {
		this.simpleCopy = simpleCopy;
	}

	public SelectItem[] getCopyItems() {
		return copyItems;
	}

	/** **/
	public void addNodeToSelectedNode(Connectable referencedConnectable) {
		// add SubNode
		ConnectableUserObject subCopy = new ConnectableUserObject();
		logger.debug("TreeDragDrop: name of Root: " + name);
		// logger.debug("name from UI: "+event.getComponent().getAttributes().get("name"));
		subCopy.setText(name);
		subCopy.setReference(referencedConnectable);
		// if(selectedComponent.equals("Droplet")){
		// Droplet d =
		// (Droplet)createObject("at.jku.ce.dropbox.domain.Droplet");
		// d.setMessage(name);
		// subCopy.setReference(d);
		// }
		this.getSelectedNodeObject().getWrapper().add(subCopy.getWrapper());
		addActionListener(getSelectedNodeObject().getWrapper());
		// close Popup
		this.name = "";
		this.popupVisible = false;
	}

	/**
	 * Deletes the selected tree node. The node object reference is set to null
	 * so that the delete and copy buttons will be disabled.
	 * 
	 * @param event
	 *            that fired this method
	 * @see #isDeleteDisabled(), isCopyDisabled()
	 */
	public void deleteSelectedNode(ActionEvent event) {// alter code!
		// don't delete the root node;
		if (getSelectedNodeObject() != null
				&& !getSelectedNodeObject().equals(
						((DefaultMutableTreeNode) sourceTree.getRoot())
								.getUserObject())
				&& !getSelectedNodeObject().equals(
						((DefaultMutableTreeNode) targetTree.getRoot())
								.getUserObject())) {
			removeActionListener(getSelectedNodeObject().getWrapper());
			getSelectedNodeObject().deleteNodeAndSubNodes();
			setSelectedNodeObject(null);
		}
	}

	/**
	 * called by the UserObjects when some action (like clicks) happen
	 * eventually also called by someone else! also notify eventlisteners
	 * registered to this TreeDragDrop.
	 * */
	@Override
	public void processAction(ActionEvent arg0) throws AbortProcessingException {
		// 
		if (arg0 instanceof UserObjectActionEvent) {
			// the selected node is now the source of the click...
			setSelectedNodeObject(((UserObjectActionEvent) arg0)
					.getUserObject());
			if (singleClickCopyToTarget) {
				if (getSelectedNodeObject().getWrapper().getRoot().equals(
						sourceTree.getRoot())) {
					// copy from source -> target
					// copy into root of target
					copyInto(getSelectedNodeObject().getWrapper(),
							(DefaultMutableTreeNode) targetTree.getRoot());
				}
			}
		} else {
			logger.debug("TreeDragDrop: processAction with unknown type!!"
					+ arg0.toString());
		}
		for (ActionListener al : actionlisteners) {
			al.processAction(arg0);
		}
	}

	public void addActionListener(ActionListener listen) {
		if (!actionlisteners.contains(listen))
			actionlisteners.add(listen);
		if (getSourceTree() != null)
			addActionListener((DefaultMutableTreeNode) getSourceTree()
					.getRoot());
		if (getTargetTree() != null)
			addActionListener((DefaultMutableTreeNode) getTargetTree()
					.getRoot());
	}

	public void removeActionListener(ActionListener listen) {
		actionlisteners.remove(listen);
	}

	public void showPopup(ActionEvent event) {
		this.popupVisible = true;
	}

	public void hidePopup(ActionEvent event) {
		this.popupVisible = false;
	}

	public void dragListener(DragEvent dragEvent) {
		logger.debug("TreeDragDrop:  dragListener "
				+ DndEvent.getEventName(dragEvent.getEventType()));
	}

	private void treeChanged(int i, Tree tree, TreeModelEvent evt) {
		logger.debug("TreeDragDrop " + evt.getSource() + " has changed "
				+ evt.toString());
		addActionListener((DefaultMutableTreeNode) tree.getRoot());
	}

	// Getter and Setter
	// -----------------
	/**
	 * Determines whether the delete button is disabled. The delete button
	 * should be disabled if the node that was previously selected was deleted
	 * or if no node is otherwise selected. The root node is a special case and
	 * cannot be deleted.
	 * 
	 * @return the disabled status of the delete button
	 */
	public boolean isDeleteDisabled() {
		// can't delete the root node
		return getSelectedNodeObject() == null
				|| getSelectedNodeObject().getText().equals(ROOT_NODE_TEXT);
	}

	public boolean isSelectedDisabled() {
		return getSelectedNodeObject() == null;
	}

	public boolean isPopupVisible() {
		return this.popupVisible;
	}

	/**
	 * Gets the tree's default sourceTree.
	 * 
	 * @return tree sourceTree.
	 */
	public Tree getSourceTree() {
		return sourceTree;
	}

	public Tree getTargetTree() {
		return targetTree;
	}

	public String getDragMessage() {
		return dragMessage;
	}

	public void setDragMessage(String dragMessage) {
		this.dragMessage = dragMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the option items for component types.
	 * 
	 * @return array of component type items
	 */
	public SelectItem[] getComponentItems() {
		return COMPONENT_ITEMS;
	}

	public ConnectableService getConnectableService() {
		return connectableService;
	}

	public void setConnectableService(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

	public ServiceManagementService getBaseService() {
		return baseService;
	}

	public void setBaseService(ServiceManagementService baseService) {
		logger.debug("setBaseService called!");
		this.baseService = baseService;
	}

	public void setSourceTree(Tree tree) {
		if (this.sourceTree != null) {
			sourceTree.removeTreeModelListeners();
			removeActionListener((DefaultMutableTreeNode) sourceTree.getRoot());
			((DefaultMutableTreeNode) sourceTree.getRoot()).removeAllChildren();
		}
		this.sourceTree = tree;
		if (sourceTree != null)// SourceTreeListener is an inner class
		{
			sourceTree.addTreeModelListener(new TreeListener(sourceTree));
			addActionListener((DefaultMutableTreeNode) sourceTree.getRoot());
		}
	}

	public void setTargetTree(Tree tree) {
		if (this.targetTree != null) {
			targetTree.removeTreeModelListeners();
			removeActionListener((DefaultMutableTreeNode) targetTree.getRoot());
			((DefaultMutableTreeNode) targetTree.getRoot()).removeAllChildren();
		}
		this.targetTree = tree;
		if (targetTree != null)// TargetTreeListener is an inner class
		{
			targetTree.addTreeModelListener(new TreeListener(targetTree));
			addActionListener((DefaultMutableTreeNode) targetTree.getRoot());
		}
	}

	private void addActionListener(DefaultMutableTreeNode x) {
		long now = System.currentTimeMillis();
		if (x != null) {
			Enumeration xx = x.breadthFirstEnumeration();
			while (xx.hasMoreElements()) {
				UserObject o = (UserObject) ((DefaultMutableTreeNode) xx
						.nextElement()).getUserObject();
				if (o != null)
					o.addActionListener(this);
			}
		}
		logger
				.debug("addactionlistener: "
						+ (System.currentTimeMillis() - now));
	}

	private void removeActionListener(DefaultMutableTreeNode x) {
		Enumeration xx = x.breadthFirstEnumeration();
		while (xx.hasMoreElements()) {
			UserObject o = (UserObject) ((DefaultMutableTreeNode) xx
					.nextElement()).getUserObject();
			if (o != null)
				o.removeActionListener(this);
		}
	}

	/**
	 * Sets the tree node.
	 * 
	 * @param selectedNodeObject
	 *            the new tree node
	 */
	public void setSelectedNodeObject(UserObject selectedNodeObject) {
		this.selectedNodeObject = selectedNodeObject;
	}

	/**
	 * Gets the tree node.
	 * 
	 * @return the tree node
	 */
	public UserObject getSelectedNodeObject() {
		if (selectedNodeObject == null) {
			if (targetTree != null) {
				if (((DefaultMutableTreeNode) targetTree.getRoot())
						.getChildCount() > 0)
					this.selectedNodeObject = (UserObject) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) targetTree
							.getRoot()).getFirstChild()).getUserObject();
				else
					this.selectedNodeObject = (UserObject) ((DefaultMutableTreeNode) targetTree
							.getRoot()).getUserObject();
			} else {
				if (((DefaultMutableTreeNode) sourceTree.getRoot())
						.getChildCount() > 0)
					this.selectedNodeObject = (UserObject) ((DefaultMutableTreeNode) ((DefaultMutableTreeNode) sourceTree
							.getRoot()).getFirstChild()).getUserObject();
				else
					this.selectedNodeObject = (UserObject) ((DefaultMutableTreeNode) sourceTree
							.getRoot()).getUserObject();
			}
		}
		return this.selectedNodeObject;
	}

	public String getSelectedText() {
		return getSelectedNodeObject().getText();
	}

	/** this inner class listener redirects any events to the treeChanged method */
	class TreeListener implements TreeModelListener {
		Tree myTree;

		TreeListener(Tree tree) {
			myTree = tree;
		}

		@Override
		public void treeNodesChanged(TreeModelEvent arg0) {
			treeChanged(NODES_CHANGED_EVENT, myTree, arg0);
		}

		@Override
		public void treeNodesInserted(TreeModelEvent arg0) {
			treeChanged(NODES_INSERTED_EVENT, myTree, arg0);
		}

		@Override
		public void treeNodesRemoved(TreeModelEvent arg0) {
			treeChanged(NODES_REMOVED_EVENT, myTree, arg0);
		}

		@Override
		public void treeStructureChanged(TreeModelEvent arg0) {
			treeChanged(NODES_STRUCTURECHANGED_EVENT, myTree, arg0);
		}
	}
}
