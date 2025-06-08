package biz.sudden.knowledgeData.competencesManagement.domain;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;

import com.icesoft.faces.component.ext.HtmlForm;
import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.HtmlOutputLabel;
import com.icesoft.faces.component.ext.HtmlPanelGrid;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.ext.HtmlSelectBooleanCheckbox;
import com.icesoft.faces.component.ext.HtmlSelectManyCheckbox;
import com.icesoft.faces.component.ext.HtmlSelectOneRadio;
import com.icesoft.faces.component.panelcollapsible.PanelCollapsible;

public class CMQuestionnaireToHtml {

	protected Questionnaire questionnaire;
	protected boolean showUIElementIDs = false;

	public boolean isShowUIElementIDs() {
		return showUIElementIDs;
	}

	public void setShowUIElementIDs(boolean showUIElementIDs) {
		this.showUIElementIDs = showUIElementIDs;
	}

	public CMQuestionnaireToHtml(Questionnaire questionnaire) {
		super();
		this.questionnaire = questionnaire;
	}

	public HtmlInputText createHtmlInputText(String id, String value, int size,
			int maxlength, String styleClass, String style) {
		HtmlInputText inputText = new HtmlInputText();

		inputText.setId(id);
		inputText.setValue(value);
		inputText.setSize(size);
		inputText.setMaxlength(maxlength);
		inputText.setStyleClass(styleClass);
		inputText.setStyle(style);

		return inputText;
	}

	public HtmlOutputLabel createHtmlOutputLabel(String id, String value,
			String forId, String styleClass, String style) {
		HtmlOutputLabel label = new HtmlOutputLabel();

		label.setId(id);
		if (showUIElementIDs) {
			label.setValue("[" + id + "]" + value);
		} else {
			label.setValue(value);
		}
		label.setFor(forId);
		label.setStyle(style);
		label.setStyleClass(styleClass);

		return label;
	}

	public HtmlPanelGrid createHtmlPanelGrid(String id, int columns,
			String styleClass, String style) {
		HtmlPanelGrid panel = new HtmlPanelGrid();

		panel.setId(id);
		panel.setColumns(columns);
		panel.setStyle(style);
		panel.setStyleClass(styleClass);

		return panel;
	}

	public HtmlPanelGrid createHtmlPanelGrid(String id, int columns,
			String styleClass, String style, String cellpadding,
			String cellspacing) {
		HtmlPanelGrid panel = new HtmlPanelGrid();

		panel.setId(id);
		panel.setColumns(columns);
		panel.setStyle(style);
		panel.setStyleClass(styleClass);
		panel.setCellpadding(cellpadding);
		panel.setCellspacing(cellspacing);

		return panel;
	}

	public HtmlPanelGroup createHtmlPanelGroup(String id, String styleClass,
			String style) {
		HtmlPanelGroup panel = new HtmlPanelGroup();

		panel.setId(id);
		panel.setStyle(style);
		panel.setStyleClass(styleClass);

		return panel;
	}

	public HtmlPanelGroup createHtmlPanelGroup(String id, String styleClass,
			String style, String headerFacetId, UIComponent facetComponent) {
		HtmlPanelGroup panel = new HtmlPanelGroup();

		panel.setId(id);
		panel.setStyle(style);
		panel.setStyleClass(styleClass);
		panel.getFacets().put(headerFacetId, facetComponent);

		return panel;
	}

	public HtmlSelectBooleanCheckbox createHtmlSelectBooleanCheckbox(String id,
			String title, String value, String styleClass, String style) {
		HtmlSelectBooleanCheckbox booleanCheckBox = new HtmlSelectBooleanCheckbox();

		booleanCheckBox.setId(id);
		booleanCheckBox.setTitle(title);
		if (showUIElementIDs) {
			booleanCheckBox.setValue("[" + id + "]" + value);
		} else {
			booleanCheckBox.setValue(value);
		}
		booleanCheckBox.setStyleClass(styleClass);
		booleanCheckBox.setStyle(style);

		return booleanCheckBox;
	}

	public HtmlForm createHtmlForm(String id) {
		HtmlForm form = new HtmlForm();

		form.setId(id);

		return form;
	}

	public HtmlSelectManyCheckbox createHtmlSelectManyCheckbox(String id,
			String title, String value, String styleClass, String style) {
		HtmlSelectManyCheckbox manyCheckBox = new HtmlSelectManyCheckbox();

		manyCheckBox.setId(id);
		manyCheckBox.setTitle(title);
		if (showUIElementIDs) {
			manyCheckBox.setValue("[" + id + "]" + value);
		} else {
			manyCheckBox.setValue(value);
		}
		manyCheckBox.setStyleClass(styleClass);
		manyCheckBox.setStyle(style);

		return manyCheckBox;
	}

	public HtmlSelectOneRadio createHtmlSelectOneRadio(String id, String title,
			String value, String styleClass, String style) {
		HtmlSelectOneRadio selectRadio = new HtmlSelectOneRadio();

		selectRadio.setId(id);
		selectRadio.setTitle(title);
		if (showUIElementIDs) {
			selectRadio.setValue("[" + id + "]" + value);
		} else {
			selectRadio.setValue(value);
		}
		selectRadio.setStyleClass(styleClass);
		selectRadio.setStyle(style);

		return selectRadio;
	}

	public PanelCollapsible createPanelCollapsible(String id, String value,
			String styleClass, String style, String headerFacetId,
			UIComponent facetComponent) {
		PanelCollapsible panel = new PanelCollapsible();

		panel.setExpanded(true);
		panel.setId(id);
		if (showUIElementIDs) {
			panel.setValue("[" + id + "]" + value);
		} else {
			panel.setValue(value);
		}
		panel.setStyle(style);
		panel.setStyleClass(styleClass);
		panel.getFacets().put(headerFacetId, facetComponent);

		return panel;
	}

	public UISelectItem createUISelectItem(String id, String value, String label) {
		UISelectItem selectItem = new UISelectItem();

		selectItem.setItemValue(value);
		// selectItem.setValue(value);
		selectItem.setItemLabel(label);
		selectItem.setId(id);

		return selectItem;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

}
