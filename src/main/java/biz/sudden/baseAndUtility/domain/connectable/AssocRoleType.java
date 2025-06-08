package biz.sudden.baseAndUtility.domain.connectable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * AssocRoleType - AssocRoleTypes represent the nature of AssociationRoles.
 * 
 * @author Matthias Neubauer
 * 
 */
@Entity
public class AssocRoleType implements Connectable {

	private String type;// ->id
	private Long id;

	/**
	 * default constructor
	 */
	public AssocRoleType() {
	}

	/**
	 * constructor
	 * 
	 * @param type
	 *            String which represents the AssocRoleType
	 */
	public AssocRoleType(String type) {
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * get type of AssocRoleType. type has to be unique
	 * 
	 * @return type
	 */
	@Column(unique = true)
	public String getType() {
		return type;
	}

	/**
	 * set type of AssocRoleType. type has to be unique
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
