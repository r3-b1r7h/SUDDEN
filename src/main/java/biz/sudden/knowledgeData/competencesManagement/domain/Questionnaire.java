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
import javax.persistence.Table;
import javax.persistence.Transient;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.IQuestionnaire;

import com.icesoft.faces.component.ext.HtmlOutputLabel;
import com.icesoft.faces.component.ext.HtmlOutputText;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.panelcollapsible.PanelCollapsible;

@SuppressWarnings("serial")
@Entity
@Table(name = "QUESTIONNAIRES")
public class Questionnaire extends CMQuestionnaireEntity implements
		IQuestionnaire, Cloneable {

	protected List<Competence> competences;
	protected HtmlOutputText questionnaireHtml;
	protected CMQuestionnaireToHtml questionnaireToHtml;
	protected ArrayList<CMQuestionnaireRequiredCompetenceDimensions> requiredCompetenceDimensions;

	public Questionnaire() {
		super();
		competences = new ArrayList<Competence>();
		requiredCompetenceDimensions = new ArrayList<CMQuestionnaireRequiredCompetenceDimensions>();
		questionnaireHtml = new HtmlOutputText();
		questionnaireToHtml = new CMQuestionnaireToHtml(this);
	}

	@Override
	public CMQuestionnaireRequiredCompetenceDimensions addCompetence(
			Competence competence) {
		competences.add(competence);
		CMQuestionnaireRequiredCompetenceDimensions questionnaireRequiredCompetenceDimensions = new CMQuestionnaireRequiredCompetenceDimensions(
				competence);
		requiredCompetenceDimensions
				.add(questionnaireRequiredCompetenceDimensions);
		return questionnaireRequiredCompetenceDimensions;
	}

	@Override
	public IQuestionnaire clone() {
		Questionnaire iQuestionnaire = new Questionnaire();

		super.clone(iQuestionnaire);

		iQuestionnaire.setId(this.Id);
		for (int c0 = 0; c0 < this.competences.size(); c0++) {
			Competence iCompetenceAux = this.competences.get(c0);
			iQuestionnaire.getCompetences().add(
					(Competence) iCompetenceAux.clone());
		}
		for (int c0 = 0; c0 < this.requiredCompetenceDimensions.size(); c0++) {
			CMQuestionnaireRequiredCompetenceDimensions iRequiredCompetenceDimensionsAux = this.requiredCompetenceDimensions
					.get(c0);
			iQuestionnaire.getRequiredCompetenceDimensions().add(
					iRequiredCompetenceDimensionsAux.clone());
		}

		return iQuestionnaire;
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
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public List<Competence> getCompetences() {
		return competences;
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
	public String getName() {
		return name;
	}

	@Override
	public String getQText() {
		return qText;
	}

	@Override
	@Transient
	public HtmlOutputText getQuestionnaireHtml() {
		return questionnaireHtml;
	}

	@Transient
	public CMQuestionnaireToHtml getQuestionnaireToHtml() {
		return questionnaireToHtml;
	}

	@Override
	public ArrayList<CMQuestionnaireRequiredCompetenceDimensions> getRequiredCompetenceDimensions() {
		return requiredCompetenceDimensions;
	}

	@Override
	public void removeCompetence(int index) {
		requiredCompetenceDimensions.remove(index);
		competences.remove(index);
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
	public void setCompetences(List<Competence> competences) {
		this.competences = competences;
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
	@Transient
	public void setQuestionnaireHtml(HtmlOutputText questionnaireHtml) {
		this.questionnaireHtml = questionnaireHtml;
	}

	@Transient
	public void setQuestionnaireToHtml(CMQuestionnaireToHtml questionnaireToHtml) {
		this.questionnaireToHtml = questionnaireToHtml;
	}

	@Override
	public void setRequiredCompetenceDimensions(
			ArrayList<CMQuestionnaireRequiredCompetenceDimensions> requiredCompetenceDimensions) {
		this.requiredCompetenceDimensions = requiredCompetenceDimensions;
	}

	@Override
	@Transient
	public void toHtml(HtmlPanelGroup container) {
		container.getChildren().clear();
		container.setStyleClass("pnlClpsblWrapper panelCollapsible");

		if (this.Id == null) {
			this.Id = new Long(-1);
		}

		if (this.name == null) {
			this.name = "NAME";
		}

		if (this.description == null) {
			this.description = " - DESCRIPTION";
		}

		HtmlOutputLabel qName = questionnaireToHtml.createHtmlOutputLabel(
				"Q_NAME", "(" + this.Id + ") " + this.name, "", "", "");
		HtmlOutputLabel qDescription = questionnaireToHtml
				.createHtmlOutputLabel("Q_DESCRIPTION", this.description, "",
						"", "");
		HtmlPanelGroup qTitle = questionnaireToHtml.createHtmlPanelGroup(
				"Q_TITLE", "", "");
		qTitle.getChildren().add(qName);
		qTitle.getChildren().add(qDescription);

		PanelCollapsible qContainer = questionnaireToHtml
				.createPanelCollapsible("Q_" + this.Id, "QUESTIONNAIRE HTML",
						"pnlClpsblWrapper panelCollapsible", "", "header",
						qTitle);

		for (int c0 = 0; c0 < competences.size(); c0++) {
			Competence competence = competences.get(c0);
			competence.toHtml(qContainer, questionnaireToHtml,
					requiredCompetenceDimensions, c0);
		}

		container.getChildren().add(qContainer);
	}

	@Override
	public String toString() {
		String aux = "------ QUESTIONNAIRE ------- \n" + super.toString();

		aux = aux + "    ------------------    ";
		aux = aux + "\n Competences -->";
		for (int c0 = 0; c0 < competences.size(); c0++) {
			aux = aux + competences.get(c0).toString();
		}
		aux = aux + "    ------------------    ";
		aux = aux + "\n Competences Required Dimensions -->";
		for (int c0 = 0; c0 < requiredCompetenceDimensions.size(); c0++) {
			aux = aux + requiredCompetenceDimensions.get(c0).toString();
		}
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";

		return aux;
	}

}