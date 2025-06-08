package biz.sudden.designCoordination.teamFormation.dataStructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.MapKeyManyToMany;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.designCoordination.handleBO.dataStructures.AbstractSupplyNetwork;
import biz.sudden.evaluation.coordinationFit.service.CoordinationFitResult;
import biz.sudden.evaluation.performanceMeasurement.domain.CompetenceNeeded;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

/**
 * 
 * A combination of an abstract supply network with a single set of candidate
 * teams. I'm doing it this way rather than using one ASN with many teams linked
 * to it to allow for situations where there are multiple ASN's in use at once.
 * 
 * Some slightly fancy interfaces on this method :) (with more to come later)
 * 
 * @author mcassmc
 * 
 */

@Entity
public class ConcreteSupplyNetwork implements Connectable {

	private Long id;
	private AbstractSupplyNetwork myASN;
	// Sorry. Can't be a plain map. I've got to clone it.
	private HashMap<ASNRoleNode, Supplier> candidateSuppliers;
	private Double ranking;
	private CoordinationFitResult cfResult;

	public ConcreteSupplyNetwork() {
	}

	/**
	 * Yes holding both the ASN & the roleNode/Supplier link separately is a bit
	 * clunky. But the ASN is being kept since it contains non role specific
	 * information and is just all round useful anyway.
	 * 
	 * The attached rankings should (I expect currently) either be positive or
	 * -MinValue if they've not been set yet.
	 * 
	 * @param ASNin
	 * @param candidateSuppliersIn
	 */
	public ConcreteSupplyNetwork(AbstractSupplyNetwork ASNin,
			Map<ASNRoleNode, Supplier> candidateSuppliersIn) {
		this.myASN = ASNin;
		this.candidateSuppliers = new HashMap<ASNRoleNode, Supplier>(
				candidateSuppliersIn);
		// This should be bad enough to make sure people notice something is
		// wrong ;)
		this.cfResult = new CoordinationFitResult(this);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Double getRanking() {
		return this.ranking;
	}

	public void setRanking(Double combinedScore) {
		this.ranking = combinedScore;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@MapKeyManyToMany(targetEntity = ASNRoleNode.class)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	public Map<ASNRoleNode, Supplier> getCandidateSuppliers() {
		return this.candidateSuppliers;
	}

	public void setCandidateSuppliers(
			Map<ASNRoleNode, Supplier> candidateSuppliersIn) {
		if (candidateSuppliersIn != null) {
			this.candidateSuppliers = new HashMap<ASNRoleNode, Supplier>(
					candidateSuppliersIn);
		} else {
			this.candidateSuppliers = null;
		}
	}

	/**
	 * The method used to extract the suppliers and the profiles for the roles
	 * against which they need to be evaluated.
	 * 
	 * @return an list containing pairs of the form <EvaluationProfile,
	 *         supplierToEvaluateVSProfile>
	 */
	@Transient
	public Map<CompetenceNeeded, Supplier> getCandidateSuppliersWithProfile() {
		Map<CompetenceNeeded, Supplier> resultAsList = new HashMap<CompetenceNeeded, Supplier>();
		Supplier tempSupplier;

		for (ASNRoleNode tempASNnode : this.candidateSuppliers.keySet()) {
			tempSupplier = this.candidateSuppliers.get(tempASNnode);
			resultAsList.put(tempASNnode.getCompetenceNeeded(), tempSupplier);
		}

		return resultAsList;
	}

	/**
	 * The method used to extract the suppliers for all the nodes
	 * 
	 * @return an map containing entries of the form <ASNRoleNode, Supplier>
	 */
	@Transient
	public Map<ASNRoleNode, Supplier> getCandidateSuppliersAsMap() {
		HashMap<ASNRoleNode, Supplier> resultMap = new LinkedHashMap<ASNRoleNode, Supplier>();

		for (ASNRoleNode tempASNnode : this.candidateSuppliers.keySet()) {
			if (tempASNnode instanceof ASNInitialNode)
				continue;

			Supplier tempSupplier = this.candidateSuppliers.get(tempASNnode);
			resultMap.put(tempASNnode, tempSupplier);
		}

		return resultMap;
	}

	/**
	 * A method for extracting the material dependency subgraphs from the
	 * concrete supply network. Intended for use by coordination fit and the
	 * like where this is required.
	 * 
	 * Moved to now work backwards as logically required.
	 */
	@Transient
	public Collection<ASNRoleNode> getNodesConnectedbyMaterialDependency(
			ASNRoleNode nodeToConnectTo) {
		ArrayList<ASNRoleNode> result = new ArrayList<ASNRoleNode>();
		ASNRoleNode tempNode;

		for (ASNMaterialDependency md : myASN
				.getMaterialDependenciesInto(nodeToConnectTo)) {
			tempNode = (ASNRoleNode) md.getSourceNode();
			result.add(tempNode);
		}

		return result;
	}

	/**
	 * This method overloads the method which
	 * 
	 */
	@Transient
	public Collection<ASNRoleNode> getNodesConnectedbyMaterialDependency(
			ASNInitialNode nodeToConnectTo) {
		ArrayList<ASNRoleNode> result = new ArrayList<ASNRoleNode>();
		ASNRoleNode tempNode;

		for (ASNMaterialDependency md : myASN
				.getMaterialDependenciesFrom(nodeToConnectTo)) {
			tempNode = (ASNRoleNode) md.getTargetNode();
			result.add(tempNode);
		}

		return result;
	}

	@Transient
	public Set<ASNRoleNode> getNodesConnectedWithNode(ASNRoleNode node) {
		Set<ASNRoleNode> result = new LinkedHashSet<ASNRoleNode>();

		for (ASNDependency md : myASN.getMyDependencies()) {
			if (!(md instanceof ASNMaterialDependency))
				continue;

			if (md.getSourceNode().equals(node))
				result.add((ASNRoleNode) md.getTargetNode());

			if (md.getTargetNode().equals(node))
				result.add((ASNRoleNode) md.getSourceNode());
		}

		return result;
	}

	/**
	 * 
	 * Get the supplier assigned to a particular node. If no supplier is found
	 * then I'll return a null for now.
	 * 
	 * @param roleNode
	 * @return the supplier assigned to that node
	 */
	@Transient
	public Supplier getSupplierForNode(ASNRoleNode roleNode) {
		// This is where I pay for not using a hash map or something :)
		// It's not too bad though.
		Supplier result = null;
		Iterator<ASNRoleNode> nextNodeIterator = candidateSuppliers.keySet()
				.iterator();
		boolean done = false;
		ASNRoleNode tempNode;

		while (nextNodeIterator.hasNext() && !done) {
			tempNode = nextNodeIterator.next();
			if (tempNode.equals(roleNode)) {
				done = true;
				result = candidateSuppliers.get(tempNode);
			}
		}

		return result;
	}

	@Transient
	public ASNInitialNode getInitialNode() {
		return myASN.getInitialNode();
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = true)
	public AbstractSupplyNetwork getASN() {
		return myASN;
	}

	public void setASN(AbstractSupplyNetwork asn) {
		myASN = asn;
	}

	// TODO - Also maybe business opportunity in some kind of node & overall
	// team evaluation criteria somewhere.
	@Embedded
	public CoordinationFitResult getCoordinationFitResults() {
		if (cfResult == null)
			cfResult = new CoordinationFitResult(this);
		return cfResult;
	}

	public void setCoordinationFitResults(CoordinationFitResult cfResult) {
		this.cfResult = cfResult;
		cfResult.setConcreteSupplyNetwork(this);
	}

	@Transient
	public boolean isReadyForCF() {
		for (Supplier tempSupplier : getCandidateSuppliersAsMap().values()) {

			if (!tempSupplier.isReadyForCF())
				return false;
		}
		return true;
	}

	public Map<Supplier, Supplier> findDependentSuppliers(
			Organization supplier1, Organization supplier2) {
		Map<Supplier, Supplier> map = new HashMap<Supplier, Supplier>();

		for (ASNDependency md : myASN.getMyDependencies()) {
			if (!(md instanceof ASNMaterialDependency))
				continue;

			if (md.getSourceNode() instanceof ASNRoleNode
					&& md.getTargetNode() instanceof ASNRoleNode) {

				Supplier sourceSupplier = getSupplierForNode((ASNRoleNode) md
						.getSourceNode());
				Supplier targetSupplier = getSupplierForNode((ASNRoleNode) md
						.getTargetNode());

				Organization sourceOrganization = sourceSupplier
						.getOrganisation();
				Organization targetOrganization = targetSupplier
						.getOrganisation();

				if (supplier1.equals(sourceOrganization)
						&& supplier2.equals(targetOrganization))
					map.put(sourceSupplier, targetSupplier);

				if (supplier2.equals(sourceOrganization)
						&& supplier1.equals(targetOrganization))
					map.put(targetSupplier, sourceSupplier);
			}
		}

		return map;
	}

	public Map<ASNRoleNode, Supplier> findRoleNodesSuppliedByOrganization(
			Organization organization) {
		if (organization == null)
			throw new IllegalArgumentException(
					"Organization should not be null.");

		Map<ASNRoleNode, Supplier> values = new HashMap<ASNRoleNode, Supplier>();

		for (Map.Entry<ASNRoleNode, Supplier> entry : getCandidateSuppliersAsMap()
				.entrySet()) {
			Supplier supplier = entry.getValue();
			if (supplier != null
					&& organization.equals(supplier.getOrganisation()))
				values.put(entry.getKey(), supplier);
		}

		return values;
	}

	@Override
	public String toString() {
		String result = "";

		result += " \nConcrete supply network  " + id;
		result += "\n nodes:\n";
		for (ASNRoleNode rn : candidateSuppliers.keySet()) {
			result += " ASN Node " + rn + " with supplier "
					+ candidateSuppliers.get(rn);
		}
		result += "\n dependencies:\n";

		for (ASNDependency dep : this.myASN.getMyDependencies()) {
			result += dep.toString();
		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		ConcreteSupplyNetwork other = (ConcreteSupplyNetwork) obj;
		if (other.id != null && this.id != null && other.id.equals(this.id))
			return true;

		return super.equals(obj);
	}

	@Override
	public ConcreteSupplyNetwork clone() {
		ConcreteSupplyNetwork result = new ConcreteSupplyNetwork();

		// should we really clone this here? I don't think so.
		result.setASN(this.myASN);
		// If you want to avoid having the same role nodes in multiple ASNs/CSNs
		// then we have to :)
		// result.setASN(this.myASN.clone());

		// this is *NOT* a deep copy of the map! keys and values are the same...
		// OK?
		// result.setCandidateSuppliers((Map<ASNRoleNode, Supplier>)
		// this.candidateSuppliers.clone());

		ASNRoleNode tempASNRoleNode;
		boolean found;
		Iterator<ASNRoleNode> overRoleNodes;

		/*
		 * A sort of clone which makes sure that the role nodes in the candidate
		 * hash map actually contain the cloned role nodes from the cloned ASN
		 */
		HashMap<ASNRoleNode, Supplier> candidates = new HashMap<ASNRoleNode, Supplier>();
		for (ASNRoleNode rn : result.myASN.getAllRoleNodes()) {
			// we don't clone the key on purpose; lets see if it works.
			// but we definitely need to clone the supplier object
			tempASNRoleNode = null;
			found = false;
			overRoleNodes = this.candidateSuppliers.keySet().iterator();

			while (!found && (overRoleNodes.hasNext())) {
				tempASNRoleNode = overRoleNodes.next();
				// TODO - note this one is a tiny dangerous if duplicated
				// products in the ASN
				// luckily bundling will mean 99% of time same suppliers
				// assigned to each anyway I guess?!
				found = (rn.getQualificationProfile().productIndividualURI
						.equals(tempASNRoleNode.getQualificationProfile().productIndividualURI));
			}

			// Certain mild level of complexity here to deal with ASNs being fed
			// through when no suppliers found for a given node.
			// The first test covers when we try to clone them directly. The
			// second is for the first time it comes in here or something.
			if (found) {
				Supplier s = candidateSuppliers.get(tempASNRoleNode);
				if (s != null) {
					candidates.put(rn, s.limitedClone());
				} else {
					candidates.put(rn, null);
				}
			} else
				candidates.put(rn, null);
		}
		result.setCandidateSuppliers(candidates);

		result.setCoordinationFitResults(cfResult);

		result.setRanking(ranking);

		return result;
	}
}
