//package biz.sudden.designCoordination.collaborativePlanning.repository;
//
//import static org.junit.Assert.fail;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import biz.sudden.designCoordination.collaborativePlanning.domain.BulletinBoard;
//import biz.sudden.designCoordination.collaborativePlanning.domain.Topic;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/biz/sudden/spring/rootContext.xml" })
//public class BulletinBoardRepositoryTest {
//
//	@Autowired
//	BulletinBoardRepository repository;
//
//	@Before
//	public void setUp() throws Exception {
//
//	}
//
//	@After
//	public void tearDown() throws Exception {
//
//	}
//
//	@Test
//	public void testBulletin() {
//		try {
//			BulletinBoard board = new BulletinBoard();
//			board.setName("testBoard");
//			Topic topic = new Topic();
//			topic.setBelongsToBoard(board);
//			Topic topic2 = new Topic();
//			topic2.setBelongsToBoard(board);
//			List list = new LinkedList();
//			list.add(topic);
//			list.add(topic2);
//			board.setTopicList(list);
//			repository.create(board);
//		} catch (Exception ex) {
//			System.out.println(ex);
//			fail(ex.toString());
//		}
//	}
//
//}
