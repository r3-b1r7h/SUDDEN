package biz.sudden.knowledgeData.serviceManagement.service.impl;

import java.util.List;

import biz.sudden.baseAndUtility.service.ConnectableService;
import biz.sudden.knowledgeData.serviceManagement.domain.Machine;
import biz.sudden.knowledgeData.serviceManagement.repository.MachineRepository;
import biz.sudden.knowledgeData.serviceManagement.service.MachineryType_Service;

public class MachineryType_ServiceImpl implements MachineryType_Service {

	private MachineRepository machineRep;
	private ConnectableService connectableService;

	/**
	 * set MachineRepository
	 * 
	 * @param machineRep
	 */
	public void setMachineRep(MachineRepository machineRep) {
		this.machineRep = machineRep;
	}

	@Override
	public void createMachine(Machine m) {
		machineRep.create(m);
	}

	@Override
	public void deleteMachine(Machine m) {
		connectableService.deleteConnectableAssocs(m, connectableService
				.getRetrieveAllScope());
		machineRep.delete(m);
	}

	@Override
	public List<Machine> retrieveAllMachines() {
		return machineRep.retrieveAll();
	}

	@Override
	public Machine retrieveMachine(Long id) {
		return machineRep.retrieve(id);
	}

	@Override
	public Machine retrieveMachine(String name) {
		return machineRep.retrieveMachineBy(name);
	}

	@Override
	public void updateMachine(Machine m) {
		machineRep.update(m);
	}

	public void setConnectableService(ConnectableService connectableService) {
		this.connectableService = connectableService;
	}

}
