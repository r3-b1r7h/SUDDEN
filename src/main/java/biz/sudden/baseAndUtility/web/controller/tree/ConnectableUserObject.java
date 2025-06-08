package biz.sudden.baseAndUtility.web.controller.tree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;

public class ConnectableUserObject extends UserObject {

	private Connectable reference;
	private List<Occurrence> occurrences;
	private String role1;

	private boolean showOccurences = true;
	private boolean showRole = true;

	public ConnectableUserObject() {
		super();
	}

	public ConnectableUserObject(DefaultMutableTreeNode treeNode) {
		super(treeNode);
		treeNode.setUserObject(this);
	}

	public ConnectableUserObject(Connectable reference) {
		super();
		this.setReference(reference);
	}

	public ConnectableUserObject(Connectable reference,
			List<Occurrence> occurences) {
		super();
		this.setReference(reference);
		this.setOccurrences(occurences);
	}

	public ConnectableUserObject(Connectable reference,
			DefaultMutableTreeNode treeNode) {
		super(treeNode);
		this.setReference(reference);
		treeNode.setUserObject(this);
	}

	public Connectable getReference() {
		return reference;
	}

	public void setReference(Connectable reference) {
		this.reference = reference;
		setMyText();
	}

	public List<Occurrence> getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(List<Occurrence> occurrences) {
		this.occurrences = occurrences;
		setMyText();
	}

	public void setCurrentRoleType(String rolename) {
		this.role1 = rolename;
		setMyText();
	}

	public String getCurrentRoleType() {
		return role1;
	}

	/**
	 * @return the showOccurences
	 */
	public boolean isShowOccurences() {
		return showOccurences;
	}

	/**
	 * @param showOccurences
	 *            the showOccurences to set
	 */
	public void setShowOccurences(boolean showOccurences) {
		boolean old = this.showOccurences;
		this.showOccurences = showOccurences;
		if (old != showOccurences)
			setMyText();
	}

	/**
	 * @return the showRole
	 */
	public boolean isShowRole() {
		return showRole;
	}

	/**
	 * @param showRole
	 *            the showRole to set
	 */
	public void setShowRole(boolean showRole) {
		boolean old = this.showRole;
		this.showRole = showRole;
		if (old != this.showRole)
			setMyText();
	}

	@Override
	public String getText() {
		String result = super.getText();
		if (result == null) {
			setMyText();
			result = super.getText();
			if (result == null)
				result = "";
		}
		return result;
	}

	private void setMyText() {
		StringBuffer txt = new StringBuffer();
		if (role1 != null && showRole) {
			txt.append('(');
			txt.append(role1);
			txt.append(") ");
		}
		if (reference != null)
			txt.append(getNameValueFrom(reference));
		if (occurrences != null && showOccurences) {
			txt.append(": ");
			for (Occurrence o : occurrences) {
				txt.append(o.getValue().getValue());
				txt.append(", ");
			}
			if (txt.lastIndexOf(",") > 0)
				txt.replace(txt.lastIndexOf(","), txt.length(), "");
		}

		if (txt.length() == 0)
			setText("___");
		else
			setText(txt.toString());
	}

	// get name attribute from connectable via java reflection
	@SuppressWarnings("empty-statement")
	private String getNameValueFrom(Connectable c) {// -->Util
		String nameValue = c.toString();
		try {
			Method getNameMethod = c.getClass().getMethod("getName",
					(Class[]) null);
			nameValue = (String) getNameMethod.invoke(c, (Object[]) null);
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		} catch (ClassCastException e) {
		} catch (NoSuchMethodException e) {
			try {
				// try getText
				Method getTextMethod = c.getClass().getMethod("getText",
						(Class[]) null);
				nameValue = (String) getTextMethod.invoke(c, (Object[]) null);
			} catch (IllegalAccessException ex) {
			} catch (IllegalArgumentException ex) {
			} catch (InvocationTargetException ex) {
			} catch (NoSuchMethodException ex) {
			} catch (ClassCastException ex) {
			} catch (SecurityException ex) {
			}
		}
		return nameValue;
	}

	@Override
	public ConnectableUserObject copyInto(DefaultMutableTreeNode wrapper) {
		ConnectableUserObject copy = new ConnectableUserObject(wrapper);
		copy.setText(this.getText());
		copy.setReference(this.getReference());
		wrapper.setUserObject(copy);
		return copy;
	}

}
