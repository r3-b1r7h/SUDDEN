package biz.sudden.designCoordination.collaborativePlanning.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import biz.sudden.baseAndUtility.domain.Actor;
import biz.sudden.baseAndUtility.domain.BusinessOpportunity;
import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.service.ActorService;
import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPGetMessageService;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPSendMessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/rootContext.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
public class ServiceImplTest extends SpringJUnit4ClassRunner {

	@Autowired
	private CPSendMessageService sendMessageService;
	@Autowired
	private CPGetMessageService getMessageService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CPSendMessageService getSendMessageService() {
		return sendMessageService;
	}

	public ActorService getActorService() {
		return actorService;
	}

	private Actor actorOne;
	private Actor actorTwo;
	private Actor actorThree;
	private BusinessOpportunity businessOpportunity;

	public ServiceImplTest() throws Exception {
		super(ServiceImplTest.class);

	}

	@Autowired
	public void setActorService(ActorService actorService) {
		this.actorService = actorService;
	}

	@Autowired
	public void setSendMessageService(CPSendMessageService sendMessageService) {
		this.sendMessageService = sendMessageService;
	}

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testCreateActor() {

		SuddenUser actor = new SuddenUser("eins", "eins", "eins");
		SuddenUser actor2 = new SuddenUser("zwei", "zwei", "zwei");
		SuddenUser actor3 = new SuddenUser("drei", "drei", "drei");

		List<SuddenUser> actorList = new ArrayList<SuddenUser>();
		actorList.add(actor2);
		actorList.add(actor3);

		Communication comm = new Communication();

		comm.setSubject("test");
		comm.setSender(actor);
		comm.setReceiver(actorList);

		sendMessageService.createMessage(comm);

		// if (1==1)
		// throw new RuntimeException("fehler bevor communication");
		//		
		// Communication comm1 = new Communication();
		// comm1.setSubject("test");
		// comm1.setSender(actor2);
		// comm1.setReceiver(actorList);

		// sendMessageService.createMessage(comm1);

		Communication testComm = getMessageService.retrieveAllCommunications()
				.get(0);

		Long id = testComm.getId();

		List<SuddenUser> list = getMessageService.retrieveReceiver(id);

		for (SuddenUser user : list) {
			System.out.println("User " + user.getNickname());
		}

		// System.out.println(getMessageService.retrieveReceiver(id));

		// System.out.println(getMessageService.retrieveMessagesFor(actor2));
	}

	// @Test
	// public void testSendMessageOne() {
	// System.out.println("Set up");
	// actorOne = actorService.createActor("eins");
	// actorTwo = actorService.createActor("zwei");
	// actorThree = actorService.createActor("drei");
	// // businessOpportunity = new BusinessOpportunity();
	// // businessOpportunity.setDescription("BusinessOpp");
	// List<Actor> actorList = new LinkedList<Actor>();
	// actorList.add(actorTwo);
	// sendMessageService.sendMessage(actorOne, actorList,
	// "hallo von eins nach zwei", "hallo von eins nach zwei");
	//
	// }

	// @Test
	// public void testSendMessageTwo() {
	// try {
	// List<String> stringList = new LinkedList<String>();
	// stringList.add("zwei");
	// sendMessageService.sendMessage("eins", stringList,
	// "hallo von eins nach zwei", "hallo von eins nach zwei");
	// } catch (AmbiguousActorException ex) {
	// fail(ex.getMessage());
	// } catch (ActorNotFoundException ex) {
	// fail(ex.getMessage());
	// } catch (Exception ex) {
	// fail(ex.getMessage());
	// }
	// }

	// @Test
	// public void testSendMessageThree() {
	// try {
	// List<String> stringList = new LinkedList<String>();
	// stringList.add("zwei");
	// Date date = new Date();
	// System.out.println("BO "+businessOpportunity);
	// //sendMessageService.sendMessage("eins", stringList, "DREI", "JO MEI",
	// new Date(), businessOpportunity);
	//			
	// } catch (AmbiguousActorException ex) {
	// fail(ex.getMessage());
	// } catch (ActorNotFoundException ex) {
	// fail(ex.getMessage());
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// fail(ex.getMessage());
	// }
	//
	// }

}
