package biz.sudden.knowledgeData.competencesManagement.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlOutputLabel;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICVI;

import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.HtmlPanelGrid;
import com.icesoft.faces.component.ext.HtmlSelectBooleanCheckbox;
import com.icesoft.faces.component.ext.HtmlSelectOneRadio;

@SuppressWarnings("serial")
@Entity
@Table(name = "CVIS")
public class CVI extends CMQuestionnaireEntity implements ICVI, Cloneable {

	protected float maxRange = 0;
	protected float minRange = 0;
	protected boolean multipleChoice = false;
	protected String multipleChoiceValues = "";
	protected List<Tick> ticks = new ArrayList<Tick>();

	@Override
	public void addTick(Tick tick) {
		ticks.add(tick);
	}

	@Override
	public ICVI clone() {
		CVI iCVI = new CVI();

		super.clone(iCVI);

		iCVI.setId(this.Id);
		iCVI.setMaxRange(this.maxRange);
		iCVI.setMinRange(this.minRange);
		iCVI.setMultipleChoice(this.multipleChoice);
		iCVI.setMultipleChoiceValues(this.multipleChoiceValues);
		for (int c0 = 0; c0 < this.ticks.size(); c0++) {
			Tick iTick = ticks.get(c0);
			iCVI.addTick((Tick) iTick.clone());
		}

		return iCVI;
	}

	@Override
	public Long getCategoryId() {
		return categoryId;
	}

