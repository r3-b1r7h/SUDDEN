/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.sudden.evaluation.performanceMeasurement.domain;

import biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile;
import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;

/**
 * stores the weight and either a competenceID or a enterpriseEvaluationProfile
 * used just for visualisation &lt;-&gt; controller communication
 * 
 * @author gweich
 */
public class ParameterizedCompetence {

	protected Object parameter = null;
	protected Dimension competence = null;
	protected EnterpriseEvaluationProfile evaluationProfile = null;
	protected AggregationFunction function = null;

	/**
	 * Get the value of evaluationProfile
	 * 
	 * @return the value of evaluationProfile
	 */
	public EnterpriseEvaluationProfile getEvaluationProfile() {
		return evaluationProfile;
	}

	/**
	 * Set the value of evaluationProfile
	 * 
	 * @param evaluationProfile
	 *            new value of evaluationProfile
	 */
	public void setEvaluationProfile(
			EnterpriseEvaluationProfile evaluationProfile) {
		this.evaluationProfile = evaluationProfile;
	}

	/**
	 * Get the value of competenceID
	 * 
	 * @return the value of competenceID
	 */
	public Dimension getCompetenceDimension() {
		return competence;
	}

	public AggregationFunction getFunction() {
		return this.function;
	}

	/**
	 * Set the value of competenceID
	 * 
	 * @param competenceID
	 *            new value of competenceID
	 */
	public void setCompetenceDimension(Dimension competence) {
		this.competence = competence;
	}

	/**
	 * Get the value of parameter
	 * 
	 * @return the value of param
	 */
	public Object getParameter() {
		return parameter;
	}

	/**
	 * Set the value of parameter; the semantics and type is function dependent
	 * 
	 * @param paraObj
	 *            new value of functionprameter
	 */
	public void setParameter(Object paraObj) {
		this.parameter = paraObj;
	}

	public void setFunction(AggregationFunction fun) {
		this.function = fun;
	}

}
