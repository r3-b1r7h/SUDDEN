package biz.sudden.designCoordination.coordination.web.controller.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;
import org.krysalis.jcharts.axisChart.AxisChart;
import org.krysalis.jcharts.axisChart.customRenderers.axisValue.renderers.ValueLabelPosition;
import org.krysalis.jcharts.axisChart.customRenderers.axisValue.renderers.ValueLabelRenderer;
import org.krysalis.jcharts.chartData.AxisChartDataSet;
import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.chartData.DataSeries;
import org.krysalis.jcharts.properties.AxisProperties;
import org.krysalis.jcharts.properties.BarChartProperties;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.util.ChartStroke;
import org.krysalis.jcharts.types.ChartType;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.designCoordination.coordination.service.impl.CoordinationService;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency.TransportationLogistics;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode.ProductionMethod;
import biz.sudden.evaluation.performanceMeasurement.web.controller.PMController;
import com.icesoft.faces.component.ext.HtmlInputHidden;
import com.icesoft.faces.component.outputchart.OutputChart;
import com.icesoft.faces.component.tree.IceUserObject;

public class CoordinationControllerImpl implements CoordinationController {

	private ConcreteSupplyNetwork myCSN;
	/*
	 * The implemented version :)
	 */
	private Logger logger = Logger.getLogger(this.getClass());

	private CoordinationService coordService;
	private PMController controllerForEvaluation;
	private DefaultTreeModel treeModel;
	private List<Map<String, Object>> tableModel;
	private AxisChart chartModel;
	private AxisChart empty;

	private boolean plotVisible = false;

	public void init() {
		;
	}

	public CoordinationControllerImpl() {
		super();
	}

	public CoordinationService getCoordService() {
		return this.coordService;
	}

	public void setCoordService(CoordinationService cs) {
		this.coordService = cs;
	}

	public PMController getControllerForEvaluation() {
		return controllerForEvaluation;
	}

	public void setControllerForEvaluation(PMController controllerForEvaluation) {
		this.controllerForEvaluation = controllerForEvaluation;
	}

	public ConcreteSupplyNetwork getCSN() {
		return this.myCSN;
	}

	public void setCSN(ConcreteSupplyNetwork csnIN) {
		this.myCSN = csnIN;
	}

	public void setPlotVisible(boolean plotVisible) {
		this.plotVisible = plotVisible;
	}

	public boolean isPlotVisible() {
		return plotVisible;
	}

	public List<ASNRoleNode> getccsnrolenodes() {
		if (myCSN != null) {
			return this.myCSN.getASN().getAllRoleNodes();
		} else {
			return null;
		}
	}

	/**
	 * This is the action listener when a user chooses a different production
	 * method in the drop-down list of the table for production methods.
	 */
	public void productionMethodChanged(ValueChangeEvent event) {
		if (event.getNewValue() == null || event.getOldValue() == null) {
			return;
		}

		if (event.getNewValue().equals(event.getOldValue())) {
			return;
		}

		HtmlInputHidden hiddenComp = (HtmlInputHidden) event.getComponent()
				.getParent().findComponent("ASNNodeId");
		Long nodeId = (Long) hiddenComp.getValue();
		ASNRoleNode node = this.myCSN.getASN().getRoleNodeWithId(nodeId);
		if (node != null) {
			productionMethodChanged(node, (ProductionMethod) event
					.getNewValue());
		}
	}

