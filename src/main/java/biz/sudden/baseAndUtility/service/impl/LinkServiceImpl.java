package biz.sudden.baseAndUtility.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.Link;
import biz.sudden.baseAndUtility.repository.LinkRepository;
import biz.sudden.baseAndUtility.service.LinkService;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;

public class LinkServiceImpl implements LinkService {

	private LinkRepository linkRepository;
	private Logger logger = Logger.getLogger(this.getClass());

	public LinkRepository getLinkRepository() {
		return linkRepository;
	}

	public void setLinkRepository(LinkRepository linkRepository) {
		this.linkRepository = linkRepository;
	}

	@Override
	public void createLink(Link link) {
		// TODO Auto-generated method stub
		try {
			linkRepository.create(link);
		} catch (Exception ex) {
			logger.debug("Creating this Link does not work");
		}
	}

	@Override
	public List<JsfLink> getLinksFor(Object object, Long id) {
		// TODO Auto-generated method stub
		List<JsfLink> jsfLinkList = new LinkedList<JsfLink>();
		List<Link> linkList = linkRepository.getLinkFor(object, id);
		for (Link link : linkList) {
			jsfLinkList.add(link.getToJsfLink());
		}
		return jsfLinkList;
	}

}
