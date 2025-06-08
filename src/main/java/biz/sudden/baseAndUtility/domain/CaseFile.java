package biz.sudden.baseAndUtility.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.designCoordination.handleBO.dataStructures.ASNNode;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;
import biz.sudden.designCoordination.teamFormation.dataStructures.TeamFormationConstants;
import biz.sudden.knowledgeData.serviceManagement.domain.ComplexProduct;

@Entity()
public class CaseFile implements Connectable {

	Logger logger = Logger.getLogger(this.getClass());
	Long id;
	String name;
	/**
	 * users may record here any information like if a contract from the OEM is
	 * existing or .....
	 */
	String sundryInformation;
	/** triggers everything */
	BusinessOpportunity bo;
	// FIXME CaseFileController dies!!
	// @Transient
	// public final static short ENGINEERING_PROTOTYPE_Phase0 = 0;
	// @Transient
	// public final static short MANUFACTURING_PROTOTYPE_Phase1 = 1;
	// @Transient
	// public final static short ENGINEERING_PRODUCT_Phase2 = 2;
	// @Transient
	// public final static short MANUFACTURING_PRODUCT_Phase3 = 3;
	short phase = 0;
	/** goal of Phase1 a concrete BO with a core team */
	ConcreteSupplyNetwork prototypeTeam;
	AbstractSupplyNetwork asnPrototypeTeam;
	/** First working prototype is the goal of Phase 2 */
	ComplexProduct prototype;
	/**
	 * Manufacturing and Logistics Process - goal Phase 3
	 * */
	AbstractSupplyNetwork asnFinalTeam;
	/**
	 * including the used technology and material (which in stored in the ASN to
	 * which the final Team points
	 */
	ConcreteSupplyNetwork finalTeam;
	/** list of candidate teams */
	List<ConcreteSupplyNetwork> tempTeams = new ArrayList<ConcreteSupplyNetwork>();
	/** End product as a goal of Phase 4 */
	ComplexProduct finalProduct;

	String targeted_End_Customer_Group = " ... ";

	Boolean has_It_Potential_For_Sales = Boolean.FALSE;

	Boolean is_There_A_Product_Or_Variant_Plan = Boolean.FALSE;

	Boolean is_It_Feasible_In_Technical_Terms = Boolean.FALSE;

	Boolean is_It_Feasible_In_Economic_Terms = Boolean.FALSE;

	String pre_Calculation = " ... ";

	String indication_Quote = " ... ";

	Boolean product_FMEA_Available = Boolean.FALSE;

	Boolean process_FMEA_Available = Boolean.FALSE;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	protected Logger getLogger() {
		return logger;
	}

	/**
	 * @return the sundryInformation
	 */
	public String getSundryInformation() {
		return sundryInformation;
	}

	/**
	 * @param sundryInformation
	 *            the sundryInformation to set
	 */
	public void setSundryInformation(String sundryInformation) {
		this.sundryInformation = sundryInformation;
	}

	/**
	 * @return the bo
	 */
	@OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
	public BusinessOpportunity getBo() {
		return bo;
	}

	/**
	 * @param bo
	 *            the bo to set
	 */
	public void setBo(BusinessOpportunity bo) {
		this.bo = bo;
		if (name == null || name.length() == 0) {
			name = bo.getName();
		}
	}

	/**
	 * @return the phase 0&1 -> Prototype .. 2&3 -> Final Product
	 */
	public short getPhase() {
		return phase;
	}

	/**
	 * @param phase
	 *            the phase to set: 0&1 -> Prototype .. 2&3 -> Final Product
	 */
	public void setPhase(short phase) {
		this.phase = phase;
	}

	public String getTargeted_End_Customer_Group() {
		return targeted_End_Customer_Group;
	}

	public void setTargeted_End_Customer_Group(
			String targeted_End_Customer_Group) {
		this.targeted_End_Customer_Group = targeted_End_Customer_Group;
	}

	public Boolean getHas_It_Potential_For_Sales() {
		return has_It_Potential_For_Sales;
	}

	public void setHas_It_Potential_For_Sales(Boolean has_It_Potential_For_Sales) {
		this.has_It_Potential_For_Sales = has_It_Potential_For_Sales;
	}

