package biz.sudden.knowledgeData.kdm.web.controller;

import java.io.File;
import java.util.EventObject;

import javax.faces.event.ActionEvent;

import biz.sudden.knowledgeData.kdm.service.FileUploadService;

import com.icesoft.faces.component.inputfile.InputFile;

public interface FileUploadController {

	public void upload(ActionEvent e);

	public void progress(EventObject e);

	public void setFileUploadService(FileUploadService fileUploadService);

	public FileUploadService getFileUploadService();

	public void setInputFile(InputFile inputFile);

	public InputFile getInputFile();

	public File getFile();

	public void setFile(File file);

	public String getMsg();

	public void setMsg(String msg);

	public void setPercent(int percent);

	public int getPercent();

	public void setPath(String path);

	public String getPath();

}