	/**
	 * Builds the table model. It first checks that the table model has changed
	 * (new nodes are in the network or the nodes have been replaced) If the ASN
	 * changed completely, then the table model is rebuild, if not, the model
	 * updates its transient properties. Transient properties: productionMethod
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getTableModel() {
		if (tableModel != null) {
			if (!updateAndCheckIfTableModelNeedsRebuild()) {
				return tableModel;
			}
		}
		if (myCSN != null) {
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

			double max_evaluation = 0;
			int row_number = 0;
			for (ASNRoleNode node : this.myCSN.getASN().getAllRoleNodes()) {
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("node", node);
				row_number++;
				row.put("rowNumber", row_number);
				row.put("nodeId", node.getId());
				Supplier supplier = this.myCSN.getSupplierForNode(node);
				if (supplier != null) {
					row.put("supplierName", supplier.getOrganisation()
							.getName());
					double nodeEvaluation = controllerForEvaluation.evaluate(
							node.getCompetenceNeeded(), supplier
									.getOrganisation());
					row.put("nodeEvaluation", nodeEvaluation);
					// Truncate to two digits precision for label in bar.
					double truncatedEvaluation = ((int) (nodeEvaluation * 100)) / 100.0;
					row.put("nodeEvaluationForBarLabel", truncatedEvaluation);
					max_evaluation = nodeEvaluation > max_evaluation ? nodeEvaluation
							: max_evaluation;
				} else {
					row.put("supplierName", "No supplier");
					row.put("nodeEvaluation", -1.0);
					row.put("nodeEvaluationForBarLabel", -1.0);
				}
				row.put("productType", stripNamespace(node.getproductType()));
				row.put("productionMethod", node.getproductionMethod());
				row.put("selected", false);
				result.add(row);
			}
			// normalize evaluation to show on the bar
			for (Map<String, Object> row : result) {
				double evaluation = (Double) row.get("nodeEvaluation");
				if (max_evaluation == 0) {
					row.put("normalizedEvaluationForBar", 0);
				} else {
					row.put("normalizedEvaluationForBar",
							(int) ((100 * evaluation) / max_evaluation));
				}
			}
			tableModel = result;
			return tableModel;
		} else {
			return null;
		}
	}

	/**
	 * Update the current table model, update transient properties of the table.
	 * Transient properties: productionMethod At the same time, it checks if new
	 * nodes have been added to the ASN... if the ASN changed completely, then
	 * it returns true.
	 * 
	 * @return
	 */
	private boolean updateAndCheckIfTableModelNeedsRebuild() {
		List<ASNRoleNode> nodes = this.myCSN.getASN().getAllRoleNodes();
		if (nodes.size() != tableModel.size()) {
			return true;
		}
		for (Map<String, Object> row : tableModel) {
			ASNRoleNode node = (ASNRoleNode) row.get("node");
			if (!nodes.contains(node)) {
				return true;
			}
			// Update transient properties of the model, the only one is the
			// combo box value
			row.put("productionMethod", node.getproductionMethod());
		}

		return false;
	}

	public void openPlotPopup() {
		this.plotVisible = true;
	}

	public void closePlotPopup() {
		this.plotVisible = false;
	}

	/**
	 * @return Returns a list of SelectItem's. The SelectItem's are enumerating
	 *         the possible values of the enumeration type
	 *         ASNRoleNode.ProductionMethod.
	 */
	public List<SelectItem> getProductionMethods() {
		List<SelectItem> result = new ArrayList<SelectItem>();

		for (ProductionMethod pm : ProductionMethod.values()) {
			SelectItem item = new SelectItem();
			item.setValue(pm);
			item.setLabel(pm.toString());
			result.add(item);
		}

		return result;
	}

	/**
	 * @return Returns a list of SelectItem's. The SelectItem's are enumerating
	 *         the possible values of the enumeration type
	 *         ASNMaterialDependency.TransportationLogistics.
	 */
	public List<SelectItem> getTransportationLogistics() {
		List<SelectItem> result = new ArrayList<SelectItem>();

		for (TransportationLogistics tl : TransportationLogistics.values()) {
			SelectItem item = new SelectItem();
			item.setValue(tl);
			item.setLabel(tl.toString());
			result.add(item);
		}

		return result;
	}

	public List<ASNMaterialDependency> getmaterialDependencies() {
		if (myCSN != null) {
			return this.myCSN.getASN().getAllMaterialDependencies();
		} else {
			return null;
		}
	}

	private String stripNamespace(String url) {
		String[] splittedUrl = url.split("#");
		if (splittedUrl.length >= 2) {
			return splittedUrl[1];
		} else {
			return url;
		}
	}

