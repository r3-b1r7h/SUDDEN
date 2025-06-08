package biz.sudden.baseAndUtility.service;

import java.util.List;

import biz.sudden.baseAndUtility.domain.Link;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;

public interface LinkService {

	public void createLink(Link link);

	public List<JsfLink> getLinksFor(Object object, Long id);
}