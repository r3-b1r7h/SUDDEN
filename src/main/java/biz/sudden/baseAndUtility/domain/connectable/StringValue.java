package biz.sudden.baseAndUtility.domain.connectable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * StringValue represents a concrete value type for Occurrences
 * 
 * @author Matthias
 * 
 */
@javax.persistence.Entity
public class StringValue implements Value<String> {

	private String value;// -> !unique
	private Long id;

	/**
	 * default constructor
	 */
	public StringValue() {
	}

	/**
	 * constructor
	 * 
	 * @param value
	 *            String value
	 */
	public StringValue(String value) {
		this.value = value;
	}

	/**
	 * get String value
	 * 
	 * @return String value
	 */
	@Override
	@Column(unique = false)
	public String getValue() {
		return value;
	}

	/**
	 * set String value. String value have to be unique
	 * 
	 * @param value
	 *            String value
	 */
	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
