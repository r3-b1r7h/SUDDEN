package biz.sudden.knowledgeData.kdm.web.controller;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import biz.sudden.baseAndUtility.web.controller.ScopeController;
import biz.sudden.baseAndUtility.web.controller.tree.Tree;
import biz.sudden.knowledgeData.kdm.service.KdmService;

/**
 * TODO: clear method, See KDMService
 * 
 * @author gweich
 * 
 */
public interface KdmController {

	public Tree getConnectableTree();

	public void clean();

	public void initOntology();

	public void initPMS();

	public void initCompanyPerformance();

	public KdmService getKDMService();

	public void setKDMService(KdmService kdmService);

	public void initTest();

	public void setWidth(int with);

	public int getWidth();

	public int getDepth();

	public void setDepth(int depth);

	public void showPopup();

	public void hidePopup();

	public void setPopupIsVisible(boolean popupIsVisible);

	public void setScopeController(ScopeController sc);

	public boolean getPopupIsVisible();

	public String getPerformanceInfo();

	public void setPerformanceInfo(String performanceinfo);

	public SelectItem[] getSelectShowOccurrences();

	public void setShowAssociations(Boolean show);

	public Boolean getShowAssociations();

	public void setShowOccurrences(Boolean show);

	public Boolean getShowOccurrences();

	public SelectItem[] getSelectShowAssociations();

	public void showNodeDetails();

	public void hideNodeDetails();

	public String getNodeDetails();

	public void setShowNodeDetails(boolean popupIsVisible);

	public boolean getShowNodeDetails();

	public String getNodeCount();

	public void refreshTree(ActionEvent ae);

	public void refreshTree();

	public int getShowTreeDepth();

	public void setShowTreeDepth(int showTreeDepth);

	public void upload(ActionEvent e);
}
