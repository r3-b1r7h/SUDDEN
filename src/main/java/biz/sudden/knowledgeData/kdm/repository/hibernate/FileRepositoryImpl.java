package biz.sudden.knowledgeData.kdm.repository.hibernate;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.knowledgeData.kdm.domain.StoredFile;
import biz.sudden.knowledgeData.kdm.repository.FileRepository;

public class FileRepositoryImpl extends GenericRepositoryImpl<StoredFile, Long>
		implements FileRepository {

	private Logger logger = Logger.getLogger(this.getClass());

	public FileRepositoryImpl() {
		super(StoredFile.class);
		logger.debug(this.getClass() + " --> cst");
	}

	public FileRepositoryImpl(Class<StoredFile> type) {
		super(type);
	}
}
