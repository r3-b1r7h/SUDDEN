package biz.sudden.knowledgeData.serviceManagement.repository;

import java.util.List;

import biz.sudden.baseAndUtility.repository.generic.GenericRepository;
import biz.sudden.knowledgeData.serviceManagement.domain.Machine;

public interface MachineRepository extends GenericRepository<Machine, Long> {

	public Machine retrieveMachineBy(String name);

	public List<Machine> retrieveAll();
}
