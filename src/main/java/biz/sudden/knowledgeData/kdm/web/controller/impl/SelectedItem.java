package biz.sudden.knowledgeData.kdm.web.controller.impl;

import javax.swing.tree.DefaultMutableTreeNode;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

import com.icesoft.faces.component.tree.IceUserObject;

public class SelectedItem extends IceUserObject {

	private boolean selected = true;
	private String entry = "...";
	private Connectable networkObject;
	private boolean rendered;

	public SelectedItem(DefaultMutableTreeNode wrapper,
			Connectable networkObject) {
		super(wrapper);
		this.networkObject = networkObject;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public Connectable getNetworkObject() {
		return networkObject;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
}
