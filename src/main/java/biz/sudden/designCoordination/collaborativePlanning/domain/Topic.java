package biz.sudden.designCoordination.collaborativePlanning.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Topic implements Connectable {

	// @Override
	// public boolean equals(Object obj) {
	//
	// if (obj instanceof Topic && ((Topic) obj).getId().equals(this.id)) {
	// return true;
	// } else {
	// return false;
	// }
	//
	// }
	//
	// @Override
	// public int hashCode() {
	// // TODO Auto-generated method stub
	// if (id != null)
	// return id.intValue();
	// else
	// return super.hashCode();
	// }

	private Long id;
	private Long springId;

	private BulletinBoard belongsToBoard;
	private String name;
	private SuddenUser owner;
	private Date dateCreated;
	private List<Communication> communications = new ArrayList<Communication>();

	public Long getSpringId() {
		return springId;
	}

	public void setSpringId(Long springId) {
		this.springId = springId;
	}

	@OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
	public List<Communication> getCommunications() {
		return communications;
	}

	public void setCommunications(List<Communication> communications) {
		this.communications = communications;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@ManyToOne
	public SuddenUser getOwner() {
		return owner;
	}

	public void setOwner(SuddenUser owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	public BulletinBoard getBelongsToBoard() {
		return belongsToBoard;
	}

	public void setBelongsToBoard(BulletinBoard belongsToBoard) {
		this.belongsToBoard = belongsToBoard;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
