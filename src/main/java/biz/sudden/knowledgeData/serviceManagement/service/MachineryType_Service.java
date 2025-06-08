package biz.sudden.knowledgeData.serviceManagement.service;

import java.util.List;

import biz.sudden.knowledgeData.serviceManagement.domain.Machine;

/**
 * 
 * MachineryType_Service - This interface defines CRUD Methods for Machines
 * 
 * @author Matthias Neubauer
 * 
 */
public interface MachineryType_Service {

	public void createMachine(Machine m);

	public Machine retrieveMachine(Long id);

	public Machine retrieveMachine(String name);

	public List<Machine> retrieveAllMachines();

	public void updateMachine(Machine m);

	public void deleteMachine(Machine m);

}
