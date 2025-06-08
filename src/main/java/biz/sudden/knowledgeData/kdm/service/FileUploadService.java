package biz.sudden.knowledgeData.kdm.service;

import java.io.File;

public interface FileUploadService {

	public String getFilePath();

	public void saveFilePath(String path);

	public Long getId();

	public void setId(Long id);

	public void setFilePath(String path);

	public File getFile();

	public String getPathById(Long id);
}
