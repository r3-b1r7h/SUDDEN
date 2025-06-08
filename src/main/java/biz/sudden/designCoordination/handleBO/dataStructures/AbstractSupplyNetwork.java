package biz.sudden.designCoordination.handleBO.dataStructures;

import java.util.ArrayList;
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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.baseAndUtility.domain.NetworkEvaluationProfile;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNInitialNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNMaterialDependency;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNodeFixedSupplier;
import biz.sudden.designCoordination.teamFormation.dataStructures.QualificationProfile;
import biz.sudden.designCoordination.teamFormation.dataStructures.TeamFormationConstants;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

import com.hp.hpl.jena.ontology.Individual;

/**
 * The 'infamous' abstract supply network. It's basically a team for which no
 * team members have yet been assigned. Thus - at the minimum - it's a set of
 * roles which normally have evaluation criteria attached to them (during HBO.).
 * This last factor ISN'T guaranteed if the ASN has been produced by bottom up
 * team formation. However the design is very general and the node structure can
 * also contain some other information.
 * 
 * @author mcassmc
 */
@Entity
public class AbstractSupplyNetwork implements Connectable {

	Logger logger = Logger.getLogger(AbstractSupplyNetwork.class);

	private Long id;
	private List<ASNDependency> myDependencies = new ArrayList<ASNDependency>();
	private List<ASNNode> myNodes = new ArrayList<ASNNode>();
	private Product goalProduct;
	private String goalProductString;
	private NetworkEvaluationProfile asnEvaluationProfile;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getGoalProductString() {
		return goalProductString;
	}

	public void setGoalProductString(String s) {
		this.goalProductString = s;
	}

	/*
	 * Commented out due to causing a hibernate crash and not being - at this
	 * stage at least strictly required for anything
	 */
	/*
	 * public Product getGoalProduct() {return this.goalProduct;}
	 * 
	 * public void setGoalProduct(Product p) {this.goalProduct = p;}
	 */

	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<ASNDependency> getMyDependencies() {
		return myDependencies;
	}

