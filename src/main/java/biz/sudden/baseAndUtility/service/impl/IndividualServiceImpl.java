package biz.sudden.baseAndUtility.service.impl;

import biz.sudden.baseAndUtility.domain.Individual;
import biz.sudden.baseAndUtility.repository.IndividualRepository;
import biz.sudden.baseAndUtility.service.IndividualService;

public class IndividualServiceImpl implements IndividualService {

	private IndividualRepository individualRepository;

	public IndividualServiceImpl() {
	}

	public IndividualRepository getIndividualRepository() {
		return individualRepository;
	}

	public void setIndividualRepository(
			IndividualRepository individualRepository) {
		this.individualRepository = individualRepository;
	}

	@Override
	public Long createIndividual(Individual individual) {
		// TODO Auto-generated method stub
		return individualRepository.create(individual);
	}

	@Override
	public Individual retrieveIndividual(String name) {
		return individualRepository.retrieve(name);

	}

}
