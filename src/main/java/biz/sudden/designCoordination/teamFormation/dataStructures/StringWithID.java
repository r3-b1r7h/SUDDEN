/**
 * 
 */
package biz.sudden.designCoordination.teamFormation.dataStructures;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity
public class StringWithID implements Connectable {

	Long id;
	String s;

	public StringWithID() {
	}

	public StringWithID(String s) {
		this.s = s;
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

	public String getString() {
		return s;
	}

	public void setString(String s) {
		this.s = s;
	}

}