	public void setMyDependencies(List<ASNDependency> myDependencies) {
		this.myDependencies = myDependencies;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<ASNNode> getMyNodes() {
		return myNodes;
	}

	public void setMyNodes(List<ASNNode> myNodes) {
		this.myNodes = myNodes;
	}

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	public NetworkEvaluationProfile getASNEvaluationProfile() {
		return asnEvaluationProfile;
	}

	public void setASNEvaluationProfile(NetworkEvaluationProfile nep) {
		if (asnEvaluationProfile != null && nep == null)
			logger.error("Overwrite Network Evaluation Profile for this ASN!!");
		asnEvaluationProfile = nep;
	}

	/** Deep copy of all nodes and dependencies and the rest */
	@Override
	public AbstractSupplyNetwork clone() {
		AbstractSupplyNetwork asn = new AbstractSupplyNetwork();

		if (this.myDependencies != null) {
			ArrayList<ASNDependency> newdeps = new ArrayList<ASNDependency>();
			for (ASNDependency x : this.myDependencies) {

				newdeps.add(x.clone());
			}
			asn.setMyDependencies(newdeps);
		}

		if (myNodes != null) {

			ArrayList<ASNNode> newnodes = new ArrayList<ASNNode>();
			ASNNode tempNode = null;
			for (ASNNode x : this.myNodes) {
				tempNode = x.clone();
				newnodes.add(tempNode);

				// Also replace all nodes in the dependencies as required
				for (ASNDependency asnDep : asn.myDependencies) {
					if (asnDep.getSourceNode().equals(x)) {
						asnDep.setSourceNode(tempNode);
					}
					if (asnDep.getTargetNode().equals(x)) {
						asnDep.setTargetNode(tempNode);
					}
				}

			}
			asn.setMyNodes(newnodes);

		}
		if (getASNEvaluationProfile() != null)
			asn.setASNEvaluationProfile(this.asnEvaluationProfile.clone());
		asn.goalProduct = this.goalProduct;
		asn.goalProductString = this.goalProductString;
		return asn;
	}

	/**
	 * Deprecated in order to handle Persistence use: public void
	 * addInitialNode(AbstractSupplyNetwork asn, ASNInitialNode initialNode) and
	 * similar in HanleBOService Add a node - no need to be careful about where
	 * as long since the dependencies give the ordering, not the data structure
	 * in which the nodes are held
	 * 
	 * @see HandleBOServiceImplUpdated#addInitialNode(AbstractSupplyNetwork,
	 *      ASNInitialNode)
	 * @see HandleBOServiceImplUpdated#addRoleNode(AbstractSupplyNetwork,
	 *      ASNRoleNode)
	 * @param node
	 */
	@Deprecated
	public void addNewNode(ASNNode node) {
		this.myNodes.add(node);
	}

	/**
	 * Deprecated in order to handle Persistence use: public void
	 * addInitialNode(AbstractSupplyNetwork asn, ASNInitialNode initialNode) and
	 * similar in HanleBOService Add a node - no need to be careful about where
	 * as long since the dependencies give the ordering, not the data structure
	 * in which the nodes are held
	 * 
	 * @see HandleBOServiceImplUpdated#addDependency(AbstractSupplyNetwork,
	 *      ASNDependency)
	 * 
	 * @param node
	 */
	@Deprecated
	public void addNewDependcy(ASNDependency dependency) {
		ASNDependency dep = dependency.clone();
		this.myDependencies.add(dep);
	}

	//
	//
	//
	//
	//
	// // A Node might be used in several ASNs???
	// // if so... this causes Trouble and crashes Tomcat
	// //@OneToMany(cascade=CascadeType.ALL)
	// // lets try this one : /// no fetchtype cuases problemss..FetchTyp.EAGER
	// also :(
	// @ManyToMany(cascade=CascadeType.ALL/*, fetch=FetchType.EAGER*/)
	// public List<ASNNode> getNodes(){
	// return this.myNodes ;
	// }

	// TODO - things like deleting nodes I suppose
	// Quite tricky that though - could leave dependencies 'dangling'.
	// What gets added here will depend rather on what HBO turns out to need.
	/**
	 * Consider using getNodesConnectedbyMaterialDependency in
	 * concreteSupplyNetwork instead. It will tend to do what you want more
	 * directly.
	 * 
	 * @param nodeToConnectTo
	 * @return
	 */
	@Transient
	@Deprecated
	public List<ASNMaterialDependency> getMaterialDependenciesFrom(
			ASNRoleNode nodeToConnectTo) {

		ArrayList<ASNMaterialDependency> result = new ArrayList<ASNMaterialDependency>();

		/*
		 * One thing for sure, thisisn't the fastest way to do this. Or more
		 * precisely it is but better data structures would no doubt be much
		 * more efficient somehow. Oh well :)
		 */
		for (ASNDependency nextDep : myDependencies) {
			if (nextDep.getdependencytype().equals(
					TeamFormationConstants.MaterialDependencyType)) {
				// Object equality at present - might want changing to compare
				// ID's or something.
				if (nextDep.getSourceNode().equals(nodeToConnectTo)) {
					/*
					 * The cast hereshould be safe. We've checked type one if
					 * statement above after all :) If the class hierarchy
					 * changes though this may need to change.
					 */
					result.add((ASNMaterialDependency) nextDep);
				}
			}
		}

		return result;
	}

	/**
	 * An overloading of the congent method for ASNRoleNodes. It has to be this
	 * way because ASNInitialNodes have a differing type to the role nodes &
	 * only these two types will have material dependencies attached to them.
	 * 
	 * @param nodeToConnectTo
	 * @return
	 */
	@Transient
	@Deprecated
	public List<ASNMaterialDependency> getMaterialDependenciesFrom(
			ASNInitialNode nodeToConnectTo) {
		ArrayList<ASNMaterialDependency> result = new ArrayList<ASNMaterialDependency>();

		/*
		 * One thing for sure, thisisn't the fastest way to do this. Or more
		 * precisely it is but better data structures would no doubt be much
		 * more efficient somehow. Oh well :)
		 */
		for (ASNDependency nextDep : myDependencies) {
			if (nextDep.getdependencytype().equals(
					TeamFormationConstants.MaterialDependencyType)) {
				// Object equality at present - might want changing to compare
				// ID's or something.
				if (nextDep.getSourceNode().equals(nodeToConnectTo)) {
					/*
					 * The cast here should be safe. We've checked type one if
					 * statement above after all :) If the class hierarchy
					 * changes though this may need to change.
					 */
					result.add((ASNMaterialDependency) nextDep);
				}
			}
		}

		return result;
	}

	/**
	 * An overloading of the congent method for ASNRoleNodes. It has to be this
	 * way because ASNInitialNodes have a differing type to the role nodes &
	 * only these two types will have material dependencies attached to them.
	 * 
	 * @param nodeToConnectTo
	 * @return
	 */
	@Transient
	public List<ASNMaterialDependency> getMaterialDependenciesInto(
			ASNRoleNode nodeConnectedTo) {
		ArrayList<ASNMaterialDependency> result = new ArrayList<ASNMaterialDependency>();

		/*
		 * One thing for sure, thisisn't the fastest way to do this. Or more
		 * precisely it is but better data structures would no doubt be much
		 * more efficient somehow. Oh well :)
		 */
		for (ASNDependency nextDep : myDependencies) {
			if (nextDep.getdependencytype().equals(
					TeamFormationConstants.MaterialDependencyType)) {
				// Object equality at present - might want changing to compare
				// ID's or something.
				if (nextDep.getTargetNode() != null
						&& nextDep.getTargetNode().equals(nodeConnectedTo)) {
					/*
					 * The cast hereshould be safe. We've checked type one if
					 * statement above after all :) If the class hierarchy
					 * changes though this may need to change.
					 */
					result.add((ASNMaterialDependency) nextDep);
				}
			}
		}

		return result;
	}

	/**
	 * Deprecated in order to handle Persistence use: public void
	 * addInitialNode(AbstractSupplyNetwork asn, ASNInitialNode initialNode) and
	 * similar in HanleBOService Get all the role nodes in the ASN
	 * 
	 * @see HandleBOServiceImplUpdated#getAllRoleNodes(AbstractSupplyNetwork)
	 * @return those nodes for which suppliers must be added
	 */
	@Transient
	public List<ASNRoleNode> getAllRoleNodes() {

		ArrayList<ASNRoleNode> result = new ArrayList<ASNRoleNode>();

		for (ASNNode nextNode : this.myNodes) {
			if (nextNode.getClass().equals(ASNRoleNode.class)
					|| nextNode.getClass().equals(ASNComplexNode.class)
					|| nextNode.getClass().equals(
							ASNRoleNodeFixedSupplier.class)) {
				result.add((ASNRoleNode) nextNode);
			}
		}

		return result;
	}

	/**
	 * Returns the ASNRoleNode that has the given id.
	 * 
	 * @param id
	 *            The id of the node.
	 * @return The ASNRoleNode that has the given id, null if the id does not
	 *         exist or the node is not an ASNRoleNode.
	 */
	public ASNRoleNode getRoleNodeWithId(Long id) {
		for (ASNNode nextNode : this.myNodes) {
			if (nextNode.getId().equals(id)) {
				if (ASNRoleNode.class.isAssignableFrom(nextNode.getClass())) {
					return (ASNRoleNode) nextNode;
				}
			}
		}

		return null;
	}

	/**
	 * Returns the ASNMaterialDependency that has the given id.
	 * 
	 * @param id
	 *            The id of the dependency.
	 * @return The ASNMaterialDependency that has the given id, null if the id
	 *         does not exist or the dependency is not an ASNMaterialDependency.
	 */
	public ASNMaterialDependency getMaterialDependencyWithId(Long id) {
		for (ASNDependency nextDep : this.myDependencies) {
			if (nextDep.getId().equals(id)) {
				if (ASNMaterialDependency.class.isAssignableFrom(nextDep
						.getClass())) {
					return (ASNMaterialDependency) nextDep;
				}
			}
		}

		return null;
	}

	/**
	 * A convenience function for creating the initial node. This contains an
	 * indication of what the BO is actually trying to make.
	 * 
	 * @param initialProducts
	 */
	public void addInitialNode(ArrayList<Product> initialProducts) {
		this.myNodes.add(new ASNInitialNode(initialProducts));
	}

	/**
	 * Deprecated in order to handle Persistence use: public void
	 * addInitialNode(AbstractSupplyNetwork asn, ASNInitialNode initialNode) and
	 * similar in HanleBOService
	 * 
	 * @see HandleBOServiceImplUpdated#getInitialNode(AbstractSupplyNetwork) Get
	 *      the initial node. This method shouldn't ever fail for a well
	 *      constructed ASN, although the actual initial node can contain no
	 *      information. If it does It will return a null answer. Note that this
	 *      node is only 'initial' in the sense of the subchain of suppliers and
	 *      material dependencies. The ASN itself has no real concept of
	 *      direction.
	 * @return
	 */
	@Transient
	@Deprecated
	public ASNInitialNode getInitialNode() {

		ASNInitialNode result = null;
		Iterator<ASNNode> overNodes = myNodes.iterator();
		boolean done = false;
		ASNNode tempNode;

		while (overNodes.hasNext() && !done) {
			tempNode = overNodes.next();
			if (tempNode.getClass().equals(ASNInitialNode.class)) {
				result = (ASNInitialNode) tempNode;
				done = true;
			}
		}
		return result;
	}

	/**
	 * A convenience function for creating the final node. This contains an
	 * indication of what the BO is actually trying to make.
	 * 
	 * @param productToProduce
	 */
	@Transient
	@Deprecated
	public void addFinalNode(Product productToProduce) {
		ASNComplexNode finalNode = new ASNComplexNode();
		finalNode.setComplexPartToAssemble(productToProduce.getURI());
		this.myNodes.add(finalNode);
		this.goalProduct = productToProduce;
		this.goalProductString = null;
	}

	/**
	 * A convenience function for creating the final node. This contains an
	 * indication of what the BO is actually trying to make.
	 * 
	 * @param productToProduce
	 */

	public void addFinalNode(String productToProduce,
			QualificationProfile qualProfile) {
		ASNComplexNode finalNode = new ASNComplexNode();
		finalNode.setComplexPartToAssemble(productToProduce);
		finalNode.setQualificationProfile(qualProfile);
		this.myNodes.add(finalNode);
		this.goalProductString = productToProduce;
		this.goalProduct = null;
	}

	public boolean allComplexNodesExpanded() {
		boolean result = true;

		Iterator<ASNNode> overRoleNodes = this.myNodes.iterator();
		ASNNode tempASNNode;
		StringBuffer debug = new StringBuffer();
		while (result && overRoleNodes.hasNext()) {
			tempASNNode = overRoleNodes.next();
			if (tempASNNode.getClass().equals(ASNComplexNode.class)) {
				System.out.println(" Testing node for expansion " + tempASNNode
						+ " result " + ((ASNComplexNode) tempASNNode).expanded);
				result = ((ASNComplexNode) tempASNNode).expanded;
				debug.append("; node exanded " + tempASNNode.toString() + ": "
						+ result);
			}
		}
		logger.debug(" Testing ASN for expansion " + this.getId() + " result "
				+ result);
		logger.debug(" nodes expanded " + debug);
		return result;
	}

	@Transient
	public ASNComplexNode getUnresolvedNode() {
		boolean found = false;

		ASNComplexNode result = null;

		Iterator<ASNNode> overRoleNodes = this.myNodes.iterator();
		ASNNode tempASNNode;

		while (!found && overRoleNodes.hasNext()) {
			tempASNNode = overRoleNodes.next();

			if (tempASNNode.getClass().equals(ASNComplexNode.class)) {
				logger.debug(" next complex node " + tempASNNode + " Resolved "
						+ ((ASNComplexNode) tempASNNode).expanded);
				found = !(((ASNComplexNode) tempASNNode).expanded);
			}
			if (found) {
				result = (ASNComplexNode) tempASNNode;
			}
		}

		return result;
	}

	@Transient
	public ASNComplexNode getComplexRoleNode(Individual typeToExpand) {
		boolean found = false;
		ASNComplexNode result = null;
		Iterator<ASNNode> overRoleNodes = this.myNodes.iterator();
		ASNNode tempASNNode;

		while (!found && overRoleNodes.hasNext()) {
			tempASNNode = overRoleNodes.next();
			if (tempASNNode.getClass().equals(ASNComplexNode.class)) {
				found = ((ASNComplexNode) tempASNNode)
						.getComplexPartToAssemble().equals(
								typeToExpand.getURI());
			}
			if (found) {
				result = (ASNComplexNode) tempASNNode;
			}
		}

		return result;
	}

	@Transient
	public ASNComplexNode getUnexpandedComplexRoleNode(Individual typeToExpand) {
		boolean found = false;
		ASNComplexNode result = null;
		Iterator<ASNNode> overRoleNodes = this.myNodes.iterator();
		ASNNode tempASNNode;

		while (!found && overRoleNodes.hasNext()) {
			tempASNNode = overRoleNodes.next();
			if (tempASNNode.getClass().equals(ASNComplexNode.class)) {
				found = (((ASNComplexNode) tempASNNode)
						.getComplexPartToAssemble().equals(
								typeToExpand.getURI()) && !((ASNComplexNode) tempASNNode).expanded);
			}
			if (found) {
				result = (ASNComplexNode) tempASNNode;
			}
		}

		return result;
	}

	@Transient
	@Deprecated
	public ASNRoleNode getFinalNode() {
		boolean foundComplex = false;
		boolean foundFixedSupplier = false;
		boolean foundRoleNode = false;
		ASNRoleNode result = null;
		Iterator<ASNNode> overRoleNodes = this.myNodes.iterator();
		ASNNode tempASNNode;
		Class tempClass;

		/*
		 * A silly branching here depending on if I've started with a product or
		 * a String
		 */
		if (this.goalProductString != null) {
			while (!foundComplex && overRoleNodes.hasNext()) {
				tempASNNode = overRoleNodes.next();
				tempClass = tempASNNode.getClass();

				if (tempClass.equals(ASNComplexNode.class)) {

					foundComplex = ((ASNComplexNode) tempASNNode)
							.getComplexPartToAssemble().equals(
									this.goalProductString);
				} else if (tempClass.equals(ASNRoleNodeFixedSupplier.class)) {
					foundFixedSupplier = ((ASNRoleNodeFixedSupplier) tempASNNode)
							.getQualificationProfile().getProductTypes().get(0)
							.equals(this.goalProductString);
				} else if (tempClass.equals(ASNRoleNode.class)) {
					foundRoleNode = ((ASNRoleNode) tempASNNode)
							.getQualificationProfile().getProductTypes().get(0)
							.equals(this.goalProductString);
				}

				if (foundComplex) {
					result = (ASNComplexNode) tempASNNode;
				}
				if (foundFixedSupplier) {
					result = (ASNRoleNodeFixedSupplier) tempASNNode;
				}
				if (foundRoleNode) {
					result = (ASNRoleNode) tempASNNode;
				}
			}
		} else if (this.goalProduct != null) {

			while (!foundComplex && overRoleNodes.hasNext()) {
				tempASNNode = overRoleNodes.next();
				tempClass = tempASNNode.getClass();

				if (tempClass.equals(ASNComplexNode.class)) {
					foundComplex = ((ASNComplexNode) tempASNNode)
							.getComplexPartToAssemble()
							.equals(this.goalProduct);
				}
				// NB can't check for fixed supplier or plain role nodes here :)
				// They don't have the complex info. Luckily we never reach here
				// right now anyway. Always a string specified product.

				if (foundComplex) {
					result = (ASNComplexNode) tempASNNode;
				}
			}
		}

		return result;
	}

	/**
	 * A recursive method for deleting role nodes - it deletes the role node
	 * along with every thing 'above' it in the Abstract supply networks graph.
	 * It is "above" because the ASN is a tree where the final product is the
	 * root.
	 */
	public void recursivelyDeleteRoleNodeAndChildren(ASNRoleNode nodeToDelete) {
		// Dependencies from the node - kill them :)
		this.myDependencies.removeAll(this
				.getMaterialDependenciesFrom(nodeToDelete));

		// Dependcies into the node, Recurse "up" and delete the nodes above
		for (ASNDependency d : this.getMaterialDependenciesInto(nodeToDelete)) {
			this.recursivelyDeleteRoleNodeAndChildren((ASNRoleNode) d
					.getSourceNode());
		}

		// Remove the node itself
		this.myNodes.remove(nodeToDelete);
	}

	/**
	 * A method for changing the type of a role node (complex or simple) into
	 * one where there is a fixed supplier involved
	 */
	public void assignFixedSupplier(ASNRoleNode nodeToAssignTo,
			Organization organisationToAttach) {
		// 1 get our shiny new 'fixed' node
		ASNRoleNodeFixedSupplier nodeWithFixedSupplier = new ASNRoleNodeFixedSupplier(
				organisationToAttach, nodeToAssignTo.getQualificationProfile()
						.clone(), nodeToAssignTo.getCompetenceNeeded());

		// 2 stick it in
		this.myNodes.add(nodeWithFixedSupplier);

		// 3 substitute in the dependencies
		for (ASNDependency ad : this.getMyDependencies()) {
			logger.debug(" testing dependency " + ad + " for node "
					+ nodeToAssignTo);
			if (ad.getSourceNode().equals(nodeToAssignTo)) {
				ad.setSourceNode(nodeWithFixedSupplier);
				logger.debug(" source node found");
			}
			if (ad.getTargetNode().equals(nodeToAssignTo)) {
				ad.setTargetNode(nodeWithFixedSupplier);
				logger.debug(" target node found");
			}
		}

		// 4 remove the old one
		this.myNodes.remove(nodeToAssignTo);
	}

	@Override
	public String toString() {
		String result = "";

		result += " Goal Product as Product " + this.goalProduct;

		result += " Goal Product as String " + this.goalProductString;

		result += " Final Node " + this.getFinalNode();

		result += " Nodes " + this.myNodes;

		result += " Dependencies " + this.myDependencies;

		return result;
	}

	@Transient
	public List<ASNMaterialDependency> getAllMaterialDependencies() {

		ArrayList<ASNMaterialDependency> matDeps = new ArrayList<ASNMaterialDependency>();

		for (ASNDependency d : this.myDependencies) {
			if (d.getClass().equals(ASNMaterialDependency.class)) {
				matDeps.add((ASNMaterialDependency) d);
			}
		}

		return matDeps;
	}

	// use: getMyDependencies
	// public Collection<ASNDependency> getAllDepencies() {
	// return this.myDependencies;
	// }
	//
	// Excuse my obviously crashing ignorance but why on earth should an object
	// use a
	// get method to access one of its own member variables??
}
