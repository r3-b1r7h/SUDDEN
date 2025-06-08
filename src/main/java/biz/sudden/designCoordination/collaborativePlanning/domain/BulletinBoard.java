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

import biz.sudden.baseAndUtility.domain.IDInterface;
import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BulletinBoard implements IDInterface, Connectable {

	private String name;
	private Long id;
	private Long springId;
	private List<Topic> topicList = new ArrayList<Topic>();
	private Date dateCreated;
	private SuddenUser owner;

	public Long getSpringId() {
		return springId;
	}

	public void setSpringId(Long springId) {
		this.springId = springId;
	}

	@ManyToOne
	public SuddenUser getOwner() {
		return owner;
	}

	public void setOwner(SuddenUser owner) {
		this.owner = owner;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@OneToMany(mappedBy = "belongsToBoard", cascade = CascadeType.ALL)
	public List<Topic> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}

}
