package biz.sudden.baseAndUtility.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/testContext.xml" })
public class ScopeSessionTest {
	ScopeService session;

	@Autowired
	public void setService(ScopeService session) {
		this.session = session;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testScopeSessionImpl() {

		// fail("Not yet implemented");
	}

	@Test
	public void testGetUserScope() {
		// assertNotNull(object)
	}

	@Test
	public void testSetUserScope() {
		// session.setUserScope(scope);
		// fail("Not yet implemented");
	}

	@Test
	public void testSetRepository() {
		// fail("Not yet implemented");
	}

	@Test
	public void testCreateScope() {
		// fail("Not yet implemented");
	}

	@Test
	public void testDeleteScope() {
		// fail("Not yet implemented");
	}

	@Test
	public void testRetrieveAllScopes() {
		// fail("Not yet implemented");
	}

	@Test
	public void testRetrieveUniversalScope() {
		// fail("Not yet implemented");
	}

	@Test
	public void testRetrieveScopeByString() {
		// fail("Not yet implemented");
	}

	@Test
	public void testRetrieveScopeBySetOfConnectable() {
		// fail("Not yet implemented");
	}

	@Test
	public void testRetrieveAssociationsFrom() {
		// fail("Not yet implemented");
	}

	@Test
	public void testRetrieveOccurrencesFrom() {
		// fail("Not yet implemented");
	}

}
