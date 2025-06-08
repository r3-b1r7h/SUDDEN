package biz.sudden.knowledgeData.kdm.service.impl;

import java.io.File;

import org.apache.log4j.Logger;

import biz.sudden.knowledgeData.kdm.domain.StoredFile;
import biz.sudden.knowledgeData.kdm.repository.FileContainerRepository;
import biz.sudden.knowledgeData.kdm.repository.FileRepository;
import biz.sudden.knowledgeData.kdm.service.FileUploadService;

/**
 * Service to store and get file path via Filerepository in DB
 * 
 * @author chris
 * 
 */
public class FileUploadServiceImpl implements FileUploadService {

	private Logger logger = Logger.getLogger(this.getClass());

	private String path;
	private Long id;
	private StoredFile storedFile;
	private FileRepository fileRep;

	private FileContainerRepository fileContainerRepository;

	public FileUploadServiceImpl() {
		logger.debug("fileUploadService --> cst");
	}

	public FileContainerRepository getFileContainerRepository() {
		return fileContainerRepository;
	}

	public void setFileContainerRepository(
			FileContainerRepository fileContainerRepository) {
		this.fileContainerRepository = fileContainerRepository;
	}

	public void setFileRepository(FileRepository fileRep) {
		this.fileRep = fileRep;
	}

	public FileRepository getFileRepository() {
		return fileRep;
	}

	public StoredFile getStoredFile() {
		return storedFile;
	}

	public void setStoredFile(StoredFile storedFile) {
		this.storedFile = storedFile;
	}

	@Override
	public String getFilePath() {
		return this.path;
	}

	@Override
	public void setFilePath(String path) {
		this.path = path;
	}

	@Override
	public void saveFilePath(String path) {
		this.path = path;
		logger.debug("FilePath is " + path);
		// FileContainer container = new FileContainer();
		// container.setFile(file);
		// fileContainerRepository.create(container);
		storedFile = new StoredFile();
		storedFile.setPath(path);
		id = fileRep.create(storedFile);
		setId(id);
	}

	@Override
	public File getFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathById(Long id) {
		return fileRep.retrieve(id).getPath();
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
