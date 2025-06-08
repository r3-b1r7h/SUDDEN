package biz.sudden.knowledgeData.competencesManagement.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICompetence;

import com.icesoft.faces.component.ext.HtmlOutputLabel;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.panelcollapsible.PanelCollapsible;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMPETENCES")
public class Competence extends CMQuestionnaireEntity implements ICompetence,
		Cloneable {

	protected List<Dimension> dimensions;
	protected float weightMultiplier = 1;

	public Competence() {
		super();
		dimensions = new ArrayList<Dimension>();
	}

	@Override
	@Transient
	public void addDimension(Dimension dimension) {
		dimensions.add(dimension);
	}

	@Override
	public ICompetence clone() {
		Competence iCompetence = new Competence();

		super.clone(iCompetence);

		iCompetence.setId(this.Id);
		for (int c0 = 0; c0 < this.dimensions.size(); c0++) {
			Dimension iDimensionAux = this.dimensions.get(c0);
			iCompetence.getDimensions().add((Dimension) iDimensionAux.clone());
		}
		return iCompetence;
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

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@Override
	public List<Dimension> getDimensions() {
		return dimensions;
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
	public void removeDimension(int index) {
		dimensions.remove(index);
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
	public void setDimensions(List<Dimension> dimensions) {
		this.dimensions = dimensions;
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
	public void setWeightMultiplier(float weightMultiplier) {
		this.weightMultiplier = weightMultiplier;
	}

	@Override
	@Transient
	public void toHtml(
			PanelCollapsible container,
			CMQuestionnaireToHtml questionnaireToHtml,
			ArrayList<CMQuestionnaireRequiredCompetenceDimensions> requiredCompetenceDimensions,
			int competenceIndex) {

		HtmlOutputLabel cName = questionnaireToHtml.createHtmlOutputLabel(
				"C_NAME_" + this.Id + "_INDEX_" + competenceIndex, this.name,
				"", "", "");
		HtmlOutputLabel cDescription = questionnaireToHtml
				.createHtmlOutputLabel("C_DESCRIPTION_" + this.Id + "_INDEX_"
						+ competenceIndex, this.description, "", "", "");
		HtmlPanelGroup cPanelName = questionnaireToHtml
				.createHtmlPanelGroup("C_PANEL_NAME_" + this.Id + "_INDEX_"
						+ competenceIndex, "", "");
		HtmlPanelGroup cPanelDescription = questionnaireToHtml
				.createHtmlPanelGroup("C_PANEL_DESCRIPTION_" + this.Id
						+ "_INDEX_" + competenceIndex, "", "");
		cPanelName.getChildren().add(cName);
		cPanelDescription.getChildren().add(cDescription);

		PanelCollapsible cContainer = questionnaireToHtml
				.createPanelCollapsible("C_" + this.Id + "_INDEX_"
						+ competenceIndex, "COMPETENCE HTML",
						"pnlClpsblWrapper panelCollapsible", "", "header",
						cPanelName);
		cContainer.getChildren().add(cPanelDescription);

		for (int c0 = 0; c0 < dimensions.size(); c0++) {
			if (requiredCompetenceDimensions.get(competenceIndex).requiredDimensions
					.get(c0).isRequired()) {
				Dimension dimension = dimensions.get(c0);
				dimension.toHtml(cContainer, questionnaireToHtml, "C_"
						+ this.Id + "_INDEX_" + competenceIndex + "_", c0);
			}
		}

		container.getChildren().add(cContainer);
	}

	@Override
	public String toString() {
		String aux = "------ CVI ------- \n" + super.toString();

		aux = aux + "\n Dimensions -->";
		for (int c0 = 0; c0 < dimensions.size(); c0++) {
			aux = aux + dimensions.get(c0).toString();
		}
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";

		return aux;
	}

}
