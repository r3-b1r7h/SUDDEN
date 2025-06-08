package biz.sudden.baseAndUtility.web.controller.tree;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.baseAndUtility.web.controller.ScopeController;

public class TreeShowNoRole extends Tree {
	public TreeShowNoRole(ConnectableService connectableService,
			ScopeController scope) {
		super(connectableService, scope);
		super.expandAll = true;
		super.alsoCreateAssociationNodes = true;
	}

	public TreeShowNoRole(ConnectableService connectableService,
			ScopeController scope, String rootnodename) {
		super(connectableService, scope, rootnodename);
		super.expandAll = true;
		super.alsoCreateAssociationNodes = true;
	}

	public TreeShowNoRole(ConnectableService connectableService,
			ScopeController scope, Connectable rootnode) {
		super(connectableService, scope, rootnode);
		super.expandAll = true;
		super.alsoCreateAssociationNodes = true;
	}

	@Override
	public String getNodeText(ConnectableUserObject connectable) {
		connectable.setShowRole(false);
		return connectable.toString();
	}

	@Override
	public String getAssociationText(AssocUserObject assocObject) {
		assocObject.setShowRoleAndScope(false);
		return assocObject.getText();
	}
}
