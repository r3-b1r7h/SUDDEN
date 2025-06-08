package biz.sudden.baseAndUtility.service;

import biz.sudden.baseAndUtility.repository.JsfLinkRepository;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;

public interface JsfLinkService {

	public abstract JsfLinkRepository getJsfLinkRepository();

	public abstract void setJsfLinkRepository(
			JsfLinkRepository jsfLinkRepository);

	public abstract void createJsfLink(JsfLink jsfLink);

	public abstract void retrieveJsfLink(Long id);

}