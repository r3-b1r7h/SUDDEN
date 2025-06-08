package biz.sudden.designCoordination.coordination.web.controller.impl;

import java.util.Hashtable;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency.TransportationLogistics;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode.ProductionMethod;

import com.icesoft.faces.component.tree.IceUserObject;

/**
 * This class is used by the TreeNodes in the tree of the coordination page. It
 * uses a table indexed by strings to store objects useful to represent the
 * state of the node in the tree.
 * 
 * @author mcaiecm2
 * 
 */
public class CoordinationTreeNodeUserObject extends IceUserObject {

	private Map<String, Object> properties;
	private CoordinationController owner;
	private Connectable networkObject;

	public CoordinationTreeNodeUserObject(DefaultMutableTreeNode wrapper,
			CoordinationController owner, Connectable networkObject) {
		super(wrapper);
		this.properties = new Hashtable<String, Object>();
		this.owner = owner;
		this.networkObject = networkObject;
	}

	public void setProperty(String propName, Object value) {
		if (propName == null || value == null) {
			return;
		}
		properties.put(propName, value);
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public Connectable getNetworkObject() {
		return networkObject;
	}

	public void nodeClicked(ValueChangeEvent event) {
		if (networkObject == null) {
			return;
		}
		if (networkObject instanceof ASNRoleNode) {
			owner.productionMethodChanged((ASNRoleNode) networkObject,
					(ProductionMethod) event.getNewValue());
		}

		if (networkObject instanceof ASNMaterialDependency) {
			owner.transportationLogisticsChanged(
					(ASNMaterialDependency) networkObject,
					(TransportationLogistics) event.getNewValue());
		}
	}

	public void productSelected(ActionEvent event) {
		if (networkObject == null) {
			return;
		}
		if (networkObject instanceof ASNRoleNode) {
			owner.selectNodeInTable((ASNRoleNode) networkObject);
		}
	}
}
