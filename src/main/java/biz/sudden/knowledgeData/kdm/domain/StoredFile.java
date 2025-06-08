package biz.sudden.knowledgeData.kdm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

/**
 * domain object Stored file
 * 
 * @author chris
 * 
 */

@Entity
public class StoredFile implements Connectable {

	private String path;
	private Long id;

	public StoredFile() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(columnDefinition = "VARCHAR")
	// ??????
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
