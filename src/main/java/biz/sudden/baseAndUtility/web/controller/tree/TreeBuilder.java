package biz.sudden.baseAndUtility.web.controller.tree;

import biz.sudden.baseAndUtility.service.ConnectableService;

class TreeBuilder {// ConnectableTreeController

	private ConnectableService connectableService;

	/**
	 * Constructor
	 * 
	 * @param connectableService
	 */
	public TreeBuilder(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

}