	public Boolean getIs_There_A_Product_Or_Variant_Plan() {
		return is_There_A_Product_Or_Variant_Plan;
	}

	public void setIs_There_A_Product_Or_Variant_Plan(
			Boolean is_There_A_Product_Or_Variant_Plan) {
		this.is_There_A_Product_Or_Variant_Plan = is_There_A_Product_Or_Variant_Plan;
	}

	public Boolean getIs_It_Feasible_In_Technical_Terms() {
		return is_It_Feasible_In_Technical_Terms;
	}

	public void setIs_It_Feasible_In_Technical_Terms(
			Boolean is_It_Feasible_In_Technical_Terms) {
		this.is_It_Feasible_In_Technical_Terms = is_It_Feasible_In_Technical_Terms;
	}

	public Boolean getIs_It_Feasible_In_Economic_Terms() {
		return is_It_Feasible_In_Economic_Terms;
	}

	public void setIs_It_Feasible_In_Economic_Terms(
			Boolean is_It_Feasible_In_Economic_Terms) {
		this.is_It_Feasible_In_Economic_Terms = is_It_Feasible_In_Economic_Terms;
	}

	public String getPre_Calculation() {
		return pre_Calculation;
	}

	public void setPre_Calculation(String pre_Calculation) {
		this.pre_Calculation = pre_Calculation;
	}

	public String getIndication_Quote() {
		return indication_Quote;
	}

	public void setIndication_Quote(String indication_Quote) {
		this.indication_Quote = indication_Quote;
	}

	public Boolean getProduct_FMEA_Available() {
		return product_FMEA_Available;
	}

	public void setProduct_FMEA_Available(Boolean product_FMEA_Available) {
		this.product_FMEA_Available = product_FMEA_Available;
	}

	public Boolean getProcess_FMEA_Available() {
		return process_FMEA_Available;
	}

	public void setProcess_FMEA_Available(Boolean process_FMEA_Available) {
		this.process_FMEA_Available = process_FMEA_Available;
	}

	/**
	 * @return the prototypeTeam
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, optional = true, fetch = FetchType.EAGER)
	public ConcreteSupplyNetwork getPrototypeTeam() {
		return prototypeTeam;
	}

	/**
	 * @param prototypeTeam
	 *            the prototypeTeam to set
	 */
	public void setPrototypeTeam(ConcreteSupplyNetwork prototypeTeam) {
		this.prototypeTeam = prototypeTeam;
	}

	/**
	 * @return the asnPrototypeTeam
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, optional = true, fetch = FetchType.EAGER)
	@Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	public AbstractSupplyNetwork getAsnPrototypeTeam() {
		return asnPrototypeTeam;
	}

	/**
	 * @param asnPrototypeTeam
	 *            the asnPrototypeTeam to set
	 */
	public void setAsnPrototypeTeam(AbstractSupplyNetwork asnPrototypeTeam) {
		this.asnPrototypeTeam = asnPrototypeTeam;
	}

	/**
	 * @return the prototype
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, optional = true, fetch = FetchType.EAGER)
	public ComplexProduct getPrototype() {
		return prototype;
	}

	/**
	 * @param prototype
	 *            the prototype to set
	 */
	public void setPrototype(ComplexProduct prototype) {
		this.prototype = prototype;
	}

	/**
	 * @return the asnFinalTeam
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, optional = true, fetch = FetchType.EAGER)
	@Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	public AbstractSupplyNetwork getAsnFinalTeam() {
		return asnFinalTeam;
	}

	/**
	 * @param asnFinalTeam
	 *            the asnFinalTeam to set
	 */
	public void setAsnFinalTeam(AbstractSupplyNetwork asnFinalTeam) {
		this.asnFinalTeam = asnFinalTeam;
	}

	/**
	 * @return the finalTeam
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, optional = true, fetch = FetchType.EAGER)
	public ConcreteSupplyNetwork getFinalTeam() {
		return finalTeam;
	}

	/**
	 * @param finalTeam
	 *            the finalTeam to set
	 */
	public void setFinalTeam(ConcreteSupplyNetwork finalTeam) {
		this.finalTeam = finalTeam;
	}

