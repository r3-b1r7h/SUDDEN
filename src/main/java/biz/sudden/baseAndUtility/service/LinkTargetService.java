package biz.sudden.baseAndUtility.service;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;

public class LinkTargetService implements ITargetService {

	List<JsfLink> targetTypes = new ArrayList<JsfLink>();

	public List<JsfLink> getTargetTypes() {
		return targetTypes;
	}

	public void setTargetTypes(List<JsfLink> targetTypes) {
		this.targetTypes = targetTypes;
	}

}