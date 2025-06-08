package biz.sudden.baseAndUtility.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import biz.sudden.baseAndUtility.domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/biz/sudden/spring/rootContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class RepositoryImplTest {// extends
									// AbstractDependencyInjectionSpringContextTests{

	private ActorRepository repository;
	private Actor actor;

	/**
	 * @param repository
	 *            The repository to set.
	 */
	@Autowired
	public void setRepository(ActorRepository repository) {
		this.repository = repository;
	}

	/**
	 * @throws java.lang.Exception
	 */

	@Before
	public void setUp() throws Exception {
		this.actor = new Actor();
		this.actor.setName("Thomas Feiner 3341123");

	}

	/**
	 * Test method for
	 * {@link at.jku.ce.dropbox.repository.hibernate.DropletRepositoryImpl#create(at.jku.ce.dropbox.domain.Droplet)}
	 * .
	 */
	@Test
	// @Rollback
	public void testCreate() {
		repository.create(actor);
		this.actor = new Actor();
		this.actor.setName("Thomas Feiner 223123123");
		repository.create(actor);
		Actor actor = repository.retrieve(this.actor.getName());
		assertEquals("Namen sind nicht gleich ", actor.getName(), this.actor
				.getName());
		// repository.delete(actor);
		// actor = repository.retrieve(actor.getName());
		// //assertEquals(actor.getName(), this.actor.getName());
		assertEquals("Aktor muss sein null ", actor, null);

	}

	/**
	 * Test method for
	 * {@link at.jku.ce.dropbox.repository.hibernate.DropletRepositoryImpl#delete(at.jku.ce.dropbox.domain.Droplet)}
	 * .
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link at.jku.ce.dropbox.repository.hibernate.DropletRepositoryImpl#retrieve(java.lang.Long)}
	 * .
	 */
	@Test
	public void testRetrieve() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link at.jku.ce.dropbox.repository.hibernate.DropletRepositoryImpl#retrieveAll()}
	 * .
	 */
	@Test
	public void testRetrieveAll() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link at.jku.ce.dropbox.repository.hibernate.DropletRepositoryImpl#update(at.jku.ce.dropbox.domain.Droplet)}
	 * .
	 */
	//	
	//	
	// @Timed(millis=3)
	// @Test
	// public void testUpdate() throws Exception {
	// //fail("Not yet implemented");
	// System.out.println("teste mich");
	//		
	// }

}
