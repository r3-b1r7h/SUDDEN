package biz.sudden.baseAndUtility.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.designCoordination.teamFormation.dataStructures.ASNRoleNode;
import biz.sudden.designCoordination.teamFormation.dataStructures.ConcreteSupplyNetwork;
import biz.sudden.designCoordination.teamFormation.dataStructures.Supplier;

@Entity
public class Invitation implements Connectable {
	public enum Status {
		OPEN, ACCEPTED, REJECTED, CLOSED
	};

	private Long id;
	private CaseFile caseFile;
	private ConcreteSupplyNetwork concreteSupplyNetwork;
	private ASNRoleNode roleNode;
	private Status status = Status.OPEN;

	protected Invitation() {
	}

	public Invitation(CaseFile caseFile, ConcreteSupplyNetwork csn,
			ASNRoleNode roleNode) {
		this.caseFile = caseFile;
		this.concreteSupplyNetwork = csn;
		this.roleNode = roleNode;
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

	@ManyToOne(cascade = CascadeType.ALL)
	public CaseFile getCaseFile() {
		return caseFile;
	}

	public void setCaseFile(CaseFile caseFile) {
		this.caseFile = caseFile;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public ConcreteSupplyNetwork getConcreteSupplyNetwork() {
		return concreteSupplyNetwork;
	}

	public void setConcreteSupplyNetwork(
			ConcreteSupplyNetwork concreteSupplyNetwork) {
		this.concreteSupplyNetwork = concreteSupplyNetwork;
	}

	@ManyToOne
	public ASNRoleNode getRoleNode() {
		return roleNode;
	}

	public void setRoleNode(ASNRoleNode roleNode) {
		this.roleNode = roleNode;
	}

	@Transient
	public Supplier getSupplier() {
		return concreteSupplyNetwork.getSupplierForNode(roleNode);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
