package biz.sudden.knowledgeData.competencesManagement.web.controller.impl.icefaces;

import java.util.ArrayList;

/**
 * <p>
 * The class DataTableBase is the base implementation for all DataTable related
 * examples. This class should be extended for any dataTable example to insure
 * that the example has easy access to common example data.
 * </p>
 * 
 * @since 1.7
 */
public class TableDefaultControllerImpl {

	protected ArrayList items;

	protected void init() {

	}

	public ArrayList getItems() {
		return items;
	}

	@SuppressWarnings("unchecked")
	public void setItems(ArrayList items) {
		this.items = items;
		System.out.println("After rem. " + this.items.size());
	}

}