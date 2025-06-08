package biz.sudden.designCoordination.collaborativePlanning.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoard;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.domain.Topic;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPBulletinBoardService;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPSendMessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/testContext.xml" })
@Transactional
public class BulletinBoardServiceTest {

	@Autowired
	CPBulletinBoardService service;

	@Autowired
	CPSendMessageService sendService;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testBulletin() {
		try {
			Communication comm = new Communication();

			sendService.createMessage(comm);
			BulletinBoard board = new BulletinBoard();
			BulletinBoard board2 = new BulletinBoard();
			board.setName("testBoard");
			Topic topic = new Topic();
			topic.setName("topic1");
			Topic topic2 = new Topic();
			topic2.setName("topic2");
			Topic topic3 = new Topic();
			topic3.setName("topic3_forBulletingBoard2");

			// UsageTracker tracker = (UsageTracker)board;

			service.createBulletin(board);
			service.createBulletin(board2);
			service.createTopicAndAddToBulletinBoard(board, topic);
			service.createTopicAndAddToBulletinBoard(board, topic2);
			service.createTopicAndAddToBulletinBoard(board2, topic3);
			// service
			List<BulletinBoard> myList = service.retrieveAllBulletinBoards();

			// for (BulletinBoard bulletinBoard : myList) {
			// System.out.println(bulletinBoard.getTopicList().size());
			// }

			System.out.println(myList.get(1).getTopicList().size());

			// List<Topic> myList2 = service.retrieveAllTopics(board);

			System.out
					.println("Topic list size " + board.getTopicList().size());

			for (Topic mytopic : board.getTopicList()) {
				System.out.println("Topic " + mytopic.getName());
			}

			System.out.println("Topic list size "
					+ board2.getTopicList().size());

			for (Topic mytopic : board2.getTopicList()) {
				System.out.println("Topic " + mytopic.getName());
			}

			// System.out.println(myList2.size());

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex);
			fail(ex.toString());
		}
	}

}
