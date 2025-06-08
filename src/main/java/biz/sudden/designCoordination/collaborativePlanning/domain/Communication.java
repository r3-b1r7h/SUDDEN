package biz.sudden.designCoordination.collaborativePlanning.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;

import biz.sudden.baseAndUtility.domain.IDInterface;
import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;

/**
 * A Communication needs at least two "Actors" which talk to each other, further
 * the Communication is stored into "CommunicationHistory" (together with a
 * "CommunicationItem")
 * 
 * @author Thomas Feiner
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Communication implements IDInterface {

	// @Override
	// public boolean equals(Object obj) {
	//
	// if (obj instanceof Communication && ((Communication)
	// obj).getId().equals(this.id)) {
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

	private Logger logger = Logger.getLogger(this.getClass());

	private Long id;
	private Long springId;

	public Long getSpringId() {
		return springId;
	}

	public void setSpringId(Long springId) {
		this.springId = springId;
	}

	private Long idComm;

	private Communication inReplyTo;

	// private List<Communication> children;
	private Date sendDate;
	private SuddenUser sender;
	private List<SuddenUser> receiver;
	private Date deadLine;
	private String message;
	private String subject;

	private List<Communication> childrenCommunications = new LinkedList<Communication>();

	// @Transient
	@OneToMany(cascade = CascadeType.ALL)
	public List<Communication> getChildrenCommunications() {
		return childrenCommunications;
	}

	public void setChildrenCommunications(
			List<Communication> childrenCommunications) {
		this.childrenCommunications = childrenCommunications;
	}

	private JsfLink jsfLink;

	@OneToOne(cascade = CascadeType.ALL)
	public JsfLink getJsfLink() {
		return jsfLink;
	}

	public void setJsfLink(JsfLink jsfLink) {
		this.jsfLink = jsfLink;
	}

	private Topic topic;

	// private BusinessOpportunity attachment;

	@ManyToOne(cascade = CascadeType.ALL)
	// cascade={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@Cascade(value = org.hibernate.annotations.CascadeType.ALL)
	public Communication getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Communication inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	// (cascade={CascadeType.MERGE, CascadeType.REFRESH})
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	// @ManyToOne(cascade=CascadeType.ALL)
	// public BusinessOpportunity getAttachment() {
	// return attachment;
	// }
	//
	// public void setAttachment(BusinessOpportunity attachment) {
	// this.attachment = attachment;
	// }

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(length = 1024)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// @ManyToOne(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
	// public Communication getInReplyTo() {
	// return inReplyTo;
	// }
	//
	// public void setInReplyTo(Communication inReplyTo) {
	// this.inReplyTo = inReplyTo;
	// }
	//	
	// @OneToMany(cascade = {CascadeType.ALL}, mappedBy="inReplyTo")
	// public List<Communication> getChildren() {
	// return children;
	// }
	//
	// public void setChildren(List<Communication> children) {
	// this.children = children;
	// }
	//	
	// public void addChildren(Communication communication) {
	// this.children.add(communication);
	// }

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@ManyToOne()
	// @ManyToOne(fetch=FetchType.EAGER, optional=false)
	public SuddenUser getSender() {
		return sender;
	}

	public void setSender(SuddenUser sender) {
		this.sender = sender;
	}

	@ManyToMany(targetEntity = SuddenUser.class)
	public List<SuddenUser> getReceiver() {
		return receiver;
	}

	public void setReceiver(List<SuddenUser> receiver) {
		this.receiver = receiver;
	}

	// public void addReceiver(User receiver) {
	// logger.debug("Receiver "+receiver);
	// this.receiver.add(receiver);
	// }

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

}