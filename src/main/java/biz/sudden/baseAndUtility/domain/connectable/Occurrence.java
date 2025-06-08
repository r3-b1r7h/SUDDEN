package biz.sudden.baseAndUtility.domain.connectable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Any;

/**
 * Occurrences are a Connectable's links to the 'outer world'. An occurrence is
 * a representation of a relationship between a Connectable and an information
 * resource. Occurrences always have an OccurType - every Occurrence of the same
 * nature references the same OccurType. Every Occurrence is attached to exactly
 * one Connectable.
 * 
 * @author Matthias
 * 
 */
@Entity
public class Occurrence extends Statement /* implements IDInterface */{

	private OccurType type;
	@SuppressWarnings("unchecked")
	private Value value;

	private Connectable parent;

	private Date dateCreated;

	/**
	 * default constructor
	 */
	public Occurrence() {

	}

	/**
	 * constructor
	 * 
	 * @param parent
	 *            Connectable to which this Occurrence belongs
	 * @param occurType
	 *            type of Occurrence
	 * @param value
	 *            value of occurrence
	 */
	@SuppressWarnings("unchecked")
	public Occurrence(Connectable parent, OccurType occurType, Value value) {
		this.parent = parent;
		this.type = occurType;
		this.value = value;
		this.dateCreated = new Date();
	}

	/**
	 * constructor
	 * 
	 * @param parent
	 *            Connectable to which this Occurrence belongs
	 * @param occurType
	 *            type of Occurrence
	 * @param value
	 *            value of occurrence
	 * @param createdAt
	 *            when has this been created.
	 */
	@SuppressWarnings("unchecked")
	public Occurrence(Connectable parent, OccurType occurType, Value value,
			Date createdAt) {
		this.parent = parent;
		this.type = occurType;
		this.value = value;
		this.dateCreated = createdAt;
	}

	/**
	 * get Connectable to which this occurrence belongs
	 * 
	 * @return parent Connectable
	 */
	@Any(metaDef = "connectable", metaColumn = @Column(name = "parent_type"))
	@JoinColumn(name = "parent_id")
	// test!!
	// @ManyToOne .. don't use this; Connectable is a very generic interface.
	public Connectable getParent() {
		return parent;
	}

	/**
	 * set Connectable to which this occurrence belongs
	 * 
	 * @param parent
	 *            parent Connectable
	 */
	public void setParent(Connectable parent) {
		this.parent = parent;
	}

	/**
	 * get type of this occurrence
	 * 
	 * @return OccurType of this occurrence
	 */
	@ManyToOne
	public OccurType getType() {
		return type;
	}

	/**
	 * set OccurType of this occurrence
	 * 
	 * @param type
	 *            OccurType
	 */
	public void setType(OccurType type) {
		this.type = type;
	}

	/**
	 * get the value of this occurrence
	 * 
	 * @return Value
	 */
	@SuppressWarnings("unchecked")
	@Any(metaDef = "value", metaColumn = @Column(name = "value_type"))
	@JoinColumn(name = "value_id")
	// test!!
	public Value getValue() {
		return value;
	}

	/**
	 * set the value of this occurrence
	 * 
	 * @param value
	 *            Value
	 */
	@SuppressWarnings("unchecked")
	public void setValue(Value value) {
		this.value = value;
	}

	public void setCreationDate(Date createdDate) {
		this.dateCreated = createdDate;
	}

	public Date getCreationDate() {
		return this.dateCreated;
	}
}
