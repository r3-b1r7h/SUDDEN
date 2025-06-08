package biz.sudden.knowledgeData.competencesManagement.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IDimension;

import com.icesoft.faces.component.ext.HtmlOutputLabel;
import com.icesoft.faces.component.ext.HtmlPanelGrid;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.panelcollapsible.PanelCollapsible;

@SuppressWarnings("serial")
@Entity
@Table(name = "DIMENSIONS")
public class Dimension extends CMQuestionnaireEntity implements IDimension,
		Cloneable, Connectable {

	protected CVI cvi;
	protected float weightMultiplier = 1;

	@Override
	public IDimension clone() {
		Dimension iDimension = new Dimension();

		super.clone(iDimension);

		iDimension.setId(this.Id);
		iDimension.setCvi(this.cvi);

		return iDimension;
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
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@PrimaryKeyJoinColumn
	public CVI getCvi() {
		return cvi;
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
	@ManyToOne
	@Override
	public Long getId() {
		return Id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getQText() {
		return qText;
	}

	@Override
	public float getWeightMultiplier() {
		return weightMultiplier;
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
	public void setCvi(CVI cvi) {
		this.cvi = cvi;
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
	public void setId(Long id) {
		this.Id = id;
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
	public void setWeightMultiplier(Float weightMultiplier) {
		if (weightMultiplier != null)
			this.weightMultiplier = weightMultiplier;
	}

	@Override
	@Transient
	public void toHtml(PanelCollapsible container,
			CMQuestionnaireToHtml questionnaireToHtml, String prefixId,
			int dimensionIndex) {

		HtmlPanelGroup dPanelGroupName = questionnaireToHtml
				.createHtmlPanelGroup("D_PANELGROUP_NAME_" + this.Id, "", "");
		HtmlPanelGroup dPanelGroupQText = questionnaireToHtml
				.createHtmlPanelGroup("D_PANELGROUP_QTEXT_" + this.Id, "", "");

		HtmlOutputLabel dName = questionnaireToHtml.createHtmlOutputLabel(
				prefixId + "D_NAME_" + this.Id, this.name, "", "", "");
		dPanelGroupName.getChildren().add(dName);
		if (!this.name.equals(this.qText)) {
			HtmlOutputLabel dQText = questionnaireToHtml.createHtmlOutputLabel(
					prefixId + "D_QTEXT_" + this.Id, this.qText, "", "", "");
			dPanelGroupQText.getChildren().add(dQText);
		}

		HtmlPanelGrid dPanelGridDimension = questionnaireToHtml
				.createHtmlPanelGrid(prefixId + "DIM_" + this.Id, 1, "",
						"width:100%;margin-top:3px;");

		if ((dimensionIndex % 2) != 0) {
			dPanelGridDimension.setStyle(dPanelGridDimension.getStyle()
					+ "background-color:#E6EDF5;");
		}

		dPanelGridDimension.getChildren().add(dPanelGroupName);
		dPanelGridDimension.getChildren().add(dPanelGroupQText);

		this.cvi.toHtml(dPanelGridDimension, questionnaireToHtml, prefixId
				+ "_D_" + this.Id);

		container.getChildren().add(dPanelGridDimension);
	}

	@Override
	public String toString() {
		String aux = "------ Dimension ------- \n" + super.toString();

		aux = aux + "\n    CVI... \n" + this.getCvi().toString();
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";

		return aux;
	}

}
