package biz.sudden.knowledgeData.competencesManagement.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CMQuestionnaireRequiredCompetenceDimensions implements
		Serializable {

	public class RequiredDimension implements Serializable {
		protected long id;
		protected String name = "";
		protected boolean required = true;

		public RequiredDimension() {
			super();
		}

		public RequiredDimension(Dimension dimension) {
			super();
			id = dimension.getId();
			name = dimension.getName();
			required = true;
		}

		@Override
		public RequiredDimension clone() {
			RequiredDimension dimension = new RequiredDimension();
			dimension.setId(this.id);
			dimension.setName(this.name);
			dimension.setRequired(this.required);

			return dimension;
		}

		public long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public boolean isRequired() {
			return required;
		}

		public void setId(long id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setRequired(boolean required) {
			this.required = required;
		}

		@Override
		public String toString() {
			String aux = "Required: " + this.required;
			aux = aux + "  Id: " + this.id;
			aux = aux + "  Name: " + this.name;

			return aux;
		}

	}

	protected String competenceDescription;
	protected long competenceId;
	protected String competenceName;
	protected ArrayList<RequiredDimension> requiredDimensions;
	protected boolean selected;

	public CMQuestionnaireRequiredCompetenceDimensions() {
		super();
		requiredDimensions = new ArrayList<RequiredDimension>();
	}

	public CMQuestionnaireRequiredCompetenceDimensions(Competence competence) {
		super();
		this.competenceId = competence.getId();
		this.competenceName = competence.getName();
		this.competenceDescription = competence.getDescription();
		requiredDimensions = new ArrayList<RequiredDimension>();
		addDimensions(competence.getDimensions());
	}

	public void addDimensions(List<Dimension> dimensions) {
		requiredDimensions.clear();
		for (int c0 = 0; c0 < dimensions.size(); c0++) {
			RequiredDimension dimension = new RequiredDimension(dimensions
					.get(c0));
			requiredDimensions.add(dimension);
		}
	}

	@Override
	public CMQuestionnaireRequiredCompetenceDimensions clone() {
		CMQuestionnaireRequiredCompetenceDimensions iRequiredCompetenceDimensions = new CMQuestionnaireRequiredCompetenceDimensions();

		iRequiredCompetenceDimensions.setCompetenceId(this.competenceId);
		iRequiredCompetenceDimensions.setCompetenceName(this.competenceName);
		iRequiredCompetenceDimensions
				.setCompetenceDescription(this.competenceDescription);
		iRequiredCompetenceDimensions.setSelected(this.selected);
		for (int c0 = 0; c0 < this.requiredDimensions.size(); c0++) {
			RequiredDimension iDimensionAux = this.requiredDimensions.get(c0);
			iRequiredCompetenceDimensions.getRequiredDimensions().add(
					iDimensionAux.clone());
		}

		return iRequiredCompetenceDimensions;
	}

	public String getCompetenceDescription() {
		return competenceDescription;
	}

	public long getCompetenceId() {
		return competenceId;
	}

	public String getCompetenceName() {
		return competenceName;
	}

	public ArrayList<RequiredDimension> getRequiredDimensions() {
		return requiredDimensions;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setCompetenceDescription(String competenceDescription) {
		this.competenceDescription = competenceDescription;
	}

	public void setCompetenceId(long competenceId) {
		this.competenceId = competenceId;
	}

	public void setCompetenceName(String competenceName) {
		this.competenceName = competenceName;
	}

	public void setRequiredDimensions(
			ArrayList<RequiredDimension> requiredDimensions) {
		this.requiredDimensions = requiredDimensions;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		String aux = "";

		aux = aux + "Id: " + this.getCompetenceId();
		aux = aux + "\nName: " + this.getCompetenceName();
		aux = aux + "\nDescription: " + this.getCompetenceDescription();
		aux = aux + "    ------------------    ";
		aux = aux + "\n Competences Required Dimensions -->";
		for (int c0 = 0; c0 < requiredDimensions.size(); c0++) {
			aux = aux + requiredDimensions.get(c0).toString();
		}
		aux = aux + "----------------------------------------------";
		aux = aux + "----------------------------------------------";

		return aux;
	}

}