	@Override
	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getEText() {
		return eText;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@Override
	public Long getId() {
		return Id;
	}

	@Override
	public float getMaxRange() {
		return maxRange;
	}

	@Override
	public float getMinRange() {
		return minRange;
	}

	@Override
	public String getMultipleChoiceValues() {
		return multipleChoiceValues;
	}

	@Override
	public String getName() {
		return name;
	}

	@Transient
	private int getNumberOf_TICK_TYPE_RADIO() {
		int numberOfTicks = 0;

		for (int c0 = 0; c0 < ticks.size(); c0++) {
			if (ticks.get(c0).getType() == Tick.TICK_TYPE_RADIO) {
				numberOfTicks++;
			}
		}

		return numberOfTicks;
	}

	@Transient
	public int getNumberOfQuantifiableTicks() {
		int numberOfQuantifiableTicks = 0;

		Iterator<Tick> iterator = ticks.iterator();
		while (iterator.hasNext()) {
			Tick tick = iterator.next();
			if (tick.isQuantifiable()) {
				numberOfQuantifiableTicks++;
			}
		}

		return numberOfQuantifiableTicks;
	}

	@Override
	public String getQText() {
		return qText;
	}

	@Transient
	public List<Tick> getQuantifiableTicks() {
		List<Tick> quantifiableTicks = new ArrayList<Tick>();

		Iterator<Tick> iterator = ticks.iterator();
		while (iterator.hasNext()) {
			Tick tick = iterator.next();
			if (tick.isQuantifiable()) {
				quantifiableTicks.add(tick);
			}
		}

		return quantifiableTicks;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL)
	public List<Tick> getTicks() {
		return ticks;
	}

	@Override
	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	@Override
	public void removeTick(int i) {
		ticks.remove(i);
	}

	@Override
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setEText(String text) {
		eText = text;
	}

	@Override
	public void setId(Long Id) {
		this.Id = Id;
	}

	@Override
	public void setMaxRange(float maxRange) {
		this.maxRange = maxRange;
	}

	@Override
	public void setMinRange(float minRange) {
		this.minRange = minRange;
	}

	@Override
	public void setMultipleChoice(boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}

	@Override
	public void setMultipleChoiceValues(String multipleChoiceValues) {
		this.multipleChoiceValues = multipleChoiceValues;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setQText(String text) {
		qText = text;
	}

	@Override
	public void setTicks(List<Tick> ticks) {
		this.ticks = ticks;
	}

	@Override
	@Transient
	public void toHtml(HtmlPanelGrid container,
			CMQuestionnaireToHtml questionnaireToHtml, String prefixId) {

		HtmlSelectOneRadio selectOneRadio = questionnaireToHtml
				.createHtmlSelectOneRadio("CVI_RADIO_" + prefixId + "_CVI_"
						+ this.Id, this.qText, "", "", "");

		HtmlPanelGrid cviPanelGridQuantifiable = questionnaireToHtml
				.createHtmlPanelGrid("CVI_PANELGRID_Q_" + prefixId + "_CVI_"
						+ this.Id, 1, "", "");

		HtmlPanelGrid cviPanelGridNonQuantifiable = questionnaireToHtml
				.createHtmlPanelGrid("CVI_PANELGRID_NQ_" + prefixId + "_CVI_"
						+ this.Id, 1, "", "");

		if (!this.multipleChoice && getNumberOf_TICK_TYPE_RADIO() > 0) {
			cviPanelGridQuantifiable.getChildren().add(selectOneRadio);
		} else {
			cviPanelGridQuantifiable.setColumns(6);
		}

		for (int c0 = 0; c0 < ticks.size(); c0++) {
			Tick tick = ticks.get(c0);
			HtmlOutputLabel label;
			UISelectItem selectItem;
			switch (tick.type) {
			case Tick.TICK_TYPE_CHECK:
				label = questionnaireToHtml.createHtmlOutputLabel("CVI_LABEL_"
						+ prefixId + "_CVI_" + this.Id + "_T_" + tick.getId(),
						tick.getTTextValue(), "CVI_CHECKBOX_" + prefixId
								+ "_CVI_" + this.Id + "_T_" + tick.getId(), "",
						"");
				HtmlSelectBooleanCheckbox booleanCheckBox = questionnaireToHtml
						.createHtmlSelectBooleanCheckbox("CVI_CHECKBOX_"
								+ prefixId + "_CVI_" + this.Id + "_T_"
								+ tick.getId(), tick.getTTextValue(), tick
								.getTTextValue(), "", "");

				cviPanelGridQuantifiable.getChildren().add(booleanCheckBox);
				cviPanelGridQuantifiable.getChildren().add(label);
				break;
			case Tick.TICK_TYPE_RADIO:
				selectItem = questionnaireToHtml.createUISelectItem(
						"CVI_RADIO_" + prefixId + "_CVI_" + this.Id + "_T_"
								+ tick.getId(), "CVI_RADIO_" + prefixId
								+ "_CVI_" + this.Id + "_T_" + tick.getId(),
						tick.getTTextValue());
				selectOneRadio.getChildren().add(selectItem);
				break;
			default:
				label = questionnaireToHtml.createHtmlOutputLabel("CVI_LABEL_"
						+ prefixId + "_CVI_" + this.Id + "_T_" + tick.getId(),
						tick.getTTextValue(), "CVI_INPUTTEXT_" + prefixId
								+ "_CVI_" + this.Id + "_T_" + tick.getId(), "",
						"");

				HtmlInputText inputText = questionnaireToHtml
						.createHtmlInputText("CVI_INPUTTEXT_" + prefixId
								+ "_CVI_" + this.Id + "_T_" + tick.getId(), "",
								100, 500, "", "");

				cviPanelGridNonQuantifiable.getChildren().add(label);
				if (tick.autoValue != Tick.TICK_AUTOVALUE_USER) {
					inputText.setDisabled(true);
				}
				cviPanelGridNonQuantifiable.getChildren().add(inputText);
				break;
			}
		}

		container.getChildren().add(cviPanelGridQuantifiable);
		container.getChildren().add(cviPanelGridNonQuantifiable);
	}

	@Override
	public String toString() {
		String aux = "------ CVI ------- \n" + super.toString();

		aux = aux + " || Max range: " + this.maxRange;
		aux = aux + " || Min range: " + this.minRange;
		aux = aux + " || Multiple Choice: " + this.multipleChoice;
		aux = aux + " || Multiple Choice Values: " + this.multipleChoiceValues;
		aux = aux + "\nTicks size: " + this.getTicks().size();
		for (int c0 = 0; c0 < this.getTicks().size(); c0++) {
			aux = aux + "\nTick " + c0 + "-> "
					+ this.getTicks().get(c0).toString();
		}
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";

		return aux;
	}

}
