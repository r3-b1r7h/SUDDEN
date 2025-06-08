package biz.sudden.knowledgeData.kdm.web.controller.impl;

import java.io.File;
import java.util.EventObject;

import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import biz.sudden.knowledgeData.kdm.service.FileUploadService;
import biz.sudden.knowledgeData.kdm.web.controller.FileUploadController;

import com.icesoft.faces.component.inputfile.InputFile;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

/**
 * 
 * @author chris
 * 
 */
public class FileUploadControllerImpl implements FileUploadController {

	private Logger logger = Logger.getLogger(this.getClass());

	private FileUploadService fileUploadService;
	private InputFile inputFile;
	private File file;
	private String msg;
	private PersistentFacesState state;
	private int percent;
	private String path = "";

	public FileUploadControllerImpl() {
		logger.debug(" --> cst");
		state = PersistentFacesState.getInstance();
		inputFile = new InputFile();
	}

	@Override
	public void upload(ActionEvent e) {
		inputFile = (InputFile) e.getSource();
		file = inputFile.getFile();
		path = inputFile.getFileInfo().getPhysicalPath();
		logger.debug("SAVED inputFile = "
				+ (inputFile.getStatus() == InputFile.SAVED));
		if (path != null) {
			fileUploadService.saveFilePath(path);
			logger.debug(fileUploadService.getPathById(fileUploadService
					.getId())
					+ " PathByID");
		}
		percent = inputFile.getFileInfo().getPercent();

		int currstate = inputFile.getStatus();
		if (currstate == InputFile.SIZE_LIMIT_EXCEEDED) {
			msg = "SIZE_LIMIT_EXCEEDED";
		} else if (currstate == InputFile.SAVED) {
			msg = "SAVED";
		} else if (currstate == InputFile.INVALID) {
			msg = "INVALID";
		} else
			msg = "Something went wrong! but don't know what: " + currstate;
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public void progress(EventObject e) {
		logger.debug("progress EventObject");
		InputFile ipf = (InputFile) e.getSource();
		percent = ipf.getFileInfo().getPercent();
		logger.debug(inputFile.getFileInfo().getPercent());
		try {
			if (state != null) {
				state.render();
			}
		} catch (RenderingException ex) {
			logger.error(ex.getMessage());
		}
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public FileUploadService getFileUploadService() {
		return this.fileUploadService;
	}

	@Override
	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	@Override
	public void setInputFile(InputFile inputFile) {
		this.inputFile = inputFile;
	}

	@Override
	public InputFile getInputFile() {
		return this.inputFile;
	}

	@Override
	public int getPercent() {
		return percent;
	}

	@Override
	public void setPercent(int percent) {
		this.percent = percent;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String getPath() {
		return path;
	}
}
