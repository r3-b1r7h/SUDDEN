package biz.sudden.baseAndUtility.web.controller.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import biz.sudden.baseAndUtility.domain.connectable.AssocType;
import biz.sudden.baseAndUtility.domain.connectable.Association;

public class AssocUserObject extends UserObject {

	protected Association reference;
	protected ConnectableUserObject parent;
	protected String parentRoleType;
	protected String childRoleType;
	protected boolean showRoleAndScope = true;

	public AssocUserObject(ConnectableUserObject parent, String parentRoleType,
			Association reference, String childRoleType) {
		super();
		this.reference = reference;
		this.parent = parent;
		this.parentRoleType = parentRoleType;
		this.childRoleType = childRoleType;
		setMyText();
	}

	public AssocType getAssocType() {
		return reference.getType();
	}

	public Association getReference() {
		return reference;
	}

	public String getAssocTypeName() {
		return reference.getType().getType();
	}

	public ConnectableUserObject getParent() {
		return parent;
	}

	public void setParent(ConnectableUserObject parent) {
		this.parent = parent;
		setMyText();
	}

	public String getParentRoleName() {
		return parentRoleType;
	}

	public String getChildRoleName() {
		return childRoleType;
	}

	public void setShowRoleAndScope(boolean show) {
		showRoleAndScope = show;
		setMyText();
	}

	public boolean getShowRoleAndScope() {
		return showRoleAndScope;
	}

	private void setMyText() {
		StringBuffer txt = new StringBuffer();
		if (getParentRoleName() != null && showRoleAndScope) {
			txt.append('(');
			txt.append(getParentRoleName());
			txt.append(')');
		}
		txt.append('-');
		if (getAssocTypeName() != null)
			txt.append(getAssocTypeName());
		if (reference.getScope() != null && showRoleAndScope) {
			txt.append(" [");
			txt.append(reference.getScope().getName());
			txt.append(']');
		}
		txt.append('-');
		if (getChildRoleName() != null && showRoleAndScope) {
			txt.append('(');
			txt.append(getChildRoleName());
			txt.append(')');
		}
		setText(txt.toString());
	}

	@Override
	public AssocUserObject copyInto(DefaultMutableTreeNode wrapper) {
		AssocUserObject copy = new AssocUserObject(parent, parentRoleType,
				reference, childRoleType);
		copy.setText(this.getText());
		copy.setWrapper(wrapper);
		wrapper.setUserObject(copy);
		return copy;
	}
}
