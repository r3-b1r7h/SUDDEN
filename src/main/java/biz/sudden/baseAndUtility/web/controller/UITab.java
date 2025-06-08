package biz.sudden.baseAndUtility.web.controller;

import com.icesoft.faces.component.ext.HtmlPanelGroup;

public class UITab extends Tab {

	HtmlPanelGroup component;

	public UITab(HtmlPanelGroup component) {
		// TODO Auto-generated constructor stub
		setComponent(component);
	}

	public HtmlPanelGroup getComponent() {
		return component;
	}

	public void setComponent(HtmlPanelGroup component) {
		this.component = component;
	}

}
