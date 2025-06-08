package biz.sudden.designCoordination.collaborativePlanning.service;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.baseAndUtility.service.ITargetService;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;

public class CollaborativePlanningTargetService implements ITargetService {

	List<JsfLink> targetTypes = new ArrayList<JsfLink>();

	public List<JsfLink> getTargetTypes() {
		return targetTypes;
	}

	public void setTargetTypes(List<JsfLink> targetTypes) {
		this.targetTypes = targetTypes;
	}

}
