package biz.sudden.baseAndUtility.domain.connectable;

import javax.persistence.ManyToOne;

/**
 * Association - Associations represent relationships between Connectables.
 * 
 * Associations have a type, which is represented by AssocType - every
 * Association of the same nature references the same AssocType.
 * 
 * Connectables are associated within an Association using AssociationRoles,
 * which describe the role a Connectable takes in this Association.
 * 
 * @author Matthias Neubauer
 * 
 */
@javax.persistence.Entity
public class Association extends Statement {

	private AssocType type;

	/**
	 * get the association type
	 * 
	 * @return AssocType of this association
	 */
	@ManyToOne
	public AssocType getType() {
		return type;
	}

	/**
	 * set the association type of this association
	 * 
	 * @param type
	 *            AssocType
	 */
	public void setType(AssocType type) {
		this.type = type;
	}

}
