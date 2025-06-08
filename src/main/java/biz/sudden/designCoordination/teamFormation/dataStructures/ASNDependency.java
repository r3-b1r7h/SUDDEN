package biz.sudden.designCoordination.teamFormation.dataStructures;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.designCoordination.handleBO.dataStructures.ASNNode;

/**
 * The basic interface supplied by Abstract Supply Network dependencies
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ASNDependency implements Connectable {

	private Long id;
	protected ASNNode sourceNode;
	protected ASNNode targetNode;
	protected String ASNDependencyType;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	public void setSourceNode(ASNNode sourceNode) {
		this.sourceNode = sourceNode;
	}

	public void setTargetNode(ASNNode targetNode) {
		this.targetNode = targetNode;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = true)
	public ASNNode getSourceNode() {
		return sourceNode;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = true)
	public ASNNode getTargetNode() {
		return targetNode;
	}

	public String getdependencytype() {
		return this.ASNDependencyType;
	}

	/**
	 * Please don't use this method without care! Basically the type of
	 * dependency fixed on creation and won't change.
	 * 
	 * @param s
	 */
	public void setdependencytype(String s) {
		this.ASNDependencyType = s;
	}

	// The traditional clone
	public abstract ASNDependency fullClone();

	// NB - not a full deep clone method
	// Calling it something other than clone seems to mess with the inheritance
	@Override
	public abstract ASNDependency clone();
}
