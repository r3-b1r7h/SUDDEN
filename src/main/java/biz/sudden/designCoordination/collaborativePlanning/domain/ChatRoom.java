package biz.sudden.designCoordination.collaborativePlanning.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import biz.sudden.baseAndUtility.domain.IDInterface;
import biz.sudden.baseAndUtility.domain.SuddenUser;

@Entity
public class ChatRoom implements IDInterface {

	private Long id;
	private String name;

	private List<SuddenUser> users = new ArrayList<SuddenUser>();
	private List<Communication> communications = new LinkedList<Communication>();

	/* ----------------- Getters and Setters BEGIN ----------------------------- */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	@ManyToMany(cascade = CascadeType.ALL)
	public List<SuddenUser> getUsers() {
		return users;
	}

	public void setUsers(List<SuddenUser> users) {
		this.users = users;
	}

	@Transient
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Communication> getCommunications() {
		return communications;
	}

	public void setCommunications(List<Communication> communications) {
		this.communications = communications;
	}

	/* ----------------- Getters and Setters END ----------------------------- */

	public void addUser(SuddenUser user) {
		getUsers().add(user);
	}

	public void removeUser(SuddenUser user) {
		getUsers().remove(user);
	}

	public void addCommunication(Communication communication) {
		getCommunications().add(communication);
	}

}
