package biz.sudden.baseAndUtility.domain.connectable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * Statements are currently Associations and Occurrences. Statements can have a
 * Scope in which they are valid.
 * 
 * @author Matthias
 * 
 */
@MappedSuperclass
// @Entity
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Statement implements IDInterface {
	// private Set<Scope> scopes;
	private Scope scope;
	private Long id;

	/**
	 * set the Scope for the Statement
	 * 
	 * @param s
	 *            the Scope to be set for this Statement
	 */
	public void setScope(Scope s) {
		scope = s;
	}

	/**
	 * get the Scope of the Statement
	 * 
	 * @return the Scope of the Statement
	 */
	@ManyToOne
	public Scope getScope() {
		return scope;
	}

	@Override
	public boolean equals(Object s) {
		return s.getClass().equals(getClass())
				&& ((Statement) s).getId().equals(id);
	}

	// public void addScope(Scope scope){
	// if(scopes==null)
	// scopes=new HashSet<Scope>();
	// scopes.add(scope);
	// }
	//	
	// public void removeScope(Scope scope){
	// if(scopes==null)
	// return;
	// scopes.remove(scope);
	// }
	//	
	// @ManyToMany
	// public Set<Scope> getScopes() {
	// return this.scopes;
	// }
	//	
	//
	// public void setScopes(Set<Scope> scopes) {
	// this.scopes = scopes;
	// }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
