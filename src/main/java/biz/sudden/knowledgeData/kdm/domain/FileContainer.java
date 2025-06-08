package biz.sudden.knowledgeData.kdm.domain;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FileContainer {

	File file;
	Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(columnDefinition = "LONGVARBINARY")
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