	/**
	 * Builds the plot model. Observe that the plot takes the data from the
	 * table model, that is why it checks that the table model has not changed.
	 * As the plot is not using any transient property from the table, the plot
	 * does not need to be rebuild when the table changes its transient
	 * properties.
	 * 
	 * @param component
	 * @return
	 */
	public boolean createChartModel(OutputChart component) {
		if (chartModel != null) {
			// Since the plot depends on what is inside the table, we
			// need to verify if the table model changed
			if (tableModel != null) {
				if (!updateAndCheckIfTableModelNeedsRebuild()) {
					component.setChart(chartModel);
					return true;
				}
			}
		}
		// Rebuild or Build the table model
		getTableModel();
		try {
			// Build the labels and data
			String[] labels = new String[tableModel.size()];
			double[] evals = new double[tableModel.size()];
			for (int i = 0; i < tableModel.size(); i++) {
				Map<String, Object> row = tableModel.get(i);
				labels[i] = "" + row.get("rowNumber");
				evals[i] = (Double) row.get("nodeEvaluation");
			}

			String[] xAxisLabels = labels;
			String xAxisTitle = "Supplier Number in Table";
			String yAxisTitle = "Evaluation Value";
			String title = "Suppliers Evaluation";
			DataSeries dataSeries = new DataSeries(xAxisLabels, xAxisTitle,
					yAxisTitle, title);

			double[][] data = new double[][] { evals };
			String[] legendLabels = { "Evaluation" };
			Paint[] paints = new Paint[] { new GradientPaint(0, 400,
					Color.green, 50, 400, Color.yellow, true) };
			BarChartProperties barChartProperties = new BarChartProperties();
			ValueLabelRenderer valueLabelRenderer = new ValueLabelRenderer(
					false, false, true, -1);
			valueLabelRenderer.setValueLabelPosition(ValueLabelPosition.ON_TOP);
			barChartProperties.addPostRenderEventListener(valueLabelRenderer);
			barChartProperties.setShowOutlinesFlag(true);
			ChartStroke outlineChartStroke = new ChartStroke(new BasicStroke(
					2.0f), Color.blue);
			barChartProperties.setBarOutlineStroke(outlineChartStroke);
			AxisChartDataSet axisChartDataSet = new AxisChartDataSet(data,
					legendLabels, paints, ChartType.BAR, barChartProperties);
			dataSeries.addIAxisPlotDataSet(axisChartDataSet);

			ChartProperties chartProperties = new ChartProperties();

			// ---to make this plot horizontally, pass true to the
			// AxisProperties Constructor
			// AxisProperties axisProperties= new AxisProperties( true );
			AxisProperties axisProperties = new AxisProperties();
			LegendProperties legendProperties = new LegendProperties();
			chartModel = new AxisChart(dataSeries, chartProperties,
					axisProperties, legendProperties, 950, 800);
			component.setChart(chartModel);
			return true;
		} catch (Exception e) {
			component.setChart(getEmptyPlot());
			return true;
		}
	}

	private AxisChart getEmptyPlot() {
		if (empty != null) {
			return empty;
		}

		// Create a fake empty chart
		String[] xAxisLabels = new String[] { "?" };
		String xAxisTitle = "Supplier Number in Table";
		String yAxisTitle = "Evaluation Value";
		String title = "Suppliers Evaluation";
		DataSeries dataSeries = new DataSeries(xAxisLabels, xAxisTitle,
				yAxisTitle, title);

		double[][] data = new double[][] { { 0 } };
		String[] legendLabels = { "Evaluation" };
		Paint[] paints = new Paint[] { Color.blue.darker() };
		BarChartProperties barChartProperties = new BarChartProperties();
		AxisChartDataSet axisChartDataSet;
		try {
			axisChartDataSet = new AxisChartDataSet(data, legendLabels, paints,
					ChartType.BAR, barChartProperties);
			dataSeries.addIAxisPlotDataSet(axisChartDataSet);
		} catch (ChartDataException e1) {
		}

		ChartProperties chartProperties = new ChartProperties();
		AxisProperties axisProperties = new AxisProperties();
		LegendProperties legendProperties = new LegendProperties();
		empty = new AxisChart(dataSeries, chartProperties, axisProperties,
				legendProperties, 950, 800);
		return empty;
	}

