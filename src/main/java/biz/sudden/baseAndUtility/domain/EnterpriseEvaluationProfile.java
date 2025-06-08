package biz.sudden.baseAndUtility.domain;

import javax.persistence.Entity;

import biz.sudden.evaluation.performanceMeasurement.domain.EvaluationProfile;

/**
 * The performance evaluation profile holds the following information:
 * <ul>
 * <li><b>Function</b> to use for aggregation <i>(implemented as
 * association)</i>; e.g.: weighted sum</li>
 * <li>selected <b>competence dimensions</b>, or <b>(sub-)profiles</b>
 * <i>(implemented as connectables</i></li>
 * <li><b>parameters</b> <i>(implemented as roles)</i> for the aggregation
 * function. e.g.:</li>
 *</ul>
 * 
 * <pre>
 *  A.QualityManagement.value = a.value * a.weight + b.value * b.weight + c.value * c.weight: 
 *  a.QualityManagement.Question_L0_1.value, weight: 40% 
 *  b.QualityManagement.Question_L0_2.value, weight: 30%
 *  c.QualityManagement.Question_L0_2.value, weight: 30%
 * 
 * B.Leadership.value = a.value * a.weight + b.value * b.weight + c.value * c.weight 
 * a.Leadership.Question_L0_1.value, weight: 33% 
 * b. Leadership.Question_L0_1.value, weight: 33% 
 * c. Leadership.Question_L0_1.value, weight: 34%
 * 
 * Total Competence Performance = A.value * A.weight + B.value * B.weight: 
 * A.QualityManagement.value, weight: 50% 
 * B.Leadership.value, weight: 50%
 * </pre>
 * 
 * This profile is applied to all suppliers under consideration and allows to
 * compare them on equal footing.
 * 
 * 
 * @see EvaluationProfile
 * @see biz.sudden.baseAndUtility.domain.connectable.Connectable
 * @author gweich
 * 
 */
@Entity
public class EnterpriseEvaluationProfile extends EvaluationProfile {

	public EnterpriseEvaluationProfile() {
		super();
	}

	public EnterpriseEvaluationProfile(String name) {
		super(name);
	}

}
