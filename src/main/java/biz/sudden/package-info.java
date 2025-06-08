@AnyMetaDefs( {
		/*specify classes which implement the connectable interface (directly and indirectly) here*/
		@AnyMetaDef(name = "connectable", metaType = "string", idType = "long", metaValues = {
				/*BaseAndUtility*/
				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.connectable.AssocRoleType.class, value = "assocRoleType"),
				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.connectable.AssocType.class, value = "assocType"),
				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.connectable.BaseConnectable.class, value = "baseConnectable"),
				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.connectable.OccurType.class, value = "occurType"),
				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.Actor.class, value = "actor"),
				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.Manufacturer.class, value = "manufacturer"),
				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.Process.class, value = "process"),

				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.Individual.class, value = "individual"),

				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.CaseFile.class, value = "caseFile"),
				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.BusinessOpportunity.class, value = "businessOpportunity"),

				/*define further Classes which implement Connectable for subsystems here*/

				@MetaValue(targetEntity = biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization.class, value = "organization"),

				/*ServiceManagement*/
				@MetaValue(targetEntity = biz.sudden.knowledgeData.serviceManagement.domain.Item.class, value = "item"),
				@MetaValue(targetEntity = biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct.class, value = "complexProduct"),
				@MetaValue(targetEntity = biz.sudden.knowledgeData.serviceManagement.domain.SimpleProduct.class, value = "simpleProduct"),
				@MetaValue(targetEntity = biz.sudden.knowledgeData.serviceManagement.domain.System.class, value = "system"),
				@MetaValue(targetEntity = biz.sudden.knowledgeData.serviceManagement.domain.Material.class, value = "material"),
				@MetaValue(targetEntity = biz.sudden.knowledgeData.serviceManagement.domain.SupportingService.class, value = "supportingService"),
				@MetaValue(targetEntity = biz.sudden.knowledgeData.serviceManagement.domain.Machine.class, value = "machine"),
				@MetaValue(targetEntity = biz.sudden.knowledgeData.serviceManagement.domain.Technology.class, value = "technology"),

				/*Performance Management*/
				@MetaValue(targetEntity = biz.sudden.evaluation.performanceMeasurement.domain.DivisionFunction.class, value = "divisionFunction"),
				@MetaValue(targetEntity = biz.sudden.evaluation.performanceMeasurement.domain.MaxFunction.class, value = "maxFunction"),
				@MetaValue(targetEntity = biz.sudden.evaluation.performanceMeasurement.domain.MinFunction.class, value = "minFunction"),
				@MetaValue(targetEntity = biz.sudden.evaluation.performanceMeasurement.domain.MinusFunction.class, value = "minusFunction"),
				@MetaValue(targetEntity = biz.sudden.evaluation.performanceMeasurement.domain.MultiplicationFunction.class, value = "multiplicationFunction"),
				@MetaValue(targetEntity = biz.sudden.evaluation.performanceMeasurement.domain.SumFunction.class, value = "sumFunction"),
				@MetaValue(targetEntity = biz.sudden.evaluation.performanceMeasurement.domain.WeightedSumFunction.class, value = "weightedSumFunction"),
				@MetaValue(targetEntity = biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded.class, value = "competenceNeeded"),
				@MetaValue(targetEntity = biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeededByNetworkEvaluationProfile.class, value = "cnByNetworkEvaluation"),

				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.EnterpriseEvaluationProfile.class, value = "enterpriseEvaluationProfile"),
				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile.class, value = "networkEvaluationProfile"),

				@MetaValue(targetEntity = biz.sudden.knowledgeData.competencesManagement.domain.Dimension.class, value = "dimension"),
				@MetaValue(targetEntity = biz.sudden.knowledgeData.competencesManagement.domain.DimensionInstance.class, value = "dimensioninstance"),

				@MetaValue(targetEntity = biz.sudden.knowledgeData.kdm.domain.FileContainer.class, value = "filecontainer"),
				@MetaValue(targetEntity = biz.sudden.knowledgeData.kdm.domain.StoredFile.class, value = "storedfile"),
				@MetaValue(targetEntity = biz.sudden.designCoordination.handleBO.dataStructures.ASNNode.class, value = "asnnode"),
				@MetaValue(targetEntity = biz.sudden.designCoordination.handleBO.dataStructures.ASNBONode.class, value = "asnbonode"),
				@MetaValue(targetEntity = biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork.class, value = "concretesn"),
				@MetaValue(targetEntity = biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork.class, value = "asn"),
				@MetaValue(targetEntity = biz.sudden.designCoordination.teamFormation.dataStructures.Supplier.class, value = "supplier"),
				@MetaValue(targetEntity = biz.sudden.designCoordination.teamFormation.dataStructures.ASNDependency.class, value = "asndependency"),
				@MetaValue(targetEntity = biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency.class, value = "asnmaterialdependency"),
				@MetaValue(targetEntity = biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode.class, value = "asnrolenode"),
				@MetaValue(targetEntity = biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile.class, value = "qualificationprofile"),
				@MetaValue(targetEntity = biz.sudden.designCoordination.teamFormation.dataStructures.StringWithID.class, value = "stringwithid") }),
		/*specify classes which implement the value interface (directly and indirectly) here*/
		@AnyMetaDef(name = "value", metaType = "string", idType = "long", metaValues = {
				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.connectable.StringValue.class, value = "stringValue"),
				@MetaValue(targetEntity = biz.sudden.baseAndUtility.domain.connectable.MethodCallValue.class, value = "referencedValue") }) })
package biz.sudden;

import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.AnyMetaDefs;
import org.hibernate.annotations.MetaValue;

