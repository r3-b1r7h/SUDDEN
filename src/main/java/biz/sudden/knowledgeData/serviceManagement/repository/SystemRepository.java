package biz.sudden.knowledgeData.serviceManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.serviceManagement.domain.System;

public interface SystemRepository extends GenericRepository<System, Long> {

	public List<System> retrieveSystemBy(String name);

	public List<System> retrieveAll();
}
