package biz.sudden.designCoordination.teamFormation.service;

import org.apache.log4j.Logger;

/**
 * A simple object that brings together information about the communication
 * between agents and services. This is used to update the controller and the
 * agent in turn after being modified.
 * 
 * If a Question is set, then statement is expected to be the answer to this
 * statement;
 * 
 * @author gweich
 */
public class AgentCommunicationObject {

	private Logger logger = Logger.getLogger(this.getClass());

	protected String agentName = "";
	protected String question = null;
	protected String statement = "";

	protected JadeService myService = null;

	protected AgentCommunications myCommunication = null;

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String AgentName) {
		this.agentName = AgentName;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String Question) {
		this.question = Question;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String Answer) {
		this.statement = Answer;
	}

	/** this way we tell the agent that the content has changed */
	public void updateAgent() {
		if (myService != null) {
			myService.talkToAgent(this);
		} else if (myCommunication != null) {
			myService = myCommunication.getMyService();
			if (myService != null) {
				myService.talkToAgent(this);
			} else {
				logger.debug("myService == null!!");
			}
		} else {
			logger.debug("myService == null && myCommunication == null !!!");
		}
	}

	/**
	 * Set the service used for talking back to the agent; i.e. notify the agent
	 * about changes in this object
	 * 
	 * @param service
	 */
	public void setMyService(JadeService service) {
		this.myService = service;
	}

	/**
	 * the "List" of communication objects (e.g. covering a dialog) within which
	 * this communication object is placed the AgentCommunicationsImpl object
	 * provides a link to the service used for talking back to the agent
	 * 
	 * @param myCommunication
	 *            the myCommunication to set
	 */
	public void setMyCommunication(AgentCommunications myCommunication) {
		this.myCommunication = myCommunication;
	}
}
