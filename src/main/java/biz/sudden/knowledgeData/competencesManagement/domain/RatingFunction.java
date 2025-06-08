package biz.sudden.knowledgeData.competencesManagement.domain;

public class RatingFunction {
	private String code;
	private String nameKey;
	private String descriptionKey;
	private String formatKey;

	public RatingFunction(String code, String nameKey, String descriptionKey,
			String formatKey) {
		this.code = code;
		setNameKey(nameKey);
		setDescriptionKey(descriptionKey);
		setFormatKey(formatKey);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNameKey() {
		return nameKey;
	}

	public void setNameKey(String nameKey) {
		this.nameKey = nameKey;
	}

	public String getDescriptionKey() {
		return descriptionKey;
	}

	public void setDescriptionKey(String descriptionKey) {
		this.descriptionKey = descriptionKey;
	}

	public String getFormatKey() {
		return formatKey;
	}

	public void setFormatKey(String formatKey) {
		this.formatKey = formatKey;
	}

}
