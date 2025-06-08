package biz.sudden.evaluation.performanceMeasurement.domain;

import com.icesoft.faces.component.outputchart.OutputChart;

public class ChartModel {

	protected String height;
	protected String width;
	protected boolean horizontal;
	protected Object xaxisLabels;
	protected String xaxisTitle;
	protected Object colors;
	protected Object data;

	protected String yaxisTitle;

	protected Object labels;
	protected String type;
	protected boolean renderOnSubmit;
	protected String title;
	protected String legendplacement;

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the Width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the horizontal
	 */
	public boolean isHorizontal() {
		return horizontal;
	}

	/**
	 * @param horizontal
	 *            the horizontal to set
	 */
	public void setHorizontal(boolean horiontal) {
		this.horizontal = horiontal;
	}

	/**
	 * @return the xaxisLabels
	 */
	public Object getXaxisLabels() {
		return xaxisLabels;
	}

	/**
	 * @param xaxisLabels
	 *            the xaxisLabels to set
	 */
	public void setXaxisLabels(Object xaxisLabels) {
		this.xaxisLabels = xaxisLabels;
	}

	/**
	 * @return the xaxisTitle
	 */
	public String getXaxisTitle() {
		return xaxisTitle;
	}

	/**
	 * @param xaxisTitle
	 *            the xaxisTitle to set
	 */
	public void setXaxisTitle(String xaxisTitle) {
		this.xaxisTitle = xaxisTitle;
	}

	/**
	 * @return the colors
	 */
	public Object getColors() {
		return colors;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(Object colors) {
		this.colors = colors;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the yaxisTitle
	 */
	public String getYaxisTitle() {
		return yaxisTitle;
	}

	/**
	 * @param yaxisTitle
	 *            the yaxisTitle to set
	 */
	public void setYaxisTitle(String yaxisTitle) {
		this.yaxisTitle = yaxisTitle;
	}

	/**
	 * @return the labels
	 */
	public Object getLabels() {
		return labels;
	}

	/**
	 * @param labels
	 *            the labels to set
	 */
	public void setLabels(Object labels) {
		this.labels = labels;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public boolean renderOnSubmit(OutputChart component) {
		boolean renderOnSubmit = this.renderOnSubmit;
		// reset render on submit for next request/response
		if (renderOnSubmit) {
			this.renderOnSubmit = false;
		}
		return renderOnSubmit;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setLegendPlacement(String legendplacement) {
		this.legendplacement = legendplacement;
	}

	public String getLegendPlacement() {
		return this.legendplacement;
	}

	public void setRenderOnSubmit(boolean render) {
		this.renderOnSubmit = render;
	}

}