	/**
	 * @return the tempTeams
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<ConcreteSupplyNetwork> getTempTeams() {
		return tempTeams;
	}

	/**
	 * @param tempTeams
	 *            the tempTeams to set
	 */
	public void setTempTeams(List<ConcreteSupplyNetwork> tempTeams) {
		if (tempTeams != null) {
			this.tempTeams = tempTeams;
		}
	}

	/**
	 * @return the finalProduct
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, optional = true, fetch = FetchType.EAGER)
	public ComplexProduct getFinalProduct() {
		return finalProduct;
	}

	/**
	 * @param finalProduct
	 *            the finalProduct to set
	 */
	public void setFinalProduct(ComplexProduct finalProduct) {
		this.finalProduct = finalProduct;
	}

	@Override
	public String toString() {
		return String.format("id: '%s' name: '%s'", getId(), getName());
	}

	/**
	 * 
	 * A method used in the integration of bottom up TF into the overall Sudden
	 * system. Essentially this one replaces a single node within a CSN with the
	 * multiple nodes generated by bottom up TF
	 * 
	 * @param concreteSupplyNetwork
	 * @param ourRoleNode
	 * @param newSolution
	 */
	public ConcreteSupplyNetwork addNewBottomUpSolution(
			ConcreteSupplyNetwork concreteSupplyNetwork,
			ASNRoleNode ourRoleNode, ConcreteSupplyNetwork newSolution) {

		logger.trace("++++++++++ hurray new BU/TF solution +++++++++++");
		/*
		 * The basic aim here is to expand the CSN by replacing the indicated
		 * node with a set of nodes/dependencies generated by bottom up TF.
		 * 
		 * No decomposition possible for this part ergo no dependencies coming
		 * into it. Good :) Dependencies going out are much easier to deal with
		 * since they relate to a node which is being replaced by just one node.
		 * 
		 * Ones coming in can't really be sensibly be dealt with anyway - need
		 * to split it to map right etc etc
		 */

		ConcreteSupplyNetwork newNetworkToAdd = concreteSupplyNetwork.clone();
		AbstractSupplyNetwork tempNetwork = newNetworkToAdd.getASN().clone();

		// 1 Find da node to replace in the original CSN
		boolean found = false;
		Iterator<ASNRoleNode> roleNodes = tempNetwork.getAllRoleNodes()
				.iterator();
		ASNRoleNode nodeToReplace = null;
		ASNRoleNode tempNode = null;

		while (roleNodes.hasNext() && !found) {
			tempNode = roleNodes.next();
			found = (tempNode.getQualificationProfile()
					.getProductIndividualURI().equals(ourRoleNode
					.getQualificationProfile().getProductIndividualURI()));
		}

		// We should have found it!
		if (found) {
			nodeToReplace = tempNode;
		} else {
			logger
					.debug(" Hummm. Matching of products from Sudden ontology/Jade ontology in bottom up TF not lined up :)");
		}

		// Find the 'final' node from our CSN
		found = false;
		roleNodes = newSolution.getASN().getAllRoleNodes().iterator();
		ASNRoleNode finalNodeNewSolution = null;

		while (roleNodes.hasNext() && !found) {
			tempNode = roleNodes.next();
			logger.debug(" Comparing "
					+ tempNode.getQualificationProfile().getProductTypes().get(
							0)
					+ " to "
					+ nodeToReplace.getQualificationProfile().getProductTypes()
							.get(0));

			// Oh I don't know. Somehow I can't make them line up without doing
			// this!
			String tempNodeURI = tempNode.getQualificationProfile()
					.getProductTypes().get(0);

			found = tempNodeURI.equals(nodeToReplace.getQualificationProfile()
					.getProductTypes().get(0));
		}

		// We should have found it!
		if (found) {
			finalNodeNewSolution = tempNode;
		} else {
			logger
					.debug(" Hummm. Matching of products from Sudden ontology/Jade ontology in bottom up TF not lined up :) ie not found the final node that we're after");
		}

		logger.debug(" Before changes network to expand is " + newNetworkToAdd);

		logger.debug(" node to replace is " + nodeToReplace);

		// Silly footwork to avoid issuesd with cloning losing the ID of
		// nodes....
		// Oh and *nasty* side effects if done non cloned. Or can we just do
		// that now?
		// newNetworkToAdd.getASN().getAllRoleNodes().remove(nodeToReplace);
		// newNetworkToAdd.getCandidateSuppliers().keySet().remove(nodeToReplace);

		// newNetworkToAdd.getASN().getAllRoleNodes().add(nodeToReplace);
		// newNetworkToAdd.getCandidateSuppliers().keySet().remove(nodeToReplace);

		// First get the dependencies going out from it and move them to going
		// out
		// from the final node from the new bit.
		ASNMaterialDependency tempMDep;
		List<ASNDependency> newDependencies = new ArrayList<ASNDependency>();
		for (ASNDependency dep : tempNetwork.getMyDependencies()) {
			if (dep.getdependencytype().equals(
					TeamFormationConstants.MaterialDependencyType)
					&& dep.getSourceNode().equals(nodeToReplace)) {
				tempMDep = (ASNMaterialDependency) dep;
				tempMDep.setSourceNode(finalNodeNewSolution);
				newDependencies.add(tempMDep);
			} else if (dep.getdependencytype().equals(
					TeamFormationConstants.MaterialDependencyType)
					&& dep.getTargetNode().equals(nodeToReplace)) {
				tempMDep = (ASNMaterialDependency) dep;
				tempMDep.setTargetNode(finalNodeNewSolution);
				newDependencies.add(tempMDep);
			} else {
				newDependencies.add(dep);
			}
		}

		// Then add the dependencies from the new solution
		newDependencies.addAll(newSolution.getASN().getMyDependencies());

		// Then set the dependencies
		tempNetwork.setMyDependencies(newDependencies);

		// Now throw away the node that we're expanding
		tempNetwork.getMyNodes().remove(nodeToReplace);
		newNetworkToAdd.setASN(tempNetwork);

		logger.debug(" Temp network is " + tempNetwork);

		HashMap<ASNRoleNode, Supplier> tempMap = new HashMap<ASNRoleNode, Supplier>();

		tempMap.putAll(newNetworkToAdd.getCandidateSuppliers());
		tempMap.putAll(newSolution.getCandidateSuppliers());
		/*
		 * Now find the node which needs replacing from the cloned CSN - the
		 * empty one basically :)
		 */
		ASNRoleNode nodeToRemove = null;
		for (ASNRoleNode roleNode : tempMap.keySet()) {
			if (tempMap.get(roleNode) == null) {
				nodeToRemove = roleNode;
			}
		}
		tempMap.keySet().remove(nodeToRemove);

		newNetworkToAdd.setCandidateSuppliers(tempMap);

		logger.debug(" After removing the nodes that needed removing it is "
				+ newNetworkToAdd);

		logger.debug(" New solution to replace node is " + newSolution
				+ " with role nodes " + newSolution.getASN().getAllRoleNodes());
		logger
				.debug(" and non role nodes "
						+ newSolution.getASN().getMyNodes());

		List<ASNNode> tempNodes = tempNetwork.getMyNodes();
		tempNodes.addAll(newSolution.getASN().getAllRoleNodes());
		tempNodes.remove(nodeToReplace);
		logger.debug(" Nodes carried over from old solution " + tempNodes);

		newNetworkToAdd.getASN().setMyNodes(tempNodes);

		// Coordination fit reuslts contain their own CSN!! Not totally sure why
		// but it needs something doing about it :)
		newNetworkToAdd.getCoordinationFitResults().setConcreteSupplyNetwork(
				newNetworkToAdd);

		logger.debug(" Candidate suppliers in new solution "
				+ newSolution.getCandidateSuppliersAsMap());
		logger.debug(" Candidate suppliers in new network "
				+ newNetworkToAdd.getCandidateSuppliersAsMap());
		logger.debug(" Candidate suppliers combined "
				+ newNetworkToAdd.getCandidateSuppliers());
		logger.debug(" After putting everying back in " + newNetworkToAdd);

		newNetworkToAdd.setASN(newNetworkToAdd.getASN().clone());
		return newNetworkToAdd.clone();
	}

	public ConcreteSupplyNetwork findTempTeamWithId(Long id) {
		for (ConcreteSupplyNetwork team : tempTeams) {
			if (team.getId().equals(id)) {
				return team;
			}
		}
		return null;
	}
}
