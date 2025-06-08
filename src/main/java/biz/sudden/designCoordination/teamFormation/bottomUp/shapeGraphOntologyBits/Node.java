package biz.sudden.designCoordination.teamFormation.bottomUp.shapeGraphOntologyBits;

import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;

/**
 * Protege name: Node
 * 
 * @author ontology bean generator
 * @version 2006/11/29, 17:09:01
 */
public class Node extends Thing {

	/**
	 * Protege name: TaskList
	 */
	private List taskList = new ArrayList();

	public void addTaskList(AbstractTask elem) {
		List oldList = this.taskList;
		taskList.add(elem);
	}

	public boolean removeTaskList(AbstractTask elem) {
		List oldList = this.taskList;
		boolean result = taskList.remove(elem);
		return result;
	}

	public void clearAllTaskList() {
		List oldList = this.taskList;
		taskList.clear();
	}

	public Iterator getAllTaskList() {
		return taskList.iterator();
	}

	public List getTaskList() {
		return taskList;
	}

	public void setTaskList(List l) {
		taskList = l;
	}

	/**
	 * Protege name: MemberList
	 */
	private List memberList = new ArrayList();

	public void addMemberList(TeamMember elem) {
		List oldList = this.memberList;
		memberList.add(elem);
	}

	public boolean removeMemberList(TeamMember elem) {
		List oldList = this.memberList;
		boolean result = memberList.remove(elem);
		return result;
	}

	public void clearAllMemberList() {
		List oldList = this.memberList;
		memberList.clear();
	}

	public Iterator getAllMemberList() {
		return memberList.iterator();
	}

	public List getMemberList() {
		return memberList;
	}

	public void setMemberList(List l) {
		memberList = l;
	}

	/**
	 * Protege name: NodeId
	 */
	private int nodeId;

	public void setNodeId(int value) {
		this.nodeId = value;
	}

	public int getNodeId() {
		return this.nodeId;
	}

}
