package biz.sudden.baseAndUtility.service;

import java.util.ArrayList;
import java.util.List;

import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;

public class RootLinkService {

	List<ITargetService> targetServices = new ArrayList<ITargetService>();

	public List<ITargetService> getTargetServices() {
		return targetServices;
	}

	public void setTargetServices(List<ITargetService> targetServices) {
		this.targetServices = targetServices;
	}

	public List<JsfLink> getAllLinkableTypes() {

		List<JsfLink> linkableTypes = new ArrayList<JsfLink>();

		for (ITargetService targetService : targetServices) {
			for (JsfLink jsfLink : targetService.getTargetTypes()) {
				linkableTypes.add(jsfLink);
			}

		}

		return linkableTypes;

	}

}