	/**
	 * Builds the tree model. It first checks that the tree model has changed
	 * (new nodes are in the network or the nodes have been replaced) If the ASN
	 * changed completely, then the tree model is rebuild, if not, the model
	 * updates its transient properties. Transient properties:
	 * dropDownSelectedElement
	 * 
	 * @return
	 */
	public DefaultTreeModel getTreeModel() {
		if (myCSN == null) {
			// Build an empty tree
			DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
			CoordinationTreeNodeUserObject rootObject = new CoordinationTreeNodeUserObject(
					rootTreeNode, this, null);
			rootObject.setText("?");
			rootObject.setLeaf(true);
			rootObject.setProperty("dropDownListElements",
					new ArrayList<SelectItem>());
			rootObject.setProperty("dropDownSelectedElement", "?");
			rootObject.setBranchContractedIcon("/images/clear.gif");
			rootObject.setBranchExpandedIcon("/images/clear.gif");
			rootObject.setLeafIcon("/images/clear.gif");
			rootTreeNode.setUserObject(rootObject);
			DefaultTreeModel empty = new DefaultTreeModel(rootTreeNode);
			return empty;
		}

		if (treeModel != null) {
			// We need to check if the model changed
			if (!updateAndCheckIfTreeModelNeedsRebuild()) {
				return treeModel;
			}
		}
		// We need to rebuild the entire model.
		ASNRoleNode node = this.myCSN.getASN().getFinalNode();
		DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
		CoordinationTreeNodeUserObject rootObject = new CoordinationTreeNodeUserObject(
				rootTreeNode, this, node);
		rootObject.setText(stripNamespace(node.getproductType()));
		rootObject.setExpanded(true);
		rootObject.setProperty("dropDownListElements", getProductionMethods());
		rootObject.setProperty("dropDownSelectedElement", node
				.getproductionMethod());
		rootObject.setBranchContractedIcon("/images/shape29.gif");
		rootObject.setBranchExpandedIcon("/images/shape29.gif");
		rootObject.setLeafIcon("/images/shape29.gif");
		rootTreeNode.setUserObject(rootObject);
		treeModel = new DefaultTreeModel(rootTreeNode);
		buildSubTree(rootTreeNode, node);

		return treeModel;
	}

	private void buildSubTree(DefaultMutableTreeNode treeNode,
			ASNRoleNode networkNode) {
		List<ASNMaterialDependency> dependencies = this.myCSN.getASN()
				.getMaterialDependenciesInto(networkNode);
		if (dependencies.size() == 0) {
			((IceUserObject) treeNode.getUserObject()).setLeaf(true);
		}
		for (ASNMaterialDependency dependency : dependencies) {
			// Add the node for the dependency
			DefaultMutableTreeNode dependencyNode = new DefaultMutableTreeNode();
			CoordinationTreeNodeUserObject dependencyObject = new CoordinationTreeNodeUserObject(
					dependencyNode, this, dependency);
			dependencyObject.setText(dependency.getdependencytype());
			dependencyObject.setExpanded(true);
			dependencyObject.setProperty("dropDownListElements",
					getTransportationLogistics());
			dependencyObject.setProperty("dropDownSelectedElement", dependency
					.gettransportationlogistics());
			dependencyObject.setBranchContractedIcon("/images/arrow_40.gif");
			dependencyObject.setBranchExpandedIcon("/images/arrow_40.gif");
			dependencyObject.setLeafIcon("/images/arrow_40.gif");
			dependencyNode.setUserObject(dependencyObject);
			treeNode.add(dependencyNode);

			// Add the node for the source of the dependency
			ASNRoleNode networkSourceNode = (ASNRoleNode) dependency
					.getSourceNode();
			DefaultMutableTreeNode sourceDependencyNode = new DefaultMutableTreeNode();
			CoordinationTreeNodeUserObject sourceDependencyObject = new CoordinationTreeNodeUserObject(
					sourceDependencyNode, this, networkSourceNode);
			sourceDependencyObject.setText(stripNamespace(networkSourceNode
					.getproductType()));
			sourceDependencyObject.setExpanded(true);
			sourceDependencyObject.setProperty("dropDownListElements",
					getProductionMethods());
			sourceDependencyObject.setProperty("dropDownSelectedElement",
					networkSourceNode.getproductionMethod());
			sourceDependencyObject
					.setBranchContractedIcon("/images/shape29.gif");
			sourceDependencyObject.setBranchExpandedIcon("/images/shape29.gif");
			sourceDependencyObject.setLeafIcon("/images/shape29.gif");
			sourceDependencyNode.setUserObject(sourceDependencyObject);
			dependencyNode.add(sourceDependencyNode);
			buildSubTree(sourceDependencyNode, networkSourceNode);
		}
	}

