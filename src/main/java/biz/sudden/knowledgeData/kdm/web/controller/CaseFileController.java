package biz.sudden.knowledgeData.kdm.web.controller;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import biz.sudden.baseAndUtility.domain.CaseFile;
import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;
import biz.sudden.knowledgeData.kdm.service.KdmService;

import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;

public interface CaseFileController extends Renderable {

	public KdmService getKDMService();

	public void setKDMService(KdmService kdmService);

	public void setScopeController(ScopeController sc);

	public List<SelectItem> getCaseFiles();

	public List<SelectItem> getCaseFiles(String keyword);

	public List<SelectItem> getCaseFilesByBO(String keyword);

	public List<SelectItem> getCaseFilesByPartner(String keyword);

	public CaseFile retrieveCaseFile(Long id);

	public List<CaseFile> retrieveAllCaseFiles();

	public void setPMController(PMController controller);

	public void newBO(ActionEvent newBOEvent);

	public void newBO();

	public void deleteBO();

	public void deleteBO(ActionEvent newBOEvent);

	public void setCurrentCaseFile(CaseFile currentCaseFile);

	public void setCurrentCaseFile(String caseFileId);

	public void setUpdateCurrentCaseFileId(String caseFileId);

	public void setNewBoName(String newBoName);

	public String getNewBoName();

	public CaseFile getCurrentCaseFile();

	public String getUpdateCurrentCaseFileId();

	public void updateCurrentCaseFile();

	public void updateCurrentCaseFile(ActionEvent evt);

	public void setCurrentCaseFileId(Long caseFileId);

	public Long getCurrentCaseFileId();

	public void reallyDeleteBOs();

	public void hidePopup();

	public void showPopup();

	public void setPopupIsVisible(boolean popupIsVisible);

	public boolean getPopupIsVisible();

	public void update(CaseFile caseFile);

	public String getBOsToDelete();

	public void setRenderManager(RenderManager rm);
}
