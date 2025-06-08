package datasource;

import org.apache.log4j.Logger;

public class SelectJSFTemplate {
	Logger logger = Logger.getLogger(this.getClass());

	private String decorateTemplate;

	/**
	 * @return the decorateTempate
	 */
	public String getDecorateTemplate() {
		logger.debug(decorateTemplate);
		return decorateTemplate;
	}

	/**
	 * @param decorateTempate
	 *            the decorateTempate to set
	 */
	public void setDecorateTemplate(String decorateTemplate) {
		this.decorateTemplate = decorateTemplate;
	}
}
