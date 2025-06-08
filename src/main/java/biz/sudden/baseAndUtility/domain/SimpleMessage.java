package biz.sudden.baseAndUtility.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SimpleMessage {

	private String name;
	private Date sendDate;
	private Actor sender;
	private List<Actor> receiver = new LinkedList<Actor>();
	private Date deadLine;
	private String subject;
	private String message;
	private Long id;
	private BusinessOpportunity invitation;

	public SimpleMessage(String name, Date sendDate, Actor sender,
			List<Actor> receiver, Date deadLine, String subject,
			String message, Long id, BusinessOpportunity invitation) {
		super();
		this.name = name;
		this.sendDate = sendDate;
		this.sender = sender;
		this.receiver = receiver;
		this.deadLine = deadLine;
		this.subject = subject;
		this.message = message;
		this.id = id;
		this.invitation = invitation;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Actor getSender() {
		return sender;
	}

	public void setSender(Actor sender) {
		this.sender = sender;
	}

	public List<Actor> getReceiver() {
		return receiver;
	}

	public void setReceiver(List<Actor> receiver) {
		this.receiver = receiver;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BusinessOpportunity getInvitation() {
		return invitation;
	}

	public void setInvitation(BusinessOpportunity invitation) {
		this.invitation = invitation;
	}

}
