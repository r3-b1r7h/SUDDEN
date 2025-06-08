package biz.sudden.evaluation.performanceMeasurement.web.controller;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.baseAndUtility.web.controller.tree.TreeDragDrop;

import com.icesoft.faces.component.dragdrop.DropEvent;

public interface PMEnterpriseProfileDesignController {

	/** set where to go to in the trace of the last visited nodes (PIs) */
	public void goTo();

	public TreeDragDrop getIndicatorTree();

	/**
	 * get all available enterprise profiles from the service
	 * 
	 * @return a list of all profiles
	 */
	public List<EnterpriseEvaluationProfile> retrieveAllEnterpriseProfiles();

	/**
	 * add a new subEnt.Eval.Prof. based on the name, weight, and funtion
	 * selected in the GUI
	 */
	public void addSub();

	/**
	 * add a new subEnt.Eval.Prof. based on the name, weight, and funtion
	 * selected in the GUI
	 */
	public void addSub(ActionEvent evt);

	public void create();

	public void create(ActionEvent evt);

	public void dropListener(DropEvent dropEvent);

	public SelectItem[] getFunctionNames();

	/** remove the current selected node from the target tree */
	public void deleteSelected(ActionEvent evt);

	/**
	 * function type to be used when dragging and dropping or creating a new
	 * subprofile
	 */
	public String getNewEProfileAggregationFunction();

	/**
	 * function type to be used when dragging and dropping or creating a new
	 * subprofile
	 */
	public void setNewEProfileAggregationFunction(
			String newEProfileAggregationFunction);

	public String getSelectedName();

	public void setSelectedName(String newName);

	public String getNewEProfileParameter();

	public void setNewEProfileParameter(String newWeight);

	public int getTreeDepth();

	public void setTreeDepth(int treeDepth);

	public void refreshTree();

	public void refreshTree(ActionEvent ae);

	public void setSelectedEProfile(String id);

	public void setSelectedEProfile(EnterpriseEvaluationProfile eep);

	/**
	 * returns true if links to other modules are possible and link-buttons and
	 * already set links should be shown.
	 */
	public boolean showLinks();

	boolean getShowLinks();

}