package biz.sudden.baseAndUtility.web.controller;

import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.service.ScopeService;

public interface ScopeController {

	public void setScopeService(ScopeService connectableService);

	public ScopeService getScopeService();

	public Scope getUserScope();

	/** sets new scope and return old scope */
	public Scope setUserScope(Scope scope);

	/** sets new scope and return old scope */
	public Scope createScope(String scopeName);

	public Scope createScope();

	public void selectScope();

	public void deleteScope(Scope deleteme);

	public List<Scope> getAllScopes();

	public SelectItem[] getListOfScopes();

	public Scope getUnspecifiedScope();

	public Scope getRetrieveAllScope();

	public void setNewScopeName(String newScopeName);

	public String getNewScopeName();

	public void selectedUserScopeChanged(ValueChangeEvent event);

	/** sets new scope and return old scope */
	public Scope setUserScope(String scopename);

	public Long getUserScopeID();

	/** sets new scope and return id old old scope */
	public Long setUserScopeID(Long id);

	public Scope retrieveScope(String name);

}