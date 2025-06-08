package biz.sudden.designCoordination.collaborativePlanning.repository.hibernate;

import java.util.List;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.designCoordination.collaborativePlanning.domain.CommunicationContainer;
import biz.sudden.designCoordination.collaborativePlanning.repository.CommunicationContainerRepository;

class CommunicationContainerRepositoryImpl extends
		GenericRepositoryImpl<CommunicationContainer, Long> implements
		CommunicationContainerRepository {

	public CommunicationContainerRepositoryImpl() {
		// TODO Auto-generated constructor stub
		super(CommunicationContainer.class);
	}

	@Override
	public List<CommunicationContainer> getAllCommunicationContainer() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().loadAll(CommunicationContainer.class);
	}

}
