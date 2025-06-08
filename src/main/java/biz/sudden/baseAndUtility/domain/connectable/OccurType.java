package biz.sudden.baseAndUtility.domain.connectable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * OccurType defines the type of an occurrence. Occurrences of the same nature
 * reference the same OccurType
 * 
 * @author Matthias
 * 
 */
@Entity
public class OccurType implements Connectable {

	private String type;// ->unique
	private Long id;

	/**
	 * default constructor
	 */
	public OccurType() {
	}

	/**
	 * constructor
	 * 
	 * @param type
	 *            type of OccurType
	 */
	public OccurType(String type) {
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
	 * get type of OccurType.
	 * 
	 * @return type
	 */
	@Column(unique = true)
	public String getType() {
		return type;
	}

	/**
	 * set type of OccurType. type has to be unique
	 * 
	 * @param type
	 *            String which defines the type of an occurrence
	 */
	public void setType(String type) {
		this.type = type;
	}
}
