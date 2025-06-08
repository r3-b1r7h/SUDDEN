package biz.sudden.baseAndUtility.domain.connectable;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.ManyToAny;

import biz.sudden.baseAndUtility.repository.ScopeRepository;

/**
 * Scope defines the valid context of Statements [Associations, Occurrences].
 * 
 * @author Matthias
 * 
 */
@javax.persistence.Entity
public class Scope {

	private Long id;
	private String name;// ->unique
	private Set<Connectable> context;

	/**
	 * default constructor
	 */
	public Scope() {
		// defaultvalues for defaultscopes (i.e. some not in the DB)
		id = -1L;
	}

	/**
	 * constructor
	 * 
	 * @param scopeName
	 *            name of this scope
	 * @param context
	 *            Set of Connectables which define the valid context of
	 *            statements
	 */
	public Scope(String scopeName, Set<Connectable> context) {
		this.name = scopeName;
		this.context = context;
		if (scopeName.equals(ScopeRepository.RETRIEVEALL_SCOPE_NAME))
			id = -1L;
	}

	/**
	 * remove a Connectable from the Scope, thus restricting the context in
	 * which Statements contained in this Scope are valid
	 * 
	 * @param c
	 *            the Topic to be removed
	 */
	public void removeFromContext(Connectable c) {
		context.remove(c);
	}

	// getter and setter

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * get the name of this scope
	 * 
	 * @return name
	 */
	@Column(unique = true)
	public String getName() {
		return name;
	}

	/**
	 * set the name of this scope. scope names have to be unique
	 * 
	 * @param name
	 *            name of this scope
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get context of this scope, which defines the validity of statements in
	 * this scope
	 * 
	 * @return context
	 */
	@ManyToAny(metaDef = "connectable", metaColumn = @Column(name = "context_type"))
	// @Cascade( { org.hibernate.annotations.CascadeType.ALL } )
	@JoinTable(name = "obj_properties", joinColumns = @JoinColumn(name = "obj_id"), inverseJoinColumns = @JoinColumn(name = "context_id"))
	public Set<Connectable> getContext() {// TODO: Mapping falsch -> Queries
											// gehen schief
		return context;
	}

	/**
	 * set context of this scope, which defines the validity of statements in
	 * this scope
	 * 
	 * @param context
	 */
	public void setContext(Set<Connectable> context) {
		this.context = context;
	}

	// TODO:equals methode ueberschreiben!
	@Override
	public boolean equals(Object o) {// TODO: compare if context is equal
		if (o == null || !(o instanceof Scope))
			return false;
		Scope i = (Scope) o;
		if (this.getId() != null && i.getId() != null
				&& this.getId().equals(i.getId()))
			return true;
		if (this.getId() == null && i.getId() == null && i.getName() != null
				&& this.getName() != null && this.getName().equals(i.getName()))
			return true;
		return false;
	}

}
