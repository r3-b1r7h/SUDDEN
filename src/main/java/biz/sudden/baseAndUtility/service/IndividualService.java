package biz.sudden.baseAndUtility.service;

import biz.sudden.baseAndUtility.domain.Individual;

public interface IndividualService {

	public Long createIndividual(Individual individual);

	public Individual retrieveIndividual(String name);

}
