package biz.sudden.knowledgeData.serviceManagement.repository.hibernate;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.serviceManagement.domain.Thing;

public class ThingRepository extends GenericRepositoryImpl<Thing, Long> {

	public ThingRepository() {
		super(Thing.class);
	}

	public ThingRepository(Class type) {
		super(type);
	}

}