	/**
	 * Update the current tree model, update transient properties of the nodes.
	 * Transient properties: dropDownSelectedElement At the same time, it checks
	 * if new nodes have been added to the tree... if the tree changed
	 * completely, then it returns true.
	 * 
	 * @return
	 */
	private boolean updateAndCheckIfTreeModelNeedsRebuild() {
		ASNRoleNode node = this.myCSN.getASN().getFinalNode();
		DefaultMutableTreeNode treeRoot = (DefaultMutableTreeNode) treeModel
				.getRoot();
		CoordinationTreeNodeUserObject treeRootUserObject = (CoordinationTreeNodeUserObject) treeRoot
				.getUserObject();
		Connectable networkObject = treeRootUserObject.getNetworkObject();
		if (!networkObject.equals(node)) {
			return true;
		}
		// Update transient properties of node
		treeRootUserObject.setProperty("dropDownSelectedElement", node
				.getproductionMethod());
		return updateAndCheckIfSubTreeNeedsRebuild(treeRoot, node);
	}

	private boolean updateAndCheckIfSubTreeNeedsRebuild(
			DefaultMutableTreeNode treeNode, ASNRoleNode networkNode) {
		List<ASNMaterialDependency> dependencies = this.myCSN.getASN()
				.getMaterialDependenciesInto(networkNode);
		if (treeNode.getChildCount() != dependencies.size()) {
			return true;
		}
		Enumeration<DefaultMutableTreeNode> children = treeNode.children();
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode child = children.nextElement();
			CoordinationTreeNodeUserObject userObject = (CoordinationTreeNodeUserObject) child
					.getUserObject();
			Connectable networkObject = userObject.getNetworkObject();
			if (!dependencies.contains(networkObject)) {
				return true;
			}
			// Update transient properties of dependency node
			userObject.setProperty("dropDownSelectedElement",
					((ASNMaterialDependency) networkObject)
							.gettransportationlogistics());
			// Until now, the dependency matched, now we need to check that the
			// source of the dependency matches
			Enumeration<DefaultMutableTreeNode> childSources = child.children();
			DefaultMutableTreeNode childSource = childSources.nextElement();
			CoordinationTreeNodeUserObject childSourceObject = (CoordinationTreeNodeUserObject) childSource
					.getUserObject();
			Connectable networkChildSourceObject = childSourceObject
					.getNetworkObject();
			if (!networkChildSourceObject
					.equals(((ASNMaterialDependency) networkObject)
							.getSourceNode())) {
				return true;
			}
			// Update transient properties
			childSourceObject.setProperty("dropDownSelectedElement",
					((ASNRoleNode) networkChildSourceObject)
							.getproductionMethod());
			if (updateAndCheckIfSubTreeNeedsRebuild(childSource,
					(ASNRoleNode) networkChildSourceObject)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * This method is called when an event in the combo boxes (in the table or
	 * in the tree) changes the production method.
	 * 
	 */
	@Override
	public void productionMethodChanged(ASNRoleNode node,
			ProductionMethod newValue) {
		node.setproductionMethod(newValue);
	}

	/**
	 * This method is called when an event in the combo boxes in the tree
	 * changes the transportation logistics.
	 * 
	 */
	@Override
	public void transportationLogisticsChanged(
			ASNMaterialDependency dependency, TransportationLogistics newValue) {
		dependency.settransportationlogistics(newValue);
	}

	/**
	 * This method is called when a user clicks on a link in the tree. (The link
	 * is the name of a product). So, the node given as parameter is the node
	 * that corresponds to the clicked product.
	 */
	@Override
	public void selectNodeInTable(ASNRoleNode node) {
		if (tableModel == null) {
			return;
		}

		for (Map<String, Object> row : tableModel) {
			ASNRoleNode nodeInTable = (ASNRoleNode) row.get("node");
			if (nodeInTable.equals(node)) {
				row.put("selected", true);
			} else {
				row.put("selected", false);
			}
		}
	}
}
