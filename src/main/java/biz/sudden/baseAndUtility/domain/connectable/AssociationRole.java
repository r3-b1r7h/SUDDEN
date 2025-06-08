package biz.sudden.baseAndUtility.domain.connectable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Any;

import biz.sudden.baseAndUtility.domain.IDInterface;

/**
 * AssociationRole - AssociationRoles provide the link between Connectables and
 * Associations. They define how (role) a Connectable participates in an
 * Association. A particular AssociationRole is always attached to exactly one
 * Association and can be taken by exactly one Connectable at the same time.
 * AssociationRoles are "typed" through AssociationRoleTypes - every
 * AssociationRole of the same nature references the same AssociationRoleType
 * 
 * @author Matthias Neubauer
 * 
 */
@Entity
public class AssociationRole implements IDInterface {

	private Association parent;
	private AssocRoleType type;
	private Connectable player;
	private Long id;

	/**
	 * default constructor
	 */
	public AssociationRole() {
	}

	/**
	 * Constructor
	 * 
	 * @param aRT
	 *            AssocRoleType
	 * @param player
	 *            Connectable which plays this AssociationRole in the given
	 *            Association
	 * @param parent
	 *            Association to which the AssociaitonRole is attached
	 */
	public AssociationRole(AssocRoleType aRT, Connectable player,
			Association parent) {
		this.type = aRT;
		this.player = player;
		this.parent = parent;
	}

	/**
	 * get Id
	 * 
	 * @return Id of AssociationRole
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	/**
	 * set Id of the AssociationRole
	 * 
	 * @param id
	 *            id of AssociationRole
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * get the association to which this AssociationRole belongs
	 * 
	 * @return parent association of this AssociationRole
	 */
	@ManyToOne
	public Association getParent() {
		return parent;
	}

	/**
	 * set the parent association of this AssociationRole
	 * 
	 * @param parent
	 *            Association to which this AssociationRole belongs
	 */
	public void setParent(Association parent) {
		this.parent = parent;
	}

	/**
	 * get the AssocRoleType of this AssociationRole
	 * 
	 * @return AssocRoleType
	 */
	@ManyToOne
	public AssocRoleType getType() {
		return type;
	}

	/**
	 * set AssocRoleType of this AssociatonRole
	 * 
	 * @param type
	 *            AssocRoleType
	 */
	public void setType(AssocRoleType type) {
		this.type = type;
	}

	/**
	 * get the connectable which plays this AssociationRole
	 * 
	 * @return Connectable
	 */
	@Any(metaDef = "connectable", metaColumn = @Column(name = "player_type"))
	@JoinColumn(name = "player_id")
	public Connectable getPlayer() {
		return player;
	}

	/**
	 * set the connectable whic plays this AssociationRole
	 * 
	 * @param player
	 *            Connectable of this AssociationRole
	 */
	public void setPlayer(Connectable player) {
		this.player = player;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof AssociationRole
				&& getId().equals(((AssociationRole) o).getId()))
			return true;
		return false;
	}
}
