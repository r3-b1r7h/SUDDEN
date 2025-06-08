package biz.sudden.baseAndUtility.repository;

import java.util.List;
import java.util.Set;

import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.generic.GenericRepository;

public interface ScopeRepository extends GenericRepository<Scope, Long> {

	public static String UNSPECIFIED_SCOPE_NAME = "UnspecifiedScope";
	public static String RETRIEVEALL_SCOPE_NAME = "RetrieveAllScope";

	public static Scope RETRIEVEALL_SCOPE = new Scope(RETRIEVEALL_SCOPE_NAME,
			null);

	public Scope retrieveScopeBy(String name);

	public Scope getUnspecifiedScope();

	public Scope getRetrieveAllScope();

	/** NOT implemented by now!! */
	public Scope retrieveScopeBy(Set<Connectable> context);

	public List<Scope> retrieveAllScopes();

	public List<Association> retrieveAssociationsFrom(Scope scope);

	public List<Occurrence> retrieveOccurrencesFrom(Scope scope);

}
