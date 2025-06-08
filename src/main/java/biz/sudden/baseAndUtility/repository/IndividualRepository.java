package biz.sudden.baseAndUtility.repository;

import biz.sudden.baseAndUtility.domain.Individual;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface IndividualRepository extends
		GenericRepository<Individual, Long> {

	Individual retrieve(String name);

}