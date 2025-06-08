package biz.sudden.baseAndUtility.domain.connectable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * AssocType - AssocTypes represent the nature of Associations.
 * 
 * @author Matthias Neubauer
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AssocType implements Connectable {

	private String type;
	private Long id;

	/**
	 * default constructor
	 */
	public AssocType() {
	}

	/**
	 * constructor
	 * 
	 * @param type
	 *            type of association
	 */
	public AssocType(String type) {
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
	 * return type of AssocType. type has to be unique
	 * 
	 * @return type
	 */
	@Column(unique = true)
	public String getType() {
		return type;
	}

	/**
	 * set type of AssocType. type has to be unique
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
