package biz.sudden.baseAndUtility.service;

import java.util.List;
import java.util.Set;

import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.ScopeRepository;

/**
 * Associations and Occurrence of Topics are only visible within a certain
 * scope. This service manages the scopes.
 * 
 * Scopes are not used for security. Each occurrence and association has only
 * one scope (due to otherwise expected performance deterioration). Currently 4
 * types of Scopes are envisioned: - Organisation - BusinessOpportunity -
 * Unspecified (not Org. and not BO) - RetrieveAll (see all occurrences, and
 * associations; might only be used when retrieving)
 * 
 * @author gweich
 * 
 */
public interface ScopeService {

	/** return all scopes a user is allowed to access from the db */
	public List<Scope> retrieveAllScopes();

	public Scope retrieveScope(Long id);

	/**
	 * get the scope that sees ALL stuff; this scope may only be used for
	 * retrieving the scope might be retrieved from the DB or created if null
	 * (i.e not exsiting).
	 * */
	public Scope getRetrieveAllScope();

	/**
	 * get the scope that sees the stuff that is not associated with a
	 * particular organisation or business opportunity the scope might be
	 * retrieved from the DB or created if null (i.e not exsiting).
	 * */
	public Scope getUnspecifiedScope();

	/**
	 * return a scope for a particular name (check if user is allowed to access)
	 */
	public Scope retrieveScopeBy(String name);

	public Scope retrieveScopeBy(Set<Connectable> context);

	/**
	 * wthe scope might be retrieved from the DB or created if null (i.e not
	 * exsiting).
	 * */
	public Scope createOrRetrieveScope(String name);

	/**
	 * wthe scope might be retrieved from the DB or created if null (i.e not
	 * exsiting).
	 * */
	public Scope createOrRetrieveScope(Set<Connectable> context);

	/** remove a scope TODO: and all statements associated with this scope??? */
	public void deleteScope(Scope deleteme);

	/**
	 * create and store in the rep a new scope; the list of connectables are the
	 * context
	 */
	public Scope createScope(String scopeName, Set<Connectable> context);

	public List<Association> retrieveAssociationsFrom(Scope scope);

	public List<Occurrence> retrieveOccurrencesFrom(Scope scope);

	/** the repository to store the scopes */
	public void setRepository(ScopeRepository sr);

	ScopeRepository getRepository();

}
