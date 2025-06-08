package biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces;

import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.event.ValueChangeEvent;

import com.icesoft.faces.component.datapaginator.DataPaginator;

/**
 * <p>
 * The DataScrollingModel class is used to show how a dataTable data scrolling
 * can be modfied. There are three display modes for the table; default or no
 * scrolling, scrolling and paging.
 * </p>
 * <p>
 * Scrolling is handled by an attribute on the dataTable component along with a
 * scroll height specification of the scroll viewport
 * </p>
 * <p>
 * Paging is handle by the dataPaginator component.
 * </p>
 * 
 * @since 1.7
 */
public class DataScrollingModel extends TableDefaultControllerImpl {
	/**
	 * Utility method for storing the states of the different scrolling modes.
	 * This class is used alone with standard JSF Map notation to retreive
	 * specific properties.
	 */
	public class DataScrollMode {
		// paging enabled.
		private boolean pagingEnabled;
		// number of rows to display when paging, default value (0) shows
		// all records.
		private int rows;
		// scrolling enabled
		private boolean scrollingEnabled;

		public DataScrollMode(int rows, boolean scrollingEnabled,
				boolean pagingEnabled) {
			this.rows = rows;
			this.scrollingEnabled = scrollingEnabled;
			this.pagingEnabled = pagingEnabled;
		}

		public int getRows() {
			return rows;
		}

		public boolean isPagingEnabled() {
			return pagingEnabled;
		}

		public boolean isScrollingEnabled() {
			return scrollingEnabled;
		}

		public void setPagingEnabled(boolean pagingEnabled) {
			this.pagingEnabled = pagingEnabled;
		}

		public void setRows(int rows) {
			this.rows = rows;
		}

		public void setScrollingEnabled(boolean scrollingEnabled) {
			this.scrollingEnabled = scrollingEnabled;
		}

	}

	/**
	 * dataTable will have no pagging or scrolling enabled.
	 */
	public static final String NO_SCROLLING = "none";
	/**
	 * dataTable will have paging enabled.
	 */
	public static final String PAGINATOR_SCROLLING = "paging";

	/**
	 * dataTable will have scrolling enabled.
	 */
	public static final String SCROLLING_SCROLLING = "scrolling";
	private static HashMap selectedDataScrollModes;

	// Used in this example to reset the paginator when moving between
	// scrolling views, not needed in normal application development.
	private DataPaginator dataPaginatorBinding;

	// currently select scrolling state select by user.
	private String selectedDataScrollMode;

	/**
	 * Creates a new instance where the default scrolling is none.
	 */
	@SuppressWarnings("unchecked")
	public DataScrollingModel() {
		selectedDataScrollMode = PAGINATOR_SCROLLING;
		selectedDataScrollModes = new HashMap();
		// default data table setting
		selectedDataScrollModes.put(NO_SCROLLING, new DataScrollMode(0, false,
				false));
		// scrolling data table settings
		selectedDataScrollModes.put(SCROLLING_SCROLLING, new DataScrollMode(0,
				true, false));
		// paging data table settings
		selectedDataScrollModes.put(PAGINATOR_SCROLLING, new DataScrollMode(9,
				false, true));

		items = new ArrayList();
	}

	@SuppressWarnings("unchecked")
	public DataScrollingModel(int rowsPerPage) {
		selectedDataScrollMode = PAGINATOR_SCROLLING;
		selectedDataScrollModes = new HashMap();
		// default data table setting
		selectedDataScrollModes.put(NO_SCROLLING, new DataScrollMode(0, false,
				false));
		// scrolling data table settings
		selectedDataScrollModes.put(SCROLLING_SCROLLING, new DataScrollMode(0,
				true, false));
		// paging data table settings
		selectedDataScrollModes.put(PAGINATOR_SCROLLING, new DataScrollMode(
				rowsPerPage, false, true));

		items = new ArrayList();
	}

	@SuppressWarnings("unchecked")
	public void addItem(Object item) {
		this.items.add(item);
	}

	public void dataModelChangeListener(ValueChangeEvent event) {
		String oldPagingValue = (String) event.getOldValue();

		if (oldPagingValue.equals(PAGINATOR_SCROLLING)
				&& dataPaginatorBinding != null) {
			dataPaginatorBinding.gotoFirstPage();
		}
	}

	public DataPaginator getDataPaginatorBinding() {
		return dataPaginatorBinding;
	}

	public String getSelectedDataScrollMode() {
		return selectedDataScrollMode;
	}

	public HashMap getSelectedDataScrollModes() {
		return selectedDataScrollModes;
	}

	public void setDataPaginatorBinding(DataPaginator dataPaginatorBinding) {
		this.dataPaginatorBinding = dataPaginatorBinding;
	}

	public void setSelectedDataScrollMode(String selectedDataScrollMode) {
		this.selectedDataScrollMode = selectedDataScrollMode;
	}
}
