package biz.sudden.baseAndUtility.repository.hibernate;

import biz.sudden.baseAndUtility.repository.JsfLinkRepository;
import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;

public class JsfLinkRepositoryImpl extends GenericRepositoryImpl<JsfLink, Long>
		implements JsfLinkRepository {

	public JsfLinkRepositoryImpl() {
		super(JsfLink.class);
	}

}
