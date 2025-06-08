/**
 * 
 */
package biz.sudden.baseAndUtility.web.controller.tree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author gweich
 * 
 */
public class StringUserObject extends UserObject {

	public StringUserObject() {
		super();
	}

	public StringUserObject(DefaultMutableTreeNode treeNode) {
		super(treeNode);
		treeNode.setUserObject(this);
	}

	public StringUserObject(String reference) {
		super();
		this.setReference(reference);
	}

	public StringUserObject(String reference, DefaultMutableTreeNode treeNode) {
		super(treeNode);
		this.setReference(reference);
		treeNode.setUserObject(this);
	}

	/**
	 * calls the getText() method, this method is here for having a similar API
	 * as the ConnectableUserObject
	 */
	public String getReference() {
		return super.getText();
	}

	/**
	 * calls the setText() method, this method is here for having a similar API
	 * as the ConnectableUserObject
	 */
	public void setReference(String reference) {
		super.setText(reference);
	}

	@Override
	public StringUserObject copyInto(DefaultMutableTreeNode wrapper) {
		StringUserObject copy = new StringUserObject(wrapper);
		copy.setText(this.getText());
		wrapper.setUserObject(copy);
		return copy;
	}

}
