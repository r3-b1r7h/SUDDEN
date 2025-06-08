package biz.sudden.baseAndUtility.web.controller.tree;

import java.util.LinkedList;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import com.icesoft.faces.component.tree.IceUserObject;

/**
 * abstract class for tree nodes - allows to determine the selected node in a
 * tree and to delete a node
 * 
 * @author MN
 * 
 */
public abstract class UserObject extends IceUserObject {

	private Logger logger = Logger.getLogger(this.getClass());

	// it might be that as a result of this event some listener registers itself
	// as a new actionlistener...>concurrent
	// modification
	// we need some threadsafe list here
	protected LinkedList<ActionListener> actionlisteners = new LinkedList<ActionListener>();
	protected SearchedAssoc searchCriteria;
	protected boolean retrieveChildren = false;

	public UserObject() {
		super(new DefaultMutableTreeNode());
		getWrapper().setUserObject(this);
	}

	public UserObject(DefaultMutableTreeNode treenode) {
		super(treenode);
		getWrapper().setUserObject(this);
	}

	@Override
	public void setWrapper(DefaultMutableTreeNode wrapper) {
		// remove old wrapper
		if (getWrapper() != null) {
			getWrapper().removeFromParent();
			getWrapper().removeAllChildren();
			getWrapper().setUserObject(null);
		}
		if (wrapper != null) {
			wrapper.setUserObject(this);
		}
		super.setWrapper(wrapper);
	}

	public void addActionListener(ActionListener listen) {
		if (!actionlisteners.contains(listen))
			actionlisteners.add(listen); // only one with this key.
	}

	public void removeActionListener(ActionListener listen) {
		if (actionlisteners.contains(listen))
			actionlisteners.remove(listen);
	}

	/**
	 * Deletes this node and the subnodes from the parent tree.
	 */
	public void deleteNodeAndSubNodes() {
		// remove children recursively
		while (getWrapper().getChildCount() > 0) {
			((UserObject) ((DefaultMutableTreeNode) getWrapper().getChildAt(0))
					.getUserObject()).deleteNodeAndSubNodes();
		}
		getWrapper().removeFromParent();
		getWrapper().removeAllChildren();
		getWrapper().setUserObject(null);
		setWrapper(null);
		actionlisteners.clear();
	}

	/**
	 * keeps this node and deletes the subnodes from the parent tree.
	 */
	public void deleteSubNodes() {
		// remove children recursively
		while (getWrapper().getChildCount() > 0) {
			((UserObject) ((DefaultMutableTreeNode) getWrapper().getChildAt(0))
					.getUserObject()).deleteNodeAndSubNodes();
		}
		getWrapper().removeAllChildren();
	}

	/**
	 * Adds a node to this
	 */
	public void addNode(UserObject child) {
		if (getWrapper() == null)
			setWrapper(new DefaultMutableTreeNode(this));
		if (child.getWrapper() == null)
			child.setWrapper(new DefaultMutableTreeNode(child));
		(getWrapper()).add(child.getWrapper());
		child.getWrapper().setParent(getWrapper());
	}

	/** create and return a new User Object and put this into the given wrapper */
	public abstract UserObject copyInto(DefaultMutableTreeNode wrapper);

	public void nodeClicked() {
		logger.debug("UserObject:nodeClicked !!!!!!");
		triggerEvent(null);
	}

	public void nodeClicked(ActionEvent evt) {
		logger.debug("UserObject:nodeClicked " + evt.getSource());
		this.triggerEvent(evt);
	}

	public void triggerEvent(ActionEvent evt) {
		UserObjectActionEvent ae = null;
		if (evt != null) {
			ae = new UserObjectActionEvent(evt.getComponent());
			ae.setPhaseId(evt.getPhaseId());
		}
		setExpanded(!super.isExpanded());
		for (int i = 0; i < actionlisteners.size(); ++i) {
			actionlisteners.get(i).processAction(ae);
		}
	}

	/**
	 * @return the searchCriteria
	 */
	public SearchedAssoc getSearchCriteria() {
		return searchCriteria;
	}

	/**
	 * @param searchCriteria
	 *            the searchCriteria to set
	 */
	public void setSearchCriteria(SearchedAssoc searchCriteria) {
		this.searchCriteria = searchCriteria;
		retrieveChildren = true;
	}

	public boolean retrieveChildren() {
		return retrieveChildren;
	}

	public class UserObjectActionEvent extends ActionEvent {
		public UserObjectActionEvent(UIComponent uic) {
			super(uic);
		}

		public UserObject getUserObject() {
			return UserObject.this;
		}
	}
}
