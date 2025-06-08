//package biz.sudden.baseAndUtility.service;
//
//import static org.junit.Assert.*;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import biz.sudden.baseAndUtility.service.UserService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/biz/sudden/spring/testContext.xml" })
//public class UserServiceImplTest {
//	static int COUNT;
//	UserService service;
//	private final String TESTUSERNAME = "chris";
//	private final String TEST = "TEST";
//	
//	@Autowired
//	public void setService(UserService service){
//		this.service = service;
//		System.out.println(TEST+" set the UserService");
//	}
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		COUNT =0;
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//		System.out.println(COUNT +" TestSetUps");
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		COUNT++;
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//	
//	@Test
//	public void testUserServiceImpl() {
//		
//	}
//
//	@Test
//	public void testCreateUser() {
//		service.createUser("TESTUSERNAME", "TESTUSERNAME");
//		
//	}
//
//	@Test
//	public void testIsCorrectLogin() {
//		assertTrue(TEST+" user is correct login", service.isCorrectLogin("TESTUSERNAME", "TESTUSERNAME"));
//		System.out.println("User is correct login after creation?????????");
//		
//	}
//
//	@Test
//	public void testUserExists() {
//		System.out.println(TEST+" run testUserExists");
//		assertTrue("user "+ TESTUSERNAME+" exists", service.userExists("TESTUSERNAME"));
//		assertFalse("user " +TESTUSERNAME+"1 does exist", service.userExists(TESTUSERNAME+"1"));
//	}
//
//	@Test
//	public void testGetRepository() {
//		service.getRepository();
//		
//	}
//
//	@Test
//	public void testSetRepository() {
//		
//		
//	}
//
//}
