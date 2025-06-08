package biz.sudden.baseAndUtility.web.controller.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.connectable.Scope;
import biz.sudden.baseAndUtility.service.ScopeService;
import biz.sudden.baseAndUtility.web.controller.ScopeController;

public class ScopeControllerImpl implements ScopeController {

	Logger logger = Logger.getLogger(this.getClass());

	private ScopeService scopeService;

	private String newScopeName;
	private SelectItem[] scopeList;
	private long lastrefresh = 0;
	private static long REFRESHINTERVAL = 5000L;

	private Scope userScope;

	public ScopeControllerImpl() {
		super();
	};

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void setScopeService(ScopeService service) {
		this.scopeService = service;
	}

	@Override
	public ScopeService getScopeService() {
		return scopeService;
	}

	@Override
	public SelectItem[] getListOfScopes() {
		if (scopeList == null || scopeList.length == 0
				|| (System.currentTimeMillis() - lastrefresh) > REFRESHINTERVAL) {
			List<SelectItem> items = new ArrayList<SelectItem>();
			// first add the user scope so its the one selected
			items.add(new SelectItem(getUserScope().getId(), getUserScope()
					.getName()));
			for (Scope s : scopeService.retrieveAllScopes()) {
				// SelectItem (value, label)
				// ... passing an object for value does not work with the value
				// change listener..
				// is this a bug or feature of ice-faces?
				if (!s.equals(getUserScope()))
					items.add(new SelectItem(s.getId(), s.getName()));
			}
			scopeList = items.toArray(new SelectItem[items.size()]);
			logger.debug("ScopeController: " + scopeList.length);
			lastrefresh = System.currentTimeMillis();
		}
		return scopeList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.ScopeJSFController#getUserScope
	 * ()
	 */
	@Override
	public Scope getUserScope() {
		if (userScope == null)
			userScope = scopeService.getUnspecifiedScope();
		// logger.debug("getUserScope: "+ userScope.getName());
		return userScope;
	}

	@Override
	public Long getUserScopeID() {
		return getUserScope().getId();
	}

	/**
	 * sets new scope and returns old
	 * 
	 * @see biz.sudden.baseAndUtility.web.controller.impl.ScopeJSFController#setUserScope(biz.sudden.baseAndUtility.domain
	 *      .connectable.Scope)
	 */
	@Override
	public Scope setUserScope(Scope scope) {
		Scope old = userScope;
		if (scope != null) {
			userScope = scope;
			logger.debug("ScopeController setUserScope: " + scope.getName());
			// redo the selecitemlist
			lastrefresh = 0;
		}
		return old;
	}

	@Override
	public Scope setUserScope(String scopename) {
		Scope scope = setUserScope(scopeService.retrieveScopeBy(scopename));
		logger.debug("ScopeController setUserScope: (String)" + scopename);
		return scope;
	}

	@Override
	public Long setUserScopeID(Long id) {
		logger.debug("ScopeController setUserScope: (Long)" + id);
		Scope old = setUserScope(scopeService.retrieveScope(id));
		return old.getId();
	}

	@Override
	public void selectedUserScopeChanged(ValueChangeEvent event) {
		logger.debug("ScopeController setUserScope: (ValueChanged)");
		if (event.getNewValue() instanceof Long) {
			setUserScopeID((Long) event.getNewValue());
		} else if (event.getNewValue() instanceof String) {
			try {
				setUserScopeID(Long.parseLong(event.getNewValue().toString()));
			} catch (Exception e) {
				logger.debug("Could not get Long ID from newValue..: "
						+ event.getNewValue().toString());
				setUserScope(event.getNewValue().toString());
			}
		} else if (event.getNewValue() instanceof Scope) {
			setUserScope((Scope) event.getNewValue());
		} else {
			setUserScope(event.getNewValue().toString());
		}
	}

	/*
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.ScopeJSFController#selectScope
	 * ()
	 */
	public void selectScope() {
		logger.debug("ScopeController selectScope");
		FacesContext context = FacesContext.getCurrentInstance();
		Long scope = (Long) context.getExternalContext().getRequestMap().get(
				"scope");
		setUserScopeID(scope);
	}

	/*
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.ScopeJSFController#createScope
	 * (java.lang.String)
	 */
	@Override
	public Scope createScope(String scopeName) {
		return scopeService.createOrRetrieveScope(scopeName);
	}

	/*
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.ScopeJSFController#createScope
	 * ()
	 */
	@Override
	public Scope createScope() {
		return createScope(this.newScopeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.baseAndUtility.web.controller.impl.ScopeJSFController#
	 * setNewScopeName(java.lang.String)
	 */
	public void setNewScopeName(String newScopeName) {
		this.newScopeName = newScopeName;
	}

	/*
	 * 
	 * @seebiz.sudden.baseAndUtility.web.controller.impl.ScopeJSFController#
	 * getNewScopeName()
	 */
	public String getNewScopeName() {
		return newScopeName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.ScopeJSFController#deleteScope
	 * (biz.sudden.baseAndUtility.domain. connectable.Scope)
	 */
	public void deleteScope(Scope deleteme) {
		scopeService.deleteScope(deleteme);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.sudden.baseAndUtility.web.controller.impl.ScopeJSFController#getAllScopes
	 * ()
	 */
	public List<Scope> getAllScopes() {
		return scopeService.retrieveAllScopes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seebiz.sudden.baseAndUtility.web.controller.impl.ScopeJSFController#
	 * retrieveUniversalScope()
	 */
	@Override
	public Scope getUnspecifiedScope() {
		return scopeService.getUnspecifiedScope();
	}

	@Override
	public Scope getRetrieveAllScope() {
		return scopeService.getRetrieveAllScope();
	}

	@Override
	public Scope retrieveScope(String name) {
		return scopeService.retrieveScopeBy(name);
	}

}
