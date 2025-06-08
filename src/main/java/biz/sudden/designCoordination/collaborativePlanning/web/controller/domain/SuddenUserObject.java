package biz.sudden.designCoordination.collaborativePlanning.web.controller.domain;

import javax.swing.tree.DefaultMutableTreeNode;

import com.icesoft.faces.component.tree.IceUserObject;

public class SuddenUserObject<T> extends IceUserObject {

	private T attachedObject;

	public T getAttachedObject() {
		return attachedObject;
	}

	public void setAttachedObject(T attachedObject) {
		this.attachedObject = attachedObject;
	}

	public SuddenUserObject(DefaultMutableTreeNode treeNode) {
		// TODO Auto-generated constructor stub
		super(new DefaultMutableTreeNode(treeNode));
	}

}
