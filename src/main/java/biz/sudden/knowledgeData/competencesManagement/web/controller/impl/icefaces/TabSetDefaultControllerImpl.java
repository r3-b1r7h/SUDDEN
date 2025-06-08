package biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AbortProcessingException;

import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.icesoft.faces.component.paneltabset.TabChangeListener;

public class TabSetDefaultControllerImpl implements TabChangeListener,
		Serializable {

	/**
	 * Inner class that represents a tab object with a label, content, and an
	 * index.
	 */
	public class Tab {
		String content;
		String label;
		boolean visible;

		Tab(String label, String content, boolean visible) {
			this.label = label;
			this.content = content;
			this.visible = visible;
		}

		/**
		 * @return the content
		 */
		public String getContent() {
			return content;
		}

		/**
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}

		public boolean isVisible() {
			return visible;
		}

		/**
		 * @param content
		 *            the content to set
		 */
		public void setContent(String content) {
			this.content = content;
		}

		/**
		 * @param label
		 *            the label to set
		 */
		public void setLabel(String label) {
			this.label = label;
		}

		/**
		 * @param visible
		 *            the visible to set
		 */
		public void setVisible(boolean visible) {
			this.visible = visible;
		}
	}

	protected String selectedIndex = "0";
	protected int tabIndex;
	protected String tabPlacement = "top";

	protected List<Tab> tabs = new ArrayList();

	public void addTab(String label, String content, boolean visible) {
		Tab iTab = new Tab(label, content, visible);
		tabs.add(iTab);
	}

	public int getFocusIndex() {
		return Integer.parseInt(selectedIndex);
	}

	public String getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * @return the tabIndex
	 */
	public int getTabIndex() {
		return tabIndex;
	}

	public String getTabLabel(int tabIndex) {
		if (tabs != null && tabs.size() > tabIndex) {
			Tab iTab = tabs.get(tabIndex);
			return iTab.label;
		}
		return null;
	}

	public String getTabPlacement() {
		return tabPlacement;
	}

	/**
	 * @return the tabs
	 */
	public List<Tab> getTabs() {
		return tabs;
	}

	public boolean isTabVisible(int tabIndex) {
		if (tabs != null && tabs.size() > tabIndex) {
			Tab iTab = tabs.get(tabIndex);
			return iTab.isVisible();
		}
		return false;
	}

	@Override
	/*
	 * * Called when the table binding's tab focus changes.
	 * 
	 * @param tabChangeEvent used to set the tab focus.
	 * 
	 * @throws AbortProcessingException An exception that may be thrown by event
	 * listeners to terminate the processing of the current event.
	 */
	public void processTabChange(TabChangeEvent tabChangeEvent)
			throws AbortProcessingException {
		// only used to show TabChangeListener usage.
	}

	/**
	 * removes a tab from panelTabSet.
	 * 
	 * @param tabIndex
	 * 
	 */
	public void removeTab(int tabIndex) {
		// remove the specified tab index if possible.
		if (tabs != null && tabs.size() > tabIndex) {
			tabs.remove(tabIndex);
			// try and fine a valid index
			if (tabIndex > 0) {
				tabIndex--;
			}
		}
	}

	public void setFocusIndex(int index) {
		selectedIndex = String.valueOf(index);
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = String.valueOf(selectedIndex);
	}

	public void setSelectedIndex(String selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	/**
	 * @param tabIndex
	 *            the tabIndex to set
	 */
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public void setTabLabel(int tabIndex, String label) {
		if (tabs != null && tabs.size() > tabIndex) {
			Tab iTab = tabs.get(tabIndex);
			iTab.label = label;
		}
	}

	public void setTabPlacement(String tabPlacement) {
		this.tabPlacement = tabPlacement;
	}

	/**
	 * @param tabs
	 *            the tabs to set
	 */
	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}

	public void setTabVisibility(int tabIndex, boolean visible) {
		if (tabs != null && tabs.size() > tabIndex) {
			Tab iTab = tabs.get(tabIndex);
			iTab.visible = visible;
		}
	}
}
