package biz.sudden.baseAndUtility.service.impl;

import biz.sudden.baseAndUtility.repository.JsfLinkRepository;
import biz.sudden.baseAndUtility.service.JsfLinkService;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;

public class JsfLinkServiceImpl implements JsfLinkService {

	private JsfLinkRepository jsfLinkRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.service.impl.JsfLinkService#getJsfLinkRepository
	 * ()
	 */
	public JsfLinkRepository getJsfLinkRepository() {
		return jsfLinkRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.service.impl.JsfLinkService#setJsfLinkRepository
	 * (biz.sudden.baseAndUtility.repository.JsfLinkRepository)
	 */
	public void setJsfLinkRepository(JsfLinkRepository jsfLinkRepository) {
		this.jsfLinkRepository = jsfLinkRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.service.impl.JsfLinkService#createJsfLink(biz
	 * .sudden.baseAndUtility.web.controller.domain.JsfLink)
	 */
	public void createJsfLink(JsfLink jsfLink) {
		jsfLinkRepository.create(jsfLink);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.service.impl.JsfLinkService#retrieveJsfLink
	 * (java.lang.Long)
	 */
	public void retrieveJsfLink(Long id) {
		jsfLinkRepository.retrieve(id);
	}

}
