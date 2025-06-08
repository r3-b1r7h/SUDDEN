package biz.sudden.baseAndUtility.repository;

import java.util.List;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.OccurType;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface OccurrenceRepository extends
		GenericRepository<Occurrence, Long> {

	// public List<Occurrence> retrieveOccurrences(Connectable c);
	public List<Occurrence> retrieveOccurrences(Connectable c, Scope scope);

	// public List<Occurrence> retrieveOccurrences(Connectable c, String
	// occType);
	// public List<Occurrence> retrieveOccurrences(Connectable c, OccurType
	// occType);
	public List<Occurrence> retrieveOccurrences(Connectable c, String occType,
			Scope s);

	public List<Occurrence> retrieveOccurrences(Connectable c,
			OccurType occType, Scope s);

	List<Occurrence> retrieveOccurrencesBy(OccurType type);

	List<Occurrence> retrieveOccurrencesBy(OccurType type, Scope scope);

}
