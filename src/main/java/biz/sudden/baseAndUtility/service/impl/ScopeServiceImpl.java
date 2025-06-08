package biz.sudden.baseAndUtility.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.connectable.Association;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.baseAndUtility.domain.connectable.Occurrence;
import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.repository.ScopeRepository;
import biz.sudden.baseAndUtility.service.ScopeService;

/**
 * Associations and Occurrence of Topics are only visible within a certain
 * scope. This service manages the scopes.
 * 
 * Scopes are not used for security. Each occurrence and association has only
 * one scope (due to otherwise expected performance deterioration). Currently 4
 * types of Scopes are envisioned: - Organisation - BusinessOpportunity -
 * Universal (not Org. and not BO) - All (see all occurrences, and associations)
 * 
 * @author gweich
 * 
 */
public class ScopeServiceImpl implements ScopeService {

	private Logger logger = Logger.getLogger(this.getClass());

	protected ScopeRepository scopeRep;

	public ScopeServiceImpl() {
		logger.debug("ScopeServiceImpl -> cst");
	}

	@Override
	public Scope createOrRetrieveScope(String scopename) {
		logger.debug("ScopeServiceImpl createOrRetrieveScope " + scopename);
		Scope newScope = scopeRep.retrieveScopeBy(scopename);
		if (newScope == null) {
			newScope = new Scope(scopename, null);
			// FIXME: InvalidDataAccessApiUsageException
			// Caused by:
			// org.springframework.dao.InvalidDataAccessApiUsageException: Write
			// operations are not allowed
			// in read-only mode (FlushMode.NEVER/MANUAL): Turn your Session
			// into FlushMode.COMMIT/AUTO or remove
			// 'readOnly' marker from transaction definition.
			scopeRep.create(newScope);
		}
		return newScope;
	}

	@Override
	public Scope createOrRetrieveScope(Set<Connectable> context) {
		logger
				.debug("ScopeServiceImpl createOrRetrieveScope " + context != null ? context
						.size()
						: "null");
		Scope newScope = scopeRep.retrieveScopeBy(context);
		if (newScope == null) {
			String name;
			if (context != null && context.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (Connectable x : context) {
					sb.append(x.getClass().getSimpleName());
					sb.append(':');
					sb.append(x.getId());
					sb.append(';');
				}
				name = sb.toString();
			} else {
				name = context.toString();
			}

			newScope = new Scope(name, context);
			// FIXME: InvalidDataAccessApiUsageException
			// Caused by:
			// org.springframework.dao.InvalidDataAccessApiUsageException: Write
			// operations are not allowed
			// in read-only mode (FlushMode.NEVER/MANUAL): Turn your Session
			// into FlushMode.COMMIT/AUTO or remove
			// 'readOnly' marker from transaction definition.
			scopeRep.create(newScope);
		}
		return newScope;
	}

	@Override
	public Scope retrieveScopeBy(String name) {
		return scopeRep.retrieveScopeBy(name);
	}

	@Override
	public Scope retrieveScopeBy(Set<Connectable> context) {
		return scopeRep.retrieveScopeBy(context);
	}

	@Override
	public List<Association> retrieveAssociationsFrom(Scope scope) {
		return scopeRep.retrieveAssociationsFrom(scope);
	}

	@Override
	public List<Occurrence> retrieveOccurrencesFrom(Scope scope) {
		return scopeRep.retrieveOccurrencesFrom(scope);
	}

	@Override
	public void deleteScope(Scope deleteme) {
		scopeRep.delete(deleteme);
	}

	@Override
	public List<Scope> retrieveAllScopes() {
		return scopeRep.retrieveAllScopes();
	}

	@Override
	public Scope retrieveScope(Long id) {
		return scopeRep.retrieve(id);
	}

	@Override
	public Scope getRetrieveAllScope() {
		return scopeRep.getRetrieveAllScope();
	}

	@Override
	public Scope getUnspecifiedScope() {
		return scopeRep.getUnspecifiedScope();
	}

	@Override
	public Scope createScope(String scopeName, Set<Connectable> context) {
		Scope scope = new Scope(scopeName, context);
		scopeRep.create(scope);
		return scope;
	}

	@Override
	public void setRepository(ScopeRepository sr) {
		scopeRep = sr;
		// logger.debug("ScopeServiceImpl setRepository");
	}

	@Override
	public ScopeRepository getRepository() {
		return scopeRep;
	}

}
